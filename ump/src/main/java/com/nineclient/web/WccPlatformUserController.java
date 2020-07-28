package com.nineclient.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpGroup;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import net.sf.json.JSONObject;

import org.apache.avalon.framework.service.ServiceException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.poi.hssf.record.formula.functions.False;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.google.gson.JsonObject;
import com.nineclient.constant.PlatformType;
import com.nineclient.constant.WccFriendFormType;
import com.nineclient.constant.WccFriendSex;
import com.nineclient.constant.WccTemplateType;
import com.nineclient.dto.WXAccessToken;
import com.nineclient.model.PubOperator;
import com.nineclient.model.PubRole;
import com.nineclient.model.WccAutokbs;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccGroup;
import com.nineclient.model.WccLotteryActivity;
import com.nineclient.model.WccMenu;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccTemplate;
import com.nineclient.thread.ThreadPoolManager;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;
import com.nineclient.utils.SingletonHttpclient;
import com.nineclient.utils.WeiXinPubReturnCode;
import com.nineclient.weixin.FriendUpdateProcesser;
import com.nineclient.weixin.GetFriendProcesser;
import com.nineclient.weixin.WxMpUserInfo;
import com.nineclient.weixin.WxMpuserManager;

@RequestMapping("/wccplatformusers")
@Controller
@RooWebScaffold(path = "wccplatformusers", formBackingObject = WccPlatformUser.class)
public class WccPlatformUserController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WccPlatformUser wccPlatformUser, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException, WxErrorException {
		PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
		List<WccPlatformUser> lists = WccPlatformUser.findWccPlatformUserByNameAndCompany(wccPlatformUser.getAccount(),pub.getCompany());
		String msg="";
		if(null!=lists&&lists.size()>0){
			msg="公众平台名重复";
		}
		List<WccPlatformUser> listss = WccPlatformUser.findWccPlatformUserByplatid(wccPlatformUser.getPlatformId());
		if(null!=listss&&listss.size()>0){
			msg="原始ID重复";
		}
		//==========================验证appId和appSecret是否有效
		String result = getAccessToken(wccPlatformUser.getAppId(),wccPlatformUser.getAppSecret());
		JSONObject json = JSONObject.fromObject(result);
		WXAccessToken accesstoken = (WXAccessToken) JSONObject.toBean(json, WXAccessToken.class);
		if(accesstoken.getErrcode()!=null&&!"".equals(accesstoken.getErrcode())){
			uiModel.addAttribute("msg", "appid和appSecret无效，请重新填写！");
			uiModel.addAttribute("platformArr", PlatformType.values());
			//return "redirect:/wccplatformusers?form";
			return "wccplatformusers/create";
		}
		
		//==========================
		
		if (bindingResult.hasErrors() || (null != lists && lists.size() > 0)
				|| (null != listss && listss.size() > 0)) {
			populateEditForm(uiModel, wccPlatformUser);
			uiModel.addAttribute("msg", msg);
			return "redirect:/wccplatformusers?form";
			//return "wccplatformusers/create";
		}
		uiModel.asMap().clear();
		wccPlatformUser.setInsertTime(new Date());
		wccPlatformUser.setIsDeleted(true);
		wccPlatformUser.setIsValidation(true);
		wccPlatformUser.setCompany(pub.getCompany());
		wccPlatformUser.persist();

		// 保存常用文本平台
		/*System.out.println("=======常用文本=========");
		WccTemplate wccTemplate = new WccTemplate();
		wccTemplate.setTitle(wccPlatformUser.getAccount());
		wccTemplate.setType(WccTemplateType.CLASSIFY);
		wccTemplate.setInsertTime(new Date());
		wccTemplate.setParentId("0");
		wccTemplate.setName(pub);
		wccTemplate.setPlatformUser(wccPlatformUser);
		wccTemplate.persist();*/

		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		String appId = wccPlatformUser.getAppId();
		String appSecret = wccPlatformUser.getAppSecret();
		config.setAppId(appId);
		config.setSecret(appSecret);
		WxMpServiceImpl wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(config);

//		if (!"".equals(wxMpServiceImpl.getAccessToken())) {
//			getFrindAndGroup(wccPlatformUser,wxMpServiceImpl.getAccessToken());
//		}
		// 添加分组
		/*List<WxMpGroup> groups = new ArrayList<WxMpGroup>();
		try {
			groups = wxMpService.groupGet();
		} catch (WxErrorException e) {
			e.printStackTrace();
			uiModel.addAttribute("wccplatformusers", WccPlatformUser
					.findAllWccPlatformUsers(pub));
			return "redirect:/wccplatformusers?page=1&amp;size=10";
			//return "wccplatformusers/list";
		}
		if(groups!=null){
			for (WxMpGroup group : groups) {
				List<WccGroup> sgroup = WccGroup.findWccGroupByName(
						wccPlatformUser.getId(), group.getName());
				if (null != sgroup && sgroup.size() > 0) {
					continue;
				}
				WccGroup wgroup = new WccGroup();
				wgroup.setInsertTime(new Date());
				wgroup.setName(group.getName());
				wgroup.setIsDelete(true);
				wgroup.setPlatformUser(wccPlatformUser);
				wgroup.setGroupId(group.getId());
				wgroup.persist();
			}
		}*/
		 WxMpUserList user = wxMpService.userList(null);	
		  List<String> openIds=user.getOpenIds();
	       int totle=0;
	       int count=100;
	       int remainder=0;
	       List<WxMpUserInfo> list=null;
	       if(openIds!=null&&openIds.size()>0)
	       {
	    	   totle=openIds.size()/100;
	    	   remainder=openIds.size()%100;
	    	   for(int i=0;i<=totle;i++)
	    	   {
	    		  if(i!=totle)
	    		  {
	    			  list=WxMpuserManager.getBatchUserinfo(openIds.subList(i*count, (i+1)*count), Global.LANG, wxMpService.getAccessToken());  
	    			  ThreadPoolManager.getThreadpool().execute( new GetFriendProcesser(list,wccPlatformUser));
	    		  }
	    		  else
	    		 {
	    			  list= WxMpuserManager.getBatchUserinfo(openIds.subList(i*count, i*count+remainder), Global.LANG, wxMpService.getAccessToken());
	    			  ThreadPoolManager.getThreadpool().execute( new GetFriendProcesser(list,wccPlatformUser));
				
	    		}
	    		  
	    	   }
	    	   
	       }
//	   WxMpUserList user=null;
//	try {
//		user = wxMpService.userList(null);
//	} catch (WxErrorException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}
//		for (String openId : user.getOpenIds()) {
//			String lang = Global.LANG; // 语言
//			List<WccFriend> wfriend = WccFriend
//					.findWccFriendsByOpenId(openId);
//			if (null != wfriend && wfriend.size() > 0) {
//				continue;
//			}
//			WxMpUser users = new WxMpUser();
//			try {
//				users = wxMpService.userInfo(openId, lang);
//			} catch (WxErrorException e) {
//				e.printStackTrace();
//			}
////			long groupId = 0l;
////			try {
////				groupId = wxMpService.userGetGroup(openId);
////			} catch (WxErrorException e) {
////				e.printStackTrace();
////			}
//			WccFriend friend = new WccFriend();
////			WccGroup group = WccGroup.findWccGroupByGroupId(
////					wccPlatformUser.getId(), groupId);
////			friend.setWgroup(group);
//			friend.setFromType(WccFriendFormType.直接获取);
//			friend.setHeadImg(users.getHeadImgUrl());
//			friend.setInsertTime(new Date());
//			friend.setIsDeleted(true);
//			friend.setIsValidated(true);
//			String niceName = java.net.URLEncoder.encode(users.getNickname(),
//					"utf-8");
//			friend.setNickName(niceName);
//			WccFriendSex sex = null;
//			//sex = WccFriendSex.valueOf(users.getSex());
//			if (users.getSex().equals("男")) {
//				sex = WccFriendSex.男;
//			} else if (users.getSex().equals("女")) {
//				sex = WccFriendSex.女;
//			} else {
//				sex = WccFriendSex.未知;
//			}
//			DateUtil.getDate(users.getSubscribeTime());
//			friend.setSex(sex);
//			friend.setSubscribeTime(DateUtil.getDate(users.getSubscribeTime()));
//			friend.setProvince(users.getProvince());
//			friend.setCity(users.getCity());
//			friend.setCountry(users.getCountry());
//			friend.setArea(users.getCountry() + users.getProvince()
//					+ users.getCity());
//			friend.setOpenId(users.getOpenId());
//			friend.setPlatformUser(wccPlatformUser);
//			friend.persist();
//		}
		uiModel.addAttribute("wccplatformusers", WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(httpServletRequest)));
		return "redirect:/wccplatformusers?page=1&amp;size=10";
		//return "wccplatformusers/list";
	}
	
	//=================================根据appaid和appsecret获得access_token
	public String getAccessToken(String appId,String appSercet){
	
		String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ appId + "&secret=" + appSercet;
		String ACCESS_TOKEN = null;
		WXAccessToken token = null;
		try {
			ACCESS_TOKEN = doGetForWx(url);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return ACCESS_TOKEN;
		
		
	}
	//============================发送get请求
	public String doGetForWx(String url) throws Exception{
		String result = null;
		if (url == null || url.length() <= 0) {
			throw new ServiceException("Url can not be null.");
		}
		String temp = url.toLowerCase();
		if (!temp.startsWith("http://") && !temp.startsWith("https://")) {
			url = "http://" + url;
		}
		URI uri = URI.create(url);
		url = uri.toASCIIString();
		HttpGet httpMethod = null;
		try {
			httpMethod = new HttpGet(url);
			httpMethod.addHeader("content-type", "text/html;charset=UTF-8");
			HttpResponse response = SingletonHttpclient.getHttpClient().execute(httpMethod);
			// 判断请求是否成功
			if (!isResponseSuccessStatus(response)) {
				int status = response.getStatusLine().getStatusCode();
				System.out.println("Http Satus:" + status + ",Url:" + url);
				if (status >= 500 && status < 600) {
					if (httpMethod != null) {
						httpMethod.abort();
					}
					throw new IOException("Remote service<" + url + "> respose a error, status:" + status);
				}
				return null;
			}
			result = parseHttpResponseString(response);
			//			result = parseHttpResponse2String(response);
		} catch (Exception e) {
			if (httpMethod != null) {
				httpMethod.abort();
			}
			throw new ServiceException("get,error!url={" + url + "}", e);
		} finally {
			if (httpMethod != null) {
				httpMethod.abort();
			}
		}
		return result;
	}
	
	//==============发送url请求是否成功
	
	private static boolean isResponseSuccessStatus(HttpResponse response) {
		 int response_state = 200;
		int status = response.getStatusLine().getStatusCode();
		return response_state == status;
	}
	//=============================
	
	public static String parseHttpResponseString(HttpResponse response) throws Exception {
		HttpEntity entity = response.getEntity();
		InputStream in = null;
		StringBuffer result = new StringBuffer();
		//		new String(response.getResponseBodyAsString().getBytes("gb2312"));
		try {

			if (entity != null) {
				in = entity.getContent();
			}
			byte[] b = new byte[215];
			int i = 0;
			while ((i = in.read(b)) > 0) {
				result.append(new String(b, 0, i));
			}
		} catch (Exception e) {
			throw new ServiceException("get,error!response={" + response + "}");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return result.toString();
	}

	//============================================================
	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new WccPlatformUser());
        uiModel.addAttribute("url", Global.Url);
        uiModel.addAttribute("token",Global.token);
        uiModel.addAttribute("platformArr", PlatformType.values());
        return "wccplatformusers/create";
    }
	
	@RequestMapping(value = "/platformBeanToJson", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
    public String platformBeanToJson(Model uiModel, HttpServletRequest httpServletRequest) {
		 List<WccPlatformUser> list = WccPlatformUser.findAllWccPlatformUsers(CommonUtils.getCurrentPubOperator(httpServletRequest));
		 String[] aa = { "account", "id" };
		 String json = WccPlatformUser.toJsonArray(list, aa);
        return json;
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("wccplatformuser",
				WccPlatformUser.findWccPlatformUser(id));
		uiModel.addAttribute("itemId", id);
		return "wccplatformusers/show";
	}

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page,
    		@RequestParam(value = "displayId", required = false) Long displayId,
    		HttpServletRequest request,
    		HttpServletRequest httpServletRequest, @RequestParam(value = "size",
    		required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        uiModel.addAttribute("limit", 10);
       //System.out.println(PlatformType.values());
        Map<Integer, String> map = new HashMap<Integer, String>();
        List<Map<Integer, String>> list = new ArrayList<Map<Integer,String>>();
        for(int i = 0;i<PlatformType.values().length;i++){
        	map.put(i, PlatformType.values()[i].toString());
        	list.add(map);
        }
        //List<WccPlatForm> wccPlatFormList = WccPlatForm.getWccPlatFormList();
        //uiModel.addAttribute("platformList", wccPlatFormList);
        if(displayId == null){
        	displayId = CommonUtils.getCurrentDisPlayId(request);
        }
        uiModel.addAttribute("platformList",list);
        uiModel.addAttribute("platformArr", PlatformType.values());
        request.getSession().setAttribute("displayId", displayId);
        return "wccplatformusers/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid WccPlatformUser wccPlatformUser,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) throws WxErrorException,
			UnsupportedEncodingException {
		
			//==========================验证appId和appSecret是否有效
				String result = getAccessToken(wccPlatformUser.getAppId(),wccPlatformUser.getAppSecret());
				JSONObject json = JSONObject.fromObject(result);
				WXAccessToken accesstoken = (WXAccessToken) JSONObject.toBean(json, WXAccessToken.class);
				if(accesstoken.getErrcode()!=null&&!"".equals(accesstoken.getErrcode())){
					uiModel.addAttribute("msg", "appid和appSecret无效，请重新填写！");
					uiModel.addAttribute("platformArr", PlatformType.values());
					//return "redirect:/wccplatformusers?form";
					return "wccplatformusers/update";
				}
				
				//==========================
				
			if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, wccPlatformUser);
			
			return "wccplatformusers/update";
		}
		uiModel.asMap().clear();
		wccPlatformUser.setInsertTime(new Date());
		wccPlatformUser.setIsDeleted(true);
		wccPlatformUser.setIsValidation(true);
		wccPlatformUser.setCompany(CommonUtils.getCurrentPubOperator(
				httpServletRequest).getCompany());
		wccPlatformUser.merge();

		
	/*	WccMenu wccMeun = WccMenu.findWccMenuByType(wccPlatformUser.getId());
		wccMeun.setMenuName(wccPlatformUser.getAccount()); wccMeun.merge();*/
		 

		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		String appId = wccPlatformUser.getAppId();
		String appSecret = wccPlatformUser.getAppSecret();
		config.setAppId(appId);
		config.setSecret(appSecret);
		WxMpServiceImpl wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(config);

        List<WccFriend> friendList=WccFriend.findMassWccFriends2(wccPlatformUser.getId());
        if(friendList.size()>0){
		    for (WccFriend wccFriend : friendList) {
		    	wccFriend.remove();
			}	
        }
		 WxMpUserList user = wxMpService.userList(null);	
		  List<String> openIds=user.getOpenIds();
	       int totle=0;
	       int count=100;
	       int remainder=0;
	       List<WxMpUserInfo> list=null;
	       if(openIds!=null&&openIds.size()>0)
	       {
	    	   totle=openIds.size()/100;
	    	   remainder=openIds.size()%100;
	    	   for(int i=0;i<=totle;i++)
	    	   {
	    		  if(i!=totle)
	    		  {
	    			  list=WxMpuserManager.getBatchUserinfo(openIds.subList(i*count, (i+1)*count), Global.LANG, wxMpService.getAccessToken());  
	    			  ThreadPoolManager.getThreadpool().execute( new GetFriendProcesser(list,wccPlatformUser));
	    		  }
	    		  else
	    		 {
	    			  list= WxMpuserManager.getBatchUserinfo(openIds.subList(i*count, i*count+remainder), Global.LANG, wxMpService.getAccessToken());
	    			  ThreadPoolManager.getThreadpool().execute( new GetFriendProcesser(list,wccPlatformUser));
				
	    		}
	    		  
	    	   }
	    	   
	       }
		
		/*List<WxMpGroup> groups = new ArrayList<WxMpGroup>();
		// 添加分组
		try {
			groups = wxMpService.groupGet();
		} catch (WxErrorException e) {
			e.printStackTrace();
			uiModel.addAttribute("wccplatformusers", WccPlatformUser
					.findAllWccPlatformUsers(CommonUtils
							.getCurrentPubOperator(httpServletRequest)));
			//return "wccplatformusers/list";
			return "redirect:/wccplatformusers?page=1&amp;size=10";
		}
		for (WxMpGroup group : groups) {
			List<WccGroup> sgroup = WccGroup.findWccGroupByName(
					wccPlatformUser.getId(), group.getName());
			if (null != sgroup && sgroup.size() > 0) {
				continue;
			}
			WccGroup wgroup = new WccGroup();
			wgroup.setInsertTime(new Date());
			wgroup.setName(group.getName());
			wgroup.setIsDelete(true);
			wgroup.setPlatformUser(wccPlatformUser);
			wgroup.setGroupId(group.getId());
			wgroup.persist();
		}

		WxMpUserList user = wxMpService.userList(null);

		for (String openId : user.getOpenIds()) {
			String lang = Global.LANG; // 语言
			List<WccFriend> wfriend = WccFriend
					.findWccFriendsByOpenId(openId);
			if (null != wfriend && wfriend.size() > 0) {
				continue;
			}
			WxMpUser users = new WxMpUser();
			try {
				users = wxMpService.userInfo(openId, lang);
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			long groupId = 0l;
			try {
				groupId = wxMpService.userGetGroup(openId);
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			WccFriend friend = new WccFriend();
			WccGroup group = WccGroup.findWccGroupByGroupId(
					wccPlatformUser.getId(), groupId);
			friend.setWgroup(group);
			friend.setFromType(WccFriendFormType.直接获取);
			friend.setHeadImg(users.getHeadImgUrl());
			friend.setInsertTime(new Date());
			friend.setIsDeleted(true);
			friend.setIsValidated(true);
			String niceName = java.net.URLEncoder.encode(users.getNickname(),
					"utf-8");
			friend.setNickName(niceName);
			WccFriendSex sex = null;
			if (users.getSex().equals("男")) {
				sex = WccFriendSex.男;
			} else if (users.getSex().equals("女")) {
				sex = WccFriendSex.女;
			} else {
				sex = WccFriendSex.未知;
			}
			DateUtil.getDate(users.getSubscribeTime());
			friend.setSex(sex);
			friend.setSubscribeTime(DateUtil.getDate(users.getSubscribeTime()));
			friend.setProvince(users.getProvince());
			friend.setCity(users.getCity());
			friend.setCountry(users.getCountry());
			friend.setArea(users.getCountry() + users.getProvince()
					+ users.getCity());
			friend.setOpenId(users.getOpenId());
			friend.setPlatformUser(wccPlatformUser);
			friend.persist();
		}

		List<WccTemplate> list = WccTemplate
				.findWccTemplateByPidAndPlatform(wccPlatformUser.getId());
		if (list.size() > 0) {
			WccTemplate template = list.get(0);
			template.setTitle(wccPlatformUser.getAccount());
			template.merge();
		}*/	
		uiModel.addAttribute("wccplatformusers", WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(httpServletRequest)));
		//return "wccplatformusers/list";
		return "redirect:/wccplatformusers?page=1&amp;size=10";
	}

	@RequestMapping(value = "/update", produces = "text/html")
	public String updateForm(@RequestParam(value = "id", required = false) Long id, Model uiModel) {
		populateEditForm(uiModel, WccPlatformUser.findWccPlatformUser(id));
		//TODO
		uiModel.addAttribute("platformArr", PlatformType.values());
		return "wccplatformusers/update";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		WccPlatformUser wccPlatformUser = WccPlatformUser
				.findWccPlatformUser(id);
		try {
			wccPlatformUser.remove();// 需要查询是否存在关联。
		} catch (Exception e) {
			return "redirect:/wccplatformusers";
		}
		List<WccTemplate> list = WccTemplate.findWccTemplateByPlatform(wccPlatformUser.getId());
		if(list.size()>0){
			for(WccTemplate tem : list){
				tem.remove();
			}
		}
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/wccplatformusers";
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"wccPlatformUser_inserttime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, WccPlatformUser wccPlatformUser) {
		uiModel.addAttribute("wccPlatformUser", wccPlatformUser);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("wccgroups", WccGroup.findAllWccGroups());
	}

	String encodeUrlPathSegment(String pathSegment,
			HttpServletRequest httpServletRequest) {
		String enc = httpServletRequest.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}
	
	/**
	 * 查询公众平台
	 * @param uiModel
	 * @param request
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryPlatformUsers", produces = "text/html")
	 public String queryPlatformUsers(
			 Model uiModel,
			 HttpServletRequest request,
			 @RequestParam(value = "start", required = false) Integer start,
			 @RequestParam(value = "limit", required = false) Integer limit
			 ) throws ServletException, IOException {
		  PubOperator operator = CommonUtils.getCurrentPubOperator(request);
		  Map parmMap = new HashMap();
		  WccPlatformUser platformUser = new WccPlatformUser();
		  QueryModel<WccPlatformUser> qm = new QueryModel<WccPlatformUser>(platformUser, start, limit);
		  qm.getInputMap().putAll(parmMap);
		  PageModel<WccPlatformUser> pm = WccPlatformUser.getQueryPlatformUser(qm,operator);
		     pm.setPageSize(limit);
			 pm.setStartIndex(start);
			 //TODO
		//List<WccPlatForm> wccPlatFormList = WccPlatForm.getWccPlatFormList();
	     uiModel.addAttribute("WccPlatformUsers", pm.getDataList());
		 uiModel.addAttribute("maxPages", pm.getTotalPage());
		 uiModel.addAttribute("page", pm.getPageNo());
		 uiModel.addAttribute("limit", pm.getPageSize());
		 
     return "wccplatformusers/result";
	 }
	
	//=======================================TODO
	@RequestMapping(value="/queryPlatfromByCond",produces="text/html")
	public String queryPlatfromByCond(
		@RequestParam(value="start",required=false)Integer start,
		@RequestParam(value="limit",required=false)Integer limit,
		@RequestParam(value="platformName",required=false)String platformName,
		@RequestParam(value="platType",required=false)String platType,
		Model uiModel, HttpServletRequest request	
			) throws UnsupportedEncodingException
	{
		 PubOperator operator = CommonUtils.getCurrentPubOperator(request);
		 Map parmMap = new HashMap();
		 parmMap.put("platformName", platformName);
		 /*if("订阅号".equals(platType)){
		 parmMap.put("platType",0 );
		 }else{
			 parmMap.put("platType",1 );
		 }*/
		 parmMap.put("platType",platType);
		 WccPlatformUser wpfu = new WccPlatformUser();
		 QueryModel<WccPlatformUser> queryModel = new QueryModel<WccPlatformUser>(wpfu, start, limit);
		 queryModel.getInputMap().putAll(parmMap);
		 
		 PageModel<WccPlatformUser> pageModel = WccPlatformUser.getPlatformByCond(queryModel,operator);
		 
		 pageModel.setPageSize(limit);
		 pageModel.setStartIndex(start);
		 
		 uiModel.addAttribute("WccPlatformUsers", pageModel.getDataList());
		 uiModel.addAttribute("maxPages", pageModel.getTotalPage());
		 uiModel.addAttribute("page", pageModel.getPageNo());
		 uiModel.addAttribute("limit", pageModel.getPageSize());
		 
     return "wccplatformusers/result";
		
	}
	//============================================
	
	@RequestMapping(value = "tree", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String tree(Model uiModel, HttpServletRequest request,
			HttpServletResponse response) {
		StringBuffer strs = new StringBuffer();
		int num = 0;
		List<WccPlatformUser> list = null;
		PubOperator pub = CommonUtils.getCurrentPubOperator(request);
		if(pub.getPubRole() == null){
			list = WccPlatformUser.findAllWccPlatformUsers(pub);
		}else{
			list = CommonUtils.set2List(PubOperator.findPubOperator(pub.getId()).getPlatformUsers());
		}
		
		for (WccPlatformUser platform : list) {
			String str = "id:" + platform.getId() + ", pId:" + 0
					+ ",name:\"" + platform.getAccount() + "\""
					+ ",open:true";
			if (num == 0) {
				strs.append("{" + str + "}");
			} else {
				strs.append(",{" + str + "}");
			}
			num++;
		}
		return strs.toString();
	}
	
	//根据账号类型重新加载平台
	@RequestMapping(value = "newtree", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String newtree(Model uiModel, HttpServletRequest request,
			HttpServletResponse response) {
		
		StringBuffer strs = new StringBuffer();
		int num = 0;
		PubOperator pub = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> list = null;
		list=WccPlatformUser.findAllWccPlatformList(pub);
		for (WccPlatformUser platform : list) {
			String str = "id:" + platform.getId() + ", pId:" + 0
					+ ",name:\"" + platform.getAccount() + "\""
					+ ",open:true";
			if (num == 0) {
				strs.append("{" + str + "}");
			} else {
				strs.append(",{" + str + "}");
			}
			num++;
		}
		return strs.toString();
		
	}
	
	
	
	@RequestMapping(value = "/delete", produces = "text/html")
    public String delete(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel,
    		HttpServletRequest request) {
        WccPlatformUser platform = WccPlatformUser.findWccPlatformUser(id);
        PubOperator operator = new PubOperator();
        Set<WccPlatformUser> plats = new HashSet<WccPlatformUser>();
        plats.add(platform);
        operator.setPlatformUsers(plats);
        QueryModel<PubOperator> qm = new QueryModel<PubOperator>(
        		operator, 0, 10);
        int num = PubOperator.findAllPubOperatorsByPlatform(qm);
        String errmsg = "";
        if(num > 0){
       	 	errmsg = "该权限有账号关联，无法删除！";
        }else{
        	platform.setIsDeleted(false);
        	platform.merge();
       		uiModel.asMap().clear();
        }
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        request.getSession().setAttribute("errmsg", errmsg);
        return "redirect:/wccplatformusers";
    }
	
	/**
	 * 根据公众平台名称查公众平台
	 * @param uiModel
	 * @param request
	 * @param name
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "checkPlat", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String checkPlat(Model uiModel, HttpServletRequest request,
			@RequestParam(value = "name", required = true) String name,
			HttpServletResponse response) {
		return CheckEmpty(WccPlatformUser.findWccPlatformUserByNameAndCompany(name,CommonUtils.getCurrentPubOperator(request).getCompany()))?"false":"true";
	}
	
	/**
	 * 检查list
	 * @param lists
	 * @return
	 */
	private boolean CheckEmpty(List<?> lists){
		return null!=lists&&lists.size()>0;
	}
	
	/**
	 * 根据公众平台ID查公众平台
	 * @param uiModel
	 * @param request
	 * @param platId
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "checkPlatId", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String checkPlatId(Model uiModel, HttpServletRequest request,
			@RequestParam(value = "platId", required = true) String platId,
			HttpServletResponse response) {
		return CheckEmpty(WccPlatformUser.findWccPlatformUserByplatid(platId))?"false":"true";
	}
}

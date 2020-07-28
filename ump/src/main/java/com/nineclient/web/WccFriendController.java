package com.nineclient.web;

import com.nineclient.constant.PlatformType;
import com.nineclient.constant.WccFriendFormType;
import com.nineclient.constant.WccFriendSex;
import com.nineclient.model.PubOperator;
import com.nineclient.model.PubOrganization;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccGroup;
import com.nineclient.model.WccMassMessage;
import com.nineclient.model.WccMaterials;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.model.WccTemplate;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.DkfEntity;
import com.nineclient.utils.Emotion;
import com.nineclient.utils.Global;
import com.nineclient.utils.HttpClientUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;
import com.nineclient.utils.RecordData;
import com.nineclient.utils.Recordlist;
import com.nineclient.utils.WxEmotion;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import me.chanjar.weixin.mp.bean.custombuilder.NewsBuilder;
import net.sf.json.JSONObject;

import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RequestMapping("/wccfriends")
@Controller
@RooWebScaffold(path = "wccfriends", formBackingObject = WccFriend.class)
public class WccFriendController {
	private final Logger log = LoggerFactory.getLogger(WccFriendController.class);
	private static Map<String, String> map = new HashMap<String, String>();

	public static Map<String, String> acctoken = new HashMap<String, String>();

	public static Map<String, String> getAcctoken() {
		return acctoken;
	}

	public static void setAcctoken(Map<String, String> acctoken) {
		RecordController.acctoken = acctoken;
	}
	
	public static Map<String, String> getMap() {
		return map;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid WccFriend wccFriend,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, wccFriend);
			return "wccfriends/create";
		}
		uiModel.asMap().clear();
		wccFriend.persist();
		return "redirect:/wccfriends/"
				+ encodeUrlPathSegment(wccFriend.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new WccFriend());
		return "wccfriends/create";
	}

	@ResponseBody
	@RequestMapping(value = "under", produces = "text/html;charset=utf-8")
	public String underFriend(
			@RequestParam(value = "underId", required = false) String underId,
			@RequestParam(value = "fids", required = false) String fids,
			Model uiModel, HttpServletRequest request,
			HttpServletResponse response) {
		StringBuffer strs = new StringBuffer();
		String str[] = fids.split(",");
		PubOrganization organization = new PubOrganization();
		if (null != underId && !"".equals(underId)) {
			organization = PubOrganization.findPubOrganization(Long
					.parseLong(underId));
		}
		List<WccFriend> friends = WccFriend.findAllWccFriends();
		for (WccFriend friend : friends) {
			for (String id : str) {
				if (friend.getId() == Long.parseLong(id)) {
					friend.setOrganization(organization);
					friend.merge();
				}
			}
		}
		strs.append("true");
		return strs.toString();
	}

	@ResponseBody
	@RequestMapping(value = "group", produces = "text/html;charset=utf-8")
	public String groupFriend(
			@RequestParam(value = "groupId", required = false) String groupId,
			@RequestParam(value = "fids", required = false) String fids,
			Model uiModel, HttpServletRequest request,
			HttpServletResponse response) {
		StringBuffer strs = new StringBuffer();
		String str[] = fids.split(",");
		WccGroup group = new WccGroup();
		if (null != groupId && !"".equals(groupId)) {
			group = WccGroup.findWccGroup(Long.parseLong(groupId));
		}

		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		String appId = group.getPlatformUser().getAppId();
		String appSecret = group.getPlatformUser().getAppSecret();
		config.setAppId(appId);
		config.setSecret(appSecret);
		WxMpServiceImpl wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(config);

		List<WccFriend> friends = WccFriend.findAllWccFriends();
		for (WccFriend friend : friends) {
			for (String id : str) {
				if (friend.getId() == Long.parseLong(id)) {
					friend.setWgroup(group);
					friend.merge();
					try {
						wxMpService.userUpdateGroup(friend.getOpenId(),
								group.getGroupId());
					} catch (WxErrorException e) {
						e.printStackTrace();
					}
				}
			}
		}
		strs.append("true");
		return strs.toString();
	}

	
	@ResponseBody
	@RequestMapping(value = "savegroupFriend", produces = "text/html;charset=utf-8")
	public String savegroupFriend(
			@RequestParam(value = "newgroupId", required = false) String newgroupId,
			@RequestParam(value = "activityName", required = false) String activityName,
			@RequestParam(value = "platformType", required = false) String platformType,
			@RequestParam(value = "nickName", required = false) String nickName,
			@RequestParam(value = "platformUserId", required = false) String platformUserId,
			@RequestParam(value = "groupId", required = false) String groupId,
			@RequestParam(value = "area", required = false) String area,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "timeEndId", required = false) Integer timeEndId,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			Model uiModel, HttpServletRequest request,
			HttpServletResponse response) {
		String msgStr= "true";
		String timeStr = "";
		if (null != timeEndId) {
			Long timeStamp = new Date().getTime() - 3600 * 1000 * timeEndId;
			timeStr += timeStr + timeStamp;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
			timeStr = sdf.format(new Date(Long.parseLong(timeStr)));
		}
		Map<String, String> parmMap = new HashMap<String, String>();
		parmMap.put("sex", gender);
		parmMap.put("platformUserId", platformUserId);
		parmMap.put("groupId", groupId);
		parmMap.put("area", area);
		parmMap.put("nickName", nickName);
		parmMap.put("from", from);
		parmMap.put("timeStamp", timeStr);
		parmMap.put("startTime", startTime);
		parmMap.put("endTime", endTime);
		parmMap.put("activityName", activityName);
		parmMap.put("platformType", platformType);

		List<WccFriend> friends= WccFriend.getFriends(parmMap);
		if(friends.size()>0){
		WccGroup group = new WccGroup();
		if (null != newgroupId && !"".equals(newgroupId)) {
			group = WccGroup.findWccGroup(Long.parseLong(newgroupId));
		}
		
		WccPlatformUser plat=WccPlatformUser.findWccPlatformUser(Long.parseLong(platformUserId));

		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		String appId = plat.getAppId();
		String appSecret =plat.getAppSecret();
		config.setAppId(appId);
		config.setSecret(appSecret);
		WxMpServiceImpl wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(config);

		for (WccFriend friend : friends) {
				friend.setWgroup(group);
				friend.merge();
					try {
						wxMpService.userUpdateGroup(friend.getOpenId(),
								group.getGroupId());
					} catch (WxErrorException e) {
						e.printStackTrace();
					}
		}
		}
		return msgStr.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "checkgroupFriend", produces = "text/html;charset=utf-8")
	public String checkgroupFriend(
			@RequestParam(value = "activityName", required = false) String activityName,
			@RequestParam(value = "platformType", required = false) String platformType,
			@RequestParam(value = "nickName", required = false) String nickName,
			@RequestParam(value = "platformUserId", required = false) String platformUserId,
			@RequestParam(value = "groupId", required = false) String groupId,
			@RequestParam(value = "area", required = false) String area,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "timeEndId", required = false) Integer timeEndId,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			Model uiModel, HttpServletRequest request,
			HttpServletResponse response) {
		String msgStr= "success:true,msg:\"粉丝分组\"";
		String timeStr = "";
		if (null != timeEndId) {
			Long timeStamp = new Date().getTime() - 3600 * 1000 * timeEndId;
			timeStr += timeStr + timeStamp;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
			timeStr = sdf.format(new Date(Long.parseLong(timeStr)));
		}
		Map<String, String> parmMap = new HashMap<String, String>();
		parmMap.put("sex", gender);
		parmMap.put("platformUserId", platformUserId);
		parmMap.put("groupId", groupId);
		parmMap.put("area", area);
		parmMap.put("nickName", nickName);
		parmMap.put("from", from);
		parmMap.put("timeStamp", timeStr);
		parmMap.put("startTime", startTime);
		parmMap.put("endTime", endTime);
		parmMap.put("activityName", activityName);
		parmMap.put("platformType", platformType);

		List<WccFriend> friends= WccFriend.getFriends(parmMap);
		if(friends.size()==0){
			msgStr="success:false,msg:\"没有粉丝数据\"";
		}
		
		return msgStr.toString();
	}
	
	
	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("wccfriend", WccFriend.findWccFriend(id));
		uiModel.addAttribute("itemId", id);
		return "wccfriends/show";
	}

	// 粉丝列表
	@RequestMapping(produces = "text/html")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			HttpServletRequest request,
			@RequestParam(value = "displayId", required = false) Long displayId,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			@RequestParam(value = "nickName", required = false) String nickName,
			Model uiModel, HttpServletRequest httpServletRequest)
			throws UnsupportedEncodingException {
		/*
		 * if(null!=nickName && !"".equals(nickName)){ int sizeNo = size == null
		 * ? 10 : size.intValue(); String nnname = new
		 * String(nickName.getBytes("ISO-8859-1"),"UTF-8"); String nname = new
		 * String(nnname.getBytes("ISO-8859-1"),"UTF-8"); String name =
		 * URLEncoder.encode(nname,"utf-8"); String qlString =
		 * "select o from WccFriend o where o.nickName like '%"+name+"%'";
		 * List<WccFriend> friends =
		 * WccFriend.entityManager().createQuery(qlString
		 * ,WccFriend.class).getResultList(); List<WccFriend> list = new
		 * ArrayList<WccFriend>(); for(WccFriend friend : friends){
		 * friend.setNickName(URLDecoder.decode(friend.getNickName(),"utf-8"));
		 * list.add(friend); } uiModel.addAttribute("wccfriends", list); float
		 * nrOfPages = (float) WccFriend.countWccFriends() / sizeNo;
		 * uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages
		 * || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages)); return
		 * "wccfriends/list"; }
		 */
		
		 uiModel.addAttribute("platformArr", PlatformType.values());
		 StringBuffer ids = new StringBuffer();
		PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
		List<WccPlatformUser> platformUser=new ArrayList<WccPlatformUser>();

		if(pub!=null&&!pub.getAccount().equals("admin")){
			Set<WccPlatformUser> platformUserset=pub.getPlatformUsers();
			for (WccPlatformUser wccPlatformUser : platformUserset) {
				if(wccPlatformUser.getPlatformType()==PlatformType.服务号){
					platformUser.add(wccPlatformUser);
			    }
				if(wccPlatformUser.getPlatformType()==PlatformType.订阅号){
					platformUser.add(wccPlatformUser);
			    }
			}
			
		}else{
			platformUser = WccPlatformUser
					.findAllWccPlatformUsers(pub);
		}
		if (platformUser != null && platformUser.size() > 0) {
			int n = 1;
			for (WccPlatformUser wccPlatformUser : platformUser) {
				ids.append(wccPlatformUser.getId());
				if (n != platformUser.size()) {
					ids.append(",");
				}
				n++;
			}
			
		}
		uiModel.addAttribute("platformUser", platformUser);
		List<WccGroup> groups = WccGroup.findAllWccGroups();

		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			List<WccFriend> friends = WccFriend.findWccFriendEntries(
					firstResult, sizeNo, sortFieldName, sortOrder,
					ids.toString());
			if (friends != null && friends.size() > 0) {
				List<WccFriend> list = new ArrayList<WccFriend>();
				for (WccFriend friend : friends) {
					friend.setNickName(URLDecoder.decode(friend.getNickName(),
							"utf-8"));
					list.add(friend);
				}
				uiModel.addAttribute("wccfriends", list);
			}
			float nrOfPages = (float) WccFriend.countWccFriends(ids.toString())
					/ sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute(
					"wccfriends",
					WccFriend.findAllWccFriends(sortFieldName, sortOrder,
							ids.toString()));
		}
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("groups", groups);
		uiModel.addAttribute("limit", 10);
		if (displayId == null) {
			displayId = CommonUtils.getCurrentDisPlayId(request);
		}
		request.getSession().setAttribute("displayId", displayId);
		List<Emotion> emotions = new ArrayList<Emotion>();

		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			Emotion emotion = new Emotion();
			emotion.setContent(key);
			emotion.setUrl(map.get(key));
			emotions.add(emotion);
		}
		
		 Object isUcc = request.getSession().getAttribute("isUcc");
		 if(null != isUcc && !"".equals(isUcc)){
		 uiModel.addAttribute("isUcc",true); }
		
		uiModel.addAttribute("emotions", emotions);
		return "wccfriends/list";
	}

	/**
	 * 粉丝分页查询
	 * 
	 * @param nickName
	 * @param platformUserId
	 * @param groupId
	 * @param area
	 * @param isUserinfo
	 * @param gender
	 * @param from
	 * @param organization
	 * @param isunder
	 * @param uiModel
	 * @param request
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryFriends", produces = "text/html")
	public String queryFriends(
			@RequestParam(value = "activityName", required = false) String activityName,
			@RequestParam(value = "platformType", required = false) String platformType,
			@RequestParam(value = "nickName", required = false) String nickName,
			@RequestParam(value = "platformUserId", required = false) String platformUserId,
			@RequestParam(value = "groupId", required = false) String groupId,
			@RequestParam(value = "area", required = false) String area,
			@RequestParam(value = "isUserinfo", required = false) String isUserinfo,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "organization", required = false) String organization,
			@RequestParam(value = "isunder", required = false) String isunder,
			@RequestParam(value = "timeEndId", required = false) Integer timeEndId,
			Model uiModel, HttpServletRequest request,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime)
			throws ServletException, IOException {
		String timeStr = "";
		Date edate = null;
		if (null != timeEndId) {
			Long timeStamp = new Date().getTime() - 3600 * 1000 * timeEndId;
			timeStr += timeStr + timeStamp;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
			timeStr = sdf.format(new Date(Long.parseLong(timeStr)));
		}

		Map<String, String> parmMap = new HashMap<String, String>();
		WccFriend friend = new WccFriend();
		friend.setNickName(nickName);
		parmMap.put("sex", gender);
		parmMap.put("platformUserId", platformUserId);
		parmMap.put("groupId", groupId);
		parmMap.put("isUserinfo", isUserinfo);
		parmMap.put("area", area);
		parmMap.put("from", from);
		parmMap.put("organization", organization);
		parmMap.put("isunder", isunder);
		parmMap.put("timeStamp", timeStr);
		parmMap.put("startTime", startTime);
		parmMap.put("endTime", endTime);
		parmMap.put("activityName", activityName);
		parmMap.put("platformType", platformType);

		PubOperator pubOper = CommonUtils.getCurrentPubOperator(request);
		Set<WccPlatformUser> platforms=new HashSet<WccPlatformUser>();
		if(pubOper!=null&&!pubOper.getAccount().equals("admin")){
		    platforms = pubOper.getPlatformUsers();
		}else{
			List<WccPlatformUser> platformlist = WccPlatformUser
					.findAllWccPlatformUsers(pubOper);
			for (WccPlatformUser wccPlatformUser : platformlist) {
				platforms.add(wccPlatformUser);
			}
		}
		Set<WccPlatformUser> platSets = new HashSet<WccPlatformUser>();
		platSets.addAll(platforms);

		QueryModel<WccFriend> qm = new QueryModel<WccFriend>(friend, start,
				limit);
		qm.getInputMap().putAll(parmMap);

//		PageModel<WccFriend> pm = WccFriend.getQueryFriends(qm, platSets);
		PageModel<WccFriend> pm = WccFriend.getQueryFriends(qm,platSets);
		pm.setPageSize(limit);
		pm.setStartIndex(start);
		uiModel.addAttribute("wccfriends", pm.getDataList());
		uiModel.addAttribute("maxPages", pm.getTotalPage());
		uiModel.addAttribute("page", pm.getPageNo());
		uiModel.addAttribute("limit", pm.getPageSize());

		return "wccfriends/result";
	}

	/**
	 * 图片分页查询
	 * 
	 * @param uiModel
	 * @param request
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryPic", produces = "text/html")
	public String queryPic(Model uiModel, HttpServletRequest request,
			@RequestParam(value = "platFormId", required = false) Long platFormId,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit){
		log.info(platFormId+"====platFormId===");
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("type", 1);
		parmMap.put("platFormId", platFormId);
		WccMaterials material = new WccMaterials();
		QueryModel<WccMaterials> qm = new QueryModel<WccMaterials>(material,start, limit);
		qm.getInputMap().putAll(parmMap);
		Long companyId = CommonUtils.getCurrentCompanyId(request);
		PageModel<WccMaterials> pm = WccMaterials.getQueryPic(qm, companyId);
		pm.setPageSize(limit);
		pm.setStartIndex(start);

		uiModel.addAttribute("wccPics", pm.getDataList());
		uiModel.addAttribute("maxPages", pm.getTotalPage());
		uiModel.addAttribute("page", pm.getPageNo());
		uiModel.addAttribute("limit", pm.getPageSize());

		return "wccfriends/picResult";
	}

	/**
	 * 图文分页查询
	 * 
	 * @param uiModel
	 * @param request
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryPicText", produces = "text/html")
	public String queryPicText(Model uiModel, HttpServletRequest request,
			@RequestParam(value = "platFormId", required = false) Long platFormId,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit)
			throws ServletException, IOException {

		Long companyId = CommonUtils.getCurrentCompanyId(request);

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("type", 4);
		parmMap.put("platFormId", platFormId);
		WccMaterials material = new WccMaterials();
		QueryModel<WccMaterials> qm = new QueryModel<WccMaterials>(material,
				start, limit);
		qm.getInputMap().putAll(parmMap);

		PageModel<WccMaterials> pm = WccMaterials.getQueryPic(qm, companyId);
		pm.setPageSize(limit);
		pm.setStartIndex(start);

		uiModel.addAttribute("wccPicTexts", pm.getDataList());
		uiModel.addAttribute("maxPages", pm.getTotalPage());
		uiModel.addAttribute("page", pm.getPageNo());
		uiModel.addAttribute("limit", pm.getPageSize());

		return "wccfriends/picTextResult";
	}

	/**
	 * 视频分页查询
	 * 
	 * @param uiModel
	 * @param request
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryVideo", produces = "text/html")
	public String queryVideo(Model uiModel, HttpServletRequest request,
			@RequestParam(value = "platFormId", required = false) Long platFormId,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit){

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("type", 3);
		parmMap.put("platFormId", platFormId);
		WccMaterials material = new WccMaterials();
		QueryModel<WccMaterials> qm = new QueryModel<WccMaterials>(material,
				start, limit);
		qm.getInputMap().putAll(parmMap);
		Long companyId = CommonUtils.getCurrentCompanyId(request);
		PageModel<WccMaterials> pm = WccMaterials.getQueryPic(qm, companyId);
		pm.setPageSize(limit);
		pm.setStartIndex(start);

		uiModel.addAttribute("wccVideos", pm.getDataList());
		uiModel.addAttribute("maxPages", pm.getTotalPage());
		uiModel.addAttribute("page", pm.getPageNo());
		uiModel.addAttribute("limit", pm.getPageSize());

		return "wccfriends/videoResult";
	}

	@RequestMapping(value = "/queryVoice", produces = "text/html")
	public String queryVoice(Model uiModel, HttpServletRequest request,
			@RequestParam(value = "platFormId", required = false) Long platFormId,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit){

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("type", 2);
		parmMap.put("platFormId", platFormId);
		WccMaterials material = new WccMaterials();
		QueryModel<WccMaterials> qm = new QueryModel<WccMaterials>(material,start, limit);
		qm.getInputMap().putAll(parmMap);
		Long companyId = CommonUtils.getCurrentCompanyId(request);
		PageModel<WccMaterials> pm = WccMaterials.getQueryPic(qm, companyId);
		pm.setPageSize(limit);
		pm.setStartIndex(start);

		uiModel.addAttribute("wccVoices", pm.getDataList());
		uiModel.addAttribute("maxPages", pm.getTotalPage());
		uiModel.addAttribute("page", pm.getPageNo());
		uiModel.addAttribute("limit", pm.getPageSize());

		return "wccfriends/voiceResult";
	}

	@RequestMapping(value = "/queryTemplate", produces = "text/html")
	public String queryTemplate(Model uiModel, HttpServletRequest request,
			@RequestParam(value = "platFormId", required = false) Long platFormId,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit)
			throws ServletException, IOException {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("platFormId", platFormId);
		WccTemplate material = new WccTemplate();
		QueryModel<WccTemplate> qm = new QueryModel<WccTemplate>(material,start, limit);
		qm.getInputMap().putAll(parmMap);

		PageModel<WccTemplate> pm = WccTemplate.getQueryTemplate(qm);
		pm.setPageSize(limit);
		pm.setStartIndex(start);

		uiModel.addAttribute("wccTemplates", pm.getDataList());
		uiModel.addAttribute("maxPages", pm.getTotalPage());
		uiModel.addAttribute("page", pm.getPageNo());
		uiModel.addAttribute("limit", pm.getPageSize());

		return "wccfriends/templateResult";
	}

	@RequestMapping(value = "/queryRecord", produces = "text/html")
	public String queryRecord(Model uiModel, HttpServletRequest request,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "friendId", required = false) Long friendId){
		String domain = Global.Url+"/ump/wccmaterialses/showdetail/";
		WccFriend friend = WccFriend.findWccFriend(friendId);
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("openId", friend.getOpenId());
		WccRecordMsg record = new WccRecordMsg();
		QueryModel<WccRecordMsg> qm = new QueryModel<WccRecordMsg>(record,
				start, limit);
		qm.getInputMap().putAll(parmMap);

		PageModel<WccRecordMsg> pm = WccRecordMsg.getQueryRecord(qm);
		pm.setPageSize(limit);
		pm.setStartIndex(start);

		List<WccRecordMsg> list = new ArrayList<WccRecordMsg>();
		for (WccRecordMsg msg : pm.getDataList()) {
			String niceName;
			try {
				niceName = java.net.URLDecoder.decode(msg.getNickName(),"utf-8");
				msg.setNickName(niceName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (msg.getMsgFrom() == 4 || msg.getMsgFrom() == 6) {
				Long id = Long.parseLong(msg.getToUserRecord());
				WccMaterials material = WccMaterials.findWccMaterials(id);
				msg.setMaterial(material);
			}
			if (msg.getMsgFrom() == 0 || msg.getMsgFrom() == 7) {
				String content = msg.getToUserRecord();
				String wxContent = msg.getRecordContent();
				String img = "<img src=\"url\" width=\"24\" height=\"24\" />";
				if (content != null && !"".equals(content.trim())) {
					Set<String> keySet = map.keySet();
					for (String key : keySet) {
						if (content.contains(key)) {
							content = content.replace(key,
									img.replace("url", map.get(key)));
							msg.setToUserRecord(content);
						}
					}
					msg.setRecordContent(WxEmotion.replaceEmotion(wxContent));// 粉丝发送表情
				}
			}
			if (msg.getMsgFrom() == 1) {
				String img = "<img src=\"url\" width=\"100\" height=\"100\" />";
				if(msg.getRecordContent() != null){
					String image = msg.getRecordContent().split("--")[0];
					msg.setRecordContent(img.replace("url", image));
				}
			}
			list.add(msg);
		}
		uiModel.addAttribute("wccRecordMsgs", list);
		uiModel.addAttribute("maxPages", pm.getTotalPage());
		uiModel.addAttribute("domain", domain);
		uiModel.addAttribute("page", pm.getPageNo());
		uiModel.addAttribute("limit", pm.getPageSize());
		return "wccfriends/recordResult";
	}

	/**
	 * 主动发送消息
	 * 
	 * @param uiModel
	 * @param request
	 * @param friendId
	 * @param sendType
	 * @param textVal
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "sendMsg", produces = "text/html;charset=utf-8")
	public String sendMsg(
			Model uiModel,
			HttpServletRequest request,
			@RequestParam(value = "friendId", required = false) Long friendId,
			@RequestParam(value = "sendType", required = false) Integer sendType,
			@RequestParam(value = "textVal", required = false) String textVal){
		StringBuffer str = new StringBuffer();
		WccFriend friend = WccFriend.findWccFriend(friendId);// 粉丝
		if(friend != null){
			String openId = friend.getOpenId();
			WccPlatformUser wccPlatformUser = friend.getPlatformUser();// 公众平台
			WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
			String appId = wccPlatformUser.getAppId();
			String appSecret = wccPlatformUser.getAppSecret();
			config.setAppId(appId);
			config.setSecret(appSecret);
			WxMpServiceImpl wxMpService = new WxMpServiceImpl();
			wxMpService.setWxMpConfigStorage(config);
			String domain = Global.Url;
			log.info(sendType+"===sendType====");
			switch (sendType) {
			case 0:// 文本
				WxMpCustomMessage textMsg = WxMpCustomMessage.TEXT().toUser(openId).content(textVal).build();
				try {
					wxMpService.customMessageSend(textMsg);
				} catch (WxErrorException e1) {
					e1.printStackTrace();
					str.append("false");
					return str.toString();
				}
				WccRecordMsg record = new WccRecordMsg();
				record.setFriendGroup(friend.getWgroup().getId() + "");
				record.setInsertTime(new Date());
				// 0:文本,1:图片,2:语音,3:视频,4:图文,5:音乐,6:多图文
				record.setMsgFrom(0);
				record.setNickName(friend.getNickName());
				record.setOpenId(friend.getOpenId());
				record.setPlatFormAccount(wccPlatformUser.getAccount());
				record.setPlatFormId(wccPlatformUser.getId() + "");
				record.setProvince(friend.getProvince());
				record.setToUserInsertTime(new Date());
				record.setToUserRecord(textVal);
				record.persist();
				break;
			case 1:// 图片
				WccMaterials materials = WccMaterials.findWccMaterials(Long.parseLong(textVal));
				String ctxPath = request.getSession().getServletContext().getRealPath("");
				File file = new File(ctxPath+ materials.getThumbnailUrl().substring(4));
				try {
					WxMediaUploadResult res = wxMpService.mediaUpload(
							WxConsts.MEDIA_IMAGE, file);
					String mediaId = res.getMediaId();
					WxMpCustomMessage imgMsg = WxMpCustomMessage.IMAGE()
							.toUser(openId).mediaId(mediaId).build();
					wxMpService.customMessageSend(imgMsg);
				} catch (WxErrorException e) {
					e.printStackTrace();
					str.append("false");
					return str.toString();
				}
				WccRecordMsg recordImg = new WccRecordMsg();
				recordImg.setFriendGroup(friend.getWgroup().getId() + "");
				recordImg.setInsertTime(new Date());
				// 0:文本,1:图片,2:语音,3:视频,4:图文,5:音乐,6:多图文
				recordImg.setMsgFrom(1);
				recordImg.setNickName(friend.getNickName());
				recordImg.setOpenId(friend.getOpenId());
				recordImg.setPlatFormAccount(wccPlatformUser.getAccount());
				recordImg.setPlatFormId(wccPlatformUser.getId() + "");
				recordImg.setProvince(friend.getProvince());
				recordImg.setToUserInsertTime(new Date());
				recordImg.setToUserRecord(materials.getThumbnailUrl());
				recordImg.persist();
				break;
			case 2:// 语音
				WccMaterials voice = WccMaterials.findWccMaterials(Long
						.parseLong(textVal));
				String voctxPath = request.getSession().getServletContext()
						.getRealPath("");
				File vofile = new File(voctxPath
						+ voice.getThumbnailUrl().substring(4));
				try {
					WxMediaUploadResult res = wxMpService.mediaUpload(
							WxConsts.MEDIA_VOICE, vofile);
					String mediaId = res.getMediaId();
					WxMpCustomMessage voiceMsg = WxMpCustomMessage.VOICE()
							.toUser(openId).mediaId(mediaId).build();
					wxMpService.customMessageSend(voiceMsg);
				} catch (WxErrorException e) {
					e.printStackTrace();
					str.append("false");
					return str.toString();
				}
				WccRecordMsg recordVoice = new WccRecordMsg();
				recordVoice.setFriendGroup(friend.getWgroup().getId() + "");
				recordVoice.setInsertTime(new Date());
				// 0:文本,1:图片,2:语音,3:视频,4:图文,5:音乐,6:多图文
				recordVoice.setMsgFrom(2);
				recordVoice.setNickName(friend.getNickName());
				recordVoice.setOpenId(friend.getOpenId());
				recordVoice.setPlatFormAccount(wccPlatformUser.getAccount());
				recordVoice.setPlatFormId(wccPlatformUser.getId() + "");
				recordVoice.setProvince(friend.getProvince());
				recordVoice.setToUserInsertTime(new Date());
				recordVoice.setToUserRecord(voice.getThumbnailUrl());
				recordVoice.persist();
				break;
			case 3:// 视频
				WccMaterials video = WccMaterials.findWccMaterials(Long.parseLong(textVal));
				String vctxPath = request.getSession().getServletContext()
						.getRealPath("");
				File vfile = new File(vctxPath
						+ video.getThumbnailUrl().substring(4));
				try {
					WxMediaUploadResult res = wxMpService.mediaUpload(WxConsts.MEDIA_VIDEO, vfile);
					String mediaId = res.getMediaId();
					WxMpCustomMessage videoMsg = WxMpCustomMessage.VIDEO()
							.toUser(openId).title(video.getTitle())
							.mediaId(mediaId).description(video.getDescription()).build();
					wxMpService.customMessageSend(videoMsg);
				} catch (WxErrorException e) {
					e.printStackTrace();
					str.append("false");
					return str.toString();
				}
				WccRecordMsg recordVideo = new WccRecordMsg();
				recordVideo.setFriendGroup(friend.getWgroup().getId() + "");
				recordVideo.setInsertTime(new Date());
				// 0:文本,1:图片,2:语音,3:视频,4:图文,5:音乐,6:多图文
				recordVideo.setMsgFrom(3);
				recordVideo.setNickName(friend.getNickName());
				recordVideo.setOpenId(friend.getOpenId());
				recordVideo.setPlatFormAccount(wccPlatformUser.getAccount());
				recordVideo.setPlatFormId(wccPlatformUser.getId() + "");
				recordVideo.setProvince(friend.getProvince());
				recordVideo.setToUserInsertTime(new Date());
				recordVideo.setToUserRecord(video.getThumbnailUrl());
				recordVideo.persist();
				break;
			case 4:// 图文
				int type = 4;
				NewsBuilder customMessage = WxMpCustomMessage.NEWS();
				WccMaterials picText = WccMaterials.findWccMaterials(Long.parseLong(textVal));// 根据ID查询图文
				WxMpCustomMessage.WxArticle article1 = new WxMpCustomMessage.WxArticle();
				article1.setUrl(domain +"/ump/wccmaterialses/showdetail/"+picText.getId());
				article1.setDescription(picText.getDescription());
				article1.setTitle(picText.getTitle());
				article1.setPicUrl(domain + picText.getThumbnailUrl());
				customMessage.addArticle(article1);// 第一个图文
				if (!picText.getChildren().isEmpty()) {// 如果有子图文（多图文）
					type = 6;
					List<WccMaterials> children = picText.getChildren();
					Iterator<WccMaterials> iter = children.iterator();
					while (iter.hasNext()) {
						WccMaterials material = iter.next();
						WxMpCustomMessage.WxArticle article = new WxMpCustomMessage.WxArticle();
						article.setUrl(domain +"/ump/wccmaterialses/showdetail/"+material.getId());
						log.info(article.getUrl()+"======url=====");
						article.setDescription(material.getDescription());
						article.setTitle(material.getTitle());
						article.setPicUrl(domain + material.getThumbnailUrl());
						customMessage.addArticle(article);
					}
				}
				customMessage.toUser(friend.getOpenId());
				WxMpCustomMessage picTextMsg = customMessage.build();
				try {
					wxMpService.customMessageSend(picTextMsg);
				} catch (WxErrorException e) {
					e.printStackTrace();
					str.append("false");
					return str.toString();
				}
				WccRecordMsg recordImgText = new WccRecordMsg();
				recordImgText.setFriendGroup(friend.getWgroup().getId() + "");
				recordImgText.setInsertTime(new Date());
				// 0:文本,1:图片,2:语音,3:视频,4:图文,5:音乐,6:多图文
				recordImgText.setMsgFrom(type);
				recordImgText.setNickName(friend.getNickName());
				recordImgText.setOpenId(friend.getOpenId());
				recordImgText.setPlatFormAccount(wccPlatformUser.getAccount());
				recordImgText.setPlatFormId(wccPlatformUser.getId() + "");
				recordImgText.setProvince(friend.getProvince());
				recordImgText.setToUserInsertTime(new Date());
				recordImgText.setToUserRecord(picText.getId() + "");
				recordImgText.persist();
				break;
			case 5:// 音乐
	
				break;
			case 7:// 表情
				WxMpCustomMessage etextMsg = WxMpCustomMessage.TEXT()
						.toUser(openId).content(textVal).build();
				try {
					wxMpService.customMessageSend(etextMsg);
				} catch (WxErrorException e1) {
					e1.printStackTrace();
					str.append("false");
					return str.toString();
				}
				WccRecordMsg erecord = new WccRecordMsg();
				erecord.setFriendGroup(friend.getWgroup().getId() + "");
				erecord.setInsertTime(new Date());
				// 0:文本,1:图片,2:语音,3:视频,4:图文,5:音乐,6:多图文
				erecord.setMsgFrom(7);
				erecord.setNickName(friend.getNickName());
				erecord.setOpenId(friend.getOpenId());
				erecord.setPlatFormAccount(wccPlatformUser.getAccount());
				erecord.setPlatFormId(wccPlatformUser.getId() + "");
				erecord.setProvince(friend.getProvince());
				erecord.setToUserInsertTime(new Date());
				erecord.setToUserRecord(textVal);
				erecord.persist();
				break;
			default:
				break;
			}
		}
		str.append("true");
		return str.toString();
	}
	


	// 查询
	@RequestMapping(value = "query", produces = "text/html", method = RequestMethod.POST)
	public String queryList(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			@RequestParam(value = "nickName", required = false) String nickName,
			@RequestParam(value = "platformUserId", required = false) String platformUserId,
			@RequestParam(value = "groupId", required = false) String groupId,
			@RequestParam(value = "area", required = false) String area,
			@RequestParam(value = "isUserinfo", required = false) String isUserinfo,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "isunder", required = false) String isunder,
			HttpServletRequest request, Model uiModel)
			throws UnsupportedEncodingException {
		List<WccPlatformUser> platformUser = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
		uiModel.addAttribute("platformUser", platformUser);

		int sizeNo = size == null ? 10 : size.intValue();
		List<WccFriend> list = new ArrayList<WccFriend>();
		List<WccFriend> friends = WccFriend.findFriendByQuery(nickName,
				platformUserId, area, gender, from, isUserinfo, groupId);
		for (WccFriend friend : friends) {
			friend.setNickName(URLDecoder.decode(friend.getNickName(), "utf-8"));
			list.add(friend);
		}
		uiModel.addAttribute("wccfriends", list);
		float nrOfPages = (float) WccFriend.countWccFriendsByFiled(nickName,
				platformUserId, area, gender, from, isUserinfo, groupId)
				/ sizeNo;
		uiModel.addAttribute(
				"maxPages",
				(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
						: nrOfPages));
		return "wccfriends/list";
	}

	/*
	 * //分页
	 * 
	 * @RequestMapping(value="query",produces = "text/html") public String
	 * queryListPage(@RequestParam(value = "page", required = false) Integer
	 * page, @RequestParam(value = "size", required = false) Integer size,
	 * @RequestParam(value = "sortFieldName", required = false) String
	 * sortFieldName, @RequestParam(value = "sortOrder", required = false)
	 * String sortOrder,
	 * 
	 * @RequestParam(value = "nickName", required = false) String nickName,
	 * Model uiModel) throws UnsupportedEncodingException { List<WccFriend> list
	 * = new ArrayList<WccFriend>(); for(WccFriend friend : friends){
	 * friend.setNickName(URLDecoder.decode(friend.getNickName(),"utf-8"));
	 * list.add(friend); } uiModel.addAttribute("wccfriends", list); float
	 * nrOfPages = (float) WccFriend.countWccFriends() / sizeNo;
	 * uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages ||
	 * nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages)); return
	 * "wccfriends/list"; }
	 */

	// 分组下拉
	@ResponseBody
	@RequestMapping(value = "getGroupByPlatformUser")
	public void getGroupByPlatformUser(@RequestParam(value = "id") Long id,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer();
			sb.append("<select id='groups' name='groupId'>");
			sb.append("<option value=''>全部</option>");
			List<WccGroup> groups = WccGroup.findGroupByPlatformUser(id + "");
			for (WccGroup group : groups) {
				sb.append("<option value='" + group.getId() + "'>"
						+ group.getName() + "</option>");
			}
			sb.append("</select>");
			out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

	}
	
	/**
	 * 根据公众平台类型查询公众平台
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getPlatformUser")
	public void getPlatformUser(@RequestParam(value = "type") String type,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/Xml;charset=utf-8");
		PubOperator pubOper = CommonUtils.getCurrentPubOperator(request);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer();
			sb.append("<select id='platformUser_' name='platformUserId' onchange='javascript:querySelect(this.value);'>");
			sb.append("<option value=''>全部</option>");
			Set<WccPlatformUser> platforms=new HashSet<WccPlatformUser>();
			if(pubOper!=null&&!pubOper.getAccount().equals("admin")){
				 platforms=pubOper.getPlatformUsers();
			}else{
				List<WccPlatformUser> platformlist = WccPlatformUser
						.findAllWccPlatformUsers(pubOper);
				for (WccPlatformUser wccPlatformUser : platformlist) {
					platforms.add(wccPlatformUser);
				}
			}
			for (WccPlatformUser platuser : platforms) {
				String plattype=platuser.getPlatformType().toString();
				if(type==platuser.getPlatformType().toString()){
				sb.append("<option value='" + platuser.getId() + "'>"
						+ platuser.getAccount() + "</option>");
				}if(type.equals(plattype)){
					sb.append("<option value='" + platuser.getId() + "'>"
							+ platuser.getAccount() + "</option>");
				}
			}
			sb.append("</select>");
			out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

	}
	
	// 分组下拉
		@ResponseBody
		@RequestMapping(value = "getGroupByPlatformUser2")
		public void getGroupByPlatformUser2(@RequestParam(value = "id") Long id,
				HttpServletRequest request, HttpServletResponse response) {
			response.setContentType("text/Xml;charset=utf-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				StringBuffer sb = new StringBuffer();
				sb.append("<select id='groupsf' name='groupId'>");
				sb.append("<option value=''>全部</option>");
				List<WccGroup> groups = WccGroup.findGroupByPlatformUser(id + "");
				for (WccGroup group : groups) {
					sb.append("<option value='" + group.getId() + "'>"
							+ group.getName() + "</option>");
				}
				sb.append("</select>");
				out.println(sb);
			} catch (IOException ex1) {
				ex1.printStackTrace();
			} finally {
				out.close();
			}

		}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid WccFriend wccFriend,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, wccFriend);
			return "wccfriends/update";
		}
		uiModel.asMap().clear();
		wccFriend.merge();
		return "redirect:/wccfriends/"
				+ encodeUrlPathSegment(wccFriend.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, WccFriend.findWccFriend(id));
		return "wccfriends/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		WccFriend wccFriend = WccFriend.findWccFriend(id);
		wccFriend.remove();
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/wccfriends";
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"wccFriend_inserttime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute(
				"wccFriend_subscribetime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute(
				"wccFriend_messageendtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute(
				"wccFriend_sendemailtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, WccFriend wccFriend) {
		uiModel.addAttribute("wccFriend", wccFriend);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("wccfriendformtypes",
				Arrays.asList(WccFriendFormType.values()));
		uiModel.addAttribute("wccfriendsexes",
				Arrays.asList(WccFriendSex.values()));
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
	 * 粉丝多客服记录查询
	 * 
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "dkf", produces = "text/html")
	public String showDkf(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "friendId", required = false) String friendId,
			@RequestParam(value = "insertTime", required = false) String insertTime,
			@RequestParam(value = "pageIndex", required = false) String pageIndex,
			@RequestParam(value = "pageSize", required = false) String pageSize)
			throws Exception {
		 WccFriend f =null;
			try {
			 f = WccFriend.findWccFriend(Long.parseLong(friendId));
			} catch (Exception e) {
				return "暂无粉丝信息！";
				// TODO: handle exception
			}
		WxMpService wxMpService = new WxMpServiceImpl();
		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		config.setAppId(f.getPlatformUser().getAppId());
		config.setSecret(f.getPlatformUser().getAppSecret());
		wxMpService.setWxMpConfigStorage(config);

		String getPostData = null;
		DkfEntity data = new DkfEntity();
		String start = insertTime + " 00:00:00";
		String end = insertTime + " 23:59:59";

		data.setStarttime(String.valueOf(DateUtil.getUnixTime(start)));
		data.setEndtime(String.valueOf(DateUtil.getUnixTime(end)));
		data.setOpenid(f.getOpenId());
		data.setPageindex(pageIndex);
		data.setPagesize(pageSize);
		JSONObject json = JSONObject.fromObject(data);

		if (f.getPlatformUser().getPlatformId() != null
				&& acctoken.get(f.getPlatformUser().getPlatformId()) == null) {
			// 获取accessToken
			String accessTok = wxMpService.getAccessToken();
			
			acctoken.put(f.getPlatformUser().getPlatformId(),
					accessTok);
		}
		// 测试
		try {
			getPostData = HttpClientUtil
					.doJsonPost(
							"https://api.weixin.qq.com/cgi-bin/customservice/getrecord?access_token="
									+ acctoken.get(f.getPlatformUser()
											.getPlatformId()), json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (getPostData.indexOf(" access_token expired") > 0
				|| getPostData.indexOf("invalid credential") > 0
				|| getPostData.indexOf("40001") > 0) {// 如果过期了
			String accessTok = wxMpService.getAccessToken();
			
			acctoken.put(f.getPlatformUser().getPlatformId(),
					accessTok);
			getPostData = HttpClientUtil
					.doJsonPost(
							"https://api.weixin.qq.com/cgi-bin/customservice/getrecord?access_token="
									+ acctoken.get(f.getPlatformUser()
											.getPlatformId()), json.toString());// 再次请求
		}
		// 将json转换为
		RecordData ls = PagerPropertyUtils.jsonToList(JSONObject
				.fromObject(getPostData));
		JSONObject jo = null;
		List<Recordlist> voList = new ArrayList<Recordlist>();
		for (int i = 0; i < ls.getRecordlist().size(); i++) {
			jo = JSONObject.fromObject(ls.getRecordlist().get(i));
			Recordlist r = (Recordlist) JSONObject.toBean(jo, Recordlist.class);
			voList.add(r);
		}

		// 写到页面上
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer();

			for (Recordlist r : voList) {
				if (r.getOpercode().equals("2001")) {
					sb.append("<br>");
					sb.append("<span style=\"color: blue\">");
					sb.append(URLDecoder.decode(f.getNickName(), "utf-8") + " "
							+ DateUtil.getUnixTimeToStr(r.getTime() + "000"));
					sb.append("</span>");
					sb.append("</br>");
					sb.append(" " + r.getText());
				}
				if (r.getOpercode().equals("1001")) {
					sb.append("<br>");
					sb.append("<span style=\"color: blue\">");
					sb.append(r.getWorker() + " "
							+ DateUtil.getUnixTimeToStr(r.getTime() + "000"));
					sb.append("</span>");
					sb.append("</br>");
					sb.append("客服转接中......");
				}
				if (r.getOpercode().equals("2002")) {// 客服发送消息
					sb.append("<br>");
					sb.append("<span style=\"color: blue\">");
					sb.append(r.getWorker() + " "
							+ DateUtil.getUnixTimeToStr(r.getTime() + "000"));
					sb.append("</span>");
					sb.append("</br>");
					sb.append(r.getText());
				}
				if (r.getOpercode().equals("2003")) {// 客服收到消息
					sb.append("<br>");
					sb.append("<span style=\"color: blue\">");
					sb.append(URLDecoder.decode(f.getNickName(), "utf-8") + " "
							+ DateUtil.getUnixTimeToStr(r.getTime() + "000"));
					sb.append("</span>");
					sb.append("</br>");
					sb.append(r.getText());
				}
				if (r.getOpercode().equals("1004")) {// 客服发送消息
					sb.append("<br>");
					sb.append("<span style=\"color: blue\">");
					sb.append(r.getWorker() + " "
							+ DateUtil.getUnixTimeToStr(r.getTime() + "000"));
					sb.append("</span>");
					sb.append("</br>");
					sb.append("客服关闭会话......");
				}
			}

			out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

		return null;
	}
	
	@RequestMapping(value = "/massMessageList", produces = "text/html")
	public String massMessageList(Model uiModel, HttpServletRequest request,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "friendId", required = false) Long friendId)
			throws ServletException, IOException {

		Long companyId = CommonUtils.getCurrentCompanyId(request);

		Map<String, Object> parmMap = new HashMap<String, Object>();
		WccMassMessage massMessage = new WccMassMessage();
		QueryModel<WccMassMessage> qm = new QueryModel<WccMassMessage>(massMessage,
				start, limit);
		qm.getInputMap().putAll(parmMap);
		PageModel<WccMassMessage> pm = WccMassMessage.getMassMessageByFriendId(qm, companyId,friendId);
		pm.setPageSize(limit);
		pm.setStartIndex(start);

		uiModel.addAttribute("wccMassMessage", pm.getDataList());
		uiModel.addAttribute("maxPages", pm.getTotalPage());
		uiModel.addAttribute("page", pm.getPageNo());
		uiModel.addAttribute("limit", pm.getPageSize());

		return "wccfriends/massResult";
	}

	static {
		map.put("/微笑", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/0.gif");
		map.put("/撇嘴", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/1.gif");
		map.put("/色", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/2.gif");
		map.put("/发呆", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/3.gif");
		map.put("/得意", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/4.gif");
		map.put("/流泪", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/5.gif");
		map.put("/害羞", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/6.gif");
		map.put("/闭嘴", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/7.gif");
		map.put("/睡", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/8.gif");
		map.put("/大哭", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/9.gif");
		map.put("/尴尬",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/10.gif");
		map.put("/发怒",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/11.gif");
		map.put("/调皮",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/12.gif");
		map.put("/呲牙",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/13.gif");
		map.put("/惊讶",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/14.gif");
		map.put("/难过",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/15.gif");
		map.put("/酷", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/16.gif");
		map.put("/冷汗",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/17.gif");
		map.put("/抓狂",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/18.gif");
		map.put("/吐", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/19.gif");
		map.put("/偷笑",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/20.gif");
		map.put("/可爱",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/21.gif");
		map.put("/白眼",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/22.gif");
		map.put("/傲慢",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/23.gif");
		map.put("/饥饿",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/24.gif");
		map.put("/困", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/25.gif");
		map.put("/惊恐",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/26.gif");
		map.put("/流汗",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/27.gif");
		map.put("/憨笑",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/28.gif");
		map.put("/大兵",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/29.gif");
		map.put("/奋斗",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/30.gif");
		map.put("/咒骂",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/31.gif");
		map.put("/疑问",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/32.gif");
		map.put("/嘘", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/33.gif");
		map.put("/晕", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/34.gif");
		map.put("/折磨",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/35.gif");
		map.put("/衰", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/36.gif");
		map.put("/骷髅",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/37.gif");
		map.put("/敲打",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/38.gif");
		map.put("/再见",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/39.gif");
		map.put("/擦汗",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/40.gif");
		map.put("/抠鼻",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/41.gif");
		map.put("/鼓掌",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/42.gif");
		map.put("/糗大了",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/43.gif");
		map.put("/坏笑",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/44.gif");
		map.put("/左哼哼",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/45.gif");
		map.put("/右哼哼",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/46.gif");
		map.put("/哈欠",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/47.gif");
		map.put("/鄙视",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/48.gif");
		map.put("/委屈",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/49.gif");
		map.put("/快哭了",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/50.gif");
		map.put("/阴险",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/51.gif");
		map.put("/亲亲",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/52.gif");
		map.put("/吓", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/53.gif");
		map.put("/可怜",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/54.gif");
		map.put("/菜刀",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/55.gif");
		map.put("/西瓜",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/56.gif");
		map.put("/啤酒",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/57.gif");
		map.put("/篮球",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/58.gif");
		map.put("/乒乓",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/59.gif");
		map.put("/咖啡",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/60.gif");
		map.put("/饭", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/61.gif");
		map.put("/猪头",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/62.gif");
		map.put("/玫瑰",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/63.gif");
		map.put("/凋谢",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/64.gif");
		map.put("/示爱",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/65.gif");
		map.put("/爱心",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/66.gif");
		map.put("/心碎",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/67.gif");
		map.put("/蛋糕",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/68.gif");
		map.put("/闪电",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/69.gif");
		map.put("/炸弹",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/70.gif");
		map.put("/刀", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/71.gif");
		map.put("/足球",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/72.gif");
		map.put("/瓢虫",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/73.gif");
		map.put("/便便",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/74.gif");
		map.put("/月亮",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/75.gif");
		map.put("/太阳",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/76.gif");
		map.put("/礼物",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/77.gif");
		map.put("/拥抱",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/78.gif");
		map.put("/强", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/79.gif");
		map.put("/弱", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/80.gif");
		map.put("/握手",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/81.gif");
		map.put("/胜利",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/82.gif");
		map.put("/抱拳",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/83.gif");
		map.put("/勾引",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/84.gif");
		map.put("/拳头",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/85.gif");
		map.put("/差劲",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/86.gif");
		map.put("/爱你",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/87.gif");
		map.put("/NO",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/88.gif");
		map.put("/OK",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/89.gif");
		map.put("/爱情",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/90.gif");
		map.put("/飞吻",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/91.gif");
		map.put("/跳跳",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/92.gif");
		map.put("/发抖",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/93.gif");
		map.put("/怄火",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/94.gif");
		map.put("/转圈",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/95.gif");
		map.put("/磕头",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/96.gif");
		map.put("/回头",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/97.gif");
		map.put("/跳绳",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/98.gif");
		map.put("/挥手",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/99.gif");
		map.put("/激动",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/100.gif");
		map.put("/街舞",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/101.gif");
		map.put("/献吻",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/102.gif");
		map.put("/左太极",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/103.gif");
		map.put("/右太极",
				"https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/104.gif");
	}

}

  package com.nineclient.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nineclient.constant.WccFriendFormType;
import com.nineclient.constant.WccFriendSex;
import com.nineclient.model.WccComment;
import com.nineclient.model.WccContent;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccGroup;
import com.nineclient.model.WccMenu;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccPraise;
import com.nineclient.model.WccStore;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.Global;
import com.nineclient.utils.RedisCommonHelper;
import com.nineclient.weixin.MsgTypeProcessor;
import com.nineclient.weixin.imp.MsgTypeProcessorImp;
import com.nineclient.weixin.imp.WeixinBasicParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 微信交互类
 *
 */
@RequestMapping("/wxController")
@Controller
public class WccWxController extends HttpServlet {
	private final Logger log = LoggerFactory.getLogger(WccWxController.class);
	public static long msgId = 0l;
	protected WxMpConfigStorage wxMpConfigStorage;
	protected WxMpService wxMpService;
	protected WxMpService wxMpServices;
	protected WxMpMessageRouter wxMpMessageRouter;
	
	@RequestMapping(value = "wx", produces = "text/html")
	protected void service(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		String actionMethod = request.getMethod().toLowerCase();
		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		//String signature = request.getParameter("signature");
		//String nonce = request.getParameter("nonce");
		//String timestamp = request.getParameter("timestamp");
		// wxMpService.setWxMpConfigStorage(config);
		PrintWriter out=null;
		try {
			out=response.getWriter();
			out.print("");
			wxMpService = new WxMpServiceImpl();
			if (actionMethod.equals("get")) {
				wxMpService.setWxMpConfigStorage(config);
				/*
				 * if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
				 * // 消息签名不正确，说明不是公众平台发过来的消息 response.getWriter().println("非法请求");
				 * return; }
				 */

				String echostr = request.getParameter("echostr");
				if (StringUtils.isNotBlank(echostr)) {
					// 说明是一个仅仅用来验证的请求，回显echostr
					out.println(echostr);
					return;
				}
			}
			if (actionMethod.equals("post")) {
				String encryptType = StringUtils.isBlank(request
						.getParameter("encrypt_type")) ? "raw" : request
						.getParameter("encrypt_type");

					WxMpXmlMessage inMessage = null;
					if("raw".equals(encryptType)){
						inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
					}
					WeixinBasicParam.timeMap.put(inMessage.getFromUserName(), ""+new Date().getTime());//保存粉丝最近交互时间
					
					long msgid = inMessage.getMsgId() == null ? 0 : inMessage
							.getMsgId();
					if (msgId == msgid) {// 消息去重
						if ("text".equals(inMessage.getMsgType())
								|| "voice".equals(inMessage.getMsgType())
								|| "video".equals(inMessage.getMsgType())) {
							return;
						}
					}
					msgId = msgid;
					String platformId = inMessage.getToUserName();// 公众号原始ID
					WccPlatformUser wccPlatformUser = WccPlatformUser.findWccPlatformUserByPlatformId(platformId);// 公众号信息
					wxMpServices =WeixinBasicParam.wxMpServiceMap.get(inMessage.getFromUserName());
					String appId = wccPlatformUser.getAppId();
					String appSecret = wccPlatformUser.getAppSecret();
					log.info("appId="+appId);
					log.info("appSecret="+appSecret);
					if(null == wxMpServices ){
						config.setAppId(appId);
						config.setSecret(appSecret);
						wxMpService.setWxMpConfigStorage(config);
						if(null != appId && !"".equals(appId) && null != appSecret && !"".equals(appSecret)){
							WeixinBasicParam.wxMpServiceMap.put(inMessage.getFromUserName(), wxMpService);
						}
					}else{
						wxMpService = wxMpServices;
					}
					MsgTypeProcessor msgTypeProcessor=new MsgTypeProcessorImp(request, response, wccPlatformUser, inMessage, wxMpService, wxMpServices);
					String msgType = inMessage.getMsgType();// 消息类型
					if ("location".equals(msgType))
					{
						msgTypeProcessor.locationPro();
					}					
					else if ("event".equals(msgType))
					{
						msgTypeProcessor.eventPro();
					}
					else if ("text".equals(msgType))
					{
						msgTypeProcessor.textPro();
					}
					else  if ("image".equals(msgType)) 
					{
						msgTypeProcessor.imagePro();
					}
					else  if ("voice".equals(msgType))
					{
						msgTypeProcessor.voicePro();
					}
					else 	if ("video".equals(msgType))
					{
						msgTypeProcessor.videoPro();
					}
					else if ("link".equals(msgType))
					{// 链接消息
	                    msgTypeProcessor.linkPro();
					}
					else {
						
					}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
       finally
       {
    	   out.close();
		   out=null;
       }
	}


	/**
	 * 查看门店
	 * 
	 * @param uiModel
	 * @param httpServletRequest
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/showStore", method = RequestMethod.GET, produces = "text/html")
	public String showStore(Model uiModel,
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "id", required = false) long id) {
		uiModel.addAttribute("wccStore", WccStore.findWccStore(id));
		return "wccstores/showStore";
	}

	/**
	 * 查看微内容
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/showContents", method = RequestMethod.GET, produces = "text/html")
	public String showContents(
			Model uiModel,
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "contentId", required = false) Long contentId,
			@RequestParam(value = "friendId", required = false) Long friendId)
			throws UnsupportedEncodingException {

		WccFriend wccFriend = WccFriend.findWccFriend(friendId);
		if (null != wccFriend) {
			wccFriend.setNickName(URLDecoder.decode(wccFriend.getNickName(),
					"utf-8"));
			uiModel.addAttribute("wccFriend", wccFriend);
		}
		List<WccComment> wccCommentList = new ArrayList<WccComment>();
		List<WccComment> wccComment = WccComment
				.findWccCommentByContentId(contentId);
		if (null != wccComment && wccComment.size() > 0) {
			WccFriend wccF = null;
			for (WccComment wccC : wccComment) {
				wccF = WccFriend.findWccFriend(wccC.getFriendId());
				wccF.setNickName(URLDecoder.decode(wccF.getNickName(), "utf-8"));
				wccC.setWccFriend(wccF);
				wccCommentList.add(wccC);
			}
		}

		WccContent wccContent = WccContent.findWccContent(contentId);
		if (null != wccContent) {
			wccContent.setCommentCount(wccComment.size());
			uiModel.addAttribute("wcccontent", wccContent);
		}
		uiModel.addAttribute("wccCommentList", wccCommentList);
		uiModel.addAttribute("commentCount", wccComment.size());
		return "wcccontents/showContents";
	}

	/**
	 * 微内容点赞
	 * 
	 * @param uiModel
	 * @param contentId
	 * @param request
	 * @param response
	 * @param friendId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pointPraise", method = RequestMethod.POST, produces = "text/html")
	@ResponseBody
	public String pointPraise(
			Model uiModel,
			@RequestParam(value = "contentId", required = false) Long contentId,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "friendId", required = false) Long friendId) {
		WccContent wccContent = WccContent.findWccContent(contentId);
		if (wccContent != null) {
			/*
			 * 查询该粉丝是否点赞过该内容
			 */
			List<WccPraise> wccPraise = WccPraise.findWccPraiseByContentId(friendId, contentId);
			if (wccPraise != null && wccPraise.size() <= 0) {
				WccFriend wccFriend = WccFriend.findWccFriend(friendId);
				wccContent.setPraiseCount(wccContent.getPraiseCount() + 1);
				wccContent.merge();
				WccPraise wccPra = new WccPraise();
				wccPra.setContentId(contentId);
				wccPra.setCreatTime(new Date());
				wccPra.setFriendId(friendId);
				wccPra.setIsDeleted(false);
				wccPra.setPlatFormId(wccFriend.getPlatformUser().getId());
				wccPra.setCompanyId(wccFriend.getPlatformUser().getCompany()
						.getId());
				wccPra.setPraiseStatus(true);
				wccPra.persist();
				try {
					response.getWriter().append(String.valueOf(wccContent.getPraiseCount()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				try {
					response.getWriter().append(String.valueOf(wccContent.getPraiseCount()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 保存微内容回复
	 * 
	 * @return
	 */
	@RequestMapping(value = "/saveWccComment", method = RequestMethod.POST, produces = "text/html")
	@ResponseBody
	public String saveWccComment(
			Model uiModel,
			@RequestParam(value = "contentId", required = false) Long contentId,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "friendId", required = false) Long friendId) {
		WccContent wccContent = WccContent.findWccContent(contentId);
		if (null != wccContent) {
			WccFriend wccFriend = WccFriend.findWccFriend(friendId);
			if (null != wccFriend) {
				WccComment wccComment = new WccComment();
				wccComment.setContent(content);
				wccComment.setCreateTime(new Date());
				wccComment.setContentId(contentId);
				wccComment.setIsDeleted(false);
				wccComment.setFriendId(friendId);
				wccComment.setContentTitle(wccContent.getTitle());
				wccComment.setPlatFormId(wccFriend.getPlatformUser().getId());
				wccComment.setPlatFormName(wccFriend.getPlatformUser()
						.getAccount());
				wccComment.setCompanyId(wccFriend.getPlatformUser()
						.getCompany().getId());
				wccComment.persist();
				wccContent.setCommentCount(wccContent.getCommentCount() + 1);
				wccContent.merge();
			}
		}
		return "0";

	}

	/**
	 * 关注
	 * 
	 * @param inMessage
	 * @param wccPlatformUser
	 */
	protected void subscribe(WxMpXmlMessage inMessage,
			WccPlatformUser wccPlatformUser) {
		String openId = inMessage.getFromUserName();
		String lang = Global.LANG; // 语言
		List<WccFriend> wfriend = WccFriend.findWccFriendsByOpenId(openId);
		WxMpUser users = new WxMpUser();
		try {
			users = wxMpService.userInfo(openId, lang);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		if (null != wfriend && wfriend.size() > 0) {
			WccFriend friend = wfriend.get(0);
			try {
				friend.setNickName(java.net.URLEncoder.encode(users.getNickname(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			friend.setIsDeleted(true);
			friend.setFromType(WccFriendFormType.关注);
			friend.setSubscribeTime(new Date());
			friend.merge();
			return;
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
		friend.setFromType(WccFriendFormType.关注);
		friend.setHeadImg(users.getHeadImgUrl());
		friend.setInsertTime(new Date());
		friend.setIsDeleted(true);
		friend.setIsValidated(true);
		String niceName = "";
		try {
			niceName = java.net.URLEncoder.encode(users.getNickname(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
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
		friend.setArea(users.getCountry() + users.getProvince()+ users.getCity());
		friend.setOpenId(users.getOpenId());
		friend.setPlatformUser(wccPlatformUser);
		friend.persist();
	}


	@RequestMapping(value = "dispacher", produces = "text/html")
	protected void dispacher(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "code", required = false) String code) {
		PrintWriter out=null;
		try {
			String mid = state.substring(0, state.length() - 1);
			String flag = state.substring(state.length() - 1, state.length());
			out=response.getWriter();
			out.print("");
			if ("1".equals(flag)) {
				WccMenu menu = WccMenu.findWccMenu(Long.parseLong(mid));
				WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
				String appId = menu.getPlatformUser().getAppId();
				String appSecret = menu.getPlatformUser().getAppSecret();
				Map<String,Object> map= RedisCommonHelper.getInstance().getNormalMap("code");
				String openId = null;
				if(null==map.get(code))
				{
					config.setAppId(appId);
					config.setSecret(appSecret);
					WxMpServiceImpl wxMpService = new WxMpServiceImpl();
					wxMpService.setWxMpConfigStorage(config);
					WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService
							.oauth2getAccessToken(code);
					openId=wxMpOAuth2AccessToken.getOpenId();
					map.put(code, openId);
				}
				else 
				{
					openId=String.valueOf(map.get(code)); 
				}
				WccFriend friend = WccFriend.findWccFriendByOpenId(openId);
				if(friend==null)
				{
			   WxMpXmlMessage inMessage=new WxMpXmlMessage();
				inMessage.setFromUserName(openId); 
				subscribe(inMessage,  menu.getPlatformUser());					
				log.error("无法查询到粉丝信息"+openId);
				try {
					friend = WccFriend.findWccFriendByOpenId(openId);
				
				} catch (Exception e) {
					// TODO: handle exception
				log.error("返回失败");
				e.printStackTrace();
				}
			
				}
				StringBuffer url = new StringBuffer();
				url.append(menu.getUrl());
				// if(menu.getUrl().contains(Global.Url)){
				System.out.println("进入~~加入粉丝信息");
//				if(menu.getUrl().contains(Global.Url) || menu.getUrl().contains("ACTIVITY")){
		  if (url.toString().contains("?")) {
		url.append("&friendId=" + friend.getId()).append("&openId="+friend.getOpenId()).append("&state="+menu.getPlatformUser().getId()).append("&code="+code);
			} 
			else{
		url.append("?friendId=" + friend.getId()).append("&openId="+friend.getOpenId()).append("&state="+menu.getPlatformUser().getId()).append("&code="+code);
				}
		  System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu"+url.toString());
//			}
				response.sendRedirect(url.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			out.close();
			out=null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/showRec", produces = "text/html")
	public void showRcorod(HttpServletRequest httpServletRequest) {
		System.out.println(wxMpConfigStorage.getAccessToken());
		System.out.println("aaaaaaaaaaaaaaa");
	}


}


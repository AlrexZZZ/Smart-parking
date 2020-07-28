package com.nineclient.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import me.chanjar.weixin.mp.bean.WxMpMassGroupMessage;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.WxMpMassVideo;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.util.HSSFColor.GOLD;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nineclient.cbd.wcc.model.WccFinancialUser;
import com.nineclient.cherry.wcc.service.MassNewsSend;
import com.nineclient.cherry.wcc.service.MassPicSend;
import com.nineclient.cherry.wcc.service.MassTextSend;
import com.nineclient.cherry.wcc.service.MassVideoSend;
import com.nineclient.cherry.wcc.service.MassVoiceSend;
import com.nineclient.constant.PlatformType;
import com.nineclient.constant.WccMaterialsType;
import com.nineclient.constant.WccTemplateType;
import com.nineclient.constant.WccWelcomkbsReplyType;
import com.nineclient.constant.WccWelcomkbsType;
import com.nineclient.entity.MassConditionEntity;
import com.nineclient.entity.MassSendEntity;
import com.nineclient.model.PubOperator;
import com.nineclient.model.WccCity;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccGroup;
import com.nineclient.model.WccMassMessage;
import com.nineclient.model.WccMaterials;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccProvince;
import com.nineclient.model.WccTemplate;
import com.nineclient.model.WccWelcomkbs;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Global;
import com.nineclient.utils.HttpClientUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;
import com.nineclient.utils.WeiXinPubReturnCode;
import com.nineclient.weixin.WxFileUtil;

@RequestMapping("/wccmassmessage")
@Controller
@RooWebScaffold(path = "wccmassmessage", formBackingObject = WccMassMessageController.class)
public class WccMassMessageController {
	private final Logger log = LoggerFactory.getLogger(WccMassMessageController.class);
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
			sb.append("<select id='msgplatformUser_' name='platformUser' class='publicSize'>");
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
	
	@RequestMapping(value = "massMessage", produces = "text/html")
	public String list(HttpServletRequest request,Model uiModel){
		PubOperator pub=CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platformUser=new ArrayList<WccPlatformUser>();
		if(pub!=null&&!pub.getAccount().equals("admin")){
			Set<WccPlatformUser> platformUserset=pub.getPlatformUsers();
			for (WccPlatformUser wccPlatformUser : platformUserset) {
				if(wccPlatformUser.getPlatformType()==PlatformType.服务号){
					platformUser.add(wccPlatformUser);
				}
			}
		}else{
		   platformUser = WccPlatformUser.findAllWccPlatformUsersByType(CommonUtils.getCurrentPubOperator(request),"服务号");
		
		}
		List<WccProvince> provinces = WccProvince.findAllWccProvinces();
		uiModel.addAttribute("platformUser", platformUser);
		uiModel.addAttribute("platformUser2", platformUser);
		uiModel.addAttribute("provinces", provinces);
		return "wccmassmessage/show";
	}
	
	
	@RequestMapping(value = "dymassMessage", produces = "text/html")
	public String dylist(HttpServletRequest request,Model uiModel){
		PubOperator pub=CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> dyhplatformUser=new ArrayList<WccPlatformUser>();
		if(pub!=null&&!pub.getAccount().equals("admin")){
			Set<WccPlatformUser> platformUserset=pub.getPlatformUsers();
			for (WccPlatformUser wccPlatformUser : platformUserset) {
				if(wccPlatformUser.getPlatformType()==PlatformType.订阅号){
					dyhplatformUser.add(wccPlatformUser);
				}
			}
		}else{
		    dyhplatformUser = WccPlatformUser.findAllWccPlatformUsersByType(CommonUtils.getCurrentPubOperator(request),"订阅号");
		
		}
		uiModel.addAttribute("dyhplatformUser", dyhplatformUser);
		uiModel.addAttribute("dyhplatformUser2", dyhplatformUser);
		return "wccmassmessage/dyhshow";
	}
	
	
	
	@RequestMapping(value = "massMessage2", produces = "text/html")
	public String list2(HttpServletRequest request,Model uiModel){
		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(CommonUtils.getCurrentPubOperator(request));
		List<WccProvince> provinces = WccProvince.findAllWccProvinces();
		uiModel.addAttribute("platformUser", platformUser);
		uiModel.addAttribute("platformUser2", platformUser);
		uiModel.addAttribute("provinces", provinces);
		return "wccmassmessage/show2";
	}
	
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
			sb.append("<select id='groups' name='groupId' class='publicSize' multiple='multiple'>");
//			sb.append("<option value=''>全部</option>");
			List<WccGroup> groups = WccGroup.findGroupByPlatformUser(id + "");
			for (WccGroup group : groups) {
				if(!"黑名单".equals(group.getName())){
					sb.append("<option value='" + group.getId() + "'>"
							+ group.getName() + "</option>");
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
	
	
	@ResponseBody
	@RequestMapping(value = "getDyhGroupByPlatformUser")
	public void getDyhGroupByPlatformUser(@RequestParam(value = "id") Long id,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer();
			sb.append("<select id='dygroups' name='dygroup' class='publicSize' multiple='multiple'>");
//			sb.append("<option value=''>全部</option>");
			List<WccGroup> groups = WccGroup.findGroupByPlatformUser(id + "");
			for (WccGroup group : groups) {
				if(!"黑名单".equals(group.getName())){
					sb.append("<option value='" + group.getId() + "'>"
							+ group.getName() + "</option>");
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
	
	
	@ResponseBody
	@RequestMapping(value = "getDyhGroupByPlatformUser2")
	public void getDyhGroupByPlatformUser2(@RequestParam(value = "id") Long id,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer();
			sb.append("<select id='dygroups2' name='dygroup2' class='publicSize' >");
//			sb.append("<option value=''>全部</option>");
			List<WccGroup> groups = WccGroup.findGroupByPlatformUser(id + "");
			for (WccGroup group : groups) {
				if(!"黑名单".equals(group.getName())){
					sb.append("<option value='" + group.getId() + "'>"
							+ group.getName() + "</option>");
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
	
	
	
	@ResponseBody
	@RequestMapping(value = "getGroupByPlatformUser1")
	public void getGroupByPlatformUser1(@RequestParam(value = "id") Long id,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer();
			sb.append("<select id='dygroups' name='dygroup' class='publicSize' multiple='multiple'>");
//			sb.append("<option value=''>全部</option>");
			List<WccGroup> groups = WccGroup.findGroupByPlatformUser(id + "");
			for (WccGroup group : groups) {
				if(!"黑名单".equals(group.getName())){
					sb.append("<option value='" + group.getId() + "'>"
							+ group.getName() + "</option>");
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
				sb.append("<select id='groups2' name='groupId2' class='publicSize'>");
//				sb.append("<option value=''>全部</option>");
				List<WccGroup> groups = WccGroup.findGroupByPlatformUser(id + "");
				for (WccGroup group : groups) {
					if(!"黑名单".equals(group.getName())){
						sb.append("<option value='" + group.getId() + "'>"
								+ group.getName() + "</option>");
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
	@RequestMapping(value = "getCityByProvince")
	public void getCityByProvince(@RequestParam(value = "id") Long id,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer();
			sb.append("<select id='citys' name='cityName' class='publicSize'");
//			sb.append("<select id='citys' name='cityName' class='publicSize' multiple='multiple'");
			sb.append("><option value=''>全部</option>");
			List<WccCity> citys = WccCity.findAllWccWccCitys(WccProvince.findWccProvince(id));
			for (WccCity city : citys) {
					sb.append("<option value='" + city.getId() + "'>"
							+ city.getName() + "</option>");
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
	 * 根据公众平台查询素材
	 * @param request
	 * @param uiModel
	 * @param platformUserId
	 * @return
	 */
	@RequestMapping(value = "materialsList", produces = "text/html")
	public String materialsList(HttpServletRequest request,Model uiModel,@RequestParam(value = "platformUserId", required = false) String platformUserId) {
		if(!"".equals(platformUserId)&&platformUserId!=null){
			populateEditForm(uiModel,  new WccWelcomkbs(),platformUserId);
		}else{
			populateEditForm(uiModel,  new WccWelcomkbs(),null);
		}
		addDateTimeFormatPatterns(uiModel);
		return "wccmassmessage/list";
	}
	/**
	 * 订阅号群发根据公众平台查询素材
	 * @param request
	 * @param uiModel
	 * @param platformUserId
	 * @return
	 */
	@RequestMapping(value = "materialsList1", produces = "text/html")
	public String materialsList1(HttpServletRequest request,Model uiModel,@RequestParam(value = "platformUserId", required = false) String platformUserId) {
		if(!"".equals(platformUserId)&&platformUserId!=null){
			populateEditForm(uiModel,  new WccWelcomkbs(),platformUserId);
		}else{
			populateEditForm(uiModel,  new WccWelcomkbs(),null);
		}
		addDateTimeFormatPatterns(uiModel);
		return "wccmassmessage/list2";
	}
	
	private void populateEditForm(Model uiModel, WccWelcomkbs wccWelcomkbs, String platformUserId) {
		uiModel.addAttribute("wccWelcomkbs", wccWelcomkbs);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("wccwelcomkbsreplytypes",
				Arrays.asList(WccWelcomkbsReplyType.values()));
		uiModel.addAttribute("wccwelcomkbstypes",
				Arrays.asList(WccWelcomkbsType.values()));
		if(platformUserId!=null){
			
		uiModel.addAttribute("TEXT", WccWelcomkbsReplyType.文本);
		uiModel.addAttribute("PIC", WccWelcomkbsReplyType.图片);
		uiModel.addAttribute("IMG", WccWelcomkbsReplyType.图文);
		uiModel.addAttribute("SOU", WccWelcomkbsReplyType.语音);
		uiModel.addAttribute("VID", WccWelcomkbsReplyType.视频);
		uiModel.addAttribute("wccPicture",WccMaterials.findWccMaterialsesByType2(WccMaterialsType.PICTURE.ordinal(),platformUserId));
		uiModel.addAttribute("wccImageText", WccMaterials
				.findWccMaterialsesByType2(WccMaterialsType.IMAGETEXT.ordinal(),platformUserId));
		uiModel.addAttribute("wccSounds", WccMaterials
				.findWccMaterialsesByType2(WccMaterialsType.SOUNDS.ordinal(),platformUserId));
		uiModel.addAttribute("wccVideo", WccMaterials
				.findWccMaterialsesByType2(WccMaterialsType.VIDEO.ordinal(),platformUserId));
		}
	}
	
	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"wccWelcomkbs_inserttime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}
	
	@RequestMapping(value = "sendMassMessage", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String sendMassMessage(Model uiModel, 
			@RequestParam(value = "platformUser",required = false) String platformUserId,
			@RequestParam(value = "gender",required = false) Long gender,
			@RequestParam(value = "group",required = false) String group,
			@RequestParam(value = "province",required = false) String province,
			@RequestParam(value = "city",required = false) String city,
			@RequestParam(value = "messNum",required = false) String messNum,
			@RequestParam(value = "sid",required = false) Long sid,
			@RequestParam(value = "messType",required = false) Long messType,
			@RequestParam(value = "content",required = false) String content,
			@RequestParam(value = "num",required = false) Long num,
//			@RequestParam(value = "massTime",required = false) String massTime,
//			@RequestParam(value = "phoneStr",required = false) String phoneStr,
			HttpServletRequest request,
			HttpServletResponse response) throws WxErrorException {
		//公众平台
		List<WccPlatformUser> platformUser = WccPlatformUser.findWccPlatformUserById2(platformUserId);
		String str = "susscess:true,msg:\"发送成功\"";
		for(WccPlatformUser wcc:platformUser){
	
		PubOperator pub = CommonUtils.getCurrentPubOperator(request);
		List<WccGroup> bgroups = WccGroup.findWccGroupByName2(platformUserId, "黑名单");
		WccGroup bgroup = null;
		if(null != bgroups && bgroups.size()>0){
			bgroup = bgroups.get(0);
		}
//		if(num == 2){//定时群发
//			String ctxPath = request.getSession().getServletContext().getRealPath("");
//			Date massDate = null;
//			SimpleDateFormat DATE_AND_TIME_FORMATER = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//			if(null != massTime && !"".equals(massTime)){
//				try {
//					massDate = DATE_AND_TIME_FORMATER.parse(massTime);
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//			}
//			
//			List<String> openIds = new ArrayList<String>();
//			Set<WccFriend> wfriends = new HashSet<WccFriend>();
//			List<WccFriend> friends = WccFriend.findMassWccFriends2(platformUserId);
//			for (WccFriend friend : friends) {
//				openIds.add(friend.getOpenId());
//			}
//			wfriends.addAll(friends);
//			WccMaterials material = WccMaterials.findWccMaterials(sid);
//		
//			WccMassMessage massMsg = new WccMassMessage();
//			
//			//文本
//			if(messType == 1){
//				massMsg.setType(1);
//				massMsg.setContent(content);
//			}
//			//图片
//			if(messType == 2){
//				massMsg.setType(2);
//				massMsg.setMaterial(material);
//			}
//			//视频
//			if(messType == 3){
//				massMsg.setType(3);
//				massMsg.setMaterial(material);
//			}
//			//语音
//			if(messType == 4){
//				massMsg.setType(4);
//				massMsg.setMaterial(material);
//			}
//			//图文
//			if(messType == 5){
//				massMsg.setType(5);
//				massMsg.setMaterial(material);
//			}
//			
//				massMsg.setPath(ctxPath);
//				massMsg.setDelete(false);
//				massMsg.setPlatformUser(wcc);
//				massMsg.setFriends(wfriends);
//				massMsg.setInsertTime(new Date());
//				massMsg.setCompany(pub.getCompany());
//				massMsg.setInsertTime(massDate);
//				massMsg.setState(1);
//				massMsg.persist();
//			
//		
//			return str;
//		}
//		
		
		
		
		WccMaterials material = new WccMaterials();
		String ctxPath = request.getSession()
				.getServletContext().getRealPath("");
		String domain = Global.Url;
		File file = null;
		FileInputStream inputStream = null;
		if(1 != messType){
			material = WccMaterials.findWccMaterials(sid);
			file = new File(ctxPath+material.getThumbnailUrl().substring(4));
			try {
				inputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//群发OpenId列表
		List<String> openIds = new ArrayList<String>();
	
		WccMassMessage massMsg = new WccMassMessage();
		massMsg.setPlatformUser(wcc);
		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		String appId = wcc.getAppId();
		String appSecret = wcc.getAppSecret();
		config.setAppId(appId);
		config.setSecret(appSecret);
		WxMpServiceImpl wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(config);
		//用户列表群发
		WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
		//分组群发
		WxMpMassGroupMessage massGroupMsg = new WxMpMassGroupMessage();
		//分组群发
		if(null == gender && "全部".equals(province) && "全部".equals(city) && "null".equals(messNum) && !"null".equals(group)){
			String[] groups = group.split(",");
			List<Long> massGroups = new ArrayList<Long>();//群发分组
			WccGroup wgroup = new WccGroup();
			for (String gstr : groups) {
				wgroup = WccGroup.findWccGroup(Long.parseLong(gstr));
				massGroups.add(wgroup.getGroupId());
			}
			System.out.println("分组群发");
			//文本
			if(messType == 1){
				massGroupMsg.setMsgtype(WxConsts.MASS_MSG_TEXT);
				massGroupMsg.setContent(content);
				massMsg.setType(1);
				massMsg.setContent(content);
			}
			//图片
			if(messType == 2){
				massGroupMsg.setMsgtype(WxConsts.MASS_MSG_IMAGE);
				WxMediaUploadResult uploadMediaRes = new WxMediaUploadResult();
				try {
					uploadMediaRes = wxMpService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, inputStream);
				} catch (WxErrorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				massGroupMsg.setMediaId(uploadMediaRes.getMediaId());
				massMsg.setType(2);
				massMsg.setMediaId(uploadMediaRes.getMediaId());
				massMsg.setMaterial(material);
			}
			//视频
			if(messType == 3){
				massGroupMsg.setMsgtype(WxConsts.MASS_MSG_VIDEO);
				WxMediaUploadResult uploadMediaRes = new WxMediaUploadResult();
				try {
					uploadMediaRes = wxMpService.mediaUpload(WxConsts.MEDIA_VIDEO, WxConsts.FILE_MP4, inputStream);
					WxMpMassVideo video = new WxMpMassVideo();
					video.setTitle(material.getTitle());
					video.setDescription(material.getDescription());
					video.setMediaId(uploadMediaRes.getMediaId());
					WxMpMassUploadResult uploadResult = wxMpService.massVideoUpload(video);
					massGroupMsg.setMediaId(uploadResult.getMediaId());
					massMsg.setType(3);
					massMsg.setMediaId(uploadResult.getMediaId());
					massMsg.setMaterial(material);
				} catch (WxErrorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				 
			}
			//语音
			if(messType == 4){
				massGroupMsg.setMsgtype(WxConsts.MASS_MSG_VOICE);
				WxMediaUploadResult uploadMediaRes = new WxMediaUploadResult();
				try {
					uploadMediaRes = wxMpService.mediaUpload(WxConsts.MEDIA_VOICE, WxConsts.FILE_MP3, inputStream);
					massGroupMsg.setMediaId(uploadMediaRes.getMediaId());
					massMsg.setType(4);
					massMsg.setMediaId(uploadMediaRes.getMediaId());
					massMsg.setMaterial(material);
				} catch (WxErrorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//图文
			if(messType == 5){
				massGroupMsg.setMsgtype(WxConsts.MASS_MSG_NEWS);
				WxMediaUploadResult uploadResult = new WxMediaUploadResult();
				try {
					uploadResult = wxMpService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, inputStream);
				} catch (WxErrorException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				WxMpMassNews news = new WxMpMassNews();
				WxMpMassNews.WxMpMassNewsArticle article1 = new WxMpMassNews.WxMpMassNewsArticle();
				article1.setTitle(material.getTitle());
				article1.setContent(WxFileUtil.replaceImgSrc(material.getContent(), request, wxMpService.getAccessToken()));
				article1.setDigest(material.getDescription());
				article1.setShowCoverPic(material.getUrlBoolean());
				article1.setThumbMediaId(uploadResult.getMediaId());
				news.addArticle(article1);//第一个图文
				if(!material.getChildren().isEmpty()){//如果有子图文（多图文）
					List<WccMaterials> children = material.getChildren();
					Iterator<WccMaterials> iter = children.iterator();
					while (iter.hasNext()) {
						WccMaterials wmaterial =  iter.next();
						file = new File(ctxPath+wmaterial.getThumbnailUrl().substring(4));
						WxMediaUploadResult uploadResult2 = new WxMediaUploadResult();
						try {
							inputStream = new FileInputStream(file);
							uploadResult2 = wxMpService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, inputStream);
						} catch (WxErrorException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						WxMpMassNews.WxMpMassNewsArticle article2 = new WxMpMassNews.WxMpMassNewsArticle();
						article2.setTitle(wmaterial.getTitle());
					
						article2.setContent(WxFileUtil.replaceImgSrc(material.getContent(), request, wxMpService.getAccessToken()));
						article2.setShowCoverPic(material.getUrlBoolean());
						article2.setDigest(wmaterial.getDescription());
						article2.setThumbMediaId(uploadResult2.getMediaId());
						news.addArticle(article2);
					}
				}
				try {
					WxMpMassUploadResult massUploadResult = wxMpService.massNewsUpload(news);
					massGroupMsg.setMediaId(massUploadResult.getMediaId());
					massMsg.setType(5);
					massMsg.setMediaId(massUploadResult.getMediaId());
					massMsg.setMaterial(material);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "susscess:false";
				}
			}
		
			for(Long mgroup : massGroups){
				WccMassMessage wmassMsg = new WccMassMessage();
				wmassMsg.setContent(massMsg.getContent());
				wmassMsg.setPlatformUser(massMsg.getPlatformUser());
				wmassMsg.setDelete(false);
				
				wmassMsg.setMediaId(massMsg.getMediaId());
				wmassMsg.setType(massMsg.getType());
				wmassMsg.setMaterial(material);
				massGroupMsg.setGroupId(mgroup);
				try {
					WxMpMassSendResult massResult = wxMpService.massGroupMessageSend(massGroupMsg);
//					WxMpMassSendResult massResult = new WxMpMassSendResult();
					if("0".equals(massResult.getErrorCode())&&massResult.getErrorMsg().contains("success")){//massResult返回成功 需改
						Set<WccFriend> set = new HashSet<WccFriend>();
						List<WccFriend> friends = WccFriend.findMassWccFriends2(platformUserId);
						set.addAll(friends);
						wmassMsg.setFriends(set);
						wmassMsg.setInsertTime(new Date());
						wmassMsg.setMsgId(massResult.getMsgId());
						wmassMsg.setCompany(pub.getCompany());
						wmassMsg.setState(4);
						wmassMsg.persist();
					}else{
						JSONObject json = JSONObject.fromObject(massResult);
						WeiXinPubReturnCode code = new WeiXinPubReturnCode();
						return "success:false,msg:\""+code.pubReturnCode(json.getString("errcode"))+"\"";
					}
					System.out.println(massResult.toString());
				} catch (Exception e) {
					e.getMessage();
					e.printStackTrace();
					if(e.getMessage().contains("errcode")){
						JSONObject json = JSONObject.fromObject(e.getMessage());
						WeiXinPubReturnCode code = new WeiXinPubReturnCode();
						return "success:false,msg:\""+code.pubReturnCode(json.getString("errcode"))+"\"";
					}
					return "success:false,msg:\""+e.getMessage()+"\"";
				}
			}
		}
			
				
		//用户列表群发
		else{
			System.out.println("条件群发");
			List<WccFriend> friends = WccFriend.findMassWccFriends2(wcc.getId());		
			if(null!=friends&&friends.size()==0){
				return "success:false,msg:\"所选条件下无粉丝\"";
			}	if(null!=friends&&friends.size()==1){
				return "success:false,msg:\"所选条件下至少两个粉丝\"";
			}
			String[] numss = messNum.split(",");
			List<WccFriend> wfriends = new ArrayList<WccFriend>();
			for (WccFriend friend : friends) {//粉丝
				int numbers = 0;
				Set<WccMassMessage> nums = friend.getMassMessages();
				for (WccMassMessage wccMassMessage : nums) {
					if(timeCheck(wccMassMessage.getInsertTime(),new Date())){
						numbers++;
					}
					System.out.println(wccMassMessage.getInsertTime());
				}
				System.out.println("numbers===="+numbers);
				if(null !=messNum && !"null".equals(messNum)){
					for (String strnum : numss) {
						if(numbers == Integer.parseInt(strnum)){
							openIds.add(friend.getOpenId());
							wfriends.add(friend);
						}
					}
				}else{
					if(numbers<4){
						if(null != friend.getOpenId() && !"".equals(friend.getOpenId())){
							
							openIds.add(friend.getOpenId());
							wfriends.add(friend);
						}
						
					}
				}
			}
			
			massMessage.getToUsers().addAll(openIds);
			
			//文本
			if(messType == 1){
				massMessage.setMsgType(WxConsts.MASS_MSG_TEXT);
				massMessage.setContent(content);
				massMsg.setType(1);
				massMsg.setContent(content);
			}
			//图片
			if(messType == 2){
				massMessage.setMsgType(WxConsts.MASS_MSG_IMAGE);
				WxMediaUploadResult uploadMediaRes = new WxMediaUploadResult();
				try {
					uploadMediaRes = wxMpService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, inputStream);
				} catch (WxErrorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				massMessage.setMediaId(uploadMediaRes.getMediaId());
				massMsg.setType(2);
				massMsg.setMediaId(uploadMediaRes.getMediaId());
				massMsg.setMaterial(material);
			}
			//视频
			if(messType == 3){
				massMessage.setMsgType(WxConsts.MASS_MSG_VIDEO);
				WxMediaUploadResult uploadMediaRes = new WxMediaUploadResult();
				try {
					uploadMediaRes = wxMpService.mediaUpload(WxConsts.MEDIA_VIDEO, WxConsts.FILE_MP4, inputStream);
					WxMpMassVideo video = new WxMpMassVideo();
					video.setTitle(material.getTitle());
					video.setDescription(material.getDescription());
					video.setMediaId(uploadMediaRes.getMediaId());
					WxMpMassUploadResult uploadResult = wxMpService.massVideoUpload(video);
					massMessage.setMediaId(uploadResult.getMediaId());
					massMsg.setType(3);
					massMsg.setMediaId(uploadResult.getMediaId());
					massMsg.setMaterial(material);
				} catch (Exception e) {
					e.printStackTrace();
					return "susscess:false";
				} 
			}
			//语音
			if(messType == 4){
				massMessage.setMsgType(WxConsts.MASS_MSG_VOICE);
				WxMediaUploadResult uploadMediaRes = new WxMediaUploadResult();
				try {
					uploadMediaRes = wxMpService.mediaUpload(WxConsts.MEDIA_VOICE, WxConsts.FILE_MP3, inputStream);
					massMessage.setMediaId(uploadMediaRes.getMediaId());
					massMsg.setType(4);
					massMsg.setMediaId(uploadMediaRes.getMediaId());
					massMsg.setMaterial(material);
				} catch (WxErrorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			}
			//图文
			if(messType == 5){
				massMessage.setMsgType(WxConsts.MASS_MSG_NEWS);
				WxMediaUploadResult uploadResult = new WxMediaUploadResult();
				try {
					uploadResult = wxMpService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, inputStream);
				} catch (WxErrorException  e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				WxMpMassNews news = new WxMpMassNews();
				WxMpMassNews.WxMpMassNewsArticle article1 = new WxMpMassNews.WxMpMassNewsArticle();
				article1.setTitle(material.getTitle());
			
				article1.setContent(WxFileUtil.replaceImgSrc(material.getContent(), request, wxMpService.getAccessToken()));
				article1.setDigest(material.getDescription());
				article1.setShowCoverPic(material.getUrlBoolean());
				article1.setThumbMediaId(uploadResult.getMediaId());
				news.addArticle(article1);//第一个图文
				if(!material.getChildren().isEmpty()){//如果有子图文（多图文）
					List<WccMaterials> children = material.getChildren();
					Iterator<WccMaterials> iter = children.iterator();
					while (iter.hasNext()) {
						WccMaterials wmaterial =  iter.next();
						file = new File(ctxPath+wmaterial.getThumbnailUrl().substring(4));
						WxMediaUploadResult uploadResult2 = new WxMediaUploadResult();
						try {
							inputStream = new FileInputStream(file);
							uploadResult2 = wxMpService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, inputStream);
						} catch (WxErrorException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						WxMpMassNews.WxMpMassNewsArticle article2 = new WxMpMassNews.WxMpMassNewsArticle();
						article2.setTitle(wmaterial.getTitle());
					
						article2.setContent(WxFileUtil.replaceImgSrc(material.getContent(), request, wxMpService.getAccessToken()));
						article2.setDigest(wmaterial.getDescription());
						article2.setShowCoverPic(material.getUrlBoolean());
						article2.setThumbMediaId(uploadResult2.getMediaId());
						news.addArticle(article2);
					}
				}
				try {
					WxMpMassUploadResult massUploadResult = wxMpService.massNewsUpload(news);
					massMessage.setMediaId(massUploadResult.getMediaId());
					massMsg.setType(5);
					massMsg.setMediaId(massUploadResult.getMediaId());
					massMsg.setMaterial(material);
				} catch (WxErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				if(null != massMessage.getToUsers() && massMessage.getToUsers().size()==0){
					return "success:false,msg:\"信息发送已上限，如果是订阅号请选择粉丝分组\"";
				}
				
				WxMpMassSendResult massResult = wxMpService.massOpenIdsMessageSend(massMessage);
//				WxMpMassSendResult massResult = new WxMpMassSendResult();
				System.out.println(massResult.toString());
				if("0".equals(massResult.getErrorCode())&&massResult.getErrorMsg().contains("success")){//massResult返回成功 需改
					Set<WccFriend> set = new HashSet<WccFriend>();
					set.addAll(wfriends);
					massMsg.setFriends(set);
					massMsg.setInsertTime(new Date());
					massMsg.setMsgId(massResult.getMsgId());
					massMsg.setCompany(pub.getCompany());
					massMsg.setState(4);
					massMsg.persist();
				}else{
					JSONObject json = JSONObject.fromObject(massResult);
					WeiXinPubReturnCode code = new WeiXinPubReturnCode();
					return "success:false,msg:\""+code.pubReturnCode(json.getString("errcode"))+"\"";
				}
			} catch (Exception e) {
				e.printStackTrace();
				if(e.getMessage().contains("errcode")){
					JSONObject json = JSONObject.fromObject(e.getMessage());
					WeiXinPubReturnCode code = new WeiXinPubReturnCode();
					return "success:false,msg:\""+code.pubReturnCode(json.getString("errcode"))+"\"";
				}
				return "success:false,msg:\""+e.getMessage()+"\"";
			}
		}
		}
		
		return str;
		
	}
	
	@RequestMapping(value = "messPreview", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String messPreview(Model uiModel, 
			@RequestParam(value = "platformUser",required = false) Long platformUserId,
			@RequestParam(value = "group",required = false) String group,
			@RequestParam(value = "sid",required = false) Long sid,
			@RequestParam(value = "messType",required = false) Long messType,
			@RequestParam(value = "content",required = false) String content,
			@RequestParam(value = "nickName",required = false) String nickName,
			HttpServletRequest request,
			HttpServletResponse response) throws WxErrorException {
		String str = "";//调用预览接口传的参数
		WccMassMessage massMessage = new WccMassMessage();
		List<WccFriend> friends = new ArrayList<WccFriend>();
		try {
			//根据粉丝昵称查询粉丝
			friends = WccFriend.findFriendByNickName(nickName,platformUserId.toString(),group);
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		WccMaterials material = new WccMaterials();
		String ctxPath = request.getSession()
				.getServletContext().getRealPath("");
		String domain = Global.Url;
		File file = null;
		FileInputStream inputStream = null;
		if(1 != messType){
			material = WccMaterials.findWccMaterials(sid);
			file = new File(ctxPath+material.getThumbnailUrl().substring(4));
			try {
				inputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//群发OpenId列表
		//公众平台
		WccPlatformUser platformUser = WccPlatformUser.findWccPlatformUser(platformUserId);
		massMessage.setPlatformUser(platformUser);
		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		String appId = platformUser.getAppId();
		String appSecret = platformUser.getAppSecret();
		
		config.setAppId(appId);
		config.setSecret(appSecret);
		WxMpServiceImpl wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(config);
		
		for (WccFriend wccFriend : friends) {
			//文本
			if(messType == 1){
				str = "{\"touser\":\""+wccFriend.getOpenId()+"\",\"text\":{\"content\":\""+content+"\"},\"msgtype\":\"text\"}";
	//			MASSGROUPMSG.SETMSGTYPE(WXCONSTS.MASS_MSG_TEXT);
	//			massGroupMsg.setContent(content);
			}
			//图片
			if(messType == 2){
	//			massGroupMsg.setMsgtype(WxConsts.MASS_MSG_IMAGE);
				WxMediaUploadResult uploadMediaRes = new WxMediaUploadResult();
				try {
					uploadMediaRes = wxMpService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, inputStream);
					str = "{\"touser\":\""+wccFriend.getOpenId()+"\",\"image\":{\"media_id\":\""+uploadMediaRes.getMediaId()+"\"},\"msgtype\":\"image\"}";
				} catch (WxErrorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	//			massGroupMsg.setMediaId(uploadMediaRes.getMediaId());
			}
			//视频
			if(messType == 3){
	//			massGroupMsg.setMsgtype(WxConsts.MASS_MSG_VIDEO);
				WxMediaUploadResult uploadMediaRes = new WxMediaUploadResult();
				try {
					uploadMediaRes = wxMpService.mediaUpload(WxConsts.MEDIA_VIDEO, WxConsts.FILE_MP4, inputStream);
					WxMpMassVideo video = new WxMpMassVideo();
					video.setTitle(material.getTitle());
					video.setDescription(material.getDescription());
					video.setMediaId(uploadMediaRes.getMediaId());
					WxMpMassUploadResult uploadResult = wxMpService.massVideoUpload(video);
					str = "{\"touser\":\""+wccFriend.getOpenId()+"\",\"mpvideo\":{\"media_id\":\""+uploadResult.getMediaId()+"\"},\"msgtype\":\"mpvideo\"}";
	//				massGroupMsg.setMediaId(uploadResult.getMediaId());
				} catch (WxErrorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			//语音
			if(messType == 4){
	//			massGroupMsg.setMsgtype(WxConsts.MASS_MSG_VOICE);
				WxMediaUploadResult uploadMediaRes = new WxMediaUploadResult();
				try {
					uploadMediaRes = wxMpService.mediaUpload(WxConsts.MEDIA_VOICE, WxConsts.FILE_MP3, inputStream);
					str = "{\"touser\":\""+wccFriend.getOpenId()+"\",\"voice\":{\"media_id\":\""+uploadMediaRes.getMediaId()+"\"},\"msgtype\":\"voice\"}";
	//				massGroupMsg.setMediaId(uploadMediaRes.getMediaId());
				} catch (WxErrorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//图文
			if(messType == 5){
	//			massGroupMsg.setMsgtype(WxConsts.MASS_MSG_NEWS);
				WxMediaUploadResult uploadResult = new WxMediaUploadResult();
				try {
					uploadResult = wxMpService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, inputStream);
				} catch (WxErrorException  e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				WxMpMassNews news = new WxMpMassNews();
				WxMpMassNews.WxMpMassNewsArticle article1 = new WxMpMassNews.WxMpMassNewsArticle();
				article1.setTitle(material.getTitle());
		
				article1.setContent(WxFileUtil.replaceImgSrc(material.getContent(), request, wxMpService.getAccessToken()));
				article1.setDigest(material.getDescription());
				article1.setShowCoverPic(material.getUrlBoolean());
				article1.setThumbMediaId(uploadResult.getMediaId());
				article1.setContentSourceUrl(material.getSourceUrl());
				news.addArticle(article1);//第一个图文
				if(!material.getChildren().isEmpty()){//如果有子图文（多图文）
					List<WccMaterials> children = material.getChildren();
					Iterator<WccMaterials> iter = children.iterator();
					while (iter.hasNext()) {
						WccMaterials wmaterial =  iter.next();
						file = new File(ctxPath+wmaterial.getThumbnailUrl().substring(4));
						WxMediaUploadResult uploadResult2 = new WxMediaUploadResult();
						try {
							inputStream = new FileInputStream(file);
							uploadResult2 = wxMpService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, inputStream);
						} catch (WxErrorException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						WxMpMassNews.WxMpMassNewsArticle article2 = new WxMpMassNews.WxMpMassNewsArticle();
						article2.setTitle(wmaterial.getTitle());
					
						article2.setContent(WxFileUtil.replaceImgSrc(wmaterial.getContent(), request, wxMpService.getAccessToken()));
						article2.setDigest(wmaterial.getDescription());
						article2.setShowCoverPic(wmaterial.getUrlBoolean());
						article2.setThumbMediaId(uploadResult2.getMediaId());
						article2.setContentSourceUrl(wmaterial.getSourceUrl());
						news.addArticle(article2);
					}
				}
				try {
					WxMpMassUploadResult massUploadResult = wxMpService.massNewsUpload(news);
					str = "{\"touser\":\""+wccFriend.getOpenId()+"\",\"mpnews\":{\"media_id\":\""+massUploadResult.getMediaId()+"\"},\"msgtype\":\"mpnews\"}";
	//				massGroupMsg.setMediaId(massUploadResult.getMediaId());
				} catch (WxErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String accessTok = "";
			if (wccFriend.getPlatformUser().getPlatformId() != null
					&& acctoken.get(wccFriend.getPlatformUser().getPlatformId()) == null) {
				try {
					accessTok =wxMpService.getAccessToken();
				} catch (WxErrorException e1) {
					e1.printStackTrace();
				}
				
				acctoken.put(wccFriend.getPlatformUser().getPlatformId(),
						accessTok);
			}
			
			try {
				String result = HttpClientUtil
						.doJsonPost(
								"https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token="
										+ acctoken.get(wccFriend.getPlatformUser()
												.getPlatformId()), str);
				
				if (result.indexOf(" access_token expired") > 0
						|| result.indexOf("invalid credential") > 0
						|| result.indexOf("40001") > 0) {// 如果过期了
					String accessToken =wxMpService.getAccessToken();
					acctoken.put(wccFriend.getPlatformUser().getPlatformId(),
							accessToken);
					result = HttpClientUtil
							.doJsonPost(
									"https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token="
											+ acctoken.get(wccFriend.getPlatformUser()
													.getPlatformId()), str);// 再次请求
				}
				System.out.println("result="+result);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}//for结束
		return null;
	}
	
	@RequestMapping(value = "checkFriend", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String checkFriend(Model uiModel, 
			@RequestParam(value = "platformUser",required = false) Long platformUserId,
			@RequestParam(value = "group",required = false) String group,
			@RequestParam(value = "nickName",required = false) String nickName,
			HttpServletRequest request,
			HttpServletResponse response) {
		String flag = "false";
		WccMassMessage massMessage = new WccMassMessage();
		List<WccFriend> friends = new ArrayList<WccFriend>();
		try {
			//根据粉丝昵称查询粉丝
			friends = WccFriend.findFriendByNickName(nickName,platformUserId.toString(),group);
			if(friends.size()>0){
				flag = "true";
			}
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		return flag;
	}
	@RequestMapping(value = "/massMessageList", produces = "text/html")
	public String massMessageList(Model uiModel, HttpServletRequest request,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "platType", required = false) String platType,
			@RequestParam(value = "platformUser", required = false) String platformUserId,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "insertStartTime", required = false) String insertStartTime,
			@RequestParam(value = "insertEndTime", required = false) String insertEndTime)
			throws ServletException, IOException {
		
		PubOperator pub=CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platformUser=new ArrayList<WccPlatformUser>();
		if(pub!=null&&!pub.getAccount().equals("admin")){
			Set<WccPlatformUser> platformUserset=pub.getPlatformUsers();
			for (WccPlatformUser wccPlatformUser : platformUserset) {
				if(wccPlatformUser.getPlatformType()==PlatformType.服务号){
					platformUser.add(wccPlatformUser);
				}if(wccPlatformUser.getPlatformType()==PlatformType.订阅号){
					platformUser.add(wccPlatformUser);
			    }
			}
		}else{
		   platformUser = WccPlatformUser.findAllWccPlatformUsersByType(CommonUtils.getCurrentPubOperator(request),"服务号");
		
		}
		 StringBuffer ids = new StringBuffer();
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
//		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(CommonUtils.getCurrentPubOperator(request));
		Long companyId = CommonUtils.getCurrentCompanyId(request);
	
		Map<String, String> parmMap = new HashMap<String, String>();
		parmMap.put("platType", platType);
		parmMap.put("platformUserId", platformUserId);
		parmMap.put("ids", ids.toString());
		parmMap.put("state", state);
		parmMap.put("insertStartTime", insertStartTime);
		parmMap.put("insertEndTime", insertEndTime);
		WccMassMessage massMessage = new WccMassMessage();
		QueryModel<WccMassMessage> qm = new QueryModel<WccMassMessage>(massMessage,
				start, limit);
		qm.getInputMap().putAll(parmMap);
		PageModel<WccMassMessage> pm = WccMassMessage.getMassMessage(qm, companyId);
		pm.setPageSize(limit);
		pm.setStartIndex(start);
		uiModel.addAttribute("platformArr", PlatformType.values());
		uiModel.addAttribute("msgplatformUser", platformUser);
		uiModel.addAttribute("wccMassMessage", pm.getDataList());
		uiModel.addAttribute("maxPages", pm.getTotalPage());
		uiModel.addAttribute("page", pm.getPageNo());
		uiModel.addAttribute("limit", pm.getPageSize());

		return "wccmassmessage/result";
	}
	
	@RequestMapping(value = "delMassMsg", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String delMassMsg(@RequestParam(value = "id",required = false) Long id){
		WccMassMessage massMsg = WccMassMessage.findWccMassMessage(id);
		massMsg.setDelete(true);
		massMsg.merge();
		return "success";
	}
	
	private static boolean timeCheck(Date d1,Date d2){
		boolean flag = false;
		Calendar cal1 = Calendar.getInstance(); 
		Calendar cal2 = Calendar.getInstance(); 
		cal1.setTime(d1);
		cal2.setTime(d2);
		int year1 = cal1.get(Calendar.YEAR);
		int month1 = cal1.get(Calendar.MONTH);
		int day1 = cal1.get(Calendar.DATE);
		int hour1 = cal1.get(Calendar.HOUR_OF_DAY);
		int minute1	= cal1.get(Calendar.MINUTE);
		int year2 = cal2.get(Calendar.YEAR);
		int month2 = cal2.get(Calendar.MONTH);
		int day2 = cal2.get(Calendar.DATE);
		int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
		int minute2	= cal2.get(Calendar.MINUTE);
		System.out.println("year="+year1+"---"+year2);
		System.out.println("month="+month1+"---"+month2);
		System.out.println("day="+day1+"---"+day2);
		System.out.println("hour="+hour1+"---"+hour2);
		System.out.println("minute="+minute1+"---"+minute2);
		if(year1 == year2 && month1 == month2){
			flag = true;
		}
		return flag;
	}
	
	
	/**
	 * 服务号群发需要有发布操作，
	 * @param uiModel
	 * @param platformUserId
	 * @param gender
	 * @param group
	 * @param province
	 * @param city
	 * @param messNum
	 * @param sid
	 * @param messType
	 * @param content
	 * @param num
	 * @param massTime
	 * @param phoneStr
	 * @param request
	 * @param response
	 * @return
	 * @throws WxErrorException
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "sendMessage", produces = "text/html;charset=utf-8")
	public String sendMessage(Model uiModel, 
			@RequestParam(value = "platformUser",required = false) String mplat,
			@RequestParam(value = "gender",required = false) String gender,
			@RequestParam(value = "group",required = false) String group,
			@RequestParam(value = "province",required = false) String province,
			@RequestParam(value = "city",required = false) String city,
			@RequestParam(value = "messNum",required = false) String messNum,
			@RequestParam(value = "fromType",required = false) String fromType,
			@RequestParam(value = "sid",required = false) String sid,
			@RequestParam(value = "messType",required = false) Long messType,
			@RequestParam(value = "content",required = false) String content,
			HttpServletRequest request,
			HttpServletResponse response) throws WxErrorException, IOException {
		String massUrl = "http://127.0.0.1:8080/tailong_wccmass/";
		//公众平台
		WccPlatformUser wcc =WccPlatformUser.findWccPlatformUser(CommonUtils.getStrToLong(mplat));
		String str = "susscess:true,msg:\"已发送\"";
		//for(WccPlatformUser wcc:platformUsers){
					PubOperator pub = CommonUtils.getCurrentPubOperator(request);
					WccMaterials material = new WccMaterials();
					String ctxPath = request.getSession().getServletContext().getRealPath("");
					
					File file = null;
					FileInputStream inputStream = null; 
					if(messType!=1){//获取素材
						material = WccMaterials.findWccMaterials(CommonUtils.getStrToLong(sid));
						try {
							file = new File(ctxPath+material.getThumbnailUrl().substring(4));
							inputStream = new FileInputStream(file);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					String key="";
					List<String> massGroups = new ArrayList<String>();//群发分组
					List<WccGroup> grouplist= new ArrayList<WccGroup>();
					//分组群发	
				    if("" == gender && "全部".equals(province) && "全部".equals(city) && "".equals(fromType)  && "null".equals(messNum) && !"null".equals(group)){
						String[] groups = group.split(",");
						WccGroup wgroup = new WccGroup();
						for (String gstr : groups) {
							wgroup = WccGroup.findWccGroup(Long.parseLong(gstr));
							massGroups.add(Long.toString(wgroup.getGroupId()));
							grouplist.add(wgroup);
						}
						Set<WccGroup> setgroups = new HashSet<WccGroup>();
						setgroups.addAll(grouplist);
						//分组群发	
						if(null!=massGroups&&massGroups.size()==0){
								str= "success:false,msg:\"所选条件下无数据\"";
						}else {	
							MassSendEntity massSendEntity=new MassSendEntity();
							massSendEntity.setPub(pub);
							massSendEntity.setCtxPatch(ctxPath);
							massSendEntity.setContent(content);
							massSendEntity.setList(massGroups);
							massSendEntity.setWccplatforuser(wcc);		
							massSendEntity.setGender(CommonUtils.getStrToInt(gender));
							massSendEntity.setRequest(request);
							massSendEntity.setState(1);
							massSendEntity.setGroupset(setgroups);
							key=MassSendEntity.getMassKey(messType, material, inputStream, massSendEntity);
							//发送群发消息
							String url=massUrl+"api/sendMassMessage";
							Map <String, String> params = new HashMap<String, String>();
							params.put("key", key);
							params.put("flag", "2");
							params.put("appId", wcc.getAppId());
							params.put("appSecret", wcc.getAppSecret());
							try{
								log.info("=====服务号分组群发====");
								HttpClientUtil.doPost(url, params, "utf-8");
							}catch (Exception e) {
								log.error("====服务号分组群发 error==="+e.getMessage());
							}
							//更新群发状态
							WccMassMessage msg=WccMassMessage.findWccMassMessage(Long.valueOf(key));
							msg.setState(5);
							msg.merge();
						}
				    }else{//openids群发
				    	List<String> openIds = new ArrayList<String>();//openidList
						//群发
						MassConditionEntity masscoindition=new MassConditionEntity();
						masscoindition.setPlatformer(wcc);
						masscoindition.setGender(CommonUtils.getStrToLong(gender));
						masscoindition.setGroups(group);
						masscoindition.setProvince(province);
						masscoindition.setCity(city);
						masscoindition.setFromType(fromType);
						masscoindition.setMassType(messType);
						masscoindition.setPubOperator(CommonUtils.getCurrentPubOperator(request));
						//获取OpenIDs
						List<WccFriend> friends=WccFriend.getMassFriend(masscoindition);
						Set<WccFriend> setfriends = new HashSet<WccFriend>();
						setfriends.addAll(friends);
						if(friends.size()>0){
//							List<WccFriend> wfriends = new ArrayList<WccFriend>();
							for (WccFriend friend : friends) {//粉丝

										if(null != friend.getOpenId() && !"".equals(friend.getOpenId())){
											openIds.add(friend.getOpenId());
//											wfriends.add(friend);
										
									}
							}
					}
					//用户列表群发	
					if(null!=openIds&&openIds.size()==0&&friends.size()==0){
							str= "success:false,msg:\"所选条件下无数据\"";
					}else {
						//将群发数据保存到redis
						MassSendEntity massSendEntity=new MassSendEntity();
						massSendEntity.setContent(content);
						massSendEntity.setPub(pub);
						massSendEntity.setCtxPatch(ctxPath);
						massSendEntity.setList(openIds);
						massSendEntity.setWccplatforuser(wcc);	
						massSendEntity.setFriends(setfriends);
						massSendEntity.setGender(CommonUtils.getStrToInt(gender));
						massSendEntity.setState(1);
						massSendEntity.setRequest(request);
						key=MassSendEntity.getMassKey(messType, material, inputStream, massSendEntity);
						//发送群发消息
						String url=massUrl+"api/sendMassMessage";
						Map <String, String> params = new HashMap<String, String>();
						params.put("key", key);
						params.put("flag", "1");
						params.put("appId", wcc.getAppId());
						params.put("appSecret", wcc.getAppSecret());
						try{
							log.info("====服务号openids群发====本次发送用户数量为："+openIds.size());
							HttpClientUtil.doPost(url, params, "utf-8");
						}catch (Exception e) { 
							log.error("====服务号openids群发 error===="+e.getMessage());
						}
						//更新群发状态
						WccMassMessage msg=WccMassMessage.findWccMassMessage(Long.valueOf(key));
						msg.setState(5);
						msg.merge();
					}
				  }
					//}
		return str;
		
	}

	
	/**
	 * 群发需要有发布操作，
	 * @param uiModel
	 * @param platformUserId
	 * @param gender
	 * @param group
	 * @param province
	 * @param city
	 * @param messNum
	 * @param sid
	 * @param messType
	 * @param content
	 * @param num
	 * @param massTime
	 * @param phoneStr
	 * @param request
	 * @param response
	 * @return
	 * @throws WxErrorException
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "sendMessage1", produces = "text/html;charset=utf-8")
	public String sendMessage1(Model uiModel, 
			@RequestParam(value = "platformUser",required = false) String mplat,
			@RequestParam(value = "group",required = false) String group,
			@RequestParam(value = "sid",required = false) String sid,
			@RequestParam(value = "messType",required = false) Long messType,
			@RequestParam(value = "content",required = false) String content,
			HttpServletRequest request,
			HttpServletResponse response) throws WxErrorException, IOException {
		String massUrl = "http://127.0.0.1:8080/tailong_wccmass/";
		//公众平台
		WccPlatformUser wcc =WccPlatformUser.findWccPlatformUser(CommonUtils.getStrToLong(mplat));
		String str = "susscess:true,msg:\"已发送\"";
					PubOperator pub = CommonUtils.getCurrentPubOperator(request);
					WccMaterials material = new WccMaterials();
					String ctxPath = request.getSession().getServletContext().getRealPath("");
					File file = null;
					FileInputStream inputStream = null; 
					if(messType!=1){//获取素材
						material = WccMaterials.findWccMaterials(CommonUtils.getStrToLong(sid));
						try {
							file = new File(ctxPath+material.getThumbnailUrl().substring(4));
							inputStream = new FileInputStream(file);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					String key="";
					List<String> massGroups = new ArrayList<String>();//群发分组
					//分组群发	
				    if(!"null".equals(group)){
						String[] groups = group.split(",");
						WccGroup wgroup = new WccGroup();
						for (String gstr : groups) {
							wgroup = WccGroup.findWccGroup(Long.parseLong(gstr));
							massGroups.add(Long.toString(wgroup.getGroupId()));
						}
						
						//分组群发	
						if(null!=massGroups&&massGroups.size()==0){
								str= "success:false,msg:\"所选条件下无数据\"";
						}else {	
							MassSendEntity massSendEntity=new MassSendEntity();
							massSendEntity.setPub(pub);
							massSendEntity.setCtxPatch(ctxPath);
							massSendEntity.setList(massGroups);
							massSendEntity.setWccplatforuser(wcc);	
							massSendEntity.setState(1);
							massSendEntity.setRequest(request);
							key=MassSendEntity.getMassKey(messType, material, inputStream, massSendEntity);
							//发送群发消息
							String url=massUrl+"api/sendMassMessage";
							Map <String, String> params = new HashMap<String, String>();
							params.put("key", key);
							params.put("flag", "2");
							params.put("appId", wcc.getAppId());
							params.put("appSecret", wcc.getAppSecret());
							try{
								log.info("====订阅号分组群发====");
								HttpClientUtil.doPost(url, params, "utf-8");
							}catch (Exception e) {
								log.error("====订阅号分组群发   error==="+e.getMessage());
							}
							//更新群发状态
							WccMassMessage msg=WccMassMessage.findWccMassMessage(Long.valueOf(key));
							msg.setState(5);
							msg.merge();
						}
				    }else{
				    	//openids群发
				    	List<String> openIds = new ArrayList<String>();//openidList
						//群发
						MassConditionEntity masscoindition=new MassConditionEntity();
						masscoindition.setPlatformer(wcc);
//						masscoindition.setGender(CommonUtils.getStrToLong(gender));
						masscoindition.setGroups(group);
//						masscoindition.setProvince(province);
//						masscoindition.setCity(city);
//						masscoindition.setFromType(fromType);
						masscoindition.setMassType(messType);
						masscoindition.setPubOperator(CommonUtils.getCurrentPubOperator(request));
						//获取OpenIDs
						List<WccFriend> friends=WccFriend.getMassFriend(masscoindition);
						Set<WccFriend> setfriends = new HashSet<WccFriend>();
						setfriends.addAll(friends);
						if(friends.size()>0){
							for (WccFriend friend : friends) {//粉丝
								openIds.add(friend.getOpenId());
							}
						}
					//用户列表群发	
					if(null!=openIds&&openIds.size()==0&&friends.size()==0){
							str= "success:false,msg:\"所选条件下无数据\"";
					}else {
						//将群发数据保存到redis
						MassSendEntity massSendEntity=new MassSendEntity();
						massSendEntity.setContent(content);
						massSendEntity.setPub(pub);
						massSendEntity.setCtxPatch(ctxPath);
						massSendEntity.setList(openIds);
						massSendEntity.setWccplatforuser(wcc);	
						massSendEntity.setFriends(setfriends);
						massSendEntity.setState(1);
						massSendEntity.setRequest(request);
						key=MassSendEntity.getMassKey(messType, material, inputStream, massSendEntity);
						//发送群发消息
						String url=massUrl+"api/sendMassMessage";
						Map <String, String> params = new HashMap<String, String>();
						params.put("key", key);
						params.put("flag", "1");
						params.put("appId", wcc.getAppId());
						params.put("appSecret", wcc.getAppSecret());
						try{
							log.info("====订阅号openids群发====本次发送用户数量为："+openIds.size());
							HttpClientUtil.doPost(url, params, "utf-8");
						}catch (Exception e) { 
							log.error("====订阅号openids群发 error===="+e.getMessage());
						}
						//更新群发状态
						WccMassMessage msg=WccMassMessage.findWccMassMessage(Long.valueOf(key));
						msg.setState(5);
						msg.merge();
					}
				  
				    }
		return str;
		
	}
	
}

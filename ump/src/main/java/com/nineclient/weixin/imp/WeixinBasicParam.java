package com.nineclient.weixin.imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import com.nineclient.constant.WccAutokbsIsReview;
import com.nineclient.constant.WccFriendFormType;
import com.nineclient.constant.WccFriendSex;
import com.nineclient.dto.WccUccError;
import com.nineclient.dto.WccUccOut;
import com.nineclient.model.WccAutokbs;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccGroup;
import com.nineclient.model.WccMaterials;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.model.WccWelcomkbs;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.Global;
import com.nineclient.utils.RedisCommonHelper;

public class WeixinBasicParam {
protected HttpServletRequest request;
protected HttpServletResponse response;
protected WccPlatformUser wccPlatformUser;
protected WxMpXmlMessage inMessage ;
protected WxMpService wxMpService;
protected WxMpService wxMpServices;
// 百度坐标点之间的距离
static double DEF_PI = 3.14159265359; // PI
static double DEF_2PI = 6.28318530712; // 2*PI
static double DEF_PI180 = 0.01745329252; // PI/180.0
static double DEF_R = 6370693.5; // radius of earth
public static final double EARTH_RADIUS = 6378137;// 地球半径
public static Map<String, Object> logMap = RedisCommonHelper.getInstance().getNormalMap("logMap");// 用户自动回复日志
public static Map<String,String> openIdMap = new HashMap<String, String>();// 对接UCC key：openId, value：粉丝最后交互时间
public static Map<String,String> timeMap = new HashMap<String, String>();// key：openId, value：(1：多客服, 2：UCC)
public static Map<String,WxMpService> wxMpServiceMap = new HashMap<String, WxMpService>();// key：openId, value：WxMpService
public static String uccToken = null;// 对接UCC的token
public static Map<String,String> uccInfo = new HashMap<String, String>();// key：openId, value：UCC返回的信息
public static WccUccError uccError = new WccUccError();
public static Map<String,String> chatIdMap = new HashMap<String, String>();// key：openId, value：chatId(本次会话ID)
public static Map<String,WccUccOut> wccUccOut = new HashMap<String,WccUccOut>();// key：openId, value：接入返回信息

public HttpServletRequest getRequest() {
	return request;
}
public void setRequest(HttpServletRequest request) {
	this.request = request;
}
public HttpServletResponse getResponse() {
	return response;
}
public void setResponse(HttpServletResponse response) {
	this.response = response;
}
public WccPlatformUser getWccPlatformUser() {
	return wccPlatformUser;
}
public void setWccPlatformUser(WccPlatformUser wccPlatformUser) {
	this.wccPlatformUser = wccPlatformUser;
}
public WxMpXmlMessage getInMessage() {
	return inMessage;
}
public void setInMessage(WxMpXmlMessage inMessage) {
	this.inMessage = inMessage;
}
public WxMpService getWxMpService() {
	return wxMpService;
}
public void setWxMpService(WxMpService wxMpService) {
	this.wxMpService = wxMpService;
}
public WxMpService getWxMpServices() {
	return wxMpServices;
}
public void setWxMpServices(WxMpService wxMpServices) {
	this.wxMpServices = wxMpServices;
}
public static double rad(double d) {
	return d * Math.PI / 180.0;
}
public static double GetShortDistance(double lon1, double lat1,
		double lon2, double lat2) {
	double ew1, ns1, ew2, ns2;
	double dx, dy, dew;
	double distance;
	// 角度转换为弧度
	ew1 = lon1 * DEF_PI180;
	ns1 = lat1 * DEF_PI180;
	ew2 = lon2 * DEF_PI180;
	ns2 = lat2 * DEF_PI180;
	// 经度差
	dew = ew1 - ew2;
	// 若跨东经和西经180 度，进行调整
	if (dew > DEF_PI)
		dew = DEF_2PI - dew;
	else if (dew < -DEF_PI)
		dew = DEF_2PI + dew;
	dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
	dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
	// 勾股定理求斜边长
	distance = Math.sqrt(dx * dx + dy * dy);
	return distance;
}
public static double GetDistance(double lng1, double lat1, double lng2,
		double lat2) {
	double radLat1 = rad(lat1);
	double radLat2 = rad(lat2);
	double a = radLat1 - radLat2;
	double b = rad(lng1) - rad(lng2);
	double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
			+ Math.cos(radLat1) * Math.cos(radLat2)
			* Math.pow(Math.sin(b / 2), 2)));
	s = s * EARTH_RADIUS;
	s = Math.round(s * 10000) / 10000;
	return s;
}
/**
 * 
 * 上传 获取MaterialId
 * 
 * @param kbs
 * @param request
 * @param wxMpService
 * @param type
 */
public void uploadKbsMadie(WccWelcomkbs kbs, HttpServletRequest request,
		WxMpService wxMpService, String type) {
	// 判断MaterialId是否存在
	if (kbs.getMaterials().getMaterialId() == null
			|| kbs.getMaterials().getMaterialId().equals("")) {
		WxMediaUploadResult res = null;
		// 上传图片 获取MaterialId
		String ctxPath = request.getSession().getServletContext()
				.getRealPath("");
		File file = new File(ctxPath
				+ kbs.getMaterials().getThumbnailUrl().substring(4));
		try {
			res = wxMpService.mediaUpload(type, file);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		// 保存MaterialId
		WccMaterials material = kbs.getMaterials();
		material.setUploadTime(new Date());
		material.setMaterialId(res.getMediaId());
		material.merge();
	} else if (kbs.getMaterials().getUploadTime() != null) {
		// 判断上传时间距今是否超过3天
		if (new Date().getTime()
				- kbs.getMaterials().getUploadTime().getTime() >= 3 * 24 * 3600 * 1000) {
			WxMediaUploadResult res = null;
			// 上传图片 获取MaterialId
			String ctxPath = request.getSession().getServletContext()
					.getRealPath("");
			File file = new File(ctxPath
					+ kbs.getMaterials().getThumbnailUrl().substring(4));
			try {
				res = wxMpService.mediaUpload(type, file);
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			// 保存MaterialId
			WccMaterials material = kbs.getMaterials();
			material.setUploadTime(new Date());
			material.setMaterialId(res.getMediaId());
			material.merge();
		}
	}
}

/**
 * 
 * 上传 获取MaterialId
 * 
 * @param kbs
 * @param request
 * @param wxMpService
 * @param type
 */
public void uploadAutoMadie(WccAutokbs kbs, HttpServletRequest request,
		WxMpService wxMpService, String type) {
	// 判断MaterialId是否存在
	if (kbs.getMaterials().getMaterialId() == null
			|| kbs.getMaterials().getMaterialId().equals("")) {
		WxMediaUploadResult res = null;
		// 上传图片 获取MaterialId
		String ctxPath = request.getSession().getServletContext()
				.getRealPath("");
		File file = new File(ctxPath
				+ kbs.getMaterials().getThumbnailUrl().substring(4));
		try {
			res = wxMpService.mediaUpload(type, file);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		// 保存MaterialId
		WccMaterials material = kbs.getMaterials();
		material.setUploadTime(new Date());
		material.setMaterialId(res.getMediaId());
		material.merge();
	} else if (kbs.getMaterials().getUploadTime() != null) {
		// 判断上传时间距今是否超过3天
		if (new Date().getTime()
				- kbs.getMaterials().getUploadTime().getTime() >= 3 * 24 * 3600 * 1000) {
			WxMediaUploadResult res = null;
			// 上传图片 获取MaterialId
			String ctxPath = request.getSession().getServletContext()
					.getRealPath("");
			File file = new File(ctxPath
					+ kbs.getMaterials().getThumbnailUrl().substring(4));
			try {
				res = wxMpService.mediaUpload(type, file);
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			// 保存MaterialId
			WccMaterials material = kbs.getMaterials();
			material.setUploadTime(new Date());
			material.setMaterialId(res.getMediaId());
			material.merge();
		}
	}
	
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
/**
 * 回复相关问题
 * 
 * @param count
 * @param inMessage
 * @param response
 * @param auto
 * @param issues
 * @throws IOException
 */
// 增加一个参数WccFriend 对象
protected String outMessageProblem(int count, WxMpXmlMessage inMessage,
		HttpServletResponse response, WccAutokbs auto,
		WccRecordMsg recordMsg, String[] issues) throws IOException {
	// TODO Auto-generated catch block 返回相关问题
	int i = count;
	String q = "相关问题：\n";
	for (String issu : issues) {
		WccAutokbs au = WccAutokbs.findWccAutokbs(Long.parseLong(issu));
		if (au.getActive()
				&& au.getIsReview().equals(WccAutokbsIsReview.审核通过)) {
			q += "[" + i + "]" + au.getTitle() + "\n";
			i++;
		}
	}
	if (i != count) {
		recordMsg.setToUserInsertTime(new Date());
		WccRecordMsg record = new WccRecordMsg();
		switch (auto.getReplyType().ordinal()) {
		case 0:// 文字
			String totalMsg = auto.getContent() + "\n" + q;
			recordMsg.setToUserRecord(totalMsg);
			break;
		case 1:// 图片
			recordMsg.setMsgFrom(1);
			recordMsg
					.setToUserRecord(auto.getMaterials().getThumbnailUrl());
			record.setToUserRecord(q);
			break;
		case 2:// 语音
			recordMsg.setMsgFrom(2);
			recordMsg
					.setToUserRecord(auto.getMaterials().getThumbnailUrl());
			record.setToUserRecord(q);
			break;
		case 3:// 视频
			recordMsg.setMsgFrom(3);
			recordMsg
					.setToUserRecord(auto.getMaterials().getThumbnailUrl());
			record.setToUserRecord(q);
			break;
		case 4:// 图文
			recordMsg.setMsgFrom(4);
			recordMsg.setToUserRecord(auto.getMaterials().getId()
					.toString());
			record.setToUserRecord(q);
			break;

		}
		recordMsg.persist();
		record.setFriendGroup(recordMsg.getFriendGroup());
		record.setInsertTime(new Date());
		record.setMsgFrom(0);
		record.setNickName(recordMsg.getNickName());
		record.setSex(recordMsg.getSex());
		record.setOpenId(recordMsg.getOpenId());
		record.setPlatFormAccount(recordMsg.getPlatFormAccount());
		record.setPlatFormId(recordMsg.getPlatFormId());
		record.setProvince(recordMsg.getProvince());
		record.setRecordFriendId(recordMsg.getRecordFriendId());
		record.setToUserInsertTime(new Date());
		record.setToUserRecord(q);
		record.setCompanyId(recordMsg.getCompanyId());
		record.persist();
		return q;
	} else {
		recordMsg.setToUserInsertTime(new Date());
		switch (auto.getReplyType().ordinal()) {
		case 0:// 文字
			String totalMsg = auto.getContent() + "\n" + q;
			recordMsg.setToUserRecord(totalMsg);
			break;
		case 1:// 图片
			recordMsg.setMsgFrom(1);
			recordMsg
					.setToUserRecord(auto.getMaterials().getThumbnailUrl());
			break;
		case 2:// 语音
			recordMsg.setMsgFrom(2);
			recordMsg
					.setToUserRecord(auto.getMaterials().getThumbnailUrl());
			break;
		case 3:// 视频
			recordMsg.setMsgFrom(3);
			recordMsg
					.setToUserRecord(auto.getMaterials().getThumbnailUrl());
			break;
		case 4:// 图文
			recordMsg.setMsgFrom(4);
			recordMsg.setToUserRecord(auto.getMaterials().getId()
					.toString());
			break;
		}
		recordMsg.persist();
		return "";
	}
}
/**
 * 复制单个文件
 * 
 * @param oldPath
 *            String 原文件路径 如：c:/fqf.txt
 * @param newPath
 *            String 复制后路径 如：f:/fqf.txt
 * @return boolean
 */
public static void copyFile(String oldPath, String newPath) {
	try {
		int bytesum = 0;
		int byteread = 0;
		File oldfile = new File(oldPath);
		if (oldfile.exists()) { // 文件存在时
			InputStream inStream = new FileInputStream(oldPath); // 读入原文件
			FileOutputStream fs = new FileOutputStream(newPath);
			byte[] buffer = new byte[1444];
			int length;
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread; // 字节数 文件大小
				System.out.println(bytesum);
				fs.write(buffer, 0, byteread);
			}
			inStream.close();
		}
	} catch (Exception e) {
		System.out.println("复制单个文件操作出错");
		e.printStackTrace();

	}
}
}

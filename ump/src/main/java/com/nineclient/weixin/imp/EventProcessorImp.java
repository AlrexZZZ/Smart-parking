package com.nineclient.weixin.imp;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;

import com.nineclient.cherry.wcc.model.WccQrCode;
import com.nineclient.constant.WccFriendFormType;
import com.nineclient.constant.WccWelcomkbsReplyType;
import com.nineclient.constant.WccWelcomkbsType;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccMassMessage;
import com.nineclient.model.WccMaterials;
import com.nineclient.model.WccMenu;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccWelcomkbs;
import com.nineclient.utils.Global;
import com.nineclient.weixin.EventProcessor;
import com.nineclient.weixin.MsgTypeProcessor;

public class EventProcessorImp extends WeixinBasicParam  implements EventProcessor {
	String event = "";
	String key = "";// 二维码的场景值ID
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
 public EventProcessorImp(String event,String key,WeixinBasicParam weixinBasicParam) {
		this.event=event;
		this.key=key;
		super.setInMessage(weixinBasicParam.getInMessage());
		super.setRequest(weixinBasicParam.getRequest());
		super.setResponse(weixinBasicParam.getResponse());
		super.setWccPlatformUser(weixinBasicParam.getWccPlatformUser());
		super.setWxMpService(weixinBasicParam.getWxMpService());
		super.setWxMpServices(weixinBasicParam.getWxMpServices());
		super.setInMessage(weixinBasicParam.getInMessage());
	}

	@Override
	public void subScribeEvent() throws IOException {		// 关注
					subscribe(inMessage, wccPlatformUser);
					if (null != key && !"".equals(key)) {// 未关注扫描二维码
						//查询二维码
						WccQrCode wccqrcode=WccQrCode.findWccQrCodeByCre(key.substring(8, key.length()),wccPlatformUser);
						 if(wccqrcode.getCodeType().equals("活动")){
								String openId = inMessage.getFromUserName();
								WccFriend wccFriend = WccFriend
										.findWccFriendByOpenId(openId);
								wccFriend.setFromType(WccFriendFormType.活动二维码);
								wccFriend.setWccqrcode(wccqrcode);
								wccFriend.merge();
							}
					}
					String platformid = inMessage.getToUserName();
					List<WccWelcomkbs> kbsList = WccWelcomkbs
							.findWccWelcomkbsesByPlatformUserAndTypeAndActive(
									wccPlatformUser.getId(),
									WccWelcomkbsType.WELCOME.ordinal());
					if (kbsList.size() > 0) {
						WccWelcomkbs kbs = kbsList.get(0);
						if (kbs.getReplyType().equals(
								WccWelcomkbsReplyType.文本)) {
							WxMpXmlOutMessage outMessage = WxMpXmlOutMessage
									.TEXT().content(kbs.getContent())
									.fromUser(inMessage.getToUserName())
									.toUser(inMessage.getFromUserName())
									.build();
							response.getWriter().write(outMessage.toXml());
						}
						if (kbs.getReplyType().equals(
								WccWelcomkbsReplyType.图片)) {

							uploadKbsMadie(kbs, request, wxMpService,
									"image");

							WxMpXmlOutMessage outMessage = WxMpXmlOutMessage
									.IMAGE()
									.mediaId(
											kbs.getMaterials()
													.getMaterialId())
									.fromUser(inMessage.getToUserName())
									.toUser(inMessage.getFromUserName())
									.build();
							response.getWriter().write(outMessage.toXml());
						}
						if (kbs.getReplyType().equals(
								WccWelcomkbsReplyType.语音)) {

							uploadKbsMadie(kbs, request, wxMpService,
									"voice");

							WxMpXmlOutMessage outMessage = WxMpXmlOutMessage
									.VOICE()
									.mediaId(
											kbs.getMaterials()
													.getMaterialId())
									.fromUser(inMessage.getToUserName())
									.toUser(inMessage.getFromUserName())
									.build();
							response.getWriter().write(outMessage.toXml());
						}
						if (kbs.getReplyType().equals(
								WccWelcomkbsReplyType.视频)) {

							uploadKbsMadie(kbs, request, wxMpService,
									"video");

							WxMpXmlOutMessage outMessage = WxMpXmlOutMessage
									.VIDEO()
									.mediaId(
											kbs.getMaterials()
													.getMaterialId())
									.fromUser(inMessage.getToUserName())
									.toUser(inMessage.getFromUserName())
									.description(
											kbs.getMaterials()
													.getDescription())
									.build();
							response.getWriter().write(outMessage.toXml());

						}
						if (kbs.getReplyType().equals(
								WccWelcomkbsReplyType.图文)) {
							String openId = inMessage.getFromUserName();
							WccFriend wccFriend = WccFriend
									.findWccFriendByOpenId(openId);
							WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
							item.setDescription(kbs.getMaterials()
									.getDescription());
							item.setPicUrl(Global.Url
									+ kbs.getMaterials().getThumbnailUrl());
							item.setTitle(kbs.getMaterials().getTitle());
							item.setUrl(Global.Url
									+ "/ump/wccmaterialses/showdetail/"
									+ kbs.getMaterials().getId()+"?friendId="+wccFriend.getId());
							WxMpXmlOutNewsMessage m = WxMpXmlOutMessage
									.NEWS()
									.fromUser(inMessage.getToUserName())
									.toUser(inMessage.getFromUserName())
									.addArticle(item).build();
							List<WccMaterials> list = kbs.getMaterials()
									.getChildren();
							if (list.size() > 0) {
								for (WccMaterials mater : list) {
									WxMpXmlOutNewsMessage.Item childItem = new WxMpXmlOutNewsMessage.Item();
									childItem.setDescription(mater
											.getDescription());
									childItem.setPicUrl(Global.Url
											+ mater.getThumbnailUrl());
									childItem.setTitle(mater.getTitle());
									childItem
											.setUrl(Global.Url
													+ "/ump/wccmaterialses/showdetail/"
													+ mater.getId()+"?friendId="+wccFriend.getId());
									m.addArticle(childItem);
								}
							}
							response.getWriter().write(m.toXml());
						}
					}
					return;
				

	}

	@Override
	public void unsbuscribeEvent() throws IOException {
		String openId = inMessage.getFromUserName();
		List<WccFriend> wfriend = WccFriend.findWccFriendsByOpenId(openId);
		if (null != wfriend && wfriend.size() > 0) {
			WccFriend friend = wfriend.get(0);
			friend.setIsDeleted(false);
			friend.merge();
			return;
		}

	}

	@Override
	public void scanEvent() throws IOException {
		// 已关注扫描二维码
				//	WccQrcode wccQrcode = WccQrcode.findWccQrcodeByScenId(Integer.parseInt(key));
					//查询二维码
					WccQrCode wccqrcode=WccQrCode.findWccQrCodeByCre(key.substring(8, key.length()),wccPlatformUser);
					 if(wccqrcode.getCodeType().equals("活动")){
							String openId = inMessage.getFromUserName();
							WccFriend wccFriend = WccFriend
									.findWccFriendByOpenId(openId);
							wccFriend.setFromType(WccFriendFormType.活动二维码);
							wccFriend.setWccqrcode(wccqrcode);
							wccFriend.merge();
						}
					return;

	}

	@Override
	public void clickEvent() throws IOException{
		// 菜单点击事件
					WccMenu wccMenu = WccMenu.findWccMenuByKeyAndPlatForm(
							key, wccPlatformUser.getId());
					if (wccMenu.getContentSelect() == 1) {// 文本
						String content = wccMenu.getContent();
						WxMpXmlOutTextMessage m = WxMpXmlOutTextMessage
								.TEXT().content(content)
								.fromUser(inMessage.getToUserName())
								.toUser(inMessage.getFromUserName())
								.build();
						response.getWriter().write(m.toXml());
					} else if (wccMenu.getContentSelect() == 2) {// 图片

						// 判断MaterialId是否存在
						if (wccMenu.getMaterials().getMaterialId() == null
								|| wccMenu.getMaterials().getMaterialId()
										.equals("")) {
							WxMediaUploadResult res = null;
							// 上传图片 获取MaterialId
							String ctxPath = request.getSession()
									.getServletContext().getRealPath("");
							File file = new File(ctxPath
									+ wccMenu.getMaterials()
											.getThumbnailUrl().substring(4));
							try {
								res = wxMpService
										.mediaUpload("image", file);
							} catch (WxErrorException e) {
								e.printStackTrace();
							}
							// 保存MaterialId
							WccMaterials material = wccMenu.getMaterials();
							material.setUploadTime(new Date());
							material.setMaterialId(res.getMediaId());
							material.merge();
						} else if (wccMenu.getMaterials().getUploadTime() != null) {
							// 判断上传时间距今是否超过3天
							if (new Date().getTime()
									- wccMenu.getMaterials()
											.getUploadTime().getTime() >= 3 * 24 * 3600 * 1000) {
								WxMediaUploadResult res = null;
								// 上传图片 获取MaterialId
								String ctxPath = request.getSession()
										.getServletContext()
										.getRealPath("");
								File file = new File(ctxPath
										+ wccMenu.getMaterials()
												.getThumbnailUrl()
												.substring(4));
								try {
									res = wxMpService.mediaUpload("image",
											file);
								} catch (WxErrorException e) {
									e.printStackTrace();
								}
								// 保存MaterialId
								WccMaterials material = wccMenu
										.getMaterials();
								material.setUploadTime(new Date());
								material.setMaterialId(res.getMediaId());
								material.merge();
							}
						}
						if (wccMenu.getMaterials().getMaterialId() != null) {
							try {
								wxMpService
										.customMessageSend(WxMpCustomMessage
												.IMAGE()
												.toUser(inMessage
														.getFromUserName())
												.mediaId(
														wccMenu.getMaterials()
																.getMaterialId())
												.build());
							} catch (WxErrorException e) {
								e.printStackTrace();
							}
						}
						return;
					} else if (wccMenu.getContentSelect() == 3) {// 图文
						String openId = inMessage.getFromUserName();
						WccFriend wccFriend = WccFriend
								.findWccFriendByOpenId(openId);
						me.chanjar.weixin.mp.bean.custombuilder.NewsBuilder builder = WxMpCustomMessage
								.NEWS();
						builder.toUser(inMessage.getFromUserName());
						WxMpCustomMessage.WxArticle article = new WxMpCustomMessage.WxArticle();
						article.setDescription(wccMenu.getMaterials()
								.getDescription());
						article.setPicUrl(Global.Url
								+ wccMenu.getMaterials().getThumbnailUrl());
						article.setTitle(wccMenu.getMaterials().getTitle());
						String urlStr = wccMenu.getMaterials().getRemakeUrl();
						if(null != urlStr && !"".equals(urlStr)){
							if(urlStr.contains("?gzjh=")){
								article.setUrl(urlStr+"&friendId="+wccFriend.getId()+"&platId="+wccPlatformUser.getId());
							}else{
								article.setUrl(Global.Url
										+ "/ump/wccmaterialses/showdetail/"
										+ wccMenu.getMaterials().getId()+"?friendId="+wccFriend.getId()+"&platId="+wccPlatformUser.getId());
							}
							
						}
				
						builder.addArticle(article);
						List<WccMaterials> list = wccMenu.getMaterials()
								.getChildren();
						if (list.size() > 0) {
							for (WccMaterials mater : list) {
								WxMpCustomMessage.WxArticle childArticle = new WxMpCustomMessage.WxArticle();
								childArticle.setDescription(mater
										.getDescription());
								childArticle.setPicUrl(Global.Url
										+ mater.getThumbnailUrl());
								childArticle.setTitle(mater.getTitle());
								childArticle.setUrl(Global.Url
										+ "/ump/wccmaterialses/showdetail/"
										+ mater.getId()+"?friendId="+wccFriend.getId());
								builder.addArticle(childArticle);
							}
						}
						WxMpCustomMessage message = builder.build();
						try {
							wxMpService.customMessageSend(message);
						} catch (WxErrorException e) {
							e.printStackTrace();
						}
						return;
					} else if (wccMenu.getContentSelect() == 4) {// 语音
						// 判断MaterialId是否存在
						if (wccMenu.getMaterials().getMaterialId() == null
								|| wccMenu.getMaterials().getMaterialId()
										.equals("")) {
							WxMediaUploadResult res = null;
							// 上传图片 获取MaterialId
							String ctxPath = request.getSession()
									.getServletContext().getRealPath("");
							File file = new File(ctxPath
									+ wccMenu.getMaterials()
											.getThumbnailUrl().substring(4));
							try {
								res = wxMpService
										.mediaUpload("voice", file);
							} catch (WxErrorException e) {
								e.printStackTrace();
							}
							// 保存MaterialId
							WccMaterials material = wccMenu.getMaterials();
							material.setUploadTime(new Date());
							material.setMaterialId(res.getMediaId());
							material.merge();
						} else if (wccMenu.getMaterials().getUploadTime() != null) {
							// 判断上传时间距今是否超过3天
							if (new Date().getTime()
									- wccMenu.getMaterials()
											.getUploadTime().getTime() >= 3 * 24 * 3600 * 1000) {
								WxMediaUploadResult res = null;
								// 上传图片 获取MaterialId
								String ctxPath = request.getSession()
										.getServletContext()
										.getRealPath("");
								File file = new File(ctxPath
										+ wccMenu.getMaterials()
												.getThumbnailUrl()
												.substring(4));
								try {
									res = wxMpService.mediaUpload("voice",
											file);
								} catch (WxErrorException e) {
									e.printStackTrace();
								}
								// 保存MaterialId
								WccMaterials material = wccMenu
										.getMaterials();
								material.setUploadTime(new Date());
								material.setMaterialId(res.getMediaId());
								material.merge();
							}
						}
						try {
							wxMpService.customMessageSend(WxMpCustomMessage
									.VOICE()
									.toUser(inMessage.getFromUserName())
									.mediaId(
											wccMenu.getMaterials()
													.getMaterialId())
									.build());
						} catch (WxErrorException e) {
							e.printStackTrace();
						}
					} else if (wccMenu.getContentSelect() == 5) {// 视频
						// 判断MaterialId是否存在
						if (wccMenu.getMaterials().getMaterialId() == null
								|| wccMenu.getMaterials().getMaterialId()
										.equals("")) {
							WxMediaUploadResult res = null;
							// 上传图片 获取MaterialId
							String ctxPath = request.getSession()
									.getServletContext().getRealPath("");
							File file = new File(ctxPath
									+ wccMenu.getMaterials()
											.getThumbnailUrl().substring(4));
							try {
								res = wxMpService
										.mediaUpload("video", file);
							} catch (WxErrorException e) {
								e.printStackTrace();
							}
							// 保存MaterialId
							WccMaterials material = wccMenu.getMaterials();
							material.setUploadTime(new Date());
							material.setMaterialId(res.getMediaId());
							material.merge();
						} else if (wccMenu.getMaterials().getUploadTime() != null) {
							// 判断上传时间距今是否超过3天
							if (new Date().getTime()
									- wccMenu.getMaterials()
											.getUploadTime().getTime() >= 3 * 24 * 3600 * 1000) {
								WxMediaUploadResult res = null;
								// 上传图片 获取MaterialId
								String ctxPath = request.getSession()
										.getServletContext()
										.getRealPath("");
								File file = new File(ctxPath
										+ wccMenu.getMaterials()
												.getThumbnailUrl()
												.substring(4));
								try {
									res = wxMpService.mediaUpload("video",
											file);
								} catch (WxErrorException e) {
									e.printStackTrace();
								}
								// 保存MaterialId
								WccMaterials material = wccMenu
										.getMaterials();
								material.setUploadTime(new Date());
								material.setMaterialId(res.getMediaId());
								material.merge();
							}
						}
						try {
							wxMpService.customMessageSend(WxMpCustomMessage
									.VIDEO()
									.toUser(inMessage.getFromUserName())
									.mediaId(
											wccMenu.getMaterials()
													.getMaterialId())
									.description(
											wccMenu.getMaterials()
													.getDescription())
									.build());
						} catch (WxErrorException e) {
							e.printStackTrace();
						}
						return;
					}
				

	}

	@Override
	public void viewEvent() throws IOException{
		// 菜单URL跳转
					key = Global.Url
							+ "/ump/wcclotteryactivitys/showActivity?id=3";// url跳转的url
					String openId = inMessage.getFromUserName();
					WccFriend friend = WccFriend
							.findWccFriendByOpenId(openId);
					StringBuffer surl = new StringBuffer();
					surl.append(key);
					if (key.contains("?")) {
						surl.append("&friendId=" + friend.getId());
					} else {
						surl.append("?friendId=" + friend.getId());
					}
					response.sendRedirect(surl.toString());
				

	}

	@Override
	public void MassSendEvent() throws IOException{
		// 群发消息结束返回事件
					WccMassMessage massMsg = WccMassMessage.findWccMassMessageByMsgId(""+inMessage.getMsgId());
					if(null != massMsg){
						massMsg.setSendCount(inMessage.getSentCount());
						massMsg.setTotleCount(inMessage.getTotalCount());
						massMsg.setFilterCount(inMessage.getFilterCount());
						massMsg.setErrorCount(inMessage.getErrorCount());
						if(inMessage.getStatus().equals("send success")){
							massMsg.setState(5);//提交成功
						}else if(inMessage.getStatus().equals("send fail")){
							massMsg.setState(6);//提交失败
						}else if(inMessage.getStatus().contains("err")){
							String err = inMessage.getStatus();
							massMsg.setState(6);//提交失败
							if(err.contains("10001")){
								massMsg.setRemark("涉嫌广告");
							}
							if(err.contains("20001")){
								massMsg.setRemark("涉嫌政治");
							}
							if(err.contains("20004")){
								massMsg.setRemark("涉嫌社会 ");
							}
							if(err.contains("20002")){
								massMsg.setRemark("涉嫌色情");
							}
							if(err.contains("20006")){
								massMsg.setRemark("涉嫌违法犯罪");
							}
							if(err.contains("20008")){
								massMsg.setRemark("涉嫌欺诈 ");
							}
							if(err.contains("20013")){
								massMsg.setRemark("涉嫌版权");
							}
							if(err.contains("22000")){
								massMsg.setRemark("涉嫌互推(互相宣传)");
							}
							if(err.contains("21000")){
								massMsg.setRemark("涉嫌其他");
							}
						}
						massMsg.merge();
					}
				

	}

}

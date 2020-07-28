package com.nineclient.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.WxMpMassVideo;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;

import com.nineclient.model.WccFriend;
import com.nineclient.model.WccMassMessage;
import com.nineclient.model.WccMaterials;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.Global;

public class massMessageTiming extends Thread {
	private WccMassMessage massMsg;
	
	public WccMassMessage getMassMsg() {
		return massMsg;
	}

	public void setMassMsg(WccMassMessage massMsg) {
		this.massMsg = massMsg;
	}

	public void run() {
		
		System.out.println("线程定时群发=============="+massMsg.getId());
		
		
		WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
		List<String> openIds = new ArrayList<String>();
		List<WccFriend> friends = new ArrayList<WccFriend>();
		friends.addAll(massMsg.getFriends());
		for (WccFriend friend : friends) {
			openIds.add(friend.getOpenId());
		}
		massMessage.getToUsers().addAll(openIds);
		int messType = massMsg.getType();
		
		WccPlatformUser platformUser = massMsg.getPlatformUser();
		massMsg.setPlatformUser(platformUser);
		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		String appId = platformUser.getAppId();
		String appSecret = platformUser.getAppSecret();
		config.setAppId(appId);
		config.setSecret(appSecret);
		WxMpServiceImpl wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(config);
		WccMaterials material = new WccMaterials();
		String ctxPath = massMsg.getPath();
		String domain = Global.Url;
		File file = null;
		FileInputStream inputStream = null;
		if(1 != messType){
			material = massMsg.getMaterial();
			file = new File(ctxPath+material.getThumbnailUrl().substring(4));
			try {
				inputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//文本
		if(messType == 1){
			String content = massMsg.getContent();
			massMessage.setMsgType(WxConsts.MASS_MSG_TEXT);
			massMessage.setContent(content);
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
			massMsg.setMediaId(uploadMediaRes.getMediaId());
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
				massMsg.setMediaId(uploadResult.getMediaId());
			} catch (WxErrorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
		}
		//语音
		if(messType == 4){
			massMessage.setMsgType(WxConsts.MASS_MSG_VOICE);
			WxMediaUploadResult uploadMediaRes = new WxMediaUploadResult();
			try {
				uploadMediaRes = wxMpService.mediaUpload(WxConsts.MEDIA_VOICE, WxConsts.FILE_MP3, inputStream);
				massMessage.setMediaId(uploadMediaRes.getMediaId());
				massMsg.setMediaId(uploadMediaRes.getMediaId());
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
			article1.setContent(material.getContent().replace("src=\"/ump/attached/wx_image", "src=\""+Global.Url+"/ump/attached/wx_image"));
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
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					WxMpMassNews.WxMpMassNewsArticle article2 = new WxMpMassNews.WxMpMassNewsArticle();
					article2.setTitle(wmaterial.getTitle());
					article2.setContent(wmaterial.getContent().replace("src=\"/ump/attached/wx_image", "src=\""+Global.Url+"/ump/attached/wx_image"));
					article2.setDigest(wmaterial.getDescription());
					article2.setShowCoverPic(material.getUrlBoolean());
					article2.setThumbMediaId(uploadResult2.getMediaId());
					news.addArticle(article2);
				}
			}
			try {
				WxMpMassUploadResult massUploadResult = wxMpService.massNewsUpload(news);
				massMessage.setMediaId(massUploadResult.getMediaId());
				massMsg.setMediaId(massUploadResult.getMediaId());
			} catch (WxErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("openId="+openIds.size());
		System.out.println(openIds.toString());
		try {
			WxMpMassSendResult massResult = wxMpService.massOpenIdsMessageSend(massMessage);
//			WxMpMassSendResult massResult = new WxMpMassSendResult();
			System.out.println("定时群发结果========"+massResult.toString());
			if(true){//massResult返回成功 需改
				massMsg.setInsertTime(new Date());
				massMsg.setMsgId(massResult.getMsgId());
				massMsg.setState(4);
				massMsg.merge();
			}
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
	
	}
	
}
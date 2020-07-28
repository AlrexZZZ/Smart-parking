package com.nineclient.cherry.wcc.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;

import com.nineclient.entity.MassSendEntity;
import com.nineclient.model.WccMassMessage;
import com.nineclient.model.WccMaterials;
import com.nineclient.utils.Global;
import com.nineclient.utils.RedisCommonHelper;
import com.nineclient.weixin.WxFileUtil;

public class MassNewsSend extends MessageSendAbstract {
	 public MassNewsSend(MassSendEntity pmasssentity) {
		massSendEntity=pmasssentity;
	
	}
	@Override
	public String sendMessage() {
		String key="";
		if(massSendEntity!=null)
		{
			//发送的内容保存在redis中
			WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
			WxMpServiceImpl wxMpService = new WxMpServiceImpl();
			config.setAppId(massSendEntity.getWccplatforuser().getAppId());
			config.setSecret(massSendEntity.getWccplatforuser().getAppSecret());
			wxMpService.setWxMpConfigStorage(config);
//			try {
//				uploadMediaRes = wxMpService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, massSendEntity.getInputfile());
//			} catch(Exception e)
//			{
//			e.printStackTrace();	
//			}

			WxMediaUploadResult uploadResult = new WxMediaUploadResult();
			try {
				uploadResult = wxMpService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, massSendEntity.getInputfile());
			} catch (WxErrorException  e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			WxMpMassNews news = new WxMpMassNews();
			WxMpMassNews.WxMpMassNewsArticle article1 = new WxMpMassNews.WxMpMassNewsArticle();
			article1.setTitle(massSendEntity.getWccmaterial().getTitle());
		
			try {
			article1.setContent(WxFileUtil.replaceImgSrc(massSendEntity.getWccmaterial().getContent(), massSendEntity.getRequest(), wxMpService.getAccessToken()));
			
			article1.setDigest(massSendEntity.getWccmaterial().getDescription());
			article1.setShowCoverPic(massSendEntity.getWccmaterial().getUrlBoolean());
			article1.setThumbMediaId(uploadResult.getMediaId());
			article1.setContentSourceUrl(massSendEntity.getWccmaterial().getSourceUrl());
			news.addArticle(article1);//第一个图文
			if(!massSendEntity.getWccmaterial().getChildren().isEmpty()){//如果有子图文（多图文）
				List<WccMaterials> children = massSendEntity.getWccmaterial().getChildren();
				Iterator<WccMaterials> iter = children.iterator();
				while (iter.hasNext()) {
					WccMaterials wmaterial =  iter.next();
					File file = new File(massSendEntity.getCtxPatch()+wmaterial.getThumbnailUrl().substring(4));
					WxMediaUploadResult uploadResult2 = new WxMediaUploadResult();
					try {
						FileInputStream inputStream = new FileInputStream(file);
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
				
					article2.setContent(WxFileUtil.replaceImgSrc(wmaterial.getContent(), massSendEntity.getRequest(), wxMpService.getAccessToken()));
					article2.setDigest(wmaterial.getDescription());
					article2.setShowCoverPic(wmaterial.getUrlBoolean());
					article2.setThumbMediaId(uploadResult2.getMediaId());
					article2.setContentSourceUrl(wmaterial.getSourceUrl());
					news.addArticle(article2);
				}
			
			}
			} catch (WxErrorException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				WxMpMassUploadResult massUploadResult = wxMpService.massNewsUpload(news);
				WccMassMessage massMsg = new WccMassMessage();
				massMsg.setPlatformUser(massSendEntity.getWccplatforuser());
				massMsg.setGroups(massSendEntity.getGroupset());
				massMsg.setFriends(massSendEntity.getFriends());
				massMsg.setType(5);
				massMsg.setMediaId(massUploadResult.getMediaId());
				massMsg.setInsertTime(new Date());
				massMsg.setCompany(massSendEntity.getPub().getCompany());
				massMsg.setMaterial(massSendEntity.getWccmaterial());
				massMsg.setGroupStr(massSendEntity.getGroups());
				massMsg.setPubOrganization(massSendEntity.getPub().getOrganization());
				massMsg.setSex(massSendEntity.getGender());
			
		     	long newid=massMsg.merge().getId();	
			
			    key =String.valueOf(newid);
		
		
		   try {
			    RedisCommonHelper.getInstance().getList(key+Global.targetflag).addAll(massSendEntity.getList());
				Map<String, String> map=new HashMap<String, String>();
				map.put(Global.messtype, massSendEntity.getMsgType());
				map.put(Global.sendcontent, massUploadResult.getMediaId());
				RedisCommonHelper.getInstance().getMap(key+Global.contentflag).putAll(map);
				//发起发送操作
				//MassWebServiceClient.getInstance().sendRedisMessage(key);
		} catch (Exception e) {
			log.error("got exception when saves data into redis news", e);
		}
			} catch (WxErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
   return key;
	}

}

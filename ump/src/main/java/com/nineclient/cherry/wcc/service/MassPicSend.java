package com.nineclient.cherry.wcc.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;

import com.nineclient.entity.MassSendEntity;
import com.nineclient.model.WccMassMessage;
import com.nineclient.utils.Global;
import com.nineclient.utils.RedisCommonHelper;

public class MassPicSend extends MessageSendAbstract {

	 public MassPicSend(MassSendEntity pmasssentity) {
		massSendEntity=pmasssentity;
	}
	@Override
	public String sendMessage() {
		String key ="";
		//发起发送操作
				if(massSendEntity!=null)
				{
					//发送的内容保存在redis中
					WxMediaUploadResult uploadMediaRes = new WxMediaUploadResult();
					WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
					WxMpServiceImpl wxMpService = new WxMpServiceImpl();
					config.setAppId(massSendEntity.getWccplatforuser().getAppId());
					config.setSecret(massSendEntity.getWccplatforuser().getAppSecret());
					wxMpService.setWxMpConfigStorage(config);
					try {
						uploadMediaRes = wxMpService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, massSendEntity.getInputfile());
					}  catch(Exception e)
					{
					e.printStackTrace();	
					}				
					WccMassMessage massMsg = new WccMassMessage();
					massMsg.setPlatformUser(massSendEntity.getWccplatforuser());
					massMsg.setType(2);
					massMsg.setMediaId(uploadMediaRes.getMediaId());
					massMsg.setInsertTime(new Date());
					massMsg.setFriends(massSendEntity.getFriends());
					massMsg.setGroups(massSendEntity.getGroupset());
					massMsg.setCompany(massSendEntity.getPub().getCompany());
					massMsg.setMaterial(massSendEntity.getWccmaterial());
					massMsg.setGroupStr(massSendEntity.getGroups());
					massMsg.setPubOrganization(massSendEntity.getPub().getOrganization());
					massMsg.setSex(massSendEntity.getGender());
					massMsg.setState(massSendEntity.getState());	
					long newid=massMsg.merge().getId();		
				    key =String.valueOf(newid);
						try {
							RedisCommonHelper.getInstance().getList(key+Global.targetflag).addAll(massSendEntity.getList());
							Map<String, String> map=new HashMap<String, String>();
							map.put(Global.messtype, massSendEntity.getMsgType());
//							map.put(Global.sendcontent, MassSendUtils.getPic(uploadMediaRes.getMediaId()));
							map.put(Global.sendcontent, uploadMediaRes.getMediaId());
							RedisCommonHelper.getInstance().getMap(key+Global.contentflag).putAll(map);	
						} catch (Exception e) {
							log.error("got exception when saves data into redis pic", e);
						}
				
					//发起发送操作
					//MassWebServiceClient.getInstance().sendRedisMessage(key);
					//发送的内容保存在数据库中
					}
				return key;
				}

}

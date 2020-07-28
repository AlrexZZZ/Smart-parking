package com.nineclient.cherry.wcc.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nineclient.entity.MassSendEntity;
import com.nineclient.model.WccMassMessage;
import com.nineclient.utils.Global;
import com.nineclient.utils.RedisCommonHelper;

public class MassTextSend extends MessageSendAbstract {

	 public MassTextSend(MassSendEntity pmasssentity) {
		massSendEntity=pmasssentity;
	}
	@Override
	public String sendMessage() {
		String key="";
	//发起发送操作
		if(massSendEntity!=null)
		{
		WccMassMessage massMsg = new WccMassMessage();
		massMsg.setPlatformUser(massSendEntity.getWccplatforuser());
		massMsg.setType(1);
		massMsg.setFriends(massSendEntity.getFriends());
		massMsg.setGroups(massSendEntity.getGroupset());
		massMsg.setContent(massSendEntity.getContent());
		massMsg.setInsertTime(new Date());
		massMsg.setCompany(massSendEntity.getPub().getCompany());
		massMsg.setGroupStr(massSendEntity.getGroups());
		massMsg.setPubOrganization(massSendEntity.getPub().getOrganization());
		massMsg.setSex(massSendEntity.getGender());
		massMsg.setState(massSendEntity.getState());
		long newid=massMsg.merge().getId();		
		key =String.valueOf(newid);
			try {
				//发送的内容保存在redis中
				RedisCommonHelper.getInstance().getList(key+Global.targetflag).addAll(massSendEntity.getList());
				Map<String, String> map=new HashMap<String, String>();
				map.put(Global.messtype, massSendEntity.getMsgType());
				map.put(Global.sendcontent, massSendEntity.getContent());
				RedisCommonHelper.getInstance().getMap(key+Global.contentflag).putAll(map);
			} catch (Exception e) {
				// TODO: handle exception
				log.error("got exception when saves data into redis text", e);
			}
		}
		
		return key;

	}
}

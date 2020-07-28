package com.nineclient.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nineclient.web.WccWxCommController;
import com.nineclient.web.WccWxController;
import com.nineclient.weixin.imp.WeixinBasicParam;

//@Component
public class WccTimeOutTask {
	public static boolean flag = true;
	@Async
	@Scheduled(cron="0/30 * * * * ? ")//每30秒执行一次
	private void timeOut(){
		if(flag){
			System.out.println("握手=====================");
			WccWxCommController.checkHand();//UCC握手操作
			flag = false;
			System.out.println(WeixinBasicParam.uccError.toString());
		}
		/*Map<String, String> timeMap = WccWxController.timeMap;//key：opendId, value：粉丝最后交互时间
		Map<String, String> openIdMap = WccWxController.openIdMap;//key：opendId, value：(1：多客服, 2：UCC)
		Map<String, WxMpService> wxMpServiceMap = WccWxController.wxMpServiceMap;//key：opendId, value：WxMpService
		System.out.println("timeMap="+timeMap.size());
		System.out.println("openIdMap="+openIdMap.size());
		System.out.println("wxMpServiceMap="+wxMpServiceMap.size());
		Set<String> set = wxMpServiceMap.keySet();
		Iterator<String> it = set.iterator();
						String key = "";
		while(it.hasNext()){
			key = it.next();//opendId
			if(null != timeMap.get(key) && new Date().getTime()-Long.parseLong(timeMap.get(key))>1000*60*60){//超过120秒则超时
				openIdMap.remove(key);
				timeMap.remove(key);
				try {
					wxMpServiceMap.get(key).customMessageSend(WxMpCustomMessage.TEXT().toUser(key).content("您已超过一个小时未发起对话,本次对话结束").build());
				} catch (WxErrorException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("timeMap============"+timeMap.size());
		System.out.println("openIdMap============"+openIdMap.size());
		System.out.println("wxMpServiceMap=========="+wxMpServiceMap.size());*/
	}
}

package com.nineclient.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nineclient.model.WccFriend;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.thread.ThreadPoolManager;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Global;
import com.nineclient.web.RecordController;
import com.nineclient.weixin.FriendUpdateProcesser;
import com.nineclient.weixin.WxMpUserInfo;
import com.nineclient.weixin.WxMpuserManager;

@Component
public class WccFriendsFlush {
	private static final Logger logger = Logger
			.getLogger(WccFriendsFlush.class);
	public static Map<String, String> acctoken = new HashMap<String, String>();
	

	public static Map<String, String> getAcctoken() {
		return acctoken;
	}

	public static void setAcctoken(Map<String, String> acctoken) {
		RecordController.acctoken = acctoken;
	}

//	 @Async
   //  @Scheduled(cron = "00 20 15 * * *")
	public void flushFriends() throws WxErrorException {
	//	logger.warn(Util.getClassNameAndMethodName(WccFriendsFlush.class));
		System.out.println("定时任务开启~~~~~~~~~~");
		List<WccPlatformUser> plats = WccPlatformUser.findAllWccPlatformUsers(
				null, null);
		for (WccPlatformUser wccPlatformUser : plats) {
			System.out.println("公众平台id"+wccPlatformUser.getId());
			if(wccPlatformUser.getId()==4 || wccPlatformUser.getId().equals("4")){
			System.setProperty("https.protocols", "SSLv3,SSLv2Hello");
			WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
			logger.info("公众平台名称==========" + wccPlatformUser.getAccount());
			config.setAppId(wccPlatformUser.getAppId());
		    config.setSecret(wccPlatformUser.getAppSecret());
		    WxMpServiceImpl wxMpServiceImpl=new WxMpServiceImpl();
		    wxMpServiceImpl.setWxMpConfigStorage(config);
			/*config.setAccessToken(WxAccessTokenManager.getInstance().getToken(wccPlatformUser.getAppId(), wccPlatformUser.getAppSecret()));
			wxMpService.setWxMpConfigStorage(config);
			if (null != wccPlatformUser.getPlatformId()
					&& null == acctoken.get(wccPlatformUser.getPlatformId())) {
				// 获取accessToken
				String accessTok = null;
				try {
					accessTok = WxAccessTokenManager.getInstance().getToken(wccPlatformUser.getAppId(), wccPlatformUser.getAppSecret());
					logger.warn(wccPlatformUser.getAccount() + "获取token为====="
							+ accessTok);
					// accessTok = wxMpService.getAccessToken();
				} catch (Exception e) {
					e.printStackTrace();
				}
				acctoken.put(wccPlatformUser.getPlatformId(), accessTok);
			}
			String accessToken = acctoken.get(wccPlatformUser.getPlatformId());*/
			logger.info("accessToken=========================" + wxMpServiceImpl.getAccessToken());
			if (!"".equals(wxMpServiceImpl.getAccessToken())) {
				getFrindAndGroup(wccPlatformUser,wxMpServiceImpl.getAccessToken());
			}
		}
		}
	}


	public static void getFrindAndGroup(WccPlatformUser wccPlatformUser,String accessToken) throws WxErrorException{
		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		String appId = wccPlatformUser.getAppId();
		config.setAppId(appId);
		config.setAccessToken(accessToken);
		WxMpServiceImpl wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(config);

		// 更新分组信息
		/*List<WxMpGroup> groups = new ArrayList<WxMpGroup>();
		try {
			groups = wxMpService.groupGet();
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		for (WxMpGroup group : groups) {
			List<WccGroup> sgroup = WccGroup.findWccGroupByName(
					wccPlatformUser.getId(), group.getName());
			if (null != sgroup && sgroup.size() > 0) {
				continue;
			}
			WccGroup wgroup = new WccGroup();
			wgroup.setInsertTime(new Date());
			wgroup.setName(group.getName());
			wgroup.setIsDelete(true);
			wgroup.setPlatformUser(wccPlatformUser);
			wgroup.setGroupId(group.getId());
			wgroup.persist();
		}*/

		WxMpUserList user = null;
		try {
			updateOpenids(wxMpService,user,wccPlatformUser,null);
		} catch (Exception e) {
			// TODO: handle exception
		}
	/*	try {
			//获取粉丝信息
			user = wxMpService.userList(null);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
       Map<String, WccFriend> map=WccFriend.findAllWccFriendsNotDe(wccPlatformUser.getId());
       List<String> openIds=user.getOpenIds();
       int totle=0;
       int count=100;
       int remainder=0;
       List<WxMpUserInfo> list=null;
       if(openIds!=null&&openIds.size()>0)
       {
    	   totle=openIds.size()/100;
    	   remainder=openIds.size()%100;
    	   for(int i=0;i<=totle;i++)
    	   {
    		  if(i!=totle)
    		  {
    			  list=WxMpuserManager.getBatchUserinfo(openIds.subList(i*count, (i+1)*count), Global.LANG, wxMpService.getAccessToken());  
    			  new FriendUpdateProcesser(list,map,wccPlatformUser).run();
    		  }
    		  else
    		 {
    			  list= WxMpuserManager.getBatchUserinfo(openIds.subList(i*count, i*count+remainder), Global.LANG, wxMpService.getAccessToken());
    			  new FriendUpdateProcesser(list,map,wccPlatformUser).run();
			
    		}
    		  
    	   }
    	   
       }*/
	}
       
			
	
	
	public static void updateOpenids(WxMpServiceImpl wxMpService,WxMpUserList user,WccPlatformUser wccPlatformUser,String openId) throws WxErrorException
	{
		try {
			//获取粉丝信息
			if(null==user)
			{
				user = wxMpService.userList(null);	
			}
			else 
			{
				user = wxMpService.userList(openId);	
			}
			
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
      // Map<String, WccFriend> map=WccFriend.findAllWccFriendsNotDe(wccPlatformUser.getId());
		 Map<String, WccFriend> map=null;
       List<String> openIds=user.getOpenIds();
       int totle=0;
       int count=100;
       int remainder=0;
       List<WxMpUserInfo> list=null;
       if(openIds!=null&&openIds.size()>0)
       {
    	   totle=openIds.size()/100;
    	   remainder=openIds.size()%100;
    	   for(int i=0;i<=totle;i++)
    	   {
    		  if(i!=totle)
    		  {
    			  list=WxMpuserManager.getBatchUserinfo(openIds.subList(i*count, (i+1)*count), Global.LANG, wxMpService.getAccessToken());  
    			 // new FriendUpdateProcesser(list,map,wccPlatformUser).run();
    			  ThreadPoolManager.getThreadpool().execute( new FriendUpdateProcesser(list,map,wccPlatformUser));
    		  }
    		  else
    		 {
    			  list= WxMpuserManager.getBatchUserinfo(openIds.subList(i*count, i*count+remainder), Global.LANG, wxMpService.getAccessToken());
    			// new FriendUpdateProcesser(list,map,wccPlatformUser).run();
    			  ThreadPoolManager.getThreadpool().execute( new FriendUpdateProcesser(list,map,wccPlatformUser));
			
    		}
    		  
    	   }
    	   
       }
       if(user!=null&&user.getNextOpenId()!=openId)
       {
    	   updateOpenids(wxMpService, user, wccPlatformUser, user.getNextOpenId());
       }
	
	}
}

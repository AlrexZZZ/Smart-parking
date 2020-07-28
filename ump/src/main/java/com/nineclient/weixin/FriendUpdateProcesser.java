package com.nineclient.weixin;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.nineclient.constant.WccFriendFormType;
import com.nineclient.constant.WccFriendSex;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccGroup;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.task.WccFriendsFlush;
import com.nineclient.utils.DateUtil;

public class FriendUpdateProcesser implements Runnable {
private List<WxMpUserInfo> list=null;
private Map<String,WccFriend> map=null;
private WccPlatformUser wccPlatformUser=null;
private static final Logger logger = Logger.getLogger(FriendUpdateProcesser.class);
public FriendUpdateProcesser(List<WxMpUserInfo> plist,Map<String,WccFriend> pmap,WccPlatformUser pwccPlatformUser)
{
	this.list=plist;
	this.map=pmap;
	this.wccPlatformUser=pwccPlatformUser;
}
	@Override
	public void run() {
		WxMpUserInfo wxMpUserInfo=null;
		WccFriend wccFriend=null;
		WccGroup group=null;
		 if(list!=null&&list.size()>0)
		   {
			   for(int i=0;i<list.size();i++)
			   {
				   wxMpUserInfo=list.get(i);
				   if(wxMpUserInfo==null ){
					   continue;
					} 
				 try {
//					   WccFriendsFlush.saveFriendArea(wxMpUserInfo.getOpenid(),wxMpUserInfo);
				} catch (Exception e) {
					System.out.println("保存粉丝区域失败");
				}
				   System.out.println("新增");
				   wccFriend=new WccFriend();
				   wccFriend.setFromType(WccFriendFormType.直接获取);
				   wccFriend.setHeadImg(wxMpUserInfo.getHeadimgurl());
				   wccFriend.setInsertTime(new Date());
				   wccFriend.setIsDeleted(true);
				   wccFriend.setIsValidated(true);
				   //wccFriend.setUnionId(wxMpUserInfo.getUnionid());
//					logger.error("uniondi is :"+wxMpUserInfo.getUnionid());
					String niceName = "";
					try {
						niceName = java.net.URLEncoder.encode(wxMpUserInfo.getNickname(),"utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						
					}
					wccFriend.setNickName(niceName);
					WccFriendSex sex = null;
					if (wxMpUserInfo.getSex().equals("1")) {
						sex = WccFriendSex.男;
					} else if (wxMpUserInfo.getSex().equals("2")) {
						sex = WccFriendSex.女;
					} else {
						sex = WccFriendSex.未知;
					}
					DateUtil.getDate(wxMpUserInfo.getSubscribe_time());
					 group=  WccGroup.findWccGroupByGroupId(wccPlatformUser.getId(),Long.parseLong( wxMpUserInfo.getGroupid()));
					wccFriend.setWgroup(group);
					wccFriend.setSex(sex);
					wccFriend.setSubscribeTime(DateUtil.getDate(wxMpUserInfo.getSubscribe_time()));
					wccFriend.setProvince(wxMpUserInfo.getProvince());
					wccFriend.setCity(wxMpUserInfo.getCity());
					wccFriend.setCountry(wxMpUserInfo.getCountry());
					wccFriend.setArea(wxMpUserInfo.getCountry() + wxMpUserInfo.getProvince()
							+ wxMpUserInfo.getCity());
					wccFriend.setOpenId(wxMpUserInfo.getOpenid());
					wccFriend.setPlatformUser(wccPlatformUser);
				//	wccFriend.setUnionId(wxMpUserInfo.getUnionid());
					System.out.println("insert friend new"+wxMpUserInfo.getOpenid());
					try {
						wccFriend.persist();
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("save friend error");
					}
					finally
					{
						wccFriend=null;
					}
					
			   }
			   
			   }
	/*
	WxMpUserInfo wxMpUserInfo=null;
	WccFriend wccFriend=null;
	String issubstrin="";
	WccGroup group=null;
   if(list!=null&&list.size()>0)
   {
	   for(int i=0;i<list.size();i++)
	   {
		   wxMpUserInfo=list.get(i);
		   if(wxMpUserInfo==null ){
			   continue;
			}
		   try {
			   WccFriendsFlush.saveFriendArea(wxMpUserInfo.getOpenid(),wxMpUserInfo);
		} catch (Exception e) {
			System.out.println("保存粉丝区域失败");
		}
		   
		   wccFriend=map.get(wxMpUserInfo.getOpenid());		  
		   issubstrin=wxMpUserInfo.getSubscribe();
		   
		   if("0".equals(issubstrin)&&null==wccFriend)
		   {
			   continue;
		   }
		   else if("0".equals(issubstrin)&&null!=wccFriend) {
			wccFriend.setIsDeleted(false);
			wccFriend.setUnionId(wxMpUserInfo.getUnionid());
			wccFriend.merge();
			continue;
		   }
		   else if("1".equals(issubstrin)&&null==wccFriend)
		   {
			   System.out.println("新增");
			   group=  WccGroup.findWccGroupByGroupId(wccPlatformUser.getId(),Long.parseLong( wxMpUserInfo.getGroupid()));
			   wccFriend=new WccFriend();
			   wccFriend.setWgroup(group);
			   wccFriend.setFromType(WccFriendFormType.直接获取);
			   wccFriend.setHeadImg(wxMpUserInfo.getHeadimgurl());
			   wccFriend.setInsertTime(new Date());
			   wccFriend.setIsDeleted(true);
			   wccFriend.setIsValidated(true);
			   wccFriend.setUnionId(wxMpUserInfo.getUnionid());
//				logger.error("uniondi is :"+wxMpUserInfo.getUnionid());
				String niceName = "";
				try {
					niceName = java.net.URLEncoder.encode(wxMpUserInfo.getNickname(),"utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				wccFriend.setNickName(niceName);
				WccFriendSex sex = null;
				if (wxMpUserInfo.getSex().equals("0")) {
					sex = WccFriendSex.男;
				} else if (wxMpUserInfo.getSex().equals("1")) {
					sex = WccFriendSex.女;
				} else {
					sex = WccFriendSex.未知;
				}
				DateUtil.getDate(wxMpUserInfo.getSubscribe_time());
				wccFriend.setSex(sex);
				wccFriend.setSubscribeTime(DateUtil.getDate(wxMpUserInfo.getSubscribe_time()));
				wccFriend.setProvince(wxMpUserInfo.getProvince());
				wccFriend.setCity(wxMpUserInfo.getCity());
				wccFriend.setCountry(wxMpUserInfo.getCountry());
				wccFriend.setArea(wxMpUserInfo.getCountry() + wxMpUserInfo.getProvince()
						+ wxMpUserInfo.getCity());
				wccFriend.setOpenId(wxMpUserInfo.getOpenid());
				wccFriend.setPlatformUser(wccPlatformUser);
				System.out.println("insert friend new"+wxMpUserInfo.getOpenid());
				try {
					wccFriend.persist();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("save friend error");
				}
				
				continue;
			   
		   }
		   else if(wccFriend!=null && StringUtils.isBlank(wccFriend.getUnionId())){
			   System.out.println("没有unionID");
			   group=  WccGroup.findWccGroupByGroupId(wccPlatformUser.getId(),Long.parseLong( wxMpUserInfo.getGroupid()));
			   wccFriend.setWgroup(group);
			   wccFriend.setHeadImg(wxMpUserInfo.getHeadimgurl());
			   wccFriend.setIsDeleted(true);
			   wccFriend.setIsValidated(true);
			   wccFriend.setUnionId(wxMpUserInfo.getUnionid());
//				logger.error("uniondi is :"+wxMpUserInfo.getUnionid());
				String niceName = "";
				try {
					niceName = java.net.URLEncoder.encode(wxMpUserInfo.getNickname(),"utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				wccFriend.setNickName(niceName);
				WccFriendSex sex = null;
				if (wxMpUserInfo.getSex().equals("0")) {
					sex = WccFriendSex.男;
				} else if (wxMpUserInfo.getSex().equals("1")) {
					sex = WccFriendSex.女;
				} else {
					sex = WccFriendSex.未知;
				}
				DateUtil.getDate(wxMpUserInfo.getSubscribe_time());
				wccFriend.setSex(sex);
				wccFriend.setSubscribeTime(DateUtil.getDate(wxMpUserInfo.getSubscribe_time()));
				wccFriend.setProvince(wxMpUserInfo.getProvince());
				wccFriend.setCity(wxMpUserInfo.getCity());
				wccFriend.setCountry(wxMpUserInfo.getCountry());
				wccFriend.setArea(wxMpUserInfo.getCountry() + wxMpUserInfo.getProvince()
						+ wxMpUserInfo.getCity());
				wccFriend.setOpenId(wxMpUserInfo.getOpenid());
				wccFriend.setUnionId(wxMpUserInfo.getUnionid());
				wccFriend.setPlatformUser(wccPlatformUser);
				System.out.println("update friend new"+wxMpUserInfo.getOpenid());
				try {
					wccFriend.merge();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("update friend error");
				}
				continue;
			
		     }
		   
	   }
   }
   this.list=null;
   this.map=null;
   this.wccPlatformUser=null;
   */
	}
	

}

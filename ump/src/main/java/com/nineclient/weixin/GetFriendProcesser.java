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

public class GetFriendProcesser implements Runnable {
private List<WxMpUserInfo> list=null;
private WccPlatformUser wccPlatformUser=null;
public GetFriendProcesser(List<WxMpUserInfo> wxMpUserInfo,WccPlatformUser pwccPlatformUser)
{
	this.list=wxMpUserInfo;
	this.wccPlatformUser=pwccPlatformUser;
}
	@Override
	public void run() {
		WxMpUserInfo wxMpUserInfo=null;
		 if(list!=null&&list.size()>0)
		   {
			   for(int i=0;i<list.size();i++)
			   {
				   wxMpUserInfo=list.get(i);
				   if(wxMpUserInfo==null ){
					   continue;
					}
		           WccFriend wccFriend=null;
				   wccFriend=new WccFriend();
				   wccFriend.setFromType(WccFriendFormType.直接获取);
				   wccFriend.setHeadImg(wxMpUserInfo.getHeadimgurl());
				   wccFriend.setInsertTime(new Date());
				   wccFriend.setIsDeleted(true);
				   wccFriend.setIsValidated(true);
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
//					 group=  WccGroup.findWccGroupByGroupId(wccPlatformUser.getId(),Long.parseLong( wxMpUserInfo.getGroupid()));
//					wccFriend.setWgroup(group);
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
					finally
					{
						wccFriend=null;
					}
					
			   }
		   }}
}

package com.nineclient.cbd.wcc.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.nineclient.model.WccFriend;

@Path("pagesLoadUrl")
public class GetFrendInfo {

	@POST
	@Path("/test1")
	@Produces(MediaType.TEXT_PLAIN)
	public String testPOST(@Context HttpServletRequest request) {
		return null;
	}

	@GET
	@Path("/friendInfo")
	@Produces("text/plain; charset=utf-8")
	public String getFriendInfo(@Context HttpServletRequest request,@Context HttpServletResponse response,
			@QueryParam("openId")String openId) {
		System.out.println("get friends info ~");
		WccFriend friend=null;
		String msg = null;
		if(null != openId && !"".equals(openId.trim())){
			  friend = WccFriend.findWccFriendByOpenId(openId);
			  if(null != friend){
					 JsonConfig config = new JsonConfig();
				     config.setIgnoreDefaultExcludes(false);
				     config.setExcludes(new String[]{"userLotterys","wccFinancialUser"
				    		 ,"massMessages","platformUser","remarkName","sendEmailTime",
				    		 "organization","isValidated","signature","userInfo",
				    		 "id","wccqrcode","wgroup","version","isDeleted","messageEndTime"});//除去emps属性
				     JSONObject js =	JSONObject.fromObject(friend,config);
				     msg = js.toString();
			  }else{
				  msg = "{\"errcode\":40011,\"errmsg\":\"no such friend exit\"}";
			  }
		}else{//   定义无参数异常
			  msg="{\"errcode\":40012,\"errmsg\":\"invalid openId\"}";
		}

		return msg;
	}
}
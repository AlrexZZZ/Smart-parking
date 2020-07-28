package com.nineclient.web;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nineclient.dto.WccUccError;
import com.nineclient.dto.WccUccHand;
import com.nineclient.dto.WccUccHandIn;
import com.nineclient.dto.WccUccOut;
import com.nineclient.dto.WccUccSendMsg;
import com.nineclient.dto.WccUccSendMsgRes;
import com.nineclient.model.WccFriend;
import com.nineclient.thread.WccSendMessage;
import com.nineclient.utils.Global;
import com.nineclient.weixin.imp.WeixinBasicParam;


@RequestMapping("/wxCommController")
@Controller
public class WccWxCommController extends HttpServlet {
	
	Map<String, WxMpService> wxMpServiceMap = WeixinBasicParam.wxMpServiceMap;//key：opendId, value：WxMpService
	Map<String,String> uccInfo =WeixinBasicParam.uccInfo;// key：openId value：UCC返回的信息
	private static final String url = Global.Url;
	private static final String uccUrl = Global.UCCURL;
	
	/**
	 * WCC接受UCC发过来的消息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "wxComm", produces = "text/html")
	protected void wxComm(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("接受UCC信息============================");
		String context = request.getParameter("context");
		WccSendMessage sendMessage = new WccSendMessage(context, wxMpServiceMap, uccInfo);
		sendMessage.start();
	}
	
//	/**
//	 * WCC和UCC接手操作
//	 * @return
//	 */
//	public static JSONObject uccHand(){
//		JSONObject jsonObject = JSONObject.fromObject(new WccUccHand("102030", "297ed0d04a9f24e1014a9f28d2fd0005", "HAND_SHAKE_TYPE", "WECHAT_CHANNAL_1", "http://192.168.1.19:80/ump/wxCommController/wxComm", "bbb", "cc", ""));
//		String url = "http://192.168.1.21:8080/JtalkManager/resteasy/Other2UccService/Service?context="+URLEncoder.encode(jsonObject.toString());
//		return JSONObject.fromObject(JSONObject.fromObject(WccWxCommController.urlService(url)).get("responseMsg"));
//	}
	
	/**
	 * WCC和UCC握手操作
	 * @return
	 */
	public static JSONObject uccHand(){
		System.out.println("uccHand=============================");
		JSONObject jsonObject = JSONObject.fromObject(new WccUccHand("102030", "402881ad4c2540ec014c254218790002", "HAND_SHAKE_TYPE", "WECHAT_CHANNAL_1", url+"/ump/wxCommController/wxComm", "bbb", "cc", ""));
		String url = uccUrl;
		long t1 = new Date().getTime();
		System.out.println("时间1================="+t1);
		String msg = WccWxCommController.urlServicePost(url,jsonObject.toString());
		System.out.println("时间2=========================="+(new Date().getTime() - t1));
		JSONObject resMsg = null;
		if(null != msg &&!"".equals(msg)){
			System.out.println("uccHand msg==========================================");
			resMsg = JSONObject.fromObject(JSONObject.fromObject(msg).get("responseMsg"));
			JSONObject errorMsg = JSONObject.fromObject(resMsg.get("error"));
			System.out.println("msg="+msg);
			System.out.println("errorMsg="+errorMsg);
		}
		return resMsg;
	}
	
	/**
	 * WCC和UCC握手操作
	 * 如果返回的error不是0（错误）
	 * 再次请求
	 * @return
	 */
	public static String checkHand(){
		JSONObject resMsg = WccWxCommController.uccHand();
		System.out.println("check resMsg="+resMsg);
		if(null == resMsg){
			WeixinBasicParam.uccError = new WccUccError("100","UCC链接服务异常");
			return "";
		}
		String error = "";
		int i = 0;
		do{
			i++;
			System.out.println("i="+i);
			error = JSONObject.fromObject(resMsg.getString("error")).getString("errorCode") ;
			System.out.println("error="+error);
			if(i==3){
				break;
			}
		}while(!"0".equals(error)&&!"5".equals(error));
		
		WeixinBasicParam.uccError = new WccUccError(error, JSONObject.fromObject(resMsg.getString("error")).getString("errorMsg").toString());
		System.out.println("uccError="+WeixinBasicParam.uccError.toString());
		WeixinBasicParam.uccToken = resMsg.getString("token");
		System.out.println("msg="+resMsg.toString());
		return WeixinBasicParam.uccToken;
	}
	
	/**
	 * 接入UCC
	 */
	public static String gotoUcc(String openId){
		WccFriend friend = WccFriend.findWccFriendByOpenId(openId);
		try {
			friend.setNickName(URLDecoder.decode(friend.getNickName(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String token = WeixinBasicParam.uccToken;
		if(null == token){
			checkHand();
		}
		JSONObject jsonObject = JSONObject.fromObject(new WccUccHandIn(token, "IN_QUEUE_TYPE", openId, friend.getNickName(),"WECHAT_CHANNAL_1"));
		System.out.println("jsonObject="+jsonObject.toString());
		String url = uccUrl;
		String msg = urlServicePost(url,jsonObject.toString());
		if(null == msg){
			WeixinBasicParam.openIdMap.remove(openId);
			WeixinBasicParam.chatIdMap.remove(openId);
			WeixinBasicParam.timeMap.remove(openId);
			try {
				WeixinBasicParam.wxMpServiceMap.get(openId).customMessageSend(WxMpCustomMessage.TEXT().toUser(openId).content("服务器异常").build());
				return null;
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
		}
		System.out.println("gotoUcc msg="+msg);
		return msg;
	}
	
	/**
	 * 接入UCC
	 */
	public static WccUccOut UccIn(String openId){
		 String msg = gotoUcc(openId);
		 if(null == msg){
			 return null;
		 }
		 JSONObject json = JSONObject.fromObject(msg);
		 String chatId = json.getString("chatID");
		 JSONObject resMsg = JSONObject.fromObject(json.getString("responseMsg"));
		 JSONObject error = JSONObject.fromObject(resMsg.getString("error"));
		 String errorCode = error.getString("errorCode");
		 String errorMsg = error.getString("errorMsg");
		 if("1".equals(errorCode)){
			 checkHand();
			 UccIn(openId);
		 }
		 WeixinBasicParam.uccError = new WccUccError(errorCode,errorMsg);
		 System.out.println("uccError="+WeixinBasicParam.uccError.toString());
		 WeixinBasicParam.chatIdMap.put(openId, chatId);
		 WeixinBasicParam.wccUccOut.put(openId, new WccUccOut(resMsg.getString("opname"),resMsg.getString("msg"),resMsg.getString("info")));
		 return WeixinBasicParam.wccUccOut.get(openId);
	}
	
	/**
	 * 发送消息给UCC
	 * @param openId
	 * @param message
	 * @return
	 */
	public static String sendMsgToUcc(String openId,String message){
		String token = WeixinBasicParam.uccToken;
		String chatId = WeixinBasicParam.chatIdMap.get(openId);
		WccUccSendMsgRes rquestMsg = new WccUccSendMsgRes();
		rquestMsg.setMessage(message);
		JSONObject jsonObject = JSONObject.fromObject(new WccUccSendMsg(token, chatId, "SEND_MSG_TYPE","WECHAT_CHANNAL_1", rquestMsg));
		System.out.println("jsonObject="+jsonObject.toString());
		String url = uccUrl;
		String msg = urlServicePost(url,jsonObject.toString());
		if(null == msg){
			WeixinBasicParam.openIdMap.remove(openId);
			WeixinBasicParam.chatIdMap.remove(openId);
			WeixinBasicParam.timeMap.remove(openId);
			try {
				WeixinBasicParam.wxMpServiceMap.get(openId).customMessageSend(WxMpCustomMessage.TEXT().toUser(openId).content("服务器异常").build());
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
		}
		JSONObject resMsgs = JSONObject.fromObject(JSONObject.fromObject(msg).getString("responseMsg"));
		WeixinBasicParam.uccError = new WccUccError(JSONObject.fromObject(resMsgs.getString("error")).getString("errorCode").toString(), JSONObject.fromObject(resMsgs.getString("error")).getString("errorMsg").toString());
		return msg;
	}
	
	public static String urlService(String url) {
		try {
			HttpURLConnection huc = (HttpURLConnection) new URL(url)
					.openConnection();
			huc.setRequestMethod("GET");
			huc.setUseCaches(true);
			huc.setRequestProperty("Content-type", "text/html");
			huc.connect();
			InputStream is = huc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"));
			StringBuffer temp = new StringBuffer();
			String str;
			while ((str = reader.readLine()) != null) {
				temp.append(str + "\n");
			}
			is.close();
			reader.close();
			return temp.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}
	
	public static String urlServicePost(String url,String json) {
		try {
			HttpURLConnection huc = (HttpURLConnection) new URL(url)
					.openConnection();
			huc.setRequestMethod("POST");
			huc.setUseCaches(true);
			huc.setDoOutput(true);
			huc.setDoInput(true);
			huc.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			huc.connect();
			DataOutputStream out = new DataOutputStream(huc.getOutputStream());
			String params = "context="+json;
			byte[] bypes = params.toString().getBytes();
			out.write(bypes);
			out.flush();
			out.close();
			
			InputStream is = huc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"));
			StringBuffer temp = new StringBuffer();
			String str;
			while ((str = reader.readLine()) != null) {
				temp.append(str + "\n");
			}
			is.close();
			reader.close();
			return temp.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}
}

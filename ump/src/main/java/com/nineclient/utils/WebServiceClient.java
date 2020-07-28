package com.nineclient.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.codehaus.xfire.client.Client;

import com.nineclient.model.VocAccount;
import com.nineclient.model.VocAppkey;
import com.nineclient.model.VocBrand;
import com.nineclient.model.VocKeyWord;

/**
 * webservice 调用
 * @author 9client
 */
public class WebServiceClient {
	private static final Logger logger = Logger
			.getLogger(WebServiceClient.class);
	static WebServiceClient instance = null;
	private  WebServiceClient() {
		
	}
	
   public static WebServiceClient getInstatnce(){
		if(null == instance){
		 instance = new WebServiceClient();
		}
		return instance;
	}
	
   
  /**
   * 添加品牌关键词任务
   * @param keyWordStr
   */
   public void addKeyWordTask(VocBrand brand,VocKeyWord kw){
		  JSONObject jsonObj = new JSONObject();
		   jsonObj.accumulate("id", kw.getId()+"");
		   jsonObj.accumulate("name", kw.getName());
		   jsonObj.accumulate("brandId", brand.getId()+"");
		   jsonObj.accumulate("brandName", brand.getBrandName()+"");
		   jsonObj.accumulate("channelId", "2");
		   jsonObj.accumulate("channelName", "京东商城");
		   jsonObj.accumulate("businessId", "");
		   jsonObj.accumulate("businessName", "");
		   String returnVal = null;
		   try {
			   returnVal = callMethod("addKeyWord",jsonObj.toString());
		  } catch (Exception e) {
			e.printStackTrace();
		  }
		   logger.info("新增品牌关键词结束："+returnVal);
	   }
   
   public void addNewAccount(VocAccount account,VocAppkey appkey){
	   JSONObject jsonObj = new JSONObject();
	   jsonObj.accumulate("company_id", account.getCompanyId());
	   jsonObj.accumulate("account_id", account.getId());
	   jsonObj.accumulate("account_name", account.getAccount());
	   jsonObj.accumulate("app_key", appkey.getAppkey());
	   jsonObj.accumulate("secret", appkey.getClientSecret());
	   jsonObj.accumulate("session_key", account.getSessionKey());
	   jsonObj.accumulate("shop_id", account.getVocShop().getId());
	   jsonObj.accumulate("shop_name",  account.getVocShop().getName());
	   jsonObj.accumulate("channel_id", account.getVocShop().getChannel().getId());
	   jsonObj.accumulate("channel_name", account.getVocShop().getChannel().getChannelName());
	   String returnVal = null;
	   try {
		   returnVal = callMethod("addNewAccount",jsonObj.toString());
		  } catch (Exception e) {
			  logger.error("接口调用异常:"+e.getMessage());
			e.printStackTrace();
		  }
	   if(null != returnVal && !"".equals(returnVal)){
		 System.out.println("returnVal "+returnVal);  
		 Pattern p = Pattern.compile("<ns1:out>(.*?)</ns1:out>");
		 Matcher m = p.matcher(returnVal);
		 if(m.find()){
			logger.info("STR "+m.group(1)); 
		 }
		 
	   }
		
	   
   }
   /* public void addKeyWordTask(VocBrand brand,VocKeyWord kw){
	  JSONObject jsonObj = new JSONObject();
	   jsonObj.accumulate("id", kw.getId()+"");
	   jsonObj.accumulate("name", kw.getName());
	   jsonObj.accumulate("brandId", brand.getId()+"");
	   jsonObj.accumulate("brandName", brand.getBrandName()+"");
	   jsonObj.accumulate("channelId", "2");
	   jsonObj.accumulate("channelName", "京东商城");
	   jsonObj.accumulate("businessId", "");
	   jsonObj.accumulate("businessName", "");

		String wsdl ="http://192.168.1.13:8080/dataService/webservice/webinterface?wsdl"; //webservice调用地址
		Object[] arguments = new Object[] {jsonObj.toString()};
		String methodName = "addKeyWord";
		try {
			URL url = new URL(wsdl);
			Client client = new Client(url);
			Object[] object = client.invoke(methodName, arguments);
			if (null != object[0]) {
			  System.err.println("returnValue "+String.valueOf(object[0]));
			  logger.info("addKeyWordTask 返回值 "+String.valueOf(object[0]));
			}
		} catch (MalformedURLException e) {
			logger.info("addKeyWordTask 调用失败 "+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("addKeyWordTask 调用失败 "+e.getMessage());
		}


}*/

	public static void main(String[] args) throws MalformedURLException, Exception {
		  Client client = new Client(new URL("http://127.0.0.1:8088/dataService/webservice/webinterface?wsdl"));
	        Object[] results = client.invoke("test", new Object[] { "Firends" });
	        System.out.println(results[0]);
	}

	
	 /**
	 * 调用测试
	 * @throws AxisFault 
	 * @throws IOException 
	 */
	/*public static void testCallWebservice(){
			String wsdl ="http://192.168.1.13:8080/dataService/webservice/webinterface?wsdl";
			Object[] arguments = new Object[] { };
			String methodName = "test";
			try {
				URL url = new URL(wsdl);
				Client client = new Client(url);
				Object[] object = client.invoke(methodName, arguments);
				System.out.println("返回值是: " + object[0]);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }*/
	
	public static String callMethod(String methodName,String parm) throws MalformedURLException, Exception{
		  Client client = new Client(new URL(Global.DATA_INTERFACE_URI));
	      Object[] results = client.invoke(methodName, new Object[] {parm});
		  if(results!=null&&results.length>0){
			return (String) results[0];
		  }
        return null;
	}
}

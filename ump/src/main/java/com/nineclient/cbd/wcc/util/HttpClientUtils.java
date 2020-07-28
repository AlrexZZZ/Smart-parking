package com.nineclient.cbd.wcc.util;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
 
/**
 * ���� httpclient 4.3.1�汾�� http������
 * @author 
 *
 */
public class HttpClientUtils {
 
     static final CloseableHttpClient httpClient;
    public static final String CHARSET = "UTF-8";
 
    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(30000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        

    }
 
    public static String doGet(String url, Map<String, String> params){
        return doGet(url, params,CHARSET);
    }
    public static String doPost(String url, Map<String, String> params,String str){
        return doPost(url, params,str,CHARSET);
    }
    /**
     * HTTP Get ��ȡ����
     * @param url  �����url��ַ ?֮ǰ�ĵ�ַ
     * @param params ����Ĳ���
     * @param charset    �����ʽ
     * @return    ҳ������
     */
    public static String doGet(String url,Map<String,String> params,String charset){
        if(StringUtils.isBlank(url)){
            return null;
        }
        HttpGet httpGet  =null;
        CloseableHttpResponse response=null;
        try {
            if(params != null && !params.isEmpty()){
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,String> entry : params.entrySet()){
                    String value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
  
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	closeConnAndStream(response, httpGet,null, httpClient);
        }
        return null;
    }
     
    /**
     * HTTP Post ��ȡ����
     * @param url  �����url��ַ ?֮ǰ�ĵ�ַ
     * @param params ����Ĳ���
     * @param charset    �����ʽ
     * @return    ҳ������
     */
    public static String doPost(String url,Map<String,String> params,String str,String charset){
        if(StringUtils.isBlank(url)){
            return null;
        }
        
        CloseableHttpResponse response = null;
        HttpPost httpPost = null;
        try {
            List<NameValuePair> pairs = null;
            if(params != null && !params.isEmpty()){
                pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,String> entry : params.entrySet()){
                    String value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
            }
                httpPost = new HttpPost(url);
            //���ݵ��������� ������һ����Ϊ��
//            if((null != pairs && pairs.size() > 0) || (null != str && !"".equals(str.trim()))){
            if(pairs != null && pairs.size() > 0){
                httpPost.setEntity(new UrlEncodedFormEntity(pairs,charset));
            }
            if((null != str && !"".equals(str.trim()))){
            	httpPost.setEntity(new StringEntity(str,charset));
            }
                httpPost.addHeader("content-type","application/json;charset=UTF-8");
//            }
             response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
   
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	closeConnAndStream(response, null, httpPost, httpClient);
        }
        return null;
    }
    
 /**
  *    �ر����Լ��ͷ�����
  * @param args
  */
    
 public static void closeConnAndStream(CloseableHttpResponse response,HttpGet httpGet,
		 HttpPost httpPost,CloseableHttpClient httpClient){
	 
 	//�ر� response ��
  if(null != response){
	 try {
		response.close();
	} catch (IOException e1) {
		e1.printStackTrace();
	}
  }
	//�ر�  httpClient
  if(null != httpClient){
	  System.out.println(httpClient.getConnectionManager().hashCode()); 
/*	try {
		httpClient.close();
	} catch (IOException e) {
		e.printStackTrace();
	}*/
  }
  //�ͷ� httpGet ����
  if(null != httpGet){
		try{
			httpGet.releaseConnection();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
  //�ͷ� httpPost ����
  if(null != httpPost){
		try{
			httpPost.releaseConnection();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
 }   
    public static void main(String []args){
//    String getData = doGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx2c94749984dbf67d&secret=f53c0f378c473d3b103d0f62dfa86c1e",null);
//    System.out.println(getData);
    	
    String acctoken = "9sdR1aX8D4JgFvGChzvqN8_dgw844_RMi7Kqz8UucD8OnZPcPI7yEmvDfqXsOTAnK4fUf_vPtT5YMXXxcCBQr83FvcTtFiOlk2A5DlUefne7iQg7AZOBwn9K8ZbrHOqbIKVjACAPRC";  	
        
  
/*      System.out.println("----------------------�ָ���-----------------------");

      String openIdStr = "{\"openid\":\"oo33Ssj2N3hTb7PTgwPKBM3Qtsbg\"}";
      
      String postData = doPost("https://api.weixin.qq.com/cgi-bin/groups/getid?access_token="+acctoken, null,openIdStr);
     
      System.out.println(postData);*/
      
      
      //ͨ��openId ��ѯ��˿��Ϣ
    String getFriendUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+acctoken+"&openid=oo33Ssj2N3hTb7PTgwPKBM3Qtsbg&lang=zh_CN";
   String  strr = doGet(getFriendUrl, null);
   
   
   System.out.println(strr);
    	
    }
     
}
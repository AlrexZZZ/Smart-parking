package com.nineclient.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.avalon.framework.service.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


/**
 * 
* @ClassName: HttpClientUtil
* @Description: httpClient工具类
* @author Osborn hu
* @date 2014-6-21 上午11:10:43
*
 */
public class HttpClientUtil {
	private static final Logger logger = Logger
			.getLogger(HttpClientUtil.class); 
	private static CloseableHttpClient __httpClient = null;
	private static final String CHARSET = "UTF-8";
	private synchronized static CloseableHttpClient createHttpClient() {
		if (__httpClient == null) {
			__httpClient = HttpClients.createDefault();
		}
		return __httpClient;
	}
	public static String doPost(String url,Map map,String charset) {
		Iterator iter = map.entrySet().iterator();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey()==null?"": entry.getKey();
			Object val = entry.getValue()==null?"":entry.getValue();
			formparams.add(new BasicNameValuePair(key.toString(), val.toString())); 
		}
		return doPost(url,formparams,charset);
	}
	public static String doPostH(String url,Map map,String charset,Map<String, String> headerMap) {
		Iterator iter = map.entrySet().iterator();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey()==null?"": entry.getKey();
			Object val = entry.getValue()==null?"":entry.getValue();
			formparams.add(new BasicNameValuePair(key.toString(), val.toString())); 
		}
		return doPostHb(url,formparams,charset,headerMap);
	}
	public static String doPost(String url, List<NameValuePair> params,
			String charset){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//Assert.notNull(httpClient);
		long startTime=System.currentTimeMillis();

		HttpPost httpRequest = new HttpPost(url);
		CloseableHttpResponse  httpResponse=null;
		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setSocketTimeout(3000)
				.setConnectTimeout(3000)
				.setConnectionRequestTimeout(3000)
				.build();
		
		//		httpRequest
		String strResult = "doPostError";
		try {
			/* 添加请求参数到请求对象 */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, charset));
			
			/* 设置请求,响应时间 */
			httpRequest.setConfig(defaultRequestConfig);
			
			/* 发送请求并等待响应 */
			httpResponse= httpClient.execute(httpRequest);
			
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				strResult = EntityUtils.toString(httpResponse.getEntity(),charset);

			} else {
				strResult = "Error Response: "
						+ httpResponse.getStatusLine().toString();
			}
			EntityUtils.consume(httpResponse.getEntity());
		} catch (Exception ex) {
			__httpClient = null;
			ex.printStackTrace();
			logger.error("响应失败！");
			strResult="响应失败！";
		}finally{
			if(httpResponse!=null)
			{
				try {
					httpResponse.close();
					httpResponse=null;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			if(httpClient!=null){
				try {
					httpClient.close();
					httpClient=null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(httpRequest!=null)
			{
				httpRequest.releaseConnection();
				httpRequest=null;
			}
			
		}
		long endTime=System.currentTimeMillis();
		logger.error(new Date()+"请求url="+url+"请求时间"+(1.0*(endTime-startTime)/1000));
		return strResult;
	}
	public static String doPostHb(String url, List<NameValuePair> params,
			String charset,Map<String, String> headerMap) {
		CloseableHttpClient httpClient =HttpClients.createDefault();
		//Assert.notNull(httpClient);
		CloseableHttpResponse httpResponse =null;
		HttpPost httpRequest = new HttpPost(url);
		Iterator iter = headerMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey()==null?"": entry.getKey();
			Object val = entry.getValue()==null?"":entry.getValue();
			httpRequest.setHeader(key.toString(), val.toString());
		}
		String strResult = "doPostError";
		try {
			/* 添加请求参数到请求对象 */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, charset));
			/* 发送请求并等待响应 */
			 httpResponse = httpClient.execute(httpRequest);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				strResult = EntityUtils.toString(httpResponse.getEntity(),charset);

			} else {
				strResult = "Error Response: "
						+ httpResponse.getStatusLine().toString();
			}
			EntityUtils.consume(httpResponse.getEntity());
		} catch (Exception ex) {
			__httpClient = null;
			ex.printStackTrace();
		}finally{
			if(httpResponse!=null)
			{
				try {
					httpResponse.close();
					httpResponse=null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(httpClient!=null){
				try {
					httpClient.close();
					httpClient=null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(httpRequest!=null)
			{
				httpRequest.releaseConnection();
				httpRequest=null;
			}
		}
		return strResult;
	}
	public static String doGet(String url, Map params, String charset) {

		StringBuffer paramStr = new StringBuffer("");
        if(params!=null)
        {
        	Iterator iter = params.entrySet().iterator();
    		while (iter.hasNext()) {
    			Map.Entry entry = (Map.Entry) iter.next();
    			Object key = entry.getKey();
    			Object val = entry.getValue();
    			paramStr.append("&").append(key).append("=").append(val);
    		}	
        }	

		if (!paramStr.equals("")) {
			String tempStr = paramStr.toString().replaceFirst("&", "?");
			url += tempStr;
		}

		return doGet(url, charset);

	}
	public static String doGetH(String url, Map params, String charset,Map<String, String> headerMap) {

		StringBuffer paramStr = new StringBuffer("");

		Iterator iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			paramStr.append("&").append(key).append("=").append(val);
		}

		if (!paramStr.equals("")) {
			String tempStr = paramStr.toString().replaceFirst("&", "?");
			url += tempStr;
		}

		return doGetHB(url, charset,headerMap);

	}
	public static String doGetHB(String url, String charset,Map<String, String> headerMap){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//Assert.notNull(httpClient);
		CloseableHttpResponse httpResponse=null;
		
		String strResult = "doGetError";
		HttpGet httpRequest = new HttpGet(url);
		try {
			
			Iterator iter = headerMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey()==null?"": entry.getKey();
				Object val = entry.getValue()==null?"":entry.getValue();
				httpRequest.setHeader(key.toString(), val.toString());
			}
			/* 发送请求并等待响应 */
			 httpResponse = httpClient.execute(httpRequest);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				strResult = EntityUtils.toString(httpResponse.getEntity(), charset);
			} else {
				strResult = "Error Response: "
						+ httpResponse.getStatusLine().toString();
			}
			EntityUtils.consume(httpResponse.getEntity());
		} catch (ClientProtocolException ex) {
			__httpClient = null;
			ex.printStackTrace();
		} catch (IOException ex) {
			__httpClient = null;
			ex.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("http错处啦"+e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				httpResponse.close();
				httpResponse=null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(httpClient!=null){
				try {
					httpClient.close();
					httpClient=null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(httpRequest!=null)
			{
				httpRequest.releaseConnection();
				httpRequest=null;
			}
		}
		return strResult;
	}
	public static String doGet(String url, String charset){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//Assert.notNull(httpClient);
		CloseableHttpResponse httpResponse =null;
		
		String strResult = "doGetError";
		HttpGet httpRequest = new HttpGet(url);
		try {
			/* 发送请求并等待响应 */
		  httpResponse = httpClient.execute(httpRequest);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				strResult = EntityUtils.toString(httpResponse.getEntity(), charset);
			} else {
				strResult = "Error Response: "
						+ httpResponse.getStatusLine().toString();
			}
			EntityUtils.consume(httpResponse.getEntity());
		} catch (ClientProtocolException ex) {
			__httpClient = null;
			ex.printStackTrace();
		} catch (IOException ex) {
			__httpClient = null;
			ex.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("http错处啦"+e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				httpResponse.close();
				httpResponse=null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(httpClient!=null){
				try {
					httpClient.close();
					httpClient=null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(httpRequest!=null)
			{
				httpRequest.releaseConnection();
				httpRequest=null;
			}
		}
		return strResult;
	}

	public static String doJsonPost(String url,JSONObject json,String charset)
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		
		HttpPost httpRequest = new HttpPost(url);
		CloseableHttpResponse httpResponse=null;
		String strResult = "doPostError";
		try {
			/* 添加请求参数到请求对象 */
			httpRequest.setEntity(new StringEntity(json.toString()));
			/* 发送请求并等待响应 */
			 httpResponse = httpClient.execute(httpRequest);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				strResult = EntityUtils.toString(httpResponse.getEntity(),charset);

			} else {
				strResult = "Error Response: "
						+ httpResponse.getStatusLine().toString();
			}
			EntityUtils.consume(httpResponse.getEntity());
		} catch (Exception ex) {
			__httpClient = null;
			ex.printStackTrace();
		}
		finally
		{
			try {
				httpResponse.close();
				httpResponse=null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(httpClient!=null){
				try {
					httpClient.close();
					httpClient=null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(httpRequest!=null)
			{
				httpRequest.releaseConnection();
				httpRequest=null;
			}
		}
		return strResult;
	}


	private static String inputStreamToString(InputStream in)
			throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}
public static  void main(String[] args) throws ClientProtocolException, IOException
{
	CloseableHttpClient httpClient = HttpClients.createDefault();
	HttpPost httpRequest = new HttpPost("http://www.facebook.com");
	RequestConfig defaultRequestConfig = RequestConfig.custom()
			.setSocketTimeout(5000)
			.setConnectTimeout(5000)
			.setConnectionRequestTimeout(5000)
			.build();
	httpRequest.setConfig(defaultRequestConfig);
	long starttime=System.currentTimeMillis();
	CloseableHttpResponse response=null;
	try {
		response=httpClient.execute(httpRequest);	
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	long end=System.currentTimeMillis();
	System.out.println(end-starttime);
	
}

/**
 * 解析Http响应结果，读出Stream流将结果转换为文本
 * 
 * @author jerry
 * @param response
 * @return InputStream
 * @throws ParseResponseFailException
 * @throws IOException
 */
public static String parseHttpResponseString(HttpResponse response)
		throws Exception {
	HttpEntity entity = response.getEntity();
	InputStream in = null;
	StringBuffer result = new StringBuffer();
	InputStreamReader isr = null;
	// new String(response.getResponseBodyAsString().getBytes("gb2312"));
	try {

		if (entity != null) {
			in = entity.getContent();
		}
		if (in != null) {
			isr = new InputStreamReader(in, "utf-8");
			char[] cha = new char[1024];
			int i = 0;
			while ((i = isr.read(cha)) > 0) {
				result.append(new String(cha, 0, i));
			}
		}
		// byte[] b = new byte[215];
		// int i = 0;
		// while ((i = in.read(b)) > 0) {
		// result.append(new String(b, 0, i));
		// }
	} catch (Exception e) {
		throw new ServiceException("get,error!response={" + response + "}");
	} finally {
		if (isr != null) {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
		if (in != null) {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}
	return result.toString();
}

public static HttpResponse doPostJson(String url, String str)
		throws ServiceException {
	HttpResponse response = null;
	if (url == null || url.length() <= 0) {
		throw new ServiceException("Url can not be null.");
	}
	String temp = url.toLowerCase();
	if (!temp.startsWith("http://") && !temp.startsWith("https://")) {
		url = "http://" + url;
	}
	URI uri = URI.create(url);
	url = uri.toASCIIString();
	HttpPost httpMethod = null;
	try {
		httpMethod = new HttpPost(url);
		StringEntity entity = new StringEntity(str, CHARSET);
		httpMethod.setEntity(entity);
		httpMethod.addHeader("content-type",
				"application/json;charset=UTF-8");
		response = SingletonHttpclient.getHttpClient().execute(httpMethod);
	} catch (Exception e) {
		if (httpMethod != null) {
			httpMethod.abort();
		}
		throw new ServiceException("post,error!url={" + url + "}");
	}
	return response;
}

public static String doJsonPost(String url, String str) throws Exception {
	HttpResponse response = null;
	if (url == null || url.length() <= 0) {
		throw new ServiceException("Url can not be null.");
	}
	String temp = url.toLowerCase();
	if (!temp.startsWith("http://") && !temp.startsWith("https://")) {
		url = "http://" + url;
	}
	URI uri = URI.create(url);
	url = uri.toASCIIString();
	HttpPost httpMethod = null;
	try {
		httpMethod = new HttpPost(url);
		StringEntity entity = new StringEntity(str, CHARSET);
		httpMethod.setEntity(entity);
		httpMethod.addHeader("content-type",
				"application/json;charset=UTF-8");
		response = SingletonHttpclient.getHttpClient().execute(httpMethod);
	} catch (Exception e) {
		if (httpMethod != null) {
			httpMethod.abort();
		}
		throw new ServiceException("post,error!url={" + url + "}");
	} finally {
		if (httpMethod != null) {
			httpMethod.abort();
		}
	}
	return parseHttpResponseString(response);
}

/**
 * 根据城市名获取经纬度(解决乱码)
 * 
 * @param name
 * @return
 */
public static String getLocation(String name) {

	try {
		HttpURLConnection huc = (HttpURLConnection) new URL(
				"http://api.map.baidu.com/geocoder/v2/?address=" + name
						+ "&output=json&ak=mrlpItoAGsgnpNyDXHZFuHDB")
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

public static Map<String, String> toMap(String json) {
	org.json.JSONObject jsonObject;
	Map<String, String> result = null;
	try {
		jsonObject = new org.json.JSONObject(json);
		result = new HashMap<String, String>();
		Iterator iterator = jsonObject.keys();
		String key = null;
		String value = null;

		while (iterator.hasNext()) {

			key = (String) iterator.next();
			value = jsonObject.getString(key);
			result.put(key, value);

		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return result;
}
public static String doGetCheryHeader(String url, String charset,StringBuffer cookie) {
	CloseableHttpClient httpClient = createHttpClient();
	String strResult = "doGetError";

	try {
		 HttpGet httpRequest = new HttpGet(url);			 
		 httpRequest.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		 httpRequest.setHeader("Accept-Encoding","gzip, deflate");
		 httpRequest.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		 httpRequest.setHeader("Connection","keep-alive");
		 if(StringUtils.isNotBlank(cookie.toString()))
		 {
			 httpRequest.setHeader("Cookie",cookie.toString());	 
		 }
		
		 httpRequest.setHeader("Host","weixin.sogou.com");
		 httpRequest.setHeader("Referer"," http://weixin.sogou.com/");
		 httpRequest.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0");
		/* 发送请求并等待响应 */
		HttpResponse httpResponse = httpClient.execute(httpRequest);
		/* 若状态码为200 ok */
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			/* 读返回数据 */
			strResult = EntityUtils.toString(httpResponse.getEntity());
			if(StringUtils.isEmpty(cookie.toString()))
			{					
				try {
					Header[] headers=httpResponse.getHeaders("Set-Cookie");
					String headervalues ="";
					if(headers!=null&&headers.length>0)
					{							
						for (Header header : headers) {
							headervalues=header.getValue().replaceAll("[\\[\\]]", "");
							cookie.append(headervalues.substring(0, headervalues.indexOf(";")+1));
						}
					}	
				} catch (Exception e) {
					
				}
				
			}
		
		
		} else {
			strResult = "Error Response: "
					+ httpResponse.getStatusLine().toString();
		}
	} catch (ClientProtocolException ex) {
		__httpClient = null;
		ex.printStackTrace();
	} catch (IOException ex) {
		__httpClient = null;
		ex.printStackTrace();
	}
	catch (Exception e) {
		System.out.println("http错处啦"+e.getMessage());
		e.printStackTrace();
	}
	return strResult;
}
public static Header[]   getheaders(String url, String charset,String cookie,String purl){
	CloseableHttpClient httpClient = HttpClients.createDefault();
	//Assert.notNull(httpClient);
	long startTime=System.currentTimeMillis();
	Header[] headers =null;
	String strResult = "doGetError";

	try {
		 HttpGet httpRequest = new HttpGet(url);			 
		 httpRequest.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		 httpRequest.setHeader("Accept-Encoding","gzip, deflate");
		 httpRequest.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		 httpRequest.setHeader("Connection","keep-alive");
		 httpRequest.setHeader("Cookie",cookie);
		 httpRequest.setHeader("Host","weixin.sogou.com");
		 httpRequest.setHeader("Referer",purl);
		 httpRequest.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0");
		/* 发送请求并等待响应 */
	      HttpParams params = new BasicHttpParams();
            params.setParameter("http.protocol.handle-redirects", false); // 默认不让重定向
                                                                            // 这样就能拿到Location头了
            httpRequest.setParams(params);
	
		HttpResponse httpResponse = httpClient.execute(httpRequest);		    
		/* 若状态码为200 ok */
		if (httpResponse.getStatusLine().getStatusCode() == 302) {
			/* 读返回数据 */
		headers=httpResponse.getHeaders("Location");
		} else {
			strResult = "Error Response: "
					+ httpResponse.getStatusLine().toString();
		}
	} catch (ClientProtocolException ex) {
		__httpClient = null;
		ex.printStackTrace();
	} catch (IOException ex) {
		__httpClient = null;
		ex.printStackTrace();
	}
	catch (Exception e) {
		System.out.println("http错处啦"+e.getMessage());
		e.printStackTrace();
	}
	return headers;
}

}

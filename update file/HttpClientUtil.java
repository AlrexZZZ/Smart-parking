package cn.shb.test;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.meterware.httpunit.WebClient;
public class HttpClientUtil {

	    public static String getWeChatUserInfo(String url){
	        String result="";
	          try {
	                // 根据地址获取请求
	                HttpGet request = new HttpGet(url);//这里发送get请求
	                // 获取当前客户端对象
	                HttpClient httpClient = new DefaultHttpClient();
	                // 通过请求对象获取响应对象
	                HttpResponse response = httpClient.execute(request);
	                
	                // 判断网络连接状态码是否正常(0--200都数正常)
	                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	                    result= EntityUtils.toString(response.getEntity(),"utf-8");
	                } 
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        return result;
	        //....result是用户信息,站内业务以及具体的json转换这里也不写了...
	    }
private static void man() {
	Map<String, String> cookies = null;
	cookies = new HashMap<String, String>();  
cookies.put("ASP.NET_SessionId", "nvu1p5552jwn2xbhn1a5dv45");  

try {
	 Jsoup.parse(new URL("").openStream(), "GBK", "");
	Document doc = Jsoup.connect("http://www.dsat.gov.mo/dsat/carpark_realtime_core.aspx").cookies(cookies).get();
	 Elements linksElements = doc.select("table[class=myTable]>tbody>tr");
	 for (Element ele:linksElements) {
		 String parkName = ele.select(">td[class=MainContentText style2]>div").text();
		 System.out.println(parkName);
	 }
	// System.out.println(linksElements.size());
	//System.out.println(linksElements.toString());
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} }
public static void main(String[] args) {
	
//man();
System.out.println(getWeChatUserInfo("http://www.dsat.gov.mo/dsat/carpark_realtime_core.aspx"));

}
}
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
	                // ���ݵ�ַ��ȡ����
	                HttpGet request = new HttpGet(url);//���﷢��get����
	                // ��ȡ��ǰ�ͻ��˶���
	                HttpClient httpClient = new DefaultHttpClient();
	                // ͨ����������ȡ��Ӧ����
	                HttpResponse response = httpClient.execute(request);
	                
	                // �ж���������״̬���Ƿ�����(0--200��������)
	                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	                    result= EntityUtils.toString(response.getEntity(),"utf-8");
	                } 
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        return result;
	        //....result���û���Ϣ,վ��ҵ���Լ������jsonת������Ҳ��д��...
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
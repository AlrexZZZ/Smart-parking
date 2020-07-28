package cn.shb.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import org.apache.http.HttpEntity;
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

/**
 * ����http://www.dsat.gov.mo/dsat/carpark_realtime.aspx
 * �еı��⡢���ڡ���Դ������
 * @author Administrator
 * 
 */
public class Jsoup_HttpClient_Test {
    public static void main(String[] args) throws Exception {
    	while(true){
        //��һ��������urlʹ��httpclient��ȡҳ����Ϣ������getHtmlByUrl();
        String html = getHtmlByUrl("http://www.dsat.gov.mo/dsat/carpark_realtime_core.aspx").replace("&nbsp;", "");
        if (html!=null&&!"".equals(html)) {
            //�ڶ�����ʹ��jsoup����html����ȡ����
            Document doc =  Jsoup.parse(html);
            String docStr = doc.toString();  
            Elements linksElements = doc.select("table[class=myTable]>tbody>tr");
            System.out.println("*******************************************************************");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd mm:hh:ss");
            int i=1;
      /*      //1.��ɾ������
            DBTools.delRecord();*/
            //2.��д���µ�����
            for (Element ele:linksElements) {
            	 i++;
            	 String parkName = ele.select(">td[class=MainContentText style2]>div[align=center]").text();
            	 String carNum = ele.select(">td[class=MainContentText style2]>div[align=left]").text();
            	 String motro = ele.select(">td[width=115]").text();
            	 String updateTime = ele.select(">td[width=171]>div[align=center]").text();
            	 if(!"��ͣ�l��ӍϢ".contains(updateTime)){
            		 System.out.println(parkName+":"+carNum+":"+motro+":"+sdf.parse(updateTime));
            		 DBTools.addData(parkName, carNum, i);
            	 }
            	 
            }
        }
        Thread.currentThread().sleep(1000*11);
    }
    	
    }
    /**
     * ����URL������е�html��Ϣ
     * @param url
     * @return
     */
    public static String getHtmlByUrl(String url){
        String html = null;
        //����httpClient����
        HttpClient httpClient = new DefaultHttpClient();
        //��get��ʽ�����URL
        HttpGet httpget = new HttpGet(url);
        
      
httpget.setHeader("Cookie", "ASP.NET_SessionId=nvu1p5552jwn2xbhn1a5dv45");
        /*httpget.setHeader("Host", "www.dsat.gov.mo");
        httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");*/
        try {
            //�õ�responce����
            HttpResponse responce = httpClient.execute(httpget);
            
            //������
            int resStatu = responce.getStatusLine().getStatusCode();
            if (resStatu==HttpStatus.SC_OK) {//200����  �����Ͳ���
               //���������
                // modify by cheally InputStream entity = responce.getEntity().getContent();
            	HttpEntity entity =	responce.getEntity();
                if (entity!=null) {
                    //ͨ��������תΪ�ַ������htmlԴ����  ע�����Ի��ʵ�壬Ȼ��ͨ�� EntityUtils.toString�������html
                	//�����п��ܳ������룬�����������������ַ�ʽ
               // modify by cheally     html=getStreamString(entity);
                	
                	html = EntityUtils.toString(responce.getEntity(), "utf-8");
                    // System.out.println(html);
                }
            	
            	
            }
        } catch (Exception e) {
            System.out.println("���ʡ�"+url+"�������쳣!");
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return html;
    }

    /**
    * ��һ��������ת��Ϊ�ַ���
    */
    public static String getStreamString(InputStream tInputStream){
        if (tInputStream != null){
        try{
	        BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(tInputStream));
	        StringBuffer tStringBuffer = new StringBuffer();
	        String sTempOneLine = new String("");
        while ((sTempOneLine = tBufferedReader.readLine()) != null){
                tStringBuffer.append(sTempOneLine+"\n");
        }
            return tStringBuffer.toString();
        }catch (Exception ex){
            ex.printStackTrace();
        }
       }
         return null;
    }
}
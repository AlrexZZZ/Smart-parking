package com.nineclient.weixin;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.nineclient.web.WccWxController;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WxFileUtil {

	/**
	 * 模拟form表单的形式 ，上传文件 以输出流的形式把文件写入到url中，然后用输入流来获取url的响应
	 * 
	 * @param url
	 *            请求地址 form表单url地址
	 * @param filePath
	 *            文件在服务器保存路径
	 * @return String url的响应信息返回值
	 * @throws IOException
	 */
	public static String send(String url, String filePath) throws IOException {

		String result = null;

		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}

		/**
		 * 第一部分
		 */
		URL urlObj = new URL(url);
		// 连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		/**
		 * 设置关键值
		 */
		con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存

		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
				+ BOUNDARY);

		// 请求正文信息

		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
				+ file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		// 获得输出流
		OutputStream out = con.getOutputStream();
		// 输出表头
		out.write(head);

		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
			throw new IOException("数据读取异常");
		} finally {
			if (reader != null) {
				reader.close();
			}
         con.disconnect();
		}

		// JSONObject jsonObj = JSONObject.fromObject(result);
		// String mediaId = jsonObj.getString("media_id");
		return result;
	}
public static String sendFileT(String url,String filePath) throws IOException
{
	String returnstr="";
	HttpPost httpPost = new HttpPost(url);
	httpPost.setHeader("User-Agent","SOHUWapRebot");
	httpPost.setHeader("Accept-Language","zh-cn,zh;q=0.5");
	httpPost.setHeader("Accept-Charset","GBK,utf-8;q=0.7,*;q=0.7");
	httpPost.setHeader("Connection","keep-alive");
	CloseableHttpClient httpClient=null;
	CloseableHttpResponse  httpResponse =null;
	try {
		httpClient=HttpClients.createDefault();
		MultipartEntity mutiEntity = new MultipartEntity();
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");	}
		mutiEntity.addPart("desc",new StringBody(file.getName(), Charset.forName("utf-8")));
		mutiEntity.addPart("pic", new FileBody(file));
		httpPost.setEntity(mutiEntity);
	    httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity =  httpResponse.getEntity();
		returnstr = EntityUtils.toString(httpEntity);
		EntityUtils.consume(httpEntity);
	} catch (Exception e) {
		// TODO: handle exception
	}
	finally
	{
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
		if(httpPost!=null)
		{
			httpPost.releaseConnection();
			httpPost=null;
		}
	}
	return returnstr;
}

	/**
	 * 保存网络图片至本地
	 * 
	 * @param fileUrl
	 * @param savePath
	 * @return
	 */
	public static boolean saveUrlAs(String fileUrl, String savePath)/* fileUrl网络资源地址 */
    {
 
        try {
            /* 将网络资源地址传给,即赋值给url */
            URL url = new URL(fileUrl);
             
            /* 此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流 */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            DataInputStream in = new DataInputStream(connection.getInputStream());
             
            /* 此处也可用BufferedInputStream与BufferedOutputStream  需要保存的路径*/
            DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath));
             
             
            /* 将参数savePath，即将截取的图片的存储在本地地址赋值给out输出流所指定的地址 */
            byte[] buffer = new byte[4096];
            int count = 0;
            while ((count = in.read(buffer)) > 0)/* 将输入流以字节的形式读取并写入buffer中 */
            {
                out.write(buffer, 0, count);
            }
            out.close();/* 后面三行为关闭输入输出流以及网络资源的固定格式 */
            in.close();
            connection.disconnect();
            return true;/* 网络资源截取并存储本地成功返回true */
 
        } catch (Exception e) {
            return false;
        }
    }
	
	/**
	 * 
	 * 删除文件
	 * 
	 * @param sPath
	 * @return
	 */
	public static boolean deleteFile(String sPath) {  
	   boolean flag = false;  
	   File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  
	
	/**
	 * 
	 * 将本地或网络图片替换为腾讯端图片
	 * 
	 * @param content
	 * @param request
	 * @param accessToken
	 * @return
	 */
	public static String replaceImgSrc(String content, HttpServletRequest request,String accessToken){
		Pattern p = Pattern
				.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");// <img[^<>]*src=[\'\"]([0-9A-Za-z.\\/]*)[\'\"].(.*?)>");
		Matcher m = p.matcher(content);
//		System.out.println(m.find());
//		System.out.println(m.groupCount());
		while (m.find()) {
			String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token="+accessToken;
			String ctxPath = request.getSession().getServletContext().getRealPath("");//服务器路径
			if(!m.group(1).startsWith("http")){
				// 上传图片 获取微信端url
				String returnStr = "";
				try {
					returnStr = WxFileUtil.sendFileT(url, ctxPath+ m.group(1).substring(4));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(!"".equals(returnStr)){
					 JSONObject jsonObj = JSONObject.fromObject(returnStr);
					 String reUrl = jsonObj.getString("url");
					 content =content.replace(m.group(1), reUrl);
				}
			}else{
				//如果网络图片，先进行下载，再重新上传至微信端服务器
				
				String Suffix = "."+m.group(1).substring(m.group(1).lastIndexOf("=")+1);
				
				String fileName=System.currentTimeMillis()+Suffix;
				boolean b = WxFileUtil.saveUrlAs(m.group(1), ctxPath+"/attached/"+fileName);
				if(b){
					String returnStr = "";
					try {
						returnStr = WxFileUtil.send(url, ctxPath+"/attached/"+fileName);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(!"".equals(returnStr)){
						 JSONObject jsonObj = JSONObject.fromObject(returnStr);
						 if(jsonObj.containsKey("url")){
							 String reUrl = jsonObj.getString("url");
							 content =content.replace(m.group(1), reUrl);
						 }
					}
					//删掉临时下载
					WxFileUtil.deleteFile(ctxPath+"/attached/"+fileName);
				}
			}
//			System.out.println(m.group(1));
		}
		return content;
	};
	
	
	
	
	public static void main(String[] args) throws IOException {
//		//本地文件上传
//		String filePath = "D:/tomcat/apache-tomcat-6.0.37/webapps/Allianture_frame/upload/test3_20140117094014814.jpg";
//		String sendUrl = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=yeQEu_OrXQanGC_G56BZ7IKJDCQCaO0ryDWKX2N2JDzGRGuiZACTGjsQXW-S-K1fgm_MViG_R5AwIEBhKKCNmUevg0H3ksfzlIfkFcP1y8g2st2LYwloL_iPqhedlT5_Z1zSM2mZSmu6cI54sayMPw&type=image";
//		String result = null;
//		result = send(sendUrl, filePath);
//		System.out.println(result);
		
        //网络图片下载
		
        //需要下载的URL
        String photoUrl = "https://mmbiz.qlogo.cn/mmbiz/gHTFP31KYWibSRCHe8CDhBJ3V4NTmITYWcxxn79m2xGpX81Yp6fpNNZtd8nqsibPIjd22KzHjiaBgN2TetsibAIBng/0?wx_fmt=gif";
//        // 截取最后/后的字符串
//        String fileName = photoUrl.substring(photoUrl.lastIndexOf("=")+1);
        String Suffix = "."+photoUrl.substring(photoUrl.lastIndexOf("=")+1);
		
		String fileName=System.currentTimeMillis()+Suffix;
//        String fileName="https://mmbiz.qlogo.cn/mmbiz/gHTFP31KYWibSRCHe8CDhBJ3V4NTmITYWcxxn79m2xGpX81Yp6fpNNZtd8nqsibPIjd22KzHjiaBgN2TetsibAIBng/0?wx_fmt=gif";
//        //图片保存路径
        String filePaths = "D:/cherry_ump/ump/src/main/webapp/attached/";
//        /* 调用函数，并且进行传参 */
        boolean flag = saveUrlAs("https://mmbiz.qlogo.cn/mmbiz/gHTFP31KYWibSRCHe8CDhBJ3V4NTmITYWcxxn79m2xGpX81Yp6fpNNZtd8nqsibPIjd22KzHjiaBgN2TetsibAIBng/0?wx_fmt=gif", filePaths +fileName);
   
        System.out.println("Run ok!\n Get URL file " + flag);
        System.out.println(filePaths);
        System.out.println(fileName);
	}
}

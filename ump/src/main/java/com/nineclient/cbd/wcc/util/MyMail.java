package com.nineclient.cbd.wcc.util;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/** 
 * PS:发送邮箱请设置为支持smtp的，如新浪等
 * 需要用到2个jar包 mail.jar和activation.jar
 */
public class MyMail
{
	String to_mail ;
	String to_title ;
	String to_content ;
	InternetAddress from;
	Session s;
	MimeMessage message;
	String FileName = null;
	
	public void setFileName(String fileName)
	{
		FileName = fileName;
	}

	public MyMail()
	{
		
	}
	
	public void setTo_mail(String to_mail)
	{
		this.to_mail = to_mail;
	}

	public void setTo_title(String to_title)
	{
		this.to_title = to_title;
	}

	public void setTo_content(String to_content)
	{
		this.to_content = to_content;
	}

	public void send()
	{
		//发送邮件
		Transport transport;
		try
		{
			transport = s.getTransport("smtp");
			transport.connect("smtp.sina.com", "foxtext@sina.com", "qwertyuiop");//设置发送者的邮箱账号与密码
			transport.sendMessage(message, message.getAllRecipients());//发送邮件,其中第二个参数是所有已设好的收件人地址
			transport.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void init(String content)
	{
		Properties props = new Properties();//也可用Properties props = System.getProperties(); 
		props.put("mail.smtp.host", "smtp.sina.com");//存储发送邮件服务器的信息
		props.put("mail.smtp.auth", "true");//同时通过验证
		s = Session.getInstance(props);//根据属性新建一个邮件会话
		s.setDebug(true);
		message = new MimeMessage(s);//由邮件会话新建一个消息对象
		//设置邮件		
		try
		{
			from = new InternetAddress(
					"foxtext@sina.com");
			message.setFrom(from);//设置发件人
			InternetAddress to = new InternetAddress(to_mail);//设置收件人
			message.setRecipient(Message.RecipientType.TO, to);//设置收件人,并设置其接收类型为TO
			message.setSubject(to_title);//设置主题
			message.setSentDate(new Date());//设置发信时间
			Multipart mm = new MimeMultipart();
//---------------------------------------文本处理-----------------------------------------------------
			if(to_content != null || !to_content.equals(""))
			{				
				BodyPart mdp = new MimeBodyPart();
				mdp.setContent(to_content,"text/html; charset=gb2312");
				DataHandler dh = new DataHandler(content,"text/plain; charset=gb2312");		
				mdp.setDataHandler(dh);
				mm.addBodyPart(mdp);	
			}
			
			
//----------------------------------------附件处理----------------------------------------------------
			if(FileName != null && !FileName.equals(""))
			{				
				BodyPart mdp = new MimeBodyPart();
				FileDataSource fds  =  new FileDataSource(FileName);
				DataHandler dh = new DataHandler(fds);			
				mdp.setFileName("fj.txt");//设置附件名称
				mdp.setDataHandler(dh);
				mm.addBodyPart(mdp);					
			}
			message.setContent(mm);		
			message.saveChanges();//存储邮件信息
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void sendEmail(String content,String title,String email){
		MyMail mail = new MyMail();
		mail.setTo_content(content);
		mail.setTo_title(title);
		mail.setTo_mail(email);
	 // mail.setFileName("c:\\1.txt");
		mail.init(content);
		mail.send();
	}
	public static void main(String[] args)
	{
		MyMail mail = new MyMail();
		mail.setTo_content("内容11212");
		mail.setTo_title("标题1111");
		mail.setTo_mail("318877671@qq.com");
		// mail.setFileName("http\://qycloud.ngrok.cc\/ump\/attached\/wxQrcode\/1465898228850.jpg");
		mail.init("您好，请查看您的地址图片：http://qycloud.ngrok.cc/ump/attached/wxQrcode/1465898228850.jpg");
		mail.send();
	}
}

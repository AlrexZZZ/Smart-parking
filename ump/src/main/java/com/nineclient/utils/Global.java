package com.nineclient.utils;

public class Global {

	/**
	 * ADMIN 展现的菜单
	 */
	public static final String ADMIN_USER = "admin";
	public static final String ADMIN_PASSWORD = "e10adc3949ba59abbe56e057f20f883e";
	public static final String ADMIN_CODE ="9";
	public static final String LANG ="zh_CN";
	public static final String UMP_LOGIN_OPERATOR ="umpOperator";
	public static final String PUB_LOGIN_OPERATOR ="pubOperator";
	public static final String[] NAME_ID_JSON_ARRARY={"name","id"};
//	public static final String Url = "http://emp.tunnel.mobi";
	public static final String Url ="http://jsp.fjpc007.top";
//	public static final String Url ="http://tlbank021.9client.com";
	
	public static final String voc = "VOC";
	public static final Long VOC = 2l;
	public static final Long UCC = 4l;
	public static final Long WCC = 3l;
	public static final String TMALL_CALL="https://oauth.taobao.com/authorize";
	public static final String TMALL_CODE="code";
	public static final String IMAGE_QRCOED_PATH="/attached/wxQrcode/";
	public static final String IMAGE_QRCOED_FULL_PATH="http://jsp.fjpc007.top";
//	public static final String IMAGE_QRCOED_FULL_PATH="http://tlbank021.9client.com/ump";
	public static final String token = "samir";
	public static final String T_MALL_VOCACCOUNT="VOCACCOUNT";
	public static final String T_MALL_VOCAPPKEY="VOCAPPKEY";
	public static final String EMAIL = "jade.lin@9client.com";
	public static final String DOWNLOAD_BASE_PATH ="/uploads/excel";
	public static final String DATA_INTERFACE_URI="http://127.0.0.1:8088/dataService/webservice/webinterface?wsdl";
	public static final String UCCURL="http://127.0.0.1:8080/JtalkManager/resteasy/Ucc2OtherService/Service";
	 // public static final String REDIS_STRING="127.0.0.1:6379";
    public static final String REDIS_STRING="127.0.0.1:6379";
    public static final String massMessPath="http://127.0.0.1:8080/tailong_wccmass/";
	/**
	 * VOC 新增关键词队列
	 */
	public static final String VOC_NEW_KEY_WORD = "VOC_NEW_KEY_WORD_QUEUE";
	
	
	 public static String targetflag="_sendtarget";//发送目标
	 public static String contentflag="_sendcotent";//发送内容
	 public static String odlerflag="OLDERID";
	 public static String resendflag="_resend";
	 public static String messtype="messagetype";
	 public static String sendcontent="sendcontent";
	 public static String hnairappid="HNAIRAPPID";
	
//	public Global(){
//		String ip=PropertyConfig.getProperty("weixing", "9client");
//	 	this.Url="http://"+ip;
//	 	this.IMAGE_QRCOED_FULL_PATH="http://"+ip+"/ump";
//	}
//	public static void main(String[] args) {
//		//new Global();
//		System.out.println(Url);
//		System.out.println(IMAGE_QRCOED_FULL_PATH);
//	}
}

package com.nineclient.utils;

import java.util.Random;

public class RadomStr {

	public static String getRandomString(int length) { //length��ʾ����ַ�ĳ���  
	    String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIKLMNOPQRSTUVWXYZ0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number)); 
	    }     
	    return sb.toString();     
	 } 
	public static String getRandomNum(int length) { //length��ʾ����ַ�ĳ���  
	    String base = "123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number)); 
	    }     
	    return sb.toString();     
	 } 
}

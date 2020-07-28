/**
 * <pre>
 * 上海xxxxxxx
 * Copyright (C): 2012
 * 
 * 文件名称：
 * PropertyConfig.java
 * 
 * 文件描述:
 * 配置属性操作类。
 * 
 * Notes:
 * 
 * 修改历史(作者/日期/改动描述):
 * 王彬/2012.04.15/初始化版本。
 * </pre>
 */
package com.nineclient.cbd.wcc.constant;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;



public class PropertyConfig {
	private static String default_config = "/url.properties";
	private static Properties mConfig;
	static {
		mConfig = new Properties();
		try {
			//InputStream is = Object.class.getResourceAsStream(default_config);
			InputStream is =
					new BufferedInputStream(new FileInputStream(System. getProperty ("user.dir")+"/src/main/resources/META-INF/spring/connfig.properties"));
			mConfig.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key) {
		return mConfig.getProperty(key);
	}
	
	public static String getProperty(String key, String defaultValue) {
		String value = mConfig.getProperty(key);
		if (value == null)
			return defaultValue;
		
		return value;
	}
	
	public static boolean getBooleanProperty(String name, boolean defaultValue) {
		String value = PropertyConfig.getProperty(name);
		
		if (value == null)
			return defaultValue;
		
		return (new Boolean(value)).booleanValue();
	}
	
	public static int getIntProperty(String name) {
		return getIntProperty(name, 0);
	}
	
	public static int getIntProperty(String name, int defaultValue) {
		String value = PropertyConfig.getProperty(name);
		
		if (value == null)
			return defaultValue;
		
		return (new Integer(value)).intValue();
	}
	
}

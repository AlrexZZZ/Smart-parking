package com.nineclient.utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.RQueue;
import org.redisson.core.RSet;

/**
 * redis 操作相关
 * 
 * @author 9client
 *
 */
public class RedisCommonHelper {
	private Redisson redisson = null;

	private static RedisCommonHelper redisCommonHelper = null;

	public static RedisCommonHelper getInstance() {
		if (null == redisCommonHelper) {
			redisCommonHelper = new RedisCommonHelper();
		}
		return redisCommonHelper;
	}

	private RedisCommonHelper() {
		createRedis();
	}

	void createRedis() {
		Config config = new Config();
		String redisIpPort = "127.0.0.1:6379";
		String[] str = redisIpPort.split(";");
		if (null != str) {
		   for (String ip : str) {
			  if(null != ip && !"".equals(ip)) {
			    config.addAddress(ip);
			  }
		   }
			try {
				redisson = Redisson.create(config);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 重新连接
	 */
	public void reConnect() {
		if (null != redisson) {
			redisson.shutdown();
		}
		createRedis();
	}

	public Redisson getRedisson() {
		return redisson;
	}

	public ConcurrentMap<String, String> getUniqueCommentsMap(String key) {
		ConcurrentMap<String, String> map = null;
		if (null != redisson) {
			try {
				map = redisson.getMap(key);
			} catch (Exception e) {
				reConnect();
				if (null != redisson) {
					map = redisson.getMap(key);
				}
			}
		} else {
			reConnect();
			map = redisson.getMap(key);
		}
		return map;
	}


	public RQueue<Object> getTaskQueue(String key) {
		RQueue<Object> que = null;
		boolean flag = true;
		while (flag) {
			try {
				que = this.redisson.getQueue(key);
				flag = false;
			} catch (Exception e) {
				e.printStackTrace();
				reConnect();

			}
		}
		return que;
	}
	
	  public List<String> getList(String key)
	   {   List<String> list=null;
		   if(null!=redisson)
		   {
			   try {
				   list=redisson.getList(key);
			} catch (Exception e) {
			
				reConnect();
				if(null != redisson){
					 list=redisson.getList(key);
				}
			}
			  
		   }
		   else {
			   reConnect();
			   list=redisson.getList(key);
		}
		   return list;
	   }
	   
		/**
		 * @return 获取发送内容的map
		 */
		public  ConcurrentMap<String, String> getMessType(String key){
			 ConcurrentMap<String, String>  map = null;
			if(null != redisson){
				try{
					 map = redisson.getMap(key);
				} catch (Exception e) {
					
					reConnect();
					if(null != redisson){
					  map = redisson.getMap(key);
					}
				}
			}else{
			  reConnect();
			  map = redisson.getMap(key);
			}
			return map;
		
		}
		public  ConcurrentMap<String, String> getMap(String key){
			 ConcurrentMap<String, String>  map = null;
			if(null != redisson){
				try{
					 map = redisson.getMap(key);
				} catch (Exception e) {
					
					reConnect();
					if(null != redisson){
					  map = redisson.getMap(key);
					}
				}
			}else{
			  reConnect();
			  map = redisson.getMap(key);
			}
			return map;
		}
	public static void main(String[] args) {
		
	}
	
	public  Map<String, Object> getNormalMap(String key)
	{
		Map<String, Object>  map = null;
		if(null != redisson){
			try{
				 map = redisson.getMap(key);
			} catch (Exception e) {
				
				reConnect();
				if(null != redisson){
				  map = redisson.getMap(key);
				}
			}
		}else{
		  reConnect();
		  map = redisson.getMap(key);
		}
		return map;
	}	

	public RSet<String>  getUniqueCommentsSet(String key){
		 RSet<String> set = null;
		 if(null != redisson){
			 try{
			   set = redisson.getSet(key);
			 }catch (Exception e) {
				reConnect();
			  if(null != redisson){
				set = redisson.getSet(key);
			  }
			}
		 }else{
		   reConnect();
		   set = redisson.getSet(key);
		 }
		 
		return set;
	}
	
}

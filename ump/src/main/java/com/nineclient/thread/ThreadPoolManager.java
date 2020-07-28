package com.nineclient.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * 定义一个线程池
 * @author Administrator
 *
 */
public class ThreadPoolManager {
/*private static ThreadPoolExecutor threadpool= new ThreadPoolExecutor(10, Integer.MAX_VALUE,
        0L, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<Runnable>());*/
	private static ExecutorService threadpool= Executors.newCachedThreadPool();

public static ExecutorService getThreadpool() {
	
	return threadpool;        
}

}

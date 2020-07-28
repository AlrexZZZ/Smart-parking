package com.nineclient.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nineclient.model.WccFriend;
import com.nineclient.model.WccMassMessage;
import com.nineclient.thread.massMessageTiming;

@Component
public class WccMassMessageTask{
	private String path = "";
	@Async
	@Scheduled(cron="0/60 * * * * ? ")//每60秒执行一次
	private void sendMassMessage(){
		boolean flag = false;
		Date time = new Date();
		List<WccMassMessage> massMsgs = WccMassMessage.findWccMassMessagesByState(1);
		for (WccMassMessage wccMassMessage : massMsgs) {
			Set<WccFriend> f = wccMassMessage.getFriends();
			if(null!=f){
				System.out.println("粉丝大小=================="+f.size());
			}
			flag = timeCheck(time,wccMassMessage.getInsertTime());
			if(flag){
				massMessageTiming massThread = new massMessageTiming();
				massThread.setMassMsg(wccMassMessage);
				massThread.start();
				System.out.println("定时群发=============="+wccMassMessage.getId());
			}else{
				System.out.println("过===================");
			}
		}
	}
	
	//判断两个时间是否在同一分钟
	private static boolean timeCheck(Date d1,Date d2){
		boolean flag = false;
		Calendar cal1 = Calendar.getInstance(); 
		Calendar cal2 = Calendar.getInstance(); 
		cal1.setTime(d1);
		cal2.setTime(d2);
		int year1 = cal1.get(Calendar.YEAR);
		int month1 = cal1.get(Calendar.MONTH);
		int day1 = cal1.get(Calendar.DATE);
		int hour1 = cal1.get(Calendar.HOUR_OF_DAY);
		int minute1	= cal1.get(Calendar.MINUTE);
		int year2 = cal2.get(Calendar.YEAR);
		int month2 = cal2.get(Calendar.MONTH);
		int day2 = cal2.get(Calendar.DATE);
		int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
		int minute2	= cal2.get(Calendar.MINUTE);
		System.out.println("year="+year1+"---"+year2);
		System.out.println("month="+month1+"---"+month2);
		System.out.println("day="+day1+"---"+day2);
		System.out.println("hour="+hour1+"---"+hour2);
		System.out.println("minute="+minute1+"---"+minute2);
		if(year1 == year2 && month1 == month2 && day1 == day2 && hour1 == hour2 && minute1 == minute2){
			flag = true;
		}
		return flag;
	}
}

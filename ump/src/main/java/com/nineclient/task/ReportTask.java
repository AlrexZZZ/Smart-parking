package com.nineclient.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



import com.nineclient.model.UmpCompany;
import com.nineclient.model.VocComment;
import com.nineclient.model.vocreport.ReportPurchaseMotive;
import com.nineclient.utils.DateUtil;
//    @Component
public class ReportTask {
	@Autowired
	private JdbcTemplate JdbcTemplate; 
//	
///*	产品热词分析      1天
//	产品问题排行榜TOP10   1天
//	SKU排行榜（好评、差评）  1天
//	好中差总量占比及趋势分析    1天
//	标签占比及趋势分析   1天
//	好中差时间分布趋势图    1小时
//	购买动机分析    1天
//	坐席工作量报表   1天
//*/
////	@Async
////    @Scheduled(cron="*/30 * * * * *")   //30秒任务  
//	public void test(){
//		String sql = "SELECT comment_level,COUNT(comment_level) FROM `voc_comment` GROUP BY comment_level";
//		List<Map<String, Object>> result = JdbcTemplate.queryForList(sql);
//		for (Map<String, Object> map : result) {
//			System.out.println(map.get("comment_level"));
//		}
////		System.out.println(result.);
////		JdbcTemplate.queryforlist
////		 List<HotWordReport> abc = JdbcTemplate.queryForList("select * from voc_comment",null,HotWordReport.class);
////		System.out.println(abc);
//		
//	}	
//	
	//坐席工作量报表 -完成

//	@Async
	@Scheduled(cron="* 0/1 12 * * *")   //每天零点任务  OK
	public void operWorkDay(){
  Date lastDay = DateUtil.getDateOfLastDay(Calendar.getInstance()).getTime();
	  List<Map<String, Object>> result = null;
		  
	String sql = "SELECT p.id,p.ump_channl_id,p.voc_brand_id,p.operator_name,c.reply_time,p.company,count(c.reply_operator) reply_num ,up.company_code"
				  +" FROM "
				  +" pub_operator p ,voc_comment c , ump_company up "
				  +" where  "
				  +" p.id = c.reply_operator and p.company = up.id  and up.is_visable = 1"
				  +" and DATE_FORMAT(c.reply_time,'%Y-%m-%d') = '"+DateUtil.getStrOfLastDay(Calendar.getInstance())+"' "
				  + " GROUP BY p.id ";
		 result = JdbcTemplate.queryForList(sql);
		 if(result !=null && result.size() > 0){
	for (Map<String, Object> map : result) {
	JdbcTemplate.execute(" insert into report_oper_count values(null,null,'"+map.get("company_code")+"','"+map.get("reply_time")+"','"+map.get("operator_name")+"','"+map.get("reply_num")+"',null,null)");
	}		 
 }
}	
	
	//	产品热词分析      1天
	@Async
	@Scheduled(cron="* * 0 * * *")   //每天零点任务  
//	@Scheduled(cron="0 0/5 * * * ?")   //30秒任务 
	public void hotWorkTaskDay(){
		
Date lastDay = DateUtil.getDateOfLastDay(Calendar.getInstance()).getTime();	
 String lastDayStr = DateUtil.formateDateToString(lastDay,"yyyy-MM-dd");
 List<Map<String, Object>> result =null;
   String sql = "select  h.channel_name, h.shop_name, h.brand_name, h.sku_name, up.company_code, COUNT(h.hot_words_name) hot_words_num, h.hot_words_name, "
 +" STR_TO_DATE(DATE_FORMAT(h.comment_time,'%Y-%m-%d'),'%Y-%m-%d') comment_time_date "
 +" from  hot_words_detail h,ump_company up "
 + " WHERE "
 + " h.company_id = up.id and up.is_visable = 1 "
 + " and DATE_FORMAT(h.comment_time,'%Y-%m-%d') = '"+lastDayStr+"'"
 + " GROUP BY   h.channel_name,h.shop_name,h.brand_name,h.sku_name,date_format(h.comment_time,'%Y-%m-%d'),up.company_code,comment_time_date ";
		 result = JdbcTemplate.queryForList(sql);
		 int i=0;
		 if(result != null && result.size() > 0) {
		 for (Map<String, Object> map : result)  {
JdbcTemplate.execute(" insert into report_hot_word "
+ "values(null,'"+map.get("brand_name")+"','"+map.get("channel_name")+"','"+map.get("comment_time_date")+"','"+map.get("company_code")+"',"
		+ "'"+map.get("hot_words_name")+"','"+map.get("hot_words_num")+"','"+map.get("shop_name")+"','"+map.get("sku_name")+"',null)");			
//		JdbcTemplate.execute(insertSql);	
          ++i;
System.out.println("热词分析数据插入第"+i+"条....");
		}
	}
}		
//	
//	
	//	产品问题排行榜top10      1天  完成
	@Async
//	@Scheduled(cron="0 0/1 * * * ?")   //30秒任务 
	@Scheduled(cron="* * 0 * * *")   //每天零点任务  
	public void productProblemTaskDay(){
		
Date lastDay = DateUtil.getDateOfLastDay(Calendar.getInstance()).getTime();	
List<Map<String, Object>> result =null;
		  
String sql = "select vc.id,up.id company_id, uc.channel_name,vp.name shop_name,vb.brand_name,vk.name sku_name, "
		+ " STR_TO_DATE(DATE_FORMAT(vc.comment_time,'%Y-%m-%d'),'%Y-%m-%d') comment_time , "
		+ " vc.comment_level, "
		+ " vt.name tag_name, "
        +" COUNT(vt.name) tag_num " 
        +" from ump_channel uc,voc_shop vp,voc_shop_voc_brands vbvs,voc_brand vb,voc_sku vk,voc_goods vg,voc_comment vc,voc_tags vt,voc_comment_voc_tags vcvt,ump_company up "
 +" where " 
 +" uc.id = vp.channel "
 +" and vp.id = vbvs.voc_shops "
 +" and vb.id = vbvs.voc_brands "
 +" and vb.id = vk.voc_brand "
 +" and vg.voc_sku = vk.id   "
 +" and vg.id = vc.goods "
 +" and vc.id = vcvt.voc_comments "
 +" and vcvt.voc_tags = vt.id "
 +" and  vt.company_id = up.id "
 +" and up.is_visable = 1 "
 +" and vc.comment_level = 2  "
 //+" and DATE_FORMAT(vc.comment_time,'%Y-%m-%d') = '2014-12-12' "
 +" GROUP BY up.id, uc.channel_name,vp.name,vb.brand_name,vt.name,vc.comment_level,DATE_FORMAT(vc.comment_time,'%Y-%m-%d') "
 +"  ORDER BY tag_num desc ";
		 result = JdbcTemplate.queryForList(sql);
		 if(result != null && result.size() > 0){
		for (Map<String, Object> map : result){
JdbcTemplate.execute(" insert into product_problem_rand_list "
+ "values(null,'"+map.get("brand_name")+"','"+map.get("channel_name")+"','"+map.get("tag_name")+"','"+map.get("comment_time")+"',"
		+ "'"+map.get("comment_level")+"','"+map.get("company_id")+"','"+map.get("tag_num")+"','"+map.get("shop_name")+"','"+map.get("sku_name")+"',null)");			
		}
    }	
 }
	

 
 
	//SKU排行榜（好评、差评）  1天  完成
	
	@Async
//	@Scheduled(cron="0 0/1 * * * ?")   //10分钟任务
	@Scheduled(cron="* * 0 * * *")   //每天零点任务  
	public void skutTop10TaskDay(){
Date lastDay = DateUtil.getDateOfLastDay(Calendar.getInstance()).getTime();	
String lastDayStr = DateUtil.formateDateToString(lastDay,"yyyy-MM-dd");
/**
 * 1.查询所有审核通过的公司的数据
 * 2.查询参数 日期[昨天凌点的日期]
 */
String sql = "select up.company_code,up.id company_id,vc.id ,uc.channel_name,vp.name shop_name,vk.name sku_name,vb.brand_name,STR_TO_DATE(DATE_FORMAT(vc.comment_time,'%Y-%m-%d'),'%Y-%m-%d') comment_time , " 
+ "  case when vc.comment_level = 0 THEN '好评' "
+ "      when vc.comment_level = 2 THEN '差评' "
+ " end  commet_type, "
+ " COUNT(vc.id) comment_num "
+ " from "
+ " ump_channel uc,voc_shop vp,voc_shop_voc_brands vbvs,voc_brand vb,voc_sku vk,voc_goods vg,voc_comment vc,voc_tags vt,voc_comment_voc_tags vcvt,ump_company up "
+ " where  "
+ " uc.id = vp.channel and vp.id = vbvs.voc_shops and vb.id = vbvs.voc_brands and vb.id = vk.voc_brand and vt.company_id = up.id and up.is_visable = 1 "
+ ""
//+ " and DATE_FORMAT(vc.comment_time,'%Y-%m-%d') = '"+lastDayStr+"'"
+ " and vg.voc_sku = vk.id  and vg.id = vc.goods and vc.id = vcvt.voc_comments and vcvt.voc_tags = vt.id and vc.comment_level <> 1  "
+ " GROUP BY uc.channel_name,vp.name,up.company_code,up.id,vk.name,vb.brand_name,vc.comment_level,DATE_FORMAT(vc.comment_time,'%Y-%m-%d') "
+ " ORDER BY company_code asc,sku_name,comment_num desc,comment_time asc,commet_type ";
List<Map<String, Object>> result = JdbcTemplate.queryForList(sql);

int i = 0;
if(result !=null && result.size() > 0){
		for (Map<String, Object> map : result){
			i++;
JdbcTemplate.execute(" insert into report_sku_range "
+ "values(null,'"+map.get("channel_name")+"','"+map.get("comment_time")+"','"+map.get("commet_type")+"','"+map.get("company_code")+"',"
		+ "'"+map.get("comment_num")+"','"+map.get("shop_name")+"','"+map.get("sku_name")+"',null)");			
	
System.out.println("********已经插入第"+i+"条数据********");
	}
  }
	}	
	
	
	
	//好中差重量占比 -天
	@Async
	@Scheduled(cron="* * 0 * * *")   //每天零点任务  
//	@Scheduled(cron="0 0/1 * * * ?")   //30秒任务 
public void commentLevelAnalyseDay(){
	Date lastDay = DateUtil.getDateOfLastDay(Calendar.getInstance()).getTime();	
	List<Map<String, Object>> result =null;
	String lastDayStr = DateUtil.getStrOfLastDay(Calendar.getInstance());
//	for(int j = 10;j <= 30 ; j++){
//		String s = "2015-01-";
//		 if(j < 10){
//			 s += "0"+j;
//		 }else{
//			 s += j+"";
//		 }
//		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^第"+j+"号数据开始插入^^^^^^^^^^^^^^^^^^^^^");
//		int u = 0;
				  String sql = " 	SELECT " 
			  			+" t0.channel_name,t0.shop_name,t0.brand_name,t0.sku_name, t0.company_code,  "
			  			+" 				   sum( case t0.commet_type when '好评'  then num else 0 end) as 'good', " 
			  			+" 				   sum( case t0.commet_type when '中评'  then num else 0 end) as 'better',  "
			  			+" 				   sum( case t0.commet_type when '差评'  then num else 0 end) as 'bad' "
			  			+"  from ( "
			  			+"  select   DISTINCT  uc.channel_name,vp.name shop_name,vb.brand_name,vk.name sku_name,vt.company_id,up.company_code,vc.comment_time,vc.comment_content,	 "
			  			+" 	case when vc.comment_level = 0 THEN '好评'  "
			  			+"  when vc.comment_level = 1 THEN '中评'  "
			  			+"  when vc.comment_level = 2 THEN '差评'  "
			  			+"  end  commet_type ,				  "
			  			+" 	1  num "
			  			+" 	from   "
			  			+" 	ump_channel uc,voc_shop vp,voc_shop_voc_brands vbvs,voc_brand vb,voc_sku vk,voc_goods vg,voc_comment vc,voc_tags vt,voc_comment_voc_tags vcvt,ump_company up " 
			  			+"  where uc.id = vp.channel and vp.id = vbvs.voc_shops and vb.id = vbvs.voc_brands and vb.id = vk.voc_brand  "
			  			+" 	and vg.voc_sku = vk.id  and vg.id = vc.goods and vc.id = vcvt.voc_comments and vcvt.voc_tags = vt.id and vt.company_id = up.id  "
			  			+"  and  DATE_FORMAT(vc.comment_time,'%Y-%m-%d') <=  '"+lastDayStr+"' "
+" ORDER BY vc.comment_content "
+" ) t0 "
+" GROUP BY t0.company_code,t0.channel_name,t0.shop_name,t0.brand_name,t0.sku_name "
+" ORDER BY t0.company_code,t0.channel_name,t0.shop_name,t0.brand_name,t0.sku_name ";	  
		
				 result = JdbcTemplate.queryForList(sql);
				 if(result != null && result.size() > 0){
				for (Map<String, Object> map : result){
					
		JdbcTemplate.execute(" insert into report_comment_ratio_trend_month "
		+ "values(null,'"+map.get("brand_name")+"','"+map.get("channel_name")+"',STR_TO_DATE('"+lastDayStr+"','%Y-%m-%d'),'"+map.get("better")+"',"
				+ "'"+map.get("company_code")+"','"+map.get("good")+"','"+map.get("bad")+"','"+map.get("shop_name")+"','"+map.get("sku_name")+"',null)");			
				}
			}
				 
//	}			 
}	
	

//好中差重量占比 -月
	 @Async
//	 @Scheduled(cron="0 0/1 * * * ?")   //5分钟任务 
	 @Scheduled(cron="* * 0 * * *")   //每天零点任务  
public void commmentLevelAnalyseMonth(){
Date lastDay = DateUtil.getDateOfLastDay(Calendar.getInstance()).getTime();	
List<Map<String, Object>> result =null;
			  String sql = " SELECT  t1.company_code,t1.channel_name,t1.shop_name,t1.brand_name,t1.sku_name,sum(t1.good) good,sum(t1.better) better,sum(t1.bad) bad "
			  		+ ",t1.comment_time from ( "
			  		+ "	SELECT  "
			  		+ "	 t0.channel_name,t0.shop_name,t0.brand_name,t0.sku_name, t0.company_code, "
			  		+ "						   sum( case t0.commet_type when '好评'  then tag_num else 0 end) as 'good', "
			  		+ "						   sum( case t0.commet_type when '中评'  then tag_num else 0 end) as 'better', "
			  		+ "						   sum( case t0.commet_type when '差评'  then tag_num else 0 end) as 'bad',"
			  		+ "              STR_TO_DATE(GROUP_CONCAT(SUBSTR(t0.comment_time,1,7),'-01'),'%Y-%m-%d') comment_time "
					+"	   from( "
					+ "	 select  uc.channel_name,vp.name shop_name,vb.brand_name,vk.`name` sku_name,vt.company_id,up.company_code, "
					+ "	 STR_TO_DATE(DATE_FORMAT(vc.comment_time,'%Y-%m-%d %H:%i:%S'),'%Y-%m-%d %H:%i:%S') comment_time , "
					+ "	 case when vc.comment_level = 0 THEN '好评' "
					+ "	  when vc.comment_level = 1 THEN '中评' "
					+ "	 when vc.comment_level = 2 THEN '差评' "
					+ "	 end  commet_type,vt.name tag_name,COUNT(vt.name) tag_num "
					+ "	 from  "
					+ "	ump_channel uc,voc_shop vp,voc_shop_voc_brands vbvs,voc_brand vb,voc_sku vk,voc_goods vg,voc_comment vc,voc_tags vt,voc_comment_voc_tags vcvt,ump_company up "
                    +"   where uc.id = vp.channel and vp.id = vbvs.voc_shops and vb.id = vbvs.voc_brands and vb.id = vk.voc_brand "
                    + "	and vg.voc_sku = vk.id  and vg.id = vc.goods and vc.id = vcvt.voc_comments and vcvt.voc_tags = vt.id and vt.company_id = up.id "
                    + "	and  DATE_FORMAT(vc.comment_time,'%Y-%m') =  '2015-01'  "
					+"  GROUP BY up.company_code,uc.channel_name,vp.name,vb.brand_name,vc.comment_level,DATE_FORMAT(vc.comment_time,'%Y-%m-%d') "
					+"  ORDER BY tag_num desc "
					+"  	  ) t0 "
					+"	 GROUP BY t0.company_code,t0.channel_name,t0.shop_name,t0.brand_name,t0.sku_name,t0.comment_time "
					+"	  ) t1 "
					+"	  GROUP BY t1.company_code,t1.channel_name,t1.shop_name,t1.brand_name,t1.sku_name,t1.comment_time "
					+" order  BY t1.company_code,t1.channel_name,t1.shop_name,t1.brand_name,t1.sku_name,t1.comment_time ";
					 
			 result = JdbcTemplate.queryForList(sql);
			 if(result != null && result.size() > 0){
		for (Map<String, Object> map : result){
			JdbcTemplate.execute(" insert into report_comment_ratio_trend_year "
	+ "values(null,'"+map.get("brand_name")+"','"+map.get("channel_name")+"','"+map.get("comment_time")+"',"
			+ "'"+map.get("better")+"','"+map.get("company_code")+"','"+map.get("good")+"','"+map.get("bad")+"','"+map.get("shop_name")+"','"+map.get("sku_name")+"',null)");			
			  }
		}
}	

	@Async
//	@Scheduled(cron="0 0/1 * * * ?")   //5分钟任务  
	@Scheduled(cron="* * 0 * * *")   //每天零点任务  
//[好中差时间分布趋势图]   统计小时好评、中评、差评新增数量
public void commmentAnalyseHour(){
List<UmpCompany>  ump =	UmpCompany.findAllServiceUmpCompanys();
String lastHourStr = DateUtil.getDateOfLastHourStr(Calendar.getInstance());	
List<Map<String, Object>> result =null;
			  String sql = "SELECT  "
			  		+ "			t0.channel_name,t0.shop_name,t0.brand_name,t0.sku_name, t0.company_code, "
			  		+ "			  				  	   sum( case t0.commet_type when '好评'  then tag_num else 0 end) as 'good', "
			  		+ "			  					  		 sum( case t0.commet_type when '中评'  then tag_num else 0 end) as 'better', "
			  		+ "			  				  		 sum( case t0.commet_type when '差评'  then tag_num else 0 end) as 'bad', "
			  		+ "			  				  		 STR_TO_DATE(t0.comment_time,'%Y-%m-%d %H:%i:%S') comment_time "
			  		+ "			  		  		 from( "
			  		+ "			  				  		 select vc.id,uc.channel_name,vp.name shop_name,vb.brand_name,vk.name sku_name,up.company_code, "
			  		+ "			  				  	  DATE_FORMAT(vc.comment_time,'%Y-%m-%d %H') comment_time , "
			  		+ "			  				  	 case  when vc.comment_level = 0 THEN '好评' "
			  		+ "			  					  		     when vc.comment_level = 1 THEN '中评' "
			  		+ "			  				  	       when vc.comment_level = 2 THEN '差评' "
			  		+ "			  				  	 end  commet_type, "
			  		+ "			  					 COUNT(vc.id) tag_num "
			  		+ "			  					  	 from  "
			  		+ "			  					  	 ump_channel uc, "
			  		+ "			  					  		 voc_shop vp, "
			  		+ "			  					  	 voc_shop_voc_brands vbvs, "
			  		+ "			  					  	 voc_brand vb, "
			  		+ "			  					  	voc_sku vk, "
			  		+ "			  					   voc_goods vg, "
			  		+ "			  					  	 voc_comment vc, "
			  		+ "			  		              ump_company up "
			  		+ "			  					   where "
			  		+ "			  				  		 uc.id = vp.channel "
			  		+ "			  					  		 and vp.id = vbvs.voc_shops "
			  		+ "			  				  		 and vb.id = vbvs.voc_brands "
			  		+ "			  					  		 and vb.id = vk.voc_brand "
			  		+ "			  					  		 and vg.voc_sku = vk.id  "
			  		+ "			  					  		 and vg.id = vc.goods "
			  		+ "			  					  		 and vg.ump_company = up.id 	"
			  		+ "			  				  	   and  DATE_FORMAT(vc.comment_time,'%Y-%m-%d %H') = '"+lastHourStr+"' "
			  		+ "			  		 GROUP BY up.company_code,uc.channel_name,vp.name,vb.brand_name,vk.name,vc.comment_level,DATE_FORMAT(vc.comment_time,'%Y-%m-%d %H') "
			  		+ "			  					  	) t0 "
			  		+ "			  					   GROUP BY t0.company_code,t0.channel_name,t0.shop_name,t0.brand_name,t0.sku_name,t0.comment_time "
			  		+ "                 ORDER BY t0.company_code,t0.channel_name,t0.shop_name,t0.brand_name,t0.sku_name,t0.comment_time ";
					 
			 result = JdbcTemplate.queryForList(sql);
			 int i = 0;
		if(result != null && result.size() > 0){
			for (Map<String, Object> map : result) {
				i++;
	JdbcTemplate.execute(" insert into report_comment_day "
	+ "values(null,'"+map.get("brand_name")+"','"+map.get("channel_name")+"','"+map.get("bad")+"','"+map.get("better")+"','"+map.get("good")+"',"
	+ "'"+map.get("company_code")+"','"+map.get("comment_time")+"',null,'"+map.get("shop_name")+"','"+map.get("sku_name")+"',null)");			
			System.out.println("********************正在插入第"+i+"条数据********************");
			}
		  } }
	@Async
//	@Scheduled(cron="0 0/1 * * * ?")   //30秒任务  
	@Scheduled(cron="* * 0 * * *")   //每天零点任务  
//[好中差时间分布趋势图]   统计每天好评、中评、差评新增数量
public void commmentAnalyseDay(){
String lastDayStr = DateUtil.getStrOfLastDay(Calendar.getInstance());	
List<Map<String, Object>> result =null;
			  String sql = " SELECT  "
				  		+ "			t0.channel_name,t0.shop_name,t0.brand_name,t0.sku_name, t0.company_code, "
				  		+ "			  	   sum( case t0.commet_type when '好评'  then tag_num else 0 end) as 'good', "
				  		+ "			  		 sum( case t0.commet_type when '中评'  then tag_num else 0 end) as 'better', "
				  		+ "			  		 sum( case t0.commet_type when '差评'  then tag_num else 0 end) as 'bad', "
				  		+ "			  		 STR_TO_DATE(t0.comment_time,'%Y-%m-%d %H:%i:%S') comment_time "
				  		+ "			  		 from( "
				  		+ "			  		 select vc.id,uc.channel_name,vp.name shop_name,vb.brand_name,vk.name sku_name,up.company_code, "
				  		+ "			  	  DATE_FORMAT(vc.comment_time,'%Y-%m-%d') comment_time , "
				  		+ "			  	 case  when vc.comment_level = 0 THEN '好评' "
				  		+ "			  		     when vc.comment_level = 1 THEN '中评' "
				  		+ "			  	       when vc.comment_level = 2 THEN '差评' "
				  		+ "			  	 end  commet_type, "
				  		+ "			  	COUNT(vc.id) tag_num "
				  		+ "			  	 from  "
				  		+ "			  	 ump_channel uc, "
				  		+ "			  		 voc_shop vp, "
				  		+ "			  	 voc_shop_voc_brands vbvs, "
				  		+ "			  	 voc_brand vb, "
				  		+ "			  	voc_sku vk, "
				  		+ "			   voc_goods vg, "
				  		+ "			  	 voc_comment vc, "
				  		+ "              ump_company up "
				  		+ "			   where "
				  		+ "			  		 uc.id = vp.channel "
				  		+ "			  		 and vp.id = vbvs.voc_shops "
				  		+ "			  		 and vb.id = vbvs.voc_brands "
				  		+ "			  		 and vb.id = vk.voc_brand "
				  		+ "			  		 and vg.voc_sku = vk.id  "
				  		+ "			  		 and vg.id = vc.goods "
				  		+ "			  		 and  vg.ump_company = up.id  "
				  	    + "			  	   and  DATE_FORMAT(vc.comment_time,'%Y-%m-%d') = '"+lastDayStr+"' "
				  		+ "			  		 GROUP BY up.company_code,uc.channel_name,vp.name,vb.brand_name,vc.comment_level,vk.name,DATE_FORMAT(vc.comment_time,'%Y-%m-%d') "
				  		+ "			  	) t0 "
				  		+ "			   GROUP BY t0.company_code,t0.channel_name,t0.shop_name,t0.brand_name,t0.sku_name,t0.comment_time  ";
			 result = JdbcTemplate.queryForList(sql);
			 if(result != null && result.size() > 0){
				 int i =0;
			 for (Map<String, Object> map : result) {
				 i++;
				 JdbcTemplate.execute(" insert into report_comment_month "
	+ "values(null,'"+map.get("brand_name")+"','"+map.get("channel_name")+"','"+map.get("bad")+"','"+map.get("better")+"','"+map.get("good")+"',"
	+ "'"+map.get("company_code")+"','"+map.get("comment_time")+"',null,'"+map.get("shop_name")+"','"+map.get("sku_name")+"',null)");			
				 System.out.println("^^^^^^^^^^插入好评、中评、差评新增数天数据第"+i+"条^^^^^^^^^^^^^");
	
			 }
			}
}	
	
	@Async
//	@Scheduled(cron="0 0/1 * * * ?")   //3分钟任务  
	@Scheduled(cron="* * 0 * * *")   //年报
//[好中差时间分布趋势图]   统计每月好评、中评、差评新增数量
public void commmentAnalyseMonth(){
//格式 yyyy-MM
String monthStr = DateUtil.getDateOfLastMonthStr(Calendar.getInstance());
List<Map<String, Object>> result =null;
			  String sql = " SELECT  t0.channel_name,t0.shop_name,t0.brand_name,t0.sku_name, t0.company_code, "
				  				  	+"   sum( case t0.commet_type when '好评'  then tag_num else 0 end) as 'good',  "
				  					+"  		 sum( case t0.commet_type when '中评'  then tag_num else 0 end) as 'better',  "
				  					+"  		 sum( case t0.commet_type when '差评'  then tag_num else 0 end) as 'bad', "
				  					+"    t0.comment_time comment_time_str,STR_TO_DATE(CONCAT(t0.comment_time,'-01'),'%Y-%m-%d')comment_time "
				  					+"  		  		 from(  "
				  					+"   		 select vc.id,uc.channel_name,vp.name shop_name,vb.brand_name,vk.name sku_name,up.company_code,  "
				  					+"    	  DATE_FORMAT(vc.comment_time,'%Y-%m') comment_time ,  "
				  					+"    	 case  when vc.comment_level = 0 THEN '好评'  "
				  					+"    		     when vc.comment_level = 1 THEN '中评'  "
				  					+"  	       when vc.comment_level = 2 THEN '差评'  "
				  					+"    	 end  commet_type,  "
				  					+"    	COUNT(vc.id) tag_num  "
				  					+"    	 from   "
				  					+"  ump_channel uc,  "
				  					+"    		 voc_shop vp,  "
				  					+"    	 voc_shop_voc_brands vbvs,  "
				  					+"    	 voc_brand vb,  "
				  					+"   	voc_sku vk,  "
				  					+"    voc_goods vg,  "
				  					+"    	 voc_comment vc,  "
				  					+"   ump_company up  "
				  					+"     where  "
				  					+"  		 uc.id = vp.channel  "
				  					+"  	     and vp.id = vbvs.voc_shops  "
				  					+"  		 and vb.id = vbvs.voc_brands  "
				  					+"   		 and vb.id = vk.voc_brand "
				  					+"  	 and vg.voc_sku = vk.id   "
				  				  	+"  	 and vg.id = vc.goods  "
				  				  	+"   	and vg.ump_company = up.id  "
//				  					+"    and  DATE_FORMAT(vc.comment_time,'%Y-%d') = '"+monthStr+"' "
				  					+"   		 GROUP BY up.company_code,uc.channel_name,vp.name,vb.brand_name,vc.comment_level,vk.name,DATE_FORMAT(vc.comment_time,'%Y-%m') " 
				  					+"  	) t0  "
				  					+"  GROUP BY t0.company_code,t0.channel_name,t0.shop_name,t0.brand_name,t0.sku_name,t0.comment_time  ";
			 result = JdbcTemplate.queryForList(sql);
			 if(result != null && result.size() > 0){
			 int i =0;
			for (Map<String, Object> map : result) {
				 i  ++;
				 JdbcTemplate.execute(" insert into report_comment_year "
	+ "values(null,'"+map.get("brand_name")+"','"+map.get("channel_name")+"','"+map.get("bad")+"','"+map.get("better")+"','"+map.get("good")+"',null,'"+map.get("company_code")+"','"+map.get("comment_time")+"','"+map.get("shop_name")+"','"+map.get("sku_name")+"',null,null)");			
		System.out.println("********************正在插入年报第"+i+"条数据********************");
	
			}
	  }
}		
	
	@Async
	@Scheduled(cron="* * 0 * * *")   //每天零点任务  
//购买动机分析
public void purchaseAnalyse(){
List<Map<String, Object>> resultTagsGood = null;
List<Map<String, Object>> resultBuessinessGood = null;
List<Map<String, Object>> resultTagsBad = null;
List<Map<String, Object>> resultBuessinessBad = null;
String lastDayStr = DateUtil.getStrOfLastDay(Calendar.getInstance());
//按照tag标签分组查询  查询好评
String groupByTagsGoodSql = " SELECT t0.company_code,t0.channel_name,t0.shop_name,t0.brand_name,t0.sku_name,sum(tag_num) tag_num,t0.tag_name,t0.bussiness_type_id  from ( "
		+ " select  uc.channel_name,vp.name shop_name,vb.brand_name,vk.name sku_name,up.company_code,  "
		+ " DATE_FORMAT(vc.comment_time,'%Y-%m-%d') comment_time , "
		+ " vt.name tag_name,COUNT(vt.name) tag_num,vg.bussiness_type_id   "
		+ " from  "
		+ " ump_channel uc, "
		+ " voc_shop vp, "
		+ " voc_shop_voc_brands vbvs, "
		+ " voc_brand vb, "
		+ " voc_sku vk, "
		+ " voc_goods vg, "
		+ " voc_comment vc, "
		+ " voc_tags vt, "
		+ " voc_comment_voc_tags vcvt, "
		+ " ump_company up   "
		+ " where  "
		+ " uc.id = vp.channel "
		+ " and vp.id = vbvs.voc_shops "
		+ " and vb.id = vbvs.voc_brands "
		+ " and vb.id = vk.voc_brand "
		+ " and vg.voc_sku = vk.id   "
		+ " and vg.id = vc.goods "
		+ " and vc.id = vcvt.voc_comments "
		+ " and vcvt.voc_tags = vt.id "
		+ " and vt.company_id = up.id "
		+ " and vc.comment_level = 0 "
		+ " and  DATE_FORMAT(vc.comment_time,'%Y-%m-%d') <= '"+lastDayStr+"' "
		+ " GROUP BY up.company_code,uc.channel_name,vp.name,vb.brand_name,vk.name,vt.name,DATE_FORMAT(vc.comment_time,'%Y-%m-%d') "
		+ " ORDER BY tag_num desc  "
		+ " ) t0 "
		+ " GROUP BY  "
		+ " t0.company_code,t0.channel_name,t0.shop_name,t0.brand_name,t0.sku_name,t0.tag_name "
		+ " ORDER BY  "
		+ "  company_code,channel_name,shop_name,brand_name,sku_name,tag_num desc ";

//按照tag标签分组查询  查询差评
String groupByTagsBadSql = " SELECT t0.company_code,t0.channel_name,t0.shop_name,t0.brand_name,t0.sku_name,sum(tag_num) tag_num,t0.tag_name,t0.bussiness_type_id  from ( "
		+ " select  uc.channel_name,vp.name shop_name,vb.brand_name,vk.name sku_name,up.company_code,   "
		+ " DATE_FORMAT(vc.comment_time,'%Y-%m-%d') comment_time , "
		+ " vt.name tag_name,COUNT(vt.name) tag_num,vg.bussiness_type_id   "
		+ " from  "
		+ " ump_channel uc, "
		+ " voc_shop vp, "
		+ " voc_shop_voc_brands vbvs, "
		+ " voc_brand vb, "
		+ " voc_sku vk, "
		+ " voc_goods vg, "
		+ " voc_comment vc, "
		+ " voc_tags vt, "
		+ " voc_comment_voc_tags vcvt, "
		+ " ump_company up   "
		+ " where  "
		+ " uc.id = vp.channel "
		+ " and vp.id = vbvs.voc_shops "
		+ " and vb.id = vbvs.voc_brands "
		+ " and vb.id = vk.voc_brand "
		+ " and vg.voc_sku = vk.id   "
		+ " and vg.id = vc.goods "
		+ " and vc.id = vcvt.voc_comments "
		+ " and vcvt.voc_tags = vt.id "
		+ " and vt.company_id = up.id "
		+ " and vc.comment_level = 2 "
		+ " and  DATE_FORMAT(vc.comment_time,'%Y-%m-%d') <= '"+lastDayStr+"' "
		+ " GROUP BY up.company_code,uc.channel_name,vp.name,vb.brand_name,vk.name,vt.name,DATE_FORMAT(vc.comment_time,'%Y-%m-%d') "
		+ " ORDER BY tag_num desc  "
		+ " ) t0 "
		+ " GROUP BY  "
		+ " t0.company_code,t0.channel_name,t0.shop_name,t0.brand_name,t0.sku_name,t0.tag_name "
		+ " ORDER BY  "
		+ "  company_code,channel_name,shop_name,brand_name,sku_name,tag_num desc ";

//按照子行业分组 查询 好评 
String sqlForGroupByTagsGood = " SELECT t0.channel_name,t0.shop_name,t0.brand_name,t0.sku_name,sum(tag_num) tag_num,t0.tag_name,t0.bussiness_type_id ,'2015-01-16' from ( "
+" select  uc.channel_name,vp.name shop_name,vb.brand_name,vk.name sku_name,  "
+"  DATE_FORMAT(vc.comment_time,'%Y-%m-%d') comment_time ,  "
+" vt.name tag_name,COUNT(vt.name) tag_num,vg.bussiness_type_id  "
+" from   "
+" ump_channel uc,  "
+" voc_shop vp,  "
+" voc_shop_voc_brands vbvs,  "
+" voc_brand vb,  "
+" voc_sku vk,  "
+" voc_goods vg,  "
+" voc_comment vc,  "
+" voc_tags vt,  "
+" voc_comment_voc_tags vcvt  "
+" where   "
+" uc.id = vp.channel  "
+" and vp.id = vbvs.voc_shops  "
+" and vb.id = vbvs.voc_brands  "
+" and vb.id = vk.voc_brand  "
+" and vg.voc_sku = vk.id    "
+" and vg.id = vc.goods  "
+" and vc.id = vcvt.voc_comments  "
+" and vcvt.voc_tags = vt.id  "
+" and vc.comment_level = 0  "
+ " and  DATE_FORMAT(vc.comment_time,'%Y-%m-%d') <= '"+lastDayStr+"' "
+" GROUP BY uc.channel_name,vp.name,vb.brand_name,vk.name,vt.name,DATE_FORMAT(vc.comment_time,'%Y-%m-%d')  "
+" ORDER BY tag_num desc  "
+" ) t0  "
+" GROUP BY   "
+" t0.channel_name,t0.tag_name,t0.bussiness_type_id  "
+" ORDER BY "  
+" t0.tag_num desc  ";	
//按照子行业分组 查询 tags差评 
String sqlForGroupByTagsBad = " SELECT t0.channel_name,t0.shop_name,t0.brand_name,t0.sku_name,sum(tag_num) tag_num,t0.tag_name,t0.bussiness_type_id ,'2015-01-16' from ( "
+" select  uc.channel_name,vp.name shop_name,vb.brand_name,vk.name sku_name,  "
+"  DATE_FORMAT(vc.comment_time,'%Y-%m-%d') comment_time ,  "
+" vt.name tag_name,COUNT(vt.name) tag_num,vg.bussiness_type_id  "
+" from   "
+" ump_channel uc,  "
+" voc_shop vp,  "
+" voc_shop_voc_brands vbvs,  "
+" voc_brand vb,  "
+" voc_sku vk,  "
+" voc_goods vg,  "
+" voc_comment vc,  "
+" voc_tags vt,  "
+" voc_comment_voc_tags vcvt  "
+" where   "
+" uc.id = vp.channel  "
+" and vp.id = vbvs.voc_shops  "
+" and vb.id = vbvs.voc_brands  "
+" and vb.id = vk.voc_brand  "
+" and vg.voc_sku = vk.id    "
+" and vg.id = vc.goods  "
+" and vc.id = vcvt.voc_comments  "
+" and vcvt.voc_tags = vt.id  "
+" and vc.comment_level = 2  "
+ " and  DATE_FORMAT(vc.comment_time,'%Y-%m-%d') <= '"+lastDayStr+"' "
+" GROUP BY uc.channel_name,vp.name,vb.brand_name,vk.name,vt.name,DATE_FORMAT(vc.comment_time,'%Y-%m-%d')  "
+" ORDER BY tag_num desc  "
+" ) t0  "
+" GROUP BY   "
+" t0.channel_name,t0.tag_name,t0.bussiness_type_id  "
+" ORDER BY "  
+" t0.tag_num desc ";	
resultTagsGood = JdbcTemplate.queryForList(groupByTagsGoodSql);//标签分组  - 好评
resultBuessinessGood = JdbcTemplate.queryForList(sqlForGroupByTagsGood);//子行业分组  - 好评	
resultTagsBad = JdbcTemplate.queryForList(groupByTagsBadSql);//标签分组  - 差评
resultBuessinessBad = JdbcTemplate.queryForList(sqlForGroupByTagsBad);//子行业分组  - 好评
Map<String, List<ReportPurchaseMotive>> skuMap = new LinkedHashMap<String, List<ReportPurchaseMotive>>();
Map<String, List<ReportPurchaseMotive>> businessMap = new LinkedHashMap<String, List<ReportPurchaseMotive>>();

Map<String, List<ReportPurchaseMotive>> skuMapBad = new LinkedHashMap<String, List<ReportPurchaseMotive>>();
Map<String, List<ReportPurchaseMotive>> businessBadMap = new LinkedHashMap<String, List<ReportPurchaseMotive>>();
/**
 * 计算好评标签占比
 */
//1.提取当前查询到的所有好评sku	,遍历sku 筛选top7 标签数量前十 按照sku分组求top7
for(Map<String, Object> map : resultTagsGood){
		skuMap.put(""+map.get("company_code")+"_"+(String)map.get("sku_name"), new ArrayList<ReportPurchaseMotive>());
	}
	for(Map.Entry<String, List<ReportPurchaseMotive>> sku:skuMap.entrySet()){
//		int temp = 0;
		for(Map<String, Object> map : resultTagsGood){
			if( sku.getValue().size()< 7 && sku.getKey().equals(map.get("company_code")+"_"+map.get("sku_name"))){
			ReportPurchaseMotive rm = new ReportPurchaseMotive();
//			temp += Integer.parseInt(map.get("tag_num")+"");
			rm.setChannelName((String)map.get("channel_name"));
			rm.setBrandName((String)map.get("brand_name"));
			rm.setShopName((String)map.get("shop_name"));
			rm.setSkuName((String)map.get("sku_name"));
			rm.setCompanyCode((String)map.get("company_code"));
			rm.setLabelName((String)map.get("tag_name"));
			rm.setProportion(""+map.get("tag_num"));
			rm.setBusinessTypeId(""+map.get("bussiness_type_id"));
			sku.getValue().add(rm);
			}
		}
	}
//分别找出每个子行业的前7好评标签[按照子行业、标签分组]
	for(Map<String, Object> map : resultBuessinessGood){
		//求出一共有多少子行业，并初始化子行业对应的容器
		businessMap.put(""+map.get("bussiness_type_id"), new ArrayList<ReportPurchaseMotive>());
	}
	for(Map.Entry<String, List<ReportPurchaseMotive>> bu:businessMap.entrySet()){
		for(Map<String, Object> map : resultBuessinessGood){
			if( bu.getValue().size() < 7 && bu.getKey().equals(""+map.get("bussiness_type_id"))){
			ReportPurchaseMotive rm = new ReportPurchaseMotive();
			rm.setSkuName((String)map.get("sku_name"));
			rm.setLabelName((String)map.get("tag_name"));
			rm.setProportion(""+map.get("tag_num"));
			rm.setBusinessTypeId(""+map.get("bussiness_type_id"));
			bu.getValue().add(rm);
			}
		}
	}
	
	
////3. 好评  比对容器   skuMap 里的每个sku对应的的标签是否在容器  resultBuessinessGood （子行业分组,拉取标签数量前十的标签）里面

	for(Map.Entry<String, List<ReportPurchaseMotive>> sku:skuMap.entrySet()){
	      for(Map.Entry<String, List<ReportPurchaseMotive>> busin:businessMap.entrySet()){
	    	  //找到统一子行业，             
	    	  if(sku.getValue().size() > 0 && sku.getValue().get(0).getBusinessTypeId().equals(busin.getKey())){
	    	  //  并对比，看skuMap标签是否在其子行业的标签里面,如果在里面，则标识 1
	    		 
	    		  Float tempScore = 0f;
	    		  for(ReportPurchaseMotive r:sku.getValue()){
	    			  for(ReportPurchaseMotive b:busin.getValue()){
	    				  if(r.getLabelName().equals(b.getLabelName())){
	    					  r.setVersion(1);
	    					  break;
	    				  }
	    			  }
	    			  if(r.getProportion() != null){
	    				  tempScore +=Float.parseFloat(r.getProportion());
		    			  r.setTotalScore(tempScore);
	    			  }
	    		  }
	    		  break;
	    	  }
	      }    
	}
	
//4.计算每个标签的占比分数	
	for(Map.Entry<String, List<ReportPurchaseMotive>> sku:skuMap.entrySet()){
		Float totalScore =  sku.getValue().get(sku.getValue().size()-1).getTotalScore();
		for(int i = (sku.getValue().size() - 1);i >= 0; i--){
			Integer v = sku.getValue().get(i).getVersion();
			if(v != null && v == 1){//如果存在于子行业里面则计算分数
			
				sku.getValue().get(i).setProportion((60*Float.parseFloat(sku.getValue().get(i).getProportion())/totalScore)+"");
			}else{
				sku.getValue().get(i).setProportion("0");
			}
		}
	}
	/**
	 * 计算差评标签占比
	 */	
	//1.提取当前查询到的所有好评sku	
	for(Map<String, Object> map : resultTagsBad){
		skuMapBad.put(map.get("company_code")+"_"+(String)map.get("sku_name"), new ArrayList<ReportPurchaseMotive>());
		}
	//2.遍历sku 筛选top, 标签数量前十 按照sku分组求top
		for(Map.Entry<String, List<ReportPurchaseMotive>> sku:skuMapBad.entrySet()){
			for(Map<String, Object> map : resultTagsBad){
				if( sku.getValue().size()< 3 && sku.getKey().equals(map.get("company_code")+"_"+map.get("sku_name"))){
				ReportPurchaseMotive rm = new ReportPurchaseMotive();
				rm.setChannelName((String)map.get("channel_name"));
				rm.setBrandName((String)map.get("brand_name"));
				rm.setShopName((String)map.get("shop_name"));
				rm.setSkuName((String)map.get("sku_name"));
				rm.setBusinessTypeId(""+map.get("bussiness_type_id"));
				rm.setCompanyCode((String)map.get("company_code"));
				rm.setLabelName((String)map.get("tag_name"));
				rm.setProportion(""+map.get("tag_num"));
				sku.getValue().add(rm);
				}
			}
		}
		
		
		//分别找出每个子行业的前3好评标签[按照子行业、标签分组]	
		for(Map<String, Object> map : resultBuessinessBad){
			//求出一共有多少子行业，并初始化子行业对应的容器
			businessBadMap.put(""+map.get("bussiness_type_id"), new ArrayList<ReportPurchaseMotive>());
		}
		for(Map.Entry<String, List<ReportPurchaseMotive>> bu:businessBadMap.entrySet()){
			for(Map<String, Object> map : resultBuessinessBad){
				if( bu.getValue().size()< 3 && bu.getKey().equals((""+map.get("bussiness_type_id")))){
				ReportPurchaseMotive rm = new ReportPurchaseMotive();
				rm.setSkuName((String)map.get("sku_name"));
				rm.setLabelName((String)map.get("tag_name"));
				rm.setProportion(""+map.get("tag_num"));
				rm.setBusinessTypeId(""+map.get("bussiness_type_id"));
				bu.getValue().add(rm);
				}
			}
		}
	////3. 好评  比对容器   skuMap 里的每个sku对应的的标签是否在容器  resultBuessinessBad （子行业分组,拉取标签数量前十的标签）里面

		for(Map.Entry<String, List<ReportPurchaseMotive>> sku:skuMapBad.entrySet()){
		      for(Map.Entry<String, List<ReportPurchaseMotive>> busin:businessBadMap.entrySet()){
		    	  //找到同一子行业，             
		    	  if(sku.getValue().size() > 0 && sku.getValue().get(0).getBusinessTypeId().equals(busin.getKey())){
		    	  //  并对比，看skuMap标签是否在其子行业的标签里面,如果在里面，则标识 1
		    		       
		    		  Float tempScore = 0f;
		    		  for(ReportPurchaseMotive r:sku.getValue()){
		    			  for(ReportPurchaseMotive b:busin.getValue()){
		    				  if(r.getLabelName().equals(b.getLabelName())){
		    					  r.setVersion(1);
		    					  break;
		    				  }
		    			  }
		    			     if(r.getProportion() != null){
		    			    	 
		    			    	 tempScore +=Float.parseFloat(r.getProportion());
		    			    	 r.setTotalScore(tempScore);
		    			     }
		    		  }
		    		  break;
		    	  }
		      }    
		}
		//4.计算每个标签的占比分数	
		for(Map.Entry<String, List<ReportPurchaseMotive>> sku:skuMapBad.entrySet()){
			int t = sku.getValue().size();
			if(t>0){
				
				for(int i = (sku.getValue().size() - 1);i >= 0; i--){
					Integer v = sku.getValue().get(i).getVersion();
					if(v !=null && v == 1){//如果差评标签存在于子行业里标签里面
						sku.getValue().get(i).setProportion("0");
					}else{
						sku.getValue().get(i).setProportion(""+40*(1/t));
					}
				}
			}
		}
	/**
	 * 合并好评、差评评标签占比，计算最终的分数
	 */
	int i =0;
	for(Map.Entry<String, List<ReportPurchaseMotive>> sku:skuMap.entrySet()){
           for(ReportPurchaseMotive s:sku.getValue()){
        	   i++;
JdbcTemplate.execute(" insert into report_purchase_motive "
	+ "values(null,'"+s.getBrandName()+"','"+s.getChannelName()+"','"+s.getCompanyCode()+"','"+lastDayStr+"','"+s.getLabelName()+"',"
	+ "'"+s.getProportion()+"','"+s.getShopName()+"','"+s.getSkuName()+"',null,null,null,'0')");	
System.out.println("****************************_当前插入数据第"+i+"条_****************************");	
  
           }
	}
	
	for(Map.Entry<String, List<ReportPurchaseMotive>> sku:skuMapBad.entrySet()){
		  for(ReportPurchaseMotive s:sku.getValue()){
			  i++;
			JdbcTemplate.execute(" insert into report_purchase_motive "
				+ "values(null,'"+s.getBrandName()+"','"+s.getChannelName()+"','"+s.getCompanyCode()+"','"+lastDayStr+"','"+s.getLabelName()+"','"+s.getProportion()+"','"+s.getShopName()+"','"+s.getSkuName()+"',null,null,null,'2')");
			System.out.println("****************************_当前插入差评标签数据数据第"+i+"条_****************************");
		  }
	}
	
}
	
//标签占比趋势分析
	@Async
//	@Scheduled(cron="0 0/1 * * * ?")   //1分钟任务  
	@Scheduled(cron="* * 0 * * *")   //每天零点任务 
	public void tagsTrendAnalyse(){
		String lastDayStr = DateUtil.getStrOfLastDay(Calendar.getInstance());
		List<Map<String, Object>> result =null;
					  String sql = " SELECT t0.channel_name,t0.shop_name,t0.brand_name,t0.company_code,t0.sku_name,t0.comment_time,t0.comment_level,t0.tag_name,sum(t0.tag_num) tag_num "
+" FROM (  "
+" select  uc.channel_name,vp.name shop_name,vb.brand_name,vk.name sku_name,up.company_code,  "
+" STR_TO_DATE(DATE_FORMAT(vc.comment_time,'%Y-%m-%d'),'%Y-%m-%d') comment_time ,  "
+" vc.comment_level,vc.comment_content,vt.name tag_name,COUNT(vt.name) tag_num  "
+" from  "
+" ump_channel uc,  "
+" voc_shop vp,  "
+" voc_shop_voc_brands vbvs,  "
+" voc_brand vb,  "
+" voc_sku vk,  "
+" voc_goods vg,  "
+" ump_company up,  "
+" voc_comment vc,  "
+" voc_tags vt,  "
+" voc_comment_voc_tags vcvt  "
+" where   "
+" uc.id = vp.channel  "
+" and vp.id = vbvs.voc_shops  "
+" and vb.id = vbvs.voc_brands  "
+" and vb.id = vk.voc_brand  "
+" and vg.voc_sku = vk.id    "
+" and vg.id = vc.goods  "
+" and vc.id = vcvt.voc_comments  "
+" and vcvt.voc_tags = vt.id  "
+" and vt.company_id = up.id  "
+" and DATE_FORMAT(vc.comment_time,'%Y-%m-%d') = '"+lastDayStr+"' "
+" GROUP BY uc.channel_name,vp.name,vb.brand_name,vc.comment_level,vc.comment_content,vt.name,vk.name,DATE_FORMAT(vc.comment_time,'%Y-%m-%d') "
+" ORDER BY tag_num desc  "
+" ) t0  "
+" GROUP BY t0.channel_name,t0.shop_name,t0.brand_name,t0.comment_time,t0.comment_level,t0.sku_name,t0.tag_name  "
+" ORDER BY t0.sku_name,t0.comment_time "; 
					 result = JdbcTemplate.queryForList(sql);
					 if(result != null && result.size() > 0){
						 int i=0;
					 for (Map<String, Object> map : result) {
						 i++;
						 JdbcTemplate.execute(" insert into report_tag_increment_trend "
			+ "values(null,'"+map.get("brand_name")+"','"+map.get("channel_name")+"','"+map.get("company_code")+"','"+map.get("comment_time")+"',null,"
			+ "'"+map.get("shop_name")+"','"+map.get("sku_name")+"','"+map.get("tag_num")+"','"+map.get("tag_name")+"',null)");			
					System.out.println("****************************_当前插入标签新增数据第"+i+"条_****************************");	
					 }
					}
		}
////*****************************小时任务************************************	
//	@Async
//    @Scheduled(cron=" * 0 * * * *")   //每小时任务  
//	public void hotword(){
//		System.out.println("--------------");
//		System.out.println(VocComment.findAllVocComments());
//		
//	}
//
////*****************************每天任务************************************	
//	@Async
//    @Scheduled(cron="* * 0 * * *")   //每天零点任务  
//	public void dayTask(){
//		System.out.println("--------------");
//	}    
}

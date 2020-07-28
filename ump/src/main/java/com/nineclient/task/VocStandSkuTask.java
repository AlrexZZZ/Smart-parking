package com.nineclient.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class VocStandSkuTask {
	private static final Logger logger = Logger
			.getLogger(VocStandSkuTask.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Async
    @Scheduled(cron="*/30 * * * * *")   //sku同步
	public void skuSync(){
		Long startTime=System.currentTimeMillis()/1000;
		logger.info("开始同步标准sku-------startSkuSync-------"+startTime+"秒");
		StringBuilder  insertsql=new StringBuilder("insert into voc_sku (id,company_code,create_time,is_deleted,is_visable,name,version,ump_business_type,voc_brand) ");
		insertsql.append(" select g.id,(select c.company_code from ump_company c where c.id=g.ump_company) as companyCode,CURRENT_TIMESTAMP(),0,1,g.`name`,0,(select bu.id from ump_business_type bu where bu.id=g.bussiness_type_id) as bussiness_type_id,b.id from voc_goods g,voc_brand b where g.voc_brand=b.id");
		insertsql.append(" and  g.bussiness_type_id is not NULL ");
		insertsql.append(" and not EXISTS (select * from voc_sku vs where vs.id = g.id)");
		StringBuilder  update=new StringBuilder(" UPDATE voc_goods vg set vg.voc_sku = vg.id where vg.bussiness_type_id is not null ");
		try{
			jdbcTemplate.execute(insertsql.toString());
			jdbcTemplate.execute(update.toString());
		//	jdbcTemplate.update(sql)
		}catch(Exception e){
			logger.info("同步标准sku-------异常-------"+e.getMessage());
		}
		Long endTime =System.currentTimeMillis()/1000;
		logger.info("结束同步标准sku-------endSkuSync-------使用："+(endTime-startTime)+"秒");
	}
	
}

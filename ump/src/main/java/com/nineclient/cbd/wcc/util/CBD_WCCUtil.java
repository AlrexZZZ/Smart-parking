package com.nineclient.cbd.wcc.util;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;


import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;



@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class CBD_WCCUtil {
	

	
	public static int getTotalPage(int limit,int totalCount){
		int page = 1;
		if((totalCount%limit) > 0 ){
			page = (totalCount/limit)+1;
		}else{
			page = (totalCount/limit);
		}
		
		return page;
	}
}

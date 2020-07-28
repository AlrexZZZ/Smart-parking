package com.nineclient.qycloud.wcc.util;

public class CacluatePage {
	
	

	public static int totalPage(int maxResults,int totalCount){
		int totalPage = 0;
		
		//根据总记录数和每页的大小计算公有多少页
		if((totalCount%maxResults) > 0){
			totalPage = (int)((totalCount/maxResults)+1);
		}else{
			totalPage =(int)(totalCount/maxResults);
		}
		return totalPage;
	}
	public static int totalPageCal(int maxResults,Long totalCount){
		int totalPage = 0;
		
		//根据总记录数和每页的大小计算公有多少页
		if((totalCount%maxResults) > 0){
			totalPage = (int)((totalCount/maxResults)+1);
		}else{
			totalPage =(int)(totalCount/maxResults);
		}
		return totalPage;
	}
}

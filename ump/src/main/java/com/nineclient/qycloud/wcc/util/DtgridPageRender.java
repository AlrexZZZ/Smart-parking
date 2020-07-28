package com.nineclient.qycloud.wcc.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;

public class DtgridPageRender {
	/**
	 * @param response
	 * @param list
	 */
	public static void writeResponseJsonToPage(HttpServletResponse response,PageModel<?>model,Pager pager){
		// 1.page赋值
		Long totalNum = Long.parseLong(model.getTotalCount()+"");
		pager.setExhibitDatas(model.getDataList());
		pager.setPageCount(CacluatePage.totalPageCal(pager.getPageSize(),totalNum));
		pager.setRecordCount(totalNum);// 设置总记录数
		pager.setIsSuccess(true);
		// 2.page 渲染
		PrintWriter  prWrite = null;
		try {
			prWrite = response.getWriter();
			response.setContentType("application/json");// 设置返回的数据为json对象
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			JSONObject pagerJSON = JSONObject.fromObject(pager);
			prWrite.write(pagerJSON.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null != prWrite){
				prWrite.close();
			}
		}
	}
	/**
	 * @param response
	 * @param list
	 */
	public static void writeResponseJsonToPageFromPager(HttpServletResponse response,PageModel<?>model,Pager pager){
		// 1.page赋值
		Long totalNum = Long.parseLong(model.getTotalCount()+"");
//		pager.setExhibitDatas(model.getDataList());
		pager.setPageCount(CacluatePage.totalPageCal(pager.getPageSize(),totalNum));
		pager.setRecordCount(totalNum);// 设置总记录数
		pager.setIsSuccess(true);
		// 2.page 渲染
		PrintWriter  prWrite = null;
		try {
			prWrite = response.getWriter();
			response.setContentType("application/json");// 设置返回的数据为json对象
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			JSONObject pagerJSON = JSONObject.fromObject(pager);
			prWrite.write(pagerJSON.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null != prWrite){
				prWrite.close();
			}
		}
	}
}

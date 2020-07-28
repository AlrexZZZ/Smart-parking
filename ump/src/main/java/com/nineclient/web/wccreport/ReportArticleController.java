package com.nineclient.web.wccreport;


import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nineclient.cherry.wcc.model.WccCheryArticle;
import com.nineclient.model.PubOperator;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.model.wccreport.ReportFriendsFrom;
import com.nineclient.model.wccreport.ReportFriendsSum;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.ExcelUtil;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;

@RequestMapping("/reportArticle")
@Controller
public class ReportArticleController {

	@RequestMapping(value = "reportPage", produces = "text/html")
	public String reportPage(HttpServletRequest request, Model uiModel) {
		PubOperator currentoper=CommonUtils.getCurrentPubOperator(request);
        List<WccPlatformUser> platformUsers=WccPlatformUser.getplatformuserListByOrg(currentoper);
		uiModel.addAttribute("platformUsers", platformUsers);
		return "reportArticle/report";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "queryDataArticleGrid", produces = "application/json")
	public void queryDataReplyGrid(HttpServletRequest request,
			HttpServletResponse response, String dtGridPager) {
		Pager pager = null;
		String brandName="";
		String platformUser = "";
		String startTime = "";
		String endTime = "";
		Map<String, Object> parm = new HashMap<String, Object>();
		try {
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			parm = pager.getParameters();
			platformUser = (String) parm.get("platformUser");
			startTime = (String) parm.get("startTime");
			endTime = (String) parm.get("endTime");
			brandName = (String) parm.get("brandName");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int firstResult = pager.getStartRecord();
		int maxResults = pager.getPageSize();
		int totalPage = 0;
		long totalCount = 0;
		List<WccCheryArticle> articlelist =WccCheryArticle.findArticleReportByDateBetween(
							brandName,platformUser, startTime, endTime, firstResult,
							maxResults);
		List<WccCheryArticle> list = new ArrayList<WccCheryArticle>();
		for (WccCheryArticle article : articlelist) {
			   list.add(article);
		}
					totalCount = WccCheryArticle.findArticleReportByDateBetween(
							brandName,platformUser, startTime, endTime, -1, -1).size();
					pager.setRecordCount(totalCount);// 设置总记录数
					// 根据总记录数和每页的大小计算公有多少页
					if ((totalCount % maxResults) > 0) {
						totalPage = (int) ((totalCount / maxResults) + 1);
					} else {
						totalPage = (int) (totalCount / maxResults);
					}
		if (list.size() != 0) {
			pager.setExhibitDatas(list);
		} else {
			pager.setExhibitDatas(null);
		}
		pager.setIsSuccess(true);
		pager.setPageCount(totalPage);
		pager.setRecordCount(totalCount);// 设置总记录数
		JSONObject pagerJSON = JSONObject.fromObject(pager);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		try {
			response.getWriter().write(pagerJSON.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "exportExcelSum",produces="application/json;charset=UTF-8")
	public void exportExcelSum(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "brandName") String brandName,
			@RequestParam(value = "platformUser") String platformUser,
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime)
			throws IOException {
		platformUser=new String(platformUser.getBytes("ISO-8859-1"),"UTF-8"); 
		List<WccCheryArticle> recordlist =WccCheryArticle.findArticleReportByDateBetween(
				brandName,platformUser, startTime, endTime, -1,-1);
		List<Map> listData = new ArrayList<Map>();
		for (WccCheryArticle article : recordlist) {
			Map<String, Object> datamap = new HashMap<String, Object>();
			datamap.put("brandName", article.getBrandName());
			datamap.put("platformAccount",article.getPlatformAccount());
			datamap.put("title", article.getTitle());
			datamap.put("createDate", article.getCreateDate());
			datamap.put("url", article.getUrl());
			listData.add(datamap);
		}
	
		
		String[] title = { "品牌", "公众号", "文章标题", "链接地址", "发布日期"};
		String[] colums = { "brandName", "platformAccount", "title", "url",	"createDate"};
		OutputStream os = null;
		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("官微及竞品发布内容报表").getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			os = response.getOutputStream();

			map.put("官微及竞品发布内容报表", listData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel(title, colums, map, os);
	}
	
}

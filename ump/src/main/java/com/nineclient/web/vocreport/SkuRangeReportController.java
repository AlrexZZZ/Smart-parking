package com.nineclient.web.vocreport;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.json.simple.JSONArray;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpOperator;
import com.nineclient.model.VocBrand;
import com.nineclient.model.vocreport.ReportSkuRange;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.ExcelUtil;
import com.nineclient.utils.Global;
import com.nineclient.utils.NumberUtil;
import com.nineclient.utils.PageBean;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.ReportModel;

@RequestMapping("/skuRangeReports")
@Controller
@RooWebScaffold(path = "vocreport", formBackingObject = SkuRangeReportController.class)
public class SkuRangeReportController {
	@RequestMapping(value = "reportPage", produces = "text/html")
	public String reportPage(HttpServletRequest request, Model model) {
		// 初始化数据
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		List<UmpChannel> channels = UmpChannel.findAllChannelsbuyProductId(Global.VOC);
		model.addAttribute("channels", channels);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umpCompanyId", company.getId());
		List<VocBrand> vocbrands = VocBrand
				.findVocBrandsByChannelAndCompany(map);
		model.addAttribute("vocbrands", vocbrands);

		return "vocreports/skuRange";
	}



	/**
	 * 饼形图
	 * 
	 * @param request
	 * @param model
	 * @param channelName
	 * @param brandName
	 * @param skuName
	 * @param shopName
	 * @param commontType
	 * @param pageBean
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "queryDataBar", produces = "text/html;charset=utf-8")
	public String queryDataBar(
			HttpServletRequest request,HttpServletResponse response,
			Model model,
			@RequestParam(value = "channelName", required = false) String channelName,
			@RequestParam(value = "commentType", required = false) String commentType,
			@RequestParam(value = "commentTime", required = false) String commentTime,
			@RequestParam(value = "shopName", required = false) String shopName
			) throws IOException {
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
     int firstResult = 0;
	int maxResults = 10;
	List<ReportSkuRange> list =null;
	
	
	try {
	list = ReportSkuRange.findRangeByQuery(firstResult, maxResults, channelName,  commentType,commentTime, shopName, company.getCompanyCode());
	
	//JSONObject json = JSONObject.fromObject(list);
	String a  =  ReportSkuRange.toJsonArray(list);
	response.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
	response.getWriter().write(a.toString());
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return null;
	}
//表格上数据
	@ResponseBody
	@RequestMapping(value = "queryDataGrid", produces = "application/json")
	public void queryDataGrid(
			HttpServletRequest request,HttpServletResponse response,
			String dtGridPager,
			@RequestParam(value="displayId",required=false)Long displayId,
			@RequestParam(value = "channelName", required = false) String channelName,
			@RequestParam(value = "commentType", required = false) String commentType,
			@RequestParam(value = "commentTime", required = false) String commentTime,
			@RequestParam(value = "shopName", required = false) String shopName
		
			) throws IOException {
		if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(request);
	    }
		request.getSession().setAttribute("displayId", displayId);
		Pager pager =null;
		String channelNamep = "";
		String shopNamep = "";
		String commentTypep = "";
		String commentTimep = "";
		Map parm = new HashMap<String, String>();
		 try {
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			parm = pager.getParameters();
			 channelNamep =(String) parm.get("channelName");
			 shopNamep = (String) parm.get("shopName");;
			 commentTypep = (String) parm.get("commentType");;
			 commentTimep = (String) parm.get("commentTime");;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
     int firstResult = 0;
	int maxResults = 10;
	List list =null;
	
	
	try {
	list = ReportSkuRange.findRangeByQuery(firstResult, maxResults, channelNamep, commentTypep, commentTimep, shopNamep,company.getCompanyCode());
	pager.setExhibitDatas(list);
	pager.setIsSuccess(true);
	JSONObject pagerJSON = JSONObject.fromObject(pager);
	response.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
	response.getWriter().write(pagerJSON.toString());
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
	
	//导出数据
		
		@RequestMapping(value = "exportExcel", method = RequestMethod.GET)
		public void exportExcel(
				HttpServletRequest request,HttpServletResponse response,
				String dtGridPager,
				@RequestParam(value = "channelName", required = false) String channelName,
				@RequestParam(value = "commentType", required = false) String commentType,
				@RequestParam(value = "commentTime", required = false) String commentTime,
				@RequestParam(value = "shopName", required = false) String shopName
				) throws IOException {
		
			PubOperator user = CommonUtils.getCurrentPubOperator(request);
			UmpCompany company = user.getCompany();
		List<ReportSkuRange> list =null;
		List<Map> listData = new ArrayList<Map>();
		String skuName = null;
		String companyCode = company.getCompanyCode();
		listData = ReportSkuRange.findReportSkuRangeEntries(channelName, skuName, shopName,commentTime, commentType ,companyCode);
		if(listData.size() > 0){
			String[] title = { "平台名称", "店铺名称", "SKU名称", "评论类型", "新增评论数",
			"日期" };
	        String[] colums = { "channelName", "shopName", "skuName", "commentType",
			"incrementComment", "commentTime" };
			
		OutputStream os = null;
		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("sku好评/差评排行top10").getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			os = response.getOutputStream();
			
			map.put("sku评论排行top10", listData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel(title, colums, map, os);
		return;
		}
		
		}	
	
}

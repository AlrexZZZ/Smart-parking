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
import com.nineclient.model.vocreport.ReportOperCount;
import com.nineclient.model.vocreport.ReportSkuRange;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.ExcelUtil;
import com.nineclient.utils.Global;
import com.nineclient.utils.NumberUtil;
import com.nineclient.utils.PageBean;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.ReportModel;

@RequestMapping("/operatorReport")
@Controller
@RooWebScaffold(path = "vocreport", formBackingObject = OperatorReportController.class)
public class OperatorReportController {
	@RequestMapping(value = "reportPage", produces = "text/html")
	public String reportPage(HttpServletRequest request, Model model,@RequestParam(value="displayId",required=false)Long displayId) {
		// 初始化数据
		if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(request);
	    }
		request.getSession().setAttribute("displayId", displayId);
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		PubOperator p = new PubOperator();
		p.setCompany(company);
		List<PubOperator> oper = PubOperator.getPubOperListByFiled2(p, null);
		model.addAttribute("operList", oper);
		return "vocreports/operatorReport";
	}



	/**
	 * 柱状图
	 * 
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "queryOper", produces = "text/html;charset=utf-8")
	public String queryDataBar(
			HttpServletRequest request,HttpServletResponse response,
			Model model,
			@RequestParam(value = "operatorName", required = false) String operatorName,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime
			) throws IOException {
	int firstResult = 0;
    int maxResults = 10;
    String startTimep = null;
    String operatorNamep = null;
    String endTimep = null;
	PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
 List<ReportOperCount> list =  ReportOperCount.findRangeByQuery(firstResult, maxResults, startTime, operatorName, endTime,company.getCompanyCode());
		String a  =  ReportOperCount.toJsonArray(list);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(a.toString());
  
       return null;
	}
//表格上数据
	@ResponseBody
	@RequestMapping(value = "queryDataGrid", produces = "application/json")
	public void queryDataGrid(
			HttpServletRequest request,HttpServletResponse response,
			String dtGridPager,
			@RequestParam(value = "operatorName", required = false) String operatorName,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime
			) throws IOException {
		Pager pager =null;
		String operatorNameP = "";
		String startTimeP = "";
		String endTimeP = "";
		Map parm = new HashMap<String, String>();
		 try {
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			parm = pager.getParameters();
			 operatorNameP = (String) parm.get("operatorName");
			 startTimeP = (String) parm.get("startTime");
			 endTimeP = (String) parm.get("endTime");
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
		 list =  ReportOperCount.findRangeByQuery(firstResult, maxResults, startTimeP, operatorNameP, endTimeP,company.getCompanyCode());
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
				@RequestParam(value = "operatorName", required = false) String operatorName,
				@RequestParam(value = "startTime", required = false) String startTime,
				@RequestParam(value = "endTime", required = false) String endTime
				) throws IOException {
		
			PubOperator user = CommonUtils.getCurrentPubOperator(request);
			UmpCompany company = user.getCompany();
		List<ReportSkuRange> list =null;
		List<Map> listData = new ArrayList<Map>();
		listData = ReportOperCount.findSkuRangeReportEntries(operatorName, startTime, endTime, company.getCompanyCode());
		if(listData.size() > 0){
			String[] title = { "坐席名称", "回复量"};
	        String[] colums = { "operatorName", "replyNum"};
			
		OutputStream os = null;
		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("坐席工作量统计").getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			os = response.getOutputStream();
			
			map.put("坐席工作量统计", listData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel(title, colums, map, os);
		return;
		}
		
		}	
	
}

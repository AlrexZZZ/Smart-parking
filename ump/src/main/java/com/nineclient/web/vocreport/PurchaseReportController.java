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
import com.nineclient.model.vocreport.ReportPurchaseMotive;
import com.nineclient.model.vocreport.ReportSkuRange;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.ExcelUtil;
import com.nineclient.utils.Global;
import com.nineclient.utils.NumberUtil;
import com.nineclient.utils.PageBean;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.ReportModel;

@RequestMapping("/purchaeReport")
@Controller
@RooWebScaffold(path = "vocreport", formBackingObject = PurchaseReportController.class)
public class PurchaseReportController {
	@RequestMapping(value = "reportPage", produces = "text/html")
	public String reportPage(HttpServletRequest request, Model model,@RequestParam(value="displayId",required=false)Long displayId) {
		if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(request);
	    }
		request.getSession().setAttribute("displayId", displayId);
		// 初始化数据
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		List<UmpChannel> channels =UmpChannel.findAllChannelsbuyProductId(Global.VOC);
		model.addAttribute("channels", channels);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umpCompanyId", company.getId());
		List<VocBrand> vocbrands = VocBrand
				.findVocBrandsByChannelAndCompany(map);
		model.addAttribute("vocbrands", vocbrands);
		return "vocreports/purchaeReport";
	}



	/**
	 * 蜘蛛网
	 * 
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "queryOper", produces = "text/html;charset=utf-8")
	public String queryDataBar(
			HttpServletRequest request,HttpServletResponse response,
			Model model,
			@RequestParam(value = "channelName", required = false) String channelName,
			@RequestParam(value = "shopName", required = false) String shopName,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "insertTime", required = false) String insertTime
			) throws IOException{
	int firstResult = 0;
    int maxResults = 10;
    String startTimep = null;
    String operatorNamep = null;
    String endTimep = null;
  PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
 List<ReportPurchaseMotive> list =  ReportPurchaseMotive.findRangeByQuery(firstResult, maxResults, channelName, shopName, brandName, skuName, insertTime, company.getCompanyCode());
		String a = ReportPurchaseMotive.toJsonArray(list);
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
			String dtGridPager
			) throws IOException {
		Pager pager =null;
		String channelName="";
		String shopName="";
		String brandName="";
		String skuName="";
		String insertTime="";
		Map parm = new HashMap<String, String>();
		 try {
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			parm = pager.getParameters();
			channelName = (String) parm.get("channelName");
			 shopName = (String) parm.get("shopName");
			 brandName = (String) parm.get("brandName");
			 skuName = (String) parm.get("skuName");
			 insertTime = (String) parm.get("insertTime");
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

	list =  ReportPurchaseMotive.findRangeByQuery(firstResult, maxResults, channelName, shopName, brandName, skuName, insertTime, company.getCompanyCode());
	
	
	if(list.size() > 0){
		ReportPurchaseMotive pu = new ReportPurchaseMotive();
		float temp=0;
		List<ReportPurchaseMotive> l = list;
		for(ReportPurchaseMotive s:l ){
			String pro = s.getProportion();
			if(pro !=null && !"".equals(pro)){
				float a = Float.parseFloat(pro);
				s.setProportion(String.valueOf(Math.round(Float.parseFloat(s.getProportion())*100/100)));
				temp += a;
			}
			
		}
		pu.setLabelName("总分数");
		pu.setProportion(String.valueOf(Math.round(temp)));
		list.add(pu);
	}
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
				@RequestParam(value = "shopName", required = false) String shopName,
				@RequestParam(value = "brandName", required = false) String brandName,
				@RequestParam(value = "skuName", required = false) String skuName,
				@RequestParam(value = "insertTime", required = false) String insertTime
				) throws IOException {
		
			PubOperator user = CommonUtils.getCurrentPubOperator(request);
			UmpCompany company = user.getCompany();
		List<ReportSkuRange> list =null;
		List<Map> listData = new ArrayList<Map>();
		String companyCode = company.getCompanyCode();
	   
		listData = ReportPurchaseMotive.findSkuRangeReportEntries(0, 10, channelName, shopName, brandName, skuName, insertTime, companyCode);
		if(listData.size() > 0){
		  String  d = null;int total = 0;
			for(Map m:listData){
				 d = ""+m.get("proportion");
				int  i = 0;
				if(null != d && !"".equals(d)){
					i = Math.round(Float.parseFloat(d));
				}
				total+= i;
				m.put("proportion",i);
			}
			 Map a = new HashMap();a.put("label_name", "总分数");a.put("proportion", total);
			 listData.add(a);
			String[] title = { "标签", "得分"};
	        String[] colums = { "label_name", "proportion"};
			
		OutputStream os = null;
		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("购买动机分析").getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			os = response.getOutputStream();
			
			map.put("购买动机分析", listData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel(title, colums, map, os);
		return;
		}
		
		}	
	
}

package com.nineclient.web.vocreport;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
import com.nineclient.model.VocBrand;
import com.nineclient.model.vocreport.ProductProblemRandList;
import com.nineclient.model.vocreport.ReportSkuRange;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.ExcelUtil;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;

@RequestMapping("/productproblemrandlist")
@Controller
@RooWebScaffold(path = "productproblemrandlist", formBackingObject =ProductProblemRandListController.class)
public class ProductProblemRandListController {
	@RequestMapping(value="reportPage",produces="text/html")
	public String reportPage(HttpServletRequest request, Model model){
		
		// 初始化数据
				PubOperator user = CommonUtils.getCurrentPubOperator(request);
				UmpCompany company = user.getCompany();
				List<UmpChannel> channels = UmpChannel
						.findAllChannelsbuyProductId(Global.VOC);
				model.addAttribute("channels", channels);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("umpCompanyId", company.getId());
				List<VocBrand> vocbrands = VocBrand
						.findVocBrandsByChannelAndCompany(map);
				model.addAttribute("vocbrands", vocbrands);
		return "vocreports/productProblem";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "queryHotWord")
	public void queryHotWord(
			@RequestParam(value="displayId",required=false)Long displayId,
			@RequestParam(value = "channelName", required = false) String channelName,
			@RequestParam(value = "shopName", required = false) String shopName,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "startTimeStr", required = false) String commentStartTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String commentEndTimeStr,
			HttpServletRequest request, HttpServletResponse response) {
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		Map parmMap = new HashMap();
		try {
		   out = response.getWriter();
		   StringBuffer sb = new StringBuffer();
//		   ProductProblemRandList model = new ProductProblemRandList();
		   
//		   String field = "increaseCount"; 
//		   String dir = "desc";
//		   QueryModel<ProductProblemRandList> qm = new QueryModel<ProductProblemRandList>(model,0,10,field,dir);
//		   qm.getInputMap().putAll(parmMap);
//		 PageModel<ProductProblemRandList> pm = ProductProblemRandList.getQueryProductProblemRandList(qm);
		  List <Map<String,Object>> dataList = ProductProblemRandList.findProductProblemRandList(channelName, brandName, skuName, shopName, DateUtil.getDateTime(commentStartTimeStr), DateUtil.getDateTime(commentEndTimeStr), company);
		   sb.append("{ hotWords:[");
		  boolean flag = true;
		  if(dataList !=null && dataList.size() > 0){
		  for(Map pl:dataList){
			  if(flag){
			   sb.append("{\"name\":\""+String.valueOf(pl.get("commentTagName"))+"\",\"count\":"+pl.get("increaseCount")+"}"); 
			   flag = false;  
			  }else{
				sb.append(",{\"name\":\""+String.valueOf(pl.get("commentTagName"))+"\",\"count\":"+pl.get("increaseCount")+"}");
			  }
		  } }
		  sb.append(" ]}");	
			out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

	}
	@ResponseBody
	@RequestMapping(value = "queryDtGrid", produces = "text/html;charset=utf-8")
	public void queryDtGrid(String dtGridPager,
			@RequestParam(value="displayId",required=false)Long displayId,
			@RequestParam(value = "channelName", required = false) String channelName,
			@RequestParam(value = "shopName", required = false) String shopName,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "startTimeStr", required = false) String commentStartTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String commentEndTimeStr,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		Pager pager =null;
		String channelNameP = "";
		String shopNameP = "";
		String brandNameP = "";
		String skuNameP = "";
		String startTimeStr = "";
		String endTimeStr = "";
		Map parm = new HashMap<String, String>();
		 try {
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			if(pager != null){
				parm = pager.getParameters();
				 channelNameP =(String) parm.get("channelName");
				 shopNameP = (String) parm.get("shopName");
				 brandNameP = (String) parm.get("brandName");
				 skuNameP = (String) parm.get("skuName");
				 startTimeStr = (String) parm.get("startTimeStr");
				 endTimeStr = (String) parm.get("endTimeStr");
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		response.setContentType("text/Xml;charset=utf-8");
		  List <Map<String,Object>> dataList = null;
			try {                                 
				dataList = ProductProblemRandList.findProductProblemRandList(channelNameP, brandNameP, skuNameP, shopNameP, DateUtil.getDateTime(startTimeStr), DateUtil.getDateTime(endTimeStr), company);
				if(dataList != null && dataList.size() > 0){
					for(int i=0;i<dataList.size();i++){
						dataList.get(i).put("id",i+1);
					}
				}
				pager.setExhibitDatas(dataList);
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
	@RequestMapping(value = "/queryHotWordTable", produces = "text/html")
	public String queryHotWordTable(
			@RequestParam(value = "channelName", required = false) String channelName,
			@RequestParam(value = "shopName", required = false) String shopName,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "commentStartTimeStr", required = false) String commentStartTimeStr,
			@RequestParam(value = "commentEndTimeStr", required = false) String commentEndTimeStr,
			 @RequestParam(value = "start", required = false) Integer start,
			 @RequestParam(value = "limit", required = false) Integer limit,
			Model uiModel,
			HttpServletRequest request, HttpServletResponse response){
		
		   ProductProblemRandList model = new ProductProblemRandList();
		   Map parmMap = new HashMap();
		   QueryModel<ProductProblemRandList> qm = new QueryModel<ProductProblemRandList>(model);
		   qm.getInputMap().putAll(parmMap);
		   PageModel<ProductProblemRandList> pm = ProductProblemRandList.getQueryProductProblemRandList(qm);
		 
		    pm.setPageSize(limit);
		    pm.setStartIndex(start);
		 	 
		 uiModel.addAttribute("maxPages", pm.getTotalPage());
		 uiModel.addAttribute("page", pm.getPageNo());
		 uiModel.addAttribute("limit", pm.getPageSize());
         uiModel.addAttribute("list", pm.getDataList());
		
		return "vocreports/productProblemTable";
	}
	//导出数据
	
	@RequestMapping(value = "exportExcel", method = RequestMethod.GET)
	public void exportExcel(
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "channelName", required = false) String channelName,
			@RequestParam(value = "shopName", required = false) String shopName,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "startTimeStr", required = false) String startTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr
			
			) throws IOException {
	
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
	List<Map> listData = new ArrayList<Map>();
	listData = ProductProblemRandList.findProblemEntriesMap(channelName, brandName, skuName, shopName, startTimeStr, endTimeStr, company);
	if(listData.size() > 0){
		int i=0;
		for(Map m: listData) {
			i++;
			m.put("company_id", i);
			}
	}
	
	
	if(listData.size() > 0){
		String[] title = { "排行", "标签名称", "评论数量"};
        String[] colums = { "company_id", "comment_tag_name", "increase_count"};
		
	OutputStream os = null;
	Map<String, List<Map>> map = new HashMap<String, List<Map>>();
	try {
		response.setHeader("content-disposition", "attachment;filename="
				+ new String(("产品问题排行top10").getBytes("gb2312"), "iso8859-1")
				+ ".xls");
		os = response.getOutputStream();
		
		map.put("sku评论排行top10", listData);
	} catch (Exception e) {
		e.printStackTrace();
	}
	ExcelUtil.exportToExcel(title, colums, map, os);

	}
	return;
	}			
	
}

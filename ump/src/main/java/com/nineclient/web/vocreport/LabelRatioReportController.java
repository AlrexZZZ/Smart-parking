package com.nineclient.web.vocreport;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpOperator;
import com.nineclient.model.VocBrand;
import com.nineclient.model.vocreport.LabelRatioReport;
import com.nineclient.model.vocreport.ReportCommentMonth;
import com.nineclient.model.vocreport.ReportTagIncrementTrend;
import com.nineclient.model.vocreport.Series;
import com.nineclient.model.vocreport.TrendModel;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.ExcelUtil;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageBean;
import com.nineclient.utils.ReportModel;

@RequestMapping("/labelratioreport")
@Controller
@RooWebScaffold(path = "labelratioreport", formBackingObject = LabelRatioReportController.class)
public class LabelRatioReportController {
	
	@RequestMapping(value = "reportPage", produces = "text/html")
	public String reportPage(
			HttpServletRequest request,
			Model model,
			@RequestParam(value="displayId",required=false)Long displayId,
			@RequestParam(value = "platformName", required = false) String platformName,
			@RequestParam(value = "shopName", required = false) String shopName,
			@RequestParam(value = "brandNname", required = false) String brandNname,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "commentTypes", required = false) String commentTypes,
			PageBean pageBean,
			@RequestParam(value = "page", required = false) Long page,
			@RequestParam(value = "size", required = false) Long size) {
		if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(request);
	    }
		request.getSession().setAttribute("displayId", displayId);
		// 初始化数据
		UmpCompany company = CommonUtils.getCurrentPubOperator(request).getCompany();
		
		//获得平台名称
		List<UmpChannel> channels = UmpChannel.findAllChannelsbuyProductId(Global.VOC);
		model.addAttribute("channels", channels);
		
		//
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umpCompanyId", company.getId());
		List<VocBrand> vocbrands = VocBrand
				.findVocBrandsByChannelAndCompany(map);
		model.addAttribute("vocbrands", vocbrands);
		return "labelratioreport/label";
	}


	/**
	 * 获取所有数据
	 * @param request
	 * @param model
	 * @param platformName
	 * @param shopName
	 * @param brandNname
	 * @param skuName
	 * @param commentTypes
	 * @param pageBean
	 * @param page
	 * @param size
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryData", produces = "text/html;charset=utf-8")
	public String queryData(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "platformName", required = false) String platformName,
			@RequestParam(value = "shopName", required = false) String shopName,
			@RequestParam(value = "brandNname", required = false) String brandNname,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "commentTypes", required = false) String commentTypes,
			PageBean pageBean,
			@RequestParam(value = "page", required = false) Long page,
			@RequestParam(value = "size", required = false) Long size) {
		UmpCompany company = CommonUtils.getCurrentPubOperator(request).getCompany();
		List<LabelRatioReport> labelList = new ArrayList<LabelRatioReport>();
		int count = 0;
		long sum = 0;
		page = page == null ? null : page;
		size = size == null ? 10 : size;
		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1)
				* sizeNo;
		try {
			labelList = LabelRatioReport.findLabelEntries(firstResult,
					sizeNo,platformName,shopName,brandNname,skuName,commentTypes,
					pageBean.getStartTime(), pageBean.getEndTime(), company);
			Long nrOfPages = LabelRatioReport.countFindLabelEntries(platformName,
					shopName,brandNname,skuName,commentTypes,
					pageBean.getStartTime(), pageBean.getEndTime(), company);
			count = nrOfPages.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ReportModel<LabelRatioReport> reportModel = new ReportModel<LabelRatioReport>();
		reportModel.setDataJson(LabelRatioReport.toJsonArray(labelList));
		reportModel.setPageNo(page.intValue());
		reportModel.setPageSize(sizeNo);
		reportModel.setTotalCount(count);
		return reportModel.toJson();
	}
	/**
	 * 获取所有数据
	 * @param request
	 * @param model
	 * @param platformName
	 * @param shopName
	 * @param brandNname
	 * @param skuName
	 * @param commentTypes
	 * @param pageBean
	 * @param page
	 * @param size
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryDataList", produces = "text/html;charset=utf-8")
	public String queryDataList(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "platformName", required = false) String platformName,
			@RequestParam(value = "shopName", required = false) String shopName,
			@RequestParam(value = "brandNname", required = false) String brandNname,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "commentTypes", required = false) String commentTypes,
			PageBean pageBean,Model uiModel,
			@RequestParam(value = "page", required = false) Long page,
			@RequestParam(value = "size", required = false) Long size) {
		UmpCompany company = CommonUtils.getCurrentPubOperator(request).getCompany();
		List<LabelRatioReport> labelList = new ArrayList<LabelRatioReport>();
		int count = 0;
		long sum = 0;
		page = page == null ? null : page;
		size = size == null ? 10 : size;
		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1)
				* sizeNo;
		try {
			labelList = LabelRatioReport.findLabelEntries(firstResult,
					sizeNo,platformName,shopName,brandNname,skuName,commentTypes,
					pageBean.getStartTime(), pageBean.getEndTime(), company);
			Long nrOfPages = LabelRatioReport.countFindLabelEntries(platformName,
					shopName,brandNname,skuName,commentTypes,
					pageBean.getStartTime(), pageBean.getEndTime(), company);
			count = nrOfPages.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		uiModel.addAttribute("maxPages", count);
		uiModel.addAttribute("page",firstResult);
		uiModel.addAttribute("limit",sizeNo);
        uiModel.addAttribute("list",labelList);
    	return "labelratioreport/label";
	}

	/**
	 * 曲线
	 * 
	 * @param request
	 * @param model
	 * @param channelName
	 * @param shopName
	 * @param commontType
	 * @param pageBean
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "queryDataBarChart", produces = "text/html;charset=utf-8")
	public String queryDataBarChart(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "channelName", required = false) String channelName,
			@RequestParam(value = "shopName", required = false) String shopName,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "dateDayStart", required = false) String dateDayStart,
			@RequestParam(value = "dateDayEnd", required = false) String dateDayEnd,
			@RequestParam(value = "commentTypes", required = false) String commentTypes,
			 HttpServletResponse response) throws IOException {
		UmpCompany company =  CommonUtils.getCurrentPubOperator(request).getCompany();
		List<ReportTagIncrementTrend> listData = new ArrayList<ReportTagIncrementTrend>();
		List<ReportTagIncrementTrend> labellist =  ReportTagIncrementTrend.findReportTagIncrementTrendByQuery(0, 10, channelName, shopName, brandName, skuName, dateDayStart, dateDayEnd, company.getCompanyCode());
		if(labellist.size() > 0){
	List<String> dayListStr = DateUtil.getDateList(dateDayStart, dateDayEnd, Calendar.DAY_OF_YEAR);			
			for(int a = 0 ;a<dayListStr.size();a++){
				ReportTagIncrementTrend r = new ReportTagIncrementTrend();
				r.setDateDayStr(dayListStr.get(a));
				for(int b=0;b<labellist.size();b++){
					if(dayListStr.get(a).equals(DateUtil.getDayStr(labellist.get(b).getDateDay()))){
						r.setTagName(labellist.get(b).getTagName());
						r.setTagIncrementNum(labellist.get(b).getTagIncrementNum());
						break;
					}
				}
				
				 listData.add(r);
			}	
				
				String a = ReportTagIncrementTrend.toJsonArray(listData);
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(a.toString());
			}
		
		return null;
	}
	@ResponseBody
	@RequestMapping(value = "queryDataPieChart", produces = "text/html;charset=utf-8")
	public String queryDataPieChart(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "platformName", required = false) String platformName,
			@RequestParam(value = "shopName", required = false) String shopName,
			@RequestParam(value = "brandNname", required = false) String brandNname,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "commentTypes", required = false) String commentTypes,
			PageBean pageBean, HttpServletResponse response){
		UmpCompany company =  CommonUtils.getCurrentPubOperator(request).getCompany();
		String objList ="[";
		try {
			List<Map> labellist =  LabelRatioReport.findLabelEntriesMap(platformName,shopName,
					brandNname,skuName,commentTypes,
					pageBean.getStartTime(), pageBean.getEndTime(), company);
			int bigBrandsInt = 0;
			int isGoodInt = 0;
			int cheapInt = 0;
			int fastGoodsInt = 0;
			int goodsPackageInt = 0;
			int totalInt = 0;
			for (Map label : labellist) {
				String bigBrands = String.valueOf(label.get("big_Brands")==null?"0":label.get("big_Brands"));
				String isGood = String.valueOf(label.get("is_Good")==null?"0":label.get("is_Good"));
				String cheap = String.valueOf(label.get("cheap")==null?"0":label.get("cheap"));
				String fastGoods = String.valueOf(label.get("fast_Goods")==null?"0":label.get("fast_Goods"));
				String goodsPackage = String.valueOf(label.get("goods_Package")==null?"0":label.get("goods_Package"));
				bigBrandsInt = Integer.parseInt(bigBrands)+bigBrandsInt;
				isGoodInt = Integer.parseInt(isGood)+isGoodInt;
				cheapInt = Integer.parseInt(cheap)+cheapInt;
				fastGoodsInt = Integer.parseInt(fastGoods)+cheapInt;
				goodsPackageInt = Integer.parseInt(goodsPackage)+goodsPackageInt;
				totalInt = Integer.parseInt(bigBrands)+Integer.parseInt(isGood)+
						Integer.parseInt(cheap)+Integer.parseInt(fastGoods)+Integer.parseInt(goodsPackage)+totalInt;
			}
			
			if(totalInt==0){
				objList +=  "{name:\'无数据\',y:100.0}";
			}else{
				objList += "{name:'大品牌',y:" +bigBrandsInt+"}";
				objList += ",{name:'效果好',y:"+isGoodInt+"}";
				objList += ",{name:'发货快',y:"+cheapInt+"}";
				objList += ",{name:'便宜',y:"+ fastGoodsInt+"}";
				objList += ",{name:'包装好',y:"+goodsPackageInt+"}";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			objList +=  "{name:\'无数据\',y:100.0}";
		}

		 objList += "]";
		return objList;
	}

	
	/**
	 * 模板导出
	 * 
	 * @param httpServletRequest
	 * @param response
	 */
	@RequestMapping(value = "exportExecl", method = RequestMethod.GET)
	public void exportExecl(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "platformName", required = false) String platformName,
			@RequestParam(value = "shopName", required = false) String shopName,
			@RequestParam(value = "brandNname", required = false) String brandNname,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "commentTypes", required = false) String commentTypes,
			PageBean pageBean, HttpServletResponse response) {
		// 生成提示信息，
		response.setContentType("application/vnd.ms-excel");
		UmpCompany company = CommonUtils.getCurrentPubOperator(request).getCompany();
		String[] title = { "平台名称", "店铺名称", "品牌名称", "SKU名称", "评论类型", "时间","日期"
				,"大品牌","效果好","便宜","发货快","包装好" };
		String[] colums = {"platform_Name","shop_Name","brand_Nname","sku_Name",
				"comment_Types","label_Time","big_Brands","is_Good","cheap","fast_Goods","goods_Package","label_Date"};
		// UmpBusinessType businessType =
		// UmpBusinessType.findUmpBusinessType(id);
		OutputStream os = null;
		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		List<Map> listData = new ArrayList<Map>();
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("标签占比趋势分析").getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			os = response.getOutputStream();
			listData = LabelRatioReport.findLabelEntriesMap(platformName,shopName,
					brandNname,skuName,commentTypes,
					pageBean.getStartTime(), pageBean.getEndTime(), company);
			map.put("标签占比趋势分析", listData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel(title, colums, map, os);
		return;
	}

}

package com.nineclient.web.vocreport;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.poi.hssf.util.HSSFColor.GOLD;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpProduct;
import com.nineclient.model.VocBrand;
import com.nineclient.model.vocreport.ReportCommentRatioTrendMonth;
import com.nineclient.model.vocreport.ReportCommentRatioTrendYear;
import com.nineclient.model.vocreport.Series;
import com.nineclient.model.vocreport.TrendModel;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.ExcelUtil;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageBean;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.sun.mail.iap.Response;

@RequestMapping("/commentratiotrends")
@Controller
public class CommentRatioTrendController {
	/**
	 * 跳转页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "reportPage", produces = "text/html")
	public String reportPage(HttpServletRequest request, Model model,@RequestParam(value="displayId",required=false)Long displayId) {
		if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(request);
	    }
		request.getSession().setAttribute("displayId", displayId);
		
		// 查询voc渠道 要关联公司
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		List<UmpProduct> product = UmpProduct.findUmpProductById(Global.VOC);
		// TODO 公司关联渠道
		List<UmpChannel> channels = UmpChannel
				.findAllChannelsbuyProductNameAndCompanyService(company,
						product.get(0));
		model.addAttribute("channels", channels);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umpCompanyId", company.getId());
		List<VocBrand> vocbrands = VocBrand
				.findVocBrandsByChannelAndCompany(map);
		model.addAttribute("vocbrands", vocbrands);
		return "commentratiotrends/commentratiotrend";
	}

	/**
	 * 分页列表 只查询前十天条数据
	 * 
	 * @param request
	 * @param model
	 * @param channelName
	 * @param brandName
	 * @param skuName
	 * @param shopName
	 * @param commontType
	 * @param pageBean
	 * @param page
	 * @param size
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryData", produces = "text/html;charset=utf-8")
	public String queryData(HttpServletRequest request,
			@RequestParam(value = "dtGridPager") String dtGridPager,
			Model model) {
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		Pager pager = null;
		Map parm = new HashMap<String, String>();
		String channelName = "";
		String brandName = "";
		String skuName = "";
		String shopName = "";
		Date startTime=null;
		Date endTime=null;
		Date dateDay=null;
		Date dateYear=null;
		String dateType ="";
		List listData = new ArrayList();
		JsonValueProcessor jsonProcessor = new DateJsonValueProcessor();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, jsonProcessor);
		try {
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			parm = pager.getParameters();
			channelName = parm.get("channelName") == null ? "" : String
					.valueOf(parm.get("channelName"));
			brandName = parm.get("brandName") == null ? "" : String
					.valueOf(parm.get("brandName"));
			skuName = parm.get("skuName") == null ? "" : String.valueOf(parm
					.get("skuName"));
			shopName = parm.get("shopName") == null ? "" : String.valueOf(parm
					.get("shopName"));
			dateType = (String) parm.get("dateType");
			//dateDay = parm.get("dateDay")==null?null:DateUtil.getDateTime(String.valueOf(parm.get("dateDay")),DateUtil.YEAR_MONTH_DAY_FORMATER);
		//	dateYear =parm.get("dateYear")==null?null:DateUtil.getDateTime(String.valueOf(parm.get("dateYear")),DateUtil.YEAR_MONTH_DAY_FORMATER);
			startTime=parm.get("startTime")==null?null:DateUtil.getDateTime(String.valueOf(parm.get("startTime")));
			endTime=parm.get("endTime")==null?null:DateUtil.getDateTime(String.valueOf(parm.get("endTime")));
			String  s =  (String) parm.get("restart");
			if(s.equals("y")){
				 pager.setStartRecord(0);
				 pager.setNowPage(1);
			 }
		} catch (Exception e) {
			e.printStackTrace();
			return JSONObject.fromObject(pager).toString();
		}
		Integer page = pager.getNowPage();
		Integer size = pager.getPageSize();
		final int firstResult = page == null ? 0 : (page.intValue() - 1) * size;
		int totalPage = 0;
		Long totalCount = null;
		try {
			if(dateType.equals("1")){
				//月报
				totalCount = ReportCommentRatioTrendMonth.countCommentRatioTrend(channelName,
						brandName, skuName, shopName,startTime,
						endTime, company.getCompanyCode());
				if (totalCount > 0) {
					listData = ReportCommentRatioTrendMonth.findCommentRatioTrendList(
							channelName, brandName, skuName, shopName,startTime,
							 endTime,
							company.getCompanyCode(), firstResult, size);
				}
				
			}
			if(dateType.equals("2")){
				//年报
				totalCount = ReportCommentRatioTrendYear.countCommentRatioTrend(channelName,
						brandName, skuName, shopName,startTime,endTime,company.getCompanyCode());
					
				if (totalCount > 0) {
					listData = ReportCommentRatioTrendYear.findCommentRatioTrendList(
							channelName, brandName, skuName, shopName,
							 startTime,endTime,
							company.getCompanyCode(), firstResult, size);
				}
			}
			
			// 根据总记录数和每页的大小计算公有多少页
			if ((totalCount % size) > 0) {
				totalPage = (int) ((totalCount / size) + 1);
			} else {
				totalPage = (int) (totalCount / size);
			}
			pager.setRecordCount(totalCount);
			pager.setExhibitDatas(listData);
			pager.setPageCount(totalPage);
			pager.setIsSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			pager.setIsSuccess(false);
		}
		JSONObject pagerJSON = JSONObject.fromObject(pager);
		return pagerJSON.toString();
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
	 */
	@ResponseBody
	@RequestMapping(value = "queryDataPie", produces = "text/html;charset=utf-8")
	public String queryDataPie(
			HttpServletRequest request,
			Model model,
			@RequestParam(value="dateType",required=false)String dateType, 
			@RequestParam(value = "channelName", required = false) String channelName,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "shopName", required = false) String shopName,
			PageBean pageBean) {
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		String json = "[";
		try {
			Date endTime=DateUtil.getDateTimeYYYYMMDD(pageBean.getEndTime(),DateUtil.YEAR_MONTH_DAY_FORMATER);
			@SuppressWarnings("rawtypes")
			Map commentMap =null;
			if(dateType.equals("1")){//月报
				endTime= DateUtil.getDateTimeYYYYMMDD(endTime,DateUtil.YEAR_MONTH_DAY_FORMATER);
				 commentMap =  ReportCommentRatioTrendMonth.findAllCommentRatioPie(
							channelName, brandName, skuName, shopName,
							null,endTime, company,dateType);
			}
			if(dateType.equals("2")){//年报
			 endTime =DateUtil.getLastMonthFirstDay(endTime);
			 commentMap =  ReportCommentRatioTrendMonth.findAllCommentRatioPie(
						channelName, brandName, skuName, shopName,
						null,endTime, company,dateType);
			 
			}
			if (commentMap != null) {
				Long goodComment = Long.parseLong(String.valueOf(commentMap.get("goodComment")==null?"0":commentMap.get("goodComment")));
				Long commonComment =Long.parseLong(String.valueOf(commentMap.get("commonComment")==null?"0":commentMap.get("commonComment")));
				Long negativeComment =  Long.parseLong(String.valueOf(commentMap.get("negativeComment")==null?"0":commentMap.get("negativeComment")));
				if((goodComment+commonComment+negativeComment)<1){
					json += "{name:\'无数据\',y:100.0}";
				}else{
				json += "{name:\'好评\',y:"+goodComment+"}";
				json += ",{name:\'中评\',y:"+commonComment+"}";	
				json += ",{name:\'差评\',y:"+negativeComment+"}";	
				}
			} else {
				json += "{name:\'无数据\',y:100.0}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			json += "{name:\'无数据\',y:100.0}";
		}
		json += "]";
		return json;
	}
	/**
	 * 趋势图
	 * @param request
	 * @param model
	 * @param channelName
	 * @param brandName
	 * @param skuName
	 * @param shopName
	 * @param pageBean
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryDataTrend", produces = "text/html;charset=utf-8")
	public String queryDataTrend(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@RequestParam(value="dateType",required=false)String dateType, 
			@RequestParam(value = "channelName", required = false) String channelName,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "shopName", required = false) String shopName,
			PageBean pageBean){
		
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		List list =new ArrayList();
		
		if(dateType!=null&&dateType.equals("1")){
			list =	ReportCommentRatioTrendMonth.findAllEntitys(
					channelName, brandName, skuName, shopName,
					pageBean.getStartTime(), pageBean.getEndTime(), company);
		}
		if(dateType!=null&&dateType.equals("2")){
			list =	ReportCommentRatioTrendYear.findAllEntitys(
					channelName, brandName, skuName, shopName,
					pageBean.getStartTime(), pageBean.getEndTime(), company);
		}
		List<Series> listTrend=new ArrayList<Series>();
		List<Long> goodCommentList=new ArrayList<Long>();
		List<Long> commonCommentList=new ArrayList<Long>();
		List<Long> negativeCommentList=new ArrayList<Long>();
		List<String> dataList=new ArrayList<String>();
		for (Object trend : list) {
			if(dateType!=null&&dateType.equals("1")){
				ReportCommentRatioTrendMonth
				obj = (ReportCommentRatioTrendMonth)trend;
				dataList.add(DateUtil.getYYYYMMDD(obj.getCommentTime()));
				goodCommentList.add(Long.parseLong(String.valueOf(obj.getGoodComment())));
				commonCommentList.add(Long.parseLong(String.valueOf(obj.getCommonComment())));
				negativeCommentList.add(Long.parseLong(String.valueOf(obj.getNegativeComment())));
			}
			if(dateType!=null&&dateType.equals("2")){
				ReportCommentRatioTrendYear
				obj = (ReportCommentRatioTrendYear)trend;
				dataList.add(DateUtil.getYYYYMMDD(obj.getCommentTime()));
				goodCommentList.add(Long.parseLong(String.valueOf(obj.getGoodComment())));
				commonCommentList.add(Long.parseLong(String.valueOf(obj.getCommonComment())));
				negativeCommentList.add(Long.parseLong(String.valueOf(obj.getNegativeComment())));
			}
		}
		Series goodModel = new Series("好评",goodCommentList);
		Series commonModel = new Series("中评",commonCommentList);
		Series negativeModel = new Series("差评",negativeCommentList);
		listTrend.add(goodModel);
		listTrend.add(commonModel);
		listTrend.add(negativeModel);
		TrendModel trendModel = new TrendModel();
		trendModel.setCategories(dataList);
		trendModel.setSeries(listTrend);
        request.setAttribute("good", "ddddf");
 
		JSONObject pagerJSON = JSONObject.fromObject(trendModel);
		return pagerJSON.toString();
		
	}
	
	
	/**
	 * 模板导出
	 * 
	 * @param httpServletRequest
	 * @param response
	 */
	@RequestMapping(value = "exportExecl", method = RequestMethod.GET)
	public void exportExecl(
			HttpServletRequest httpServletRequest,
			@RequestParam(value="dateType",required=false)String dateType, 
			@RequestParam(value = "channelName", required = false) String channelName,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "shopName", required = false) String shopName,
			PageBean pageBean, HttpServletResponse response) {
		// 生成提示信息，
		response.setContentType("application/vnd.ms-excel");
		PubOperator user = CommonUtils
				.getCurrentPubOperator(httpServletRequest);
		UmpCompany company = user.getCompany();
		String[] title = { "平台名称", "店铺名称", "品牌名称", "好评", "中评", "差评",
				"日期" };
		String[] colums = { "channelName", "shopName", "brandName","goodComment","commonComment","negativeComment"
				, "dateTime" };
		// UmpBusinessType businessType =
		// UmpBusinessType.findUmpBusinessType(id);
		OutputStream os = null;
		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		List<Map> listData = new ArrayList<Map>();
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("好中差评论趋势").getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			os = response.getOutputStream();
			if(dateType.equals("1")){
				listData = ReportCommentRatioTrendMonth.findCommentRatioTrendEntries(channelName,
						brandName, skuName, shopName, pageBean.getStartTime(),
						pageBean.getEndTime(), company);
			}
			if(dateType.equals("2")){
				listData = ReportCommentRatioTrendYear.findCommentRatioTrendEntries(channelName,
						brandName, skuName, shopName, pageBean.getStartTime(),
						pageBean.getEndTime(), company);
			}
			
			map.put("好中差评论趋势", listData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel(title, colums, map, os);
		return;
	}

}

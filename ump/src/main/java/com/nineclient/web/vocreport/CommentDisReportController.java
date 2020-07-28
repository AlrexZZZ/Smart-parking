package com.nineclient.web.vocreport;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
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

import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.VocBrand;
import com.nineclient.model.vocreport.ReportCommentDay;
import com.nineclient.model.vocreport.ReportCommentMonth;
import com.nineclient.model.vocreport.ReportCommentYear;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.ExcelUtil;
import com.nineclient.utils.Global;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;

@RequestMapping("/commentDisReport")
@Controller
@RooWebScaffold(path = "vocreport", formBackingObject = CommentDisReportController.class)
public class CommentDisReportController {
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
		return "vocreports/commentReport";
	}

	/**
	 * 折线图
	 * 
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "queryOper", produces = "text/html;charset=utf-8")
	public String queryDataBar(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@RequestParam(value = "channelName", required = false) String channelName,
			@RequestParam(value = "shopName", required = false) String shopName,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "dateDay", required = false) String dateDay,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "dateYear", required = false) String dateYear,
			@RequestParam(value = "dateType", required = false) String dateType

	) throws IOException {
 
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		//如果是查询小时数据
		if (dateType.equals("0")) {
			List<ReportCommentDay> listData = new ArrayList<ReportCommentDay>();
			List<ReportCommentDay> list = null;
			list = ReportCommentDay.findRangeByQuery(0, 0, channelName,
					shopName, brandName, skuName, dateDay, company.getCompanyCode());
			if (list.size() > 0) {
				String hourStr = "";
				
				for(int i=0 ;i<24;i++){
					ReportCommentDay r = new ReportCommentDay();
					//初始化时间
					 if(i<10){
						 hourStr = dateDay+" 0"+i;
						 r.setDateDayStr(dateDay+" 0"+i); 
					 }else{
						 hourStr = dateDay+" "+i;
						 r.setDateDayStr(dateDay+" "+i);
					 }      
					       //初始化时间
					        r.setDateDayStr(hourStr);
					for(int j = 0;j<list.size();j++){
						   //如果当前时间有数据
						String nowStr = DateUtil.getHourStrOfDate(list.get(j).getDateDay());
						if(hourStr.equals(nowStr)){
							r.setCommentGood(list.get(j).getCommentGood());
							r.setCommentBetter(list.get(j).getCommentBetter());
							r.setCommentBad(list.get(j).getCommentBad());
							break;
						}
					}
					        listData.add(r);
					
				}
				
				String a = ReportCommentDay.toJsonArray(listData);
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(a.toString());
			}

		}
	//	如果是查询的是天数据
		if (dateType.equals("1")) {
			List<ReportCommentMonth> listData = new ArrayList<ReportCommentMonth>();
			List<ReportCommentMonth> list = null;
			list = ReportCommentMonth.findRangeByQuery(0, 0, channelName,
					shopName, brandName, skuName, startTime,endTime, company.getCompanyCode());
			if (list.size() > 0) {
	List<String> dayListStr = DateUtil.getDateList(startTime, endTime, Calendar.DAY_OF_YEAR);			
			for(int a = 0 ;a<dayListStr.size();a++){
				ReportCommentMonth r = new ReportCommentMonth();
				r.setDateMonthStr(dayListStr.get(a));
				for(int b=0;b<list.size();b++){
					if(dayListStr.get(a).equals(DateUtil.getDayStr(list.get(b).getDateDay()))){
						r.setCommentGood(list.get(b).getCommentGood());
						r.setCommentBetter(list.get(b).getCommentBetter());
						r.setCommentBad(list.get(b).getCommentBad());
						break;
					}
				}
				
				 listData.add(r);
			}	
				
				String a = ReportCommentMonth.toJsonArray(listData);
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(a.toString());
			}
		}
//		如果是查询的是月份数据
		if (dateType.equals("2")) {
			List<ReportCommentYear> dataList = new ArrayList<ReportCommentYear>();
			List<ReportCommentYear> list = null;
			list = ReportCommentYear.findRangeByQuery(0, 0, channelName,
					shopName, brandName, skuName, dateYear, company.getCompanyCode());
			if (list.size() > 0) {
				String yearMonth = "";
				for(int i = 1;i<=12;i++){
					ReportCommentYear r = new ReportCommentYear();	
					if(i<10){
						yearMonth = dateYear+"-0"+i;	
					}else{
						yearMonth = dateYear+"-"+i; 
					}
					r.setDateYearStr(yearMonth);
				for(ReportCommentYear m:list){
					if(yearMonth.equals(DateUtil.getMonthStrOfDate(m.getDateDay()))){
						r.setCommentGood(m.getCommentGood());
						r.setCommentBetter(m.getCommentBetter());
						r.setCommentBad(m.getCommentBad());
						break;
					}
				}	
					
				dataList.add(r);	
				}
				
				String a = ReportCommentYear.toJsonArray(dataList);
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(a.toString());
			}
		}
		return null;
	}

	// 表格上数据
	@ResponseBody
	@RequestMapping(value = "queryDataGrid", produces = "application/json")
	public void queryDataGrid(HttpServletRequest request,
			HttpServletResponse response, String dtGridPager)
			throws IOException {
		Pager pager = null;
		String channelName = "";
		String shopName = "";
		String brandName = "";
		String skuName = "";
		String dateType = "";
		String dateDay = "";

		String dateYear = "";
		String startTime = "";
		String endTime = "";
		Map parm = new HashMap<String, String>();
		try {
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			parm = pager.getParameters();
			dateType = (String) parm.get("dateType");
			channelName = (String) parm.get("channelName");
			shopName = (String) parm.get("shopName");
			brandName = (String) parm.get("brandName");
			skuName = (String) parm.get("skuName");
			dateDay = (String) parm.get("dateDay");

			dateYear = (String) parm.get("dateYear");
			startTime = (String) parm.get("startTime");
			endTime = (String) parm.get("endTime");
		} catch (Exception e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		int firstResult = pager.getStartRecord();
		int maxResults = pager.getPageSize();
		int totalPage = 0;
		List list = null;

		try {
			if (dateType.equals("0")) {
				long totalCount = ReportCommentDay.findRcordCountByQuery(
						channelName, shopName, brandName, skuName, dateDay,
						company.getCompanyCode());
				pager.setRecordCount(totalCount);// 设置总记录数
				// 根据总记录数和每页的大小计算公有多少页
				if ((totalCount % maxResults) > 0) {
					totalPage = (int) ((totalCount / maxResults) + 1);
				} else {
					totalPage = (int) (totalCount / maxResults);
				}
				list = ReportCommentDay.findRangeByQuery(firstResult,
						maxResults, channelName, shopName, brandName, skuName,
						dateDay, company.getCompanyCode());

			}if (dateType.equals("1")) {
				long totalCount = ReportCommentMonth.findRcordCountByQuery(
						channelName, shopName, brandName, skuName, startTime,endTime,
						company.getCompanyCode());
				pager.setRecordCount(totalCount);// 设置总记录数
				// 根据总记录数和每页的大小计算公有多少页
				if ((totalCount % maxResults) > 0) {
					totalPage = (int) ((totalCount / maxResults) + 1);
				} else {
					totalPage = (int) (totalCount / maxResults);
				}
				list = ReportCommentMonth.findRangeByQuery(firstResult,
						maxResults, channelName, shopName, brandName, skuName,
						startTime,endTime, company.getCompanyCode());

			}if (dateType.equals("2")) {
				long totalCount = ReportCommentYear.findRcordCountByQuery(
						channelName, shopName, brandName, skuName, dateYear,
						company.getCompanyCode());
				pager.setRecordCount(totalCount);// 设置总记录数
				// 根据总记录数和每页的大小计算公有多少页
				if ((totalCount % maxResults) > 0) {
					totalPage = (int) ((totalCount / maxResults) + 1);
				} else {
					totalPage = (int) (totalCount / maxResults);
				}
				list = ReportCommentYear.findRangeByQuery(firstResult,
						maxResults, channelName, shopName, brandName, skuName,
						dateYear, company.getCompanyCode());

			}

			pager.setExhibitDatas(list);
			pager.setIsSuccess(true);pager.setPageCount(totalPage);
			JSONObject pagerJSON = JSONObject.fromObject(pager);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(pagerJSON.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 导出数据

	@RequestMapping(value = "exportExcel", method = RequestMethod.GET)
	public void exportExcel(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "channelName", required = false) String channelName,
			@RequestParam(value = "shopName", required = false) String shopName,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "dateDay", required = false) String dateDay,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "dateYear", required = false) String dateYear,
			@RequestParam(value = "dateType", required = false) String dateType)
			throws IOException {

		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		List<ReportCommentDay> list = null;
		List<Map> listData = new ArrayList<Map>();
		String companyCode = company.getCompanyCode();
 if(dateType.equals("0")){
		listData = ReportCommentDay.findSkuRangeReportEntries(channelName, shopName, brandName, skuName, dateDay, companyCode);
		
 }if(dateType.equals("1")){
	 listData = ReportCommentMonth.findMapReportEntries(channelName, shopName, brandName, skuName, startTime, endTime, companyCode);
 }if(dateType.equals("2")){
	 listData = ReportCommentYear.findCommenYearReportEntries(channelName, shopName, brandName, skuName, dateYear, companyCode);
 }
		
//		if (listData.size() > 0) {
			String[] title = { "时间", "好评","中评","差评" };
			String[] colums = { "date_day", "comment_good","comment_better","comment_bad" };
			OutputStream os = null;
			Map<String, List<Map>> map = new HashMap<String, List<Map>>();
			try {
				response.setHeader(
						"content-disposition",
						"attachment;filename="
								+ new String(("好中差评分布趋势").getBytes("gb2312"),
										"iso8859-1") + ".xls");
				os = response.getOutputStream();

				map.put("好中差评分布趋势", listData);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ExcelUtil.exportToExcel(title, colums, map, os);
			return;
		}

//	}

}

package com.nineclient.web.vocreport;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.nineclient.model.vocreport.ReportHotWord;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.ExcelUtil;
import com.nineclient.utils.Global;
import com.nineclient.utils.NumberUtil;
import com.nineclient.utils.PageBean;
import com.nineclient.utils.ReportModel;

@RequestMapping("/hotwordreports")
@Controller
@RooWebScaffold(path = "hotwordreports", formBackingObject = HotWordReportController.class)
public class HotWordReportController {
	@RequestMapping(value = "reportPage", produces = "text/html")
	public String reportPage(HttpServletRequest request, Model model,@RequestParam(value="displayId",required=false)Long displayId) {
		if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(request);
	    }
		request.getSession().setAttribute("displayId", displayId);
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

		return "hotwordreports/hotWord";
	}

	/**
	 * 分页列表 只查询前十条数据
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
	public String queryData(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "channelName", required = false) String channelName,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "shopName", required = false) String shopName,
			PageBean pageBean,
			@RequestParam(value = "page", required = false) Long page,
			@RequestParam(value = "size", required = false) Long size) {
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		List<ReportHotWord> hotwordreports = new ArrayList<ReportHotWord>();
		int count = 0;
		long sum = 0;
		size = 10L;
		page = page == null ? null : page;
		size = size == null ? 10 : size;
		int sizeNo = size == null ? 10 : size.intValue();
		int firstResult = 0;
		  if(page >= 1 && sizeNo > 0){
			  firstResult = (int)((page - 1)*sizeNo + 1) ; 
		  }
		try {
			hotwordreports = ReportHotWord.findReportHotWordEntries(
					firstResult, sizeNo, channelName, brandName, skuName,
					shopName, pageBean.getStartTime(), pageBean.getEndTime(),
					company);
			Long nrOfPages = ReportHotWord.countReportHotWords(channelName,
					brandName, skuName, shopName, pageBean.getStartTime(),
					pageBean.getEndTime(), company);
			count = nrOfPages.intValue();
			
//			sum = ReportHotWord.sumReportHotWords(channelName, brandName,
//					skuName, shopName, pageBean.getStartTime(),
//					pageBean.getEndTime(), company);
			if(hotwordreports != null && hotwordreports.size() > 0){
				for(ReportHotWord r:hotwordreports){
					sum += r.getIncrement();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ReportModel<ReportHotWord> reportModel = new ReportModel<ReportHotWord>();
		reportModel.setDataJson(ReportHotWord.toJsonArray(hotwordreports));
		reportModel.setPageNo(page.intValue());
		reportModel.setPageSize(sizeNo);
		reportModel.setTotalCount(count);
		reportModel.setSumNum(sum);
		return reportModel.toJson();
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
			@RequestParam(value = "channelName", required = false) String channelName,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "shopName", required = false) String shopName,
			PageBean pageBean) {
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		String json = "[";
		try {
			List<Map> hotwordreports = ReportHotWord.findAllReportHotWords(
					channelName, brandName, skuName, shopName,
					pageBean.getStartTime(), pageBean.getEndTime(), company);
			// Long comentCount = HotWordReport.sumHotWordReports(channelName,
			// brandName, skuName, shopName,
			// pageBean.getStartTime(), pageBean.getEndTime(), company);
			String name;
			boolean flag = true;
			if (hotwordreports != null && hotwordreports.size() > 0) {
				for (Map hotWordReport : hotwordreports) {
					name = String.valueOf(hotWordReport.get("hotWordName"));
					if (flag) {
						json += "{name:\'"
								+ name
								+ "\',y:"
								+ String.valueOf(hotWordReport.get("increment"))
								+ "}";
						flag = false;
					} else {
						json += ",{name:\'"
								+ name
								+ "\',y:"
								+ String.valueOf(hotWordReport.get("increment"))
								+ "}";
					}
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
	 * 模板导出
	 * 
	 * @param httpServletRequest
	 * @param response
	 */
	@RequestMapping(value = "exportExecl", method = RequestMethod.GET)
	public void exportExecl(
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "channelName", required = false) String channelName,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "shopName", required = false) String shopName,
			PageBean pageBean, HttpServletResponse response) {
		// 生成提示信息，
		response.setContentType("application/vnd.ms-excel");
		PubOperator user = CommonUtils.getCurrentPubOperator(httpServletRequest);
		UmpCompany company = user.getCompany();
		String[] title = { "平台名称", "店铺名称", "品牌名称", "SKU名称", "热词名称", "热词增量" };
		String[] colums = { "channelName", "shopName", "brandName", "skuName",
				"hotWordName", "increment" };
		// UmpBusinessType businessType =
		// UmpBusinessType.findUmpBusinessType(id);
		OutputStream os = null;
		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		List<Map> listData = new ArrayList<Map>();
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("产品热词分析").getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			os = response.getOutputStream();
			listData = ReportHotWord.findReportHotWordEntries(channelName,
					brandName, skuName, shopName, pageBean.getStartTime(),
					pageBean.getEndTime(), company);
			map.put("热词分析", listData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel(title, colums, map, os);
		return;
	}

}

package com.nineclient.cbd.wcc.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Border;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nineclient.cbd.wcc.constant.CacluatePage;
import com.nineclient.cbd.wcc.dto.WccFinancialProductDTO;
import com.nineclient.cbd.wcc.dto.WccFinancialUserDTO;
import com.nineclient.cbd.wcc.model.CbdWccProprietor;
import com.nineclient.cbd.wcc.model.WccFees;
import com.nineclient.cbd.wcc.model.WccFinancialProduct;
import com.nineclient.cbd.wcc.model.WccFinancialUser;
import com.nineclient.model.PubOperator;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.ExcelUtil;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;

/**
 * 理财产品
 * 
 * @author 9client
 *
 */
@RequestMapping("/financialProduct")
@Controller
@RooWebScaffold(path = "cbdwcc/financialProduct", formBackingObject = WccFinancialProduct.class)
public class FinancialProductController {

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "loadFinancial", produces = "application/json")
	public void loadFinancial(String dtGridPager, HttpServletRequest request,
			Model model, HttpServletResponse response)
			throws UnsupportedEncodingException, ParseException {
		Pager pager = null;
		Map parm = new HashMap<String, String>();
		String financialName = "";
		String productCode = "";
		String riskLevel = "";
		String productType = "";
		String saleBeginDate = "";
		String platFormId = "";
		// 查询公众平台
		PubOperator pub = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platformUser = WccPlatformUser
				.findAllWccPlatformUsers(pub);
		StringBuffer ids = new StringBuffer();
		if (platformUser != null && platformUser.size() > 0) {
			int n = 1;
			for (WccPlatformUser wccPlatformUser : platformUser) {
				ids.append(wccPlatformUser.getId());
				if (n != platformUser.size()) {
					ids.append(",");
				}
				n++;
			}
		}
		try {
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			parm = pager.getParameters();
			financialName = (String) parm.get("financialName");
			productCode = (String) parm.get("productCode");
			riskLevel = (String) parm.get("riskLevel");
			productType = (String) parm.get("productType");
			saleBeginDate = (String) parm.get("saleBeginDate");
			platFormId = (String) parm.get("platFormId");
			String s = (String) parm.get("restart");
			if (s.equals("y")) {
				pager.setStartRecord(0);
				pager.setNowPage(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		QueryModel<WccFinancialProduct> qm = new QueryModel<WccFinancialProduct>();
		
		String[] platIds = null;
		Set<WccPlatformUser> platformUsers = new HashSet<WccPlatformUser>();
		WccPlatformUser user = null;
		if (platFormId != null && !"".equals(platFormId)) {
			platIds = platFormId.split(",");
			if (platIds != null && platIds.length > 0) {
				for (String id : platIds) {
					user = WccPlatformUser.findWccPlatformUser(Long.valueOf(id));
					platformUsers.add(user);
				}
			}
		} else {
			platformUsers.addAll(platformUser);
		}
		qm.putParams("platformUsers", platformUsers);
		
		qm.putParams("financialName", financialName);
		qm.putParams("productCode", productCode);
		qm.putParams("riskLevel", riskLevel);
		qm.putParams("productType", productType);
		qm.putParams("saleBeginDate", saleBeginDate);
		qm.putParams("platFormId", platFormId);
		qm.setStart(pager.getStartRecord());
		qm.setLimit(pager.getPageSize());
		PageModel<WccFinancialProduct> p = WccFinancialProduct.getRecord(qm);
		CacluatePage.totalPage(p.getPageSize(), p.getTotalCount());
		List<WccFinancialProduct> lit = p.getDataList();
		List<WccFinancialProductDTO> list = new ArrayList<WccFinancialProductDTO>();
		if (null != lit && lit.size() > 0) {
			for (WccFinancialProduct s : lit) {
				String plIds = "";
				String names = "";
				if (null != s.getPlatformUsers()
						&& s.getPlatformUsers().size() > 0) {
					Set<WccPlatformUser> ss = s.getPlatformUsers();
					Iterator<WccPlatformUser> it = ss.iterator();
					while (it.hasNext()) {
						WccPlatformUser u = it.next();
						if (null != u.getId() && !"".equals(u.getId())) {
							plIds += u.getId() + ",";
						}
						if (null != u.getAccount()
								&& !"".equals(u.getAccount())) {
							names += u.getAccount() + ",";
						}
					}
				}
				if (plIds != "") {
					plIds = plIds.substring(0, (plIds.length() - 1));
				}
				if (names != "") {
					names = names.substring(0, (names.length() - 1));
				}
				WccPlatformUser plf = new WccPlatformUser();
				plf.setAccount(names);
				plf.setRemark(plIds);
				list.add(new WccFinancialProductDTO(s.getId(), plf, s
						.getFinancialName(), s.getThemeImage(), s
						.getThemeImageText(), s.getProductCode(), s
						.getRiskLevel(), s.getProductType(), s
						.getSaleBeginDate(), s.getSaleEndDate(), s
						.getValueDate(), s.getArrivalDate(), s
						.getInvestmentHorizon(), s.getExpectedYield(), s
						.getMinMoney(), s.getMaxMoney(),
						s.getIncreasingMoney(), s.getTotalMoney(), s
								.getTotalNumber(), s.getInsertTime(), s
								.getIsUsing(),s.getCk()));
			}
		}
		pager.setExhibitDatas(list);
		pager.setPageCount(CacluatePage.totalPage(p.getPageSize(),
				p.getTotalCount()));
		pager.setRecordCount(Long.parseLong(p.getTotalCount() + "")); // 设置总记录数
		pager.setIsSuccess(true);
		try {
			response.setContentType("application/json"); // 设置返回的数据为json对象
			JsonValueProcessor jsonProcessor = new DateJsonValueProcessor();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class,
					jsonProcessor);
			JSONObject pagerJSON = JSONObject.fromObject(pager);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(pagerJSON.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 跳转列表页面
	 * 
	 * @param request
	 * @param uiModel
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "showRecord", produces = "text/html")
	public String showRecord(HttpServletRequest request, Model uiModel,
			HttpServletResponse response) {
		List<WccPlatformUser> list = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
		uiModel.addAttribute("plats", list);
		return "financialProduct/culture";
	}

	/**
	 * 跳转到新增、修改页面
	 * 
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(HttpServletRequest request,
			HttpServletResponse response, Model uiModel,
			@RequestParam(value = "dataId", required = false) String dataId) {
		if (null != dataId && !"".equals(dataId)) {
			WccFinancialProduct w = new WccFinancialProduct();
			WccFinancialProduct wccFinancialProduct = w
					.findWccFinancialProduct(Long.parseLong(dataId));
			String pltIds = "";
			if (null != wccFinancialProduct.getPlatformUsers()) {
				Set<WccPlatformUser> ss = wccFinancialProduct
						.getPlatformUsers();
				Iterator<WccPlatformUser> it = ss.iterator();
				while (it.hasNext()) {
					WccPlatformUser u = it.next();
					if (null != u.getId() && !"".equals(u.getId())) {
						pltIds += u.getId() + ",";
					}
				}
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				if (null != wccFinancialProduct.getInsertTime()) {
					uiModel.addAttribute("insertTimeStr",
							df.format(wccFinancialProduct.getInsertTime()));
				}
			}
			if (null != pltIds && !"".equals(pltIds)) {
				uiModel.addAttribute("pltIds",
						pltIds.subSequence(0, (pltIds.length() - 1)));
			} else {
				uiModel.addAttribute("pltIds", "");
			}
			uiModel.addAttribute("wccFinancialProduct", wccFinancialProduct);
		}
		return "financialProduct/create";
	}

	/**
	 * 根据wccFinancialProduct的id判断新增或修改
	 * 
	 * @param httpServletRequest
	 * @param uiModel
	 * @param wccFinancialProduct
	 * @param bindingResult
	 * @param insertTimeStr
	 * @param saleBeginDateStr
	 * @param saleEndDateStr
	 * @param valueDateStr
	 * @param arrivalDateStr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(
			HttpServletRequest httpServletRequest,
			Model uiModel,
			@Valid WccFinancialProduct wccFinancialProduct,
			BindingResult bindingResult,
			@RequestParam(value = "insertTimeStr", required = false) String insertTimeStr,
			@RequestParam(value = "saleBeginDateStr", required = false) String saleBeginDateStr,
			@RequestParam(value = "saleEndDateStr", required = false) String saleEndDateStr,
			@RequestParam(value = "valueDateStr", required = false) String valueDateStr,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "arrivalDateStr", required = false) String arrivalDateStr)
			throws UnsupportedEncodingException {
//		if (bindingResult.hasErrors()) {
//			return "redirect:/financialProduct?form";
//		}
		if (null != wccFinancialProduct.getId()
				&& !"".equals(wccFinancialProduct.getId())) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			if (insertTimeStr != null && !"".equals(insertTimeStr)) {
				try {
					wccFinancialProduct.setInsertTime(df.parse(insertTimeStr));
					wccFinancialProduct.setInsertTimeStr("");
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if (saleBeginDateStr != null && !"".equals(saleBeginDateStr)) {
				wccFinancialProduct.setSaleBeginDate(DateUtil.getDateTime(
						saleBeginDateStr, DateUtil.YEAR_MONTH_DAY_FORMATER));
			}
			if (saleEndDateStr != null && !"".equals(saleEndDateStr)) {
				wccFinancialProduct.setSaleEndDate(DateUtil.getDateTime(
						saleEndDateStr, DateUtil.YEAR_MONTH_DAY_FORMATER));
			}
			if (valueDateStr != null && !"".equals(valueDateStr)) {
				wccFinancialProduct.setValueDate(DateUtil.getDateTime(
						valueDateStr, DateUtil.YEAR_MONTH_DAY_FORMATER));
			}
			if (arrivalDateStr != null && !"".equals(arrivalDateStr)) {
				wccFinancialProduct.setArrivalDate(DateUtil.getDateTime(
						arrivalDateStr, DateUtil.YEAR_MONTH_DAY_FORMATER));
			}
			if(endDate != null && !"".equals(endDate)){
				wccFinancialProduct.setEndDate(DateUtil.getDateTime(
						endDate, DateUtil.YEAR_MONTH_DAY_FORMATER));
			}
			// 根据页面传来的值更新实体信息
			WccFinancialProduct wccFinancialProductUpdate = copyFinancialProduct(wccFinancialProduct);
			uiModel.asMap().clear();
			wccFinancialProductUpdate.merge();
		} else {
			uiModel.asMap().clear();
			if (saleBeginDateStr != null && !"".equals(saleBeginDateStr)) {
				wccFinancialProduct.setSaleBeginDate(DateUtil.getDateTime(
						saleBeginDateStr, DateUtil.YEAR_MONTH_DAY_FORMATER));
			}
			if (saleEndDateStr != null && !"".equals(saleEndDateStr)) {
				wccFinancialProduct.setSaleEndDate(DateUtil.getDateTime(
						saleEndDateStr, DateUtil.YEAR_MONTH_DAY_FORMATER));
			}
			if (valueDateStr != null && !"".equals(valueDateStr)) {
				wccFinancialProduct.setValueDate(DateUtil.getDateTime(
						valueDateStr, DateUtil.YEAR_MONTH_DAY_FORMATER));
			}
			if (arrivalDateStr != null && !"".equals(arrivalDateStr)) {
				wccFinancialProduct.setArrivalDate(DateUtil.getDateTime(
						arrivalDateStr, DateUtil.YEAR_MONTH_DAY_FORMATER));
			}
			if(endDate != null && !"".equals(endDate)){
				wccFinancialProduct.setEndDate(DateUtil.getDateTime(
						endDate, DateUtil.YEAR_MONTH_DAY_FORMATER));
			}
			wccFinancialProduct.setInsertTime(new Date());
			wccFinancialProduct.setIsDelete(false);
			wccFinancialProduct.setIsUsing(true);
			wccFinancialProduct.persist();
		}
		return "redirect:/financialProduct/showRecord?";
	}

	/**
	 * 修改时将页面传来的值赋给需要修改的对象
	 * 
	 * @param wccFinancialProduct
	 *            页面传来的修改对象
	 * @return 要修改的对象
	 */
	private WccFinancialProduct copyFinancialProduct(
			WccFinancialProduct wccFinancialProduct) {
		WccFinancialProduct wccFinancialProductUpdate = WccFinancialProduct
				.findWccFinancialProduct(wccFinancialProduct.getId());
		wccFinancialProductUpdate.setArrivalDate(wccFinancialProduct
				.getArrivalDate());
		wccFinancialProductUpdate.setExpectedYield(wccFinancialProduct
				.getExpectedYield());
		wccFinancialProductUpdate.setFinancialName(wccFinancialProduct
				.getFinancialName());
		wccFinancialProductUpdate.setIncreasingMoney(wccFinancialProduct
				.getIncreasingMoney());
		wccFinancialProductUpdate.setInvestmentHorizon(wccFinancialProduct
				.getInvestmentHorizon());
		wccFinancialProductUpdate
				.setMaxMoney(wccFinancialProduct.getMaxMoney());
		wccFinancialProductUpdate
				.setMinMoney(wccFinancialProduct.getMinMoney());
		wccFinancialProductUpdate.setPlatformUsers(wccFinancialProduct
				.getPlatformUsers());
		wccFinancialProductUpdate.setProductCode(wccFinancialProduct
				.getProductCode());
		wccFinancialProductUpdate.setProductType(wccFinancialProduct
				.getProductType());
		wccFinancialProductUpdate.setRiskLevel(wccFinancialProduct
				.getRiskLevel());
		wccFinancialProductUpdate.setSaleBeginDate(wccFinancialProduct
				.getSaleBeginDate());
		wccFinancialProductUpdate.setSaleEndDate(wccFinancialProduct
				.getSaleEndDate());
		wccFinancialProductUpdate.setThemeImage(wccFinancialProduct
				.getThemeImage());
		wccFinancialProductUpdate.setThemeImageText(wccFinancialProduct
				.getThemeImageText());
		wccFinancialProductUpdate.setTotalMoney(wccFinancialProduct
				.getTotalMoney());
		wccFinancialProductUpdate.setTotalNumber(wccFinancialProduct
				.getTotalNumber());
		wccFinancialProductUpdate.setValueDate(wccFinancialProduct
				.getValueDate());
		wccFinancialProductUpdate.setEndDate(wccFinancialProduct
				.getEndDate());
		return wccFinancialProductUpdate;
	}

	@RequestMapping(params = "delete", produces = "text/html")
	public void deleteForm(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "dataId", required = true) Long dataId) {
		WccFinancialProduct wccFinancialProduct = null;
		if (dataId != null && !"".equals(dataId)) {
			wccFinancialProduct = WccFinancialProduct
					.findWccFinancialProduct(dataId);
		}
		if (wccFinancialProduct != null) {
			wccFinancialProduct.remove();
		}
	}

	/**
	 * 根据理财产品id跳转到预约此产品用户列表页面
	 * 
	 * @param request
	 * @param response
	 * @param uiModel
	 * @param dataId
	 * @return
	 */
	@RequestMapping(params = "showFinancialUser", produces = "text/html")
	public String showFinancialUser(HttpServletRequest request,
			HttpServletResponse response, Model uiModel,
			@RequestParam(value = "dataId", required = false) String dataId) {
		uiModel.addAttribute("dataId", dataId);
		WccFinancialProduct wcc=WccFinancialProduct.findWccFinancialProduct(Long.parseLong(dataId));
		wcc.setCk("0");
		wcc.merge();
		return "financialProduct/showFinancialUser";
	}

	/**
	 * 后台分页展现预约用户
	 * 
	 * @param dtGridPager
	 * @param request
	 * @param model
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws ParseException
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "loadFinancialUser", produces = "application/json")
	public void loadFinancialUser(String dtGridPager,
			HttpServletRequest request, Model model,
			HttpServletResponse response) throws UnsupportedEncodingException,
			ParseException {
		Pager pager = null;
		Map parm = new HashMap<String, String>();
		String financialProductId = "";
		try {
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			parm = pager.getParameters();
			String s = (String) parm.get("restart");
			financialProductId = (String) parm.get("financialProductId");
			if (s.equals("y")) {
				pager.setStartRecord(0);
				pager.setNowPage(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		WccFinancialProduct wccFinancialProduct = WccFinancialProduct
				.findWccFinancialProduct(Long.valueOf(financialProductId));
		QueryModel<WccFinancialUser> qm = new QueryModel<WccFinancialUser>();
		qm.putParams("dataId", financialProductId);
		qm.setStart(pager.getStartRecord());
		qm.setLimit(pager.getPageSize());
		PageModel<WccFinancialUser> p = WccFinancialUser.getRecord(qm);
		CacluatePage.totalPage(p.getPageSize(), p.getTotalCount());
		List<WccFinancialUser> lit = p.getDataList();
		List<WccFinancialUserDTO> list = new ArrayList<WccFinancialUserDTO>();
		if (null != lit && lit.size() > 0) {
			for (WccFinancialUser user : lit) {
				list.add(new WccFinancialUserDTO(user.getId(),
						wccFinancialProduct.getProductCode(), user
								.getCredentialType(), user
								.getCredentialNumber(), user.getMoney(), user
								.getUserName(),user.getIsActive(),user.getPhone(),user.getInsertTime(),user.getPlatformUsers()));
			}
		}
		pager.setExhibitDatas(list);
		pager.setPageCount(CacluatePage.totalPage(p.getPageSize(),
				p.getTotalCount()));
		pager.setRecordCount(Long.parseLong(p.getTotalCount() + "")); // 设置总记录数
		pager.setIsSuccess(true);
		try {
			response.setContentType("application/json"); // 设置返回的数据为json对象
			JsonValueProcessor jsonProcessor = new DateJsonValueProcessor();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class,
					jsonProcessor);
			JSONObject pagerJSON = JSONObject.fromObject(pager);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(pagerJSON.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量导出
	 * 
	 * @param uiModel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/batchExport", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String batchExport(Model uiModel, HttpServletRequest request,
			@RequestParam(value = "dataId", required = false) String dataId) {
		List<WccFinancialUser> list = null;
		if (dataId != null && !"".equals(dataId)) {
			Long financialProjectId = Long.valueOf(dataId);
			try {
				list = WccFinancialUser
						.findFinancialUsersByFinancialProjectId(financialProjectId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			list = new ArrayList<WccFinancialUser>();
		}

		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();

		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");

		// 设置excel每列宽度
		// sheet.setColumnWidth(0, 4000);
		// sheet.setColumnWidth(1, 3500);

		// 创建字体样式
		HSSFFont font = wb.createFont();
		font.setFontName("Verdana");
		font.setBoldweight((short) 100);
		font.setFontHeight((short) 300);
		font.setColor(HSSFColor.BLUE.index);

		// 创建单元格样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		// 设置边框
		style.setBottomBorderColor(HSSFColor.RED.index);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setFont(font);// 设置字体

		// 写表格头
		// 行
		int rowNumber = 1;
		// 列
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// 第一行信息
				WccFinancialUser wccFinancialUser = list.get(i);
				Set<WccFinancialProduct> wccFinancialProducts = wccFinancialUser
						.getWccFinancialProducts();
				WccFinancialProduct wccFinancialProduct = null;
				for (WccFinancialProduct product : wccFinancialProducts) {
					wccFinancialProduct = product;
				}
				List<Map<String, Object>> list_map = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("产品代码",
						wccFinancialProduct.getProductCode() != null ? wccFinancialProduct
								.getProductCode() : "");
				list_map.add(map);
				map.put("证件类型", wccFinancialUser.getCredentialType()
						.getTypeName() != null ? wccFinancialUser
						.getCredentialType().getTypeName() : "");
				list_map.add(map);
				map.put("证件号码",
						wccFinancialUser.getCredentialNumber() != null ? wccFinancialUser
								.getCredentialNumber() : "");
				list_map.add(map);
				map.put("申请预约额度",
						wccFinancialUser.getMoney() != null ? wccFinancialUser
								.getMoney() : "");
				list_map.add(map);
				map.put("客户姓名",
						wccFinancialUser.getUserName() != null ? wccFinancialUser
								.getUserName() : "");
				list_map.add(map);
				map.put("公众平台来源",
						wccFinancialUser.getUserName() != null ? wccFinancialUser
								.getPlatformUsers() : "");
				list_map.add(map);
				HSSFRow firstrow = sheet.createRow(0); // 下标为0的行开始
				int CountColumnNum = list_map.size();

				HSSFCell[] firstcell = new HSSFCell[CountColumnNum];
				String[] names = new String[CountColumnNum];
				names[0] = "产品代码";
				names[1] = "证件类型";
				names[2] = "证件号码";
				names[3] = "申请预约额度";
				names[4] = "客户姓名";
				names[5] = "公众平台来源";
				for (int j = 0; j < CountColumnNum; j++) {
					firstcell[j] = firstrow.createCell(j);
					firstcell[j].setCellValue(new HSSFRichTextString(names[j]));
				}
				HSSFRow row = sheet.createRow((int) rowNumber);// 创建一行
				// HSSFCell cell = null;// 创建列
				try {
					for (int j = 0; j < list_map.size(); j++) {
						Map<String, Object> map_s = list_map.get(j);
						for (String key : map_s.keySet()) {
							for (int k = 0; k < names.length; k++) {
								String ss = names[k];
								if (ss.equals(key)) {
									row.createCell((short) k).setCellValue(
											map.get(key).toString());
									break;
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				rowNumber++;
			}
		}
		String wxCtxPath = request.getSession().getServletContext()
				.getRealPath(Global.DOWNLOAD_BASE_PATH);
		File file = new File(wxCtxPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String fileName_ = System.currentTimeMillis() + "workbook.xls";
		String fileName = wxCtxPath + "/" + fileName_;
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(fileName);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (wb != null) {
					wb.write(os);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String resps = "";
		try {
			resps = "." + Global.DOWNLOAD_BASE_PATH + "/" + fileName_;
		} catch (Exception e) {
			resps = "error";
		}
		return resps;
	}
	
	/**
	 * 查询产品是否已有用户预约
	 * 
	 * @param request
	 * @param response
	 * @param uiModel
	 * @param dataId
	 * @return
	 */
	@RequestMapping(value = "/checkFinancialUser", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String checkFinancialUser(Model uiModel, HttpServletRequest request,
			@RequestParam(value = "dataId", required = false) String dataId) {
		String flag = "0";
		List<WccFinancialUser> list = null;
		if (dataId != null && !"".equals(dataId)) {
			list = WccFinancialUser.findFinancialUsersByFinancialProjectId(Long
					.valueOf(dataId));
			if (list != null && list.size() > 0) {
				flag = "1";
			}
		} else {
			flag = "0";
		}
		uiModel.addAttribute("dataId", dataId);
		return flag;
	}
	
	/**
	 * 页面上是否启用的ajax调用
	 * 
	 * @param uiModel
	 * @param request
	 * @param id
	 * @param isUsing
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/updateActive", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String updateActive(Model uiModel, HttpServletRequest request,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "isUsing", required = false) Boolean isUsing,
			@RequestParam(value = "userId", required = false) String userId) {
		String flag = "0";
		WccFinancialProduct wccFinancialProduct = null;
		WccFinancialUser wccFinancialUser = null;
		// 理财产品是否启用
		if (id != null && !"".equals(id)) {
			wccFinancialProduct = WccFinancialProduct.findWccFinancialProduct(Long.valueOf(id));
			if (wccFinancialProduct != null && isUsing != null) {
				wccFinancialProduct.setIsUsing(isUsing);
				wccFinancialProduct.merge();
				flag = "1";
			}
		} else if (userId != null && !"".equals(userId)) { // 预约审核
			wccFinancialUser = WccFinancialUser.findWccFinancialUser(Long.valueOf(userId));
			if (wccFinancialUser != null && isUsing != null) {
				wccFinancialUser.setIsActive(isUsing);
				wccFinancialUser.merge();
				flag = "1";
			}
		} else {
			flag = "0";
		}
		return flag;
	}
	
	
	/**
	 * 下载模板
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/downLoadTemplate", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadTemplate(Model uiModel,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		// 生成提示信息，
		  Long a = System.currentTimeMillis();
		  String aa="产品导入模板";
		  response.setCharacterEncoding("UTF-8");
		  response.setContentType("application/ms-excel");  
		  response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(("产品导入模板").getBytes("gb2312"),"iso8859-1") + ".xls\"");
		  OutputStream os = null;
		  try {
			     os = response.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		  
		    try{  
		          
		        WritableWorkbook book = Workbook.createWorkbook(os);  
		          
		        WritableSheet sheet = book.createSheet("sheet_1", 0);  
		        jxl.write.WritableFont font1 = new jxl.write.WritableFont(  
		                jxl.write.WritableFont.TIMES, 16,  
		                jxl.write.WritableFont.BOLD);  
		        jxl.write.WritableFont font3 = new jxl.write.WritableFont(  
		                jxl.write.WritableFont.TIMES, 10,  
		                jxl.write.WritableFont.BOLD);  
		        jxl.write.WritableCellFormat CBwcfF1 = new jxl.write.WritableCellFormat(  
		                font1);  
		        jxl.write.WritableCellFormat CBwcfF2 = new jxl.write.WritableCellFormat();  
		        jxl.write.WritableCellFormat CBwcfF3 = new jxl.write.WritableCellFormat(  
		                font3);  
		        jxl.write.WritableCellFormat CBwcfF4 = new jxl.write.WritableCellFormat();  
		        CBwcfF1.setAlignment(jxl.write.Alignment.CENTRE);  
		        CBwcfF2.setAlignment(jxl.write.Alignment.RIGHT);  
		        CBwcfF3.setAlignment(jxl.write.Alignment.CENTRE);  
		        CBwcfF3.setBorder(Border.ALL, jxl.format.BorderLineStyle.THIN,  
		                Colour.BLACK);  
		        CBwcfF4.setBorder(Border.ALL, jxl.format.BorderLineStyle.THIN,  
		                Colour.BLACK);  
		          
		        sheet.setColumnView(0, 10);  
		        sheet.setColumnView(1, 10);  
		        sheet.setColumnView(2, 10);  
		   ///Label labelhead0 = new Label("当前列", "当前行", " 当前单元格的值", "引用的样式");     
		        Label labelhead0 = new Label(0, 0, "公众平台", CBwcfF3);  
		        Label labelhead1 = new Label(1, 0, "理财产品名称", CBwcfF3);  
		        Label labelhead2 = new Label(2, 0, "产品代码", CBwcfF3); 
		        Label labelhead3 = new Label(3, 0, "风险等级", CBwcfF3);  
		        Label labelhead4 = new Label(4, 0, "产品类型", CBwcfF3);  
		        Label labelhead5 = new Label(5, 0, "销售起日", CBwcfF3);  
		        Label labelhead6 = new Label(6, 0, "销售止日", CBwcfF3);  
		        Label labelhead7 = new Label(7, 0, "起息日", CBwcfF3);  
		        Label labelhead8 = new Label(8, 0, "到期日", CBwcfF3);
		        Label labelhead9 = new Label(9, 0, "到账日", CBwcfF3);
		        Label labelhead10 = new Label(10, 0, "投资期限", CBwcfF3);  
		        Label labelhead11 = new Label(11, 0, "预期收益率", CBwcfF3);  
		        Label labelhead12 = new Label(12, 0, "产品起点金额", CBwcfF3);  
		        Label labelhead13 = new Label(13, 0, "产品上限金额", CBwcfF3);  
		        Label labelhead14 = new Label(14, 0, "递增金额", CBwcfF3);
		        Label labelhead15 = new Label(15, 0, "微信预约总额", CBwcfF3);
		        Label labelhead16 = new Label(16, 0, "其他描述", CBwcfF3);
		        sheet.addCell(labelhead0);  
		        sheet.addCell(labelhead1);  
		        sheet.addCell(labelhead2);  
		        sheet.addCell(labelhead3);
		        sheet.addCell(labelhead4);
		        sheet.addCell(labelhead5);
		        sheet.addCell(labelhead6);
		        sheet.addCell(labelhead7);
		        sheet.addCell(labelhead8);
		        sheet.addCell(labelhead9);
		        sheet.addCell(labelhead10);
		        sheet.addCell(labelhead11);
		        sheet.addCell(labelhead12);
		        sheet.addCell(labelhead13);
		        sheet.addCell(labelhead14);
		        sheet.addCell(labelhead15);
		        sheet.addCell(labelhead16);
		        Label labe0 = new Label(0, 1, "交通银行"); //公众平台
 		        Label labe1 = new Label(1, 1, "沃德添利42天"); //理财产品名称
 		        Label labe2 = new Label(2, 1, "121150058"); //产品代码
 		        Label labe3 = new Label(3, 1, "1R");//风险等级
 		        Label labe4 = new Label(4, 1, "非保本浮动收益"); //产品类型
 		        Label labe5 = new Label(5, 1, "2015-06-24");  //销售起日
 		        Label labe6 = new Label(6, 1, "2015-06-28");  //销售止日
 		        Label labe7 = new Label(7, 1, "2015-06-24");  //起息日
 		        Label labe8 = new Label(8, 1, "2015-06-27");
 		        Label labe9 = new Label(9, 1, "2015-06-28");//到期日
 		        Label labe10 = new Label(10, 1, "20");//投资期限   
		        Label labe11 = new Label(11, 1, "5-20万（不含）4.8%"); //预期收益率
		        Label labe12 = new Label(12, 1, "5000");  //产品起点金额
		        Label labe13 = new Label(13, 1, "60000");  //产品上限金额
		        Label labe14 = new Label(14, 1, "1000");  //递增金额
		        Label labe15 = new Label(15, 1, "2000000");//微信预约总额
		        Label labe16 = new Label(16, 1, "XXXXXXXXX");//微信预约总额
		        Label labe17 = new Label(17, 1, "该数据仅供参考，添加时自动删除", CBwcfF3);
 		        sheet.addCell(labe0);  
 		        sheet.addCell(labe1);  
 		        sheet.addCell(labe2);  
 		        sheet.addCell(labe3);  
 		        sheet.addCell(labe4);  
 		        sheet.addCell(labe5);
 		        sheet.addCell(labe6);
 		        sheet.addCell(labe7);
 		        sheet.addCell(labe8);
 		        sheet.addCell(labe9);  
		        sheet.addCell(labe10);  
		        sheet.addCell(labe11);
		        sheet.addCell(labe12);
		        sheet.addCell(labe13);
		        sheet.addCell(labe14);
		        sheet.addCell(labe15);
		        sheet.addCell(labe16);
		        sheet.addCell(labe17);
		        CellView cellView = new CellView();
		        cellView.setAutosize(true); //设置自动大小    
		        sheet.setColumnView(1, cellView);//根据内容自动设置列宽    
		        try{
		        	
		        	book.setProtected(false);
		        	book.write();
		        	os.flush();
		        	
		        }catch(Exception e){
		        	e.printStackTrace();
		        }finally{
		        	 book.close(); 
		        	 os.close();
		        }
		        
		        
		          
		    }catch (Exception e) {  
		        e.printStackTrace();  
		    }  
	}
	
	@RequestMapping(value = "importExcel", produces = "text/html")
    public  String importExcel(HttpServletRequest request,@RequestParam(value = "importFile") MultipartFile file,
			HttpServletResponse response) throws ParseException{
	// MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
	 //MultipartFile file  =  multipartRequest.getFile("importFile");
	 Workbook rwb =null;
	try {
		rwb = Workbook.getWorkbook(file.getInputStream());
	} catch (BiffException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

/*       if(null != rwb){
    		//查询所有的项目
    		List<WccAppartment> app = WccAppartment.getWccAppartmentByXp(null);
    		Map<Long,WccAppartment> proprMap = new LinkedHashMap<Long,WccAppartment>();
    	  for(WccAppartment wcca:app){
    		  proprMap.put(wcca.getId(), wcca);
    	  }  */ 
    	   
    	   
    	   //查询所有的理财产品
       List<WccFinancialProduct>  proList = WccFinancialProduct.findFinancialProdocts();
       Map<String,WccFinancialProduct>cbdMap = new LinkedHashMap<String,WccFinancialProduct>();   
         if( null != proList && proList.size() > 0){
        	 for(WccFinancialProduct c:proList){
        //	String key = c.getFinancialName()+""+c.getProductCode()+""+c.getRiskLevel()+""+c.getProductType(); 
        	String key = c.getProductCode(); 
        	cbdMap.put(key, c);		 
          }
        }
       
       
       
       
    	   //查询所有的公共账号 ,放到map中
   		List<WccPlatformUser> plist = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
    	Map<String,WccPlatformUser> pltMap = new LinkedHashMap<String,WccPlatformUser>(); 
    	if(null != plist && plist.size() > 0){
    		for(WccPlatformUser p:plist){
    			pltMap.put(p.getAccount(), p);
    		}
    	}
    	   Sheet rs=rwb.getSheet(0);//或者rwb.getSheet(0)
           int clos=rs.getColumns();//得到所有的列
           int rows=rs.getRows();//得到所有的行  
    	   List <WccFees> wl = new ArrayList<WccFees>();
    	   WccFinancialProduct w = null;
    	   boolean casse = true;
           for (int i = 2; i < (rows); i++) {
               for (int j = 0; j < clos; j++) {
                   //第一个是列数，第二个是行数
               String platformUsers = rs.getCell(j++, i).getContents();// 关联公众号
               String financialName  = rs.getCell(j++, i).getContents();  //理财产品名称
               String productCode   = rs.getCell(j++, i).getContents();   //产品代码
               String riskLevel     = rs.getCell(j++, i).getContents();  //风险等级
               String productType = rs.getCell(j++, i).getContents();//产品类型
               String saleBeginDate = rs.getCell(j++, i).getContents();//销售起日
               String saleEndDate = rs.getCell(j++, i).getContents();// 销售止日
               String valueDate = rs.getCell(j++, i).getContents(); //起息日
               String endDate = rs.getCell(j++, i).getContents();//到期日
               String arrivalDate = rs.getCell(j++, i).getContents();//到账日
               String investmentHorizon   = rs.getCell(j++, i).getContents();   //投资期限
               String expectedYield     = rs.getCell(j++, i).getContents();  //预期收益率
               String minMoney = rs.getCell(j++, i).getContents();//产品起点金额
               String maxMoney = rs.getCell(j++, i).getContents();//产品上限金额
               String increasingMoney = rs.getCell(j++, i).getContents();// 递增金额
               String totalMoney = rs.getCell(j++, i).getContents(); //微信预约总额
               String themeImageText=rs.getCell(j++, i).getContents();
               
                 w = new WccFinancialProduct();
            //根据项 目名+姓名+手机+门牌号 找业主是否存在
            //   if(null != cbdMap.get(financialName+productCode+riskLevel+productType)){
                 if(null != cbdMap.get(productCode)){
            	   //保存项 目名+姓名+手机+门牌号 
            	   casse = false;
            	   System.out.println("该数据已存在！");
               }else{
            	   
               }
               
              if(null != platformUsers && !"".equals(platformUsers.trim())){
            	   String tempPlt[] = platformUsers.replaceAll("，", ",").split(",");
            	   Set<WccPlatformUser> pltSet = new HashSet<WccPlatformUser>();
            
            	   for(String s:tempPlt){
            		  if(null != pltMap.get(s)){
            			  pltSet.add(pltMap.get(s));	  
            		  }
            	   }
            	  if(pltSet.size() > 0){
            		 // 保存公众账号
            		  w.setPlatformUsers(pltSet);
            	  }else{
            		  casse = false;
            		  System.out.println("不存在的公共账号！");
            	  } 
               }
              if(financialName !=null && !"".equals(financialName)){
            	  w.setFinancialName(financialName);
              }else{
            	  casse = false;
            	  System.out.println("理财产品名称不能为空");
              }
              if(productCode !=null && !"".equals(productCode)){
            	  w.setProductCode(productCode);
              }else{
            	  casse = false;
            	 System.out.println("理财产品代码不能为空");  
              }
              if(riskLevel !=null && !"".equals(riskLevel)){
            	  	w.setRiskLevel(riskLevel);
              }else{
            	  casse = false;
            	  System.out.println("风险等级不能为空");
              }
              if(productType !=null && !"".equals(productType)){
            	  w.setProductType(productType);
              }else{
            	  casse = false;
            	  System.out.println("产品类型不能为空");
              }
              if(saleBeginDate !=null && !"".equals(saleBeginDate)){
            	  Pattern pat = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");//正则
            	  Matcher m  = pat.matcher(saleBeginDate);
            	  if(m.matches()){
            		  saleBeginDate = saleBeginDate.substring(0, 4)+"-"+saleBeginDate.substring(4, saleBeginDate.length())+"-01";
            		  DateUtil.formateDate(saleBeginDate, "yyyy-MM-dd");
            		  w.setSaleBeginDate(DateUtil.formateDate(saleBeginDate, "yyyy-MM-dd"));
            	  }
            	  else{
            		  casse = false;
            		  System.out.println("请输入正确日期");
            	  }
              }else{
            	  casse = false;
            	  System.out.println("销售起日不能为空");
              }
              
              if(saleEndDate !=null && !"".equals(saleEndDate)){
            	  Pattern pat = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");//正则
            	  Matcher m  = pat.matcher(saleEndDate);
            	  if(m.matches()){
            	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            	  Date saleBeginDa = sdf.parse(saleBeginDate);
            	  Date saleEndDa=sdf.parse(valueDate);
            	  if(saleEndDa.getTime()>saleBeginDa.getTime()){
            		  saleEndDate = saleEndDate.substring(0, 4)+"-"+saleEndDate.substring(4, saleEndDate.length())+"-01";
            		  DateUtil.formateDate(saleEndDate, "yyyy-MM-dd");
            		  w.setSaleEndDate(DateUtil.formateDate(saleEndDate, "yyyy-MM-dd"));
            	  }
            	  else{
            		  casse = false;
            		  System.out.println("销售起日大于销售止日");
            	  }
            	  }
            	  else{
            		  casse = false;
            		  System.out.println("日期格式不正确");
            	  }

              }else{
            	  casse = false;
            	  System.out.println("销售止日不能为空");
              }
              
              
              if(valueDate !=null && !"".equals(valueDate)){
            	  Pattern pat = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");//正则
            	  Matcher m  = pat.matcher(valueDate);
            	  if(m.matches()){
            	  valueDate = valueDate.substring(0, 4)+"-"+valueDate.substring(4, valueDate.length())+"-01";
        		  DateUtil.formateDate(valueDate, "yyyy-MM-dd");
        		  w.setValueDate(DateUtil.formateDate(valueDate, "yyyy-MM-dd"));
            	  }else{
            		  casse = false;
            		  System.out.println("日期格式不正确");
            	  }
              }else{
            	  casse = false;
            	  System.out.println("起息日不能为空");
              }
              if(endDate !=null && !"".equals(endDate)){
            	  Pattern pat = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");//正则
            	  Matcher m  = pat.matcher(endDate);
            	  if(m.matches()){
            	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            	  Date endDa = sdf.parse(endDate);
            	  Date valueDa=sdf.parse(valueDate);
            	  if(endDa.getTime()>valueDa.getTime()){
            		  endDate = endDate.substring(0, 4)+"-"+endDate.substring(4, endDate.length())+"-01";
            		  DateUtil.formateDate(endDate, "yyyy-MM-dd");
            		  w.setEndDate(DateUtil.formateDate(endDate, "yyyy-MM-dd"));
            	  }
            	  else{
            		  casse = false;
            		  System.out.println("起息日大于到期日");
            	  }
            	  }else{
            		  casse = false;
            		  System.out.println("日期格式不正确");
            	  }
              }else{
            	  casse = false;
            	  System.out.println("到期日不能为空");
              }
              if(arrivalDate !=null && !"".equals(arrivalDate)){
            	  Pattern pat = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");//正则
            	  Matcher m  = pat.matcher(arrivalDate);
            	  if(m.matches()){
            	  arrivalDate = arrivalDate.substring(0, 4)+"-"+arrivalDate.substring(4, arrivalDate.length())+"-01";
        		  DateUtil.formateDate(arrivalDate, "yyyy-MM-dd");
        		  w.setArrivalDate(DateUtil.formateDate(arrivalDate, "yyyy-MM-dd"));
            	  }else{
            		  casse = false;
            		  System.out.println("日期格式不正确");
            	  }
              }else{
            	  casse = false;
            	  System.out.println("到账日不能为空");
              }
              if(investmentHorizon !=null && !"".equals(investmentHorizon)){
            	  w.setInvestmentHorizon(investmentHorizon);
              }else{
            	  casse = false;
            	  System.out.println("不能为空");
              }
              if(expectedYield !=null && !"".equals(expectedYield)){
            	  w.setExpectedYield(expectedYield);
              }else{
            	  casse = false;
            	  System.out.println("");
              }
              if(minMoney !=null && !"".equals(minMoney)){
            	  Pattern pattern = Pattern.compile("[0-9]*"); 
            	   Matcher isNum = pattern.matcher(minMoney);
            	 if( isNum.matches() ){
            	  w.setMinMoney(Long.parseLong(minMoney));
            	 }else{
            		 casse = false;
            		 System.out.println("不是数字");
            	 }
              }else{
            	  casse = false;
            	  System.out.println("最低金额不能为空");
              }
              if(maxMoney !=null && !"".equals(maxMoney)){
              Pattern pattern = Pattern.compile("[0-9]*"); 
           	  Matcher isNum = pattern.matcher(maxMoney);
           	  if( isNum.matches() ){
           		 w.setMaxMoney(Long.parseLong(maxMoney));
           	  }else{
           		casse = false;
           		 System.out.println("不是数字");
           	  }
              }else{
            	  casse = false;
            	  System.out.println("最多金额不能为空");
              }
              if(increasingMoney !=null && !"".equals(increasingMoney)){
            	  Pattern pattern = Pattern.compile("[0-9]*"); 
               	  Matcher isNum = pattern.matcher(increasingMoney);
               	  if( isNum.matches() ){
               		 w.setIncreasingMoney(Long.parseLong(increasingMoney));
               	  }else{
               		casse = false;
               		 System.out.println("不是数字");
               	  } 
              }else{
            	  casse = false;
            	  System.out.println("不能为空");
              }
              if(totalMoney !=null && !"".equals(totalMoney)){
            	  Pattern pattern = Pattern.compile("[0-9]*"); 
               	  Matcher isNum = pattern.matcher(totalMoney);
               	  if( isNum.matches() ){
               		 w.setTotalMoney(Long.parseLong(totalMoney));
               	  }else{
               		casse = false;
               		 System.out.println("不是数字");
               	  }
              }else{
            	  casse = false;
            	  System.out.println("不能为空");
              }
              	w.setThemeImageText(themeImageText);
//              if(themeImageText !=null && !"".equals(themeImageText)){
//            	  w.setThemeImageText(themeImageText);
//              }else{
//            	  casse = false;
//            	  System.out.println("");
//              }
             
      /*       if(null != monthStr && !"".equals(monthStr)){
            	  
            	   w.setMonthStr(monthStr);
            	   String mStr = w.getMonthStr();
     		       mStr = mStr.substring(0, 4)+"-"+mStr.substring(4, mStr.length())+"-01";
     		       DateUtil.formateDate(mStr, "yyyy-MM-dd");
     		       w.setMonth(DateUtil.formateDate(mStr, "yyyy-MM-dd"));  
            	  
              }
             if(null != state && !"".equals(state)){
           	   
            	 if("已支付".equals(state)){
            		 w.setState(1);
            	 }else{
            		 w.setState(0);
            	 }
           	   
             }else{
            	 casse = false;
            	 System.out.println("状态不能为空！");
             }
             if(null != amount && !"".equals(amount)){
            	 Float am = null;
           	  try{
           		 am = Float.parseFloat(amount);
           	  }catch(Exception e){
           	  }
           	    if(am != null){
           	    	w.setAmount(am);
           	    }
             }  */
             
               if(casse){
            	   w.setIsDelete(false);
            	   w.setIsUsing(true);
            	   w.setInsertTime(new Date());
       		       w.persist();
       			   
                 }
               }
           }
   		return "financialProduct/culture";
    }

}

package com.nineclient.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.nineclient.constant.VocCommentLevel;
import com.nineclient.constant.VocDispatchState;
import com.nineclient.constant.VocReplayState;
import com.nineclient.constant.VocWorkOrderEmergency;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpOperator;
import com.nineclient.model.VocBrand;
import com.nineclient.model.VocComment;
import com.nineclient.model.VocCommentCategory;
import com.nineclient.model.VocEmailGroup;
import com.nineclient.model.VocGoods;
import com.nineclient.model.VocShop;
import com.nineclient.model.VocWorkOrder;
import com.nineclient.model.VocWorkOrderType;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@RequestMapping("/voccomments")
@Controller
@RooWebScaffold(path = "voccomments", formBackingObject = VocComment.class)
public class VocCommentController {
	static Map<String, String> sortedFieldMap = new HashMap<String, String>();
	static {
		sortedFieldMap.put("sortCommentLevelsId", "o.commentLevel");
		sortedFieldMap.put("sortReplyStateId", "o.replyState");
		sortedFieldMap.put("sortGoodsNameId", "goods.name");
		sortedFieldMap.put("sortBrandNameId", "goods.vocBrand.brandName");
		sortedFieldMap.put("sortChannelNameId", "goods.channel.channelName");
		sortedFieldMap.put("sortCommentTimeId", "o.commentTime");
		sortedFieldMap.put("sortReplyOperatorId","o.replyOperator.operatorName");
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid VocComment vocComment,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, vocComment);
			return "voccomments/create";
		}
		uiModel.asMap().clear();
		vocComment.persist();
		return "redirect:/voccomments/"
				+ encodeUrlPathSegment(vocComment.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new VocComment());
		return "voccomments/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("voccomment", VocComment.findVocComment(id));
		uiModel.addAttribute("itemId", id);
		return "voccomments/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			HttpServletRequest request, Model uiModel) {

		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("channels", UmpChannel.findAllUmpChannels());
		List<VocShop> list = null;
		try {
			list = VocShop.findAllVocShopsByCompanyAndChannel(null,CommonUtils.getCurrentCompanyId(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		uiModel.addAttribute("shops",list);
		
		uiModel.addAttribute("brands", VocBrand.findAllVocBrands());
		List<PubOperator> dispatchOperList = PubOperator
				.findAllPubOperatorsByCompanyId(CommonUtils
						.getCurrentPubOperator(request).getCompany().getId());
		removeAdminUser(dispatchOperList);
		uiModel.addAttribute("dispatchOperators", dispatchOperList);
		uiModel.addAttribute("replyStates",
				Arrays.asList(VocReplayState.values()));
		uiModel.addAttribute("commentLevels",
				Arrays.asList(VocCommentLevel.values()));
		uiModel.addAttribute("limit", 10);
		return "voccomments/list";
	}
	
	void removeAdminUser(List<PubOperator> list) {
		Iterator<PubOperator> it = list.iterator();
		while (it.hasNext()) {
			PubOperator oper = it.next();
			if (oper.getPubRole() == null) {
				it.remove();
			}
		}

	}

	void keepeAdminUser(List<PubOperator> list) {
		Iterator<PubOperator> it = list.iterator();
		while (it.hasNext()) {
			PubOperator oper = it.next();
			if (oper.getPubRole() != null) {
				it.remove();
			}
		}

	}
	
	@ResponseBody
	@RequestMapping(value = "loadMultSelect")
	public void loadMultSelect(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer(); // 拼成Json格式字符串{key:value,key1:value1}
			UmpCompany company = CommonUtils.getCurrentPubOperator(request)
					.getCompany();
			PubOperator pubOperator = CommonUtils
					.getCurrentPubOperator(request);

			// 店铺
			Set<VocShop> shopSet = new HashSet<VocShop>();

			// 渠道
			List<UmpChannel> channelList = UmpChannel
					.findChannelsByCompany(company.getCompanyCode());
			boolean channelFlag = true;
			StringBuffer channels = new StringBuffer();
			channels.append("\"channels\":[");
			for (UmpChannel uc : channelList) {
				if (channelFlag) {
					channels.append("{\"id\":" + uc.getId() + ",\"name\":\""
							+ uc.getChannelName() + "\",\"open\":true}");
					channelFlag = false;
				} else {
					channels.append(",{\"id\":" + uc.getId() + ",\"name\":\""
							+ uc.getChannelName() + "\",\"open\":true}");
				}

			}
			channels.append(" ] ");

			// 品牌
			boolean brandsFlag = true;
			StringBuffer brands = new StringBuffer();
			List<VocBrand> brandList = VocBrand.findVocBrandbyCompany(company);
			brands.append("\"brands\":[");
			for (VocBrand uc : brandList) {
				for (VocShop shop : uc.getVocShops()) {
					if (shop.getCompanyId() == company.getId()) {
						shopSet.add(shop);
					}
				}

				if (brandsFlag) {
					brands.append("{\"id\":" + uc.getId() + ",\"name\":\""
							+ uc.getBrandName() + "\",\"open\":true}");
					brandsFlag = false;
				} else {
					brands.append(",{\"id\":" + uc.getId() + ",\"name\":\""
							+ uc.getBrandName() + "\",\"open\":true}");
				}
			}
			brands.append(" ] ");

			// 分配坐席
			boolean dispatchOperatorsFlag = true;
			StringBuffer dispatchOperators = new StringBuffer();
			List<PubOperator> ispatchOperatorsList = PubOperator
					.findAllPubOperatorsByCompanyId(company.getId());
			if (pubOperator.getPubRole() != null) {// 不是管理员，显示自己,否则显示全部
				ispatchOperatorsList.clear();
				ispatchOperatorsList.add(pubOperator);
			}

			dispatchOperators.append("\"dispatchOperators\":[");
			for (PubOperator uc : ispatchOperatorsList) {
				if (dispatchOperatorsFlag) {
					dispatchOperators.append("{\"id\":" + uc.getId()
							+ ",\"name\":\"" + uc.getAccount()
							+ "\",\"open\":true}");
					dispatchOperatorsFlag = false;
				} else {
					dispatchOperators.append(",{\"id\":" + uc.getId()
							+ ",\"name\":\"" + uc.getAccount()
							+ "\",\"open\":true}");
				}
			}
			dispatchOperators.append(" ] ");

			// 回复坐席
			boolean replyOperatorsFlag = true;
			StringBuffer replyOperators = new StringBuffer();
			List<PubOperator> replyOperatorsList = PubOperator.findAllPubOperatorsByCompanyId(company.getId());
			if (pubOperator.getPubRole() != null) {// 不是管理员，显示自己,否则显示全部
				ispatchOperatorsList.clear();
				ispatchOperatorsList.add(pubOperator);
			}

			replyOperators.append("\"replyOperators\":[");
			for (PubOperator uc : replyOperatorsList) {
				if (replyOperatorsFlag) {
					replyOperators.append("{\"id\":" + uc.getId()
							+ ",\"name\":\"" + uc.getAccount()
							+ "\",\"open\":true}");
					replyOperatorsFlag = false;
				} else {
					replyOperators.append(",{\"id\":" + uc.getId()
							+ ",\"name\":\"" + uc.getAccount()
							+ "\",\"open\":true}");
				}
			}
			replyOperators.append(" ] ");

			// 回复状态
			boolean replyStatesFlag = true;
			StringBuffer replyStates = new StringBuffer();
			replyStates.append("\"replyStates\":[");
			for (VocReplayState uc : VocReplayState.values()) {
				if (replyStatesFlag) {
					replyStates.append("{\"id\":" + uc.getId() + ",\"name\":\""
							+ uc.getName() + "\",\"open\":true}");
					replyStatesFlag = false;
				} else {
					replyStates.append(",{\"id\":" + uc.getId()
							+ ",\"name\":\"" + uc.getName()
							+ "\",\"open\":true}");
				}
			}
			replyStates.append(" ] ");

			// 评论分类
			boolean commentLevelsFlag = true;
			StringBuffer commentLevels = new StringBuffer();
			commentLevels.append("\"commentLevels\":[");
			for (VocCommentLevel uc : VocCommentLevel.values()) {
				if (commentLevelsFlag) {
					commentLevels.append("{\"id\":" + uc.getId()
							+ ",\"name\":\"" + uc.getName()
							+ "\",\"open\":true}");
					commentLevelsFlag = false;
				} else {
					commentLevels.append(",{\"id\":" + uc.getId()
							+ ",\"name\":\"" + uc.getName()
							+ "\",\"open\":true}");
				}
			}
			commentLevels.append(" ] ");

			// 评论分类
			boolean shopsFlag = true;
			StringBuffer shops = new StringBuffer();
			List<VocShop> shopList = CommonUtils.set2List(shopSet);
			shops.append("\"shops\":[");
			for (VocShop uc : shopList) {
				if (shopsFlag) {
					shops.append("{\"id\":" + uc.getId() + ",\"name\":\""
							+ uc.getName() + "\",\"open\":true}");
					shopsFlag = false;
				} else {
					shops.append(",{\"id\":" + uc.getId() + ",\"name\":\""
							+ uc.getName() + "\",\"open\":true}");
				}
			}
			shops.append(" ] ");

			sb.append(" { ");
			sb.append(channels);
			sb.append(",");
			sb.append(brands);
			sb.append(",");
			sb.append(dispatchOperators);
			sb.append(",");
			sb.append(replyOperators);
			sb.append(",");
			sb.append(replyStates);
			sb.append(",");
			sb.append(commentLevels);
			sb.append(",");
			sb.append(shops);
			sb.append(" } ");

			out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

	}

	@RequestMapping(value = "/queryComments", produces = "text/html")
	public String queryComments(
			@Valid VocComment vocComment,
			Model uiModel,
			HttpServletRequest request,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "goodsName", required = false) String goodsName,
			@RequestParam(value = "channels", required = false) String channels,
			@RequestParam(value = "shops", required = false) String shops,
			@RequestParam(value = "brands", required = false) String brands,
			@RequestParam(value = "dispatchOperators", required = false) String dispatchOperators,
			@RequestParam(value = "replyOperators", required = false) String replyOperators,
			@RequestParam(value = "replyStates", required = false) String replyStates,
			@RequestParam(value = "commentLevels", required = false) String commentLevels,
			@RequestParam(value = "commentStartTime", required = false) String commentStartTimeStr,
			@RequestParam(value = "commentEndTime", required = false) String commentEndTimeStr,
			@RequestParam(value = "createStartTime", required = false) String createStartTimeStr,
			@RequestParam(value = "createEndTime", required = false) String createEndTimeStr,
			@RequestParam(value = "replyStartTime", required = false) String replyStartTimeStr,
			@RequestParam(value = "replyEndTime", required = false) String replyEndTimeStr,
			@RequestParam(value = "commentCategory", required = false) String commentCategory,
			@RequestParam(value = "tagName", required = false) String tagName,
			@RequestParam(value = "sortedField", required = false) String sortedField)
			throws ServletException, IOException {
		String formatPattern = "yyyy-MM-dd HH:mm:ss";
		Date commentStartTime = DateUtil.formateDate(commentStartTimeStr,
				formatPattern);
		Date commentEndTime = DateUtil.formateDate(commentEndTimeStr,
				formatPattern);
		Date createStartTime = DateUtil.formateDate(createStartTimeStr,
				formatPattern);
		Date createEndTime = DateUtil.formateDate(createEndTimeStr,
				formatPattern);
		Date replyStartTime = DateUtil.formateDate(replyStartTimeStr,
				formatPattern);
		Date replyEndTime = DateUtil
				.formateDate(replyEndTimeStr, formatPattern);

		PubOperator operator = CommonUtils.getCurrentPubOperator(request);
		if (null == operator.getPubRole()) { // admin 用户,查看全部数据

		} else {// 普通用户,查看自己的数据
			dispatchOperators = operator.getId() + "";
		}

		Map parmMap = new HashMap();
		parmMap.put("commentStartTime", commentStartTime);
		parmMap.put("commentEndTime", commentEndTime);
		parmMap.put("createStartTime", createStartTime);
		parmMap.put("createEndTime", createEndTime);
		parmMap.put("replyStartTime", replyStartTime);
		parmMap.put("replyEndTime", replyEndTime);

		parmMap.put("goodsName", goodsName);
		parmMap.put("channels", channels);
		parmMap.put("shops", shops);

		parmMap.put("brands", brands);
		parmMap.put("dispatchOperators", dispatchOperators);
		parmMap.put("replyOperators", replyOperators);
		parmMap.put("replyStates", replyStates);
		parmMap.put("commentLevels", commentLevels);

		parmMap.put("commentCategory", commentCategory);
		parmMap.put("tagName", tagName);
		parmMap.put("companyId", CommonUtils.getCurrentCompanyId(request));

		QueryModel<VocComment> qm = new QueryModel<VocComment>(vocComment,
				start, limit);
		qm.getInputMap().putAll(parmMap);

		// 排序
		if (null != sortedField && !"".equals(sortedField)) {
			qm.getOrderMap().putAll(createOrderByField(sortedField));
		}

		PageModel<VocComment> pm = VocComment.getQueryComments(qm);
		pm.setPageSize(limit);
		pm.setStartIndex(start);

		uiModel.addAttribute("fieldSortInfo", createFieldSortInfo());
		uiModel.addAttribute("voccomments", pm.getDataList());
		uiModel.addAttribute("maxPages", pm.getTotalPage());
		uiModel.addAttribute("page", pm.getPageNo());
		uiModel.addAttribute("limit", pm.getPageSize());

		return "voccomments/result";
	}

	private String createFieldSortInfo() {

		return "[{'id':'sortCommentLevelsId','value':'text-left sorting_desc'},{'id':'sortReplyStateId','value':'text-left sorting_asc'},{'id':'sortGoodsNameId','value':'text-left sorting_asc'},{'id':'sortBrandNameId','value':'text-left sorting_asc'},{'id':'sortChannelNameId','value':'text-left sorting_asc'},{'id':'sortCommentTimeId','value':'text-left sorting_asc'},{'id':'sortReplyOperatorId','value':'text-left sorting_asc'}]";
	}

	private Map<String, String> createOrderByField(String sortedField) {
		Map<String, String> map = new HashMap<String, String>();
		String regex = "\"id\":\"(.*?)\",\"value\":\"(.*?)\"";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sortedField);
		String id = "";
		String value = "";
		if (m.find()) {
			id = m.group(1);
			value = m.group(2);
		}

		if (null != id && !"".equals(id)) {
			map.put(sortedFieldMap.get(id), value.split("sorting_")[1]);
		}

		return map;
	}

	@RequestMapping(value = "/replyRecordlist", method = RequestMethod.GET, produces = "text/html")
	public String replyRecordlist(HttpServletRequest request, Model uiModel) {

		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("channels", UmpChannel.findAllUmpChannels());
		uiModel.addAttribute(
				"shops",
				VocShop.findAllVocShopsByCompanyAndChannel(null,
						CommonUtils.getCurrentCompanyId(request)));
		uiModel.addAttribute("brands", VocBrand.findAllVocBrands());
		uiModel.addAttribute("dispatchOperators",
				UmpOperator.findAllUmpOperators());
		uiModel.addAttribute("replyStates",
				Arrays.asList(VocReplayState.values()));
		uiModel.addAttribute("commentLevels",
				Arrays.asList(VocCommentLevel.values()));
		uiModel.addAttribute("limit", 10);
		return "voccomments/replyRecordlist";
	}

	@RequestMapping(value = "/queryReplyRecord", produces = "text/html")
	public String queryReplyRecord(
			@Valid VocComment vocComment,
			Model uiModel,
			HttpServletRequest request,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "goodsName", required = false) String goodsName,
			@RequestParam(value = "channels", required = false) String channels,
			@RequestParam(value = "shops", required = false) String shops,
			@RequestParam(value = "brands", required = false) String brands,
			@RequestParam(value = "dispatchOperators", required = false) String dispatchOperators,
			@RequestParam(value = "replyOperators", required = false) String replyOperators,
			@RequestParam(value = "replyStates", required = false) String replyStates,
			@RequestParam(value = "commentLevels", required = false) String commentLevels,
			@RequestParam(value = "commentStartTime", required = false) String commentStartTimeStr,
			@RequestParam(value = "commentEndTime", required = false) String commentEndTimeStr,
			@RequestParam(value = "createStartTime", required = false) String createStartTimeStr,
			@RequestParam(value = "createEndTime", required = false) String createEndTimeStr,
			@RequestParam(value = "commentCategory", required = false) String commentCategory,
			@RequestParam(value = "tagName", required = false) String tagName,
			@RequestParam(value = "sortedField", required = false) String sortedField)
			throws ServletException, IOException {
		String formatPattern = "yyyy-MM-dd HH:mm:ss";
		Date commentStartTime = DateUtil.formateDate(commentStartTimeStr,
				formatPattern);
		Date commentEndTime = DateUtil.formateDate(commentEndTimeStr,
				formatPattern);
		Date createStartTime = DateUtil.formateDate(createStartTimeStr,
				formatPattern);
		Date createEndTime = DateUtil.formateDate(createEndTimeStr,
				formatPattern);

		Map parmMap = new HashMap();
		parmMap.put("companyId", CommonUtils.getCurrentCompanyId(request));
		parmMap.put("commentStartTime", commentStartTime);
		parmMap.put("commentEndTime", commentEndTime);
		parmMap.put("createStartTime", createStartTime);
		parmMap.put("createEndTime", createEndTime);

		parmMap.put("goodsName", goodsName);
		parmMap.put("channels", channels);
		parmMap.put("shops", shops);

		parmMap.put("brands", brands);
		parmMap.put("dispatchOperators", dispatchOperators);
		parmMap.put("replyOperators", replyOperators);
		parmMap.put("replyStates", VocReplayState.已回复.getId());
		parmMap.put("commentLevels", commentLevels);

		parmMap.put("commentCategory", commentCategory);
		parmMap.put("tagName", tagName);

		QueryModel<VocComment> qm = new QueryModel<VocComment>(vocComment,
				start, limit);
		qm.getInputMap().putAll(parmMap);
		// 排序
		if (null != sortedField && !"".equals(sortedField)) {
			qm.getOrderMap().putAll(createOrderByField(sortedField));
		}

		PageModel<VocComment> pm = VocComment.getQueryComments(qm);
		pm.setPageSize(limit);
		pm.setStartIndex(start);

		uiModel.addAttribute("voccomments", pm.getDataList());
		uiModel.addAttribute("maxPages", pm.getTotalPage());
		uiModel.addAttribute("page", pm.getPageNo());
		uiModel.addAttribute("limit", pm.getPageSize());

		return "voccomments/replyRecordResult";
	}

	@RequestMapping(value = "/exportExcle", method = RequestMethod.GET, produces = "text/html")
	public void exportExcle(
			@Valid VocComment vocComment,
			HttpServletResponse response,
			HttpServletRequest request,
			@RequestParam(value = "goodsName", required = false) String goodsName,
			@RequestParam(value = "channels", required = false) String channels,
			@RequestParam(value = "shops", required = false) String shops,
			@RequestParam(value = "brands", required = false) String brands,
			@RequestParam(value = "dispatchOperators", required = false) String dispatchOperators,
			@RequestParam(value = "replyStates", required = false) String replyStates,
			@RequestParam(value = "commentLevels", required = false) String commentLevels,
			@RequestParam(value = "commentStartTime", required = false) String commentStartTimeStr,
			@RequestParam(value = "commentEndTime", required = false) String commentEndTimeStr,
			@RequestParam(value = "createStartTime", required = false) String createStartTimeStr,
			@RequestParam(value = "createEndTime", required = false) String createEndTimeStr,
			@RequestParam(value = "isWebSearch", required = true) boolean isWebSearch) {

		String formatPattern = "yyyy-MM-dd HH:mm:ss";
		Date commentStartTime = DateUtil.formateDate(commentStartTimeStr,
				formatPattern);
		Date commentEndTime = DateUtil.formateDate(commentEndTimeStr,
				formatPattern);
		Date createStartTime = DateUtil.formateDate(createStartTimeStr,
				formatPattern);
		Date createEndTime = DateUtil.formateDate(createEndTimeStr,
				formatPattern);
		int limit = 5000;
		int start = 0;

		Map parmMap = new HashMap();
		parmMap.put("commentStartTime", commentStartTime);
		parmMap.put("commentEndTime", commentEndTime);
		parmMap.put("createStartTime", createStartTime);
		parmMap.put("createEndTime", createEndTime);

		parmMap.put("goodsName", goodsName);
		parmMap.put("channels", channels);
		parmMap.put("shops", shops);

		parmMap.put("brands", brands);
		parmMap.put("dispatchOperators", dispatchOperators);
		parmMap.put("replyStates", replyStates);
		parmMap.put("commentLevels", commentLevels);
		parmMap.put("companyId", CommonUtils.getCurrentCompanyId(request));

		QueryModel<VocComment> qm = new QueryModel<VocComment>(vocComment);
		qm.getInputMap().putAll(parmMap);

		PageModel<VocComment> pm = VocComment.getQueryComments(qm);

		HttpSession session = request.getSession();
		session.setAttribute("state", null);

		qm = new QueryModel<VocComment>(vocComment, start, limit);

		pm.setPageSize(limit);
		pm.setStartIndex(start);
		List<VocComment> list = pm.getDataList();
		// 生成提示信息，
		response.setContentType("application/vnd.ms-excel");
		OutputStream fOut = null;
		try {
			// 进行转码，使其支持中文文件名
			response.setHeader("content-disposition", "attachment;filename="+ new String("导出记录".getBytes("gb2312"), "iso8859-1")+ ".xls");
			
			// 产生工作簿对象
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 产生工作表对象
			HSSFSheet sheet = workbook.createSheet();

			// 设置单元格样子
			HSSFCellStyle cellstyle = workbook.createCellStyle();
			cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			
			
			 String[] arr = { "平台名称", "店铺名称", "品牌名称", "分配坐席", "商品名称", "标签","评论类型", "评论内容", "回复状态", "会员名" }; 
			 // 行
			int rowNumber = 0;
			// 列
			int colomnNumber = 0;
			HSSFRow row = sheet.createRow((int) rowNumber);// 创建一行
			rowNumber++;
			for (String value : arr) {
				HSSFCell cell = row.createCell((int) colomnNumber);// 创建一列
				cell.setCellValue(value);
				colomnNumber++;
			}
			colomnNumber = 0;
			HSSFCell cell = null;
			HSSFRow rowL = null;
			VocComment model = null;
			for (int index = 0; index < list.size(); index++) {
				model = list.get(index);
				rowL = sheet.createRow(rowNumber);// 创建一行

				cell = rowL.createCell(colomnNumber);// 创建一列
				cell.setCellValue(model.getGoods().getChannel()
						.getChannelName());
				colomnNumber++;

				cell = rowL.createCell(colomnNumber);// 创建一列
				cell.setCellValue(model.getGoods().getShop().getName());
				colomnNumber++;

				cell = rowL.createCell(colomnNumber);// 创建一列
				cell.setCellValue(model.getGoods().getVocBrand().getBrandName());
				colomnNumber++;

				cell = rowL.createCell(colomnNumber);// 创建一列
				if (null != model.getDispatchOperator()) {
					cell.setCellValue(model.getDispatchOperator()
							.getOperatorName());
				} else {
					cell.setCellValue("");
				}
				colomnNumber++;

				cell = rowL.createCell(colomnNumber);// 创建一列
				cell.setCellValue(model.getGoods().getName());
				colomnNumber++;

				cell = rowL.createCell(colomnNumber);// 创建一列
				cell.setCellValue("");
				colomnNumber++;

				cell = rowL.createCell(colomnNumber);// 创建一列
				cell.setCellValue(model.getCommentLevel().getName());
				colomnNumber++;

				cell = rowL.createCell(colomnNumber);// 创建一列
				cell.setCellValue(model.getCommentContent());
				colomnNumber++;

				cell = rowL.createCell(colomnNumber);// 创建一列
				cell.setCellValue(model.getReplyState().getName());
				colomnNumber++;

				cell = rowL.createCell(colomnNumber);// 创建一列
				cell.setCellValue(model.getUserName());
				colomnNumber++;

				rowNumber++;
				colomnNumber = 0;
			}
			HSSFCell cell1 = null;
			HSSFRow rowL1 = null;
			List<List<String>> valueList = new ArrayList<List<String>>();
			List<String> listStr = null;
			String[] arr1 = {};
			if(isWebSearch){
			  arr1 = new String[]{"平台名称", "店铺名称", "品牌名称", "分配坐席", "商品名称", "标签","评论类型", "评论内容","评论时间", "回复状态", "会员名"};
			  for(VocComment model1:list){
					 listStr = new ArrayList<String>();
					 listStr.add(model1.getGoods().getChannel().getChannelName());
					 listStr.add(model1.getGoods().getShop().getName());
					 listStr.add(model1.getGoods().getVocBrand().getBrandName());
					 if (null != model1.getDispatchOperator()) {
						 listStr.add(model1.getDispatchOperator().getOperatorName());
						} else {
						listStr.add("");
					 }
					 listStr.add(model1.getGoods().getName());
					 listStr.add("");
					 listStr.add(model1.getCommentLevel().getName());
					 listStr.add(model1.getCommentContent());
					 listStr.add(DateUtil.formateDateToString(model1.getCommentTime(), "yyyy-MM-dd HH:mm:ss"));
					 listStr.add(model1.getReplyState().getName());
					 listStr.add(model1.getUserName());
					 valueList.add(listStr);
					}
			}else{
				arr1 = new String[]{"平台名称", "店铺名称", "品牌名称", "分配坐席", "商品名称", "标签","评论类型", "评论内容", "回复状态","评论时间","导入时间","回复时间","回复内容","回复坐席","会员名"};
				 for(VocComment model1:list){
					 listStr = new ArrayList<String>();
					 listStr.add(model1.getGoods().getChannel().getChannelName());
					 listStr.add(model1.getGoods().getShop().getName());
					 listStr.add(model1.getGoods().getVocBrand().getBrandName());
					 if (null != model1.getDispatchOperator()) {
						 listStr.add(model1.getDispatchOperator().getOperatorName());
						} else {
						listStr.add("");
					 }
					 listStr.add(model1.getGoods().getName());
					 listStr.add("");
					 listStr.add(model1.getCommentLevel().getName());
					 listStr.add(model1.getCommentContent());
					 listStr.add(model1.getReplyState().getName());
					 listStr.add(DateUtil.formateDateToString(model1.getCommentTime(), "yyyy-MM-dd HH:mm:ss"));
					 listStr.add(DateUtil.formateDateToString(model1.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
					 listStr.add(DateUtil.formateDateToString(model1.getReplyTime(), "yyyy-MM-dd HH:mm:ss"));
					 listStr.add(model1.getReplyContent());
					 listStr.add(model1.getReplyOperator().getAccount());
					 listStr.add(model1.getUserName());
					 valueList.add(listStr);
				  }
			}
		   
			createExcl(arr1, valueList, sheet,cell1,rowL1);
			fOut = response.getOutputStream();
			workbook.write(fOut);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fOut) {
					fOut.flush();
					fOut.close();
				}

			} catch (IOException e) {
			}
			session.setAttribute("state", "open");
		}

	}

	public void createExcl(String[] arr, List<List<String>> list,HSSFSheet sheet,HSSFCell cell,HSSFRow rowL) {
		int rowNumber = 0;
		// 列
		int colomnNumber = 0;
		HSSFRow row = sheet.createRow((int) rowNumber);// 创建一行
		rowNumber++;
		for (String value : arr) {
			 cell = row.createCell((int) colomnNumber);// 创建一列
			cell.setCellValue(value);
			colomnNumber++;
		}			
		
		colomnNumber = 0;
		List<String> valueList = null;
		for (int index = 0; index < list.size(); index++) {
			rowL = sheet.createRow(rowNumber);// 创建一行
			valueList = list.get(index);
			for(String value:valueList){
				cell = rowL.createCell(colomnNumber);// 创建一列
				cell.setCellValue(value);
				colomnNumber++;
			}
			rowNumber++;
			colomnNumber = 0;
		}

	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid VocComment vocComment,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, vocComment);
			return "voccomments/update";
		}
		uiModel.asMap().clear();
		vocComment.merge();
		return "redirect:/voccomments/"
				+ encodeUrlPathSegment(vocComment.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, VocComment.findVocComment(id));
		return "voccomments/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		VocComment vocComment = VocComment.findVocComment(id);
		vocComment.remove();
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/voccomments";
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"vocComment_commenttime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute(
				"vocComment_buytime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute(
				"vocComment_createtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute(
				"vocComment_replytime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute(
				"vocComment_dispatchtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute("vocComment", new VocComment());
	}

	void populateEditForm(Model uiModel, VocComment vocComment) {
		uiModel.addAttribute("vocComment", vocComment);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("vocdispatchstates",
				Arrays.asList(VocDispatchState.values()));
		uiModel.addAttribute("vocreplaystates",
				Arrays.asList(VocReplayState.values()));
		uiModel.addAttribute("voccommentLevel",
				Arrays.asList(VocCommentLevel.values()));
		uiModel.addAttribute("goodsList", VocGoods.findAllVocGoodses());
		uiModel.addAttribute("commentCategoryList",
				VocCommentCategory.findAllVocCommentCategorys());

	}

	String encodeUrlPathSegment(String pathSegment,
			HttpServletRequest httpServletRequest) {
		String enc = httpServletRequest.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}

	@ResponseBody
	@RequestMapping(value = "getShopsByProductId")
	public void getShopsByProductId(
			@RequestParam(value = "companyId", required = false) Long companyId,
			@RequestParam(value = "channelIds", required = true) String channelIds,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer(); // 拼成Json格式字符串{key:value,key1:value1}
			List<UmpChannel> channels = UmpChannel
					.findAllUmpChannelsByIds(channelIds);
			sb.append(" [ ");
			if (null != channels && channels.size() > 0) {
				boolean flag = true;
				for (UmpChannel channel : channels) {
					if (flag) {
						sb.append("{id:" + channel.getId() + ",name:\'"
								+ channel.getChannelName() + "\'}");
						flag = false;
					} else {
						sb.append(",{id:" + channel.getId() + ",name:\'"
								+ channel.getChannelName() + "\'}");
					}
				}
			}
			sb.append(" ] ");

			out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

	}

	@ResponseBody
	@RequestMapping(value = "checkUnique")
	public void checkUnique(
			@RequestParam(value = "companyId", required = false) Long companyId,
			@RequestParam(value = "objectName", required = true) String objectName,
			@RequestParam(value = "fieldName", required = true) String fieldName,
			@RequestParam(value = "fieldValue", required = true) Object fieldValue,
			HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer();// 拼成Json格式字符串{key:value,key1:value1}
			Long count = VocComment.queryUniqueCount(companyId, objectName,
					fieldName, fieldValue);
			if (null != count) {
				if (count > 0) {
					sb.append("{success:true}");
				} else {
					sb.append("{success:false}");
				}
			} else {
				sb.append("{success:false}");
			}
			out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

	}

	@RequestMapping(value = "/getRelyPage", produces = "text/html")
	public String getRelyPage(
			HttpServletRequest request,
			Model uiModel,
			@RequestParam(value = "commentIds", required = true) String commentIds) {
		List<VocComment> list = new ArrayList<VocComment>();
		for (String commentIdStr : commentIds.split(",")) {
			Long commentId = Long.parseLong(commentIdStr);
			list.add(VocComment.findVocComment(commentId));
		}
		uiModel.addAttribute("list", list);
		return "voccomments/reply";
	}

	@ResponseBody
	@RequestMapping(value = "sendReply")
	public void sendReply(
			@RequestParam(value = "commentIds", required = true) String commentIds,
			@RequestParam(value = "replyContent", required = true) String replyContent,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		String msg = "{success:true}";
		PubOperator opertor = PubOperator.findPubOperator(CommonUtils
				.getCurrentPubOperator(request).getId());

		try {
			out = response.getWriter();
			VocComment model = null;
			for (String commentIdStr : commentIds.split(",")) {
				Long commentId = Long.parseLong(commentIdStr);
				model = VocComment.findVocComment(commentId);
				model.setReplyState(VocReplayState.已回复);
				model.setReplyTime(new Date());
				model.setReplyContent(replyContent);
				model.setReplyOperator(opertor);
				model.merge();
			}

		} catch (IOException ex1) {
			ex1.printStackTrace();
			msg = "{success:false}";
		} finally {
			out.println(msg);
			out.close();
		}

	}

	@ResponseBody
	@RequestMapping(value = "changeReplyState")
	public void changeReplyState(
			@RequestParam(value = "commentIds", required = true) String commentIds,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		String msg = "{success:true}";

		try {
			out = response.getWriter();
			VocComment model = null;
			for (String commentIdStr : commentIds.split(",")) {
				Long commentId = Long.parseLong(commentIdStr);
				model = VocComment.findVocComment(commentId);
				if (model.getReplyState() == VocReplayState.待回复) {
					model.setReplyState(VocReplayState.不回复);
				} else {
					model.setReplyState(VocReplayState.待回复);
				}
				model.merge();
			}

		} catch (IOException ex1) {
			ex1.printStackTrace();
			msg = "{success:false}";
		} finally {
			out.println(msg);
			out.close();
		}

	}

	@ResponseBody
	@RequestMapping(value = "getTransferOperator")
	public void getTransferOperator(
			@RequestParam(value = "channelId", required = true) Long channelId,
			@RequestParam(value = "brandId", required = true) Long brandId,
			HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer();// 拼成Json格式字符串{key:value,key1:value1}

			List<PubOperator> list = PubOperator
					.findAllPubOperatorsByCompanyId(CommonUtils
							.getCurrentPubOperator(request).getCompany()
							.getId());
			sb.append("[");
			boolean flag = true;
			if (null != list && list.size() > 0) {
				for (PubOperator mOper : list) {
					if (flag) {
						sb.append("{\"id\":" + mOper.getId() + ",\"name\":\""
								+ mOper.getOperatorName() + "\"}");
						flag = false;
					} else {
						sb.append(",{\"id\":" + mOper.getId() + ",\"name\":\""
								+ mOper.getOperatorName() + "\"}");
					}
				}
			}
			sb.append("]");
			out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

	}

	@ResponseBody
	@RequestMapping(value = "doTransfer")
	public void doTransfer(
			@RequestParam(value = "operatorId", required = true) Long operatorId,
			@RequestParam(value = "commentIds", required = true) String commentIds,
			HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		String msg = "{success:true}";
		try {
			out = response.getWriter();
			VocComment model = new VocComment();
			QueryModel<VocComment> qm = new QueryModel<VocComment>(model);
			qm.putParams("commentIds", commentIds);
			qm.putParams("companyId", CommonUtils.getCurrentCompanyId(request));
			PageModel<VocComment> pm = VocComment.getQueryComments(qm);
			PubOperator operator = PubOperator.findPubOperator(operatorId);
			for (VocComment vm : pm.getDataList()) {
				vm.setDispatchOperator(operator);
				vm.merge();

			}

		} catch (IOException ex1) {
			msg = "{success:false}";
			ex1.printStackTrace();
		} finally {
			out.println(msg);
			out.close();
		}

	}

	@ResponseBody
	@RequestMapping(value = "getCommentById")
	public void getCommentById(
			@RequestParam(value = "commentId", required = true) Long commentId,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		StringBuffer sb = new StringBuffer();
		try {
			String jsonStr = createWorkOrderInfo();

			out = response.getWriter();
			VocComment model = VocComment.findVocComment(commentId);
			sb.append(" { ");
			sb.append("\"comment\":{\"id\":" + model.getId() + "");
			sb.append(",\"channelName\":\""
					+ model.getGoods().getChannel().getChannelName() + "\"");
			sb.append(",\"shopName\":\"" + model.getGoods().getShop().getName()
					+ "\"");
			sb.append(",\"brandName\":\""
					+ model.getGoods().getVocBrand().getBrandName() + "\"");
			sb.append(",\"operatorName\":\""
					+ (model.getDispatchOperator() == null ? "" : model
							.getDispatchOperator().getOperatorName()) + "\"");
			sb.append(",\"goodsName\":\"" + model.getGoods().getName() + "\"");
			sb.append(",\"goodsUrl\":\"" + model.getGoods().getUrl() + "\"");
			sb.append(",\"commentLevel\":\""
					+ model.getCommentLevel().getName() + "\"");
			sb.append(",\"commentContent\":\"" + model.getCommentContent()
					+ "\"");
			sb.append("}");
			sb.append("," + jsonStr);
			sb.append(" } ");
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.println(sb.toString());
			out.close();
		}

	}

	@ResponseBody
	@RequestMapping(value = "saveWorkOrder")
	public void saveWorkOrder(
			@RequestParam(value = "commentId", required = true) Long commentId,
			@RequestParam(value = "updateReason", required = true) String updateReason,
			@RequestParam(value = "emailGroupId", required = true) Long emailGroupId,
			@RequestParam(value = "workOrderEmergency", required = true) Long workOrderEmergency,
			@RequestParam(value = "workOrderTypeId", required = true) Long workOrderTypeId,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		String msg = "{success:true}";
		try {
			out = response.getWriter();
			VocWorkOrder model = new VocWorkOrder();
			model.setCompanyCode(CommonUtils.getCurrentPubOperator(request)
					.getCompany().getCompanyCode());
			model.setCreateTime(new Date());
			model.setUpdateReason(updateReason);
			model.setVocEmailGroup(VocEmailGroup
					.findVocEmailGroup(emailGroupId));
			model.setVocWorkOrderEmergency(VocWorkOrderEmergency
					.getEnum(workOrderEmergency.intValue()));
			model.setVocWorkOrderType(VocWorkOrderType
					.findVocWorkOrderType(workOrderTypeId));
			model.persist();
		} catch (IOException ex1) {
			msg = "{success:false}";
			ex1.printStackTrace();
		} finally {
			out.println(msg);
			out.close();
		}

	}

	private String createWorkOrderInfo() {

		StringBuffer workOrderEmergency = new StringBuffer(); // 优先级
		workOrderEmergency.append("workOrderEmergency:[");
		boolean emergencyFlag = true;
		for (VocWorkOrderEmergency voe : VocWorkOrderEmergency.values()) {
			if (emergencyFlag) {
				workOrderEmergency.append("{\"id\":" + voe.getId()
						+ ",\"name\":\"" + voe.getName() + "\"}");
				emergencyFlag = false;
			} else {
				workOrderEmergency.append(",{\"id\":" + voe.getId()
						+ ",\"name\":\"" + voe.getName() + "\"}");
			}
		}
		workOrderEmergency.append("]");

		StringBuffer workType = new StringBuffer(); // 邮件组
		workType.append("workType:[");
		boolean workTypeFlag = true;
		for (VocWorkOrderType vwt : VocWorkOrderType.findAllVocWorkOrderTypes()) {
			if (workTypeFlag) {
				workType.append("{\"id\":" + vwt.getId() + ",\"name\":\""
						+ vwt.getName() + "\"}");
				workTypeFlag = false;
			} else {
				workType.append(",{\"id\":" + vwt.getId() + ",\"name\":\""
						+ vwt.getName() + "\"}");
			}
		}
		workType.append("]");

		StringBuffer emailGroup = new StringBuffer(); // 邮件组
		emailGroup.append("emallGroup:[");
		boolean emailGroupFlag = true;
		for (VocEmailGroup veg : VocEmailGroup.findAllVocEmailGroups()) {
			if (emailGroupFlag) {
				emailGroup.append("{\"id\":" + veg.getId() + ",\"name\":\""
						+ veg.getName() + "\"}");
				emailGroupFlag = false;
			} else {
				emailGroup.append(",{\"id\":" + veg.getId() + ",\"name\":\""
						+ veg.getName() + "\"}");
			}
		}
		emailGroup.append("]");

		return workOrderEmergency + "," + workType + "," + emailGroup;

	}

}
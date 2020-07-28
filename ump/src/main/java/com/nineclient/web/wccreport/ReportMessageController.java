package com.nineclient.web.wccreport;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nineclient.model.PubOperator;
import com.nineclient.model.PubOrganization;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.wccreport.ReportAutokbs;
import com.nineclient.model.wccreport.ReportFriendsFrom;
import com.nineclient.model.wccreport.ReportMessage;
import com.nineclient.model.wccreport.ReportMessageDistribute;
import com.nineclient.model.wccreport.WccMassPicText;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.ExcelUtil;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;

@RequestMapping("/reportmessages")
@Controller
@RooWebScaffold(path = "reportmessages", formBackingObject = ReportAutokbs.class)
public class ReportMessageController {

	@RequestMapping(value = "reportPage", produces = "text/html")
	public String reportPage(HttpServletRequest request, Model uiModel) {
		// 初始化数据
//		List<WccPlatformUser> platformUsers = WccPlatformUser
//				.findAllWccPlatformUsers(CommonUtils
//						.getCurrentPubOperator(request));
//		if (platformUsers.size() > 0) {
//			uiModel.addAttribute("platformUsers", platformUsers);
//		}
		
		PubOperator currentoper=CommonUtils.getCurrentPubOperator(request);
        List<WccPlatformUser> platformUsers=WccPlatformUser.getplatformuserListByOrg(currentoper);
		uiModel.addAttribute("platformUsers", platformUsers);
		return "reportmessages/report";
	}

	@RequestMapping(value = "queryAuto", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAuto(HttpServletRequest request,
			@RequestParam(value = "platform") WccPlatformUser platform,
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "dateType") Integer dateType,
			@RequestParam(value = "endTime") String endTime) {
		List listData = null;
		if (dateType == 0) {
			listData = ReportAutokbs.findReportAutokbsByDateTop(platform,
					startTime, endTime);
		} else {
			String lastdate = DateUtil.getLastMonthDayOfCurrentDay(endTime
					+ "-01", DateUtil.YEAR_MONTH_DAY_FORMATER);
			String firstdate = DateUtil.getFirstMonthDayOfCurrentDay(startTime
					+ "-01", DateUtil.YEAR_MONTH_DAY_FORMATER);
			listData = ReportAutokbs.findReportAutokbsByMonthTop(platform,
					firstdate, lastdate);
		}
		List<ReportAutokbs> list = new ArrayList<ReportAutokbs>();
		if (listData.size() > 21) {
			for (int i = 0; i < 20; i++) {
				Object[] obj = (Object[]) listData.get(i);
				ReportAutokbs mass = new ReportAutokbs();
				mass.setAppearNum(Integer.parseInt(obj[0].toString()));
				mass.setKeyWord(obj[1].toString());
				list.add(mass);
			}
		} else {
			for (int i = 0; i < listData.size(); i++) {
				Object[] obj = (Object[]) listData.get(i);
				ReportAutokbs mass = new ReportAutokbs();
				mass.setAppearNum(Integer.parseInt(obj[0].toString()));
				mass.setKeyWord(obj[1].toString());
				list.add(mass);
			}

		}
		return ReportAutokbs.toJsonArray(list);
	}

	@RequestMapping(value = "queryDataGridAuto", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public void queryDataGridAuto(HttpServletRequest request,
			HttpServletResponse response, String dtGridPager) {
		Pager pager = null;
		String platformUser = "";
		String startTime = "";
		String endTime = "";
		String dateType = "";
		Map parm = new HashMap<String, String>();
		try {
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			parm = pager.getParameters();
			platformUser = (String) parm.get("platformUser");
			startTime = (String) parm.get("startTime");
			endTime = (String) parm.get("endTime");
			dateType = (String) parm.get("dateType");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int firstResult = pager.getStartRecord();
		int maxResults = pager.getPageSize();
		int totalPage = 0;
		List<ReportAutokbs> list = new ArrayList<ReportAutokbs>();
		List<ReportAutokbs> listdata = new ArrayList<ReportAutokbs>();
		if (platformUser != null && platformUser != "") {
			WccPlatformUser platform = WccPlatformUser
					.findWccPlatformUserByIds(platformUser).get(0);
			if (platform != null) {
				List listData = null;
				if (dateType.equals("0")) {
					listData = ReportAutokbs.findReportAutokbsByDateTop(
							platform, startTime, endTime);
				} else {
					String lastdate = DateUtil.getLastMonthDayOfCurrentDay(
							endTime + "-01", DateUtil.YEAR_MONTH_DAY_FORMATER);
					String firstdate = DateUtil
							.getFirstMonthDayOfCurrentDay(startTime + "-01",
									DateUtil.YEAR_MONTH_DAY_FORMATER);
					listData = ReportAutokbs.findReportAutokbsByMonthTop(
							platform, firstdate, lastdate);
				}
				System.out.println(listData.size());
				if (listData.size() >= 21) {
					for (int i = 0; i < 20; i++) {
						Object[] obj = (Object[]) listData.get(i);
						ReportAutokbs mass = new ReportAutokbs();
						mass.setPlatformUser(platform.getId());
						mass.setAppearNum(Integer.parseInt(obj[0].toString()));
						mass.setKeyWord(obj[1].toString());
						mass.setRanking(i + 1);
						list.add(mass);
					}
				} else {
					for (int i = 0; i < listData.size(); i++) {
						Object[] obj = (Object[]) listData.get(i);
						ReportAutokbs mass = new ReportAutokbs();
						mass.setPlatformUser(platform.getId());
						mass.setAppearNum(Integer.parseInt(obj[0].toString()));
						mass.setKeyWord(obj[1].toString());
						mass.setRanking(i + 1);
						list.add(mass);
					}

				}
				long totalCount = list.size();
				if (firstResult + maxResults < totalCount) {
					for (int i = firstResult; i < firstResult + maxResults; i++) {
						listdata.add(list.get(i));
					}
				} else {
					for (int i = firstResult; i < totalCount; i++) {
						listdata.add(list.get(i));
					}
				}
				pager.setRecordCount(totalCount);// 设置总记录数
				// 根据总记录数和每页的大小计算公有多少页
				if ((totalCount % maxResults) > 0) {
					totalPage = (int) ((totalCount / maxResults) + 1);
				} else {
					totalPage = (int) (totalCount / maxResults);
				}
			}
		}
		pager.setExhibitDatas(listdata);
		pager.setIsSuccess(true);
		pager.setPageCount(totalPage);
		JSONObject pagerJSON = JSONObject.fromObject(pager);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		try {
			response.getWriter().write(pagerJSON.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "exportExcelTop")
	public void exportExcelTop(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "platform") WccPlatformUser platform,
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "dateType") Integer dateType,
			@RequestParam(value = "endTime") String endTime) throws IOException {
		List listData = null;
		if (dateType == 0) {
			listData = ReportAutokbs.findReportAutokbsByDateTop(platform,
					startTime, endTime);
		} else {
			String lastdate = DateUtil.getLastMonthDayOfCurrentDay(startTime
					+ "-01", DateUtil.YEAR_MONTH_DAY_FORMATER);
			String firstdate = DateUtil.getFirstMonthDayOfCurrentDay(endTime
					+ "-01", DateUtil.YEAR_MONTH_DAY_FORMATER);
			listData = ReportAutokbs.findReportAutokbsByMonthTop(platform,
					firstdate, lastdate);
		}
		List<ReportAutokbs> list = new ArrayList<ReportAutokbs>();
		if (listData.size() > 21) {
			for (int i = 0; i < 20; i++) {
				Object[] obj = (Object[]) listData.get(i);
				ReportAutokbs mass = new ReportAutokbs();
				mass.setPlatformUser(platform.getId());
				mass.setAppearNum(Integer.parseInt(obj[0].toString()));
				mass.setKeyWord(obj[1].toString());
				mass.setRanking(i + 1);
				list.add(mass);
			}
		} else {
			for (int i = 0; i < listData.size(); i++) {
				Object[] obj = (Object[]) listData.get(i);
				ReportAutokbs mass = new ReportAutokbs();
				mass.setPlatformUser(platform.getId());
				mass.setAppearNum(Integer.parseInt(obj[0].toString()));
				mass.setKeyWord(obj[1].toString());
				mass.setRanking(i + 1);
				list.add(mass);
			}

		}
		List<Map> listDatas = new ArrayList<Map>();
		for (ReportAutokbs mass : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("platformUserName", mass.getPlatformUserName());
			map.put("ranking", mass.getRanking());
			map.put("keyWord", mass.getKeyWord());
			map.put("appearNum", mass.getAppearNum());
			listDatas.add(map);
		}

		String[] title = { "公众平台", "排行", "消息关键词", "出现次数" };
		String[] colums = { "platformUserName", "ranking", "keyWord",
				"appearNum" };
		OutputStream os = null;
		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("关键词TOP20").getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			os = response.getOutputStream();

			map.put("关键词TOP20", listDatas);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel(title, colums, map, os);

	}

	@RequestMapping(value = "queryMessage", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryMessage(
			HttpServletRequest request,
			@RequestParam(value = "platform") WccPlatformUser platform,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "commentTimeId", required = false) String commentTimeId,
			@RequestParam(value = "dateType") Integer dateType,
			@RequestParam(value = "queryType") Integer queryType,
			@RequestParam(value = "endTime", required = false) String endTime) {
		List<ReportMessage> listData = new ArrayList<ReportMessage>();
		if (dateType == 0) {
			listData = showDayDate(platform, startTime, endTime, queryType);
		} else if (dateType == 1) {
			listData = showMonthDate(platform, startTime, endTime, queryType);
		} else {
			listData = showHourDate(platform, commentTimeId, queryType);
		}
		return ReportMessage.toJsonArray(listData);
	}

	private List<ReportMessage> showDayDate(WccPlatformUser platform,
			String startTime, String endTime, Integer queryType) {
		List<ReportMessage> listData = new ArrayList<ReportMessage>();
		List<ReportMessage> list = ReportMessage
				.findReportMessageByDateBetween(platform, startTime, endTime,
						-1, -1, 1);
		List<String> dayListStr = DateUtil.getDateList(startTime, endTime,
				Calendar.DAY_OF_YEAR);
		for (String date : dayListStr) {
			ReportMessage mess = new ReportMessage();
			mess.setDateStr(date);
			mess.setPlatformUser(platform.getId());
			for (ReportMessage message : list) {
				if (date.equals(DateUtil.getDayStr(message.getRefDate()))) {
					// 消息类型，1代表文字 2代表图片 3代表语音 4代表视频 6代表第三方应用消息（链接消息）
					if (queryType == 0) {// 0 人数 1 次数
						switch (message.getMsgType()) {
						case 1:
							mess.setTextCount(message.getMsgUser());
							break;
						case 2:
							mess.setImageCount(message.getMsgUser());
							break;
						case 3:
							mess.setVoiceCount(message.getMsgUser());
							break;
						case 4:
							mess.setVodieCount(message.getMsgUser());
							break;
						case 6:
							mess.setOtherCount(message.getMsgUser());
							break;
						}
					} else {
						switch (message.getMsgType()) {
						case 1:
							mess.setTextCount(message.getMsgCount());
							break;
						case 2:
							mess.setImageCount(message.getMsgCount());
							break;
						case 3:
							mess.setVoiceCount(message.getMsgCount());
							break;
						case 4:
							mess.setVodieCount(message.getMsgCount());
							break;
						case 6:
							mess.setOtherCount(message.getMsgCount());
							break;
						}
					}
				}
			}
			listData.add(mess);
		}
		return listData;
	}

	private List<ReportMessage> showMonthDate(WccPlatformUser platform,
			String startTime, String endTime, Integer queryType) {
		List<ReportMessage> listData = new ArrayList<ReportMessage>();
		List<ReportMessage> list = ReportMessage
				.findReportMessageByDateBetween(platform, startTime + "-01",
						endTime + "-01", -1, -1, 2);
		List<String> monthListStr = null;
		try {
			monthListStr = DateUtil.getMonthList(startTime, endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (monthListStr != null) {
			for (String month : monthListStr) {
				ReportMessage mess = new ReportMessage();
				mess.setDateStr(month);
				mess.setPlatformUser(platform.getId());
				for (ReportMessage message : list) {
					if ((month + "-01").equals(DateUtil.getDayStr(message
							.getRefDate()))) {
						// 消息类型，1代表文字 2代表图片 3代表语音 4代表视频 6代表第三方应用消息（链接消息）
						if (queryType == 0) {// 0 人数 1 次数
							switch (message.getMsgType()) {
							case 1:
								mess.setTextCount(message.getMsgUser());
								break;
							case 2:
								mess.setImageCount(message.getMsgUser());
								break;
							case 3:
								mess.setVoiceCount(message.getMsgUser());
								break;
							case 4:
								mess.setVodieCount(message.getMsgUser());
								break;
							case 6:
								mess.setOtherCount(message.getMsgUser());
								break;
							}
						} else {
							switch (message.getMsgType()) {
							case 1:
								mess.setTextCount(message.getMsgCount());
								break;
							case 2:
								mess.setImageCount(message.getMsgCount());
								break;
							case 3:
								mess.setVoiceCount(message.getMsgCount());
								break;
							case 4:
								mess.setVodieCount(message.getMsgCount());
								break;
							case 6:
								mess.setOtherCount(message.getMsgCount());
								break;
							}
						}
					}
				}
				listData.add(mess);
			}
		}
		return listData;
	}

	private List<ReportMessage> showHourDate(WccPlatformUser platform,
			String commentTimeId, Integer queryType) {
		List<ReportMessage> listData = new ArrayList<ReportMessage>();
		List<ReportMessage> list = ReportMessage
				.findReportMessageByDateBetween(platform, commentTimeId,
						commentTimeId, -1, -1, 0);
		for (int i = 0; i < 24; i++) {
			ReportMessage mess = new ReportMessage();
			mess.setDateStr(i + "时");
			mess.setPlatformUser(platform.getId());
			for (ReportMessage message : list) {
				if (i == Integer.parseInt(message.getRefHour())) {
					// 消息类型，1代表文字 2代表图片 3代表语音 4代表视频 6代表第三方应用消息（链接消息）
					if (queryType == 0) {// 0 人数 1 次数
						switch (message.getMsgType()) {
						case 1:
							mess.setTextCount(message.getMsgUser());
							break;
						case 2:
							mess.setImageCount(message.getMsgUser());
							break;
						case 3:
							mess.setVoiceCount(message.getMsgUser());
							break;
						case 4:
							mess.setVodieCount(message.getMsgUser());
							break;
						case 6:
							mess.setOtherCount(message.getMsgUser());
							break;
						}
					} else {
						switch (message.getMsgType()) {
						case 1:
							mess.setTextCount(message.getMsgCount());
							break;
						case 2:
							mess.setImageCount(message.getMsgCount());
							break;
						case 3:
							mess.setVoiceCount(message.getMsgCount());
							break;
						case 4:
							mess.setVodieCount(message.getMsgCount());
							break;
						case 6:
							mess.setOtherCount(message.getMsgCount());
							break;
						}
					}
				}
			}
			listData.add(mess);
		}
		return listData;
	}

	@ResponseBody
	@RequestMapping(value = "queryDataGridMessage", produces = "application/json")
	public void queryDataGridMessage(HttpServletRequest request,
			HttpServletResponse response, String dtGridPager) {
		Pager pager = null;
		String platformUser = "";
		String startTime = "";
		String endTime = "";
		String dateType = "";
		String queryType = "";
		String commentTimeId = "";
		Map parm = new HashMap<String, String>();
		try {
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			parm = pager.getParameters();
			platformUser = (String) parm.get("platformUser");
			dateType = (String) parm.get("dateType");
			queryType = (String) parm.get("queryType");
			if ("2".equals(dateType)) {
				commentTimeId = (String) parm.get("commentTimeId");
			} else {
				startTime = (String) parm.get("startTime");
				endTime = (String) parm.get("endTime");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int firstResult = pager.getStartRecord();
		int maxResults = pager.getPageSize();
		int totalPage = 0;
		List<ReportMessage> list = null;
		List<ReportMessage> listdata = new ArrayList<ReportMessage>();
		if (platformUser != null && platformUser != "") {
			WccPlatformUser platform = WccPlatformUser
					.findWccPlatformUserByIds(platformUser).get(0);
			if ("0".equals(dateType)) {// 按日查询
				list = showDayDate(platform, startTime, endTime,
						Integer.parseInt(queryType));
			} else if ("1".equals(dateType)) {// 按月查询
				list = showMonthDate(platform, startTime, endTime,
						Integer.parseInt(queryType));
			} else {// 按天查询
				list = showHourDate(platform, commentTimeId,
						Integer.parseInt(queryType));
			}
			long totalCount = list.size();
			if (firstResult + maxResults < totalCount) {
				for (int i = firstResult; i < firstResult + maxResults; i++) {
					listdata.add(list.get(i));
				}
			} else {
				for (int i = firstResult; i < totalCount; i++) {
					listdata.add(list.get(i));
				}
			}
			pager.setRecordCount(totalCount);// 设置总记录数
			// 根据总记录数和每页的大小计算公有多少页
			if ((totalCount % maxResults) > 0) {
				totalPage = (int) ((totalCount / maxResults) + 1);
			} else {
				totalPage = (int) (totalCount / maxResults);
			}
		}
		pager.setExhibitDatas(listdata);
		pager.setIsSuccess(true);
		pager.setPageCount(totalPage);
		JSONObject pagerJSON = JSONObject.fromObject(pager);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		try {
			response.getWriter().write(pagerJSON.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "exportExcelMessage")
	public void exportExcelMessage(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "platform") WccPlatformUser platform,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "commentTimeId", required = false) String commentTimeId,
			@RequestParam(value = "dateType") Integer dateType,
			@RequestParam(value = "queryType") Integer queryType,
			@RequestParam(value = "endTime", required = false) String endTime)
			throws IOException {
		List<ReportMessage> listData = new ArrayList<ReportMessage>();
		if (dateType == 0) {
			listData = showDayDate(platform, startTime, endTime, queryType);
		} else if (dateType == 1) {
			listData = showMonthDate(platform, startTime, endTime, queryType);
		} else {
			listData = showHourDate(platform, commentTimeId, queryType);
		}
		List<Map> listDatas = new ArrayList<Map>();
		for (ReportMessage mass : listData) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("platformUserName", mass.getPlatformUserName());
			map.put("refdate", mass.getDateStr());
			map.put("text", mass.getTextCount());
			map.put("image", mass.getImageCount());
			map.put("voice", mass.getVoiceCount());
			map.put("vodie", mass.getVodieCount());
			map.put("other", mass.getOtherCount());
			listDatas.add(map);
		}
		String[] title = { "公众平台", "时间", "文本/次数", "图片/次数", "语音/次数", "视频/次数",
				"第三方应用/次数" };
		if (queryType == 0) {
			title[2] = "文本/人数";
			title[3] = "图片/人数";
			title[4] = "语音/人数";
			title[5] = "视频/人数";
			title[6] = "第三方应用/人数";
		}
		String[] colums = { "platformUserName", "refdate", "text", "image",
				"voice", "vodie", "other" };
		OutputStream os = null;
		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("互动消息统计").getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			os = response.getOutputStream();

			map.put("互动消息统计", listDatas);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel(title, colums, map, os);
	}

	@RequestMapping(value = "queryMessageDistribute", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryMessageDistribute(
			HttpServletRequest request,
			@RequestParam(value = "platform") WccPlatformUser platform,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "dateType") Integer dateType,
			@RequestParam(value = "endTime", required = false) String endTime) {
		List<ReportMessageDistribute> listData = new ArrayList<ReportMessageDistribute>();
		if (dateType == 0) {
			listData = showDayDistributeDate(platform, startTime, endTime);
		} else if (dateType == 1) {
			listData = showMonthDistributeDate(platform, startTime, endTime);
		}
		return ReportMessageDistribute.toJsonArray(listData);
	}

	private List<ReportMessageDistribute> showDayDistributeDate(
			WccPlatformUser platform, String startTime, String endTime) {
		List<ReportMessageDistribute> listData = new ArrayList<ReportMessageDistribute>();
		List<ReportMessageDistribute> list = ReportMessageDistribute
				.findReportMessageDistributeByDateBetween(platform, startTime,
						endTime, -1, -1);
		List<String> dayListStr = DateUtil.getDateList(startTime, endTime,
				Calendar.DAY_OF_YEAR);
		for (String date : dayListStr) {
			ReportMessageDistribute mess = new ReportMessageDistribute();
			mess.setDateStr(date);
			mess.setPlatformUser(platform.getId());
			for (ReportMessageDistribute message : list) {
				if (date.equals(DateUtil.getDayStr(message.getRefDate()))) {
					// 当日发送消息量分布的区间，0代表 “0”，1代表“1-5”，2代表“6-10”，3代表“10次以上”
					switch (message.getCountInterval()) {
					case 1:
						mess.setaCount(message.getMsgUser());
						break;
					case 2:
						mess.setbCount(message.getMsgUser());
						break;
					case 3:
						mess.setcCount(message.getMsgUser());
						break;
					}
				}
			}
			listData.add(mess);
		}
		return listData;
	}

	private List<ReportMessageDistribute> showMonthDistributeDate(
			WccPlatformUser platform, String startTime, String endTime) {
		List<ReportMessageDistribute> listData = new ArrayList<ReportMessageDistribute>();
		List<ReportMessageDistribute> list = ReportMessageDistribute
				.findReportMessageDistributeByDateBetween(platform, startTime
						+ "-01", endTime + "-01", -1, -1);
		List<String> monthListStr = null;
		try {
			monthListStr = DateUtil.getMonthList(startTime, endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (monthListStr != null) {
			for (String month : monthListStr) {
				ReportMessageDistribute mess = new ReportMessageDistribute();
				mess.setDateStr(month);
				mess.setPlatformUser(platform.getId());
				for (ReportMessageDistribute message : list) {
					if ((month + "-01").equals(DateUtil.getDayStr(message
							.getRefDate()))) {
						// 消息类型，1代表文字 2代表图片 3代表语音 4代表视频 6代表第三方应用消息（链接消息）
						switch (message.getCountInterval()) {
						case 1:
							mess.setaCount(message.getMsgUser());
							break;
						case 2:
							mess.setbCount(message.getMsgUser());
							break;
						case 3:
							mess.setcCount(message.getMsgUser());
							break;
						}
					}
				}
				listData.add(mess);
			}
		}
		return listData;
	}

	@ResponseBody
	@RequestMapping(value = "queryDataGridMessageDistributeDate", produces = "application/json")
	public void queryDataGridMessageDistributeDate(HttpServletRequest request,
			HttpServletResponse response, String dtGridPager) {
		Pager pager = null;
		String platformUser = "";
		String startTime = "";
		String endTime = "";
		String dateType = "";
		String commentTimeId = "";
		Map parm = new HashMap<String, String>();
		try {
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			parm = pager.getParameters();
			platformUser = (String) parm.get("platformUser");
			dateType = (String) parm.get("dateType");
			startTime = (String) parm.get("startTime");
			endTime = (String) parm.get("endTime");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int firstResult = pager.getStartRecord();
		int maxResults = pager.getPageSize();
		int totalPage = 0;
		List<ReportMessageDistribute> list = null;
		List<ReportMessageDistribute> listdata = new ArrayList<ReportMessageDistribute>();
		if (platformUser != null && platformUser != "") {
			WccPlatformUser platform = WccPlatformUser
					.findWccPlatformUserByIds(platformUser).get(0);
			if ("0".equals(dateType)) {// 按日查询
				list = showDayDistributeDate(platform, startTime, endTime);
			} else if ("1".equals(dateType)) {// 按月查询
				list = showMonthDistributeDate(platform, startTime, endTime);
			}
			long totalCount = list.size();
			if (firstResult + maxResults < totalCount) {
				for (int i = firstResult; i < firstResult + maxResults; i++) {
					listdata.add(list.get(i));
				}
			} else {
				for (int i = firstResult; i < totalCount; i++) {
					listdata.add(list.get(i));
				}
			}
			pager.setRecordCount(totalCount);// 设置总记录数
			// 根据总记录数和每页的大小计算公有多少页
			if ((totalCount % maxResults) > 0) {
				totalPage = (int) ((totalCount / maxResults) + 1);
			} else {
				totalPage = (int) (totalCount / maxResults);
			}
		}
		pager.setExhibitDatas(listdata);
		pager.setIsSuccess(true);
		pager.setPageCount(totalPage);
		JSONObject pagerJSON = JSONObject.fromObject(pager);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		try {
			response.getWriter().write(pagerJSON.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "exportExcelDistribute")
	public void exportExcelDistribute(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "platform") WccPlatformUser platform,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "dateType") Integer dateType,
			@RequestParam(value = "endTime", required = false) String endTime)
			throws IOException {
		List<ReportMessageDistribute> listData = new ArrayList<ReportMessageDistribute>();
		if (0==dateType) {// 按日查询
			listData = showDayDistributeDate(platform, startTime, endTime);
		} else if (1==dateType) {// 按月查询
			listData = showMonthDistributeDate(platform, startTime, endTime);
		}
		List<Map> listDatas = new ArrayList<Map>();
		for (ReportMessageDistribute mass : listData) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("platformUserName", mass.getPlatformUserName());
			map.put("refdate", mass.getDateStr());
			map.put("a", mass.getaCount());
			map.put("b", mass.getbCount());
			map.put("c", mass.getcCount());
			listDatas.add(map);
		}
		String[] title = { "公众平台", "时间", "1-5次", "6-10次", "10次以上" };
		String[] colums = { "platformUserName", "refdate", "a", "b", "c" };
		OutputStream os = null;
		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("消息分布统计").getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			os = response.getOutputStream();

			map.put("消息分布统计", listDatas);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel(title, colums, map, os);
	}

}

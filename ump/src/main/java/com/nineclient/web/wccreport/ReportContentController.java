package com.nineclient.web.wccreport;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nineclient.model.PubOperator;
import com.nineclient.model.PubOrganization;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.wccreport.ReportContentChannel;
import com.nineclient.model.wccreport.WccMassPicText;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.ExcelUtil;
import com.nineclient.utils.HttpClientUtil;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;

@RequestMapping("/reportcontents")
@Controller
@RooWebScaffold(path = "reportcontents", formBackingObject = ReportContentChannel.class)
public class ReportContentController {

	@RequestMapping(value = "saveContentChannelFromWx", produces = "text/html")
	@ResponseBody
	public String saveContentChannelFromWx(
			@RequestParam(value = "platform") WccPlatformUser platform,
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime) {
		String flag = "0"; // 返回标识 0 失败 1 成功
		if (platform != null) {
			WxMpService wxMpService = new WxMpServiceImpl();
			WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
			config.setAppId(platform.getAppId());
			config.setSecret(platform.getAppSecret());
			wxMpService.setWxMpConfigStorage(config);
			String accessToken = "";
			try {
				accessToken = wxMpService.getAccessToken();
			} catch (WxErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!"".equals(accessToken)) {
				String json = "";
				if (startTime != null && endTime != null) {
					json = "{\"begin_date\": \"" + startTime
							+ "\",\"end_date\": \"" + endTime + "\"}";
				}
				String url = "https://api.weixin.qq.com/datacube/getusershare?access_token=";
				String returnList = "";
				try {
					returnList = HttpClientUtil.doJsonPost(url + accessToken, json);
					return returnList;
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!"".equals(returnList)) {
					JSONObject jsonObject = JSONObject.fromObject(returnList);
					JSONArray data = jsonObject.getJSONArray("list");
					for (int i = 0; i < data.size(); i++) {
						String s = data.getString(i);
						JSONObject data2 = JSONObject.fromObject(s);
						ReportContentChannel contentChannel = new ReportContentChannel();
						contentChannel.setRefDate(DateUtil.getDate(data2
								.getString("ref_date")));
						contentChannel.setShareScene(Integer.parseInt(data2
								.getString("share_scene")));
						contentChannel.setShareCount(Integer.parseInt(data2
								.getString("share_count")));
						contentChannel.setShareUser(Integer.parseInt(data2
								.getString("share_user")));
						contentChannel.setPlatformUser(platform.getId());
						contentChannel.persist();
						// System.out.println(data2.getString("ref_date"));
						// System.out.println(data2.getString("cumulate_user"));
					}
					flag = "1";
				}
			}
		}
		return flag;
	}

	@RequestMapping(value = "saveContentTopFromWx", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String saveContentTopFromWx(
			@RequestParam(value = "platform") WccPlatformUser platform,
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime) {
		String flag = "0"; // 返回标识 0 失败 1 成功
		if (platform != null) {
			WxMpService wxMpService = new WxMpServiceImpl();
			WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
			config.setAppId(platform.getAppId());
			config.setSecret(platform.getAppSecret());
			wxMpService.setWxMpConfigStorage(config);
			String accessToken = "";
			try {
				accessToken = wxMpService.getAccessToken();
			} catch (WxErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!"".equals(accessToken)) {
				String json = "";
				if (startTime != null && endTime != null) {
					json = "{\"begin_date\": \"" + startTime
							+ "\",\"end_date\": \"" + endTime + "\"}";
				}
				String url = "https://api.weixin.qq.com/datacube/getarticlesummary?access_token=";
				String returnList = "";
				try {
					returnList = HttpClientUtil.doJsonPost(url + accessToken, json);
					return returnList;
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!"".equals(returnList)) {
					JSONObject jsonObject = JSONObject.fromObject(returnList);
					JSONArray data = jsonObject.getJSONArray("list");
					for (int i = 0; i < data.size(); i++) {
						String s = data.getString(i);
						JSONObject data2 = JSONObject.fromObject(s);
						WccMassPicText mass = new WccMassPicText();
						mass.setRefDate(DateUtil.getDate(data2
								.getString("ref_date")));
						mass.setMsgid(data2.getString("msgid"));
						mass.setTitle(data2.getString("title"));
						mass.setIntPageReadUser(data2
								.getInt("int_page_read_user"));
						mass.setIntPageReadCount(data2
								.getInt("int_page_read_count"));
						mass.setShareUser(Integer.parseInt(data2
								.getString("share_user")));
						mass.setShareCount(Integer.parseInt(data2
								.getString("share_count")));
						mass.setAddToFavUser(data2.getInt("add_to_fav_user"));
						mass.setAddToFavCount(data2.getInt("add_to_fav_count"));
						mass.setType(3);
						mass.persist();
						// System.out.println(data2.getString("ref_date"));
						// System.out.println(data2.getString("cumulate_user"));
					}
					flag = "1";
				}
			}
		}
		return flag;
	}

	@RequestMapping(value = "reportPage", produces = "text/html")
	public String reportPage(HttpServletRequest request, Model uiModel) {
//		// 初始化数据
//		List<WccPlatformUser> platformUsers = WccPlatformUser
//				.findAllWccPlatformUsers(CommonUtils
//						.getCurrentPubOperator(request));
//		if (platformUsers.size() > 0) {
//			uiModel.addAttribute("platformUsers", platformUsers);
//		}
		PubOperator currentoper=CommonUtils.getCurrentPubOperator(request);
        List<WccPlatformUser> platformUsers=WccPlatformUser.getplatformuserListByOrg(currentoper);
		uiModel.addAttribute("platformUsers", platformUsers);
		return "reportcontents/report";
	}

	@RequestMapping(value = "queryContentChannel", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryContentChannel(HttpServletRequest request,
			@RequestParam(value = "platform") WccPlatformUser platform,
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime,
			@RequestParam(value = "dateType") Integer dateType,
			@RequestParam(value = "queryType") Integer queryType) {
		List<ReportContentChannel> listData = new ArrayList<ReportContentChannel>();
		if (dateType == 0) {
			listData = showDate(platform, startTime, endTime, queryType);
		} else if (dateType == 1) {
			listData = showMonthDate(platform, startTime, endTime, queryType);
		}
		return ReportContentChannel.toJsonArray(listData);
	}

	@RequestMapping(value = "queryContentTop", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryContentTop(HttpServletRequest request,
			@RequestParam(value = "platform") WccPlatformUser platform,
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime) {
		List listData = WccMassPicText.findWccMassPicTextByDateTop(platform,
				startTime, endTime);
		List<WccMassPicText> list = new ArrayList<WccMassPicText>();
		if (listData.size() > 6) {
			for (int i = 0; i < 5; i++) {
				Object[] obj = (Object[]) listData.get(i);
				WccMassPicText mass = new WccMassPicText();
				mass.setTitle(obj[3].toString());
				mass.setIntPageReadUser(Integer.parseInt(obj[0].toString()));
				mass.setShareUser(Integer.parseInt(obj[1].toString()));
				mass.setAddToFavUser(Integer.parseInt(obj[2].toString()));
				list.add(mass);
			}
		} else {
			for (int i = 0; i < listData.size(); i++) {
				Object[] obj = (Object[]) listData.get(i);
				WccMassPicText mass = new WccMassPicText();
				mass.setTitle(obj[3].toString());
				mass.setIntPageReadUser(Integer.parseInt(obj[0].toString()));
				mass.setShareUser(Integer.parseInt(obj[1].toString()));
				mass.setAddToFavUser(Integer.parseInt(obj[2].toString()));
				list.add(mass);
			}

		}
		return WccMassPicText.toJsonArray(list);
	}

	// 表格上 来源数据
	@ResponseBody
	@RequestMapping(value = "queryDataGridChannel", produces = "application/json")
	public void queryDataGridChannel(HttpServletRequest request,
			HttpServletResponse response, String dtGridPager) {
		Pager pager = null;
		String platformUser = "";
		String startTime = "";
		String endTime = "";
		String dateType = "";
		String queryType = "";
		Map parm = new HashMap<String, String>();
		try {
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			parm = pager.getParameters();
			platformUser = (String) parm.get("platformUser");
			startTime = (String) parm.get("startTime");
			endTime = (String) parm.get("endTime");
			dateType = (String) parm.get("dateType");
			queryType = (String) parm.get("queryType");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int firstResult = pager.getStartRecord();
		int maxResults = pager.getPageSize();
		int totalPage = 0;
		List<ReportContentChannel> list = null;
		List<ReportContentChannel> listdata = new ArrayList<ReportContentChannel>();
		if (platformUser != null && platformUser != "") {
			WccPlatformUser platform = WccPlatformUser
					.findWccPlatformUserByIds(platformUser).get(0);
			if ("0".equals(dateType)) {// 按日查询
				if (platform != null) {
					list = showDate(platform, startTime, endTime,
							Integer.parseInt(queryType));
					long totalCount = list.size();
					if(firstResult+maxResults<totalCount){
						for(int i=firstResult;i<firstResult+maxResults;i++){
							listdata.add(list.get(i));
						}
					}else{
						for(int i=firstResult;i<totalCount;i++){
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
			} else {// 按月查询
				list = showMonthDate(platform, startTime, endTime,
						Integer.parseInt(queryType));
				long totalCount = list.size();
				if(firstResult+maxResults<totalCount){
					for(int i=firstResult;i<firstResult+maxResults;i++){
						listdata.add(list.get(i));
					}
				}else{
					for(int i=firstResult;i<totalCount;i++){
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

	// 表格上 来源数据
	@ResponseBody
	@RequestMapping(value = "queryDataGridTop", produces = "application/json")
	public void queryDataGridTop(HttpServletRequest request,
			HttpServletResponse response, String dtGridPager) {
		Pager pager = null;
		String platformUser = "";
		String startTime = "";
		String endTime = "";
		Map parm = new HashMap<String, String>();
		try {
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			parm = pager.getParameters();
			platformUser = (String) parm.get("platformUser");
			startTime = (String) parm.get("startTime");
			endTime = (String) parm.get("endTime");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int firstResult = pager.getStartRecord();
		int maxResults = pager.getPageSize();
		int totalPage = 0;
		List<WccMassPicText> list = new ArrayList<WccMassPicText>();
		if (platformUser != null && platformUser != "") {
			WccPlatformUser platform = WccPlatformUser
					.findWccPlatformUserByIds(platformUser).get(0);
			if (platform != null) {
				List listData = WccMassPicText.findWccMassPicTextByDateTop(
						platform, startTime, endTime);
				if (listData.size() > 6) {
					for (int i = 0; i < 5; i++) {
						Object[] obj = (Object[]) listData.get(i);
						WccMassPicText mass = new WccMassPicText();
						mass.setTitle(obj[3].toString());
						mass.setIntPageReadUser(Integer.parseInt(obj[0]
								.toString()));
						mass.setShareUser(Integer.parseInt(obj[1].toString()));
						mass.setAddToFavUser(Integer.parseInt(obj[2].toString()));
						mass.setRanking(i + 1);
						mass.setPlatformUserName(platform.getAccount());
						list.add(mass);
					}
				} else {
					for (int i = 0; i < listData.size(); i++) {
						Object[] obj = (Object[]) listData.get(i);
						WccMassPicText mass = new WccMassPicText();
						mass.setTitle(obj[3].toString());
						mass.setIntPageReadUser(Integer.parseInt(obj[0]
								.toString()));
						mass.setShareUser(Integer.parseInt(obj[1].toString()));
						mass.setAddToFavUser(Integer.parseInt(obj[2].toString()));
						mass.setRanking(i + 1);
						mass.setPlatformUserName(platform.getAccount());
						list.add(mass);
					}

				}
				long totalCount = list.size();
				pager.setRecordCount(totalCount);// 设置总记录数
				// 根据总记录数和每页的大小计算公有多少页
				if ((totalCount % maxResults) > 0) {
					totalPage = (int) ((totalCount / maxResults) + 1);
				} else {
					totalPage = (int) (totalCount / maxResults);
				}
			}
		}
		pager.setExhibitDatas(list);
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

	/**
	 * 转发途径报表List(日)
	 * 
	 * @param platform
	 * @param startTime
	 * @param endTime
	 * @param queryType
	 * @return
	 */
	private List<ReportContentChannel> showDate(WccPlatformUser platform,
			String startTime, String endTime, Integer queryType) {
		List<ReportContentChannel> listData = new ArrayList<ReportContentChannel>();
		List<ReportContentChannel> list = ReportContentChannel
				.findReportContentChannelsByDateBetween(platform, startTime,
						endTime);
		List<String> dayListStr = DateUtil.getDateList(startTime, endTime,
				Calendar.DAY_OF_YEAR);
		for (String date : dayListStr) {
			ReportContentChannel channel = new ReportContentChannel();
			channel.setDateStr(date);
			channel.setPlatformUser(platform.getId());
			for (ReportContentChannel contentChannel : list) {
				if (date.equals(DateUtil.getDayStr(contentChannel.getRefDate()))) {
					if (0 == queryType) {
						// 1代表好友转发 2代表朋友圈 3代表腾讯微博 255代表其他
						switch (contentChannel.getShareScene()) {
						case 1:
							channel.setForward(contentChannel.getShareUser());
							break;
						case 2:
							channel.setCircle(contentChannel.getShareUser());
							break;
						case 3:
							channel.setBlog(contentChannel.getShareUser());
							break;
						case 5:
							channel.setOther(contentChannel.getShareUser());
							break;
						}
					} else {
						switch (contentChannel.getShareScene()) {
						case 1:
							channel.setForward(contentChannel.getShareCount());
							break;
						case 2:
							channel.setCircle(contentChannel.getShareCount());
							break;
						case 3:
							channel.setBlog(contentChannel.getShareCount());
							break;
						case 5:
							channel.setOther(contentChannel.getShareCount());
							break;
						}
					}
				}
			}
			listData.add(channel);
		}
		return listData;
	}

	/**
	 * 粉丝增长来源报表List(月)
	 * 
	 * @param platform
	 * @param startTime
	 * @param endTime
	 * @param queryType
	 * @return
	 */
	private List<ReportContentChannel> showMonthDate(WccPlatformUser platform,
			String startTime, String endTime, Integer queryType) {
		List<ReportContentChannel> listData = new ArrayList<ReportContentChannel>();
		List<String> monthListStr = null;
		try {
			monthListStr = DateUtil.getMonthList(startTime, endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (monthListStr != null) {
			for (String month : monthListStr) {
				String lastdate = DateUtil.getLastMonthDayOfCurrentDay(month
						+ "-01", DateUtil.YEAR_MONTH_DAY_FORMATER);
				String firstdate = DateUtil.getFirstMonthDayOfCurrentDay(month
						+ "-01", DateUtil.YEAR_MONTH_DAY_FORMATER);
				if(DateUtil.getDate(lastdate).getTime()>new Date().getTime()){
					lastdate = DateUtil.getDayStr(DateUtil.getBeforeDay(new Date(), -1));
				}
				List list = ReportContentChannel
						.findReportContentChannelsByDate(platform, firstdate,
								lastdate);
				ReportContentChannel channel = new ReportContentChannel();
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[]) list.get(i);
					if (0 == queryType) {
						switch (Integer.parseInt(obj[2].toString())) {
						case 1:
							channel.setForward(Integer.parseInt(obj[0]
									.toString()));
							break;
						case 2:
							channel.setCircle(Integer.parseInt(obj[0]
									.toString()));
							break;
						case 3:
							channel.setBlog(Integer.parseInt(obj[0].toString()));
							break;
						case 5:
							channel.setOther(Integer.parseInt(obj[0].toString()));
							break;
						}
					} else {
						switch (Integer.parseInt(obj[2].toString())) {
						case 1:
							channel.setForward(Integer.parseInt(obj[0]
									.toString()));
							break;
						case 2:
							channel.setCircle(Integer.parseInt(obj[0]
									.toString()));
							break;
						case 3:
							channel.setBlog(Integer.parseInt(obj[0].toString()));
							break;
						case 5:
							channel.setOther(Integer.parseInt(obj[0].toString()));
							break;
						}

					}
				}
				channel.setDateStr(month);
				channel.setPlatformUser(platform.getId());
				listData.add(channel);
			}
		}
		return listData;

	}

	@RequestMapping(value = "exportExcelChannel")
	public void exportExcelChannel(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "platform") WccPlatformUser platform,
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime,
			@RequestParam(value = "dateType") Integer dateType,
			@RequestParam(value = "queryType") Integer queryType)
			throws IOException {

		List<ReportContentChannel> list = new ArrayList<ReportContentChannel>();
		List<Map> listData = new ArrayList<Map>();
		if (dateType == 0) {// 按日查询
			list = showDate(platform, startTime, endTime, queryType);
			for (ReportContentChannel channel : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("platformUserName", channel.getPlatformUserName());
				map.put("dateStr", channel.getDateStr());
				map.put("forward", channel.getForward());
				map.put("circle", channel.getCircle());
				map.put("blog", channel.getBlog());
				map.put("other", channel.getOther());
				listData.add(map);
			}
		} else {// 按月查询
			list = showMonthDate(platform, startTime, endTime, queryType);
			for (ReportContentChannel channel : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("platformUserName", channel.getPlatformUserName());
				map.put("dateStr", channel.getDateStr());
				map.put("platformUserName", channel.getPlatformUserName());
				map.put("dateStr", channel.getDateStr());
				map.put("forward", channel.getForward());
				map.put("circle", channel.getCircle());
				map.put("blog", channel.getBlog());
				map.put("other", channel.getOther());
				listData.add(map);
			}
		}

		String[] title = { "公众平台", "日期", "好友转发", "朋友圈", "腾讯微博", "其它" };
		String[] colums = { "platformUserName", "dateStr", "forward", "circle",
				"blog", "other" };
		OutputStream os = null;
		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("转发渠道报表").getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			os = response.getOutputStream();

			map.put("转发渠道报表", listData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel(title, colums, map, os);
	}

	@RequestMapping(value = "exportExcelTop")
	public void exportExcelTop(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "platform") WccPlatformUser platform,
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime) throws IOException {
		List listDatas = WccMassPicText.findWccMassPicTextByDateTop(platform,
				startTime, endTime);
		List<WccMassPicText> list = new ArrayList<WccMassPicText>();
		if (listDatas.size() > 6) {
			for (int i = 0; i < 5; i++) {
				Object[] obj = (Object[]) listDatas.get(i);
				WccMassPicText mass = new WccMassPicText();
				mass.setTitle(obj[3].toString());
				mass.setIntPageReadUser(Integer.parseInt(obj[0].toString()));
				mass.setShareUser(Integer.parseInt(obj[1].toString()));
				mass.setAddToFavUser(Integer.parseInt(obj[2].toString()));
				mass.setPlatformUserName(platform.getAccount());
				mass.setRanking(i + 1);
				list.add(mass);
			}
		} else {
			for (int i = 0; i < listDatas.size(); i++) {
				Object[] obj = (Object[]) listDatas.get(i);
				WccMassPicText mass = new WccMassPicText();
				mass.setTitle(obj[3].toString());
				mass.setIntPageReadUser(Integer.parseInt(obj[0].toString()));
				mass.setShareUser(Integer.parseInt(obj[1].toString()));
				mass.setAddToFavUser(Integer.parseInt(obj[2].toString()));
				mass.setPlatformUserName(platform.getAccount());
				mass.setRanking(i + 1);
				list.add(mass);
			}

		}

		List<Map> listData = new ArrayList<Map>();
		for (WccMassPicText mass : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("platformUserName", mass.getPlatformUserName());
			map.put("ranking", mass.getRanking());
			map.put("title", mass.getTitle());
			map.put("intPageReadUser", mass.getIntPageReadUser());
			map.put("shareUser", mass.getShareUser());
			map.put("addToFavUser", mass.getAddToFavUser());
			listData.add(map);
		}

		String[] title = { "公众平台", "排行", "文章名", "阅读量", "转发量", "收藏量" };
		String[] colums = { "platformUserName", "ranking", "title",
				"intPageReadUser", "shareUser", "addToFavUser" };
		OutputStream os = null;
		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("图文影响力TOP5").getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			os = response.getOutputStream();

			map.put("图文影响力TOP5", listData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel(title, colums, map, os);
	}

}

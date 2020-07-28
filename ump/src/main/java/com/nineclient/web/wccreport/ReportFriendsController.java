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

import com.nineclient.constant.WccFriendFormType;
import com.nineclient.model.PubOperator;
import com.nineclient.model.PubOrganization;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.wccreport.ReportFriendsFrom;
import com.nineclient.model.wccreport.ReportFriendsSum;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.ExcelUtil;
import com.nineclient.utils.HttpClientUtil;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;

@RequestMapping("/reportfriends")
@Controller
@RooWebScaffold(path = "reportfriends", formBackingObject = ReportFriendsSum.class)
public class ReportFriendsController {

	@RequestMapping(value = "saveFriendsSumFromWx", produces = "text/html")
	@ResponseBody
	public String saveFriendsSumFromWx(HttpServletRequest request,
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
				String url = "https://api.weixin.qq.com/datacube/getusercumulate?access_token=";
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
						ReportFriendsSum friendsSum = new ReportFriendsSum();
						friendsSum.setRefDate(DateUtil.getDate(data2
								.getString("ref_date")));
						friendsSum.setCumulateUser(Long.parseLong(data2
								.getString("cumulate_user")));
						friendsSum.setPlatformUser(platform.getId());
						friendsSum.persist();
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
//			List<WccPlatformUser> platformUsers = WccPlatformUser
//				.findAllWccPlatformUsers(CommonUtils
//						.getCurrentPubOperator(request));
//		if (platformUsers.size() > 0) {
//			uiModel.addAttribute("platformUsers", platformUsers);
//		}
		PubOperator currentoper=CommonUtils.getCurrentPubOperator(request);
        List<WccPlatformUser> platformUsers=WccPlatformUser.getplatformuserListByOrg(currentoper);
		uiModel.addAttribute("platformUsers", platformUsers);
		
		return "reportfriends/report";
	}

	@RequestMapping(value = "queryFriendsSum", produces = "text/html")
	@ResponseBody
	public String queryFriendsSum(HttpServletRequest request,
			@RequestParam(value = "platform") WccPlatformUser platform,
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime,
			@RequestParam(value = "dateType") Integer dateType) {
		List<ReportFriendsSum> listData = new ArrayList<ReportFriendsSum>();
		if (dateType == 0) {
			List<ReportFriendsSum> list = ReportFriendsSum
					.findReportFriendsSumsByDateBetween(platform, startTime,
							endTime, -1, -1);
			List<String> dayListStr = DateUtil.getDateList(startTime, endTime,
					Calendar.DAY_OF_YEAR);
			List changeNum = ReportFriendsFrom.findReportFriendsFromsByDay(
					platform, startTime, endTime);
			for (String date : dayListStr) {
				for (ReportFriendsSum friendsSum : list) {
					if (date.equals(DateUtil.getDayStr(friendsSum.getRefDate()))) {
						ReportFriendsSum sum = new ReportFriendsSum();
						for (int i = 0; i < changeNum.size(); i++) {
							Object[] obj = (Object[]) changeNum.get(i);
							// System.out.println(obj[0]);增长
							// System.out.println(obj[1]);取消
							// System.out.println(obj[2]);日期 2015-03-02
							// 00:00:00.0
							if (obj[2].toString().substring(0, 10).equals(date)) {
								sum.setNewNum(Integer.parseInt(obj[0]
										.toString()));
								sum.setCancelNum(Integer.parseInt(obj[1]
										.toString()));
								continue;
							}
						}
						sum.setDateStr(date);
						sum.setCumulateUser(friendsSum.getCumulateUser());
						listData.add(sum);
					}
				}
			}
		} else if (dateType == 1) {
			List<String> monthListStr = null;
			try {
				monthListStr = DateUtil.getMonthList(startTime, endTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (monthListStr != null) {
				for (String month : monthListStr) {
					String firstdate = DateUtil.getFirstMonthDayOfCurrentDay(
							month + "-01", DateUtil.YEAR_MONTH_DAY_FORMATER);
					String lastdate = DateUtil.getLastMonthDayOfCurrentDay(
							month + "-01", DateUtil.YEAR_MONTH_DAY_FORMATER);
					if (DateUtil.getDate(lastdate).getTime() > new Date()
							.getTime()) {
						lastdate = DateUtil.getDayStr(DateUtil.getBeforeDay(
								new Date(), -1));
					}
					ReportFriendsSum sum = ReportFriendsSum
							.findReportFriendsSumsByDate(platform.getId(),
									lastdate);
					sum.setDateStr(month);
					List list = ReportFriendsFrom
							.findReportFriendsFromsByMonth(platform, firstdate,
									lastdate);
					for (int i = 0; i < list.size(); i++) {
						Object[] obj = (Object[]) list.get(i);
						if (obj[0] != null) {
							sum.setNewNum(Integer.parseInt(obj[0].toString()));
						} else {
							sum.setNewNum(0);
						}
						if (obj[1] != null) {
							sum.setCancelNum(Integer.parseInt(obj[1].toString()));
						} else {
							sum.setCancelNum(0);
						}
					}
					listData.add(sum);
				}
			}
		}
		return ReportFriendsSum.toJsonArray(listData);
	}

	// 表格上 增长数据
	@ResponseBody
	@RequestMapping(value = "queryDataGridSum", produces = "application/json")
	public void queryDataGridSum(HttpServletRequest request,
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
		long totalCount = 0;
		List<ReportFriendsSum> list = new ArrayList<ReportFriendsSum>();
		if (platformUser != "" && platformUser != null) {
			WccPlatformUser platform = WccPlatformUser
					.findWccPlatformUserByIds(platformUser).get(0);
			if ("0".equals(dateType)) {
				if (platform != null) {
					list = ReportFriendsSum.findReportFriendsSumsByDateBetween(
							platform, startTime, endTime, firstResult,
							maxResults);
					List changeNum = ReportFriendsFrom
							.findReportFriendsFromsByDay(platform, startTime,
									endTime);
					for (ReportFriendsSum sum : list) {
						for (int i = 0; i < changeNum.size(); i++) {
							Object[] obj = (Object[]) changeNum.get(i);
							// System.out.println(obj[0]);增长
							// System.out.println(obj[1]);取消
							// System.out.println(obj[2]);日期 2015-03-02
							// 00:00:00.0
							if (obj[2]
									.toString()
									.substring(0, 10)
									.equals(DateUtil.getDayStr(sum.getRefDate()))) {
								sum.setNewNum(Integer.parseInt(obj[0]
										.toString()));
								sum.setCancelNum(Integer.parseInt(obj[1]
										.toString()));
								continue;
							}
						}
						sum.setDateStr(sum.getRefDateStr());
					}
					totalCount = ReportFriendsSum
							.findReportFriendsSumsByDateBetween(platform,
									startTime, endTime, -1, -1).size();
					pager.setRecordCount(totalCount);// 设置总记录数
					// 根据总记录数和每页的大小计算公有多少页
					if ((totalCount % maxResults) > 0) {
						totalPage = (int) ((totalCount / maxResults) + 1);
					} else {
						totalPage = (int) (totalCount / maxResults);
					}
				}
			} else {
				List<String> monthListStr = null;
				try {
					monthListStr = DateUtil.getMonthList(startTime, endTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (monthListStr != null) {
					for (String month : monthListStr) {
						String firstdate = DateUtil
								.getFirstMonthDayOfCurrentDay(month + "-01",
										DateUtil.YEAR_MONTH_DAY_FORMATER);
						String lastdate = DateUtil
								.getLastMonthDayOfCurrentDay(month + "-01",
										DateUtil.YEAR_MONTH_DAY_FORMATER);
						if (DateUtil.getDate(lastdate).getTime() > new Date()
								.getTime()) {
							lastdate = DateUtil.getDayStr(DateUtil
									.getBeforeDay(new Date(), -1));
						}
						ReportFriendsSum sum = ReportFriendsSum
								.findReportFriendsSumsByDate(platform.getId(),
										lastdate);
						sum.setDateStr(month);
						List changeNum = ReportFriendsFrom
								.findReportFriendsFromsByMonth(platform,
										firstdate, lastdate);
						for (int i = 0; i < changeNum.size(); i++) {
							Object[] obj = (Object[]) changeNum.get(i);
							if (obj[0] != null) {
								sum.setNewNum(Integer.parseInt(obj[0]
										.toString()));
							} else {
								sum.setNewNum(0);
							}
							if (obj[1] != null) {
								sum.setCancelNum(Integer.parseInt(obj[1]
										.toString()));
							} else {
								sum.setCancelNum(0);
							}
						}
						list.add(sum);
					}
					totalCount = monthListStr.size();
					pager.setRecordCount(totalCount);// 设置总记录数
					// 根据总记录数和每页的大小计算公有多少页
					if ((totalCount % maxResults) > 0) {
						totalPage = (int) ((totalCount / maxResults) + 1);
					} else {
						totalPage = (int) (totalCount / maxResults);
					}
				}
			}
		}
		if (list.size() != 0) {
			pager.setExhibitDatas(list);
		} else {
			pager.setExhibitDatas(null);
		}
		pager.setIsSuccess(true);
		pager.setPageCount(totalPage);
		pager.setRecordCount(totalCount);// 设置总记录数
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
	 * 
	 * 粉丝来源报表数据
	 * 
	 * @param request
	 * @param platform
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "saveFriendsFromsFromWx", produces = "text/html")
	@ResponseBody
	public String saveFriendsFromsFromWx(HttpServletRequest request,
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
				String url = "https://api.weixin.qq.com/datacube/getusersummary?access_token=";
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
						if (Integer.parseInt(data2.getString("user_source")) == 30) {
							int storeCount = WccFriend
									.findFriendsByPlatFromAndDateAndFromType(
											platform,
											data2.getString("ref_date"),
											WccFriendFormType.门店二维码);
							int imageCount = WccFriend
									.findFriendsByPlatFromAndDateAndFromType(
											platform,
											data2.getString("ref_date"),
											WccFriendFormType.图文二维码);
							int other = Integer.parseInt(data2
									.getString("new_user"))
									- storeCount
									- imageCount;
							// 保存门店二维码数量
							ReportFriendsFrom storeNew = new ReportFriendsFrom();
							storeNew.setRefDate(DateUtil.getDate(data2
									.getString("ref_date")));
							storeNew.setUserSource(931);
							storeNew.setNewUser(storeCount);
							storeNew.setPlatformUser(platform.getId());
							storeNew.persist();
							// 保存图文二维码数量
							ReportFriendsFrom imageNew = new ReportFriendsFrom();
							imageNew.setRefDate(DateUtil.getDate(data2
									.getString("ref_date")));
							imageNew.setUserSource(932);
							imageNew.setNewUser(imageCount);
							imageNew.setPlatformUser(platform.getId());
							imageNew.persist();
							// 保存门店二维码数量
							ReportFriendsFrom otherNew = new ReportFriendsFrom();
							otherNew.setRefDate(DateUtil.getDate(data2
									.getString("ref_date")));
							otherNew.setUserSource(933);
							otherNew.setNewUser(other);
							otherNew.setCancelUser(Integer.parseInt(data2
									.getString("cancel_user")));
							otherNew.setPlatformUser(platform.getId());
							otherNew.persist();
						} else if (Integer.parseInt(data2
								.getString("user_source")) == 35
								|| Integer.parseInt(data2
										.getString("user_source")) == 39) {
							List<ReportFriendsFrom> formList = ReportFriendsFrom
									.findReportFriendsFromsByDateAndType(
											platform,
											data2.getString("ref_date"), 936);
							if (formList.size() == 0) {// 新建
								ReportFriendsFrom friendsFrom = new ReportFriendsFrom();
								friendsFrom.setRefDate(DateUtil.getDate(data2
										.getString("ref_date")));
								friendsFrom.setUserSource(936);
								friendsFrom.setNewUser(Integer.parseInt(data2
										.getString("new_user")));
								friendsFrom.setCancelUser(Integer
										.parseInt(data2
												.getString("cancel_user")));
								friendsFrom.setPlatformUser(platform.getId());
								friendsFrom.persist();
							} else {// 修改
								ReportFriendsFrom friendsFrom = formList.get(0);
								friendsFrom.setNewUser(friendsFrom.getNewUser()
										+ Integer.parseInt(data2
												.getString("new_user")));
								friendsFrom.setCancelUser(friendsFrom
										.getCancelUser()
										+ Integer.parseInt(data2
												.getString("cancel_user")));
								friendsFrom.merge();
							}
						} else {
							ReportFriendsFrom friendsFrom = new ReportFriendsFrom();
							friendsFrom.setRefDate(DateUtil.getDate(data2
									.getString("ref_date")));
							friendsFrom.setUserSource(Integer.parseInt(data2
									.getString("user_source")));
							friendsFrom.setNewUser(Integer.parseInt(data2
									.getString("new_user")));
							friendsFrom.setCancelUser(Integer.parseInt(data2
									.getString("cancel_user")));
							friendsFrom.setPlatformUser(platform.getId());
							friendsFrom.persist();
						}
					}
					flag = "1";
				}
			}
		}
		return flag;
	}

	@RequestMapping(value = "queryFriendsFrom", produces = "text/html")
	@ResponseBody
	public String queryFriendsFrom(HttpServletRequest request,
			@RequestParam(value = "platform") WccPlatformUser platform,
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime,
			@RequestParam(value = "dateType") Integer dateType) {
		List<ReportFriendsFrom> listData = new ArrayList<ReportFriendsFrom>();
		if (dateType == 0) {
			listData = showDate(platform, startTime, endTime);
		} else if (dateType == 1) {
			listData = showMonthDate(platform, startTime, endTime);
		}
		return ReportFriendsFrom.toJsonArray(listData);
	}

	// 表格上 来源数据
	@ResponseBody
	@RequestMapping(value = "queryDataGridFrom", produces = "application/json")
	public void queryDataGridFrom(HttpServletRequest request,
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
		List<ReportFriendsFrom> list = null;
		List<ReportFriendsFrom> listdata = new ArrayList<ReportFriendsFrom>();
		if (platformUser != null && platformUser != "") {
			WccPlatformUser platform = WccPlatformUser
					.findWccPlatformUserByIds(platformUser).get(0);
			if ("0".equals(dateType)) {// 按日查询
				if (platform != null) {
					list = showDate(platform, startTime, endTime);
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
			} else {// 按月查询
				list = showMonthDate(platform, startTime, endTime);
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

	/**
	 * 粉丝增长来源报表List(日)
	 * 
	 * @param platform
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private List<ReportFriendsFrom> showDate(WccPlatformUser platform,
			String startTime, String endTime) {
		List<ReportFriendsFrom> listData = new ArrayList<ReportFriendsFrom>();
		List<ReportFriendsFrom> list = ReportFriendsFrom
				.findReportFriendsFromsByDateBetween(platform, startTime,
						endTime, -1, -1);
		List<String> dayListStr = DateUtil.getDateList(startTime, endTime,
				Calendar.DAY_OF_YEAR);
		for (String date : dayListStr) {
			ReportFriendsFrom from = new ReportFriendsFrom();
			from.setDateStr(date);
			from.setPlatformUser(platform.getId());
			for (ReportFriendsFrom friendsFrom : list) {
				if (date.equals(DateUtil.getDayStr(friendsFrom.getRefDate()))) {
					// 0代表其他 30代表扫二维码(931门店二维码、932图文二维码、933其它二维码) 17代表名片分享
					// 936公众号搜索(35代表搜号码（即微信添加朋友页的搜索） 39代表查询微信公众帐号) 43代表图文页右上角菜单
					switch (friendsFrom.getUserSource()) {
					case 0:
						from.setOtherCount(friendsFrom.getNewUser());
						break;
					case 931:
						from.setStoreCount(friendsFrom.getNewUser());
						break;
					case 932:
						from.setImageCount(friendsFrom.getNewUser());
						break;
					case 933:
						from.setOther2Count(friendsFrom.getNewUser());
						break;
					case 17:
						from.setShareCount(friendsFrom.getNewUser());
						break;
					case 936:
						from.setFindNumCount(friendsFrom.getNewUser());
						break;
					case 43:
						from.setMenuCount(friendsFrom.getNewUser());
						break;
					}
				}
			}
			listData.add(from);
		}
		return listData;
	}

	/**
	 * 粉丝增长来源报表List(月)
	 * 
	 * @param platform
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private List<ReportFriendsFrom> showMonthDate(WccPlatformUser platform,
			String startTime, String endTime) {
		List<ReportFriendsFrom> listData = new ArrayList<ReportFriendsFrom>();
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
				if (DateUtil.getDate(lastdate).getTime() > new Date().getTime()) {
					lastdate = DateUtil.getDayStr(DateUtil.getBeforeDay(
							new Date(), -1));
				}
				List list = ReportFriendsFrom.findReportFriendsFromsByDate(
						platform, firstdate, lastdate);
				ReportFriendsFrom from = new ReportFriendsFrom();
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[]) list.get(i);
					switch (Integer.parseInt(obj[1].toString())) {
					case 0:
						from.setOtherCount(Integer.parseInt(obj[0].toString()));
						break;
					case 931:
						from.setStoreCount(Integer.parseInt(obj[0].toString()));
						break;
					case 932:
						from.setImageCount(Integer.parseInt(obj[0].toString()));
						break;
					case 933:
						from.setOther2Count(Integer.parseInt(obj[0].toString()));
						break;
					case 17:
						from.setShareCount(Integer.parseInt(obj[0].toString()));
						break;
					case 936:
						from.setFindNumCount(Integer.parseInt(obj[0].toString()));
						break;
					case 43:
						from.setMenuCount(Integer.parseInt(obj[0].toString()));
						break;
					}
				}
				from.setDateStr(month);
				from.setPlatformUser(platform.getId());
				listData.add(from);
			}
		}
		return listData;

	}

	@RequestMapping(value = "exportExcelSum")
	public void exportExcelSum(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "platform") WccPlatformUser platform,
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime,
			@RequestParam(value = "dateType") Integer dateType)
			throws IOException {

		List<ReportFriendsSum> list = new ArrayList<ReportFriendsSum>();
		List<Map> listData = new ArrayList<Map>();
		if (dateType == 0) {
			if (platform != null) {
				list = ReportFriendsSum.findReportFriendsSumsByDateBetween(
						platform, startTime, endTime, -1, -1);
				List changeNum = ReportFriendsFrom.findReportFriendsFromsByDay(
						platform, startTime, endTime);
				for (ReportFriendsSum sum : list) {
					for (int i = 0; i < changeNum.size(); i++) {
						Object[] obj = (Object[]) changeNum.get(i);
						// System.out.println(obj[0]);增长
						// System.out.println(obj[1]);取消
						// System.out.println(obj[2]);日期 2015-03-02 00:00:00.0
						if (obj[2].toString().substring(0, 10)
								.equals(DateUtil.getDayStr(sum.getRefDate()))) {
							sum.setNewNum(Integer.parseInt(obj[0].toString()));
							sum.setCancelNum(Integer.parseInt(obj[1].toString()));
							continue;
						}
					}
					sum.setDateStr(sum.getRefDateStr());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("platform_user_name", sum.getPlatformUserName());
					map.put("date_str", sum.getDateStr());
					map.put("cumulate_user", sum.getCumulateUser());
					map.put("new_num", sum.getNewNum());
					map.put("cancel_num", sum.getCancelNum());
					listData.add(map);
				}
			}
		}
		if (dateType == 1) {
			List<String> monthListStr = null;
			try {
				monthListStr = DateUtil.getMonthList(startTime, endTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (monthListStr != null) {
				for (String month : monthListStr) {
					String firstdate = DateUtil.getFirstMonthDayOfCurrentDay(
							month + "-01", DateUtil.YEAR_MONTH_DAY_FORMATER);
					String lastdate = DateUtil.getLastMonthDayOfCurrentDay(
							month + "-01", DateUtil.YEAR_MONTH_DAY_FORMATER);
					if (DateUtil.getDate(lastdate).getTime() > new Date()
							.getTime()) {
						lastdate = DateUtil.getDayStr(DateUtil.getBeforeDay(
								new Date(), -1));
					}
					ReportFriendsSum sum = ReportFriendsSum
							.findReportFriendsSumsByDate(platform.getId(),
									lastdate);
					sum.setDateStr(month);
					List changeNum = ReportFriendsFrom
							.findReportFriendsFromsByMonth(platform, firstdate,
									lastdate);
					for (int i = 0; i < changeNum.size(); i++) {
						Object[] obj = (Object[]) changeNum.get(i);
						if (obj[0] != null) {
							sum.setNewNum(Integer.parseInt(obj[0].toString()));
						} else {
							sum.setNewNum(0);
						}
						if (obj[1] != null) {
							sum.setCancelNum(Integer.parseInt(obj[1].toString()));
						} else {
							sum.setCancelNum(0);
						}
					}
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("platform_user_name", sum.getPlatformUserName());
					map.put("date_str", sum.getDateStr());
					map.put("cumulate_user", sum.getCumulateUser());
					map.put("new_num", sum.getNewNum());
					map.put("cancel_num", sum.getCancelNum());
					listData.add(map);
				}
			}
		}

		// if (listData.size() > 0) {
		String[] title = { "公众平台", "日期", "粉丝数", "新增粉丝数", "粉丝取消关注数" };
		String[] colums = { "platform_user_name", "date_str", "cumulate_user",
				"new_num", "cancel_num" };
		OutputStream os = null;
		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("粉丝增长报表").getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			os = response.getOutputStream();

			map.put("粉丝增长报表", listData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel(title, colums, map, os);
	}

	@RequestMapping(value = "exportExcelFrom")
	public void exportExcelFrom(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "platform") WccPlatformUser platform,
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime,
			@RequestParam(value = "dateType") Integer dateType)
			throws IOException {

		List<ReportFriendsFrom> list = new ArrayList<ReportFriendsFrom>();
		List<Map> listData = new ArrayList<Map>();
		if (dateType == 0) {// 按日查询
			list = showDate(platform, startTime, endTime);
			for (ReportFriendsFrom from : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("platformUserName", from.getPlatformUserName());
				map.put("dateStr", from.getDateStr());
				map.put("findNumCount", from.getFindNumCount());
				map.put("storeCount", from.getStoreCount());
				map.put("imageCount", from.getImageCount());
				map.put("shareCount", from.getShareCount());
				map.put("menuCount", from.getMenuCount());
				map.put("otherCount", from.getOtherCount());
				listData.add(map);
			}
		} else {// 按月查询
			list = showMonthDate(platform, startTime, endTime);
			for (ReportFriendsFrom from : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("platformUserName", from.getPlatformUserName());
				map.put("dateStr", from.getDateStr());
				map.put("findNumCount", from.getFindNumCount());
				map.put("storeCount", from.getStoreCount());
				map.put("imageCount", from.getImageCount());
				map.put("shareCount", from.getShareCount());
				map.put("menuCount", from.getMenuCount());
				map.put("otherCount", from.getOtherCount());
				listData.add(map);
			}
		}

		String[] title = { "公众平台", "日期", "公众号搜索", "门店二维码", "图文二维码", "名片分享",
				"图文右上角菜单", "其它" };
		String[] colums = { "platformUserName", "dateStr", "findNumCount",
				"storeCount", "imageCount", "shareCount", "menuCount",
				"otherCount" };
		OutputStream os = null;
		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("粉丝增长来源").getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			os = response.getOutputStream();

			map.put("粉丝增长来源", listData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel(title, colums, map, os);
	}

}

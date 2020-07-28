package com.nineclient.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nineclient.constant.WccFriendFormType;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.wccreport.ReportContentChannel;
import com.nineclient.model.wccreport.ReportFriendsFrom;
import com.nineclient.model.wccreport.ReportFriendsSum;
import com.nineclient.model.wccreport.ReportMessage;
import com.nineclient.model.wccreport.ReportMessageDistribute;
import com.nineclient.model.wccreport.WccMassPicText;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.HttpClientUtil;
import com.nineclient.utils.TokenManager;

/**
 * @author Brian
 *
 */
@Component
public class WccReportTask {
	private static final Logger logger = Logger.getLogger(WccReportTask.class);
	public static WxMpService wxMpService = new WxMpServiceImpl();

	@Async
//	@Scheduled(cron = "* * 0 * * *")// 每日凌晨任务
	 @Scheduled(cron = "* 48 18 * * *")
//	 @Scheduled(cron = "0/30 * * * * ?")
	// 每天0时30分点运行
	private void getMassPicTextDataFromWx() {
		logger.warn(getDateStr(1) + "=====>>>>>>>>微信数据采集开始");
		logger.info(">>>>>>>>图文群发总数据采集开始");
//		for (int i = 7; i > 0; i--) {// 从7天前开始获取数据
			massTotleInfo(getDateStr(1));
//		}
		logger.info("<<<<<<<<<<图文群发总数据采集结束");

		logger.info(">>>>>>>>粉丝来源数据采集开始");
		saveFriendsFromsFromWx(getDateStr(1), getDateStr(1));
		logger.info("<<<<<<<<<<粉丝来源数据采集结束");

		logger.info(">>>>>>>>粉丝增长数据采集开始");
		saveFriendsSumFromWx(getDateStr(1), getDateStr(1));
		logger.info("<<<<<<<<<<粉丝增长数据采集结束");

		logger.info(">>>>>>>>转发渠道数据采集开始");
		saveContentChannelFromWx(getDateStr(1), getDateStr(1));
		logger.info("<<<<<<<<<<转发渠道数据采集结束");

		logger.info(">>>>>>>>群发每日数据采集开始");
//		for (int i = 7; i > 0; i--) {// 从7天前开始获取数据
			saveContentTopFromWx(getDateStr(1));
//		}
		logger.info("<<<<<<<<<<群发每日数据采集结束");

		logger.info(">>>>>>>>互动时段数据采集开始");
//		for (int i = 6; i > 0; i--) {// 从7天前开始获取数据
			saveHourRecordMessageFromWx(getDateStr(1));
//		}
		logger.info("<<<<<<<<<<互动时段数据采集结束");

		logger.info(">>>>>>>>互动每天数据采集开始");
		saveDayRecordMessageFromWx(getDateStr(1), getDateStr(1));
		logger.info("<<<<<<<<<<互动每天数据采集结束");
		
		logger.info(">>>>>>>>时间分布每天数据采集开始");
		saveDistributeFromWx(getDateStr(1), getDateStr(1));
		logger.info("<<<<<<<<<<时间分布每天数据采集结束");
		
//		acctoken.clear();
		logger.warn("<<<<<<<<<<========微信数据采集结束");
	}

	/**
	 * 获取几天前日期（年-月-日）
	 * 
	 * @return
	 */
	private static String getDateStr(int num) {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(new Date()
				.getTime() - 24 * 60 * 60 * 1000 * num));
	}

	/**
	 * 获取图文群发总数据(可优化--线程)
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private String massTotleInfo(String time) {
		System.out.println("时间===========" + time);
		String startTime = time;
		String endTime = time;
		System.out.println("获取图文群发总数据===================");
		String str = "";
//		List<WccPlatformUser> plats = WccPlatformUser.findAllWccPlatformUsers(null, null);
		WccPlatformUser wccPlatformUser = WccPlatformUser.findWccPlatformUser(Long.parseLong("4"));
//		System.out.println("公众平台大小==========" + plats.size());
//		for (WccPlatformUser wccPlatformUser : plats) {
			System.setProperty("https.protocols", "SSLv3,SSLv2Hello");
			WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
			System.out.println("公众平台名称=========="
					+ wccPlatformUser.getAccount());
			config.setAppId(wccPlatformUser.getAppId().trim());
			config.setSecret(wccPlatformUser.getAppSecret().trim());
			wxMpService.setWxMpConfigStorage(config);
		
//			if (null != wccPlatformUser.getPlatformId()
//					&& null == acctoken.get(wccPlatformUser.getPlatformId())) {
//				// 获取accessToken
//				String accessTok = null;
//				try {
//					accessTok=wxMpService.getAccessToken();
//					logger.warn(wccPlatformUser.getAccount() + "获取token为====="
//							+ accessTok);
//					// accessTok = wxMpService.getAccessToken();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				acctoken.put(wccPlatformUser.getPlatformId(), accessTok);
//			}
			String accessToken=null;
			try {
				accessToken = wxMpService.getAccessToken();
			} catch (WxErrorException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			System.out.println("accessToken========================="
					+ accessToken);
			if (!"".equals(accessToken)) {
				String json = "";
				if (startTime != null && endTime != null) {
					json = "{\"begin_date\": \"" + startTime
							+ "\",\"end_date\": \"" + endTime + "\"}";
				}
				String url = "https://api.weixin.qq.com/datacube/getarticletotal?access_token=";
				String returnList = "";
				try {
					returnList = HttpClientUtil.doJsonPost(url + accessToken, json);
					System.out.println(json);
					System.out.println(returnList);
					if (returnList.contains("errcode")) {// 发生错误时{"errcode":45009,"errmsg":"api freq out of limit"}
						String accessTok =TokenManager.getOfficialToken(wccPlatformUser.getAppId().trim(),wccPlatformUser.getAppSecret().trim(),true);
						returnList = HttpClientUtil.doJsonPost(url + accessTok, json);
//						acctoken.put(wccPlatformUser.getPlatformId(), accessTok);
						if(returnList.contains("errcode")){
							logger.warn(wccPlatformUser.getAccount()
									+ "发生错误=======" + returnList);
//							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!"".equals(returnList)) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					JSONObject jsonObject = JSONObject.fromObject(returnList);
					JSONArray data = jsonObject.getJSONArray("list");
					for (int i = 0; i < data.size(); i++) {
						String s = data.getString(i);
						JSONObject data2 = JSONObject.fromObject(s);
						String ref_date = data2.getString("ref_date");// 发表日期
						String msgid = data2.getString("msgid");
						String title = data2.getString("title");
						JSONArray details = data2.getJSONArray("details");
						// 排重 (根据msgId、statDate、type)
						WccMassPicText mass = WccMassPicText
								.CheckWccMassByMsgIdStatTime(msgid, ref_date,
										null, 1);
						Date rdate = null;
						try {
							rdate = format.parse(ref_date);
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						if (null == mass) {
							mass = new WccMassPicText();
							mass.setRefDate(rdate);
							mass.setDelete(true);
							mass.setMsgid(msgid);
							mass.setTitle(title);
							mass.setType(1);
							mass.setPlatformUser(wccPlatformUser);
							mass.merge();
						}
						for (Object detail : details) {
							JSONObject detailStr = JSONObject.fromObject(detail
									.toString());
							String statDate = detailStr.getString("stat_date");
							int targetUser = detailStr.getInt("target_user");// 送达人数
							int int_page_read_user = detailStr
									.getInt("int_page_read_user");// 阅读人数
							int int_page_read_count = detailStr
									.getInt("int_page_read_count");// 阅读次数
							int ori_page_read_user = detailStr
									.getInt("ori_page_read_user");// 原文页的阅读人数
							int ori_page_read_count = detailStr
									.getInt("ori_page_read_count");// 原文页的阅读次数
							int share_user = detailStr.getInt("share_user");// 分享的人数
							int share_count = detailStr.getInt("share_count");// 分享的次数
							int add_to_fav_user = detailStr
									.getInt("add_to_fav_user");// 收藏的人数
							int add_to_fav_count = detailStr
									.getInt("add_to_fav_count");// 收藏的次数
							// 排重 (根据msgId、statDate、type)
							WccMassPicText picText = WccMassPicText
									.CheckWccMassByMsgIdStatTime(msgid, null,
											statDate, 2);
							Date sdate = null;
							try {
								sdate = format.parse(statDate);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							if (null == picText) {
								picText = new WccMassPicText();
								picText.setStatDate(sdate);
								picText.setDelete(true);
								picText.setMsgid(msgid);
								picText.setTitle(title);
								picText.setTarget_user(targetUser);
								picText.setIntPageReadUser(int_page_read_user);
								picText.setIntPageReadCount(int_page_read_count);
								picText.setOriPageReadUser(ori_page_read_user);
								picText.setOriPageReadCount(ori_page_read_count);
								picText.setShareUser(share_user);
								picText.setShareCount(share_count);
								picText.setAddToFavUser(add_to_fav_user);
								picText.setAddToFavCount(add_to_fav_count);
								picText.setType(2);
								picText.setMassId(WccMassPicText
										.CheckWccMassByMsgIdStatTime(msgid,
												ref_date, null, 1).getId());
								picText.setPlatformUser(wccPlatformUser);
								picText.merge();
							}
						}
					}
				}
			}

//		}
		return str;
	}

	public void saveFriendsSumFromWx(
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime) {
//		List<WccPlatformUser> plats = WccPlatformUser.findAllWccPlatformUsers(null, null);
		WccPlatformUser platform = WccPlatformUser.findWccPlatformUser(Long.parseLong("4"));

//		for (WccPlatformUser platform : plats) {
			if (platform != null) {
				WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
				config.setAppId(platform.getAppId().trim());
				config.setSecret(platform.getAppSecret().trim());
				wxMpService.setWxMpConfigStorage(config);
				
//				if (null != platform.getPlatformId()
//						&& null == acctoken.get(platform.getPlatformId())) {
//					// 获取accessToken
//					String accessTok = null;
//					try {
//						accessTok = wxMpService.getAccessToken();
//						logger.warn(platform.getAccount() + "获取token为====="
//								+ accessTok);
////						accessTok = wxMpService.getAccessToken();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					acctoken.put(platform.getPlatformId(), accessTok);
//				}
				String accessToken=null;
				try {
					accessToken = wxMpService.getAccessToken();
				} catch (WxErrorException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
						returnList = HttpClientUtil.doJsonPost(url + accessToken,
								json);
						System.out.println(platform.getAccount() + "==="
								+ accessToken);
						System.out.println(json);
						System.out.println(returnList);
						if (returnList.contains("errcode")) {// 发生错误时{"errcode":45009,"errmsg":"api freq out of limit"}
							String accessTok = TokenManager.getOfficialToken(platform.getAppId().trim(),platform.getAppSecret().trim(),true);
							returnList = HttpClientUtil.doJsonPost(url + accessTok, json);
//							acctoken.put(platform.getPlatformId(), accessTok);
							if(returnList.contains("errcode")){
								logger.warn(platform.getAccount()
										+ "发生错误=======" + returnList);
//								continue;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (!"".equals(returnList)) {
						JSONObject jsonObject = JSONObject
								.fromObject(returnList);
						JSONArray data = jsonObject.getJSONArray("list");
						for (int i = 0; i < data.size(); i++) {
							String s = data.getString(i);
							JSONObject data2 = JSONObject.fromObject(s);
							ReportFriendsSum friendsSum = ReportFriendsSum
									.findReportFriendsSumsByDate2(
											platform.getId(),
											data2.getString("ref_date"));
							if (friendsSum == null) {
								friendsSum = new ReportFriendsSum();
								friendsSum.setRefDate(DateUtil.getDate(data2
										.getString("ref_date")));
								friendsSum.setCumulateUser(Long.parseLong(data2
										.getString("cumulate_user")));
								friendsSum.setPlatformUser(platform.getId());
								friendsSum.persist();
							}
							// System.out.println(data2.getString("ref_date"));
							// System.out.println(data2.getString("cumulate_user"));
						}
					}
				}
			}
//		}
	}

	public void saveFriendsFromsFromWx(
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime) {
//		List<WccPlatformUser> plats = WccPlatformUser.findAllWccPlatformUsers(null, null);
		
		WccPlatformUser platform = WccPlatformUser.findWccPlatformUser(Long.parseLong("4"));

//		for (WccPlatformUser platform : plats) {
			if (platform != null) {
				WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
				config.setAppId(platform.getAppId().trim());
				config.setSecret(platform.getAppSecret().trim());
				wxMpService.setWxMpConfigStorage(config);
			
//				if (null != platform.getPlatformId()
//						&& null == acctoken.get(platform.getPlatformId())) {
//					// 获取accessToken
//					String accessTok = null;
//					try {
//						accessTok =wxMpService.getAccessToken();
//						logger.warn(platform.getAccount() + "获取token为====="
//								+ accessTok);
////						accessTok = wxMpService.getAccessToken();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					acctoken.put(platform.getPlatformId(), accessTok);
//				}
				String accessToken=null;
				try {
					accessToken = wxMpService.getAccessToken();
				} catch (WxErrorException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
						returnList = HttpClientUtil.doJsonPost(url + accessToken,
								json);
						System.out.println(json);
						System.out.println(returnList);
						if (returnList.contains("errcode")) {// 发生错误时{"errcode":45009,"errmsg":"api freq out of limit"}
							String accessTok = TokenManager.getOfficialToken(platform.getAppId().trim(),platform.getAppSecret().trim(),true);
							returnList = HttpClientUtil.doJsonPost(url + accessTok, json);
//							acctoken.put(platform.getPlatformId(), accessTok);
							if(returnList.contains("errcode")){
								logger.warn(platform.getAccount()
										+ "发生错误=======" + returnList);
//								continue;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (!"".equals(returnList)) {
						JSONObject jsonObject = JSONObject
								.fromObject(returnList);
						JSONArray data = jsonObject.getJSONArray("list");
						for (int i = 0; i < data.size(); i++) {
							String s = data.getString(i);
							JSONObject data2 = JSONObject.fromObject(s);
							if (Integer
									.parseInt(data2.getString("user_source")) == 0) {
								int storeCount = WccFriend
										.findFriendsByPlatFromAndDateAndFromType2(
												platform,
												data2.getString("ref_date"),
												WccFriendFormType.门店二维码);
								int imageCount = WccFriend
										.findFriendsByPlatFromAndDateAndFromType2(
												platform,
												data2.getString("ref_date"),
												WccFriendFormType.图文二维码);
								int other = Integer.parseInt(data2
										.getString("new_user"))
										- storeCount
										- imageCount;
								// 保存门店二维码数量
								ReportFriendsFrom storeNew = ReportFriendsFrom
										.findReportFriendsFromsByDateAndType2(
												platform,
												data2.getString("ref_date"),
												931);
								if (storeNew == null) {
									if (storeCount != 0) {
										storeNew = new ReportFriendsFrom();
										storeNew.setRefDate(DateUtil
												.getDate(data2
														.getString("ref_date")));
										storeNew.setUserSource(931);
										storeNew.setNewUser(storeCount);
										storeNew.setPlatformUser(platform
												.getId());
										storeNew.setInsertTime(new Date());
										storeNew.persist();
									}
								}
								// 保存图文二维码数量
								ReportFriendsFrom imageNew = ReportFriendsFrom
										.findReportFriendsFromsByDateAndType2(
												platform,
												data2.getString("ref_date"),
												932);
								if (imageNew == null) {
									if (imageCount != 0) {
										imageNew = new ReportFriendsFrom();
										imageNew.setRefDate(DateUtil
												.getDate(data2
														.getString("ref_date")));
										imageNew.setUserSource(932);
										imageNew.setNewUser(imageCount);
										imageNew.setInsertTime(new Date());
										imageNew.setPlatformUser(platform
												.getId());
										imageNew.persist();
									}
								}
								// 保存门店二维码数量
								ReportFriendsFrom otherNew = ReportFriendsFrom
										.findReportFriendsFromsByDateAndType2(
												platform,
												data2.getString("ref_date"), 0);
								if (otherNew == null) {
									if (other != 0) {
										otherNew = new ReportFriendsFrom();
										otherNew.setRefDate(DateUtil
												.getDate(data2
														.getString("ref_date")));
										otherNew.setUserSource(0);
										otherNew.setNewUser(other);
										otherNew.setInsertTime(new Date());
										otherNew.setCancelUser(Integer.parseInt(data2
												.getString("cancel_user")));
										otherNew.setPlatformUser(platform
												.getId());
										otherNew.persist();
									}
								}
							} else if (Integer.parseInt(data2
									.getString("user_source")) == 35
									|| Integer.parseInt(data2
											.getString("user_source")) == 39
									|| Integer.parseInt(data2
											.getString("user_source")) == 3) {
								ReportFriendsFrom friendsFrom = ReportFriendsFrom
										.findReportFriendsFromsByDateAndType2(
												platform,
												data2.getString("ref_date"),
												936);
								if (friendsFrom == null) {// 新建
									friendsFrom = new ReportFriendsFrom();
									friendsFrom.setRefDate(DateUtil
											.getDate(data2
													.getString("ref_date")));
									friendsFrom.setUserSource(936);
									friendsFrom.setNewUser(Integer
											.parseInt(data2
													.getString("new_user")));
									friendsFrom.setCancelUser(Integer
											.parseInt(data2
													.getString("cancel_user")));
									friendsFrom.setPlatformUser(platform
											.getId());
									friendsFrom.setInsertTime(new Date());
									friendsFrom.persist();
								} else {// 修改
									if(new Date().getTime()-friendsFrom.getInsertTime().getTime()<(1000*3600)){
									friendsFrom.setNewUser(friendsFrom
											.getNewUser()
											+ Integer.parseInt(data2
													.getString("new_user")));
									friendsFrom.setCancelUser(friendsFrom
											.getCancelUser()
											+ Integer.parseInt(data2
													.getString("cancel_user")));
									friendsFrom.merge();
									}
								}
							} else {
								ReportFriendsFrom friendsFrom = ReportFriendsFrom
										.findReportFriendsFromsByDateAndType2(
												platform,
												data2.getString("ref_date"),
												Integer.parseInt(data2
														.getString("user_source")));
								if (friendsFrom == null) {
									friendsFrom = new ReportFriendsFrom();
									friendsFrom.setRefDate(DateUtil
											.getDate(data2
													.getString("ref_date")));
									friendsFrom.setUserSource(Integer
											.parseInt(data2
													.getString("user_source")));
									friendsFrom.setNewUser(Integer
											.parseInt(data2
													.getString("new_user")));
									friendsFrom.setCancelUser(Integer
											.parseInt(data2
													.getString("cancel_user")));
									friendsFrom.setPlatformUser(platform
											.getId());
									friendsFrom.setInsertTime(new Date());
									friendsFrom.persist();
								}
							}
						}
					}
				}
			}
//		}
	}

	public void saveContentChannelFromWx(
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime) {
//		List<WccPlatformUser> plats = WccPlatformUser.findAllWccPlatformUsers(null, null);
		WccPlatformUser platform = WccPlatformUser.findWccPlatformUser(Long.parseLong("4"));

//		for (WccPlatformUser platform : plats) {
			if (platform != null) {
				WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
				config.setAppId(platform.getAppId().trim());
				config.setSecret(platform.getAppSecret().trim());
				wxMpService.setWxMpConfigStorage(config);
				
//				if (null != platform.getPlatformId()
//						&& null == acctoken.get(platform.getPlatformId())) {
//					// 获取accessToken
//					String accessTok = null;
//					try {
//						accessTok =wxMpService.getAccessToken();
//						logger.warn(platform.getAccount() + "获取token为====="
//								+ accessTok);
////						accessTok = wxMpService.getAccessToken();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					acctoken.put(platform.getPlatformId(), accessTok);
//				}
				String accessToken =null;
				try {
					accessToken = wxMpService.getAccessToken();
				} catch (WxErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.info("=========" + platform.getAccount()
							+ "获取转发渠道获取accessToken失败");
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
						returnList = HttpClientUtil.doJsonPost(url + accessToken,
								json);
						System.out.println(json);
						System.out.println(returnList);
						if (returnList.contains("errcode")) {// 发生错误时{"errcode":45009,"errmsg":"api freq out of limit"}
							String accessTok =TokenManager.getOfficialToken(platform.getAppId().trim(),platform.getAppSecret().trim(),true);
							returnList = HttpClientUtil.doJsonPost(url + accessTok, json);
//							acctoken.put(platform.getPlatformId(), accessTok);
							if(returnList.contains("errcode")){
								logger.warn(platform.getAccount()
										+ "发生错误=======" + returnList);
//								continue;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (!"".equals(returnList)) {
						JSONObject jsonObject = JSONObject
								.fromObject(returnList);
						JSONArray data = jsonObject.getJSONArray("list");
						for (int i = 0; i < data.size(); i++) {
							String s = data.getString(i);
							JSONObject data2 = JSONObject.fromObject(s);
							ReportContentChannel contentChannel = ReportContentChannel
									.findReportContentChannelsByDate2(platform
											.getId(), data2
											.getString("ref_date"), Integer
											.parseInt(data2
													.getString("share_scene")));
							if (contentChannel == null) {
								contentChannel = new ReportContentChannel();
								contentChannel.setRefDate(DateUtil
										.getDate(data2.getString("ref_date")));
								contentChannel.setShareScene(Integer
										.parseInt(data2
												.getString("share_scene")));
								contentChannel.setShareCount(Integer
										.parseInt(data2
												.getString("share_count")));
								contentChannel
										.setShareUser(Integer.parseInt(data2
												.getString("share_user")));
								contentChannel
										.setPlatformUser(platform.getId());
								contentChannel.persist();
							}
						}
					}
				}
			}
//		}
	}

	public void saveContentTopFromWx(@RequestParam(value = "time") String time) {
//		List<WccPlatformUser> plats = WccPlatformUser.findAllWccPlatformUsers(null, null);
		WccPlatformUser platform = WccPlatformUser.findWccPlatformUser(Long.parseLong("4"));

//		for (WccPlatformUser platform : plats) {
			if (platform != null) {
				WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
				config.setAppId(platform.getAppId().trim());
				config.setSecret(platform.getAppSecret().trim());
				wxMpService.setWxMpConfigStorage(config);
				
//				if (null != platform.getPlatformId()
//						&& null == acctoken.get(platform.getPlatformId())) {
//					// 获取accessToken
//					String accessTok = null;
//					try {
//						accessTok =wxMpService.getAccessToken();
//						logger.warn(platform.getAccount() + "获取token为====="
//								+ accessTok);
////						accessTok = wxMpService.getAccessToken();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					acctoken.put(platform.getPlatformId(), accessTok);
//				}
				String accessToken=null;
				try {
					accessToken = wxMpService.getAccessToken();
				} catch (WxErrorException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					accessToken = wxMpService.getAccessToken();
				} catch (WxErrorException e) {
					e.printStackTrace();
					logger.info("=========" + platform.getAccount()
							+ "获取群发每日数据获取accessToken失败");
				}
				if (!"".equals(accessToken)) {
					String json = "";
					if (time != null) {
						json = "{\"begin_date\": \"" + time
								+ "\",\"end_date\": \"" + time + "\"}";
					}
					String url = "https://api.weixin.qq.com/datacube/getarticlesummary?access_token=";
					String returnList = "";
					try {
						returnList = HttpClientUtil.doJsonPost(url + accessToken,
								json);
						System.out.println(json);
						System.out.println(returnList);
						if (returnList.contains("errcode")) {// 发生错误时{"errcode":45009,"errmsg":"api freq out of limit"}
							String accessTok = TokenManager.getOfficialToken(platform.getAppId().trim(),platform.getAppSecret().trim(),true);
							if(returnList.contains("errcode")){
								logger.warn(platform.getAccount()
										+ "发生错误=======" + returnList);
//								continue;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (!"".equals(returnList)) {
						JSONObject jsonObject = JSONObject
								.fromObject(returnList);
						JSONArray data = jsonObject.getJSONArray("list");
						for (int i = 0; i < data.size(); i++) {
							String s = data.getString(i);
							JSONObject data2 = JSONObject.fromObject(s);

							WccMassPicText mass = WccMassPicText
									.findWccMassPicTextByDate2(
											platform.getId(), time,
											data2.getString("title"));
							if (mass == null) {
								mass = new WccMassPicText();
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
								mass.setAddToFavUser(data2
										.getInt("add_to_fav_user"));
								mass.setAddToFavCount(data2
										.getInt("add_to_fav_count"));
								mass.setType(3);
								mass.setPlatformUser(platform);
								mass.merge();
							}
						}
					}
				}
			}
//		}
	}

	public static String getToken(String apiurl, String appid, String secret) {
		String turl = String.format(
				"%s?grant_type=client_credential&appid=%s&secret=%s", apiurl,
				appid, secret);
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(turl);
		JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
		String result = null;
		try {
			HttpResponse res = client.execute(get);
			String responseContent = null; // 响应内容
			HttpEntity entity = res.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
			JsonObject json = jsonparer.parse(responseContent)
					.getAsJsonObject();
			// 将json字符串转换为json对象
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (json.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
				} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
					result = json.get("access_token").getAsString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接 ,释放资源
			client.getConnectionManager().shutdown();
			return result;
		}
	}

	/**
	 * 
	 * 按天获取小时互动记录数据
	 * 
	 * @param time
	 */
	public void saveHourRecordMessageFromWx(
			@RequestParam(value = "time") String time) {
		String startTime = time;
		String endTime = time;
//		List<WccPlatformUser> plats = WccPlatformUser.findAllWccPlatformUsers(null, null);
		WccPlatformUser wccPlatformUser = WccPlatformUser.findWccPlatformUser(Long.parseLong("4"));

//		for (WccPlatformUser wccPlatformUser : plats) {
			System.setProperty("https.protocols", "SSLv3,SSLv2Hello");
			WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
			System.out.println("公众平台名称=========="
					+ wccPlatformUser.getAccount());
			config.setAppId(wccPlatformUser.getAppId().trim());
			config.setSecret(wccPlatformUser.getAppSecret().trim());
			wxMpService.setWxMpConfigStorage(config);
			
//			if (null != wccPlatformUser.getPlatformId()
//					&& null == acctoken.get(wccPlatformUser.getPlatformId())) {
//				// 获取accessToken
//				String accessTok = null;
//				try {
//					accessTok =wxMpService.getAccessToken();
//					logger.warn(wccPlatformUser.getAccount() + "获取token为====="
//							+ accessTok);
//					// accessTok = wxMpService.getAccessToken();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				acctoken.put(wccPlatformUser.getPlatformId(), accessTok);
//			}
			String accessToken=null;
			try {
				accessToken = wxMpService.getAccessToken();
			} catch (WxErrorException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			System.out.println("accessToken========================="
					+ accessToken);
			if (!"".equals(accessToken)) {
				String json = "";
				if (startTime != null && endTime != null) {
					json = "{\"begin_date\": \"" + startTime
							+ "\",\"end_date\": \"" + endTime + "\"}";
				}
				String url = "https://api.weixin.qq.com/datacube/getupstreammsghour?access_token=";
				String returnList = "";
				try {
					returnList = HttpClientUtil.doJsonPost(url + accessToken, json);
					System.out.println(json);
					System.out.println(returnList);
					if (returnList.contains("errcode")) {// 发生错误时{"errcode":45009,"errmsg":"api freq out of limit"}
						String accessTok =TokenManager.getOfficialToken(wccPlatformUser.getAppId().trim(),wccPlatformUser.getAppSecret().trim(),true);
						returnList = HttpClientUtil.doJsonPost(url + accessTok, json);
//						acctoken.put(wccPlatformUser.getPlatformId(), accessTok);
						if(returnList.contains("errcode")){
							logger.warn(wccPlatformUser.getAccount()
									+ "发生错误=======" + returnList);
//							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!"".equals(returnList)) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					JSONObject jsonObject = JSONObject.fromObject(returnList);
					JSONArray data = jsonObject.getJSONArray("list");
					for (int i = 0; i < data.size(); i++) {
						String s = data.getString(i);
						JSONObject data2 = JSONObject.fromObject(s);
						String ref_date = data2.getString("ref_date");// 发表日期
						String ref_hour = data2.getString("ref_hour");
						ref_hour = ref_hour.substring(0, ref_hour.length() - 2);
						String msg_type = data2.getString("msg_type");
						String msg_user = data2.getString("msg_user");
						String msg_count = data2.getString("msg_count");
						// 排重 (根据msgId、statDate、type)
						ReportMessage mass = ReportMessage.checkReportMessage(
								wccPlatformUser.getId(), ref_date, ref_hour,
								msg_type, 0);
						Date rdate = null;
						try {
							rdate = format.parse(ref_date);
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						if (null == mass) {
							mass = new ReportMessage();
							mass.setRefDate(rdate);
							mass.setType(0);
							mass.setPlatformUser(wccPlatformUser.getId());
							mass.setMsgCount(Integer.parseInt(msg_count));
							mass.setMsgType(Integer.parseInt(msg_type));
							mass.setMsgUser(Integer.parseInt(msg_user));
							mass.setRefHour(ref_hour);
							mass.persist();
						}
					}
				}
			}
//		}
	}
	public void saveDayRecordMessageFromWx(
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime) {
//		List<WccPlatformUser> plats = WccPlatformUser.findAllWccPlatformUsers(null, null);
		WccPlatformUser wccPlatformUser = WccPlatformUser.findWccPlatformUser(Long.parseLong("4"));

//		for (WccPlatformUser wccPlatformUser : plats) {
			System.setProperty("https.protocols", "SSLv3,SSLv2Hello");
			WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
			System.out.println("公众平台名称=========="
					+ wccPlatformUser.getAccount());
			config.setAppId(wccPlatformUser.getAppId().trim());
			config.setSecret(wccPlatformUser.getAppSecret().trim());
			wxMpService.setWxMpConfigStorage(config);
			
//			if (null != wccPlatformUser.getPlatformId()
//					&& null == acctoken.get(wccPlatformUser.getPlatformId())) {
//				// 获取accessToken
//				String accessTok = null;
//				try {
//					accessTok = wxMpService.getAccessToken();
//					logger.warn(wccPlatformUser.getAccount() + "获取token为====="
//							+ accessTok);
//					// accessTok = wxMpService.getAccessToken();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				acctoken.put(wccPlatformUser.getPlatformId(), accessTok);
//			}
			String accessToken=null;
			try {
				accessToken = wxMpService.getAccessToken();
			} catch (WxErrorException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			System.out.println("accessToken========================="
					+ accessToken);
			if (!"".equals(accessToken)) {
				String json = "";
				if (startTime != null && endTime != null) {
					json = "{\"begin_date\": \"" + startTime
							+ "\",\"end_date\": \"" + endTime + "\"}";
				}
				String url = "https://api.weixin.qq.com/datacube/getupstreammsg?access_token=";
				String returnList = "";
				try {
					returnList = HttpClientUtil.doJsonPost(url + accessToken, json);
					System.out.println(json);
					System.out.println(returnList);
					if (returnList.contains("errcode")) {// 发生错误时{"errcode":45009,"errmsg":"api freq out of limit"}
						String accessTok =TokenManager.getOfficialToken(wccPlatformUser.getAppId().trim(),wccPlatformUser.getAppSecret().trim(),true);
						returnList = HttpClientUtil.doJsonPost(url + accessTok, json);
//						acctoken.put(wccPlatformUser.getPlatformId(), accessTok);
						if(returnList.contains("errcode")){
							logger.warn(wccPlatformUser.getAccount()
									+ "发生错误=======" + returnList);
//							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!"".equals(returnList)) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					JSONObject jsonObject = JSONObject.fromObject(returnList);
					JSONArray data = jsonObject.getJSONArray("list");
					for (int i = 0; i < data.size(); i++) {
						String s = data.getString(i);
						JSONObject data2 = JSONObject.fromObject(s);
						String ref_date = data2.getString("ref_date");// 发表日期
						String msg_type = data2.getString("msg_type");
						String msg_user = data2.getString("msg_user");
						String msg_count = data2.getString("msg_count");
						// 排重 (根据msgId、statDate、type)
						ReportMessage mass = ReportMessage.checkReportMessage(
								wccPlatformUser.getId(), ref_date, null,
								msg_type, 1);
						Date rdate = null;
						try {
							rdate = format.parse(ref_date);
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						if (null == mass) {
							mass = new ReportMessage();
							mass.setRefDate(rdate);
							mass.setType(1);
							mass.setPlatformUser(wccPlatformUser.getId());
							mass.setMsgCount(Integer.parseInt(msg_count));
							mass.setMsgType(Integer.parseInt(msg_type));
							mass.setMsgUser(Integer.parseInt(msg_user));
							mass.persist();
						}
					}
				}
			}
//		}
	}
	
	public void saveDistributeFromWx(
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime) {
//		List<WccPlatformUser> plats = WccPlatformUser.findAllWccPlatformUsers(null, null);
		WccPlatformUser wccPlatformUser = WccPlatformUser.findWccPlatformUser(Long.parseLong("4"));

//		for (WccPlatformUser wccPlatformUser : plats) {
			System.setProperty("https.protocols", "SSLv3,SSLv2Hello");
			WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
			System.out.println("公众平台名称=========="
					+ wccPlatformUser.getAccount());
			config.setAppId(wccPlatformUser.getAppId().trim());
			config.setSecret(wccPlatformUser.getAppSecret().trim());
			wxMpService.setWxMpConfigStorage(config);
			
//			if (null != wccPlatformUser.getPlatformId()
//					&& null == acctoken.get(wccPlatformUser.getPlatformId())) {
//				// 获取accessToken
//				String accessTok = null;
//				try {
//					accessTok = wxMpService.getAccessToken();
//					logger.warn(wccPlatformUser.getAccount() + "获取token为====="
//							+ accessTok);
//					// accessTok = wxMpService.getAccessToken();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				acctoken.put(wccPlatformUser.getPlatformId(), accessTok);
//			}
			String accessToken=null;
			try {
				accessToken = wxMpService.getAccessToken();
			} catch (WxErrorException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			System.out.println("accessToken========================="
					+ accessToken);
			if (!"".equals(accessToken)) {
				String json = "";
				if (startTime != null && endTime != null) {
					json = "{\"begin_date\": \"" + startTime
							+ "\",\"end_date\": \"" + endTime + "\"}";
				}
				String url = "https://api.weixin.qq.com/datacube/getupstreammsgdist?access_token=";
				String returnList = "";
				try {
					returnList = HttpClientUtil.doJsonPost(url + accessToken, json);
					System.out.println(json);
					System.out.println(returnList);
					if (returnList.contains("errcode")) {// 发生错误时{"errcode":45009,"errmsg":"api freq out of limit"}
						String accessTok =TokenManager.getOfficialToken(wccPlatformUser.getAppId().trim(),wccPlatformUser.getAppSecret().trim(),true);
						returnList = HttpClientUtil.doJsonPost(url + accessTok, json);
//						acctoken.put(wccPlatformUser.getPlatformId(), accessTok);
						if(returnList.contains("errcode")){
							logger.warn(wccPlatformUser.getAccount()
									+ "发生错误=======" + returnList);
//							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!"".equals(returnList)) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					JSONObject jsonObject = JSONObject.fromObject(returnList);
					JSONArray data = jsonObject.getJSONArray("list");
					for (int i = 0; i < data.size(); i++) {
						String s = data.getString(i);
						JSONObject data2 = JSONObject.fromObject(s);
						String ref_date = data2.getString("ref_date");// 发表日期
						String count_interval = data2.getString("count_interval");
						String msg_user = data2.getString("msg_user");
						// 排重 (根据msgId、statDate、type)
						ReportMessageDistribute mass = ReportMessageDistribute.checkReportMessageDistribute(
								wccPlatformUser.getId(), ref_date, count_interval);
						Date rdate = null;
						try {
							rdate = format.parse(ref_date);
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						if (null == mass) {
							mass = new ReportMessageDistribute();
							mass.setRefDate(rdate);
							mass.setPlatformUser(wccPlatformUser.getId());
							mass.setMsgUser(Integer.parseInt(msg_user));
							mass.setCountInterval(Integer.parseInt(count_interval));
							mass.persist();
						}
					}
				}
			}
//		}
	}
	
}

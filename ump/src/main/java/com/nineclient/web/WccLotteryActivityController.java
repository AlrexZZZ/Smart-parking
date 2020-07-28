package com.nineclient.web;
import com.nineclient.constant.WccActivityType;
import com.nineclient.constant.WccSnCodeStatus;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.WccAwardInfo;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccGroup;
import com.nineclient.model.WccLotteryActivity;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccSncode;
import com.nineclient.model.WccUserLottery;
import com.nineclient.utils.AwardInfos;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.Global;
import com.nineclient.utils.LotteryInfo;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

import flexjson.JSON;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.AbstractHashedMap;
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

@RequestMapping("/wcclotteryactivitys")
@Controller
@RooWebScaffold(path = "wcclotteryactivitys", formBackingObject = WccLotteryActivity.class)
public class WccLotteryActivityController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(WccLotteryActivity wccLotteryActivity, Model uiModel,HttpServletRequest httpServletRequest,
//    		@RequestParam(value = "platformId", required = false) Long platformId,
    		@RequestParam(value = "startTimes", required = false) String startTimes,
			@RequestParam(value = "endTimes", required = false) String endTimes,@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "oneName", required = false) String oneName,
			@RequestParam(value = "oneNumber", required = false) int oneNumber,
			@RequestParam(value = "oneProbability", required = false) Integer oneProbability,
			@RequestParam(value = "oneAwardInfo", required = false) String oneAwardInfo,
			@RequestParam(value = "twoName", required = false) String twoName,
			@RequestParam(value = "twoNumber", required = false) int twoNumber,
			@RequestParam(value = "twoProbability", required = false) Integer twoProbability,
			@RequestParam(value = "twoAwardInfo", required = false) String twoAwardInfo,
			@RequestParam(value = "threeName", required = false) String threeName,
			@RequestParam(value = "threeNumber", required = false) int threeNumber,
			@RequestParam(value = "threeProbability", required = false) Integer threeProbability,
			@RequestParam(value = "threeAwardInfo", required = false) String threeAwardInfo,
			@RequestParam(value = "fourName", required = false) String fourName,
			@RequestParam(value = "fourNumber", required = false) int fourNumber,
			@RequestParam(value = "fourProbability", required = false) Integer fourProbability,
			@RequestParam(value = "fourAwardInfo", required = false) String fourAwardInfo,
			@RequestParam(value = "fiveName", required = false) String fiveName,
			@RequestParam(value = "fiveNumber", required = false) int fiveNumber,
			@RequestParam(value = "fiveProbability", required = false) Integer fiveProbability,
			@RequestParam(value = "fiveAwardInfo", required = false) String fiveAwardInfo,
			@RequestParam(value = "sixName", required = false) String sixName,
			@RequestParam(value = "sixNumber", required = false) int sixNumber,
			@RequestParam(value = "sixProbability", required = false) Integer sixProbability,
			@RequestParam(value = "sixAwardInfo", required = false) String sixAwardInfo,
			@RequestParam(value="lotteryId", required = false) Long lotteryId,
			@RequestParam(value="activityNStartInfo",required = false) String activityNStartInfo) {
		SimpleDateFormat DATE_AND_TIME_FORMATER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(null != startTimes && !"".equals(startTimes)){
			Date sdate = null;
			try {
				sdate = DATE_AND_TIME_FORMATER.parse(startTimes);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			wccLotteryActivity.setStartTime(sdate);
		}
		if(null != endTimes && !"".equals(endTimes)){
			Date edate = null;
			try {
				edate = DATE_AND_TIME_FORMATER.parse(endTimes);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			wccLotteryActivity.setEndTime(edate);
		}
//		WccPlatformUser platformUser = WccPlatformUser.findWccPlatformUser(platformId);
//		Set<WccPlatformUser> platformUsers =  new HashSet<WccPlatformUser>();
//		platformUsers.add(platformUser);
		uiModel.asMap().clear();
		if(null!=lotteryId){//修改
		    List<WccSncode> sncodes = WccSncode.findAllWccSncodesByLotteryId(lotteryId);
		    for(WccSncode sncode : sncodes){
		    	sncode.setFriend(null);
		    	sncode.setAwardInfo(null);
		    	sncode.setLotteryActivity(null);
		    	sncode.remove();
		    }
		    
		     List<WccAwardInfo> awards = WccAwardInfo.findAllWccAwardInfoesByActivityId(lotteryId);
		     for(WccAwardInfo award : awards){
		    	 award.setLotteryActivity(null);
		    	 award.remove();
		     }
		     wccLotteryActivity.setId(lotteryId);
		     WccLotteryActivity activity = WccLotteryActivity.findWccLotteryActivity(lotteryId);
		     activity.setActivityName(wccLotteryActivity.getActivityName());
		     activity.setActivityEndInfo(wccLotteryActivity.getActivityEndInfo());
		     activity.setActivityIntroduction(wccLotteryActivity.getActivityIntroduction());
		     activity.setStartTime(wccLotteryActivity.getStartTime());
		     activity.setEndTime(wccLotteryActivity.getEndTime());
		     activity.setLotteryNum(wccLotteryActivity.getLotteryNum());
		     activity.setNumEndInfo(wccLotteryActivity.getNumEndInfo());
		     activity.setRepeatAwardReply(wccLotteryActivity.getRepeatAwardReply());
		     activity.setAwardInfos(null);
		     activity.setSncodes(null);
		     activity.setActivityNStartInfo(activityNStartInfo);
		     activity.merge();
		     
		}else{
			wccLotteryActivity.setIsVisibale(false);
			wccLotteryActivity.setInsertTime(new Date());
			wccLotteryActivity.setActivityType(WccActivityType.大转盘);
			wccLotteryActivity.setIsDeleted(true);
//			wccLotteryActivity.setPlatformUsers(platformUsers);
			Long companyId = CommonUtils.getCurrentCompanyId(httpServletRequest);
			UmpCompany company = UmpCompany.findUmpCompany(companyId);
			wccLotteryActivity.setUmpCompany(company);
			wccLotteryActivity.persist();
		}
        WccLotteryActivity activity = WccLotteryActivity.findWccLotteryActivity(wccLotteryActivity.getId());
        
        saveAwardInfo(oneName, oneNumber, 1, oneAwardInfo, oneProbability, activity);
        saveAwardInfo(twoName, twoNumber, 2, twoAwardInfo, twoProbability, activity);
        saveAwardInfo(threeName, threeNumber, 3, threeAwardInfo, threeProbability, activity);
        saveAwardInfo(fourName, fourNumber, 4, fourAwardInfo, fourProbability, activity);
        saveAwardInfo(fiveName, fiveNumber, 5, fiveAwardInfo, fiveProbability, activity);
        saveAwardInfo(sixName, sixNumber, 6, sixAwardInfo, sixProbability, activity);
        Long displayId = CommonUtils.getCurrentDisPlayId(httpServletRequest);
        //return "wcclotteryactivitys/list";
        return "redirect:/wcclotteryactivitys?page=1&amp;size=10";
    }
	
	/**
	 * 保存奖品信息
	 * @param name
	 * @param number
	 * @param level
	 * @param info
	 * @param probability
	 * @param activity
	 * @return
	 */
	private Long saveAwardInfo(String name,int number,int level,String info,int probability,WccLotteryActivity activity){
		 WccAwardInfo awardInfo = new WccAwardInfo();
	        awardInfo.setAwardName(name);
	        awardInfo.setAwardNum(number);
	        awardInfo.setAwardLevel(level);
	        awardInfo.setAwardInfo(info);
	        awardInfo.setWinRate(probability);
	        awardInfo.setIsDeleted(true);
	        awardInfo.setLotteryActivity(activity);
	        awardInfo.persist();
	       WccAwardInfo ainfo = new WccAwardInfo();
	       for(int i = 0; i < number; i++){
	    	   WccSncode sncode = new WccSncode();
	    	   sncode.setIsDeleted(true);
	    	   sncode.setSnStatus(WccSnCodeStatus.未中奖);
	    	   sncode.setSnCode(UUID.randomUUID().toString().replace("-", ""));
	    	   sncode.setAwardInfo(awardInfo);
	    	   sncode.setLotteryActivity(activity);
	    	   sncode.setAwardLevel(level);
	    	   sncode.persist();
	       }
		return awardInfo.getId();
	}

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(@RequestParam(value = "id",required = false) Long id,Model uiModel,HttpServletRequest httpServletRequest) {
        populateEditForm(uiModel, new WccLotteryActivity());
        List<WccPlatformUser> platformUsers = WccPlatformUser.findAllWccPlatformUsers(CommonUtils.getCurrentPubOperator(httpServletRequest));
        uiModel.addAttribute("platformUsers", platformUsers);
        if(null!=id){//修改
        	WccLotteryActivity lottery = WccLotteryActivity.findWccLotteryActivity(id);
        	if(null!=lottery&&null!=lottery.getStartTime()&&null!=lottery.getEndTime()){
        		String stime = DateUtil.formatDateAndTime(lottery.getStartTime());
        		String etime = DateUtil.formatDateAndTime(lottery.getEndTime());
        		uiModel.addAttribute("stime", stime);
        		uiModel.addAttribute("etime", etime);
        	}
        	uiModel.addAttribute("lottery", lottery);
//        	Iterator<WccPlatformUser> it = lottery.getPlatformUsers().iterator();
//        	WccPlatformUser platformUser = new WccPlatformUser();
//        	while(it.hasNext()){
//        		platformUser = it.next();
//        		break;
//        	}
//        	uiModel.addAttribute("platformUser",platformUser);
        	Set<WccAwardInfo> awards = lottery.getAwardInfos();
        	for(WccAwardInfo award : awards){
        		switch (award.getAwardLevel()) {
    			case 1://一等奖
    				WccAwardInfo award1 = award;
    				uiModel.addAttribute("award1", award1);
    				break;
    			case 2://二等奖
    				WccAwardInfo award2 = award;
    				uiModel.addAttribute("award2", award2);
    				break;
    			case 3://三等奖
    				WccAwardInfo award3 = award;
    				uiModel.addAttribute("award3", award3);
    				break;
    			case 4://四等奖
    				WccAwardInfo award4 = award;
    				uiModel.addAttribute("award4", award4);
    				break;
    			case 5://五等奖
    				WccAwardInfo award5 = award;
    				uiModel.addAttribute("award5", award5);
    				break;
    			case 6://六等奖
    				WccAwardInfo award6 = award;
    				uiModel.addAttribute("award6", award6);
    				break;
    			default:
    				break;
    			}
        	}
        }
        uiModel.addAttribute("lotteryId", id);
        return "wcclotteryactivitys/create";
    }
	
	/**
	 * 进入大转盘抽奖活动
	 * @param id
	 * @param uiModel
	 * @return
	 * @throws WxErrorException 
	 */
	@RequestMapping(value = "showActivity", produces = "text/html")
	public String showLotteryActivity(@RequestParam(value = "state", required = true) String state,
			@RequestParam(value = "code", required = true) String code,Model uiModel,HttpServletRequest request) throws WxErrorException{
		long d1 = new Date().getTime();
		System.out.println("第一次时间======================================"+d1);
		long id=0L;
		long platformId=0L;
		if(state.contains("-")){
			String[] strs = state.split("-");
			id = Long.parseLong(strs[0]);
			platformId = Long.parseLong(strs[1]);
		}else{
			id=Long.parseLong(request.getParameter("id"));
			platformId=Long.parseLong(state);
			code=null;
		}
		WccLotteryActivity lotteryActivity = WccLotteryActivity.findWccLotteryActivity(id);//根据抽奖活动ID查询抽奖活动
		if(lotteryActivity.getIsVisibale()==false){//活动未启用
			uiModel.addAttribute("info", "抽奖活动尚未启用");
			return "wcclotteryactivitys/lotteryError";
		}
		if(lotteryActivity.getStartTime().getTime()>new Date().getTime()){
			uiModel.addAttribute("info", lotteryActivity.getActivityNStartInfo());
			return "wcclotteryactivitys/lotteryError";
		}
		if(lotteryActivity.getEndTime().getTime()<new Date().getTime()){
			uiModel.addAttribute("info", lotteryActivity.getActivityEndInfo());
			return "wcclotteryactivitys/lotteryError";
		}
		WccPlatformUser platformUser = WccPlatformUser.findWccPlatformUser(platformId);
		WccFriend friend = null;
		Long friendId = 0L;
		if(code!=null){
			WxMpService wxMpService = new WxMpServiceImpl();
			WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
			config.setAppId(platformUser.getAppId());
			config.setSecret(platformUser.getAppSecret());
			wxMpService.setWxMpConfigStorage(config);
			wxMpService.oauth2buildAuthorizationUrl(WxConsts.OAUTH2_SCOPE_BASE, null);
			WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
			System.out.println("token="+wxMpOAuth2AccessToken);
			JSONObject json = JSONObject.fromObject(wxMpOAuth2AccessToken);
			String openId = json.getString("openId");
			System.out.println("openId="+openId);
			friend = WccFriend.findWccFriendByOpenId(openId);
			friendId = friend.getId();
			System.out.println("friendId="+friendId);
		}else{
			friendId = Long.parseLong(request.getParameter("friendId"));
			friend = WccFriend.findWccFriend(friendId);
		}
		List<WccAwardInfo> awards = WccAwardInfo.findAllWccAwardInfoesByActivityId(id);//根据活动ID查询活动中奖信息
		WccUserLottery userLottery = WccUserLottery.findAllWccUserLotterysByFriendId(friendId,id);//根据粉丝ID、活动ID查询粉丝中奖信息
		List<WccSncode> sncode = WccSncode.findAllWccSncodesByFriendId(friendId,id);//根据粉丝ID、活动ID查询粉丝中奖SN码
		boolean flag = false;//粉丝是否已中过奖
		if(sncode.size()>0){
			flag = true;
		}
		int number = 0 ; //粉丝已抽奖次数
		int daynNumber = 0 ; //粉丝每天已抽奖次数
		if(null!=userLottery){
			number = userLottery.getLotteryNumber();
			if(lotteryActivity.getLotteryDayNum() > 0){
				  if(DateUtil. getStrOfDate( userLottery.getLotterDay()).equals(DateUtil. getStrOfDate( new Date()))){
	                  daynNumber = userLottery.getLotterDayNum();
	                  System.out.println(daynNumber+"-=daynNumber-=");
	            }
			}
		}
		
		for(WccAwardInfo award : awards){
			switch (award.getAwardLevel()) {
			case 1://一等奖
				WccAwardInfo award1 = award;
				uiModel.addAttribute("award1", award1);
				break;
			case 2://二等奖
				WccAwardInfo award2 = award;
				uiModel.addAttribute("award2", award2);
				break;
			case 3://三等奖
				WccAwardInfo award3 = award;
				uiModel.addAttribute("award3", award3);
				break;
			case 4://四等奖
				WccAwardInfo award4 = award;
				uiModel.addAttribute("award4", award4);
				break;
			case 5://五等奖
				WccAwardInfo award5 = award;
				uiModel.addAttribute("award5", award5);
				break;
			case 6://六等奖
				WccAwardInfo award6 = award;
				uiModel.addAttribute("award6", award6);
				break;
			default:
				break;
			}
		}
		uiModel.addAttribute("number", number);//粉丝已抽奖次数
		uiModel.addAttribute("daynNumber", daynNumber);
		uiModel.addAttribute("friendId", friendId);//粉丝ID
		uiModel.addAttribute("lotteryActivity", lotteryActivity);//抽奖活动
		uiModel.addAttribute("platformUserId", friend.getPlatformUser().getId());//公众平台ID
		uiModel.addAttribute("flag", flag);//粉丝是否已中奖过
		long d2 = new Date().getTime();
		System.out.println("第二次时间======================================"+(d2-d1));
		return "wcclotteryactivitys/lotteryWeb";
	}
	
	/**
	 * 抽奖
	 * @param id
	 * @param oneProbability 一等奖概率
	 * @param twoProbability 二等奖概率
	 * @param threeProbability 三等奖概率
	 * @param fourProbability 四等奖中间概率
	 * @param fiveProbability 五等奖中间概率
	 * @param sixProbability 六等奖中间概率
	 * @param friendId 粉丝ID
	 * @param platformUserId 公众平台ID
	 * @param lotteryId 抽奖活动ID
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "lotteryClick", produces = "text/html;charset=utf-8")
	public String lotteryClick(@RequestParam(value = "id") Long id,
			@RequestParam(value = "oneProbability") String oneProbability,//一等奖中间概率
			@RequestParam(value = "twoProbability") String twoProbability,//二等奖中间概率
			@RequestParam(value = "threeProbability") String threeProbability,//三等奖中间概率
			@RequestParam(value = "fourProbability") String fourProbability,//四等奖中间概率
			@RequestParam(value = "fiveProbability") String fiveProbability,//五等奖中间概率
			@RequestParam(value = "sixProbability") String sixProbability,//六等奖中间概率
			@RequestParam(value = "friendId") String friendId,//粉丝ID
			@RequestParam(value = "platformUserId") String platformUserId,//公众平台ID
			@RequestParam(value = "lotteryId") String lotteryId,//抽奖活动ID
			@RequestParam(value = "lotteryNumber") int lotteryNumber,//可抽奖次数
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		WccFriend friend = WccFriend.findWccFriend(Long.parseLong(friendId));
		/**
		 * 根据抽奖活动id查询
		 * 如果设置了每日抽奖活动次数
		 * 抽完一次就必须拿总抽奖次数减去。
		 */
		Map<String,Integer> num = null;
		//获取中奖级别
		LotteryInfo info = getAwardLevel(oneProbability, twoProbability, threeProbability, fourProbability, fiveProbability, sixProbability);
		WccLotteryActivity lotter =  WccLotteryActivity.findWccLotteryActivity(Long.parseLong(lotteryId));
		if(null != lotter){
			if(lotter.getLotteryDayNum() > 0){
			   num = getLotteryNumber(friend, platformUserId, lotteryId, lotteryNumber,new Date(),lotter.getLotteryDayNum());//粉丝抽奖次数
			   info.setDayNumber(num.get("lottDayNum"));//粉丝每日抽奖次数
			   info.setNumber(num.get("lottNum"));//粉丝抽奖次数
			}else{
				num = getLotteryNumber(friend, platformUserId, lotteryId, lotteryNumber,null,0);//粉丝抽奖次数
				info.setNumber(num.get("lottNum"));//粉丝抽奖次数
			}
		}
		//获取奖品snCode
		String snCode = getSncode(lotteryId, info.getAwardLevel()+"",friend);
		//如果snCode为空（奖品已中完） 则中奖级别设为0（未中奖）
		if(null!=snCode&&!"".equals(snCode)){
			info.setSnCode(snCode);//粉丝中奖sn码
		}else{
			info.setAwardLevel(0);//奖品已完
		}
		return info.toJson();//返回中奖级别、sn码、抽奖次数
	}
	
	/**
	 * 获取中奖级别
	 * @param oneProbability 一等奖概率
	 * @param twoProbability 二等奖概率
	 * @param threeProbability 三等奖概率
	 * @param fourProbability 四等奖概率
	 * @param fiveProbability 五等奖概率
	 * @param sixProbability 六等奖概率
	 * @return
	 */
	private LotteryInfo getAwardLevel(String oneProbability,String twoProbability,String threeProbability,
			String fourProbability,String fiveProbability,String sixProbability){
		LotteryInfo info = new LotteryInfo();
		int number = 100000;// 中奖数
		String awardLevel = "";// 中奖等级
		long num = Math.round(Math.random() * number);// 用户抽奖生成的随机数
		long number1 = new Double(number * Double.parseDouble(oneProbability) / 100)
				.longValue();// 一等奖中奖的最大数字
		 long number2 = new Double(number * Double.parseDouble(twoProbability) / 100)
		 .longValue() + number1;// 二等奖中奖的最大数字
		 long number3 = new Double(number * Double.parseDouble(threeProbability) / 100)
		 .longValue() + number2;// 三等奖中奖的最大数字
		 long number4 = new Double(number * Double.parseDouble(fourProbability) / 100)
		 .longValue() + number3;// 四等奖中奖的最大数字
		 long number5 = new Double(number * Double.parseDouble(fiveProbability) / 100)
		 .longValue() + number4;// 五等奖中奖的最大数字
		 long number6 = new Double(number * Double.parseDouble(sixProbability) / 100)
		 .longValue() + number5;// 六等奖中奖的最大数字
		 if (num < number6) {
			 info.setAwardLevel(6);//六等奖
		 }
		 if (num < number5) {
			 info.setAwardLevel(5);//五等奖
		 }
		 if (num < number4) {
			 info.setAwardLevel(4);//四等奖
		 }
		 if (num < number3) {
			 info.setAwardLevel(3);//三等奖
		 }
		 if (num < number2) {
			 info.setAwardLevel(2);//二等奖
		 }
		 if (num < number1) {
			 info.setAwardLevel(1);//一等奖
		 }
		return info;
	}
	
	/**
	 * 获取奖品snCode
	 * @param lotteryId
	 * @param awardLevel
	 * @return
	 */
	private String getSncode(String lotteryId,String awardLevel,WccFriend friend){
		//根据抽奖活动ID、奖品等级获取sn码
		String sncode = WccSncode.findAllWccSncodesByLotteryIdLevel(lotteryId, awardLevel,friend);
		return sncode;
	}
	
	/**
	 * 获取抽奖次数
	 * @param friend
	 * @param platformId
	 * @param lotteryId
	 * @param lotteryNumber
	 * @return
	 */
	private Map<String,Integer> getLotteryNumber(WccFriend friend,String platformId,String lotteryId,int lotteryNumber,Date lotterDate,int dayNum){
		Long acid = 0l;
		if(null!=lotteryId&&!"".equals(lotteryId)){
			acid = Long.parseLong(lotteryId);
		}
		//根据粉丝ID、抽奖活动ID获取粉丝已抽奖的次数
		WccUserLottery userLottery = WccUserLottery.findAllWccUserLotterysByFriendId(friend.getId(),acid);
		int num = 1;
		Map<String,Integer> map = new HashMap<String, Integer>();
		if(null==userLottery){//该粉丝是第一次抽奖
			WccPlatformUser platformUser = new WccPlatformUser();
			WccLotteryActivity lottery = new WccLotteryActivity();
			if(null!=platformId&&!"".equals(platformId)){
				//根据公众平台ID查询公众平台
				platformUser = WccPlatformUser.findWccPlatformUser(Long.parseLong(platformId));
			}
			if(null!=lotteryId&&!"".equals(lotteryId)){
				//根据抽奖活动ID查询抽奖活动
				lottery = WccLotteryActivity.findWccLotteryActivity(Long.parseLong(lotteryId));
			}
			userLottery = new WccUserLottery();
			userLottery.setFriend(friend);
			userLottery.setLotteryActivity(lottery);
			userLottery.setPlatformUser(platformUser);
			userLottery.setIsDeleted(true);
			userLottery.setLotteryNumber(num);
			if(dayNum > 0){
				userLottery.setLotterDayNum(num);
				userLottery.setLotterDay(new Date());
			}
			userLottery.persist();
			map.put("lottNum", num);
			map.put("lottDayNum", num);
			return map;
		}
		num = userLottery.getLotteryNumber()+1;//不是第一次抽奖,抽奖次数+1
		userLottery.setLotteryNumber(num);
		map.put("lottNum", num);
		if(dayNum > 0){
			if(DateUtil.getStrOfDate(userLottery.getLotterDay()).equals(DateUtil.getStrOfDate(lotterDate))){
				userLottery.setLotterDayNum(userLottery.getLotterDayNum()+1);
				map.put("lottDayNum", userLottery.getLotterDayNum());
			}else{
				userLottery.setLotterDay(new Date());
				userLottery.setLotterDayNum(1);
				map.put("lottDayNum", userLottery.getLotterDayNum());
			}
		}
		userLottery.merge();
		return map;
	}
	
	/**
	 * 中奖保存粉丝号码
	 * @param tel
	 * @param sncode
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "saveTel")
	public void saveTel(@RequestParam(value = "tel") String tel,
			@RequestParam(value = "sncode") String sncode,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			WccSncode wsncode = WccSncode.findAllWccSncodesBySncode(sncode);
			wsncode.setTel(tel);
			wsncode.setAwardTime(new Date());
			wsncode.persist();
			out.println("true");
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

	}
	
	 @RequestMapping(value = "/queryActivities", produces = "text/html")
	 public String queryActivities(@RequestParam(value = "start", required = false) Integer start,
			 @RequestParam(value = "limit", required = false) Integer limit,
			 @RequestParam(value = "platformUserId", required = false) String platformUserId,
			 @RequestParam(value = "nickName", required = false) String nickName,
			 @RequestParam(value = "insertTime", required = false) String insertTime,
			 @RequestParam(value = "startTime", required = false) String startTime,
			 @RequestParam(value = "endTime", required = false) String endTime,
			 @RequestParam(value = "createEndTime", required = false) String createEndTime,
			 @RequestParam(value = "isvisible", required = false) String isvisible,
			 Model uiModel,HttpServletRequest request) {
		String domain = Global.Url;
		String url = domain + "/ump/wcclotteryactivitys/showActivity?id=";
		uiModel.addAttribute("url", url);
		PubOperator pubOper = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platforms = WccPlatformUser.findAllWccPlatformUsers(pubOper);
		
		Long companyId = CommonUtils.getCurrentCompanyId(request);
		
		Map<String,Object> parmMap = new HashMap<String, Object>();
		parmMap.put("activityName", nickName);
		parmMap.put("insertTime", insertTime);
		parmMap.put("startTime", startTime);
		parmMap.put("endTime", endTime);
		parmMap.put("createEndTime", createEndTime);
		parmMap.put("isvisible", isvisible);
		parmMap.put("companyId", companyId);
		Set<WccPlatformUser> plats = new HashSet<WccPlatformUser>();
		if(null!=platformUserId&&!"".equals(platformUserId)){
			String[] platIds = platformUserId.split(",");
			for (String platid : platIds) {
				WccPlatformUser plat = WccPlatformUser.findWccPlatformUser(Long.parseLong(platid));
				plats.add(plat);
			}
		}
		WccLotteryActivity lottery = new WccLotteryActivity();
		if(plats.size()==0){
			for(WccPlatformUser platform : platforms){
				WccPlatformUser plat = WccPlatformUser.findWccPlatformUser(platform.getId());
				platformUserId=plat.getId()+"";
				plats.add(plat);
			}
		}
//		lottery.setPlatformUsers(plats);
		QueryModel<WccLotteryActivity> qm = new QueryModel<WccLotteryActivity>(
				lottery, start, limit);
		parmMap.put("platformUserId",platformUserId);
		qm.getInputMap().putAll(parmMap);
		
		PageModel<WccLotteryActivity> pm = WccLotteryActivity
				.getQueryActivities(qm);
		pm.setPageSize(limit);
		pm.setStartIndex(start);
		uiModel.addAttribute("wcclotteryactivitys", pm.getDataList());
		uiModel.addAttribute("maxPages", pm.getTotalPage());
		uiModel.addAttribute("page", pm.getPageNo());
		uiModel.addAttribute("limit", pm.getPageSize());
		 
      return "wcclotteryactivitys/result";
	 }	
	

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("wcclotteryactivity", WccLotteryActivity.findWccLotteryActivity(id));
        uiModel.addAttribute("itemId", id);
        return "wcclotteryactivitys/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page,
    		@RequestParam(value = "displayId", required = false) Long displayId, 
    		HttpServletRequest httpServletRequest, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("wcclotteryactivitys", WccLotteryActivity.findWccLotteryActivityEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) WccLotteryActivity.countWccLotteryActivitys() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("wcclotteryactivitys", WccLotteryActivity.findAllWccLotteryActivitys(sortFieldName, sortOrder));
        }
        if(displayId == null){
        	displayId = CommonUtils.getCurrentDisPlayId(httpServletRequest);
        }
        httpServletRequest.getSession().setAttribute("displayId", displayId);
        List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(CommonUtils.getCurrentPubOperator(httpServletRequest));
		uiModel.addAttribute("platformUser", platformUser);
        addDateTimeFormatPatterns(uiModel);
        String domain = Global.Url;
        String url = domain+"/ump/wcclotteryactivitys/showActivity?id=";
        uiModel.addAttribute("url", url);
        uiModel.addAttribute("limit", 10);
        return "wcclotteryactivitys/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid WccLotteryActivity wccLotteryActivity, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccLotteryActivity);
            return "wcclotteryactivitys/update";
        }
        uiModel.asMap().clear();
        wccLotteryActivity.merge();
        return "redirect:/wcclotteryactivitys/" + encodeUrlPathSegment(wccLotteryActivity.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, WccLotteryActivity.findWccLotteryActivity(id));
        return "wcclotteryactivitys/update";
    }

	@RequestMapping(value = "delete", produces = "text/html")
    public String delete(@RequestParam(value = "id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        WccLotteryActivity wccLotteryActivity = WccLotteryActivity.findWccLotteryActivity(id);
        wccLotteryActivity.setIsDeleted(false);
        wccLotteryActivity.merge();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/wcclotteryactivitys";
    }
	
	@RequestMapping(value = "visible", produces = "text/html")
    public String visible(@RequestParam(value = "id") Long id, 
    		@RequestParam(value="status") Integer status ,
    		@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        WccLotteryActivity wccLotteryActivity = WccLotteryActivity.findWccLotteryActivity(id);
        if(status==0){
        	wccLotteryActivity.setIsVisibale(false);
        }else{
        	wccLotteryActivity.setIsVisibale(true);
        }
        wccLotteryActivity.setActivityRemark("1");
        wccLotteryActivity.merge();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/wcclotteryactivitys";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("wccLotteryActivity_starttime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("wccLotteryActivity_endtime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, WccLotteryActivity wccLotteryActivity) {
        uiModel.addAttribute("wccLotteryActivity", wccLotteryActivity);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("wccactivitytypes", Arrays.asList(WccActivityType.values()));
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
	
	/**
	 * 检查抽奖活动名称
	 * @param uiModel
	 * @param request
	 * @param name
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "checkLottery", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String checkLottery(Model uiModel, HttpServletRequest request,
			@RequestParam(value = "name", required = false) String name,
			HttpServletResponse response) {
		PubOperator pub = CommonUtils.getCurrentPubOperator(request);
		List<WccLotteryActivity> lists = WccLotteryActivity.findWccLotteryByName(name,pub.getCompany());
		String flag = "true";
		if(null!=lists&&lists.size()>0){
			flag = "false";
		}
		return flag;
	}
}

package com.nineclient.web.wccreport;


import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nineclient.model.PubOperator;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.model.wccreport.ReportFriendsFrom;
import com.nineclient.model.wccreport.ReportFriendsSum;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.ExcelUtil;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;

@RequestMapping("/reportReply")
@Controller
public class ReportReplyController {

	@RequestMapping(value = "reportPage", produces = "text/html")
	public String reportPage(HttpServletRequest request, Model uiModel) {
		PubOperator currentoper=CommonUtils.getCurrentPubOperator(request);
        List<WccPlatformUser> platformUsers=WccPlatformUser.getplatformuserListByOrg(currentoper);
		uiModel.addAttribute("platformUsers", platformUsers);
		return "reportReply/report";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "queryDataReplyGrid", produces = "application/json")
	public void queryDataReplyGrid(HttpServletRequest request,
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
		long totalCount = 0;
		List<WccRecordMsg> recordlist =WccRecordMsg.findReplyReportByDateBetween(
							platformUser, startTime, endTime, firstResult,
							maxResults);
		List<WccRecordMsg> list = new ArrayList<WccRecordMsg>();
		for (WccRecordMsg record : recordlist) {
			try {
				record.setNickName(URLDecoder.decode(record.getNickName(),
						"utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.add(record);
		}
					totalCount = WccRecordMsg.findReplyReportByDateBetween(
							platformUser, startTime, endTime, -1, -1).size();
					pager.setRecordCount(totalCount);// 设置总记录数
					// 根据总记录数和每页的大小计算公有多少页
					if ((totalCount % maxResults) > 0) {
						totalPage = (int) ((totalCount / maxResults) + 1);
					} else {
						totalPage = (int) (totalCount / maxResults);
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
	
	@RequestMapping(value = "exportExcelSum")
	public void exportExcelSum(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "platformUser") String platformUser,
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime)
			throws IOException {

		List<WccRecordMsg> recordlist =WccRecordMsg.findReplyReportByDateBetween(
				platformUser, startTime, endTime, -1,-1);
		List<Map> listData = new ArrayList<Map>();
		for (WccRecordMsg wccRecordMsg : recordlist) {
			Map<String, Object> datamap = new HashMap<String, Object>();
			datamap.put("platformAccount", wccRecordMsg.getPlatFormAccount());
			datamap.put("nickName",wccRecordMsg.getNickName());
			datamap.put("sex", wccRecordMsg.getSex());
			datamap.put("province", wccRecordMsg.getProvince());
			datamap.put("recordContent", wccRecordMsg.getRecordContent());
			datamap.put("insertTime", wccRecordMsg.getInsertTime());
			listData.add(datamap);
		}
	
		
		String[] title = { "公众平台", "粉丝昵称", "性别", "地区", "评论内容","评论时间" };
		String[] colums = { "platformAccount", "nickName", "sex",	"province", "recordContent" ,"insertTime"};
		OutputStream os = null;
		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("公众号回复统计报表").getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			os = response.getOutputStream();

			map.put("公众号回复统计报表", listData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel(title, colums, map, os);
	}
	
}

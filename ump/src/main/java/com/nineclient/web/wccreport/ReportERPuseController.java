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
import java.util.Set;

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

import com.nineclient.dto.PubOperatorDto;
import com.nineclient.dto.ReportOperatorDto;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpLog;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.model.wccreport.ReportFriendsFrom;
import com.nineclient.model.wccreport.ReportFriendsSum;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.ExcelUtil;
import com.nineclient.utils.ListSort;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;

@RequestMapping("/reporterpuse")
@Controller
public class ReportERPuseController {

	@RequestMapping(value = "reportPage", produces = "text/html")
	public String reportPage(HttpServletRequest request, Model uiModel) {
		return "reporterpuse/report";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "queryDataGrid", produces = "application/json")
	public void queryDataGrid(HttpServletRequest request,
			HttpServletResponse response, String dtGridPager) {
		Pager pager = null;
		String startTime = "";
		String endTime = "";
		Map parm = new HashMap<String, String>();
		try {
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			parm = pager.getParameters();
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
		ReportOperatorDto dto=null;
		Long companyid=CommonUtils.getCurrentCompanyId(request);
		List<WccPlatformUser> operlist =WccPlatformUser.findReportByDateBetween(startTime, endTime, firstResult,
							maxResults);
		List<ReportOperatorDto> list = new ArrayList<ReportOperatorDto>();
        for (WccPlatformUser wccPlatformUser : operlist) {
        	Long friendNum=WccFriend.getFriendNum(wccPlatformUser.getId());
        	  Set<PubOperator> pubopers=wccPlatformUser.getPubOperators();
        	  if(pubopers.size()>0){
        		  for (PubOperator pubOperator : pubopers) {
        			  Long loginNum=UmpLog.getloginNum(pubOperator.getAccount());
        			  dto=new ReportOperatorDto();
        			  dto.setAccount(pubOperator.getAccount());
        			  dto.setOperatorName(pubOperator.getOperatorName());
        			  dto.setLoginNum(loginNum);
        			  dto.setInsertTime(wccPlatformUser.getInsertTime());
  					  dto.setPlatformAccount(wccPlatformUser.getAccount());
  					  dto.setFriendNum(friendNum);
  					  dto.setPlatType(wccPlatformUser.getPlatformType().name());
  					  list.add(dto);
				}
        	  }
		}
					totalCount = PubOperator.findReportByDateBetween(companyid,startTime, endTime, -1, -1).size();
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
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime)
			throws IOException {

		List<Map> listData = new ArrayList<Map>();
		List<WccPlatformUser> operlist =WccPlatformUser.findReportByDateBetween(startTime, endTime,-1,-1);
		Map<String, Object> datamap =null;
	    for (WccPlatformUser wccPlatformUser : operlist) {
		Long friendNum=WccFriend.getFriendNum(wccPlatformUser.getId());
  	  Set<PubOperator> pubopers=wccPlatformUser.getPubOperators();
  	  if(pubopers.size()>0){
  		  for (PubOperator pubOperator : pubopers) {
  			        Long loginNum=UmpLog.getloginNum(pubOperator.getAccount());
				    datamap = new HashMap<String, Object>();
					datamap.put("account", pubOperator.getAccount());
					datamap.put("loginNum",loginNum);
					datamap.put("operatorName", pubOperator.getOperatorName());
					datamap.put("platformAccount", wccPlatformUser.getAccount());
					datamap.put("platType", wccPlatformUser.getPlatformType().name());
					datamap.put("insertTime",wccPlatformUser.getInsertTime());
					datamap.put("friendNum",friendNum);
					listData.add(datamap);
			}
  	  }
		}

		String[] title = { "ERP号", "经销商简称", "入驻时间", "经销商公众号名称", "账号类型","登陆次数" ,"粉丝数"};
		String[] colums = { "account", "operatorName", "insertTime",	"platformAccount", "platType" ,"loginNum","friendNum"};
		OutputStream os = null;
		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("xxxxxxx平台经销商使用统计报表").getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			os = response.getOutputStream();

			map.put("xxxxxxx平台经销商使用统计报表", listData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel(title, colums, map, os);
	}
	
}

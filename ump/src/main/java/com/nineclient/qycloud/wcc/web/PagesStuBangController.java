package com.nineclient.qycloud.wcc.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nineclient.cbd.wcc.model.WccCredentialType;
import com.nineclient.cbd.wcc.model.WccCultureLife;
import com.nineclient.cbd.wcc.model.WccFinancialProduct;
import com.nineclient.cbd.wcc.model.WccFinancialUser;
import com.nineclient.cbd.wcc.model.WccImgSave;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccGroup;
import com.nineclient.model.WccMaterials;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.qycloud.wcc.dto.TDormbulid;
import com.nineclient.qycloud.wcc.model.WccEnList;
import com.nineclient.qycloud.wcc.model.WccRepairReg;
import com.nineclient.qycloud.wcc.model.WccStu;
import com.nineclient.tailong.wcc.model.WccTproducts;
import com.nineclient.task.GetMapInfo;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.DkfEntity;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;
import com.nineclient.utils.RecordData;
import com.nineclient.utils.Recordlist;
import com.nineclient.utils.WxEmotion;
import com.nineclient.web.WccFriendController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/pageBding")
@Controller
public class PagesStuBangController {

	/**
	 * 查询页面
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "showBding", produces = "text/html")
	public String showBding(HttpServletRequest request, Model uiModel,
			@RequestParam(value = "openId", required = false) String openId,
			HttpServletResponse response) {
		uiModel.addAttribute("openId", openId);
		System.out.println("enter banding page~");

		// uiModel.addAttribute("openId", openId);
		return "page/showBding";
	}
	@RequestMapping(value = "showInfo_", produces = "text/html")
	public String showInfo_(HttpServletRequest request, Model uiModel,
			@RequestParam(value = "openId", required = false) String openId,
			HttpServletResponse response) {

		// uiModel.addAttribute("openId", openId);
		return "page/showMap";
	}
	@RequestMapping(value = "showMap", produces = "text/html")
	public void getRecord(HttpServletRequest request,HttpServletResponse response){
		System.out.println("12");
		List<TDormbulid> list = GetMapInfo.getInfo();
		try {
			response.getWriter().write(JSONArray.fromObject(list).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
// 保存学生信息绑定
	@RequestMapping(value = "saveRepair", method = RequestMethod.POST, produces = "text/html")
	public String saveRepair(HttpServletRequest httpServletRequest,
			@RequestParam(value = "openId", required = false) String openId,
			@Valid WccRepairReg wccRepairReg, HttpServletResponse response)
			throws UnsupportedEncodingException {
		
	     if(null != wccRepairReg){
	        	if(null != openId){
	        		wccRepairReg.setOpenId(openId);
	        	}
	        	wccRepairReg.setInsertTime(new Date());
	        	wccRepairReg.persist();
	        }
		
		return null;
	}	
	
	// 保存学生信息绑定
		@RequestMapping(value = "addStu", method = RequestMethod.POST, produces = "text/html")
		public String addStu(
				HttpServletRequest httpServletRequest,
				@RequestParam(value = "openId", required = false) String openId,
				@Valid WccStu wccstu, HttpServletResponse response)
				throws UnsupportedEncodingException {
			
		     if(null != wccstu){
		        	if(null != openId){
		        		wccstu.setOpenId(openId);
		        	}
		        	wccstu.persist();
		        }
			
			return null;
		}	
	
		
		// 保存 报名信息
		@RequestMapping(value = "addEnlist", produces = "text/html")
		public String addEnlist(
				HttpServletRequest httpServletRequest,
				@RequestParam(value = "openId", required = false) String openId,
				@Valid WccEnList wccEnList, HttpServletResponse response)
				throws UnsupportedEncodingException {
			
		     if(null != wccEnList){
		        	if(null != openId){
		        		wccEnList.setOpenId(openId);
		        	}
		        	wccEnList.setInsertTime(new Date());
		        	wccEnList.persist();
		        }
			
			return null;
		}
	/**
	 * 查询页面
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "showEnlist", produces = "text/html")
	public String showEnlist(HttpServletRequest request, Model uiModel,
			@RequestParam(value = "openId", required = false) String openId,
			HttpServletResponse response) {
		uiModel.addAttribute("openId", openId);
		System.out.println("enter enlist page~");

		// uiModel.addAttribute("openId", openId);
		return "page/showEnlist";
	}

	/**
	 * 查询页面
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "showRepairPage", produces = "text/html")
	public String showRepairPage(HttpServletRequest request, Model uiModel,
			@RequestParam(value = "openId", required = false) String openId,
			HttpServletResponse response) {

		System.out.println("enter enlist page~");

		// uiModel.addAttribute("openId", openId);
		return "page/showRepairReg";
	}

	@RequestMapping(value = "saveBding", produces = "text/html")
	public String saveBding(@Valid WccStu wccStu, HttpServletRequest request,
			Model uiModel,
			@RequestParam(value = "openId", required = false) String openId,
			HttpServletResponse response) {
String result = "绑定成功!";
		System.out.println("dispayyy~~~");
		List<WccStu>  list =null;
		if(null != openId){
			list =	WccStu.getStuByOpenId(openId);
		}
		if(null != list && list.size() > 0){
			result = "您的微信已经绑定，不能重复绑定！";
		}else{
			if (null != wccStu) {
				wccStu.setInsertTime(new Date());
				wccStu.persist();
				if(null != openId){
					wccStu.setOpenId(openId);
				}
			}
		}
	PrintWriter pr = null;
	try {
		pr = response.getWriter();
		pr.write(result);
	} catch (IOException e) {
		e.printStackTrace();
	}finally{
		if(pr != null ){
			pr.close();
		}
	}
	
		return null;
	}
}

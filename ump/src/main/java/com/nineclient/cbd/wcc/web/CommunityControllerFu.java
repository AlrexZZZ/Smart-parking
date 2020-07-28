package com.nineclient.cbd.wcc.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nineclient.cbd.wcc.constant.CacluatePage;
import com.nineclient.cbd.wcc.model.WccAppartment;
import com.nineclient.cbd.wcc.model.WccItemotherinfo;
import com.nineclient.cbd.wcc.model.WccMalfunctionType;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpOperator;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;

@RequestMapping("/cdbWccAppartmentFu")
@Controller
@RooWebScaffold(path = "cdbWccAppartmentFu", formBackingObject = UmpOperator.class)
public class CommunityControllerFu {

	
	@RequestMapping(value="/TestCbdWccAppartmentFu",method = RequestMethod.GET, produces = "text/html")
	 public String createFu( Model uiModel,
			 HttpServletRequest request,
			 @RequestParam(value = "start", required = false) Integer start,
			 @RequestParam(value = "limit", required = false) Integer limit) {
	
		
		List<WccItemotherinfo> platformUser=WccItemotherinfo.getCBD_WCC(null);
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platformUsers = WccPlatformUser.findAllWccPlatformUsers(user);
		String platform="";
		for(WccPlatformUser use:platformUsers){
			platform+=use.getId()+",";
		}
		QueryModel<WccAppartment> qm = new QueryModel<WccAppartment>();
	//	qm.setStart(1);
    //	qm.setLimit(Integer.MAX_VALUE);
		PageModel<WccAppartment> pageModel =WccAppartment.getRecord(qm, null, null, platform.substring(0,platform.length()-1), null);
		uiModel.addAttribute("list",pageModel.getDataList());
		uiModel.addAttribute("platformUser", platformUser);
		return "cdbwccappartment/createFu";
   }
	

	@RequestMapping(value="/CdbWccAppartmentFu", produces = "application/json")
   public void createFu( String dtGridPager,HttpServletRequest request,Model model,HttpServletResponse response) throws Exception {
		Map parm = new HashMap<String, String>();
		String itemPk="";
		String otherType="";
		String otherTitle="";
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		//	List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
		
		Pager pager =null;
		Long totalCount = null;
		// 查找当用户的公司
		pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
		parm = pager.getParameters();
		itemPk=(String)parm.get("itemPk");
		otherType=(String)parm.get("otherType");
		otherTitle=(String)parm.get("otherTitle");
		int maxResults   =  pager.getPageSize() ;
		int totalPage    =  0;
		QueryModel<WccItemotherinfo> qm = new QueryModel<WccItemotherinfo>();
			
		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
		String platform="";
		for(WccPlatformUser use:platformUser){
			platform+=use.getId()+",";
		}
		QueryModel<WccAppartment> qm1 = new QueryModel<WccAppartment>();
	//	qm.setStart(1);
    //	qm.setLimit(Integer.MAX_VALUE);
		PageModel<WccAppartment> pageModel =WccAppartment.getRecord(qm1, null, null, platform.substring(0,platform.length()-1), null);
		String wccId="";
		qm.setStart(pager.getStartRecord());
		qm.setLimit(pager.getPageSize());
		for(WccAppartment wcc:pageModel.getDataList()){
			wccId+=wcc.getId()+",";
		}
		PageModel<WccItemotherinfo> pm=null;
		if(itemPk != null && !itemPk.equals("")){
			pm=WccItemotherinfo.getRecord(qm,itemPk,otherType,otherTitle);
		}
		else{
			pm=WccItemotherinfo.getRecord(qm,wccId,otherType,otherTitle);
		}
	   
		
       	try {
				totalCount =Long.parseLong(pm.getDataList().size()+"");
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
   		//pager.setRecordCount(Long.parseLong(pm.getTotalCount()+""));//设置总记录数
   		int totalpage=CacluatePage.totalPage(pager.getPageSize(), pm.getTotalCount());
       	List<WccItemotherinfo>  ceshi = pm.getDataList();
       		pager.setExhibitDatas(ceshi);
       		pager.setRecordCount(Long.parseLong(pm.getTotalCount()+""));//设置总记录数
       		pager.setPageCount(totalpage);
       		pager.setIsSuccess(true);
	    try {
	    	response.setContentType("application/json");//设置返回的数据为json对象
	    	JsonValueProcessor jsonProcessor = new DateJsonValueProcessor();
	    	JsonConfig jsonConfig=new JsonConfig();
	    	jsonConfig.setIgnoreDefaultExcludes(false);
	    	jsonConfig.setExcludes(new String[]{"platformUsers"});
	    	
	    	JSONObject pagerJSON = JSONObject.fromObject(pager,jsonConfig);
	    	response.setCharacterEncoding("UTF-8");
	    	response.setContentType("text/html;charset=UTF-8");
	    	response.getWriter().write(pagerJSON.toString());
	    } catch (IOException e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	    }
	
}

	//修改
	@SuppressWarnings("static-access")
	@RequestMapping(params = "update", produces = "text/html")
    public String createForm(HttpServletRequest request,Model uiModel,
    		@RequestParam(value = "dataId", required = false) String dataId,
			HttpServletResponse response) {
		   if(null != dataId && !"".equals(dataId)){
    	   WccItemotherinfo w  = new WccItemotherinfo();
    	   WccItemotherinfo wccItemotherinfo = w.findPubOrganization(Long.parseLong(dataId));
    	   SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
    	   String itemTimeStr=df.format(wccItemotherinfo.getPublicTime());
    	   
    	   
    	   String str=wccItemotherinfo.getOtherPic().substring(1,wccItemotherinfo.getOtherPic().length());
	        String[] itemImg=str.split(",");
	        uiModel.addAttribute("itemImg", itemImg);
    	   uiModel.addAttribute("wccItemotherinfo", wccItemotherinfo);
    	   uiModel.addAttribute("itemTimeStr", itemTimeStr);
       }
        return "cdbwccappartment/updateFormFu";
    }
	
	@SuppressWarnings("static-access")
	@RequestMapping(params = "form", produces = "text/html")
	 public String createFormte(HttpServletRequest request,Model uiModel,
				HttpServletResponse response) {
		
		return "cdbwccappartment/createFormFu";
		
	}
	
	@RequestMapping(value="/createCommunityFu", produces = "text/html")
	public String createCommunityFu(
			@RequestParam(value="itemStatus",required=true)String itemStatus,
	    	Model uiModel, HttpServletRequest httpServletRequest){
		
		
		PubOperator user = CommonUtils.getCurrentPubOperator(httpServletRequest);
		List<WccPlatformUser> platformUsers = WccPlatformUser.findAllWccPlatformUsers(user);
		String platform="";
		for(WccPlatformUser use:platformUsers){
			platform+=use.getId()+",";
		}
		QueryModel<WccAppartment> qm = new QueryModel<WccAppartment>();
	//	qm.setStart(1);
    //	qm.setLimit(Integer.MAX_VALUE);
		PageModel<WccAppartment> pageModel =WccAppartment.getRecord(qm, null, itemStatus, platform.substring(0,platform.length()-1), null);
		uiModel.addAttribute("list",pageModel.getDataList());
		uiModel.addAttribute("itemStatus", itemStatus);
		return "community/createFu";
	}
	
	@RequestMapping(value = "addStore", method = RequestMethod.POST, produces = "text/html")
    public String create(
    		@RequestParam(value="itemPkStr",required=true)String itemPkStr,
    		@Valid WccItemotherinfo wccItemotherinfo, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException, ParseException {
		PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
		System.out.println(pub);
		WccAppartment app=WccAppartment.findPubOrganization(Long.parseLong(itemPkStr));
		if(null!=wccItemotherinfo.getId() && !wccItemotherinfo.getId().equals("")){
			
			
			wccItemotherinfo.setPublicTime(new Date());
			wccItemotherinfo.setItemPk(app);
			if(wccItemotherinfo.getOtherTitle().equals("小区基本概况") || wccItemotherinfo.getOtherTitle()=="项目介绍"){
				wccItemotherinfo.setUrl("createItme");
			 }
			else	if(wccItemotherinfo.getOtherTitle().equals("小区地理位置与周边") || wccItemotherinfo.getOtherTitle()=="项目介绍"){
				wccItemotherinfo.setUrl("createItmeDitu");
			 }
			else	if(wccItemotherinfo.getOtherTitle().equals("小区物管信息") || wccItemotherinfo.getOtherTitle()=="项目介绍"){
				wccItemotherinfo.setUrl("createItmeWg");
			 }
			else	if(wccItemotherinfo.getOtherTitle().equals("项目介绍") || wccItemotherinfo.getOtherTitle()=="项目介绍"){
				wccItemotherinfo.setUrl("newCreateItme");
			 }
			else{
				wccItemotherinfo.setUrl("newCreateImg");
			}
			uiModel.asMap().clear();
			wccItemotherinfo.merge();
			System.out.println(wccItemotherinfo.getPublicTime()+"日期");
			System.out.println(wccItemotherinfo.getOtherIntro()+"内容");
			System.out.println(wccItemotherinfo.getOtherPic()+"图片");
			return "redirect:TestCbdWccAppartmentFu?";
		}
		else{
			wccItemotherinfo.setItemPk(app);
//		if (bindingResult.hasErrors() ) {
//			return "redirect:/cultureLife?form";
//		}
		wccItemotherinfo.setPublicTime(new Date());
		if(wccItemotherinfo.getOtherTitle().equals("小区基本概况")  || wccItemotherinfo.getOtherTitle()=="小区基本概况"){
			wccItemotherinfo.setUrl("createItme");
		 }
		else	if(wccItemotherinfo.getOtherTitle().equals("小区地理位置与周边") || wccItemotherinfo.getOtherTitle()=="小区地理位置与周边"){
			wccItemotherinfo.setUrl("createItmeDitu");
		 }
		else	if(wccItemotherinfo.getOtherTitle().equals("小区物管信息")  || wccItemotherinfo.getOtherTitle()=="小区物管信息"){
			wccItemotherinfo.setUrl("createItmeWg");
		 }
		else	if(wccItemotherinfo.getOtherTitle().equals("项目介绍") || wccItemotherinfo.getOtherTitle()=="项目介绍"){
			wccItemotherinfo.setUrl("newCreateItme");
		 }
		else{
			wccItemotherinfo.setUrl("newCreateImg");
		}
		uiModel.asMap().clear();
		wccItemotherinfo.persist();
		System.out.println(wccItemotherinfo.getPublicTime()+"日期");
		System.out.println(wccItemotherinfo.getOtherIntro()+"内容");
		System.out.println(wccItemotherinfo.getOtherPic()+"图片");
		return "redirect:TestCbdWccAppartmentFu?";
		}
	}
	
	
	//删除
		@RequestMapping(params = "delete", produces = "text/html")
	    public String deleteForm(HttpServletRequest request,
	    		@RequestParam(value = "dataId", required = true) String dataId,
				HttpServletResponse response) {
			if(null != dataId && !"".equals(dataId)){
				
				WccItemotherinfo w  = new WccItemotherinfo();
				w.setId(Long.parseLong(dataId));
				w.remove();	
			}
			
	        return null;
	    }
	  
		@RequestMapping(value = "del", produces = "text/html")
	    public void delForm(HttpServletRequest request,
	    		@RequestParam(value = "otherTitle", required = false) String otherTitle,
	    		@RequestParam(value = "itemPkStr", required = false) String itemPkStr,
	    		HttpServletResponse response) throws IOException {
			if((null != otherTitle && !"".equals(otherTitle)) && (null != itemPkStr && !"".equals(itemPkStr))){
					
				int i=WccItemotherinfo.getByType(otherTitle, itemPkStr);
//				Map<String, Object> map=new HashMap<String, Object>();
//				map.put("data", i);
				System.out.println(i+"~~~~~~~~~~~~~~`");
				//JSONObject	json = JSONObject. fromObject("{'data':'1'}");
				
				String str="{\"d\" : "+i+"}";
				response.getWriter().write(str);
				
			}
	       
	    }
}

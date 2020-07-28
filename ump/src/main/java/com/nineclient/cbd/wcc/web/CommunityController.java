package com.nineclient.cbd.wcc.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
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
import org.springframework.web.multipart.MultipartFile;

import com.nineclient.cbd.wcc.dto.WccAppartmentDTO;
import com.nineclient.cbd.wcc.dto.WccCultureLifeDTO;
import com.nineclient.cbd.wcc.model.WccAppartment;
import com.nineclient.cbd.wcc.model.WccCultureLife;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpOperator;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;

@RequestMapping("/cdbWccAppartment")
@Controller
@RooWebScaffold(path = "cdbWccAppartment", formBackingObject = UmpOperator.class)
public class CommunityController {

	@RequestMapping(value="/TestCdbWccAppartment",method = RequestMethod.GET, produces = "text/html")
    public String create( Model uiModel,
			 HttpServletRequest request,
			 @RequestParam(value = "start", required = false) Integer start,
			 @RequestParam(value = "limit", required = false) Integer limit) {
		
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
		List<WccAppartment> appArtment=WccAppartment.getWccAppartmentByName("", "");
		uiModel.addAttribute("platformUser", platformUser);
		uiModel.addAttribute("appArtment", appArtment);
		return "cdbwccappartment/create";
    }
	
	@RequestMapping(value="/CdbWccAppartment", produces = "application/json")
    public void create( String dtGridPager,HttpServletRequest request,Model model,HttpServletResponse response) throws Exception {
		Map parm = new HashMap<String, String>();
		String platformUsers="";
		String itemName="";
		String itemStatus="";
		String itemIntro="";
		
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
	//	List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
	
		Pager pager =null;
		Long totalCount = null;
		// 查找当用户的公司
	    pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
	    parm = pager.getParameters();
	    platformUsers=(String)parm.get("platformUsers");
	    itemName=(String)parm.get("itemName");
	    itemStatus=(String)parm.get("itemStatus");
	    itemIntro=(String)parm.get("itemIntro");
		int maxResults   = pager.getPageSize() ;
		int totalPage    = 0;
		QueryModel<WccAppartment> qm = new QueryModel<WccAppartment>();
		
    //	qm.putParams("insertTime", "2015-05-01");
    	qm.setStart(pager.getStartRecord()+1);
    	qm.setLimit(pager.getPageSize());
    	PageModel<WccAppartment> pm=null;
    	if(platformUsers != null && !"".equals(platformUsers)){
    		 pm=WccAppartment.getRecord(qm,itemName,itemStatus,platformUsers,itemIntro);
    	}else{
    		String platform="";
    		for(WccPlatformUser use:platformUser){
    			platform+=use.getId()+",";
    		}
    		 pm=WccAppartment.getRecord(qm,itemName,itemStatus,platform.substring(0, platform.length()-1),itemIntro);
    	}
    	
		try {
				totalCount =Long.parseLong(pm.getTotalCount()+"");
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
    		pager.setRecordCount(totalCount);//设置总记录数
    		//根据总记录数和每页的大小计算公有多少页
    		if((totalCount%maxResults) > 0){
    			totalPage = (int)((totalCount/maxResults)+1);
    		}else{
    			totalPage =(int)(totalCount/maxResults);
    		}
    		
        	List<WccAppartment>  ceshi =null;
        	List<WccAppartmentDTO> list=new ArrayList<WccAppartmentDTO>();
		//List  list =	WccRecordMsg.findWccRecordMsgEntries(firstResult, maxResults);
	
	//ceshi2 =	WccRecordMsg.findRcordByQuery(firstResult, maxResults, nickName, platFormId, itemTime, itemStatus, beginTime, endTime, itemAddress,company.getId());
        ceshi =	pm.getDataList();
        System.out.println(ceshi.size());
        if(null != ceshi && ceshi.size() > 0 ){
            for(WccAppartment wccApp:ceshi){
            String plIds = "";String names = "";
            if(null != wccApp.getPlatformUsers() && wccApp.getPlatformUsers().size() > 0){
            	Set <WccPlatformUser> ss  =	wccApp.getPlatformUsers();
            	Iterator<WccPlatformUser> it = ss.iterator();
            	
            	while (it.hasNext()){ 
            		WccPlatformUser u = it.next();
            	     if(null != u.getId() && !"".equals(u.getId())){
            	    	 plIds += u.getId()+",";
            	     }
                    if(null != u.getAccount() && !"".equals(u.getAccount())){
                    	names += u.getAccount()+",";
            	     }
            	
            	}
            }
            if(plIds != ""){
            	plIds = plIds.substring(0, (plIds.length()-1));
            }
            if(names != ""){
            	names = names.substring(0, (names.length()-1));
            }
            
            WccPlatformUser plf = new WccPlatformUser();
            plf.setAccount(names);plf.setRemark(plIds);
            list.add(new WccAppartmentDTO(wccApp.getId(),wccApp.getIsDelete(),wccApp.getInsertIp(),
        			wccApp.getInsertPk(),wccApp.getInsertTime(),wccApp.getUpdateIp(),
        			wccApp.getUpdatePk(),wccApp.getUpdateTime(),wccApp.getDeleteIp(),
        			wccApp.getDeletePk(),wccApp.getDeleteTime(),wccApp.getCompanyId(),
        			wccApp.getOrganizationPk(),plf,wccApp.getItemPick(),
        			wccApp.getItemName(),wccApp.getItemIntro(),wccApp.getItemAddress(),
        			wccApp.getItemStatus(),wccApp.getItemLng(),wccApp.getItemLat(),
        			wccApp.getItemTime(),wccApp.getItemPhone(),wccApp.getWccItemtherinfo()));
            }
            		
           }
        
        List<WccAppartmentDTO> dto=new ArrayList<WccAppartmentDTO>();
        if(list.size()>pager.getStartRecord()+pager.getPageSize()){
        	for(int i=pager.getStartRecord();i<pager.getStartRecord()+pager.getPageSize();i++){
        		dto.add(list.get(i));
        	}
        }
        else{
        	for(int i=pager.getStartRecord();i<list.size();i++){
        		dto.add(list.get(i));
        	}
        }
		pager.setExhibitDatas(dto);
		pager.setPageCount(totalPage);
		pager.setIsSuccess(true);
		//List<WccRecordMsg> list = WccRecordMsg.findAllWccRecordMsg();
	   try {
		response.setContentType("application/json");//设置返回的数据为json对象
		JsonValueProcessor jsonProcessor = new DateJsonValueProcessor();
        JsonConfig jsonConfig = new JsonConfig();
     //   jsonConfig.registerJsonValueProcessor(java.util.Date.class, jsonProcessor);
		JSONObject pagerJSON = JSONObject.fromObject(pager);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(pagerJSON.toString());
		//System.out.println(pagerJSON.toString());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
	
	
	/**
	 * 查询公众平台
	 * @param uiModel
	 * @param request
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryCdbWccAppartment", produces = "text/html")
	 public String queryPlatformUsers(
			 Model uiModel,
			 HttpServletRequest request,
			 @RequestParam(value = "start", required = false) Integer start,
			 @RequestParam(value = "limit", required = false) Integer limit
			 ) throws ServletException, IOException {
		  PubOperator operator = CommonUtils.getCurrentPubOperator(request);
		  Map parmMap = new HashMap();
		  WccAppartment platformUser = new WccAppartment();
		  QueryModel<WccAppartment> qm = new QueryModel<WccAppartment>(platformUser, start, limit);
		  qm.getInputMap().putAll(parmMap);
		  PageModel<WccAppartment> pm = WccAppartment.getQueryAppartment(qm,operator);
		     pm.setPageSize(limit);
			 pm.setStartIndex(start);
		 uiModel.addAttribute("WccPlatformUsers", pm.getDataList());
		 uiModel.addAttribute("maxPages", pm.getTotalPage());
		 uiModel.addAttribute("page", pm.getPageNo());
		 uiModel.addAttribute("limit", pm.getPageSize());
		 
     return "wccplatformusers/result";
	 }


	@RequestMapping(value = "addStore", method = RequestMethod.POST, produces = "text/html")
    public String createAdd(
    		@RequestParam(value="itemTimeStr",required=true)String itemTimeStr,
    		@Valid WccAppartment wccAppartment, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException, ParseException {
			if (bindingResult.hasErrors() ) {
				return "redirect:/cultureLife?form";
			}
			PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
			System.out.println(pub);
			if( null!=wccAppartment.getId() && !wccAppartment.getId().equals("")  ){
//			WccAppartment wccApp=WccAppartment.findPubOrganization(wccAppartment.getId());
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			wccAppartment.setItemTime(df.parse(itemTimeStr));
//			wccApp.setItemName(wccAppartment.getItemName());
//			wccApp.setItemIntro(wccAppartment.getItemIntro());
//			wccApp.setItemAddress(wccAppartment.getItemAddress());
//			wccApp.setItemStatus(wccAppartment.getItemStatus());
//			wccApp.setItemLng(wccAppartment.getItemLng());
//			wccApp.setItemLat(wccAppartment.getItemLat());
//			wccApp.setItemPick(wccAppartment.getItemPick());
//			wccApp.setItemPhone(wccAppartment.getItemPhone());
			uiModel.asMap().clear();
			wccAppartment.merge();
			return "redirect:TestCdbWccAppartment?";
			}
			else{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			wccAppartment.setItemTime(df.parse(itemTimeStr));
			uiModel.asMap().clear();
			wccAppartment.persist();
			System.out.println(wccAppartment.getItemName()+"sssssssss");
		
			return "redirect:TestCdbWccAppartment?";
			
		}
		
	}
	
	//删除
	@RequestMapping(params = "delete", produces = "text/html")
    public String deleteForm(HttpServletRequest request,
    		@RequestParam(value = "dataId", required = true) String dataId,
			HttpServletResponse response) {
		if(null != dataId  && !"".equals(dataId)){
			
			WccAppartment w  = new WccAppartment();
			w.setId(Long.parseLong(dataId));
			w.remove();	
		}
		
        return null;
    }

	
	
	
	@SuppressWarnings("static-access")
	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(HttpServletRequest request,Model uiModel,
    		@RequestParam(value = "dataId", required = false) String dataId,
			HttpServletResponse response) {
		   if(null != dataId && !"".equals(dataId)){
    	   WccAppartment w  = new WccAppartment();
    	   WccAppartment wccAppartment = w.findPubOrganization(Long.parseLong(dataId));
    	   String pltIds = "";
    	      if(null != wccAppartment.getPlatformUsers()){
    	    	  
    	        	Set <WccPlatformUser> ss  =	wccAppartment.getPlatformUsers();
    	        	Iterator<WccPlatformUser> it = ss.iterator();
    	        	while (it.hasNext()){ 
    	        		WccPlatformUser u = it.next();
    	        	     if(null != u.getId() && !"".equals(u.getId())){
    	        	    	 pltIds += u.getId()+",";
    	        	     }
    	        	}
    	        	
    	        }
    	   	if(null != pltIds && !"".equals(pltIds)){
        		uiModel.addAttribute("pltIds", pltIds.subSequence(0, (pltIds.length()-1)));
        	}else{
        		uiModel.addAttribute("pltIds", "");
        	}  
    		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
    		String itemTimeStr="";
    		if(wccAppartment.getItemTime()!=null && !wccAppartment.getItemTime().equals("")){
    			itemTimeStr=df.format(wccAppartment.getItemTime());
    		}
    	   uiModel.addAttribute("wccAppartment", wccAppartment);
    	   uiModel.addAttribute("itemTimeStr", itemTimeStr);
       }
        return "cdbwccappartment/createForm";
    }
	
	
}

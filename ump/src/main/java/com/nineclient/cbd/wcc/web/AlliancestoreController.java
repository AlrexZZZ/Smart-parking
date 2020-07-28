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

import com.nineclient.cbd.wcc.dto.WccAllancestoreDTO;
import com.nineclient.cbd.wcc.dto.WccAppartmentDTO;
import com.nineclient.cbd.wcc.dto.WccLifeHelperDTO;
import com.nineclient.cbd.wcc.dto.WccMyCardDTO;
import com.nineclient.cbd.wcc.model.WccAlliancestore;
import com.nineclient.cbd.wcc.model.WccAlliancestoreType;
import com.nineclient.cbd.wcc.model.WccAppartment;
import com.nineclient.cbd.wcc.model.WccMycard;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpOperator;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;
//周边特惠
@RequestMapping("/Alliancestore")
@Controller
@RooWebScaffold(path = "Alliancestore", formBackingObject = UmpOperator.class)
public class AlliancestoreController {
	@RequestMapping(value="/TestAlliancestore",method = RequestMethod.GET, produces = "text/html")
    public String create( Model uiModel,
			 HttpServletRequest request,
			 @RequestParam(value = "start", required = false) Integer start,
			 @RequestParam(value = "limit", required = false) Integer limit) {
		List<WccAlliancestoreType> type=WccAlliancestoreType.getwccAlliancestoreType();
		uiModel.addAttribute("type",type);
		return "Alliancestore/create";
    }
	
	
	
	@RequestMapping(value="/WccAlliancestore", produces = "application/json")
    public void create( String dtGridPager,HttpServletRequest request,Model model,HttpServletResponse response) throws Exception {
		Map parm = new HashMap<String, String>();
		String platformUser="";
		String storeType="";
		String storeName="";
		String typeStr="";
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platformUsers = WccPlatformUser.findAllWccPlatformUsers(user);
	//	List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
	
		Pager pager =null;
		Long totalCount = null;
		// 查找当用户的公司
	    pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
	    parm = pager.getParameters();
	    platformUser=(String)parm.get("platformUser");
	    storeType=(String)parm.get("storeType");
	    storeName=(String)parm.get("storeName");
	    typeStr=(String)parm.get("typeStr");
		int maxResults   = pager.getPageSize() ;
		int totalPage    = 0;
		QueryModel<WccAlliancestore> qm = new QueryModel<WccAlliancestore>();
		if(platformUser != null && !platformUser.equals("")){
    		qm.putParams("platformUser", platformUser);
    	}
    	else{
    		String platform="";
    		for(WccPlatformUser use:platformUsers){
    			platform+=use.getId()+",";
    		}
    		qm.putParams("platformUser", platform);
    	}
		qm.putParams("storeName", storeName);
		qm.putParams("storeType", storeType);
		qm.putParams("type", typeStr);
    	qm.setStart(pager.getStartRecord());
    	qm.setLimit(pager.getPageSize());
		PageModel<WccAlliancestore> pm=WccAlliancestore.getRecord(qm);
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
    		
        	List<WccAlliancestore>  ceshi =null;
        	List<WccAllancestoreDTO> list=new ArrayList<WccAllancestoreDTO>();
		//List  list =	WccRecordMsg.findWccRecordMsgEntries(firstResult, maxResults);
	
	//ceshi2 =	WccRecordMsg.findRcordByQuery(firstResult, maxResults, nickName, platFormId, itemTime, itemStatus, beginTime, endTime, itemAddress,company.getId());
        ceshi =	pm.getDataList();
        System.out.println(ceshi.size());
        if(null != ceshi && ceshi.size() > 0 ){
            for(WccAlliancestore wccApp:ceshi){
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
            list.add(new WccAllancestoreDTO(wccApp.getId(),wccApp.getIsDelete(),wccApp.getInsertIp(),
        			wccApp.getInsertPk(),wccApp.getInsertTime(),wccApp.getUpdateIp(),
        			wccApp.getUpdatePk(),wccApp.getUpdateTime(),wccApp.getDeleteIp(),
        			wccApp.getDeletePk(),wccApp.getDeleteTime(),wccApp.getCompanyId(),
        			wccApp.getOrganizationPk(),plf,wccApp.getStoreName(),
        			wccApp.getStoreAddress(),wccApp.getStoreLng(),wccApp.getStoreLat(),
        			wccApp.getStorePhone(),wccApp.getStorePic(),wccApp.getStoreIntro(),wccApp.getStoreType(),wccApp.getTypeStr(),wccApp.getWccAlliancestoreType()));
            		
            }
            		
           }
        List<WccAllancestoreDTO> dto=new ArrayList<WccAllancestoreDTO>();
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
	
	
	
	@SuppressWarnings("static-access")
	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(HttpServletRequest request,Model uiModel,
    		@RequestParam(value = "dataId", required = false) String dataId,
			HttpServletResponse response) {
		   if(null != dataId && !"".equals(dataId)){
    	   WccAlliancestore w  = new WccAlliancestore();
    	   WccAlliancestore wccAlliancestore = w.findPubOrganization(Long.parseLong(dataId));
    	   String pltIds = "";
    	      if(null != wccAlliancestore.getPlatformUsers()){
    	    	  
    	        	Set<WccPlatformUser> ss  =wccAlliancestore.getPlatformUsers();
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
    		
    	   uiModel.addAttribute("wccAlliancestore", wccAlliancestore);
    	   
       }
		List<WccAlliancestoreType> type=WccAlliancestoreType.getwccAlliancestoreType();
		uiModel.addAttribute("type",type);
        return "Alliancestore/createForm";
    }
	
	//修改，保存
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String createAdd(
    		@Valid WccAlliancestore wccAlliancestore, BindingResult bindingResult, 
    		@RequestParam(value = "type", required = false) String type,
    		Model uiModel, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException, ParseException {
//			if (bindingResult.hasErrors() ) {
//				return "redirect:/cultureLife?form";
//			}
			PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
			System.out.println(pub);
			if( null!=wccAlliancestore.getId() && !wccAlliancestore.getId().equals("") ){
				uiModel.asMap().clear();
				wccAlliancestore.setWccAlliancestoreType(WccAlliancestoreType.getById(Long.parseLong(type)));
				wccAlliancestore.merge();
				return "redirect:/Alliancestore/TestAlliancestore?";
			}
			else{
			uiModel.asMap().clear();
			wccAlliancestore.setWccAlliancestoreType(WccAlliancestoreType.getById(Long.parseLong(type)));
			wccAlliancestore.persist();
		
			return "redirect:/Alliancestore/TestAlliancestore?";
			
		}
		
	}
	//删除
		@RequestMapping(params = "delete", produces = "text/html")
	    public String deleteForm(HttpServletRequest request,
	    		@RequestParam(value = "dataId", required = true) String dataId,
				HttpServletResponse response) {
			if(null != dataId && !"".equals(dataId)){
				
				WccAlliancestore w  = new WccAlliancestore();
				w.setId(Long.parseLong(dataId));
				w.remove();	
			}
			
	        return null;
	    }
	
	
}

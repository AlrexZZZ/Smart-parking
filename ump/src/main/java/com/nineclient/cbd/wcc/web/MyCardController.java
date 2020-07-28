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

import com.nineclient.cbd.wcc.dto.WccAppartmentDTO;
import com.nineclient.cbd.wcc.dto.WccMyCardDTO;
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

@RequestMapping("/myCard")
@Controller
@RooWebScaffold(path = "myCard", formBackingObject = UmpOperator.class)
public class MyCardController {
	@RequestMapping(value="/TestMyCard",method = RequestMethod.GET, produces = "text/html")
    public String create( Model uiModel,
			 HttpServletRequest request,
			 @RequestParam(value = "start", required = false) Integer start,
			 @RequestParam(value = "limit", required = false) Integer limit) {
		
		return "myCard/create";
    }
	
	
	
	@RequestMapping(value="/WccMyCard", produces = "application/json")
    public void create( String dtGridPager,HttpServletRequest request,Model model,HttpServletResponse response) throws Exception {
		Map parm = new HashMap<String, String>();
		String platFormId="";
		String nickName="";
		String startTimeId="";
		String endTimeId="";
		//查询当前帐号下的公众平台
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
	//	List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
		
		Pager pager =null;
		Long totalCount = null;
		// 查找当用户的公司
	    pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
	    parm = pager.getParameters();
	    platFormId=(String)parm.get("platFormId");
	    nickName=(String)parm.get("nickName");
	    startTimeId=(String)parm.get("startTimeId");
	    endTimeId=(String)parm.get("endTimeId");
		int maxResults   = pager.getPageSize() ;
		int totalPage    = 0;
		QueryModel<WccMycard> qm = new QueryModel<WccMycard>();
		//判断是否是初始查询
		if(platFormId != null && !platFormId.equals("")){
    		qm.putParams("platFormId", platFormId);
    	}
    	else{
    		String platform="";
    		for(WccPlatformUser use:platformUser){
    			platform+=use.getId()+",";
    		}
    		qm.putParams("platFormId", platform);
    	}
		qm.putParams("nickName", nickName);
		qm.putParams("startTimeId", startTimeId);
		qm.putParams("endTimeId", endTimeId);
    	qm.setStart(pager.getStartRecord());
    	qm.setLimit(pager.getPageSize());
		PageModel<WccMycard> pm=WccMycard.getRecord(qm);
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
    		
        	List<WccMycard>  ceshi =null;
        	List<WccMyCardDTO> list=new ArrayList<WccMyCardDTO>();
		//List  list =	WccRecordMsg.findWccRecordMsgEntries(firstResult, maxResults);
	
	//ceshi2 =	WccRecordMsg.findRcordByQuery(firstResult, maxResults, nickName, platFormId, itemTime, itemStatus, beginTime, endTime, itemAddress,company.getId());
        ceshi =	pm.getDataList();
        System.out.println(ceshi.size());
        if(null != ceshi && ceshi.size() > 0 ){
            for(WccMycard wccApp:ceshi){
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
            list.add(new WccMyCardDTO(wccApp.getId(),wccApp.getIsDelete(),wccApp.getInsertIp(),
        			wccApp.getInsertPk(),wccApp.getInsertTime(),wccApp.getUpdateIp(),
        			wccApp.getUpdatePk(),wccApp.getUpdateTime(),wccApp.getDeleteIp(),
        			wccApp.getDeletePk(),wccApp.getDeleteTime(),wccApp.getCompanyId(),
        			wccApp.getOrganizationPk(),plf,wccApp.getCardTitle(),
        			wccApp.getCardTime(),wccApp.getCardPic(),wccApp.getCardName(),
        			wccApp.getContentTitle(),wccApp.getCardIntro(),wccApp.getCardUrl()));
            }
           }
        List<WccMyCardDTO> dto=new ArrayList<WccMyCardDTO>();
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
    	   WccMycard w  = new WccMycard();
    	   WccMycard wccMycard = w.findPubOrganization(Long.parseLong(dataId));
    	   String pltIds = "";
    	      if(null != wccMycard.getPlatformUsers()){
    	    	  
    	        	Set<WccPlatformUser> ss  =wccMycard.getPlatformUsers();
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
    		
    	   uiModel.addAttribute("wccMycard", wccMycard);
    	   
       }
        return "MyCard/createForm";
    }
	
	//修改，保存
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String createAdd(
    		@RequestParam(value = "cardTimeStr", required = false) String cardTimeStr,
    		@Valid WccMycard wccMycard, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException, ParseException {
//			if (bindingResult.hasErrors() ) {
//				return "redirect:/cultureLife?form";
//			}
			PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
			System.out.println(pub);
			if( null!=wccMycard.getId() && !wccMycard.getId().equals("") ){
				uiModel.asMap().clear();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				wccMycard.setCardTime(df.parse(cardTimeStr));
				wccMycard.merge();
				return "redirect:/myCard/TestMyCard?";
			}
			else{
			wccMycard.setCardTime(new Date());
			uiModel.asMap().clear();
			wccMycard.persist();
		
			return "redirect:/myCard/TestMyCard?";
			
		}
		
	}
	//删除
		@RequestMapping(params = "delete", produces = "text/html")
	    public String deleteForm(HttpServletRequest request,
	    		@RequestParam(value = "dataId", required = true) String dataId,
				HttpServletResponse response) {
			if(null != dataId && !"".equals(dataId)){
				
				WccMycard w  = new WccMycard();
				w.setId(Long.parseLong(dataId));
				w.remove();	
			}
			
	        return null;
	    }
	
	
}

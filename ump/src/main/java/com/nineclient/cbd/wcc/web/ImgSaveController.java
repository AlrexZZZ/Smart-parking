package com.nineclient.cbd.wcc.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
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
import com.nineclient.cbd.wcc.dto.WccAppartmentDTO;
import com.nineclient.cbd.wcc.dto.WccImgSaveDTO;
import com.nineclient.cbd.wcc.dto.WccMyCardDTO;
import com.nineclient.cbd.wcc.model.WccAlliancestoreType;
import com.nineclient.cbd.wcc.model.WccAppartment;
import com.nineclient.cbd.wcc.model.WccImgSave;
import com.nineclient.cbd.wcc.model.WccItemotherinfo;
import com.nineclient.cbd.wcc.model.WccMalfunctionType;
import com.nineclient.model.PubOperator;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;

@RequestMapping("/ImgSave")
@Controller
@RooWebScaffold(path = "cbdwcc/imgSave", formBackingObject = WccRecordMsg.class)
public class ImgSaveController {
	@RequestMapping(value = "/TestImgSave", produces = "text/html")
	public String showCulture(HttpServletRequest request,Model uiModel,
			HttpServletResponse response) {
	    return "imgSave/create";
	}
	   @RequestMapping(value="/loadFaultServiceType", produces = "application/json")
	   public void createFu( String dtGridPager,HttpServletRequest request,Model model,HttpServletResponse response) throws Exception {
		   Map parm = new HashMap<String, String>();
			String platformUsers="";
			String type="";
			PubOperator user = CommonUtils.getCurrentPubOperator(request);
			List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
		
			Pager pager =null;
			Long totalCount = null;
			// 查找当用户的公司
		    pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
		    parm = pager.getParameters();
		    platformUsers=(String)parm.get("platformUsers");
		    type=(String)parm.get("type");
			int maxResults   = pager.getPageSize() ;
			int totalPage    = 0;
			QueryModel<WccImgSave> qm = new QueryModel<WccImgSave>();
			if(platformUsers != null && !"".equals(platformUsers)){
				qm.putParams("platformUsers", platformUsers);
			}else{
	    		String platform="";
	    		for(WccPlatformUser use:platformUser){
	    			platform+=use.getId()+",";
	    		}
	    		qm.putParams("platformUsers", platform);
			}
			
			qm.putParams("type", type);
	    //	qm.putParams("insertTime", "2015-05-01");
	    	qm.setStart(pager.getStartRecord());
	    	qm.setLimit(pager.getPageSize());
			PageModel<WccImgSave> pm=WccImgSave.getRecord(qm);
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
	    		
	        	List<WccImgSave>  ceshi =null;
	        	List<WccImgSaveDTO> list=new ArrayList<WccImgSaveDTO>();
			//List  list =	WccRecordMsg.findWccRecordMsgEntries(firstResult, maxResults);
		
		//ceshi2 =	WccRecordMsg.findRcordByQuery(firstResult, maxResults, nickName, platFormId, itemTime, itemStatus, beginTime, endTime, itemAddress,company.getId());
	        ceshi =	pm.getDataList();
	        System.out.println(ceshi.size());
	        if(null != ceshi && ceshi.size() > 0 ){
	            for(WccImgSave wccApp:ceshi){
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
	            list.add(new WccImgSaveDTO(wccApp.getId(),plf,wccApp.getType(),wccApp.getImgPath()));
	            }
	            		
	           }
	        List<WccImgSaveDTO> dto=new ArrayList<WccImgSaveDTO>();
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
	 public String createFormte(HttpServletRequest request,Model uiModel,
			 @RequestParam(value="dataId",required=false)String dataId,
				HttpServletResponse response) {
		if(dataId!=null && !dataId.equals("")){
			WccImgSave wccAlliancestoreType=WccImgSave.getById(Long.parseLong(dataId));
			uiModel.addAttribute("wccAlliancestoreType", wccAlliancestoreType);
	    	   String pltIds = "";
	    	      if(null != wccAlliancestoreType.getPlatformUsers()){
	    	    	  
	    	        	Set <WccPlatformUser> ss  =	wccAlliancestoreType.getPlatformUsers();
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
		}
		
	    return "imgSave/createType";
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(
    		@Valid WccImgSave wccImgSave, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException, ParseException {
			if(wccImgSave.getId()==null || wccImgSave.getId().equals("")){
				wccImgSave.persist();
			}else{
				wccImgSave.merge();
			}
			
			return "redirect:ImgSave/TestImgSave?";
	}
	@SuppressWarnings("static-access")
	@RequestMapping(params = "delete", produces = "text/html")
	 public String delete(HttpServletRequest request,Model uiModel,
			 @RequestParam(value="dataId",required=false)String dataId,
				HttpServletResponse response) {
		if(null != dataId  && !"".equals(dataId)){
			WccImgSave w  = new WccImgSave();
			w.setId(Long.parseLong(dataId));
			w.remove();	
		}
	
		return "redirect:ImgSave/TestImgSave?";
	}
	
}

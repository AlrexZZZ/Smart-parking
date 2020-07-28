package com.nineclient.cbd.wcc.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
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

import net.sf.json.JSONArray;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.nineclient.cbd.wcc.constant.CacluatePage;
import com.nineclient.cbd.wcc.dto.CbdWccProprietorDTO;
import com.nineclient.cbd.wcc.dto.WccAppartmentDTO;
import com.nineclient.cbd.wcc.dto.WccFeesDTO;
import com.nineclient.cbd.wcc.model.CbdWccProprietor;
import com.nineclient.cbd.wcc.model.WccAppartment;
import com.nineclient.cbd.wcc.model.WccFees;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;

@RequestMapping("/wccFees")
@Controller
@RooWebScaffold(path = "cbdwcc/wccFees", formBackingObject = WccRecordMsg.class)
public class FeesController {

	/**
	 * 粉丝记录查询
	 * 
	 * @param uiModel
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException 
	 */
	
	@ResponseBody
	@RequestMapping(value = "loadCulture", produces = "application/json")
	public void loadCulture(String dtGridPager,HttpServletRequest request,Model model,HttpServletResponse response) throws UnsupportedEncodingException, ParseException {
		
	
		
		
		Pager pager =null;
		Map parm = new HashMap<String, String>();
		String nickName = "";
		String platFormId = "";
		String beginTime = "";
		String endTime = "";
	    String summary = "";
	    String state = "";
	    String monthStr ="";
	    String itemName = "";
	    PubOperator user = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
		
		try {
			 pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			 parm  = pager.getParameters();
				 nickName = (String) parm.get("nickName");
				 platFormId = (String) parm.get("platFormId");
				 beginTime = (String) parm.get("beginTime");
				 endTime = (String) parm.get("endTime");
				 summary = (String) parm.get("summary");
				 state = (String) parm.get("state");
				 monthStr = (String) parm.get("monthStr");
				 itemName = (String) parm.get("itemName");
				 String  s =  (String) parm.get("restart");
				 if(s.equals("y")){
					 pager.setStartRecord(0);
					 pager.setNowPage(1);
				 }
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        	QueryModel<WccFees> qm = new QueryModel<WccFees>();
        	if(platFormId != null && !"".equals(platFormId)){
        		qm.putParams("platFormId", platFormId);
       	}else{
       		String platform="";
       		for(WccPlatformUser use:platformUser){
       			platform+=use.getId()+",";
       		}
       		qm.putParams("platFormId", platform.substring(0, platform.length()-1));
       	}
        	
        	qm.putParams("summary", summary);
        	qm.putParams("state", state);
        	qm.putParams("monthStr", monthStr);
        	qm.putParams("itemName", itemName);
        	qm.putParams("beginTime", beginTime);
        	qm.putParams("endTime", endTime);
        	qm.setStart(pager.getStartRecord());
        	qm.setLimit(pager.getPageSize());
        	PageModel <WccFees> p =	WccFees.getRecord(qm);

        	CacluatePage.totalPage(p.getPageSize(), p.getTotalCount());
        	List<WccFees> lit =	p.getDataList();

        List<WccFeesDTO> list = new ArrayList<WccFeesDTO>();
        if(null != lit && lit.size() > 0 ){
        for(WccFees s:lit){
        	String plIds = "";String names = "";
        if(null != s.getPlatformUsers() && s.getPlatformUsers().size() > 0){
        	Set <WccPlatformUser> ss  =	s.getPlatformUsers();
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
        list.add(new WccFeesDTO(s.getId(), s.getCbdWccProprietor().getWccappartment().getItemName(),s.getSummary(), s.getCbdWccProprietor().getName(),s.getCbdWccProprietor().getPhone(),s.getCbdWccProprietor().getDoorplate(),s.getMonth(),s.getState(), s.getAmount(),s.getOtherUrl(), s.getVersion(), plf, s.getInsertTime(),s.getMonthStr()));
   
     
         }
        		
       }
        List<WccFeesDTO> dto=new ArrayList<WccFeesDTO>();
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
		if(list.size() > 0){
			pager.setExhibitDatas(dto);
		}else{
			pager.setExhibitDatas(null);
		}
		
		pager.setPageCount(CacluatePage.totalPage(p.getPageSize(), p.getTotalCount()));
		pager.setRecordCount(Long.parseLong(p.getTotalCount()+""));//设置总记录数
		pager.setIsSuccess(true);
	   try {
		response.setContentType("application/json");//设置返回的数据为json对象
		JsonValueProcessor jsonProcessor = new DateJsonValueProcessor();
        JsonConfig jsonConfig = new JsonConfig();
//        jsonConfig.setIgnoreDefaultExcludes(false);
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, jsonProcessor);
//        jsonConfig.setExcludes(new String[]{"platformUsers","tempappartment","company","organization","wccplatformuser"}); 
        
		JSONObject pagerJSON = JSONObject.fromObject(pager);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(pagerJSON.toString());
	} catch (IOException e){
		e.printStackTrace();
	}
}
	@ResponseBody
	@RequestMapping(value="getProInfo",produces="application/json")
	public void getProInfo(HttpServletRequest request,Model model,HttpServletResponse response,
			@RequestParam(value = "phoneNum", required = false) String phoneNum){
		
		
		List<CbdWccProprietor> proList =new ArrayList<CbdWccProprietor>();
		 List<CbdWccProprietorDTO>	 dlist=null;
		if(null != phoneNum && !"".equals(phoneNum)){
			///////////////////////////////////////////
			CbdWccProprietor proList1 =  CbdWccProprietor.findCbdWccProprietor(Long.parseLong(phoneNum));
			proList.add(proList1);
			if(null != proList && proList.size() > 0){
			dlist  = new  ArrayList<CbdWccProprietorDTO>();
			///封装dto
			for(CbdWccProprietor s:proList){
				WccAppartment wccappartment = new WccAppartment();
					CbdWccProprietorDTO d = new CbdWccProprietorDTO();
				    d.setId(s.getId());
					d.setTempphone(s.getPhone());
					d.setTempname(s.getName());
					d.setTempdoorplate(s.getDoorplate());
					if(null != s.getWccappartment()){
						wccappartment.setItemName(s.getWccappartment().getItemName());
						wccappartment.setId(s.getWccappartment().getId());
						d.setTempappartment(wccappartment);
					}
					
					dlist.add(d);
					
				}
			}	//返回json string
				if(null != dlist && dlist.size() > 0){
				
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html;charset=UTF-8");
					try {
					  JSONObject ja = JSONObject.fromObject(dlist.get(0));
					  response.getWriter().write(ja.toString());
					 
					} catch (IOException e) {
						e.printStackTrace();
					}
						
				}
		/////////////////////////////////////////////////////////////		
		}else{
			
			proList =  CbdWccProprietor.getProprietorsList(null, null,null, null);
			String phone = "";
			if(null != proList && proList.size() > 0){
			
			for(CbdWccProprietor s:proList){
					phone +=s.getPhone()+"("+s.getWccappartment().getItemName()+"，"+s.getDoorplate()+")"+s.getId()+",";
   //				phone +=s.getPhone()+"_("+s.getWccappartment().getItemName()+"，"+s.getDoorplate()+")_"+s.getId()+",";
				}
			}	
				if(null != phone &&!"".equals(phone)){
					
					phone = phone.substring(0, (phone.length()-1));
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html;charset=UTF-8");
					try {
						response.getWriter().write(phone);
					} catch (IOException e) {
						e.printStackTrace();
					}
						
				}
		}
	}	
	

	
	/**
	 * 粉丝记录页面
	 * 
	 * @param 
	 * @return
	 */
	
	@RequestMapping(value = "showRecord", produces = "text/html")
	public String showRecord(HttpServletRequest request,Model uiModel,
			HttpServletResponse response) {
//		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		
		
		
//		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
//		uiModel.addAttribute("platformUser", platformUser);
	
		
		List<WccPlatformUser> list = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
		
/*		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
		String platform="";
		for(WccPlatformUser use:platformUser){
			platform+=use.getId()+",";
		}
		QueryModel<WccAppartment> qm = new QueryModel<WccAppartment>();
	//	qm.setStart(1);
    //	qm.setLimit(Integer.MAX_VALUE);
		PageModel<WccAppartment> pageModel =WccAppartment.getRecord(qm, null, "2", platform.substring(0,platform.length()-1), null);
		uiModel.addAttribute("list2", pageModel.getDataList());*/
		
		List<WccAppartment> list2=WccAppartment.getWccAppartmentByXp(null, "2");
		uiModel.addAttribute("list2",list2);
		uiModel.addAttribute("plats", list);
	   return "wccFees/wccFees";
	}
   
	@SuppressWarnings("static-access")
	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(HttpServletRequest request,Model uiModel,
    		@RequestParam(value = "dataId", required = false) String dataId,
			HttpServletResponse response) {
		
		
       if(null != dataId && !"".equals(dataId)){
    	   WccFees w  = new WccFees();
    	   WccFees wccFees = w.findWccFees(Long.parseLong(dataId));
    	   String pltIds = "";
    	      if(null != wccFees.getPlatformUsers()){
    	    	  
    	        	Set <WccPlatformUser> ss  =	wccFees.getPlatformUsers();
    	        	Iterator<WccPlatformUser> it = ss.iterator();
    	        	while (it.hasNext()){ 
    	        		WccPlatformUser u = it.next();
    	        	     if(null != u.getId() && !"".equals(u.getId())){
    	        	    	 pltIds += u.getId()+",";
    	        	     }
    	        	}
    	        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	        	if(null != wccFees.getInsertTime()){
    	        		uiModel.addAttribute("insertTimeStr", df.format(wccFees.getInsertTime()));
    	        	}
    	        	
    	        }
    	   	if(null != pltIds && !"".equals(pltIds)){
        		uiModel.addAttribute("pltIds", pltIds.subSequence(0, (pltIds.length()-1)));
        	}else{
        		uiModel.addAttribute("pltIds", "");
        	}  
    	   uiModel.addAttribute("wccFees", wccFees);
    	   
       }
        return "wccFees/create";
    }

	@RequestMapping(params = "delete", produces = "text/html")
    public String deleteForm(HttpServletRequest request,
    		@RequestParam(value = "dataId", required = true) String dataId,
			HttpServletResponse response) {
		if(null != dataId && !"".equals(dataId)){
			
			WccFees w  = new WccFees();
			w.setId(Long.parseLong(dataId));
			w.remove();	
		}
		
        return null;
    }
	
	
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WccFees wccFees, BindingResult bindingResult,
    		@RequestParam(value = "insertTimeStr", required = false) String insertTimeStr,
    		Model uiModel, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
//		PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
		if (bindingResult.hasErrors() ) {
			return "redirect:/wccFees?form";
		}
	//	将月份转化为date
		if(null != wccFees.getMonthStr() && !"".equals(wccFees.getMonthStr())){
		String mStr = wccFees.getMonthStr();
		       mStr = mStr.substring(0, 4)+"-"+mStr.substring(4, mStr.length())+"-01";
		       DateUtil.formateDate(mStr, "yyyy-MM-dd");
		       wccFees.setMonth(DateUtil.formateDate(mStr, "yyyy-MM-dd"));
			}
		
	if(null != wccFees.getId() && !"".equals(wccFees.getId())){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if(insertTimeStr != null && !"".equals(insertTimeStr)){
			try {
				wccFees.setInsertTime(df.parse(insertTimeStr));
				wccFees.setInsertTimeStr(null);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		//先查询出实体
		  WccFees wccFeesUpdate = WccFees.findWccFees(wccFees.getId());
		  wccFeesUpdate.setPlatformUsers(wccFees.getPlatformUsers());
		  
		  wccFeesUpdate.setCellphone(wccFees.getCellphone());
		  wccFeesUpdate.setSummary(wccFees.getSummary());
		  wccFeesUpdate.setCbdWccProprietor(wccFees.getCbdWccProprietor());
		  wccFeesUpdate.setMonthStr(wccFees.getMonthStr());
		  wccFeesUpdate.setState(wccFees.getState());
		  
//		  wccFeesUpdate.setInsertTime(wccFees.getInsertTime());
//		  wccFeesUpdate.setDetailContent(wccFees.getDetailContent());
		 uiModel.asMap().clear();
		 //再更新数据
		 wccFeesUpdate.merge();
		}else{
			uiModel.asMap().clear();
			wccFees.setInsertTime(new Date());
			wccFees.persist();
		}
	
		return "redirect:/wccFees/showRecord?";
	}
	
	
}

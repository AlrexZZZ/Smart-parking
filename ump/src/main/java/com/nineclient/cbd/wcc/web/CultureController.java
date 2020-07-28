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
import javax.servlet.jsp.jstl.core.Config;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.nineclient.cbd.wcc.constant.CacluatePage;
import com.nineclient.cbd.wcc.dto.WccCultureLifeDTO;
import com.nineclient.cbd.wcc.dto.WccImgSaveDTO;
import com.nineclient.cbd.wcc.model.WccCultureLife;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;

@RequestMapping("/cultureLife")
@Controller
@RooWebScaffold(path = "cbdwcc/cultureLife", formBackingObject = WccRecordMsg.class)
public class CultureController {

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
	
		// 查找当用户的公司
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
		UmpCompany company = user.getCompany();
		
		try {
			 pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			 parm  = pager.getParameters();
				 nickName = (String) parm.get("nickName");
				 platFormId = (String) parm.get("platFormId");
				 beginTime = (String) parm.get("beginTime");
				 endTime = (String) parm.get("endTime");
				 String  s =  (String) parm.get("restart");
				 if(s.equals("y")){
					 pager.setStartRecord(0);
					 pager.setNowPage(1);
				 }
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
   
        	QueryModel<WccCultureLife> qm = new QueryModel<WccCultureLife>();
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
        	qm.putParams("beginTime", beginTime);
        	qm.putParams("endTime", endTime);
        	qm.setStart(pager.getStartRecord());
        	qm.setLimit(pager.getPageSize());
        	PageModel <WccCultureLife> p =	WccCultureLife.getRecord(qm);

        	CacluatePage.totalPage(p.getPageSize(), p.getTotalCount());
        	List<WccCultureLife> lit =	p.getDataList();

        List<WccCultureLifeDTO> list = new ArrayList<WccCultureLifeDTO>();
        if(null != lit && lit.size() > 0 ){
        for(WccCultureLife s:lit){
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
  list.add(new WccCultureLifeDTO(s.getId(),s.getBackgroundImg(), s.getBackgroundTip()
		 ,s.getListImg(),s.getListTip(),s.getDetailTitle(), s.getDetailContent(), 
		 s.getOtherUrl(), plf, s.getInsertTime()));
        }
        		
       }
        List<WccCultureLifeDTO> dto=new ArrayList<WccCultureLifeDTO>();
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
		pager.setPageCount(CacluatePage.totalPage(p.getPageSize(), p.getTotalCount()));
		pager.setRecordCount(Long.parseLong(p.getTotalCount()+""));//设置总记录数
		pager.setIsSuccess(true);
	   try {
		response.setContentType("application/json");//设置返回的数据为json对象
		JsonValueProcessor jsonProcessor = new DateJsonValueProcessor();
        JsonConfig jsonConfig = new JsonConfig();
       // jsonConfig.setIgnoreDefaultExcludes(false);
     jsonConfig.registerJsonValueProcessor(java.util.Date.class, jsonProcessor);
       // jsonConfig.setExcludes(new String[]{"platformUsers"}); 
        
		JSONObject pagerJSON = JSONObject.fromObject(pager);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(pagerJSON.toString());
	} catch (IOException e){
		e.printStackTrace();
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
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		
		
		
//		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
//		uiModel.addAttribute("platformUser", platformUser);
	
		
		List<WccPlatformUser> list = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
		uiModel.addAttribute("plats", list);
		
		
	   return "cultureLife/culture";
	}
   
	@SuppressWarnings("static-access")
	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(HttpServletRequest request,Model uiModel,
    		@RequestParam(value = "dataId", required = false) String dataId,
			HttpServletResponse response) {
       if(null != dataId && !"".equals(dataId)){
    	   WccCultureLife w  = new WccCultureLife();
    	   WccCultureLife wccCultrueLife = w.findWccCultureLife(Long.parseLong(dataId));
    	   String pltIds = "";
    	      if(null != wccCultrueLife.getPlatformUsers()){
    	    	  
    	        	Set <WccPlatformUser> ss  =	wccCultrueLife.getPlatformUsers();
    	        	Iterator<WccPlatformUser> it = ss.iterator();
    	        	while (it.hasNext()){ 
    	        		WccPlatformUser u = it.next();
    	        	     if(null != u.getId() && !"".equals(u.getId())){
    	        	    	 pltIds += u.getId()+",";
    	        	     }
    	        	}
    	        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	        	if(null != wccCultrueLife.getInsertTime()){
    	        		uiModel.addAttribute("insertTimeStr", df.format(wccCultrueLife.getInsertTime()));
    	        	}
    	        	
    	        }
    	   	if(null != pltIds && !"".equals(pltIds)){
        		uiModel.addAttribute("pltIds", pltIds.subSequence(0, (pltIds.length()-1)));
        	}else{
        		uiModel.addAttribute("pltIds", "");
        	}  
    	   uiModel.addAttribute("wccCultureLife", wccCultrueLife);
    	   
       }
        return "cultureLife/create";
    }

	@RequestMapping(params = "delete", produces = "text/html")
    public String deleteForm(HttpServletRequest request,
    		@RequestParam(value = "dataId", required = true) String dataId,
			HttpServletResponse response) {
		if(null != dataId && !"".equals(dataId)){
			
			WccCultureLife w  = new WccCultureLife();
			w.setId(Long.parseLong(dataId));
			w.remove();	
		}
		
        return null;
    }
	
	
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WccCultureLife wccCultureLife, BindingResult bindingResult,
    		@RequestParam(value = "insertTimeStr", required = false) String insertTimeStr,
    		Model uiModel, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
//		PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
		if (bindingResult.hasErrors() ) {
			return "redirect:/cultureLife?form";
		}
	if(null != wccCultureLife.getId() && !"".equals(wccCultureLife.getId())){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if(insertTimeStr != null && !"".equals(insertTimeStr)){
			try {
				wccCultureLife.setInsertTime(df.parse(insertTimeStr));
				wccCultureLife.setInsertTimeStr(null);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		//先查询出实体
		  WccCultureLife wccCultrueLifeUpdate = WccCultureLife.findWccCultureLife(wccCultureLife.getId());
		  wccCultrueLifeUpdate.setPlatformUsers(wccCultureLife.getPlatformUsers());
		  wccCultrueLifeUpdate.setBackgroundImg(wccCultureLife.getBackgroundImg());
		  wccCultrueLifeUpdate.setBackgroundTip(wccCultureLife.getBackgroundTip());
		  wccCultrueLifeUpdate.setListImg(wccCultureLife.getListImg());
		  wccCultrueLifeUpdate.setListTip(wccCultureLife.getListTip());
		  wccCultrueLifeUpdate.setInsertTime(wccCultureLife.getInsertTime());
		  wccCultrueLifeUpdate.setDetailTitle(wccCultureLife.getDetailTitle());
		  wccCultrueLifeUpdate.setDetailContent(wccCultureLife.getDetailContent());
		 uiModel.asMap().clear();
		 //再更新数据
		 wccCultrueLifeUpdate.merge();
		}else{
			uiModel.asMap().clear();
			wccCultureLife.setInsertTime(new Date());
			wccCultureLife.persist();
		}
	
	    	
		
		return "redirect:/cultureLife/showRecord";
	}
	
	
}

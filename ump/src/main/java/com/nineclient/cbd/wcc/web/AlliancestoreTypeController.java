package com.nineclient.cbd.wcc.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.nineclient.cbd.wcc.model.WccAlliancestoreType;
import com.nineclient.cbd.wcc.model.WccAppartment;
import com.nineclient.cbd.wcc.model.WccItemotherinfo;
import com.nineclient.cbd.wcc.model.WccMalfunctionType;
import com.nineclient.model.PubOperator;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;

@RequestMapping("/AlliancestoreType")
@Controller
@RooWebScaffold(path = "cbdwcc/alliancestore", formBackingObject = WccRecordMsg.class)
public class AlliancestoreTypeController {
	@RequestMapping(value = "/TestAlliancestoreType", produces = "text/html")
	public String showCulture(HttpServletRequest request,Model uiModel,
			HttpServletResponse response) {

	    return "AlliancestoreType/create";
	}
	   @RequestMapping(value="/loadFaultServiceType", produces = "application/json")
	   public void createFu( String dtGridPager,HttpServletRequest request,Model model,HttpServletResponse response) throws Exception {
			Map parm = new HashMap<String, String>();
			PubOperator user = CommonUtils.getCurrentPubOperator(request);
			Pager pager =null;
			Long totalCount = null;
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			parm = pager.getParameters();
			int maxResults   =  pager.getPageSize() ;
			int totalPage    =  0;
			QueryModel<WccAlliancestoreType> qm = new QueryModel<WccAlliancestoreType>();
		    qm.setStart(pager.getStartRecord());
		    qm.setLimit(pager.getPageSize());
			PageModel<WccAlliancestoreType> pm=WccAlliancestoreType.getRecord(qm);
	       	try {
					totalCount =Long.parseLong(pm.getDataList().size()+"");
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
	   		//pager.setRecordCount(Long.parseLong(pm.getTotalCount()+""));//设置总记录数
	   		int totalpage=CacluatePage.totalPage(pager.getPageSize(), pm.getTotalCount());
	       	List<WccAlliancestoreType>  ceshi = pm.getDataList();
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
	
	@SuppressWarnings("static-access")
	@RequestMapping(params = "form", produces = "text/html")
	 public String createFormte(HttpServletRequest request,Model uiModel,
			 @RequestParam(value="dataId",required=false)String dataId,
				HttpServletResponse response) {
		if(dataId!=null && !dataId.equals("")){
			WccAlliancestoreType wccAlliancestoreType=WccAlliancestoreType.getById(Long.parseLong(dataId));
			uiModel.addAttribute("wccAlliancestoreType", wccAlliancestoreType);
		}
		
	    return "AlliancestoreType/createType";
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(
    		@Valid WccAlliancestoreType wccAlliancestoreType, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException, ParseException {
			if(wccAlliancestoreType.getId()==null || wccAlliancestoreType.getId().equals("")){
				wccAlliancestoreType.persist();
			}else{
				wccAlliancestoreType.merge();
			}
			
		return "redirect:AlliancestoreType/TestAlliancestoreType?";
	}
	@SuppressWarnings("static-access")
	@RequestMapping(params = "delete", produces = "text/html")
	 public String delete(HttpServletRequest request,Model uiModel,
			 @RequestParam(value="dataId",required=false)String dataId,
				HttpServletResponse response) {
		if(null != dataId  && !"".equals(dataId)){
			WccAlliancestoreType w  = new WccAlliancestoreType();
			w.setId(Long.parseLong(dataId));
			w.remove();	
		}
	
		return "redirect:AlliancestoreType/TestAlliancestoreType?";
	}
	
}

package com.nineclient.cbd.wcc.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nineclient.cbd.wcc.constant.CacluatePage;
import com.nineclient.cbd.wcc.model.WccAppartment;
import com.nineclient.cbd.wcc.model.WccItemotherinfo;
import com.nineclient.cbd.wcc.model.WccMalfunctionType;
import com.nineclient.cbd.wcc.model.WccMalfunctions;
import com.nineclient.model.PubOperator;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;

@RequestMapping("/faultService")
@Controller
@RooWebScaffold(path = "cbdwcc/faulService", formBackingObject = WccRecordMsg.class)
public class FaultServiceController {
	@RequestMapping(value = "faultService", produces = "text/html")
	public String showCulture(HttpServletRequest request,Model uiModel,
			HttpServletResponse response) {
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
		String platform="";
		for(WccPlatformUser use:platformUser){
			platform+=use.getId()+",";
		}
		QueryModel<WccAppartment> qm = new QueryModel<WccAppartment>();
	//	qm.setStart(1);
    //	qm.setLimit(Integer.MAX_VALUE);
		PageModel<WccAppartment> pageModel =WccAppartment.getRecord(qm, null, "2", platform.substring(0,platform.length()-1), null);
		uiModel.addAttribute("list", pageModel.getDataList());
		return "faultService/culture";
	}
	@RequestMapping(value="/loadFaultService", produces = "application/json")
	   public void createFu( String dtGridPager,HttpServletRequest request,Model model,HttpServletResponse response) throws Exception {
			Map parm = new HashMap<String, String>();
			String itemPk="";
			String nickName="";
			String isDealed="";
			PubOperator user = CommonUtils.getCurrentPubOperator(request);
			//	List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
			
			Pager pager =null;
	//		Long totalCount = null;
			// 查找当用户的公司
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			parm = pager.getParameters();
			itemPk=(String)parm.get("itemPk");
			nickName=(String)parm.get("nickName");
			isDealed=(String)parm.get("isDealed");
			int maxResults   =  pager.getPageSize() ;
			int totalPage    =  0;
			List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
			String platform="";
			for(WccPlatformUser use:platformUser){
				platform+=use.getId()+",";
			}
			QueryModel<WccAppartment> qm1 = new QueryModel<WccAppartment>();
		//	qm.setStart(1);
	    //	qm.setLimit(Integer.MAX_VALUE);
			PageModel<WccAppartment> pageModel =WccAppartment.getRecord(qm1, null, "2", platform.substring(0,platform.length()-1), null);
			String wccId="";
			for(WccAppartment wcc:pageModel.getDataList()){
				wccId+=wcc.getId()+",";
			}
			QueryModel<WccMalfunctions> qm = new QueryModel<WccMalfunctions>();
			if(itemPk != null && !itemPk.equals("")){
				qm.putParams("itemPk", itemPk);
			}
			else{
				qm.putParams("itemPk", wccId.substring(0,wccId.length()-1));
			}
		   	qm.putParams("nickName", nickName);
		   	qm.putParams("isDealed", isDealed);
		    qm.setStart(pager.getStartRecord());
		    qm.setLimit(pager.getPageSize());
			PageModel<WccMalfunctions> pm=WccMalfunctions.getRecord(qm);
	       	try {
	       		
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
	   		//pager.setRecordCount(Long.parseLong(pm.getTotalCount()+""));//设置总记录数
	   		int totalpage=CacluatePage.totalPage(pager.getPageSize(), pm.getTotalCount());
	       	List<WccMalfunctions>  ceshi = pm.getDataList();
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
	@RequestMapping(params = "delete", produces = "text/html")
	 public String delete(HttpServletRequest request,Model uiModel,
			 @RequestParam(value="dataId",required=false)String dataId,
				HttpServletResponse response) {
		if(null != dataId  && !"".equals(dataId)){
			WccMalfunctions w  = new WccMalfunctions();
			w.setId(Long.parseLong(dataId));
			w.remove();	
		}
	
		return "redirect:faultService/faultService?";
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(params = "update", produces = "text/html")
	 public String update(HttpServletRequest request,Model uiModel,
			 @RequestParam(value="dataId",required=false)String dataId,
				HttpServletResponse response) {
		if(null != dataId  && !"".equals(dataId)){
			WccMalfunctions wccMal=WccMalfunctions.getById(Long.parseLong(dataId));
			if(wccMal.getIsDealed()=="未处理" || wccMal.getIsDealed().equals("未处理")){
				wccMal.setIsDealed("已处理");
				wccMal.setDealDate(new Date());
				wccMal.merge();
			}
		}
	
		return "redirect:faultService/faultService?";
	}
	
	
}

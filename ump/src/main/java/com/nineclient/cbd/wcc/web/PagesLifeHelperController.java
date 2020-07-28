package com.nineclient.cbd.wcc.web;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nineclient.cbd.wcc.model.WccLifeHelper;
import com.nineclient.model.WccPlatformUser;

@RequestMapping("/pageLifeHelper")
@Controller

public class PagesLifeHelperController {


	/**
	 * 查询页面
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "showLife", produces = "text/html")
	public String showLife(HttpServletRequest request,Model uiModel,
			@RequestParam(value = "platId", required = false) String platId,
			HttpServletResponse response) {
		Long pid = null;
	if(null != platId && !"".equals(platId) ){
		pid = Long.parseLong(platId+"");
	}
    	
    	
    List <WccLifeHelper> list =	WccLifeHelper.getQueryRecordList(pid);
    uiModel.addAttribute("list", list);
    if(null != list && list.size() > 0){
    	 if((list.size()%3)>0){
    		 uiModel.addAttribute("listSize", list.size()%3);
    	 }
    }
    uiModel.addAttribute("list", list);
    uiModel.addAttribute("backObj", list.get(0));
	return "page/life";
}
	@RequestMapping(value = "showLifeDetail", produces = "text/html")
	public String showLifeDetail(HttpServletRequest request,Model uiModel,
			@RequestParam(value = "id", required = false) String id,
			HttpServletResponse response) {
	
    	
       List <WccLifeHelper> list = null;	
        if(null != id && !"".equals(id)){
        	list =  WccLifeHelper.getQueryRecordDetail(Long.parseLong(id));
        	Set s = null;
        	if(null != list && list.size() > 0){
        		s = list.get(0).getPlatformUsers();
                Iterator<WccPlatformUser> it = s.iterator();
                WccPlatformUser u =null;
                while (it.hasNext()){
    				u=it.next();
    				break;
    			}
                uiModel.addAttribute("plt", u);
            	uiModel.addAttribute("detailObj", list.get(0));
        	}
            

        }
          
    
          
	return "page/lifeHelperDetail";
}
	
}

package com.nineclient.cbd.wcc.web;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nineclient.cbd.wcc.model.WccContactUs;
import com.nineclient.cbd.wcc.model.WccImgSave;

@RequestMapping("/pageContactUs")
@Controller

public class PagesContactUsController {


	/**
	 * 查询页面
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "showContactInfo", produces = "text/html")
	public String showContactInfo(HttpServletRequest request,Model uiModel,
			@RequestParam(value = "platId", required = false) String platId,
			HttpServletResponse response) {
		Long pid = null;
	if(null != platId && !"".equals(platId) ){
		pid = Long.parseLong(platId+"");
	}
    	
    	
    List <WccContactUs> list =	WccContactUs.getQueryRecordList(pid);
    List<WccImgSave> wcc=WccImgSave.findBytype(Long.parseLong(platId), "1");
	  if(wcc.size()>0){
		  uiModel.addAttribute("wcc", wcc.get(0));
	  }
    uiModel.addAttribute("list", list);
	return "page/contactUs";
}
	
	
}

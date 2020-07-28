package com.nineclient.cbd.wcc.web;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nineclient.cbd.wcc.model.WccMycard;
import com.nineclient.model.WccRecordMsg;



@RequestMapping("/pageMyCard")
@Controller
@RooWebScaffold(path = "cbdwcc_pages/myOne_Card", formBackingObject = WccRecordMsg.class)
public class PagesMyCardController {
	
	@RequestMapping(value = "showCreate", produces = "text/html")
	public String show(HttpServletRequest request,Model uiModel,
			@RequestParam(value = "platId", required = false) String platId,
			HttpServletResponse response) {
		if(WccMycard.findMyCard(platId).size()>0){
			WccMycard wcc=WccMycard.findMyCard(platId).get(0);
			uiModel.addAttribute("wcc", wcc);
		}
		else{
			return null;
		}
	   return "page/MyCard";
	}
	
	/**
	 * 查询页面
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "showMyCard", produces = "text/html")
	public String showCulture(HttpServletRequest request,Model uiModel,
			@RequestParam(value = "id", required = false) Long id,
			HttpServletResponse response) {
		    WccMycard wcc=WccMycard.findPubOrganization(id);
		    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	        String dateStr=format.format(wcc.getCardTime());
	        uiModel.addAttribute("dateStr", dateStr);
	        uiModel.addAttribute("wcc", wcc);
		
	   return "page/showMyCard";
	}
	
}

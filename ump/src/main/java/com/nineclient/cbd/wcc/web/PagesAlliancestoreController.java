package com.nineclient.cbd.wcc.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nineclient.cbd.wcc.model.WccAlliancestore;
import com.nineclient.cbd.wcc.model.WccAlliancestoreType;
import com.nineclient.cbd.wcc.model.WccMycard;
import com.nineclient.model.WccRecordMsg;



@RequestMapping("/pageAlliancestore")
@Controller
@RooWebScaffold(path = "cbdwcc_pages/alliancestore", formBackingObject = WccRecordMsg.class)
public class PagesAlliancestoreController {
	
	@RequestMapping(value = "showCreate", produces = "text/html")
	public String show(HttpServletRequest request,Model uiModel,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value="platId",required=false)String platId,
			HttpServletResponse response) {
		List<WccAlliancestoreType> type1=WccAlliancestoreType.getwccAlliancestoreType();
		uiModel.addAttribute("type1",type1);
		if(type==null || type.equals("")) {
			type=type1.get(0).getId()+"";
		}

		List<WccAlliancestore> wccAlliancestore=WccAlliancestore.findMyCard(type,platId);
		
/*		String aa="";
		if(type=="1" || type.equals("1")){
			aa="美食餐饮";
		}
		if(type=="2" || type.equals("2")){
			aa="商家服务";
		}
		if(type=="3" || type.equals("3")){
			aa="便民服务";
		}
		if(type=="4" || type.equals("4")){
			aa="美容养生";
		}*/
		List<WccAlliancestore> wccAlliancestore2=new ArrayList<WccAlliancestore>();
			if(wccAlliancestore.size()>0){
				for(WccAlliancestore wccAlliancestore1:wccAlliancestore){
					String str=wccAlliancestore1.getStoreIntro().replaceAll("<[.[^<]]*>","");
					wccAlliancestore1.setStoreIntro(str);
					wccAlliancestore2.add(wccAlliancestore1);
				}
			}
			if(type!=null && !type.equals("")){
				
				uiModel.addAttribute("type", WccAlliancestoreType.getById(Long.parseLong(type)));
			}
			
			uiModel.addAttribute("wccAlliancestore", wccAlliancestore2);
			uiModel.addAttribute("platId", platId);
			return "page/Alliancestore";
	}
	
	/**
	 * 查询页面
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "showAlliancestore", produces = "text/html")
	public String showCulture(HttpServletRequest request,Model uiModel,
			@RequestParam(value = "id", required = false) String id,
			HttpServletResponse response) {
		WccAlliancestore wccAlliancestore = WccAlliancestore.findPubOrganization(Long.parseLong(id));
		uiModel.addAttribute("wccAlliancestore", wccAlliancestore);
	   return "page/showAlliancestore";
	}
	
}

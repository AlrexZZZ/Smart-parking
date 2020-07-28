package com.nineclient.cbd.wcc.web;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nineclient.cbd.wcc.model.WccAppartment;
import com.nineclient.cbd.wcc.model.WccItemotherinfo;
import com.nineclient.model.UmpOperator;

@RequestMapping("/pageCommunity")
@Controller
@RooWebScaffold(path = "cbdwcc_pages/community", formBackingObject = UmpOperator.class)
public class PagesCommunityController {
	@RequestMapping(value="/createCommunity", produces = "text/html")
    public String create(
    	@RequestParam(value="id",required=false)Long id,
    	@RequestParam(value="platId",required=false)String platId,
    	Model uiModel, HttpServletRequest httpServletRequest) {
        List<WccAppartment> list=WccAppartment.getWccAppartmentByXp(platId,"2");
        uiModel.addAttribute("list", list);
        WccAppartment wccAppartment=null;
        if( id==null  || id<=0l){
        	if(list.size()>0){
        		wccAppartment=list.get(0);
        	}
//        	else{
//        		return null;
//        	}
        }
        else{
        	//uiModel.addAttribute("id", id);
        	wccAppartment=WccAppartment.findPubOrganization(id);
        }
        List<WccItemotherinfo> listWcc=null;
        if(null != wccAppartment && null != wccAppartment.getId()){
        	listWcc = WccItemotherinfo.getCBD_WCC(wccAppartment.getId());
        	uiModel.addAttribute("setWcc", listWcc);
        	uiModel.addAttribute("id", wccAppartment.getId());
        	 uiModel.addAttribute("platId", platId);   
            uiModel.addAttribute("wccAppartment", wccAppartment);
        }
       
        
     
        return "community/create";
    }
	@RequestMapping(value="/createItme", produces = "text/html")
	   public String itemCreate(
	    		@RequestParam(value="id",required=true)Long id,
	    		@RequestParam(value="appId",required=false)Long appId,
	    		Model uiModel, HttpServletRequest httpServletRequest) {
	      
	        if(id<=0l || id==null){
	        	id=1l;
	        }
	        WccAppartment wccAppartment=WccAppartment.findPubOrganization(appId);
	        WccItemotherinfo wccItemotherinfo=WccItemotherinfo.findPubOrganization(id);
	        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	        String dateStr=format.format(wccItemotherinfo.getPublicTime());
	        uiModel.addAttribute("dateStr", dateStr);
	       	uiModel.addAttribute("wccItemotherinfo", wccItemotherinfo);
	       	uiModel.addAttribute("wccAppartment", wccAppartment);
	        return "community/introduction";
	    }
	@RequestMapping(value="/createItmeWg", produces = "text/html")
	   public String itemCreateWg(
	    		@RequestParam(value="id",required=true)Long id,
	    		@RequestParam(value="appId",required=false)Long appId,
	    		Model uiModel, HttpServletRequest httpServletRequest) {
	      
	        if(id<=0l || id==null){
	        	id=1l;
	        }
	        WccAppartment wccAppartment=WccAppartment.findPubOrganization(appId);
	        WccItemotherinfo wccItemotherinfo=WccItemotherinfo.findPubOrganization(id);
	        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	        String dateStr=format.format(wccItemotherinfo.getPublicTime());
	        uiModel.addAttribute("dateStr", dateStr);
	       	uiModel.addAttribute("wccItemotherinfo", wccItemotherinfo);
	       	uiModel.addAttribute("wccAppartment", wccAppartment);
	        return "community/introductionWg";
	    }
	
	@RequestMapping(value="/createItmeDitu", produces = "text/html")
	   public String itemCreateDitu(
	    		@RequestParam(value="id",required=true)Long id,
	    		@RequestParam(value="appId",required=false)Long appId,
	    		Model uiModel, HttpServletRequest httpServletRequest) {
	      
	        if(id<=0l || id==null){
	        	id=1l;
	        }
	        WccAppartment wccAppartment=WccAppartment.findPubOrganization(appId);
	        WccItemotherinfo wccItemotherinfo=WccItemotherinfo.findPubOrganization(id);
	       	uiModel.addAttribute("wccItemotherinfo", wccItemotherinfo);
	       	uiModel.addAttribute("wccAppartment", wccAppartment);
	        return "community/location";
	    }
	@RequestMapping(value="/newCreateCommunity", produces = "text/html")
    public String newCreate(
    		@RequestParam(value="id",required=false)Long id,
    		@RequestParam(value="platId",required=false)String platId,
    		Model uiModel, HttpServletRequest httpServletRequest) {
		List<WccAppartment> list=WccAppartment.getWccAppartmentByXp(platId,"1");
        uiModel.addAttribute("list", list);
        WccAppartment wccAppartment=null;
        if( id==null  || id<=0l){
        	if(list.size()>0){
        		wccAppartment=list.get(0);
        	}
        }
        else{
        
        	wccAppartment=WccAppartment.findPubOrganization(id);
        }
        if(null !=wccAppartment && null != wccAppartment.getId()){
            List<WccItemotherinfo> listWcc=WccItemotherinfo.getNewCBD_WCC(wccAppartment.getId());
            uiModel.addAttribute("id", wccAppartment.getId());
            uiModel.addAttribute("setWcc", listWcc);
            uiModel.addAttribute("platId", platId);
           	uiModel.addAttribute("wccAppartment", wccAppartment);
        }
  
        return "community/newCreate";
    }
	
	@RequestMapping(value="/newCreateItme", produces = "text/html")
	   public String newItemCreate(
	    		@RequestParam(value="id",required=true)Long id,
	    		@RequestParam(value="appId",required=false)Long appId,
	    		Model uiModel, HttpServletRequest httpServletRequest) {
	      
	        if(id<=0l || id==null){
	        	id=1l;
	        }
	        WccAppartment wccAppartment=WccAppartment.findPubOrganization(appId);
	        WccItemotherinfo wccItemotherinfo=WccItemotherinfo.findPubOrganization(id);
	        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	        String dateStr=format.format(wccItemotherinfo.getPublicTime());
	        uiModel.addAttribute("dateStr", dateStr);
	       	uiModel.addAttribute("wccItemotherinfo", wccItemotherinfo);
	       	uiModel.addAttribute("wccAppartment", wccAppartment);
	        return "community/newCreateItme";
	    }
	@RequestMapping(value="/newCreateImg", produces = "text/html")
	   public String newCreate(
	    		@RequestParam(value="id",required=true)Long id,
	    		@RequestParam(value="appId",required=false)Long appId,
	    		Model uiModel, HttpServletRequest httpServletRequest) {
	      
	        if(id<=0l || id==null){
	        	id=1l;
	        }
	        WccAppartment wccAppartment=WccAppartment.findPubOrganization(appId);
	        WccItemotherinfo wccItemotherinfo=WccItemotherinfo.findPubOrganization(id);
	        String str=wccItemotherinfo.getOtherIntro();
	        String[] itemImg=str.split("alt=\"\" />");
	        
	        	for(int i=0;i<itemImg.length;i++){
	        	
	        			itemImg[i]+="alt=\"\" />";
	        		
	        		
	        	}
	        	String[] s=new String[itemImg.length-1];
	        	if(itemImg[itemImg.length-1].contains("</p>")){
	        		for(int i=0;i<(itemImg.length-1);i++){
	        			s[i]=itemImg[i];
	        			
	        	}
	        		uiModel.addAttribute("itemImg", s);
	        	}
	        	else{
	        		uiModel.addAttribute("itemImg", itemImg);
	        	//	itemImg[itemImg.length-1]=itemImg[itemImg.length-1].substring(0,itemImg[itemImg.length-1].lastIndexOf("</p>"));
	        	}
	        
	       // List<WccItemImg> itemImg=WccItemImg.getWccItemImg(wccItemotherinfo.getId());
	       	uiModel.addAttribute("wccItemotherinfo", wccItemotherinfo);
	       	uiModel.addAttribute("wccAppartment", wccAppartment);
	       	
	        return "community/newCreateImg";
	    }
	
}

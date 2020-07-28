package com.nineclient.cbd.wcc.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;








import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nineclient.cbd.wcc.dto.WccFeesDTO;
import com.nineclient.cbd.wcc.model.WccFees;
import com.nineclient.cbd.wcc.model.WccLifeHelper;
import com.nineclient.model.WccPlatformUser;

@RequestMapping("/pageFees")
@Controller

public class PagesFeesController {


	/**
	 * 查询页面
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "showFees", produces = "text/html")
	public String showLife(HttpServletRequest request,Model uiModel,
			HttpServletResponse response) {
	
    	
    	
    List <WccLifeHelper> list =	WccLifeHelper.getQueryRecordList(null);
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
	public String showFriendFees(HttpServletRequest request,Model uiModel,
			@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "friendId", required = false) String friendId,
			HttpServletResponse response) {
		List<WccFeesDTO> listdto = null ;
		try {
				listdto =  WccFees.findRcordCountByQuery(friendId, date);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	Map <String,List<WccFeesDTO>> dm = new LinkedHashMap<String,List<WccFeesDTO>>();
	for(WccFeesDTO fdt:listdto){
		  if(null != fdt.getMonthStr() && null == dm.get(fdt.getMonthStr())){
			  dm.put(fdt.getMonthStr(), new ArrayList<WccFeesDTO>());
		  }
	}
  if(null != listdto){
	  for(WccFeesDTO fd:listdto){
		  if(null != fd.getMonthStr()){
			  dm.get(fd.getMonthStr()).add(fd);
		  }
	  }
  }
  Map <String,List<WccFeesDTO>> dtm = new LinkedHashMap<String,List<WccFeesDTO>>();
   for(Map.Entry<String,List<WccFeesDTO>> md :dm.entrySet()){
	   float t = 0;
	   for(WccFeesDTO dt:md.getValue()){
		   if(dt.getState() == 0){
			   t += dt.getAmount();
		   }
	   }
	   dtm.put(md.getKey()+"_"+t, md.getValue());  
   }
  
  

    uiModel.addAttribute("dataMap", dtm);   
    uiModel.addAttribute("friendId", friendId);
    return "page/showFees";
}
	
}

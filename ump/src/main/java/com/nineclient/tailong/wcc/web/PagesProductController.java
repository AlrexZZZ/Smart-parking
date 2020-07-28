package com.nineclient.tailong.wcc.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import com.nineclient.model.WccFriend;
import com.nineclient.model.WccStore;
import com.nineclient.tailong.wcc.model.WccFriendsAppointment;
import com.nineclient.tailong.wcc.model.WccTproducts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/pageTLProduct")
@Controller
public class PagesProductController {

	@RequestMapping(value = "showBding", produces = "text/html")
	public String showBding(HttpServletRequest request,Model uiModel,
			@RequestParam(value = "openId", required = false) String openId,
			HttpServletResponse response) {
	
System.out.println("dispayyy~~~");
		
   // uiModel.addAttribute("openId", openId);
	return "page/showBding";
}
	/**
	 * 查询页面
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "showTLProduct", produces = "text/html")
	public String showCulture(HttpServletRequest request,Model uiModel,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "openId", required = false) String openId,
			@RequestParam(value = "type", required = true) String type,
			HttpServletResponse response) {
		System.out.println("dddddddddddddddddddddddddd999");
		Long pid = null;
		if(null != state && !"".equals(state)){
			pid = Long.parseLong(state+"");
		}
		List<WccTproducts> product = WccTproducts.getRecordListByType(pid, type);
		 uiModel.addAttribute("openId", openId);
         uiModel.addAttribute("productList", product);
	return "page/showTLProductList";
}
	
	@RequestMapping(value = "appointmentProduct",produces = "text/html" )
	public String appointmentProduct(HttpServletRequest request,Model uiModel,
			@RequestParam(value = "productId", required = true) Long productId,
			@RequestParam(value = "openId", required = true) String openId,
			@RequestParam(value = "fname", required = false) String fname,
			@RequestParam(value = "fphone", required = false) String fphone,
			@RequestParam(value = "farea", required = false) String farea,
			HttpServletResponse response){
		WccFriend f = WccFriend.findWccFriendByOpenId(openId);
		WccTproducts wps = WccTproducts.findWccTproducts(productId);
		WccFriendsAppointment ap = new WccFriendsAppointment();
		ap.setFname(fname);ap.setFphone(fphone);ap.setFarea(farea);
		if(f != null){
			ap.setFnickName(f.getNickName());ap.setFopenId(f.getOpenId());
		}
		if(wps != null){
			ap.setProductName(wps.getProductName());
			ap.setProductType(wps.getProductType()==1?"融资缺钱":"钱生钱");
		}
		ap.setInsertTime(new Date());  
		ap.setResult("待处理");
		ap.persist();
		
		return null;
	}
	
	
	
	@RequestMapping(value = "showTLProductDetail", produces = "text/html")
	public String showDetail(HttpServletRequest request,Model uiModel,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "platId", required = false) String platId,
			@RequestParam(value = "openId", required = false) String openId,
			HttpServletResponse response) {
	
    	
        WccTproducts  wccTproducts = null;	
        if(null != id && !"".equals(id)){
        	wccTproducts =  WccTproducts.findWccTproducts(Long.parseLong(id));
/*            Set s = wccTproducts.getPlatformUsers();
            Iterator<WccPlatformUser> it = s.iterator();
            WccPlatformUser u =null;
            while (it.hasNext()) {
				u=it.next();
				break;
			}
            uiModel.addAttribute("plt", u);*/
        	uiModel.addAttribute("openId", openId);
        	uiModel.addAttribute("productId", wccTproducts.getId());
        	uiModel.addAttribute("wccTproducts", wccTproducts);
        }
          
    
          
	return "page/showTLProductDetail";
}
	@RequestMapping(value = "showProductStroe", produces = "text/html")
	public String showProductStroe(HttpServletRequest request,Model uiModel,
			@RequestParam(value = "areaDis", required = false) String areaDis,
			@RequestParam(value = "area", required = false) String area,
			HttpServletResponse response) {
		// System.out.println("~~~~~~~~~~~~~~~~~~");
//	   if(null != area){
		  if(area != null &&area.equals("全部浦东新区")){
			  area = null; 
		  }
		    
			List<WccStore> storeList = WccStore.getStoreByOrName(area);
			if(null != area){
				 uiModel.addAttribute("area", area);
				 uiModel.addAttribute("areaDis", areaDis);
			}
	        uiModel.addAttribute("storeList", storeList);
//	   }

          
    
          
	return "page/showProductStroe";
}
	@RequestMapping(value = "showProductStroeMap", produces = "text/html")
	public String showProductStroeMap(HttpServletRequest request,Model uiModel,
			@RequestParam(value = "a", required = true) String a,
			@RequestParam(value = "b", required = true) String b,
			@RequestParam(value = "c", required = true) String c,
			@RequestParam(value = "d", required = true) String d,
			@RequestParam(value = "store",required = true)String store,
			HttpServletResponse response) throws UnsupportedEncodingException {
//	   if(null != area){
	        uiModel.addAttribute("store_lngx", a);
	        uiModel.addAttribute("store_laty", b);
	        
	        uiModel.addAttribute("p1store_lngx", c);
	        uiModel.addAttribute("p1store_laty", d);
	        store = new String(store.getBytes("ISO8859-1"),"utf-8");
			uiModel.addAttribute("store", store);
//	   }

          
    
          
	return "page/showProductStroeMap";
}
}

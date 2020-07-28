package com.nineclient.cbd.wcc.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nineclient.cbd.wcc.model.WccCultureLife;
import com.nineclient.cbd.wcc.model.WccImgSave;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccGroup;
import com.nineclient.model.WccMaterials;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.DkfEntity;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;
import com.nineclient.utils.RecordData;
import com.nineclient.utils.Recordlist;
import com.nineclient.utils.WxEmotion;
import com.nineclient.web.WccFriendController;

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

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/pageCultureLife")
@Controller
@RooWebScaffold(path = "cbdwcc_pages/cultureLife", formBackingObject = WccRecordMsg.class)
public class PagesCultureController {


	/**
	 * 查询页面
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "showCulture", produces = "text/html")
	public String showCulture(HttpServletRequest request,Model uiModel,
			@RequestParam(value = "platId", required = false) String platId,
			HttpServletResponse response) {
		Long pid = null;
		if(null != platId && !"".equals(platId)){
			pid = Long.parseLong(platId+"");
		}
		List<WccImgSave> wcc=WccImgSave.findBytype(Long.parseLong(platId), "4");
    	QueryModel<WccCultureLife> qm = new QueryModel<WccCultureLife>();
    	
    List <WccCultureLife> list =null;	
    if(pid!=null){
    	list=WccCultureLife.getQueryRecordList(null,Long.parseLong(pid+""));
    }
    if(wcc.size()>0){
    	 uiModel.addAttribute("wcc", wcc.get(0));
    }
    uiModel.addAttribute("list", list);
    uiModel.addAttribute("backObj", list.get(0));
	return "page/culture";
}
	@RequestMapping(value = "showDetail", produces = "text/html")
	public String showDetail(HttpServletRequest request,Model uiModel,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "platId", required = false) String platId,
			HttpServletResponse response) {
	
    	
       List <WccCultureLife> list = null;	
        if(null != id && !"".equals(id)){
        	list =  WccCultureLife.getQueryRecordByDetailId(Long.parseLong(id+""));
            Set s = list.get(0).getPlatformUsers();
            Iterator<WccPlatformUser> it = s.iterator();
            WccPlatformUser u =null;
            while (it.hasNext()) {
				u=it.next();
				break;
			}
            uiModel.addAttribute("plt", u);
        	uiModel.addAttribute("detailObj", list.get(0));
        }
          
    
          
	return "page/detail";
}
	
}

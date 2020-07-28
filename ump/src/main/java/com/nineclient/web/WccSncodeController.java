package com.nineclient.web;
import com.nineclient.constant.WccSnCodeStatus;
import com.nineclient.model.PubOperator;
import com.nineclient.model.WccLotteryActivity;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccSncode;
import com.nineclient.model.WccUserLottery;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/wccsncodes")
@Controller
@RooWebScaffold(path = "wccsncodes", formBackingObject = WccSncode.class)
public class WccSncodeController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WccSncode wccSncode, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccSncode);
            return "wccsncodes/create";
        }
        uiModel.asMap().clear();
        wccSncode.persist();
        return "redirect:/wccsncodes/" + encodeUrlPathSegment(wccSncode.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new WccSncode());
        return "wccsncodes/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("wccsncode", WccSncode.findWccSncode(id));
        uiModel.addAttribute("itemId", id);
        return "wccsncodes/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page
    		,HttpServletRequest request
    		,@RequestParam(value = "id", required = false) Long id
    		,@RequestParam(value = "sn", required = false) String sn
    		,@RequestParam(value = "tel", required = false) String tel, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) throws UnsupportedEncodingException {
		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(CommonUtils.getCurrentPubOperator(request));
		uiModel.addAttribute("platformUser", platformUser);
//		List<WccSncode> sncodes = new ArrayList<WccSncode>();
//		if(null!=sn||null!=tel){
//			sncodes = WccSncode.findWccSncodeBySnStatusLotteryId(id,sn,tel);
//		}else{
//			sncodes = WccSncode.findWccSncodeBySnStatusLotteryId(id);
//		}
//				
//		for(WccSncode sncode : sncodes){
//			//sncode.setSnRemark(DateUtil.formatDateAndTime(sncode.getAwardTime()));
//			if(null!=sncode.getFriend()){
//				sncode.getFriend().setNickName(URLDecoder.decode(sncode.getFriend().getNickName(),"utf-8"));
//			}
//		}
		uiModel.addAttribute("limit", 10);
		uiModel.addAttribute("sn", sn);
		uiModel.addAttribute("tel",tel);
		request.getSession().setAttribute("activityId", id);
//		uiModel.addAttribute("wccsncodes",sncodes);
        return "wccsncodes/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid WccSncode wccSncode, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccSncode);
            return "wccsncodes/update";
        }
        uiModel.asMap().clear();
        wccSncode.merge();
        return "redirect:/wccsncodes/" + encodeUrlPathSegment(wccSncode.getId().toString(), httpServletRequest);
    }
	

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, WccSncode.findWccSncode(id));
        return "wccsncodes/update";
    }
	
	@RequestMapping(value = "update", produces = "text/html")
    public String updateSncode(@RequestParam(value = "id",required = false) String sid,
    		@RequestParam(value = "activityId",required = false) Long activityId,Model uiModel) throws UnsupportedEncodingException {
		String[] ids = sid.split(",");
		for(String id : ids){
			WccSncode sncode = WccSncode.findWccSncode(Long.parseLong(id));
			sncode.setSnStatus(WccSnCodeStatus.已兑奖);
			sncode.merge();
		}
		List<WccSncode> sncodes = WccSncode.findWccSncodeBySnStatusLotteryId(activityId);
		for(WccSncode wsncode : sncodes){
			//sncode.setSnRemark(DateUtil.formatDateAndTime(sncode.getAwardTime()));
			if(null!=wsncode.getFriend()){
				wsncode.getFriend().setNickName(URLDecoder.decode(wsncode.getFriend().getNickName(),"utf-8"));
			}
		}
		uiModel.addAttribute("activityId", activityId);
		uiModel.addAttribute("wccsncodes",sncodes);
        addDateTimeFormatPatterns(uiModel);
        return "wccsncodes/list";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        WccSncode wccSncode = WccSncode.findWccSncode(id);
        wccSncode.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/wccsncodes";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("wccSncode_awardtime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, WccSncode wccSncode) {
        uiModel.addAttribute("wccSncode", wccSncode);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("wccsncodestatuses", Arrays.asList(WccSnCodeStatus.values()));
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
	
	@RequestMapping(value = "/querySncode", produces = "text/html")
	 public String querySncode(
			 Model uiModel,
			 HttpServletRequest request,
			 @RequestParam(value = "id", required = false) Long id,
	    	 @RequestParam(value = "sn", required = false) String sn,
	    	 @RequestParam(value = "tel", required = false) String tel,
	    	 @RequestParam(value = "platformId", required = false) String platformId,
			 @RequestParam(value = "start", required = false) Integer start,
			 @RequestParam(value = "limit", required = false) Integer limit
			 ) throws ServletException, IOException {
		  Map<String, String> parmMap = new HashMap<String, String>();
		  WccSncode sncode = new WccSncode();
		  String activityId = request.getSession().getAttribute("activityId")+"";
		  List<WccPlatformUser> platformUsers = WccPlatformUser.findAllWccPlatformUsers(CommonUtils.getCurrentPubOperator(request));
		  parmMap.put("aid", activityId);
		  parmMap.put("sn", sn);
		  parmMap.put("tel", tel);
		  parmMap.put("platformId", platformId);
		  QueryModel<WccSncode> qm = new QueryModel<WccSncode>(sncode, start, limit);
		  qm.getInputMap().putAll(parmMap);
		  PageModel<WccSncode> pm = WccSncode.getQuerySncode(qm,platformUsers);
		  pm.setPageSize(limit);
		  pm.setStartIndex(start);
		  WccLotteryActivity activity = WccLotteryActivity.findWccLotteryActivity(Long.parseLong(activityId));
		  List<WccSncode> sncodes =  pm.getDataList();
				for(WccSncode wsncode : sncodes){
					//sncode.setSnRemark(DateUtil.formatDateAndTime(sncode.getAwardTime()));
					if(null!=wsncode.getFriend()){
						wsncode.getFriend().setNickName(URLDecoder.decode(wsncode.getFriend().getNickName(),"utf-8"));
						wsncode.setLotteryNumber(WccUserLottery.getLotteryNumber(wsncode.getFriend(), activity).getLotteryNumber());
					}
				}
			
		 uiModel.addAttribute("WccSncode", sncodes);
		 uiModel.addAttribute("maxPages", pm.getTotalPage());
		 uiModel.addAttribute("page", pm.getPageNo());
		 uiModel.addAttribute("limit", pm.getPageSize());
		 
		 return "wccsncodes/result";
	 }
	
	@RequestMapping(value = "/awardSncode", produces = "text/html;charset=utf-8")
	@ResponseBody
	 public String awardSncode(
			 HttpServletRequest request,
	    	 @RequestParam(value = "sncodeId", required = false) String sncodeId,
	    	 @RequestParam(value = "lotteryId", required = false) Long lotteryId
			 ) throws ServletException, IOException {
		String[] ids = sncodeId.split(",");
		for(String id : ids){
			WccSncode sncode = WccSncode.findWccSncode(Long.parseLong(id));
			sncode.setSnStatus(WccSnCodeStatus.已兑奖);
			sncode.merge();
		}
		return "";
	 }
}

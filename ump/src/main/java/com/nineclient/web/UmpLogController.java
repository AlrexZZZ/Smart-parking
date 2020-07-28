package com.nineclient.web;
import com.nineclient.constant.UmpCheckStatus;
import com.nineclient.model.UmpBrand;
import com.nineclient.model.UmpLog;
import com.nineclient.model.WccStore;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.PageModel;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

@RequestMapping("/umplogs")
@Controller
@RooWebScaffold(path = "umplogs", formBackingObject = UmpBrand.class)
public class UmpLogController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid UmpLog umpLog, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, umpLog);
            return "umplogs/create";
        }
        uiModel.asMap().clear();
        umpLog.persist();
        return "redirect:/umplogs?page=1&amp;size=10";
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new UmpLog());
        return "umplogs/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("umpLog", UmpLog.findUmpLog(id));
        uiModel.addAttribute("itemId", id);
        return "umplogs/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size,
    		@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
    		@RequestParam(value = "displayId", required = false) Long displayId,
    		@RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel
    		,HttpServletRequest request) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("umplogs", UmpLog.findUmpLogEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) UmpLog.countUmpLogs() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
            uiModel.addAttribute("page", page);
			uiModel.addAttribute("size", size);
        } else {
            uiModel.addAttribute("umplogs", UmpLog.findAllUmpLogs(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        if(displayId == null){
        	displayId = CommonUtils.getCurrentDisPlayId(request);
        }
        request.getSession().setAttribute("displayId", displayId);
        return "umplogs/list";
    }

	@RequestMapping(value="findListByFiled",method=RequestMethod.POST,produces="text/html;charset=utf-8" )
	@ResponseBody
	public String findListByFiled(Model uiModel,UmpLog umpLog,
			@RequestParam(value = "starTime", required = false) String starTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "account", required = false) String account,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size) throws ParseException{
		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
		List<UmpLog> umpList = UmpLog.findAllUmpLogsByFiled(firstResult, sizeNo, starTime,endTime,account);
		Long count = UmpLog.countUmpLogsByFiled(starTime,endTime,account);
		PageModel<UmpLog> pageMode = new PageModel<UmpLog>();
		pageMode.setPageNo(page);
		pageMode.setTotalCount(Integer.parseInt(count+""));
		pageMode.setPageSize(sizeNo);
		pageMode.setDataJson(UmpLog.toJsonArray(umpList));
		return pageMode.toJson();
	}
	
	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid UmpLog umpLog, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, umpLog);
            return "umplogs/update";
        }
        uiModel.asMap().clear();
        umpLog.merge();
        return "redirect:/umplogs/" + encodeUrlPathSegment(umpLog.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, UmpLog.findUmpLog(id));
        return "umplogs/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		UmpLog umpLog = UmpLog.findUmpLog(id);
		umpLog.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/umplogs";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("umpLog_createtime_date_format", "yyyy-MM-dd hh:mm:ss");
    }

	void populateEditForm(Model uiModel, UmpLog umplogs) {
        uiModel.addAttribute("umplogs", umplogs);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("umpcheckstatuses", Arrays.asList(UmpCheckStatus.values()));
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

	void populateEditForm(Model uiModel, UmpBrand umpBrand) {
        uiModel.addAttribute("umpBrand", umpBrand);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("umpcheckstatuses", Arrays.asList(UmpCheckStatus.values()));
    }
}

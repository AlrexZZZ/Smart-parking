package com.nineclient.web;

import java.util.Date;

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

import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpOperator;
import com.nineclient.model.UmpRole;
import com.nineclient.model.WccUtils;
import com.nineclient.utils.CommonUtils;
@RequestMapping("/wccutils")
@Controller
@RooWebScaffold(path = "wccutils", formBackingObject = WccUtils.class)
public class WccUtilController {

	@RequestMapping(value="/addWccUtil",method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WccUtils wccUtils, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) {
        uiModel.asMap().clear();
        wccUtils.setIsDeleted(true);
        wccUtils.setUserName(CommonUtils.getCurrentOperator(httpServletRequest).getAccount());
        wccUtils.setCreateTime(new Date());
        wccUtils.persist();
        return "redirect:/wccutils/umpList?page=1&amp;size=10";
    }
	
	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new WccUtils());
        return "wccutils/umpCreate";
    }
	
	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, WccUtils.findWccUtils(id));
        return "wccutils/umpUpdate";
    }
	
	@RequestMapping(value = "/umpDelete", produces = "text/html")
    public String delete(@RequestParam("id") Long id, 
    		Model uiModel) {
		WccUtils wccUtil = WccUtils.findWccUtils(id);
		wccUtil.remove();
        uiModel.asMap().clear();
        return "redirect:/wccutils/umpList?page=1&amp;size=10";
    }
	
	@RequestMapping(value="/updateWccUtil",method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String updateWccUtil(Model uiModel,WccUtils wccUtils){
		WccUtils wccUtil = WccUtils.findWccUtils(wccUtils.getId());
		wccUtil.setUtilDesc(wccUtils.getUtilDesc());
		wccUtil.setUtilName(wccUtils.getUtilName());
		wccUtil.setUtilUrl(wccUtils.getUtilUrl());
		uiModel.asMap().clear();
		wccUtil.merge();
		return "redirect:/wccutils/umpList?page=1&amp;size=10";
	}
	@RequestMapping(value = "/umpList", produces = "text/html")
    public String umpList(@RequestParam(value = "page", required = false) Integer page,
    		@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("wccutils", WccUtils.findWccUtilsEntries(firstResult, sizeNo));
            float nrOfPages = (float) WccUtils.countWccUtils() / sizeNo;
            uiModel.addAttribute("limit", sizeNo);
            uiModel.addAttribute("page", page);
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("wccutils", WccUtils.findAllWccUtils());
        }
        addDateTimeFormatPatterns(uiModel);
        return "wccutils/umpList";
    }
	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page,
    		@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		System.out.println(page+"=========");
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("wccutils", WccUtils.findWccUtilsEntries(firstResult, sizeNo));
            float nrOfPages = (float) WccUtils.countWccUtils() / sizeNo;
            uiModel.addAttribute("limit", sizeNo);
            uiModel.addAttribute("page", page);
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("wccutils", WccUtils.findAllWccUtils());
        }
        addDateTimeFormatPatterns(uiModel);
        return "wccutils/list";
    }
	
	void populateEditForm(Model uiModel, WccUtils wccutils) {
        uiModel.addAttribute("wccutils", wccutils);
        addDateTimeFormatPatterns(uiModel);
    }
	
	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("wccUser_firsttopictime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("wccUser_lasttopictime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
}

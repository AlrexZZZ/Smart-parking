package com.nineclient.web;

import com.nineclient.constant.VocAuditStatus;
import com.nineclient.model.UmpAuthority;
import com.nineclient.model.VocWordExpressions;
import com.nineclient.utils.CommonUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

@RequestMapping("/vocwordexpressionses")
@Controller
@RooWebScaffold(path = "vocwordexpressionses", formBackingObject = VocWordExpressions.class)
public class VocWordExpressionsController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid VocWordExpressions vocWordExpressions,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, vocWordExpressions);
			return "vocwordexpressionses/create";
		}
		uiModel.asMap().clear();
		vocWordExpressions.persist();
		return "redirect:/vocwordexpressionses/"
				+ encodeUrlPathSegment(vocWordExpressions.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new VocWordExpressions());
		return "vocwordexpressionses/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("vocwordexpressions",
				VocWordExpressions.findVocWordExpressions(id));
		uiModel.addAttribute("itemId", id);
		return "vocwordexpressionses/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(
			@RequestParam(value = "keyName", required = false) String keyName,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyName", keyName);
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			uiModel.addAttribute("vocwordexpressionses", VocWordExpressions
					.findVocWordExpressionsEntries(firstResult, sizeNo,
							sortFieldName, sortOrder, map));
			float nrOfPages = (float) VocWordExpressions
					.countVocWordExpressionses(map) / sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("vocwordexpressionses", VocWordExpressions
					.findAllVocWordExpressionses(sortFieldName, sortOrder, map));
		}
		addDateTimeFormatPatterns(uiModel);
		return "redirect:/vocwordcategorys?form";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid VocWordExpressions vocWordExpressions,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, vocWordExpressions);
			return "vocwordexpressionses/update";
		}
		uiModel.asMap().clear();
		vocWordExpressions.merge();
		return "redirect:/vocwordexpressionses/"
				+ encodeUrlPathSegment(vocWordExpressions.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, VocWordExpressions.findVocWordExpressions(id));
		return "vocwordexpressionses/update";
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String delete(@RequestParam(value = "id") Long id, Model uiModel) {
		String msg = "";
		try {
			VocWordExpressions vocWordExpressions = VocWordExpressions
					.findVocWordExpressions(id);
			vocWordExpressions.remove();
			msg = "成功";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "失败";
		}
		return msg;
	}
	@ResponseBody
	@RequestMapping(value = "checkStatus", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String checkStatus(@RequestParam("id") Long id,HttpServletRequest httpServletRequest,
			@RequestParam(value = "status", required = false) Integer status,@RequestParam(value = "displayId", required = false) Long displayId,
			Model uiModel) {
		String msg = "";
		boolean audMenu = false;
		VocWordExpressions vocWordExpressions = null;
		if(displayId == null){
			displayId = CommonUtils.getCurrentDisPlayId(httpServletRequest);
		}
		httpServletRequest.getSession().setAttribute("displayId",displayId);
		if(CommonUtils.getCurrentPubOperator(httpServletRequest).getPubRole() == null){
			audMenu = true;
		}else{
			Map<String, String> auditMenu = (Map<String, String>) httpServletRequest.getSession().getAttribute("auditMenu");
			if(null != auditMenu &&auditMenu.size()>0){
				UmpAuthority umpMenu = null;
				for (String menuId : auditMenu.keySet()) {
					umpMenu  = UmpAuthority.findUmpAuthority(Long.parseLong(menuId));
					if(null != umpMenu){
						//说明有审核权限
						audMenu = true;
					}
				}
			}
		}
		if(!audMenu){
			return "无审核权限";
		}
		try{
			 vocWordExpressions = VocWordExpressions
					.findVocWordExpressions(id);
			VocAuditStatus st=VocAuditStatus.待审核;
			for(VocAuditStatus audit: VocAuditStatus.values()){
				if(audit.ordinal()==status){
					st=audit;
					break;
				}
			}
			vocWordExpressions.setAuditStatus(st);
			vocWordExpressions.merge();
			msg="成功";
		}catch(Exception e){
			e.printStackTrace();
			msg ="失败";
		}
		uiModel.asMap().clear();
		return msg;
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"vocWordExpressions_createtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, VocWordExpressions vocWordExpressions) {
		uiModel.addAttribute("vocWordExpressions", vocWordExpressions);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("vocauditstatuses",
				Arrays.asList(VocAuditStatus.values()));
	}

	String encodeUrlPathSegment(String pathSegment,
			HttpServletRequest httpServletRequest) {
		String enc = httpServletRequest.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}
}

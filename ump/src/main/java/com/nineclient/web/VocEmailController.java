package com.nineclient.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.nineclient.constant.NotifyMsg;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.VocEmail;
import com.nineclient.model.VocEmailGroup;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Global;

@RequestMapping("/vocemails")
@Controller
@RooWebScaffold(path = "vocemails", formBackingObject = VocEmail.class)
public class VocEmailController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid VocEmail vocEmail, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, vocEmail);
			return "vocemails/create";
		}
		uiModel.asMap().clear();
		vocEmail.persist();
		return "redirect:/vocemails/"
				+ encodeUrlPathSegment(vocEmail.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new VocEmail());
		return "vocemails/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("vocemail", VocEmail.findVocEmail(id));
		uiModel.addAttribute("itemId", id);
		return "vocemails/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			uiModel.addAttribute("vocemails", VocEmail.findVocEmailEntries(
					firstResult, sizeNo, sortFieldName, sortOrder));
			float nrOfPages = (float) VocEmail.countVocEmails() / sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("vocemails",
					VocEmail.findAllVocEmails(sortFieldName, sortOrder));
		}
		addDateTimeFormatPatterns(uiModel);
		return "vocemails/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid VocEmail vocEmail, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, vocEmail);
			return "vocemails/update";
		}
		uiModel.asMap().clear();
		vocEmail.merge();
		return "redirect:/vocemails/"
				+ encodeUrlPathSegment(vocEmail.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, VocEmail.findVocEmail(id));
		return "vocemails/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		VocEmail vocEmail = VocEmail.findVocEmail(id);
		vocEmail.remove();
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/vocemails";
	}

	/**
	 * 添加邮件
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addEmail", produces = "text/html;charset=utf-8")
	public String addEamil(HttpServletRequest request, VocEmail vocEmail,
			@RequestParam(value = "groupId", required = false) Long groupId) {
		String msg = "";
		try {
			PubOperator user = CommonUtils.getCurrentPubOperator(request);
			UmpCompany company = user.getCompany();
			String name = vocEmail.getName();
			List<VocEmail> listEmail =  VocEmail.findAllVocEmailsByName(name, company);
			if(listEmail!=null&&listEmail.size()>0){
				msg="邮件名称不能重复";
				return msg;
			}
			
			
			vocEmail.setCreateTime(new Date());
			vocEmail.setIsDeleted(false);
			vocEmail.setIsVisable(true);
			vocEmail.setUmpCompanyId(company.getId());
			vocEmail.persist();
			VocEmailGroup emailGroup = null;
			if (groupId != null && groupId != -1) {
				emailGroup = VocEmailGroup.findVocEmailGroup(groupId);
				emailGroup.getVocEmails().add(vocEmail);
				emailGroup.merge();
			}
			msg =NotifyMsg.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			msg = NotifyMsg.EXCEPTION;
		}
		return msg;
	}
	/**
	 * 加载邮箱
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="emailList",produces="text/html;charset=utf-8")
	public String emailList(HttpServletRequest request) {

		String json = "[]";
		try {
			PubOperator user = CommonUtils.getCurrentPubOperator(request);
			UmpCompany company = user.getCompany();
			List<VocEmail> list = VocEmail.findAllVocEmailsByCompany(company);
			json = VocEmail.toJsonArray(list, Global.NAME_ID_JSON_ARRARY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	/**
	 * 根据邮件组加载邮件
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="loadTree",produces="text/html;charset=utf-8")
	public String loadEmailTree(HttpServletRequest request,@RequestParam(value="id")Long id){
			VocEmailGroup group = VocEmailGroup.findVocEmailGroup(id);
			List<VocEmail> list = VocEmail.findVocEmailEntriesByGrouId(group);
			StringBuilder tree = new StringBuilder("[");
			boolean flag = true;
			for (VocEmail vocEmail : list) {
				if (flag) {
					flag = false;
					tree.append("{");
					tree.append("id:\'" + vocEmail.getId() + "\',");
					tree.append("name:\'" + vocEmail.getName() + "\',");
					tree.append("pId:\'"+id+"\',");
					tree.append("open:false}");

				} else {
					tree.append(",{");
					tree.append("id:\'" + vocEmail.getId() + "\',");
					tree.append("name:\'" + vocEmail.getName() + "\',");
					tree.append("pId:\'"+id+"\',");
					tree.append("open:false}");
				}
			}
			tree.append("]");
			return tree.toString();
	}
	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"vocEmail_createtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, VocEmail vocEmail) {
		uiModel.addAttribute("vocEmail", vocEmail);
		addDateTimeFormatPatterns(uiModel);
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

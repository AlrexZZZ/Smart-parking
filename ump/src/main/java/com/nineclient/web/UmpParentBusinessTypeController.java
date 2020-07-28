package com.nineclient.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import com.nineclient.model.UmpBusinessType;
import com.nineclient.model.UmpParentBusinessType;
import com.nineclient.model.UmpProduct;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.PageModel;

@RequestMapping("/umpparentbusinesstypes")
@Controller
@RooWebScaffold(path = "umpparentbusinesstypes", formBackingObject = UmpParentBusinessType.class)
public class UmpParentBusinessTypeController {
	@ResponseBody
	@RequestMapping(value="create",method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String create(UmpParentBusinessType umpParentBusinessType,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		List<UmpParentBusinessType> list = UmpParentBusinessType.findAllUmpParentBusinessTypes(
				umpParentBusinessType.getBusinessName(), umpParentBusinessType.getId());
		String msg = "";
		if (umpParentBusinessType.getBusinessName() == null
				|| umpParentBusinessType.getBusinessName().trim().equals("")) {
			return "行业名称不能为空";
		}
		if (list != null && list.size() > 0) {
			msg = "行业名称不能重复";
			return msg;
		}
		try {
			umpParentBusinessType.setIsDeleted(false);
			umpParentBusinessType.setCreateTime(new Date());
			uiModel.asMap().clear();
			umpParentBusinessType.persist();
		} catch (Exception e) {
			e.printStackTrace();
			msg = NotifyMsg.EXCEPTION;
		}
		return msg;
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new UmpParentBusinessType());
		return "umpparentbusinesstypes/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("umpparentbusinesstype",
				UmpParentBusinessType.findUmpParentBusinessType(id));
		uiModel.addAttribute("itemId", id);
		return "umpparentbusinesstypes/show";
	}

	@RequestMapping(value = "listPage", produces = "text/html")
	public String listPage(@RequestParam(value="active",required=false) String active,
			Model model,
			@RequestParam(value="displayId",required=false) Long displayId,
			HttpServletRequest request
			) {
		model.addAttribute("active", active);
		if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(request);
	    }
		request.getSession().setAttribute("displayId", displayId);
		return "umpparentbusinesstypes/businessTypeList";
	}

	@ResponseBody
	@RequestMapping(value = "list", produces = "text/html;charset=utf-8")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {
		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1)
				* sizeNo;
		int pageNo = page == null ? 0 : page.intValue();
		List<UmpParentBusinessType> umpparentbusinesstypes = UmpParentBusinessType
				.findUmpParentBusinessTypeEntries(firstResult, sizeNo,
						sortFieldName, sortOrder);
		Long count = UmpParentBusinessType.countUmpParentBusinessTypes();
		PageModel<UmpParentBusinessType> pageMode = new PageModel<UmpParentBusinessType>();
		pageMode.setDataJson(UmpParentBusinessType
				.toJsonArray(umpparentbusinesstypes));
		pageMode.setPageNo(pageNo);
		pageMode.setPageSize(sizeNo);
		pageMode.setTotalCount(count.intValue());
		return pageMode.toJson();
	}

	/**
	 * 更新行业
	 * 
	 * @param umpParentBusinessType
	 * @param bindingResult
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="update",method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String update(UmpParentBusinessType umpParentBusinessType,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		List<UmpParentBusinessType> list = UmpParentBusinessType
				.findAllUmpParentBusinessTypes(
						umpParentBusinessType.getBusinessName(),
						umpParentBusinessType.getId());
		String msg = "";
		if (umpParentBusinessType.getBusinessName() == null
				|| umpParentBusinessType.getBusinessName().trim().equals("")) {
			return "行业名称不能为空";
		}
		if (list != null && list.size() > 0) {
			msg = "行业名称不能重复";
			return msg;
		}
		try {
			UmpParentBusinessType umpp = UmpParentBusinessType
					.findUmpParentBusinessType(umpParentBusinessType.getId());
			umpp.setRemark(umpParentBusinessType.getRemark());
			umpp.setBusinessName(umpParentBusinessType.getBusinessName());
			umpp.setIsVisable(umpParentBusinessType.getIsVisable());
			uiModel.asMap().clear();
			umpp.merge();
		} catch (Exception e) {
			e.printStackTrace();
			msg = NotifyMsg.EXCEPTION;
		}
		return msg;
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel,
				UmpParentBusinessType.findUmpParentBusinessType(id));
		return "umpparentbusinesstypes/update";
	}
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String delete(@RequestParam(value = "id") Long id, Model uiModel) {
		try {
			UmpParentBusinessType umpParentBusinessType = UmpParentBusinessType
					.findUmpParentBusinessType(id);
			Set<UmpBusinessType> set = umpParentBusinessType.getSubBusinesses();
			if(set!=null&&set.size()>1){
				umpParentBusinessType.remove(set);
			}else{
				umpParentBusinessType.remove();
			}
			uiModel.asMap().clear();
		} catch (Exception e) {
			e.printStackTrace();
			return NotifyMsg.EXCEPTION;
		}
		return NotifyMsg.SUCCESS;
	}
	@ResponseBody
	@RequestMapping(value = "hasSubBusiness", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String findSubBusinessByPBusiness(@RequestParam(value = "id") Long id, Model uiModel){
		UmpParentBusinessType umpParentBusinessType = UmpParentBusinessType
				.findUmpParentBusinessType(id);
		Set<UmpBusinessType> set = umpParentBusinessType.getSubBusinesses();
		if(set!=null&&set.size()>1){
			return "true";
		}
		return "false";
	}
	@ResponseBody
	@RequestMapping(value = "updateVisable", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String updateVisAble(@RequestParam(value="id") Long id,
			@RequestParam(value = "isVisable", required = false) Boolean visable,
			Model uiModel) {
		try {
			UmpParentBusinessType umpProduct = UmpParentBusinessType.findUmpParentBusinessType(id);
			umpProduct.setIsVisable(visable);
			uiModel.asMap().clear();
			umpProduct.merge();
		} catch (Exception e) {
			e.printStackTrace();
			return NotifyMsg.FIALED;
		}
		return NotifyMsg.SUCCESS;
	}
	@ResponseBody
	@RequestMapping(value = "loadParentBusiness", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String loadParentBusiness(){
		String json="[";
		List<UmpParentBusinessType> list = UmpParentBusinessType.findAllUmpParentBusinessTypes();
		boolean flag=true;
		for (UmpParentBusinessType umpParentBusinessType : list) {
			if(flag){
				flag=false;
				json+="{name:\'"+umpParentBusinessType.getBusinessName()+"\',id:"+umpParentBusinessType.getId()+"}";
			}else{
				json+=",{name:\'"+umpParentBusinessType.getBusinessName()+"\',id:"+umpParentBusinessType.getId()+"}";
			}
		}
		json+="]";
		return json;
	}
	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"umpParentBusinessType_createtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel,
			UmpParentBusinessType umpParentBusinessType) {
		uiModel.addAttribute("umpParentBusinessType", umpParentBusinessType);
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

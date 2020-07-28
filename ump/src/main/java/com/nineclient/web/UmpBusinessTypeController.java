package com.nineclient.web;

import com.nineclient.constant.NotifyMsg;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpBrand;
import com.nineclient.model.UmpBusinessType;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpParentBusinessType;
import com.nineclient.model.UmpProduct;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageModel;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

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

@RequestMapping("/umpbusinesstypes")
@Controller
@RooWebScaffold(path = "umpbusinesstypes", formBackingObject = UmpBusinessType.class)
public class UmpBusinessTypeController {
	@ResponseBody
	@RequestMapping(value="create",method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String create(UmpBusinessType umpBusinessType,
			@RequestParam(value = "parentBusinessId") Long parentBusinessId,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		List<UmpBusinessType> list = UmpBusinessType.findAllUmpBusinessTypes(
				umpBusinessType.getBusinessName(), umpBusinessType.getId());
		String msg = "";
		if (umpBusinessType.getBusinessName() == null
				|| umpBusinessType.getBusinessName().trim().equals("")) {
			return "行业名称不能为空";
		}
		if (list != null && list.size() > 0) {
			msg = "行业名称不能重复";
			return msg;
		}
		try {
			//查询服行业
			UmpParentBusinessType parent=UmpParentBusinessType.findUmpParentBusinessType(parentBusinessId);
			if(parent!=null){
				umpBusinessType.setParentBusinessType(parent);
				umpBusinessType.setIsDeleted(false);
				umpBusinessType.setCreateTime(new Date());
				uiModel.asMap().clear();
				umpBusinessType.persist();
			}else{
				msg=NotifyMsg.FIALED;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = NotifyMsg.EXCEPTION;
		}
		return msg;
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new UmpBusinessType());
		return "umpbusinesstypes/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("umpbusinesstype",
				UmpBusinessType.findUmpBusinessType(id));
		uiModel.addAttribute("itemId", id);
		return "umpbusinesstypes/show";
	}

	@RequestMapping(value = "listPage", produces = "text/html")
	public String listPage() {
		return "umpbusinesstypes/list";
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
		List<UmpBusinessType> umpparentbusinesstypes = UmpBusinessType
				.findUmpBusinessTypeEntries(firstResult, sizeNo,
						sortFieldName, sortOrder);
		Long count = UmpBusinessType.countUmpBusinessTypes();
		PageModel<UmpBusinessType> pageMode = new PageModel<UmpBusinessType>();
		pageMode.setDataJson(UmpBusinessType
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
	public String update(UmpBusinessType umpBusinessType,
			BindingResult bindingResult, Model uiModel,
			@RequestParam(value = "parentBusinessId", required = true) Long parentBusinessId,
			HttpServletRequest httpServletRequest) {
		List<UmpBusinessType> list = UmpBusinessType
				.findAllUmpBusinessTypes(
						umpBusinessType.getBusinessName(),
						umpBusinessType.getId());
		String msg = "";
		if (umpBusinessType.getBusinessName() == null
				|| umpBusinessType.getBusinessName().trim().equals("")) {
			return "行业名称不能为空";
		}
		if (list != null && list.size() > 0) {
			msg = "行业名称不能重复";
			return msg;
		}
		try {
			UmpParentBusinessType parent=UmpParentBusinessType.findUmpParentBusinessType(parentBusinessId);
			if(parent!=null){
				UmpBusinessType umpp = UmpBusinessType
						.findUmpBusinessType(umpBusinessType.getId());
				umpp.setRemark(umpBusinessType.getRemark());
				umpp.setParentBusinessType(parent);
				umpp.setBusinessName(umpBusinessType.getBusinessName());
				umpp.setIsVisable(umpBusinessType.getIsVisable());
				uiModel.asMap().clear();
				umpp.merge();
			}else{
				msg=NotifyMsg.FIALED;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = NotifyMsg.EXCEPTION;
		}
		return msg;
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, UmpBusinessType.findUmpBusinessType(id));
		return "umpbusinesstypes/update";
	}
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String delete(@RequestParam(value = "id") Long id, Model uiModel) {
		try {
			UmpBusinessType umpBusinessType = UmpBusinessType
					.findUmpBusinessType(id);
			uiModel.asMap().clear();
			umpBusinessType.remove();
		} catch (Exception e) {
			e.printStackTrace();
			return NotifyMsg.EXCEPTION;
		}
		return NotifyMsg.SUCCESS;
	}
	@ResponseBody
	@RequestMapping(value = "updateVisable", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String updateVisAble(@RequestParam(value="id") Long id,
			@RequestParam(value = "isVisable", required = false) Boolean visable,
			Model uiModel) {
		try {
			UmpBusinessType umpBusiness = UmpBusinessType.findUmpBusinessType(id);
			umpBusiness.setIsVisable(visable);
			uiModel.asMap().clear();
			umpBusiness.merge();
		} catch (Exception e) {
			e.printStackTrace();
			return NotifyMsg.FIALED;
		}
		return NotifyMsg.SUCCESS;
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"umpBusinessType_createtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, UmpBusinessType umpBusinessType) {
		uiModel.addAttribute("umpBusinessType", umpBusinessType);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("umpparentbusinesstypes",
				UmpParentBusinessType.findAllUmpParentBusinessTypes());
		//uiModel.addAttribute("umpbrands", UmpBrand.findAllUmpBrands());
	}

	@RequestMapping(value = "queryBusinessByParentId", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String queryBusinessByParentId(Model uiModel,@RequestParam(value="id",required=false)Long id,
			HttpServletRequest request, HttpServletResponse response) {
		{
			List<UmpBusinessType> list = UmpBusinessType
					.findAllUmpBusinessTypesByParentId(id);
			StringBuilder json = new StringBuilder("[");
			boolean flag = true;
			for (UmpBusinessType umpBusinessType : list) {
				if (flag) {
					flag = false;
					json.append("{id:" + umpBusinessType.getId() + ",name:\'"
							+ umpBusinessType.getBusinessName() + "\'}");
				} else {
					json.append(",{id:" + umpBusinessType.getId() + ",name:\'"
							+ umpBusinessType.getBusinessName() + "\'}");
				}

			}
			json.append("]");
			return json.toString();
		}
	}
	@RequestMapping(value = "queryCompanyBusinessTypes",method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String queryCompanyBusinessTypes(Model uiModel,
			HttpServletRequest request, HttpServletResponse response){
		UmpCompany company = getUser(request);
		UmpParentBusinessType business=UmpParentBusinessType.findUmpParentBusinessTypeByCompany(company);
		List<UmpBusinessType> businessTypes = null;
		String json=null;
		try{
			businessTypes = UmpBusinessType.findAllUmpBusinessTypesByParentId(business.getId());
			JsonConfig config=new JsonConfig();
			//config.setExcludes(new String[]{"dianYuanHeSuans"});
			config.setJsonPropertyFilter(new PropertyFilter() {  
                @Override  
                public boolean apply(Object source, String name, Object value) {  
                    if(name.equals("id")){  
                        return false;  
                    }  
                    else if(name.equals("businessName")){  
                        return false;  
                    }else{ 
                    return true;  
                    }
                }  
            });  
			json=JSONArray.fromObject(businessTypes,config).toString();
		}catch(Exception e){
			e.printStackTrace();
			businessTypes=new ArrayList<UmpBusinessType>();
			return JSONArray.fromObject(businessTypes).toString();
		}
		return json;
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
	/**
	 * 获取公司
	 * @param request
	 * @return
	 */
	private UmpCompany getUser(HttpServletRequest request){
		PubOperator user = (PubOperator) request.getSession().getAttribute(
				Global.PUB_LOGIN_OPERATOR);
		UmpCompany company = user.getCompany();
		return company;
	}
}

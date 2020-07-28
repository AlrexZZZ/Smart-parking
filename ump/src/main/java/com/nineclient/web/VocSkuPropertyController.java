package com.nineclient.web;

import com.nineclient.constant.NotifyMsg;
import com.nineclient.model.CommentDBValidata;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpOperator;
import com.nineclient.model.VocBrand;
import com.nineclient.model.VocSkuProperty;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageBean;
import com.nineclient.utils.PageModel;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.util.HSSFColor.GOLD;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/vocskupropertys")
@Controller
@RooWebScaffold(path = "vocskupropertys", formBackingObject = VocSkuProperty.class)
public class VocSkuPropertyController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid VocSkuProperty vocSkuProperty,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		vocSkuProperty.setCreateTime(new Date());
		vocSkuProperty.setIsDeleted(false);
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, vocSkuProperty);
			return "vocskupropertys/create";
		}
		uiModel.asMap().clear();
		vocSkuProperty.persist();
		return "redirect:/vocskupropertys";
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new VocSkuProperty());
		return "vocskupropertys/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("vocskuproperty",
				VocSkuProperty.findVocSkuProperty(id));
		uiModel.addAttribute("itemId", id);
		return "vocskupropertys/show";
	}
	@ResponseBody
	@RequestMapping(value="update",method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String update(VocSkuProperty vocSkuProperty,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		String json = "";
		VocSkuProperty voc = VocSkuProperty.findVocSkuProperty(vocSkuProperty.getId());
		UmpCompany company = getUser(httpServletRequest);
		vocSkuProperty.setUmpCompany(company);
		String name = vocSkuProperty.getName() == null ? "" : vocSkuProperty
				.getName();
		if (null == name || "".equals(name)) {
			json = "属性名称不能为空";
			return json;
		}
		
		if(null == vocSkuProperty.getPropertyDetail() || "".equals(vocSkuProperty.getPropertyDetail())){
			json = "属性定义不能为空";
			return json;
		}
		
		Map<String,Object> field= new HashMap<String, Object>();
		field.put("name", vocSkuProperty.getName());
		field.put("umpCompany", company);
		field.put("id", vocSkuProperty.getId());
		Long count = CommentDBValidata.queryUniqueCount("VocSkuProperty", field);
		if (count> 0) {
			json = "属性名称不能重复";
			return json;
		}
		try {
			voc.setName(name);
			voc.setPropertyDetail(vocSkuProperty.getPropertyDetail());
			voc.merge();
			json = "成功";
		} catch (Exception e) {
			e.printStackTrace();
			json = "失败";
		}
		return json;
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, VocSkuProperty.findVocSkuProperty(id));
		return "vocskupropertys/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		VocSkuProperty vocSkuProperty = VocSkuProperty.findVocSkuProperty(id);
		vocSkuProperty.remove();
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/vocskupropertys";
	}

	@RequestMapping(value = "loadSkuProperty", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String loadSkuProperty(Model uiModel,
			HttpServletRequest httpServletRequest, HttpServletResponse response) {
		String json = "[";
		UmpCompany company = getUser(httpServletRequest);
		List<VocSkuProperty> list = VocSkuProperty.findAllVocSkuPropertys(company);
		boolean flag = true;
		for (VocSkuProperty vocSkuProperty : list) {
			if (flag) {
				flag = false;
				json += "{id:" + vocSkuProperty.getId() + ",name:\'"
						+ vocSkuProperty.getName() + "\'}";
			} else {
				json += ",{id:" + vocSkuProperty.getId() + ",name:\'"
						+ vocSkuProperty.getName() + "\'}";
			}
		}
		json += "]";
		return json;

	}

	@RequestMapping(value = "addVocSkuProperty", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String addVocSkuProperty(VocSkuProperty vocSkuProperty,
			Model uiModel, HttpServletRequest httpServletRequest,
			HttpServletResponse response) {
		String json = "";
		UmpCompany company = getUser(httpServletRequest);
		vocSkuProperty.setUmpCompany(company);
		String name = vocSkuProperty.getName() == null ? "" : vocSkuProperty
				.getName();
		if (StringUtils.isEmpty(name)) {
			json = "属性名称不能为空";
			return json;
		}
		if(StringUtils.isEmpty(vocSkuProperty.getPropertyDetail())){
			json = "属性定义不能为空";
			return json;
		}
		Map<String,Object> field= new HashMap<String, Object>();
		field.put("name", vocSkuProperty.getName());
		field.put("umpCompany", company);
		Long count = CommentDBValidata.queryUniqueCount("VocSkuProperty", field);
		if (count> 0) {
			json = "属性名称不能重复";
			return json;
		}
		vocSkuProperty.setCreateTime(new Date());
		vocSkuProperty.setIsDeleted(false);
		vocSkuProperty.setIsVisable(true);
		try {
			vocSkuProperty.persist();
			json = "成功";
		} catch (Exception e) {
			e.printStackTrace();
			json = "失败";
		}
		return json;
	}

	@RequestMapping(value = "listSkuProperty", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String listSkuProperty(
			PageBean pageBean,
			VocSkuProperty vocSkuProperty, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel,
			HttpServletRequest httpServletRequest, HttpServletResponse response) {
		String json = "";
		UmpCompany umpcompany = getUser(httpServletRequest);
		String name = vocSkuProperty.getName() == null ? "" : vocSkuProperty
				.getName();
		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1)
				* sizeNo;
		page=page==null?0:page;
		List<VocSkuProperty> list = VocSkuProperty
				.findVocSkuPropertyEntries(umpcompany,
						vocSkuProperty.getId(), vocSkuProperty.getIsVisable(), pageBean.getStartTime(), pageBean.getEndTime(),
						firstResult, sizeNo,
						sortFieldName, sortOrder);
		int count = (int) VocSkuProperty.countVocSkuPropertys(umpcompany,vocSkuProperty.getId(), vocSkuProperty.getIsVisable(), pageBean.getStartTime(), pageBean.getEndTime());
				
		PageModel pageModel = new PageModel();
		pageModel.setDataList(list);
		pageModel.setDataJson(VocSkuProperty.toJsonArray(list));
		pageModel.setPageSize(sizeNo);
		pageModel.setPageNo(page);
		pageModel.setTotalCount(count);
		json = pageModel.toJson();
		return json;
	}
	@RequestMapping(value = "delete", produces = "text/html;charset=utf-8")
	@ResponseBody
    public String deleteSkuProperty(@RequestParam(value = "id", required = false) Long id){
		try{
		VocSkuProperty.findVocSkuProperty(id).remove();
		}catch(Exception e){
			e.printStackTrace();
			return "失败";
		}
		return "成功";
    }
	
	/**
	 * 更新状态
	 * @param request
	 * @param visable
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateVisable",method=RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String updateVisable(HttpServletRequest request,
			@RequestParam(value = "visable",required=false) Long visable,
			@RequestParam(value = "id",required=false) Long id) {
		String msg ="";
		try{
			VocSkuProperty property = VocSkuProperty.findVocSkuProperty(id);
			if(visable==1){
				property.setIsVisable(true);
			}else{
				property.setIsVisable(false);
			}
			property.merge();
			msg=NotifyMsg.SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			msg=NotifyMsg.FIALED;
		}
		return msg;
			
	}
	/**
	 * 根据属性ID查询属性
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryById",method=RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String queryById(@RequestParam(value = "id") Long id){
		VocSkuProperty property = VocSkuProperty.findVocSkuProperty(id);
		JSONObject json = null;
		if(property!=null){
		 json = JSONObject.fromObject(property);
		}
		return json.toString();
	}
	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"vocSkuProperty_createtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, VocSkuProperty vocSkuProperty) {
		uiModel.addAttribute("vocSkuProperty", vocSkuProperty);
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

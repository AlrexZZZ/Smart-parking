package com.nineclient.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import com.nineclient.constant.NotifyMsg;
import com.nineclient.model.CommentDBValidata;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpBusinessType;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpParentBusinessType;
import com.nineclient.model.VocBrand;
import com.nineclient.model.VocGoodsProperty;
import com.nineclient.model.VocGoodsPropertyValue;
import com.nineclient.model.VocSku;
import com.nineclient.model.VocSkuProperty;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageBean;
import com.nineclient.utils.PageModel;

@RequestMapping("/vocskus")
@Controller
@RooWebScaffold(path = "vocskus", formBackingObject = VocSku.class)
public class VocSkuController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid VocSku vocSku, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, vocSku);
			return "vocskus/create";
		}
		uiModel.asMap().clear();
		vocSku.persist();
		return "redirect:/vocskus/"
				+ encodeUrlPathSegment(vocSku.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new VocSku());
		return "vocskus/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("vocsku", VocSku.findVocSku(id));
		uiModel.addAttribute("itemId", id);
		return "vocskus/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "displayId", required = false) Long displayId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {
		
		HttpSession session=	httpServletRequest.getSession();
		UmpCompany company = getUser(httpServletRequest);
		if(session.getAttribute("importmsg")!=null)
		{
			uiModel.addAttribute("importmsg", session.getAttribute("importmsg"));
			session.removeAttribute("importmsg");
		}
		if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(httpServletRequest);
	    }
		httpServletRequest.getSession().setAttribute("displayId", displayId);
		addSelectData(uiModel,company);
		addDateTimeFormatPatterns(uiModel);
		return "vocskus/list";
	}
	@ResponseBody
	@RequestMapping(value="update",method=RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String update(@RequestParam(value = "businessTypeId",required=false) Long businessTypeId,
			@RequestParam(value = "vocBrandId",required=false) Long vocBrandId,
			@RequestParam(value = "goodsName",required=false) String goodsName,
//			@RequestParam(value = "skuCode") String skuCode,
			@RequestParam(value = "skuPropertyId",required=false) Long skuPropertyId,
//			@RequestParam(value = "skuName") String[] skuName,
			VocSku vocSku, HttpServletRequest httpServletRequest,Model uiModel) {
		if(StringUtils.isEmpty(businessTypeId)||businessTypeId==-1){
			return "请选择行业";
		}
		if(StringUtils.isEmpty(vocBrandId)||vocBrandId==-1){
			return "请选择品牌";
		}
		if(StringUtils.isEmpty(goodsName)){
			return "SKU名称不能为空";
		}
		if(StringUtils.isEmpty(vocSku.getSkuCode())){
			return "SKU编号不能为空";
		}
		Long id = vocSku.getId();
		UmpCompany company = getUser(httpServletRequest);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("name", goodsName);
		map.put("companyCode", company.getCompanyCode());
		map.put("id", id);
		Long cvocSku = CommentDBValidata.queryUniqueCount("VocSku", map);
		if(cvocSku>0){
			return "SKU名称不能重复";
		}
		VocSku voc = VocSku.findVocSku(id);
		voc.setName(goodsName);
		UmpBusinessType umpBusinessType = UmpBusinessType.findUmpBusinessType(businessTypeId);
		VocBrand vocBrand =VocBrand.findVocBrand(vocBrandId);
		VocSkuProperty property = VocSkuProperty.findVocSkuProperty(skuPropertyId);
		voc.setVocSkuProperty(property);
		voc.setVocBrand(vocBrand);
		voc.setUmpBusinessType(umpBusinessType);
		voc.setSkuCode(vocSku.getSkuCode());
		voc.setSkuModel(vocSku.getSkuModel());
		voc.setSkuOrigin(vocSku.getSkuOrigin());
		voc.setSkuPackage(vocSku.getSkuPackage());
		voc.setSkuType(vocSku.getSkuType());
		voc.setSkuWeight(vocSku.getSkuWeight());
		try{
			voc.merge();
		}catch(Exception e){
			e.printStackTrace();
			return NotifyMsg.EXCEPTION;
		}
		return NotifyMsg.SUCCESS;
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, VocSku.findVocSku(id));
		return "vocskus/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		VocSku vocSku = VocSku.findVocSku(id);
		vocSku.remove();
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/vocskus";
	}

	@RequestMapping(value = "addSkuGoods", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String addSkuGoosds(
			HttpServletRequest request,
			@RequestParam(value = "businessTypeId",required=false) Long businessTypeId,
			@RequestParam(value = "vocBrandId",required=false) Long vocBrandId,
			@RequestParam(value = "goodsName",required=false) String goodsName,
//			@RequestParam(value = "skuCode") String skuCode,
			@RequestParam(value = "skuPropertyId",required=false) Long skuPropertyId,
//			@RequestParam(value = "skuName") String[] skuName,
			VocSku vocSku,
			@RequestParam(value = "goodsPropertyValue",required=false) String[] goodsPropertyValue) {
		if(StringUtils.isEmpty(businessTypeId)||businessTypeId==-1){
			return "请选择行业";
		}
		if(StringUtils.isEmpty(vocBrandId)||vocBrandId==-1){
			return "请选择品牌";
		}
		if(StringUtils.isEmpty(goodsName)){
			return "SKU名称不能为空";
		}
		if(StringUtils.isEmpty(vocSku.getSkuCode())){
			return "SKU编号不能为空";
		}
		UmpCompany company = getUser(request);
		vocSku.setCreateTime(new Date());
		vocSku.setIsDeleted(false);
		vocSku.setIsVisable(true);
		
		vocSku.setCompanyCode(company.getCompanyCode());
		VocSkuProperty vocSkuProperty = VocSkuProperty
				.findVocSkuProperty(skuPropertyId);
		vocSku.setVocSkuProperty(vocSkuProperty);
		VocBrand vocBrand = VocBrand.findVocBrand(vocBrandId);
		vocSku.setVocBrand(vocBrand);
		UmpBusinessType umpBusinessType = UmpBusinessType.findUmpBusinessType(businessTypeId);
		vocSku.setUmpBusinessType(umpBusinessType);
		vocSku.setName(goodsName);
		String json = "";
		try {
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("name", goodsName);
			map.put("companyCode", company.getCompanyCode());
			Long cvocSku = CommentDBValidata.queryUniqueCount("VocSku", map);
			if(cvocSku>0){
				return "SKU名称不能重复";
			}
//			VocGoodsPropertyValue vocGoodsPropertyValues = null;
//			Set<VocGoodsPropertyValue> setList = new HashSet<VocGoodsPropertyValue>();
//			for (int j = 0; j < skuName.length; j++) {
//				vocGoodsPropertyValues = new VocGoodsPropertyValue();
//				vocGoodsPropertyValues.setName(skuName[j]);
//				vocGoodsPropertyValues.setPropertyValue(goodsPropertyValue[j]);
//				setList.add(vocGoodsPropertyValues);
//			}
//			vocSku.setVocGoodsPropertyValues(setList);
			vocSku.persist();
			json = "成功";
		} catch (Exception e) {
			e.printStackTrace();
			json = "失败";
		}
		return json;
	}
	/**
	 * 根据子行业查询sku
	 * @param businessTypeId
	 * @param page
	 * @param size
	 * @param sortFieldName
	 * @param sortOrder
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "skuGoodsList", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String skuGoodsList(
			HttpServletRequest request,
			PageBean pageBean,
			@RequestParam(value = "businessTypeId", required = false) Long businessTypeId,
			@RequestParam(value = "vocBrandId", required = false) Long vocBrandId,
			@RequestParam(value = "skuGoodsName", required = false) String skuGoodsName,
			@RequestParam(value = "vocSkuPropertyId", required = false) Long vocSkuPropertyId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			page=page==null?0:page;
			Map<String,Object> map= new HashMap<String,Object>();
			UmpCompany company = getUser(request);
			map.put("businessTypeId", businessTypeId);
			map.put("company", company);
			VocSkuProperty vocSkuProperty = VocSkuProperty.findVocSkuProperty(vocSkuPropertyId);
			List<VocSku> list = VocSku.findVocSkuEntries(businessTypeId,vocBrandId,skuGoodsName,vocSkuProperty,pageBean.getStartTime(),pageBean.getEndTime(),
					firstResult, sizeNo, sortFieldName, sortOrder);
//			String valuesJson = "[";
//			boolean flag= true;
//			for (VocSku vocSku : list) {
//				List<VocGoodsPropertyValue> vales =	VocGoodsPropertyValue.findAllVocGoodsPropertyValuesBySkuId(vocSku.getId());
//				if(flag){
//					flag=false;
//					valuesJson+="{skuId:"+vocSku.getId()+",propertyValueList:"+VocGoodsPropertyValue.toJsonArray(vales)+"}";
//				}else{
//					valuesJson+=",{skuId:"+vocSku.getId()+",propertyValueList:"+VocGoodsPropertyValue.toJsonArray(vales)+"}";
//				}
//			}
//			valuesJson+="]";
			Long count = VocSku.countVocSkus(businessTypeId,vocBrandId,skuGoodsName,vocSkuProperty,pageBean.getStartTime(),pageBean.getEndTime());
			List<VocGoodsProperty> listGoodsProperty = VocGoodsProperty.findVocGoodsPropertyByBusinessId(businessTypeId);
			PageModel<VocSku> pageModel=new PageModel<VocSku>();
			pageModel.setPageSize(sizeNo);
			pageModel.setPageNo(page);
			pageModel.setTotalCount(count.intValue());
			pageModel.setDataJson(VocSku.toJsonArray(list));
			pageModel.setDataHtml(VocGoodsProperty.toJsonArray(listGoodsProperty));
		return pageModel.toJson();
	}
	/**
	 * 根据品牌加载sku商品
	 * @param vocBrandId
	 * @return
	 */
	@RequestMapping(value = "querySkuGoodsList", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String querySkuGoodsList(@RequestParam(value = "vocBrandId", required = false) String vocBrandId){
		List<VocSku> list = VocSku.findAllVocSkusByVocBrandId(vocBrandId);
		return VocSku.toJsonArray(list);
	}
	@RequestMapping(value = "delete", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String deleteSkuGoods(@RequestParam(value = "id", required = false) Long id){
		VocSku skugoods = VocSku.findVocSku(id);
		//先删除属性值
		List<VocGoodsPropertyValue> list = VocGoodsPropertyValue.findAllVocGoodsPropertyValuesBySkuId(id);
		String msg="";
		try{
		 skugoods.remove(list);
		 msg="成功";
		}catch(Exception e){
			e.printStackTrace();
			msg="失败";
		}
		return msg;
	}
	/**
	 * 根据商品名称糊匹配sku 商品
	 * @param skuName
	 * @param page
	 * @param size
	 * @param sortFieldName
	 * @param sortOrder
	 * @param uiModel
	 * @return
	 */
	public String querySkuGoodsBySkuName(@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "vocBrandId", required = false) Long vocBrandId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel){

		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1)
				* sizeNo;
		Map<String,Object> map= new HashMap<String,Object>();
		map.put("skuName", skuName);
		map.put("vocBrandId", vocBrandId);
		List<VocSku> list = VocSku.findVocSkuEntries(map,
				firstResult, sizeNo, sortFieldName, sortOrder);
		int count = (int) VocSku.countVocSkus(map);
		PageModel<VocSku> pageModel=new PageModel<VocSku>();
		pageModel.setPageSize(sizeNo);
		pageModel.setTotalCount(count);
		pageModel.setDataJson(VocSku.toJsonArray(list));
	return pageModel.toJson();

		
	}
	private void addSelectData(Model uiModel,UmpCompany company) {
		// 行业
//		uiModel.addAttribute("umpparentbusinesstypes",
//				UmpParentBusinessType.findAllUmpParentBusinessTypes());
		UmpParentBusinessType pb=UmpParentBusinessType.findUmpParentBusinessTypeByCompany(company);
		//父行业
		 uiModel.addAttribute("parentBusinessType",
				 pb);
		List<UmpBusinessType> list	= UmpBusinessType.findAllUmpBusinessTypesByParentId(pb.getId());
		// //子行业
		 uiModel.addAttribute("umpbusinesstypes",
				 list);
		// //sku属性
		// uiModel.addAttribute("vocgoodsproperties",
		// VocGoodsProperty.findAllVocGoodsPropertys());
		 Map<String,Object> map = new HashMap<String, Object>();
		 map.put("umpCompanyId", company.getId());
		 //品牌
		uiModel.addAttribute("vocbrands",
		 VocBrand.findVocBrandsByChannelAndCompany(map));
		//属性
		uiModel.addAttribute("vocskupropertys",
				 VocSkuProperty.findAllVocSkuPropertys(company));
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"vocSku_createtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, VocSku vocSku) {
		uiModel.addAttribute("vocSku", vocSku);
		UmpCompany company = vocSku.getVocBrand().getUmpCompany();
		Long companyId = company.getId();
		Set<UmpParentBusinessType> umpParentBusinessType = company.getParentBusinessType();
		UmpParentBusinessType parentBusiness = null;
		for (UmpParentBusinessType umpParentBusinessType2 : umpParentBusinessType) {
			parentBusiness=umpParentBusinessType2;
		}
		uiModel.addAttribute("umpparentbusinesstype", parentBusiness);
		uiModel.addAttribute("vocSku", vocSku);
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

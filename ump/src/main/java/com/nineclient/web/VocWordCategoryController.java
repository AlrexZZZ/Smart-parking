package com.nineclient.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.nineclient.constant.NotifyMsg;
import com.nineclient.constant.VocAuditStatus;
import com.nineclient.model.CommentDBValidata;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpAuthority;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.VocWordCategory;
import com.nineclient.model.VocWordExpressions;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageModel;

@RequestMapping("/vocwordcategorys")
@Controller
@RooWebScaffold(path = "vocwordcategorys", formBackingObject = VocWordCategory.class)
public class VocWordCategoryController {
	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(HttpServletRequest httpServletRequest,Model uiModel,@RequestParam(value = "displayId", required = false) Long displayId){
		
		if(displayId == null){
			displayId = CommonUtils.getCurrentDisPlayId(httpServletRequest);
		}
		httpServletRequest.getSession().setAttribute("displayId",displayId);
		if(CommonUtils.getCurrentPubOperator(httpServletRequest).getPubRole() == null){
			httpServletRequest.getSession().setAttribute("audMenu",true);
		}else{
			Map<String, String> auditMenu = (Map<String, String>) httpServletRequest.getSession().getAttribute("auditMenu");
			if(null != auditMenu && auditMenu.keySet().size()>0){
				UmpAuthority umpMenu = null;
				for (String menuId : auditMenu.keySet()) {
					umpMenu  = UmpAuthority.findUmpAuthority(Long.parseLong(menuId));
					if(null != umpMenu){
						uiModel.addAttribute("audMenu",true);
					}
				}
			}else{
				uiModel.addAttribute("audMenu",false);
			}
		}
		addDateTimeFormatPatterns(uiModel);
		populateEditForm(uiModel, new VocWordCategory());
		return "vocwordcategorys/create";
	}
	@ResponseBody
	@RequestMapping(value="keyWordList", produces = "text/html;charset=utf-8")
	public String list(
			HttpServletRequest request,
			@RequestParam(value = "keyName", required = false) String keyName,
			@RequestParam(value = "pId", required = false) Long pId,
			@RequestParam(value = "cId", required = false) Long cId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {
		UmpCompany company= getUser(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyName", keyName);
		map.put("pId", pId);
		map.put("cId", cId);
		map.put("companyId", company.getId());
		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1)
				* sizeNo;
		page=page == null ? 0 : page.intValue();
		List<VocWordExpressions> listVocWord = VocWordExpressions
				.findVocWordExpressionsEntries(firstResult, sizeNo,
						sortFieldName, sortOrder, map);
		Long count = VocWordExpressions.countVocWordExpressionses(map);
		PageModel<VocWordExpressions> pageMode = new PageModel<VocWordExpressions>();
		pageMode.setPageNo(page);
		pageMode.setTotalCount(count.intValue());
		pageMode.setPageSize(sizeNo);
		pageMode.setDataJson(VocWordExpressions.toJsonArray(listVocWord));
		return pageMode.toJson();
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("vocwordcategory",
				VocWordCategory.findVocWordCategory(id));
		uiModel.addAttribute("itemId", id);
		return "vocwordcategorys/show";
	}


	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, VocWordCategory.findVocWordCategory(id));
		return "vocwordcategorys/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		VocWordCategory vocWordCategory = VocWordCategory
				.findVocWordCategory(id);
		vocWordCategory.remove();
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/vocwordcategorys";
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"vocWordCategory_createtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, VocWordCategory vocWordCategory) {
		uiModel.addAttribute("vocWordCategory", vocWordCategory);
		addDateTimeFormatPatterns(uiModel);
	}

	/**
	 * 加载词库分类树
	 * 
	 * @param productId
	 * @param uiModel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "loadCategoryList", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String tree(@RequestParam(value = "id", required = false) Long id,
			Model uiModel, HttpServletRequest request,
			HttpServletResponse response) {
		UmpCompany company = getUser(request);
		List<VocWordCategory> vocWordCategorys = VocWordCategory
				.findAllVocWordCategorysByCompany(company);
		StringBuilder tree = new StringBuilder("[");
		boolean flag = true;
		for (VocWordCategory vocWord : vocWordCategorys) {
			if (flag) {
				flag = false;
				tree.append("{");
				tree.append("id:\'" + vocWord.getId() + "\',");
				tree.append("name:\'" + vocWord.getName() + "\',");
				tree.append("pId:\'" + vocWord.getParentId() + "\',");
				tree.append("isDefault:" + vocWord.getIsDefault()+ ",");
				tree.append("level:" + vocWord.getLevel()+ ",");
				tree.append("open:true}");

			} else {
				tree.append(",{");
				tree.append("id:\'" + vocWord.getId() + "\',");
				tree.append("name:\'" + vocWord.getName() + "\',");
				tree.append("pId:\'" + vocWord.getParentId() + "\',");
				tree.append("isDefault:" + vocWord.getIsDefault()+ ",");
				tree.append("level:" + vocWord.getLevel()+ ",");
				tree.append("open:true}");
			}

		}
		tree.append("]");
		return tree.toString();
	}
	@RequestMapping(value = "selectCategory", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String selectCategory(
			HttpServletRequest request,
			@RequestParam(value = "pId", required = false) Long pId){
		UmpCompany company = getUser(request);
		List<VocWordCategory> vocWordCategorys = VocWordCategory
				.findVocWordCategorysByParentId(pId,company);
		String json ="[";
		boolean flag = true;
		for (VocWordCategory vocWordCategory : vocWordCategorys) {
			if(flag){
				flag=false;
				json+="{id:"+vocWordCategory.getId()+",name:\'"+vocWordCategory.getName()+"\'}";
			}else{
				json+=",{id:"+vocWordCategory.getId()+",name:\'"+vocWordCategory.getName()+"\'}";
			}
		}
		json+="]";
		return json;
	}
	@RequestMapping(value = "addNode", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String addNode(
			HttpServletRequest request,
			@RequestParam(value = "level", required = false) Integer level,
			@RequestParam(value = "pId", required = false) Long pId,
			@RequestParam(value = "name", required = false) String name) {
		String msg = "";
		UmpCompany company = getUser(request);
		VocWordCategory vocWord = new VocWordCategory();
		if(StringUtils.isEmpty(name)||name.trim().equals("")){
			return "名称不能为空";
		}
		//查询当前分类下的子节点
		List<VocWordCategory> allList= VocWordCategory.findVocWordCategorysByParentIdAndCompany(pId, company);
		for (VocWordCategory vocWordCategory : allList) {
			if(vocWordCategory.getName().equals(name)){
				return "名称不能重复";
			}
		}
		//唯一验证
		vocWord.setParentId(pId);
		vocWord.setName(name);
		vocWord.setCreateTime(new Date());
		vocWord.setIsDeleted(false);
		vocWord.setIsDefault(false);
		vocWord.setLevel(level);
		vocWord.setCompanyId(company.getId());
		try {
			vocWord.persist();
			msg = "添加成功";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "添加失败";
		}
		return msg;
	}

	@RequestMapping(value = "editNode",method=RequestMethod.POST,produces = "text/html;charset=utf-8")
	@ResponseBody
	public String editNode(
			HttpServletRequest request,
			@RequestParam(value = "level", required = false) Integer level,
			@RequestParam(value = "nodeId", required = false) Long nodeId,
			@RequestParam(value = "nodeName", required = false) String nodeName) {
		String msg = "";
		if(StringUtils.isEmpty(nodeName)||nodeName.trim().equals("")){
			return "名称不能为空";
		}
		UmpCompany company = getUser(request);
		//唯一验证
		Long count =CommentDBValidata.uniqueCountTreeNode("VocWordCategory", nodeId,nodeName,level,company.getId());
		if(count>0){
			return "名称不能重复";
		}
		
		VocWordCategory vocWord = VocWordCategory.findVocWordCategory(nodeId);
		vocWord.setName(nodeName);
		try {
			vocWord.merge();
			msg = "修改成功";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "修改失败";
		}
		return msg;
	}

	@RequestMapping(value = "deleteNode", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String deleteNode(
			@RequestParam(value = "nodeId", required = false) Long nodeId) {
		String msg = "";
		if(nodeId!=null&&nodeId<=21){
			return "系统默认分类不可删除";
		}
		VocWordCategory vocWord = VocWordCategory.findVocWordCategory(nodeId);
		try {
			vocWord.remove();
			msg = "\'成功\'";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "\'失败\'";
		}
		return msg;
	}

	@RequestMapping(value = "addKeyWord", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String addKeyWord(
			HttpServletRequest request,
			@RequestParam(value = "nodeId", required = false) Long nodeId,
			@RequestParam(value = "keyWord", required = false) String keyWord) {
		String msg = "";
		if(StringUtils.isEmpty(keyWord)||keyWord.trim().equals("")){
			return "关键词不能为空";
		}
		UmpCompany company = getUser(request);
		//验证同一分类下不能重复关键字
		Long count = VocWordExpressions.uniqueVocWordExpressionses(keyWord,nodeId,company.getId());
		if(count>0){
			return "关键词不能重复";
		}
		VocWordCategory vocWord = VocWordCategory.findVocWordCategory(nodeId);
		
		try {
			VocWordExpressions vocKeyWord = new VocWordExpressions();
			vocKeyWord.setAuditStatus(VocAuditStatus.待审核);
			vocKeyWord.setCreateTime(new Date());
			vocKeyWord.setIsDeleted(false);
			vocKeyWord.setName(keyWord);
			vocKeyWord.setVocWordCatagory(vocWord);
			vocKeyWord.setCompanyId(company.getId());
			vocKeyWord.persist();
			msg = NotifyMsg.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			msg = NotifyMsg.FIALED;
		}
		return msg;
	}

	@RequestMapping(value = "queryKeyWordById", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String queryKeyWordById(
			@RequestParam(value = "id", required = false) Long id) {
		String msg = "";
		VocWordExpressions vocWord = VocWordExpressions
				.findVocWordExpressions(id);
		msg = vocWord.toJson();
		return msg;
	}

	@RequestMapping(value = "editKeyWord", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String editKeyWord(
			HttpServletRequest request,
			@RequestParam(value = "nodeId", required = false) Long nodeId,
			@RequestParam(value = "keyId", required = false) Long id,
			@RequestParam(value = "keyWord", required = false) String keyWord) {
		String msg = "";
		if(StringUtils.isEmpty(keyWord)||keyWord.trim().equals("")){
			return "关键词不能为空";
		}
		UmpCompany company = getUser(request);
		//验证同一分类下不能重复关键字
		Long count = VocWordExpressions.uniqueVocWordExpressionses(keyWord,nodeId,company.getId());
		if(count>0){
			return "关键词不能重复";
		}
		VocWordExpressions vocWord = VocWordExpressions
				.findVocWordExpressions(id);
		vocWord.setName(keyWord);
		vocWord.setAuditStatus(VocAuditStatus.待审核);
		try {
			vocWord.merge();
			msg = NotifyMsg.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			msg = NotifyMsg.FIALED;

		}
		return msg;
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

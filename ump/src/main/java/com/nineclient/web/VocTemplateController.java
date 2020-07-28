package com.nineclient.web;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.nineclient.constant.VocCommentLevel;
import com.nineclient.model.UmpOperator;
import com.nineclient.model.UmpParentBusinessType;
import com.nineclient.model.VocBrand;
import com.nineclient.model.VocTemplate;
import com.nineclient.model.VocTemplateRule;
import com.nineclient.model.VocWordCategory;
import com.nineclient.model.VocWordExpressions;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@RequestMapping("/voctemplates")
@Controller
@RooWebScaffold(path = "voctemplates", formBackingObject = VocTemplate.class)
public class VocTemplateController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid VocTemplate vocTemplate, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, vocTemplate);
            return "voctemplates/create";
        }
        uiModel.asMap().clear();
        vocTemplate.persist();
        return "redirect:/voctemplates/" + encodeUrlPathSegment(vocTemplate.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new VocTemplate());
        return "voctemplates/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("voctemplate", VocTemplate.findVocTemplate(id));
        uiModel.addAttribute("itemId", id);
        return "voctemplates/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel,HttpServletRequest request) {
		uiModel.addAttribute("commentLevels", VocCommentLevel.values());
		Map<String, Object> map = new HashMap<String, Object>();
		Long companyId = CommonUtils.getCurrentCompanyId(request);
		map.put("umpCompanyId",companyId );	
		
		List<UmpParentBusinessType> parentBussinesslist = new ArrayList<UmpParentBusinessType>();
		UmpParentBusinessType pb = UmpParentBusinessType.findUmpParentBusinessTypeByCompany(CommonUtils.getCurrentPubOperator(request).getCompany());
		parentBussinesslist.add(pb);
		uiModel.addAttribute("brands", VocBrand.findVocBrandbyCompany(CommonUtils.getCurrentPubOperator(request).getCompany()));
		uiModel.addAttribute("parentBussinessTypes", parentBussinesslist);
		addDateTimeFormatPatterns(uiModel);
        
        return "voctemplates/list";
    }
	
	/**
	 * 根据行业类别查询品牌
	 */
	@ResponseBody
	@RequestMapping(value = "getBrandByParentBussinessTypeId")
    public void getBrandByParentBussinessTypeId(
    		@RequestParam(value = "id", required = true) Long id,
			HttpServletRequest request, HttpServletResponse response) {
    	response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			 out = response.getWriter();
			 StringBuffer sb = new StringBuffer(); // 拼成Json格式字符串{key:value,key1:value1}
			 UmpParentBusinessType parentBussinessType = UmpParentBusinessType.findUmpParentBusinessType(id);
			 
			Set<VocBrand> brands =  parentBussinessType.getVocBrands();
			sb.append(" [ ");
			if(null != brands && brands.size() > 0){
				boolean flag = true;
				for(VocBrand brand:brands){
					if(flag){
					  sb.append("{id:"+brand.getId()+",name:\'"+brand.getBrandName()+"\'}");
					  flag = false;	
					}else{
					  sb.append(",{id:"+brand.getId()+",name:\'"+brand.getBrandName()+"\'}");
					}
				}
			}
			sb.append(" ] ");
			
			 out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

    }
	
	
	/**
	 * 根据品牌，分类获取模板
	 */
	@ResponseBody
	@RequestMapping(value = "callTemplate")
    public void callTemplate(
    		@RequestParam(value = "commentLevel", required = true) Long commentLevel,
    		@RequestParam(value = "brandId", required = true) Long brandId,
			HttpServletRequest request, HttpServletResponse response) {
    	response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			 out = response.getWriter();
			 StringBuffer sb = new StringBuffer(); 
			 Map param = new HashMap();
			 param.put("commentLevel", commentLevel);
			 param.put("brandId", brandId);
			 
			List<VocTemplate> vtList = VocTemplate.findVocTemplates(param);
			   String level1 = "level1_"+brandId;
			   String level2 = "level2_"+commentLevel;
			   VocBrand vb = VocBrand.findVocBrand(brandId);
			   sb.append("{id:\""+level1+"\",pId:\"-1\",name:\""+vb.getBrandName()+"\",isLeaf:false,open:true}");
			   sb.append(",{id:\""+level2+"\",pId:\""+level1+"\",name:\""+VocCommentLevel.getEnum(commentLevel.intValue()).getName()+"\",isLeaf:false,open:true}");
			
			if(null != vtList && vtList.size() > 0){
				for(VocTemplate template:vtList){
				  String level3 = "level3_"+template.getVocWordCategory().getId();
				  sb.append(",{id:\""+level3+"\",pId:\""+level2+"\",name:\""+template.getVocWordCategory().getName()+"\",isLeaf:false,open:true}");
				  sb.append(",{id:\""+template.getId()+"\",pId:\""+level3+"\",name:\""+template.getTitle()+"\",content:\""+template.getContent()+"\",isLeaf:true,open:true}");
				}
			}
			
			 out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

    }
	
	/**
	 * 根据分类id查询子分类
	 */
	@ResponseBody
	@RequestMapping(value = "getWordCategoryById")
    public void getWordCategoryById(
    		@RequestParam(value = "id", required = true) Long id,
			HttpServletRequest request, HttpServletResponse response) {
    	response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			 out = response.getWriter();
			 StringBuffer sb = new StringBuffer(); // 拼成Json格式字符串{key:value,key1:value1}
			 List<VocWordCategory> wordCategoryList = VocWordCategory.findVocWordCategorysByParentId(id, CommonUtils.getCurrentPubOperator(request).getCompany());
			 
			sb.append(" [ ");
			if(null != wordCategoryList && wordCategoryList.size() > 0){
				boolean flag = true;
				for(VocWordCategory wc:wordCategoryList){
					if(flag){
					  sb.append("{id:"+wc.getId()+",name:\'"+wc.getName()+"\'}");
					  flag = false;	
					}else{
					  sb.append(",{id:"+wc.getId()+",name:\'"+wc.getName()+"\'}");
					}
				}
			}
			sb.append(" ] ");
			
			 out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

    }
	
	
	
	/**
	 * 根据词库分类查询关键词
	 */
	@ResponseBody
	@RequestMapping(value = "getWordExpressionByWordCategoryId")
    public void getWordExpressionByWordCategoryId(
    		HttpServletRequest request, 
			HttpServletResponse response,
    		@RequestParam(value = "wordCategoryId", required = true) Long wordCategoryId
			){
		
    	response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			 out = response.getWriter();
			 StringBuffer sb = new StringBuffer(); // 拼成Json格式字符串{key:value,key1:value1}
			 Set<VocWordExpressions> veSet = VocWordCategory.findVocWordCategory(wordCategoryId).getVocWordExpressionses();
			 
			sb.append(" [ ");
			if(null != veSet && veSet.size() > 0){
				boolean flag = true;
				for(VocWordExpressions wc:veSet){
					if(flag){
					  sb.append("{id:"+wc.getId()+",name:\'"+wc.getName()+"\'}");
					  flag = false;	
					}else{
					  sb.append(",{id:"+wc.getId()+",name:\'"+wc.getName()+"\'}");
					}
				}
			}
			sb.append(" ] ");
			
			 out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

    }
	
	/**
	 * 添加模板
	 */
	@ResponseBody
	@RequestMapping(value = "addTemplate")
    public void addTemplate(
    		@RequestParam(value = "commentLevel", required = true) Integer commentLevel,
    		@RequestParam(value = "brandId", required = true) Long brandId,
    		@RequestParam(value = "wordCategoryId", required = true) Long wordCategoryId,
    		@RequestParam(value = "title", required = true) String title,
    		@RequestParam(value = "content", required = true) String content,
    		@RequestParam(value = "id", required = false) Long id,
			HttpServletRequest request, HttpServletResponse response) {
    	response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		String msg = "{success:true}"; // 拼成Json格式字符串{key:value,key1:value1}
		try {
			 out = response.getWriter();
			VocTemplate tp = null;
			 if(null != id){
				 tp = VocTemplate.findVocTemplate(id);
			 }else{
				 tp = new VocTemplate();
			 }
			
			 tp.setCommentLevel(VocCommentLevel.getEnum(commentLevel));
			 tp.setBrand(VocBrand.findVocBrand(brandId));
			 tp.setVocWordCategory(VocWordCategory.findVocWordCategory(wordCategoryId));
			 tp.setTitle(title);
			 tp.setContent(content);
			 tp.setIsDeleted(true);
			 tp.setIsVisable(true);
			 tp.setCreateTime(new Date());
			 
			 tp.merge();
		} catch (IOException ex1) {
			ex1.printStackTrace();
			msg = "{success:false}"; 
		} finally {
			out.println(msg);
			out.close();
		}

    }
	
	/**
	 * 删除模板
	 */
	@ResponseBody
	@RequestMapping(value = "deleteTemplate")
    public void deleteTemplate(
    		@RequestParam(value = "id", required = false) Long id,
			HttpServletRequest request, HttpServletResponse response) {
    	response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		String msg = "{success:true}"; 
		try {
			 out = response.getWriter();
			VocTemplate  tp = VocTemplate.findVocTemplate(id);
			if(null !=tp){
			  tp.remove();
			}else{
			  msg = "{success:false}"; 
			}
			
		} catch (IOException ex1) {
			ex1.printStackTrace();
			msg = "{success:false}"; 
		} finally {
			out.println(msg);
			out.close();
		}

    }
	
	/**
	 * 删除模板规则
	 */
	@ResponseBody
	@RequestMapping(value = "deleteTemplateRule")
    public void deleteTemplateRule(
    		@RequestParam(value = "id", required = false) Long id,
			HttpServletRequest request, HttpServletResponse response) {
    	response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		String msg = "{success:true}"; 
		try {
			 out = response.getWriter();
			VocTemplateRule  tp = VocTemplateRule.findVocTemplate(id);
			if(null !=tp){
			  tp.remove();
			}else{
			  msg = "{success:false}"; 
			}
		} catch (IOException ex1) {
			ex1.printStackTrace();
			msg = "{success:false}"; 
		} finally {
			out.println(msg);
			out.close();
		}

    }
	
	/**
	 * 查询模板
	 */
	@ResponseBody
	@RequestMapping(value = "getQueryTemplate")
    public void getQueryTemplate(
    		Model uiModel,
			 HttpServletRequest request,
			 HttpServletResponse response,
			 @RequestParam(value = "start", required = false) Integer start,
			 @RequestParam(value = "limit", required = false) Integer limit,
			 @RequestParam(value = "brandIds", required = false) String brandIds,
			 @RequestParam(value = "commentLevels", required = false) String commentLevels) {
    	response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			StringBuffer sb = new StringBuffer();
			 out = response.getWriter();
			 Map parmMap = new HashMap();
			 parmMap.put("brandIds", brandIds);
			 parmMap.put("commentLevels", commentLevels);
			  VocTemplate model = new VocTemplate();
			  
			  QueryModel<VocTemplate> qm = new QueryModel<VocTemplate>(model, start, limit);
			  qm.getInputMap().putAll(parmMap);
			   
			  PageModel<VocTemplate> pm = VocTemplate.getQueryTemplate(qm);
			     pm.setPageSize(limit);
				 pm.setStartIndex(start);
			 uiModel.addAttribute("maxPages", pm.getTotalPage());
			 uiModel.addAttribute("page", pm.getPageNo());
			 sb.append("{");
			 sb.append("maxPages:"+pm.getTotalPage()+",");
			 sb.append("page:"+pm.getPageNo()+",");
			 sb.append("data:"+VocTemplate.toJsonArray(pm.getDataList())+",");
			 sb.append("}");
			 out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

    }
	
	/**
	 * 查询模板规则
	 */
	@ResponseBody
	@RequestMapping(value = "getQueryTemplateRule")
    public void getQueryTemplateRule(
    		 Model uiModel,
			 HttpServletRequest request,
			 HttpServletResponse response,
			 @RequestParam(value = "start", required = false) Integer start,
			 @RequestParam(value = "limit", required = false) Integer limit,
			 @RequestParam(value = "brandIds", required = false) String brandIds,
			 @RequestParam(value = "commentLevels", required = false) String commentLevels) {
    	response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			StringBuffer sb = new StringBuffer();
			 out = response.getWriter();
			 Map parmMap = new HashMap();
			 parmMap.put("brandIds", brandIds);
			 parmMap.put("commentLevels", commentLevels);
			 VocTemplateRule model = new VocTemplateRule();
			  QueryModel<VocTemplateRule> qm = new QueryModel<VocTemplateRule>(model, start, limit);
			  qm.getInputMap().putAll(parmMap);
			   
			  PageModel<VocTemplateRule> pm = VocTemplateRule.getQueryTemplateRule(qm);
			     pm.setPageSize(limit);
				 pm.setStartIndex(start);
			 uiModel.addAttribute("maxPages", pm.getTotalPage());
			 uiModel.addAttribute("page", pm.getPageNo());
			 sb.append("{");
			 sb.append("maxPages:"+pm.getTotalPage()+",");
			 sb.append("page:"+pm.getPageNo()+",");
			 sb.append("data:"+VocTemplateRule.toJsonArray(pm.getDataList())+",");
			 sb.append("}");
			 out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}
    }
	
	
	/**
	 * 查询将要生成规则的模板
	 */
	@ResponseBody
	@RequestMapping(value = "queryTemplateToRules")
    public void queryTemplateToRules(
			 HttpServletRequest request,
			 HttpServletResponse response,
			 @RequestParam(value = "brandId", required = true) Long brandId,
			 @RequestParam(value = "commentLevel", required = true) Integer commentLevel
    		) {
    	response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			StringBuffer sb = new StringBuffer();
			 out = response.getWriter();
			 Map param= new HashMap();
			 param.put("brandId",brandId);
			 param.put("commentLevel",commentLevel);
			 
			List<VocTemplate> list = VocTemplate.findVocTemplates(param);  
			
			sb.append(" [ ");
			if(null != list && list.size() > 0){
				boolean flag = true;
				for(VocTemplate wc:list){
					if(flag){
					  sb.append("{id:"+wc.getId()+",name:\'"+wc.getTitle()+"\'}");
					  flag = false;	
					}else{
					  sb.append(",{id:"+wc.getId()+",name:\'"+wc.getTitle()+"\'}");
					}
				}
			}
			
			sb.append(" ] ");
			out.println(sb);
			
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}
    }

	@ResponseBody
	@RequestMapping(value = "addTemplateRule")
    public void addTemplateRule(
    		@RequestParam(value = "templateId", required = true) Long templateId,
    		@RequestParam(value = "names", required = true) String names,
			HttpServletRequest request, HttpServletResponse response) {
    	response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		String msg = "{success:true}"; 
		try {
			 out = response.getWriter();
			
			VocTemplateRule  tp = new VocTemplateRule();
			tp.setTemplate(VocTemplate.findVocTemplate(templateId));
			tp.setName(names);
			tp.setCreateTime(new Date());
			tp.merge();
			
		} catch (IOException ex1) {
			ex1.printStackTrace();
			msg = "{success:false}"; 
		} finally {
			out.println(msg);
			out.close();
		}

    }
	
	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid VocTemplate vocTemplate, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, vocTemplate);
            return "voctemplates/update";
        }
        uiModel.asMap().clear();
        vocTemplate.merge();
        return "redirect:/voctemplates/" + encodeUrlPathSegment(vocTemplate.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, VocTemplate.findVocTemplate(id));
        return "voctemplates/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        VocTemplate vocTemplate = VocTemplate.findVocTemplate(id);
        vocTemplate.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/voctemplates";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("vocTemplate_createtime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, VocTemplate vocTemplate) {
        uiModel.addAttribute("vocTemplate", vocTemplate);
        addDateTimeFormatPatterns(uiModel);
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
}

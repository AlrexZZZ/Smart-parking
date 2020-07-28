package com.nineclient.web;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONArray;

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
import com.nineclient.model.UmpBusinessType;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpParentBusinessType;
import com.nineclient.model.VocCommentLevelRule;
import com.nineclient.model.VocWordCategory;
import com.nineclient.model.VocWordExpressions;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

/**
 * 好中差规则管理
 * @author 9client
 *
 */
@RequestMapping("/voccommentlevelrules")
@Controller
@RooWebScaffold(path = "voccommentlevelrules", formBackingObject = VocCommentLevelRule.class)
public class VocCommentLevelRuleController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid VocCommentLevelRule vocCommentLevelRule, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, vocCommentLevelRule);
            return "voccommentlevelrules/create";
        }
        uiModel.asMap().clear();
        vocCommentLevelRule.persist();
        return "redirect:/voccommentlevelrules/" + encodeUrlPathSegment(vocCommentLevelRule.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new VocCommentLevelRule());
        return "voccommentlevelrules/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("voccommentlevelrule", VocCommentLevelRule.findVocCommentLevelRule(id));
        uiModel.addAttribute("itemId", id);
        return "voccommentlevelrules/show";
    }

	@RequestMapping(produces = "text/html")
    public String list( HttpServletRequest request,@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
		//查询评论分类---词库分类
		List<VocWordCategory> vocWordCategorys = VocWordCategory.findCommentLevel();
		uiModel.addAttribute("commentLevels",JSONArray.fromObject(vocWordCategorys));
		uiModel.addAttribute("vocCommentLevels",VocCommentLevel.values());
		UmpCompany company = UmpCompany.findUmpCompany(CommonUtils.getCurrentPubOperator(request).getCompany().getId());
		List<UmpBusinessType> bussinessTypes = new ArrayList<UmpBusinessType>();
		
		for(UmpParentBusinessType pt:company.getParentBusinessType()){
		  bussinessTypes.addAll(pt.getSubBusinesses());
		}
		
		uiModel.addAttribute("bussinessTypes", bussinessTypes);
        addDateTimeFormatPatterns(uiModel);
        return "voccommentlevelrules/list";
    }

	
	@RequestMapping(value = "/queryCommentLevelRule", produces = "text/html")
	 public String queryCommentLevelRule(
			 Model uiModel,
			 HttpServletRequest request,
			 @RequestParam(value = "start", required = false) Integer start,
			 @RequestParam(value = "limit", required = false) Integer limit,
			 @RequestParam(value = "parentBussinessTypeId", required = false) Integer parentBussinessTypeId,
			 @RequestParam(value = "bussinessTypeId", required = false) Integer bussinessTypeId,
			 @RequestParam(value = "commentLevel", required = false) Integer commentLevel
			 ) {
		 
		  Map parmMap = new HashMap();
		  parmMap.put("parentBussinessTypeId", parentBussinessTypeId);
		  parmMap.put("bussinessTypeId", bussinessTypeId);
		  VocCommentLevelRule model = new VocCommentLevelRule();
		  model.setCompanyId(CommonUtils.getCurrentCompanyId(request));
		  if(null != commentLevel){
		    model.setCommentLevel(VocCommentLevel.getEnum(commentLevel));
		  }
		  
		  QueryModel<VocCommentLevelRule> qm = new QueryModel<VocCommentLevelRule>(model, start, limit);
		  qm.getInputMap().putAll(parmMap);
		   
		  PageModel<VocCommentLevelRule> pm = VocCommentLevelRule.getQueryCommentLevelRule(qm);
		     pm.setPageSize(limit);
			 pm.setStartIndex(start);
			 
		 uiModel.addAttribute("vocCommentLevelRules", pm.getDataList());
		 uiModel.addAttribute("maxPages", pm.getTotalPage());
		 uiModel.addAttribute("page", pm.getPageNo());
		 
       return "voccommentlevelrules/result";
	 }
	
	
	/**
	 * 根据行业查询商品子分类
	 */
	@ResponseBody
	@RequestMapping(value = "getBussinessTypeByCompany")
    public void getBussinessTypeByCompany(
			HttpServletRequest request, HttpServletResponse response) {
    	response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			 out = response.getWriter();
			 StringBuffer sb = new StringBuffer(); // 拼成Json格式字符串{key:value,key1:value1}
			 UmpCompany company = UmpCompany.findUmpCompany(CommonUtils.getCurrentPubOperator(request).getCompany().getId());
				List<UmpBusinessType> bussinessTypes = new ArrayList<UmpBusinessType>();
				
				for(UmpParentBusinessType pt:company.getParentBusinessType()){
				  bussinessTypes.addAll(pt.getSubBusinesses());
				}
			sb.append(" [ ");
			if(null != bussinessTypes && bussinessTypes.size() > 0){
				boolean flag = true;
				for(UmpBusinessType businessType:bussinessTypes){
					if(flag){
					  sb.append("{id:"+businessType.getId()+",name:\'"+businessType.getBusinessName()+"\'}");
					  flag = false;	
					}else{
					  sb.append(",{id:"+businessType.getId()+",name:\'"+businessType.getBusinessName()+"\'}");
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
	 * 添加关键词
	 */
	@ResponseBody
	@RequestMapping(value = "addCommentLevelRules")
    public void addCommentLevelRules(
    		@RequestParam(value = "bussinessTypeId", required = true) Long bussinessTypeId,
    		@RequestParam(value = "commentLevel", required = true) Integer commentLevel,
    		@RequestParam(value = "wordExpressions", required = true) String wordExpressions,
			HttpServletRequest request, HttpServletResponse response) {
    	response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			 out = response.getWriter();
			 StringBuffer sb = new StringBuffer(); // 拼成Json格式字符串{key:value,key1:value1}
			 VocCommentLevelRule model = null;
			 UmpBusinessType bt = UmpBusinessType.findUmpBusinessType(bussinessTypeId);
			 VocWordExpressions we = null;
			 Long companyId = CommonUtils.getCurrentCompanyId(request);
			 VocCommentLevelRule existsModel = null;
			 int commentLevel_ = 0;
			 for(String id:wordExpressions.split(",")){
				 we = VocWordExpressions.findVocWordExpressions(Long.parseLong(id));
				 model = new VocCommentLevelRule();
				 model.setBussinessType(bt);
				 //评论分类有相差，所有做一个同步操作
				 commentLevel_ = commentLevel - 2;
				 model.setCommentLevel(VocCommentLevel.getEnum(commentLevel_));
				 model.setCreateTime(new Date());
				 model.setIsDeleted(false);
				 model.setName(we.getName());
				 model.setCompanyId(companyId);
				 model.setBussinessType(UmpBusinessType.findUmpBusinessType(bussinessTypeId));
				 long count = VocCommentLevelRule.findCountVocCommentLevelRule(model);
				 if(count == 0){
					  try {
					   existsModel = VocCommentLevelRule.findUniqueVocCommentLevelRule(model);
					  } catch (Exception e) {
						//e.printStackTrace();
					  } 
					 if(existsModel == null){ //没有存在的，添加
						 model.merge();
					 }
				 }
			 }
			 
			 sb.append("{success:true}");
			 out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

    }
	
	
	/*@RequestMapping(value = "/getQueryWordExpressions", produces = "text/html")
	 public String getQueryWordExpressions(
			 Model uiModel,
			 HttpServletRequest request,
			 @RequestParam(value = "start", required = false) Integer start,
			 @RequestParam(value = "limit", required = false) Integer limit,
			 @RequestParam(value = "name", required = false) String name) {
		
		  Map parmMap = new HashMap();
		  VocWordExpressions model = new VocWordExpressions();
		  model.setName(name);
		  
		  QueryModel<VocWordExpressions> qm = new QueryModel<VocWordExpressions>(model, start, limit);
		  qm.getInputMap().putAll(parmMap);
		   
		  PageModel<VocWordExpressions> pm = VocWordExpressions.getQueryWordExpressions(qm);
		     pm.setPageSize(limit);
			 pm.setStartIndex(start);
		 uiModel.addAttribute("wordExpressionsList", pm.getDataList());
		 uiModel.addAttribute("maxPages", pm.getTotalPage());
		 uiModel.addAttribute("page", pm.getPageNo());
		 
      return "voccommentlevelrules/wordExpressionsList";
	 }*/
	
	/**
	 *查询关键词
	 */
	@ResponseBody
	@RequestMapping(value = "getQueryWordExpressions")
    public void getQueryWordExpressions(
    		Model uiModel,
			 HttpServletRequest request,
			 HttpServletResponse response,
			 @RequestParam(value = "commentSubLevelId", required = false) Long commentSubLevelId,
			 @RequestParam(value = "start", required = false) Integer start,
			 @RequestParam(value = "limit", required = false) Integer limit,
			 @RequestParam(value = "name", required = false) String name) {
    	response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			StringBuffer sb = new StringBuffer();
			 out = response.getWriter();
			 Map parmMap = new HashMap();
			  VocWordExpressions model = new VocWordExpressions();
			  model.setName(name);
			  
			  QueryModel<VocWordExpressions> qm = new QueryModel<VocWordExpressions>(model, start, limit);
			   parmMap.put("companyId", CommonUtils.getCurrentCompanyId(request));
			   parmMap.put("commentSubLevelId", commentSubLevelId);
			  qm.getInputMap().putAll(parmMap);
			   
			  PageModel<VocWordExpressions> pm = VocWordExpressions.getQueryWordExpressions(qm);
			     pm.setPageSize(limit);
				 pm.setStartIndex(start);
			 uiModel.addAttribute("maxPages", pm.getTotalPage());
			 uiModel.addAttribute("page", pm.getPageNo());
			 sb.append("{");
			 sb.append("maxPages:"+pm.getTotalPage()+",");
			 sb.append("page:"+pm.getPageNo()+",");
			 sb.append("pageSize:"+pm.getPageSize()+",");
			 sb.append("data:"+VocWordExpressions.toJsonArray(pm.getDataList())+",");
			 sb.append("}");
			 out.println(sb);
		} catch (Exception ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

    }
	
	
	@ResponseBody
	@RequestMapping(value="deleteCommentLevelRule",produces = "text/html")
    public void deleteCommentLevelRule(
			 Model uiModel,
			 @RequestParam(value = "id", required = false) Long id,
			 HttpServletRequest request,
			 HttpServletResponse response
			 ) {
		VocCommentLevelRule.findVocCommentLevelRule(id).remove();
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		String msg = "{success:true}";
		try {
			 out = response.getWriter();
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.println(msg);
			out.close();
		}
    }
	
	
	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid VocCommentLevelRule vocCommentLevelRule, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, vocCommentLevelRule);
            return "voccommentlevelrules/update";
        }
        uiModel.asMap().clear();
        vocCommentLevelRule.merge();
        return "redirect:/voccommentlevelrules/" + encodeUrlPathSegment(vocCommentLevelRule.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, VocCommentLevelRule.findVocCommentLevelRule(id));
        return "voccommentlevelrules/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        VocCommentLevelRule vocCommentLevelRule = VocCommentLevelRule.findVocCommentLevelRule(id);
        vocCommentLevelRule.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/voccommentlevelrules";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("vocCommentLevelRule_createtime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, VocCommentLevelRule vocCommentLevelRule) {
        uiModel.addAttribute("vocCommentLevelRule", vocCommentLevelRule);
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

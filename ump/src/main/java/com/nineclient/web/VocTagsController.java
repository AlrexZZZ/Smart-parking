package com.nineclient.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nineclient.model.CommentDBValidata;
import com.nineclient.model.UmpBusinessType;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpOperator;
import com.nineclient.model.UmpParentBusinessType;
import com.nineclient.model.VocTags;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@RequestMapping("/voctagses")
@Controller
@RooWebScaffold(path = "voctagses", formBackingObject = VocTags.class)
public class VocTagsController {

	@RequestMapping(produces = "text/html")
	public String list(
			Model uiModel,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder
		 ) {
		UmpOperator op = (UmpOperator)request.getSession().getAttribute(Global.UMP_LOGIN_OPERATOR);
		 UmpCompany company = UmpCompany.findUmpCompany(CommonUtils.getCurrentPubOperator(request).getCompany().getId());
			List<UmpBusinessType> list = new ArrayList<UmpBusinessType>();
			for(UmpParentBusinessType pt:company.getParentBusinessType()){
				list.addAll(pt.getSubBusinesses());
			}
		
		uiModel.addAttribute("bussinessTypes",list);
		return "voctagses/list";
	}

	/**
	 * 查询模板
	 */
	@RequestMapping(value = "getQueryTags")
	public String getQueryTags(Model uiModel, 
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "bussinessTypeId", required = false) Long bussinessTypeId
			) {
		Map parmMap = new HashMap();
		VocTags model = new VocTags();
		model.setName(name);
		model.setCompanyId(CommonUtils.getCurrentCompanyId(request));
		if(null != bussinessTypeId){
		 model.setBussinessType(UmpBusinessType.findUmpBusinessType(bussinessTypeId));
		}
		
		model.setCompanyId(CommonUtils.getCurrentCompanyId(request));
		QueryModel<VocTags> qm = new QueryModel<VocTags>(model, start, limit);
		qm.getInputMap().putAll(parmMap);
		PageModel<VocTags> pm = VocTags.getQueryVocTags(qm);
		pm.setPageSize(limit);
		pm.setStartIndex(start);
		uiModel.addAttribute("maxPages", pm.getTotalPage());
		uiModel.addAttribute("page", pm.getPageNo());
		uiModel.addAttribute("limit", limit);
		uiModel.addAttribute("tags", pm.getDataList());
		return "voctagses/result";
	}
	
	/**
	 * 添加模板
	 */
	@ResponseBody
	@RequestMapping(value = "editTags")
    public void editTags(
    		@RequestParam(value = "id", required = false) Long id,
    		@RequestParam(value = "bussinessTypeId", required = true) Long bussinessTypeId,
    		@RequestParam(value = "name", required = true) String name,
			HttpServletRequest request, HttpServletResponse response) {
    	response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		String msg = "{success:true}"; 
		try {
			 out = response.getWriter();
			VocTags tp = null;
			 if(null != id){
				 tp = VocTags.findVocTags(id);
			 }else{
				 tp = new VocTags();
			 }
		  tp.setBussinessType(UmpBusinessType.findUmpBusinessType(bussinessTypeId));
		  tp.setName(name);
		  tp.setCompanyId(CommonUtils.getCurrentCompanyId(request));
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
	 * 删除标签
	 */
	@ResponseBody
	@RequestMapping(value = "deleteTags")
    public void deleteTags(
    		@RequestParam(value = "id", required = true) Long id,
			HttpServletRequest request, HttpServletResponse response) {
    	response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		String msg = "{success:true}"; 
		try {
			 out = response.getWriter();
			VocTags tp =  VocTags.findVocTags(id);
			 if(null != tp){
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
	 * 唯一性验证
	 * 
	 * @param companyId
	 *            公司id 非必选
	 * @param objectName
	 *            对象名称 后台用 hql 查询 必选
	 * @param fieldName
	 *            字段名称 必选
	 * @param fieldValue
	 *            字段值 必选
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "checkUnique")
	public void checkUnique(
			@RequestParam(value = "paramtersJson", required = true) String paramtersJson,
			HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer();// 拼成Json格式字符串{key:value,key1:value1}
			Map paramMap = parseParamMap(paramtersJson);
			
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(*) from voc_tags o where o.company_id = "+CommonUtils.getCurrentCompanyId(request));

			if(null != paramMap.get("id")){
			   Long id = Long.parseLong(String.valueOf(paramMap.get("id")));
			   sql.append(" and o.id !="+id);
			}
			
			if(null != paramMap.get("name")){
			   String name = String.valueOf(paramMap.get("name"));
			   sql.append(" and o.name = '"+name+"'");
			}
			
			if(null != paramMap.get("bussinessTypeId")){
			   String bussinessTypeId = String.valueOf(paramMap.get("bussinessTypeId"));
			   sql.append(" and o.bussiness_type = "+Long.parseLong(String.valueOf(paramMap.get("bussinessTypeId"))));
			 }
		    
			Long count =  CommentDBValidata.queryUniqueNum(sql.toString());
			if (null != count) {
				if (count > 0) {
					sb.append("{success:true}");
				} else {
					sb.append("{success:false}");
				}
			} else {
				sb.append("{success:false}");
			}
			out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

	}

 private Map<String,String> parseParamMap(String paramtersJson){
  Map<String,String> map = new HashMap<String,String>();
  String regex = "\"([^\"]+?)\":\"([^\"]+?)\"";	 
  Pattern p = Pattern.compile(regex);
  Matcher m = p.matcher(paramtersJson);
  String key = null;
  String value = null;
  
  while(m.find()){
	key = m.group(1);
	value = m.group(2);
	map.put(key, value);
  }
  return map;
 }
 
	
}

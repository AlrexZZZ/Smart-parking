package com.nineclient.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nineclient.model.CommentDBValidata;
import com.nineclient.model.PubOperator;
import com.nineclient.model.VocKeyWord;
import com.nineclient.utils.CommonUtils;

@RequestMapping("/commentvalidata")
@Controller
public class CommentValidataController {
	/**
	 * companyCode
	 * @param field
	 * @param value
	 * @param id
	 * @param table
	 * @param companyField
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "validataUnique", produces = "text/html;charset=utf-8")
	public String validataUnique(@RequestParam(value = "field") String field,
			@RequestParam(value = "value") String value,
			@RequestParam(value="id",required=false)Long id,
			@RequestParam(value = "table") String table,
			@RequestParam(value = "companyField",required=false) String companyField,HttpServletRequest request) {
		
		if (StringUtils.isEmpty(field) || StringUtils.isEmpty(value)||StringUtils.isEmpty(table) ) {
			return "true";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(field, value);
		if(id!=null){
			map.put("id", id);
		}
		if(!StringUtils.isEmpty(companyField)&&companyField.trim()!=""){
			PubOperator user = CommonUtils.getCurrentPubOperator(request);
			map.put(companyField, user.getCompany().getCompanyCode());
		}
		Long count = CommentDBValidata.queryUniqueCount(table, map);
		if(count>0){
			return "false";
		}
		return "true";
	}
	/**
	 * companyId
	 * @param field
	 * @param value
	 * @param id
	 * @param table
	 * @param companyField
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "validataUniqueId", produces = "text/html;charset=utf-8")
	public String validataUniqueId(@RequestParam(value = "field") String field,
			@RequestParam(value = "value") String value,
			@RequestParam(value="id",required=false)Long id,
			@RequestParam(value = "table") String table,
			@RequestParam(value = "companyField",required=false) String companyField,HttpServletRequest request) {
		
		if (StringUtils.isEmpty(field) || StringUtils.isEmpty(value)||StringUtils.isEmpty(table) ) {
			return "true";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(field, value);
		if(id!=null){
			map.put("id", id);
		}
		if(!StringUtils.isEmpty(companyField)&&companyField.trim()!=""){
			PubOperator user = CommonUtils.getCurrentPubOperator(request);
			map.put(companyField, user.getCompany().getId());
		}
		Long count = CommentDBValidata.queryUniqueCount(table, map);
		if(count>0){
			return "false";
		}
		return "true";
	}
	/**
	 * company
	 * @param field
	 * @param value
	 * @param id
	 * @param table
	 * @param companyField
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "validataUniqueCompany", produces = "text/html;charset=utf-8")
	public String validataUniqueCompany(@RequestParam(value = "field") String field,
			@RequestParam(value = "value") String value,
			@RequestParam(value="id",required=false)Long id,
			@RequestParam(value = "table") String table,
			@RequestParam(value = "companyField",required=false) String companyField,HttpServletRequest request) {
		
		if (StringUtils.isEmpty(field) || StringUtils.isEmpty(value)||StringUtils.isEmpty(table) ) {
			return "true";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(field, value);
		if(id!=null){
			map.put("id", id);
		}
		if(!StringUtils.isEmpty(companyField)&&companyField.trim()!=""){
			PubOperator user = CommonUtils.getCurrentPubOperator(request);
			map.put(companyField, user.getCompany());
		}
		Long count = CommentDBValidata.queryUniqueCount(table, map);
		if(count>0){
			return "false";
		}
		return "true";
	}
	/**
	 * 与公司建立关联关系的唯一校验
	 * @param field
	 * @param value
	 * @param id
	 * @param table
	 * @param companyField
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "validataUniqueJoin", produces = "text/html;charset=utf-8")
	public String validataUniqueJoin(@RequestParam(value = "field") String field,
			@RequestParam(value = "value") String value,
			@RequestParam(value="id",required=false)Long id,
			@RequestParam(value = "table") String table,
			@RequestParam(value = "companyField",required=false) String companyField,HttpServletRequest request) {
		
		if (StringUtils.isEmpty(field) || StringUtils.isEmpty(value)||StringUtils.isEmpty(table) ) {
			return "true";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(field, value);
		if(id!=null){
			map.put("id", id);
		}
	    PubOperator user = CommonUtils.getCurrentPubOperator(request);
		Long count = CommentDBValidata.queryUniqueCount(table, map,companyField,user.getCompany().getId());
		if(count>0){
			return "false";
		}
		return "true";
	}
	
	/**
	 * 版本名唯一验证
	 * @param id
	 * @param productId
	 * @param versionName
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "uniqueVersionName", produces = "text/html;charset=utf-8")
	public String uniqueVersionName(
			@RequestParam(value="id",required=false)Long id,
			@RequestParam(value="productId",required=false)Long productId,
			@RequestParam(value = "versionName") String versionName,
			HttpServletRequest request){
		if (StringUtils.isEmpty(versionName) || StringUtils.isEmpty(versionName)||StringUtils.isEmpty(versionName) ) {
			return "true";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Long count = CommentDBValidata.uniqueVersionName(versionName, id, productId);
		if(count>0){
			return "false";
		}
		return "true";
	}
	@ResponseBody
	@RequestMapping(value = "validataUniqueKeyWord", produces = "text/html;charset=utf-8")
	public String validataUniqueKeyWord(
			@RequestParam(value="id",required=false)Long id,
			@RequestParam(value="brandName",required=false)String brandName,
			@RequestParam(value = "keyWords") String[] keyWords,
			@RequestParam(value = "businessTypes") String[] businessTypes,
			HttpServletRequest request){
		//判断数据里面是否存在重复的关键词
		for(int i=0;i<keyWords.length;i++){
			for(int j=0;j<keyWords.length;j++){
				if(keyWords[i].equals(keyWords[j])){
					return "false";
				}
			}
		}
		return "true";
		
	}
	/**
	 * 判断 子行业是否被使用
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "businessIsUsing", produces = "text/html;charset=utf-8")
	public String businessIsUsing(@RequestParam(value="businessId")Long businessId){
		//关键词
		List<VocKeyWord> list=null;
		try{
			list=VocKeyWord.findVocKeyWordByBusinessId(businessId);
		}catch(Exception e){
			return "false";
		}
		if(list!=null&&list.size()>0){
			return "true";
		}else{
			return "false";
		}
	}
	
}

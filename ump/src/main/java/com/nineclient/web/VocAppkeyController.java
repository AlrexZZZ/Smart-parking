package com.nineclient.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.nineclient.constant.NotifyMsg;
import com.nineclient.model.CommentDBValidata;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpOperator;
import com.nineclient.model.VocAppkey;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageModel;

@RequestMapping("/vocappkeys")
@Controller
@RooWebScaffold(path = "vocappkeys", formBackingObject = VocAppkey.class)
public class VocAppkeyController {
	@RequestMapping(value = "appKeyManage",method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String appKeyManage(Model model,HttpServletRequest request,@RequestParam(value="displayId",required=false)Long displayId) {
		if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(request);
	    }
		request.getSession().setAttribute("displayId", displayId);
		//查询渠道
		return "vocappkeys/appKeyManage";
	}
	/**
	 * 添加appkey
	 * @param vocAppkey
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="create",method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String create(VocAppkey vocAppkey,
			Model uiModel, HttpServletRequest httpServletRequest) {
		String msg="";
		try {
			if(StringUtils.isEmpty(vocAppkey.getName())){
				msg="应用名称不能为空";
				return msg;
			}
			UmpCompany company = getUser(httpServletRequest);
			vocAppkey.setCompanyId(company.getId());
			VocAppkey appkey=new VocAppkey();
			appkey.setCompanyId(company.getId());
			appkey.setName(vocAppkey.getName());
			List<VocAppkey> list = VocAppkey.findAllVocAppkeys(appkey);
			if(list!=null&&list.size()>0){
				msg="应用名称不能重复";
				return msg;
			}
			uiModel.asMap().clear();
			vocAppkey.setCreateTime(new Date());
			vocAppkey.setIsDeleted(false);
			vocAppkey.persist();
			msg="成功";
		} catch (Exception e) {
			e.printStackTrace();
			msg="失败";
		}
		return msg;
	}

	@ResponseBody
	@RequestMapping(value = "list", produces = "text/html;charset=utf-8")
	public String list(
			HttpServletRequest request,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {
			UmpCompany company = getUser(request);
		    VocAppkey appKey=new VocAppkey();
		    appKey.setCompanyId(company.getId());
			page = page == null ? 0 : page;
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page==0?page:(page.intValue()-1)*sizeNo;
			List<VocAppkey> vocappkeys = VocAppkey.findVocAppkeyEntries(appKey,
					firstResult, sizeNo, sortFieldName, sortOrder);
			long count = 	VocAppkey.countVocAppkeys(appKey);
			PageModel<VocAppkey> pageMode =new PageModel<VocAppkey>();
			pageMode.setDataJson(VocAppkey.toJsonArray(vocappkeys));
			pageMode.setPageSize(size);
			pageMode.setPageNo(page);
			pageMode.setTotalCount(Integer.parseInt(count+""));
		return pageMode.toJson();
	}
	@ResponseBody
	@RequestMapping(value="update",method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String update(VocAppkey appkey,
			Model uiModel,
			HttpServletRequest request) {
		Long  id= appkey.getId();
		VocAppkey vocAppkey = VocAppkey.findVocAppkey(id);
		UmpCompany company = getUser(request);
		Map<String,Object> field = new HashMap<String, Object>();
		field.put("id", id);
		field.put("name", appkey.getName());
		field.put("companyId", company.getId());
		Long count = CommentDBValidata.queryUniqueCount("VocAppkey", field);
		if(count>0){
			return "应用名称不能重复";
		}
		try{
			vocAppkey.setName(appkey.getName());
			vocAppkey.setClientSecret(appkey.getClientSecret());
			vocAppkey.setClientId(appkey.getClientId());
			vocAppkey.setRedirectUrl(appkey.getRedirectUrl());
			vocAppkey.merge();
		}catch(Exception e){
			e.printStackTrace();
			return NotifyMsg.FIALED;
		}
		return NotifyMsg.SUCCESS;
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, VocAppkey.findVocAppkey(id));
		return "vocappkeys/update";
	}
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String delete(@RequestParam(value="id") Long id,
			Model uiModel) {
		String msg ="";
		try{
			VocAppkey vocAppkey = VocAppkey.findVocAppkey(id);
			vocAppkey.remove();
			msg="成功";
		}catch(Exception e){
			e.printStackTrace();
			msg="失败";
		}
		return msg;
	}
	
	@ResponseBody
	@RequestMapping(value = "updateVisable", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String updateVisable(@RequestParam(value="id") Long id,@RequestParam(value="isVisable") Boolean isVisable,
			Model uiModel) {
		String msg ="";
		try{
			VocAppkey vocAppkey = VocAppkey.findVocAppkey(id);
			vocAppkey.setIsVisable(isVisable);
			vocAppkey.merge();
			msg="成功";
		}catch(Exception e){
			e.printStackTrace();
			msg="失败";
		}
		return msg;
	}
	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"vocAppkey_createtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}
	@ResponseBody
	@RequestMapping(value="selectData",produces="text/html;charset=utf-8")
	public String selectData(HttpServletRequest request){
		UmpCompany company = getUser(request);
		List<VocAppkey> list = VocAppkey.findAllVocAppkeysByCompany(company);
		String json ="";
		json=VocAppkey.toJsonArrayNameAndId(list);
		return json;
	}
	
	void populateEditForm(Model uiModel, VocAppkey vocAppkey) {
		uiModel.addAttribute("vocAppkey", vocAppkey);
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

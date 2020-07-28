package com.nineclient.web;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpLog;
import com.nineclient.model.UmpOperator;
import com.nineclient.model.UmpRole;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.MD5Util;
import com.nineclient.utils.PageModel;

@RequestMapping("/umpoperators")
@Controller
@RooWebScaffold(path = "umpoperators", formBackingObject = UmpOperator.class)
public class UmpOperatorController {

	@RequestMapping(value="/addPubOper",method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid UmpOperator umpOperator, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) {
        uiModel.asMap().clear();
        umpOperator.setPassword(MD5Util.MD5(umpOperator.getPassword()));
        umpOperator.setIsDeleted(true);
        umpOperator.setCompany(CommonUtils.getCurrentOperator(httpServletRequest).getCompany());
        umpOperator.setCreateTime(new Date());
        umpOperator.persist();
        return "redirect:/umpoperators?page=1&amp;size=10";
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new UmpOperator());
        uiModel.addAttribute("umproles", UmpRole.findAllUmpRoles());
        uiModel.addAttribute("umpcompanys", UmpCompany.findAllUmpCompanysById());
        return "umpoperators/create";
    }

	/**
	 * 改变用户的状态（是否禁用）
	 * @param id
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	public void updateStatus(@RequestParam(value = "id", required = false) Long id,HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "active", required = false) Boolean active
			) {
		PrintWriter out = null;
		try {
			UmpOperator umpOperator = UmpOperator.findUmpOperator(id);
			umpOperator.setActive(active);
			umpOperator.merge();
			response.setContentType("text/Xml;charset=utf-8");
			out = response.getWriter();
			String json = "{\"success\":\"true\"}";// 拼成Json格式字符串{key:value,key1:value1}
			out.println(json);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}
	}

	
	@RequestMapping(value="login",method=RequestMethod.POST, produces = "text/html")
	public String loginForm(Model uiModel,UmpOperator umpOperator,HttpServletRequest request){
		UmpOperator u = new UmpOperator();
		u.setEmail("test");
		u.setId(2l);
		request.getSession().setAttribute("user", u);
		return "redirect:/ump";
	}
	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("umpoperator", UmpOperator.findUmpOperator(id));
        uiModel.addAttribute("itemId", id);
        return "umpoperators/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size,
    		@RequestParam(value = "displayId", required = false) Long displayId,
    		@RequestParam(value = "sortFieldName", required = false) String sortFieldName, 
    		@RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel
    		,HttpServletRequest request) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("umpoperators", UmpOperator.findUmpOperatorEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) UmpOperator.countUmpOperators() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
            uiModel.addAttribute("page", page);
			uiModel.addAttribute("size", size);
        } else {
            uiModel.addAttribute("umpoperators", UmpOperator.findAllUmpOperators(sortFieldName, sortOrder));
        }
        uiModel.addAttribute("umproles", UmpRole.findAllUmpRoles());
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
	    uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
	    if(displayId == null){
        	displayId = CommonUtils.getCurrentDisPlayId(request);
        }
	    request.getSession().setAttribute("displayId", displayId);
        return "umpoperators/list";
    }

	@RequestMapping(value="findListByFiled",method=RequestMethod.POST,produces="text/html;charset=utf-8")
	@ResponseBody
	public String findListByFiled(Model uiModel, UmpOperator umpOperator
			,@RequestParam(value = "page", required = false) Integer page,
			 @RequestParam(value = "size", required = false) Integer size,
			 @RequestParam(value = "account", required = false) String account,
			 @RequestParam(value = "email", required = false) String email,
			 @RequestParam(value = "active", required = false) Boolean active,
			 @RequestParam(value = "urole", required = false) Long uroleId
			){
		
		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
		
		List<UmpOperator> umpOperList = UmpOperator.findAllUmpOperatorByFiled(firstResult, sizeNo, account,email,active,uroleId);
		System.out.println(umpOperList.size());
		Long count = UmpOperator.countUmpOperatorByFiled(account,email,active,uroleId);
		System.out.println(count);
		PageModel<UmpOperator> pageMode = new PageModel<UmpOperator>();
		pageMode.setPageNo(page);
		pageMode.setTotalCount(Integer.parseInt(count+""));
		pageMode.setPageSize(sizeNo);
		pageMode.setDataJson(UmpOperator.toJsonArray(umpOperList));
		return pageMode.toJson();
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST, produces = "text/html;charset=utf-8")
    public String update(@Valid UmpOperator umpOperator, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		UmpOperator umpOperators = UmpOperator.findUmpOperator(umpOperator.getId());
		umpOperators.setActive(umpOperator.getActive());
		umpOperators.setAccount(umpOperator.getAccount());
		umpOperators.setEmail(umpOperator.getEmail());
		umpOperators.setMobile(umpOperator.getMobile());
		umpOperators.setUrole(umpOperator.getUrole());
		umpOperators.setPassword(umpOperator.getPassword());
        uiModel.asMap().clear();
        umpOperators.merge();
        return "redirect:/umpoperators?page=1&amp;size=10";
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, UmpOperator.findUmpOperator(id));
        uiModel.addAttribute("umproles", UmpRole.findAllUmpRoles());
        uiModel.addAttribute("umpcompanys", UmpCompany.findAllUmpCompanysById());
        return "umpoperators/update";
    }

	@RequestMapping(value = "delete", produces = "text/html")
    public String delete(@RequestParam("id") Long id, 
    		@RequestParam(value = "page", required = false) Integer page,
    		@RequestParam(value = "size", required = false) Integer size, 
    		Model uiModel) {
        UmpOperator umpOperator = UmpOperator.findUmpOperator(id);
        umpOperator.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/umpoperators?page=1&amp;size=10";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("umpOperator_createtime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, UmpOperator umpOperator) {
        uiModel.addAttribute("umpOperator", umpOperator);
        addDateTimeFormatPatterns(uiModel);
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = "utf-8";//WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
	
	@RequestMapping(value = "/checkAccount", method = RequestMethod.POST,produces="text/html;charset=utf-8")
	@ResponseBody
	public String checkAccount(@RequestParam(value = "account") String account,HttpServletRequest request, HttpServletResponse response){
			List<UmpOperator> umpOperator = UmpOperator.findUmpOperatorByAccount(account);
			if(umpOperator !=null && umpOperator.size() > 0){
				String str = "{msg:\'false\'}";// 拼成Json格式字符串{key:value,key1:value1}
				return str.toString();
			}
			String str = "{msg:\'true\'}";// 拼成Json格式字符串{key:value,key1:value1}
			return str;
	}
	
	@RequestMapping(value = "updatePassword", produces = "text/html")
	@ResponseBody
	public String updatePassword(@RequestParam(value = "id") long id,
			@RequestParam(value = "operatorName") String operatorName,
			@RequestParam(value = "password") String password) {
		UmpOperator operator = UmpOperator.findUmpOperator(id);
		String md5Password = MD5Util.MD5(password);
		operator.setOperatorName(operatorName);
		operator.setPassword(md5Password);
		operator.merge();
		return "0";
	}
}

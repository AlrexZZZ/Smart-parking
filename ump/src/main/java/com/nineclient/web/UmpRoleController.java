package com.nineclient.web;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
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

import com.nineclient.model.PubOperator;
import com.nineclient.model.PubRole;
import com.nineclient.model.UmpAuthority;
import com.nineclient.model.UmpOperator;
import com.nineclient.model.UmpRole;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@RequestMapping("/umproles")
@Controller
@RooWebScaffold(path = "umproles", formBackingObject = UmpRole.class)
public class UmpRoleController {
	
    @RequestMapping(value="show",produces = "text/html")
    public String show(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
       /* if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("umproles", UmpRole.findUmpRoleEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) UmpRole.countUmpRoles() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("umproles", UmpRole.findAllUmpRoles(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);*/
        return "umproles/lists";
    }
    
    @RequestMapping(value = "create",params = "form", produces = "text/html")
    public String create(Model uiModel
    		,@RequestParam(value = "displayId", required = false) Long displayId,
    		HttpServletRequest request) {
        populateEditForm(uiModel, new UmpRole());
        if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(request);
	    }
		request.getSession().setAttribute("displayId", displayId);
        return "umproles/creates";
    }
    
    @RequestMapping(value = "creates", produces = "text/html")
    public String creates(Model uiModel,HttpServletRequest request) throws UnsupportedEncodingException {
        populateEditForm(uiModel, new UmpRole());
        String nname = request.getParameter("name");
        String name = new String(nname.getBytes("ISO-8859-1"),"UTF-8");
        String status = request.getParameter("status");
        String rid = request.getParameter("id");
        boolean statu = false;
        if(null!=status&&!"".equals(status)){
        	statu = (Boolean.parseBoolean(status));
        }
        String ids = request.getParameter("ids");
        List<UmpAuthority> authoritys = new ArrayList<UmpAuthority>();
        if(null!=ids&&!"".equals(ids)){
        	String idss[] = ids.split(",");
        	if(idss.length>0){
        		for(String id : idss){
        			UmpAuthority authority = UmpAuthority.findUmpAuthority(Long.parseLong(id));
        			authoritys.add(authority);
        		}
        	}
        }
        UmpRole role = new UmpRole();
        if(null!=rid&&!"".equals(rid)){
        	role = UmpRole.findUmpRole(Long.parseLong(rid));
        	role.setRoleName(name);
            role.setUmpAuthoritys(authoritys);
            role.setIsVisable(statu);
            role.merge();
            return "redirect:/umproles?page=1&amp;size=10";
        }
        role.setRoleName(name);
        role.setUmpAuthoritys(authoritys);
        role.setIsDeleted(true);
       // role.setIsVisable(statu);
        role.setIsVisable(true);
        role.setCreateTime(new Date());
        role.setStartTime(new Date());
        role.persist();
        return "redirect:/umproles?page=1&amp;size=10";
    }
    
    @RequestMapping(value = "tree",produces="text/html;charset=utf-8")
    @ResponseBody
    public String tree(Model uiModel,HttpServletRequest request, HttpServletResponse response) {
    	StringBuffer strs = new StringBuffer();
    	int num = 0;
    	List<UmpAuthority> authoritys = UmpAuthority.findAllUmpAuthoritys();
    	for(UmpAuthority auth : authoritys){
    		String str = "id:"+auth.getId()+",pId:"+auth.getParentId()+",name:\""+auth.getDisplayName()+"\""+",open:true,checked:true";
    		if(num==0){
    			strs.append("{"+str+"}");
    		}else{
    			strs.append(",{"+str+"}");
    		}
    		num++;
    	}
    	return strs.toString();
    }
    

	@RequestMapping(value = "/update", produces = "text/html")
    public String updateForm(@RequestParam(value = "id", required = false) Long id, Model uiModel) {
		UmpRole umpRole = UmpRole.findUmpRole(id);
		StringBuffer ids = new StringBuffer();
		int n = 1;
		for(UmpAuthority au : umpRole.getUmpAuthoritys()){
			ids.append(au.getId());
			if(n!=umpRole.getUmpAuthoritys().size()){
				ids.append(",");
			}
			n++;
		}
		uiModel.addAttribute("name",umpRole.getRoleName());
		uiModel.addAttribute("status", umpRole.getIsVisable());
		uiModel.addAttribute("ids", ids);
		uiModel.addAttribute("id", umpRole.getId());
        return "umproles/creates";
    }

	@RequestMapping(value = "/delete", produces = "text/html")
    public String delete(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel,
    		HttpServletRequest request) {
        UmpRole umpRole = UmpRole.findUmpRole(id);
        Set<UmpOperator> opers = umpRole.getOperators();
        int num = opers.size();
        String errmsg = "";
        if(num > 0){
        	 errmsg = "该权限有账号关联，无法删除！";
        }else{
        	umpRole.setUmpAuthoritys(null);
        	umpRole.remove();
        	uiModel.asMap().clear();
        }
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        request.getSession().setAttribute("errmsg", errmsg);
        return "redirect:/umproles";
    }
	
	
	 	@RequestMapping(value = "check",produces="text/html;charset=utf-8")
	    @ResponseBody
	    public String check(@RequestParam(value = "name") String name) {
	 		boolean flag = true;
	    	List<UmpRole> roles = UmpRole.findAllUmpRoles();
	    	if(null!=name&&!"".equals(name)){
	    		for(UmpRole role : roles){
		    		if(role.getRoleName().trim().equals(name.trim())){
		    			flag = false;
		    		}
		    	}
	    	}
	    	return flag+"";
	    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new UmpRole());
        return "umproles/creates";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, 
    		@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
    		@RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel,
    		@RequestParam(value = "displayId", required = false) Long displayId,HttpServletRequest request) {
		 sortFieldName = "createTime";
         sortOrder = "DESC";
		if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("umproles", UmpRole.findUmpRoleEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) UmpRole.countUmpRoles() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("umproles", UmpRole.findAllUmpRoles(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(request);
	    }
        String errmsg = request.getSession().getAttribute("errmsg")+"";
        if(null!=errmsg&&!"".equals(errmsg)){
        	request.getSession().removeAttribute("errmsg");
        }
		request.getSession().setAttribute("displayId", displayId);
		uiModel.addAttribute("limit", 10);
		uiModel.addAttribute("errmsg", errmsg);
        return "umproles/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid UmpRole umpRole, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, umpRole);
            return "umproles/update";
        }
        uiModel.asMap().clear();
        umpRole.merge();
        return "redirect:/umproles";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("umpRole_starttime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("umpRole_createtime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, UmpRole umpRole) {
        uiModel.addAttribute("umpRole", umpRole);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("umpauthoritys", UmpAuthority.findAllUmpAuthoritys());
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
	
	@RequestMapping(value = "/queryRoles", produces = "text/html")
	 public String queryRoles(
			 Model uiModel,
			 HttpServletRequest request,
			 @RequestParam(value = "start", required = false) Integer start,
			 @RequestParam(value = "limit", required = false) Integer limit
			 ) throws ServletException, IOException {
		  PubOperator operator = CommonUtils.getCurrentPubOperator(request);
		  Map parmMap = new HashMap();
		  UmpRole role = new UmpRole();
		  QueryModel<UmpRole> qm = new QueryModel<UmpRole>(role, start, limit);
		  qm.getInputMap().putAll(parmMap);
		   
		  PageModel<UmpRole> pm = UmpRole.getQueryUmpRole(qm);
		     pm.setPageSize(limit);
			 pm.setStartIndex(start);
			 
		 uiModel.addAttribute("umpRoles", pm.getDataList());
		 uiModel.addAttribute("maxPages", pm.getTotalPage());
		 uiModel.addAttribute("page", pm.getPageNo());
		 uiModel.addAttribute("limit", pm.getPageSize());
		 
     return "umproles/result";
	 }	
	
	@RequestMapping(value = "checks",produces="text/html;charset=utf-8")
    @ResponseBody
    public String check(Model uiModel,
    		@RequestParam(value = "name", required = false) String name,
    		HttpServletRequest request, HttpServletResponse response) {
    	List<UmpRole> roles = UmpRole.findUmpRoleByName(name);
    	String flag = "true";
    	if(roles.size()>0){
    		flag = "false";
    	}
    	return flag;
    }
	
	/**
	 * 状态修改
	 * @param uiModel
	 * @param name
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "rcheck",produces="text/html;charset=utf-8")
    @ResponseBody
    public Long rcheck(Model uiModel,
    		@RequestParam(value = "id", required = false) Long id,
    		@RequestParam(value="status") Boolean status,
    		HttpServletRequest request, HttpServletResponse response) {
		UmpRole umpRole = UmpRole.findUmpRole(id);
		umpRole.setIsVisable(status);
    	return umpRole.merge().getId();
    }
}

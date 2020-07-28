package com.nineclient.web;
import com.nineclient.model.PubOperator;
import com.nineclient.model.PubRole;
import com.nineclient.model.UmpAuthority;
import com.nineclient.model.UmpCompanyService;
import com.nineclient.model.UmpProduct;
import com.nineclient.model.UmpVersion;
import com.nineclient.model.WccFriend;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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

@RequestMapping("/pubroles")
@Controller
@RooWebScaffold(path = "pubroles", formBackingObject = PubRole.class)
public class PubRoleController {
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid PubRole pubRole, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) {
		pubRole.setCompanyId(CommonUtils.getCurrentPubOperator(httpServletRequest).getCompany().getId());
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, pubRole);
            return "pubroles/create";
        }
        uiModel.asMap().clear();
        pubRole.persist();
        return "redirect:/pubroles/" + encodeUrlPathSegment(pubRole.getId().toString(), httpServletRequest);
    }
	
	 @RequestMapping(value = "creates", produces = "text/html")
	 public String creates(Model uiModel,HttpServletRequest request,
			 @RequestParam(value = "name", required = false) String name,
			 @RequestParam(value = "authids", required = false) String aids,
			 @RequestParam(value = "status", required = false) boolean status,
			 @RequestParam(value = "id", required = false) Long id) throws UnsupportedEncodingException {
		if(null!=name && !"".equals(name)){
			name = new String(name.getBytes("ISO-8859-1"),"UTF-8");
		}
		List<UmpAuthority> authoritys = new ArrayList<UmpAuthority>();
        if(null!=aids&&!"".equals(aids)){
        	String idss[] = aids.split(",");
        	if(idss.length>0){
        		UmpAuthority authority = null;
        		for(String aid : idss){
        			authority = UmpAuthority.findUmpAuthority(Long.parseLong(aid));
        			authoritys.add(authority);
        		}
        	}
        }
        
        PubRole role = new PubRole();
        if(null!=id&&!"".equals(id)){
        	role = PubRole.findPubRole(id);
        	role.setRoleName(name);
            role.setAuthoritys(authoritys);
            role.setIsVisable(status);
            role.setCompanyId(CommonUtils.getCurrentPubOperator(request).getCompany().getId());
            role.merge();
            return "redirect:/pubroles?page=1&amp;size=10";
        }
        role.setCompanyId(CommonUtils.getCurrentPubOperator(request).getCompany().getId());
        role.setRoleName(name);
        role.setIsDeleted(true);
        role.setIsVisable(status);
        role.setCreateTime(new Date());
        role.setAuthoritys(authoritys);
        role.persist();
        return "redirect:/pubroles?page=1&amp;size=10";
	 }
	 
	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel,HttpServletRequest request,
    		@RequestParam(value = "displayId", required = false) Long displayId
    		) {
        populateEditForm(uiModel, new PubRole());
        List<String> list = new ArrayList<String>();
        List<UmpProduct> products= new ArrayList<UmpProduct>();
        Set<UmpProduct> productset = new HashSet<UmpProduct>();
        List<UmpCompanyService> umpService = UmpCompanyService.findUmpCompanyServiceByCode(CommonUtils.getCurrentPubOperator(request).getCompany().getCompanyCode());
        if(umpService.size() > 0){
        	List<UmpProduct> up = null;
    	   for (UmpCompanyService umpCom : umpService) {
    		   up = UmpProduct.findUmpProductById(umpCom.getProductId());
    		   for (UmpProduct umpProduct : up) {
    			   products.add(umpProduct);
    		   }
    	   }
       }
        productset.addAll(products);
        
        for(UmpProduct product : products){
        	list.add(product.getId()+"");
        }
        String ids = list.toString();
        uiModel.addAttribute("productIds", ids);
        uiModel.addAttribute("productss", productset);
        if(displayId == null){
        	displayId = CommonUtils.getCurrentDisPlayId(request);
        }
        request.getSession().setAttribute("displayId", displayId);
        return "pubroles/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("pubrole", PubRole.findPubRole(id));
        uiModel.addAttribute("itemId", id);
        return "pubroles/show";
    }
	
	@RequestMapping(value = "tree",produces="text/html;charset=utf-8")
    @ResponseBody
    public String tree(Model uiModel,
    		@RequestParam(value = "pid", required = false) Long pid,
    		@RequestParam(value = "roleId", required = false) Long roleId,
    		HttpServletRequest request, HttpServletResponse response) {
    	StringBuffer strs = new StringBuffer();
    	int num = 0;	
    	List<UmpCompanyService> umpService = UmpCompanyService.findUmpCompanyServiceByCodeAndProductId(pid,CommonUtils.getCurrentPubOperator(request).getCompany().getCompanyCode());
    	List<UmpAuthority> authoritys = null;
    	if(umpService.size() > 0){
    		for (UmpCompanyService umpSer : umpService) {
    			UmpVersion uv = UmpVersion.findUmpVersion(umpSer.getVersionId());
    			authoritys = UmpAuthority.findUmpAuthoritysVersionIdIsUmp(uv);
			}
    	}
    	//List<UmpAuthority> authoritys = UmpAuthority.findAllUmpAuthoritysByProduct(id);
    	List<UmpAuthority> root = UmpAuthority.findAllUmpAuthoritysByProduct(null);
    	if(null!=roleId){
    		if(null!=root&&root.size()>0){
        		strs.append("{id:"+root.get(0).getId()+",pId:"+root.get(0).getParentId()+",name:\""+root.get(0).getDisplayName()+"\""+",open:true}");
        	}
        	for(UmpAuthority auth : authoritys){
        		String str = "id:"+auth.getId()+",pId:"+auth.getParentId()+",name:\""+auth.getDisplayName()+"\""+",open:true";
        		strs.append(",{"+str+"}");
        	}
        	return strs.toString();
    	}
    	if(null!=root&&root.size()>0){
    		strs.append("{id:"+root.get(0).getId()+",pId:"+root.get(0).getParentId()+",name:\""+root.get(0).getDisplayName()+"\""+",open:true,checked:true}");
    	}
    	for(UmpAuthority auth : authoritys){
    		String str = "id:"+auth.getId()+",pId:"+auth.getParentId()+",name:\""+auth.getDisplayName()+"\""+",open:true,checked:true";
    		strs.append(",{"+str+"}");
    	}
    	return strs.toString();
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page,
    		@RequestParam(value = "size", required = false) Integer size,
    		@RequestParam(value = "displayId", required = false) Long displayId, 
    		@RequestParam(value = "sortFieldName", required = false) String sortFieldName, 
    		@RequestParam(value = "sortOrder", required = false) String sortOrder,
    		Model uiModel, HttpServletRequest request) {
        uiModel.addAttribute("limit", 10);
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("pubroles", PubRole.findPubRoleEntries(firstResult, sizeNo, sortFieldName, sortOrder, CommonUtils.getCurrentPubOperator(request).getCompany().getId()));
            float nrOfPages = (float) PubRole.countPubRoles(CommonUtils.getCurrentPubOperator(request).getCompany().getId()) / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("pubroles", PubRole.findAllPubRoles(sortFieldName, sortOrder,CommonUtils.getCurrentPubOperator(request).getCompany()));
        }
        String errmsg = request.getSession().getAttribute("errmsg")+"";
        if(null!=errmsg&&!"".equals(errmsg)){
        	request.getSession().removeAttribute("errmsg");
        }
        if(displayId == null){
        	displayId = CommonUtils.getCurrentDisPlayId(request);
        }
        request.getSession().setAttribute("displayId", displayId);
        System.out.println(displayId+"-=-displayId=-=");
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("errmsg", errmsg);
        return "pubroles/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid PubRole pubRole, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, pubRole);
            return "pubroles/update";
        }
        uiModel.asMap().clear();
        pubRole.merge();
        return "redirect:/pubroles/" + encodeUrlPathSegment(pubRole.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/update", produces = "text/html")
    public String updateForm( @RequestParam(value = "id", required = false) Long id, Model uiModel,HttpServletRequest request) {
        PubRole role = PubRole.findPubRole(id);
        
		StringBuffer ids = new StringBuffer();
		int n = 1;
		for(UmpAuthority au : role.getAuthoritys()){
			ids.append(au.getId());
			if(n!=role.getAuthoritys().size()){
				ids.append(",");
			}
			n++;
		}
		
		List<String> list = new ArrayList<String>();
		
		List<UmpProduct> products= new ArrayList<UmpProduct>();
		 Set<UmpProduct> productset = new HashSet<UmpProduct>();
        List<UmpCompanyService> umpService = UmpCompanyService.findUmpCompanyServiceByCode(CommonUtils.getCurrentPubOperator(request).getCompany().getCompanyCode());
        if(umpService.size() > 0){
        	List<UmpProduct> up = null;
    	   for (UmpCompanyService umpCom : umpService) {
    		   up = UmpProduct.findUmpProductById(umpCom.getProductId());
    		   for (UmpProduct umpProduct : up) {
    			   products.add(umpProduct);
    			   list.add(umpProduct.getId()+"");
    		   }
    	   }
       }
        productset.addAll(products);
        String proids = list.toString();
        uiModel.addAttribute("productIds", proids);
        uiModel.addAttribute("productss", products);
        uiModel.addAttribute("name", role.getRoleName());
        uiModel.addAttribute("id", role.getId());
        uiModel.addAttribute("status", role.getIsVisable());
        uiModel.addAttribute("ids", ids);
        return "pubroles/create";
    }
	
	@RequestMapping(value = "/visible", produces = "text/html")
    public String visible(@RequestParam(value = "id", required = false) Long id,
    		@RequestParam(value="status") Integer status ,@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel,
    		HttpServletRequest request) {
        PubRole pubRole = PubRole.findPubRole(id);
        if(status==0){
        	pubRole.setIsVisable(false);
        }else{
        	pubRole.setIsVisable(true);
        }
        pubRole.merge();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/pubroles";
    }


	@RequestMapping(value = "/delete", produces = "text/html")
    public String delete(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel,
    		HttpServletRequest request) {
        PubRole pubRole = PubRole.findPubRole(id);
        Set<PubOperator> opers = pubRole.getPubOperators();
        int num = opers.size();
        String errmsg = "";
        if(num > 0){
       	 	errmsg = "该权限有账号关联，无法删除！";
        }else{
    	    pubRole.setAuthoritys(null);
    	    pubRole.remove();
       		uiModel.asMap().clear();
        }
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        request.getSession().setAttribute("errmsg", errmsg);
        return "redirect:/pubroles";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("pubRole_createtime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, PubRole pubRole) {
        uiModel.addAttribute("pubRole", pubRole);
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
	
	
	/**
	 * 查询权限
	 * @param uiModel
	 * @param request
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryRoles", produces = "text/html")
	 public String queryRoles(
			 Model uiModel,
			 HttpServletRequest request,
			 @RequestParam(value = "start", required = false) Integer start,
			 @RequestParam(value = "limit", required = false) Integer limit
			 ) throws ServletException, IOException {
		  PubOperator operator = CommonUtils.getCurrentPubOperator(request);
		  Map parmMap = new HashMap();
		  parmMap.put("company", operator.getCompany().getId());
		  PubRole role = new PubRole();
		  QueryModel<PubRole> qm = new QueryModel<PubRole>(role, start, limit);
		  qm.getInputMap().putAll(parmMap);
		   
		  PageModel<PubRole> pm = PubRole.getQueryPubRole(qm);
		     pm.setPageSize(limit);
			 pm.setStartIndex(start);
			 
		 uiModel.addAttribute("pubRoles", pm.getDataList());
		 uiModel.addAttribute("maxPages", pm.getTotalPage());
		 uiModel.addAttribute("page", pm.getPageNo());
		 uiModel.addAttribute("limit", pm.getPageSize());
		 
      return "pubroles/result";
	 }	
	
	/**
	 * 检查是否重名
	 * @param uiModel
	 * @param name
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "check",produces="text/html;charset=utf-8")
    @ResponseBody
    public String check(Model uiModel,
    		@RequestParam(value = "name", required = false) String name,
    		HttpServletRequest request, HttpServletResponse response) {
		Long companyId = CommonUtils.getCurrentCompanyId(request);
    	List<PubRole> roles = PubRole.findPubRoleByName(name,companyId);
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
    	PubRole pubRole = PubRole.findPubRole(id);
        pubRole.setIsVisable(status);
    	return pubRole.merge().getId();
    }
}

package com.nineclient.web;
import com.nineclient.model.PubOperator;
import com.nineclient.model.PubOrganization;
import com.nineclient.model.UmpAuthority;
import com.nineclient.model.WccStore;
import com.nineclient.utils.CommonUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/puborganizations")
@Controller
@RooWebScaffold(path = "puborganizations", formBackingObject = PubOrganization.class)
public class PubOrganizationController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid PubOrganization pubOrganization, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        List<PubOrganization> pubOrganizations = PubOrganization.findAllPubOrganizationsByName(pubOrganization.getName(),CommonUtils.getCurrentPubOperator(httpServletRequest));
		if(pubOrganizations != null && pubOrganizations.size() > 0){
			uiModel.addAttribute("msg", "组织菜单存在");
		}
		if (bindingResult.hasErrors() || (pubOrganizations != null && pubOrganizations.size() > 0 )) {
            populateEditForm(uiModel, pubOrganization);
            return "puborganizations/create";
        }
        uiModel.asMap().clear();
        pubOrganization.setInsertTime(new Date());
        pubOrganization.setIsDeleted(true);
        pubOrganization.setIsVisable(true);
        pubOrganization.persist();
        return "redirect:/puborganizations/" + encodeUrlPathSegment(pubOrganization.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new PubOrganization());
        return "puborganizations/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("puborganization", PubOrganization.findPubOrganization(id));
        uiModel.addAttribute("itemId", id);
        return "puborganizations/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page,
    		@RequestParam(value = "size", required = false) Integer size,
    		@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
    		@RequestParam(value = "displayId", required = false) Long displayId,
    		@RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel
    		,HttpServletRequest httpServletRequest) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("puborganizations", PubOrganization.findPubOrganizationEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) PubOrganization.countPubOrganizations() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("puborganizations", PubOrganization.findAllPubOrganizations(sortFieldName, sortOrder));
        }
        if(displayId == null){
        	displayId = CommonUtils.getCurrentDisPlayId(httpServletRequest);
        }
        httpServletRequest.getSession().setAttribute("displayId", displayId);
        addDateTimeFormatPatterns(uiModel);
        return "puborganizations/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid PubOrganization pubOrganization, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, pubOrganization);
            return "puborganizations/update";
        }
        uiModel.asMap().clear();
        PubOrganization pubOrgan = PubOrganization.findPubOrganization(pubOrganization.getId());
        pubOrganization.setInsertTime(pubOrgan.getInsertTime());
        pubOrganization.setIsDeleted(pubOrgan.getIsDeleted());
        pubOrganization.setIsVisable(pubOrgan.getIsVisable());
        pubOrganization.merge();
        return "redirect:/puborganizations/" + encodeUrlPathSegment(pubOrganization.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		PubOrganization ogran = PubOrganization.findPubOrganization(id);
        uiModel.addAttribute("aid",ogran.getId());
		uiModel.addAttribute("pid",ogran.getParentId());
		populateEditForm(uiModel, ogran);
        return "puborganizations/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        PubOrganization pubOrganization = PubOrganization.findPubOrganization(id);
        pubOrganization.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/puborganizations";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("pubOrganization_inserttime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, PubOrganization pubOrganization) {
        uiModel.addAttribute("pubOrganization", pubOrganization);
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
	
	 @RequestMapping(value = "tree",produces="text/html;charset=utf-8")
	 @ResponseBody
	 public String tree(Model uiModel,HttpServletRequest request, HttpServletResponse response) {
	    StringBuilder strs = new StringBuilder();
	    List<PubOrganization> organizations = PubOrganization.findAllPubOrganizations(CommonUtils.getCurrentPubOperator(request).getCompany().getId());
	    List<UmpAuthority> root = UmpAuthority.findAllUmpAuthoritysByProduct(null);
    	if(null!=root&&root.size()>0){
    		strs.append("{id:"+root.get(0).getId()+",pId:"+root.get(0).getParentId()+",name:\""+root.get(0).getDisplayName()+"\""+",open:false}");
    	}
	    for(PubOrganization auth : organizations){
	    	String str = "id:"+auth.getId()+",pId:"+auth.getParentId()+",name:\""+auth.getName()+"\""+",open:false";
	    	strs.append(",{"+str+"}");
	    	/*if(num==0){
	    		strs.append("{"+str+"}");
	    	}else{
	    		strs.append(",{"+str+"}");
	    	}
	    	num++;*/
	    }
	    return strs.toString();
	}
 
	@RequestMapping(value = "updateTree",produces="text/html;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String updateTree(Model uiModel,long id,String name,HttpServletRequest request, HttpServletResponse response) {
		List<PubOrganization> pubOrganizations = PubOrganization.findAllPubOrganizationsByName(name,CommonUtils.getCurrentPubOperator(request));
		if(pubOrganizations != null && pubOrganizations.size() > 0){
			String str = "{msg:\'faile\'}";//修改失败菜单名称存在
			return str;
		}
		PubOrganization pubOrganization = PubOrganization.findPubOrganization(id);
		pubOrganization.setName(name);
		pubOrganization.setCompanyId(CommonUtils.getCurrentPubOperator(request).getCompany().getId());
		pubOrganization.merge();
		String str = "{msg:\'success\'}";//修改菜单名称成功
		return str;
	}
	
	/**
	 * 查詢是否有子節點
	 * @param uiModel
	 * @param id
	 * @param pid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteTreeById",produces="text/html;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String deleteTreeById(Model uiModel,long id,HttpServletRequest request, HttpServletResponse response){
		
		List<WccStore> wccStores = WccStore.findAllWccStoresById(id);
		if(wccStores != null && wccStores.size() > 0){
			String str = "{msg:\'fails\'}";//关联门店
			return str;
		}
		
		List<PubOperator> pubOperators = PubOperator.findAllPubOperatorsById(id);
		if(pubOperators != null && pubOperators.size() > 0){
			String str = "{msg:\'failes\'}";//关联账号
			return str;
		}
		
		List<PubOrganization> pubOrganizations = PubOrganization.findAllPubOrganizationsById(id,false);
		if(pubOrganizations != null && pubOrganizations.size() > 0){
			String str = "{msg:\'faile\'}";//存在子節點
			return str;
		}
		String str = "{msg:\'success\'}";//沒有存在子節點
		return str;
	}
	
	/**
	 * 删除节点
	 * 如果删除父节点，连子节点都删除。
	 * @param uiModel
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteTreeByIdOrIds",produces="text/html;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String deleteTreeByIdOrIds(Model uiModel,long id,HttpServletRequest request, HttpServletResponse response){
		List<PubOrganization> pubOrganizations = PubOrganization.findAllPubOrganizationsById(id,false);
		if(pubOrganizations != null && pubOrganizations.size() > 0){
			for (PubOrganization pubOrgan : pubOrganizations) {
				PubOrganization pubOrganization = PubOrganization.findPubOrganization(pubOrgan.getId());
				pubOrganization.remove();//循環刪除pid是传进来的id。
			}
		}
		PubOrganization pubOrganization = PubOrganization.findPubOrganization(id);
		pubOrganization.remove();
		String str = "{msg:\'success\'}";//沒有存在子節點直接刪除
		return str;
	}
	
	@RequestMapping(value = "addTreeByPid",produces="text/html;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String addTreeByPid(Model uiModel,long pId,String name,HttpServletRequest request, HttpServletResponse response){
		List<PubOrganization> pubOrganiza = PubOrganization.findAllPubOrganizationsByName(name,CommonUtils.getCurrentPubOperator(request));
		if(pubOrganiza != null && pubOrganiza.size() > 0){
			String str = "{msg:\'fail\'}";//菜单名称存在
			return str;
		}
		/**
		 * 上述判断都通过。
		 * 添加节点，
		 */
		PubOrganization ubOrgan = new PubOrganization();
		ubOrgan.setName(name);
		ubOrgan.setParentId(new Long(pId));
		ubOrgan.setInsertTime(new Date());
		ubOrgan.setIsDeleted(true);
		ubOrgan.setIsVisable(true);
		ubOrgan.setCompanyId(CommonUtils.getCurrentPubOperator(request).getCompany().getId());
		ubOrgan.persist();
		String str = "{msg:\'success\'}";//沒有存在子節點直接刪除
		return str;
	}
	
	//TODO下载模板
	@RequestMapping(value = "/downLoadTemplate", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView downLoadTemplate(Model uiModel, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String rootpath = request.getSession().getServletContext()
				.getRealPath("/");
		String fileName = System.currentTimeMillis() + "organization.xls";
		InputStream inStream = new FileInputStream(rootpath+"WEB-INF\\classes\\template\\organization.xls");
		response.setContentType("text/html;charset=UTF-8"); 
		response.setContentType("application/x-excel");  
        response.setCharacterEncoding("UTF-8"); 
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
        
        byte[] b = new byte[100];
        int len;
        
        while((len=inStream.read(b))>0){
        	response.getOutputStream().write(b, 0, len);
        }
        inStream.close();
        
        return null;
	
	}
	
}

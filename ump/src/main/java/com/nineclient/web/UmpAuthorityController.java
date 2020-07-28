package com.nineclient.web;
import com.nineclient.model.UmpAuthority;
import com.nineclient.model.UmpProduct;
import com.nineclient.model.UmpRole;
import com.nineclient.utils.CommonUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/umpauthoritys")
@Controller
@RooWebScaffold(path = "umpauthoritys", formBackingObject = UmpAuthority.class)
public class UmpAuthorityController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid UmpAuthority umpAuthority, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		List<UmpAuthority> umpAuthoritys = UmpAuthority.findUmpAuthorityByName(umpAuthority.getDisplayName());
		if(umpAuthoritys!=null&&umpAuthoritys.size()>0){
			List<UmpProduct> products = UmpProduct.findAllUmpProducts();
		    uiModel.addAttribute("products", products);
			uiModel.addAttribute("msg", "菜单名称不能重复");
		}
		if (bindingResult.hasErrors()||(null!=umpAuthoritys&&umpAuthoritys.size()>0)) {
            populateEditForm(uiModel, umpAuthority);
            return "umpauthoritys/create";
        }
        uiModel.asMap().clear();
        umpAuthority.persist();
        return "redirect:/umpauthoritys?form";
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel,HttpServletRequest request,
    		@RequestParam(value = "displayId", required = false) Long displayId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size) {
        populateEditForm(uiModel, new UmpAuthority());
        List<UmpProduct> products = UmpProduct.findAllUmpProducts();
        uiModel.addAttribute("products", products);
        if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(request);
	    }
		request.getSession().setAttribute("displayId", displayId);
        return "umpauthoritys/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("umpauthority", UmpAuthority.findUmpAuthority(id));
        uiModel.addAttribute("itemId", id);
        return "umpauthoritys/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page,
    		@RequestParam(value = "size", required = false) Integer size,
    		@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
    		@RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel,
    		@RequestParam(value = "displayId", required = false) Long displayId,HttpServletRequest request) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("umpauthoritys", UmpAuthority.findUmpAuthorityEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) UmpAuthority.countUmpAuthoritys() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("umpauthoritys", UmpAuthority.findAllUmpAuthoritys(sortFieldName, sortOrder));
        }
        if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(request);
	    }
		request.getSession().setAttribute("displayId", displayId);
        return "umpauthoritys/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid UmpAuthority umpAuthority, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, umpAuthority);
            return "umpauthoritys/update";
        }
        uiModel.asMap().clear();
        umpAuthority.merge();
        return "redirect:/umpauthoritys?page=1&amp;size=10";
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		UmpAuthority auth = UmpAuthority.findUmpAuthority(id);
		uiModel.addAttribute("aid",auth.getId());
		uiModel.addAttribute("pid",auth.getParentId());
		uiModel.addAttribute("status", auth.getIsVisable());
		List<UmpProduct> products = UmpProduct.findAllUmpProducts();
        uiModel.addAttribute("products", products);
        populateEditForm(uiModel, auth);
        return "umpauthoritys/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        UmpAuthority umpAuthority = UmpAuthority.findUmpAuthority(id);
        umpAuthority.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/umpauthoritys";
    }

	void populateEditForm(Model uiModel, UmpAuthority umpAuthority) {
        uiModel.addAttribute("umpAuthority", umpAuthority);
        uiModel.addAttribute("umproles", UmpRole.findAllUmpRoles());
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

package com.nineclient.web;
import com.nineclient.model.VocTemplateCategory;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/voctemplatecategorys")
@Controller
@RooWebScaffold(path = "voctemplatecategorys", formBackingObject = VocTemplateCategory.class)
public class VocTemplateCategoryController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid VocTemplateCategory vocTemplateCategory, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, vocTemplateCategory);
            return "voctemplatecategorys/create";
        }
        uiModel.asMap().clear();
        vocTemplateCategory.persist();
        return "redirect:/voctemplatecategorys/" + encodeUrlPathSegment(vocTemplateCategory.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new VocTemplateCategory());
        return "voctemplatecategorys/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("voctemplatecategory", VocTemplateCategory.findVocTemplateCategory(id));
        uiModel.addAttribute("itemId", id);
        return "voctemplatecategorys/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("voctemplatecategorys", VocTemplateCategory.findVocTemplateCategoryEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) VocTemplateCategory.countVocTemplateCategorys() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("voctemplatecategorys", VocTemplateCategory.findAllVocTemplateCategorys(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "voctemplatecategorys/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid VocTemplateCategory vocTemplateCategory, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, vocTemplateCategory);
            return "voctemplatecategorys/update";
        }
        uiModel.asMap().clear();
        vocTemplateCategory.merge();
        return "redirect:/voctemplatecategorys/" + encodeUrlPathSegment(vocTemplateCategory.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, VocTemplateCategory.findVocTemplateCategory(id));
        return "voctemplatecategorys/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        VocTemplateCategory vocTemplateCategory = VocTemplateCategory.findVocTemplateCategory(id);
        vocTemplateCategory.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/voctemplatecategorys";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("vocTemplateCategory_createtime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, VocTemplateCategory vocTemplateCategory) {
        uiModel.addAttribute("vocTemplateCategory", vocTemplateCategory);
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

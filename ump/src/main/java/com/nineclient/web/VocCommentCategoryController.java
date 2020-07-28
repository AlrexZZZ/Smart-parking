package com.nineclient.web;
import com.nineclient.model.VocCommentCategory;
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

@RequestMapping("/voccommentcategorys")
@Controller
@RooWebScaffold(path = "voccommentcategorys", formBackingObject = VocCommentCategory.class)
public class VocCommentCategoryController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid VocCommentCategory vocCommentCategory, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, vocCommentCategory);
            return "voccommentcategorys/create";
        }
        uiModel.asMap().clear();
        vocCommentCategory.persist();
        return "redirect:/voccommentcategorys/" + encodeUrlPathSegment(vocCommentCategory.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new VocCommentCategory());
        return "voccommentcategorys/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("voccommentcategory", VocCommentCategory.findVocCommentCategory(id));
        uiModel.addAttribute("itemId", id);
        return "voccommentcategorys/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("voccommentcategorys", VocCommentCategory.findVocCommentCategoryEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) VocCommentCategory.countVocCommentCategorys() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("voccommentcategorys", VocCommentCategory.findAllVocCommentCategorys(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "voccommentcategorys/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid VocCommentCategory vocCommentCategory, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, vocCommentCategory);
            return "voccommentcategorys/update";
        }
        uiModel.asMap().clear();
        vocCommentCategory.merge();
        return "redirect:/voccommentcategorys/" + encodeUrlPathSegment(vocCommentCategory.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, VocCommentCategory.findVocCommentCategory(id));
        return "voccommentcategorys/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        VocCommentCategory vocCommentCategory = VocCommentCategory.findVocCommentCategory(id);
        vocCommentCategory.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/voccommentcategorys";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("vocCommentCategory_createtime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, VocCommentCategory vocCommentCategory) {
        uiModel.addAttribute("vocCommentCategory", vocCommentCategory);
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

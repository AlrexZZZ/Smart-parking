package com.nineclient.web;
import com.nineclient.model.WccUserPagetemp;
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

@RequestMapping("/wccuserpagetemps")
@Controller
@RooWebScaffold(path = "wccuserpagetemps", formBackingObject = WccUserPagetemp.class)
public class WccUserPagetempController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WccUserPagetemp wccUserPagetemp, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccUserPagetemp);
            return "wccuserpagetemps/create";
        }
        uiModel.asMap().clear();
        wccUserPagetemp.persist();
        return "redirect:/wccuserpagetemps/" + encodeUrlPathSegment(wccUserPagetemp.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new WccUserPagetemp());
        return "wccuserpagetemps/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("wccuserpagetemp", WccUserPagetemp.findWccUserPagetemp(id));
        uiModel.addAttribute("itemId", id);
        return "wccuserpagetemps/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("wccuserpagetemps", WccUserPagetemp.findWccUserPagetempEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) WccUserPagetemp.countWccUserPagetemps() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("wccuserpagetemps", WccUserPagetemp.findAllWccUserPagetemps(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "wccuserpagetemps/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid WccUserPagetemp wccUserPagetemp, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccUserPagetemp);
            return "wccuserpagetemps/update";
        }
        uiModel.asMap().clear();
        wccUserPagetemp.merge();
        return "redirect:/wccuserpagetemps/" + encodeUrlPathSegment(wccUserPagetemp.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, WccUserPagetemp.findWccUserPagetemp(id));
        return "wccuserpagetemps/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        WccUserPagetemp wccUserPagetemp = WccUserPagetemp.findWccUserPagetemp(id);
        wccUserPagetemp.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/wccuserpagetemps";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("wccUserPagetemp_createtime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, WccUserPagetemp wccUserPagetemp) {
        uiModel.addAttribute("wccUserPagetemp", wccUserPagetemp);
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

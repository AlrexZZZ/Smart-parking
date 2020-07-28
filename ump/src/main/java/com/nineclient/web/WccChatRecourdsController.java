package com.nineclient.web;
import com.nineclient.model.WccChatRecourds;
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

@RequestMapping("/wccchatrecourdses")
@Controller
@RooWebScaffold(path = "wccchatrecourdses", formBackingObject = WccChatRecourds.class)
public class WccChatRecourdsController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WccChatRecourds wccChatRecourds, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccChatRecourds);
            return "wccchatrecourdses/create";
        }
        uiModel.asMap().clear();
        wccChatRecourds.persist();
        return "redirect:/wccchatrecourdses/" + encodeUrlPathSegment(wccChatRecourds.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new WccChatRecourds());
        return "wccchatrecourdses/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("wccchatrecourds", WccChatRecourds.findWccChatRecourds(id));
        uiModel.addAttribute("itemId", id);
        return "wccchatrecourdses/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("wccchatrecourdses", WccChatRecourds.findWccChatRecourdsEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) WccChatRecourds.countWccChatRecourdses() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("wccchatrecourdses", WccChatRecourds.findAllWccChatRecourdses(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "wccchatrecourdses/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid WccChatRecourds wccChatRecourds, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccChatRecourds);
            return "wccchatrecourdses/update";
        }
        uiModel.asMap().clear();
        wccChatRecourds.merge();
        return "redirect:/wccchatrecourdses/" + encodeUrlPathSegment(wccChatRecourds.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, WccChatRecourds.findWccChatRecourds(id));
        return "wccchatrecourdses/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        WccChatRecourds wccChatRecourds = WccChatRecourds.findWccChatRecourds(id);
        wccChatRecourds.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/wccchatrecourdses";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("wccChatRecourds_inserttime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("wccChatRecourds_starttime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("wccChatRecourds_endtime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, WccChatRecourds wccChatRecourds) {
        uiModel.addAttribute("wccChatRecourds", wccChatRecourds);
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

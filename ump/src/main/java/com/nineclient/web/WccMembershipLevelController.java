package com.nineclient.web;
import com.nineclient.model.WccMembershipLevel;
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

@RequestMapping("/wccmembershiplevels")
@Controller
@RooWebScaffold(path = "wccmembershiplevels", formBackingObject = WccMembershipLevel.class)
public class WccMembershipLevelController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WccMembershipLevel wccMembershipLevel, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccMembershipLevel);
            return "wccmembershiplevels/create";
        }
        uiModel.asMap().clear();
        wccMembershipLevel.persist();
        return "redirect:/wccmembershiplevels/" + encodeUrlPathSegment(wccMembershipLevel.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new WccMembershipLevel());
        return "wccmembershiplevels/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("wccmembershiplevel", WccMembershipLevel.findWccMembershipLevel(id));
        uiModel.addAttribute("itemId", id);
        return "wccmembershiplevels/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("wccmembershiplevels", WccMembershipLevel.findWccMembershipLevelEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) WccMembershipLevel.countWccMembershipLevels() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("wccmembershiplevels", WccMembershipLevel.findAllWccMembershipLevels(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "wccmembershiplevels/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid WccMembershipLevel wccMembershipLevel, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccMembershipLevel);
            return "wccmembershiplevels/update";
        }
        uiModel.asMap().clear();
        wccMembershipLevel.merge();
        return "redirect:/wccmembershiplevels/" + encodeUrlPathSegment(wccMembershipLevel.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, WccMembershipLevel.findWccMembershipLevel(id));
        return "wccmembershiplevels/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        WccMembershipLevel wccMembershipLevel = WccMembershipLevel.findWccMembershipLevel(id);
        wccMembershipLevel.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/wccmembershiplevels";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("wccMembershipLevel_createtime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, WccMembershipLevel wccMembershipLevel) {
        uiModel.addAttribute("wccMembershipLevel", wccMembershipLevel);
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

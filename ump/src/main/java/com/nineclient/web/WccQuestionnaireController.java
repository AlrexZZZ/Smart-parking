package com.nineclient.web;
import com.nineclient.constant.WccStatus;
import com.nineclient.model.WccQuestionnaire;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
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

@RequestMapping("/wccquestionnaires")
@Controller
@RooWebScaffold(path = "wccquestionnaires", formBackingObject = WccQuestionnaire.class)
public class WccQuestionnaireController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WccQuestionnaire wccQuestionnaire, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccQuestionnaire);
            return "wccquestionnaires/create";
        }
        uiModel.asMap().clear();
        wccQuestionnaire.persist();
        return "redirect:/wccquestionnaires/" + encodeUrlPathSegment(wccQuestionnaire.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new WccQuestionnaire());
        return "wccquestionnaires/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("wccquestionnaire", WccQuestionnaire.findWccQuestionnaire(id));
        uiModel.addAttribute("itemId", id);
        return "wccquestionnaires/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("wccquestionnaires", WccQuestionnaire.findWccQuestionnaireEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) WccQuestionnaire.countWccQuestionnaires() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("wccquestionnaires", WccQuestionnaire.findAllWccQuestionnaires(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "wccquestionnaires/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid WccQuestionnaire wccQuestionnaire, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccQuestionnaire);
            return "wccquestionnaires/update";
        }
        uiModel.asMap().clear();
        wccQuestionnaire.merge();
        return "redirect:/wccquestionnaires/" + encodeUrlPathSegment(wccQuestionnaire.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, WccQuestionnaire.findWccQuestionnaire(id));
        return "wccquestionnaires/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        WccQuestionnaire wccQuestionnaire = WccQuestionnaire.findWccQuestionnaire(id);
        wccQuestionnaire.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/wccquestionnaires";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("wccQuestionnaire_visabletime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, WccQuestionnaire wccQuestionnaire) {
        uiModel.addAttribute("wccQuestionnaire", wccQuestionnaire);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("wccstatuses", Arrays.asList(WccStatus.values()));
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

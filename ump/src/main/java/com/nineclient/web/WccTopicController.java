package com.nineclient.web;
import com.nineclient.constant.WccTopicStatus;
import com.nineclient.model.WccTopic;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
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

@RequestMapping("/wcctopics")
@Controller
@RooWebScaffold(path = "wcctopics", formBackingObject = WccTopic.class)
public class WccTopicController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WccTopic wccTopic, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccTopic);
            return "wcctopics/create";
        }
        uiModel.asMap().clear();
        wccTopic.persist();
        return "redirect:/wcctopics/" + encodeUrlPathSegment(wccTopic.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new WccTopic());
        return "wcctopics/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("wcctopic", WccTopic.findWccTopic(id));
        uiModel.addAttribute("itemId", id);
        return "wcctopics/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("wcctopics", WccTopic.findWccTopicEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) WccTopic.countWccTopics() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("wcctopics", WccTopic.findAllWccTopics(sortFieldName, sortOrder));
        }
        return "wcctopics/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid WccTopic wccTopic, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccTopic);
            return "wcctopics/update";
        }
        uiModel.asMap().clear();
        wccTopic.merge();
        return "redirect:/wcctopics/" + encodeUrlPathSegment(wccTopic.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, WccTopic.findWccTopic(id));
        return "wcctopics/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        WccTopic wccTopic = WccTopic.findWccTopic(id);
        wccTopic.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/wcctopics";
    }

	void populateEditForm(Model uiModel, WccTopic wccTopic) {
        uiModel.addAttribute("wccTopic", wccTopic);
        uiModel.addAttribute("wcctopicstatuses", Arrays.asList(WccTopicStatus.values()));
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

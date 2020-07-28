package com.nineclient.web;
import com.nineclient.constant.WccTopicStatus;
import com.nineclient.model.WccCommunity;
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

@RequestMapping("/wcccommunitys")
@Controller
@RooWebScaffold(path = "wcccommunitys", formBackingObject = WccCommunity.class)
public class WccCommunityController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WccCommunity wccCommunity, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccCommunity);
            return "wcccommunitys/create";
        }
        uiModel.asMap().clear();
        wccCommunity.persist();
        return "redirect:/wcccommunitys/" + encodeUrlPathSegment(wccCommunity.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new WccCommunity());
        return "wcccommunitys/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("wcccommunity", WccCommunity.findWccCommunity(id));
        uiModel.addAttribute("itemId", id);
        return "wcccommunitys/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("wcccommunitys", WccCommunity.findWccCommunityEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) WccCommunity.countWccCommunitys() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("wcccommunitys", WccCommunity.findAllWccCommunitys(sortFieldName, sortOrder));
        }
        return "wcccommunitys/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid WccCommunity wccCommunity, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccCommunity);
            return "wcccommunitys/update";
        }
        uiModel.asMap().clear();
        wccCommunity.merge();
        return "redirect:/wcccommunitys/" + encodeUrlPathSegment(wccCommunity.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, WccCommunity.findWccCommunity(id));
        return "wcccommunitys/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        WccCommunity wccCommunity = WccCommunity.findWccCommunity(id);
        wccCommunity.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/wcccommunitys";
    }

	void populateEditForm(Model uiModel, WccCommunity wccCommunity) {
        uiModel.addAttribute("wccCommunity", wccCommunity);
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

package com.nineclient.web;
import com.nineclient.model.WccInterActiveApp;
import java.io.UnsupportedEncodingException;
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

@RequestMapping("/wccinteractiveapps")
@Controller
@RooWebScaffold(path = "wccinteractiveapps", formBackingObject = WccInterActiveApp.class)
public class WccInterActiveAppController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WccInterActiveApp wccInterActiveApp, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccInterActiveApp);
            return "wccinteractiveapps/create";
        }
        uiModel.asMap().clear();
        wccInterActiveApp.persist();
        return "redirect:/wccinteractiveapps/" + encodeUrlPathSegment(wccInterActiveApp.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new WccInterActiveApp());
        return "wccinteractiveapps/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("wccinteractiveapp", WccInterActiveApp.findWccInterActiveApp(id));
        uiModel.addAttribute("itemId", id);
        return "wccinteractiveapps/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("wccinteractiveapps", WccInterActiveApp.findWccInterActiveAppEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) WccInterActiveApp.countWccInterActiveApps() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("wccinteractiveapps", WccInterActiveApp.findAllWccInterActiveApps(sortFieldName, sortOrder));
        }
        return "wccinteractiveapps/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid WccInterActiveApp wccInterActiveApp, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccInterActiveApp);
            return "wccinteractiveapps/update";
        }
        uiModel.asMap().clear();
        wccInterActiveApp.merge();
        return "redirect:/wccinteractiveapps/" + encodeUrlPathSegment(wccInterActiveApp.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, WccInterActiveApp.findWccInterActiveApp(id));
        return "wccinteractiveapps/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        WccInterActiveApp wccInterActiveApp = WccInterActiveApp.findWccInterActiveApp(id);
        wccInterActiveApp.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/wccinteractiveapps";
    }

	void populateEditForm(Model uiModel, WccInterActiveApp wccInterActiveApp) {
        uiModel.addAttribute("wccInterActiveApp", wccInterActiveApp);
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

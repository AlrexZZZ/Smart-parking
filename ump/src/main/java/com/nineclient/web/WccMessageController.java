package com.nineclient.web;
import com.nineclient.constant.WccMessageContentType;
import com.nineclient.constant.WccMessageMsgType;
import com.nineclient.constant.WccMessageStatus;
import com.nineclient.model.WccMessage;
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

@RequestMapping("/wccmessages")
@Controller
@RooWebScaffold(path = "wccmessages", formBackingObject = WccMessage.class)
public class WccMessageController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WccMessage wccMessage, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccMessage);
            return "wccmessages/create";
        }
        uiModel.asMap().clear();
        wccMessage.persist();
        return "redirect:/wccmessages/" + encodeUrlPathSegment(wccMessage.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new WccMessage());
        return "wccmessages/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("wccmessage", WccMessage.findWccMessage(id));
        uiModel.addAttribute("itemId", id);
        return "wccmessages/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("wccmessages", WccMessage.findWccMessageEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) WccMessage.countWccMessages() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("wccmessages", WccMessage.findAllWccMessages(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "wccmessages/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid WccMessage wccMessage, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccMessage);
            return "wccmessages/update";
        }
        uiModel.asMap().clear();
        wccMessage.merge();
        return "redirect:/wccmessages/" + encodeUrlPathSegment(wccMessage.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, WccMessage.findWccMessage(id));
        return "wccmessages/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        WccMessage wccMessage = WccMessage.findWccMessage(id);
        wccMessage.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/wccmessages";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("wccMessage_inserttime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, WccMessage wccMessage) {
        uiModel.addAttribute("wccMessage", wccMessage);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("wccmessagecontenttypes", Arrays.asList(WccMessageContentType.values()));
        uiModel.addAttribute("wccmessagemsgtypes", Arrays.asList(WccMessageMsgType.values()));
        uiModel.addAttribute("wccmessagestatuses", Arrays.asList(WccMessageStatus.values()));
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

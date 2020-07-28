package com.nineclient.web;
import com.nineclient.constant.WccQuestionType;
import com.nineclient.model.WccQuestion;
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

@RequestMapping("/wccquestions")
@Controller
@RooWebScaffold(path = "wccquestions", formBackingObject = WccQuestion.class)
public class WccQuestionController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WccQuestion wccQuestion, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccQuestion);
            return "wccquestions/create";
        }
        uiModel.asMap().clear();
        wccQuestion.persist();
        return "redirect:/wccquestions/" + encodeUrlPathSegment(wccQuestion.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new WccQuestion());
        return "wccquestions/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("wccquestion", WccQuestion.findWccQuestion(id));
        uiModel.addAttribute("itemId", id);
        return "wccquestions/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("wccquestions", WccQuestion.findWccQuestionEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) WccQuestion.countWccQuestions() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("wccquestions", WccQuestion.findAllWccQuestions(sortFieldName, sortOrder));
        }
        return "wccquestions/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid WccQuestion wccQuestion, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccQuestion);
            return "wccquestions/update";
        }
        uiModel.asMap().clear();
        wccQuestion.merge();
        return "redirect:/wccquestions/" + encodeUrlPathSegment(wccQuestion.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, WccQuestion.findWccQuestion(id));
        return "wccquestions/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        WccQuestion wccQuestion = WccQuestion.findWccQuestion(id);
        wccQuestion.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/wccquestions";
    }

	void populateEditForm(Model uiModel, WccQuestion wccQuestion) {
        uiModel.addAttribute("wccQuestion", wccQuestion);
        uiModel.addAttribute("wccquestiontypes", Arrays.asList(WccQuestionType.values()));
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

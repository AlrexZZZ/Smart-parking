package com.nineclient.web;
import com.nineclient.constant.WccCommentStatus;
import com.nineclient.model.PubOperator;
import com.nineclient.model.WccComment;
import com.nineclient.model.WccContent;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.PageModel;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/wcccomments")
@Controller
@RooWebScaffold(path = "wcccomments", formBackingObject = WccComment.class)
public class WccCommentController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WccComment wccComment, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccComment);
            return "wcccomments/create";
        }
        uiModel.asMap().clear();
        wccComment.persist();
        return "redirect:/wcccomments/" + encodeUrlPathSegment(wccComment.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new WccComment());
        return "wcccomments/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("wcccomment", WccComment.findWccComment(id));
        uiModel.addAttribute("itemId", id);
        return "wcccomments/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("wcccomments", WccComment.findAllWccComments(sortFieldName, sortOrder));
            float nrOfPages = (float) WccComment.countWccComments() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("wcccomments", WccComment.findAllWccComments(sortFieldName, sortOrder));
        }
        return "wcccomments/list";
    }
	
	@RequestMapping(value = "/findListByFiled", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String findListByFiled(
			Model uiModel,
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "contentId", required = false) Long contentId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "platId", required = false) String platId) {
		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
		Set<WccPlatformUser> platSets = new HashSet<WccPlatformUser>();
		PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
		if(platId != null && !"".equals(platId)){
			String[] ids = platId.split(",");
			for (String id : ids) {
				platSets.add(WccPlatformUser.findWccPlatformUser(Long.parseLong(id)));
			}
		}else{
			if(pub.getPubRole() == null){
				platSets.addAll(WccPlatformUser.findAllWccPlatformUsers(pub));
			}else{
				for (WccPlatformUser wccPlatformUser : CommonUtils.set2List(PubOperator.findPubOperator(pub.getId()).getPlatformUsers())) {
					platSets.add(wccPlatformUser);
				}
			}
		}
		StringBuilder sq = new StringBuilder();
		int i = 1;
		for (WccPlatformUser wccPlatformUser : CommonUtils.set2List(platSets)) {
			sq.append(wccPlatformUser.getId());
			if(i != platSets.size()){
				sq.append(",");
			}
			i++;
		}
		List<WccComment> wccCommentList = new ArrayList<WccComment>();
		List<WccComment> wccComment = WccComment.findWccCommentEntriesByFiled(firstResult, sizeNo, platId, title,pub,contentId);
		if(null != wccComment && wccComment.size() > 0){
			WccFriend wccF = null;
			for (WccComment wccC : wccComment) {
				wccF = WccFriend.findWccFriend(wccC.getFriendId());
				try {
					wccF.setNickName(URLDecoder.decode(wccF.getNickName(),"utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				wccC.setWccFriend(wccF);
				wccCommentList.add(wccC);
			}
		}
		Long count = WccComment.countWccCommentByFiled(platId, title);
		PageModel<WccComment> pageMode = new PageModel<WccComment>();
		pageMode.setPageNo(page);
		pageMode.setTotalCount(Integer.parseInt(""+count));
		pageMode.setPageSize(sizeNo);
		pageMode.setDataJson(WccComment.toJsonArray(wccCommentList));
		return pageMode.toJson();
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid WccComment wccComment, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccComment);
            return "wcccomments/update";
        }
        uiModel.asMap().clear();
        wccComment.merge();
        return "redirect:/wcccomments/" + encodeUrlPathSegment(wccComment.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, WccComment.findWccComment(id));
        return "wcccomments/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        WccComment wccComment = WccComment.findWccComment(id);
        wccComment.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/wcccomments";
    }

	void populateEditForm(Model uiModel, WccComment wccComment) {
        uiModel.addAttribute("wccComment", wccComment);
        uiModel.addAttribute("wcccommentstatuses", Arrays.asList(WccCommentStatus.values()));
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

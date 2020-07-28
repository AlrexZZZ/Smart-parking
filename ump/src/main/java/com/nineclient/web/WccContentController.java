package com.nineclient.web;

import com.nineclient.model.PubOperator;
import com.nineclient.model.PubOrganization;
import com.nineclient.model.WccComment;
import com.nineclient.model.WccContent;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccMenu;
import com.nineclient.model.WccPraise;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageModel;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import net.sf.json.JSONObject;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/wcccontents")
@Controller
@RooWebScaffold(path = "wcccontents", formBackingObject = WccContent.class)
public class WccContentController {
	
	@RequestMapping(value = "/addWccConent", method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid WccContent wccContent,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest request) {
		PubOperator pub = CommonUtils.getCurrentPubOperator(request);
		wccContent.setInsertTime(new Date());
		wccContent.setIsVisible(true);
		wccContent.setClickCount(0);
		wccContent.setIsDeleted(false);
		wccContent.setIsCheck(0);
		wccContent.setContentUrl(null);
		wccContent.setUserName(pub
				.getAccount());
		wccContent.setCompanyId(pub
				.getCompany().getId());
		uiModel.asMap().clear();
		wccContent.persist();
		return "redirect:/wcccontents?page=1&amp;size=10";
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel,
			HttpServletRequest httpServletRequest) {
		populateEditForm(uiModel, new WccContent());
		uiModel.addAttribute("test", "");
		
		return "wcccontents/create";
	}

	@RequestMapping(produces = "text/html")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "displayId", required = false) Long displayId,
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		
		uiModel.addAttribute("Global", Global.Url);
		if(displayId == null){
			displayId = CommonUtils.getCurrentDisPlayId(httpServletRequest);
		}
		httpServletRequest.getSession().setAttribute("displayId",displayId);
		return "wcccontents/list";
	}

	@RequestMapping(value = "/findListByFiled", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String findListByFiled(
			Model uiModel,
			WccContent wccContent,
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "starTime", required = false) String starTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "isPrizeCommit", required = false) Boolean isPrizeCommit,
			@RequestParam(value = "isCheck", required = false) Integer isCheck,
			@RequestParam(value = "title", required = false) String title) {

		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1)* sizeNo;
		PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
		List<WccContent> contentList = WccContent.findWccContentEntriesByFiled(
				firstResult, sizeNo,isPrizeCommit, isCheck, starTime,
				endTime, title,pub.getCompany().getId());
		Long count = WccContent.countWccContentsByFiled(isPrizeCommit,isCheck, title, starTime, endTime,pub.getCompany().getId());
		PageModel<WccContent> pageMode = new PageModel<WccContent>();
		pageMode.setPageNo(page);
		pageMode.setTotalCount(Integer.parseInt(count + ""));
		pageMode.setPageSize(sizeNo);
		pageMode.setDataJson(WccContent.toJsonArray(contentList));
		return pageMode.toJson();
	}
	

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "text/html")
	public String update(@RequestParam(value = "id", required = false) long id,
			WccContent wccContent, BindingResult bindingResult, Model uiModel,
			HttpServletRequest request) {
		WccContent wccConts = WccContent.findWccContent(id);
		wccConts.setTitle(wccContent.getTitle());
		wccConts.setIsCheck(0);
		wccConts.setContent(wccContent.getContent());
		wccConts.setIsPrizeCommit(wccContent.getIsPrizeCommit());
		wccConts.setUserName(CommonUtils.getCurrentPubOperator(request)
				.getAccount());
		uiModel.asMap().clear();
		wccConts.merge();
		return "redirect:/wcccontents?page=1&amp;size=10";
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel,
			HttpServletRequest httpServletRequest) {
		populateEditForm(uiModel, WccContent.findWccContent(id));
		return "wcccontents/update";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "text/html")
	public String delete(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			HttpServletRequest request,
			Model uiModel) {
		WccContent wccContent = WccContent.findWccContent(id);
		if(null != wccContent){
			wccContent.setIsDeleted(true);
			wccContent.merge();
		}
		//删除微内容先要删除微内容的评论
		List<WccComment> wccComment =  WccComment.findWccCommentByContentId(id);
		if(null !=wccComment && wccComment.size() > 0 ){
			for (WccComment wccComm : wccComment) {
				wccComm.setIsDeleted(true);
				wccComm.merge();
			}
		}
		//删除微内容先要删除点赞
		List<WccPraise> wccPraise = WccPraise.findWccPraiseByContentId(0l, id);
		if(null != wccPraise && wccPraise.size() > 0){
			for (WccPraise wccPra : wccPraise) {
				wccPra.setIsDeleted(true);
				wccPra.merge();
			}
		}
		
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/wcccontents?page=1&amp;size=10";
	}
	
	@RequestMapping(value = "/deleteCommentByIds", method = RequestMethod.GET, produces = "text/html")
	public String deleteCommentByIds(
			@RequestParam(value = "valuestr", required = false) String valuestr,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		Long contentId = null;
		if(null != valuestr && !"".equals(valuestr)){
			List<Long> lists = CommonUtils.stringArray2List(valuestr);
			WccComment wccComment = null;
			WccContent wccContent = null;
			for (Long id : lists) {
				wccComment = WccComment.findWccComment(id);
				if(null != wccComment){
					contentId = wccComment.getContentId();
					wccContent = WccContent.findWccContent(contentId);
					wccContent.setCommentCount(wccContent.getCommentCount() - 1);
					wccContent.merge();
					wccComment.setIsDeleted(true);
					wccComment.merge();
				}
			}
		}
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/wcccontents/showContentByContentId?contentId="+contentId+"&amp;page=1&amp;size=10";
	}
	
	@RequestMapping(value = "/deleteCommentById", method = RequestMethod.GET, produces = "text/html")
	public String deleteCommentById(@RequestParam(value = "commentId", required = false) Long commentId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		Long contentId = null;
		WccContent wccContent = null;
		WccComment wccComment = WccComment.findWccComment(commentId);
		if(null != wccComment){
			contentId = wccComment.getContentId();
			wccContent = WccContent.findWccContent(wccComment.getContentId());
			if(null != wccContent){
				wccContent.setCommentCount(wccContent.getCommentCount() - 1);
				wccContent.merge();
				wccComment.setIsDeleted(true);
				wccComment.merge();
			}
		}
		
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/wcccontents/showContentByContentId?contentId="+contentId+"&amp;page=1&amp;size=10";
	}
	
	@SuppressWarnings("null")
	@RequestMapping(value = "/delCommByIds", method = RequestMethod.GET, produces = "text/html")
	public String delCommByIds(
			@RequestParam(value = "valuestr", required = false) String valuestr,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		if(null != valuestr && !"".equals(valuestr)){
			List<Long> lists = CommonUtils.stringArray2List(valuestr);
			WccComment wccComment = null;
			WccContent wccContent = null;
			for (Long id : lists) {
				wccComment = WccComment.findWccComment(id);
				if(null != wccComment){
					wccContent = WccContent.findWccContent(wccComment.getContentId());
					wccContent.setCommentCount(wccContent.getCommentCount() - 1);
					wccContent.merge();
					wccComment.setIsDeleted(true);
					wccComment.merge();
				}
			}
		}
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/wcccontents/showContent?page=1&amp;size=10";
	}
	
	@RequestMapping(value = "/delCommById", method = RequestMethod.GET, produces = "text/html")
	public String delCommById(@RequestParam(value = "commentId", required = false) Long commentId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		
		WccComment wccComment = WccComment.findWccComment(commentId);
		if(null != wccComment){
			WccContent wccContent = WccContent.findWccContent(wccComment.getContentId());
			wccContent.setCommentCount(wccContent.getCommentCount() - 1);
			wccContent.merge();
			
			wccComment.setIsDeleted(true);
			wccComment.merge();
		}
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/wcccontents/showContent?page=1&amp;size=10";
	}


	/**
	 * 
	 * @param id
	 * @param page
	 * @param uiModel
	 * @param size
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/showContent", method = RequestMethod.GET, produces = "text/html")
	public String showContent(Model uiModel,
			HttpServletRequest request,
			@RequestParam(value = "page", required = false) Integer page,
    		@RequestParam(value = "size", required = false) Integer size){
		/*List<WccComment> wccCommentList = new ArrayList<WccComment>();
		PubOperator pub = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> list = new ArrayList<WccPlatformUser>();
		if(pub.getPubRole() == null){
			list.addAll( WccPlatformUser.findAllWccPlatformUsers(pub));
		}else{
			PubOperator pubOper = PubOperator.findPubOperator(pub.getId());
			list.addAll(CommonUtils.set2List(pubOper.getPlatformUsers()));
		}
		StringBuilder sq = new StringBuilder();
		int i = 1;
		for (WccPlatformUser wccPlatformUser : list) {
			sq.append(wccPlatformUser.getId());
			if(i != list.size()){
				sq.append(",");
			}
			i++;
		}*/
		
		/*int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
		List<WccComment> wccComment =  WccComment.findWccCommentEntries(firstResult, sizeNo,sq.toString(),pub);
		if(wccComment.size() > 0){
			WccFriend wccF = null;
			for (WccComment wccC : wccComment) {
				wccF = WccFriend.findWccFriend(wccC.getFriendId());
				wccF.setNickName(URLDecoder.decode(wccF.getNickName(),"utf-8"));
				wccC.setWccFriend(wccF);
				wccCommentList.add(wccC);
			}
		}
		float nrOfPages = (float) WccComment.countWccCommentsByContentIds(sq.toString(),pub) / sizeNo;
		uiModel.addAttribute("maxPages",(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1: nrOfPages));
		uiModel.addAttribute("page", page);
		uiModel.addAttribute("size", size);
		uiModel.addAttribute("wccCommentList",wccCommentList);*/
		return "wcccontents/showContentList";
	}

	@RequestMapping(value = "/showContentByContentId", method = RequestMethod.GET, produces = "text/html")
	public String showContentByContentId(Model uiModel,
    		HttpServletRequest httpServletRequest,
    		@RequestParam(value = "contentId", required = false) Long contentId,
    		@RequestParam(value = "page", required = false) Integer page,
    		@RequestParam(value = "size", required = false) Integer size
    		){
			List<WccComment> wccCommentList = new ArrayList<WccComment>();
		
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)* sizeNo;
			List<WccComment> wccComment =  WccComment.findWccCommentByContentId2(firstResult, sizeNo,contentId,CommonUtils.getCurrentPubOperator(httpServletRequest));
			if(null !=wccComment && wccComment.size() > 0){
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
			float nrOfPages = (float) WccComment.countWccCommentsByContentId(contentId) / sizeNo;
			uiModel.addAttribute("maxPages",(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1: nrOfPages));
			uiModel.addAttribute("page", page);
			uiModel.addAttribute("size", size);
			uiModel.addAttribute("wccCommentList",wccCommentList);
			uiModel.addAttribute("contentId", contentId);
		    return "wcccontents/showContentByIdList";
	}

	/**
	 * 根据公司验证微内容标题是否存在
	 * 
	 * @param uiModel
	 * @param request
	 * @param title
	 * @return
	 */
	@RequestMapping(value = "/checkStatus", method = RequestMethod.POST, produces = "text/html")
	@ResponseBody
	public String checkStatus(Model uiModel, HttpServletRequest request,
			@RequestParam(value = "title", required = false) String title) {
		String msg = "0";
		List<WccContent> wccContents = WccContent.findWccContentByTitle(title,CommonUtils.getCurrentPubOperator(request));
		if (wccContents != null && wccContents.size() > 0) {
			msg = "1";
		}
		return msg;
	}

	void populateEditForm(Model uiModel, WccContent wccContent) {
		uiModel.addAttribute("wccContent", wccContent);
		addDateTimeFormatPatterns(uiModel);
	}

	String encodeUrlPathSegment(String pathSegment,
			HttpServletRequest httpServletRequest) {
		String enc = httpServletRequest.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"wccContent_inserttime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * 批量审核
	 * 
	 * @param valuestr
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkStatusByIds", method = RequestMethod.POST, produces = "text/html")
	@ResponseBody
	public String checkStatusByIds(
			Model uiModel,
			@RequestParam(value = "valuestr", required = false) String valuestr,
			HttpServletRequest request,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "isCheck", required = false) Integer isCheck) {
		List<Long> lists = CommonUtils.stringArray2List(valuestr);
		boolean flag = true;
		String msg = "0";
		for (Long id : lists) {
			WccContent wccContent = WccContent.findWccContent(id);
			if (wccContent.getIsCheck() == 0) {
				if (isCheck == 1) {
					wccContent.setIsCheck(1);
					try {
						wccContent.merge();
					} catch (Exception e) {
						flag = false;
					}
				} else if (isCheck == 2) {
					wccContent.setIsCheck(2);
					try {
						wccContent.merge();
					} catch (Exception e) {
						flag = false;
					}
				}
			}
		}

		if (flag) {
			msg = "1";
		} 
		return msg;
	}

	@RequestMapping(value = "/checkStatusById", method = RequestMethod.POST, produces = "text/html")
	@ResponseBody
	public String checkStatusById(Model uiModel,
			@RequestParam(value = "id", required = false) Long id,
			HttpServletRequest request,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "isCheck", required = false) Integer isCheck) {

		WccContent wccContent = WccContent.findWccContent(id);
		String msg = "1";
		if(null != wccContent){
			if (isCheck == 1) {
				wccContent.setIsCheck(1);
				try {
					wccContent.merge();
				} catch (Exception e) {
					msg = "0";
				}
			} else if (isCheck == 2) {
				wccContent.setIsCheck(2);
				try {
					wccContent.merge();
				} catch (Exception e) {
					msg = "0";
				}
			}
		}else{
			msg = "0";
		}
		
		return msg;
	}
	
	
	@RequestMapping(value = "/findContentsById", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String findContentsById(
			@RequestParam(value = "id", required = false) Long id,
			HttpServletRequest request){
		WccContent wccContent = WccContent.findWccContent(id);
		JSONObject json = JSONObject.fromObject(wccContent);
		return json.toString();
	}

	/**
	 * 删除微内容查询是否在菜单里引用
	 * @param id
	 * @param menuUrl
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findIdToWccMenu", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String findIdToWccMenu(
			@RequestParam(value = "menuUrl", required = false) String menuUrl,
			HttpServletRequest request){
		String str = "{msg:\'success\'}";
		List<WccMenu> wccMenuSize = WccMenu.findIdToWccMenu(menuUrl);
		if(null != wccMenuSize && wccMenuSize.size() > 0){
			str = "{msg:\'faile\'}";
		}
		return str;
	}
}

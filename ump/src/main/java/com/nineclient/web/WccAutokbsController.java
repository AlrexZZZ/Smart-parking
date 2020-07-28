package com.nineclient.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.nineclient.constant.WccAutokbsIsReview;
import com.nineclient.constant.WccAutokbsMatchWay;
import com.nineclient.constant.WccAutokbsReplyType;
import com.nineclient.constant.WccAutokbsShowWay;
import com.nineclient.constant.WccMaterialsType;
import com.nineclient.constant.WccWelcomkbsReplyType;
import com.nineclient.model.UmpAuthority;
import com.nineclient.model.WccAutokbs;
import com.nineclient.model.WccMaterials;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccTemplate;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@RequestMapping("/wccautokbses")
@Controller
@RooWebScaffold(path = "wccautokbses", formBackingObject = WccAutokbs.class)
public class WccAutokbsController {

	@RequestMapping(value = "create", method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid WccAutokbs wccAutokbs,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, wccAutokbs, request);
			return "wccautokbses/create";
		}
		// 如果类型是文本，且内容不为空，则为文本，如果类型不为文本，则调用素材
		if (wccAutokbs.getReplyType().equals(WccAutokbsReplyType.文本)
				&& wccAutokbs.getContent() != "") {
			wccAutokbs.setMaterials(null);
		} else if (!wccAutokbs.getReplyType().equals(WccAutokbsReplyType.文本)) {
			// 判断是会含有图片或图文
			wccAutokbs.setContent("");
			String materialStr = request.getParameter("selectId");
			String material = materialStr.substring(4, materialStr.length());
			WccMaterials mate = WccMaterials.findWccMaterials(Long
					.parseLong(material));
			wccAutokbs.setMaterials(mate);
		}
		if (wccAutokbs.getId() != null) {
			update(wccAutokbs, bindingResult, uiModel, request);
		} else {
			if (wccAutokbs.getReplyType().equals(WccAutokbsReplyType.文本)&& wccAutokbs.getContent() != ""){
				wccAutokbs.setContent(wccAutokbs.getContent().substring(0, wccAutokbs.getContent().length()-1));
			}
			wccAutokbs.setAuthor(CommonUtils.getCurrentPubOperator(request));
			wccAutokbs.setIsReview(WccAutokbsIsReview.待审核);
			wccAutokbs.setInsertTime(new Date());
			uiModel.asMap().clear();
			wccAutokbs.persist();
		}
		populateEditForm(uiModel, new WccAutokbs(), request);
		addDateTimeFormatPatterns(uiModel);
		return "redirect:/wccwelcomkbses?form&welType=3";
//		return "wccautokbses/showList";
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel,
			HttpServletRequest httpServletRequest) {
		populateEditForm(uiModel, new WccAutokbs(), httpServletRequest);
		return "wccautokbses/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("wccautokbs", WccAutokbs.findWccAutokbs(id));
		uiModel.addAttribute("itemId", id);
		return "wccautokbses/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel, HttpServletRequest request) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			uiModel.addAttribute("wccautokbses",
					WccAutokbs.findWccAutokbsEntries(firstResult, sizeNo,
							sortFieldName, sortOrder,
							CommonUtils.getCurrentCompanyId(request)));
			float nrOfPages = (float) WccAutokbs.countWccAutokbses() / sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("wccautokbses", WccAutokbs
					.findAllWccAutokbses(sortFieldName, sortOrder,
							CommonUtils.getCurrentCompanyId(request)));
		}
		addDateTimeFormatPatterns(uiModel);
		return "wccautokbses/list";
	}

	@RequestMapping(value = "showList", produces = "text/html")
	@SuppressWarnings("unchecked")
	public String showList(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			@RequestParam(value = "displayId", required = false) Long displayId,
			Model uiModel, HttpServletRequest request) {
		if (displayId == null) {
			displayId = CommonUtils.getCurrentDisPlayId(request);
		}
		request.getSession().setAttribute("displayId", displayId);
		
//		if(CommonUtils.getCurrentPubOperator(request).getPubRole() == null){
			request.getSession().setAttribute("audMenu",true);
//		}else{
			Map<String, String> auditMenu = (Map<String, String>) request.getSession().getAttribute("auditMenu");
			if(null != auditMenu ){
				UmpAuthority umpMenu = null;
				for (String menuId : auditMenu.keySet()) {
					umpMenu  = UmpAuthority.findUmpAuthority(Long.parseLong(menuId));
					if(null != umpMenu){
						request.getSession().setAttribute("audMenu",true);
					}
				}
//			}
		}
		populateEditForm(uiModel, new WccAutokbs(), request);
		addDateTimeFormatPatterns(uiModel);
		return "wccautokbses/showList";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public void update(@Valid WccAutokbs wccAutokbs,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, wccAutokbs, request);
		}
		WccAutokbs autokbs = WccAutokbs.findWccAutokbs(wccAutokbs.getId());
		autokbs.setPlatformUser(wccAutokbs.getPlatformUser());
		autokbs.setTitle(wccAutokbs.getTitle());
		autokbs.setKeyWord(wccAutokbs.getKeyWord());
		autokbs.setShowWay(wccAutokbs.getShowWay());
		autokbs.setMatchWay(wccAutokbs.getMatchWay());
		autokbs.setContent(wccAutokbs.getContent());
		autokbs.setReplyType(wccAutokbs.getReplyType());
		autokbs.setRelatedIssues(wccAutokbs.getRelatedIssues());
		autokbs.setActive(wccAutokbs.getActive());
		autokbs.setIsReview(WccAutokbsIsReview.待审核);
		autokbs.setMaterials(wccAutokbs.getMaterials());
		autokbs.setAuthor(CommonUtils.getCurrentPubOperator(request));
		if (!wccAutokbs.getReplyType().equals(WccAutokbsReplyType.文本)) {
			autokbs.setMaterials(wccAutokbs.getMaterials());
		}
		uiModel.asMap().clear();
		autokbs.merge();

	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel,
			HttpServletRequest request) {
		populateEditForm(uiModel, WccAutokbs.findWccAutokbs(id), request);
		return "wccautokbses/create";
	}

	@RequestMapping(value = "deleteAuto", produces = "text/html")
	@ResponseBody
	public String deleteAuto(@RequestParam("id") Long id,
			@RequestParam("type") Integer type, Model uiModel) {

		switch (type) {
		case 0:// 相关问题内不包含此问题 直接删除
			WccAutokbs wccAutokbs = WccAutokbs.findWccAutokbs(id);
			wccAutokbs.remove();
			uiModel.asMap().clear();
			return "2";
		case 1:// 相关问题包含此问题 先去除相关问题中的此问题
			WccAutokbs autokbs = WccAutokbs.findWccAutokbs(id);
			List<WccAutokbs> list = WccAutokbs
					.findWccAutokbsesByPlatform(autokbs.getPlatformUser()
							.getId());
			for (WccAutokbs auto : list) {
				if (auto.getId() != id) {// 判断不是当前问题
					if (auto.getRelatedIssues().length() > 0) {// 判断该自动回复是否存在相关问题
						String[] autoStr = auto.getRelatedIssues().split(",");
						String autoStrs = "";// 去除删除自动回复id后的字符串
						for (String au : autoStr) {
							if (Long.parseLong(au) != id) {// 如果当前自动回复不是选中id，则加到新字符串
								autoStrs += au + ",";
							}
						}
						if (autoStrs.length() > 0) {// 去掉最后的“，”
							autoStrs = autoStrs.substring(0,
									autoStrs.length() - 1);
						}
						if (!autoStrs.equals(auto.getRelatedIssues())) {
							auto.setRelatedIssues(autoStrs);
							auto.merge();
						}
					}
				}
			}
			autokbs.remove();
			uiModel.asMap().clear();
			return "2";
		case 2:// 判断是否存在
			WccAutokbs checkAutokbs = WccAutokbs.findWccAutokbs(id);
			List<WccAutokbs> checkList = WccAutokbs
					.findWccAutokbsesByPlatform(checkAutokbs.getPlatformUser()
							.getId());
			for (WccAutokbs auto : checkList) {
				if (auto.getId() != id) {
					if (auto.getRelatedIssues().length() > 0) {
						String[] autoStr = auto.getRelatedIssues().split(",");
						for (String au : autoStr) {
							if (Long.parseLong(au) == id) {
								return "1";// 存在相关问题
							}
						}
					}
				}
			}
			return "0";// 不存在相关问题
		}
		return "3";// 删除失败
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"wccAutokbs_inserttime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, WccAutokbs wccAutokbs,
			HttpServletRequest request) {
		uiModel.addAttribute("wccAutokbs", wccAutokbs);
		addDateTimeFormatPatterns(uiModel);
		List<WccPlatformUser> platformUsers = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
		uiModel.addAttribute("platformUsers", platformUsers);
		uiModel.addAttribute("unReview", WccAutokbsIsReview.待审核);
		uiModel.addAttribute("showWay", WccAutokbsShowWay.values());
		uiModel.addAttribute("DEFALT", WccAutokbsShowWay.默认回复);
		uiModel.addAttribute("ABOUT", WccAutokbsShowWay.相关回复);
		uiModel.addAttribute("matchWay", WccAutokbsMatchWay.values());
		uiModel.addAttribute("ALL", WccAutokbsMatchWay.全部匹配);
		uiModel.addAttribute("LIKE", WccAutokbsMatchWay.部分匹配);
		uiModel.addAttribute("isReview", WccAutokbsIsReview.values());
		uiModel.addAttribute("replyTypes", WccAutokbsReplyType.values());
		uiModel.addAttribute("TEXT", WccAutokbsReplyType.文本);
		uiModel.addAttribute("PIC", WccAutokbsReplyType.图片);
		uiModel.addAttribute("IMG", WccAutokbsReplyType.图文);
		uiModel.addAttribute("SOU", WccAutokbsReplyType.语音);
		uiModel.addAttribute("VID", WccAutokbsReplyType.视频);
		uiModel.addAttribute(
				"wccPicture",
				WccMaterials.findWccMaterialsesByType(
						WccMaterialsType.PICTURE.ordinal(),
						CommonUtils.getCurrentCompanyId(request)));
		uiModel.addAttribute("wccImageText", WccMaterials
				.findWccMaterialsesByType(WccMaterialsType.IMAGETEXT.ordinal(),
						CommonUtils.getCurrentCompanyId(request)));
		uiModel.addAttribute(
				"wccSounds",
				WccMaterials.findWccMaterialsesByType(
						WccMaterialsType.SOUNDS.ordinal(),
						CommonUtils.getCurrentCompanyId(request)));
		uiModel.addAttribute(
				"wccVideo",
				WccMaterials.findWccMaterialsesByType(
						WccMaterialsType.VIDEO.ordinal(),
						CommonUtils.getCurrentCompanyId(request)));
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

	@RequestMapping(value = "fuzzyQuery", produces = "text/html")
	public String fuzzyQuery(
			@RequestParam(value = "platformUser") String platformUser,
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "title") String title,
			@RequestParam(value = "keyWord") String keyWord,
			@RequestParam(value = "active") String active,
			@RequestParam(value = "isReview") String isReview,
			@RequestParam(value = "content") String content,
			@RequestParam(value = "showWay") String showWay,
			@RequestParam(value = "commentStartTimeId") String commentStartTimeId,
			@RequestParam(value = "commentEndTimeId") String commentEndTimeId,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit,
			Model uiModel, HttpServletRequest request) {
		WccAutokbs model = new WccAutokbs();
		QueryModel<WccAutokbs> qm = new QueryModel<WccAutokbs>(model, start,
				limit);
		List<WccPlatformUser> platList = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
		PageModel<WccAutokbs> pm = WccAutokbs.getQueryAutokbs(qm, platformUser,
				title, keyWord, active, isReview, content, showWay,
				commentStartTimeId, commentEndTimeId,
				CommonUtils.getCurrentCompanyId(request), platList);
		pm.setPageSize(limit);
		pm.setStartIndex(start);
		uiModel.addAttribute("maxPages", pm.getTotalPage());
		uiModel.addAttribute("page", pm.getPageNo());
		uiModel.addAttribute("limit", pm.getPageSize());
		uiModel.addAttribute("wccautokbses", pm.getDataList());
		addDateTimeFormatPatterns(uiModel);

		populateEditForm(uiModel, new WccAutokbs(), httpServletRequest);
		addDateTimeFormatPatterns(uiModel);
		System.out.println("redirect:/wccwelcomkbses/" + encodeUrlPathSegment("3", httpServletRequest));
//		return "redirect:/wccwelcomkbses/" + encodeUrlPathSegment("3", httpServletRequest);
		return "wccautokbses/list";
	}

	@RequestMapping(value = "reviews", produces = "text/html")
	@ResponseBody
	public String reviews(@RequestParam(value = "id") long id,
			@RequestParam(value = "isReview") int isReview) {
		WccAutokbs autokbs = WccAutokbs.findWccAutokbs(id);
		if (isReview == 1) {
			autokbs.setIsReview(WccAutokbsIsReview.审核通过);
			autokbs.merge();
			return "1";
		} else if (isReview == 2) {
			autokbs.setIsReview(WccAutokbsIsReview.审核未通过);
			autokbs.merge();
			return "1";
		}
		return "0";
	}

	@RequestMapping(value = "batchReviews", produces = "text/html")
	@ResponseBody
	public String batchReviews(@RequestParam(value = "arrStr") String arrStr,
			@RequestParam(value = "isReview") int isReview) {
		Set<Long> ls = CommonUtils.stringArray2Set(arrStr);
		boolean boo = true;
		for (long id : ls) {
			WccAutokbs autokbs = WccAutokbs.findWccAutokbs(id);
			if (isReview == 1) {
				if (autokbs.getIsReview().equals(WccAutokbsIsReview.待审核)) {
					autokbs.setIsReview(WccAutokbsIsReview.审核通过);
					try {
						autokbs.merge();
					} catch (Exception e) {
						boo = false;
					}
				}
			} else if (isReview == 2) {
				if (autokbs.getIsReview().equals(WccAutokbsIsReview.待审核)) {
					autokbs.setIsReview(WccAutokbsIsReview.审核未通过);
					try {
						autokbs.merge();
					} catch (Exception e) {
						boo = false;
					}
				}
			}
		}
		if (boo) {
			return "1";
		} else {
			return "0";
		}
	}

	/**
	 * 
	 * 创建/修改页面相关问题下拉复选
	 * 
	 * @param uiModel
	 * @param request
	 * @param response
	 * @param id
	 * @param selIds
	 * @param autoId
	 * @return
	 */
	@RequestMapping(value = "tree", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String tree(Model uiModel, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "selIds", required = false) String selIds,
			@RequestParam(value = "autoId", required = false) String autoId) {
		StringBuffer strs = new StringBuffer();
		int num = 0;
		List<WccPlatformUser> list = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
		if (list.size() > 0) {
			List<WccAutokbs> autokbs;
			// 平台id如果存在 则根据平台查询 如平台不存在 则查询默认第一个
			if (id == null) {
				autokbs = WccAutokbs.findWccAutokbsesByActiveAndIsReview(list
						.get(0).getId());
			} else {
				autokbs = WccAutokbs.findWccAutokbsesByActiveAndIsReview(id
						.longValue());
			}
			// 如果autoId不为空，则为修改 去除此自动回复
			if (autoId != null && !autoId.equals("")) {
				WccAutokbs au = WccAutokbs.findWccAutokbs(Long
						.parseLong(autoId));
				if (au.getPlatformUser().getId() == id.longValue()) {
					autokbs.remove(au);
				}
			}
			if (autokbs.size() > 0) {
				for (WccAutokbs auto : autokbs) {
					if (selIds != null && !selIds.equals("")) {
						boolean boo = false;
						String[] selIdStr = selIds.split(",");
						for (String sel : selIdStr) {
							// 选中id与自动回复id相同 返回true json中加checked:true默认选中，
							// 返回flase则不默认选中
							boo = sel.equals(auto.getId().toString());
							if (boo) {
								break;
							}
						}
						if (boo) {
							String str = "id:" + auto.getId() + ", pId:" + 0
									+ ",name:\"" + auto.getTitle() + "\""
									+ ",open:true,checked:true";
							if (num == 0) {
								strs.append("{" + str + "}");
							} else {
								strs.append(",{" + str + "}");
							}
							num++;
						} else {
							String str = "id:" + auto.getId() + ", pId:" + 0
									+ ",name:\"" + auto.getTitle() + "\""
									+ ",open:true";
							if (num == 0) {
								strs.append("{" + str + "}");
							} else {
								strs.append(",{" + str + "}");
							}
							num++;
						}
					} else {
						String str = "id:" + auto.getId() + ", pId:" + 0
								+ ",name:\"" + auto.getTitle() + "\""
								+ ",open:true";
						if (num == 0) {
							strs.append("{" + str + "}");
						} else {
							strs.append(",{" + str + "}");
						}
						num++;
					}
				}
			}
		}
		return strs.toString();
	}

	/**
	 * 
	 * 条件查询 平台复选树
	 * 
	 * @param uiModel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "treePlatform", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String treePlatform(Model uiModel, HttpServletRequest request,
			HttpServletResponse response) {
		StringBuffer strs = new StringBuffer();
		int num = 0;
		List<WccPlatformUser> list = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
		if (list.size() > 0) {
			for (WccPlatformUser plat : list) {
				String str = "id:" + plat.getId() + ", pId:" + 0 + ",name:\""
						+ plat.getAccount() + "\"" + ",open:true";
				if (num == 0) {
					strs.append("{" + str + "}");
				} else {
					strs.append(",{" + str + "}");
				}
				num++;
			}
		}
		return strs.toString();
	}

	//TODO0324
	@RequestMapping(value = "treeNewSearchPlatform", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String treeNewSearchPlatform(Model uiModel, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "inputText") String inputText) {
		StringBuffer strs = new StringBuffer();
		int num = 0;
		List<WccPlatformUser> list = WccPlatformUser
				.findAllWccPlatformUsersBySearchCondi(CommonUtils
						.getCurrentPubOperator(request),inputText);
		if (list.size() > 0) {
			for (WccPlatformUser plat : list) {
				String str = "id:" + plat.getId() + ", pId:" + 0 + ",name:\""
						+ plat.getAccount() + "\"" + ",open:true";
				if (num == 0) {
					strs.append("{" + str + "}");
				} else {
					strs.append(",{" + str + "}");
				}
				num++;
			}
		}
		return strs.toString();
	}
	
	
	@RequestMapping(value = "materialsList", produces = "text/html")
	public String materialsList(HttpServletRequest request, Model uiModel,
			@RequestParam(value = "platformUserId") Long platformUserId,
			@RequestParam(value = "id") Long autoId) {
		if(autoId!=null){
			uiModel.addAttribute("wccAutokbs", WccAutokbs.findWccAutokbs(autoId));
		}
		if(platformUserId!=null){
			uiModel.addAttribute("TEXT", WccAutokbsReplyType.文本);
			uiModel.addAttribute("PIC", WccAutokbsReplyType.图片);
			uiModel.addAttribute("IMG", WccAutokbsReplyType.图文);
			uiModel.addAttribute("SOU", WccAutokbsReplyType.语音);
			uiModel.addAttribute("VID", WccAutokbsReplyType.视频);
			uiModel.addAttribute(
					"wccPicture",
					WccMaterials.findWccMaterialsesByType(
							WccMaterialsType.PICTURE.ordinal(),
							platformUserId));
			uiModel.addAttribute("wccImageText", WccMaterials
					.findWccMaterialsesByType(WccMaterialsType.IMAGETEXT.ordinal(),
							platformUserId));
			uiModel.addAttribute(
					"wccSounds",
					WccMaterials.findWccMaterialsesByType(
							WccMaterialsType.SOUNDS.ordinal(),
							platformUserId));
			uiModel.addAttribute(
					"wccVideo",
					WccMaterials.findWccMaterialsesByType(
							WccMaterialsType.VIDEO.ordinal(),
							platformUserId));
		}
		return "wccautokbses/show";
	}
	@RequestMapping(value = "checkTitle", produces = "text/html")
	@ResponseBody
	public String checkTitle(@RequestParam(value = "title") String title,@RequestParam(value = "platform") String platform,@RequestParam(value = "id") Long id) {
		
		if (title != null &&!"".equals(title)) {
			if (WccAutokbs.findWccAutokbsByTitle(title,platform,id).size() > 0) {
				return "0";
			} else {
				return "1";
			}
		}
		return "";
	}
}

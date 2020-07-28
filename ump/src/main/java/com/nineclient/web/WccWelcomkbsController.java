package com.nineclient.web;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.nineclient.constant.WccMaterialsType;
import com.nineclient.constant.WccWelcomkbsReplyType;
import com.nineclient.constant.WccWelcomkbsType;
import com.nineclient.model.UmpAuthority;
import com.nineclient.model.WccAutokbs;
import com.nineclient.model.WccMaterials;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccWelcomkbs;
import com.nineclient.utils.CommonUtils;

@RequestMapping("/wccwelcomkbses")
@Controller
@RooWebScaffold(path = "wccwelcomkbses", formBackingObject = WccWelcomkbs.class)
public class WccWelcomkbsController {

	@RequestMapping(value = "create", method = RequestMethod.POST, produces = "text/html")
	@ResponseBody
	public String create(@Valid WccWelcomkbs wccWelcomkbs,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest request) {
 		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, wccWelcomkbs,null);
			return "wccwelcomkbses/create";
		}
		uiModel.asMap().clear();
		List<WccWelcomkbs> welcomkbss = WccWelcomkbs
				.findWccWelcomkbsesByPlatformUserAndType(wccWelcomkbs
						.getPlatformUser().getId(), wccWelcomkbs.getType()
						.ordinal());
		if (wccWelcomkbs.getReplyType().equals(WccWelcomkbsReplyType.文本)
				&& wccWelcomkbs.getContent() != "") {
			wccWelcomkbs.setMaterials(null);
		} else if (!wccWelcomkbs.getReplyType()
				.equals(WccWelcomkbsReplyType.文本)) {
			// 判断是会含有图片或图文
			wccWelcomkbs.setContent("");
			String materialStr = request.getParameter("selectId");
			String material = materialStr.substring(5, materialStr.length());
			WccMaterials mate = WccMaterials.findWccMaterials(Long
					.parseLong(material));
			wccWelcomkbs.setMaterials(mate);
		}
		if (welcomkbss.size() == 1) {
			WccWelcomkbs welcomkbs = welcomkbss.get(0);
			welcomkbs.setActive(wccWelcomkbs.getActive());
			welcomkbs.setContent(wccWelcomkbs.getContent());
			welcomkbs.setReplyType(wccWelcomkbs.getReplyType());
			welcomkbs.setIsCustomer(wccWelcomkbs.getIsCustomer());
			if (!wccWelcomkbs.getReplyType().equals(WccWelcomkbsReplyType.文本)) {
				welcomkbs.setMaterials(wccWelcomkbs.getMaterials());
			}else{
				welcomkbs.setMaterials(null);
			}
			welcomkbs.merge();
			return "{\"state\":\"0\"}";
		} else {
			wccWelcomkbs.persist();
			return "{\"state\":\"1\"}";
		}
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel,
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "welType", required = false) Long welType) {
		List<WccPlatformUser> platformUsers = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(httpServletRequest));
		if (platformUsers.size() > 0) {
			uiModel.addAttribute("platformUsers", platformUsers);
			WccPlatformUser platformUser = platformUsers.get(0);
			List<WccWelcomkbs> welR = WccWelcomkbs
					.findWccWelcomkbsesByPlatformUserAndType(
							platformUser.getId(),
							WccWelcomkbsType.ROBOT.ordinal());
			List<WccWelcomkbs> welW = WccWelcomkbs
					.findWccWelcomkbsesByPlatformUserAndType(
							platformUser.getId(),
							WccWelcomkbsType.WELCOME.ordinal());
			if (welR.size() > 0) {
				uiModel.addAttribute("robotWelcom", welR.get(0));
			}
			if (welW.size() > 0) {
				uiModel.addAttribute("welcomeWelcom", welW.get(0));
			}
			if(welType==null){
				uiModel.addAttribute("welType", 0);
			}else{
				uiModel.addAttribute("welType", welType);
			}
//			if(CommonUtils.getCurrentPubOperator(httpServletRequest).getPubRole() == null){
				httpServletRequest.getSession().setAttribute("audMenu",true);
//			}else{
				Map<String, String> auditMenu = (Map<String, String>) httpServletRequest.getSession().getAttribute("auditMenu");
				if(null != auditMenu ){
					UmpAuthority umpMenu = null;
					for (String menuId : auditMenu.keySet()) {
						umpMenu  = UmpAuthority.findUmpAuthority(Long.parseLong(menuId));
						if(null != umpMenu){
							httpServletRequest.getSession().setAttribute("audMenu",true);
						}
					}
//				}
			}
		}
		uiModel.addAttribute("wccAutokbs", new WccAutokbs());
		populateEditForm(uiModel, new WccWelcomkbs(),null);
		return "wccwelcomkbses/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("wccwelcomkbs", WccWelcomkbs.findWccWelcomkbs(id));
		uiModel.addAttribute("itemId", id);
		return "wccwelcomkbses/show";
	}

	@RequestMapping(value = "materialsList", produces = "text/html")
	public String materialsList(HttpServletRequest request,Model uiModel,@RequestParam(value = "platformUserId", required = false) Long platformUserId) {
		if(!"".equals(platformUserId)&&platformUserId!=null){
			WccPlatformUser platformUser = WccPlatformUser.findWccPlatformUser(platformUserId);
			List<WccWelcomkbs> welR = WccWelcomkbs
					.findWccWelcomkbsesByPlatformUserAndType(
							platformUser.getId(),
							WccWelcomkbsType.ROBOT.ordinal());
			List<WccWelcomkbs> welW = WccWelcomkbs
					.findWccWelcomkbsesByPlatformUserAndType(
							platformUser.getId(),
							WccWelcomkbsType.WELCOME.ordinal());
			if (welR.size() > 0) {
				uiModel.addAttribute("robotWelcom", welR.get(0));
			}
			if (welW.size() > 0) {
				uiModel.addAttribute("welcomeWelcom", welW.get(0));
			}
			populateEditForm(uiModel,  new WccWelcomkbs(),platformUserId);
		}else{
			uiModel.addAttribute("robotWelcom", new WccWelcomkbs());
			uiModel.addAttribute("welcomeWelcom", new WccWelcomkbs());
			populateEditForm(uiModel,  new WccWelcomkbs(),null);
		}
		addDateTimeFormatPatterns(uiModel);
		return "wccwelcomkbses/list";
	}
	@RequestMapping(value = "materialsList2", produces = "text/html")
	public String materialsList2(HttpServletRequest request,Model uiModel,@RequestParam(value = "platformUserId", required = false) Long platformUserId) {
		if(!"".equals(platformUserId)&&platformUserId!=null){
			WccPlatformUser platformUser = WccPlatformUser.findWccPlatformUser(platformUserId);
			List<WccWelcomkbs> welR = WccWelcomkbs
					.findWccWelcomkbsesByPlatformUserAndType(
							platformUser.getId(),
							WccWelcomkbsType.ROBOT.ordinal());
			List<WccWelcomkbs> welW = WccWelcomkbs
					.findWccWelcomkbsesByPlatformUserAndType(
							platformUser.getId(),
							WccWelcomkbsType.WELCOME.ordinal());
			if (welR.size() > 0) {
				uiModel.addAttribute("robotWelcom", welR.get(0));
			}
			if (welW.size() > 0) {
				uiModel.addAttribute("welcomeWelcom", welW.get(0));
			}
			populateEditForm(uiModel,  new WccWelcomkbs(),platformUserId);
		}else{
			uiModel.addAttribute("robotWelcom", new WccWelcomkbs());
			uiModel.addAttribute("welcomeWelcom", new WccWelcomkbs());
			populateEditForm(uiModel,  new WccWelcomkbs(),null);
		}
		addDateTimeFormatPatterns(uiModel);
		return "wccwelcomkbses/list2";
	}

	private void populateEditForm(Model uiModel, WccWelcomkbs wccWelcomkbs, Long platformUserId) {
		uiModel.addAttribute("wccWelcomkbs", wccWelcomkbs);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("wccwelcomkbsreplytypes",
				Arrays.asList(WccWelcomkbsReplyType.values()));
		uiModel.addAttribute("wccwelcomkbstypes",
				Arrays.asList(WccWelcomkbsType.values()));
		if(platformUserId!=null){
			
		uiModel.addAttribute("TEXT", WccWelcomkbsReplyType.文本);
		uiModel.addAttribute("PIC", WccWelcomkbsReplyType.图片);
		uiModel.addAttribute("IMG", WccWelcomkbsReplyType.图文);
		uiModel.addAttribute("SOU", WccWelcomkbsReplyType.语音);
		uiModel.addAttribute("VID", WccWelcomkbsReplyType.视频);
		uiModel.addAttribute("wccPicture", WccMaterials
				.findWccMaterialsesByType(WccMaterialsType.PICTURE.ordinal(),platformUserId));
		uiModel.addAttribute("wccImageText", WccMaterials
				.findWccMaterialsesByType(WccMaterialsType.IMAGETEXT.ordinal(),platformUserId));
		uiModel.addAttribute("wccSounds", WccMaterials
				.findWccMaterialsesByType(WccMaterialsType.SOUNDS.ordinal(),platformUserId));
		uiModel.addAttribute("wccVideo", WccMaterials
				.findWccMaterialsesByType(WccMaterialsType.VIDEO.ordinal(),platformUserId));
		}
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid WccWelcomkbs wccWelcomkbs,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, wccWelcomkbs,null);
			return "wccwelcomkbses/update";
		}
		uiModel.asMap().clear();
		wccWelcomkbs.merge();
		return "redirect:/wccwelcomkbses/"
				+ encodeUrlPathSegment(wccWelcomkbs.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel,HttpServletRequest httpServletRequest) {
		populateEditForm(uiModel, WccWelcomkbs.findWccWelcomkbs(id),null);
		return "wccwelcomkbses/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		WccWelcomkbs wccWelcomkbs = WccWelcomkbs.findWccWelcomkbs(id);
		wccWelcomkbs.remove();
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/wccwelcomkbses";
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		
		uiModel.addAttribute(
				"wccWelcomkbs_inserttime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
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

	@RequestMapping(value = "changePlatformUser", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String changePlatformUser(
			@RequestParam(value = "platformUser") Long platformUser,
			@RequestParam(value = "type") int type,
			HttpServletRequest httpServletRequest) {
		List<WccWelcomkbs> welcomkbss = WccWelcomkbs
				.findWccWelcomkbsesByPlatformUserAndType(platformUser, type);
		if (welcomkbss.size() > 0) {
			StringBuffer data = new StringBuffer();
			data.append("\"active\":\"" + welcomkbss.get(0).getActive() + "\",");
			data.append("\"replyType\":\"" + welcomkbss.get(0).getReplyType()+ "\",");
			if(type==0){
				data.append("\"isCustomer\":\"" + welcomkbss.get(0).getIsCustomer()+ "\",");
			}
			if (!welcomkbss.get(0).getReplyType()
					.equals(WccWelcomkbsReplyType.文本)) {
				data.append("\"materials\":\""
						+ welcomkbss.get(0).getMaterials().getId() + "\",");
			}
			data.append("\"content\":\"" + welcomkbss.get(0).getContent()
					+ "\"");
			return "{" + data.toString() + "}";
		} else {
			return "0";
		}

	}
}

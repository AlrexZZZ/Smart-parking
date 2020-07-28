package com.nineclient.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpGroup;

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

import com.nineclient.model.WccFriend;
import com.nineclient.model.WccGroup;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.CommonUtils;

@RequestMapping("/wccgroups")
@Controller
@RooWebScaffold(path = "wccgroups", formBackingObject = WccGroup.class)
public class WccGroupController {

	@RequestMapping(value = "submit", method = RequestMethod.POST, produces = "text/html")
	public String createGroup(@Valid WccGroup wccGroup,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest request) throws WxErrorException,
			UnsupportedEncodingException {
		uiModel.asMap().clear();
		String gid = request.getParameter("gidn");
		WccPlatformUser platformUser = new WccPlatformUser();

		long id = Long.parseLong((String) request.getParameter("platformId"));// 获取公众平台ID
		platformUser = WccPlatformUser.findWccPlatformUser(id);// 根据公众平台ID查询公众平台
		// 调用微信接口
		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		String appId = platformUser.getAppId();
		String appSecret = platformUser.getAppSecret();
		config.setAppId(appId);
		config.setSecret(appSecret);
		WxMpServiceImpl wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(config);

		if (null != gid && !"".equals(gid)) {
			WccGroup wGroup = WccGroup.findWccGroup(Long.parseLong(gid));
			wGroup.setName(wccGroup.getName());
			wGroup.merge();
			// List<WxMpGroup> groups = wxMpService.groupGet();
			// for(WxMpGroup wmg:groups){
			// if(wmg.getId()==wGroup.getGroupId()){
			// wmg.setName(wccGroup.getName());
			// wxMpService.groupUpdate(wmg);
			// }
			// }
			WxMpGroup group = new WxMpGroup();
			group.setId(wGroup.getGroupId());
			group.setName(wGroup.getName());
			wxMpService.groupUpdate(group);
			return "redirect:/wccgroups/show";
		}
		WxMpGroup res = wxMpService.groupCreate(wccGroup.getName());
		wccGroup.setGroupId(res.getId());
		Date date = new Date();
		wccGroup.setInsertTime(date);
		// wccGroup.setVersion(Integer.parseInt(time+""));
		wccGroup.setPlatformUser(platformUser);
		wccGroup.setIsDelete(true);
		wccGroup.persist();// 添加分组
		// 创建分组
		return "redirect:/wccgroups/show";
	}

	@RequestMapping(produces = "text/html", value = "show")
	public String lists(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			@RequestParam(value = "displayId", required = false) Long displayId,
			Model uiModel, HttpServletRequest request) {
		/*
		 * List<WccGroup> groups = WccGroup.findAllWccGroups(sortFieldName,
		 * sortOrder); uiModel.addAttribute("wccgroups", groups);
		 */
		List<WccPlatformUser> platformUsers = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
		uiModel.addAttribute("wccplatformusers", platformUsers);

		addDateTimeFormatPatterns(uiModel);
		return "wccgroups/lists";
	}

	@RequestMapping(value = "delete", produces = "text/html")
	public String delete(@RequestParam(value = "gid") String gid,
			Model uiModel, HttpServletRequest request) {
		uiModel.asMap().clear();
		if (null != gid && !"".equals(gid)) {
			List<WccFriend> friends = WccFriend.findWccFriendByGroup(Long
					.parseLong(gid));
			if (friends.size() > 0) {
				WccGroup wGroup = WccGroup.findWccGroup(Long.parseLong(gid));
				WccPlatformUser platformUser = wGroup.getPlatformUser();
				long l = 0;
				// 获取默认分组
				WccGroup defaultGroup = WccGroup.findWccGroupByGroupId(platformUser.getId(),l
						);
				// 调用微信接口
				WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
				String appId = platformUser.getAppId();
				String appSecret = platformUser.getAppSecret();
				config.setAppId(appId);
				config.setSecret(appSecret);
				WxMpServiceImpl wxMpService = new WxMpServiceImpl();
				wxMpService.setWxMpConfigStorage(config);
				// 删除分组下有粉丝的分组，首先要将粉丝移入默认分组，然后再进行删除
				for (WccFriend friend : friends) {
					try {
						wxMpService.userUpdateGroup(friend.getOpenId(), 0);
					} catch (WxErrorException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					friend.setWgroup(defaultGroup);
					friend.merge();
				}
				wGroup.remove();
			} else {
				WccGroup wGroup = WccGroup.findWccGroup(Long.parseLong(gid));
				wGroup.remove();
			}
		}
		return "redirect:/wccgroups/show";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid WccGroup wccGroup, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, wccGroup, httpServletRequest);
			return "wccgroups/create";
		}
		uiModel.asMap().clear();
		wccGroup.persist();
		return "redirect:/wccgroups/"
				+ encodeUrlPathSegment(wccGroup.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel,
			HttpServletRequest httpServletRequest) {
		populateEditForm(uiModel, new WccGroup(), httpServletRequest);
		return "wccgroups/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("wccgroup", WccGroup.findWccGroup(id));
		uiModel.addAttribute("itemId", id);
		return "wccgroups/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			uiModel.addAttribute("wccgroups", WccGroup.findWccGroupEntries(
					firstResult, sizeNo, sortFieldName, sortOrder));
			float nrOfPages = (float) WccGroup.countWccGroups() / sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("wccgroups",
					WccGroup.findAllWccGroups(sortFieldName, sortOrder));
		}
		addDateTimeFormatPatterns(uiModel);
		return "wccgroups/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid WccGroup wccGroup, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, wccGroup, httpServletRequest);
			return "wccgroups/update";
		}
		uiModel.asMap().clear();
		wccGroup.merge();
		return "redirect:/wccgroups/"
				+ encodeUrlPathSegment(wccGroup.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel,
			HttpServletRequest httpServletRequest) {
		populateEditForm(uiModel, WccGroup.findWccGroup(id), httpServletRequest);
		return "wccgroups/update";
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"wccGroup_inserttime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, WccGroup wccGroup,
			HttpServletRequest httpServletRequest) {
		uiModel.addAttribute("wccGroup", wccGroup);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("wccplatformusers", WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(httpServletRequest)));
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

	@RequestMapping(value = "checkGName")
	@ResponseBody
	public String checkGName(@RequestParam(value = "name") String name,
			@RequestParam(value = "pid") long pid) {
		WccPlatformUser pfu = WccPlatformUser.findWccPlatformUser(pid);
		for (WccGroup group : pfu.getGroups()) {
			if (group.getName().equals(name)) {
				return "1";
			}
		}
		return "0";
	}

	@RequestMapping(value = "checkChildren")
	@ResponseBody
	public String checkChildren(@RequestParam(value = "gid") long gid) {
		List<WccFriend> friends = WccFriend.findWccFriendByGroup(gid);
		if (friends.size() > 0) {
			return "1";
		}
		return "0";
	}
}

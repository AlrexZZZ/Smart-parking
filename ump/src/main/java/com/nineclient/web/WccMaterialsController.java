package com.nineclient.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.nineclient.constant.WccMaterialsType;
import com.nineclient.dto.Platfrom;
import com.nineclient.model.PubOperator;
import com.nineclient.model.WccAutokbs;
import com.nineclient.model.WccMassMessage;
import com.nineclient.model.WccMaterials;
import com.nineclient.model.WccMenu;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccQrcode;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.model.WccWelcomkbs;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Global;
import com.nineclient.utils.Qrcode;
import com.nineclient.utils.WeiXinPubReturnCode;

@RequestMapping("/wccmaterialses")
@Controller
@RooWebScaffold(path = "wccmaterialses", formBackingObject = WccMaterials.class)
public class WccMaterialsController {

	@ModelAttribute
	public void populateModel(Model uiModel, HttpServletRequest request) {
		List<WccPlatformUser> list = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
		uiModel.addAttribute("plats", list);
	}

	@RequestMapping(value = "/platforms")
	@ResponseBody
	public String platforms(HttpServletRequest request,
			HttpServletResponse response) {
		List<WccPlatformUser> list = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
		List<Platfrom> plats = new ArrayList<Platfrom>();

		for (WccPlatformUser wccPlatformUser : list) {
			Platfrom p = new Platfrom();
			p.setId(wccPlatformUser.getId().toString());
			p.setText(wccPlatformUser.getAccount());
			p.setValue(wccPlatformUser.getAccount());
			p.setChecked(true);
			p.setEnable(true);
			plats.add(p);
		}

		return JSONArray.fromObject(plats).toString();
	}

	@RequestMapping(value = "/news")
	public String news() {
		return "wccmaterialses/news";
	}

	@RequestMapping(value = "/wxarticlelist", produces = "text/html", params = "WccMaterialsType")
	public String wxArticleList(
			HttpServletRequest request,
			@RequestParam(value = "WccMaterialsType") WccMaterialsType materialsType,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			@RequestParam(value = "SelIds", required = false) String SelIds,
			@RequestParam(value = "wxErrorMsg", required = false) String wxErrorMsg,
			Model uiModel) {
		String url = "wccmaterialses/wx_material_imagetext_list";
		if (materialsType.equals(WccMaterialsType.IMAGETEXT)) {
			url = "wccmaterialses/wx_material_imagetext_list";
		} else if (materialsType.equals(WccMaterialsType.VIDEO)) {
			url = "wccmaterialses/wx_material_video_list";
		} else if (materialsType.equals(WccMaterialsType.SOUNDS)) {
			url = "wccmaterialses/wx_material_sound_list";
		} else if (materialsType.equals(WccMaterialsType.PICTURE)) {
			url = "wccmaterialses/wx_material_pic_list";
		}

		// uiModel.addAttribute("plats", list);

		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1)
				* sizeNo;
		List<WccPlatformUser> platformUsers = new ArrayList<WccPlatformUser>();
		if (!"".equals(SelIds) && SelIds != null) {
			String[] SelIdStr = SelIds.split(",");
			String selVal = "";
			for (String selId : SelIdStr) {
				WccPlatformUser platformUser = WccPlatformUser
						.findWccPlatformUser(Long.parseLong(selId));
				selVal += platformUser.getAccount() + ",";
				platformUsers.add(platformUser);
			}
			uiModel.addAttribute("selVal",
					selVal.substring(0, selVal.length() - 1));
			uiModel.addAttribute("SelIds", SelIds);
		} else {
			platformUsers = WccPlatformUser.findAllWccPlatformUsers(CommonUtils
					.getCurrentPubOperator(request));
		}
		if (platformUsers.size() > 0) {
			uiModel.addAttribute("wccmaterialses", WccMaterials
					.findWccMaterialsEntriesByMaterialsType(firstResult,
							sizeNo, sortFieldName, sortOrder, materialsType,
							platformUsers));
		}

		float nrOfPages = (float) WccMaterials.countWccMaterialses() / sizeNo;
		uiModel.addAttribute(
				"maxPages",
				(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
						: nrOfPages));
		String wxCtxPath = Global.IMAGE_QRCOED_FULL_PATH
				+ Global.IMAGE_QRCOED_PATH;
		uiModel.addAttribute("wxCtxPath", wxCtxPath);
		if (wxErrorMsg != "") {
			uiModel.addAttribute("wxErrorMsg", wxErrorMsg);
		}
		return url;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid WccMaterials wccMaterials,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, wccMaterials);
			return "wccmaterialses/create";
		}
		uiModel.asMap().clear();
		wccMaterials.setCompanyId(CommonUtils
				.getCurrentCompanyId(httpServletRequest));
		wccMaterials.persist();
		return "redirect:/wccmaterialses/"
				+ encodeUrlPathSegment(wccMaterials.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new WccMaterials());
		return "wccmaterialses/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("wccmaterials", WccMaterials.findWccMaterials(id));
		uiModel.addAttribute("itemId", id);
		return "wccmaterialses/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "WccMaterialsType", required = false) WccMaterialsType materialsType,
			@RequestParam(value = "type", required = false) WccMaterialsType type,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {
		if (type != null) {
			materialsType = type;
		}
		String url = "wccmaterialses/wx_material_imagetext_list";
		if (materialsType != null) {
			if (materialsType.equals(WccMaterialsType.IMAGETEXT)) {
				url = "wccmaterialses/wx_material_imagetext_list";
			} else if (materialsType.equals(WccMaterialsType.VIDEO)) {
				url = "wccmaterialses/wx_material_video_list";
			} else if (materialsType.equals(WccMaterialsType.SOUNDS)) {
				url = "wccmaterialses/wx_material_sound_list";
			} else if (materialsType.equals(WccMaterialsType.PICTURE)) {
				url = "wccmaterialses/wx_material_pic_list";
			}
		}

		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1)
				* sizeNo;
		List<WccPlatformUser> platformUsers = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(httpServletRequest));
		if (platformUsers.size() > 0) {
			uiModel.addAttribute("wccmaterialses", WccMaterials
					.findWccMaterialsEntriesByMaterialsType(firstResult,
							sizeNo, sortFieldName, sortOrder, materialsType,
							platformUsers));
		}
		float nrOfPages = (float) WccMaterials.countWccMaterialses() / sizeNo;
		uiModel.addAttribute(
				"maxPages",
				(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
						: nrOfPages));
		// if(materialsType!=null){
		// uiModel.addAttribute("type", materialsType);
		// }
		addDateTimeFormatPatterns(uiModel);
		return url;
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid WccMaterials wccMaterials,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, wccMaterials);
			return "wccmaterialses/update";
		}
		uiModel.asMap().clear();
		wccMaterials.merge();
		return "redirect:/wccmaterialses/wxarticlelist?WccMaterialsType=PICTURE&sortFieldName=insertTime&sortOrder=DESC";
	}

	@RequestMapping(value = "/ajaxupload")
	@ResponseBody
	public void ajaxUpload(@RequestParam String url,
			@RequestParam(value = "title", required = false) String  title,
			@RequestParam(value = "muti", required = false) String muti,
			@RequestParam(value = "plat", required = false) String plat,
			Model uiModel, HttpServletRequest httpServletRequest) {
		String [] str=plat.split(",");
		String title2="";
		try {
			title2 = new String(title.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(str !=null && str.length>0){
		for(String stri:str){
		WccMaterials materials = new WccMaterials();
		if (plat != null) {
			materials.setWccPlatformUsers(WccPlatformUser
					.findWccPlatformUser(Long.parseLong(stri)));
		}
	
		title = title2.substring(0, title2.length() - 4);
		materials.setCompanyId(CommonUtils
				.getCurrentCompanyId(httpServletRequest));
		// List<WccPlatformUser> platformUsers = (List<WccPlatformUser>)
		// uiModel.asMap().get("plats");
		// materials.setWccPlatformUsers(new
		// HashSet<WccPlatformUser>(platformUsers));
		materials.setMaterialType(".jpg");
		materials.setType(WccMaterialsType.PICTURE);
		materials.setTitle(title);
		materials.setInsertTime(new Date());
		materials.setThumbnailUrl(url);
		materials.setCompanyId(CommonUtils
				.getCurrentCompanyId(httpServletRequest));
		materials.persist();
			}
		}
	}

	@RequestMapping(value = "/ajaxupload_sound")
	public String ajaxUploadSound(@RequestParam String url,
			@RequestParam(value = "plat", required = false) String plat,
			@RequestParam String title, @RequestParam WccMaterialsType type,
			HttpServletRequest request, Model uiModel)
			throws UnsupportedEncodingException {
		String[] str=plat.split(",");
		String title2="";
		try {
			title2 = new String(title.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(str!=null && str.length>0){
		for(String stri:str){
		request.setCharacterEncoding("UTF-8");
		WccMaterials materials = new WccMaterials();
		if (stri != null) {
			materials.setWccPlatformUsers(WccPlatformUser
					.findWccPlatformUser(Long.parseLong(stri)));
		}
	
		if (title.endsWith(".amr")) {
			materials.setMaterialType(".amr");
		} else {
			materials.setMaterialType(".mp3");
		}
		title = title2.substring(0, title2.length() - 4);

		// materials.setType(WccMaterialsType.PICTURE);
		// List<WccPlatformUser> platformUsers = (List<WccPlatformUser>)
		// uiModel.asMap().get("plats");
		// materials.setWccPlatformUsers(new
		// HashSet<WccPlatformUser>(platformUsers));
		// String title_zh = new String(title.getBytes("iso8859-1"),"utf-8");
		materials.setType(type);
		materials.setTitle(title);
		materials.setThumbnailUrl(url);
		materials.setCompanyId(CommonUtils.getCurrentCompanyId(request));
		materials.setInsertTime(new Date());
		materials.persist();
		}
		}
		return "redirect:/wccmaterialses/wxarticlelist?WccMaterialsType=PICTURE&sortFieldName=insertTime&sortOrder=DESC";
	}

	@RequestMapping(value = "/ajaxupload_video")
	public String ajaxUploadVideo(@RequestParam String url,
			@RequestParam String title, HttpServletRequest request,
			Model uiModel) {
		WccMaterials materials = new WccMaterials();
		// materials.setType(WccMaterialsType.PICTURE);
		// materials.setWccPlatformUsers(wccPlatformUsers);
		materials.setCompanyId(CommonUtils.getCurrentCompanyId(request));
		// List<WccPlatformUser> platformUsers = (List<WccPlatformUser>)
		// uiModel.asMap().get("plats");
		// materials.setWccPlatformUsers(new
		// HashSet<WccPlatformUser>(platformUsers));
		try {
			title = new String(title.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		materials.setMaterialType(".mp4");
		materials.setType(WccMaterialsType.VIDEO);
		materials.setTitle(title);
		materials.setThumbnailUrl(url);
		materials.setCompanyId(CommonUtils.getCurrentCompanyId(request));
		materials.setInsertTime(new Date());
		materials.persist();
		return "redirect:/wccmaterialses/wxarticlelist?WccMaterialsType=PICTURE&sortFieldName=insertTime&sortOrder=DESC";
	}

	@RequestMapping(value = "/ajaxUpdateTitle", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String ajaxUpdateTitle(@RequestParam Long id,
			@RequestParam String title) {
		try {
			title = new String(title.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WccMaterials wc = WccMaterials.findWccMaterials(id);
		wc.setVersion(wc.getVersion());
		wc.setTitle(title);
		wc.merge();
		return null;
	}

	@RequestMapping(value = "/ajaxdelete")
	@ResponseBody
	public String ajaxDelete(@RequestParam Long id) {
		WccMaterials wc = WccMaterials.findWccMaterials(id);
		wc.remove();
		return null;
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, WccMaterials.findWccMaterials(id));
		return "wccmaterialses/update";
	}

	@RequestMapping(value = "/updateone/{id}")
	public String updateFormOne(@PathVariable("id") Long id, Model uiModel) {
		WccMaterials materials = WccMaterials.findWccMaterials(id);
		if (materials.getChildren().size() > 0) {
			uiModel.addAttribute("wccMaterials", materials);
			uiModel.addAttribute("cloumn", materials.getChildren().size() + 1);
			return "wccmaterialses/editormulti";
		} else {
			uiModel.addAttribute("wccMaterials", materials);
			uiModel.addAttribute("cloumn", 1);
			return "wccmaterialses/editor";
		}
	}

	@RequestMapping(value = "/updatemulti/{id}")
	public String updateFormMutil(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, WccMaterials.findWccMaterials(id));
		return "wccmaterialses/editormulti";
	}

	@RequestMapping(value = "/updatmulti")
	public String updatMulti(WccMaterials wccMaterials, Model uiModel,
			HttpServletRequest request, @RequestParam int cloumn,
			@RequestParam Long id) {
		JSONObject json = null;
		String flag = "";

		WccPlatformUser platformUsers = WccPlatformUser
				.findWccPlatformUser(Long.parseLong(request
						.getParameter("wccPlatformUsers")));
		WccMaterials material = WccMaterials.findWccMaterials(id);
		material.setTitle(request.getParameter("c1_1"));
		material.setToken(request.getParameter("c2_1"));
		material.setThumbnailUrl(request.getParameter("c3_1"));
		String urlBoo = request.getParameter("c4_1");
		if ("".equals(urlBoo)) {
			wccMaterials.setUrlBoolean(false);
		} else {
			wccMaterials.setUrlBoolean(Boolean.parseBoolean(urlBoo));
		}
		material.setContent(request.getParameter("c5_1"));
		String remakeUrl = request.getParameter("c7_1");
		if (!"".equals(remakeUrl) && remakeUrl != null) {
			if (!"http://".equals(remakeUrl.substring(0, 7))
					&& !"HTTP://".equals(remakeUrl.substring(0, 7))) {
				material.setRemakeUrl("http://" + remakeUrl);
			} else {
				material.setRemakeUrl(remakeUrl);
			}
		} else {
			material.setRemakeUrl(remakeUrl);
		}

		material.setCodeStatus(Boolean.parseBoolean(request
				.getParameter("codeStatus")));
		if (wccMaterials.getCodeStatus()) {
			if (material.getCodeId() == null) {
				// 调用微信接口
				String appId = platformUsers.getAppId();
				String appSecret = platformUsers.getAppSecret();
				// 生成二维码
				WccQrcode wccQrcodes;
				try {
					wccQrcodes = Qrcode.createOrgetCode(Qrcode.NEWS,
							platformUsers, appId, appSecret, request);
					material.setCodeId(wccQrcodes.getSceneId());
				} catch (WxErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					json = JSONObject.fromObject(e.getError());
					flag = String.valueOf(json.get("errorCode"));
				}
			} else {
				if (material.getWccPlatformUsers().getId() != wccMaterials
						.getWccPlatformUsers().getId()) {
					// 调用微信接口
					String appId = platformUsers.getAppId();
					String appSecret = platformUsers.getAppSecret();
					// 生成二维码
					WccQrcode wccQrcodes;
					try {
						wccQrcodes = Qrcode.createOrgetCode(Qrcode.NEWS,
								platformUsers, appId, appSecret, request);
						material.setCodeId(wccQrcodes.getSceneId());
					} catch (WxErrorException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						json = JSONObject.fromObject(e.getError());
						flag = String.valueOf(json.get("errorCode"));
					}
				}
			}
		}
		if (flag != "") {
			material.setCodeStatus(false);
		}
		material.setWccPlatformUsers(platformUsers);

		for (WccMaterials mater : material.getChildren()) {
			try {
				mater.remove();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// cloumn 总的列，包括了头 c1_1
		for (int i = 2; i <= cloumn; i++) {
			// 保存子列
			WccMaterials m = new WccMaterials();
			m.setTitle(request.getParameter("c1_" + i));
			m.setToken(request.getParameter("c2_" + i));
			m.setThumbnailUrl(request.getParameter("c3_" + i));
			String urlBooi = request.getParameter("c4_" + i);
			if ("".equals(urlBooi)) {
				m.setUrlBoolean(false);
			} else {
				m.setUrlBoolean(Boolean.parseBoolean(urlBooi));
			}
			m.setContent(request.getParameter("c5_" + i));
			String remakeUrl2 = request.getParameter("c7_" + i);
			if (!"".equals(remakeUrl2) && remakeUrl2 != null) {
				if (!"http://".equals(remakeUrl2.substring(0, 7))
						&& !"HTTP://".equals(remakeUrl2.substring(0, 7))) {
					remakeUrl2 = "http://" + remakeUrl2;
				}
			}
			m.setRemakeUrl(remakeUrl2);
			m.setType(WccMaterialsType.MUTIIMAGETEXT);
			m.setInsertTime(new Date());
			m.setParent(material.getId());
			m.setCompanyId(CommonUtils.getCurrentCompanyId(request));
			m.setWccPlatformUsers(platformUsers);
			m.persist();
		}

		material.merge();
		if (flag != "") {
			return "redirect:/wccmaterialses/wxarticlelist?WccMaterialsType=IMAGETEXT&sortFieldName=insertTime&sortOrder=DESC&wxErrorMsg="
					+ flag;
		} else {
			return "redirect:/wccmaterialses/wxarticlelist?WccMaterialsType=IMAGETEXT&sortFieldName=insertTime&sortOrder=DESC";
		}
	}

	@RequestMapping(value = "/updateone")
	public String updateOne(WccMaterials wccMaterials,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest request) {
		JSONObject json = null;
		String flag = "";

		WccMaterials mater = WccMaterials
				.findWccMaterials(wccMaterials.getId());
		mater.setContent(wccMaterials.getContent());
		mater.setDescription(wccMaterials.getDescription());
		mater.setThumbnailUrl(wccMaterials.getThumbnailUrl());
		mater.setTitle(wccMaterials.getTitle());
		mater.setToken(wccMaterials.getToken());
		String remakeUrl = wccMaterials.getRemakeUrl();
		if (!"".equals(remakeUrl) && remakeUrl != null) {
			if (!"http://".equals(remakeUrl.substring(0, 7))
					&& !"HTTP://".equals(remakeUrl.substring(0, 7))) {
				mater.setRemakeUrl("http://" + remakeUrl);
			} else {
				mater.setRemakeUrl(remakeUrl);
			}
		} else {
			mater.setRemakeUrl(remakeUrl);
		}
		mater.setCodeStatus(wccMaterials.getCodeStatus());
		if (wccMaterials.getCodeStatus()) {
			if (mater.getCodeId() == null) {
				WccPlatformUser wccPlatFom = wccMaterials.getWccPlatformUsers();
				// 调用微信接口
				String appId = wccPlatFom.getAppId();
				String appSecret = wccPlatFom.getAppSecret();
				// 生成二维码
				WccQrcode wccQrcodes;
				try {
					wccQrcodes = Qrcode.createOrgetCode(Qrcode.NEWS,
							wccPlatFom, appId, appSecret, request);
					mater.setCodeId(wccQrcodes.getSceneId());
				} catch (WxErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					json = JSONObject.fromObject(e.getError());
					flag = String.valueOf(json.get("errorCode"));
				}
			} else {
				if (mater.getWccPlatformUsers().getId() != wccMaterials
						.getWccPlatformUsers().getId()) {
					WccPlatformUser wccPlatFom = wccMaterials
							.getWccPlatformUsers();
					// 调用微信接口
					String appId = wccPlatFom.getAppId();
					String appSecret = wccPlatFom.getAppSecret();
					// 生成二维码
					WccQrcode wccQrcodes;
					try {
						wccQrcodes = Qrcode.createOrgetCode(Qrcode.NEWS,
								wccPlatFom, appId, appSecret, request);
						mater.setCodeId(wccQrcodes.getSceneId());
					} catch (WxErrorException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						json = JSONObject.fromObject(e.getError());
						flag = String.valueOf(json.get("errorCode"));
					}
				}
			}
		}
		if (flag != "") {
			mater.setCodeStatus(false);
		}
		mater.setUrlBoolean(wccMaterials.getUrlBoolean());
		mater.setWccPlatformUsers(wccMaterials.getWccPlatformUsers());
		mater.merge();
		if (flag != "") {
			return "redirect:/wccmaterialses/wxarticlelist?WccMaterialsType=IMAGETEXT&sortFieldName=insertTime&sortOrder=DESC&wxErrorMsg="
					+ flag;
		} else {
			return "redirect:/wccmaterialses/wxarticlelist?WccMaterialsType=IMAGETEXT&sortFieldName=insertTime&sortOrder=DESC";
		}
	}

	@RequestMapping(value = "/addsn")
	public String addSinglePicNews(Model uiModel,HttpServletRequest request) {
		WccMaterials materials = new WccMaterials();
		uiModel.addAttribute("wccMaterials", materials);
		return "wccmaterialses/addsn";
	}

	@RequestMapping(value = "/addsnSubmit")
	public String addSinglePicNewsSubmit(Model uiModel,
			 @RequestParam(value = "platformUsers", required = false) String platformUsers,
			WccMaterials wccMaterials, HttpServletRequest request) {
		JSONObject json = null;
		String flag = "";
		String[] str=platformUsers.split(",");
		if(str!=null && str.length>0 && !str.equals("")){
		for(String stri:str){
		if(stri!=null  && !stri.equals("")){
		WccMaterials wccMaterials1=new WccMaterials();
		
		wccMaterials.setWccPlatformUsers(WccPlatformUser.findWccPlatformUser(Long.parseLong(stri)));
		wccMaterials.setType(WccMaterialsType.IMAGETEXT);
		wccMaterials.setInsertTime(new Date());
		if (null == wccMaterials.getDescription()
				|| "".equals(wccMaterials.getDescription())) {
			// int maxLength = wccMaterials.getContent().length();
			// System.out.println(wccMaterials.getContent().replaceAll("<[^>]+?>",
			// ""));
			// String regex = "<div\\s*?class=[\"\']article_content[\"\']>";
			// Pattern p = Pattern.compile(regex);
			// Matcher m = p.matcher(wccMaterials.getContent());
			// if(m.find()){
			// System.out.println(m.group().replaceAll("<[^>]+?>", ""));
			// }
			String desc = wccMaterials.getContent().replaceAll("<[^>]+?>", "")
					.trim();
			if (desc.length() > 50) {
				try {
					wccMaterials.setDescription(desc.substring(0, 50));
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				wccMaterials.setDescription(desc);
			}
		}
		wccMaterials.setCompanyId(CommonUtils.getCurrentCompanyId(request));
		String remakeUrl = wccMaterials.getRemakeUrl();
		if (!"".equals(remakeUrl) && remakeUrl != null) {
			if (!"http://".equals(remakeUrl.substring(0, 7))
					&& !"HTTP://".equals(remakeUrl.substring(0, 7))) {
				wccMaterials.setRemakeUrl("http://" + remakeUrl);
			}
		}
		if (wccMaterials.getCodeStatus() == true) {
			WccPlatformUser wccPlatFom = wccMaterials.getWccPlatformUsers();
			// 调用微信接口
			String appId = wccPlatFom.getAppId();
			String appSecret = wccPlatFom.getAppSecret();
			// 生成二维码
			WccQrcode wccQrcodes;
			try {
				wccQrcodes = Qrcode.createOrgetCode(Qrcode.NEWS, wccPlatFom,
						appId, appSecret, request);
				wccMaterials.setCodeId(wccQrcodes.getSceneId());
			} catch (WxErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				json = JSONObject.fromObject(e.getError());
				flag = String.valueOf(json.get("errorCode"));
			}

		}
		if (flag != "") {
			wccMaterials.setCodeStatus(false);
		}
		// List<WccPlatformUser> platformUsers = (List<WccPlatformUser>)
		// uiModel.asMap().get("plats");
		// wccMaterials.setWccPlatformUsers(new
		// HashSet<WccPlatformUser>(platformUsers));
		wccMaterials1.setCodeId(wccMaterials.getCodeId());
		wccMaterials1.setCodeStatus(wccMaterials.getCodeStatus());
		wccMaterials1.setCompanyId(wccMaterials.getCompanyId());
		wccMaterials1.setContent(wccMaterials.getContent());
		wccMaterials1.setDescription(wccMaterials.getDescription());
		wccMaterials1.setInsertTime(wccMaterials.getInsertTime());
		wccMaterials1.setIsDeleted(wccMaterials.getIsDeleted());
		wccMaterials1.setMaterialId(wccMaterials.getMaterialId());
		wccMaterials1.setMaterialType(wccMaterials.getMaterialType());
		wccMaterials1.setMode(wccMaterials.getMode());
		wccMaterials1.setParent(wccMaterials.getParent());
		wccMaterials1.setRemakeUrl(wccMaterials.getRemakeUrl());
		wccMaterials1.setResourceUrl(wccMaterials.getResourceUrl());
		wccMaterials1.setSort(wccMaterials.getSort());
		wccMaterials1.setThumbnailUrl(wccMaterials.getThumbnailUrl());
		wccMaterials1.setTitle(wccMaterials.getTitle());
		wccMaterials1.setToken(wccMaterials.getToken());
		wccMaterials1.setType(wccMaterials.getType());
		wccMaterials1.setUploadTime(wccMaterials.getUploadTime());
		wccMaterials1.setUrlBoolean(wccMaterials.getUrlBoolean());
		wccMaterials1.setVersion(wccMaterials.getVersion());
		wccMaterials1.setWccPlatformUsers(wccMaterials.getWccPlatformUsers());
		wccMaterials1.persist();
		}
		}
		}
		if (flag != "") {
			return "redirect:/wccmaterialses/wxarticlelist?WccMaterialsType=IMAGETEXT&sortFieldName=insertTime&sortOrder=DESC&wxErrorMsg="
					+ flag;
		} else {
			return "redirect:/wccmaterialses/wxarticlelist?WccMaterialsType=IMAGETEXT&sortFieldName=insertTime&sortOrder=DESC";
		}
	}

	@RequestMapping(value = "/add_new_video")
	public String addNewVideo(Model uiModel) {
		WccMaterials materials = new WccMaterials();
		uiModel.addAttribute("materials", materials);
		return "wccmaterialses/add_new_video";
	}

	@RequestMapping(value = "/updateVideo")
	public String updateVideo(Model uiModel, @RequestParam(value = "id") Long id) {
		WccMaterials materials = WccMaterials.findWccMaterials(id);
		uiModel.addAttribute("materials", materials);
		return "wccmaterialses/update_video";
	}

	@RequestMapping(value = "/add_new_video_form")
	public String addNewVideoForm(WccMaterials materials,
			 @RequestParam(value = "platformUsers", required = false) String platformUsers,
			HttpServletRequest request, Model uiModel) {
		if (materials.getId() == null) {
			String[] str=platformUsers.split(",");
			
			for(String stri:str){
				WccMaterials wccMaterials1=new WccMaterials();
				materials.setCompanyId(CommonUtils.getCurrentCompanyId(request));
				// List<WccPlatformUser> platformUsers = (List<WccPlatformUser>)
				// uiModel.asMap().get("plats");
				// materials.setWccPlatformUsers(new
				// HashSet<WccPlatformUser>(platformUsers));
				materials.setType(WccMaterialsType.VIDEO);
				materials.setMaterialType(".mp4");
				materials.setInsertTime(new Date());
				materials.setWccPlatformUsers(WccPlatformUser.findWccPlatformUser(Long.parseLong(stri)));
				
				//materials.persist();
				wccMaterials1.setCodeId(materials.getCodeId());
				wccMaterials1.setCodeStatus(materials.getCodeStatus());
				wccMaterials1.setCompanyId(materials.getCompanyId());
				wccMaterials1.setContent(materials.getContent());
				wccMaterials1.setDescription(materials.getDescription());
				wccMaterials1.setInsertTime(materials.getInsertTime());
				wccMaterials1.setIsDeleted(materials.getIsDeleted());
				wccMaterials1.setMaterialId(materials.getMaterialId());
				wccMaterials1.setMaterialType(materials.getMaterialType());
				wccMaterials1.setMode(materials.getMode());
				wccMaterials1.setParent(materials.getParent());
				wccMaterials1.setRemakeUrl(materials.getRemakeUrl());
				wccMaterials1.setResourceUrl(materials.getResourceUrl());
				wccMaterials1.setSort(materials.getSort());
				wccMaterials1.setThumbnailUrl(materials.getThumbnailUrl());
				wccMaterials1.setTitle(materials.getTitle());
				wccMaterials1.setToken(materials.getToken());
				wccMaterials1.setType(materials.getType());
				wccMaterials1.setUploadTime(materials.getUploadTime());
				wccMaterials1.setUrlBoolean(materials.getUrlBoolean());
				wccMaterials1.setVersion(materials.getVersion());
				wccMaterials1.setWccPlatformUsers(materials.getWccPlatformUsers());
				wccMaterials1.persist();
			}
		} else {
			WccMaterials m = WccMaterials.findWccMaterials(materials.getId());
			m.setThumbnailUrl(materials.getThumbnailUrl());
			m.setTitle(materials.getTitle());
			m.setDescription(materials.getDescription());
			m.setWccPlatformUsers(materials.getWccPlatformUsers());
			m.merge();
		}
		return "redirect:/wccmaterialses/wxarticlelist?WccMaterialsType=VIDEO&sortFieldName=insertTime&sortOrder=DESC";
	}

	@RequestMapping(value = "/addsnUpload", method = RequestMethod.POST)
	@ResponseBody
	public String addSinglePicNewsUpload(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String ctxPath = request.getSession().getServletContext()
				.getRealPath("/uploads");
		File uploadsFile = new File(ctxPath);
		if (!uploadsFile.exists()) {
			uploadsFile.mkdirs();
		}
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();
			String fileName = mf.getOriginalFilename();
			String uuid = UUID.randomUUID().toString().replaceAll("\\-", "");// 返回一个随机UUID。
			File uploadFile = new File(uploadsFile + "/" + uuid + "-"
					+ fileName);
			try {
				FileCopyUtils.copy(mf.getBytes(), uploadFile);
				return uploadFile.getName();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@RequestMapping(value = "/addmn")
	public String addMutilPicNews(Model uiModel, HttpServletRequest request) {
		WccMaterials materials = new WccMaterials();
		uiModel.addAttribute("materials", materials);
		populateModel(uiModel, request);
		return "wccmaterialses/addmn";
	}

	@RequestMapping(value = "/addmnn")
	public String addMnn(Model uiModel, HttpServletRequest request) {
		WccMaterials materials = new WccMaterials();
		uiModel.addAttribute("materials", materials);
		return "wccmaterialses/addmnn";
	}

	@RequestMapping(value = "/addmnSubmit")
	public String addMutilPicNewsSubmit(Model uiModel,
			WccMaterials wccMaterials, HttpServletRequest request,
			 @RequestParam(value = "platformUsers", required = false) String platformUsers,
			 @RequestParam int cloumn) {
		// wccMaterials.setType(WccMaterialsType.IMAGETEXT);
		// wccMaterials.setInsertTime(new Date());
		// wccMaterials.setCompanyId(CommonUtils.getCurrentCompanyId(request));
		// // List<WccPlatformUser> platformUsers = (List<WccPlatformUser>)
		// // uiModel.asMap().get("plats");
		// // wccMaterials.setWccPlatformUsers(new
		// // HashSet<WccPlatformUser>(platformUsers));
		// wccMaterials.setWccPlatformUsers(platformUsers);
		// wccMaterials.persist();
		String[] str=platformUsers.split(",");
		String flag = "";
		JSONObject json = null;
		if(str!=null && str.length>0 && !str.equals("")){
			for(String stri:str){
			if(stri!=null  && !stri.equals("")){
		
		WccMaterials wccMaterials1=new WccMaterials();

		WccPlatformUser platformUsers1 = WccPlatformUser
				.findWccPlatformUser(Long.parseLong(stri));
		wccMaterials.setTitle(request.getParameter("c1_1"));
		wccMaterials.setToken(request.getParameter("c2_1"));
		wccMaterials.setThumbnailUrl(request.getParameter("c3_1"));
		String urlBoo = request.getParameter("c4_1");
		if ("".equals(urlBoo)) {
			wccMaterials.setUrlBoolean(false);
		} else {
			wccMaterials.setUrlBoolean(Boolean.parseBoolean(urlBoo));
		}
		wccMaterials.setContent(request.getParameter("c5_1"));
		String remakeUrl = request.getParameter("c7_1");
		if (!"".equals(remakeUrl) && remakeUrl != null) {
			if (!"http://".equals(remakeUrl.substring(0, 7))
					&& !"HTTP://".equals(remakeUrl.substring(0, 7))) {
				remakeUrl = "http://" + remakeUrl;
			}
		}

		wccMaterials.setRemakeUrl(remakeUrl);
		wccMaterials.setType(WccMaterialsType.IMAGETEXT);
		wccMaterials.setInsertTime(new Date());
		wccMaterials.setCompanyId(CommonUtils.getCurrentCompanyId(request));
		wccMaterials.setWccPlatformUsers(platformUsers1);
		wccMaterials.setCodeStatus(Boolean.parseBoolean(request
				.getParameter("codeStatus")));
		if (Boolean.parseBoolean(request.getParameter("codeStatus"))) {
			// 调用微信接口
			String appId = platformUsers1.getAppId();
			String appSecret = platformUsers1.getAppSecret();
			// 生成二维码
			WccQrcode wccQrcodes;
			try {
				wccQrcodes = Qrcode.createOrgetCode(Qrcode.NEWS, platformUsers1,
						appId, appSecret, request);
				wccMaterials.setCodeId(wccQrcodes.getSceneId());
			} catch (WxErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				json = JSONObject.fromObject(e.getError());
				flag = String.valueOf(json.get("errorCode"));
			}
		}
		if (flag != "") {
			wccMaterials.setCodeStatus(false);
		}

		wccMaterials1.setCodeId(wccMaterials.getCodeId());
		wccMaterials1.setCodeStatus(wccMaterials.getCodeStatus());
		wccMaterials1.setCompanyId(wccMaterials.getCompanyId());
		wccMaterials1.setContent(wccMaterials.getContent());
		wccMaterials1.setDescription(wccMaterials.getDescription());
		wccMaterials1.setInsertTime(wccMaterials.getInsertTime());
		wccMaterials1.setIsDeleted(wccMaterials.getIsDeleted());
		wccMaterials1.setMaterialId(wccMaterials.getMaterialId());
		wccMaterials1.setMaterialType(wccMaterials.getMaterialType());
		wccMaterials1.setMode(wccMaterials.getMode());
		wccMaterials1.setParent(wccMaterials.getParent());
		wccMaterials1.setRemakeUrl(wccMaterials.getRemakeUrl());
		wccMaterials1.setResourceUrl(wccMaterials.getResourceUrl());
		wccMaterials1.setSort(wccMaterials.getSort());
		wccMaterials1.setThumbnailUrl(wccMaterials.getThumbnailUrl());
		wccMaterials1.setTitle(wccMaterials.getTitle());
		wccMaterials1.setToken(wccMaterials.getToken());
		wccMaterials1.setType(wccMaterials.getType());
		wccMaterials1.setUploadTime(wccMaterials.getUploadTime());
		wccMaterials1.setUrlBoolean(wccMaterials.getUrlBoolean());
		wccMaterials1.setVersion(wccMaterials.getVersion());
		wccMaterials1.setWccPlatformUsers(wccMaterials.getWccPlatformUsers());
		wccMaterials1.persist();
		// cloumn 总的列，包括了头 c1_1
		for (int i = 2; i <= cloumn; i++) {
			// 保存子列
			WccMaterials m = new WccMaterials();
			m.setTitle(request.getParameter("c1_" + i));
			m.setToken(request.getParameter("c2_" + i));
			m.setThumbnailUrl(request.getParameter("c3_" + i));
			String urlBooi = request.getParameter("c4_" + i);
			if ("".equals(urlBooi)) {
				m.setUrlBoolean(false);
			} else {
				m.setUrlBoolean(Boolean.parseBoolean(urlBooi));
			}
			m.setContent(request.getParameter("c5_" + i));
			String remakeUrl2 = request.getParameter("c7_" + i);
			if (!"".equals(remakeUrl2) && remakeUrl2 != null) {
				if (!"http://".equals(remakeUrl2.substring(0, 7))
						&& !"HTTP://".equals(remakeUrl2.substring(0, 7))) {
					remakeUrl2 = "http://" + remakeUrl2;
				}
			}
			m.setRemakeUrl(remakeUrl2);
			m.setType(WccMaterialsType.MUTIIMAGETEXT);
			m.setInsertTime(new Date());
			m.setParent(wccMaterials1.getId());
			m.setCompanyId(CommonUtils.getCurrentCompanyId(request));
			m.setWccPlatformUsers(platformUsers1);
			m.persist();
			}
			}
			}
	}
		if (flag != "") {
			return "redirect:/wccmaterialses/wxarticlelist?WccMaterialsType=IMAGETEXT&sortFieldName=insertTime&sortOrder=DESC&wxErrorMsg="
					+ flag;
		} else {
			return "redirect:/wccmaterialses/wxarticlelist?WccMaterialsType=IMAGETEXT&sortFieldName=insertTime&sortOrder=DESC";
		}
	}

	@RequestMapping(value = "/showdetail/{id}")
	public String showdetail(@PathVariable Long id, Model uiModel,
			@RequestParam(value = "friendId", required = false) Long friendId) {
		WccMaterials materials = WccMaterials.findWccMaterials(id);
		if (materials.getContent() == null || "".equals(materials.getContent())) {
			materials.getContent().replace("/ump/attached/wx_image", Global.Url+"/ump/attached/wx_image");
			materials.setThumbnailUrl(Global.Url+materials.getThumbnailUrl());
			String remakeUrl = materials.getRemakeUrl();
			if (remakeUrl.contains("ump/wcclotteryactivitys/showActivity?id=") || remakeUrl.contains("ump/pageSurvey/showSurvey")) {
				StringBuffer urls = new StringBuffer();
				String[] url = remakeUrl.split("\\?");
				urls.append(
						"https://open.weixin.qq.com/connect/oauth2/authorize?appid=")
						.append(materials.getWccPlatformUsers().getAppId())
						.append("&redirect_uri=" + url[0])
						.append("&response_type=code&scope=snsapi_base&state=")
						.append(url[1].split("=")[1])
						.append("-" + materials.getWccPlatformUsers().getId())
						.append("#wechat_redirect");
				materials.setRemakeUrl(urls.toString());
			} else if (remakeUrl
					.contains("ump/wxController/showContents?contentId=")) {
				if (friendId != null) {
					remakeUrl += "&friendId=" + friendId;
					materials.setRemakeUrl(remakeUrl);
				}
			}
		}
		uiModel.addAttribute("material", materials);
		return "wccmaterialses/showdetail";
	}

	@RequestMapping(value = "/showdetailmuti/{id}")
	public String showdetailMuti(@PathVariable Long id, Model uiModel) {
		uiModel.addAttribute("material", WccMaterials.findWccMaterials(id));
		return "wccmaterialses/showdetailmuti";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		WccMaterials wccMaterials = WccMaterials.findWccMaterials(id);
		for (WccMaterials materials : wccMaterials.getChildren()) {
			materials.remove();
		}
		wccMaterials.remove();
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/wccmaterialses&sortFieldName=insertTime&sortOrder=DESC";
	}

	@RequestMapping(value = "delete/{id}")
	public String deleteOne(
			@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "WccMaterialsType", required = false) WccMaterialsType type,
			Model uiModel) {
		WccMaterials wccMaterials = WccMaterials.findWccMaterials(id);
		for (WccMaterials mater : wccMaterials.getChildren()) {
			mater.remove();
		}
		wccMaterials.remove();
		uiModel.asMap().clear();
		// uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		// uiModel.addAttribute("size", (size == null) ? "10" :
		// size.toString());
		return "redirect:/wccmaterialses/wxarticlelist?WccMaterialsType="
				+ type + "&sortFieldName=insertTime&sortOrder=DESC";
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"wccMaterials_inserttime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, WccMaterials wccMaterials) {
		uiModel.addAttribute("wccMaterials", wccMaterials);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("wccmaterialstypes",
				Arrays.asList(WccMaterialsType.values()));
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

	@RequestMapping("download/{id}")
	public ModelAndView download(@PathVariable("id") Long id,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;

		String ctxPath = request.getSession().getServletContext()
				.getRealPath("/");
		// String downLoadPath = ctxPath + fileName;
		WccMaterials materials = WccMaterials.findWccMaterials(id);
		String downLoadPath = ctxPath
				+ materials.getThumbnailUrl().substring(5);
		String fileName = materials.getTitle();
		fileName += materials.getMaterialType();
		System.out.println(downLoadPath);
		System.out.println(fileName);
		// fileName = fileName.replace(" ", ""); //encode后替换 解决空格问题
		try {
			long fileLength = new File(downLoadPath).length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ new String(fileName.getBytes("utf-8"), "ISO8859-1")
					+ "\"");
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;
	}

	@RequestMapping(value = "/seeCode", produces = "text/html")
	@ResponseBody
	public String seeCode(Model uiModel,
			@RequestParam(value = "id", required = false) Long id,
			HttpServletRequest request) {
		WccMaterials materials = WccMaterials.findWccMaterials(id);
		if (materials.getCodeStatus() == true) {
			Integer scenId = materials.getCodeId();
			if (null != scenId && !"".equals(scenId)) {
				WccQrcode wccQrcode = WccQrcode.findWccQrcodeByScendId("6",
						scenId);
				return wccQrcode.getCodeUrl();
			}
		}
		return "0";
	}

	@RequestMapping(value = "/checkUsed", produces = "text/html")
	@ResponseBody
	public String checkUsed(Model uiModel,
			@RequestParam(value = "id", required = false) Long id,
			HttpServletRequest request) {
		if (WccAutokbs.findWccAutokbsByMaterial(id).size() > 0
				|| WccWelcomkbs.findWccWelcomkbsesByMaterial(id).size() > 0
				|| WccMenu.findWccMenuByMaterial(id).size() > 0
				|| WccRecordMsg.findWccRecordMsgByToUser(id).size() > 0||
				WccMassMessage.findWccMassMessagesByMaterial(id).size()>0
				) {
			return "1";
		}
		;
		return "0";
	}

}

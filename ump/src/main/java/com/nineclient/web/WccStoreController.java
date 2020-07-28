package com.nineclient.web;

import com.nineclient.constant.WccCheckStatus;
import com.nineclient.model.PubOperator;
import com.nineclient.model.PubOrganization;
import com.nineclient.model.UmpAuthority;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpConfig;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccQrcode;
import com.nineclient.model.WccStore;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.ExcelUtil;
import com.nineclient.utils.Global;
import com.nineclient.utils.HttpClientUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Qrcode;
import com.nineclient.utils.QueryModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.chanjar.weixin.common.exception.WxErrorException;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/wccstores")
@Controller
@RooWebScaffold(path = "wccstores", formBackingObject = WccStore.class)
public class WccStoreController {
	private final Logger log = LoggerFactory.getLogger(WccStoreController.class);
	/**
	 * 添加门店
	 * @param wccStore
	 * @param uiModel
	 * @param httpServletRequest
	 * @param relatedIssues
	 * @param organizationId
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "addStore", method = RequestMethod.POST, produces = "text/html")
	public String create(
			WccStore wccStore,
			Model uiModel,
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "relatedIssues", required = false) String relatedIssues,
			@RequestParam(value = "organizationId", required = false) Long organizationId
			){
		JSONObject json = null;
		String flag = "";
		if (null == relatedIssues && "".equals(relatedIssues)) {
			populateEditForm(uiModel, wccStore);
			return "wccstores/create";
		}
		if (null == organizationId && organizationId <= 0l) {
			populateEditForm(uiModel, wccStore);
			return "wccstores/create";
		}
		
		PubOrganization pubOrgan = PubOrganization.findPubOrganization(organizationId);
		wccStore.setCreateTime(new Date());
		wccStore.setIsDeleted(true);
		wccStore.setStoreAuditing(0);
		wccStore.setOrganization(pubOrgan);
		wccStore.setCompany(CommonUtils.getCurrentPubOperator(httpServletRequest).getCompany());
		
		Set<WccPlatformUser> platSets = new HashSet<WccPlatformUser>();
		String[] ids = relatedIssues.split(",");
		WccPlatformUser wccPlatFom = null;
		StringBuilder sb = new StringBuilder();
		List<WccQrcode> wccQrcodeList = new ArrayList<WccQrcode>();
		for (String id : ids) {
			 wccPlatFom = WccPlatformUser.findWccPlatformUser(Long.parseLong(id));
			 if(wccStore.getIsStoreCode() != null &&wccStore.getIsStoreCode() == true){
				// 调用微信接口
				String appId = wccPlatFom.getAppId();
				String appSecret = wccPlatFom.getAppSecret();
				//生成二维码
				WccQrcode wccQrcodes;
				try {
					wccQrcodes = Qrcode.createOrgetCode(Qrcode.STORE, wccPlatFom, appId, appSecret, httpServletRequest);
					if(wccQrcodes.getId() != null){
						wccQrcodeList.add(wccQrcodes);
						//生成sceneid
						sb.append(wccQrcodes.getSceneId()).append(",");
						//wccStore.setStoreCodeUrl(wccQrcodes.getCodeUrl()); 
					}
				} catch (WxErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					json = JSONObject.fromObject(e.getError());
					flag = String.valueOf(json.get("errorCode"));
				}
				wccStore.setSceneId(sb.toString());
				if(flag!=""){
					wccStore.setIsStoreCode(false);
				}
			}else{
				wccStore.setSceneId("");
				wccStore.setIsStoreCode(false);
			}
			platSets.add(wccPlatFom);
		}
		wccStore.setPlatformUsers(platSets);
		uiModel.asMap().clear();
		wccStore.persist();
		/**
		 * 分别给二维码加上门店id
		 */
		if(wccQrcodeList != null && wccQrcodeList.size() > 0){
			for (WccQrcode wccQrcode : wccQrcodeList) {
				wccQrcode.setWccStroeId(wccStore.getId());
				wccQrcode.merge();
			}
		}
		uiModel.addAttribute("page",1);
		uiModel.addAttribute("size",10);
		if(flag==""){
			return "redirect:/wccstores?page=1&size=10";
		}else{
			return "redirect:/wccstores?page=1&size=10&wxErrorMsg="+flag;
		}
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel,HttpServletRequest httpServletRequest) {
		populateEditForm(uiModel, new WccStore());
		PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
		uiModel.addAttribute("platformUsers",
				WccPlatformUser.findAllWccPlatformUsers(pub));
		uiModel.addAttribute("puborganizations",
				PubOrganization.findAllPubOrganizations(pub.getCompany().getId()));
		return "wccstores/create";
	}

	@RequestMapping(produces = "text/html")
	@SuppressWarnings("unchecked")
	public String list(
			@RequestParam(value = "displayId", required = false) Long displayId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "wxErrorMsg", required = false) String wxErrorMsg,
			HttpServletRequest request,
			Model uiModel) {
		
		if(displayId == null){
			displayId = CommonUtils.getCurrentDisPlayId(request);
		}
		if(wxErrorMsg!=""){
			uiModel.addAttribute("wxErrorMsg", wxErrorMsg);
		}
		request.getSession().setAttribute("displayId",displayId);
		/**
		 *管理员除外
		 *判断用户是否有审核按钮
		 */
		if(CommonUtils.getCurrentPubOperator(request).getPubRole() == null){
			request.getSession().setAttribute("audMenu",true);
		}else{
			Map<String, String> auditMenu = (Map<String, String>) request.getSession().getAttribute("auditMenu");
			if(null != auditMenu ){
				UmpAuthority umpMenu = null;
				for (String menuId : auditMenu.keySet()) {
					umpMenu  = UmpAuthority.findUmpAuthority(Long.parseLong(menuId));
					if(null != umpMenu){
						request.getSession().setAttribute("audMenu",true);
					}
				}
			}
		}
		return "wccstores/list";
	}

	@RequestMapping(value = "/getStoreListByFiled", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String getStoreListByFiled(
			Model uiModel,
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "organName", required = false) String organName,
			@RequestParam(value = "platId", required = false) String platId,
			@RequestParam(value = "storeName", required = false) String storeName,
			@RequestParam(value = "storeAuditing", required = false) Integer storeAuditing,
			@RequestParam(value = "isSendMail", required = false) Boolean isSendMail,
			@RequestParam(value = "starTime", required = false) String starTime,
			@RequestParam(value = "endTime", required = false) String endTime) {
		
			Map<String,Object> parmMap = new HashMap<String, Object>();
			parmMap.put("storeAuditing", storeAuditing);
			parmMap.put("starTime", starTime);
			parmMap.put("endTime", endTime);
			parmMap.put("isSendMail", isSendMail);
			parmMap.put("storeName", storeName);
			
			PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
			WccStore wccStore = new WccStore();
			Set<WccPlatformUser> platSets = new HashSet<WccPlatformUser>();
			List<WccPlatformUser> wccPlatList = null;
			if(null != platId && !"".equals(platId)){
				String[] ids = platId.split(",");
				for (String id : ids) {
					platSets.add(WccPlatformUser.findWccPlatformUser(Long.parseLong(id)));
				}
				wccStore.setPlatformUsers(platSets);
			}else{
				if(pub.getPubRole() == null){
					wccStore.setPlatformUsers(new HashSet<WccPlatformUser>(WccPlatformUser.findAllWccPlatformUsers(CommonUtils.getCurrentPubOperator(httpServletRequest))));
				}else{
					wccPlatList = CommonUtils.set2List(PubOperator.findPubOperator(pub.getId()).getPlatformUsers());
					for (WccPlatformUser wccPlatformUser : wccPlatList) {
						platSets.add(wccPlatformUser);
					}
					wccStore.setPlatformUsers(platSets);
				}
			}
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			QueryModel<WccStore> qm = new QueryModel<WccStore>(wccStore, firstResult, size);
			qm.getInputMap().putAll(parmMap);
			PageModel<WccStore> pm = WccStore.findWccStoreEntries(qm,pub,organName);
			PageModel<WccStore> pageMode = new PageModel<WccStore>();
			pageMode.setPageNo(page);
			pageMode.setTotalCount(pm.getTotalCount());
			pageMode.setPageSize(sizeNo);
			pageMode.setDataList(pm.getDataList());
			List<WccStore> wccList = new ArrayList<WccStore>();
			for (WccStore list : pm.getDataList()) {
				StringBuilder stringb = new StringBuilder();
				for (WccPlatformUser platList : list.getPlatformUsers()) {
					if(pub.getPubRole() != null){
						for (WccPlatformUser wccpList : wccPlatList) {
							if(platList.getId() == wccpList.getId()){
								stringb.append(platList.getAccount()).append(" ");
							}
						}
					}else{
						stringb.append(platList.getAccount()).append(" ");
					}
				}
				list.setPlatFormName(stringb.toString());
				wccList.add(list);
			}
			pageMode.setDataJson(WccStore.toJsonArray(wccList));
			return pageMode.toJson();
	}

	@RequestMapping(value = "/updateStore", method = RequestMethod.POST, produces = "text/html")
	public String updateStore(
			WccStore wccStore,
			Model uiModel,
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "relatedIssues", required = false) String relatedIssues,
			@RequestParam(value = "organName", required = false) String organName) {
		JSONObject json = null;
		String flag = "";
		if (null == relatedIssues && "".equals(relatedIssues)) {
			populateEditForm(uiModel, wccStore);
			return "wccstores/update";
		}
		if (null == organName && "".equals(organName)) {
			populateEditForm(uiModel, wccStore);
			return "wccstores/update";
		}
		
		PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
		PubOrganization pubOrgan = PubOrganization.findPubOrganizationByName(organName,pub);
		WccStore wccStores = WccStore.findWccStore(wccStore.getId());
		wccStores.setOrganization(pubOrgan);
		wccStore.setCompany(pub.getCompany());
		Set<WccPlatformUser> platSets = new HashSet<WccPlatformUser>();
		/**
		 * 修改
		 * 之前有二维码的，如果选择取消二维码，
		 * 需要解绑二维码。
		 */
		String[] platFormIds = relatedIssues.split(",");
		WccPlatformUser wccPlatFom = null;
		StringBuilder sb = new StringBuilder();
		String[] scenrIds = null;
		if(wccStore.getIsStoreCode() != null && wccStore.getIsStoreCode() == false){
			if(wccStores.getSceneId() != null){
				scenrIds = wccStores.getSceneId().split(",");
				for (String scenId : scenrIds) {
					Qrcode.setIsuseCode(Integer.parseInt(scenId));
				}
			}
			for (String platFormId : platFormIds) {
				platSets.add(WccPlatformUser.findWccPlatformUser(Long.parseLong(platFormId)));
			}
			wccStores.setIsStoreCode(false);
		}else if(wccStore.getIsStoreCode() != null && wccStore.getIsStoreCode() == true){
			if(wccStores.getSceneId() != null && !"".equals(wccStores.getSceneId())){//之前生成过二维码
				wccStores.setSceneId("");
				wccStores.merge();
				for (String platFormId : platFormIds) {
						wccPlatFom = WccPlatformUser.findWccPlatformUser(Long.parseLong(platFormId));
						String appId = wccPlatFom.getAppId();
						String appSecret = wccPlatFom.getAppSecret();
						List<WccQrcode> wccQrcode = WccQrcode.findWccQrcodeByStoreIdAndPlatFormId(wccStore.getId(),Long.parseLong(platFormId));
						if(null != wccQrcode && wccQrcode.size() > 0){
							WccQrcode wccQrco = wccQrcode.get(0);
							wccQrco.setIsUse(true);
							wccQrco.setUseType(Qrcode.STORE);
							wccQrco.merge();
							sb.append(wccQrco.getSceneId()).append(",");
						}else{
							WccQrcode wccQrcodes;
							try {
								wccQrcodes = Qrcode.createOrgetCode(Qrcode.STORE, wccPlatFom, appId, appSecret, httpServletRequest);
								if(null!=wccQrcodes){
									wccQrcodes.setWccStroeId(wccStore.getId());
									wccQrcodes.merge();
									sb.append(wccQrcodes.getSceneId()).append(",");
								}	
							} catch (WxErrorException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								json = JSONObject.fromObject(e.getError());
								flag = String.valueOf(json.get("errorCode"));
							}
						}
						platSets.add(wccPlatFom);
					}
				if(flag!=""){
					wccStore.setIsStoreCode(false);
				}else{
					wccStores.setIsStoreCode(true);
				}
				wccStores.setSceneId(sb.toString());
			}else{//修改之前没有生成过二维码的。
				for (String platFormId : platFormIds) {
					wccPlatFom = WccPlatformUser.findWccPlatformUser(Long.parseLong(platFormId));
					String appId = wccPlatFom.getAppId();
					String appSecret = wccPlatFom.getAppSecret();
					WccQrcode wccQrcodes;
					try {
						wccQrcodes = Qrcode.createOrgetCode(Qrcode.STORE, wccPlatFom, appId, appSecret, httpServletRequest);
						if(null!=wccQrcodes){
							wccQrcodes.setWccStroeId(wccStore.getId());
							wccQrcodes.merge();
							sb.append(wccQrcodes.getSceneId()).append(",");
						}
					} catch (WxErrorException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						json = JSONObject.fromObject(e.getError());
						flag = String.valueOf(json.get("errorCode"));
					}
					platSets.add(wccPlatFom);
					wccStores.setSceneId(sb.toString());
					}
				if(flag!=""){
					wccStore.setIsStoreCode(false);
				}else{
					wccStores.setIsStoreCode(true);
				}
			}
		}
		
		wccStores.setPlatformUsers(platSets);
		wccStores.setStoreAddres(wccStore.getStoreAddres());
		wccStores.setStoreEmail(wccStore.getStoreEmail());
		wccStores.setStoreUrl(wccStore.getStoreUrl());
		wccStores.setStorePhone(wccStore.getStorePhone());
		wccStores.setStoreLaty(wccStore.getStoreLaty());
		wccStores.setStoreLngx(wccStore.getStoreLngx());
		wccStores.setStoreName(wccStore.getStoreName());
		wccStores.setStoreText(wccStore.getStoreText());
		wccStores.setStoreAuditing(0);
		uiModel.asMap().clear();
		wccStores.merge();
		if(flag==""){
			return "redirect:/wccstores?page=1&size=10";
		}else{
			return "redirect:/wccstores?page=1&size=10&wxErrorMsg="+flag;
		}
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel,HttpServletRequest httpServletRequest) {
		if(id == null){
			return "redirect:/wccstores?page=1&amp;size=10";
		}
		List<WccPlatformUser>  platFoems = CommonUtils.set2List(WccStore.findWccStore(id).getPlatformUsers());
		
		StringBuffer ids = new StringBuffer();
		int n = 1;
		for (WccPlatformUser wccPlatformUser : platFoems) {
			ids.append(wccPlatformUser.getId());
			if(n!=platFoems.size()){
				ids.append(",");
			}
			n++;
		}
		 
		uiModel.addAttribute("platformUserIds", ids);
		populateEditForm(uiModel, WccStore.findWccStore(id));
		return "wccstores/update";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "text/html")
	public String delete(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		WccStore wccStore = WccStore.findWccStore(id);
		//删除门店的同时删除关联的二维码表对应的数据
		if(wccStore.getSceneId() != null){
			String[] scenrIds = wccStore.getSceneId().split(",");
			for (String scenId : scenrIds) {
				Qrcode.setIsuseCode(Integer.parseInt(scenId));
			}
		}
		wccStore.remove();
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/wccstores";
	}

	void populateEditForm(Model uiModel, WccStore wccStore) {
		uiModel.addAttribute("wccStore", wccStore);
		uiModel.addAttribute("wcccheckstatuses",
				Arrays.asList(WccCheckStatus.values()));
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

	/**
	 * 门店审核
	 * 
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkStatus", method = RequestMethod.POST, produces = "text/html")
	@ResponseBody
	public String checkStatus(@RequestParam(value = "id") Long id,
			HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel,
			@RequestParam(value = "isCheck", required = false) Integer isCheck) {
			WccStore wccStore = WccStore.findWccStore(id);
			 if(isCheck == 1){
				 wccStore.setStoreAuditing(1);
				 wccStore.merge();
				 return "1";
			 }else if(isCheck == 2){
				 wccStore.setStoreAuditing(2);
				 wccStore.merge();
				 return "1";
			 }
			 uiModel.asMap().clear();
		     uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		     uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		     return "0";
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
		for (Long id : lists) {
			WccStore wccStore = WccStore.findWccStore(id);
			if(wccStore.getStoreAuditing() == 0){
				if(isCheck == 1){
					wccStore.setStoreAuditing(1);
					try {
						wccStore.merge();
					} catch (Exception e) {
						flag = false;
					}
				}else if(isCheck == 2){
					wccStore.setStoreAuditing(2);
					try {
						wccStore.merge();
					} catch (Exception e) {
						flag = false;
					}
				}
			}
		}
		if (flag) {
			return "1";
		} else {
			return "0";
		}
	}
	
	/**
	 * 验证门店名称是否存在
	 * @return
	 */
	@RequestMapping(value = "/checkStoreNameExits", method = RequestMethod.POST, produces = "text/html")
	@ResponseBody
	public String checkStoreNameExits(Model uiModel,
			@RequestParam(value = "storeName", required = false) String storeName,
			HttpServletRequest request){
		String msg = "0";
		PubOperator pub = CommonUtils.getCurrentPubOperator(request);
		List<WccStore>  wccStore = WccStore.findAllWccStoresByStoreName(storeName,pub.getCompany());
		if(wccStore != null && wccStore.size() > 0){
			msg = "1";
		}
		return msg;
	}
	
	/**
	 * 下载模板
	 * @return
	 */
	@RequestMapping(value = "/downLoadTemplate", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadTemplate(Model uiModel,HttpServletRequest request,HttpServletResponse response){
		// 生成提示信息，
		response.setContentType("application/vnd.ms-excel");
		String[] title = {"公众平台","组织架构","门店名称","门店地址","门店邮箱","门店电话","门店URL","是否关联二维码","营业时间"};
		OutputStream os = null;
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("WCC门店导入模板").getBytes("gb2312"),
							"iso8859-1") + ".xls");
			os = response.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel_templete(title,os);
		return;
	}
	
	
	/**
	 * 批量生成二维码
	 * @param wccStore
	 * @param uiModel
	 * @param request
	 * @param pks
	 * @return
	 */
	@RequestMapping(value = "/batchCode", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String batchCode(
			Model uiModel,
			HttpServletRequest request,
			@RequestParam(value = "pks", required = false) String pks) {
		String resp = "";
		String msg  = "操作成功";
		List<Long> lists = CommonUtils.stringArray2List(pks);
		
		HttpSession session= request.getSession();
		if(session.getAttribute("importmsg")!=null)
		{
			session.removeAttribute("importmsg");
		}
		String[] scenrIds = null;
		WccStore wccStore = null;
		WccQrcode wccQrcodes = null;
		StringBuilder sb = new StringBuilder();
		try{
			for (Long id : lists) {
				wccStore = WccStore.findWccStore(id);
				if(null!=wccStore){
					if(wccStore.getIsStoreCode() == null || wccStore.getIsStoreCode() == false){
						Set<WccPlatformUser> wccPlatformSet = wccStore.getPlatformUsers();
						for(WccPlatformUser wccPlat:wccPlatformSet){
							wccQrcodes = Qrcode.createOrgetCode(Qrcode.STORE,wccPlat, wccPlat.getAppId(), wccPlat.getAppSecret(), request);
							 if(null!=wccQrcodes){
								 wccQrcodes.setWccStroeId(id);
								 wccQrcodes.merge();
								 sb.append(wccQrcodes.getSceneId()).append(",");
								 wccStore.setStoreCodeUrl(wccQrcodes.getCodeUrl()); //已废除 
								 wccStore.setIsStoreCode(true);
							 }
						}
						 wccStore.setSceneId(sb.deleteCharAt(sb.lastIndexOf(",")).toString());
						 wccStore.merge();
					}else{
						if(wccStore.getSceneId() != null){
							scenrIds = wccStore.getSceneId().split(",");
							for (String scenId : scenrIds) {
								Qrcode.setIsuseCodeOn(Integer.parseInt(scenId));
							}
						}
					}
				}
			}
			session.setAttribute("importmsg", msg);
        }catch(Exception e){
        	 e.printStackTrace();
        	 resp="error";
		}
		
		return resp;
		
	}
	
	
	/**
	 * 批量发送二维码邮件
	 * @param uiModel
	 * @param request
	 * @param content
	 * @param pks
	 * @return
	 */
	@RequestMapping(value = "batchSendEmail", method = RequestMethod.POST,produces="text/html;charset=utf-8")
	@ResponseBody
	public String batchSendEmail(
			Model uiModel,
			HttpServletRequest request,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "company", required = false) String company,
			@RequestParam(value = "pks", required = false) String pks) {
		List<Long> lists = CommonUtils.stringArray2List(pks);
		String resp = null;
		String flag = "";
		try{
	    if(lists.size()>0){
	    	List<UmpConfig> configs = UmpConfig
					.findConfigsByKeyType("wccEmailConfig_"
							+ CommonUtils.getCurrentPubOperator(request).getCompany().getId());
			for (Long id : lists) {
				System.out.println(id);
				WccStore wccStore = WccStore.findWccStore(id);
				if(null!=wccStore){
					if(wccStore.getStoreEmail()!=null && !"".equals(wccStore.getStoreEmail())){
						if(null!=wccStore.getSceneId() && !"".equals(wccStore.getSceneId())){
							StringBuffer  emailContents = new StringBuffer();
							emailContents
							.append("<div><p>亲爱的:"+wccStore.getStoreName()+"</p>") 
							.append("<p>"+content+"</p>");
							String[] scenIds  = wccStore.getSceneId().split(",");
							WccQrcode  wccQrcode = null;
							emailContents.append("<p>专属二维码：</p>")
							 .append("<div style=\"width:20%; height:auto; float:left; overflow:hidden;\">");
							for (String scenId : scenIds) {
								if(null!=scenId && !"".equals(scenId)){
									wccQrcode = WccQrcode.findWccQrcodeByScendId("2",Integer.parseInt(scenId));
									 emailContents
									 .append("<div style=\"width:100%; height:auto; clear:both;overflow:hidden;\">")
									 .append("<img src="+Global.IMAGE_QRCOED_FULL_PATH+Global.IMAGE_QRCOED_PATH+"/")
									 .append(wccQrcode.getCodeUrl()!=null?wccQrcode.getCodeUrl():"")
									 .append(" style=\"width:100%; height:auto;\" />")
									 .append("</div>")
									 .append("<div style=\"width:100%;height:auto;clear:both;overflow:hidden;text-align:center;line-height:24px;\"><span>")
									 .append(wccQrcode.getPlatformUser().getAccount())
									 .append("</span></div>");
								}
						    }
							emailContents
							.append("<p style=\"float:right;\" > 公司署名：")
							.append(company)
							.append("</p></div>")
							.append("</div>");
							flag = UmpConfig.sendMessageHtml(title,  wccStore.getStoreEmail(), emailContents.toString(), configs);
							if("0".equals(flag)){//发送失败
								resp = "error";
								wccStore.setSendMail(false);
								wccStore.merge();
								break;
							}else{
								wccStore.setSendMail(true);
								wccStore.merge();
							}
						}else{
							resp="off";
						}
					}
				}
			}
	    }
        }catch(Exception e){
        	 e.printStackTrace();
        	 resp = "error";
		}
		
		return resp;
		
	}
	
	
	/**
	 * 批量发送邮件
	 * @param uiModel
	 * @param request
	 * @param content
	 * @param pks
	 * @return
	 */
	@RequestMapping(value = "checkSendEmail", method = RequestMethod.POST,produces="text/html;charset=utf-8")
	@ResponseBody
	public String checkSendEmail(
			Model uiModel,
			HttpServletRequest request) {
		String resp = "";
		List<UmpConfig> configs = UmpConfig
				.findConfigsByKeyType("wccEmailConfig_"
						+ CommonUtils.getCurrentPubOperator(request).getCompany().getId());
		if(configs.size()>0){
			resp = "ok";
		}else{
			resp = "error";
		}
		return resp;
	}
	
	   
	
	/**
	 * 批量导出
	 * @param wccStore
	 * @param uiModel
	 * @param httpServletRequest
	 * @param page
	 * @param size
	 * @param organName
	 * @param platId
	 * @param storeName
	 * @param isSendMail
	 * @param starTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/batchExport", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String batchExport(
			WccStore wccStore,
			Model uiModel,
			HttpServletRequest request,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "organName", required = false) String organName,
			@RequestParam(value = "platId", required = false) String platId,
			@RequestParam(value = "storeName", required = false) String storeName,
			@RequestParam(value = "isSendMail", required = false) Boolean isSendMail,
			@RequestParam(value = "starTime", required = false) String starTime,
			@RequestParam(value = "pks", required = false) String pks,
			@RequestParam(value = "endTime", required = false) String endTime) {
			PubOperator pub = CommonUtils.getCurrentPubOperator(request);
			StringBuilder sb = new StringBuilder();
			if(platId != null){
				sb.append(platId);
				String[] ids = platId.split(",");
				for (String id : ids) {
					sb.append(id).append(",");
				}
			}else{
				int n = 1;
				List<WccPlatformUser> wccPlatformUser = null;
				if(pub.getPubRole() == null){
					wccPlatformUser =  WccPlatformUser.findAllWccPlatformUsers(pub);
					for (WccPlatformUser wccPlat : wccPlatformUser) {
						sb.append(wccPlat.getId());
						if(n != wccPlatformUser.size()){
							sb.append(",");
						}
						n++;
					}
				}else{
					wccPlatformUser = CommonUtils.set2List(pub.getPlatformUsers());
					for (WccPlatformUser wccPlat : wccPlatformUser) {
						sb.append(wccPlat.getId());
						if(n != wccPlatformUser.size()){
							sb.append(",");
						}
						n++;
					}
				}
			}
			
			int lo = (int)WccStore.countWccStoresByFiled(organName, sb.toString(), storeName,isSendMail, starTime, endTime,CommonUtils.getCurrentPubOperator(request));
			List<WccStore> list = WccStore.findWccStoreEntriesByFiled(0,lo, organName,sb.toString(),storeName,isSendMail,starTime,endTime,pks,CommonUtils.getCurrentPubOperator(request));
			
			// 创建Excel的工作书册 Workbook,对应到一个excel文档
			HSSFWorkbook wb = new HSSFWorkbook();

			// 创建Excel的工作sheet,对应到一个excel文档的tab
			HSSFSheet sheet = wb.createSheet("sheet1");

			// 设置excel每列宽度
			//sheet.setColumnWidth(0, 4000);
			//sheet.setColumnWidth(1, 3500);

			// 创建字体样式
			HSSFFont font = wb.createFont();
			font.setFontName("Verdana");
			font.setBoldweight((short) 100);
			font.setFontHeight((short) 300);
			font.setColor(HSSFColor.BLUE.index);

			// 创建单元格样式
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			// 设置边框
			style.setBottomBorderColor(HSSFColor.RED.index);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setFont(font);// 设置字体

			// 写表格头
			// 行
			int rowNumber = 1;
			// 列
			if(null!=list){
			
			for (int i = 0; i < list.size(); i++) {
				// 第一行信息
				WccStore store = list.get(i);
				List<Map<String, Object>> list_map = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = new HashMap<String, Object>();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				StringBuilder string = new StringBuilder();
				for(WccPlatformUser wccPlat:store.getPlatformUsers()){
					string.append(wccPlat.getAccount()+",");
				}
				map.put("公众平台",string.deleteCharAt(string.lastIndexOf(",")).toString());
				list_map.add(map);
				map.put("门店名称", store.getStoreName()!=null?store.getStoreName():"");
				list_map.add(map);
				map.put("门店所属", store.getOrganization().getName()!=null?store.getOrganization().getName():"");
				list_map.add(map);
				map.put("门店地址", store.getStoreAddres()!=null?store.getStoreAddres():"");
				list_map.add(map);
				map.put("门店邮箱", store.getStoreEmail()!=null?store.getStoreEmail():"");
				list_map.add(map);
				map.put("门店电话", store.getStorePhone()!=null?store.getStorePhone():"");
				list_map.add(map);
				map.put("门店链接", store.getStoreUrl()!=null?store.getStoreUrl():"");
				list_map.add(map);
				String storeAuditing = "";
					if(store.getStoreAuditing()==0){
						storeAuditing ="未审核";
					}else if(store.getStoreAuditing()==1){
						storeAuditing ="已审核";
					}else if(store.getStoreAuditing()==2){
						storeAuditing ="审核未通过";
					}
			
				map.put("状态", storeAuditing);
				list_map.add(map);
				map.put("经度", store.getStoreLngx()!=null?store.getStoreLngx():"");
				list_map.add(map);
				map.put("纬度", store.getStoreLaty()!=null?store.getStoreLaty():"");
				list_map.add(map);
				map.put("是否关联二维码",store.getIsStoreCode()?"是":"否");
				list_map.add(map);
				map.put("组织架构",store.getOrganization().getName());
				list_map.add(map);
				map.put("营业时间",store.getStoreText()!=null?store.getStoreText():"");
				list_map.add(map);
				map.put("创建时间", DateUtil.formatDateAndTime(store.getCreateTime()));
				list_map.add(map);
				HSSFRow firstrow = sheet.createRow(0); // 下标为0的行开始
				int CountColumnNum = list_map.size();

				HSSFCell[] firstcell = new HSSFCell[CountColumnNum];
				String[] names = new String[CountColumnNum];
				names[0] ="公众平台";
				names[1] ="门店名称";
				names[2] ="门店所属";
				names[3] ="门店地址";
				names[4] ="门店邮箱";
				names[5] ="门店电话";
				names[6] ="门店链接";
				names[7] ="状态";
				names[8] ="经度";
				names[9] ="纬度";
				names[10] ="是否关联二维码";
				names[11] ="组织架构";
				names[12] ="营业时间";
				names[13] ="创建时间";
				for (int j = 0; j < CountColumnNum; j++) {
					firstcell[j] = firstrow.createCell(j);
					firstcell[j].setCellValue(new HSSFRichTextString(names[j]));
				}
				HSSFRow row = sheet.createRow((int) rowNumber);// 创建一行
				// HSSFCell cell = null;// 创建列

				for (int j = 0; j < list_map.size(); j++) {
					Map<String, Object> map_s = list_map.get(j);
					for (String key : map_s.keySet()) {
						for (int k = 0; k < names.length; k++) {
							String ss = names[k];
							if (ss.equals(key)) {
								row.createCell((short) k).setCellValue(
										(String) map.get(key));
								break;
							}

						}
					}

				}
				rowNumber++;
			}
			}
			String wxCtxPath = request.getSession().getServletContext().getRealPath(Global.DOWNLOAD_BASE_PATH);
			File file = new File(wxCtxPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String fileName_ = System.currentTimeMillis() + "workbook.xls";
			String fileName = wxCtxPath+"/" + fileName_;
			FileOutputStream os = null;
			try {
				os = new FileOutputStream(fileName);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}finally{
				try {
					if(wb!=null){
						wb.write(os);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					if(os!=null){
						os.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
			String resps = "";
			try {
				resps = "."+Global.DOWNLOAD_BASE_PATH+"/" + fileName_;
			} catch (Exception e) {
				resps = "error";
			}
			return resps;
	}
	
	
	/**
	 * 文件导入
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "importExcel", method = RequestMethod.POST,produces="text/html;charset=utf-8")
	public String importExcel(HttpServletRequest request,Model model,
			@RequestParam(value = "importFile") MultipartFile file) {
		String msg ="";
		Map<String, List<Map>> errorMap = new HashMap<String, List<Map>>();
		String[] title = { "出错行", "出错列", "出错原因" };
		String[] colums = {"rows","clos","cause"};
		HttpSession session= request.getSession();
		if(session.getAttribute("importmsg")!=null)
		{
			session.removeAttribute("importmsg");
		}
		
		/** 获取文件的后缀 **/
		String suffix = file.getOriginalFilename().substring(
				file.getOriginalFilename().lastIndexOf("."));
		if (!suffix.equals(".xls")) {
			// 文件格式不正确
			msg="文件格式不正确";
			session.setAttribute("importmsg", msg);
			return "redirect:/wccstores?page=1&size=10";
		}
		PubOperator  user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		List<Map> listerror = new ArrayList<Map>();
		InputStream in = null;
		try {
			in = file.getInputStream();
			List<Map<String,Object>> list = ExcelUtil.parseExcel(in);
			if(list==null||list.size() < 1){
				msg="文件格式异常";
				session.setAttribute("importmsg", msg);
				return "redirect:/wccstores?page=1&size=10";
			}
			Set<WccStore> setWccStore = new HashSet<WccStore>();
			String name = null;
			String address = null;
			String email = null;
			String phone = null;
			String url = null;
			String isCode = null;
			String remark = null;
			String organName  = null;
			String platName = null;
			
			int rows =1;
			for (Map<String, Object> kmap:list) {
				//int clos =1;
				WccStore wccStore = new WccStore();
				wccStore.setCreateTime(new Date());
				wccStore.setIsDeleted(true);
				wccStore.setCompany(company);
				for(Entry<String, Object> entity:kmap.entrySet()){
					Map maps = new HashMap();
					name = entity.getKey().equals("门店名称")?
							entity.getValue()!=null?String.valueOf(entity.getValue()).trim():"":null;
					if(name!=null){
						if(name.trim().equals("")){
							maps.put("rows", rows);
							maps.put("clos", 3);
							maps.put("cause", "门店名称不能为空");
							listerror.add(maps);
						}else{
							List<WccStore> wccStores = WccStore.findAllWccStoresByStoreNameAndCompany(name,user.getCompany());
							if(null != wccStores && wccStores.size() > 0){
								maps.put("rows", rows);
								maps.put("clos", 3);
								maps.put("cause", "门店名称存在！");
								listerror.add(maps);
							}else if(null == wccStores){
								maps.put("rows", rows);
								maps.put("clos", 3);
								maps.put("cause", "门店名称不能为空");
								listerror.add(maps);
							}else{	
								wccStore.setStoreName(name);
							}
						}
					}
					
					address =entity.getKey().equals("门店地址")?
							entity.getValue()!=null?String.valueOf(entity.getValue()).trim():"":null;
					
					if(address!=null){
						if(address.trim().equals("")){
							maps.put("rows", rows);
							maps.put("clos", 4);
							maps.put("cause", "门店地址不能为空");
							listerror.add(maps);
						}else{
							wccStore.setStoreAddres(address);
							String result=HttpClientUtil.getLocation(address);
							Map<String, String> lct = HttpClientUtil.toMap(result);
							String states = lct.get("status");
							if (states.equals("0")) {
								Map<String, String> res = HttpClientUtil.toMap(lct
										.get("result"));
								Map<String, String> re = HttpClientUtil.toMap(res
										.get("location"));
								String lat = re.get("lat");// 纬度
								String lng = re.get("lng");// 经度
								wccStore.setStoreLaty(lat);
								wccStore.setStoreLngx(lng);
							}else{
								maps.put("rows", rows);
								maps.put("clos", 4);
								maps.put("cause", "门店地址调用经纬度接口失败");
								listerror.add(maps);
							}
						}
					}
					
					email =entity.getKey().equals("门店邮箱")?
							entity.getValue()!=null?String.valueOf(entity.getValue()).trim():"":null;
					if(email!=null){
						if(email.trim().equals("")){
							maps.put("rows", rows);
							maps.put("clos", 5);
							maps.put("cause", "门店邮箱不能为空");
							listerror.add(maps);
						}else{
							Pattern regex = Pattern.compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
						    Matcher matcher = regex.matcher(email);
						    if(!matcher.matches()){
						    	maps.put("rows", rows);
								maps.put("clos", 5);
								maps.put("cause", "门店邮箱格式不正确");
								listerror.add(maps);
						    }else{
						    	wccStore.setStoreEmail(email);
						    }
						}
						
					}
					phone =entity.getKey().equals("门店电话")?
							entity.getValue()!=null?String.valueOf(entity.getValue()).trim():"":null;
					if(phone!=null){
						if(phone.trim().equals("")){
							maps.put("rows", rows);
							maps.put("clos", 6);
							maps.put("cause", "门店电话不能为空");
							listerror.add(maps);
						}else{
							Pattern regex = Pattern.compile("^(\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$");
						    Matcher matcher = regex.matcher(phone);
						    if(!matcher.matches()){
						    	maps.put("rows", rows);
								maps.put("clos", 6);
								maps.put("cause", "门店电话应为11-12位数字或座机号码");
								listerror.add(maps);
						    }else{
						    	wccStore.setStorePhone(phone);
						    }
						}
						
					}
					url =entity.getKey().equals("门店URL")?
							entity.getValue()!=null?String.valueOf(entity.getValue()).trim():"":null;
					if(url!=null){
							wccStore.setStoreUrl(url);
					}
				
					isCode = entity.getKey().equals("是否关联二维码")?
							entity.getValue()!=null?String.valueOf(entity.getValue()).trim():"":null;
					if(isCode!=null){
						if(isCode.trim().equals("")){
							maps.put("rows", rows);
							maps.put("clos", 8);
							maps.put("cause", "是否关联二维码不能为空");
							listerror.add(maps);
						}else{
							if("是".equals(isCode)){
								 if(wccStore.getPlatformUsers()!=null){
									 StringBuilder sb = new StringBuilder();
									 for(WccPlatformUser wccPlat:wccStore.getPlatformUsers()){
										    String appId = wccPlat.getAppId();
											String appSecret = wccPlat.getAppSecret();
											WccQrcode wccQrcodes = Qrcode.createOrgetCode(Qrcode.STORE, wccPlat, appId, appSecret, request);
											if(null!=wccQrcodes){
												sb.append(wccQrcodes.getSceneId()).append(",");
												wccStore.setStoreCodeUrl(wccQrcodes.getCodeUrl());  //已废除
												wccStore.setIsStoreCode(true);
											}
									 }
									 wccStore.setSceneId(sb.deleteCharAt(sb.lastIndexOf(",")).toString());
								 }
							}else if("否".equals(isCode)){
								wccStore.setSceneId("");
								wccStore.setIsStoreCode(false);
							}else{
								maps.put("rows", rows);
								maps.put("clos", 8);
								maps.put("cause", "请填写'是'或'否'");
								listerror.add(maps);
							}
						}
						
					}
					remark = entity.getKey().equals("营业时间")?
							entity.getValue()!=null?String.valueOf(entity.getValue()).trim():"":null;
					if(remark!=null){
							wccStore.setStoreText(remark);
					}
					organName = entity.getKey().equals("组织架构")?
							entity.getValue()!=null?String.valueOf(entity.getValue()).trim():"":null;
					if(organName!=null){
						if(!"".equals(organName.trim())){
							try{
								PubOrganization pubOrgan = PubOrganization.findPubOrganizationByName(organName.trim(),CommonUtils.getCurrentPubOperator(request));
								if(pubOrgan==null){
									maps.put("rows", rows);
									maps.put("clos", 2);
									maps.put("cause", "填写组织架构名称不存在");
									listerror.add(maps);
								}else{
									wccStore.setOrganization(pubOrgan);
								}
							}catch(Exception e) {
								e.printStackTrace();
								maps.put("rows", rows);
								maps.put("clos", 2);
								maps.put("cause", "填写组织架构名称不存在");
								listerror.add(maps);
							}
						}else{
							maps.put("rows", rows);
							maps.put("clos", 2);
							maps.put("cause", "组织架构名称不能为空");
							listerror.add(maps);
						}
						
					}
					platName =  entity.getKey().equals("公众平台")?
							entity.getValue()!=null?String.valueOf(entity.getValue()).trim():"":null;
					if(platName!=null){
						if(!"".equals(platName.trim()) ){
							String[] platNames = platName.split(",");
							Set<WccPlatformUser>  wccPlat = new HashSet<WccPlatformUser>();
							for(String names:platNames){
								try{
									WccPlatformUser platformUser = WccPlatformUser.findWccPlatformUserByOneAccount(names,user.getCompany().getId());
									if(platformUser==null){
										maps.put("rows", rows);
										maps.put("clos", 1);
										maps.put("cause", "填写公众平台不存在");
										listerror.add(maps);
									}else{
										wccPlat.add(platformUser);
									}
								}catch(Exception e) {
									e.printStackTrace();
									maps.put("rows", rows);
									maps.put("clos", 1);
									maps.put("cause", "填写公众平台不存在");
									listerror.add(maps);
								}
							}
							wccStore.setPlatformUsers(wccPlat);
						}else{
							maps.put("rows", rows);
							maps.put("clos", 1);
							maps.put("cause", "填写公众平台不能为空");
							listerror.add(maps);
						}
					}
					//clos++;
				}
				
				wccStore.setStoreAuditing(0);
				setWccStore.add(wccStore);
				rows++;
			}
			if(listerror.size()>0){
				errorMap.put("门店错误信息", listerror);
				String fileName = String.valueOf(System.currentTimeMillis()/1000)+"_error.xls";
				String wxCtxPath = request.getSession().getServletContext()
						.getRealPath(Global.DOWNLOAD_BASE_PATH);
				ExcelUtil.getExcelFile(title, colums, errorMap, fileName,wxCtxPath);
				session.setAttribute("importmsg", "您导入的信息有误，请点击<a href=\"javascript:window.location.href='."
				+Global.DOWNLOAD_BASE_PATH+"/"+fileName+"'\"  style=color:red;text-decoration:underline;cursor:pointer;   >\""+fileName+"\"</a>文件查看错误信息");
		    }else{
				WccStore.batchPersist(setWccStore);
				session.setAttribute("importmsg", "导入成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(listerror.size()>0){
				errorMap.put("门店错误信息", listerror);
				String fileName = String.valueOf(System.currentTimeMillis()/1000)+"_error.xls";
				String wxCtxPath = request.getSession().getServletContext()
						.getRealPath(Global.DOWNLOAD_BASE_PATH);
				ExcelUtil.getExcelFile(title, colums, errorMap, fileName,wxCtxPath);
				session.setAttribute("importmsg", "您导入的信息有误，请点击<a href=\"javascript:window.location.href='."
				+Global.DOWNLOAD_BASE_PATH+"/"+fileName+"'\"  style=color:red;text-decoration:underline;cursor:pointer;   >\""+fileName+"\"</a>文件查看错误信息");
		    }else{
		    	session.setAttribute("importmsg", "导入异常,请检查文件数据是否正确");
		    }
			
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "redirect:/wccstores?page=1&size=10";
	}
	
	
	
	
	@RequestMapping(value = "/excuteClean", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public  String excuteClean(HttpServletRequest request,Model uiModel) {
		HttpSession session= request.getSession();
		if(session.getAttribute("importmsg")!=null){
			session.removeAttribute("importmsg");
		}
		return null;
	}
	

	@RequestMapping(value = "/seaPrcode", method = RequestMethod.GET, produces = "text/html")
	public String  seaPrcode(Model uiModel,@RequestParam(value = "id", required = false) Long id
			,HttpServletRequest request){
		WccStore wccStore = WccStore.findWccStore(id);
		String[] scenIds = null;
		if(wccStore != null){
			scenIds  = wccStore.getSceneId().split(",");
		}
		List<WccQrcode> listQrcode = new ArrayList<WccQrcode>();
		WccQrcode  wccQrcode = null;
		PubOperator pub = CommonUtils.getCurrentPubOperator(request);
		WccPlatformUser  wccPlatform = null;
		for (String scenId : scenIds) {
			if(null!=scenId && !"".equals(scenId)){
				if(pub.getPubRole() != null){
					List<WccPlatformUser> wccPlatList = CommonUtils.set2List(PubOperator.findPubOperator(pub.getId()).getPlatformUsers());
					for (WccPlatformUser wccPlatformUser : wccPlatList) {
						wccPlatform = WccPlatformUser.findWccPlatformUser(wccPlatformUser.getId());
						wccQrcode = WccQrcode.findWccQrcodeByScendIdAndPlatform(Integer.parseInt(scenId),wccPlatform);
					}
				}else{
					wccQrcode = WccQrcode.findWccQrcodeByScendId("2",Integer.parseInt(scenId));
				}
				if(null != wccQrcode){
					listQrcode.add(wccQrcode);
				}
			}
		}
		String wxCtxPath = Global.IMAGE_QRCOED_FULL_PATH + Global.IMAGE_QRCOED_PATH;
		uiModel.addAttribute("wccQrcode", listQrcode);
		uiModel.addAttribute("wxCtxPath", wxCtxPath);
		return "wccstores/showPrcode";
	}
	
}

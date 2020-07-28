package com.nineclient.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.nineclient.constant.NotifyMsg;
import com.nineclient.model.UmpAuthority;
import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpCompanyService;
import com.nineclient.model.UmpProduct;
import com.nineclient.model.UmpVersion;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.PageModel;

@RequestMapping("/umpversions")
@Controller
@RooWebScaffold(path = "umpversions", formBackingObject = UmpVersion.class)
public class UmpVersionController {
	@ResponseBody
	@RequestMapping(value = "createVersion", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String createVersion(
			UmpVersion umpVersion,
			@RequestParam(value = "productId", required = false) Long productId,
			Model uiModel,
			@RequestParam(value = "authortyId", required = false) String authortyId,
			HttpServletRequest httpServletRequest, HttpServletResponse response) {
		String umpVersionName = umpVersion.getVersionName() == null ? ""
				: umpVersion.getVersionName().trim();
		authortyId = authortyId == null ? "" : authortyId.trim();
		try {
			if(StringUtils.isEmpty(productId)||productId==-1){
				return NotifyMsg.FIALED+",请选择产品";
			}
			if (StringUtils.isEmpty(umpVersionName)
					|| StringUtils.isEmpty(authortyId)) {
				return NotifyMsg.FIALED+",菜单不能为空";
			} else if (!StringUtils.isEmpty(umpVersionName)
					&& StringUtils.isEmpty(authortyId)) {
				List<UmpVersion> list = UmpVersion
						.findAllUmpVersionsByName(umpVersionName,productId);
				if (list != null && list.size() > 0) {
				return NotifyMsg.FIALED+",产品名称不能重复";
				}
			} else {
				List<UmpAuthority> list = UmpAuthority
						.findUmpAuthorityByIds(authortyId);
				Set<UmpAuthority> sets = new HashSet<UmpAuthority>();
				sets.addAll(list);
				umpVersion.setUmpAuthoritys(sets);
				UmpProduct product = UmpProduct.findUmpProduct(productId);
				umpVersion.setProduct(product);
				umpVersion.setCreateTime(new Date());
				uiModel.asMap().clear();
				umpVersion.persist();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return NotifyMsg.EXCEPTION;
		} 
		return NotifyMsg.SUCCESS;
	}

	/**
	 * 跳转页面
	 * 
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "listPage", produces = "text/html")
	public String listPage(Model uiModel,@RequestParam(value = "displayId", required = false) Long displayId,
			HttpServletRequest request) {
		uiModel.addAttribute("productList", UmpProduct.findAllUmpProducts(null));
		if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(request);
	    }
		request.getSession().setAttribute("displayId", displayId);
		return "umpversions/list";
	}

	/**
	 * 版本列表
	 * 
	 * @param page
	 * @param size
	 * @param sortFieldName
	 * @param sortOrder
	 * @param uiModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "versionList", produces = "text/html;charset=utf-8")
	public String versionList(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {
		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1)
				* sizeNo;
		int pageNo = page == null ? 0 : page.intValue();
		List<UmpVersion> umpversions = UmpVersion.findUmpVersionEntries(
				firstResult, sizeNo, sortFieldName, sortOrder);
		Long count = UmpVersion.countUmpVersions();
		PageModel<UmpVersion> pageMode = new PageModel<UmpVersion>();
		pageMode.setDataJson(UmpVersion.toJsonArray(umpversions));
		pageMode.setTotalCount(count.intValue());
		pageMode.setPageNo(pageNo);
		pageMode.setPageSize(sizeNo);
		return pageMode.toJson();
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @param uiModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete", produces = "text/html;charset=utf-8")
	public String deleteVersion(@RequestParam("id") Long id, Model uiModel) {
		try {
			UmpVersion umpVersion = UmpVersion.findUmpVersion(id);
			Long count = UmpCompanyService.findUmpCompanyServiceByVersionId(id);
			if(count>0){
				return "已有公司申请过的版本不能删除";
			}
			umpVersion.remove();
		} catch (Exception e) {
			e.printStackTrace();
			return NotifyMsg.EXCEPTION;
		}
		return NotifyMsg.SUCCESS;
	}

	/**
	 * 修改页面
	 * 
	 * @param id
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "updateForm", produces = "text/html")
	public String updateVersionForm(@RequestParam("id") Long id, Model uiModel) {
		populateEditForm(uiModel, UmpVersion.findUmpVersion(id));
		System.out.println("------>" + id);
		return "umpversions/update";
	}

	/**
	 * 更新
	 * 
	 * @param umpVersion
	 * @param productId
	 * @param authortyId
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String updateVersion(
			UmpVersion umpVersion,
			@RequestParam(value = "productId", required = false) Long productId,
			@RequestParam(value = "authortyId", required = false) String authortyId,
			Model uiModel, HttpServletRequest httpServletRequest) {
		try {
			UmpProduct product = UmpProduct.findUmpProduct(productId);
			if (StringUtils.isEmpty(authortyId) || authortyId.trim().equals("")) {
				populateEditForm(
						uiModel,
						umpVersion = UmpVersion.findUmpVersion(umpVersion
								.getId()));
				//uiModel.addAttribute("msg", "请选择菜单");
				return "请选择菜单";
			}
			List<UmpAuthority> list = UmpAuthority
					.findUmpAuthorityByIds(authortyId);
			if (list == null || list.size() < 1) {
				populateEditForm(
						uiModel,
						umpVersion = UmpVersion.findUmpVersion(umpVersion
								.getId()));
				return "请选择菜单";
			}
			Set<UmpAuthority> sets = new HashSet<UmpAuthority>();
			sets.addAll(list);
			// umpVersion.setUmpAuthoritys(null);
			// umpVersion.setUmpAuthoritys(sets);
			addDateTimeFormatPatterns(uiModel);
			uiModel.asMap().clear();
			umpVersion = UmpVersion.findUmpVersion(umpVersion.getId());
			umpVersion.setProduct(product);
			umpVersion.setUmpAuthoritys(sets);
			umpVersion.setCreateTime(new Date());
			umpVersion.merge();
		} catch (Exception e) {
			e.printStackTrace();
			return NotifyMsg.EXCEPTION;
		}
		return NotifyMsg.SUCCESS;
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new UmpVersion());
		return "umpversions/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("umpversion", UmpVersion.findUmpVersion(id));
		uiModel.addAttribute("itemId", id);
		return "umpversions/show";
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, UmpVersion.findUmpVersion(id));
		return "umpversions/update";
	}

	/**
	 * 根据产品加载菜单
	 * 
	 * @param productId
	 * @param uiModel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "tree", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String tree(
			@RequestParam(value = "versionId", required = false) Long versionId,
			@RequestParam(value = "productId", required = false) Long productId,
			Model uiModel, HttpServletRequest request,
			HttpServletResponse response) {
		StringBuffer strs = new StringBuffer();
		int num = 0;
		List<UmpAuthority> authoritys = UmpAuthority
				.findAllUmpAuthoritysByProductId(productId);

		List<UmpAuthority> authoritysChecked = null;
		if (versionId != null && !versionId.equals("")) {
			authoritysChecked = UmpAuthority
					.findAllUmpAuthoritysByVersion(versionId);
		}
		for (UmpAuthority auth : authoritys) {
			String str = "id:" + auth.getId() + ",pId:" + auth.getParentId()
					+ ",name:\"" + auth.getDisplayName() + "\"" + ",open:true";
			if (authoritysChecked != null && authoritysChecked.contains(auth)) {
				str = str + ",checked:true";
			} else {
				str = str + ",checked:false";
			}

			if (num == 0) {
				strs.append("{" + str + "}");
			} else {
				strs.append(",{" + str + "}");
			}
			num++;
		}
		return strs.toString();
	}
	/**
	 * 根据产品id查询
	 * @param productId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="loadVersionByProductId",method=RequestMethod.POST,produces="text/html;charset=utf-8")
	public String loadVersionByProductId(@RequestParam(value="productId")Long productId){
		List<UmpVersion> versions = UmpVersion.findAllVersionsbuyProductId(productId);
		String json = "[";
		boolean flag=true;
		for (UmpVersion umpversion : versions) {
			if(flag){
				flag=false;
				json+="{id:"+umpversion.getId()+",name:\'"+umpversion.getVersionName()+"\'}";
			}else{
				json+=",{id:"+umpversion.getId()+",name:\'"+umpversion.getVersionName()+"\'}";
			}
		}
		json+="]";
		return json;
	}
	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"umpVersion_createtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, UmpVersion umpVersion) {
		uiModel.addAttribute("umpVersion", umpVersion);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("productList", UmpProduct.findAllUmpProducts(null));
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

}

package com.nineclient.web;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.nineclient.constant.NotifyMsg;
import com.nineclient.constant.UmpCheckStatus;
import com.nineclient.model.UmpBrand;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpParentBusinessType;
import com.nineclient.model.VocBrand;
import com.nineclient.model.VocKeyWord;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.RedisCommonHelper;

@RequestMapping("/umpbrands")
@Controller
@RooWebScaffold(path = "umpbrands", formBackingObject = UmpBrand.class)
public class UmpBrandController {
	private static final Logger logger = Logger.getLogger(UmpBrandController.class);
	@RequestMapping(value = "listPage", produces = "text/html")
	public String listPage(@RequestParam(value = "displayId", required = false) Long displayId,
			HttpServletRequest request,Model model,
			@RequestParam(value="active",required=false) String active,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size) {
		if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(request);
	    }
		request.getSession().setAttribute("displayId", displayId);
		model.addAttribute("active", active);
		return "umpbrands/list";
	}

	@ResponseBody
	@RequestMapping(value = "create", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String createBrand(
			UmpBrand umpBrand,
			BindingResult bindingResult,
			Model uiModel,
			@RequestParam(value = "parentBusinessId", required = false) Long parentBusinessId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			HttpServletRequest httpServletRequest) {
		String name = umpBrand.getBrandName();
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(name.trim())) {
			return NotifyMsg.FIALED + ",品牌名称不能为空";
		}
		List<UmpBrand> listBrands = UmpBrand.findAllUmpBrandsByName(
				umpBrand.getBrandName(), null);
		if (listBrands != null && listBrands.size() > 0) {
			return NotifyMsg.FIALED + "品牌名称不能重复";
		}
		if (StringUtils.isEmpty(parentBusinessId) || parentBusinessId == -1) {
			return NotifyMsg.FIALED + "请选择行业";
		}
		try {
			UmpParentBusinessType parentBusiness = UmpParentBusinessType
					.findUmpParentBusinessType(parentBusinessId);
			umpBrand.setBusiness(parentBusiness);
			umpBrand.setCheckStatus(UmpCheckStatus.已审核);
			umpBrand.setCreateTime(new Date());
			umpBrand.setIsDeleted(false);
			uiModel.asMap().clear();
			umpBrand.persist();
		} catch (Exception e) {
			e.printStackTrace();
			return NotifyMsg.EXCEPTION;
		}
		return NotifyMsg.SUCCESS;
	}

	/**
	 * 品牌列表
	 * 
	 * @param page
	 * @param size
	 * @param businessTypeId
	 * @param brandName
	 * @param keyName
	 * @param sortFieldName
	 * @param sortOrder
	 * @param uiModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list", produces = "text/html;charset=utf-8")
	public String barndList(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "businessTypeId", required = false) Integer businessTypeId,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "keyName", required = false) String keyName,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {
		uiModel.asMap().clear();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("brandName", brandName);
		populateEditForm(uiModel, new UmpBrand());
		map.put("businessTypeId",
				(businessTypeId == null || businessTypeId == -1) ? ""
						: businessTypeId + "");
		map.put("keyName", keyName);
		// form 查询条件 绑定
		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1)
				* sizeNo;
		int pageNo = page == null ? 0 : page.intValue();
		List<UmpBrand> list = UmpBrand.findUmpBrandEntries(firstResult, sizeNo,
				sortFieldName, sortOrder, true, map);
		Long count = UmpBrand.countUmpBrands(map);
		PageModel<UmpBrand> pageMode = new PageModel<UmpBrand>();
		pageMode.setDataJson(UmpBrand.toJsonArray(list));
		pageMode.setPageNo(pageNo);
		pageMode.setPageSize(sizeNo);
		pageMode.setTotalCount(count.intValue());

		// //根据公司ID查询
		// uiModel.addAttribute("vocbrands", null);
		//
		// uiModel.addAttribute("umpcheckstatuses",
		// Arrays.asList(UmpCheckStatus.values()));
		// uiModel.addAttribute("umpbusinesstypes",
		// UmpParentBusinessType.findAllUmpParentBusinessTypes());
		// uiModel.addAttribute("isCheckPage", false);
		// addDateTimeFormatPatterns(uiModel);
		return pageMode.toJson();
	}

	/**
	 * 查询审核品牌
	 * 
	 * @param umpBrand
	 * @param page
	 * @param size
	 * @param sortFieldName
	 * @param sortOrder
	 * @param uiModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "querCheckList", produces = "text/html;charset=utf-8")
	public String querCheckList(
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "companyCode", required = false) String companyCode,
			@RequestParam(value = "businessTypeId", required = false) Integer businessTypeId,
			@RequestParam(value = "keyName", required = false) String keyName,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "isCheckPage", required = false) Boolean isCheckPage,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {

		PageModel<Map> pageMode = new PageModel<Map>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("brandName", brandName);
			map.put("companyCode", companyCode);
			map.put("keyName", keyName);
			map.put("businessTypeId",
					(businessTypeId == null || businessTypeId == -1) ? ""
							: businessTypeId + "");
			// map.put("businessTypeId", ((UmpBusinessType)
			// umpBrand.getBusinesses().toArray()[0]).getId()+"");
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			int pageNo = page == null ? 0 : page.intValue();
			List<Map> list = VocBrand.findVocBrandCheckStatus(firstResult,
					sizeNo, map);
			Integer count = VocBrand.countVocBrandCheckStatus(map);
			JSONArray json =JSONArray.fromObject(list);
			pageMode.setDataJson(json.toString());
			pageMode.setPageNo(pageNo);
			pageMode.setPageSize(sizeNo);
			pageMode.setTotalCount(count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageMode.toJson();
	}

	/**
	 * 修改页面跳转
	 * 
	 * @param id
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, UmpBrand.findUmpBrand(id));
		return "umpbrands/update";
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @param page
	 * @param size
	 * @param uiModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String delete(@RequestParam(value = "id") Long id, Model uiModel) {
		try {
			UmpBrand umpBrand = UmpBrand.findUmpBrand(id);
			umpBrand.remove();
			uiModel.asMap().clear();
		} catch (Exception e) {
			e.printStackTrace();
			return NotifyMsg.EXCEPTION;
		}
		return NotifyMsg.SUCCESS;
	}

	/**
	 * 审核
	 * 
	 * @param id
	 * @param uiModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkStatus", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String checkStatus(@RequestParam(value = "id") Long id,
			@RequestParam(value = "status") Long status, Model uiModel) {
		UmpCheckStatus st = status == 1 ? UmpCheckStatus.已审核
				: UmpCheckStatus.审核不通过;
		try {
			VocKeyWord keyWord = VocKeyWord.findVocKeyWordById(id);
			keyWord.setStatus(st);
			keyWord.merge();
			VocBrand vocBrand = VocBrand.findVocBrand(keyWord.getVocBrandId());
			uiModel.asMap().clear();
			UmpParentBusinessType business = vocBrand
					.getUmpParentBusinessType();
			if(st == UmpCheckStatus.已审核){ //加入爬虫队列
				 /*try{
				  WebServiceClient.getInstatnce().addKeyWordTask(vocBrand, keyWord);
				 }catch(Exception e){
					 e.printStackTrace();
				 }*/
				addKeyWordTask(vocBrand, keyWord);
			}

			// 数据匹配
			List<UmpBrand> umpBrands = UmpBrand.findAllUmpBrandsByBusinessId(
					business.getId(), vocBrand.getBrandName());
			String vocBrandName = vocBrand.getBrandName();
			String vocBrandKeyName = keyWord.getName() == null ? ""
					: keyWord.getName();
			if (umpBrands != null && umpBrands.size() > 0) {
				for (UmpBrand umpBrand : umpBrands) {// 同一行业不能有相同的品牌
					String umpBrandKeyName = umpBrand.getKeyName() == null ? ""
							: umpBrand.getKeyName();
					if (!umpBrandKeyName.contains(vocBrandKeyName)) {
						StringBuilder keyBuff = new StringBuilder(
								umpBrandKeyName);
								keyBuff.append("\r\n" + vocBrandKeyName);
						umpBrand.setKeyName(keyBuff.toString());
						umpBrand.merge();
					}
				}
			} else {
				// 品牌不同，新增品牌关键词
				UmpBrand numpBrand = new UmpBrand();
				numpBrand.setBusiness(business);
				numpBrand.setBrandName(vocBrandName);
				numpBrand.setCheckStatus(st);
				numpBrand.setCreateTime(new Date());
				numpBrand.setIsDeleted(false);
				numpBrand.setIsVisable(true);
				numpBrand.setKeyName(vocBrandKeyName);
				numpBrand.setRemark(vocBrand.getRemark());
				numpBrand.setIsDeleted(false);
				numpBrand.persist();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return NotifyMsg.FIALED;
		}
		return NotifyMsg.SUCCESS;
	}

	 public void addKeyWordTask(VocBrand brand,VocKeyWord kw){
		  JSONObject jsonObj = new JSONObject();
		   jsonObj.accumulate("id", kw.getId()+"");
		   jsonObj.accumulate("name", kw.getName());
		   jsonObj.accumulate("brandId", brand.getId()+"");
		   jsonObj.accumulate("brandName", brand.getBrandName()+"");
		   jsonObj.accumulate("channelId", "2");
		   jsonObj.accumulate("channelName", "京东商城");
		   jsonObj.accumulate("businessId", "");
		   jsonObj.accumulate("businessName", "");
		   RedisCommonHelper.getInstance().getTaskQueue(Global.VOC_NEW_KEY_WORD).add(jsonObj.toString());
	   }
	 
	@ResponseBody
	@RequestMapping(value = "batchCheck", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String batchCheck(@RequestParam(value = "id") String id,
			@RequestParam(value = "status") Long status,
			Model uiModel) {
		UmpCheckStatus st = status == 1 ? UmpCheckStatus.已审核
				: UmpCheckStatus.审核不通过;
		try {
			List<VocKeyWord> vocKeyWords = VocKeyWord.findVocKeyWordEntriesByIds(id);
			uiModel.asMap().clear();
			for(VocKeyWord keyWord:vocKeyWords){
				keyWord.setStatus(st);
				keyWord.merge();
				VocBrand vocBrand = VocBrand.findVocBrand(keyWord.getVocBrandId());
				UmpParentBusinessType business = vocBrand
						.getUmpParentBusinessType();
                //调用接口
				if(st == UmpCheckStatus.已审核){ //加入爬虫队列
					 /*try{
					  WebServiceClient.getInstatnce().addKeyWordTask(vocBrand, keyWord);
					 }catch(Exception e){
						 logger.error("批量审核关键词--新增："+e.getMessage());
					 }*/
					addKeyWordTask(vocBrand, keyWord);
				}
				
				// 数据匹配
				List<UmpBrand> umpBrands = UmpBrand
						.findAllUmpBrandsByBusinessId(business.getId(),
								vocBrand.getBrandName());
				String vocBrandName = vocBrand.getBrandName();
				String vocBrandKeyName = keyWord.getName() == null ? ""
						: keyWord.getName();
				if (umpBrands != null && umpBrands.size() > 0) {
					for (UmpBrand umpBrand : umpBrands) {// 同一行业不能有相同的品牌
						String umpBrandKeyName = umpBrand.getKeyName() == null ? ""
								: umpBrand.getKeyName();
						if (!umpBrandKeyName.contains(vocBrandKeyName)) {
							StringBuilder keyBuff = new StringBuilder(
									umpBrandKeyName);
							keyBuff.append("\r\n" + vocBrandKeyName);
							umpBrand.setKeyName(keyBuff.toString());
							umpBrand.merge();
						}
					}
				} else {
					// 品牌不同，新增品牌关键词
					UmpBrand numpBrand = new UmpBrand();
					numpBrand.setBusiness(business);
					numpBrand.setBrandName(vocBrandName);
					numpBrand.setCheckStatus(st);
					numpBrand.setCreateTime(new Date());
					numpBrand.setIsDeleted(false);
					numpBrand.setIsVisable(true);
					numpBrand.setKeyName(vocBrandKeyName);
					numpBrand.setRemark(vocBrand.getRemark());
					numpBrand.setIsDeleted(false);
					numpBrand.persist();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return NotifyMsg.FIALED;
		}
		return NotifyMsg.SUCCESS;
	}

	@RequestMapping(value = "checkStatusForm/{id}", params = "form", produces = "text/html")
	public String checkStatusForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, UmpBrand.findUmpBrand(id));
		return "umpbrands/checkStatusForm";
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new UmpBrand());
		return "umpbrands/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("umpbrand", UmpBrand.findUmpBrand(id));
		uiModel.addAttribute("itemId", id);
		return "umpbrands/show";
	}

	/**
	 * 修改品牌
	 * 
	 * @param umpBrand
	 * @param parentBusinessId
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String update(
			UmpBrand umpBrand,
			@RequestParam(value = "parentBusinessId", required = false) Long parentBusinessId,
			Model uiModel, HttpServletRequest httpServletRequest) {
		String name = umpBrand.getBrandName();
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(name.trim())) {
			return NotifyMsg.FIALED + ",品牌名称不能为空";
		}
		List<UmpBrand> listBrands = UmpBrand.findAllUmpBrandsByName(
				umpBrand.getBrandName(), umpBrand.getId());
		if (listBrands != null && listBrands.size() > 0) {
			return NotifyMsg.FIALED + "品牌名称不能重复";
		}
		if (StringUtils.isEmpty(parentBusinessId) || parentBusinessId == -1) {
			return NotifyMsg.FIALED + "请选择行业";
		}
		try {
			UmpParentBusinessType parentBusiness = UmpParentBusinessType
					.findUmpParentBusinessType(parentBusinessId);
			Long id = umpBrand.getId();
			UmpBrand brand = UmpBrand.findUmpBrand(id);
			brand.setBusiness(parentBusiness);
			brand.setBrandName(umpBrand.getBrandName());
			brand.setRemark(umpBrand.getRemark());
			brand.setKeyName(umpBrand.getKeyName());
		//	brand.setIsVisable(umpBrand.getIsVisable());
			uiModel.asMap().clear();
			brand.merge();
		} catch (Exception e) {
			e.printStackTrace();
			return NotifyMsg.EXCEPTION;
		}
		return NotifyMsg.SUCCESS;
	}

	@RequestMapping(value = "queryUmpBrandByBusinessId", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String queryBusinessByParentId(Model uiModel,
			@RequestParam(value = "id", required = false) Long id,
			HttpServletRequest request, HttpServletResponse response) {
		{
			List<UmpBrand> list = UmpBrand.findAllUmpBrandsByBusinessId(id, "");
			StringBuilder json = new StringBuilder("[");
			boolean flag = true;
			for (UmpBrand umpBrand : list) {
				if (flag) {
					flag = false;
					json.append("{id:" + umpBrand.getId() + ",name:\'"
							+ umpBrand.getBrandName() + "\'}");
				} else {
					json.append(",{id:" + umpBrand.getId() + ",name:\'"
							+ umpBrand.getBrandName() + "\'}");
				}

			}
			json.append("]");
			return json.toString();
		}
	}
	/**
	 * 更新状态
	 * @param request
	 * @param visable
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateVisable",method=RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String updateVisable(HttpServletRequest request,
			@RequestParam(value = "visable",required=false) Long visable,
			@RequestParam(value = "id",required=false) Long id) {
		String msg ="";
		try{
			UmpBrand property = UmpBrand.findUmpBrand(id);
			if(visable==1){
				property.setIsVisable(true);
			}else{
				property.setIsVisable(false);
			}
			property.merge();
			msg=NotifyMsg.SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			msg=NotifyMsg.FIALED;
		}
		return msg;
			
	}
	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"umpBrand_createtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, UmpBrand umpBrand) {
		uiModel.addAttribute("umpBrand", umpBrand);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("umpcheckstatuses",
				Arrays.asList(UmpCheckStatus.values()));
		List<UmpParentBusinessType> list = UmpParentBusinessType
				.findAllUmpParentBusinessTypes();
		uiModel.addAttribute("umpparentbusinesstypes", list);
		uiModel.addAttribute("umpcompanys", UmpCompany.findAllUmpCompanys());
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

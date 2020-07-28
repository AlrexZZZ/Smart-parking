package com.nineclient.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

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
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpBrand;
import com.nineclient.model.UmpBusinessType;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpParentBusinessType;
import com.nineclient.model.VocBrand;
import com.nineclient.model.VocKeyWord;
import com.nineclient.model.VocShop;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageModel;

@RequestMapping("/vocbrands")
@Controller
@RooWebScaffold(path = "vocbrands", formBackingObject = VocBrand.class)
public class VocBrandController {
	/**
	 * 创建品牌
	 * 
	 * @param vocBrand
	 * @param bindingResult
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addVocBrand", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String addVocBrand(
			VocBrand vocBrand,
			BindingResult bindingResult,
			@RequestParam(value = "isOn", required = false) int isOn,
			@RequestParam(value = "keyWords", required = false) String[] keyWords,
			@RequestParam(value = "businessTypes", required = false) Long[] businessTypes,
			Model uiModel, HttpServletRequest httpServletRequest) {
		vocBrand.setCreateTime(new Date());
		vocBrand.setIsDeleted(false);
		if (isOn == 1) {
			vocBrand.setIsVisable(true);
		} else {
			vocBrand.setIsVisable(false);
		}
		UmpCompany umpcompany = getUser(httpServletRequest);
		vocBrand.setStatus(UmpCheckStatus.未审核);
		UmpParentBusinessType businessType = null;
		businessType = UmpParentBusinessType
				.findUmpParentBusinessTypeByCompany(umpcompany);
		// UmpOperator umpOperator = (UmpOperator)
		// httpServletRequest.getAttribute(Global.LOGIN_OPERATOR);
		// UmpCompany company = umpOperator.getCompany();
		if(!(CommonUtils.isFullArray(keyWords)&&CommonUtils.isFullArray(businessTypes))){
			return "关键词/子行业不能为空";
		}
		//判断关键词是否重复
		
		String msg = "";
		String brandName = vocBrand.getBrandName();// 品牌唯一性验证
		
		List<VocBrand> list = VocBrand.findVocBrandEntriesByBrandName(
				brandName, umpcompany.getId());
		boolean flag = false;
		if (brandName == null || StringUtils.isEmpty(brandName.trim())) {
			flag = true;
			msg = " 品牌名称不能为空 ";
		}
		if (list != null && list.size() > 0) {
			flag = true;
			msg += " 品牌名称不能重复 ";
		}
		if (flag) {
			return msg;
		}
		List<UmpBrand> listBrand= UmpBrand.findAllUmpBrandsByBusinessId(businessType.getId(), vocBrand.getBrandName());
		//自动审核通过
		String umpBrandKeyName =null;
		String [] arr = null;
		if(listBrand!=null&&listBrand.size()==1){
			UmpBrand brand =listBrand.get(0);
			 umpBrandKeyName = brand.getKeyName() == null ? ""
					: brand.getKeyName();
			arr = umpBrandKeyName.split("\r\n");
			
		}
		List<VocKeyWord> listKey = new ArrayList<VocKeyWord>();
		for (int i = 0; i < keyWords.length; i++) {
			VocKeyWord keyWord = new VocKeyWord();
			keyWord.setCompanyId(umpcompany.getId());
			keyWord.setName(keyWords[i]);
			boolean kF=false;
			//自动审核判断
			if(arr!=null&&arr.length>0){
				for(String keyName:arr){
					if(keyName.equals(keyWords[i])){
						kF=true;
						break;
					}
				}
			}
			keyWord.setStatus(kF?UmpCheckStatus.已审核:UmpCheckStatus.未审核);
			keyWord.setCreateTime(new Date());
			keyWord.setBusinessTypeId(businessTypes[i]);
			listKey.add(keyWord);
		}
		try {
			vocBrand.setUmpCompany(umpcompany);
			vocBrand.setStatus(UmpCheckStatus.已审核);
			vocBrand.setUmpParentBusinessType(businessType);
			uiModel.asMap().clear();
			vocBrand.persist(listKey);
			msg = "成功";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "失败";
		}
		return msg;
	}

	@ResponseBody
	@RequestMapping(value = "queryVocBrand", produces = "text/html;charset=utf-8")
	public String queryVocBrandByChannelAndCompany(
			HttpServletRequest request,
			@RequestParam(value = "umpChannelId", required = false) String umpChannelId,
			@RequestParam(value = "umpCompanyId", required = false) String umpCompanyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umpChannelId", umpChannelId);
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		map.put("umpCompanyId", user.getCompany().getId());
		List<VocBrand> list = VocBrand.findVocBrandsByChannelAndCompany(map);
		String json = "[";
		boolean flag = true;
		for (VocBrand vocBrand : list) {
			if (flag) {
				flag = false;
				json += "{id:" + vocBrand.getId() + ",name:\'"
						+ vocBrand.getBrandName() + "\'}";
			} else {
				json += ",{id:" + vocBrand.getId() + ",name:\'"
						+ vocBrand.getBrandName() + "\'}";
			}
		}
		json += "]";
		return json;
	}

	@ResponseBody
	@RequestMapping(value="queryVocBrands",produces = "text/html")
    public void queryVocBrands(
			 Model uiModel,
			 @RequestParam(value = "name", required = true) String name,
			 HttpServletRequest request,
			 HttpServletResponse response
			 ) {
		
		Long count =VocBrand.findVocBrandName(CommonUtils.getCurrentPubOperator(request).getId(), name);
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		String msg = "{success:true}";
		if(null == count || count == 0){
		  msg = "{success:false}";
		}
		
		try {
			 out = response.getWriter();
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.println(msg);
			out.close();
		}
		
    }
	
	
	/**
	 * 平台级联品牌
	 * @param request
	 * @param umpChannelId
	 * @param umpCompanyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectVocBrand", produces = "text/html;charset=utf-8")
	public String selectVocBrandByChannelAndCompany(
			HttpServletRequest request,
			@RequestParam(value = "umpChannelId", required = false) Long umpChannelId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umpChannelId", umpChannelId);
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		map.put("umpCompanyId", user.getCompany().getId());
		// 查询店铺
		List<VocShop> listShop = VocShop.findAllVocShopsByCompanyAndChannel(
				umpChannelId, user.getCompany().getId());
		// 根据店铺查询品牌
		List<VocBrand> list = VocBrand.findVocBrandsByVocShop(listShop);
		String json = "[";
		boolean flag = true;
		for (VocBrand vocBrand : list) {
			if (flag) {
				flag = false;
				json += "{id:" + vocBrand.getId() + ",name:\'"
						+ vocBrand.getBrandName() + "\'}";
			} else {
				json += ",{id:" + vocBrand.getId() + ",name:\'"
						+ vocBrand.getBrandName() + "\'}";
			}
		}
		json += "]";
		return json;
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new VocBrand());
		return "vocbrands/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("vocbrand", VocBrand.findVocBrand(id));
		uiModel.addAttribute("itemId", id);
		return "vocbrands/show";
	}

	@RequestMapping(produces = "text/html")
	public String listPage(
			HttpServletRequest request,
			@RequestParam(value = "displayId", required = false) Long displayId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		if (displayId == null) {
			displayId = CommonUtils.getCurrentDisPlayId(request);
		}
		request.getSession().setAttribute("displayId", displayId);
		UmpCompany company = getUser(request);
		UmpParentBusinessType business = UmpParentBusinessType
				.findUmpParentBusinessTypeByCompany(company);
		List<UmpBusinessType> businessTypes = UmpBusinessType
				.findAllUmpBusinessTypesByParentId(business.getId());
		uiModel.addAttribute("businessTypes", businessTypes);
		return "vocbrands/list";
	}

	@ResponseBody
	@RequestMapping(value = "list", produces = "text/html;charset=utf-8")
	public String list(
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {

		UmpCompany company = getUser(httpServletRequest);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyCode", company.getCompanyCode());
		List<VocBrand> list = new ArrayList<VocBrand>();
		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1)
				* sizeNo;
		list = VocBrand.findVocBrandEntries(firstResult, sizeNo, sortFieldName,
				sortOrder, map);
		Long count = VocBrand.countVocBrands(map);
		int allCount = Integer.parseInt(count + "");
		float nrOfPages = (float) count / sizeNo;
		uiModel.addAttribute(
				"maxPages",
				(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
						: nrOfPages));

		PageModel<VocBrand> pageModel = new PageModel<VocBrand>();
		pageModel.setDataJson(VocBrand.toJsonArray(list));
		pageModel.setPageNo(page);
		pageModel.setPageSize(sizeNo);
		pageModel.setTotalCount(allCount);
		return pageModel.toJson();
	}

	@ResponseBody
	@RequestMapping(value = "queryKeyWord", produces = "text/html;charset=utf-8")
	public String queryKeyWord(
			@RequestParam(value = "id", required = false) Long id) {
		List<VocKeyWord> vocK = VocKeyWord.findVocKeyWordByBrandId(id);
		String json =null;
		try{
			JSONArray jsonArr = JSONArray.fromObject(vocK);
			json=jsonArr.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "addKeyWord", produces = "text/html;charset=utf-8")
	public String addKeyWord(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "keyWord", required = false) String keyWord) {
		VocBrand vocbrand = VocBrand.findVocBrand(id);
		String json = "";
		String keys = vocbrand.getKeyName();
		try {
			if (keys != null) {
				if (keys.contains(keyWord)) {
					json = "该关键词已存在";
				} else {
					keys += "\r\n" + keyWord;
					vocbrand.setKeyName(keys);
					vocbrand.merge();
					json = "成功";
				}
			} else {
				vocbrand.setKeyName(keyWord);
				vocbrand.merge();
				json = "成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = "失败";
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "delete", produces = "text/html;charset=utf-8")
	public String deleteKeyWord(
			@RequestParam(value = "id", required = false) Long id) {
		VocBrand vocBrand = VocBrand.findVocBrand(id);
		String msg = "";
		try {
			Set<VocShop> shops=
			vocBrand.getVocShops();
			if(shops != null && shops.size() > 0){
			  return "该品牌已关联店铺，不能删除";
			}
			vocBrand.remove();
			msg = "成功";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "失败";
		}

		return msg;
	}

	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String update(
			VocBrand vocBrand,
			@RequestParam(value = "keyWordId", required = false) Long[] keyWordId,
			@RequestParam(value = "keyWordName", required = false) String[] keyWordName,
			@RequestParam(value = "businessTypeIds", required = false) Long[] businessTypeIds,
			@RequestParam(value = "keyWords", required = false) String[] keyWords,
			@RequestParam(value = "businessTypes", required = false) Long[] businessTypes,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		UmpCompany company = getUser(httpServletRequest);
		UmpParentBusinessType businessType = null;
		businessType = UmpParentBusinessType
				.findUmpParentBusinessTypeByCompany(company);
		if(keyWordId==null&&keyWordName==null&&businessTypeIds==null){
			
		}else if(keyWordId.length==keyWordName.length&&keyWordName.length==businessTypeIds.length){
			
		   if(!CommonUtils.isRepeatFromArray(keyWordName)||!CommonUtils.isRepeatFromArray(keyWords)){
			 return "关键词重复";
		   }
		}else{
			return NotifyMsg.EXCEPTION;
		}
		if(keyWordId==null&&keyWords==null){
			return NotifyMsg.FIALED;
		}
		
		if((keyWords!=null&&CommonUtils.isFullArray(keyWords)
				&& CommonUtils.isFullArray(businessTypes))||(keyWordId!=null&&CommonUtils.isFullArray(keyWordId)
				&& CommonUtils.isFullArray(businessTypeIds))) {
			try {
				VocBrand voc = VocBrand.findVocBrand(vocBrand.getId());
				 if(null != voc && (null != keyWords && keyWords.length > 0)){
				   List<VocKeyWord> keyWordList = VocKeyWord.queryBrandKeyWordByBrandId(voc.getId());
				   if(null != keyWordList && keyWordList.size() > 0){
					 for(String kwName:keyWords){
						if(CommonUtils.isKeyWordRepeat(kwName, keyWordList)){
						 return "关键词重复";
						}
					 }
				   }
				}
				 
				boolean flag = false;
				String brandName = voc.getBrandName();
				List<VocBrand> list = VocBrand.findVocBrandEntriesByBrandName(voc.getId(),
						brandName, company.getId());
				String msg="";
				if (brandName == null || StringUtils.isEmpty(brandName.trim())) {
					flag = true;
					msg = " 品牌名称不能为空 ";
				}
				if (list != null && list.size() > 0) {
					flag = true;
					msg += " 品牌名称不能重复 ";
				}
				//当前品牌下关键词的重复
				List<String> keys = new ArrayList<String>();
				if(keyWordName!=null){
					for(String name:keyWordName){
						keys.add(name);
					}
				}
				if(keyWords!=null){
					for(String name:keyWords){
						keys.add(name);
					}
				}
				List<Long> listOldId=new ArrayList<Long>();
				if(keyWordId!=null){
					for(Long id:keyWordId){
						listOldId.add(id);
					}
				}
				Long count = VocKeyWord.findVocKeyWordUnique(vocBrand.getId(),keys, company.getId(), listOldId);
				if(count>0){
					return "关键词重复";
				}
				if (flag) {
					return msg;
				}
				List<VocKeyWord> oldList = new ArrayList<VocKeyWord>();
				List<UmpBrand> listBrand= UmpBrand.findAllUmpBrandsByBusinessId(businessType.getId(), vocBrand.getBrandName());
				//自动审核通过
				String umpBrandKeyName =null;
				String [] arr = null;
				if(listBrand!=null&&listBrand.size()==1){
					UmpBrand brand =listBrand.get(0);
					 umpBrandKeyName = brand.getKeyName() == null ? ""
							: brand.getKeyName();
					arr = umpBrandKeyName.split("\r\n");
					
				}
				if(keyWordId!=null){
					for (int i = 0; i < keyWordId.length; i++) {
						VocKeyWord oldKey = VocKeyWord
								.findVocKeyWordById(keyWordId[i]);
						oldKey.setBusinessTypeId(businessTypeIds[i]);
						boolean kF=false;
						if(arr!=null&&arr.length>0){
							for(String keyName:arr){
								if(keyName.equals(keyWordName[i])){
									kF=true;
									break;
								}
							}
							
						}
						oldKey.setStatus(kF?UmpCheckStatus.已审核:UmpCheckStatus.未审核);
						oldKey.setName(keyWordName[i]);
						oldList.add(oldKey);
					}
				}
				if(keyWords!=null){
					for (int j = 0; j < keyWords.length; j++) {
						VocKeyWord newKey = new VocKeyWord();
						newKey.setVocBrandId(voc.getId());
						newKey.setBusinessTypeId(businessTypes[j]);
						newKey.setCreateTime(new Date());
						boolean kF=false;
						if(arr!=null&&arr.length>0){
							for(String keyName:arr){
								if(keyName.equals(keyWords[j])){
									kF=true;
									break;
								}
							}
							
						}
						newKey.setStatus(kF?UmpCheckStatus.已审核:UmpCheckStatus.未审核);
						newKey.setName(keyWords[j]);
						newKey.setCompanyId(company.getId());
						oldList.add(newKey);
					}
				}
				voc.setBrandName(vocBrand.getBrandName());
				voc.setRemark(vocBrand.getRemark());
				voc.merge(oldList);
			} catch (Exception e) {
				e.printStackTrace();
				return NotifyMsg.EXCEPTION;
			}

		} else {
			return NotifyMsg.EXCEPTION;
		}
		return NotifyMsg.SUCCESS;
	}

	/**
	 * 更新状态
	 * 
	 * @param request
	 * @param visable
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateVisable", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String updateVisable(HttpServletRequest request,
			@RequestParam(value = "visable", required = false) Long visable,
			@RequestParam(value = "id", required = false) Long id) {
		String msg = "";
		try {
			VocBrand brand = VocBrand.findVocBrand(id);
			if (visable == 1) {
				brand.setIsVisable(true);
			} else {
				brand.setIsVisable(false);
			}
			brand.merge();
			msg = "成功";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "失败";
		}
		return msg;

	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, VocBrand.findVocBrand(id));
		return "vocbrands/update";
	}

	/**
	 * 根据 渠道加载店铺
	 * 
	 * @param uiModel
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "queryVocShops", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String queryVocShops(Model uiModel,
			@RequestParam(value = "id") String ids, HttpServletRequest request,
			HttpServletResponse response) {
		{
			List<VocShop> list = VocShop.findAllVocShopsByVocChannels(ids);
			StringBuilder json = new StringBuilder("[");
			boolean flag = true;
			for (VocShop vocShop : list) {
				if (flag) {
					flag = false;
					json.append("{id:" + vocShop.getId() + ",name:\'"
							+ vocShop.getName() + "\'}");
				} else {
					json.append(",{id:" + vocShop.getId() + ",name:\'"
							+ vocShop.getName() + "\'},");
				}

			}
			json.append("]");
			return json.toString();
		}
	}

	/**
	 * 根据父行业查询voc品牌
	 * 
	 * @param uiModel
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "queryVocBrandByBusinessId", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String queryVocBrandByBusinessId(Model uiModel,
			@RequestParam(value = "id", required = false) Long id,
			HttpServletRequest request, HttpServletResponse response) {
		UmpCompany company = getUser(request);
		
		List<VocBrand> list = VocBrand.findVocBrandsByBusinessType(id,company.getId());
		StringBuilder json = new StringBuilder("[");
		boolean flag = true;
		for (VocBrand vocBrand : list) {
			if (flag) {
				flag = false;
				json.append("{id:" + vocBrand.getId() + ",name:\'"
						+ vocBrand.getBrandName() + "\'}");
			} else {
				json.append(",{id:" + vocBrand.getId() + ",name:\'"
						+ vocBrand.getBrandName() + "\'}");
			}

		}
		json.append("]");
		return json.toString();
	}

	@RequestMapping(value = "deleteKey", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String deleteKeyName(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "key", required = false) Long key) {
		VocKeyWord vocKey=VocKeyWord.findVocKeyWordById(key);
		
		try{
			vocKey.remove();
		}catch(Exception e){
			e.printStackTrace();
			return NotifyMsg.EXCEPTION;
		}
		return NotifyMsg.SUCCESS;
	}

	@RequestMapping(value = "queryBrandKeyWord", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String queryBrandKeyWord(
			@RequestParam(value = "id", required = false) Long id) {
		List<VocKeyWord> list = null;
		try {
			list = VocKeyWord.queryBrandKeyWordByBrand(id);
		} catch (Exception e) {
			e.printStackTrace();
			list = new ArrayList<VocKeyWord>();
		}
		return JSONArray.fromObject(list).toString();
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"vocBrand_createtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, VocBrand vocBrand) {
		uiModel.addAttribute("vocBrand", vocBrand);
		// addDateTimeFormatPatterns(uiModel);
		// uiModel.addAttribute("umpcheckstatuses",
		// Arrays.asList(UmpCheckStatus.values()));
		// List<UmpParentBusinessType> list =
		// UmpParentBusinessType.findAllUmpParentBusinessTypes();
		// // UmpParentBusinessType pt=null;
		// // if(list!=null&&list.size()>0){
		// // pt=list.get(0);
		// // }
		// uiModel.addAttribute("umpparentbusinesstypes",
		// list);
		// // List<UmpBusinessType> listsub = new ArrayList<UmpBusinessType>();
		// // if(pt!=null){
		// //
		// listsub=UmpBusinessType.findAllUmpBusinessTypesByParentId(pt.getId());
		// // }
		// // uiModel.addAttribute("umpbusinesstypes",
		// // listsub);
		// uiModel.addAttribute("umpchannels",
		// UmpChannel.findAllChannelsbuyProductName("VOC"));
		// uiModel.addAttribute("vocshops",
		// VocShop.findAllVocShops());
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
	 * 获取公司
	 * 
	 * @param request
	 * @return
	 */
	private UmpCompany getUser(HttpServletRequest request) {
		PubOperator user = (PubOperator) request.getSession().getAttribute(
				Global.PUB_LOGIN_OPERATOR);
		UmpCompany company = user.getCompany();
		return company;
	}
	
	@RequestMapping(value = "tree", produces = "text/html;charset=utf-8")
	@ResponseBody
	public  String tree(Model uiModel, HttpServletRequest request,
			@RequestParam(value = "shoplIds") String ids,
			HttpServletResponse response){
		StringBuffer strs = new StringBuffer();
		int num = 0;
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		List<VocBrand> list = VocBrand.findVocBrandbyCompanyAndVocShopIds(company, ids);
		if(null != list && list.size() > 0){
			for (VocBrand vocBrand : list) {
				String str = "id:" + vocBrand.getId() + ", pId:" + 0
						+ ",name:\"" + vocBrand.getBrandName() + "\""
						+ ",open:true";
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
	
}

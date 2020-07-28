package com.nineclient.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.nineclient.constant.VocSkuCheckStatus;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpBusinessType;
import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.VocBrand;
import com.nineclient.model.VocGoods;
import com.nineclient.model.VocShop;
import com.nineclient.model.VocSku;
import com.nineclient.model.VocSkuProperty;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.ExcelUtil;
import com.nineclient.utils.PageBean;
import com.nineclient.utils.PageModel;

@RequestMapping("/vocgoodses")
@Controller
@RooWebScaffold(path = "vocgoodses", formBackingObject = VocGoods.class)
public class VocGoodsController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid VocGoods vocGoods, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, vocGoods);
			return "vocgoodses/create";
		}
		uiModel.asMap().clear();
		vocGoods.persist();
		return "redirect:/vocgoodses/"
				+ encodeUrlPathSegment(vocGoods.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new VocGoods());
		return "vocgoodses/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("vocgoods", VocGoods.findVocGoods(id));
		uiModel.addAttribute("itemId", id);
		return "vocgoodses/show";
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
			uiModel.addAttribute("vocgoodses", VocGoods.findVocGoodsEntries(
					firstResult, sizeNo, sortFieldName, sortOrder));
			float nrOfPages = (float) VocGoods.countVocGoodses() / sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("vocgoodses",
					VocGoods.findAllVocGoodses(sortFieldName, sortOrder));
		}
		addDateTimeFormatPatterns(uiModel);
		return "vocgoodses/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid VocGoods vocGoods, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, vocGoods);
			return "vocgoodses/update";
		}
		uiModel.asMap().clear();
		vocGoods.merge();
		return "redirect:/vocgoodses/"
				+ encodeUrlPathSegment(vocGoods.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, VocGoods.findVocGoods(id));
		return "vocgoodses/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		VocGoods vocGoods = VocGoods.findVocGoods(id);
		vocGoods.remove();
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/vocgoodses";
	}

	/**
	 * 查询电商sku
	 * 
	 * @param pageBean
	 * @param page
	 * @param size
	 * @param skuCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryVocGoodsList", produces = "text/html;charset=utf-8")
	public String queryVocGoodsList(
			HttpServletRequest requst,
			PageBean pageBean,
			@RequestParam(value = "channelId", required = false) Long channelId,
			@RequestParam(value = "goodsName", required = false) String goodsName,
			@RequestParam(value = "vocBrandId", required = false) Long vocbrandId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "goodsStatus", required = false) Long goodsStatus) {
		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1)
				* sizeNo;
		page=page==null?0:page;
		VocSkuCheckStatus status = null;
		PubOperator user = CommonUtils.getCurrentPubOperator(requst);
		UmpCompany company = user.getCompany();
		
		if (goodsStatus != null) {
			for (VocSkuCheckStatus st : VocSkuCheckStatus.values()) {
				if (st.ordinal() == goodsStatus) {
					status = st;
					break;
				}
			}
		}
		Long count = VocGoods.findCountGoodsesBySkuCodes(company,channelId,
				goodsName, vocbrandId, status, pageBean.getStartTime(),
				pageBean.getEndTime());
		List<VocGoods> list = VocGoods.findAllVocGoodsesBySkuCodes(company,channelId,
				goodsName, vocbrandId, status, pageBean.getStartTime(),
				pageBean.getEndTime(), firstResult, sizeNo, "", "");
		PageModel<VocGoods> pageModel = new PageModel<VocGoods>();
		pageModel.setDataJson(VocGoods.toJsonArray(list));
		pageModel.setPageNo(page);
		pageModel.setPageSize(sizeNo);
		pageModel.setTotalCount(count.intValue());
		return pageModel.toJson();
	}

	@ResponseBody
	@RequestMapping(value = "queryMacthVocGoodsList", produces = "text/html;charset=utf-8")
	public String queryVocGoodsList(
			@RequestParam(value = "skuName", required = false) String skuName,
			@RequestParam(value = "vocBrandId", required = false) Long vocBrandId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "skuCode", required = false) String skuCode) {
		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1)
				* sizeNo;
		Long maxPages = VocGoods.findCountMatchGoodses(vocBrandId, skuName);
		List<VocSku> list = VocGoods.findAllMatchVocGoodses(vocBrandId,
				skuName, firstResult, sizeNo, "", "");
		PageModel<VocGoods> pageModel = new PageModel<VocGoods>();
		pageModel.setDataJson(VocSku.toJsonArray(list));
		pageModel.setPageNo(page);
		pageModel.setPageSize(sizeNo);
		pageModel.setTotalCount(Integer.parseInt(maxPages + ""));
		return pageModel.toJson();
	}
	
	/**
	 * 模板导出
	 * 
	 * @param httpServletRequest
	 * @param response
	 */
	@RequestMapping(value = "exportExecl", method = RequestMethod.GET)
	public void exportExecl(HttpServletRequest httpServletRequest,
			HttpServletResponse response,@RequestParam(value="id",required=false)Long id) {
		// 生成提示信息，
		response.setContentType("application/vnd.ms-excel");

		String[] title = { "子行业", "品牌","sku编码","商品名称","型号","类型","商品产地","sku属性","包装","商品毛重"};
		//UmpBusinessType businessType = UmpBusinessType.findUmpBusinessType(id);
		OutputStream os = null;
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("SKU导入-模板").getBytes("gb2312"),
							"iso8859-1") + ".xls");
			os = response.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel_templete(title, os);
		return;
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
			return "redirect:/vocskus";
		}
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		InputStream in = null;
		try {
			in = file.getInputStream();
			List<Map<String,Object>> list = ExcelUtil.parseExcel(in);
			if(list==null||list.size()<1){
				msg="文件格式异常";
				session.setAttribute("importmsg", msg);
				return "redirect:/vocskus";
			}
			Map<String,Object> kmap = list.get(0);
			//String businessTypeName = String.valueOf(kmap.get("子行业"));
			Set<VocSku> vocSkuSet = new HashSet<VocSku>();
			String businessName = null;
			String skuName = null;
			String skuCode = null;
			String brandName = null;
			String skuProperty=null;
			StringBuilder msgBuf = new StringBuilder();
			for (Map<String, Object> map : list) {
				VocSku vocSku = new VocSku();
				vocSku.setCompanyCode(company.getCompanyCode());
				vocSku.setCreateTime(new Date());
				vocSku.setIsDeleted(false);
				vocSku.setIsVisable(true);
				businessName=map.get("子行业")==null?"":String.valueOf(map.get("子行业")).trim();
				skuName = map.get("商品名称")==null?"":String.valueOf(map.get("商品名称")).trim();
				skuCode=map.get("sku编码")==null?"":String.valueOf(map.get("sku编码")).trim();
				brandName =map.get("品牌")==null?"":String.valueOf(map.get("品牌")).trim();
				skuProperty =map.get("sku属性")==null?"":String.valueOf(map.get("sku属性")).trim();
				if(businessName==""){
					msgBuf.append("【子行业不能为空】");
				}
				if(skuName==""){
					msgBuf.append("【商品名称不能为空】");
				}
				if(skuCode==""){
					msgBuf.append("【sku编码不能为空】");
				}
				if(brandName==""){
					msgBuf.append("【品牌名称不能为空】");
				}
				UmpBusinessType business =null;
				business= 	UmpBusinessType.findUmpBusinessTypeByName(businessName);
				if(business==null){
					msgBuf.append("【子行业不存在】");
				}
				VocBrand vocBrand =VocBrand.findVocBrandbyCompanyAndName(company,brandName);
				if(vocBrand==null){
					msgBuf.append("【品牌不存在】");
				}
				if(skuProperty=="")
				{
					msgBuf.append("【sku属性不能为空】");
				}
				VocSkuProperty vocSkuProperty = VocSkuProperty.findVocSkuPropertyCompanyAndName(company,skuProperty);
				if(vocSkuProperty== null){
					msgBuf.append("【sku属性不存在】");
				}
				vocSku.setVocSkuProperty(vocSkuProperty);
				vocSku.setUmpBusinessType(business);
				vocSku.setName(skuName);
				vocSku.setSkuCode(skuCode);
				vocSku.setVocBrand(vocBrand);
				vocSku.setSkuModel(String.valueOf(map.get("型号")));
				vocSku.setSkuOrigin(String.valueOf(map.get("商品产地")).trim());
				vocSku.setSkuPackage(String.valueOf(map.get("包装")).trim());
				vocSku.setSkuType(String.valueOf(map.get("类型")).trim());
				vocSku.setSkuWeight(String.valueOf(map.get("商品毛重")).trim());
				vocSkuSet.add(vocSku);
				if(msgBuf.length()>1){
					break;
				}
			}
			if(msgBuf.length()>1){
				session.setAttribute("importmsg", msgBuf.toString());
			}else{
				VocSku.batchPersist(vocSkuSet);
				session.setAttribute("importmsg", "导入成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("importmsg", "导入异常,请检查文件数据是否正确");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "redirect:/vocskus";
	}

	/**
	 * 匹配页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "macthPage", method = RequestMethod.GET)
	public String macthPage(
			@RequestParam(value = "skuGoodsId", required = false) Long id,
			Model uiModel) {
		VocGoods goods = VocGoods.findVocGoods(id);
		uiModel.addAttribute("vocGoods", goods);
		List<VocBrand> list = VocBrand
				.findVocBrandsByChannelAndCompany(new HashMap<String, Object>());
		uiModel.addAttribute("vocBrands", list);
		return "vocskus/matchSkuGoods";
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteVocGoods", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String deleteVocGoods(
			@RequestParam(value = "id", required = false) Long id) {
		String msg = "成功";
		VocGoods goods = VocGoods.findVocGoods(id);
		try {
			if (goods != null) {
				goods.remove();
			} else {
				msg = "数据已不存在";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "失败";
		}
		return msg;
	}

	/**
	 * 审核
	 * 
	 * @return
	 */
	@RequestMapping(value = "approveVocGoods", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String approveVocGoods(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "status", required = false) Long status) {
		String msg = "成功";
		List<VocGoods> goodsList = null;
		goodsList = VocGoods.findVocGoodsListById(id);
		try {
			VocGoods.merge(goodsList, status);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "失败";
		}
		return msg;
	}

	/**
	 * 匹配开始
	 * 
	 * @return
	 */
	@RequestMapping(value = "matchGoodsToSku", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String matchGoodsToSku(
			@RequestParam(value = "skuId", required = false) Long skuId,
			@RequestParam(value = "vocGoodsId", required = false) Long vocGoodsId,
			@RequestParam(value = "matchType", required = false) Long matchType,
			Model uiModel) {
		String msg = "";
		// 查询待匹配商品
		VocGoods goods = VocGoods.findVocGoods(vocGoodsId);
		VocSku sku = VocSku.findVocSku(skuId);
		try {
			if (matchType == 1) {
				// 人工匹配
				goods.setMatchTime(new Date());
				goods.setVocSku(sku);
				goods.setMatchType(matchType);
				goods.setStatus(VocSkuCheckStatus.未审批);
				goods.merge();
			} else if (matchType == 2) {
				// 智能匹配
				// 查询匹配规则
			}
			// 跟新匹配分值和匹配时间
			msg = "成功";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "失败";
		}
		return msg;
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"vocGoods_createtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, VocGoods vocGoods) {
		uiModel.addAttribute("vocGoods", vocGoods);
		uiModel.addAttribute("channels", UmpChannel.findAllUmpChannels());
		uiModel.addAttribute("shops", VocShop.findAllVocShops());
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
}

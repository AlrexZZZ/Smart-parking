package com.nineclient.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.nineclient.constant.NotifyMsg;
import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpProduct;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.PageModel;

@RequestMapping("/umpproducts")
@Controller
@RooWebScaffold(path = "umpproducts", formBackingObject = UmpProduct.class)
public class UmpProductController {
	/**
	 * 查询商品-渠道列表
	 * 
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "productList", produces = "text/html")
	public String productList(Model uiModel,
			@RequestParam(value = "displayId", required = false) Long displayId,
			HttpServletRequest request) {
		uiModel.addAttribute("productList", UmpProduct.findAllUmpProducts(null));
		if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(request);
	    }
		request.getSession().setAttribute("displayId", displayId);
		return "umpproducts/productList";
	}
	/**
	 * 产品列表页面跳转
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "listPage", produces = "text/html")
	public String listPage(HttpServletRequest request,Model uiModel,@RequestParam(value = "displayId", required = false) Long displayId) {
		if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(request);
	    }
		request.getSession().setAttribute("displayId", displayId);
		return "umpproducts/list";
	}
	
	@ResponseBody
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	public void updateStatus(@RequestParam(value = "id") Long id,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			UmpChannel umpchannel = UmpChannel.findUmpChannel(id);
			umpchannel.setIsVisable(!umpchannel.getIsVisable());
			umpchannel.merge();
			response.setContentType("text/Xml;charset=utf-8");
			out = response.getWriter();
			String json = "{\"success\":\"true\"}";// 拼成Json格式字符串{key:value,key1:value1}
			out.println(json);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 添加产品
	 * 
	 * @param umpProduct
	 * @param bindingResult
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String create(UmpProduct umpProduct, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		List<UmpProduct> list = UmpProduct.findUmpProductByName(
				umpProduct.getProductName(), umpProduct.getId());
		String msg = "";
		if (umpProduct.getProductName() == null
				|| umpProduct.getProductName().trim().equals("")) {
			return "产品名称不能为空";
		}
		if (list != null && list.size() > 0) {
			msg = "产品名称不能重复";
			return msg;
		}
		try {
			umpProduct.setIsDeleted(false);
			umpProduct.setCreateTime(new Date());
			uiModel.asMap().clear();
			umpProduct.persist();
		} catch (Exception e) {
			e.printStackTrace();
			msg = NotifyMsg.EXCEPTION;
		}
		return msg;
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel,HttpServletRequest request,
			@RequestParam(value = "displayId", required = false) Long displayId
			) {
		 if(displayId == null){
	       displayId = CommonUtils.getCurrentDisPlayId(request);
	     }
		request.getSession().setAttribute("displayId", displayId);
		return "umpproducts/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("umpproduct", UmpProduct.findUmpProduct(id));
		uiModel.addAttribute("itemId", id);
		return "umpproducts/show";
	}

	/**
	 * 产品列表
	 * 
	 * @param httpServletRequest
	 * @param page
	 * @param size
	 * @param sortFieldName
	 * @param sortOrder
	 * @param uiModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list", produces = "text/html;charset=utf-8")
	public String list(
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {
		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1)
				* sizeNo;
		int pageNo=page == null ? 0 : page.intValue();
		List<UmpProduct> umpproducts = UmpProduct.findUmpProductEntries(
				firstResult, sizeNo, sortFieldName, sortOrder);
		Long count = UmpProduct.countUmpProducts();
		PageModel<UmpProduct> pageModel = new PageModel<UmpProduct>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(sizeNo);
		pageModel.setTotalCount(count.intValue());
		pageModel.setDataJson(UmpProduct.toJsonArray(umpproducts));
		return pageModel.toJson();
	}

	/**
	 * @param umpProduct
	 * @param bindingResult
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String update(UmpProduct umpProduct, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		List<UmpProduct> list = UmpProduct.findUmpProductByName(
				umpProduct.getProductName(), umpProduct.getId());
		String msg = "";
		if (umpProduct.getProductName() == null
				|| umpProduct.getProductName().trim().equals("")) {
			return "产品名称不能为空";
		}
		if (list != null && list.size() > 0) {
			msg = "产品名称不能重复";
			return msg;
		}
		try {
			UmpProduct umpp = UmpProduct.findUmpProduct(umpProduct.getId());
			umpp.setRemark(umpProduct.getRemark());
			umpp.setProductName(umpProduct.getProductName());
			umpp.setIsVisable(umpProduct.getIsVisable());
			uiModel.asMap().clear();
			umpp.merge();
		} catch (Exception e) {
			e.printStackTrace();
			msg = NotifyMsg.EXCEPTION;
		}
		return msg;
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html;charset=utf-8")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, UmpProduct.findUmpProduct(id));
		return "umpproducts/update";
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String delete(@RequestParam(value="id") Long id, Model uiModel) {
		try {
			UmpProduct umpProduct = UmpProduct.findUmpProduct(id);
			umpProduct.remove();
			uiModel.asMap().clear();
		} catch (Exception e) {
			e.printStackTrace();
			return NotifyMsg.FIALED;
		}

		return NotifyMsg.SUCCESS;
	}

	@ResponseBody
	@RequestMapping(value = "updateVisable", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String updateVisAble(@RequestParam(value="id") Long id,
			@RequestParam(value = "isVisable", required = false) Boolean visable,
			Model uiModel) {
		try {
			UmpProduct umpProduct = UmpProduct.findUmpProduct(id);
			umpProduct.setIsVisable(visable);
			uiModel.asMap().clear();
			umpProduct.merge();
		} catch (Exception e) {
			e.printStackTrace();
			return NotifyMsg.FIALED;
		}
		return NotifyMsg.SUCCESS;
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"umpProduct_createtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, UmpProduct umpProduct) {
		uiModel.addAttribute("umpProduct", umpProduct);
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

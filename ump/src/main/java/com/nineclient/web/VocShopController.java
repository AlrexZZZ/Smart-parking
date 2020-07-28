package com.nineclient.web;
import java.io.IOException;
import java.io.PrintWriter;
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

import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpProduct;
import com.nineclient.model.VocBrand;
import com.nineclient.model.VocShop;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@RequestMapping("/vocshops")
@Controller
@RooWebScaffold(path = "vocshops", formBackingObject = VocShop.class)
public class VocShopController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid VocShop vocShop, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, vocShop);
            return "vocshops/create";
        }
        uiModel.asMap().clear();
        vocShop.persist();
        return "redirect:/vocshops/" + encodeUrlPathSegment(vocShop.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new VocShop());
        return "vocshops/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("vocshop", VocShop.findVocShop(id));
        uiModel.addAttribute("itemId", id);
        return "vocshops/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, 
    		@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
    		@RequestParam(value = "sortOrder", required = false) String sortOrder,
    		@RequestParam(value = "displayId", required = false) Long displayId,
    		 @RequestParam(value = "page", required = false) Integer start,
			 @RequestParam(value = "size", required = false) Integer limit,
    		HttpServletRequest httpServletRequest,
    		Model uiModel) {
		UmpCompany company = CommonUtils.getCurrentPubOperator(httpServletRequest).getCompany();
		   Map parmMap = new HashMap();
		   parmMap.put("companyId", company.getId());
		  VocShop model = new VocShop();
		  model.setCompanyId(CommonUtils.getCurrentCompanyId(httpServletRequest));
		  QueryModel<VocShop> qm = new QueryModel<VocShop>(model, start, limit);
		  qm.getInputMap().putAll(parmMap);
		   
		  PageModel<VocShop> pm = VocShop.getQueryShop(qm);
		     pm.setPageSize(limit);
			 pm.setStartIndex(start);
			 
        uiModel.addAttribute("vocshops", pm.getDataList());
        addDateTimeFormatPatterns(uiModel);
        List<UmpProduct> product = UmpProduct.findUmpProductById(Global.VOC);
    	List<UmpChannel> channelList = UmpChannel.findAllChannelsbuyProductNameAndCompanyService(company,product.get(0));
    	uiModel.addAttribute("channels",channelList);
    	List<VocBrand> brandList = VocBrand.findVocBrandbyCompany(company);
		uiModel.addAttribute("brands",brandList);
		uiModel.addAttribute("limit", 10);
		if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(httpServletRequest);
	    }
	    httpServletRequest.getSession().setAttribute("displayId", displayId);
        return "vocshops/list";
    }
	
	/**
	 * 店铺列表查询
	 * @param uiModel
	 * @param start
	 * @param limit
	 * @param request
	 * @return
	 */
	@RequestMapping(value="queryShops",produces = "text/html")
    public String queryShops(
			 Model uiModel,
			 @RequestParam(value = "start", required = false) Integer start,
			 @RequestParam(value = "limit", required = false) Integer limit,
			 HttpServletRequest request
			 ) {
		  Map parmMap = new HashMap();
		  VocShop model = new VocShop();
		  model.setCompanyId(CommonUtils.getCurrentCompanyId(request));
		  QueryModel<VocShop> qm = new QueryModel<VocShop>(model, start, limit);
		  qm.getInputMap().putAll(parmMap);
		   
		  PageModel<VocShop> pm = VocShop.getQueryShop(qm);
		     pm.setPageSize(limit);
			 pm.setStartIndex(start);
			 
		 uiModel.addAttribute("maxPages", pm.getTotalPage());
		 uiModel.addAttribute("page", pm.getPageNo());
		 uiModel.addAttribute("limit", pm.getPageSize());
		 StringBuffer sb = null;
		 for(VocShop vs:pm.getDataList()){
			 sb = new StringBuffer();
			 boolean flag = true;
		   for(VocBrand vb:vs.getVocBrands()){
			   if(flag){
				sb.append(vb.getBrandName());	   
				flag =false;
			   }else{
				sb.append(","+vb.getBrandName());
			   }
		   }
		   vs.setVocBrandNames(sb.toString());
		 }
		 
        uiModel.addAttribute("vocshops", pm.getDataList());
        addDateTimeFormatPatterns(uiModel);
        return "vocshops/result";
    }
	
	
	@ResponseBody
	@RequestMapping(value="deleteShop",produces = "text/html")
    public void deleteShop(
			 Model uiModel,
			 @RequestParam(value = "id", required = false) Long id,
			 HttpServletRequest request,
			 HttpServletResponse response
			 ) {
		VocShop.findVocShop(id).remove();
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		String msg = "{success:true}";
		try {
			 out = response.getWriter();
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.println(msg);
			out.close();
		}
    }
	
	@ResponseBody
	@RequestMapping(value="queryShopName",produces = "text/html")
    public void queryShopName(
			 Model uiModel,
			 @RequestParam(value = "name", required = true) String name,
			 HttpServletRequest request,
			 HttpServletResponse response
			 ) {
		
		Long count = VocShop.findShopName(CommonUtils.getCurrentPubOperator(request).getCompany().getId(),name);
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
	
	
	@ResponseBody
	@RequestMapping(value="changeState",produces = "text/html")
    public void changeState(
			 Model uiModel,
			 @RequestParam(value = "id", required = false) Long id,
			 @RequestParam(value = "isVisable", required = false) Boolean isVisable,
			 HttpServletRequest request,
			 HttpServletResponse response
			 ) {
		VocShop vshop = VocShop.findVocShop(id);
		 vshop.setIsVisable(isVisable);
		 vshop.merge();
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		String msg = "{success:true}";
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
	 * 新增，编辑店铺
	 * @param uiModel
	 * @param id
	 * @param name
	 * @param url
	 * @param remark
	 * @param channelId
	 * @param isVisable
	 * @param brandIds
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="editShop",produces = "text/html;charset=utf-8")
    public String editShop(
			 Model uiModel,
			 @RequestParam(value = "id", required = false) Long id,
			 @RequestParam(value = "name", required = false) String name,
			 @RequestParam(value = "url", required = false) String url,
			 @RequestParam(value = "remark", required = false) String remark,
			 @RequestParam(value = "channelId", required = false) Long channelId,
			 @RequestParam(value = "isVisable", required = false) Boolean isVisable,
			 @RequestParam(value = "brandIds", required = false) String brandIds,
			 HttpServletRequest request,
			 HttpServletResponse response
			 ) {
		VocShop shop = null;
		Long count = 0L;
		String msg = "";
		if (null != id) {
			shop = VocShop.findVocShop(id);
		} else {
			shop = new VocShop();
			shop.setCreateTime(new Date());
		}
		count = VocShop.findVocShopByName(id,channelId, name,
				CommonUtils.getCurrentCompanyId(request));
		if (count > 0) {
			return "店铺名称不能重复";
		}
		shop.setChannel(UmpChannel.findUmpChannel(channelId));
		shop.setName(name);
		shop.setRemark(remark);
		shop.setUrl(url);
		Set<VocBrand> bSet = CommonUtils.list2Set(VocBrand
				.findVocBrandEntriesByIds(brandIds));
		shop.setVocBrands(bSet);
		shop.setIsVisable(isVisable);
		shop.setCompanyId(CommonUtils.getCurrentCompanyId(request));
		try {
			shop.merge();
			msg = "添加成功";
		} catch (Exception e) {
			msg = "添加失败";
		}
		return msg;
    }
	
	/**
	 * 根据店铺查询品牌
	 * @param uiModel
	 * @param shopId
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="getBrandByShopId",produces = "text/html")
    public void getBrandByShopId(
			 Model uiModel,
			 @RequestParam(value = "shopId", required = false) Long shopId,
			 HttpServletRequest request,
			 HttpServletResponse response
			 ) {
		
		 VocShop shop = VocShop.findVocShop(shopId);
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		StringBuffer sb = new StringBuffer();
		try {
			 out = response.getWriter();
			 sb.append("[");
			 boolean flag = true;
			  for(VocBrand vb:shop.getVocBrands()){
				  if(flag){
				   sb.append("{id:"+vb.getId()+",name:'"+vb.getBrandName()+"'}");
				   flag = false; 
				  }else{
				   sb.append(",{id:"+vb.getId()+",name:'"+vb.getBrandName()+"'}");
				  }
			  }
			  sb.append("]");
			  out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}
    }
	
	/**
	 * 检查唯一性
	 * @param uiModel
	 * @param brandId
	 * @param channelId
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="checkUniqueShopBrand",produces = "text/html")
    public void checkUniqueShopBrand(
			 Model uiModel,
			 @RequestParam(value = "brandId", required = false) String brandId,
			 @RequestParam(value = "channelId", required = false) Long channelId,
			 @RequestParam(value = "shopId", required = false) Long shopId,
			 HttpServletRequest request,
			 HttpServletResponse response
			 ) {
		 Long  count = VocShop.findUniqueShopBrandByCompany(CommonUtils.getCurrentCompanyId(request), channelId, brandId,shopId);
		 response.setContentType("text/Xml;charset=utf-8");
		 PrintWriter out = null;
		
		 String msg = "{\"success\":false}";
		  if(null != count && count > 0){
			 msg = "{\"success\":true}";
		  }
		try {
		    out = response.getWriter();
		    out.println(msg);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}
    }

	
	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid VocShop vocShop, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, vocShop);
            return "vocshops/update";
        }
        uiModel.asMap().clear();
        vocShop.merge();
        return "redirect:/vocshops/" + encodeUrlPathSegment(vocShop.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, VocShop.findVocShop(id));
        return "vocshops/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        VocShop vocShop = VocShop.findVocShop(id);
        vocShop.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/vocshops";
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
			@RequestParam(value = "umpChannelId") String ids, HttpServletRequest request,
			HttpServletResponse response) {
		{
			PubOperator user = CommonUtils.getCurrentPubOperator(request);
			UmpCompany company = user.getCompany();
			List<VocShop> list = VocShop.findAllVocShopsByVocChannelsAndCompany(ids,company);
			StringBuilder json = new StringBuilder("[");
			boolean flag = true;
			for (VocShop vocShop : list) {
				if (flag) {
					flag = false;
					json.append("{id:" + vocShop.getId() + ",name:\'"
							+ vocShop.getName() + "\'}");
				} else {
					json.append(",{id:" + vocShop.getId() + ",name:\'"
							+ vocShop.getName() + "\'}");
				}

			}
			json.append("]");
			return json.toString();
		}
	}
	@RequestMapping(value = "queryVocBrands", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String queryVocBrands(Model uiModel,
			@RequestParam(value = "shopId") long id, HttpServletRequest request,
			HttpServletResponse response) {
		{
			List<VocBrand> listBrands = VocShop.findVocShopIsVisable(id);
			StringBuilder json = new StringBuilder("[");
			boolean flag = true;
			for (VocBrand vocBrand : listBrands) {
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
	}
	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("vocShop_createtime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, VocShop vocShop) {
        uiModel.addAttribute("vocShop", vocShop);
        uiModel.addAttribute("channels", UmpChannel.findAllUmpChannels());
        addDateTimeFormatPatterns(uiModel);
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
	
	@RequestMapping(value = "tree", produces = "text/html;charset=utf-8")
	@ResponseBody
	public  String tree(Model uiModel, HttpServletRequest request,
			@RequestParam(value = "channelIds") String ids,
			HttpServletResponse response){
		StringBuffer strs = new StringBuffer();
		int num = 0;
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		List<VocShop> list = VocShop.findAllVocShopsByChannelIdsAndCompany(ids,company);
		System.out.println(list.size());
		if(null != list && list.size() > 0){
			for (VocShop vocShop : list) {
				String str = "id:" + vocShop.getId() + ", pId:" + 0
						+ ",name:\"" + vocShop.getName() + "\""
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

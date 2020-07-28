package com.nineclient.cherry.wcc.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import net.sf.json.JSONObject;

import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nineclient.cherry.wcc.model.WccCheryBrands;
import com.nineclient.constant.PlatformType;
import com.nineclient.model.PubOperator;
import com.nineclient.model.PubOrganization;
import com.nineclient.model.PubRole;
import com.nineclient.model.WccGroup;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.thread.ThreadPoolManager;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageModel;
import com.nineclient.weixin.GetFriendProcesser;
import com.nineclient.weixin.WxMpUserInfo;
import com.nineclient.weixin.WxMpuserManager;


@RequestMapping("/cherybrands")
@Controller
public class WccCheryBrandController {
	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
    		@RequestParam(value = "displayId", required = false) Long displayId,
    		HttpServletRequest request,
    		HttpServletRequest httpServletRequest, @RequestParam(value = "size",
    		required = false) Integer size, Model uiModel) {
		    uiModel.addAttribute("limit", 10);
	        if(displayId == null){
	        	displayId = CommonUtils.getCurrentDisPlayId(request);
	        }
	        request.getSession().setAttribute("displayId", displayId);
		return "cherybrands/list";
	}
	
	
	
	@RequestMapping(value="/getBrandList",method = RequestMethod.POST,produces = "text/html;charset=utf-8")
	@ResponseBody
	public String getPubOperListByFiled(PubOperator pubOperator, Model uiModel,
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "active", required = false) Boolean active,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size) throws Exception{
		Map<String,Object> parmMap = new HashMap<String, Object>();
		parmMap.put("brandName", brandName);
		parmMap.put("active", active);
		
		int maxResults = size == null ? 4 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1) * maxResults;
		List<WccCheryBrands> brandList=WccCheryBrands.findBrandByFiled(firstResult, maxResults, brandName, active);
		List<WccCheryBrands> rtlists=new ArrayList<WccCheryBrands>();
		if(brandList!=null&&brandList.size()>0){
			for (WccCheryBrands wccCheryBrands : brandList) {
				rtlists.add(wccCheryBrands);
			}			
		}
		int count =WccCheryBrands.findBrandByFiled(-1, -1, brandName, active).size();
		PageModel<WccCheryBrands> pageMode = new PageModel<WccCheryBrands>();
		pageMode.setPageNo(page);
		pageMode.setTotalCount(count);
		pageMode.setPageSize(maxResults);
		pageMode.setDataJson(WccCheryBrands.toJsonArray(rtlists));
		return pageMode.toJson();
	}
	
	/**
	 * 是否使用
	 * @param id
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	public void updateStatus(@RequestParam(value = "id", required = false) Long id,HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "active", required = false) Boolean active) {
		PrintWriter out = null;
		WccCheryBrands brand = null;
		try {
			brand = WccCheryBrands.findBrandById(id);
			brand.setActive(active);
			brand.merge();
			response.setContentType("text/Xml;charset=utf-8");
			out = response.getWriter();
			String json = "{\"success\":\"true\"}";
			out.println(json);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}
	}
	
	@RequestMapping(value="/create",method = RequestMethod.POST, produces = "text/html")
    public String create(@RequestParam(value = "brandName", required = false) String brandName,@RequestParam(value = "active", required = false) Boolean active,
    		Model uiModel, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException, WxErrorException {
		uiModel.asMap().clear();
		WccCheryBrands brand =new WccCheryBrands();
		brand.setCreateDate(new Date());
		brand.setIsDeleted(true);
		brand.setActive(active);
		brand.setBrandName(brandName);
		brand.persist();
		return "redirect:/cherybrands?page=1&amp;size=10";
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "text/html")
    public void delete(@RequestParam(value = "id", required = false) Long id,Model uiModel,
    		HttpServletRequest httpServletRequest,HttpServletResponse response) {
		PrintWriter out = null;
		try {
			WccCheryBrands brand = WccCheryBrands.findBrandById(id);
			brand.remove();
			uiModel.asMap().clear();
			response.setContentType("text/Xml;charset=utf-8");
		
			out = response.getWriter();
			String json = "{\"success\":\"true\"}";
			out.println(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.close();
		}
	
   }
	
	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new WccCheryBrands());
        return "cherybrands/create";
    }

	@RequestMapping(value = "/update", produces = "text/html")
	public String updateForm(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "active", required = false) Boolean active,Model uiModel) {
		WccCheryBrands brand = WccCheryBrands.findBrandById(id);
		brand.setActive(active);
		brand.merge();
        uiModel.asMap().clear();
        return "redirect:/cherybrands?page=1&amp;size=10";
	}
	
	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel,
    		HttpServletRequest httpServletRequest) {
        uiModel.addAttribute("brand", WccCheryBrands.findBrandById(id));
        return "cherybrands/update";
    }
	
	void populateEditForm(Model uiModel, WccCheryBrands brand) {
		uiModel.addAttribute("brand", brand);
		addDateTimeFormatPatterns(uiModel);
	}
	
	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"brand_createDate_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}
	
	
	@RequestMapping(value = "/checkBrand", method = RequestMethod.POST,produces="text/html;charset=utf-8")
	@ResponseBody
	public String checkBrand(@RequestParam(value = "brand") String brand,HttpServletRequest request, HttpServletResponse response){
		String str = "{msg:\'true\'}";
		List<WccCheryBrands> brandList = WccCheryBrands.findBrand(brand);
		if(brandList !=null && brandList.size() > 0){
			str = "{msg:\'false\'}";
		}
		return str;
	}
}

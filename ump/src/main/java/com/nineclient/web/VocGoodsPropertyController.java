package com.nineclient.web;
import com.nineclient.constant.VocValueType;
import com.nineclient.model.UmpBusinessType;
import com.nineclient.model.VocGoodsProperty;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

@RequestMapping("/vocgoodspropertys")
@Controller
@RooWebScaffold(path = "vocgoodspropertys", formBackingObject = VocGoodsProperty.class)
public class VocGoodsPropertyController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid VocGoodsProperty vocGoodsProperty,
    		@RequestParam(value="umpBusinessTypeId",required=false) Long umpBusinessTypeId,
    		BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		vocGoodsProperty.setCreateTime(new Date());
		UmpBusinessType umpbusiness = UmpBusinessType.findUmpBusinessType(umpBusinessTypeId);
		vocGoodsProperty.setUmpBusinessType(umpbusiness);
		if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, vocGoodsProperty);
            return "vocgoodspropertys/create";
        }
        uiModel.asMap().clear();
        vocGoodsProperty.persist();
        return "redirect:/vocgoodspropertys/" + encodeUrlPathSegment(vocGoodsProperty.getId().toString(), httpServletRequest);
    }
	@RequestMapping(value = "addGoodsproperty", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String addGoodsproperty(VocGoodsProperty vocGoodsProperty,
    		@RequestParam(value="umpBusinessTypeId",required=false) Long umpBusinessTypeId,
    		 Model uiModel, HttpServletRequest httpServletRequest,HttpServletResponse response){
		String msg="";
		vocGoodsProperty.setCreateTime(new Date());
		UmpBusinessType umpbusiness = UmpBusinessType.findUmpBusinessType(umpBusinessTypeId);
		vocGoodsProperty.setUmpBusinessType(umpbusiness);
		try{
			vocGoodsProperty.persist();
			msg="\'成功\'";
		}catch(Exception e){
			e.printStackTrace();
			msg="\'失败\'";
		}
		return msg;
		
		
	}
	@RequestMapping(value = "queryGoodspropertyByBusinessId", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String queryGoodspropertyByBusinessId(
    		@RequestParam(value="umpBusinessTypeId",required=false) Long umpBusinessTypeId,
    		 Model uiModel, HttpServletRequest httpServletRequest,HttpServletResponse response){
		String json="[";
		List<VocGoodsProperty> list = VocGoodsProperty.findVocGoodsPropertyByBusinessId(umpBusinessTypeId);
		boolean flag=true;
		for (VocGoodsProperty vocGoodsProperty : list) {
			if(flag){
				flag=false;
				json+="{id:"+vocGoodsProperty.getId()+",name:\'"+vocGoodsProperty.getPropertyName()+"\',valueType:\'"+vocGoodsProperty.getValueType()+"\',unit:\'"+vocGoodsProperty.getUnit()+"\'}";
			}else{
				json+=",{id:"+vocGoodsProperty.getId()+",name:\'"+vocGoodsProperty.getPropertyName()+"\',valueType:\'"+vocGoodsProperty.getValueType()+"\',unit:\'"+vocGoodsProperty.getUnit()+"\'}";
			}
		}
		json+="]";
		return json;
		
		
	}
	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new VocGoodsProperty());
        return "vocgoodspropertys/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("vocgoodsproperty", VocGoodsProperty.findVocGoodsProperty(id));
        uiModel.addAttribute("itemId", id);
        return "vocgoodspropertys/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("vocgoodspropertys", VocGoodsProperty.findVocGoodsPropertyEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) VocGoodsProperty.countVocGoodsPropertys() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("vocgoodspropertys", VocGoodsProperty.findAllVocGoodsPropertys(sortFieldName, sortOrder));
        }
        return "vocgoodspropertys/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid VocGoodsProperty vocGoodsProperty, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, vocGoodsProperty);
            return "vocgoodspropertys/update";
        }
        uiModel.asMap().clear();
        vocGoodsProperty.merge();
        return "redirect:/vocgoodspropertys/" + encodeUrlPathSegment(vocGoodsProperty.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, VocGoodsProperty.findVocGoodsProperty(id));
        return "vocgoodspropertys/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        VocGoodsProperty vocGoodsProperty = VocGoodsProperty.findVocGoodsProperty(id);
        vocGoodsProperty.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/vocgoodspropertys";
    }

	void populateEditForm(Model uiModel, VocGoodsProperty vocGoodsProperty) {
        uiModel.addAttribute("vocGoodsProperty", vocGoodsProperty);
        uiModel.addAttribute("vocValueTypes", Arrays.asList(VocValueType.values()));
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
}

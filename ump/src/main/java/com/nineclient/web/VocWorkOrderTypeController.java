//package com.nineclient.web;
//import java.io.UnsupportedEncodingException;
//import java.util.Date;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//
//import org.joda.time.format.DateTimeFormat;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.util.UriUtils;
//import org.springframework.web.util.WebUtils;
//
//import com.nineclient.model.PubOperator;
//import com.nineclient.model.UmpCompany;
//import com.nineclient.model.UmpOperator;
//import com.nineclient.model.VocEmail;
//import com.nineclient.model.VocEmailGroup;
//import com.nineclient.model.VocWarningTime;
//import com.nineclient.model.VocWorkOrderType;
//import com.nineclient.utils.CommonUtils;
//import com.nineclient.utils.Global;
//
//@RequestMapping("/vocworkordertypes")
//@Controller
//@RooWebScaffold(path = "vocworkordertypes", formBackingObject = VocWorkOrderType.class)
//public class VocWorkOrderTypeController {
//
//	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
//    public String create(@Valid VocWorkOrderType vocWorkOrderType, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
//        if (bindingResult.hasErrors()) {
//            populateEditForm(uiModel, vocWorkOrderType);
//            return "vocworkordertypes/create";
//        }
//        uiModel.asMap().clear();
//        vocWorkOrderType.persist();
//        return "redirect:/vocworkordertypes/" + encodeUrlPathSegment(vocWorkOrderType.getId().toString(), httpServletRequest);
//    }
//
//	@RequestMapping(params = "form", produces = "text/html")
//    public String createForm(Model uiModel,HttpServletRequest request,@RequestParam(value="displayId",required=false)Long displayId) {
//		if(displayId == null){
//	        displayId = CommonUtils.getCurrentDisPlayId(request);
//	    }
//		request.getSession().setAttribute("displayId", displayId);
//		PubOperator user =CommonUtils.getCurrentPubOperator(request);
//		UmpCompany company = user.getCompany();
//        populateEditForm(uiModel, new VocWorkOrderType());
//        populateEditForm(uiModel, company);
//        return "vocworkordertypes/create";
//    }
//	/**
//	 * 添加工单类型
//	 * @param name
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value="addWordOrderType",produces="text/html;charset=utf-8")
//	public String addWordOrderType(@RequestParam(value = "name", required = false) String name,HttpServletRequest request){
//		String msg = "";
//		if(StringUtils.isEmpty(name)){
//			msg="工单类型名称不能为空";
//			return msg;
//		}
//		List<VocWorkOrderType> list = VocWorkOrderType.findVocWorkOrderTypeEntriesByName(name);
//		if(list!=null&&list.size()>0){
//			msg="工单类型名称不能重复";
//			return msg;
//		}
//		 PubOperator user =	CommonUtils.getCurrentPubOperator(request);
//		UmpCompany company = user.getCompany();
//		
//		VocWorkOrderType type = new VocWorkOrderType();
//		type.setCreateTime(new Date());
//		type.setIsDeleted(false);
//		type.setName(name);
//		type.setCompanyCode(company.getCompanyCode());
//		try{
//			type.persist();
//			msg="成功";
//		}catch(Exception e){
//			e.printStackTrace();
//			msg="失败";
//		}
//		return msg;
//	}
//	/**
//	 * 查询全部类型
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value="querWorkOrderTypeListAll",produces="text/html;charset=utf-8")
//	public String querWorkOrderTypeListAll(HttpServletRequest request){
//	 PubOperator user =	CommonUtils.getCurrentPubOperator(request);
//	 UmpCompany company = user.getCompany();
//	  List<VocWorkOrderType> list = VocWorkOrderType.findAllVocWorkOrderTypesByCompanyCode(company.getCompanyCode());
//		return VocWorkOrderType.toJsonArray(list);
//	}
//	/**
//	 * 查询全部类型
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value="delete",produces="text/html;charset=utf-8")
//	public String deleteWorkOrderType(@RequestParam(value="id",required=false) String id){
//		String msg="";
//		List<VocWorkOrderType> list = VocWorkOrderType.findVocWorkOrderTypeEntriesById(id);
//		try{
//			VocWorkOrderType.remove(list);
//			msg = "成功";
//		}catch(Exception e){
//			e.printStackTrace();
//			msg="失败";
//		}
//		return msg;
//	}
//	
//	@RequestMapping(value = "/{id}", produces = "text/html")
//    public String show(@PathVariable("id") Long id, Model uiModel) {
//        addDateTimeFormatPatterns(uiModel);
//        uiModel.addAttribute("vocworkordertype", VocWorkOrderType.findVocWorkOrderType(id));
//        uiModel.addAttribute("itemId", id);
//        return "vocworkordertypes/show";
//    }
//
//	@RequestMapping(produces = "text/html")
//    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
//        if (page != null || size != null) {
//            int sizeNo = size == null ? 10 : size.intValue();
//            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
//            uiModel.addAttribute("vocworkordertypes", VocWorkOrderType.findVocWorkOrderTypeEntries(firstResult, sizeNo, sortFieldName, sortOrder));
//            float nrOfPages = (float) VocWorkOrderType.countVocWorkOrderTypes() / sizeNo;
//            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
//        } else {
//            uiModel.addAttribute("vocworkordertypes", VocWorkOrderType.findAllVocWorkOrderTypes(sortFieldName, sortOrder));
//        }
//        addDateTimeFormatPatterns(uiModel);
//        return "vocworkordertypes/list";
//    }
//
//	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
//    public String update(@Valid VocWorkOrderType vocWorkOrderType, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
//        if (bindingResult.hasErrors()) {
//            populateEditForm(uiModel, vocWorkOrderType);
//            return "vocworkordertypes/update";
//        }
//        uiModel.asMap().clear();
//        vocWorkOrderType.merge();
//        return "redirect:/vocworkordertypes/" + encodeUrlPathSegment(vocWorkOrderType.getId().toString(), httpServletRequest);
//    }
//
//	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
//    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
//        populateEditForm(uiModel, VocWorkOrderType.findVocWorkOrderType(id));
//        return "vocworkordertypes/update";
//    }
//
//	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
//    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
//        VocWorkOrderType vocWorkOrderType = VocWorkOrderType.findVocWorkOrderType(id);
//        vocWorkOrderType.remove();
//        uiModel.asMap().clear();
//        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
//        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
//        return "redirect:/vocworkordertypes";
//    }
//	/**
//	 * ajax导入数据
//	 * @param umpDictionary
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value="addValue",produces="text/html;charset=utf-8")
//	public String addValue(VocWarningTime warningTime,HttpServletRequest request){
//		String msg ="";
//		 PubOperator user =	CommonUtils.getCurrentPubOperator(request);
//		UmpCompany company = user.getCompany();
//		
//		VocWarningTime warnTime = null;
//			try{
//				warnTime = VocWarningTime.findVocWarningTimeByCompanyCode(company.getCompanyCode());
//			}catch(Exception e){
//				e.printStackTrace();
//				
//			}
//				
//		try{
//		if(warnTime==null){
//			warningTime.setCreateTime(new Date());
//			warningTime.setCompanyCode(company.getCompanyCode());
//			warningTime.persist();
//			msg="成功";
//		}
//		if(warnTime!=null){
//			warnTime.setWarningTime(warningTime.getWarningTime());
//			warnTime.merge();
//			msg="成功";
//		}
//		}catch(Exception e){
//			e.printStackTrace();
//			msg= "失败";
//		}
//		return msg;
//	}
//	void addDateTimeFormatPatterns(Model uiModel) {
//        uiModel.addAttribute("vocWorkOrderType_createtime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
//    }
//	void  populateEditForm(Model uiMode,UmpCompany company){
//		VocWarningTime warningTime=null;
//		try{
//			warningTime= VocWarningTime.findVocWarningTimeByCompanyCode(company.getCompanyCode());
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	    uiMode.addAttribute("vocwarningtime",warningTime);
//		List<VocEmailGroup> list = VocEmailGroup.findVocEmailGroupByName(null, company.getId());
//		uiMode.addAttribute("vocemailgroups",list);//邮件组
//		List<VocEmail> emailList = VocEmail.findAllVocEmailsByCompany(company);
//		uiMode.addAttribute("vocemails",emailList);//邮件组
//	}
//	
//	void populateEditForm(Model uiModel, VocWorkOrderType vocWorkOrderType) {
//        uiModel.addAttribute("vocWorkOrderType", vocWorkOrderType);
//        addDateTimeFormatPatterns(uiModel);
//    }
//
//	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
//        String enc = httpServletRequest.getCharacterEncoding();
//        if (enc == null) {
//            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
//        }
//        try {
//            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
//        } catch (UnsupportedEncodingException uee) {}
//        return pathSegment;
//    }
//}

package com.nineclient.web;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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

import com.nineclient.constant.UmpCompanyServiceStatus;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpBusinessType;
import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpCompanyService;
import com.nineclient.model.UmpProduct;
import com.nineclient.model.UmpVersion;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@RequestMapping("/umpcompanyservices")
@Controller
@RooWebScaffold(path = "umpcompanyservices", formBackingObject = UmpCompanyService.class)
public class UmpCompanyServiceController {
	
	@RequestMapping(value = "/list",produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "displayId", required = false) Long displayId,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel, HttpServletRequest request) {
	        initSelect(uiModel);
	        if(displayId == null){
	        	displayId = CommonUtils.getCurrentDisPlayId(request);
	        }
	        request.getSession().setAttribute("displayId", displayId);
	     return "umpcompanyservices/companyServiceList";
    }
	
	
	/** 查询公司服务
	 * @param uiModel
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryCompanyService", produces = "text/html")
	 public String queryCompanyService( Model uiModel,
			 @RequestParam(value = "companyCode", required = false) String companyCode,
			 @RequestParam(value = "companyServiceStatus", required = false) Long companyServiceStatus,
			 @RequestParam(value = "productId", required = false) Long productId,
			 @RequestParam(value = "versionId", required = false) Long versionId,
			 @RequestParam(value = "channelId", required = false) Long channelId,
			 @RequestParam(value = "serviceStartTime", required = false) String serviceStartTimeStr,
			 @RequestParam(value = "serviceEndTime", required = false) String serviceEndTimeStr,
			 @RequestParam(value = "start", required = true) Integer start,
			 @RequestParam(value = "limit", required = true) Integer limit,
			 HttpServletRequest request) {
		 UmpCompanyService umpCompanyService = new UmpCompanyService();
		 umpCompanyService.setCompanyCode(companyCode);
		 if(null != companyServiceStatus){
			 umpCompanyService.setCompanyServiceStatus(UmpCompanyServiceStatus.getEnum(companyServiceStatus.intValue()));			 
		 }
		 umpCompanyService.setProductId(productId);
		 umpCompanyService.setVersionId(versionId);
		  Date serviceStartTime = null;
		  Date serviceEndTime = null;
		  
         if(null != serviceStartTimeStr && !"".equals(serviceStartTimeStr)){
        	 serviceStartTime = DateUtil.formateDate(serviceStartTimeStr+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
        	 umpCompanyService.setServiceStartTime(serviceStartTime);
         }
         
         if(null != serviceEndTimeStr && !"".equals(serviceEndTimeStr)){
        	 serviceEndTime = DateUtil.formateDate(serviceEndTimeStr+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
        	 umpCompanyService.setServiceEndTime(serviceEndTime);
         }
         
         if(null != channelId ){
        	 Set<UmpChannel> set = new HashSet<UmpChannel>();
        	 set.add(UmpChannel.findUmpChannel(channelId));
  	         umpCompanyService.setChannels(set); 
         }
         
        Map parmMap = new HashMap();
 		QueryModel<UmpCompanyService> qm = new QueryModel<UmpCompanyService>(umpCompanyService,start, limit);
		qm.getInputMap().putAll(parmMap);
		
		 PageModel<UmpCompanyService> pm = UmpCompanyService.getQueryUmpCompanyServices(qm);
		 pm.setPageSize(limit);
		 pm.setStartIndex(start);

		 List<UmpCompanyService> list = pm.getDataList();
		 UmpProduct pc = null;
		 UmpVersion vc = null;
	      for(UmpCompanyService cs:list){ //获取公司，产品，版本，渠道的显示值
	    		pc = UmpProduct.findUmpProduct(cs.getProductId());
	    		cs.setProductName(pc.getProductName());
	    		vc = UmpVersion.findUmpVersion(cs.getVersionId());
	    		cs.setVersionName(vc.getVersionName());
	    	if(cs.getCompanyServiceStatus() == UmpCompanyServiceStatus.开通 || cs.getCompanyServiceStatus() == UmpCompanyServiceStatus.试用 ){
	    		if(null != cs.getServiceEndTime()){
	    			String daoqiDate = DateUtil.dayOfBefore(cs.getServiceEndTime(), 30); //遇到期时间（开通产品到期前一个月）
	    			String currentDate = DateUtil.formateDateToString(new Date(System.currentTimeMillis()), "yyyy-MM-dd");
	    			System.out.println("daoqiDate "+daoqiDate);
	    			System.out.println("currentDate "+currentDate);
	    		  if(daoqiDate.equals(currentDate)){
	    			  cs.setCompanyServiceStatus(UmpCompanyServiceStatus.预到期);	
	    		  }
	    		  if(DateUtil.formateDateToString(cs.getServiceEndTime(), "yyyy-MM-dd") .equals( DateUtil.formateDateToString(new Date(System.currentTimeMillis()), "yyyy-MM-dd"))){
    				  cs.setCompanyServiceStatus(UmpCompanyServiceStatus.到期);	
    			  }
	    		}
	    	 }
	    	
	       }
	    	
			uiModel.addAttribute("maxPages", pm.getTotalPage());
			uiModel.addAttribute("page", pm.getPageNo());
			uiModel.addAttribute("limit", pm.getPageSize());
	        uiModel.addAttribute("list", list);
	        
	        return "umpcompanyservices/result";
	    }	
	 
	   /**
	     * 添加服务
	     */
	    @ResponseBody
	    @RequestMapping(value = "/addService", produces = "text/html")
	    public void addService(
	    		Model uiModel, 
	    		HttpServletRequest request,
	    		HttpServletResponse response,
	    		 @RequestParam(value = "companyCode", required = false) String companyCode,
	    		 @RequestParam(value = "productId", required = false) Long productId,
	    		 @RequestParam(value = "channelIds", required = false) String channelIds,
	    		 @RequestParam(value = "versionId", required = false) Long versionId,
	    		 @RequestParam(value = "maxAccount", required = false) Long maxAccount,
	    		 @RequestParam(value = "serviceStartTime", required = false) String serviceStartTimeStr,
	    		 @RequestParam(value = "serviceEndTime", required = false) String serviceEndTimeStr
	    		) {
	     
	    	
	        String formatPattern = "yyyy-MM-dd";
			 Date serviceStartTime =  DateUtil.formateDate(serviceStartTimeStr, formatPattern);
			 Date serviceEndTime =  DateUtil.formateDate(serviceEndTimeStr, formatPattern);
	        
			UmpCompanyService companyService =  new UmpCompanyService();
	        companyService.setProductId(productId);
	        companyService.setVersionId(versionId); 
	        companyService.setCompanyCode(companyCode);
	        companyService.setMaxAccount(maxAccount);
	        companyService.setCompanyServiceStatus(UmpCompanyServiceStatus.试用);
	        companyService.setServiceStartTime(serviceStartTime);
	        companyService.setServiceEndTime(serviceEndTime);
	        companyService.getChannels().addAll(UmpChannel.findAllUmpChannelsByIds(channelIds));
	        companyService.setCreateTime(new Date());
	        companyService.persist();
	        
	        String msg = "{success:true}";
	        response.setContentType("text/Xml;charset=utf-8");
			PrintWriter out = null;
			try {
				 out = response.getWriter();
				 out.println(msg);
			} catch (IOException ex1) {
				ex1.printStackTrace();
			} finally {
				out.close();
			}
	    }
	    
	    
	    /**
	     * 修改服务
	     */
	    @RequestMapping(value = "/updateService", produces = "text/html")
	    public String updateService(@Valid UmpCompanyService umpCompanyService, 
	    		BindingResult bindingResult, Model uiModel, HttpServletRequest request,
	    		 @RequestParam(value = "id", required = false) Long id,
	    		 @RequestParam(value = "companyServiceStatus", required = false) Long companyServiceStatus,
					 @RequestParam(value = "companyCode", required = false) String companyCode,
					 @RequestParam(value = "productId", required = false) Long productId,
					 @RequestParam(value = "channelIds", required = false) String channelIds,
					 @RequestParam(value = "versionId", required = false) Long versionId,
					 @RequestParam(value = "maxAccount", required = false) Long maxAccount,
					 @RequestParam(value = "serviceStartTime", required = false) String serviceStartTimeStr,
					 @RequestParam(value = "serviceEndTime", required = false) String serviceEndTimeStr
	    		 
	    		) {
            
	        UmpCompanyService companyService =  UmpCompanyService.findUmpCompanyService(id);
	        companyService.setCompanyServiceStatus(UmpCompanyServiceStatus.getEnum(companyServiceStatus.intValue()));
	        companyService.setProductId(umpCompanyService.getProductId());
	        companyService.setVersionId(versionId); 
	        Set<UmpChannel> setChannel = new HashSet<UmpChannel>();
	        setChannel.addAll(UmpChannel.findAllUmpChannelsByIds(channelIds));
	        companyService.setChannels(setChannel);
	        companyService.setMaxAccount(umpCompanyService.getMaxAccount());
	        Date serviceStartTime = null;
            Date serviceEndTime = null;
            
            
            SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
            try {
			  serviceStartTime = new Date(formate.parse(serviceStartTimeStr).getTime());
			  serviceEndTime = new Date(formate.parse(serviceEndTimeStr).getTime());
			  companyService.setServiceStartTime(serviceStartTime);
		       companyService.setServiceEndTime(serviceEndTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
	       
	        companyService.merge();
	        return "umpcompanyservices/result";
	    }
	 
	 
	 /**
	     * 下拉复选
	     * @param uiModel
	     */
	    void initSelect( Model uiModel){
	    	//审核状态
	    	 Map<Long, String> statusMap = new HashMap<Long, String>();
		         statusMap.put(1L, "待审核");
		    	 statusMap.put(3L,"审核不通过");
	    	 //服务状态
              List<UmpCompanyServiceStatus> serviceStatus = new ArrayList<UmpCompanyServiceStatus>();
		    	 serviceStatus.add(UmpCompanyServiceStatus.试用);
		    	 serviceStatus.add(UmpCompanyServiceStatus.开通);
		    	 serviceStatus.add(UmpCompanyServiceStatus.到期);
		    	 serviceStatus.add(UmpCompanyServiceStatus.停止);
		    	 serviceStatus.add(UmpCompanyServiceStatus.预到期);
		    	 
		     //操作状态
		     List<UmpCompanyServiceStatus> operatorServiceStatus = new ArrayList<UmpCompanyServiceStatus>();
		      for(UmpCompanyServiceStatus companyService:UmpCompanyServiceStatus.values()){
		    	 operatorServiceStatus.add(companyService);
		      }
		   
		    List<UmpProduct> productList = UmpProduct.findAllUmpProducts();
		    uiModel.addAttribute("productList", productList);
		    
		    uiModel.addAttribute("channelList", UmpChannel.findAllUmpChannels());
		    uiModel.addAttribute("versionList", UmpVersion.findAllUmpVersions());
		    uiModel.addAttribute("statusMap", statusMap);
		    uiModel.addAttribute("serviceStatus", serviceStatus);
		    uiModel.addAttribute("operatorServiceStatus",operatorServiceStatus);
		    uiModel.addAttribute("bussinessTypeMap", UmpBusinessType.findAllUmpBusinessTypes());
		    uiModel.addAttribute("umpcompanys", UmpCompany.findAllServiceUmpCompanys());
	    }
	
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid UmpCompanyService umpCompanyService, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, umpCompanyService);
            return "umpcompanyservices/create";
        }
        uiModel.asMap().clear();
        umpCompanyService.persist();
        return "redirect:/umpcompanyservices/" + encodeUrlPathSegment(umpCompanyService.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new UmpCompanyService());
        return "umpcompanyservices/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("umpcompanyservice", UmpCompanyService.findUmpCompanyService(id));
        uiModel.addAttribute("itemId", id);
        return "umpcompanyservices/show";
    }


	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid UmpCompanyService umpCompanyService, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, umpCompanyService);
            return "umpcompanyservices/update";
        }
        uiModel.asMap().clear();
        umpCompanyService.merge();
        return "redirect:/umpcompanyservices/" + encodeUrlPathSegment(umpCompanyService.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, UmpCompanyService.findUmpCompanyService(id));
        return "umpcompanyservices/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        UmpCompanyService umpCompanyService = UmpCompanyService.findUmpCompanyService(id);
        umpCompanyService.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/umpcompanyservices";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("umpCompanyService_servicestarttime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("umpCompanyService_serviceendtime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, UmpCompanyService umpCompanyService) {
        uiModel.addAttribute("umpCompanyService", umpCompanyService);
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
	@ResponseBody
	@RequestMapping(value = "getUmpChanelByProduct")
    public void getUmpChanelByProduct(@RequestParam(value = "id") Long id,
			HttpServletRequest request, HttpServletResponse response) {
    	response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			 out = response.getWriter();
			 StringBuffer sb = new StringBuffer();// 拼成Json格式字符串{key:value,key1:value1}
			 sb.append("[");
			 boolean flag = true;
			 for(UmpChannel chanel:UmpChannel.findUmpChannelByProduct(id)){
				  if(flag){
					sb.append("{id:"+chanel.getId()+",");
					sb.append("name:\'"+chanel.getChannelName()+"\'}"); 
					flag = false;  
				  }else{
					sb.append(",{id:"+chanel.getId()+",");
				    sb.append("name:\'"+chanel.getChannelName()+"\'}");  
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
    
    @ResponseBody
	@RequestMapping(value = "getUmpVersionByProduct")
    public void getUmpVersionByProduct(@RequestParam(value = "id") Long id,
			HttpServletRequest request, HttpServletResponse response) {
    	response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			 out = response.getWriter();
			 StringBuffer sb = new StringBuffer();// 拼成Json格式字符串{key:value,key1:value1}
			 sb.append("[");
			 boolean flag = true;
			 for(UmpVersion version:UmpVersion.findUmpVersionByProduct(id)){
				  if(flag){
					  sb.append("{id:"+version.getId()+",");
					  sb.append("name:\'"+version.getVersionName()+"\'}"); 
					flag = false;  
				  }else{
					sb.append(",{id:"+version.getId()+",");
				    sb.append("name:\'"+version.getVersionName()+"\'}");  
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
    
    @ResponseBody
   	@RequestMapping(value = "queryAddableProductByCompanyCode")
       public void queryAddableProductByCompanyCode(@RequestParam(value = "companyCode") String companyCode,
   			HttpServletRequest request, HttpServletResponse response) {
       	response.setContentType("text/Xml;charset=utf-8");
   		PrintWriter out = null;
   		try {
   			 out = response.getWriter();
   			 StringBuffer sb = new StringBuffer();// 拼成Json格式字符串{key:value,key1:value1}
   			 sb.append("[");
   			 boolean flag = true;
   			List<Object[]> objList = UmpProduct.queryAddableProductByCompanyId(companyCode);
   			 for(Object[] obj:objList){
   				 String id = String.valueOf(obj[0]);
   				 String name = String.valueOf(obj[1]);
   				  if(flag){
   					sb.append("{id:"+id+",");
   					sb.append("name:\'"+name+"\'}"); 
   					flag = false;  
   				  }else{
   					sb.append(",{id:"+id+",");
   				    sb.append("name:\'"+name+"\'}");  
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
    
    
    @ResponseBody
   	@RequestMapping(value = "getUmpCompanyByCode")
       public void getUmpCompanyByCode(@RequestParam(value = "companyCode") String companyCode,
   			HttpServletRequest request, HttpServletResponse response) {
       	response.setContentType("text/Xml;charset=utf-8");
   		PrintWriter out = null;
   		try {
   			 out = response.getWriter();
   			 UmpCompany company=UmpCompany.findUmpCompanyByCode(companyCode);
   			 List<PubOperator> operatorList = PubOperator.findPubOperatorsByCompany(company);
   			PubOperator operator = operatorList.get(0);
   			
   			 StringBuilder sb = new StringBuilder();
	   			sb.append("{ id:\'"+company.getCompanyCode()+"\',");
	   			sb.append("name:\'"+company.getName()+"\',");
	   			sb.append("account:\'"+operator.getAccount()+"\',");
	   			sb.append("email:\'"+operator.getEmail()+"\',");
	   			sb.append("mobile:\'"+company.getMobilePhone()+"\',");
	   			sb.append("url:\'"+company.getUrl()+"\',");
	   			sb.append("address:\'"+company.getAddress()+"\' }");
   			 out.println(sb);
   		} catch (IOException ex1) {
   			ex1.printStackTrace();
   		} finally {
   			out.close();
   		}

       }
    
    /**
     * 根据id获取产品信息
     * @param productId
     * @param request
     * @param response
     */
    @ResponseBody
   	@RequestMapping(value = "getVersionById")
       public void getVersionById(@RequestParam(value = "versionId") Long versionId,
   			HttpServletRequest request, HttpServletResponse response) {
       	response.setContentType("text/Xml;charset=utf-8");
   		PrintWriter out = null;
   		try {
   			 out = response.getWriter();
   			 UmpVersion version = UmpVersion.findUmpVersion(versionId);
   			 StringBuilder sb = new StringBuilder();
   			   sb.append("[");
	   			sb.append("{ id:\'"+version.getId()+"\',");
	   			sb.append("name:\'"+version.getVersionName()+"\'");
	   			sb.append("}");
	   			sb.append("]");
   			 out.println(sb);
   		} catch (IOException ex1) {
   			ex1.printStackTrace();
   		} finally {
   			out.close();
   		}

       }
    
    /**
     * 根据产品id获取渠道信息
     * @param productId
     * @param request
     * @param response
     */
    @ResponseBody
   	@RequestMapping(value = "getChannelByProductId")
       public void getChannelByProductId(@RequestParam(value = "productId") Long productId,
   			HttpServletRequest request, HttpServletResponse response) {
       	response.setContentType("text/Xml;charset=utf-8");
   		PrintWriter out = null;
   		try {
   			 out = response.getWriter();
   			List<UmpChannel> list = UmpChannel.findUmpChannelByProduct(productId);
   			 StringBuilder sb = new StringBuilder();
   			 boolean flag = true;
   			   sb.append("[");
   			   for(UmpChannel channel:list){
   				   if(flag){
   					sb.append("{ id:\'"+channel.getId()+"\',");
   		   			sb.append("name:\'"+channel.getChannelName()+"\'");
   		   			sb.append("}");
   					 flag = false; 
   				   }else{
   					sb.append(",{ id:\'"+channel.getId()+"\',");
   		   			sb.append("name:\'"+channel.getChannelName()+"\'");
   		   			sb.append("}");
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
	 * 加载选中的下拉复选
	 */
	@ResponseBody
	@RequestMapping(value = "getCheckedMultSelect")
	public void getCheckedMultSelect(
			HttpServletRequest request,
			@RequestParam(value = "companyServiceId",required = true) Long companyServiceId,
			@RequestParam(value = "productId",required = true) Long productId,
			HttpServletResponse response) {
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			 StringBuffer sb = new StringBuffer(); 
			 
			 List<UmpChannel> allProductChannels = UmpChannel.findUmpChannelByProduct(productId); //voc产品下所有的渠道
			 
			  List<UmpChannel> selectedChannels = UmpCompanyService.findChannelsByCompanyServiceId(companyServiceId); //需要选中的渠道
			
			boolean allChannelFlag = true;
			StringBuffer allChannels = new StringBuffer();
			allChannels.append("\"allChannels\":[");
			for(UmpChannel uc:allProductChannels){
			  if(allChannelFlag){
				  allChannels.append("{\"id\":"+uc.getId()+",\"name\":\""+uc.getChannelName()+"\",\"open\":true}");
				  allChannelFlag = false;	
			  }else{
				  allChannels.append(",{\"id\":"+uc.getId()+",\"name\":\""+uc.getChannelName()+"\",\"open\":true}");
			  }
				
			}
			allChannels.append(" ] ");
			
			
			boolean selectedChannelFlag = true;
			StringBuffer selectedChannel = new StringBuffer();
			selectedChannel.append("\"selectedChannels\":[");
			String names = "";
			String ids = "";
			
			for(UmpChannel uc:selectedChannels){
			  if(selectedChannelFlag){
				  selectedChannel.append("{\"id\":"+uc.getId()+",\"name\":\""+uc.getChannelName()+"\",\"open\":true}");
				  names += uc.getChannelName();
				  ids += uc.getId();
				 selectedChannelFlag = false;	
			  }else{
				  selectedChannel.append(",{\"id\":"+uc.getId()+",\"name\":\""+uc.getChannelName()+"\",\"open\":true}");
				  names += ","+uc.getChannelName();
				  ids += ","+uc.getId();
			  }
			}
			selectedChannel.append(" ] ");
			
			 StringBuffer selectedCfg = new StringBuffer();
			  selectedCfg.append("\"selectedCfg\":[{\"ids\":\""+ids+"\",\"names\":\""+names+"\"}]");
			 
			 
			sb.append(" { ");
			 sb.append(allChannels);
			 sb.append(",");
			 sb.append(selectedChannel);
			 sb.append(",");
			 sb.append(selectedCfg);
			sb.append(" } ");

			out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

	}
    
    /**
     * 根据产品id获取信息
     * @param productId
     * @param request
     * @param response
     */
    @ResponseBody
   	@RequestMapping(value = "getProductById")
       public void getProductById(@RequestParam(value = "productId") Long productId,
   			HttpServletRequest request, HttpServletResponse response) {
       	response.setContentType("text/Xml;charset=utf-8");
   		PrintWriter out = null;
   		try {
   			 out = response.getWriter();
   			UmpProduct product = UmpProduct.findUmpProduct(productId);
   			 StringBuilder sb = new StringBuilder();
   			sb.append(" [ ");
   			    sb.append("{ id:\'"+product.getId()+"\',");
	   			sb.append("name:\'"+product.getProductName()+"\'");
	   			sb.append("}");
	   			sb.append(" ] ");
   			 out.println(sb);
   		} catch (IOException ex1) {
   			ex1.printStackTrace();
   		} finally {
   			out.close();
   		}

       }
}

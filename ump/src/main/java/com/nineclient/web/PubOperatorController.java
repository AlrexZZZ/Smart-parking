package com.nineclient.web;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.nineclient.constant.PlatformType;
import com.nineclient.constant.WccPlateStatus;
import com.nineclient.dto.PubOperatorDto;
import com.nineclient.model.PubOperator;
import com.nineclient.model.PubOrganization;
import com.nineclient.model.PubRole;
import com.nineclient.model.UmpAuthority;
import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpCompanyService;
import com.nineclient.model.UmpProduct;
import com.nineclient.model.VocAccount;
import com.nineclient.model.VocBrand;
import com.nineclient.model.VocEmail;
import com.nineclient.model.VocShop;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Global;
import com.nineclient.utils.MD5Util;
import com.nineclient.utils.PageModel;

import java.awt.BufferCapabilities;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * 公共账号管理
 * @author
 *
 */
@RequestMapping("/puboperators")
@Controller
@RooWebScaffold(path = "puboperators", formBackingObject = PubOperator.class)
public class PubOperatorController {
	private final Logger log = LoggerFactory.getLogger(PubOperatorController.class);

	@RequestMapping(value="/addPubOper",method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid PubOperator pubOperator, BindingResult bindingResult, Model uiModel, 
    		HttpServletRequest httpServletRequest,
    		@RequestParam(value = "organName", required = false) String organName) {
		Long maxAcount = 0l;
		PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
		List<UmpCompanyService> umpcsList = UmpCompanyService.findUmpCompanyServiceByCode(pub.getCompany().getCompanyCode());
		if(null != umpcsList && umpcsList.size() > 0){
			for (UmpCompanyService umpcs : umpcsList) {
				if((umpcs.getServiceEndTime().getTime() - (new Date().getTime())) > 0){
					if(umpcs.getProductId() == 3){//wcc产品
						maxAcount += umpcs.getMaxAccount();
					}else if(umpcs.getProductId() == 2){//voc产品
						maxAcount += umpcs.getMaxAccount();
					}
				}	
			}
		}
		log.info("======"+maxAcount);
		List<PubOperator> pubOper = PubOperator.findPubOperatorsByCompany(pub.getCompany());
		if(null !=pubOper && pubOper.size() > maxAcount){
			 populateEditForm(uiModel, pubOperator);
			 uiModel.addAttribute("pubroles", PubRole.findAllPubRoles(pub.getCompany()));
			 uiModel.addAttribute("msg", "你创建账号数已满");
	         return "puboperators/create";
		}
		
		if(null == organName && "".equals(organName)){
			 populateEditForm(uiModel, pubOperator);
			 uiModel.addAttribute("msg", "请选择所属");
			 uiModel.addAttribute("pubroles", PubRole.findAllPubRoles(pub.getCompany()));
	         return "puboperators/create";
		}
		
		pubOperator.setPassword(MD5Util.MD5(pubOperator.getPassword()));
		pubOperator.setOrganization(PubOrganization.findPubOrganizationByName(organName,pub));
		pubOperator.setCreateTime(new Date());
		pubOperator.setIsDeleted(true);
		pubOperator.setCompany(pub.getCompany());
        pubOperator.persist();
        uiModel.asMap().clear();
        addVocEmail(pubOperator,false);
        uiModel.addAttribute("page",1);
		uiModel.addAttribute("size",10);
		return "redirect:/puboperators?page=1&amp;size=10";
    }
	
	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel,HttpServletRequest httpServletRequest) {
        populateEditForm(uiModel, new PubOperator());
        uiModel.addAttribute("pubroles", PubRole.findAllPubRoles(CommonUtils.getCurrentPubOperator(httpServletRequest).getCompany()));
        return "puboperators/create";
    }
	@ResponseBody
	@RequestMapping(value="/validateAccountCount",method = RequestMethod.POST,produces = "text/html;charset=utf-8")
	public String validateAccountCount(HttpServletRequest httpServletRequest){
		PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
		UmpCompany company = pub.getCompany();
		Long count = UmpCompanyService.findMaxAccountCount(company);
		Long currAccount =PubOperator.findCurrentAccountCount(company);
		if(currAccount+1<=count){
			return "1";
		}else{
			return "0";
		}
		
	}
	@RequestMapping(produces = "text/html;charset=utf-8")
    public String list(@RequestParam(value = "page", required = false) Integer page,
    		@RequestParam(value = "size", required = false) Integer size,
    		@RequestParam(value = "displayId", required = false) Long displayId,
    		Model uiModel,HttpServletRequest httpServletRequest) {
        if(displayId == null){
        	displayId = CommonUtils.getCurrentDisPlayId(httpServletRequest);
        }
        httpServletRequest.getSession().setAttribute("displayId", displayId);
        uiModel.addAttribute("pubroles", PubRole.findAllPubRoles(CommonUtils.getCurrentPubOperator(httpServletRequest).getCompany()));
        uiModel.addAttribute("platformArr", PlatformType.values());
        return "puboperators/list";
    }

	@RequestMapping(value="/getPubOperListByFiled",method = RequestMethod.POST,produces = "text/html;charset=utf-8")
	@ResponseBody
	public String getPubOperListByFiled(PubOperator pubOperator, Model uiModel,
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "organName", required = false) String organName,
			@RequestParam(value = "pubAccount", required = false) String pubAccount,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "pubRole", required = false) Long pubRole,
			@RequestParam(value = "active", required = false) Boolean active,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "platType",required = false)String plattype) throws Exception{
		PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
		uiModel.addAttribute("pubroles", PubRole.findAllPubRoles(pub.getCompany()));
		
		Map<String,Object> parmMap = new HashMap<String, Object>();
		parmMap.put("organName", organName);
		parmMap.put("pubAccount", pubAccount);
		parmMap.put("email", email);
		parmMap.put("pubRole", pubRole);
		parmMap.put("active", active);
		parmMap.put("plattype",plattype);
		
		int sizeNo = size == null ? 4 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
		List<PubOperator> pubOperList = PubOperator.findPubOperatorEntriesByFiled(firstResult, sizeNo, organName,pubAccount,email,pubRole,active,pub,plattype);
		Set<WccPlatformUser> sets=null;
		StringBuffer strb=null;
		PubOperatorDto pubdto=null;
		List<PubOperatorDto> rtlists=new ArrayList<PubOperatorDto>();
		if(pubOperList!=null&&pubOperList.size()>0)
		{
		for(int i=0;i<pubOperList.size();i++)
		{   strb=new StringBuffer();
			sets=pubOperList.get(i).getPlatformUsers();
			pubdto=new PubOperatorDto();
			BeanUtils.copyProperties(pubdto,pubOperList.get(i));
			if(sets!=null)
			{
				
				for (WccPlatformUser wccPlatformUser : sets) {
					strb.append(wccPlatformUser.getAccount()).append(";");
				}
			}
			pubdto.setPlatStrings(strb.toString());
			rtlists.add(pubdto);
			
		}
		}
		Long count = PubOperator.countWccStoresByFiled(organName,pubAccount,email,pubRole,active,pub);
		PageModel<PubOperator> pageMode = new PageModel<PubOperator>();
		pageMode.setPageNo(page);
		pageMode.setTotalCount(Integer.parseInt(""+count));
		pageMode.setPageSize(sizeNo);
		pageMode.setDataJson(PubOperator.toJsonArrayDto(rtlists));
		
		return pageMode.toJson();
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST, produces = "text/html")
    public String update(PubOperator pubOperator, BindingResult bindingResult, Model uiModel, 
    		HttpServletRequest httpServletRequest,
    		@RequestParam(value = "organName", required = false) String organName) {
		PubOperator pubOperators = PubOperator.findPubOperator(pubOperator.getId());
		pubOperators.setAccount(pubOperator.getAccount());
		pubOperators.setMobile(pubOperator.getMobile());
		pubOperators.setOperatorName(pubOperator.getOperatorName());
		pubOperators.setPassword(MD5Util.MD5(pubOperator.getPassword()));
		pubOperators.setEmail(pubOperator.getEmail());
		pubOperators.setPubRole(pubOperator.getPubRole());
		pubOperators.setActive(pubOperator.getActive());
		PubOrganization pubOrgan = PubOrganization.findPubOrganizationByName(organName,CommonUtils.getCurrentPubOperator(httpServletRequest));
		pubOperators.setOrganization(pubOrgan);
		if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, pubOperator);
            return "puboperators/update";
        }
        uiModel.asMap().clear();
        pubOperators.merge();
        addVocEmail(pubOperators,false);
        return "redirect:/puboperators?page=1&amp;size=10";
    }
	
	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel,
    		HttpServletRequest httpServletRequest) {
		uiModel.addAttribute("pubroles", PubRole.findAllPubRoles(CommonUtils.getCurrentPubOperator(httpServletRequest).getCompany()));
        uiModel.addAttribute("puboperator", PubOperator.findPubOperator(id));
        return "puboperators/update";
    }
	
	/**
	 * 删除账号先查询是否被voc_comment引用
	 * @param id
	 * @param uiModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pubOperToVOC", method = RequestMethod.POST,produces="text/html;charset=utf-8")
	public String pubOperToVOC(@RequestParam(value = "id", required = false) Long id,Model uiModel,
    		HttpServletRequest request){
		String msg;
		int count = PubOperator.findPubOperToVocComment(id);
		log.info("====count===="+count);
		if(count > 0){
			msg = "true";
		}else{
			msg = "false";
		}
		return msg;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "text/html")
    public String delete(@RequestParam(value = "id", required = false) Long id,Model uiModel,
    		HttpServletRequest httpServletRequest) {
        PubOperator pubOperator = PubOperator.findPubOperator(id);
        pubOperator.remove();
        uiModel.asMap().clear();
        return "redirect:/puboperators?page=1&amp;size=10";
    }

	/**
	 * 改变用户的状态（是否禁用）
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
		PubOperator pubOperator = null;
		try {
			pubOperator = PubOperator.findPubOperator(id);
			pubOperator.setActive(active);
			pubOperator.merge();
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
	
	@RequestMapping(value = "/disSkill", method = RequestMethod.GET ,produces = "text/html")
	public String disSkill(Model uiModel,@RequestParam(value = "id",required = false) Long id,HttpServletRequest httpServletRequest,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size){
		
		PubOperator pubOperator = PubOperator.findPubOperator(id);
		uiModel.addAttribute("pubOperator", pubOperator);
		List<UmpProduct> umpProList =  new ArrayList<UmpProduct>();
		Set<Long> productIds = new HashSet<Long>();
		List<UmpCompanyService> umpcsList = UmpCompanyService.findUmpCompanyServiceByCode(pubOperator.getCompany().getCompanyCode());
		if(null != umpcsList && umpcsList.size() > 0){
				for (UmpCompanyService umpcs : umpcsList) {
					if((umpcs.getServiceEndTime().getTime() - (new Date().getTime())) > 0){
						productIds.add(umpcs.getProductId());
						if(umpcs.getProductId() == 3){//wcc产品
								if(pubOperator.getPlatformUsers().size() > 0){
									System.out.println(pubOperator.getPlatformUsers().size()+"===size===");
									List<WccPlatformUser>  platForms = CommonUtils.set2List(pubOperator.getPlatformUsers());
									StringBuffer ids = new StringBuffer();
									int n = 1;
									for (WccPlatformUser wccPlatformUser : platForms) {
										ids.append(wccPlatformUser.getId());
										if(n!=platForms.size()){
											ids.append(",");
										}
										n++;
									}
									uiModel.addAttribute("platformUserIds", ids);
								}
						}	
						if(umpcs.getProductId() == 4){//ucc产品
						}
						if(umpcs.getProductId() == 2){//voc产品
							if(pubOperator.getVocBrands().size() > 0){//品牌
								List<VocBrand>  vocBrands = CommonUtils.set2List(pubOperator.getVocBrands());
								StringBuffer ids = new StringBuffer();
								int n = 1;
								for (VocBrand vocBrand : vocBrands) {
									ids.append(vocBrand.getId());
									if(n!=vocBrands.size()){
										ids.append(",");
									}
									n++;
								}
								uiModel.addAttribute("vocBrandIds", ids);
							}
							if(pubOperator.getVocShops().size() > 0){//店铺
								System.out.println(pubOperator.getVocShops().size()+"--=shopsize-=");
								List<VocShop>  vocShops = CommonUtils.set2List(pubOperator.getVocShops());
								StringBuffer ids = new StringBuffer();
								int n = 1;
								for (VocShop vocShop : vocShops) {
									ids.append(vocShop.getId());
									if(n!=vocShops.size()){
										ids.append(",");
									}
									n++;
								}
								System.out.println(ids);
								uiModel.addAttribute("vocShopIds", ids);
							}
							if(pubOperator.getChannels().size() > 0){//平台
								List<UmpChannel>  umpChannels = CommonUtils.set2List(pubOperator.getChannels());
								StringBuffer ids = new StringBuffer();
								int n = 1;
								for (UmpChannel umpChannel : umpChannels) {
									ids.append(umpChannel.getId());
									if(n!=umpChannels.size()){
										ids.append(",");
									}
									n++;
								}
								uiModel.addAttribute("umpChannels", ids);
							}
							if(pubOperator.getVocAccounts().size() > 0){//平台
								List<VocAccount>  vocAccounts = CommonUtils.set2List(pubOperator.getVocAccounts());
								StringBuffer ids = new StringBuffer();
								int n = 1;
								for (VocAccount vocAccount : vocAccounts) {
									ids.append(vocAccount.getId());
									if(n!=vocAccounts.size()){
										ids.append(",");
									}
									n++;
								}
								uiModel.addAttribute("vocAccounts", ids);
							}
						}
					}
				}
			}
		
		if(productIds != null && productIds.size() > 0){
			for (Long pId : productIds) {
				if(pId != 4){
					umpProList.add(UmpProduct.findUmpProduct(pId));
				}
			}
		}
		uiModel.addAttribute("companyProducts",umpProList);
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		uiModel.addAttribute("platformArr", PlatformType.values());
		return "puboperators/disSkill";
	}
	
	/**
	 * 分配公众号
	 * @param uiModel
	 * @param id
	 * @param pubOperator
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/disSkillWccOper", method = RequestMethod.POST,produces = "text/html")
	public String disSkillOper(Model uiModel,
			@RequestParam(value = "id", required = false) Long id,
			PubOperator pubOperator,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "relatedIssues", required = false) String relatedIssues,
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "plattype", required = false) String plattype){
		if(relatedIssues == null){
			return "puboperators/disSkill";
		}
		
		PubOperator pubOperators = PubOperator.findPubOperator(id);
		Set<WccPlatformUser> platSets = new HashSet<WccPlatformUser>();
		String[] ids = relatedIssues.split(",");
		for (String platId : ids) {
			 platSets.add(WccPlatformUser.findWccPlatformUser(Long.parseLong(platId)));
		}
		pubOperators.setPlatformUsers(platSets);
		pubOperators.merge();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
	    uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
	    return "redirect:/puboperators?page=1&size=10";
	}
	
	/**
	 * 分配voc
	 * @param uiModel
	 * @param id
	 * @param pubOperator
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/disSkillOperVOC", method = RequestMethod.POST,produces = "text/html")
	public String disSkillOperVOC(Model uiModel,@RequestParam(value = "id", required = false) Long id,PubOperator pubOperator,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "vocChannel", required = false) String vocChannel,
			@RequestParam(value = "vocShop", required = false) String vocShop,
			@RequestParam(value = "vocBrand", required = false) String vocBrand,
			@RequestParam(value = "vocAccount", required = false) String vocAccount,
			@RequestParam(value = "autoAllocate", required = false) Boolean autoAllocate
			){
		if(id == null){
			return "puboperators/disSkill";
		}
		if(null == vocChannel || "".equals(vocChannel)){
			return "puboperators/disSkill";
		}
		if(null == vocShop || "".equals(vocShop)){
			return "puboperators/disSkill";
		}
		if(null == vocBrand || "".equals(vocBrand)){
			return "puboperators/disSkill";
		}
		if(null == vocAccount || "".equals(vocAccount)){
			return "puboperators/disSkill";
		}
		PubOperator pubOperators = PubOperator.findPubOperator(id);
		/**
		 * 分配平台（渠道）
		 */
		Set<UmpChannel> channels = new HashSet<UmpChannel>();
		String[] ids = vocChannel.split(",");
		for (String channId : ids) {
			channels.add(UmpChannel.findUmpChannel(Long.parseLong(channId)));
		}
		pubOperators.setChannels(channels);
		
		/**
		 * 分配店铺
		 */
		Set<VocShop> vocShops = new HashSet<VocShop>();
		String[] shopIds = vocShop.split(",");
		for (String shopId : shopIds) {
			vocShops.add(VocShop.findVocShop(Long.parseLong(shopId)));
		}
		pubOperators.setVocShops(vocShops);
		
		/**
		 * 分配品牌
		 */
		Set<VocBrand> vocBrands = new HashSet<VocBrand>();
		String[] vocBrandIds = vocBrand.split(",");
		for (String vocBrandId : vocBrandIds) {
			vocBrands.add(VocBrand.findVocBrand(Long.parseLong(vocBrandId)));
		}
		pubOperators.setVocBrands(vocBrands);
		
		/**
		 * 分配电商账号
		 */
		Set<VocAccount> vocAccounts = new HashSet<VocAccount>();
		String[] vocAccountIds = vocAccount.split(",");
		for (String vocAccountId : vocAccountIds) {
			vocAccounts.add(VocAccount.findVocAccount(Long.parseLong(vocAccountId)));
		}
		pubOperators.setVocAccounts(vocAccounts);
		
		//是否自动分配
		pubOperators.setAutoAllocate(autoAllocate);
		
		pubOperators.merge();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
	    uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
	    return "redirect:/puboperators";
	}
	
	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("pubOperator_createtime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, PubOperator pubOperator) {
        uiModel.addAttribute("pubOperator", pubOperator);
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
	@RequestMapping(value = "updatePassword", produces = "text/html")
	@ResponseBody
	public String updatePassword(@RequestParam(value = "id") long id,
			@RequestParam(value = "operatorName") String operatorName,
			@RequestParam(value = "password") String password) {
		PubOperator operator = PubOperator.findPubOperator(id);
		operator.setOperatorName(operatorName);
		if(!operator.getPassword().equals(password)){
			operator.setPassword(MD5Util.MD5(password));
		}
		operator.merge();
		return "0";
	}
	
	/**
	 * 添加账号
	 * 根据公司判断是否存在
	 * @param account
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/checkAccount", method = RequestMethod.POST,produces="text/html;charset=utf-8")
	@ResponseBody
	public String checkAccount(@RequestParam(value = "account") String account,HttpServletRequest request, HttpServletResponse response){
		String str = "{msg:\'true\'}";
		List<PubOperator> pubOperator = PubOperator.findPubOperatorByAccount(account,CommonUtils.getCurrentPubOperator(request));
		if(pubOperator !=null && pubOperator.size() > 0){
			str = "{msg:\'false\'}";
		}
		return str;
	}
	
	/**
	 * 
	 * @param pubOperator
	 */
	private void addVocEmail(PubOperator pubOperator,boolean isUpdate){
		PubRole role =	pubOperator.getPubRole();
		List<UmpAuthority> authority=null;
		UmpProduct product = null;
		boolean isVoc=false;
		try{
			if(role!=null){
				authority =role.getAuthoritys();
			}
			
			if(authority!=null){
				for (UmpAuthority umpAuthority : authority) {
					product =	umpAuthority.getProduct();
					if(product!=null&&product.getId()==Global.VOC){
						//---权限存在voc产品
						isVoc=true;
						break;
					}
				}
			}
			if(isVoc){
				VocEmail email = new VocEmail();
				if(isUpdate){
					email=VocEmail.findVocEmailByOperator(pubOperator.getId());
					email.setEmail(pubOperator.getEmail());
					email.merge();
				}else{
					email.setCreateTime(new Date());
					email.setEmail(pubOperator.getEmail());
					email.setIsDeleted(false);
					email.setIsVisable(true);
					email.setName(pubOperator.getOperatorName());
					email.setOperatorId(pubOperator.getId());
					email.setRemark("子账户邮件");
					email.setUmpCompanyId(pubOperator.getCompany().getId());
					email.persist();
				}
			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

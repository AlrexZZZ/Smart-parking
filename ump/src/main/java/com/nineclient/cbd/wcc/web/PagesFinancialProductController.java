package com.nineclient.cbd.wcc.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nineclient.cbd.wcc.model.WccCredentialType;
import com.nineclient.cbd.wcc.model.WccFinancialProduct;
import com.nineclient.cbd.wcc.model.WccFinancialUser;
import com.nineclient.model.PubOperator;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;

@RequestMapping("/pageFinancialProduct")
@Controller
@RooWebScaffold(path = "cbdwcc_pages/financialProduct", formBackingObject = WccRecordMsg.class)
public class PagesFinancialProductController {

	/**
	 * 查询页面
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "showFinancialProduct", produces = "text/html")
	public String showFinancialProduct(HttpServletRequest request,
			HttpServletResponse response, Model uiModel,
			@RequestParam(value = "platId", required = false) String platId,
			@RequestParam(value = "friendId", required = false) String friendId) {
		Long pid = null;
	if(null != platId && !"".equals(platId) ){
		pid = Long.parseLong(platId+"");
	}
    	
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platformUser = WccPlatformUser
				.findAllWccPlatformUsers(user);
		List<WccFinancialProduct> wccFinancialProducts = WccFinancialProduct
				.findFinancialProductsByDate(new Date(),pid);
		uiModel.addAttribute("friendId", friendId);
		uiModel.addAttribute("platId", platId);
		uiModel.addAttribute("platformUser", platformUser);
		uiModel.addAttribute("financialProducts", wccFinancialProducts);
		return "page/showFinancial";
	}
	
	/**
	 * 跳转到新增页面
	 * 
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(HttpServletRequest request,
			HttpServletResponse response, Model uiModel,
			@RequestParam(value = "dataId", required = false) String dataId,
			@RequestParam(value = "platId", required = false) String platId,
			@RequestParam(value = "friendId", required = false) String friendId,
			@RequestParam(value = "financialProductId", required = false) Long financialProductId) {
		List<WccCredentialType> credentialTypes = WccCredentialType.findAllWccCredentialType();
		String dateStr = DateUtil.getFirstDayOfCurrentYear(DateUtil.DATE_AND_TIME_FORMATER);
		//由粉丝id和理财产品id 来 查询当前用户是否有预约，校验预约产品的额度 。
		List<WccFinancialUser> li =null;
		
		long personTotalMoney = 0;
		if(null != friendId && null != financialProductId ){
			
			  li =  WccFinancialUser.findFinancialUsersInfo(financialProductId, Long.parseLong(friendId+""));
			  if(null != li &&  li.size() > 0){
				  for(WccFinancialUser u :li){
					  personTotalMoney += u.getMoney(); 
				  }
				  
			  }
			
		}
       
		List<WccFinancialUser> wccFinancialUsers = WccFinancialUser.findFinancialUsersByThisYear(dateStr,financialProductId);
		WccFinancialProduct wccFinancialProduct = WccFinancialProduct.findWccFinancialProduct(financialProductId);
		uiModel.addAttribute("friendId", friendId);
		uiModel.addAttribute("financialProduct", wccFinancialProduct);
		uiModel.addAttribute("credentialTypes", credentialTypes);
		uiModel.addAttribute("totalNumber", wccFinancialUsers.size());
		uiModel.addAttribute("totalMy", wccFinancialProduct.getMaxMoney());
		uiModel.addAttribute("personTotalMoney", personTotalMoney);
		uiModel.addAttribute("platId", platId);
		return "page/addFinancialUser";
	}
	
	
	@RequestMapping(value = "addFinancialUser", method = RequestMethod.POST, produces = "text/html")
	public String create(
			HttpServletRequest httpServletRequest,
			Model uiModel,
			@Valid WccFinancialUser wccFinancialUser,
			BindingResult bindingResult,
			@RequestParam(value = "platId", required = false) String platId,
			@RequestParam(value = "financialProductId", required = false) Long financialProductId,
			@RequestParam(value = "credentialTypeId", required = false) Long credentialTypeId,
			@RequestParam(value = "friendId", required = false) Long friendId)
			throws UnsupportedEncodingException {
//		if (bindingResult.hasErrors()) {
//			return "redirect:/pageFinancialProduct/showFinancialProduct";
//		}
		Set<WccFinancialProduct> wccFinancialProducts = new HashSet<WccFinancialProduct>();
		WccFinancialProduct wccFinancialProduct = WccFinancialProduct.findWccFinancialProduct(financialProductId);
		WccCredentialType wccCredentialType = WccCredentialType.findWccCredentialType(credentialTypeId);
		wccFinancialProduct.setCk("1");
		wccFinancialProduct.merge();
		if (wccFinancialProduct != null) {
			wccFinancialProducts.add(wccFinancialProduct);
		}
		if (wccFinancialProducts != null && wccFinancialProducts.size() > 0) {
			wccFinancialUser.setWccFinancialProducts(wccFinancialProducts);
		} else {
		}
		if (wccCredentialType != null) {
			wccFinancialUser.setCredentialType(wccCredentialType);
		}
		WccFriend wccFriend = WccFriend.findWccFriend(friendId);
		if (wccFriend != null) {
			wccFinancialUser.setWccFriend(wccFriend);
		}
		wccFinancialUser.setInsertTime(new Date());
		wccFinancialUser.setIsDelete(false);
		wccFinancialUser.setCk("1");
		if(platId != null && !platId.equals("")){
			wccFinancialUser.setPlatformUsers(WccPlatformUser.findWccPlatformUser(Long.parseLong(platId)).getAccount());
		}
		try {
			wccFinancialUser.persist();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "redirect:/pageFinancialProduct/showFinancialProduct";
	}

}

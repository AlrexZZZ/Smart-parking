package com.nineclient.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
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
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpParentBusinessType;
import com.nineclient.model.UmpProduct;
import com.nineclient.model.UmpVersion;
import com.nineclient.model.VocAccount;
import com.nineclient.model.VocAppkey;
import com.nineclient.model.VocShop;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.WebServiceClient;

@RequestMapping("/vocaccounts")
@Controller
@RooWebScaffold(path = "vocaccounts", formBackingObject = VocAccount.class)
public class VocAccountController {
	private static final Logger logger = Logger.getLogger(VocAccountController.class);
	@RequestMapping(value = "accountManage", method = RequestMethod.GET)
	public String accountManage(HttpServletRequest httpServletRequest,@RequestParam(value="displayId",required=false)Long displayId,
			Model model) {
		if(displayId == null){
	        displayId = CommonUtils.getCurrentDisPlayId(httpServletRequest);
	    }
		httpServletRequest.getSession().setAttribute("displayId", displayId);
		PubOperator user = CommonUtils.getCurrentPubOperator(httpServletRequest);
		UmpCompany company = user.getCompany();
		List<UmpProduct> product = UmpProduct.findUmpProductById(Global.VOC);
		//TODO 公司关联渠道
		List<UmpChannel> channels = UmpChannel.findAllChannelsbuyProductNameAndCompanyService(company, product.get(0));
		model.addAttribute("umpchannels",
				channels);
		return "vocaccounts/accountManage";
	}

	/**
	 * 添加账号
	 * 
	 * @param vocAccount
	 * @param bindingResult
	 * @param umpchannelId
	 * @param vocShopId
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String create(
			VocAccount vocAccount,
			BindingResult bindingResult,
			@RequestParam(value = "umpChannelId", required = false) Long umpchannelId,
			@RequestParam(value = "vocShopId", required = false) Long vocShopId,
			Model uiModel, HttpServletRequest httpServletRequest) {
		String msg = "";
		try {
			String account = vocAccount.getAccount();
			UmpCompany company = getUser(httpServletRequest);
			List<VocAccount> list = VocAccount.findAllVocAccounts(company,
					account, null);
			if (list != null && list.size() > 0) {
				msg = "账号不能重复";
				return msg;
			}
			UmpChannel channel = UmpChannel.findUmpChannel(umpchannelId);
			if (channel == null) {
				msg = "选择的渠道不存在";
				return msg;
			}
			vocAccount.setUmpChannel(channel);
			VocShop vocShop = null;
			if (vocShopId!=null&&vocShopId > 0) {
				vocShop = VocShop.findVocShop(vocShopId);
			}
			if (vocShop != null) {
				vocAccount.setVocShop(vocShop);
			}

			vocAccount.setCreateTime(new Date());
			vocAccount.setCompanyId(company.getId());
			vocAccount.setIsDeleted(false);
			vocAccount.persist();
			msg = "成功";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "失败";
		}
		return msg;
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new VocAccount());
		return "vocaccounts/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("vocaccount", VocAccount.findVocAccount(id));
		uiModel.addAttribute("itemId", id);
		return "vocaccounts/show";
	}

	@ResponseBody
	@RequestMapping(value = "list", produces = "text/html;charset=utf-8")
	public String list(
			HttpServletRequest request,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "channelId", required = false) Long channelId,
			@RequestParam(value = "vocShopId", required = false) Long vocShopId,
			@RequestParam(value = "isVisable", required = false) Boolean isVisable,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {
		PageModel<VocAccount> pageMode = new PageModel<VocAccount>();
		try {
			UmpCompany company = getUser(request);
			VocAccount vocAccount = new VocAccount();
			vocAccount.setCompanyId(company.getId());
			page = page == null ? 0 : page;
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == 0 ? page : (page.intValue() - 1)
					* sizeNo;

			List<VocAccount> vocAccounts = VocAccount.findVocAccountEntries(
					isVisable, channelId, vocShopId, company, firstResult,
					sizeNo, sortFieldName, sortOrder);
			long count = VocAccount.countVocAccounts(isVisable, channelId,
					vocShopId, company);

			pageMode.setDataJson(VocAccount.toJsonArray(vocAccounts));
			pageMode.setPageSize(size);
			pageMode.setPageNo(page);
			pageMode.setTotalCount(Integer.parseInt(count + ""));
		} catch (Exception e) {
			e.printStackTrace();

		}
		return pageMode.toJson();
	}
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String update(
			VocAccount vocAccount,
			BindingResult bindingResult,
			Model uiModel,
			@RequestParam(value = "umpChannelId", required = false) Long umpchannelId,
			@RequestParam(value = "vocShopId", required = false) Long vocShopId,
			HttpServletRequest httpServletRequest) {
		UmpCompany company = getUser(httpServletRequest);
		String account = vocAccount.getAccount();
		Long id = vocAccount.getId();
		List<VocAccount> list = VocAccount.findAllVocAccounts(company, account,
				id);
		if (list != null & list.size() > 0) {
			return "账号名称不能重复";
		}
		try {
			UmpChannel umpChannel = UmpChannel.findUmpChannel(umpchannelId);
			VocShop vocShop = null;
			if (!StringUtils.isEmpty(vocShopId)&&vocShopId > 0) {
				vocShop = VocShop.findVocShop(vocShopId);
			}

			VocAccount meAccount = VocAccount.findVocAccount(id);
			meAccount.setAccount(vocAccount.getAccount());
			meAccount.setPassword(vocAccount.getPassword());
			meAccount.setUmpChannel(umpChannel);
			if (vocShop != null) {
				meAccount.setVocShop(vocShop);
			}else{
				meAccount.setVocShop(null);
			}
			uiModel.asMap().clear();
			meAccount.merge();
		} catch (Exception e) {
			e.printStackTrace();
			return NotifyMsg.EXCEPTION;
		}
		return NotifyMsg.SUCCESS;
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, VocAccount.findVocAccount(id));
		return "vocaccounts/update";
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String delete(@RequestParam("id") Long id, Model uiModel) {
		String msg = "";
		try {
			VocAccount vocAccount = VocAccount.findVocAccount(id);
			vocAccount.remove();
			uiModel.asMap().clear();
			msg = "成功";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "失败";
		}
		return msg;
	}

	@ResponseBody
	@RequestMapping(value = "updateVisable", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String updateVisable(@RequestParam(value = "id") Long id,
			@RequestParam(value = "isVisable") Boolean isVisable, Model uiModel) {
		String msg = "";
		try {
			VocAccount vocAccount = VocAccount.findVocAccount(id);
			vocAccount.setIsVisable(isVisable);
			vocAccount.merge();
			msg = "成功";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "失败";
		}
		return msg;
	}
	/**
	 * 去认证
	 * @param request
	 * @param model
	 * @param accountId
	 * @param appKeyId
	 * @return
	 */
	@RequestMapping(value = "approveAccount", produces = "text/html")
	public void approveAccount(HttpServletRequest request, Model model,
			@RequestParam(value = "accountId") Long accountId,
			HttpServletResponse response,
			@RequestParam(value = "appKeyId") Long appKeyId) {
		UmpCompany company = getUser(request);
		VocAccount vocAccount = VocAccount.findVocAccount(accountId);
		if (appKeyId == null || appKeyId < 0) {
			//return "请选择AppKey";
		}
		VocAppkey vocAppkey = VocAppkey.findVocAppkey(appKeyId);
		if (vocAppkey == null) {

			//return "appKey已不存在";
		}
		HttpSession session=request.getSession();
		model.asMap().clear();
		model.addAttribute("address", Global.TMALL_CALL);
		model.addAttribute("client_id", vocAppkey.getClientId());
		model.addAttribute("response_type", Global.TMALL_CODE);
		model.addAttribute("redirect_uri", vocAppkey.getRedirectUrl());
		if(session.getAttribute(Global.T_MALL_VOCACCOUNT)!=null){
			session.removeAttribute(Global.T_MALL_VOCACCOUNT);
		}
		if(session.getAttribute(Global.T_MALL_VOCAPPKEY)!=null){
			session.removeAttribute(Global.T_MALL_VOCAPPKEY);
		}
		session.setAttribute(Global.T_MALL_VOCACCOUNT,vocAccount);
		session.setAttribute(Global.T_MALL_VOCAPPKEY,vocAppkey);
		try {
			response.sendRedirect(Global.TMALL_CALL+"?client_id="+vocAppkey.getClientId()+"&response_type="+Global.TMALL_CODE+"&redirect_uri="+vocAppkey.getRedirectUrl());
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return "vocaccounts/TaoBaoCall";
	}
	/**
	 * 京东去认证
	 * @param request
	 * @param model
	 * @param accountId
	 * @param cookie
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "approveJdAccount", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String approveAccount(HttpServletRequest request, Model model,
			@RequestParam(value = "accountId") Long accountId,
			HttpServletResponse response,
			@RequestParam(value = "cookie") String cookie) {
		String msg = "";
		VocAccount vocAccount = VocAccount.findVocAccount(accountId);
		if (StringUtils.isEmpty(cookie)) {
			return "请填写cookie";
		}
		try{
			vocAccount.setCookie(cookie);
			vocAccount.setUmpChannel(UmpChannel.findUmpChannel(2L));
			vocAccount.setApproveTime(new Date());
			vocAccount.setCreateTime(new Date());
			vocAccount.setIsApprove(true);
			vocAccount.merge();
			msg = "认证成功";
		}catch(Exception e){
			e.printStackTrace();
			msg="认证失败，请重试";
		}
		return msg;
	}
	/**
	 * 认证sessionkey
	 * @param request
	 * @param model
	 * @param accountId
	 * @param appKeyId
	 * @return
	 */
	@RequestMapping(value = "callBack", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String approveSessionKey(HttpServletRequest request, Model model,
			@RequestParam(value = "code",required=false) String code,@RequestParam(value = "state",required=false)String state,
			@RequestParam(value = "error",required=false)String error
			,@RequestParam(value = "error_description",required=false)String error_description
			){
		JSONObject json = null;
		VocAccount vocAccount= null;
		HttpSession session = null;
		VocAppkey vocAppkey= null;
		String s="";
	    String taobao_user = "";
		//判断是否是购买试用
		if(error==null&&code!=null&&error_description==null&&state!=null){
			logger.info("试用入口。。。。。。"+state);
			if(state.contains("itemCode")){
				 try{
					 logger.info("试用入口。。。。。。1");
					model.addAttribute("UmpParentBusinessTypeList",
							UmpParentBusinessType
									.findAllUmpParentBusinessTypes());
					model.addAttribute("products",
							UmpProduct.findAllUmpProducts(Global.VOC));
					model.addAttribute("channelList",
							UmpChannel.findAllChannelsbuyProductId(Global.VOC));
					model.addAttribute("umpVersionList",
							UmpVersion.findAllVersionsbuyProductId(Global.VOC));
					model.addAttribute("umpCompany", new UmpCompany());
					 logger.info("试用入口。。。。。。2");
					// 获取用户淘宝账号
					vocAppkey = VocAppkey.findVocAppkey(1L);
					 logger.info("试用入口。。。。。。2");
					String url = "https://oauth.taobao.com/token";
					Map<String, String> props = new HashMap<String, String>();
					props.put("grant_type", "authorization_code");
					/* 测试时，需把test参数换成自己应用对应的值 */
					props.put("code", code);
					props.put("client_id", vocAppkey.getAppkey());
					props.put("client_secret", vocAppkey.getClientSecret());
					props.put("redirect_uri", Global.Url
							+ "/ump/vocaccounts/callBack");
					props.put("view", "web");
					s = com.taobao.api.internal.util.WebUtils.doPost(url,
							props, 30000, 30000);
					logger.info("获取用户淘宝账户：" + s);
					json = JSONObject.fromObject(s);
					taobao_user = String.valueOf(json.get("taobao_user_nick"));
				} catch (IOException e) {
					logger.error("获取淘宝账户异常：" + e.getMessage());
					e.printStackTrace();
				}
				model.addAttribute("taobao_user_nick", taobao_user);
				return "umpcompany/tmallRegister";
			}
		}
		if(error!=null){
			model.addAttribute("msg", "认证失败");
			return "vocaccounts/approveFail";
		}
		//error=access_denied&error_description=authorize reject&state=
		 session =request.getSession();
		 vocAccount=(VocAccount) session.getAttribute(Global.T_MALL_VOCACCOUNT);
		 vocAppkey=(VocAppkey) session.getAttribute(Global.T_MALL_VOCAPPKEY); 
		session.setAttribute(Global.T_MALL_VOCAPPKEY,vocAppkey);
		boolean flag=doAuthorize(code, vocAppkey.getClientId(), vocAppkey.getClientSecret(), vocAppkey.getRedirectUrl(),vocAccount);
		if(flag){
			model.addAttribute("msg", "认证成功");
			//认证成功将新账号添加到任务队列
			 WebServiceClient.getInstatnce().addNewAccount(vocAccount,vocAppkey);
			
		}else{
			model.addAttribute("msg", "认证失败");
		}
		model.addAttribute("info", "认证");
		model.addAttribute("btn", "关闭");
		return "vocaccounts/TaoBaoCallBack";
	}
	
	/**
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryVocAccountById", produces = "text/html;charset=utf-8")
	public String findeVocAccountById(@RequestParam(value = "id") Long id) {

		try {
			VocAccount account = VocAccount.findVocAccount(id);
			return account.toJson();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateCookie", produces = "text/html;charset=utf-8")
	public String updateCookie(@RequestParam(value = "id") Long id,
			@RequestParam(value = "cookie") String cookie,
			@RequestParam(value = "isValid") Boolean isValid) {
		String msg = "";
		try {
			VocAccount account = VocAccount.findVocAccount(id);
			account.setCookie(cookie);
			account.setIsValid(isValid);
			account.merge();
			msg = "成功";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "失败";
		}
		return msg;
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"vocAccount_createtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute(
				"vocAccount_effecttime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute(
				"vocAccount_expirytime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}
	/**
	 * 功能说明: 根据code 去认证，获得 包含sessionCode的字符串
	 */
	public boolean doAuthorize(String code,String clientId,String clientSecret,String redirectUrl,VocAccount vocAccount) {
		boolean flag =false;
		TrustManager[] trustAllCerts = { new X509TrustManager(){
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs,
					String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs,
					String authType) {
			}
		} };
		SSLContext sc = null;
		try {
			sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new SecureRandom());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null == sc)
			return false;

		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

		URL url = null;
		StringBuilder result = new StringBuilder();
		String ur ="https://oauth.taobao.com/token?grant_type=authorization_code&client_id="
				+ clientId + "&client_secret=" + clientSecret
				+ "&code=" + code + "&redirect_uri=" + redirectUrl;
		try {
			logger.info("获取sessionKey-url:"+ur);
			url = new URL(ur);
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url
					.openConnection();
			httpsURLConnection.setConnectTimeout(30000);
			httpsURLConnection.setReadTimeout(30000);
			httpsURLConnection.setDoOutput(true);
			httpsURLConnection.setDoInput(true);
			httpsURLConnection.setUseCaches(false);
			httpsURLConnection.setRequestMethod("POST");

			httpsURLConnection.connect();

			int responseCode = httpsURLConnection.getResponseCode();
			InputStream input = null;
			if (responseCode == 200)
				input = httpsURLConnection.getInputStream();
			else {
				input = httpsURLConnection.getErrorStream();
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			flag=false;
		} catch (MalformedURLException e) {
			logger.error("sessionKey接口获取异常1："+e.getMessage());
			flag=false;
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("sessionKey接口获取异常2："+e.getMessage());
			flag=false;
			e.printStackTrace();
		}
		String authorizeResult = result.toString();
		if(authorizeResult==null){
			return false;
		}
		JSONObject json = JSONObject.fromObject(authorizeResult);
		logger.info("sessonKey 解析json："+json);
		String access_token = String.valueOf(json.get("access_token"));
		logger.info("sessonKey=:"+access_token);
		vocAccount.setSessionKey(access_token);
		vocAccount.setResult(json.toString());
		if(access_token!=null&&!access_token.equals("null"))
		{
			vocAccount.setIsApprove(true);
			vocAccount.setApproveTime(new Date());
			flag=true;
		}else{
			vocAccount.setIsApprove(false);
			flag=false;
		}
		vocAccount.setApproveCode(code);
		try{
			vocAccount.setUmpChannel(UmpChannel.findUmpChannel(1L));
			vocAccount.merge();
		}catch(Exception e){
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}
	
	public 
	void populateEditForm(Model uiModel, VocAccount vocAccount) {
		uiModel.addAttribute("vocAccount", vocAccount);
		UmpChannel umpChannel = vocAccount.getUmpChannel();
		uiModel.addAttribute("umpchannels",
				UmpChannel.findAllChannelsbuyProductId(Global.VOC));
		uiModel.addAttribute("vocShops", VocShop
				.findAllVocShopsByCompanyAndChannel(umpChannel.getId(),
						vocAccount.getCompanyId()));
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
	/**
	 * 获取公司
	 * @param request
	 * @return
	 */
	private UmpCompany getUser(HttpServletRequest request){
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
		List<VocAccount> list = VocAccount.findVocAccountbyCompanyAndVocShopIds(company, ids);
		if(null != list && list.size() > 0){
			for (VocAccount vocAccount : list) {
				String str = "id:" + vocAccount.getId() + ", pId:" + 0
						+ ",name:\"" + vocAccount.getAccount() + "\""
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

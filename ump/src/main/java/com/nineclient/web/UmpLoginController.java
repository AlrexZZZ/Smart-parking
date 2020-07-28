package com.nineclient.web;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.hibernate.Session;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.nineclient.constant.UmpCompanyServiceStatus;
import com.nineclient.constant.UmpCompanyStatus;
import com.nineclient.model.PubOperator;
import com.nineclient.model.PubRole;
import com.nineclient.model.UmpAuthority;
import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpCompanyService;
import com.nineclient.model.UmpLog;
import com.nineclient.model.UmpOperator;
import com.nineclient.model.UmpParentBusinessType;
import com.nineclient.model.UmpProduct;
import com.nineclient.model.UmpRole;
import com.nineclient.model.UmpVersion;
import com.nineclient.utils.Global;
import com.nineclient.utils.MD5Util;
import com.nineclient.utils.RandomValidateCode;

@Controller
@SessionAttributes("loginOperator")
public class UmpLoginController {
	private final Logger log = LoggerFactory.getLogger(UmpLoginController.class);

	@RequestMapping(value = "/umps", method = RequestMethod.GET, produces = "text/html")
	public String login(HttpServletRequest request, HttpServletResponse response){
		log.info("==进入此方法===");
		return "redirect:/ump";
		// response.sendRedirect(request.getContextPath() + "/login.jspx");
		// return null;
	}

	@RequestMapping(value="/umps", method=RequestMethod.POST, produces = "text/html")
	public String login(Model uiModel,String account,String password,String companyCode,
			HttpServletRequest request){
		log.info("==登陆方法===");
		String msg = "";
		String code = request.getParameter("code").toUpperCase();
		// 判断用户名和密码是否填写
		if (null == companyCode || "".equals(companyCode)) {
			msg += "请填写公司ID。";
		}
		if (null == account || "".equals(account)) {
			msg += "请填写用户名。";
		}
		if (null == password || "".equals(password)) {
			msg += "请填写密码。";
		}
		if(!request.getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY).equals(code)){
			msg += "验证码错误";
		}
		if (msg.length() > 0) {
			uiModel.addAttribute("msg", msg);
			uiModel.addAttribute("account", account);
			uiModel.addAttribute("companyCode", companyCode);
			return "login";
		}
		List<UmpCompanyService> companys = UmpCompanyService.findUmpCompanyServiceByCode(companyCode);//公司
		for (UmpCompanyService umpCompanyService : companys) {//公司服务
			Set<UmpChannel> channels = umpCompanyService.getChannels();
			for (UmpChannel umpChannel : channels) {//渠道
				if("全渠道智慧互动".equals(umpChannel.getChannelName())){
					request.getSession().setAttribute("isUcc", "true");//判断接入是否是UCC
					break;
				}
			}
		}
		String md5Password = MD5Util.MD5(password);//给密码加密
		// 如果公司id是9，账号是admin就直接登陆ump后台。
		if (Global.ADMIN_USER.equals(account)
				&& Global.ADMIN_PASSWORD.equals(md5Password)
				&& Global.ADMIN_CODE.equals(companyCode)) {
			UmpOperator user = new UmpOperator();
			user.setAccount(account);
			user.setOperatorName("nineClient");
			/**
			 * 下面就是循环该账号所拥有的功能菜单
			 */
			UmpAuthority umpauthority = new UmpAuthority();
			List<UmpAuthority> umpauthoritys = UmpAuthority.findUmpAuthoritysProductIsUmp(1L);
			// 记录登陆日志
			UmpLog ulog = new UmpLog();
			try {
				ulog.setLoginIp(InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			ulog.setLoginTime(new Date());
			ulog.setIsStatus(true);// 状态。
			ulog.setAccount(account);
			ulog.persist();

			user.setCompany(UmpCompany.findUmpCompanyByCode(Global.ADMIN_CODE));
			request.getSession().setAttribute("umpauthorityss", umpauthoritys);
			request.getSession().setAttribute("umpLogId", ulog.getId());
			request.getSession().setAttribute("umpOperator", user);
			request.getSession().setAttribute("operatorName", user.getOperatorName());
			request.getSession().setAttribute("flagOperator", 0);
			/**
			 * 如果公司id不是9,就跳转到产品中心。
			 */
		} else if ((null != companyCode || !"".equals(companyCode))&& !companyCode.equals("9")) {
			PubOperator user = PubOperator.findPubOperatorByAccount(account,companyCode);
			if (user == null) {
				msg += "用户不存在，请检查输入是否有误！";
				uiModel.addAttribute("msg", msg);
				uiModel.addAttribute("account", account);
				uiModel.addAttribute("companyCode", companyCode);
				return "login";
			}
			if(!user.getPassword().equals(md5Password)){
				msg += "密码错误，请检查输入是否有误！";
				uiModel.addAttribute("msg", msg);
				uiModel.addAttribute("account", account);
				uiModel.addAttribute("companyCode", companyCode);
				return "login";
			}
			if (user.getCompany().getStatus().ordinal() == UmpCompanyStatus.待审核
					.ordinal()) {
				msg += "该账号正在审核中，请稍后登录";
				uiModel.addAttribute("msg", msg);
				uiModel.addAttribute("account", account);
				uiModel.addAttribute("companyCode", companyCode);
				return "login";
			}
			if (user.getCompany().getStatus().ordinal() == UmpCompanyStatus.审核不通过
					.ordinal()) {
				msg += "账号审核不通过，无法登陆";
				uiModel.addAttribute("msg", msg);
				uiModel.addAttribute("account", account);
				uiModel.addAttribute("companyCode", companyCode);
				return "login";
			}
			if (user.getActive() == false) {
				msg += "该用户已被禁用";
				uiModel.addAttribute("msg", msg);
				uiModel.addAttribute("account", account);
				uiModel.addAttribute("companyCode", companyCode);
				return "login";
			}
			// 判断是否到期
			long start = System.currentTimeMillis();
			List<UmpCompanyService> umpService = UmpCompanyService.findUmpCompanyServiceByVersionId(companyCode);
			if (umpService != null && umpService.size() > 0) {
				List<UmpAuthority> wccauthoritys = null;
				//List<UmpAuthority> uccauthoritys = null;
				List<UmpAuthority> vocauthoritys = null;
				PubRole role =null;
				UmpVersion wccVersion = null;
				UmpVersion vocVersion = null;
				String productWcc = "";
				String productVoc = "";
				int wccServiceDate = 0;
				int vocServiceDate = 0;
				int count = 0;
				Map<String, String> auditMenu = new HashMap<String, String>();//保存按钮
				for (UmpCompanyService umpSer : umpService) {
					UmpProduct umpProduct = UmpProduct.findUmpProduct(umpSer.getProductId());
					if(umpProduct.getId() == Global.WCC){
						System.out.println(umpSer.getServiceEndTime().getTime() - (new Date().getTime())+"-=-time=-=");
						if((umpSer.getServiceEndTime().getTime() - (new Date().getTime())) > 0){
							wccVersion = UmpVersion.findUmpVersion(umpSer.getVersionId());
							if (user.getPubRole() == null || "".equals(user.getPubRole())){
								/**
								 * 下面循环该admin所拥有的功能菜单
								 */
								wccauthoritys = UmpAuthority.findUmpAuthoritysVersionIdIsUmp(wccVersion);
							}else{
								//该账户如果没有被分配公众号就不查询wcc菜单
								if(user.getPlatformUsers().size() > 0){
									//角色
									role = PubRole.findPubRole(user.getPubRole().getId());
									productWcc = "WCC";
								}
							}
							count++;
							wccServiceDate = (int)((umpSer.getServiceEndTime().getTime()-(new Date().getTime()))/(1000*60*60*24) + 1 );
						}
					}else if(umpProduct.getId()== Global.VOC){
						if((umpSer.getServiceEndTime().getTime() - (new Date().getTime())) > 0){
							vocVersion = UmpVersion.findUmpVersion(umpSer.getVersionId());
							if (user.getPubRole() == null || "".equals(user.getPubRole())){
								/**
								 * 下面就是循环admin所拥有的功能菜单
								 */
								vocauthoritys = UmpAuthority.findUmpAuthoritysVersionIdIsUmp(vocVersion);
							}else{
								role = PubRole.findPubRole(user.getPubRole().getId());
								productVoc = "VOC";
							}
							count++;
							vocServiceDate = (int)((umpSer.getServiceEndTime().getTime()-(new Date().getTime()))/(1000*60*60*24) + 1);
						}
					}else if(umpProduct.getId() == Global.UCC){
						
					}
				}
				if(count == 0){
					msg += "你使用的版本都已过期";
					uiModel.addAttribute("msg", msg);
					uiModel.addAttribute("account", account);
					uiModel.addAttribute("companyCode", companyCode);
					return "login";
				}
				if(productWcc.equals("WCC")){
					/**
					 * 下面就是循环该账号所拥有的WCC功能菜单
					 */
					List<UmpAuthority> authoritys = user.getPubRole().getAuthoritys();//权限菜单
					List<UmpAuthority> authowcc = new ArrayList<UmpAuthority>();
					List<UmpAuthority> listMenuWcc = UmpAuthority.findUmpAuthoritysVersionIdIsUmp(wccVersion);//版本菜单
					for (UmpAuthority umpAuthority : listMenuWcc) {
						for (UmpAuthority umpStr : authoritys) {
							if(umpStr.getId() == umpAuthority.getId()){
								authowcc.add(umpStr);
								if(umpStr.getDisplayName().equals("审核")){
									auditMenu.put(umpStr.getParentId(), "审核");
								}
								break;
							}
						}
					}
					wccauthoritys = new ArrayList<UmpAuthority>();
					wccauthoritys.addAll(authowcc);
					
				}
				if(productVoc.equals("VOC")){
					/**
					 * 下面就是循环该账号所拥有的VOC功能菜单
					 */
					List<UmpAuthority> authoritysVoc = user.getPubRole().getAuthoritys();
					List<UmpAuthority> authovoc = new ArrayList<UmpAuthority>();
					List<UmpAuthority> listMenuVoc = UmpAuthority.findUmpAuthoritysVersionIdIsUmp(vocVersion);
					for (UmpAuthority umpAuthorityvoc : listMenuVoc) {
						for (UmpAuthority umpStrvoc : authoritysVoc) {
							if(umpStrvoc.getId() == umpAuthorityvoc.getId()){
								authovoc.add(umpStrvoc);
								if(umpStrvoc.getDisplayName().equals("审核")){
									auditMenu.put(umpStrvoc.getParentId(), "审核");
								}
								break;
							}
						}
					}
					vocauthoritys = new ArrayList<UmpAuthority>();
					vocauthoritys.addAll(authovoc);
				}

				// 记录登陆日志
				UmpLog ulog = new UmpLog();
				try {
					ulog.setLoginIp(InetAddress.getLocalHost().getHostAddress());
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				ulog.setLoginTime(new Date());
				ulog.setIsStatus(true);// 状态。
				ulog.setAccount(account);
				ulog.persist();

				// 存放公司code方便区分公司
				try {
					user.setCompany(UmpCompany.findUmpCompanyByCode(companyCode));
				} catch (Exception e) {
					e.printStackTrace();
				}

				int flags = 0;
				if(null != user.getPubRole()){
					if(wccauthoritys != null && vocauthoritys != null && wccauthoritys.size() > 0 && vocauthoritys.size() > 0){
						//wcc和voc产品都有，但是该账户wcc没有被分配公众号，voc没有被分配电商平台
						if(user.getPlatformUsers().size() <= 0 && user.getChannels().size() <= 0){
							msg += "你没有被分配任何技能";
							uiModel.addAttribute("msg", msg);
							uiModel.addAttribute("account", account);
							uiModel.addAttribute("companyCode", companyCode);
							return "login";
						}else if(user.getPlatformUsers().size() <= 0 && user.getChannels().size() > 0){
							//wcc和voc产品都有，但是该账户没有被分配公众号，加载voc菜单
							flags = 4;
						}else if(user.getPlatformUsers().size() > 0 && user.getChannels().size() <= 0){
							//wcc和voc产品都有，但是该账户voc没有被分配电商平台，加载wcc才到那
							flags = 6;
						}else{
							flags = 1;//默认加载wcc菜单
						}
						log.info("==wccvoc==="+flags);
					}else if(wccauthoritys !=null && wccauthoritys.size() > 0){
						/**
						 * 如果该公司只有wcc产品，但是该账户没有被分配任何公众号，直接到登陆页面提示。
						 * flags = 2 否则就登陆进去，默认加载wcc菜单
						 */
						if(user.getPlatformUsers().size() <= 0){
							msg += "你没有被分配任何技能";
							uiModel.addAttribute("msg", msg);
							uiModel.addAttribute("account", account);
							uiModel.addAttribute("companyCode", companyCode);
							return "login";
						}else{
							flags = 2;//加载wcc菜单
						}
						log.info("==wcc==="+flags);
						/**
						 * 如果只有voc产品默认加载voc菜单
						 */
					}else if(vocauthoritys!=null && vocauthoritys.size() > 0){
						if(user.getChannels().size() <= 0){
							msg += "你没有被分配电商平台";
							uiModel.addAttribute("msg", msg);
							uiModel.addAttribute("account", account);
							uiModel.addAttribute("companyCode", companyCode);
							return "login";
						}else{
							flags = 3;//加载voc菜单
						}
						log.info("==voc==="+flags);
					}else{
						log.info("==else==="+flags);
						msg += "你没有被分配任何技能";
						uiModel.addAttribute("msg", msg);
						uiModel.addAttribute("account", account);
						uiModel.addAttribute("companyCode", companyCode);
						return "login";
					}
				}else{
					if(vocauthoritys!=null && wccauthoritys == null && vocauthoritys.size() > 0){
						flags = 5;
					}
				}
				
				// 将用户存储在session中
				request.getSession().setAttribute("umpLogId", ulog.getId());//记录登陆日志id
				request.getSession().setAttribute("pubOperator", user);//保存登陆用户
				request.getSession().setAttribute("puboperatorName", user.getOperatorName());
				request.getSession().setAttribute("flagOperator", 1);//表示登陆非ump后台
				request.getSession().setAttribute("flags", flags);//保存登陆者的所拥有产品几种状态
				request.getSession().setAttribute("wccServiceDate", wccServiceDate);//保存wcc产品试用还有多少天
				request.getSession().setAttribute("vocServiceDate", vocServiceDate);//保存voc试用还有多少条
				request.getSession().setAttribute("wccauthoritys",wccauthoritys);//保存wcc菜单
				request.getSession().setAttribute("vocauthoritys",vocauthoritys);//保存voc菜单
				request.getSession().setAttribute("url", Global.Url);
				request.getSession().setAttribute("auditMenu",auditMenu);
				
				long end = System.currentTimeMillis();
				long currentTime = end - start;
				log.info("==耗时==="+currentTime);
				
			} else {
				msg += "你没有选择任何服务！";
				uiModel.addAttribute("msg", msg);
				uiModel.addAttribute("account", account);
				uiModel.addAttribute("companyCode", companyCode);
				return "login";
			}
			
		} else {
			// 查詢是否有該公司信息
			UmpOperator user = UmpOperator.findUmpOperatorByAccount(account,companyCode);
			if (user == null) {
				msg += "信息不合法，请检查输入是否有误！";
				uiModel.addAttribute("msg", msg);
				uiModel.addAttribute("account", account);
				uiModel.addAttribute("companyCode", companyCode);
				return "login";
			}
			if(!user.getPassword().equals(md5Password)){
				msg += "信息不合法，请检查输入是否有误！";
				uiModel.addAttribute("msg", msg);
				uiModel.addAttribute("account", account);
				uiModel.addAttribute("companyCode", companyCode);
				return "login";
			}

			if (user.getActive() == false) {
				msg += "该用户已被禁用";
				uiModel.addAttribute("msg", msg);
				uiModel.addAttribute("account", account);
				uiModel.addAttribute("companyCode", companyCode);
				return "login";
			}

			// 角色
			UmpRole role = UmpRole.findUmpRole(user.getUrole().getId());
			/**
			 * 下面就是循环该账号所拥有的功能菜单
			 */
			List<UmpAuthority> umpauthoritys = role.getUmpAuthoritys();
			request.getSession().setAttribute("umpauthorityss", umpauthoritys);
			// 记录登陆日志
			UmpLog ulog = new UmpLog();
			try {
				ulog.setLoginIp(InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			ulog.setLoginTime(new Date());
			ulog.setIsStatus(true);// 状态。
			ulog.setAccount(account);
			ulog.persist();
			
			UmpCompany umpCompany = null;
			try {
				umpCompany = UmpCompany.findUmpCompanyByCode(companyCode);
				user.setCompany(umpCompany);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 将用户存储在session中
			request.getSession().setAttribute("umpLogId", ulog.getId());
			request.getSession().setAttribute("umpOperator", user);
			request.getSession().setAttribute("flagOperator", 0);
			request.getSession().setAttribute("operatorName", user.getOperatorName());
		}
		return "loginIndex";
	}

	@RequestMapping(value = "/loginOut", method = RequestMethod.GET, produces = "text/html")
	public String loginOut(Model uiModel, HttpSession session,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		long umpLogId = (Long) session.getAttribute("umpLogId");
		// 退出需记录退出时间
		UmpLog ulog = UmpLog.findUmpLogByLogId(umpLogId);
		ulog.setLoginOutTime(new Date());
		ulog.merge();
		// 销毁session
		session.invalidate();
		
		/*session.removeAttribute("umpLogId");
		session.removeAttribute("umpOperator");
		session.removeAttribute("flagOperator");
		session.removeAttribute("operatorName");
		session.removeAttribute("umpLogId");
		session.removeAttribute("pubOperator");
		session.removeAttribute("puboperatorName");
		session.removeAttribute("flags");
		session.removeAttribute("umpLogId");
		session.removeAttribute("wccServiceDate");
		session.removeAttribute("vocServiceDate");
		session.removeAttribute("wccauthoritys");
		session.removeAttribute("vocauthoritys");
		session.removeAttribute("displayId");
		session.removeAttribute(RandomValidateCode.RANDOMCODEKEY);
		session.removeAttribute("umpauthorityss");
		session.removeAttribute("auditMenu");
		session.removeAttribute("url");
		session.removeAttribute("audMenu");*/
		//uiModel.asMap().remove("umpOperator");
		//uiModel.asMap().remove("pubOperator");
		// return "redirect:/ump";
		response.sendRedirect(request.getContextPath() + "/login.jspx");
		return null;
	}

	/**
	 * 
	 * 激活id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "companyActivation", produces = "text/html")
	public String CompanyActivation(@RequestParam(value = "id") String id) {
		if (id != null && id != "") {
			List<UmpCompany> list =  UmpCompany.findUmpCompanyByIsVisable();
			for(UmpCompany company : list){
				if(id.equals(MD5Util.MD5(company.getId().toString()))){
					company.setIsVisable(true);
					company.merge();
					return "umpcompanys/activation";
				}
			}
		}
		return "login";
	}

	@RequestMapping(value = "register", produces = "text/html")
	public String register(Model uiModel) {

		uiModel.addAttribute("UmpParentBusinessTypeList",
				UmpParentBusinessType.findAllUmpParentBusinessTypes());
		uiModel.addAttribute("umpProductList", UmpProduct.findAllUmpProducts());
		uiModel.addAttribute("umpChannelList", UmpChannel.findAllUmpChannels());
		uiModel.addAttribute("umpVersionList", UmpVersion.findAllUmpVersions());
		uiModel.addAttribute("umpCompany", new UmpCompany());
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("umpcompanystatuses",
				Arrays.asList(UmpCompanyStatus.values()));
		return "umpcompanys/register";
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"umpCompany_createtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute(
				"umpCompany_servicestarttime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute(
				"umpCompany_serviceendtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * 公司注册
	 */
	@RequestMapping(value = "/companyRegister", produces = "text/html")
	public String companyRegister(
			@Valid UmpCompany umpCompany,
			BindingResult bindingResult,
			Model uiModel,
			HttpServletRequest request,
			@RequestParam(value = "parentBusinessType") String parentBusinessType) {
		uiModel.asMap().clear();
		umpCompany.setCreateTime(new Date());
		umpCompany.setStatus(UmpCompanyStatus.待审核);
		umpCompany.setIsDeleted(true);
		umpCompany.setIsVisable(false);
		String operatorName = request.getParameter("operatorName");
		String password = request.getParameter("password");
		String products = request.getParameter("products");
		String channels_ = "";
		if(products.equals("3")){
			channels_ = request.getParameter("channels_");
		}else{
			channels_ = request.getParameter("SelIds");
		}
		String version_ = request.getParameter("version_");
		
		PubOperator pubOper = new PubOperator();
		pubOper.setOperatorName(operatorName);
		pubOper.setAccount(operatorName);
		pubOper.setPassword(MD5Util.MD5(password));
		pubOper.setAutoAllocate(false);
		pubOper.setActive(true);
		pubOper.setCreateTime(new Date());
		pubOper.setIsDeleted(true);
		pubOper.setEmail(umpCompany.getEmail());
		pubOper.setCompany(umpCompany);
		pubOper.setMobile(umpCompany.getMobilePhone());
		Set<PubOperator> pubOperSet = new HashSet<PubOperator>();
		pubOperSet.add(pubOper);
		umpCompany.setPubOpers(pubOperSet);

		UmpParentBusinessType bType = UmpParentBusinessType
				.findUmpParentBusinessType(Long.parseLong(parentBusinessType));
		Set<UmpParentBusinessType> parentBusinessTypeSet = new HashSet<UmpParentBusinessType>();
		parentBusinessTypeSet.add(bType);
		umpCompany.setParentBusinessType(parentBusinessTypeSet);

		// 添加服务
		UmpCompanyService ser = new UmpCompanyService();
		ser.setCompanyCode(umpCompany.getCompanyCode());
		Date date = new Date();
		ser.setServiceStartTime(date);
		ser.setMaxAccount(5);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 7); // 将当前日期加7天
		ser.setServiceEndTime(c.getTime());
		ser.setCompanyServiceStatus(UmpCompanyServiceStatus.试用);
		Set<UmpChannel> cset = new HashSet<UmpChannel>();
		if(products.equals("3")){
			UmpChannel uc = UmpChannel.findUmpChannel(Long.parseLong(channels_));
			cset.add(uc);
		}else{
			String[] channelsStr =  channels_.split(",");
			for(String channel : channelsStr){
				UmpChannel uc = UmpChannel.findUmpChannel(Long.parseLong(channel));
				cset.add(uc);
			}
		}
		ser.setChannels(cset);
		ser.setProductId(Long.parseLong(products));
		ser.setVersionId(Long.parseLong(version_));
		ser.setCreateTime(new Date());
		ser.persist(umpCompany);

		activationMail(umpCompany.getId() + "", umpCompany.getEmail(),"0",request);

		return "umpcompanys/registerSuccess";

	}

	
	/**
	 * 天猫入口公司注册
	 */
	@RequestMapping(value = "/tmallRegister", produces = "text/html")
	public String tmallRegister(
			@Valid UmpCompany umpCompany,
			BindingResult bindingResult,
			Model uiModel,
			HttpServletRequest request,
			@RequestParam(value = "parentBusinessType") String parentBusinessType) {
		uiModel.asMap().clear();
		umpCompany.setCreateTime(new Date());
		umpCompany.setStatus(UmpCompanyStatus.审核通过);
		umpCompany.setIsDeleted(true);
		umpCompany.setIsVisable(false);
		String operatorName = request.getParameter("operatorName");
		String password = request.getParameter("password");
		String products = request.getParameter("products");
		String channels_ =request.getParameter("channels_");
		String version_ = request.getParameter("version_");
		if(StringUtils.isEmpty(operatorName)||StringUtils.isEmpty(password)||StringUtils.isEmpty( products)||StringUtils.isEmpty(channels_)||StringUtils.isEmpty(version_)){
			uiModel.addAttribute("msg", 1);
			return "umpcompanys/tmallRegisterFail";
		}
		PubOperator pubOper = new PubOperator();
		pubOper.setOperatorName(operatorName);
		pubOper.setAccount(operatorName);
		pubOper.setPassword(MD5Util.MD5(password));
		pubOper.setAutoAllocate(false);
		pubOper.setActive(true);
		pubOper.setCreateTime(new Date());
		pubOper.setIsDeleted(true);
		pubOper.setEmail(umpCompany.getEmail());
		pubOper.setCompany(umpCompany);
		pubOper.setMobile(umpCompany.getMobilePhone());
		Set<PubOperator> pubOperSet = new HashSet<PubOperator>();
		pubOperSet.add(pubOper);
		umpCompany.setPubOpers(pubOperSet);

		UmpParentBusinessType bType = UmpParentBusinessType
				.findUmpParentBusinessType(Long.parseLong(parentBusinessType));
		Set<UmpParentBusinessType> parentBusinessTypeSet = new HashSet<UmpParentBusinessType>();
		parentBusinessTypeSet.add(bType);
		umpCompany.setParentBusinessType(parentBusinessTypeSet);

		// 添加服务
		UmpCompanyService ser = new UmpCompanyService();
		ser.setCompanyCode(umpCompany.getCompanyCode());
		Date date = new Date();
		ser.setServiceStartTime(date);
		ser.setMaxAccount(5);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 15); // 将当前日期加15天
		ser.setServiceEndTime(c.getTime());
		ser.setCompanyServiceStatus(UmpCompanyServiceStatus.试用);
		Set<UmpChannel> cset = new HashSet<UmpChannel>();
		UmpChannel uc = UmpChannel.findUmpChannel(Long.parseLong(channels_));
		cset.add(uc);
		ser.setChannels(cset);
		ser.setProductId(Long.parseLong(products));
		ser.setVersionId(Long.parseLong(version_));
		ser.setCreateTime(new Date());
		ser.persist(umpCompany);
		return "login";

	}
	/**
	 * 
	 * 发送激活邮件
	 * 
	 * @param id
	 * @param email
	 * @param type
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "activationMail", produces = "text/html")
	public String activationMail(@RequestParam(value = "id") String id,
			@RequestParam(value = "email") String email,@RequestParam(value = "type",required=false) String type,
			HttpServletRequest request) {
		UmpCompany company =  UmpCompany.findUmpCompany(Long.parseLong(id));
		PubOperator operator = PubOperator.findAdminOperatorByCompanyId(company);
		if(type!=null&&!type.equals("")){
				if(type.equals("1")){
					company.setEmail(email);
					company.merge();
					operator.setEmail(email);
					operator.merge();
				}
				
		}
		String activation = Global.Url + "/ump/companyActivation?id=" + MD5Util.MD5(id);
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy年MM月dd日");
		String nowdate = dateformat.format(new Date());
		String name = company.getName();
		if(name==null||name.equals("")){
			name = "admin";
		}
		String message = "Hi，"+name+"：\r\n        你好!\r\n\r\n        感谢你注册久科集中管理平台。\r\n\r\n        "
				+ "请点击以下链接激活帐号："
				+ activation
				+ "\r\n\r\n如果以上链接无法点击，请将上面的地址复制到你的浏览器(如IE)的地址栏进入平台。\r\n\r\n                                                                                                "
				+ "久科服务团队\r\n\r\n                                                                                              "
				+ nowdate;
		UmpOperator.sendMessage("激活您的久科集中管理平台账号", email, message);
		request.setAttribute("email", email);
		request.setAttribute("id", id);
		return "umpcompanys/registerSuccess";
	}

	/**
	 * 
	 * 公司code查重
	 * 
	 * @param companyCode
	 * @return
	 */
	@RequestMapping(value = "checkCompanyCode", produces = "text/html")
	@ResponseBody
	public String checkCompanyCode(
			@RequestParam(value = "companyCode") String companyCode) {
		if (companyCode != null && companyCode != "") {
			if (UmpCompany.findUmpCompanysByCompanyCode(companyCode).size() > 0) {
				return "0";
			} else {
				return "1";
			}
		}
		return "";
	}

	/**
	 * 
	 * 邮箱查重
	 * 
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "checkEmail", produces = "text/html")
	@ResponseBody
	public String checkEmail(@RequestParam(value = "email") String email) {
		if (email != null && email != "") {
			if (UmpCompany.findUmpCompanysByEmail(email).size() > 0) {
				return "0";
			} else {
				return "1";
			}
		}
		return "";
	}

	@RequestMapping(value = "forgetPass", produces = "text/html")
	public String forgetPass() {
		return "umpoperators/forget";
	}

	@RequestMapping(value = "resetSuccess", produces = "text/html")
	public String resetSuccess(@RequestParam("email") String email,
			HttpServletRequest request) {
		request.setAttribute("email", email);
		return "umpoperators/success";
	}

	/**
	 * 校对公司id与email是否匹配
	 * 
	 * @param cid
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "checkValue", produces = "text/html")
	@ResponseBody
	public String checkValue(@RequestParam("cid") String cid,
			@RequestParam("email") String email) {
		List<PubOperator> operators = PubOperator.findAllPubOperators();
		for (PubOperator operator : operators) {
			if (operator.getEmail().equals(email)
					&& operator.getCompany().getCompanyCode().equals(cid)&&operator.getPubRole()==null) {
				String newpass = randomPassword(6);
				SimpleDateFormat dateformat = new SimpleDateFormat(
						"yyyy年MM月dd日");
				String nowdate = dateformat.format(new Date());
				String name = "admin";
				if(operator!=null){
					name = operator.getAccount();
				}
				String message = "Hi，"+name+"：\r\n\r\n        请查收新密码："
						+ newpass
						+ "。请使用新密码重新登录，谢谢。\r\n\r\n                                                                        "
						+ "久科服务团队\r\n\r\n                                                                      "
						+ nowdate;
				try {
					if (UmpOperator.sendMessage("密码重置", email, message).equals(
							"0")) {
						return "1";
					}
				} catch (Exception e) {
					// TODO: handle exception
					return "1";
				}
				operator.setPassword(MD5Util.MD5(newpass));
				operator.merge();
				return "0";
			}
		}
		return "-1";
	}

	/**
	 * 生成随机数
	 * 
	 * @return
	 */
	public String randomPassword(int length) {

		String val = "";
		Random random = new Random();

		// 参数length，表示生成几位随机数
		for (int i = 0; i < length; i++) {

			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	/**
	 * 验证码
	 */
	@RequestMapping(value = "verCode", produces = "text/html")
	public void verCode(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "time", required = false) String time) {
		response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
		response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);
		RandomValidateCode randomValidateCode = new RandomValidateCode();
		try {
			randomValidateCode.getRandcode(request, response);// 输出图片方法
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "checkCode", produces = "text/html")
	@ResponseBody
	public String checkCode(HttpServletRequest request,@RequestParam(value = "code") String code){
		if(null != code || !"".equals(code)){
			if(!request.getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY).equals(code.toUpperCase())){
				return "1"; 
			}else{
				return "2";
			}
		}
		return "0";
	}
	@RequestMapping(value = "checkTitle", produces = "text/html")
	@ResponseBody
	public String checkTitle(@RequestParam(value = "email") String email) {
		if (email != null && email != "") {
			if (UmpCompany.findUmpCompanysByEmail(email).size() > 0) {
				return "0";
			} else {
				return "1";
			}
		}
		return "";
	}
	
	
}

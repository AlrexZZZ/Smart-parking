package com.nineclient.web;

import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpConfig;
import com.nineclient.model.UmpOperator;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.Global;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

@RequestMapping("/umpconfigs")
@Controller
@RooWebScaffold(path = "umpconfigs", formBackingObject = UmpConfig.class)
public class UmpConfigController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid UmpConfig umpConfig,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, umpConfig);
			return "umpconfigs/create";
		}
		uiModel.asMap().clear();
		umpConfig.persist();
		return "redirect:/umpconfigs/"
				+ encodeUrlPathSegment(umpConfig.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new UmpConfig());
		return "umpconfigs/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		uiModel.addAttribute("umpconfig", UmpConfig.findUmpConfig(id));
		uiModel.addAttribute("itemId", id);
		return "umpconfigs/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			uiModel.addAttribute("umpconfigs", UmpConfig.findUmpConfigEntries(
					firstResult, sizeNo, sortFieldName, sortOrder));
			float nrOfPages = (float) UmpConfig.countUmpConfigs() / sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("umpconfigs",
					UmpConfig.findAllUmpConfigs(sortFieldName, sortOrder));
		}
		return "umpconfigs/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid UmpConfig umpConfig,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, umpConfig);
			return "umpconfigs/update";
		}
		uiModel.asMap().clear();
		umpConfig.merge();
		return "redirect:/umpconfigs/"
				+ encodeUrlPathSegment(umpConfig.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, UmpConfig.findUmpConfig(id));
		return "umpconfigs/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		UmpConfig umpConfig = UmpConfig.findUmpConfig(id);
		umpConfig.remove();
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/umpconfigs";
	}

	void populateEditForm(Model uiModel, UmpConfig umpConfig) {
		uiModel.addAttribute("umpConfig", umpConfig);
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
	 * 配置邮箱
	 * 
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "ec", produces = "text/html")
	public String emailConfig(
			HttpServletRequest request,
			@RequestParam(value = "displayId", required = false) Long displayId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size) {
		List<UmpConfig> configs = UmpConfig.findConfigsByKeyType("emailConfig");
		if (configs.size() > 0) {
			for (UmpConfig config : configs) {
				if ("HOST".equals(config.getName())) {
					request.setAttribute("host", config.getKeyValue());
				}
				if ("PORT".equals(config.getName())) {
					request.setAttribute("port", config.getKeyValue());
				}
				if ("USERNAME".equals(config.getName())) {
					request.setAttribute("emailname", config.getKeyValue());
				}
				if ("PASSWORD".equals(config.getName())) {
					request.setAttribute("emailPass", config.getKeyValue());
				}
				if ("SENDER".equals(config.getName())) {
					request.setAttribute("sender", config.getKeyValue());
				}
			}
		}
		if (displayId == null) {
			displayId = CommonUtils.getCurrentDisPlayId(request);
		}
		request.getSession().setAttribute("displayId", displayId);
		return "umpconfigs/emailConfig";
	}

	/**
	 * 保存邮箱发送配置
	 * 
	 * @param host
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "emailSava", produces = "text/html")
	@ResponseBody
	public String emailSava(@RequestParam("host") String host,
			@RequestParam("port") Integer port,
			@RequestParam("username") String username,
			@RequestParam("emailPass") String emailPass,
			@RequestParam("sender") String sender, HttpServletRequest request) {
		UmpOperator operator = (UmpOperator) request.getSession().getAttribute(
				"loginOperator");
		String operatorName = "";
		if (operator != null) {
			operatorName = operator.getOperatorName();
		} else {
			operatorName = "admin";
		}
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy年MM月dd日");
		String nowdate = dateformat.format(new Date());
		String returnStr = UmpConfig
				.sendMessage(
						"恭喜您的邮箱配置成功",
						username,
						"Hi，"
								+ operatorName
								+ "：\r\n"
								+ "        恭喜您！您在久科集中管理平台的邮箱配置成功！现在平台上的所有发送邮件功能可以正常使用了！"
								+ "（这是一封系统邮件，请勿回复！）"
								+ "\r\n                                                                        久科服务团队"
								+ "\r\n                                                                      "
								+ nowdate, host, port, username, emailPass,
						"help@9client.com");

		if (returnStr.equals("1")) {// 查询是否已有
			List<UmpConfig> configs = UmpConfig
					.findConfigsByKeyType("emailConfig");
			if (configs.size() > 0) {
				for (UmpConfig config : configs) {

					if ("HOST".equals(config.getName())) {
						if (host != null && !host.equals("")) {
							config.setKeyValue(host);
							config.merge();
						}
					}
					if ("PORT".equals(config.getName())) {
						if (port != null && !port.equals("")) {
							config.setKeyValue(port.toString());
							config.merge();
						}
					}
					if ("USERNAME".equals(config.getName())) {
						if (username != null && !username.equals("")) {
							config.setKeyValue(username);
							config.merge();
						}
					}
					if ("PASSWORD".equals(config.getName())) {
						if (emailPass != null && !emailPass.equals("")) {
							config.setKeyValue(emailPass);
							config.merge();
						}
					}
					if ("SENDER".equals(config.getName())) {
						if (sender != null && !sender.equals("")) {
							config.setKeyValue(sender);
							config.merge();
						}
					}
				}
			} else {
				if (host != null && host.equals("") != true) {
					UmpConfig configHost = new UmpConfig();
					configHost.setName("HOST");
					configHost.setKeyValue(host);
					configHost.setKeyType("emailConfig");
					configHost.setRemarks("邮箱服务器host");
					configHost.persist();
				}
				if (port != null && port.equals("") != true) {
					UmpConfig configHost = new UmpConfig();
					configHost.setName("PORT");
					configHost.setKeyValue(port.toString());
					configHost.setKeyType("emailConfig");
					configHost.setRemarks("邮箱服务器port");
					configHost.persist();
				}
				if (username != null && username.equals("") != true) {
					UmpConfig configUser = new UmpConfig();
					configUser.setName("USERNAME");
					configUser.setKeyValue(username);
					configUser.setKeyType("emailConfig");
					configUser.setRemarks("邮箱账号");
					configUser.persist();
				}
				if (emailPass != null && emailPass.equals("") != true) {
					UmpConfig configPass = new UmpConfig();
					configPass.setName("PASSWORD");
					configPass.setKeyValue(emailPass);
					configPass.setKeyType("emailConfig");
					configPass.setRemarks("邮箱密码");
					configPass.persist();
				}
				if (sender != null && sender.equals("") != true) {
					UmpConfig configPass = new UmpConfig();
					configPass.setName("SENDER");
					configPass.setKeyValue(sender);
					configPass.setKeyType("emailConfig");
					configPass.setRemarks("邮箱显示发送人");
					configPass.persist();
				}
			}
			return "1";
		} else {
			return "0";
		}
	}

	/**
	 * 配置邮箱
	 * 
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "wccEmailConfig", produces = "text/html")
	public String wccEmailConfig(HttpServletRequest request,
			@RequestParam(value = "displayId", required = false) Long displayId) {
		PubOperator operator = CommonUtils.getCurrentPubOperator(request);
		List<UmpConfig> configs = UmpConfig
				.findConfigsByKeyType("wccEmailConfig_"
						+ operator.getCompany().getId());
		if (configs.size() > 0) {
			for (UmpConfig config : configs) {
				if ("HOST".equals(config.getName())) {
					request.setAttribute("host", config.getKeyValue());
				}
				if ("PORT".equals(config.getName())) {
					request.setAttribute("port", config.getKeyValue());
				}
				if ("USERNAME".equals(config.getName())) {
					request.setAttribute("emailname", config.getKeyValue());
				}
				if ("PASSWORD".equals(config.getName())) {
					request.setAttribute("emailPass", config.getKeyValue());
				}
				if ("SENDER".equals(config.getName())) {
					request.setAttribute("sender", config.getKeyValue());
				}
			}
		}

		if (displayId == null) {
			displayId = CommonUtils.getCurrentDisPlayId(request);
		}
		request.getSession().setAttribute("displayId", displayId);
		return "umpconfigs/wccEmailConfig";
	}

	/**
	 * 保存邮箱发送配置
	 * 
	 * @param host
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "wccEmailSava", produces = "text/html")
	@ResponseBody
	public String wccEmailSava(@RequestParam("host") String host,
			@RequestParam("port") Integer port,
			@RequestParam("username") String username,
			@RequestParam("emailPass") String emailPass,
			@RequestParam("sender") String sender, HttpServletRequest request) {
		PubOperator operator = CommonUtils.getCurrentPubOperator(request);

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy年MM月dd日");
		String nowdate = dateformat.format(new Date());
		String returnStr = UmpConfig
				.sendMessage(
						"恭喜您的邮箱配置成功",
						username,
						"Hi，"
								+ operator.getOperatorName()
								+ "：\r\n"
								+ "        恭喜您！您在久科集中管理平台的邮箱配置成功！现在平台上的所有发送邮件功能可以正常使用了！"
								+ "（这是一封系统邮件，请勿回复！）"
								+ "\r\n                                                                        久科服务团队"
								+ "\r\n                                                                      "
								+ nowdate, host, port, username, emailPass,
								username);

		if (returnStr.equals("1")) {
			// 查询是否已有
			List<UmpConfig> configs = UmpConfig
					.findConfigsByKeyType("wccEmailConfig_"
							+ operator.getCompany().getId());
			if (configs.size() > 0) {
				for (UmpConfig config : configs) {
					if ("HOST".equals(config.getName())) {
						if (host != null && !host.equals("")) {
							config.setKeyValue(host);
							config.merge();
						}
					}
					if ("PORT".equals(config.getName())) {
						if (port != null && !port.equals("")) {
							config.setKeyValue(port.toString());
							config.merge();
						}
					}
					if ("USERNAME".equals(config.getName())) {
						if (username != null && !username.equals("")) {
							config.setKeyValue(username);
							config.merge();
						}
					}
					if ("PASSWORD".equals(config.getName())) {
						if (emailPass != null && !emailPass.equals("")) {
							config.setKeyValue(emailPass);
							config.merge();
						}
					}
					if ("SENDER".equals(config.getName())) {
						if (sender != null && !sender.equals("")) {
							config.setKeyValue(sender);
							config.merge();
						}
					}
				}
			} else {
				if (host != null && host.equals("") != true) {
					UmpConfig configHost = new UmpConfig();
					configHost.setName("HOST");
					configHost.setKeyValue(host);
					configHost.setKeyType("wccEmailConfig_"
							+ operator.getCompany().getId());
					configHost.setRemarks("邮箱服务器host");
					configHost.persist();
				}
				if (port != null && port.equals("") != true) {
					UmpConfig configHost = new UmpConfig();
					configHost.setName("PORT");
					configHost.setKeyValue(port.toString());
					configHost.setKeyType("wccEmailConfig_"
							+ operator.getCompany().getId());
					configHost.setRemarks("邮箱服务器port");
					configHost.persist();
				}
				if (username != null && username.equals("") != true) {
					UmpConfig configUser = new UmpConfig();
					configUser.setName("USERNAME");
					configUser.setKeyValue(username);
					configUser.setKeyType("wccEmailConfig_"
							+ operator.getCompany().getId());
					configUser.setRemarks("邮箱账号");
					configUser.persist();
				}
				if (emailPass != null && emailPass.equals("") != true) {
					UmpConfig configPass = new UmpConfig();
					configPass.setName("PASSWORD");
					configPass.setKeyValue(emailPass);
					configPass.setKeyType("wccEmailConfig_"
							+ operator.getCompany().getId());
					configPass.setRemarks("邮箱密码");
					configPass.persist();
				}
				if (sender != null && sender.equals("") != true) {
					UmpConfig configPass = new UmpConfig();
					configPass.setName("SENDER");
					configPass.setKeyValue(sender);
					configPass.setKeyType("wccEmailConfig_"
							+ operator.getCompany().getId());
					configPass.setRemarks("邮箱显示发送人");
					configPass.persist();
				}
			}
			return "1";
		} else {
			return "0";
		}
	}

	@RequestMapping(value = "proposeContent", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String proposeContent(
			Model uiModel,
			HttpServletRequest request,
			@RequestParam(value = "flagOperator", required = false) Integer flagOperator,
			@RequestParam(value = "content", required = false) String content) {
		if (flagOperator == 0) {
			UmpOperator.sendMessage("建议反馈", Global.EMAIL, content.toString()+"\r\n 本意见反馈来自本公司的"+CommonUtils.getCurrentOperator(request).getAccount());
		}
		if (flagOperator == 1) {
			UmpCompany company =UmpCompany.findUmpCompany(CommonUtils.getCurrentCompanyId(request));
			UmpOperator.sendMessage("建议反馈", Global.EMAIL, content.toString()+"\r\n 本意见反馈来自"+company.getName()+"的"+CommonUtils.getCurrentPubOperator(request).getAccount());
		}
		return "0";
	}
}

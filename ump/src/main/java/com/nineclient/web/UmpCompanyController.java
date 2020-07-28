package com.nineclient.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONArray;

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
import com.nineclient.constant.UmpCompanyServiceStatus;
import com.nineclient.constant.UmpCompanyStatus;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpBusinessType;
import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpCompanyService;
import com.nineclient.model.UmpConfig;
import com.nineclient.model.UmpOperator;
import com.nineclient.model.UmpParentBusinessType;
import com.nineclient.model.UmpProduct;
import com.nineclient.model.UmpVersion;
import com.nineclient.model.VocEmail;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageBean;
import com.nineclient.utils.PageModel;

@RequestMapping("/umpcompanys")
@Controller
@RooWebScaffold(path = "umpcompanys", formBackingObject = UmpCompany.class)
public class UmpCompanyController {
	/**
	 * 查询页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "listPage", produces = "text/html")
	public String listPage(Model uiModel,
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "displayId", required = false) Long displayId) {
		// 加载产品
		uiModel.addAttribute("parentbusinesstypes",
				UmpParentBusinessType.findAllUmpParentBusinessTypes());
		// 加载产品
		uiModel.addAttribute("products", UmpProduct.findAllUmpProducts(null));
		if (displayId == null) {
			displayId = CommonUtils.getCurrentDisPlayId(httpServletRequest);
		}
		httpServletRequest.getSession().setAttribute("displayId", displayId);
		return "umpcompanys/list";
	}

	/**
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String companyList(
			@RequestParam(value = "parentBusinessId", required = false) Long parentBusinessId,
			@RequestParam(value = "checkStatus", required = false) Long checkStatus,
			@RequestParam(value = "productId", required = false) Long productId,
			@RequestParam(value = "channelId", required = false) Long channelId,
			@RequestParam(value = "versionId", required = false) Long versionId,
			@RequestParam(value = "companyCode", required = false) String companyCode,
			PageBean pageBean,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size) {
		PageModel<UmpCompany> pageModel = new PageModel<UmpCompany>();
		try {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			int pageNo = page == null ? 0 : page.intValue();
			List<Map> list = UmpCompany.findUmpCompanyList(firstResult, sizeNo,
					parentBusinessId, checkStatus, productId, channelId,
					versionId,companyCode, pageBean.getStartTime(), pageBean.getEndTime());
			int count = UmpCompany.countUmpCompanys(firstResult, sizeNo,
					parentBusinessId, checkStatus, productId, channelId,
					versionId,companyCode ,pageBean.getStartTime(), pageBean.getEndTime());
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(sizeNo);
			pageModel.setDataJson(JSONArray.fromObject(list).toString());
			pageModel.setTotalCount(count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageModel.toJson();
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid UmpCompany umpCompany,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, umpCompany);
			return "umpcompanys/create";
		}
		uiModel.asMap().clear();
		umpCompany.persist();
		return "redirect:/umpcompanys/"
				+ encodeUrlPathSegment(umpCompany.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {

		uiModel.addAttribute("umpBusinessTypeList",
				UmpBusinessType.findAllUmpBusinessTypes());
		uiModel.addAttribute("umpProductList", UmpProduct.findAllUmpProducts());
		uiModel.addAttribute("umpChannelList", UmpChannel.findAllUmpChannels());
		uiModel.addAttribute("umpVersionList", UmpVersion.findAllUmpVersions());
		populateEditForm(uiModel, new UmpCompany());
		return "umpcompanys/register";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("umpcompany", UmpCompany.findUmpCompany(id));
		uiModel.addAttribute("itemId", id);
		return "umpcompanys/show";
	}

	/**
	 * 下拉复选
	 * 
	 * @param uiModel
	 */
	void initSelect(Model uiModel) {
		// 审核状态

		List<UmpCompanyStatus> status = new ArrayList<UmpCompanyStatus>();
		status.add(UmpCompanyStatus.待审核);
		status.add(UmpCompanyStatus.审核不通过);

		// 服务状态
		List<UmpCompanyServiceStatus> serviceStatus = new ArrayList<UmpCompanyServiceStatus>();
		serviceStatus.add(UmpCompanyServiceStatus.试用);
		serviceStatus.add(UmpCompanyServiceStatus.开通);
		serviceStatus.add(UmpCompanyServiceStatus.到期);
		serviceStatus.add(UmpCompanyServiceStatus.停止);
		serviceStatus.add(UmpCompanyServiceStatus.预到期);

		// 操作状态
		List<UmpCompanyServiceStatus> operatorServiceStatus = new ArrayList<UmpCompanyServiceStatus>();
		operatorServiceStatus.add(UmpCompanyServiceStatus.开通);
		operatorServiceStatus.add(UmpCompanyServiceStatus.关闭);

		uiModel.addAttribute("channelList", UmpChannel.findAllUmpChannels());
		uiModel.addAttribute("versionList", UmpVersion.findAllUmpVersions());
		uiModel.addAttribute("status", status);
		uiModel.addAttribute("serviceStatus", serviceStatus);
		uiModel.addAttribute("operatorServiceStatus", operatorServiceStatus);
		uiModel.addAttribute("products", UmpProduct.findAllUmpProducts());
		uiModel.addAttribute("parentBusinessTypes",
				UmpParentBusinessType.findAllUmpParentBusinessTypes());
	}

	/**
	 * 公司注册列表
	 * 
	 * @param page
	 * @param size
	 * @param sortFieldName
	 * @param sortOrder
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "companyRegisterList", produces = "text/html")
	public String companyRegisterList(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {
		populateEditForm(uiModel, new UmpCompany());

		uiModel.addAttribute("umpcompanys",
				UmpCompany.findAllRegisterUmpCompanys(sortFieldName, sortOrder));
		initSelect(uiModel);

		uiModel.addAttribute("perPage", size);
		addDateTimeFormatPatterns(uiModel);
		return "umpcompanys/companyRegisterList";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/queryCompanyRegister", produces = "text/html")
	public String queryCompanyRegister(Model uiModel,
			HttpServletRequest request,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit) {
		List<UmpCompany> list = UmpCompany.findAllRegisterUmpCompanys("", "");
		uiModel.addAttribute("umpcompanys", list);
		return "umpcompanys/result";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid UmpCompany umpCompany,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, umpCompany);
			return "umpcompanys/update";
		}
		uiModel.asMap().clear();
		umpCompany.merge();
		return "redirect:/umpcompanys/"
				+ encodeUrlPathSegment(umpCompany.getId().toString(),
						httpServletRequest);
	}

	@ResponseBody
	@RequestMapping(value = "getUmpChanelByProduct")
	public void getUmpChanelByProduct(@RequestParam(value = "id") Long id,
			@RequestParam(value = "type", required = false) Long type,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer();// 拼成Json格式字符串{key:value,key1:value1}
			if (type == 1) {
				sb.append("<select id='channels' name='channels' style='width: 100%;'>");
			} else {
				sb.append("<select id='channels' name='channels'>");
			}
			for (UmpChannel chanel : UmpChannel.findUmpChannelByProduct(id)) {
				sb.append("<option value='" + chanel.getId() + "'>"
						+ chanel.getChannelName() + "</option>");
			}
			sb.append("</select>");
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
			@RequestParam(value = "type", required = false) Long type,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer();// 拼成Json格式字符串{key:value,key1:value1}
			if (type == 1) {
				sb.append("<select id='version' name='version' style='width: 100%;'>");
			} else {
				sb.append("<select id='version' name='version'>");
			}
			for (UmpVersion version : UmpVersion.findUmpVersionByProduct(id)) {
				sb.append("<option value='" + version.getId() + "'>"
						+ version.getVersionName() + "</option>");
			}
			sb.append("</select>");
			out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

	}

	@ResponseBody
	@RequestMapping(value = "changeStatus", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String changeStatus(Model uiModel,
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "status") int status,
			@RequestParam(value = "remark",required=false) String remark,
			HttpServletRequest request, HttpServletResponse response) {
		UmpCompany company = UmpCompany.findUmpCompany(id);
		UmpCompanyService service = null;
		Set<PubOperator> oSet = null;
		List<VocEmail> listEmail=null;
		PubOperator pubOp = null;
		try {
			List<UmpCompanyService> serviceList = UmpCompanyService
					.findUmpCompanyServiceByCodeAndServiceStatus(
							company.getCompanyCode(),
							UmpCompanyServiceStatus.试用);
			service = serviceList.get(0);
			if(service==null){
				throw new RuntimeException("服务为空");
			}
		
			if (UmpCompanyStatus.审核通过.ordinal() == status) {
				 oSet = company.getPubOpers();
				for (PubOperator operator : oSet) {
					operator.setActive(true);
					operator.merge();
				}
				company.setStatus(UmpCompanyStatus.审核通过);
				// 审核--服务第二天开始试用
				service.setCompanyServiceStatus(UmpCompanyServiceStatus.试用);
				Date date = new Date();
				Calendar c = Calendar.getInstance();
				Date dayDate = DateUtil.getDateTimeYYYYMMDD(date,
						DateUtil.YEAR_MONTH_DAY_FORMATER);
				c.setTime(DateUtil.getNextDAYDateYYYYMMDD(dayDate));
				service.setServiceStartTime(DateUtil
						.getNextDAYDateYYYYMMDD(dayDate));
				c.add(Calendar.DAY_OF_YEAR, 14);
				service.setServiceEndTime(c.getTime());
				//获取邮件信息添加到邮件
				try{
					Long pId = service.getProductId();
					if(pId!=null&&pId==Global.VOC&&oSet!=null){
						listEmail=new ArrayList<VocEmail>();
						for (PubOperator pubOperator : oSet) {
							VocEmail vocEmail = new VocEmail();
							vocEmail.setEmail(pubOperator.getEmail());
							vocEmail.setCreateTime(new Date());
							vocEmail.setIsDeleted(false);
							vocEmail.setIsVisable(true);
							vocEmail.setOperatorId(pubOperator.getId());
							vocEmail.setRemark("总账户邮箱");
							vocEmail.setName(pubOperator.getOperatorName());
							vocEmail.setUmpCompanyId(company.getId());
							listEmail.add(vocEmail);
						}
					}
					VocEmail.meger(listEmail);
				}catch(Exception e){
					e.printStackTrace();
				}
			} else {
				company.setStatus(UmpCompanyStatus.审核不通过);
			}
			if(!StringUtils.isEmpty(remark)){
				company.setRemark(remark);
			}
			service.merge(company);
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy年MM月dd日");
			pubOp = PubOperator.findAdminOperatorByCompanyId(company);
			if (UmpCompanyStatus.审核通过.ordinal() == status){
				UmpOperator.sendMessage("您的久科全渠道营销服务平台注册信息已审核通过", company.getEmail(), "Hi，"
						+ pubOp.getOperatorName()
						+ "：\r\n"
						+ "        恭喜你！你在久科全渠道营销服务平台的注册信息已审核通过，请尽情登录体验吧。登录地址："+Global.Url+"/ump"
						+"\n"
						+ "如果以上链接无法点击，请将上面的地址复制到你的浏览器(如IE)的地址栏进入平台。"
						+ "\r\n                                                                       "
						+ "                                                                        久科服务团队"
						+ "\r\n                    	                                               "
						+ "                                                                          "
						+ dateformat.format(new Date()));
			}else if(UmpCompanyStatus.审核不通过.ordinal()==status){
				UmpOperator.sendMessage("您的久科全渠道营销服务平台注册信息审核不通过", company.getEmail(), "Hi，"
						+ pubOp.getOperatorName()
						+ "：\r\n"
						+ "        很遗憾！你在久科全渠道营销服务平台的注册信息审核不通过，原因是："+remark+"。请重新注册。注册地址："+Global.Url+"/ump/register"
						+"\n"
						+ "如果以上链接无法点击，请将上面的地址复制到你的浏览器(如IE)的地址栏进入平台。"
						+ "\r\n                                                                       "
						+ "                                                                        久科服务团队"
						+ "\r\n                    	                                               "
						+ "                                                                          "
						+ dateformat.format(new Date()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return NotifyMsg.EXCEPTION;
		}
		return NotifyMsg.SUCCESS;
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, UmpCompany.findUmpCompany(id));
		return "umpcompanys/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		UmpCompany umpCompany = UmpCompany.findUmpCompany(id);
		umpCompany.remove();
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/umpcompanys";
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

	void populateEditForm(Model uiModel, UmpCompany umpCompany) {
		uiModel.addAttribute("umpCompany", umpCompany);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("umpcompanystatuses",
				Arrays.asList(UmpCompanyStatus.values()));
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
			uiModel.addAttribute("umpcompanys", UmpCompany
					.findUmpCompanyEntries(firstResult, sizeNo, sortFieldName,
							sortOrder));
			float nrOfPages = (float) UmpCompany.countUmpCompanys() / sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("umpcompanys",
					UmpCompany.findAllUmpCompanys(sortFieldName, sortOrder));
		}
		addDateTimeFormatPatterns(uiModel);
		return "umpcompanys/list";
	}

	/**
	 * 
	 * 切换产品时 提供tree数据
	 * 
	 * @param uiModel
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "treeJson", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String treeJson(@RequestParam(value = "id") Long id,
			HttpServletRequest request, HttpServletResponse response) {
		StringBuffer strs = new StringBuffer();
		int num = 0;
		List<UmpChannel> list = UmpChannel.findUmpChannelByProduct(id);
		if (list.size() > 0) {
			for (UmpChannel channel : list) {
				String str = "id:" + channel.getId() + ", pId:" + 0
						+ ",name:\"" + channel.getChannelName() + "\""
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

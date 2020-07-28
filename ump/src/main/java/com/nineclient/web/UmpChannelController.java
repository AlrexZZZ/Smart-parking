package com.nineclient.web;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpCompanyService;
import com.nineclient.model.UmpProduct;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Global;
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

@RequestMapping("/umpchannels")
@Controller
@RooWebScaffold(path = "umpchannels", formBackingObject = UmpChannel.class)
public class UmpChannelController {

	/**
	 * add by hunter
	 * 
	 * @param umpChannel
	 * @param bindingResult
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */

	@RequestMapping(value = "createChannel", method = RequestMethod.POST)
	public void createChannel(@Valid UmpChannel umpChannel,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest, HttpServletResponse response) {
		Long productId = umpChannel.getProduct().getId();
		PrintWriter out = null;
		String json = "";
		try {
			out = response.getWriter();
			if (umpChannel.getIsVisable() == null) {
				umpChannel.setIsVisable(false);
			} else {
				umpChannel.setIsVisable(true);
			}

			if (umpChannel.getChannelName() == ""
					|| umpChannel.getChannelName() == null) {
				json = "{\"msg\":\"渠道名称不能为空\",success:\"fasle\"}";
			} else {
				List<UmpChannel> chanels = UmpChannel
						.findAllUmpChannelsByName(umpChannel.getChannelName());
				// response.setContentType("text/xml;charset=utf-8");

				if (chanels != null && chanels.size() > 0) {
					json = "{\"msg\":\"渠道名称已存在\",success:\"fasle\"}";// 拼成Json格式字符串{key:value,key1:value1}
				} else {
					UmpProduct product = UmpProduct.findUmpProduct(productId);
					umpChannel.setProduct(product);
					uiModel.asMap().clear();
					umpChannel.persist();
					json = "{\"msg\":\"添加成功\",success:\"true\"}";// 拼成Json格式字符串{key:value,key1:value1}
				}
			}
			out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
			json = "{\"msg\":\"添加异常\",success:\"fasle\"}";
			out.println(json);
		} finally {
			out.close();
		}
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid UmpChannel umpChannel,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, umpChannel);
			return "umpchannels/create";
		}
		uiModel.asMap().clear();
		umpChannel.persist();
		return "redirect:/umpchannels/"
				+ encodeUrlPathSegment(umpChannel.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new UmpChannel());
		return "umpchannels/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("umpchannel", UmpChannel.findUmpChannel(id));
		uiModel.addAttribute("itemId", id);
		return "umpchannels/show";
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
			uiModel.addAttribute("umpchannels", UmpChannel
					.findUmpChannelEntries(firstResult, sizeNo, sortFieldName,
							sortOrder));
			float nrOfPages = (float) UmpChannel.countUmpChannels() / sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("umpchannels",
					UmpChannel.findAllUmpChannels(sortFieldName, sortOrder));
		}
		addDateTimeFormatPatterns(uiModel);
		return "umpchannels/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid UmpChannel umpChannel,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, umpChannel);
			return "umpchannels/update";
		}
		uiModel.asMap().clear();
		umpChannel.merge();
		return "redirect:/umpchannels/"
				+ encodeUrlPathSegment(umpChannel.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, UmpChannel.findUmpChannel(id));
		return "umpchannels/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		UmpChannel umpChannel = UmpChannel.findUmpChannel(id);
		umpChannel.remove();
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/umpchannels";
	}
	/**
	 * 加载voc 产品渠道
	 * @param companyId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="loadChannelsSelect",produces="text/html;charset=utf-8")
	public String loadChannelsSelect(HttpServletRequest request){
		//查询voc渠道 要关联公司
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		List<UmpProduct> product = UmpProduct.findUmpProductById(Global.VOC);
		//TODO 公司关联渠道
		List<UmpChannel> channels = UmpChannel.findAllChannelsbuyProductNameAndCompanyService(company, product.get(0));
		String json = "[";
		boolean flag=true;
		for (UmpChannel umpChannel : channels) {
			if(flag){
				flag=false;
				json+="{id:"+umpChannel.getId()+",name:\'"+umpChannel.getChannelName()+"\'}";
			}else{
				json+=",{id:"+umpChannel.getId()+",name:\'"+umpChannel.getChannelName()+"\'}";
			}
		}
		json+="]";
		return json;
	}
	/**
	 * 根据产品加载渠道
	 * @param productId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="loadChannelsByProductId",produces="text/html;charset=utf-8")
	public String loadChannelsByProductId(@RequestParam(value="productId",required=false)Long productId,HttpServletRequest request){
		List<UmpChannel> channels = UmpChannel.findAllChannelsbuyProductId(productId);
		String json = "[";
		boolean flag=true;
		for (UmpChannel umpChannel : channels) {
			if(flag){
				flag=false;
				json+="{id:"+umpChannel.getId()+",name:\'"+umpChannel.getChannelName()+"\'}";
			}else{
				json+=",{id:"+umpChannel.getId()+",name:\'"+umpChannel.getChannelName()+"\'}";
			}
		}
		json+="]";
		return json;
	}
	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"umpChannel_createtime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, UmpChannel umpChannel) {
		uiModel.addAttribute("umpChannel", umpChannel);
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
	
	@RequestMapping(value = "tree", produces = "text/html;charset=utf-8")
	@ResponseBody
	public  String tree(Model uiModel, HttpServletRequest request,
			HttpServletResponse response){
		StringBuffer strs = new StringBuffer();
		int num = 0;
		List<UmpChannel> list = null;
		PubOperator pub = CommonUtils.getCurrentPubOperator(request);
		if(pub.getPubRole() == null){
			List<UmpCompanyService> umpcsList = UmpCompanyService.findUmpCompanyServiceByCode(pub.getCompany().getCompanyCode());
			if(null != umpcsList && umpcsList.size() > 0){
				for (UmpCompanyService umpcs : umpcsList) {
					if(umpcs.getProductId() == 2){//voc产品
						list = CommonUtils.set2List(umpcs.getChannels());
					}
				}
			}
		}else{
			list = CommonUtils.set2List(PubOperator.findPubOperator(pub.getId()).getChannels());
		}
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
		return strs.toString();
	}
}

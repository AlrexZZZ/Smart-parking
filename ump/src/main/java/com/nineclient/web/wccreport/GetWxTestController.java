package com.nineclient.web.wccreport;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.wccreport.ReportContentChannel;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.HttpClientUtil;

@RequestMapping("/wxtests")
@Controller
@RooWebScaffold(path = "wxtests", formBackingObject = ReportContentChannel.class)
public class GetWxTestController {
	@RequestMapping
	public String reportPage(HttpServletRequest request, Model uiModel) {
		List<WccPlatformUser> platformUsers = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
		if (platformUsers.size() > 0) {
			uiModel.addAttribute("platformUsers", platformUsers);
		}
		return "wxdatas";
	}
	
	@RequestMapping(value = "getWxDate", produces = "text/html")
	@ResponseBody
	public String getWxDate(@RequestParam(value = "url") String url,
			@RequestParam(value = "platformUser") WccPlatformUser platform,
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime) {
		WxMpService wxMpService = new WxMpServiceImpl();
		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		config.setAppId(platform.getAppId());
		config.setSecret(platform.getAppSecret());
		wxMpService.setWxMpConfigStorage(config);
		String accessToken = "";
		try {
			accessToken = wxMpService.getAccessToken();
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String json = "";
		if (startTime != null && endTime != null) {
			json = "{\"begin_date\": \"" + startTime + "\",\"end_date\": \""
					+ endTime + "\"}";
		}
		String returnList = "";
		try {
			returnList = HttpClientUtil.doJsonPost(url + accessToken, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
}

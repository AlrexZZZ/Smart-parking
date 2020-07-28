package com.nineclient.web.wccreport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nineclient.model.PubOperator;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.wccreport.WccMassPicText;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@RequestMapping("/reportMassPicText")
@Controller
@RooWebScaffold(path = "reportMassPicText", formBackingObject = WccMassMessageReportController.class)
public class WccMassMessageReportController {
	@RequestMapping(value = "reportPage", produces = "text/html")
	public String reportPage(HttpServletRequest request, Model uiModel) {
//		// 初始化数据
//		List<WccPlatformUser> platformUsers = WccPlatformUser
//				.findAllWccPlatformUsers(CommonUtils
//						.getCurrentPubOperator(request));
//		if (platformUsers.size() > 0) {
//			uiModel.addAttribute("platformUsers", platformUsers);
//		}
		PubOperator currentoper=CommonUtils.getCurrentPubOperator(request);
        List<WccPlatformUser> platformUsers=WccPlatformUser.getplatformuserListByOrg(currentoper);
		uiModel.addAttribute("platformUsers", platformUsers);
		return "reportMassPicText/report";
	}
	
	@RequestMapping(value = "/massPicTextList", produces = "text/html")
	public String massPicTextList(Model uiModel, HttpServletRequest request,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "platformId", required = false) Long platformId,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime)
			throws ServletException, IOException {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("platformId", platformId);
		parmMap.put("startTime", startTime);
		parmMap.put("endTime", endTime);
		WccMassPicText massMessage = new WccMassPicText();
		QueryModel<WccMassPicText> qm = new QueryModel<WccMassPicText>(massMessage,start, limit);
		qm.getInputMap().putAll(parmMap);
		PageModel<WccMassPicText> pm = WccMassPicText.getMassPicText(qm);
		pm.setPageSize(limit);
		pm.setStartIndex(start);
		List<WccMassPicText> masss = pm.getDataList();
		int i = 0;
		for (WccMassPicText wccMassPicText : masss) {
			wccMassPicText.setMassPicTexts(WccMassPicText.findWccMassPicTextByMassId(wccMassPicText.getId()));
			uiModel.addAttribute("mass"+i, WccMassPicText.toJsonArray(masss.get(i).getMassPicTexts()));
			i++;
		}
		uiModel.addAttribute("reportMassPicText", masss);
		uiModel.addAttribute("maxPages", pm.getTotalPage());
		uiModel.addAttribute("page", pm.getPageNo());
		uiModel.addAttribute("limit", pm.getPageSize());

		return "reportMassPicText/result";
	}
}

package com.nineclient.web.vocreport;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/vocreports")
@Controller
@RooWebScaffold(path = "vocreports", formBackingObject =VocReport.class)
public class VocReport {
	@RequestMapping(value="reportPage",produces="text/html")
	public String reportPage(){
		return "vocreports/list";
	}
}

package com.nineclient.tailong.wcc.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import javax.validation.Valid;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import me.chanjar.weixin.common.exception.WxErrorException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nineclient.cbd.wcc.constant.CacluatePage;
import com.nineclient.cbd.wcc.dto.WccCultureLifeDTO;
import com.nineclient.cbd.wcc.dto.WccLifeHelperDTO;
import com.nineclient.cbd.wcc.model.WccAppartment;
import com.nineclient.cbd.wcc.model.WccLifeHelper;
import com.nineclient.cbd.wcc.util.MyMail;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.model.WccStore;
import com.nineclient.tailong.wcc.dto.WccSaQrCodeDTO;
import com.nineclient.tailong.wcc.model.WccSaQrCode;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.CreateQrcode;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;

@RequestMapping("/serviceAdvisor")
@Controller
public class WccServiceAdvisorController {

	/**
	 * 粉丝记录查询
	 * 
	 * @param uiModel
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ParseException
	 */

	@ResponseBody
	@RequestMapping(value = "loadCulture", produces = "application/json")
	public void loadCulture(String dtGridPager, HttpServletRequest request,
			Model model, HttpServletResponse response)
			throws UnsupportedEncodingException, ParseException {

		Pager pager = null;
		Map parm = new HashMap<String, String>();
		String productName = "";
		String productType = "";
		String platFormId = "";
		String beginTime = "";
		String endTime = "";

		// 查找当用户的公司
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platformUser = WccPlatformUser
				.findAllWccPlatformUsers(user);
		UmpCompany company = user.getCompany();

		try {
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			parm = pager.getParameters();
			productName = (String) parm.get("productName");
			productType = (String) parm.get("productType");
			platFormId = (String) parm.get("platFormId");
			beginTime = (String) parm.get("beginTime");
			endTime = (String) parm.get("endTime");
			String s = (String) parm.get("restart");
			if (s.equals("y")) {
				pager.setStartRecord(0);
				pager.setNowPage(1);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		QueryModel<WccSaQrCode> qm = new QueryModel<WccSaQrCode>();
		if (platFormId != null && !"".equals(platFormId)) {
			qm.putParams("platFormId", platFormId);
		} else {
			String platform = "";
			for (WccPlatformUser use : platformUser) {
				platform += use.getId() + ",";
			}
			qm.putParams("platFormId", platform);
		}

		qm.putParams("productType", productType);
		qm.putParams("productName", productName);
		qm.putParams("beginTime", beginTime);
		qm.putParams("endTime", endTime);
		qm.setStart(pager.getStartRecord());
		qm.setLimit(pager.getPageSize());
		PageModel<WccSaQrCode> p = WccSaQrCode.getRecord(qm);

		CacluatePage.totalPage(p.getPageSize(), p.getTotalCount());
		List<WccSaQrCode> lit = p.getDataList();
        WccStore wst = null;
		List<WccSaQrCodeDTO> list = new ArrayList<WccSaQrCodeDTO>();
		if (null != lit && lit.size() > 0) {
			for (WccSaQrCode s : lit) {
				String plIds = "";
				String names = "";
				if (null != s.getPlatformUsers()
						&& s.getPlatformUsers().size() > 0) {
					Set<WccPlatformUser> ss = s.getPlatformUsers();
					Iterator<WccPlatformUser> it = ss.iterator();

					while (it.hasNext()) {
						WccPlatformUser u = it.next();
						if (null != u.getId() && !"".equals(u.getId())) {
							plIds += u.getId() + ",";
						}
						if (null != u.getAccount()
								&& !"".equals(u.getAccount())) {
							names += u.getAccount() + ",";
						}

					}
				}
				if (plIds != "") {
					plIds = plIds.substring(0, (plIds.length() - 1));
				}
				if (names != "") {
					names = names.substring(0, (names.length() - 1));
				}
				WccPlatformUser plf = new WccPlatformUser();
				plf.setAccount(names);
				plf.setRemark(plIds);
				wst= new WccStore();
				wst.setId(s.getWccStore().getId());
			    wst.setStoreName(s.getWccStore().getStoreName());
				list.add(new WccSaQrCodeDTO(s.getId(), plf, s.getCodeName(), s
						.getRemark(), wst, s.getSceneStr(), s
						.getCodeUrl(), s.getInsertTime(), s.getSaName(), s
						.getSaPhone(), s.getSaMailAddress(), s.getState()));
			}

		}
		List<WccSaQrCodeDTO> dto = new ArrayList<WccSaQrCodeDTO>();
		if (list.size() > pager.getStartRecord() + pager.getPageSize()) {
			for (int i = pager.getStartRecord(); i < pager.getStartRecord()
					+ pager.getPageSize(); i++) {
				dto.add(list.get(i));
			}
		} else {
			for (int i = pager.getStartRecord(); i < list.size(); i++) {
				dto.add(list.get(i));
			}
		}

		pager.setExhibitDatas(dto);
		pager.setPageCount(CacluatePage.totalPage(p.getPageSize(),
				p.getTotalCount()));
		pager.setRecordCount(Long.parseLong(p.getTotalCount() + ""));// 设置总记录数
		pager.setIsSuccess(true);
		try {
			response.setContentType("application/json");// 设置返回的数据为json对象
//			JsonValueProcessor jsonProcessor = new DateJsonValueProcessor();
//			JsonConfig jsonConfig = new JsonConfig();
//			 jsonConfig.setIgnoreDefaultExcludes(false);
//			jsonConfig.registerJsonValueProcessor(java.util.Date.class,jsonProcessor);
//			 jsonConfig.setExcludes(new String[]{"wccStore","organization","company"});
			JSONObject pagerJSON = JSONObject.fromObject(pager);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(pagerJSON.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
@RequestMapping(value="sendMail",produces ="text/html")	
public String sendMail(HttpServletRequest request,HttpServletResponse response){
	PubOperator user = CommonUtils.getCurrentPubOperator(request);
	List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
	Iterator<WccPlatformUser> it = platformUser.iterator();
	 Long[] larray = new Long[platformUser.size()];
	WccPlatformUser wp = null;
	int a=0;
	while(it.hasNext()){
		wp = it.next();
		larray[a]=wp.getId();
		a++;
		 
	}
	List<WccSaQrCode> list = WccSaQrCode.getRecordListByState(larray, "1");
	for(WccSaQrCode s :list){
		try{
			String content = s.getSaName()+" 您好，您的专属二维码地址是："+Global.Url+s.getCodeUrl()+" 请点击查看和下载，谢谢！";
			MyMail.sendEmail(content, "专属二维码", s.getSaMailAddress());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	return null;
}
	/**
	 * 粉丝记录页面
	 * 
	 * @param
	 * @return
	 */

	@RequestMapping(value = "showRecord", produces = "text/html")
	public String showRecord(HttpServletRequest request, Model uiModel,
			HttpServletResponse response) {
		PubOperator user = CommonUtils.getCurrentPubOperator(request);

		// List<WccPlatformUser> platformUser =
		// WccPlatformUser.findAllWccPlatformUsers(user);
		// uiModel.addAttribute("platformUser", platformUser);

		List<WccPlatformUser> list = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
		uiModel.addAttribute("plats", list);

		return "tailong/sa_qrCodeList";
	}

	@SuppressWarnings("static-access")
	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(HttpServletRequest request, Model uiModel,
			@RequestParam(value = "dataId", required = false) String dataId,
			HttpServletResponse response) {
		/*
		 * //获取当前系统登录者的信息 PubOperator user =
		 * CommonUtils.getCurrentPubOperator(request); //获取当前登录者所管理的公众平台
		 * List<WccPlatformUser> platformUser =
		 * WccPlatformUser.findAllWccPlatformUsers(user);
		 */
		List<WccStore> store = WccStore.findAllWccStores();
		if (null != dataId && !"".equals(dataId)) {
			WccSaQrCode w = new WccSaQrCode();
			WccSaQrCode wccSaQrCode = w
					.findWccTproducts(Long.parseLong(dataId));
			String pltIds = "";
			if (null != wccSaQrCode.getPlatformUsers()) {

				Set<WccPlatformUser> ss = wccSaQrCode.getPlatformUsers();
				Iterator<WccPlatformUser> it = ss.iterator();
				while (it.hasNext()) {
					WccPlatformUser u = it.next();
					if (null != u.getId() && !"".equals(u.getId())) {
						pltIds += u.getId() + ",";
					}
				}

			}
			if (null != pltIds && !"".equals(pltIds)) {
				uiModel.addAttribute("pltIds",
						pltIds.subSequence(0, (pltIds.length() - 1)));
			} else {
				uiModel.addAttribute("pltIds", "");
			}
			uiModel.addAttribute("wccSaQrCode", wccSaQrCode);
		}
		uiModel.addAttribute("storeList", store);
		return "tailong/sa_qrCodeCreate";
	}

	@RequestMapping(params = "delete", produces = "text/html")
	public String deleteForm(HttpServletRequest request,
			@RequestParam(value = "dataId", required = true) String dataId,
			HttpServletResponse response) {
		if (null != dataId && !"".equals(dataId)) {

			WccSaQrCode w = new WccSaQrCode();
			w.setId(Long.parseLong(dataId));
			w.remove();
		}

		return null;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid WccSaQrCode wccSaQrCode,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest)
			throws UnsupportedEncodingException {
		// PubOperator pub =
		// CommonUtils.getCurrentPubOperator(httpServletRequest);
		if (bindingResult.hasErrors()) {
			return "redirect:/serviceAdvisor?form";
		}
		if (null != wccSaQrCode.getId() && !"".equals(wccSaQrCode.getId())) {

			// 先查询出实体
			WccSaQrCode wccSaQrCodeUpdate = wccSaQrCode
					.findWccTproducts(wccSaQrCode.getId());
			wccSaQrCodeUpdate.setPlatformUsers(wccSaQrCode.getPlatformUsers());
			wccSaQrCodeUpdate.setSaName(wccSaQrCode.getSaName());
			wccSaQrCodeUpdate.setSaPhone(wccSaQrCode.getSaPhone());
			wccSaQrCodeUpdate.setSaMailAddress(wccSaQrCode.getSaMailAddress());
			wccSaQrCodeUpdate.setRemark(wccSaQrCode.getRemark());
			wccSaQrCodeUpdate.setWccStore(wccSaQrCode.getWccStore());
			wccSaQrCodeUpdate.setState(wccSaQrCode.getState());
			uiModel.asMap().clear();
			// 再更新数据
			wccSaQrCodeUpdate.merge();
		} else {
			uiModel.asMap().clear();
			wccSaQrCode.setInsertTime(new Date());
			try {
				CreateQrcode.createOrgetCode(wccSaQrCode.getPlatformUsers(),
						httpServletRequest, wccSaQrCode);
			} catch (WxErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// wccSaQrCode.persist();
		}

		return "redirect:/serviceAdvisor/showRecord";
	}

	@RequestMapping(value = "importExcel", produces = "text/html")
	public String importExcel(HttpServletRequest request,
			HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("importFile");
		Workbook rwb = null;
		try {
			rwb = Workbook.getWorkbook(file.getInputStream());
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
      String errorMsg = "";
		// 查询所有的公共账号 ,放到map中
		List<WccPlatformUser> plist = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
		List<WccStore> storeList = WccStore.findAllWccStores();
		Map<String,WccStore> smap = new HashMap<String, WccStore>();
		for(WccStore ws:storeList){
			smap.put(ws.getStoreName(), ws);
		}
		Map<String, WccPlatformUser> pltMap = new LinkedHashMap<String, WccPlatformUser>();
		if (null != plist && plist.size() > 0) {
			for (WccPlatformUser p : plist) {
				pltMap.put(p.getAccount(), p);
			}
		}
		Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
		int clos = rs.getColumns();// 得到所有的列
		int rows = rs.getRows();// 得到所有的行
		WccSaQrCode w = null;
		for (int i = 2; i < (rows); i++) {
			for (int j = 0; j < clos; j++) {
				// 第一个是列数j，第二个是行数i
				String platForms = rs.getCell(j++, i).getContents();// 关联公众号
																	// -第一列
				String saName = rs.getCell(j++, i).getContents(); // sa name
				String saPhone = rs.getCell(j++, i).getContents(); // sa phone
				String saEmail = rs.getCell(j++, i).getContents(); // sa email
				String wccstore = rs.getCell(j++, i).getContents(); // wccstore

				w = new WccSaQrCode();

				if (null != platForms && !"".equals(platForms.trim())) {
					String tempPlt[] = platForms.replaceAll("，", ",")
							.split(",");
					Set<WccPlatformUser> pltSet = new HashSet<WccPlatformUser>();

					for (String s : tempPlt) {
						if (null != pltMap.get(s)) {
							pltSet.add(pltMap.get(s));
						}
					}
					if (pltSet.size() > 0) {
						// 1.保存公众账号
						w.setPlatformUsers(pltSet);
					} else {
						errorMsg = "您的文件中第"+i+"行，第"+j+"列，公众账号不存在，导入失败！";	
						msgTipUtil(response, errorMsg);
			    		break;
					}
				}
				// 2.保存sa name
				if (null != saName && !"".equals(saName)) {
                w.setSaName(saName);
				}else{
					errorMsg = "您的文件中第"+i+"行，第"+j+"列，SA名称不能为空，导入失败！";
		    		msgTipUtil(response, errorMsg);
		    		break;
				}
				// 3.保存sa phone
				if (null != saPhone && !"".equals(saPhone)) {
                 w.setSaPhone(saPhone);
				}else{
					errorMsg = "您的文件中第"+i+"行，第"+j+"列，SA手机号不能为空，导入失败！";
		    		msgTipUtil(response, errorMsg);
		    		break;
				}
				// 4.保存sa email
				if (null != saEmail && !"".equals(saEmail)) {
                w.setSaMailAddress(saEmail);
				}else{
					errorMsg = "您的文件中第"+i+"行，第"+j+"列，SA邮箱不能为空，导入失败！";
		    		msgTipUtil(response, errorMsg);
		    		break;
				}
				// 5.保存sa wccstore
			    if(null != wccstore && !"".equals(wccstore)){
			    	if(smap.get(wccstore) != null){
			    		w.setWccStore(smap.get(wccstore));
			    	}else{
			    		errorMsg = "您的文件中第"+i+"行，第"+j+"列，门店不存在，导入失败！";
			    		msgTipUtil(response, errorMsg);
			    		break;
			    	}
			    	
	            }
			    w.setState("1");
			    w.setInsertTime(new Date());
						try {
							CreateQrcode.createOrgetCode(w.getPlatformUsers(),
									request, w);
						} catch (WxErrorException e) {
							e.printStackTrace();
						}
			}
		}
		return "qrCodeTicket/qrCode";
	}
public  static void msgTipUtil(HttpServletResponse response,String str){
	 PrintWriter printWriter = null;
	 if(null != str){
	try {
		printWriter = response.getWriter();
		printWriter.write(str);
	} catch (IOException e) {
		e.printStackTrace();
	}finally{
		if(printWriter != null){
			printWriter.close();
		}
	} 
	}
}
	// 导出模板
	@ResponseBody
	@RequestMapping(value = "excelModel")
	public void getExcelModel(HttpServletRequest request, Model model,
			HttpServletResponse response,
			@RequestParam(value = "phoneNum", required = false) String phoneNum) {

		Long a = System.currentTimeMillis();

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + a
				+ ".xls\"");
		OutputStream os = null;
		try {
			os = response.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {

			WritableWorkbook book = Workbook.createWorkbook(os);

			WritableSheet sheet = book.createSheet("sheet_1", 0);
			jxl.write.WritableFont font1 = new jxl.write.WritableFont(
					jxl.write.WritableFont.TIMES, 16,
					jxl.write.WritableFont.BOLD);
			jxl.write.WritableFont font3 = new jxl.write.WritableFont(
					jxl.write.WritableFont.TIMES, 10,
					jxl.write.WritableFont.BOLD);
			jxl.write.WritableCellFormat CBwcfF1 = new jxl.write.WritableCellFormat(
					font1);
			jxl.write.WritableCellFormat CBwcfF2 = new jxl.write.WritableCellFormat();
			jxl.write.WritableCellFormat CBwcfF3 = new jxl.write.WritableCellFormat(
					font3);
			jxl.write.WritableCellFormat CBwcfF4 = new jxl.write.WritableCellFormat();
			CBwcfF1.setAlignment(jxl.write.Alignment.CENTRE);
			CBwcfF2.setAlignment(jxl.write.Alignment.RIGHT);
			CBwcfF3.setAlignment(jxl.write.Alignment.CENTRE);
			CBwcfF3.setBorder(Border.ALL, jxl.format.BorderLineStyle.THIN,
					Colour.BLACK);
			CBwcfF4.setBorder(Border.ALL, jxl.format.BorderLineStyle.THIN,
					Colour.BLACK);

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 10);
			sheet.setColumnView(2, 10);
			// /Label labelhead0 = new Label("当前列", "当前行", " 当前单元格的值", "引用的样式");
			Label labelhead0 = new Label(0, 0, "公众平台", CBwcfF3);
			Label labelhead1 = new Label(1, 0, "SA姓名", CBwcfF3);
			Label labelhead2 = new Label(2, 0, "SA电话", CBwcfF3);
			Label labelhead3 = new Label(3, 0, "SA邮件地址", CBwcfF3);
			Label labelhead4 = new Label(4, 0, "门店名称", CBwcfF3);

			sheet.addCell(labelhead0);
			sheet.addCell(labelhead1);
			sheet.addCell(labelhead2);
			sheet.addCell(labelhead3);
			sheet.addCell(labelhead4);

			Label labe0 = new Label(0, 1, "泰隆银行(系统中存在的公众平台名称)"); // 公众平台
			Label labe1 = new Label(1, 1, "SA姓名-必填"); // 项目名称
			Label labe2 = new Label(2, 1, "SA电话-必填"); // 摘要
			Label labe3 = new Label(3, 1, "SA邮件地址-必填"); // 摘要
			Label labe4 = new Label(4, 1, "系统中存在的门店"); // 摘要

			Label labe5 = new Label(5, 1, "该数据仅供参考，添加时自动删除");// 金额
			sheet.addCell(labe0);
			sheet.addCell(labe1);
			sheet.addCell(labe2);
			sheet.addCell(labe3);
			sheet.addCell(labe4);

			sheet.addCell(labe5);
			try {

				book.setProtected(false);
				book.write();
				os.flush();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				book.close();
				os.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

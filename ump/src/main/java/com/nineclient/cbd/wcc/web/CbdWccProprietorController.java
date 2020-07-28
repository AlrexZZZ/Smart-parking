package com.nineclient.cbd.wcc.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;



















import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Border;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nineclient.cbd.wcc.dto.WccFeesDTO;
import com.nineclient.cbd.wcc.model.WccAppartment;
import com.nineclient.cbd.wcc.model.WccDictionary;
import com.nineclient.cbd.wcc.model.CbdWccProprietor;
import com.nineclient.cbd.wcc.model.WccFees;
import com.nineclient.cbd.wcc.model.WccItemotherinfo;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.model.WccStore;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.ExcelUtil;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;


@RequestMapping("/wccproprietor")
@Controller
@RooWebScaffold(path = "wccproprietor", formBackingObject = CbdWccProprietor.class)
public class CbdWccProprietorController {
	
	private final Logger log = LoggerFactory.getLogger(CbdWccProprietorController.class);
	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("cbdWccProprietor_insertTime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("cbdWccProprietor_updateTime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("cbdWccProprietor_certificationTime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("cbdWccProprietor_insertTime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
	void populateEditForm(Model uiModel, CbdWccProprietor wccproprietor ) {
        uiModel.addAttribute("cbdWccProprietor", wccproprietor);
        addDateTimeFormatPatterns(uiModel);
    }
	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel,HttpServletRequest httpServletRequest) {
        populateEditForm(uiModel, new CbdWccProprietor());
        PubOperator currentOp=CommonUtils.getCurrentPubOperator(httpServletRequest);
    	PubOperator user = CommonUtils.getCurrentPubOperator(httpServletRequest);
		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
		String platform="";
		for(WccPlatformUser use:platformUser){
			platform+=use.getId()+",";
		}
		QueryModel<WccAppartment> qm = new QueryModel<WccAppartment>();
	//	qm.setStart(1);
    //	qm.setLimit(Integer.MAX_VALUE);
		PageModel<WccAppartment> pageModel =WccAppartment.getRecord(qm, null, "2", platform.substring(0,platform.length()-1), null);
        	uiModel.addAttribute("iteminfo",pageModel.getDataList());
        return "wccproprietor/create";
    }
	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel,
    		HttpServletRequest httpServletRequest) {
		  PubOperator currentOp=CommonUtils.getCurrentPubOperator(httpServletRequest);
	    	PubOperator user = CommonUtils.getCurrentPubOperator(httpServletRequest);
			List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
			String platform="";
			for(WccPlatformUser use:platformUser){
				platform+=use.getId()+",";
			}
			QueryModel<WccAppartment> qm = new QueryModel<WccAppartment>();
		//	qm.setStart(1);
	    //	qm.setLimit(Integer.MAX_VALUE);
			PageModel<WccAppartment> pageModel =WccAppartment.getRecord(qm, null, "2", platform.substring(0,platform.length()-1), null);
	        	uiModel.addAttribute("iteminfo",pageModel.getDataList());

            uiModel.addAttribute("cbdWccProprietor", CbdWccProprietor.findCbdWccProprietor(id));
        return "wccproprietor/update";
    }

	@RequestMapping(value="/addProprietor",method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid CbdWccProprietor wccproprietor, BindingResult bindingResult, Model uiModel,
    		@RequestParam(value = "wccappartment", required = true) String wccappartment,
    		HttpServletRequest httpServletRequest) {
		PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
		wccproprietor.setInsertTime(new Date());
		wccproprietor.setIsDelete(false);
		wccproprietor.setCompany(pub.getCompany());
		wccproprietor.setPlatformUsers(pub.getPlatformUsers());
		wccproprietor.setIsEnsured("0");
		wccproprietor.setWccappartment(WccAppartment.findPubOrganization(Long.parseLong(wccappartment)));
		List<CbdWccProprietor> list=CbdWccProprietor.getProprietorsList(wccproprietor.getName(),wccproprietor.getPhone(), wccproprietor.getWccappartment(), wccproprietor.getDoorplate());
		if(list.size()<1){
			wccproprietor.merge();
		}
	
		uiModel.asMap().clear();
        uiModel.addAttribute("page",1);
		uiModel.addAttribute("size",10);
		return "redirect:/wccproprietor/queryproprietor";
    }
	@RequestMapping(value = "del", produces = "text/html")
    public void delForm(HttpServletRequest request,
    		@RequestParam(value = "phone", required = false) String phone,
    		@RequestParam(value = "name", required = false) String name,
    		@RequestParam(value = "itempk", required = false) String itempk,
    		@RequestParam(value = "doorplate", required = false) String doorplate,
    		HttpServletResponse response) throws IOException {
		System.out.println("~~~~~~~~~~~~fsdfsaf");	
			List<CbdWccProprietor> list=CbdWccProprietor.getProprietorsList(name,phone, WccAppartment.findWccAppartmentById(Long.parseLong(itempk)), doorplate);
//			Map<String, Object> map=new HashMap<String, Object>();
//			map.put("data", i);
			int i=list.size();
			//JSONObject	json = JSONObject. fromObject("{'data':'1'}");
			String str="{\"d\" : "+i+"}";
			response.getWriter().write(str);
    }
	
	
	
	@RequestMapping(value="/update",method = RequestMethod.POST, produces = "text/html")
    public String update(CbdWccProprietor wccproprietor, BindingResult bindingResult, Model uiModel, 
    		@RequestParam(value = "wccappartment", required = true) String wccappartment,
    		HttpServletRequest httpServletRequest) {
		wccproprietor.setUpdateTime(new Date());
		/*if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccproprietor);
            return "wccproprietor/update";
        }*/
		PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
		CbdWccProprietor tempProprietor=CbdWccProprietor.findCbdWccProprietor(wccproprietor.getId());
		tempProprietor.setName(wccproprietor.getName());
		tempProprietor.setPhone(wccproprietor.getPhone());
	//	tempProprietor.setWccappartment(wccproprietor.getWccappartment());
		tempProprietor.setDoorplate(wccproprietor.getDoorplate());
		tempProprietor.setCompany(pub.getCompany());
		tempProprietor.setWccappartment(WccAppartment.findPubOrganization(Long.parseLong(wccappartment)));
	//	tempProprietor.setIsEnsured(null);
//		tempProprietor.setCertificationTime(null);
//		tempProprietor.setTempappartment(null);
//		tempProprietor.setName("");
//		tempProprietor.setPhone("");
//		tempProprietor.setDoorplate("");		
        uiModel.asMap().clear();
        tempProprietor.merge();
        return "redirect:/wccproprietor/queryproprietor";
    }
	@RequestMapping(value="/confirmed", produces = "text/html")
    public String confirmed(HttpServletRequest httpServletRequest,
    		@RequestParam(value = "id", required = false) Long id) {
	   CbdWccProprietor wccproprietor=null;
	   wccproprietor=CbdWccProprietor.findCbdWccProprietor(id);
	 if(wccproprietor!=null)
	 {
		 wccproprietor.setName(wccproprietor.getTempname());
		 wccproprietor.setPhone(wccproprietor.getTempphone());
		 wccproprietor.setDoorplate(wccproprietor.getTempdoorplate());
		 wccproprietor.setWccappartment(wccproprietor.getTempappartment());
		 wccproprietor.setTempphone(null);
		 wccproprietor.setTempname(null);
		 wccproprietor.setTempdoorplate(null);
		 wccproprietor.setTempappartment(null);
	 }
        wccproprietor.merge();
        return "redirect:/wccproprietor/queryproprietor";
    }
	/**
	 * 下载模板
	 * @return
	 */
	@RequestMapping(value = "/downLoadTemplate", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadTemplate(Model uiModel,HttpServletRequest request,HttpServletResponse response){

		
		  Long a = System.currentTimeMillis();
		  
		  response.setCharacterEncoding("UTF-8");
		  response.setContentType("application/ms-excel");  
		  try {
			response.setHeader("content-disposition", "attachment;filename="
						+ new String(("业主信息导入模板").getBytes("gb2312"),
								"iso8859-1") + ".xls");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		  OutputStream os = null;
		  try {
			     os = response.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		  
		    try{  
		          
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
		   ///Label labelhead0 = new Label("当前列", "当前行", " 当前单元格的值", "引用的样式");     
		        Label labelhead0 = new Label(0, 0, "姓名", CBwcfF3);  
		        Label labelhead1 = new Label(1, 0, "手机", CBwcfF3);  
		        Label labelhead2 = new Label(2, 0, "项目名称", CBwcfF3); 
		        Label labelhead3 = new Label(3, 0, "门牌号", CBwcfF3);  
 
		        sheet.addCell(labelhead0);  
		        sheet.addCell(labelhead1);  
		        sheet.addCell(labelhead2);  
		        sheet.addCell(labelhead3);

		        
		        Label labe0 = new Label(0, 1, "张三"); //公众平台
 		        Label labe1 = new Label(1, 1, "156XXXXXXXX"); //项目名称 
 		        Label labe2 = new Label(2, 1, "兰亭荟"); //摘要
 		        Label labe3 = new Label(3, 1, "1栋1301");//姓名  
 		        Label labe4 = new Label(4, 1, "该数据仅供参考，添加时自动删除");//姓名 
 		        sheet.addCell(labe0);  
 		        sheet.addCell(labe1);  
 		        sheet.addCell(labe2);  
 		        sheet.addCell(labe3);  
 		        sheet.addCell(labe4);  
		        try{
		        	
		        	book.setProtected(false);
		        	book.write();
		        	os.flush();
		        	
		        }catch(Exception e){
		        	e.printStackTrace();
		        }finally{
		        	 book.close(); 
		        	 os.close();
		        }
		        
		        
		          
		    }catch (Exception e) {  
		        e.printStackTrace();  
		    }  
		      
	
		
		
		
		/*// 生成提示信息，
		response.setContentType("application/vnd.ms-excel");
		String[] title = {"姓名","手机","项目名称","门牌号"};
		OutputStream os = null;
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(("业主信息导入模板").getBytes("gb2312"),
							"iso8859-1") + ".xls");
			os = response.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportToExcel_templete(title,os);
		return;*/
	}
	@RequestMapping(value = "/delete", produces = "text/html")
    public String delete(HttpServletRequest request,@RequestParam(value = "id", required = false) String id,Model uiModel
    		) throws UnsupportedEncodingException {
		List<WccFees> list=null;
		CbdWccProprietor  cbdwccproprietor=null;
		if(id!=null && !id.equals("")){
			cbdwccproprietor= CbdWccProprietor.findCbdWccProprietor(Long.parseLong(id));
			list=WccFees.findFs(id);
		}
		String masg="删除成功";
	if(list.size()>0){
			masg="该业主有物管费信息，无法删除";
		}else{
			cbdwccproprietor.remove();
			uiModel.asMap().clear();
		}
		request.setAttribute("tishi", masg);
        return "wccproprietor/wccproprietor";
    }
	
	@RequestMapping(value = "/queryproprietor", produces = "text/html")
	public String list(
			@RequestParam(value = "tishi", required = false) String tishi,
			Model uiModel, HttpServletRequest request)
			throws ServletException, IOException {
	//	Map<String, String> parmMap = new HashMap<String, String>();
	//	CbdWccProprietor cbdwccproprietor=new CbdWccProprietor();
	//	PubOperator pubOper = CommonUtils.getCurrentPubOperator(request);
	//	Set<WccPlatformUser> platSets =  PubOperator.findPubOperator(pubOper.getId()).getPlatformUsers();
	//	cbdwccproprietor.setPlatformUsers(platSets);
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
		String platform="";
		for(WccPlatformUser use:platformUser){
			platform+=use.getId()+",";
		}
		QueryModel<WccAppartment> qm = new QueryModel<WccAppartment>();
	//	qm.setStart(1);
    //	qm.setLimit(Integer.MAX_VALUE);
		PageModel<WccAppartment> pageModel =WccAppartment.getRecord(qm, null, "2", platform.substring(0,platform.length()-1), null);
		uiModel.addAttribute("appartment",pageModel.getDataList());
		
	
		return "wccproprietor/wccproprietor";
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/getProprietor", produces = "text/html;charset=utf-8;")
	public String getProprietor(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "platformUserId", required = false) String platformUserId,
			@RequestParam(value = "itempk", required = false) String itempk,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "isensured", required = false) String isensured,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "createStartTime", required = false) String createStartTime,
			@RequestParam(value = "createEndTime", required = false) String createEndTime,
			@RequestParam(value = "confirmStartTime", required = false) String confirmStartTime,
			@RequestParam(value = "confirmEndTime", required = false) String confirmEndTime,
			@RequestParam(value = "tishi", required = false) String tishi,
			Model uiModel, HttpServletRequest request)
			throws ServletException, IOException {
		Map<String, String> parmMap = new HashMap<String, String>();
		CbdWccProprietor cbdwccproprietor=new CbdWccProprietor();
		cbdwccproprietor.setName(name);
		cbdwccproprietor.setPhone(phone);
		cbdwccproprietor.setIsEnsured(isensured);
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
		String platform="";
		for(WccPlatformUser use:platformUser){
			platform+=use.getId()+",";
		}
		QueryModel<WccAppartment> qm1 = new QueryModel<WccAppartment>();
	//	qm.setStart(1);
    //	qm.setLimit(Integer.MAX_VALUE);
		PageModel<WccAppartment> pageModel =WccAppartment.getRecord(qm1, null, "2", platform.substring(0,platform.length()-1), null);
		String wccId="";
		for(WccAppartment wcc:pageModel.getDataList()){
			wccId+=wcc.getId()+",";
		}
		
		
		parmMap.put("name", name);
		parmMap.put("phone", phone);
		parmMap.put("platformUserId", platformUserId);
		if(itempk != null && !itempk.equals("")){
			parmMap.put("itempk", itempk);
		}
		else{
			parmMap.put("itempk", wccId.substring(0,wccId.length()-1));
		}
		parmMap.put("createStartTime", createStartTime);
		parmMap.put("createEndTime", createEndTime);
		parmMap.put("confirmStartTime", confirmStartTime);
		parmMap.put("confirmEndTime", confirmEndTime);
		PubOperator pubOper = CommonUtils.getCurrentPubOperator(request);
		Set<WccPlatformUser> platSets =  PubOperator.findPubOperator(pubOper.getId()).getPlatformUsers();
		cbdwccproprietor.setPlatformUsers(platSets);
		cbdwccproprietor.setCompany(pubOper.getCompany());
		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
		QueryModel<CbdWccProprietor> qm = new QueryModel<CbdWccProprietor>(cbdwccproprietor, start,
				limit);
		qm.getInputMap().putAll(parmMap);
		PageModel<CbdWccProprietor> pm = CbdWccProprietor.getProprietorForPage(qm);
		PageModel<CbdWccProprietor> pageMode = new PageModel<CbdWccProprietor>();
		pageMode.setPageNo(page);
		pageMode.setTotalCount(pm.getTotalCount());
		pageMode.setPageSize(sizeNo);
		pageMode.setDataList(pm.getDataList());
		List<CbdWccProprietor> wccList = new ArrayList<CbdWccProprietor>();
		for (CbdWccProprietor list : pm.getDataList()) {
			wccList.add(list);
		}
		
		for(CbdWccProprietor cbd:wccList){
			if(cbd.getWccFriend()!=null){
			cbd.getWccFriend().setNickName(URLDecoder.decode(cbd.getWccFriend().getNickName(),"utf-8"));
			}
		}
		pageMode.setDataJson(CbdWccProprietor.toJsonArray(wccList));
		
		return pageMode.toJson();
	}
	/**
	 * 导出业主信息
	 * @param uiModel
	 * @param request
	 * @param name
	 * @param platformUserId
	 * @param itempk
	 * @param phone
	 * @param isensured
	 * @param start
	 * @param limit
	 * @param createStartTime
	 * @param createEndTime
	 * @param confirmStartTime
	 * @param confirmEndTime
	 * @return
	 */
	@RequestMapping(value = "/exportExcel", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String ExportExcel(
			Model uiModel,
			HttpServletRequest request,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "platformUserId", required = false) String platformUserId,
			@RequestParam(value = "itempk", required = false) String itempk,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "isensured", required = false) String isensured,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "createStartTime", required = false) String createStartTime,
			@RequestParam(value = "createEndTime", required = false) String createEndTime,
			@RequestParam(value = "confirmStartTime", required = false) String confirmStartTime,
			@RequestParam(value = "confirmEndTime", required = false) String confirmEndTime) {
		Map<String, String> parmMap = new HashMap<String, String>();
		CbdWccProprietor cbdwccproprietor=new CbdWccProprietor();
		cbdwccproprietor.setName(name);
		cbdwccproprietor.setPhone(phone);
		cbdwccproprietor.setIsEnsured(isensured);
		parmMap.put("platformUserId", platformUserId);
		parmMap.put("itempk", itempk);
		parmMap.put("createStartTime", createStartTime);
		parmMap.put("createEndTime", createEndTime);
		parmMap.put("confirmStartTime", confirmStartTime);
		parmMap.put("confirmEndTime", confirmEndTime);
		PubOperator pubOper = CommonUtils.getCurrentPubOperator(request);
		Set<WccPlatformUser> platSets =  PubOperator.findPubOperator(pubOper.getId()).getPlatformUsers();
		cbdwccproprietor.setPlatformUsers(platSets);
		QueryModel<CbdWccProprietor> qm = new QueryModel<CbdWccProprietor>(cbdwccproprietor, start,
				limit);
		qm.getInputMap().putAll(parmMap);
		PageModel<CbdWccProprietor> pm = CbdWccProprietor.getProprietorForPage(qm);
		List<CbdWccProprietor> list=pm.getDataList();
			
			// 创建Excel的工作书册 Workbook,对应到一个excel文档
			HSSFWorkbook wb = new HSSFWorkbook();

			// 创建Excel的工作sheet,对应到一个excel文档的tab
			HSSFSheet sheet = wb.createSheet("sheet1");

			// 设置excel每列宽度
			//sheet.setColumnWidth(0, 4000);
			//sheet.setColumnWidth(1, 3500);

			// 创建字体样式
			HSSFFont font = wb.createFont();
			font.setFontName("Verdana");
			font.setBoldweight((short) 100);
			font.setFontHeight((short) 300);
			font.setColor(HSSFColor.BLUE.index);

			// 创建单元格样式
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			// 设置边框
			style.setBottomBorderColor(HSSFColor.RED.index);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setFont(font);// 设置字体

			// 写表格头
			// 行
			int rowNumber = 1;
			// 列
			if(null!=list){
			
			for (int i = 0; i < list.size(); i++) {
				// 第一行信息
				CbdWccProprietor proprietor = list.get(i);
				List<Map<String, Object>> list_map = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = new HashMap<String, Object>();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				StringBuilder string = new StringBuilder();
				for(WccPlatformUser wccPlat:proprietor.getPlatformUsers()){
					string.append(wccPlat.getAccount()+",");
				}
				map.put("姓名",proprietor.getName()==null?"":proprietor.getName());
				list_map.add(map);
				map.put("电话",proprietor.getPhone()==null?"":proprietor.getPhone());
				list_map.add(map);
				map.put("项目名称", proprietor.getWccappartment()==null?"":proprietor.getWccappartment().getItemName());
				list_map.add(map);
				map.put("门牌号", proprietor.getDoorplate()==null?"":proprietor.getDoorplate());
				list_map.add(map);
				map.put("是否已认证", proprietor.getIsEnsured().equals("1")?"已认证":"未认证");
				list_map.add(map);
				map.put("认证时间",proprietor.getCertificationTime()==null?"":DateUtil.formatDateAndTime(proprietor.getCertificationTime()));
				list_map.add(map);

				HSSFRow firstrow = sheet.createRow(0); // 下标为0的行开始
				int CountColumnNum = list_map.size();

				HSSFCell[] firstcell = new HSSFCell[CountColumnNum];
				String[] names = new String[CountColumnNum];
				names[0] ="姓名";
				names[1] ="电话";
				names[2] ="项目名称";
				names[3] ="门牌号";
				names[4] ="是否已认证";
				names[5] ="认证时间";
				for (int j = 0; j < CountColumnNum; j++) {
					firstcell[j] = firstrow.createCell(j);
					firstcell[j].setCellValue(new HSSFRichTextString(names[j]));
				}
				HSSFRow row = sheet.createRow((int) rowNumber);// 创建一行
				// HSSFCell cell = null;// 创建列

				for (int j = 0; j < list_map.size(); j++) {
					Map<String, Object> map_s = list_map.get(j);
					for (String key : map_s.keySet()) {
						for (int k = 0; k < names.length; k++) {
							String ss = names[k];
							if (ss.equals(key)) {
								row.createCell((short) k).setCellValue(
										(String) map.get(key));
								break;
							}

						}
					}

				}
				rowNumber++;
			}
			}
			String wxCtxPath = request.getSession().getServletContext().getRealPath(Global.DOWNLOAD_BASE_PATH);
			File file = new File(wxCtxPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String fileName_ = System.currentTimeMillis() + "workbook.xls";
			String fileName = wxCtxPath+"/" + fileName_;
			FileOutputStream os = null;
			try {
				os = new FileOutputStream(fileName);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}finally{
				try {
					if(wb!=null){
						wb.write(os);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					if(os!=null){
						os.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
			String resps = "";
			try {
				resps = "."+Global.DOWNLOAD_BASE_PATH+"/" + fileName_;
			} catch (Exception e) {
				resps = "error";
			}
			return resps;
	}
	
	/**
	 * 文件导入
	 * 
	 * @param file
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "importExcel", method = RequestMethod.POST,produces="text/html;charset=utf-8")
	public String importExcel(HttpServletRequest request,Model model,HttpServletResponse response, 
			@RequestParam(value = "importFile") MultipartFile file,RedirectAttributes attr, HttpSession session) throws UnsupportedEncodingException {
		 Workbook rwb =null;
		 String flag = null;
		try {
			rwb = Workbook.getWorkbook(file.getInputStream());
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PubOperator  user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		Set<CbdWccProprietor> setCbdWccProprietor = new HashSet<CbdWccProprietor>();
		
				
			Sheet rs=rwb.getSheet(0);//或者rwb.getSheet(0)
		    int clos=rs.getColumns();//得到所有的列
		    int rows=rs.getRows();//得到所有的行  
		    request.setCharacterEncoding("utf-8");
		 
		    CbdWccProprietor proprietor =null;
		    String masgStr="导入成功";
		    boolean open = true;
		    for (int i = 2; i < (rows) && open; i++) {
		          for (int j = 0; j < clos; j++) {
		        	  boolean casse = true;
		              //第一个是列数，第二个是行数
		             String  name  =  rs.getCell(j++, i).getContents();// 关联公众号
		             String  phone  = rs.getCell(j++, i).getContents();  // 关联业主
		             String  itemname   = rs.getCell(j++, i).getContents();   //摘要
		             String  doorplate   = rs.getCell(j++, i).getContents();  //关联业主
		             proprietor=new CbdWccProprietor();
		             List<WccAppartment> app=WccAppartment.getWccAppartmentByName(itemname, "2");
		             if(app.size()>0){
		            	 List<CbdWccProprietor> list=CbdWccProprietor.getProprietorsList(name, phone, app.get(0), doorplate);
		            	 if(list.size()>0){
		            		 casse = false;
		            		 open = false;
		            		 masgStr= "已存在";
		            		 System.out.println("已存在");
		            		 break;
		            	 }
		             }
		             else{
		            	 masgStr="项目不存在";
		            	 casse=false;
		             }
		            
		               
		               if(name!=null){
		            	   if(name.equals("")){
		            		   casse=false;
		            		   open = false;
		            		   System.out.println("姓名不能为空");
		            		   flag = "1";
		            		   masgStr= "姓名不能为空";
		            		   break;
		            		   
		            	   }else{
		            		   proprietor.setName(name);
		            	   }
		               }
		               if(phone!=null){
		            	   if(phone.equals("")){
		            		   casse=false;
		            		   open = false;
		            		   System.out.println("电话不能为空");
		            		   masgStr="电话不能为空";
		            		   break;
		            	   }
		            	   else{
		            		   Pattern p=Pattern.compile("((0\\d{2,3}-\\d{7,8})|(1[35847]\\d{9}))"); 
		            		   Matcher m=p.matcher(phone); 
		            		   if(!m.find()){
		            			   System.out.println("电话不符合标准");
		            			   casse= false;
		            			   open = false;
		            			   masgStr="电话不符合标准";
		            			   break;
		            		   }
		            		   else{
		            			   proprietor.setPhone(phone);
		            		   }
		            	   }
		               }
		               if(itemname!=null){
		            	   if(itemname.equals("")){
		            		   casse=false;
		            		   open = false;
		            		   System.out.println("项目不能为空");
		            		   masgStr= "项目不能为空";
		            		   break;
		            	   }else{
		            		   if(app.size()>0){
		            			   proprietor.setWccappartment(app.get(0));
		            		   }
		         
		            		}
		               }
		               if(doorplate!=null){
		            	   if(doorplate.equals("")){
		            		   casse=false;
		            		   open = false;
		            		   System.out.println("门店号不能为空");
		            		   masgStr="门店号不能为空";
		            		   break;
		            	   }
		            	   else{
		            		   proprietor.setDoorplate(doorplate);
		            	   }
		               }

		               if(casse){
		            	    proprietor.setInsertTime(new Date());
			   				proprietor.setIsDelete(true);
			   				proprietor.setCompany(company);
			   				proprietor.setIsEnsured("0");
			   			  //proprietor.setPlatformUsers(user.getPlatformUsers());
		            	    proprietor.persist();
		            	    
		                 }
		               }
		           }
		request.setAttribute("tishi", masgStr);
		return "wccproprietor/wccproprietor";
	}
	
	

}

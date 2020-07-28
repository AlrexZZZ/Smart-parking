package com.nineclient.cherry.wcc.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
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
import javax.validation.Valid;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Border;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import me.chanjar.weixin.common.exception.WxErrorException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

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
import com.nineclient.cbd.wcc.dto.WccImgSaveDTO;
import com.nineclient.cbd.wcc.model.CbdWccProprietor;
import com.nineclient.cbd.wcc.model.WccFees;
import com.nineclient.cherry.wcc.model.WccQrCode;
import com.nineclient.cherry.wcc.util.CreateQrcode;
import com.nineclient.dto.WccQrCodeDTO;
import com.nineclient.model.PubOperator;
import com.nineclient.model.PubOrganization;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccProvince;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;

@RequestMapping("/qrCodeManage")
@Controller
public class QrCodeManageController {

	/**
	 * 二维码管理页面
	 * 
	 * @param 
	 * @return
	 */
	
	@RequestMapping(value = "showQrManageTable", produces = "text/html")
	public String showRecord(HttpServletRequest request,Model uiModel,
			HttpServletResponse response) {
		List<WccProvince> provinces = WccProvince.findAllWccProvinces();
		List<WccPlatformUser> list = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
		uiModel.addAttribute("plats", list);
		uiModel.addAttribute("provinces", provinces);
	   return "qrCodeTicket/qrCode";
	}
	
	/**
	 * 二维码记录查询
	 * 
	 * @param uiModel
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException 
	 */
	
	@ResponseBody
	@RequestMapping(value = "loadQrCode", produces = "application/json")
	public void loadCulture(String dtGridPager,HttpServletRequest request,Model model,HttpServletResponse response) throws UnsupportedEncodingException, ParseException {
		
		
		
		Pager pager =null;
		Map parm = new HashMap<String, String>();
		
		String platFormId = "";
		String codeType = "";
		String codeAttr = "";
		String areaOrActicityDes = "";
	    String areaInfo = "";
		// 查找当用户的公司
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
		PubOrganization orgaization = user.getOrganization();
		
		try {
			pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			parm  = pager.getParameters();
			platFormId = (String) parm.get("platFormId");
			codeAttr = (String) parm.get("codeAttr");
			codeType = (String) parm.get("codeType");
			areaOrActicityDes = (String) parm.get("areaOrActicityDes");
			areaInfo = (String) parm.get("areaInfo");
				 String  s =  (String) parm.get("restart");
				 if(s.equals("y")){
					 pager.setStartRecord(0);
					 pager.setNowPage(1);
				 }
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
   
        	QueryModel<WccQrCode> qm = new QueryModel<WccQrCode>();
        	if(platFormId != null && !platFormId.equals("")){
        		qm.putParams("platFormId", platFormId);
        	}
        	else{
        		String platform="";
        		for(WccPlatformUser use:platformUser){
        			platform+=use.getId()+",";
        		}
        		qm.putParams("platFormId", platform);
        	}
        	if(null != orgaization){
        		
        		/*qm.putParams("currentArea", orgaization.getRegion());*/
//       qm.putParams("currentArea",AuthorizationAreaInfo.getAreaByPubInfo(user.getCompany().getId(), orgaization));
        	}
        	qm.putParams("codeType", codeType);
        	qm.putParams("codeAttr", codeAttr);
        	qm.putParams("areaOrActicityDes", areaOrActicityDes);
        	qm.putParams("areaInfo", areaInfo);
        	qm.setStart(pager.getStartRecord());
        	qm.setLimit(pager.getPageSize());
        	PageModel <WccQrCode> p =	WccQrCode.getRecord(qm);

        	CacluatePage.totalPage(p.getPageSize(), p.getTotalCount());
        	List<WccQrCode> lit =	p.getDataList();

        List<WccQrCodeDTO> list = new ArrayList<WccQrCodeDTO>();
        if(null != lit && lit.size() > 0 ){
        for(WccQrCode s:lit){
        	String plIds = "";String names = "";
        if(null != s.getPlatformUsers() && s.getPlatformUsers().size() > 0){
        	Set <WccPlatformUser> ss  =	s.getPlatformUsers();
        	Iterator<WccPlatformUser> it = ss.iterator();
        	
        	while (it.hasNext()){ 
        		WccPlatformUser u = it.next();
        	     if(null != u.getId() && !"".equals(u.getId())){
        	    	 plIds += u.getId()+",";
        	     }
                if(null != u.getAccount() && !"".equals(u.getAccount())){
                	names += u.getAccount()+",";
        	     }
        	
        	}
        }
        if(plIds != ""){
        	plIds = plIds.substring(0, (plIds.length()-1));
        }
        if(names != ""){
        	names = names.substring(0, (names.length()-1));
        }
    WccPlatformUser plf = new WccPlatformUser();
    plf.setAccount(names);plf.setRemark(plIds);
    list.add(new WccQrCodeDTO(s.getId(), s.getCodeType(), s.getAreaInfo(), s.getAreaOrActicityDes(), 
    		s.getIsCreateCode(), s.getEmailAddress(), plf,s.getCodeAttr(),s.getCodeUrl(),
    		s.getActivityName(),s.getExpireTime(),s.getActityImg(),s.getCreateDate()));     
        }
   }
        List<WccQrCodeDTO> dto=new ArrayList<WccQrCodeDTO>();
        if(list.size()>pager.getStartRecord()+pager.getPageSize()){
        	for(int i=pager.getStartRecord();i<pager.getStartRecord()+pager.getPageSize();i++){
        		dto.add(list.get(i));
        	}
        }
        else{
        	for(int i=pager.getStartRecord();i<list.size();i++){
        		dto.add(list.get(i));
        	}
        }
		pager.setExhibitDatas(dto);
		pager.setPageCount(CacluatePage.totalPage(p.getPageSize(), p.getTotalCount()));
		pager.setRecordCount(Long.parseLong(p.getTotalCount()+""));//设置总记录数
		pager.setIsSuccess(true);
	   try {
		response.setContentType("application/json");//设置返回的数据为json对象
		JsonValueProcessor jsonProcessor = new DateJsonValueProcessor();
        JsonConfig jsonConfig = new JsonConfig();
       // jsonConfig.setIgnoreDefaultExcludes(false);
     jsonConfig.registerJsonValueProcessor(java.util.Date.class, jsonProcessor);
       // jsonConfig.setExcludes(new String[]{"platformUsers"}); 
        
		JSONObject pagerJSON = JSONObject.fromObject(pager);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(pagerJSON.toString());
	} catch (IOException e){
		e.printStackTrace();
	}
}


   
	@SuppressWarnings("static-access")
	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(HttpServletRequest request,Model uiModel,
    		@RequestParam(value = "dataId", required = false) String dataId,
			HttpServletResponse response) {
		List<WccProvince> provinces = WccProvince.findAllWccProvinces();
		 uiModel.addAttribute("provinces", provinces);
       if(null != dataId && !"".equals(dataId)){
    	   WccQrCode w  = new WccQrCode();
    	   WccQrCode wccQrCode = w.findWccQrCode(Long.parseLong(dataId));
    	   String pltIds = "";
    	      if(null != wccQrCode.getPlatformUsers()){
    	    	  
    	        	Set <WccPlatformUser> ss  =	wccQrCode.getPlatformUsers();
    	        	Iterator<WccPlatformUser> it = ss.iterator();
    	        	while (it.hasNext()){ 
    	        		WccPlatformUser u = it.next();
    	        	     if(null != u.getId() && !"".equals(u.getId())){
    	        	    	 pltIds += u.getId()+",";
    	        	     }
    	        	}
    	        }
    	   	if(null != pltIds && !"".equals(pltIds)){
        		uiModel.addAttribute("pltIds", pltIds.subSequence(0, (pltIds.length()-1)));
        	}else{
        		uiModel.addAttribute("pltIds", "");
        	}  
    	   uiModel.addAttribute("wccQrCode", wccQrCode);
    	   
       }
        return "qrCodeTicket/createQrCode";
    }

	@RequestMapping(params = "delete", produces = "text/html")
    public String deleteForm(HttpServletRequest request,
    		@RequestParam(value = "dataId", required = true) String dataId,
			HttpServletResponse response) {
		if(null != dataId && !"".equals(dataId)){
			
			WccQrCode w  = new WccQrCode();
			w.setId(Long.parseLong(dataId));
			w.remove();	
		}
		
        return null;
    }
	
	
	@RequestMapping(method = RequestMethod.POST,value = "createCodeSingle", produces = "text/html")
    public String createCodeSingle(@Valid WccQrCode wccQrCode, BindingResult bindingResult,
    		Model uiModel, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
//		PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
		if (bindingResult.hasErrors() ) {
			return "redirect:/qrCodeManage?form";
		}
	if(null != wccQrCode.getId() && !"".equals(wccQrCode.getId())){
	
		//先查询出实体
		WccQrCode wccQrCodeUpdate = WccQrCode.findWccQrCode(wccQrCode.getId());
		wccQrCodeUpdate.setPlatformUsers(wccQrCode.getPlatformUsers());
		wccQrCodeUpdate.setCodeType(wccQrCode.getCodeType());
		wccQrCodeUpdate.setCodeAttr(wccQrCode.getCodeAttr());
		wccQrCodeUpdate.setAreaInfo(wccQrCode.getAreaInfo());
		wccQrCodeUpdate.setAreaOrActicityDes(wccQrCode.getAreaOrActicityDes());
		wccQrCodeUpdate.setIsCreateCode(wccQrCode.getIsCreateCode());
		wccQrCodeUpdate.setEmailAddress(wccQrCode.getEmailAddress());
		wccQrCodeUpdate.setActivityName(wccQrCode.getActivityName());
		wccQrCodeUpdate.setActityImg(wccQrCode.getActityImg());
		   uiModel.asMap().clear();
		 //再更新数据
		   wccQrCodeUpdate.merge();
		}else{
			uiModel.asMap().clear();
		  if(wccQrCode.getIsCreateCode().equals("true")){
				// 创建二维码
			  try{
				  WccQrCode  wccqrcode   =	CreateQrcode.createOrgetCode(wccQrCode.getPlatformUsers(),httpServletRequest,wccQrCode);
			  } catch (WxErrorException e) {
				e.printStackTrace();
			}
		  }
			  wccQrCode.setCreateDate(new Date());
			  wccQrCode.persist();
		  
	    }
		return "qrCodeTicket/qrCode";
	}
	
	@RequestMapping(value = "importExcel", produces = "text/html")
    public String importExcel(HttpServletRequest request,
			HttpServletResponse response){
	MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
	 MultipartFile file  =  multipartRequest.getFile("file");
	 Workbook rwb =null;
	try {
		rwb = Workbook.getWorkbook(file.getInputStream());
	} catch (BiffException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

       
    	   //查询所有的公共账号 ,放到map中
   		List<WccPlatformUser> plist = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
    	Map<String,WccPlatformUser> pltMap = new LinkedHashMap<String,WccPlatformUser>(); 
    	if(null != plist && plist.size() > 0){
    		for(WccPlatformUser p:plist){
    			pltMap.put(p.getAccount(), p);
    		}
    	}
    	   Sheet rs=rwb.getSheet(0);//或者rwb.getSheet(0)
           int clos=rs.getColumns();//得到所有的列
           int rows=rs.getRows();//得到所有的行  
    	   List <WccFees> wl = new ArrayList<WccFees>();
    	   WccQrCode w = null;
    	   boolean casse = true;
           for (int i = 2; i < (rows); i++) {
               for (int j = 0; j < clos; j++) {
                   //第一个是列数j，第二个是行数i
               String platForms = rs.getCell(j++, i).getContents();// 关联公众号 -第一列
               String codeType  = rs.getCell(j++, i).getContents();  // 二维码类型-第二列
               String acitvityName  = rs.getCell(j++, i).getContents();  // 活动名称
               String codeAttr   = rs.getCell(j++, i).getContents();   //二维码属性
               String expireTime   = rs.getCell(j++, i).getContents();   //二维码有效期时间
               String areaInfo     = rs.getCell(j++, i).getContents();  //区域
               String describtion = rs.getCell(j++, i).getContents();//区域说明
               String isCreateCode = rs.getCell(j++, i).getContents();//是否生成二维码
               String emailInfo = rs.getCell(j++, i).getContents();//邮箱地址
               if(platForms.equals("") && codeType.equals("") && codeAttr.equals("") && 
            		   areaInfo.equals("") &&describtion.equals("")&&isCreateCode.equals("") && emailInfo.equals("") ){
            	   casse = false;
               }
               if(!"".equals(codeAttr.trim()) ){
            	   if(codeAttr.trim().equals("临时")){
            		   if("".equals(expireTime.trim())){
            			   casse = false;
            		   }
            	   }
               }
                 w = new WccQrCode();
   
              if(null != platForms && !"".equals(platForms.trim())){
            	   String tempPlt[] = platForms.replaceAll("，", ",").split(",");
            	   Set<WccPlatformUser> pltSet = new HashSet<WccPlatformUser>();
            
            	   for(String s:tempPlt){
            		  if(null != pltMap.get(s)){
            			  pltSet.add(pltMap.get(s));	  
            		  }
            	   }
            	  if(pltSet.size() > 0){
            		 // 1.保存公众账号
            		  w.setPlatformUsers(pltSet);
            	  }else{
            		  casse = false;
            	  } 
               }
             if(null != codeType && !"".equals(codeType)){
            	 // 2.保存二维码类型
            	  w.setCodeType(codeType);
              }
             if(null != acitvityName && !"".equals(acitvityName)){
            	 // 2.保存活动名称
            	 
            	 w.setActivityName(acitvityName);
              }
             if(null != codeAttr && !"".equals(codeAttr)){
            	// 3.保存二维码属性
            	   w.setCodeAttr(codeAttr);
              }
             if(null != expireTime && !"".equals(expireTime)){
             	// 3.保存二维码有效期
            	 try{
            		 int tem = Integer.parseInt(expireTime);
               	   if(tem > 0 && tem <=30){
               		  w.setExpireTime(Integer.parseInt(expireTime));
               	   }
            	 }catch(Exception ex){
            		 casse = false;
            	 }
            
             	   
               }
             if(null != areaInfo && !"".equals(areaInfo)){
            	// 4.保存区域
            	  w.setAreaInfo(areaInfo);
            	
           	   
             }
             if(null != describtion && !"".equals(describtion)){
            	// 5.保存区域、活动说明
           	    	w.setAreaOrActicityDes(describtion);
             }  
             if(null != isCreateCode && !"".equals(isCreateCode)){
             	// 6.是否生成二维码/ump/qrCodeManage/createCodeList
            	    w.setIsCreateCode(isCreateCode.equals("是")?"true":"false");
              }
             if(null != emailInfo && !"".equals(emailInfo)){
              	// 7.是否生成二维码
             	    w.setEmailAddress(emailInfo);
               } 
               if(casse){
            	   if(w.getIsCreateCode().equals("true")){
//            		   try {
//						CreateQrcode.createOrgetCode(w.getPlatformUsers(),request,w);
//					} catch (WxErrorException e) {
//						e.printStackTrace();
//					}	   
            	   }else{
            		   w.persist();
            	   }
       		      
       			   
                 }
               }
           }
		return "qrCodeTicket/qrCode";
	}
//导出模板
	@ResponseBody
	@RequestMapping(value="excelModel")
	public void getExcelModel(HttpServletRequest request,Model model,HttpServletResponse response,
			@RequestParam(value = "phoneNum", required = false) String phoneNum){
	
		  Long a = System.currentTimeMillis();
		  
		  response.setCharacterEncoding("UTF-8");
		  response.setContentType("application/ms-excel");  
		  response.setHeader("Content-Disposition", "attachment; filename=\"" + a + ".xls\"");
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
		        Label labelhead0 = new Label(0, 0, "公众平台", CBwcfF3);  
		        Label labelhead1 = new Label(1, 0, "二维码类型", CBwcfF3);
		        Label labelhead2 = new Label(2, 0, "活动名称", CBwcfF3); 
		        Label labelhead3 = new Label(3, 0, "二维码属性", CBwcfF3); 
		        Label labelhead4 = new Label(4, 0, "二维码有效期", CBwcfF3); 
		        Label labelhead5 = new Label(5, 0, "区域", CBwcfF3);  
		        Label labelhead6 = new Label(6, 0, "区域说明", CBwcfF3);  
		        Label labelhead7 = new Label(7, 0, "生成二维码", CBwcfF3);  
		        Label labelhead8 = new Label(8, 0, "邮箱地址", CBwcfF3);  
		        sheet.addCell(labelhead0);  
		        sheet.addCell(labelhead1);  
		        sheet.addCell(labelhead2);  
		        sheet.addCell(labelhead3);
		        sheet.addCell(labelhead4);
		        sheet.addCell(labelhead5);
		        sheet.addCell(labelhead6);
		        sheet.addCell(labelhead7);
		        sheet.addCell(labelhead8);
		        Label labe0 = new Label(0, 1, "广州交行(公众平台名称)"); //公众平台
   		        Label labe1 = new Label(1, 1, "活动(区域)"); //项目名称 
   		        Label labe2 = new Label(2, 1, "若二维码类型是活动请填写"); //摘要
   		        Label labe3 = new Label(3, 1, "临时(永久)"); //摘要
   		        Label labe4 = new Label(4, 1, "临时二维码请填写30以内的有效天数如:3"); //摘要
   		        Label labe5 = new Label(5, 1, "河南-信阳");//姓名  
   		        Label labe6 = new Label(6, 1, "区域(活动)说明"); // 电话
   		        Label labe7 = new Label(7, 1, "是(否)");  //门牌号
   		        Label labe8 = new Label(8, 1, "16888@qq.com");  //月份
   		        Label labe9 = new Label(9, 1, "该数据仅供参考，添加时自动删除");//金额	
   		        sheet.addCell(labe0);  
   		        sheet.addCell(labe1);  
   		        sheet.addCell(labe2);  
   		        sheet.addCell(labe3);  
   		        sheet.addCell(labe4);  
   		        sheet.addCell(labe5);
   		        sheet.addCell(labe6);
   		        sheet.addCell(labe7);
   		        sheet.addCell(labe8);
   		        sheet.addCell(labe9);
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
		      
	}
	//导出excel
	@ResponseBody
	@RequestMapping(value="getExcelDate",produces="text/html;charset=UTF-8")
	public void getExcelDate(HttpServletRequest request,Model model,HttpServletResponse response,
			@RequestParam(value = "platFormId", required = false) String platFormId,
			@RequestParam(value = "codeType", required = false) String codeType,
			@RequestParam(value = "codeAttr", required = false) String codeAttr,
			@RequestParam(value = "areaOrActicityDes", required = false) String areaOrActicityDes,
			@RequestParam(value = "areaInfo", required = false) String areaInfo) throws UnsupportedEncodingException{
	
		  Long a = System.currentTimeMillis();
		  response.setContentType("APPLICATION/OCTET-STREAM");  
          response.setHeader("Content-Disposition", "attachment; filename=\"" + a + ".xls\""); 
		  OutputStream os = null;
		   	PubOperator user = CommonUtils.getCurrentPubOperator(request);
		   	List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
			PubOrganization orgaization = user.getOrganization();
			QueryModel<WccQrCode> qm = new QueryModel<WccQrCode>();

     	if(platFormId != null && !platFormId.equals("")){
    		qm.putParams("platFormId", platFormId);
    	}else{
    		String platform="";
    		for(WccPlatformUser use:platformUser){
    			platform+=use.getId()+",";
    		}
    		qm.putParams("platFormId", platform);
    	}
 
//    	if(null != orgaization){
//   		qm.putParams("currentArea", orgaization.getRegion());
//    	}
    	qm.putParams("codeType",  new String(codeType.getBytes("ISO-8859-1"),"utf-8"));
    	qm.putParams("codeAttr", new String(codeAttr.getBytes("ISO-8859-1"),"utf-8"));
    	qm.putParams("areaOrActicityDes", new String(areaOrActicityDes.getBytes("ISO-8859-1"),"utf-8"));
    	qm.putParams("activityName", new String(areaOrActicityDes.getBytes("ISO-8859-1"),"utf-8"));
    	qm.putParams("areaInfo", new String(areaInfo.getBytes("ISO-8859-1"),"utf-8"));
		
		  
			   try {
				os   =	  response.getOutputStream();
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
		        Label labelhead0 = new Label(0, 0, "公众平台", CBwcfF3);  
		        Label labelhead1 = new Label(1, 0, "区域", CBwcfF3);  
		        Label labelhead2 = new Label(2, 0, "二维码来源", CBwcfF3); 
		        Label labelhead3 = new Label(3, 0, "二维码属性", CBwcfF3); 
		        Label labelhead4 = new Label(4, 0, "有效期(天)", CBwcfF3);
		        Label labelhead5 = new Label(5, 0, "活动名称", CBwcfF3);  
		        Label labelhead6 = new Label(6, 0, "创建二维码", CBwcfF3);  
		        Label labelhead7 = new Label(7, 0, "活动/区域说明", CBwcfF3);  
		        Label labelhead8 = new Label(8, 0, "邮箱地址", CBwcfF3);  
		        sheet.addCell(labelhead0);  
		        sheet.addCell(labelhead1);  
		        sheet.addCell(labelhead2);  
		        sheet.addCell(labelhead3);
		        sheet.addCell(labelhead4);
		        sheet.addCell(labelhead5);
		        sheet.addCell(labelhead6);
		        sheet.addCell(labelhead7);
		        sheet.addCell(labelhead8);
		       List<WccQrCode>  list =  WccQrCode.getExcelRecord(qm);
		       WccQrCode wccCode = null;
		        for (int i = 0; i < list.size(); i++) {  
		        	           wccCode = list.get(i);  
		        	           Set s = wccCode.getPlatformUsers();
		        	           String plStr = "";
		        	            Iterator<WccPlatformUser> it = s.iterator();
		        	            while (it.hasNext()){
		        					plStr += it.next().getAccount()+",";
		        				}
		        	            if(plStr != ""){
		        	            	plStr = plStr.substring(0, (plStr.length()-1));
		        	            }
		        	            Label labe0 = new Label(0, i+1, plStr); //公众平台
			       		        Label labe1 = new Label(1, i+1, wccCode.getAreaInfo()); //区域
			       		        Label labe2 = new Label(2, i+1, wccCode.getCodeType()); //二维码来源
			       		        Label labe3 = new Label(3, i+1, wccCode.getCodeAttr());//二维码属性
			       		        Label labe4 = new Label(4, i+1, wccCode.getExpireTime()==null?"":wccCode.getExpireTime()+"");//二维码属性
			       		        Label labe5 = new Label(5, i+1, wccCode.getActivityName());//活动名称 
			       		        Label labe6 = new Label(6, i+1, wccCode.getIsCreateCode().equals("true")?"是":"否"); // 电话
			       		        Label labe7 = new Label(7, i+1, wccCode.getAreaOrActicityDes());  //门牌号
			       		        Label labe8 = new Label(8, i+1, wccCode.getEmailAddress());  //月份
			       		        
			       		        sheet.addCell(labe0);  
			       		        sheet.addCell(labe1);  
			       		        sheet.addCell(labe2);  
			       		        sheet.addCell(labe3);  
			       		        sheet.addCell(labe4);  
			       		        sheet.addCell(labe5);
			       		        sheet.addCell(labe6);
			       		        sheet.addCell(labe7);
			       		        sheet.addCell(labe8);
        	        }   
		        try{
		        	 book.write();
		        	 os.flush();
		        }catch(Exception ex){
		        	ex.printStackTrace();
		        }finally{
		        	book.close();  
		        	os.close();
		        }
		    }catch (Exception e) {  
		        e.printStackTrace();  
		    }  
	}
	@RequestMapping(value = "createCodeList", produces = "text/html")
	public String createCodeList(HttpServletRequest request,Model uiModel,
			HttpServletResponse response) {
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		
		 List<WccQrCode>  list =  WccQrCode.getNeedCodeList();
		
         if(list != null && list.size() > 0){
        	 for(WccQrCode wccQrCode:list){
        		 try {
					CreateQrcode.createOrgetCode(wccQrCode.getPlatformUsers(),request,wccQrCode);
				} catch (WxErrorException e) {
					e.printStackTrace();
				} 
        	 }
         }
	   return "qrCodeTicket/qrCode";
	}
}

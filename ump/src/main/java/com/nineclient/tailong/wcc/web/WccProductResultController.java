package com.nineclient.tailong.wcc.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Border;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nineclient.cbd.wcc.model.WccFees;
import com.nineclient.cbd.wcc.util.CBD_WCCUtil;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.tailong.wcc.dto.WccFriendsAppointmentDTO;
import com.nineclient.tailong.wcc.model.WccFriendsAppointment;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;

@RequestMapping("/wccProductResult")
@Controller
public class WccProductResultController {

	/**
	 * 粉丝记录查询
	 * 
	 * @param uiModel
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException 
	 */
	
	@ResponseBody
	@RequestMapping(value = "loadResult", produces = "application/json")
	public void loadCulture(String dtGridPager,HttpServletRequest request,Model model,HttpServletResponse response) throws UnsupportedEncodingException, ParseException {
		
		
		
		Pager pager =null;
		Map parm = new HashMap<String, String>();
		String productName = "";
		String productType = "";
		String platFormId = "";
		String beginTime = "";
		String endTime = "";
	
		// 查找当用户的公司
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
		UmpCompany company = user.getCompany();
		
		try {
			 pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			 parm  = pager.getParameters();
			 productName = (String) parm.get("productName");
			 productType = (String) parm.get("productType");
				 platFormId = (String) parm.get("platFormId");
				 beginTime = (String) parm.get("beginTime");
				 endTime = (String) parm.get("endTime");
				 String  s =  (String) parm.get("restart");
				 if(s.equals("y")){
					 pager.setStartRecord(0);
					 pager.setNowPage(1);
				 }
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
   
        	QueryModel<WccFriendsAppointment> qm = new QueryModel<WccFriendsAppointment>();
        	if(platFormId != null && !"".equals(platFormId)){
        		qm.putParams("platFormId", platFormId);
        	}else{
       		String platform="";
       		for(WccPlatformUser use:platformUser){
       			platform+=use.getId()+",";
       		}
       		qm.putParams("platFormId", platform);
        	}
        	
        	qm.putParams("productType", productType);
        	qm.putParams("productName", productName);
        	qm.putParams("beginTime", beginTime);
        	qm.putParams("endTime", endTime);
        	System.out.println("+++++++++============"+pager.getStartRecord());
        	qm.setStart(pager.getStartRecord());
        	qm.setLimit(pager.getPageSize());
        	PageModel <WccFriendsAppointment> p =	WccFriendsAppointment.getRecord(qm);
        	List<WccFriendsAppointmentDTO> dto = new ArrayList<WccFriendsAppointmentDTO>();
            for(WccFriendsAppointment wt:p.getDataList()){
            	dto.add(new WccFriendsAppointmentDTO(wt.getId(), wt.getFname(),
            			wt.getFphone(), wt.getFarea(),wt.getFnickName(), wt.getFopenId(), 
            			wt.getProductType(),wt.getProductName(),wt.getResult(),wt.getInsertTime()));	
            }
		pager.setExhibitDatas(dto);
		pager.setPageCount(CBD_WCCUtil.getTotalPage(qm.getLimit(), p.getTotalCount()));
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

	
	
	//导出预约详情
	
	@ResponseBody
	@RequestMapping(value="getExcelResult",produces="text/html;charset=UTF-8")
	public void getExcelResult(HttpServletRequest request,Model model,HttpServletResponse response,
			@RequestParam(value = "phoneNum", required = false) String phoneNum){
	
		  Long a = System.currentTimeMillis();
		  String path="C:\\" + a + ".xls";  
		  
		  response.setContentType("APPLICATION/OCTET-STREAM");  
          response.setHeader("Content-Disposition", "attachment; filename=\"" + a + ".xls\""); 
		  OutputStream os = null;
		  
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
		        Label labelhead0 = new Label(0, 0, "姓名", CBwcfF3);  
		        Label labelhead1 = new Label(1, 0, "手机号", CBwcfF3);  
		        Label labelhead2 = new Label(2, 0, "粉丝昵称", CBwcfF3); 
		        Label labelhead3 = new Label(3, 0, "所在地区", CBwcfF3); 
		        Label labelhead4 = new Label(4, 0, "预约产品类型", CBwcfF3);  
		        Label labelhead5 = new Label(5, 0, "预约产品名称", CBwcfF3);  
		        Label labelhead6 = new Label(6, 0, "预约结果", CBwcfF3);  
		        Label labelhead7 = new Label(7, 0, "统计时间", CBwcfF3);  
		        sheet.addCell(labelhead0);  
		        sheet.addCell(labelhead1);  
		        sheet.addCell(labelhead2);  
		        sheet.addCell(labelhead3);
		        sheet.addCell(labelhead4);
		        sheet.addCell(labelhead5);
		        sheet.addCell(labelhead6);
		        sheet.addCell(labelhead7);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        List<WccFriendsAppointment>  list = WccFriendsAppointment.getRecordListByType(null, null);
		        WccFriendsAppointment data = null;
		        for (int i = 0; i < list.size(); i++) {  
		        	            data = list.get(i);  
		   
		        	            Label labe0 = new Label(0, i+1, data.getFname()); //姓名
			       		        Label labe1 = new Label(1, i+1, data.getFphone()); //手机号
			       		        Label labe2 = new Label(2, i+1, URLDecoder.decode(data.getFnickName(),"utf-8")); //粉丝昵称
			       		        Label labe3 = new Label(3, i+1, data.getFarea());//所在地区
			       		        Label labe4 = new Label(4, i+1, data.getProductType());//预约产品类型
			       		        Label labe5 = new Label(5, i+1, data.getProductName()); // 预约产品名称
			       		        Label labe6 = new Label(6, i+1, data.getResult());  //预约结果
			       		        Label labe7 = new Label(7, i+1, sdf.format(data.getInsertTime()));  //统计时间
			       		        
			       		        sheet.addCell(labe0);  
			       		        sheet.addCell(labe1);  
			       		        sheet.addCell(labe2);  
			       		        sheet.addCell(labe3);  
			       		        sheet.addCell(labe4);  
			       		        sheet.addCell(labe5);
			       		        sheet.addCell(labe6);
			       		        sheet.addCell(labe7);
		        	    
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
	
	
	
	
	
	
	/**
	 * 粉丝记录页面
	 * 
	 * @param 
	 * @return
	 */
	
	@RequestMapping(value = "showResult", produces = "text/html")
	public String showRecord(HttpServletRequest request,Model uiModel,
			HttpServletResponse response) {
		
		
		
	   return "tailong/productResult";
	}
   
	

	@RequestMapping(params = "update", produces = "text/html")
    public String deleteForm(HttpServletRequest request,
    		@RequestParam(value = "dataId", required = true) Long dataId,
    		@RequestParam(value = "result", required = true) String result,
			HttpServletResponse response) {
		if(null != dataId && !"".equals(dataId)){
			WccFriendsAppointment w  =	WccFriendsAppointment.findWccFriendsAppointment(dataId);
			w.setResult(result);
			w.merge();	
		}
		
        return null;
    }
	
	
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WccFriendsAppointment WccFriendsAppointment, BindingResult bindingResult,
    		Model uiModel, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
//		PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
		if (bindingResult.hasErrors() ) {
			return "redirect:/lifeHelper?form";
		}
	if(null != WccFriendsAppointment.getId() && !"".equals(WccFriendsAppointment.getId())){

		//先查询出实体

		 uiModel.asMap().clear();
		 //再更新数据
//		 WccFriendsAppointmentUpdate.merge();
		}else{
			uiModel.asMap().clear();
			WccFriendsAppointment.setInsertTime(new Date());
			WccFriendsAppointment.persist();
		}
	
	    	
		
		return "redirect:/wccProduct/showRecord";
	}
	
	
}

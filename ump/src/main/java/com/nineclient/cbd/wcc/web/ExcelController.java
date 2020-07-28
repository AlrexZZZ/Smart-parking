package com.nineclient.cbd.wcc.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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
import javax.validation.Valid;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Border;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
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
import com.nineclient.cbd.wcc.dto.CbdWccProprietorDTO;
import com.nineclient.cbd.wcc.dto.WccFeesDTO;
import com.nineclient.cbd.wcc.model.CbdWccProprietor;
import com.nineclient.cbd.wcc.model.WccAppartment;
import com.nineclient.cbd.wcc.model.WccCultureLife;
import com.nineclient.cbd.wcc.model.WccFees;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;

@RequestMapping("/excel")
@Controller
public class ExcelController {

	/**
	 * excel 处理文件
	 * 
	 * @param uiModel
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException 
	 */
	

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
		        Label labelhead1 = new Label(1, 0, "项目名称", CBwcfF3);  
		        Label labelhead2 = new Label(2, 0, "摘要", CBwcfF3); 
		        Label labelhead3 = new Label(3, 0, "姓名", CBwcfF3);  
		        Label labelhead4 = new Label(4, 0, "电话", CBwcfF3);  
		        Label labelhead5 = new Label(5, 0, "门牌号", CBwcfF3);  
		        Label labelhead6 = new Label(6, 0, "月份", CBwcfF3);  
		        Label labelhead7 = new Label(7, 0, "状态", CBwcfF3);  
		        Label labelhead8 = new Label(8, 0, "金额", CBwcfF3);  
		        sheet.addCell(labelhead0);  
		        sheet.addCell(labelhead1);  
		        sheet.addCell(labelhead2);  
		        sheet.addCell(labelhead3);
		        sheet.addCell(labelhead4);
		        sheet.addCell(labelhead5);
		        sheet.addCell(labelhead6);
		        sheet.addCell(labelhead7);
		        sheet.addCell(labelhead8);
		        
		        Label labe0 = new Label(0, 1, "广州交行"); //公众平台
   		        Label labe1 = new Label(1, 1, "兰亭荟"); //项目名称 
   		        Label labe2 = new Label(2, 1, "煤气费"); //摘要
   		        Label labe3 = new Label(3, 1, "张三");//姓名  
   		        Label labe4 = new Label(4, 1, "13XXXXXXXXX"); // 电话
   		        Label labe5 = new Label(5, 1, "1栋201");  //门牌号
   		        Label labe6 = new Label(6, 1, "201506");  //月份
   		        Label labe7 = new Label(7, 1, "未支付");  //支付状态
   		        Label labe8 = new Label(8, 1, "1000");//金额	           
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

/*       if(null != rwb){
    		//查询所有的项目
    		List<WccAppartment> app = WccAppartment.getWccAppartmentByXp(null);
    		Map<Long,WccAppartment> proprMap = new LinkedHashMap<Long,WccAppartment>();
    	  for(WccAppartment wcca:app){
    		  proprMap.put(wcca.getId(), wcca);
    	  }  */ 
    	   
    	   
    	   //查询所有的业主信息
       List<CbdWccProprietor>  proList =  CbdWccProprietor.getProprietorsList(null, null,null, null);
       Map<String,CbdWccProprietor>cbdMap = new LinkedHashMap<String,CbdWccProprietor>();   
         if( null != proList && proList.size() > 0){
        	 for(CbdWccProprietor c:proList){
        	String key = c.getWccappartment().getItemName()+""+c.getName()+""+c.getPhone()+""+c.getDoorplate(); 
        	cbdMap.put(key, c);		 
          }
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
    	   WccFees w = null;
    	   boolean casse = true;
           for (int i = 2; i < (rows); i++) {
               for (int j = 0; j < clos; j++) {
                   //第一个是列数，第二个是行数
               String platForms = rs.getCell(j++, i).getContents();// 关联公众号
               String itemName  = rs.getCell(j++, i).getContents();  // 关联业主
               String summary   = rs.getCell(j++, i).getContents();   //摘要
               String uname     = rs.getCell(j++, i).getContents();  //关联业主
               String cellphone = rs.getCell(j++, i).getContents();//关联业主
               String doorNum = rs.getCell(j++, i).getContents();//关联业主
               String monthStr = rs.getCell(j++, i).getContents();//月份
               String state = rs.getCell(j++, i).getContents(); //状态
               String amount = rs.getCell(j++, i).getContents();//金额
                 w = new WccFees();
               //根据项 目名+姓名+手机+门牌号 找业主是否存在
               if(null != cbdMap.get(itemName+uname+cellphone+doorNum)){
            	   //保存项 目名+姓名+手机+门牌号 
            	   w.setCbdWccProprietor(cbdMap.get(itemName+uname+cellphone+doorNum));
               }else{
            	   casse = false;
            	   System.out.println("此业主信息不对！");
               }
              if(null != platForms && !"".equals(platForms.trim())){
            	   String tempPlt[] = platForms.replaceAll("，", ",").split(",");
            	   Set<WccPlatformUser> pltSet = new HashSet<WccPlatformUser>();
            
            	   for(String s:tempPlt){
            		  if(null != pltMap.get(s)){
            			  pltSet.add(pltMap.get(s));	  
            		  }
            	   }
            	  if(pltSet.size() > 0){
            		 // 保存公众账号
            		  w.setPlatformUsers(pltSet);
            	  }else{
            		  casse = false;
            		  System.out.println("不存在的公共账号！");
            	  } 
               }
             if(null != summary && !"".equals(summary)){
            	  
            	  w.setSummary(summary);
              }
              
             if(null != monthStr && !"".equals(monthStr)){
            	  
            	   w.setMonthStr(monthStr);
            	   String mStr = w.getMonthStr();
     		       mStr = mStr.substring(0, 4)+"-"+mStr.substring(4, mStr.length())+"-01";
     		       DateUtil.formateDate(mStr, "yyyy-MM-dd");
     		       w.setMonth(DateUtil.formateDate(mStr, "yyyy-MM-dd"));  
            	  
              }
             if(null != state && !"".equals(state)){
           	   
            	 if("已支付".equals(state)){
            		 w.setState(1);
            	 }else{
            		 w.setState(0);
            	 }
           	   
             }else{
            	 casse = false;
            	 System.out.println("状态不能为空！");
             }
             if(null != amount && !"".equals(amount)){
            	 Float am = null;
           	  try{
           		 am = Float.parseFloat(amount);
           	  }catch(Exception e){
           	  }
           	    if(am != null){
           	    	w.setAmount(am);
           	    }
             }  
             
               if(casse){
            	   w.setInsertTime(new Date());
       		       w.persist();
       			   
                 }
               }
           }

           
         
		
        return "redirect:/wccFees/showRecord?&displayId=90";
    }
	@ResponseBody
	@RequestMapping(value="getExcelDate",produces="text/html;charset=UTF-8")
	public void getExcelDate(HttpServletRequest request,Model model,HttpServletResponse response,
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
		        Label labelhead0 = new Label(0, 0, "公众平台", CBwcfF3);  
		        Label labelhead1 = new Label(1, 0, "项目名称", CBwcfF3);  
		        Label labelhead2 = new Label(2, 0, "摘要", CBwcfF3); 
		        Label labelhead3 = new Label(3, 0, "姓名", CBwcfF3);  
		        Label labelhead4 = new Label(4, 0, "电话", CBwcfF3);  
		        Label labelhead5 = new Label(5, 0, "门牌号", CBwcfF3);  
		        Label labelhead6 = new Label(6, 0, "月份", CBwcfF3);  
		        Label labelhead7 = new Label(7, 0, "状态", CBwcfF3);  
		        Label labelhead8 = new Label(8, 0, "金额", CBwcfF3);  
		        sheet.addCell(labelhead0);  
		        sheet.addCell(labelhead1);  
		        sheet.addCell(labelhead2);  
		        sheet.addCell(labelhead3);
		        sheet.addCell(labelhead4);
		        sheet.addCell(labelhead5);
		        sheet.addCell(labelhead6);
		        sheet.addCell(labelhead7);
		        sheet.addCell(labelhead8);
	
		       List<WccFees>  list =  WccFees.getQueryRecordList(null);
		        
		        for (int i = 0; i < list.size(); i++) {  
		        	           WccFees data=list.get(i);  
		   
/*		        	        Label labe0 = new Label(0, i+1, "公众平台", CBwcfF3);  
		       		        Label labe1 = new Label(1, i+1, "项目名称", CBwcfF3);  
		       		        Label labe2 = new Label(2, i+1, "摘要", CBwcfF3); 
		       		        Label labe3 = new Label(3, i+1, "姓名", CBwcfF3);  
		       		        Label labe4 = new Label(4, i+1, "电话", CBwcfF3);  
		       		        Label labe5 = new Label(5, i+1, "门牌号", CBwcfF3);  
		       		        Label labe6 = new Label(6, i+1, "月份", CBwcfF3);  
		       		        Label labe7 = new Label(7, i+1, "状态", CBwcfF3);  
		       		        Label labe8 = new Label(8, i+1, "金额", CBwcfF3);*/            
		        	           Set s = list.get(i).getPlatformUsers();
		        	           String plStr = "";
		        	            Iterator<WccPlatformUser> it = s.iterator();
		        	            while (it.hasNext()){
		        					plStr += it.next().getAccount()+",";
		        				}
		        	            if(plStr != ""){
		        	            	plStr = plStr.substring(0, (plStr.length()-1));
		        	            }
		        	            Label labe0 = new Label(0, i+1, plStr); //公众平台
			       		        Label labe1 = new Label(1, i+1, data.getCbdWccProprietor().getWccappartment().getItemName()); //项目名称 
			       		        Label labe2 = new Label(2, i+1, data.getSummary()); //摘要
			       		        Label labe3 = new Label(3, i+1, data.getCbdWccProprietor().getName());//姓名  
			       		        Label labe4 = new Label(4, i+1, data.getCbdWccProprietor().getPhone()); // 电话
			       		        Label labe5 = new Label(5, i+1, data.getCbdWccProprietor().getDoorplate());  //门牌号
			       		        Label labe6 = new Label(6, i+1, data.getMonthStr());  //月份
			       		        Label labe7 = new Label(7, i+1, data.getState()==0?"未支付":"已支付");  //支付状态
			       		        Label labe8 = new Label(8, i+1, data.getAmount()+"");//金额	           
			       		        
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
}

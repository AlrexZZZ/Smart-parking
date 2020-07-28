package com.nineclient.qycloud.wcc.web;

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
import me.chanjar.weixin.common.exception.WxErrorException;
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
import com.nineclient.qycloud.wcc.dto.WccEnListDTO;
import com.nineclient.qycloud.wcc.dto.WccRepairRegDTO;
import com.nineclient.qycloud.wcc.dto.WccStuDTO;
import com.nineclient.qycloud.wcc.model.WccEnList;
import com.nineclient.qycloud.wcc.model.WccRepairReg;
import com.nineclient.qycloud.wcc.model.WccStu;
import com.nineclient.qycloud.wcc.util.DtgridPageRender;
import com.nineclient.qycloud.wcc.util.DtgridQueryCondition;
import com.nineclient.qycloud.wcc.util.PageCondition;
import com.nineclient.tailong.wcc.dto.WccFriendsAppointmentDTO;
import com.nineclient.tailong.wcc.model.WccFriendsAppointment;
import com.nineclient.tailong.wcc.model.WccSaQrCode;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.CreateQrcode;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;

@RequestMapping("/bdingStuInfoResult")
@Controller
public class StuBdingResultController {
	
	
	/**
	 * qcloud/update
	 * 进入显示线索数据页面
	 */
	@RequestMapping(value = "showStuResult", produces = "text/html")
	public String showRecord(HttpServletRequest request, Model uiModel,
			HttpServletResponse response,
			@RequestParam(value = "name", required = false) String name) {
		// 根据登录者的信息来展示和隐藏按钮功能
		return "qcloud/stuInfo";
	}
	
	
	
	@RequestMapping(value = "showRepairInfo", produces = "text/html")
	public String showRepairInfo(HttpServletRequest request, Model uiModel,
			HttpServletResponse response,
			@RequestParam(value = "name", required = false) String name) {
		// 根据登录者的信息来展示和隐藏按钮功能
		return "qcloud/repairInfo";
	}
	
	@RequestMapping(value = "showEnListInfo", produces = "text/html")
	public String showEnListInfo(HttpServletRequest request, Model uiModel,
			HttpServletResponse response,
			@RequestParam(value = "name", required = false) String name) {
		// 根据登录者的信息来展示和隐藏按钮功能
		return "qcloud/enListInfo";
	}
	
	/**
	 * qcloud/update
	 * 进入显示线索数据页面
	 */
	@RequestMapping(value = "showRepairUpdate", produces = "text/html")
	public String showRepairUpdate(HttpServletRequest request, Model uiModel,
			HttpServletResponse response,
			@RequestParam(value = "dataId", required = false) Long dataId) {
		
		uiModel.addAttribute("s", WccRepairReg.findWccRepairReg(dataId));
		return "qcloud/updateRepair";
	}
	
	
	/**
	 * qcloud/update
	 * 进入显示线索数据页面
	 */
	@RequestMapping(value = "showEnListUpdate", produces = "text/html")
	public String showEnListUpdate(HttpServletRequest request, Model uiModel,
			HttpServletResponse response,
			@RequestParam(value = "dataId", required = false) Long dataId) {
		
		uiModel.addAttribute("s", WccEnList.findWccEnList(dataId));
		return "qcloud/updateEnList";
	}
	
	
	/**
	 * qcloud/update
	 * 进入显示线索数据页面
	 */
	@RequestMapping(value = "showStuUpdate", produces = "text/html")
	public String showStuUpdate(HttpServletRequest request, Model uiModel,
			HttpServletResponse response,
			@RequestParam(value = "dataId", required = false) Long dataId) {
		
		uiModel.addAttribute("s", WccStu.findWccStu(dataId));
		return "qcloud/update";
	}
	
	@RequestMapping(value = "UpdateRepair",method = RequestMethod.POST, produces = "text/html")
	public String UpdateRepair(@Valid WccRepairReg wccRepairReg,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest)
			throws UnsupportedEncodingException {
		
		if (bindingResult.hasErrors()) {
			return "redirect:/qcloud/update";
		}
		if (null != wccRepairReg.getId() && !"".equals(wccRepairReg.getId())) {
			WccRepairReg update = WccRepairReg.findWccRepairReg(wccRepairReg.getId());
			update.setFname(wccRepairReg.getFname());
			update.setDept(wccRepairReg.getDept());
			update.setArea(wccRepairReg.getArea());
			update.setStuNo(wccRepairReg.getStuNo());
			update.setFphone(wccRepairReg.getFphone());
			update.setAddress(wccRepairReg.getAddress());
			update.setProblemType(wccRepairReg.getProblemType());
			update.setIsBack(wccRepairReg.getIsBack());
			update.setProblemDes(wccRepairReg.getProblemDes());
			update.merge();
		} else {
			
		}

		return "redirect:/bdingStuInfoResult/showRepairInfo";
	}
	
	//
	@RequestMapping(value = "deleteRepair", produces = "text/html")
	public String deleteRepair(@RequestParam(value = "dataId", required = false) Long dataId,
			 Model uiModel,
			HttpServletRequest httpServletRequest)
			throws UnsupportedEncodingException {
		WccRepairReg data = WccRepairReg.findWccRepairReg(dataId);
	if(null != data){
		data.remove();
	}

		return "redirect:/bdingStuInfoResult/showRepairInfo";
	}
	//
	@RequestMapping(value = "deleteEnList", produces = "text/html")
	public String deleteEnList(@RequestParam(value = "dataId", required = false) Long dataId,
			 Model uiModel,
			HttpServletRequest httpServletRequest)
			throws UnsupportedEncodingException {
		WccEnList data = WccEnList.findWccEnList(dataId);
	if(null != data){
		data.remove();
	}

		return "redirect:/bdingStuInfoResult/showRepairInfo";
	}	
	
	@RequestMapping(value = "UpdateEnList",method = RequestMethod.POST, produces = "text/html")
	public String UpdateEnList(@Valid WccEnList enList,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest)
			throws UnsupportedEncodingException {
		
		if (bindingResult.hasErrors()) {
			return "redirect:/qcloud/update";
		}
		if (null != enList.getId() && !"".equals(enList.getId())) {
			WccEnList update = WccEnList.findWccEnList(enList.getId());
			update.setStuNo(enList.getStuNo());
			update.setFname(enList.getFname());
			update.setGender(enList.getGender());
			update.setDept(enList.getDept());
			update.setArea(enList.getArea());
			update.setPhone(enList.getPhone());
			update.setForDept(enList.getForDept());
			update.setGoodAt(enList.getGoodAt());
			update.setDes(enList.getDes());
			update.setPhone(enList.getPhone());
			update.merge();
		} else {
			
		}

		return "redirect:/bdingStuInfoResult/showEnListInfo";
	}
	
	
	@RequestMapping(value = "UpdateStu",method = RequestMethod.POST, produces = "text/html")
	public String UpdateStu(@Valid WccStu wccstu,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest)
			throws UnsupportedEncodingException {
		
		if (bindingResult.hasErrors()) {
			return "redirect:/qcloud/update";
		}
		if (null != wccstu.getId() && !"".equals(wccstu.getId())) {
			WccStu update = WccStu.findWccStu(wccstu.getId());
			update.setFname(wccstu.getFname());
			update.setFphone(wccstu.getFphone());
			update.setStuNo(wccstu.getStuNo());
			update.setStuColleage(wccstu.getStuColleage());
			update.merge();
		} else {
			
		}

		return "redirect:/bdingStuInfoResult/showStuResult";
	}
	
	/**
	 * 记录查询
	 * 
	 * @param uiModel
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException 
	 */
	
	//查询线索列表
	@ResponseBody
	@RequestMapping(value="repairList", produces = "application/json" )
	public String repairList(
			@RequestParam(value = "page", required = false) Integer page,
			HttpServletRequest request,String dtGridPager,
			@RequestParam(value = "displayId", required = false) Long displayId,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel, HttpServletResponse response)
			throws UnsupportedEncodingException {
		//获取当前用户及其下属的id		
		//new page dtgird对象
		QueryModel<WccRepairReg> qm = new QueryModel<WccRepairReg>();
		PageCondition  condition = DtgridQueryCondition.makeCondition(dtGridPager, qm,request);
		PageModel<WccRepairReg> pagerModel = WccRepairReg.getRecord((QueryModel<WccRepairReg>)condition.getQm());
		List <WccRepairReg> list = pagerModel.getDataList();
		List <WccRepairRegDTO> listdto = new ArrayList<WccRepairRegDTO>();
		WccRepairRegDTO  dto = null; 
		if(null != list && list.size() > 0){
		for(WccRepairReg w:list){
			dto = new WccRepairRegDTO(w.getId(), w.getFname(), w.getFphone(), w.getStuNo(), 
					w.getDept(), w.getArea(), w.getAddress(),
					w.getProblemType(), w.getIsBack(), w.getProblemDes(), 
					w.getFnickName(), w.getOpenId(), w.getInsertTime());
			listdto.add(dto);
		}
		}
		///page 封装写到页面
		condition.getPager().setExhibitDatas(listdto);
		DtgridPageRender.writeResponseJsonToPageFromPager(response, pagerModel, condition.getPager());
		
		return null;
	}
	//查询线索列表
		@ResponseBody
		@RequestMapping(value="enList", produces = "application/json" )
		public String enList(
				@RequestParam(value = "page", required = false) Integer page,
				HttpServletRequest request,String dtGridPager,
				@RequestParam(value = "displayId", required = false) Long displayId,
				@RequestParam(value = "size", required = false) Integer size,
				@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
				@RequestParam(value = "sortOrder", required = false) String sortOrder,
				Model uiModel, HttpServletResponse response)
				throws UnsupportedEncodingException {
			//获取当前用户及其下属的id		
			//new page dtgird对象
			QueryModel<WccEnList> qm = new QueryModel<WccEnList>();
			PageCondition  condition = DtgridQueryCondition.makeCondition(dtGridPager, qm,request);
			PageModel<WccEnList> pagerModel = WccEnList.getRecord((QueryModel<WccEnList>)condition.getQm());
			List<WccEnList> list = pagerModel.getDataList();
			List <WccEnListDTO> listdto = new ArrayList<WccEnListDTO>();
			WccEnListDTO  dto = null; 
			if(null != list && list.size() > 0){
			for(WccEnList w:list){
				dto = new WccEnListDTO(w.getId(), w.getStuNo(), w.getFname(), w.getGender(),
						w.getDept(), w.getArea(), w.getPhone(), w.getForDept(),
						w.getGoodAt(), w.getDes(), w.getOpenId(), w.getInsertTime());
				listdto.add(dto);
			}
			}
			///page 封装写到页面
			condition.getPager().setExhibitDatas(listdto);
			DtgridPageRender.writeResponseJsonToPageFromPager(response, pagerModel, condition.getPager());
			
			return null;
		}
	/**
	 * 记录查询
	 * 
	 * @param uiModel
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException 
	 */
	
	//查询线索列表
	@ResponseBody
	@RequestMapping(value="list", produces = "application/json" )
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			HttpServletRequest request,String dtGridPager,
			@RequestParam(value = "displayId", required = false) Long displayId,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel, HttpServletResponse response)
			throws UnsupportedEncodingException {
		//获取当前用户及其下属的id		
		//new page dtgird对象
		QueryModel<WccStu> qm = new QueryModel<WccStu>();
		PageCondition  condition = DtgridQueryCondition.makeCondition(dtGridPager, qm,request);
		PageModel<WccStu> pagerModel = WccStu.getRecord((QueryModel<WccStu>)condition.getQm());
		List <WccStu> list = pagerModel.getDataList();
		List <WccStuDTO> listdto = new ArrayList<WccStuDTO>();
		WccStuDTO  dto = null; 
		if(null != list && list.size() > 0){
		for(WccStu w:list){
			dto = new WccStuDTO(w.getId(), w.getFname(), w.getFphone(), w.getStuNo(),
					w.getStuColleage(), w.getFnickName(), w.getOpenId(), w.getInsertTime());
			listdto.add(dto);
		}
		}
		///page 封装写到页面
		condition.getPager().setExhibitDatas(listdto);
		DtgridPageRender.writeResponseJsonToPageFromPager(response, pagerModel, condition.getPager());
		
		return null;
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
	
	

	
	
}

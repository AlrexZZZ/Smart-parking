package com.nineclient.cbd.wcc.web;

//故障报修
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nineclient.cbd.wcc.model.WccAppartment;
import com.nineclient.cbd.wcc.model.WccImgSave;
import com.nineclient.cbd.wcc.model.WccMalfunctionType;
import com.nineclient.cbd.wcc.model.WccMalfunctions;
import com.nineclient.model.WccRecordMsg;

@RequestMapping("/pageRepair")
@Controller
@RooWebScaffold(path = "cbdwcc_pages/faulService", formBackingObject = WccRecordMsg.class)
public class PagesFaultServiceController {
	@RequestMapping(value = "showRepair", produces = "text/html")
	public String showCulture(HttpServletRequest request,Model uiModel,
			 @RequestParam(value="platId",required=false)String platId,
			HttpServletResponse response) {
		List<WccAppartment> list=WccAppartment.getWccAppartmentByXp(platId,"2");
		List<WccImgSave> wcc=WccImgSave.findBytype(Long.parseLong(platId), "3");
	
		  uiModel.addAttribute("list", list);
		  uiModel.addAttribute("platId", platId);
		  if(wcc.size()>0){
			  uiModel.addAttribute("wcc", wcc.get(0));
		  }
	   return "page/repair";
	}
	
	@RequestMapping(value = "typeCreate", produces = "text/html")
	public String loadType(HttpServletRequest request,Model uiModel,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value="platId",required=false)String platId,
			HttpServletResponse response) {
		if(platId!=null && !platId.equals("")){
			List<WccAppartment> list=WccAppartment.getWccAppartmentByXp(platId,"2");
		
		if(type==null || type.equals("")){
			if(list.size()>0){
				type=list.get(0).getId()+"";
			}
			else{
				return null;
			}
		}
		
	//System.out.println(type+"~~~~~~~~~~~~");
		List<WccMalfunctionType> listMal=WccMalfunctionType.findByItem(WccAppartment.findPubOrganization(Long.parseLong(type)));
		uiModel.addAttribute("list", list);
		uiModel.addAttribute("listMal",listMal);
		uiModel.addAttribute("id", type);
		uiModel.addAttribute("platId", platId);
		List<WccImgSave> wcc=WccImgSave.findBytype(Long.parseLong(platId), "3");
		  if(wcc.size()>0){
			  uiModel.addAttribute("wcc", wcc.get(0));
		  }
		}
	   return "page/repair";
	}
	
	
	
	@RequestMapping(value = "create", produces = "text/html")
	public String create(HttpServletRequest request,Model uiModel,
			@RequestParam(value = "itmePk", required = false) String itmePk,
			@RequestParam(value = "malfunctionType", required = false) String malfunctionType,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "malPic", required = false) MultipartFile malPic,
			@RequestParam(value = "remark", required = false) String remark,
			 @RequestParam(value="platId",required=false)String platId,
			HttpServletResponse response) throws IOException {
		if(itmePk!=null && !itmePk.equals("") && malfunctionType!=null && !malfunctionType.equals("") && name!=null && !name.equals("")){
			WccMalfunctions wccMalfunctions=new WccMalfunctions();
			
			 if(!malPic.isEmpty()){
				         ServletContext sc = request.getSession().getServletContext();
				         String dir = sc.getRealPath("/ump/cbdwccui/images/");    //设定文件保存的目录
				         
				         String filename = malPic.getOriginalFilename();    //得到上传时的文件名
				         String str=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				         String file=str+filename;
				         File targetFile = new File(dir, file);
				         FileUtils.writeByteArrayToFile(new File(dir,file), malPic.getBytes());
				         malPic.transferTo(targetFile);
				         wccMalfunctions.setMalPic("/ump/ump/cbdwccui/images/"+file);    //设置图片所在路径
	
				         System.out.println(dir+ file);
				     }    
			
			
			WccAppartment wccAppartment=WccAppartment.findPubOrganization(Long.parseLong(itmePk));
			wccMalfunctions.setItemPk(wccAppartment);
			wccMalfunctions.setMalfunctionType(WccMalfunctionType.getById(Long.parseLong(malfunctionType)));
			wccMalfunctions.setName(name);
			wccMalfunctions.setPhone(phone);
			wccMalfunctions.setAddress(address);
			wccMalfunctions.setRemark(remark);
			wccMalfunctions.setIsDealed("未处理");
			wccMalfunctions.persist();
		}
		List<WccAppartment> list=WccAppartment.getWccAppartmentByXp(platId,"2");
		uiModel.addAttribute("list", list);
		uiModel.addAttribute("platId", platId);
		return "page/repair";
	}
	
}

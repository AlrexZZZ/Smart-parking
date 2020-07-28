package com.nineclient.cbd.wcc.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nineclient.cbd.wcc.model.CbdWccProprietor;
import com.nineclient.cbd.wcc.model.WccAppartment;
import com.nineclient.cbd.wcc.model.WccImgSave;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.utils.CommonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/pageProprietor")
@Controller
@RooWebScaffold(path = "cbdwcc_pages/wccproprietor", formBackingObject = WccRecordMsg.class)
public class PagesProprietorController {
	private final Logger log = LoggerFactory.getLogger(PagesProprietorController.class);
	/**
	 * 
	 * 
	 * @param 
	 * @return
	 */
	
	@RequestMapping(value = "proprietor_web", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String pageProprietor(HttpServletRequest request,Model uiModel,
			HttpServletResponse response,
			@RequestParam(value = "friendId", required = false) String friendId,
			@RequestParam(value = "platId", required = false) String platId) {
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		if(StringUtils.isEmpty(friendId))
		{
			return null;
			
		}
		else 
		{
			WccFriend friend=WccFriend.findWccFriend(Long.parseLong(friendId));
			List<CbdWccProprietor> list=CbdWccProprietor.getProprietorForList(Long.parseLong(friendId));
			Set<WccPlatformUser> setp=new HashSet<WccPlatformUser>();
			setp.add(friend.getPlatformUser());
			List<WccAppartment> applist=WccAppartment.getWccAppartmentByXp(platId,"2");
			uiModel.addAttribute("friendId",friendId);
			if(applist==null)
			{
				applist=new ArrayList<WccAppartment>();
			}
			uiModel.addAttribute("apartment", applist);
			if(list!=null&&list.size()>0)
			{
				uiModel.addAttribute("isensured","1");
				uiModel.addAttribute("proprietor", list.get(0));			
			}
			else 
			{
				uiModel.addAttribute("isensured","0");
			}
		    List<WccImgSave> wcc=WccImgSave.findBytype(Long.parseLong(platId), "2");
			if(wcc.size()>0){
				  uiModel.addAttribute("wcc", wcc.get(0));
			}
			return "page/proprietor_web";
		}
		
	  
	}
	/**
	 * 绑定业主信息
	 * @param request
	 * @param response
	 * @param friendId
	 * @param name
	 * @param phone
	 * @param itempk
	 * @param doorplate
	 * @return
	 */
	@RequestMapping(value = "/bindprop", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String bindProb(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "friendId", required = false) Long friendId,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "itempk", required = false) Long itempk,
			@RequestParam(value = "doorplate", required = false) String doorplate)
	{
		String msg="";
//		PubOperator  user = CommonUtils.getCurrentPubOperator(request);
//		UmpCompany company = user.getCompany();
		try 
		{
			WccAppartment appartment=WccAppartment.findWccAppartmentById(itempk);
			List<CbdWccProprietor> list=CbdWccProprietor.getProprietorsList(name, phone, appartment, doorplate);
			WccFriend wccFriend=WccFriend.findWccFriend(friendId);
			if(list!=null&&list.size()>0&&wccFriend!=null)
			{
				for(CbdWccProprietor cbd:list){
					if(cbd.getIsEnsured()=="1" || cbd.getIsEnsured().equals("1")){
						msg="绑定失败,该信息已被认证";
						return msg;
					}
				}
				CbdWccProprietor wccProprietor=list.get(0);
				if(wccProprietor.getWccFriend()==null || wccProprietor.getWccFriend().equals("")){
					wccProprietor.setWccFriend(wccFriend);
					wccProprietor.setIsEnsured("1");
					wccProprietor.setCertificationTime(new Date());
					wccFriend.merge();
					msg="绑定成功";
					
				}else{
					msg="绑定失败,没有该业主信息";
				}
			}
			else{
				msg="绑定失败,没有该业主信息";
			}
		} catch (Exception e)
		{
			log.error("绑定业主信息失败");
			msg="绑定失败";
		}		
		return msg;
	}
	
	/**
	 * 更新业主信息
	 * @param request
	 * @param response
	 * @param friendId
	 * @param name
	 * @param phone
	 * @param itempk
	 * @param doorplate
	 * @return
	 */
	@RequestMapping(value = "/updateprop", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String updateProb(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "friendId", required = false) Long friendId,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "itempk", required = false) Long itempk,
			@RequestParam(value = "doorplate", required = false) String doorplate)
	{
		String msg="";
		try 
		{
			WccAppartment appartment=WccAppartment.findWccAppartmentById(itempk);
			WccFriend wccFriend=WccFriend.findWccFriend(friendId);
			List<CbdWccProprietor> list=CbdWccProprietor.getProprietorForList(friendId);
			
			if(list!=null&&list.size()>0&&wccFriend!=null)
			{
				CbdWccProprietor wccProprietor=list.get(0);
				wccProprietor.setTempappartment(appartment);
				wccProprietor.setTempname(name);
				wccProprietor.setTempdoorplate(doorplate);
				wccProprietor.setTempphone(phone);
				wccFriend.merge();
				msg="修改已提交,正在审核中！";
			}
			
		} catch (Exception e)
		{
			log.error("修改业主信息失败");
			msg="修改失败";
		}		
		return msg;
	}
	@RequestMapping(value = "/chaxun", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String chaxun(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "friendId", required = false) Long friendId,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "itempk", required = false) Long itempk
		)
	{
//		PubOperator  user = CommonUtils.getCurrentPubOperator(request);
//		UmpCompany company = user.getCompany();
			WccAppartment appartment=WccAppartment.findWccAppartmentById(itempk);
			String str  = null;
		if(name!=null && !name.equals("") && phone!=null && !phone.equals("")){
			List<CbdWccProprietor> list=CbdWccProprietor.getProprietorsList(name, phone, appartment, null);
			if(list.size()>0){
			 str="[";
			for(CbdWccProprietor cbd:list){
				str+="{\"doorplate\":\""+cbd.getDoorplate()+"\"},";
			}
			System.out.println(str.substring(0, str.length()-1)+"]");
			str = str.substring(0, str.length()-1)+"]";
			}
			
		}
		return str;
	}
}

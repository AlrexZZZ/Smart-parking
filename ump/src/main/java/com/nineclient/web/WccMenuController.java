package com.nineclient.web;
import com.nineclient.constant.WccMaterialsType;
import com.nineclient.model.PubOperator;
import com.nineclient.model.WccContent;
import com.nineclient.model.WccLotteryActivity;
import com.nineclient.model.WccMaterials;
import com.nineclient.model.WccMenu;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Emotion;
import com.nineclient.utils.Global;
import com.nineclient.utils.WeiXinPubReturnCode;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.bean.WxMenu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/wccmenus")
@Controller
@RooWebScaffold(path = "wccmenus", formBackingObject = WccMenu.class)
public class WccMenuController {
	private final int ONE_MENU = 3;
	private final int SONG_MENU = 5;
	private static Map<String, String> map = new HashMap<String, String>();
	/**
	 * 添加菜单
	 * @param wccMenu
	 * @param bindingResult
	 * @param uiModel
	 * @param request
	 * @param pId  
	 * @param status
	 * @return
	 */
    @RequestMapping(value="/addMeun", method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WccMenu wccMenu, BindingResult bindingResult, Model uiModel,
    		HttpServletRequest request,
    		@RequestParam(value = "pId", required = false) Long pId,
    		@RequestParam(value = "status", required = false) Integer status) {
       if(null == status){
    	   uiModel.addAttribute("pId", pId);
    	 //  uiModel.addAttribute("platId", wccMenu.getPlatformUser().getId());
		   uiModel.addAttribute("status", status);
    	   return "wccmenus/create";
       }
       if(null == pId){
    	   uiModel.addAttribute("pId", pId);
    	 //  uiModel.addAttribute("platId", wccMenu.getPlatformUser().getId());
		   uiModel.addAttribute("status", status);
    	   return "wccmenus/create"; 
       }
       /**
        * 根据公众判断菜单名称是否存在
        */
      /* List<WccMenu>  menuExist = WccMenu.findAllWccMenusByMenuName(wccMenu.getMenuName(),wccMenu.getPlatformUser());
       if(null != menuExist && menuExist.size() > 0){
    	   uiModel.addAttribute("pId", pId);
    	   uiModel.addAttribute("platId", wccMenu.getPlatformUser().getId());
		   uiModel.addAttribute("status", status);
		   uiModel.addAttribute("msg", "一级菜单最多只能创建三个");
    	   return "wccmenus/create"; 
       }*/
       PubOperator pub = CommonUtils.getCurrentPubOperator(request);
       if(status == 1){//判断一级菜单
    	   List<WccMenu> wccMenus = WccMenu.findAllWccMenusByPid(wccMenu.getPlatformUser(),pub);
			if(wccMenus != null && wccMenus.size() >= ONE_MENU){
				uiModel.addAttribute("pId", pId);
			    uiModel.addAttribute("status", status);
				uiModel.addAttribute("msg", "一级菜单最多只能创建三个");
				return "wccmenus/create";
			}
			/**
			 * 如果是一级菜单只能根据公众平台判断，
			 * 所以父id设为0
			 */
			wccMenu.setParentId(0);	
		}else if(status == 2){//判断二级菜单
			List<WccMenu> wccMenus = WccMenu.findAllWccMenusByPrentId(pId,pub);
			if(wccMenus != null && wccMenus.size() >= SONG_MENU){
				uiModel.addAttribute("pId", pId);
			    uiModel.addAttribute("status", status);
				uiModel.addAttribute("msg", "二级菜单最多只能创建五个");
				return "wccmenus/create";
			}
			wccMenu.setParentId(pId);
		}
       
       // 如果类型是文本，且内容不为空，则为文本，如果类型不为文本，则调用素材
       if(wccMenu.getContentSelect() != 0){
	    	if (wccMenu.getContentSelect() == 1){
	    	} else if (wccMenu.getContentSelect() != 1) {
	    			// 判断是会含有图片或图文
	    		wccMenu.setContent("");
	    		String materialStr = request.getParameter("selectId");
	    		String material = materialStr.substring(4, materialStr.length());
	    		WccMaterials mate = WccMaterials.findWccMaterials(Long.parseLong(material));
	    		wccMenu.setMaterials(mate);
	    	}
       }
       
       wccMenu.setCreateTime(new Date());
       wccMenu.setIsDeleted(true);
       wccMenu.setCompanyId(pub.getCompany().getId());
       if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccMenu);
            return "wccmenus/create";
        }
        uiModel.asMap().clear();
        wccMenu.persist();
        return "redirect:/wccmenus?page=1&amp;size=10";
    }

    @RequestMapping(value = "/createForm",method=RequestMethod.GET, produces = "text/html")
    public String createForm(Model uiModel,@RequestParam(value = "pId", required = false) Long pId,
    		@RequestParam(value = "status", required = false) int status,
    		@RequestParam(value = "platId", required = false) Long platId,
    		HttpServletRequest request) {
        populateEditForm(uiModel, new WccMenu());
        uiModel.addAttribute("pId", pId);
        uiModel.addAttribute("status", status);
        uiModel.addAttribute("platId", platId);
       
        return "wccmenus/create";
    }

    @RequestMapping(value = "/materialsList", produces = "text/html")
    public String materialsList(
    		Model uiModel,
    		HttpServletRequest request,
    		@RequestParam(value = "platformUserId", required = false) Long platformUserId) {
    	 WccPlatformUser wccplatform = null;
         if(!StringUtils.isEmpty(platformUserId)){
         	wccplatform = WccPlatformUser.findWccPlatformUser(platformUserId);
         }
         
         List<Emotion> emotions = new ArrayList<Emotion>();
         /**
          * 加载头像
          */
 	    for (String key : map.keySet()) {
 			Emotion emotion = new Emotion();
 			emotion.setContent(key);
 			emotion.setUrl(map.get(key));
 			emotions.add(emotion);
 		}
 		 uiModel.addAttribute("platformUserId", platformUserId);
 		uiModel.addAttribute("emotions", emotions);
    	 uiModel.addAttribute("wccPicture", WccMaterials.findWccMaterialsesByTypeAndPlatform(WccMaterialsType.PICTURE.ordinal(),CommonUtils.getCurrentCompanyId(request),wccplatform));
 		 uiModel.addAttribute("wccImageText", WccMaterials.findWccMaterialsesByTypeAndPlatform(WccMaterialsType.IMAGETEXT.ordinal(),CommonUtils.getCurrentCompanyId(request),wccplatform));
 		 uiModel.addAttribute("wccSounds", WccMaterials.findWccMaterialsesByTypeAndPlatform(WccMaterialsType.SOUNDS.ordinal(),CommonUtils.getCurrentCompanyId(request),wccplatform));
 		 uiModel.addAttribute("wccVideo", WccMaterials.findWccMaterialsesByTypeAndPlatform(WccMaterialsType.VIDEO.ordinal(),CommonUtils.getCurrentCompanyId(request),wccplatform));
         return "wccmenus/show";
    }

    
    @RequestMapping(value = "/materialsListForUpdate", produces = "text/html")
    public String materialsListForUpdate(
    		Model uiModel,
    		HttpServletRequest request,
    		@RequestParam(value = "id", required = false) Long id,
    		@RequestParam(value = "platformUserId", required = false) Long platformUserId) {
    	 WccPlatformUser wccplatform = null;
         if(!StringUtils.isEmpty(platformUserId)){
         	wccplatform = WccPlatformUser.findWccPlatformUser(platformUserId);
         }
         
         List<Emotion> emotions = new ArrayList<Emotion>();
         /**
          * 加载头像
          */
 	    for (String key : map.keySet()) {
 			Emotion emotion = new Emotion();
 			emotion.setContent(key);
 			emotion.setUrl(map.get(key));
 			emotions.add(emotion);
 		}
 	     populateEditForm(uiModel, WccMenu.findWccMenu(id));
 		 uiModel.addAttribute("platformUserId", platformUserId);
 		 uiModel.addAttribute("emotions", emotions);
    	 uiModel.addAttribute("wccPicture", WccMaterials.findWccMaterialsesByTypeAndPlatform(WccMaterialsType.PICTURE.ordinal(),CommonUtils.getCurrentCompanyId(request),wccplatform));
 		 uiModel.addAttribute("wccImageText", WccMaterials.findWccMaterialsesByTypeAndPlatform(WccMaterialsType.IMAGETEXT.ordinal(),CommonUtils.getCurrentCompanyId(request),wccplatform));
 		 uiModel.addAttribute("wccSounds", WccMaterials.findWccMaterialsesByTypeAndPlatform(WccMaterialsType.SOUNDS.ordinal(),CommonUtils.getCurrentCompanyId(request),wccplatform));
 		 uiModel.addAttribute("wccVideo", WccMaterials.findWccMaterialsesByTypeAndPlatform(WccMaterialsType.VIDEO.ordinal(),CommonUtils.getCurrentCompanyId(request),wccplatform));
         return "wccmenus/showUpdate";
    }
    
    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page,
    		HttpServletRequest httpServletRequest,
    		@RequestParam(value = "size", required = false) Integer size,
    		@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
    		@RequestParam(value = "sortOrder", required = false) String sortOrder,
    		Model uiModel,
    		@RequestParam(value = "displayId", required = false) Long displayId) {
    	if (page != null || size != null) {
           // int sizeNo = size == null ? 10 : size.intValue();
           // final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("wccmenus", WccMenu.findWccMenuEntries(sortFieldName, sortOrder,CommonUtils.getCurrentPubOperator(httpServletRequest)));
            //float nrOfPages = (float) WccMenu.countWccMenus(CommonUtils.getCurrentPubOperator(httpServletRequest)) / sizeNo;
            //uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("wccmenus", WccMenu.findAllWccMenus(sortFieldName, sortOrder,CommonUtils.getCurrentPubOperator(httpServletRequest)));
        }
    	uiModel.addAttribute("wccPlatForms", WccPlatformUser.findAllWccPlatformUsers(CommonUtils.getCurrentPubOperator(httpServletRequest)));
    	if(displayId == null){
    		displayId = CommonUtils.getCurrentDisPlayId(httpServletRequest);
        }
        httpServletRequest.getSession().setAttribute("displayId", displayId);
    	return "wccmenus/list";
    }

    /**
     * 修改
     * @param wccMenu
     * @param bindingResult
     * @param uiModel
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value="/update",method = RequestMethod.POST, produces = "text/html")
    public String update(@Valid WccMenu wccMenu, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) {
    	WccMenu wccMenus = WccMenu.findWccMenu(wccMenu.getId());
    	PubOperator pub = CommonUtils.getCurrentPubOperator(httpServletRequest);
    	List<WccMenu> wccMenuSize = null;
    	if(wccMenu.getType() == 1){
 
    		wccMenus.setType(1);
    		wccMenus.setUrl("");
        	wccMenus.setMkey(null);
    	}else if(wccMenu.getType() == 2){
    		int index_ = wccMenu.getContent().lastIndexOf(",");
       		if(wccMenu.getContent() != null && index_ >= 0){
       		 wccMenu.setContent(wccMenu.getContent().substring(0, index_));
       		}
    		wccMenuSize = WccMenu.findAllWccMenusByPrentId(wccMenu.getId(),pub);
    		if(wccMenuSize != null && wccMenuSize.size() > 0){
    			populateEditForm(uiModel, wccMenu);
				uiModel.addAttribute("msg", "一级菜单有二级菜单无法修改成二级菜单！");
				return "wccmenus/update";
			}
    		wccMenus.setType(2);
    		wccMenus.setUrl("");
        	wccMenus.setMkey(wccMenu.getMkey());
    	}else if(wccMenu.getType() == 3){
    		wccMenuSize = WccMenu.findAllWccMenusByPrentId(wccMenu.getId(),pub);
    		if(wccMenuSize != null && wccMenuSize.size() > 0){
    			populateEditForm(uiModel, wccMenu);
				uiModel.addAttribute("msg", "一级菜单有二级菜单无法修改成二级菜单！");
				return "wccmenus/update";
			}
    		wccMenus.setType(3);
    		wccMenus.setUrl(wccMenu.getUrl());
        	wccMenus.setMkey(null);
    	}
    	wccMenus.setFlag(wccMenu.getFlag());
    	wccMenus.setSort(wccMenu.getSort());
    	wccMenus.setMenuName(wccMenu.getMenuName());
    	wccMenus.setRemark(wccMenu.getRemark());
    	wccMenus.setContentSelect(wccMenu.getContentSelect());
    	// 如果类型是文本，且内容不为空，则为文本，如果类型不为文本，则调用素材
    	if(wccMenu.getType() != 1){
	        if(wccMenu.getContentSelect() != 0){
	 	    	if (wccMenu.getContentSelect() == 1){
	 	    		wccMenus.setMaterials(null);
	 	    		wccMenus.setContent(wccMenu.getContent());
	 	    	} else if (wccMenu.getContentSelect() != 1) {
	 	    	// 判断是会含有图片或图文
 	 	    		wccMenus.setContent("");
 	 	    		String materialStr = httpServletRequest.getParameter("selectId");
 	 	    		String material = materialStr.substring(4, materialStr.length());
 	 	    		WccMaterials mate = WccMaterials.findWccMaterials(Long.parseLong(material));
 	 	    		wccMenus.setMaterials(mate);
	 	    	}
	        }
    	}
    	if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccMenu);
            return "wccmenus/update";
        }
        uiModel.asMap().clear();
        wccMenus.merge();
        return "redirect:/wccmenus?page=1&amp;size=10";
    }

    @RequestMapping(value = "/updateForm", method=RequestMethod.GET, produces = "text/html")
    public String updateForm(@RequestParam(value = "id", required = false) Long id,
    		@RequestParam(value = "status", required = false) Integer status,
    		@RequestParam(value = "platId", required = false) Long platId,
    		Model uiModel, HttpServletRequest request) {
        populateEditForm(uiModel, WccMenu.findWccMenu(id));
        uiModel.addAttribute("status", status);
        uiModel.addAttribute("platId", platId);
        /*WccPlatformUser wccplatform = null;
        if(!StringUtils.isEmpty(platId)){
        	wccplatform = WccPlatformUser.findWccPlatformUser(platId);
        }
        uiModel.addAttribute("wccPicture", WccMaterials.findWccMaterialsesByTypeAndPlatform(WccMaterialsType.PICTURE.ordinal(),CommonUtils.getCurrentCompanyId(request),wccplatform));
		uiModel.addAttribute("wccImageText", WccMaterials.findWccMaterialsesByTypeAndPlatform(WccMaterialsType.IMAGETEXT.ordinal(),CommonUtils.getCurrentCompanyId(request),wccplatform));
		uiModel.addAttribute("wccSounds", WccMaterials.findWccMaterialsesByTypeAndPlatform(WccMaterialsType.SOUNDS.ordinal(),CommonUtils.getCurrentCompanyId(request),wccplatform));
		uiModel.addAttribute("wccVideo", WccMaterials.findWccMaterialsesByTypeAndPlatform(WccMaterialsType.VIDEO.ordinal(),CommonUtils.getCurrentCompanyId(request),wccplatform));
		List<Emotion> emotions = new ArrayList<Emotion>();
        
	    for (String key : map.keySet()) {
			Emotion emotion = new Emotion();
			emotion.setContent(key);
			emotion.setUrl(map.get(key));
			emotions.add(emotion);
		}
		uiModel.addAttribute("emotions", emotions);*/
        return "wccmenus/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        WccMenu wccMenu = WccMenu.findWccMenu(id);
        wccMenu.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/wccmenus";
    }

    void populateEditForm(Model uiModel, WccMenu wccMenu) {
        uiModel.addAttribute("wccMenu", wccMenu);
    }

    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {
        }
        return pathSegment;
    }
    
	/**
	 * 删除查询是否有二级菜单
	 * @param uiModel
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getAddTreeMsg",produces="text/html;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String getAddTreeMsg(Model uiModel,long pId,int status,HttpServletRequest request, HttpServletResponse response){
		String str = "{msg:\'faile2\'}";//查询失败
		if(pId > 0 && status > 0){
			if(status == 1){
				List<WccMenu> wccMenus = WccMenu.findAllWccMenusBypId(pId,CommonUtils.getCurrentPubOperator(request));
				if(wccMenus != null && wccMenus.size() > 0){
					str = "{msg:\'faile\'}";//含有二级菜单
				}else{
					str = "{msg:\'success\'}";//没有二级菜单
				}
			}
		}
		return str;
	}
	
	/**
	 * 添加菜单根据公众号判断菜单名称是否存在
	 * @param uiModel
	 * @param name
	 * @param platId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addTreeByPid",produces="text/html;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String addTreeByPid(Model uiModel,
			String name,
			long platId,
			HttpServletRequest request, HttpServletResponse response){
		String str = "";
		if(null != name && platId > 0){
			List<WccMenu> wccMenus = null;
			WccPlatformUser wccPlatForm = WccPlatformUser.findWccPlatformUser(platId);
			if(null != wccPlatForm){
				wccMenus = WccMenu.findAllWccMenusByMenuName(name,wccPlatForm);
			}
			if(null !=wccMenus && wccMenus.size() > 0){
				 str = "{msg:\'faile\'}";//菜单名存在
				return str;
			}
			 str = "{msg:\'success\'}";//不存在
			return str;
		}
		str = "{msg:\'faile2\'}";//查询失败
		return str;
	}
	
	/**
	 * 删除节点
	 * 如果删除父节点，连子节点都删除。
	 * @param uiModel
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteTreeByIdOrIds",produces="text/html;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String deleteTreeByIdOrIds(Model uiModel,long id,int status,HttpServletRequest request, HttpServletResponse response){
		WccMenu wccMes = WccMenu.findWccMenu(id);
		String str = "{msg:\'faile\'}";
		if(status > 0 && status == 2){
			wccMes.remove();
			//沒有存在子節點直接刪除
			str = "{msg:\'success\'}";
		}else if(status > 0 && status == 1){
			List<WccMenu> wccMenus = WccMenu.findAllWccMenusById(id,false);
			if(wccMenus != null && wccMenus.size() > 0){
				for (WccMenu wccMe : wccMenus) {
					wccMe.remove();//循環刪除pid是传进来的id。
				}
			}
			//WccMenu wccMes = WccMenu.findWccMenu(id);
			wccMes.remove();
			str = "{msg:\'success\'}";//沒有存在子節點直接刪除
		}
		return str;
	}
	
	/**
	 * 提交菜单
	 * @param platId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/commitMeun",produces="text/html;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String commitMeun( @RequestParam(value = "platId", required = false) Long platId
			,HttpServletRequest request){
		
		String flag = "3";
		String DOMAIN = Global.Url;
		List<WxMenuButton> list = new ArrayList<WxMenuButton>();
		WxMenu wxmenu = new WxMenu();
		List<WccMenu> menus =  WccMenu.findWccMenuById(platId);//获取平台下所有菜单
		WccPlatformUser platformUser = WccPlatformUser.findWccPlatformUser(platId);// 根据公众平台ID查询公众平台
		List<WxMenuButton> childMenus = null;
		List<WccMenu> menues = null;
		JSONObject json = null;
		if(null != menus && menus.size() > 0 ){
			//循环该平台下所有一级菜单
			for (WccMenu oneMenu : menus) {
				WxMenuButton menu = new WxMenuButton();//存储要提交的一级菜单按钮
					menu.setName(oneMenu.getMenuName());
					if (oneMenu.getType() == 2) {
						// click类型点击回复
						menu.setKey(oneMenu.getMkey());
						menu.setType("click");
					}
					else if(oneMenu.getType() == 3){
						//url跳转类型
						StringBuffer urls = new StringBuffer();
						if(oneMenu.getUrl().contains("ump/wcclotteryactivitys/showActivity?id=")){
							String[] url = oneMenu.getUrl().split("\\?");
							urls.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=")
							.append(oneMenu.getPlatformUser().getAppId())
							.append("&redirect_uri="+url[0])
							.append("&response_type=code&scope=snsapi_base&state=")
							.append(url[1].split("=")[1])
							.append("-"+oneMenu.getPlatformUser().getId())
							.append("#wechat_redirect");
						}else{
							// 注释一下代码不走 回调方法中的 dispatch 方法
//							urls.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=")
//							.append(oneMenu.getPlatformUser().getAppId())
//							.append("&redirect_uri="+DOMAIN+"/ump/wxController/dispacher")
//							.append("&response_type=code&scope=snsapi_base&state=")
//							.append(oneMenu.getId())
//							.append("1")  //因为state参数只能为数字 、字母 1：表示菜单  2：表示素材
//							.append("#wechat_redirect");
//							System.out.println(urls.toString());
							urls.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=")
							.append(oneMenu.getPlatformUser().getAppId())
							.append("&redirect_uri="+DOMAIN+"/ump/wxController/dispacher")
							.append("&response_type=code&scope=snsapi_base&state=")
							.append(oneMenu.getId())
							.append("1")  //因为state参数只能为数字 、字母 1：表示菜单  2：表示素材
							.append("#wechat_redirect");
							System.out.println(urls.toString());
							if(oneMenu.getUrl().length()>0){
								urls.append(oneMenu.getUrl());
							}else{
								urls.append("");
							}
						}
						menu.setType("view");
						oneMenu.setUrl(urls.toString());
						menu.setUrl(oneMenu.getUrl());
					}
					
					else if(oneMenu.getType() == 1){
						//menu.setName(oneMenu.getMenuName());
						//存放子菜单
						childMenus = new ArrayList<WxMenuButton>();
						//循环子菜单
						menues =  WccMenu.findWccMenuByParentId(oneMenu.getId());
						if(null != menues && menues.size() > 0){
							//添加二级菜单
							for (WccMenu sonMenu : menues) {
								StringBuffer urls = new StringBuffer();
								if(sonMenu.getUrl()!=null){
									if(sonMenu.getUrl().contains("ump/wcclotteryactivitys/showActivity?id=")){
										String[] url = sonMenu.getUrl().split("\\?");
										urls.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=")
										.append(oneMenu.getPlatformUser().getAppId())
										.append("&redirect_uri="+url[0])
										.append("&response_type=code&scope=snsapi_base&state=")
										.append(url[1].split("=")[1])
										.append("-"+oneMenu.getPlatformUser().getId())
										.append("#wechat_redirect");
									}else{
										//注释一下不走回调函数中的 dispatch方法
//										urls.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=")
//										.append(oneMenu.getPlatformUser().getAppId())
//										.append("&redirect_uri="+DOMAIN+"/ump/wxController/dispacher")
//										.append("&response_type=code&scope=snsapi_base&state=")
//										.append(sonMenu.getId())
//										.append("1")  //因为state参数只能为数字 、字母 1：表示菜单 2：表示素材
//										.append("#wechat_redirect");
										
										urls.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=")
										.append(oneMenu.getPlatformUser().getAppId())
										.append("&redirect_uri="+DOMAIN+"/ump/wxController/dispacher")
										.append("&response_type=code&scope=snsapi_base&state=")
										.append(sonMenu.getId())
										.append("1")  //因为state参数只能为数字 、字母 1：表示菜单 2：表示素材
										.append("#wechat_redirect");
										if(sonMenu.getUrl().length()>0){
											if(sonMenu.getUrl().contains("?")){
												urls.append(sonMenu.getUrl()+"&platId="+sonMenu.getPlatformUser().getId());
											}else{
												urls.append(sonMenu.getUrl()+"?platId="+sonMenu.getPlatformUser().getId());
												
											}
										System.out.println(urls);	
										}else{
											urls.append("");
										}
									}
								}
									WxMenuButton sendMenu = new WxMenuButton();
									sendMenu.setName(sonMenu.getMenuName());
									sendMenu.setKey(sonMenu.getMkey());
									if(sonMenu.getType() == 2){
										sendMenu.setType("click");
									}else if(sonMenu.getType() == 3){
										sendMenu.setType("view");
										sendMenu.setUrl(urls.toString());
									}
									childMenus.add(sendMenu);
								}
							}else{
								return WeiXinPubReturnCode.pubReturnCode("3");
							}
						menu.setSubButtons(childMenus);
						}
					list.add(menu);
				}
			wxmenu.setButtons(list);
		}
		if(null !=list && list.size() > 0){
			// 调用微信接口
			WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
			String appId = platformUser.getAppId();
			String appSecret = platformUser.getAppSecret();
			config.setAppId(appId);
			config.setSecret(appSecret);
			WxMpServiceImpl wxMpService = new WxMpServiceImpl();
			wxMpService.setWxMpConfigStorage(config);
			/**
			 * 提交菜单先删除微信那边菜单。再重新提交。
			 */
			try {
				wxMpService.menuDelete();
			} catch (WxErrorException e1) {
				e1.printStackTrace();
				json = JSONObject.fromObject(e1.getError());
			}
			try{
			wxMpService.menuDelete();//有时候一次清不掉，再重新调用次
				// 设置菜单
				wxMpService.menuCreate(wxmenu);	
				flag = WeiXinPubReturnCode.pubReturnCode("1");;
			} catch (WxErrorException e) {
				e.printStackTrace();
				json = JSONObject.fromObject(e.getError());
				flag = WeiXinPubReturnCode.pubReturnCode(String.valueOf(json.get("errorCode")));
			}
		}else{
			flag = WeiXinPubReturnCode.pubReturnCode("0");
		}
		return flag;
	}
	
	/**
	 * 注销菜单
	 * @param menuName
	 * @return
	 */
	@RequestMapping(value = "/deleteMenu",produces="text/html;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String deleteMenu( @RequestParam(value = "platId", required = false) Long platId){
		boolean flag = true;
		String msg = "删除成功";
		JSONObject json = null;
		WccPlatformUser platformUser = new WccPlatformUser();
		platformUser = WccPlatformUser.findWccPlatformUser(platId);// 根据公众平台ID查询公众平台
		// 调用微信接口
		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		String appId = platformUser.getAppId();
		String appSecret = platformUser.getAppSecret();
		config.setAppId(appId);
		config.setSecret(appSecret);
		WxMpServiceImpl wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(config);
		try{
			// 删除菜单
			wxMpService.menuDelete();
			flag = true;
		} catch (WxErrorException e) {
			e.printStackTrace();
			json = JSONObject.fromObject(e.getError());
			msg = WeiXinPubReturnCode.pubReturnCode(String.valueOf(json.get("errorCode")));
			flag = false;
		}
		/**
		 * 判断微信那边删除是否成功。
		 * 成功则删除本地菜单。
		 */
		if(flag){
			List<WccMenu> menus =  WccMenu.findWccMenuById(platId);
			List<WccMenu> menues = null;
			for (WccMenu wccMenu : menus) {
				menues =  WccMenu.findWccMenuByParentId(wccMenu.getId());
				if(null != menues && menues.size() > 0){
					for (WccMenu wccMe : menues) {
						 wccMe.remove();
					}
				}
				wccMenu.remove();
			}
		}
		return msg;
	}
	
	/**
	 * 根据公众号判断key是否存在
	 * @param mkey
	 * @param platId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkKeyExits",produces="text/html;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String checkKeyExits(
			@RequestParam(value = "mkey", required = false) String mkey,
			@RequestParam(value = "platId", required = false) Long platId,
			HttpServletRequest request){
		String str = "{msg:\'success\'}";//查询失败
		WccPlatformUser wccPlat = WccPlatformUser.findWccPlatformUser(platId);
		List<WccMenu> wccMenus = WccMenu.findAllWccMenusByKeyExits(mkey,wccPlat);
		if(wccMenus != null && wccMenus.size() > 0){
			str = "{msg:\'faile\'}";
		}
		return str;
	}
	
	@RequestMapping(value = "/checkUrlById",produces="text/html;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String checkUrlById(
			@RequestParam(value = "id", required = false) Long id,
			HttpServletRequest request){
		PubOperator pub = CommonUtils.getCurrentPubOperator(request);
		JSONArray json = null;
		int limit = 50;
		Emotion menuContent = null; 
		List<Emotion> list = new ArrayList<Emotion>();
		if(null != id && !"".equals(id)){
			if(id == 1){//调用微内容
				String url = Global.Url+"/ump/wxController/showContents?contentId=";
				List<WccContent> wccContentList = WccContent.findAllWccContents(pub.getCompany().getId(),limit);
				for (WccContent wccContent : wccContentList) {
					menuContent = new Emotion();
					menuContent.setContent(wccContent.getTitle());
					menuContent.setUrl(url+wccContent.getId());
					list.add(menuContent);
				}
				json = JSONArray.fromObject(list);
			}else if(id == 2){//调用抽奖活动
				String url = Global.Url+"/ump/wcclotteryactivitys/showActivity?id=";
				List<WccLotteryActivity> wccLotteryList = WccLotteryActivity.findAllWccLotteryActivitys(pub.getCompany(),limit,new Date());
				for (WccLotteryActivity wccLottery : wccLotteryList) {
					menuContent = new Emotion();
					menuContent.setContent(wccLottery.getActivityName());
					menuContent.setUrl(url+wccLottery.getId());
					list.add(menuContent);
				}
				json = JSONArray.fromObject(list);
			}
		}
		return json.toString();
	}
	
	static {
		map.put("/微笑", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/0.gif");
		map.put("/撇嘴", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/1.gif");
		map.put("/色", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/2.gif");
		map.put("/发呆", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/3.gif");
		map.put("/得意", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/4.gif");
		map.put("/流泪", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/5.gif");
		map.put("/害羞", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/6.gif");
		map.put("/闭嘴", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/7.gif");
		map.put("/睡", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/8.gif");
		map.put("/大哭", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/9.gif");
		map.put("/尴尬", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/10.gif");
		map.put("/发怒", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/11.gif");
		map.put("/调皮", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/12.gif");
		map.put("/呲牙", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/13.gif");
		map.put("/惊讶", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/14.gif");
		map.put("/难过", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/15.gif");
		map.put("/酷", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/16.gif");
		map.put("/冷汗", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/17.gif");
		map.put("/抓狂", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/18.gif");
		map.put("/吐", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/19.gif");
		map.put("/偷笑", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/20.gif");
		map.put("/可爱", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/21.gif");
		map.put("/白眼", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/22.gif");
		map.put("/傲慢", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/23.gif");
		map.put("/饥饿", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/24.gif");
		map.put("/困", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/25.gif");
		map.put("/惊恐", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/26.gif");
		map.put("/流汗", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/27.gif");
		map.put("/憨笑", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/28.gif");
		map.put("/大兵", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/29.gif");
		map.put("/奋斗", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/30.gif");
		map.put("/咒骂", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/31.gif");
		map.put("/疑问", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/32.gif");
		map.put("/嘘", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/33.gif");
		map.put("/晕", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/34.gif");
		map.put("/折磨", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/35.gif");
		map.put("/衰", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/36.gif");
		map.put("/骷髅", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/37.gif");
		map.put("/敲打", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/38.gif");
		map.put("/再见", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/39.gif");
		map.put("/擦汗", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/40.gif");
		map.put("/抠鼻", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/41.gif");
		map.put("/鼓掌", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/42.gif");
		map.put("/糗大了", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/43.gif");
		map.put("/坏笑", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/44.gif");
		map.put("/左哼哼", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/45.gif");
		map.put("/右哼哼", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/46.gif");
		map.put("/哈欠", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/47.gif");
		map.put("/鄙视", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/48.gif");
		map.put("/委屈", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/49.gif");
		map.put("/快哭了", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/50.gif");
		map.put("/阴险", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/51.gif");
		map.put("/亲亲", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/52.gif");
		map.put("/吓", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/53.gif");
		map.put("/可怜", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/54.gif");
		map.put("/菜刀", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/55.gif");
		map.put("/西瓜", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/56.gif");
		map.put("/啤酒", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/57.gif");
		map.put("/篮球", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/58.gif");
		map.put("/乒乓", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/59.gif");
		map.put("/咖啡", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/60.gif");
		map.put("/饭", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/61.gif");
		map.put("/猪头", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/62.gif");
		map.put("/玫瑰", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/63.gif");
		map.put("/凋谢", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/64.gif");
		map.put("/示爱", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/65.gif");
		map.put("/爱心", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/66.gif");
		map.put("/心碎", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/67.gif");
		map.put("/蛋糕", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/68.gif");
		map.put("/闪电", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/69.gif");
		map.put("/炸弹", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/70.gif");
		map.put("/刀", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/71.gif");
		map.put("/足球", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/72.gif");
		map.put("/瓢虫", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/73.gif");
		map.put("/便便", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/74.gif");
		map.put("/月亮", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/75.gif");
		map.put("/太阳", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/76.gif");
		map.put("/礼物", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/77.gif");
		map.put("/拥抱", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/78.gif");
		map.put("/强", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/79.gif");
		map.put("/弱", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/80.gif");
		map.put("/握手", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/81.gif");
		map.put("/胜利", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/82.gif");
		map.put("/抱拳", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/83.gif");
		map.put("/勾引", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/84.gif");
		map.put("/拳头", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/85.gif");
		map.put("/差劲", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/86.gif");
		map.put("/爱你", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/87.gif");
		map.put("/NO", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/88.gif");
		map.put("/OK", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/89.gif");
		map.put("/爱情", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/90.gif");
		map.put("/飞吻", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/91.gif");
		map.put("/跳跳", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/92.gif");
		map.put("/发抖", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/93.gif");
		map.put("/怄火", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/94.gif");
		map.put("/转圈", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/95.gif");
		map.put("/磕头", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/96.gif");
		map.put("/回头", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/97.gif");
		map.put("/跳绳", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/98.gif");
		map.put("/挥手", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/99.gif");
		map.put("/激动", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/100.gif");
		map.put("/街舞", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/101.gif");
		map.put("/献吻", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/102.gif");
		map.put("/左太极", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/103.gif");
		map.put("/右太极", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/104.gif");
	}
}

package com.nineclient.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.Check;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import com.nineclient.model.PubOperator;
import com.nineclient.model.PubOrganization;
import com.nineclient.model.UmpAuthority;
import com.nineclient.model.UmpOperator;
import com.nineclient.model.VocKeyWord;
import com.nineclient.thread.massMessageTiming;

/**
 * @author 9client 自定义扩展工具类
 *
 */
public class CommonUtils {
	/**
	 * 判断是否有空元素
	 * 
	 * @param arr
	 * @return
	 */
	public static boolean isFullArray(Object[] arr) {
		boolean flag = true;
		for (Object object : arr) {
			if (StringUtils.isEmpty(object) || object.equals("")) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	/**
	 * 判断数组里面是否有重复的元素
	 * 
	 * @return
	 */
	public static boolean isSetArray(String[] arr) {
		if (arr == null || arr.length < 1)
			return true;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (arr[i] == arr[j] && i != j) {
					return false;
				}
			}
		}
		return true;
	}
	
	
 /** 
  * 数组内的元素是否重复
  * @param arr
  * @return
  */
public static boolean isRepeatFromArray(String[] arr){
	  if (arr == null || arr.length < 1)
	    return true;
	
	 Set<String> hashSet = new HashSet<String>();
	  for(String str:arr){
	    hashSet.add(str);
	  }
      if(hashSet.size() != arr.length){
    	  return false;
      }
	  return true; 
  }


/**
 * 关键词是否重复，如果重复 返回 true 否则返回 false
 * @param keyWordName
 * @param keyWordList
 * @return
 */
public static boolean isKeyWordRepeat(String keyWordName, List<VocKeyWord> keyWordList){
 for(VocKeyWord kw:keyWordList){
   if(kw.getName().equals(keyWordName)){
	   return true;
   }
 }
  return false;	
}

	/**
	 * 将以逗号分隔的id字符串生成 Set<Long> 集合
	 * 
	 * @param stringArrayStr
	 * @return
	 */
	public static Set<Long> stringArray2Set(String stringArrayStr) {
		Set<Long> set = new HashSet<Long>();
		for (String str : stringArrayStr.split(",")) {
			set.add(Long.parseLong(str));
		}
		return set;
	}
	public static Set<String> stringArray2Sets(String stringArrayStr) {
		Set<String> set = new HashSet<String>();
		if(!StringUtils.isEmpty(stringArrayStr))
		{
		for (String str : stringArrayStr.split(",")) {
			set.add(str);
		}
		}		
		return set;
	}
   public static String getOrCoinditions(Set<String> pset,String pname,String fieldname,Map<String, String> pmap,String operater)
   {
		int i=0;
		StringBuffer sqlb=new  StringBuffer();
		if(operater.equals("like"))
	      for (String str : pset) {
	    	 if(i==0)
	    	 {
	    		 sqlb.append(" and ("+fieldname+" like :"+pname+i);
	    	 }
	    	 else {
	    		 sqlb.append(" or "+fieldname+" like :"+pname+i);
			}
	    	 if(i==pset.size()-1)
	    	 {
	    		 sqlb.append(") "); 
	    	 }
	    	 pmap.put(pname+i,"%"+str+"%");
	    	 i++;
		     }
		else if("equal".equals(operater)) {
			  for (String str : pset) {
			    	 if(i==0)
			    	 {
			    		 sqlb.append(" and ("+fieldname+" =:"+pname+i);
			    	 }
			    	 else {
			    		 sqlb.append(" or "+fieldname+" =:"+pname+i);
					}
			    	 if(i==pset.size()-1)
			    	 {
			    		 sqlb.append(" ) "); 
			    	 }
			    	 pmap.put(pname+i,str);
			    	 i++;
				     }	
		}
	      return sqlb.toString();
   }
	/**
	 * 将以逗号分隔的id字符串生成 List<Long> 集合
	 * 
	 * @param stringArrayStr
	 * @return
	 */
	public static List<Long> stringArray2List(String stringArrayStr) {
		List<Long> list = new ArrayList<Long>();
		for (String str : stringArrayStr.split(",")) {
			list.add(Long.parseLong(str));
		}
		return list;
	}

	/**
	 * list集合 转换成set集合
	 * 
	 * @param list
	 * @return
	 */
	public static <T> Set<T> list2Set(List<T> list) {
		Set<T> set = new HashSet<T>();
		set.addAll(list);
		return set;
	}

	/**
	 * set 集合转换成list
	 * 
	 * @param set
	 * @return
	 */
	public static <T> List<T> set2List(Set<T> set) {
		List<T> list = new ArrayList<T>();
		list.addAll(set);
		return list;
	}

	/**
	 * 获得当前的用户
	 * 
	 * @param request
	 * @return
	 */
	public static UmpOperator getCurrentOperator(HttpServletRequest request) {
		UmpOperator op = (UmpOperator) request.getSession().getAttribute(
				Global.UMP_LOGIN_OPERATOR);
		return op;
	}

	/**
	 * 获得当前的用户
	 * 
	 * @param request
	 * @return
	 */
	public static PubOperator getCurrentPubOperator(HttpServletRequest request) {
		PubOperator pubOper = (PubOperator) request.getSession().getAttribute(
				Global.PUB_LOGIN_OPERATOR);
		return pubOper;
	}
////获取当前用户组织架构等信息
//	public static String getCurrentPubOrags(HttpServletRequest request)
//	{
//	String returnstr=(String)request.getSession().getAttribute(Global.orgids);
//		return returnstr;
//	}
	/**
	 * 获得当前的公司ID
	 * 
	 * @param request
	 * @return
	 */
	public static Long getCurrentCompanyId(HttpServletRequest request) {
		return ((PubOperator) request.getSession().getAttribute(
				Global.PUB_LOGIN_OPERATOR)).getCompany().getId();
	}

	/**
	 * 获取菜单的Id 方便模块的CRUD选择点击的菜单
	 * 
	 * @param request
	 * @return
	 */
	public static Long getCurrentDisPlayId(HttpServletRequest request) {
		return (Long) request.getSession().getAttribute("displayId");
	}
	public static String getPath() {
		String path = getClassFilePath(CommonUtils.class);
		path = path.substring(0, path.lastIndexOf("classes") + 8);
		return path;
	}
	public static String getClassFilePath(Class<?> clazz) {
		try {
			return java.net.URLDecoder.decode(getClassFile(clazz).getAbsolutePath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	private static File getClassFile(Class<?> clazz) {
		URL path = clazz.getResource(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1) + ".class");
		if (path == null) {
			String name = clazz.getName().replaceAll("[.]", "/");
			path = clazz.getResource("/" + name + ".class");
		}
		return new File(path.getFile());
	}
  public static void getAreares(String areaStr,Set<String> areaList, Map<String, Set<String>> provinceMap,Map<String,Set<String>> cityMap)
  {
	   
		String[] areas=null;
		Set<String> provinceSet=null;
		Set<String> citieSet=null;
		String[] singleare=null;
		String tempArea="";
		String tempProvince="";
		String tempCity="";
		areas=areaStr.split(",");
    	if(null!=areas&&areas.length>0)
    	{
    
   
		 for(int i=0;i<areas.length;i++)
    	 {
    		 singleare=areas[i].split("-");
    		 tempArea=singleare[0];
    		 areaList.add(tempArea);
    		 if(singleare.length>1)
    		 {
    			 tempProvince=singleare[1];
    			if(provinceMap.containsKey(tempArea))
    			{
    				provinceMap.get(tempArea).add(tempProvince);
    			}
    			else
    			{
    				provinceSet=new HashSet<String>();
    				provinceSet.add(tempProvince);
    				provinceMap.put(tempArea, provinceSet);
    			}
    			 
    		 }
    		 if(singleare.length>2)
    		 {
    			 tempCity=singleare[2];
    			if(cityMap.containsKey(tempProvince))
    			{
    				cityMap.get(tempProvince).add(tempCity)	;
    			}
    			else
    			{
    				citieSet=new HashSet<String>();
    				citieSet.add(tempCity);
    				cityMap.put(tempProvince, citieSet);

    			}
    			 
    		 }
    		 
    	 }
    	}
  }
  
  public static  Long getStrToLong(String num)
  {
		Long lsid;
		try{
		lsid=Long.parseLong(num);
		}
		catch(Exception e){
			lsid=null;
		}
		return lsid;
  }
  public static  int getStrToInt(String num)
  {
		int lsid;
		try{
		lsid=Integer.parseInt(num);
		}
		catch(Exception e){
			lsid=-1;
		}
		return lsid;
  }
  public static String getStringDate(Date pdate) {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    
	    String dateString = "";
	    try {
	    	 dateString=formatter.format(pdate);
		} catch (Exception e) {
			
		}
	   
	    return dateString;
	   } 
 
public static  void getPubOrganization(StringBuffer stringBuffer ,List<PubOrganization> list,long pid)
{
	PubOrganization pubog=null;
	if(list!=null&&list.size()>0)
	{
		for(int i=0;i<list.size();i++)
		{
	
		pubog=list.get(i);
    if(pubog.getParentId()==pid)
    {
    	stringBuffer.append(",").append(pubog.getId());    	
    	getPubOrganization(stringBuffer,list,pubog.getId());
    }
		}
	}
}
public static   void  getOrgObject(List<PubOrganization> relist,List<PubOrganization> list,PubOrganization pubOrganization)
{
	PubOrganization pubog=null;
	if(list!=null&&list.size()>0)
	{
		for(int i=0;i<list.size();i++)
		{
	
		pubog=list.get(i);
    if(pubog.getParentId()==pubOrganization.getId())
    {
    	relist.add(pubog);
    	getOrgObject(relist,list,pubog);
    }
		}
	}
}
public static void setTheMassAutho(HttpServletRequest request,Model uiModel)
{
	if(CommonUtils.getCurrentPubOperator(request).getPubRole() == null){
		uiModel.addAttribute("auditMass","编辑审核");
		uiModel.addAttribute("sendAutho","发送");
	}else{
		Map<String, String> massSend = (Map<String, String>) request.getSession().getAttribute("massSend");
		Map<String, String> sendAutho = (Map<String, String>) request.getSession().getAttribute("sendAutho");
		if(null != massSend ){
			UmpAuthority umpMenu = null;
			for (String menuId : massSend.keySet()) {
				umpMenu  = UmpAuthority.findUmpAuthority(Long.parseLong(menuId));
				if(null != umpMenu){
					uiModel.addAttribute("auditMass",massSend.get(menuId));
				}
			}
			//发送权限
			for (String menuId : sendAutho.keySet()) {
				umpMenu  = UmpAuthority.findUmpAuthority(Long.parseLong(menuId));
				if(null != umpMenu){
					uiModel.addAttribute("sendAutho",sendAutho.get(menuId));
				}
			}
		}
	}
	}

//转义参数中的特殊字符
public static String filtParam(String value) {
	if (value == null) {
		return null;
	}
	StringBuffer result = new StringBuffer(value.length());
	for (int i = 0; i < value.length(); ++i) {
		switch (value.charAt(i)) {
		case '<':
			result.append("&lt;");
			break;
		case '>':
			result.append("&gt;");
			break;
		case '"':
			result.append("&quot;");
			break;
		case '\'':
			result.append("&#39;");
			break;
		//			case '%':
		//				result.append("&#37;");
		//				break;
		case ';':
			result.append("&#59;");
			break;
		case '(':
			result.append("&#40;");
			break;
		case ')':
			result.append("&#41;");
			break;
		case '&':
			result.append("&amp;");
			break;
		case '+':
			result.append("&#43;");
			break;
		default:
			result.append(value.charAt(i));
			break;
		}
	}
	return result.toString();
}


//校验参数中是否含有特殊字符
	public static boolean validateParam(String value) {
	if (value == null) {
	return false;
	}
	if(value.contains("%"))
	{
	try {
		value=URLDecoder.decode(value,"UTF-8");
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}	
	}
	for (int i = 0; i < value.length(); ++i) {
	char c = value.charAt(i);
	if (c == '<' || c == '>' || c == '"' || c == '\'' || c == '%' || c == ';' || c == '(' || c == ')'
	|| c == '&' || c == '+') {
	return true;
	}
	}
	return false;
	}
	public static String html(String content) {
		if(content==null) return "";        
		String html = content;
		html = StringUtils.replace(html, ";", "&#59;");
		html = StringUtils.replace(html, "\t", "&nbsp;&nbsp;");// 替换跳格
		html = StringUtils.replace(html, "\\", "&#39;");
		html = StringUtils.replace(html, "'", "&apos;");
		html = StringUtils.replace(html, "\"", "&quot;");		
		html = StringUtils.replace(html, " ", "&nbsp;");// 替换空格
		html = StringUtils.replace(html, "<", "&lt;");
		html = StringUtils.replace(html, ">", "&gt;");				
		html = StringUtils.replace(html, "(", "&#40;");
		html = StringUtils.replace(html, ")", "&#41;");		
		html = StringUtils.replace(html, "+", "&#43;");
		return html;
	}
	public static boolean CheckValues(Map<String,String[]> pMap)
	{
		boolean returnbl=false;
		if(pMap!=null&&pMap.size()>0)
			
		{
			for (Map.Entry<String, String[]> entity : pMap.entrySet()) {
				try {
					if(entity.getValue()[0].startsWith("{\""))
					{
						returnbl=false;
						break;
					}
					if(validateParam(entity.getValue()[0]))
					{
						returnbl= true;
						break;
					}
				} catch (Exception e) {
					
					break;
				}
				
			}
		}
		return returnbl;
		
	}
	
	 
}

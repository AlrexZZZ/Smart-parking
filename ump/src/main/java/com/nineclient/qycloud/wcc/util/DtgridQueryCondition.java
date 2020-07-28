package com.nineclient.qycloud.wcc.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import net.sf.json.JSONObject;

import com.nineclient.model.PubOperator;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;

public class DtgridQueryCondition {
/*
 * @ dtGridPager json 条件查询字符串；
 * @ qm QueryModel 对象；
 * @ request 用来获取公众平台的，如果不需要按照公众平台来查询可以为空
 */
public static PageCondition  makeCondition(String dtGridPager,QueryModel<?> qm,HttpServletRequest request){
	PubOperator user = CommonUtils.getCurrentPubOperator(request);
	//此时page 只赋了起始页的参数
	Pager pager =null;
	Map <String, Object> parm = new HashMap<String, Object>();
	PageCondition conditon = null;
	
	  try {
		pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
		qm.setLimit(pager.getPageSize());
		qm.setStart(pager.getStartRecord());
		if(null != pager){
			parm = pager.getParameters();
		}
		if(parm.size() > 0){
			for(Map.Entry<String, Object> omap:parm.entrySet()){
				// 判断是否重新翻页
				if("restart".equals(omap.getKey())){
					if (omap.getValue().equals("y")) {
						pager.setStartRecord(0);
						pager.setNowPage(1);
					}
				}
			
				qm.putParams(omap.getKey(), omap.getValue());
			}
		}

		// 增加公众平台
		if(null != request){
		
		//增加过滤公司的条件
		qm.putParams("company", user.getCompany().getId());
		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);	
	   String	platFormId = (String) parm.get("platFormId");
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
    	///
    	///
/*    	/// 增加按照权限来增加查询条件start
    	 List<PubOperator> pubList = EmpTreeList.getPubOperList(request);
    	String  pubIds = "";
    	for(PubOperator ps:pubList){
    		pubIds += ps.getId()+",";
    	}
    	 pubIds = pubIds.substring(0, pubIds.length()-1);
    	 qm.putParams("pubOperatorArrayStr", pubIds);
    	/// 增加按照权限来增加查询条件 end
*/     }
		//
		if( null != qm){
			 conditon = new PageCondition(pager, qm);
			 }
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	return conditon;
	
 }
}

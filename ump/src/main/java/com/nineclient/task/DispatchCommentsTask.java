package com.nineclient.task;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Dispatcher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MMSeg;
import com.chenlb.mmseg4j.Seg;
import com.chenlb.mmseg4j.SimpleSeg;
import com.chenlb.mmseg4j.Word;
import com.nineclient.constant.VocDispatchState;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.VocBrand;
import com.nineclient.model.VocComment;
import com.nineclient.model.VocShop;
import com.nineclient.model.vocreport.HotWordsDetail;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

/**
 * 评论分配 定时任务
 * @author 9client
 *
 */
//@Component
public class DispatchCommentsTask {
	@Autowired
	private JdbcTemplate JdbcTemplate; 
	//保存公司下的分配坐席配置
    public static Map<Long,Map<Long,DispatchOperator>> companyDispatchOperMap = new HashMap<Long, Map<Long,DispatchOperator>>();

    @Async
    //@Scheduled(cron="*/180 * * * * *")   //60秒任务  
	public void dispatch(){
	List<UmpCompany> companyList = UmpCompany.findAllServiceUmpCompanys();
	DispatchService dispatch = null;
	 for(UmpCompany company:companyList){
		 if(company.getIsDeleted() && company.getIsVisable()){
			 System.out.println(" company "+company.getName());
			 dispatch = new DispatchService(company);
			 dispatch.runProcess(); 
		 }else{
			 System.out.println("公司 [ "+company.getName()+" ] 被删除，不参与分配");
		 }
		
	 }
		
   }	

}

/**
 * 分配服务
 * @author 9client
 * TODO 应该为多线程实现
 */
class DispatchService{
	UmpCompany company;
 public DispatchService(UmpCompany company) {
	this.company = company;
  }
 
 public DispatchService() {
	 
  }
 
 List<PubOperator> loadPubOperator(){
	
  return PubOperator.findAllPubOperatorsByCompanyId(company.getId());
 }
 
 List<VocComment> loadComments(){
	 Map parmMap = new HashMap();
	 parmMap.put("companyId", company.getId());
	 VocComment model = new VocComment();
	 model.setDispatchState(VocDispatchState.未分配);
	 QueryModel<VocComment> qm = new QueryModel<VocComment>(model,0, 30);
	 qm.getInputMap().putAll(parmMap);
	  PageModel<VocComment> pm = VocComment.getQueryComments(qm);
	
	 return  pm.getDataList(); 
 }
 


 void runProcess(){
  List<VocComment> commentsList = loadComments();
  if(null != commentsList &&commentsList.size() > 0){
	  System.out.println("goodsName --------->>> "+commentsList.get(0).getGoods().getName());
	  List<PubOperator>  operatorList = loadPubOperator();
	  Iterator<PubOperator> it = operatorList.iterator();
	  PubOperator oper = null;
	  while(it.hasNext()){
		  oper = it.next();
		  if(oper.getPubRole() == null){
			it.remove();
		  }
	  }
	  
	  if(null != operatorList && operatorList.size() > 0){
		  initDispatcher(operatorList);  
	  }
	  
	  Map<Long,DispatchOperator> map = DispatchCommentsTask.companyDispatchOperMap.get(this.company.getId());
	  
	  if(null != map && map.size() > 0){
		  System.out.println("公司 "+company.getName()+" 加载一批数据 "+commentsList.size());
		  DispatchOperator bestOper = null;
		  for(VocComment comment:commentsList){
			   bestOper =  getBestOperator(comment,map);
			   if(null != bestOper){
				  comment.setDispatchOperator(bestOper.getOperator());
				  comment.setDispatchState(VocDispatchState.已分配);
				  comment.merge();
			   }else{
				   System.out.println("无坐席分配数据....");
			   }
			  /* if(null != comment.getGoods().getVocSku() ){
				   runHotWord(comment);
			   }*/
		    } 
	  }
	  
  }else{
	  System.out.println("公司 [ "+this.company.getName()+" ]没有待分配的评论...");
  }
  
 }
 
 
 /**
  * 初始化分配坐席
  * @param operatorList
 */
void initDispatcher(List<PubOperator>  operatorList){
   Map<Long,DispatchOperator> companyDispatcherMap = DispatchCommentsTask.companyDispatchOperMap.get(this.company.getId());
   if(null != companyDispatcherMap){
	   DispatchOperator dispatcherOper = null;
	   Map<Long, Long> brandCountMap = null;
	   
	   for(PubOperator operator:operatorList){
		 dispatcherOper = companyDispatcherMap.get(operator.getId());
		 if(null == dispatcherOper){
			 dispatcherOper = new DispatchOperator(operator);
			 brandCountMap = new HashMap<Long, Long>();
			 List<Long> brandIds = PubOperator.findPubOperatorBrands(operator.getId());
			 
			 for(Long brandId:brandIds){
			   brandCountMap.put(brandId, 0L);
			 }
			 
			dispatcherOper.setBrandCountMap(brandCountMap); 
			companyDispatcherMap.put(dispatcherOper.operator.getId(), dispatcherOper);   
		  }
	   }
	   
   }else{ //不存在
	   DispatchOperator dispatcherOper = null;
	   Map<Long, Long> brandCountMap = null;
	   companyDispatcherMap  = new HashMap<Long, DispatchOperator>();
	   for(PubOperator operator:operatorList){
			 if(PubOperator.findPubOperatorVocShops(operator.getId()).size() > 0){
				 
				 dispatcherOper = new DispatchOperator(operator);
				 brandCountMap = new HashMap<Long, Long>();
				 List<Long> brandIds = PubOperator.findPubOperatorBrands(operator.getId());
				 
				 for(Long brandId:brandIds){
				   brandCountMap.put(brandId, 0L);
				 }
				 
				dispatcherOper.setBrandCountMap(brandCountMap); 
				companyDispatcherMap.put(dispatcherOper.operator.getId(), dispatcherOper);
				
			 }else{
				 System.out.println("坐席 [ "+operator.getAccount()+" ]没有配置信息!!!");
			 }
		 }
   }
  
 }
 
 
 /**
  * 跑热词
  * @param comment
 */

void runHotWord(VocComment comment){
  HotWordsDetail model = null;
  List<String> list = null;
  try {
	  list = fenCi(comment.getCommentContent());
  } catch (IOException e) {
	e.printStackTrace();
  }
  if(null != list && list.size() > 0){
    for(String str:list){
    	model = new HotWordsDetail();
    	model.setHotWordsName(str);
    	model.setBrandName(comment.getGoods().getVocBrand().getBrandName());
    	model.setChannelName(comment.getGoods().getChannel().getChannelName());
    	model.setShopName(comment.getGoods().getShop().getName());	 
    	model.setCommentId(comment.getId());
    	model.setCompanyId(comment.getGoods().getUmpCompany().getId());
    	model.setGoodsId(comment.getGoods().getId());
    	model.setInsertTime(new Date()); 
	    model.setCommentTime(comment.getCommentTime()); 
    	model.setSkuName(comment.getGoods().getVocSku().getName());
    	model.setVersion(0);
    	model.persist(); 	     		     
    }
	    
  }
      
 }
public static void main(String[] args) {
	DispatchCommentsTask dt = new DispatchCommentsTask();
	dt.dispatch();
} 

public List<String> fenCi(String text) throws IOException{	
	Dictionary dic = Dictionary.getInstance();  
	Seg seg =  new SimpleSeg(dic);
	MMSeg mmSeg = new MMSeg(new InputStreamReader(new ByteArrayInputStream(text.getBytes())), seg);
	List<String> List = new ArrayList<String>();
	Word word = null;		
	while ((word = mmSeg.next()) != null) {
		//切词为单字的无实际意思，所以词组做为返回结果，仅仅为测试
		if (word.getString().length()>=2){
			List.add(word.getString());
		}
	}	
	
	return List;
}

 /**
  * 获取最该分配的坐席
 * @param map
 * @return
 */
public DispatchOperator getBestOperator(Map<Long, DispatchOperator>  operMap){
	DispatchOperator bestOperator = null;
	 Iterator<Long> it = operMap.keySet().iterator();
	 while(it.hasNext()){
	   Long operatorId = it.next();
		if(null == bestOperator){
		  bestOperator = operMap.get(operatorId);
		}else{
		 if(bestOperator.getDispatchCount() > operMap.get(operatorId).getDispatchCount()){
			 bestOperator = operMap.get(operatorId);
		 }
		}
	 }
	  
	return bestOperator;
  }


/**
 * 获取最该分配的坐席
* @param map
* @return
*/
public DispatchOperator getBestOperator(VocComment comment,List<DispatchOperator> operList){
	DispatchOperator bestOperator = null;
	Long brandId = comment.getGoods().getVocBrand().getId();
	Long shopId = comment.getGoods().getShop().getId();
	
	List<DispatchOperator> pubOperList = new ArrayList<DispatchOperator>();
	for(DispatchOperator oper:operList){
		boolean brandFlag = false;
		for(VocBrand vb:oper.getOperator().getVocBrands()){
		if(vb.getId() == brandId){
			brandFlag = true;
			break;
		  }
	    }
		
		boolean shopFlag = false;
      if(brandFlag){ //匹配上品牌
		 for(VocShop vshop:oper.getOperator().getVocShops()){
			 if(vshop.getId() == shopId){
				 shopFlag = true;
				 break;
			 }
		 }
		 
		 if(shopFlag){//匹配上一个坐席
		   pubOperList.add(oper);
		 }
		 
	   }
	}
	
	
	//找出同一品牌下分配最少的评论
	for(DispatchOperator oper:operList){
	   if(null == bestOperator){
		 bestOperator = oper;
	   }else{
		 if(bestOperator.getBrandCountMap().get(brandId) < oper.getBrandCountMap().get(brandId)){
			 bestOperator = oper; 
		 }
	   }
	  
	}
	
	return bestOperator;
 }

/**
 * 获得公司下的，相同品牌下的最佳分配坐席
 * @param comment
 * @param operatorMap
 * @return
 */
public DispatchOperator getBestOperator(VocComment comment,Map<Long,DispatchOperator> operatorMap){
	DispatchOperator bestOperator = null;
	Long brandId = comment.getGoods().getVocBrand().getId();
	Long shopId = comment.getGoods().getShop().getId();
	List<DispatchOperator> operList = new ArrayList<DispatchOperator>();
	for(Long key:operatorMap.keySet()){
	  operList.add(operatorMap.get(key));
	}
	
	List<DispatchOperator> pubOperList = new ArrayList<DispatchOperator>();
	
	for(DispatchOperator oper:operList){
		boolean brandFlag = false;
		for(VocBrand vb:oper.getOperator().getVocBrands()){
		if(vb.getId() == brandId){
			brandFlag = true;
			break;
		  }
	    }
		
		boolean shopFlag = false;
      if(brandFlag){ //匹配上品牌
		 for(VocShop vshop:oper.getOperator().getVocShops()){
			 if(vshop.getId() == shopId){
				 shopFlag = true;
				 break;
			 }
		 }
		 
		 if(shopFlag){//匹配上一个坐席
		   pubOperList.add(oper);
		 }
		 
	   }
	}
	
	
	//找出同一品牌下分配最少的评论
	for(DispatchOperator oper:operList){
	   if(null == bestOperator){
		 bestOperator = oper;
	   }else{
		 if(bestOperator.getBrandCountMap().get(brandId) < oper.getBrandCountMap().get(brandId)){
			 bestOperator = oper; 
		 }
	   }
	  
	}
	
	return bestOperator;
 }


}

/**
 * 分配坐席
 * @author 9client
 *
 */
class DispatchOperator{
  PubOperator operator;
  private int dispatchCount=0; //分配数量
  public Map<Long,Long> brandCountMap = new HashMap<Long, Long>(); //每个品牌下的分配数量
  
  
  public DispatchOperator(PubOperator operator) {
		this.operator = operator;

  }
  
public PubOperator getOperator() {
	return operator;
}

public void disPatchCountUp(){
	this.dispatchCount++;
}

public int getDispatchCount() {
	return dispatchCount;
}

public Map<Long, Long> getBrandCountMap() {
	return brandCountMap;
}

public void setBrandCountMap(Map<Long, Long> brandCountMap) {
	this.brandCountMap = brandCountMap;
}

/**
 * 品牌数量添加
 * @param brandId
 */
public  void brandMapUp(Long brandId){
	Long count = this.brandCountMap.get(brandId);
	if(null == count){
	  count = 0L;
	}
	
	count++;
    this.brandCountMap.put(brandId, count);
}


	
}

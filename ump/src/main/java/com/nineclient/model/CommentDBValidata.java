package com.nineclient.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.util.StringUtils;

@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class CommentDBValidata {
	@PersistenceContext
	transient EntityManager entityManager;
	public static final EntityManager entityManager() {
		EntityManager em = new VocSku().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}
	/**
	 * 
	 * @param tableObject
	 * @param field
	 * @return
	 */
	public static long queryUniqueCount(String tableObject,Map<String,Object> field) {
		StringBuffer sql = new StringBuffer(" SELECT COUNT(o) FROM "+tableObject+" o  WHERE 1=1 ");
		 for(String key:field.keySet()){
			 if(key.equals("id")){
				 sql.append(" and "+key+"!=:"+key);
			 }else{
				 sql.append(" and "+key+"=:"+key);
			 }
			
		  }
	   TypedQuery<Long> query = entityManager().createQuery(sql.toString(), Long.class);
		  for(String key:field.keySet()){
		    query.setParameter(key, field.get(key));	
		  }
		return query.getSingleResult();
    }
	/**
	 * 关联关系查询
	 * @param tableObject
	 * @param joinTable
	 * @param field
	 * @param joinCol
	 * @param joinVal
	 * @return
	 */
	public static long queryUniqueCount(String tableObject,Map<String,Object> field,String joinCol,Long joinVal) {
		StringBuffer sql = new StringBuffer(" SELECT COUNT(o) FROM "+tableObject+" o");
		if(!StringUtils.isEmpty(joinCol)){
			sql.append(" join o."+joinCol+" c ");
		}
		sql.append(" where 1=1 ");
		 for(String key:field.keySet()){
			 if(key.equals("id")){
			 sql.append(" and o."+key+"!=:"+key);
		 }else{
			 sql.append(" and o."+key+"=:"+key);
			 }
		  }
		 if(!StringUtils.isEmpty(joinCol)){
				sql.append(" and c.id=:joinVal");
				field.put("joinVal", joinVal);
			}
	   TypedQuery<Long> query = entityManager().createQuery(sql.toString(), Long.class);
		  for(String key:field.keySet()){
		    query.setParameter(key, field.get(key));	
		  }
		return query.getSingleResult();
    }
	/**
	 * 版本名称唯一验证
	 * @param versionName
	 * @param versionId
	 * @param productId
	 * @return
	 */
	public static long uniqueVersionName(String versionName,Long versionId,Long productId){
		String sql="select count(o) from UmpVersion o join o.product p where 1=1 ";
		if(!StringUtils.isEmpty(versionId)){
			sql+=" and o.id!="+versionId;
		}
		if(!StringUtils.isEmpty(versionName)){
			sql+=" and o.versionName='"+versionName+"'";
		}
		if(!StringUtils.isEmpty(productId)){
			sql+=" and p.id="+productId;
		}
		 TypedQuery<Long> query = entityManager().createQuery(sql.toString(), Long.class);	
		 return query.getSingleResult();
	}
	/**
	 * 校验节点唯一性
	 * @param tableObject
	 * @param nodeId
	 * @param nodeName
	 * @param level
	 * @param companyId
	 * @return
	 */
	public static long uniqueCountTreeNode(String tableObject,Long nodeId,String nodeName,Integer level,Long companyId){
		StringBuilder sql=new StringBuilder("select count(o) from "+tableObject+" o where o.level="+level);
		 if(nodeId!=null){
			 sql.append(" and o.id!="+nodeId);
		 }
		 sql.append(" and o.name='"+nodeName+"'");
		 sql.append(" and (o.companyId="+companyId+" or o.isDefault="+true+")");
	   TypedQuery<Long> query = entityManager().createQuery(sql.toString(), Long.class);
		
		return query.getSingleResult();
	}
	/**
	 * 
	 * @param sql
	 * @return
	 */
	public static long queryUniqueCount(String hql) {
	   TypedQuery<Long> query = entityManager().createQuery(hql.toString(), Long.class);
		return query.getSingleResult();
    }
	
	public static Long queryUniqueNum(String sql){
		Long num = 0L;
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(sql);
		num = Long.parseLong(String.valueOf(query.uniqueResult()));
		return num;
	}
}

/**
 * <pre>
 * 上海xxxxxxx
 * Copyright (C): 2012
 * 
 * 文件名称：
 * com.nusof.iptv.common.QueryModel.java
 * 
 * 文件描述: 
 * 查询模型，主要用于从 controller -&gt; service -&gt; dao 参数的传递。
 * 
 * Notes:
 * 修改历史(作者/日期/改动描述):
 * 王彬/2012.04.15/初始化版本。
 * </pre>
 */
package com.nineclient.utils;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @date 2010-9-25
 * @author 
 */
public class QueryModel<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 缺省构造
	 */
	public QueryModel() {
	}

	/**
	 * 传递参数构造。
	 * 
	 * @param t
	 */
	public QueryModel(T entity) {
		this.entity = entity;
	}

	/**
	 * 传递参数构造。
	 * 
	 * @param inputMap
	 */
	public QueryModel(Map<String, Object> inputMap) {
		this.inputMap = inputMap;
	}

	/**
	 * 传递参数构造。
	 * 
	 * @param entity
	 *            实体类
	 * @param start
	 *            开始索引
	 * @param limit
	 *            限制
	 */
	public QueryModel(T entity, int start, int limit) {
		this.entity = entity;
		this.start = start;
		this.limit = limit;
	}

	/**
	 * 传递参数构造。
	 * 
	 * @param entity
	 * @param start
	 * @param limit
	 * @param field
	 * @param dir
	 */
	public QueryModel(T entity, int start, int limit, String field, String dir) {
		this.entity = entity;
		this.start = start;
		this.limit = limit;
		this.putOrder(field, dir);
	}

	/**
	 * 开始
	 */
	private int start = 0;
	/**
	 * 数量限制
	 */
	private int limit = Integer.MAX_VALUE;
	/**
	 * 由 start ,limit 算出 pageNo，以适应更多的查询方式。
	 */
	private int pageNo;
	/**
	 * 排序字段名,使用map来应对多字段排序, key=字段名，value=字段排序规则。
	 */
	private Map<String, String> orderMap;
	/**
	 * 需要查询的实体类，有限从此处取参数。<br />
	 * 优先级 1
	 */
	private T entity;
	/**
	 * 参数map，实体类没有的，从map取参数。<br />
	 * 优先级 2
	 */
	private Map<String, Object> inputMap;
	/**
	 * 用于应急传递参数的 拼串传递。 <br />
	 * 优先级3
	 */
	private String sql = "";

	/**
	 * putMap 方法，提供便捷的管理。默认使用 HashMap
	 * 
	 * @author 
	 * @2010-9-25
	 * @param key
	 *            参数key,
	 * @param value
	 *            参数 value
	 * @return
	 */
	public QueryModel<T> putParams(String key, Object value) {
		this.getInputMap().put(key, value);
		return this;
	}

	/**
	 * 将排序 map 放入
	 * 
	 * @author 
	 * @2010-9-25
	 * @param field
	 * @param type
	 * @return
	 */
	public QueryModel<T> putOrder(String field, String type) {
		this.getOrderMap().put(field, type);
		return this;
	}

	/**
	 * 获取默认排序，即 orderMap 大小为 1 时的排序。
	 * 
	 * @author 
	 * @2010-9-25
	 * @return
	 */
	public String getFirstOrderName() {
		if (this.getOrderMap().isEmpty()) {
			return "";
		}
		return this.getOrderMap().keySet().iterator().next();
	}

	/**
	 * 获取默认排序顺序，即 orderMap 大小为 1 时的排序。
	 * 
	 * @author 
	 * @2010-9-25
	 * @return
	 */
	public String getFirstOrderType() {
		if (this.getOrderMap().isEmpty()) {
			return "";
		}
		return this.orderMap.values().iterator().next();
	}

	/**
	 * 根据 参数 key 获取字符串参数值，为空返回 Null。
	 * 
	 * @author  @2010-10-14
	 * @param key
	 * @return
	 */
	public String getParamsString(String key) {
		Object value = this.getInputMap().get(key);
		return value == null ? null : value.toString();
	}

	/**
	 * 根据 参数 key 获取整形参数值，为空返回 Null。
	 * 
	 * @author  @2010-10-14
	 * @param key
	 * @return
	 */
	public Integer getParamsInteger(String key) {
		Object value = this.getInputMap().get(key);
		return (value == null ? null : (Integer) value);
	}

	/**
	 * 根据 参数 key 获取整形参数值，为空返回 Null。
	 * 
	 * @author  @2010-10-14
	 * @param key
	 * @return
	 */
	public Long getParamsLong(String key) {
		Object value = this.getInputMap().get(key);
		return (value == null ? null : (Long) value);
	}

	/**
	 * 根据 参数 key 获取整形参数值，为空返回 Null。
	 * 
	 * @author  @2010-10-14
	 * @param key
	 * @return
	 */
	public Boolean getParamsBoolean(String key) {
		Object value = this.getInputMap().get(key);
		return (value == null ? null : (Boolean) value);
	}



	public int getStart() {
		return this.start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return this.limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public Map<String, String> getOrderMap() {
		if (this.orderMap == null) {
			this.orderMap = new LinkedHashMap<String, String>();
		}
		return this.orderMap;
	}

	public void setOrderMap(Map<String, String> orderMap) {
		this.orderMap = orderMap;
	}

	public T getEntity() {
		return this.entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public Map<String, Object> getInputMap() {
		if (this.inputMap == null) {
			this.inputMap = new LinkedHashMap<String, Object>();
		}
		return this.inputMap;
	}

	public String getSql() {
		return this.sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public static String toString(Object value) {
		if (null != value) {
			return value.toString();
		}
		return null;
	}
	/* ----------------- get set ----------------- */

}

package com.nineclient.weixin;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

/**
  *
  * <b>类名称：</b>JsonUtil <br />
  * <b>类描述：</b> <br />
  * <b>创建人：</b>hugq <br />
  * <b>修改人：</b> <br />
  * <b>修改时间：</b> <br />
  * <b>修改备注：</b> <br />
  * @version 1.0
  */
public class JsonHelper {
	@SuppressWarnings("finally")
	public static <T> String beanToJson(T obj) {
		String json = null;
		if (obj == null) {
			return json;
		}
		ObjectMapper mapper = null;
		try {
			mapper = new ObjectMapper();
			json = mapper.writeValueAsString(obj);//把map或者是list转换成
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mapper = null;
			return json;
		}
	}

	@SuppressWarnings("finally")
	public static <T> String beanToJsonNoNull(T obj) {
		String json = null;
		if (obj == null) {
			return json;
		}
		ObjectMapper mapper = null;
		try {
			mapper = new ObjectMapper();
			json = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mapper = null;
			return json;
		}
	}

	public static Map<String, Object> jsonToMap(String jsonStr) {
		return jsonToBean(jsonStr, Map.class);
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public static <T> T jsonToBean(String jsonStr, Class<?> valueType) {
		T obj = null;
		if (jsonStr == null || "".equals(jsonStr)) {
			return null;
		}
		ObjectMapper mapper = null;
		try {
			mapper = new ObjectMapper();
			obj = (T) mapper.readValue(jsonStr, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mapper = null;
			return obj;
		}
	}

	public static void main(String[] args) {
	}
}

package com.nineclient.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信全局错误返回码
 *
 */
public class WeiXinPubReturnCode {
	private static Map<String, String> map = new HashMap<String, String>();
	public static String pubReturnCode(String code) {
		String msg = "";
		if (code != null && !"".equals(code)) {
			for (String key : map.keySet()) {
				if (code.contains(key)) {
					msg = map.get(key);
				}
			}
		}
		return msg;
	}
	static {
		 map.put("-1", "系统繁忙，请稍候再试");
		 map.put("0", "系统繁忙，请稍候再试");
		 map.put("1", "提交成功");
		 map.put("2", "提交失败");
		 map.put("3", "提交的一级菜单缺少二级菜单");
		 map.put("40001" , "AppSecret输入有误");  
		 map.put("40002" , "AppID与AppSecret信息不匹配");
		 map.put("40003" , "该用户不存在");
		 map.put("40004" , "不合法的媒体文件类型");
		 map.put("40005" , "不合法的文件类型");
		 map.put("40006" , "文件大小超过限制");
		 map.put("40007" , "不合法的媒体文件id");
		 map.put("40008" , "不合法的消息类型");
		 map.put("40009" , "图片大小不能超过1M");
		 map.put("40010" , "语音大小不能超过2M且长度不超过60S");
		 map.put("40011" , "视频大小不能超过10M");
		 map.put("40012" , "缩略图大小不能超过64KB");
		 map.put("40013" , "AppID输入有误");
		 map.put("40014" , "access_token有误");
		 map.put("40015" , "不合法的菜单类型");
		 map.put("40016" , "不合法的按钮个数");
		 map.put("40017" , "不合法的按钮个数");
		 map.put("40018" , "一级菜单标题不能超过10个字符");
		 map.put("40019" , "Key长度不能超过128个字符");
		 map.put("40020" , "URL长度不能超过256个字符");
		 map.put("40021" , "不合法的菜单版本号");
		 map.put("40022" , "不合法的子菜单级数");
		 map.put("40023" , "二级菜单不能超过5个");
		 map.put("40024" , "不合法的子菜单按钮类型");
		 map.put("40025" , "二级菜单标题不能超过16个字符");
		 map.put("40026" , "Key长度不能超过128个字符");
		 map.put("40027" , "URL长度不能超过256个字符");
		 map.put("40028" , "不合法的自定义菜单使用用户");
		 map.put("40029" , "不合法的oauth_code");
		 map.put("40030" , "不合法的refresh_token");
		 map.put("40031" , "粉丝列表不能为空且不能超过10000个");
		 map.put("40032" , "粉丝列表不能为空且不能超过10000个");
		 map.put("40033" , "不能包含特殊格式的字符");
		 map.put("40035" , "不合法的参数");
		 map.put("40038" , "不合法的请求字符");
		 map.put("40039" , "URL长度不能超过256个字符");
		 map.put("40050" , "不合法的分组id");
		 map.put("40051" , "分组名称不能超过30个字符");
		 map.put("41001" , "缺少access_token参数");
		 map.put("41002" , "缺少AppID");
		 map.put("41003" , "缺少refresh_token参数");
		 map.put("41004" , "缺少AppSecret");
		 map.put("41005" , "缺少多媒体文件数据");
		 map.put("41006" , "缺少media_id参数");
		 map.put("41007" , "提交的一级菜单缺少二级菜单");
		 map.put("41008" , "缺少oauth code");
		 map.put("41009" , "请选择粉丝");
		 map.put("42001" , "access_token超时");
		 map.put("42002" , "refresh_token超时");
		 map.put("42003" , "oauth_code超时");
		 map.put("43001" , "需要GET请求");
		 map.put("43002" , "需要POST请求");
		 map.put("43003" , "需要HTTPS请求");
		 map.put("43004" , "该粉丝已取消关注");
		 map.put("43005" , "需要好友关系");
		 map.put("44001" , "多媒体文件为空");
		 map.put("44002" , "POST的数据包为空");
		 map.put("44003" , "图文消息内容为空");
		 map.put("44004" , "文本消息内容为空");
		 map.put("45001" , "多媒体文件大小超过限制");
		 map.put("45002" , "消息内容不能超过20000字");
		 map.put("45003" , "标题不能超过64个字");
		 map.put("45004" , "摘要不能超过120个字");
		 map.put("45005" , "链接不能超过256个字符");
		 map.put("45006" , "图片链接不能超过256个字符");
		 map.put("45007" , "语音播放时间不能超过60S");
		 map.put("45008" , "图文消息超过限制");
		 map.put("45009" , "接口调用超过限制");
		 map.put("45010" , "最多支持3个一级菜单，5个二级菜单");
		 map.put("45015" , "回复时间不能超过48小时");
		 map.put("45016" , "系统分组，不允许修改");
		 map.put("45017" , "分组名称不能超过30个字符");
		 map.put("45018" , "分组数量不能超过100个");
		 map.put("46001" , "不存在媒体数据");
		 map.put("46002" , "不存在的菜单版本");
		 map.put("46003" , "不存在的菜单数据");
		 map.put("46004" , "该用户不存在");
		 map.put("47001" , "解析JSON/XML内容错误");
		 map.put("48001" , "该公众号无此接口权限");
		 map.put("50001" , "用户未授权该api");
		 map.put("61451" , "参数错误 ");
		 map.put("61452" , "该客服账号不存在");
		 map.put("61453" , "客服帐号已存在");
		 map.put("61454" , "客服帐号名长度不能超过10个字符");
		 map.put("61455" , "客服帐号名仅支持英文+数字");
		 map.put("61456" , "客服帐号数不能超过10个");
		 map.put("61457" , "头像仅支持JPG格式");
		 map.put("61450" , "系统错误");
		 map.put("61500" , "日期格式错误");
		 map.put("61501" , "日期范围错误");
	}
	
	public static void main(String[] args) {
		//System.out.println(pubReturnCode("61501"));
	}
}

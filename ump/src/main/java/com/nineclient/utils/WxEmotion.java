package com.nineclient.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WxEmotion {

	private static Map<String, String> map = new HashMap<String, String>();

	private static String img = "<img src=\"url\" width=\"24\" height=\"24\" >";

	public static String replaceEmotion(String content) {
		if (content != null && !"".equals(content.trim())) {
			Set<String> keySet = map.keySet();
			for (String key : keySet) {
				if (content.contains(key)) {
					content = content.replace(key, img.replace("url", map.get(key)));
				}
			}
		}
		return content;
	}

	static {
		 map.put("/::)", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/0.gif");  
		 map.put("/::~", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/1.gif");  
		  map.put("/::B", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/2.gif");  
		  map.put("/::|", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/3.gif");  
		 map.put("/:8-)", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/4.gif");  
		 map.put("/::<", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/5.gif");  
		 map.put("/::$", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/6.gif");  
		  map.put("/::X", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/7.gif");  
		  map.put("/::Z", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/8.gif");  
		  map.put("/::'(", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/9.gif");  
		  map.put("/::-|", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/10.gif"); 
		  map.put("/::@", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/11.gif"); 
		   map.put("/::P", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/12.gif"); 
		   map.put("/::D", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/13.gif"); 
		    map.put("/::O", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/14.gif"); 
		  map.put("/::(", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/15.gif"); 
		   map.put("/::+", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/16.gif"); 
		  map.put("/:--b", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/17.gif"); 
		    map.put("/::Q", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/18.gif"); 
		   map.put("/::T", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/19.gif"); 
		   map.put("/:,@P", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/20.gif"); 
		   map.put("/:,@-D", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/21.gif"); 
		   map.put("/::d", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/22.gif"); 
		   map.put("/:,@o", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/23.gif"); 
		 map.put("/::g", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/24.gif"); 
		 map.put("/:|-)", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/25.gif"); 
		 map.put("/::!", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/26.gif"); 
		 map.put("/::L", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/27.gif"); 
		 map.put("/::&gt;", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/28.gif"); 
		 map.put("/::,@", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/29.gif"); 
		 map.put("/:,@f", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/30.gif"); 
		 map.put("/::-S", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/31.gif"); 
		  map.put("/:?", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/32.gif"); 
		 map.put("/:,@x", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/33.gif"); 
		 map.put("/:,@@", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/34.gif"); 
		 map.put("/::8", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/35.gif"); 
		  map.put("/:,@!", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/36.gif"); 
		 map.put("/:!!!", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/37.gif"); 
		 map.put("/:xx", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/38.gif"); 
		 map.put("/:bye", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/39.gif"); 
		 map.put("/:wipe", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/40.gif"); 
		 map.put("/:dig", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/41.gif"); 
		 map.put("/:handclap", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/42.gif"); 
		 map.put("/:&amp;-(", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/43.gif"); 
		 map.put("/:B-)", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/44.gif"); 
		 map.put("/:&lt;@", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/45.gif"); 
		 map.put("/:@&gt;", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/46.gif"); 
		 map.put("/::-O", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/47.gif"); 
		 map.put("/:&gt;-|", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/48.gif"); 
		 map.put("/:P-(", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/49.gif"); 
		 map.put("/::’|", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/50.gif"); 
		 map.put("/:X-)", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/51.gif"); 
		 map.put("/::*", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/52.gif"); 
		 map.put("/:@x", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/53.gif"); 
		 map.put("/:8*", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/54.gif"); 
		 map.put("/:pd", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/55.gif"); 
		 map.put("/:&lt;W&gt;", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/56.gif"); 
		 map.put("/:beer", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/57.gif"); 
		 map.put("/:basketb", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/58.gif"); 
		 map.put("/:oo", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/59.gif"); 
		 map.put("/:coffee", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/60.gif"); 
		 map.put("/:eat", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/61.gif"); 
		 map.put("/:pig", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/62.gif"); 
		 map.put("/:rose", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/63.gif"); 
		 map.put("/:fade", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/64.gif"); 
		 map.put("/:showlove", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/65.gif"); 
		 map.put("/:heart", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/66.gif"); 
		 map.put("/:break", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/67.gif"); 
		 map.put("/:cake", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/68.gif"); 
		 map.put("/:li", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/69.gif"); 
		 map.put("/:bome", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/70.gif"); 
		 map.put("/:kn", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/71.gif"); 
		 map.put("/:footb", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/72.gif"); 
		 map.put("/:ladybug", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/73.gif"); 
		 map.put("/:shit", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/74.gif"); 
		 map.put("/:moon", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/75.gif"); 
		 map.put("/:sun", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/76.gif"); 
		 map.put("/:gift", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/77.gif"); 
		 map.put("/:hug", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/78.gif"); 
		 map.put("/:strong", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/79.gif"); 
		 map.put("/:weak", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/80.gif"); 
		 map.put("/:share", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/81.gif"); 
		 map.put("/:v", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/82.gif"); 
		 map.put("/:@)", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/83.gif"); 
		 map.put("/:jj", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/84.gif"); 
		 map.put("/:@@", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/85.gif"); 
		 map.put("/:bad", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/86.gif"); 
		 map.put("/:lvu", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/87.gif"); 
		 map.put("/:no", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/88.gif"); 
		 map.put("/:ok", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/89.gif"); 
		 map.put("/:love", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/90.gif"); 
		 map.put("/:&lt;L&gt;", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/91.gif"); 
		 map.put("/:jump", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/92.gif"); 
		 map.put("/:shake", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/93.gif"); 
		 map.put("/:&lt;O&gt;", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/94.gif"); 
		  map.put("/:circle", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/95.gif"); 
		 map.put("/:kotow", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/96.gif"); 
		 map.put("/:turn", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/97.gif"); 
		 map.put("/:skip", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/98.gif"); 
		 map.put("[挥手]", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/99.gif"); 
		 map.put("/:#-0 ", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/100.gif");
		 map.put(" [街舞]", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/101.gif");
		 map.put("/:kiss", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/102.gif");
		 map.put("/:&lt;&amp;", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/103.gif");
		 map.put("/:&amp;&gt;", "https://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/104.gif");
	}
}

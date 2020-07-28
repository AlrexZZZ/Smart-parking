package com.nineclient.utils;

public class Emotion {
	
	private String content;
	
	private String url;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Emotion [content=" + content + ", url=" + url + "]";
	}

}

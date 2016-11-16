package com.faceye.component.spider.service.domain;
/**
 * 网站链接对像
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年1月1日
 */
public class DomainLink {
	private String url="";
	private Integer type=null;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}

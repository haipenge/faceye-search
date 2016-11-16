package com.faceye.component.spider.service.domain;

/**
 * 网站链接对像
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年1月1日
 */
public class DomainLink {
	private String url = "";
	private Integer type = null;
	// 是否将本链接的文章推广到微信
	private Boolean isWeixin = Boolean.FALSE;
	/**
	 * 分类
	 */
	private String categoryKey="";

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

	public Boolean getIsWeixin() {
		return isWeixin;
	}

	public void setIsWeixin(Boolean isWeixin) {
		this.isWeixin = isWeixin;
	}

	public String getCategoryKey() {
		return categoryKey;
	}

	public void setCategoryKey(String categoryKey) {
		this.categoryKey = categoryKey;
	}
	

}

package com.faceye.component.spider.doc;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "spider_crawl_result")
@CompoundIndexes({ @CompoundIndex(name = "global_index", def = "{'_id': -1, 'linkId': -1,'siteId':1,'linkType':1}") })
public class CrawlResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8926119711730830203L;
	@Id
	private Long id = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 说明:标题 属性名: name 类型: String 数据库字段:name
	 * 
	 * @author haipenge
	 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 说明:内容 属性名: content 类型: String 数据库字段:content
	 * 
	 * @author haipenge
	 */
	private String storePath;

	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

	/**
	 * 是否已解析
	 */
	private Boolean isParse = Boolean.FALSE;

	public Boolean getIsParse() {
		return isParse;
	}

	public void setIsParse(Boolean isParse) {
		this.isParse = isParse;
	}

	/**
	 * 说明:爬取日期 属性名: crawlDate 类型: Date 数据库字段:crawl_date
	 * 
	 * @author haipenge
	 */
	private Date crawlDate = new Date();

	public Date getCrawlDate() {
		return crawlDate;
	}

	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}

	/**
	 * 说明:链接 属性名: link 类型: Link 数据库字段:link_id
	 * 
	 * @author haipenge
	 */
	@Indexed(direction = IndexDirection.DESCENDING)
	private Long linkId = null;

	// @ManyToOne(cascade = CascadeType.MERGE)
	// @JoinColumn(name = "link_id", nullable = true, updatable = true)
	// private Link link;
	//
	// public Link getLink() {
	// return link;
	// }
	//
	// public void setLink(Link link) {
	// this.link = link;
	// }

	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}

	private String linkUrl = "";

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	private Boolean isParseSuccess = Boolean.FALSE;

	public Boolean getIsParseSuccess() {
		return isParseSuccess;
	}

	public void setIsParseSuccess(Boolean isParseSuccess) {
		this.isParseSuccess = isParseSuccess;
	}
@Indexed
	private Long siteId = null;

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	private Integer linkType = null;

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	/**
	 * 说明:是否微信 属性名: isWeixin 类型: Boolean 数据库字段:is_weixin
	 * 
	 * @author haipenge
	 */

	private Boolean isWeixin = Boolean.FALSE;

	public Boolean getIsWeixin() {
		return isWeixin;
	}

	public void setIsWeixin(Boolean isWeixin) {
		this.isWeixin = isWeixin;
	}

}/** @generate-entity-source@ **/

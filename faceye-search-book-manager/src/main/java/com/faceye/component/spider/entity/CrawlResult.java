package com.faceye.component.spider.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CrawlResult ORM 实体
 * 数据库表:spider_crawlResult
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */
@Entity
@Table(name = "spider_crawl_result")
public class CrawlResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8926119711730830203L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 说明:标题
	 * 属性名: name
	 * 类型: String
	 * 数据库字段:name
	 * @author haipenge
	 */
	@Column(name = "name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 说明:内容
	 * 属性名: content
	 * 类型: String
	 * 数据库字段:content
	 * @author haipenge
	 */
	@Column(name = "store_path")
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
	@Column(name = "is_parse")
	private Boolean isParse = Boolean.FALSE;

	public Boolean getIsParse() {
		return isParse;
	}

	public void setIsParse(Boolean isParse) {
		this.isParse = isParse;
	}

	/**
	    * 说明:爬取日期
	    * 属性名: crawlDate
	    * 类型: Date
	    * 数据库字段:crawl_date
	    * @author haipenge
	    */
	@Column(name = "crawl_date")
	private Date crawlDate=new Date();

	public Date getCrawlDate() {
		return crawlDate;
	}

	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}

	/**
	 * 说明:链接
	 * 属性名: link
	 * 类型: Link
	 * 数据库字段:link_id
	 * @author haipenge
	 */
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "link_id", nullable = true, updatable = true)
	private Link link;

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	@Column(name = "is_parse_success")
	private Boolean isParseSuccess = Boolean.FALSE;

	public Boolean getIsParseSuccess() {
		return isParseSuccess;
	}

	public void setIsParseSuccess(Boolean isParseSuccess) {
		this.isParseSuccess = isParseSuccess;
	}

	
}
/**@generate-entity-source@**/


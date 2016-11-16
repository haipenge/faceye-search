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
 * Link ORM 实体
 * 数据库表:spider_link
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */
@Entity
@Table(name = "spider_link")
public class Link implements Serializable {

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
	 * 说明:地址
	 * 属性名: url
	 * 类型: String
	 * 数据库字段:url
	 * @author haipenge
	 */
	@Column(name = "url")
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 说明:是否已爬取
	 * 属性名: isCrawled
	 * 类型: Boolean
	 * 数据库字段:is_crawl_ed
	 * @author haipenge
	 */
	@Column(name = "is_crawl_ed")
	private Boolean isCrawled=false;

	public Boolean getIsCrawled() {
		return isCrawled;
	}

	public void setIsCrawled(Boolean isCrawled) {
		this.isCrawled = isCrawled;
	}

	/**
	 * 说明:创建日期
	 * 属性名: createDate
	 * 类型: Date
	 * 数据库字段:create_date
	 * @author haipenge
	 */
	@Column(name = "create_date")
	private Date createDate=new Date();

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 说明:是否爬取成功
	 * 属性名: isCrawlSuccess
	 * 类型: Boolean
	 * 数据库字段:is_crawl_success
	 * @author haipenge
	 */
	@Column(name = "is_crawl_success")
	private Boolean isCrawlSuccess=false;

	public Boolean getIsCrawlSuccess() {
		return isCrawlSuccess;
	}

	public void setIsCrawlSuccess(Boolean isCrawlSuccess) {
		this.isCrawlSuccess = isCrawlSuccess;
	}

	/**
	 * 说明:最后爬取时间
	 * 属性名: lastCrawlDate
	 * 类型: Date
	 * 数据库字段:last_crawl_date
	 * @author haipenge
	 */
	@Column(name = "last_crawl_date")
	private Date lastCrawlDate;

	public Date getLastCrawlDate() {
		return lastCrawlDate;
	}

	public void setLastCrawlDate(Date lastCrawlDate) {
		this.lastCrawlDate = lastCrawlDate;
	}

	/**
	 * 链接类型：1-》列表页,2-》明细页,3->Cnblogs博客园子首页
	 */
	@Column(name = "link_type")
	private Integer type = null;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "site_id", nullable = true, updatable = true)
	private Site site = null;

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Column(name="parent_id")
	private Long parentId=null;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * 文件类型
	 * 0,网页，1，图片
	 */
	@Column(name="mime_type")
	private Integer mimeType=0;

	public Integer getMimeType() {
		return mimeType;
	}

	public void setMimeType(Integer mimeType) {
		this.mimeType = mimeType;
	}
	
	
	
//	@ManyToOne(cascade = CascadeType.PERSIST)
//	@JoinColumn(name = "parent_id", nullable = true, updatable = false)
//	private Link parent = null;
//
//	public Link getParent() {
//		return parent;
//	}
//
//	public void setParent(Link parent) {
//		this.parent = parent;
//	}

}
/**@generate-entity-source@**/


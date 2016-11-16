package com.faceye.component.spider.doc;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.mongodb.core.mapping.Document;

import com.faceye.component.spider.entity.Site;

@Document(collection="spider_link")
public class Link implements java.io.Serializable {
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
	 * 说明:地址
	 * 属性名: url
	 * 类型: String
	 * 数据库字段:url
	 * @author haipenge
	 */
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
	 * 链接类型：0->种子链接,1-》列表页,2-》明细页,3->Cnblogs博客园子首页(将逐渐废弃）
	 */
	@Column(name = "link_type")
	private Integer type = null;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 所属站点ID
	 */
	private Long siteId=null;
	

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

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
	
}

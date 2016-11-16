package com.faceye.component.spider.doc;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "spider_site")
public class Site implements java.io.Serializable {
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
	 * 说明:网站名
	 * 属性名: name
	 * 类型: String
	 * 数据库字段:name
	 * @author haipenge
	 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 说明:是否使用超级解析器
	 * 属性名: isUseSuperParse
	 * 类型: Boolean
	 * 数据库字段:is_user_super_parse
	 * @author haipenge
	 */

	private Boolean isUseSuperParse=Boolean.FALSE;

	public Boolean getIsUseSuperParse() {
		return isUseSuperParse;
	}

	public void setIsUseSuperParse(Boolean isUseSuperParse) {
		this.isUseSuperParse = isUseSuperParse;
	}



   /**
    * 说明:线程数量
    * 属性名: threadCount
    * 类型: Integer
    * 数据库字段:thread_count
    * @author haipenge
    */
    
	private  Integer threadCount=1;
	public Integer getThreadCount() {
		return threadCount;
	}
	public void setThreadCount(Integer threadCount) {
		this.threadCount = threadCount;
	}
	

	
   /**
    * 说明:最后爬取时间 
    * 属性名: lastCrawlDate
    * 类型: Date
    * 数据库字段:last_crawl_date
    * @author haipenge
    */
    
	private  Date lastCrawlDate;
	public Date getLastCrawlDate() {
		return lastCrawlDate;
	}
	public void setLastCrawlDate(Date lastCrawlDate) {
		this.lastCrawlDate = lastCrawlDate;
	}
	

	
   /**
    * 说明:是否爬取图片
    * 属性名: isCrawlImage
    * 类型: Boolean
    * 数据库字段:is_crawl_image
    * @author haipenge
    */
    
	private  Boolean isCrawlImage=Boolean.FALSE;
	public Boolean getIsCrawlImage() {
		return isCrawlImage;
	}
	public void setIsCrawlImage(Boolean isCrawlImage) {
		this.isCrawlImage = isCrawlImage;
	}
	
	/**
	 * 是否爬取页面
	 */
	private Boolean isCrawl=Boolean.TRUE;

	public Boolean getIsCrawl() {
		return isCrawl;
	}

	public void setIsCrawl(Boolean isCrawl) {
		this.isCrawl = isCrawl;
	}
	
	
}/**@generate-entity-source@**/
	

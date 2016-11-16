package com.faceye.component.spider.doc;

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
	
}/**@generate-entity-source@**/
	

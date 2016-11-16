package com.faceye.component.search.doc;

import java.io.Serializable;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
/**
 * RequestRecord ORM 实体
 * 数据库表:search_requestRecord
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */
@Document(collection="search_request_record")
public class RequestRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8926119711730830203L;
	@Id
	private  Long id=null;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

   /**
    * 说明:请求URL
    * 属性名: url
    * 类型: String
    * 数据库字段:url
    * @author haipenge
    */
    
	private  String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	

	
   /**
    * 说明:源
    * 属性名: referer
    * 类型: String
    * 数据库字段:referer
    * @author haipenge
    */
    
	private  String referer;
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	
}/**@generate-entity-source@**/
	

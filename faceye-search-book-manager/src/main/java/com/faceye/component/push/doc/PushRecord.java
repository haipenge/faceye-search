package com.faceye.component.push.doc;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * PushRecord ORM 实体
 * 数据库表:push_pushRecord
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */
@Document(collection="push_push_record")
public class PushRecord implements Serializable {

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
    * 说明:推送文章ID
    * 属性名: articleId
    * 类型: Long
    * 数据库字段:article_id
    * @author haipenge
    */
    
	private  Long articleId;
	public Long getArticleId() {
		return articleId;
	}
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	/**
	 * 推送网站的ID
	 */
	private Long siteId=null;
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	
	
	
}/**@generate-entity-source@**/
	

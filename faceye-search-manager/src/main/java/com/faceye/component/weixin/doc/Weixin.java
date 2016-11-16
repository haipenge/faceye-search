package com.faceye.component.weixin.doc;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.faceye.component.search.doc.Article;

/**
 * Weixin ORM 实体 数据库表:weixin_weixin
 * 
 * @author @haipenge haipenge@gmail.com Create Date:2014年5月21日
 */
@Document(collection = "weixin_weixin")
public class Weixin implements Serializable {

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
	 * 说明:名称 属性名: name 类型: String 数据库字段:name
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
	 * 说明:文章ID 属性名: articleId 类型: Long 数据库字段:article_id
	 * 
	 * @author haipenge
	 */
	@DBRef
	private Article article = null;

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	/**
	 * 创建日期
	 */
	private Date createDate = new Date();

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
/** @generate-entity-source@ **/

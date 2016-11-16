package com.faceye.component.search.doc;

import java.io.Serializable;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
/**
 * ArticleCategory ORM 实体
 * 数据库表:search_articleCategory
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */
@Document(collection="search_article_category")
public class ArticleCategory implements Serializable {

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
    * 说明:Category Name
    * 属性名: name
    * 类型: String
    * 数据库字段:article_category
    * @author haipenge
    */
    
	private  String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

	
   /**
    * 说明:Order Index
    * 属性名: orderIndex
    * 类型: Integer
    * 数据库字段:order_index
    * @author haipenge
    */
    
	private  Integer orderIndex;
	public Integer getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
	

	
   /**
    * 说明:Alias
    * 属性名: alias
    * 类型: String
    * 数据库字段:alias
    * @author haipenge
    */
    
	private  String alias;
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	

	
   /**
    * 说明:关键字
    * 属性名: keywords
    * 类型: String
    * 数据库字段:keywords
    * @author haipenge
    */
    
	private  String keywords;
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
}/**@generate-entity-source@**/
	

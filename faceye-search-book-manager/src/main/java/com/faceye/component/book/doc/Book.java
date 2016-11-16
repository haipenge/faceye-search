package com.faceye.component.book.doc;

import java.io.Serializable;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
/**
 * Book ORM 实体
 * 数据库表:book_book
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */
@Document(collection="book_book")
public class Book implements Serializable {

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
    * 说明:Name
    * 属性名: name
    * 类型: String
    * 数据库字段:name
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
    * 说明:Author
    * 属性名: author
    * 类型: String
    * 数据库字段:author
    * @author haipenge
    */
    
	private  String author;
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	

	
   /**
    * 说明:Pic
    * 属性名: pic
    * 类型: String
    * 数据库字段:pic
    * @author haipenge
    */
    
	private  String pic;
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	

	
   /**
    * 说明:CategoryId
    * 属性名: categoryId
    * 类型: Long
    * 数据库字段:category_id
    * @author haipenge
    */
    
	private  Long categoryId;
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	

	
   /**
    * 说明:Category Name
    * 属性名: categoryName
    * 类型: String
    * 数据库字段:category_name
    * @author haipenge
    */
    
	private  String categoryName;
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	

	
   /**
    * 说明:Content
    * 属性名: content
    * 类型: String
    * 数据库字段:content
    * @author haipenge
    */
    
	private  String content;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}/**@generate-entity-source@**/
	

package com.faceye.component.book.doc;

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
 * Section ORM 实体
 * 数据库表:book_section
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */
@Document(collection="book_section")
public class Section implements Serializable {

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
    * 说明:Create Date
    * 属性名: createDate
    * 类型: Date
    * 数据库字段:create_date
    * @author haipenge
    */
    
	private  Date createDate;
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	

	
   /**
    * 说明:Index Number
    * 属性名: indexNum
    * 类型: Integer
    * 数据库字段:index_num
    * @author haipenge
    */
    
	private  Integer indexNum;
	public Integer getIndexNum() {
		return indexNum;
	}
	public void setIndexNum(Integer indexNum) {
		this.indexNum = indexNum;
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
	

	
   /**
    * 说明:Book ID
    * 属性名: bookId
    * 类型: Long
    * 数据库字段:book_id
    * @author haipenge
    */
    
	private  Long bookId;
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	

	
   /**
    * 说明:Book Name
    * 属性名: bookName
    * 类型: String
    * 数据库字段:book_name
    * @author haipenge
    */
    
	private  String bookName;
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
}/**@generate-entity-source@**/
	

package com.faceye.component.book.doc;

import java.io.Serializable;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
/**
 * Category ORM 实体
 * 数据库表:book_category
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */
@Document(collection="book_category")
public class Category implements Serializable {

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
    * 说明:OrderIndex
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
	
}/**@generate-entity-source@**/
	

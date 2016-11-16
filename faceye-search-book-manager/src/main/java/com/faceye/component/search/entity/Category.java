package com.faceye.component.search.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Category ORM 实体
 * 数据库表:search_category
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */
@Entity
@Table(name="search_category")
public class Category implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8926119711730830203L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private  Long id=null;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

   /**
    * 说明:分类名
    * 属性名: name
    * 类型: String
    * 数据库字段:name
    * @author haipenge
    */
    @Column(name="name")
	private  String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

	
   /**
    * 说明:排序值
    * 属性名: orderIndex
    * 类型: Integer
    * 数据库字段:order_index
    * @author haipenge
    */
    @Column(name="order_index")
	private  Integer orderIndex;
	public Integer getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
	
}/**@generate-entity-source@**/
	

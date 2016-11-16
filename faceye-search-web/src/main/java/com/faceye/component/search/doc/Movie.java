package com.faceye.component.search.doc;

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
 * Movie ORM 实体
 * 数据库表:search_movie
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */
@Document(collection="search_movie")
public class Movie implements Serializable {

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
    * 说明:名称
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
    * 说明:导演
    * 属性名: director
    * 类型: String
    * 数据库字段:director
    * @author haipenge
    */
    
	private  String director;
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	

	
   /**
    * 说明:演员
    * 属性名: actor
    * 类型: String
    * 数据库字段:actor
    * @author haipenge
    */
    
	private  String actor;
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	

	
   /**
    * 说明:分类
    * 属性名: categoryName
    * 类型: String
    * 数据库字段:category
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
    * 说明:语言
    * 属性名: language
    * 类型: String
    * 数据库字段:language
    * @author haipenge
    */
    
	private  String language;
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * 上影时间
	 */
	private Date onlineDate=null;
	public Date getOnlineDate() {
		return onlineDate;
	}
	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}
	/**
	 * 产地-地区
	 */
	private String area="";
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	/**
	 * 影片简介
	 */
	private String remark="";
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 时长
	 */
	private String totalMinutes="";
	public String getTotalMinutes() {
		return totalMinutes;
	}
	public void setTotalMinutes(String totalMinutes) {
		this.totalMinutes = totalMinutes;
	}
	/**
	 * 是否已推送到线上
	 */
	private Boolean isPush=false;
	public Boolean getIsPush() {
		return isPush;
	}
	public void setIsPush(Boolean isPush) {
		this.isPush = isPush;
	}
	
	/**
	 * 更多描述，SEO优化
	 */
	private String description="";
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}/**@generate-entity-source@**/
	

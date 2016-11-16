package com.faceye.component.search.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.faceye.component.spider.entity.CrawlResult;

/**
 * Book ORM 实体
 * 数据库表:search_book
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */
@Entity
@Table(name = "search_book")
public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8926119711730830203L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = null;

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
	@Column(name = "name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 小说作者
	 */
	@Column(name = "author")
	private String author = "";

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * 小说封面
	 */
	@Column(name = "pic")
	private String pic = "";

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	/**
	 * 小说概述
	 */
	@Lob
	@Column(name = "content")
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	   * 说明:所属分类
	   * 属性名: category
	   * 类型: Category
	   * 数据库字段:category_id
	   * @author haipenge
	   */
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "category_id", nullable = true, updatable = true)
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	/**
	 * 存储小说封面页链接
	 */
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "crawl_result_id", nullable = true, updatable = true)
	private CrawlResult crawlResult=null;

	public CrawlResult getCrawlResult() {
		return crawlResult;
	}

	public void setCrawlResult(CrawlResult crawlResult) {
		this.crawlResult = crawlResult;
	}
    
}
/**@generate-entity-source@**/


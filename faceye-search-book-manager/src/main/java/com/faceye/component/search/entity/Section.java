package com.faceye.component.search.entity;

import java.io.Serializable;
import java.util.Date;

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

/**
 * Section ORM 实体
 * 数据库表:search_section
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */
@Entity
@Table(name = "search_section")
public class Section implements Serializable {

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
	 * 说明:序号
	 * 属性名: indexNum
	 * 类型: Integer
	 * 数据库字段:index_num
	 * @author haipenge
	 */
	@Column(name = "index_num")
	private Integer indexNum;

	public Integer getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(Integer indexNum) {
		this.indexNum = indexNum;
	}

	/**
	 * 说明:创建日期
	 * 属性名: createDate
	 * 类型: Date
	 * 数据库字段:create_date
	 * @author haipenge
	 */
	@Column(name = "create_date")
	private Date createDate;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 说明:内容
	 * 属性名: content
	 * 类型: String
	 * 数据库字段:content
	 * @author haipenge
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

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "book_id", nullable = true, updatable = true)
	private Book book = null;

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

}
/**@generate-entity-source@**/


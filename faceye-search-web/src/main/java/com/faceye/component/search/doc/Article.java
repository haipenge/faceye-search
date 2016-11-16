package com.faceye.component.search.doc;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 存储在mogo里的文章结构
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年8月9日
 */
@Document(collection="search_article")
public class Article implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	// 文章标题
//	@Indexed
	private String name="";
	//文章关键字，用于 SEO,meta:keywords
	private String keywords="";
	//文章摘要,用于meta:description:
	private String description="";
	//文章别名
//	@Indexed
	private String alias="";
	// 文章内容
	private String content="";
	//创建日期
//	@Indexed
	private Date createDate=new Date();
	// 点击次数
//	@Indexed
	private Integer clickCount = 0;
	// 分类ID
//	@Indexed
	private Long categoryId=null;
	// 分类名
	private String categoryName="";
	//分类别名
	private String categoryAlias="";
	//专题ID
//	@Indexed
	private Long subjectId=null;
	//专题名
	private String subjectName="";
	//专题别名
	private String subjectAlias="";
	//来源URL
	@Indexed
	private String sourceUrl="";
	
	/**
	 * 是否已创建了索引
	 */
	private Boolean isIndexed=Boolean.FALSE;
	
	/**
	 * 是否分享到微信
	 */
	private Boolean isWeixin=Boolean.FALSE;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getCategoryAlias() {
		return categoryAlias;
	}

	public void setCategoryAlias(String categoryAlias) {
		this.categoryAlias = categoryAlias;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectAlias() {
		return subjectAlias;
	}

	public void setSubjectAlias(String subjectAlias) {
		this.subjectAlias = subjectAlias;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getIsIndexed() {
		return isIndexed;
	}

	public void setIsIndexed(Boolean isIndexed) {
		this.isIndexed = isIndexed;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public Boolean getIsWeixin() {
		return isWeixin;
	}

	public void setIsWeixin(Boolean isWeixin) {
		this.isWeixin = isWeixin;
	}

	
	
}

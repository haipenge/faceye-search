package com.faceye.component.parse.doc;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.faceye.feature.util.MD5Utils;

@Document(collection = "spider_parse_result_union")
@CompoundIndexes({ @CompoundIndex(name = "global_index", def = "{'categoryId':1,'level':1,'siteId':1}",background=true) })
public class ParseResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3203460817244723518L;

	@Id
	@Indexed(direction=IndexDirection.DESCENDING,background=true)
	private Long id = null;

	/**
	 * 说明:标题
	 * 属性名: name
	 * 类型: String
	 * 数据库字段:name
	 * @author haipenge
	 */
	// @Indexed
	private String name;

	/**
	 * html keywords:
	 */
	private String keywords = "";

	public String description = "";

	/**
	 * 文章摘要
	 */
	public String summary = "";

	/**
	 * 说明:内容
	 * 属性名: content
	 * 类型: String
	 * 数据库字段:content
	 * @author haipenge
	 */
	private String content;

	/**
	 * 说明:解析日期
	 * 属性名: createDate
	 * 类型: Date
	 * 数据库字段:create_date
	 * @author haipenge
	 */
	// @Indexed
	private Date createDate;

	/**
	 * 是否包含禁词
	 */
	// @Indexed
	private Boolean isContainsFilterWord = Boolean.FALSE;

	/**
	 * 说明:爬取对像
	 * 属性名: crawlResult
	 * 类型: CrawlResult
	 * 数据库字段:crawl_result_id
	 * @author haipenge
	 */
//	@Indexed(background=true)
	private Long crawlResultId = null;

	/**
	 * 是否已推送到Mogo
	 */
	// @Indexed
	private Boolean isPush2Mongo = Boolean.FALSE;

	/**
	 * 分类相关信息
	 */
	
	private Long categoryId = null;
	private String categoryName = "";
	private String categoryAlias = "";

	/**
	 * 专题相关信息
	 */
	 //@Indexed
	private Long subjectId = null;
	private String subjectName = "";
	private String subjectAlias = "";

	/**
	 * 源URL
	 */
	// @Indexed
	private String sourceUrl = "";

	/**
	 * 文章 级别
	 * 1级：不包含禁词，标题长度大于10个符号，内容长度大于500个字符，归属于具体分类
	 * 2级：不包含禁词，标题长度大于10个字符，内容长度在200-500字符，归属于具体分类
	 * 3级：不包含禁词，标题长度大于10个字符，内容长度大于500个字符，归属于其它分类
	 * 4级：不包含禁词，标题长度小于10个字符，长度不限，有分类或无分类
	 * -------------------------------------------------------------------
	 * 5级:包含禁词,标题长度大于10个符号，内容长度大于500个字符。归属于具体分类
	 * 6级,包含禁词，标题长度大于10字符,内容长度在200-500字符，归属于具体分类
	 * 7级,包含禁词，标题长度大于10个字符，内容长度大于500字符，归属于其它分类
	 * 8级,包含禁词，标题长度小于10个字符，长度不限。有分类或无分类
	 */
	
	private Integer level = 0;

	/**
	 * 是否成功推送到生产环境
	 */
//	 @Indexed
	private Boolean isPush2ProductEnv = Boolean.FALSE;

	/**
	 * 是否审核通过
	 */
	// @Indexed
	private Boolean isAllow = Boolean.FALSE;

	/**
	 * 是否已删除
	 * 逻辑删除
	 */
	// @Indexed
	private Boolean isRemove = Boolean.FALSE;

	/**
	 * 站点
	 */
	
	private Long siteId = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.setSign(MD5Utils.md5(name));
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getIsContainsFilterWord() {
		return isContainsFilterWord;
	}

	public void setIsContainsFilterWord(Boolean isContainsFilterWord) {
		this.isContainsFilterWord = isContainsFilterWord;
	}

	public Long getCrawlResultId() {
		return crawlResultId;
	}

	public void setCrawlResultId(Long crawlResultId) {
		this.crawlResultId = crawlResultId;
	}

	public Boolean getIsPush2Mongo() {
		return isPush2Mongo;
	}

	public void setIsPush2Mongo(Boolean isPush2Mongo) {
		this.isPush2Mongo = isPush2Mongo;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public Boolean getIsPush2ProductEnv() {
		return isPush2ProductEnv;
	}

	public void setIsPush2ProductEnv(Boolean isPush2ProductEnv) {
		this.isPush2ProductEnv = isPush2ProductEnv;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Boolean getIsAllow() {
		return isAllow;
	}

	public void setIsAllow(Boolean isAllow) {
		this.isAllow = isAllow;
	}

	public Boolean getIsRemove() {
		return isRemove;
	}

	public void setIsRemove(Boolean isRemove) {
		this.isRemove = isRemove;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	/**
	 * 说明:是否微信分享
	 * 属性名: isWeixin
	 * 类型: Boolean
	 * 数据库字段:is_weixin
	 * @author haipenge
	 */

	private Boolean isWeixin = Boolean.FALSE;

	public Boolean getIsWeiXin() {
		return isWeixin;
	}

	public void setIsWeiXin(Boolean isWeixin) {
		this.isWeixin = isWeixin;
	}
	
	/**
	 * 文章标题的MD5值
	 */
	@Indexed
	private String sign="";

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	

}
/**@generate-entity-source@**/


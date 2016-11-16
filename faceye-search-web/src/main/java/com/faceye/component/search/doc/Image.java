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
 * Image ORM 实体
 * 数据库表:parse_image
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */
@Document(collection = "spider_parse_image")
public class Image implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8926119711730830203L;
	@Id
	private Long id = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 说明:存储路径
	 * 属性名: storePath
	 * 类型: String
	 * 数据库字段:store_path
	 * @author haipenge
	 */

	private String storePath;

	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

	/**
	 * 图片对外访问路径
	 */
	private String url = "";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	    * 说明:原链接
	    * 属性名: sourceUrl
	    * 类型: String
	    * 数据库字段:source_url
	    * @author haipenge
	    */

	private String sourceUrl;

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	/**
	 * 说明:解析结果ID
	 * 属性名: parseResultId
	 * 类型: Long
	 * 数据库字段:parse_result_id
	 * @author haipenge
	 */

	private Long parseResultId;

	public Long getParseResultId() {
		return parseResultId;
	}

	public void setParseResultId(Long parseResultId) {
		this.parseResultId = parseResultId;
	}

	/**
	 * 说明:链接ID
	 * 属性名: linkId
	 * 类型: Long
	 * 数据库字段:link_id
	 * @author haipenge
	 */

	private Long linkId;

	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}

	/**
	 * 说明:爬取结果ID
	 * 属性名: crawlResultId
	 * 类型: Long
	 * 数据库字段:crawl_result_id
	 * @author haipenge
	 */

	private Long crawlResultId;

	public Long getCrawlResultId() {
		return crawlResultId;
	}

	public void setCrawlResultId(Long crawlResultId) {
		this.crawlResultId = crawlResultId;
	}

	/**
	 * 相关文章 ID
	 */
	private Long articleId = null;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

}
/**@generate-entity-source@**/


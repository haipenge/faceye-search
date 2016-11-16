package com.faceye.component.spider.doc;

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
 * MatcherConfig ORM 实体
 * 数据库表:spider_matcherConfig
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */
@Document(collection="spider_matcherConfig")
public class MatcherConfig implements Serializable {

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
    * 说明:正则表达式
    * 属性名: regexp
    * 类型: String
    * 数据库字段:regexp
    * @author haipenge
    */
    
	private  String regexp;
	public String getRegexp() {
		return regexp;
	}
	public void setRegexp(String regexp) {
		this.regexp = regexp;
	}
	

	
   /**
    * 说明:站ID
    * 属性名: siteId
    * 类型: Long
    * 数据库字段:site_id
    * @author haipenge
    */
    
	private  Long siteId;
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	

	
   /**
    * 说明:页面匹配类型
    * 属性名: type
    * 类型: Integer
    * 数据库字段:type
    * @author haipenge
    */
    
	private  Integer type;
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	

	
   /**
    * 说明:前缀
    * 属性名: prefix
    * 类型: String
    * 数据库字段:prefix
    * @author haipenge
    */
    
	private  String prefix;
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	

	
   /**
    * 说明:缀
    * 属性名: suffix
    * 类型: String
    * 数据库字段:suffix
    * @author haipenge
    */
    
	private  String suffix;
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	

	
   /**
    * 说明:标题匹配
    * 属性名: titleRegexp
    * 类型: String
    * 数据库字段:title_regexp
    * @author haipenge
    */
    
	private  String titleRegexp;
	public String getTitleRegexp() {
		return titleRegexp;
	}
	public void setTitleRegexp(String titleRegexp) {
		this.titleRegexp = titleRegexp;
	}
	

	
   /**
    * 说明:解析后的链接类型
    * 属性名: typeOfLinkAfterParse
    * 类型: Integer
    * 数据库字段:type_of_link_after_parse
    * @author haipenge
    */
    
	private  Integer typeOfLinkAfterParse;
	public Integer getTypeOfLinkAfterParse() {
		return typeOfLinkAfterParse;
	}
	public void setTypeOfLinkAfterParse(Integer typeOfLinkAfterParse) {
		this.typeOfLinkAfterParse = typeOfLinkAfterParse;
	}
	
}/**@generate-entity-source@**/
	

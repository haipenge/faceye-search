package com.faceye.component.search.doc;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Email ORM 实体
 * 数据库表:search_email
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */
@Document(collection = "search_email")
@CompoundIndexes({ @CompoundIndex(name = "c_index", def = "{'address': 1, 'from': -1}") })
public class Email implements Serializable {

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
	 * 说明:地址
	 * 属性名: address
	 * 类型: String
	 * 数据库字段:address
	 * @author haipenge
	 */

	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 邮箱来源,0,爬虫添加，1:csdn,2:xiaomi
	 */
	private Integer from = 0;

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	@Transient
	private String fromName = "爬取";

	public String getFromName() {
		if (this.getFrom().intValue() == 1) {
			fromName = "CSDN";
		} else if (this.getFrom().intValue() == 2) {
			fromName = "Xiaomi";
		}
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	/**
	 * 创建日期
	 */
	private Date createDate = new Date();

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
/**@generate-entity-source@**/


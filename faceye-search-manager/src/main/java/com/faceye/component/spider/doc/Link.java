package com.faceye.component.spider.doc;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.faceye.feature.util.MD5Utils;

@Document(collection = "spider_link")
@CompoundIndexes({ @CompoundIndex(name = "global_index", def = "{'_id': -1, 'siteId': -1,'type':1,'mimeType':1,'url':1,'distributeChannel':1}") })
public class Link implements java.io.Serializable {
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
	 * 属性名: url
	 * 类型: String
	 * 数据库字段:url
	 * @author haipenge
	 */
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		this.setSign(MD5Utils.md5(url));
	}

	/**
	 * 说明:是否已爬取
	 * 属性名: isCrawled
	 * 类型: Boolean
	 * 数据库字段:is_crawl_ed
	 * @author haipenge
	 */
	private Boolean isCrawled = false;

	public Boolean getIsCrawled() {
		return isCrawled;
	}

	public void setIsCrawled(Boolean isCrawled) {
		this.isCrawled = isCrawled;
	}

	/**
	 * 说明:创建日期
	 * 属性名: createDate
	 * 类型: Date
	 * 数据库字段:create_date
	 * @author haipenge
	 */
	private Date createDate = new Date();

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 说明:是否爬取成功
	 * 属性名: isCrawlSuccess
	 * 类型: Boolean
	 * 数据库字段:is_crawl_success
	 * @author haipenge
	 */
	private Boolean isCrawlSuccess = false;

	public Boolean getIsCrawlSuccess() {
		return isCrawlSuccess;
	}

	public void setIsCrawlSuccess(Boolean isCrawlSuccess) {
		this.isCrawlSuccess = isCrawlSuccess;
	}

	/**
	 * 说明:最后爬取时间
	 * 属性名: lastCrawlDate
	 * 类型: Date
	 * 数据库字段:last_crawl_date
	 * @author haipenge
	 */
	private Date lastCrawlDate;

	public Date getLastCrawlDate() {
		return lastCrawlDate;
	}

	public void setLastCrawlDate(Date lastCrawlDate) {
		this.lastCrawlDate = lastCrawlDate;
	}

	/**
	 * 链接类型：0->种子链接,1-》列表页,2-》明细页,3->Cnblogs博客园子首页(将逐渐废弃）
	 */
	@Indexed
	private Integer type = null;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 所属站点ID
	 */
	@Indexed
	private Long siteId = null;

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	private Long parentId = null;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * 文件类型
	 * 0,网页，1，图片
	 */
	private Integer mimeType = 0;

	public Integer getMimeType() {
		return mimeType;
	}

	public void setMimeType(Integer mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * 是否已进行分发爬取
	 * 
	 */
	private Boolean isDistributed = Boolean.FALSE;

	

	public Boolean getIsDistributed() {
		return isDistributed;
	}

	public void setIsDistributed(Boolean isDistributed) {
		this.isDistributed = isDistributed;
	}

	/**
	 * 分发时间
	 */
	private Date distributeDate = null;

	public Date getDistributeDate() {
		return distributeDate;
	}

	public void setDistributeDate(Date distributeDate) {
		this.distributeDate = distributeDate;
	}

	/**
	 * 分发渠道
	 */
	private String distributeChannel = null;

	public String getDistributeChannel() {
		return distributeChannel;
	}

	public void setDistributeChannel(String distributeChannel) {
		this.distributeChannel = distributeChannel;
	}

   /**
    * 说明:是否微信
    * 属性名: isWeixin
    * 类型: Boolean
    * 数据库字段:is_weixin
    * @author haipenge
    */
    
	private  Boolean isWeixin=Boolean.FALSE;
	public Boolean getIsWeixin() {
		return isWeixin;
	}
	public void setIsWeixin(Boolean isWeixin) {
		this.isWeixin = isWeixin;
	}
	/**
	 * URL的MD5签名
	 */
	@Indexed
	private String sign="";

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
}/**@generate-entity-source@**/
	

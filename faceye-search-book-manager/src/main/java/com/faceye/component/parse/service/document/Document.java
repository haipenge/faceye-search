package com.faceye.component.parse.service.document;

import java.util.List;

import com.faceye.component.parse.service.MetaData;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.service.domain.DomainLink;
/**
 * 解析网页取得的Document信息
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年3月7日
 */
public class Document implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1346198746894357124L;
	
	//被解析页面
	private CrawlResult crawlResult=null;
	//解析后获取的页面链接
	private List<DomainLink> links=null;
	//网页标题
	private String title="";
	//Meta信息
	private MetaData metaData=null;
	//解析后提取的body信息
	private String body="";
	public CrawlResult getCrawlResult() {
		return crawlResult;
	}
	public void setCrawlResult(CrawlResult crawlResult) {
		this.crawlResult = crawlResult;
	}
	public List<DomainLink> getLinks() {
		return links;
	}
	public void setLinks(List<DomainLink> links) {
		this.links = links;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public MetaData getMetaData() {
		return metaData;
	}
	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	
    

}

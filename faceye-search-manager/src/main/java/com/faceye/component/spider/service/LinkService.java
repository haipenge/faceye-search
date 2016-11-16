package com.faceye.component.spider.service;

import java.util.List;
import java.util.Map;

import com.faceye.component.parse.service.document.Document;
import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.domain.DomainLink;
import com.faceye.feature.service.BaseService;

public interface LinkService extends BaseService<Link,Long>{
  
	
	/**
	 * 判断链接 是否已存在
	 * @todo
	 * @param url
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月11日
	 */
	public Boolean isLinkExist(String url);
	
	/**
	 * 批量保存链接
	 * @todo
	 * @param links
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月19日
	 */
	public void saveDomainLinks(List<DomainLink> links,Site site);

	
	
	/**
	 * 取得待爬取的URL集合
	 * @todo
	 * @param searchParams
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年2月2日
	 */
	public List<Link> getLinks2Crawl(Map searchParams);
	
	
	/**
	 * 保存新链接
	 * @todo
	 * @param url
	 * @param siteId
	 * @param type
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年2月9日
	 */
	public void saveLink(String url,Long siteId,Integer type);
	
	/**
	 * 保存新链接
	 * @todo
	 * @param url
	 * @param siteId
	 * @param type
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年2月9日
	 */
	public void saveLink(String url,Long siteId,Integer type,String categoryType);
	
	/**
	 * 
	 * @todo
	 * @param url
	 * @param siteId
	 * @param type
	 * @param mimeType
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年2月9日
	 */
	public void saveLink(String url,Long siteId,Integer type,Integer mimeType);
	
	/**
	 * 重置种子链接
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月12日
	 */
	public void resetSeedLinks();
	
	
	/**
	 * 为各爬取渠道分发链接
	 * @todo
	 * @param channel
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月27日
	 */
	public List getDistributeLinks(String channel);
	
	
	public Link getLinkBySign(String sign);
	
}/**@generate-service-source@**/

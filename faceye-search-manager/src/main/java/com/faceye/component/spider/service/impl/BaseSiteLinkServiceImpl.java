package com.faceye.component.spider.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteService;
import com.faceye.component.spider.service.domain.DomainLink;
/**
 * 站点管理
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年1月1日
 */
public abstract class BaseSiteLinkServiceImpl {
	protected Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	protected SiteService siteService=null;
	@Autowired
	protected LinkService linkService=null;
	/**
	 * 网站域名
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月1日
	 */
	abstract protected String getDomain();
	
	/**
	 * 取得待初始化的URL集合
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月1日
	 */
	abstract protected List<DomainLink> getDomainLinks2Save();
	
	/**
	 * 取得需要重置的URL集合
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月1日
	 */
	abstract protected List<Link> getLinks2Reset();
	
	
	/**
	 * 初始化网站链接
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月1日
	 */
	public void saveInitLinks() {
		List<DomainLink> links=this.getDomainLinks2Save();
		if(CollectionUtils.isNotEmpty(links)){
			Site site=this.getSite();
			for(DomainLink domainLink:links){
				String url=domainLink.getUrl();
				Integer type=domainLink.getType();
				boolean isExist=this.linkService.isLinkExist(url);
				if(!isExist){
//					this.linkService.saveLink(url, site.getId(), type);
					this.linkService.saveLink(url, site.getId(), type, domainLink.getCategoryKey());
				}
			}
		}
	}
	/**
	 * 根据重新爬取规则,重置爬取链接
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月1日
	 */
	public void reInitLinks() {
		List<Link> needResetLinks=this.getLinks2Reset();
		if(CollectionUtils.isNotEmpty(needResetLinks)){
			for(Link link:needResetLinks){
				link.setIsCrawled(false);
				link.setIsCrawlSuccess(false);
				//重新设置时间，保证为新链接，可以被最先爬取到
				link.setCreateDate(new Date());
				this.linkService.save(link);
			}
		}
	}
	
	/**
	 * 取得网站对像
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月1日
	 */
	public synchronized Site getSite() {
		Site site=this.siteService.getSiteByName(getDomain());
		if(null==site && StringUtils.isNotEmpty(getDomain())){
			site=new Site();
			site.setName(getDomain());
			site.setUrl(getDomain());
			this.siteService.save(site);
		}
		return site;
	} 
}

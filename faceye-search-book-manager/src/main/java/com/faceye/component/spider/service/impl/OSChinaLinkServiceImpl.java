package com.faceye.component.spider.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteLinkService;
import com.faceye.component.spider.service.SiteService;
import com.faceye.component.spider.service.domain.DomainLink;
import com.faceye.component.spider.service.domain.LinkBuilder;

/**
 * OSChina的链接管理实现类
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年12月10日
 */
@Service("OSChinaLinkServiceImpl")
public class OSChinaLinkServiceImpl extends BaseSiteLinkServiceImpl implements SiteLinkService {
//	private final static String OSCHINA = "OSChina";
	@Value("#{property['domain.oschina.net']}")
	private String domain="";
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SiteService siteService = null;
	@Autowired
	private LinkService linkService = null;

	/**
	 * 是否重新爬取URL
	 * @todo
	 * @param url
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年12月29日
	 */
	private boolean isResetLink(String url) {
		boolean isReset = Boolean.FALSE;
		if (StringUtils.isNotEmpty(url)) {
			if (url.endsWith("p=1") || url.endsWith("p=2") || url.endsWith("p=3")) {
				isReset = Boolean.TRUE;
			}
			if (!isReset && (url.indexOf("p=1#") != -1 || url.indexOf("p=2#") != -1 || url.indexOf("p=3#") != -1)) {
				isReset = Boolean.TRUE;
			}
		}
		return isReset;
	}

	

	@Override
	protected String getDomain() {
		return domain;
	}

	@Override
	protected List<DomainLink> getDomainLinks2Save() {
		List<DomainLink> links=new ArrayList<DomainLink>(0);
		Site site = this.getSite();
		for (int i = 1; i <= 25; i++) {
			String url = "http://www.oschina.net/blog?type=0&p=" + i + "#catalogs";
			LinkBuilder.getInstance().build(links, url, 1);
		}
		return links;
	}

	@Override
	protected List<Link> getLinks2Reset() {
		List<Link> links=new ArrayList<Link>(0);
		Site site = this.getSite();
		// while (isContinue) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ|site.id", site.getId());
		searchParams.put("EQ|type", new Integer(1));
		searchParams.put("ISTRUE|isCrawled", new Integer(1));
		Page<Link> page = this.linkService.getPage(searchParams, 1, 0);
		for(Link link:page.getContent()){
			if(this.isResetLink(link.getUrl())){
				links.add(link);
			}
		}
		return links;
	}

}

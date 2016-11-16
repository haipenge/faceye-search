package com.faceye.component.spider.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.SiteLinkService;
import com.faceye.component.spider.service.domain.DomainLink;
import com.faceye.component.spider.service.domain.LinkBuilder;

/**
 * 爬取51 cto
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年9月16日
 */
@Service("ctoLinkService")
public class CTOLinkServiceImpl extends BaseSiteLinkServiceImpl implements SiteLinkService {
    @Value("#{property['domain.51cto.com']}")
	private String domain="";
	
	@Override
	protected String getDomain() {
		return domain;
	}

	/**
	 * 1,列表页
	 */
	@Override
	protected List<DomainLink> getDomainLinks2Save() {
		List<DomainLink> links=new ArrayList<DomainLink>(0);
		LinkBuilder.getInstance().build(links,"http://blog.51cto.com/original/", 1);
		return links;
	}

	@Override
	protected List<Link> getLinks2Reset() {
		List<Link> links=new ArrayList<Link>(0);
		Site site = this.getSite();
		Map searchParams = new HashMap();
		searchParams.put("EQ|site.id", site.getId());
		searchParams.put("EQ|type", new Integer(1));
		searchParams.put("ISTRUE|isCrawled", new Integer(1));
		Page<Link> page = this.linkService.getPage(searchParams, 1, 1500);
		if(CollectionUtils.isNotEmpty(page.getContent())){
			for(Link link:page.getContent()){
				String url =link.getUrl();
				//http://blog.51cto.com/original/6
				String regexp="^http:\\/\\/blog\\.51cto\\.com\\/original\\/[\\d]+\\/[1-5]$";
				boolean isMatch=RegexpUtil.isMatch(url, regexp);
				if(isMatch){
					links.add(link);
				}
			}
		}
		return null;
	}
   

}

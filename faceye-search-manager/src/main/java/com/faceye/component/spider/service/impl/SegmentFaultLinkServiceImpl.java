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

@Service("segmentFaultLinkServiceImpl")
public class SegmentFaultLinkServiceImpl extends BaseSiteLinkServiceImpl implements SiteLinkService {
	@Value("#{property['domain.segmentfault.com']}")
	private String domain = "";

	@Override
	protected String getDomain() {
		return domain;
	}

	@Override
	protected List<DomainLink> getDomainLinks2Save() {
		// http://segmentfault.com/blogs/recommend?page=2
		List<DomainLink> links = new ArrayList<DomainLink>();
		for (int i = 1; i <= 144; i++) {
			String url = "http://segmentfault.com/blogs/recommend?page=" + i;
			LinkBuilder.getInstance().build(links, url, 1);
		}
		for(int i=1;i<388;i++){
			//http://segmentfault.com/blogs/newest?page=2
			String url="http://segmentfault.com/blogs/newest?page="+i;
			LinkBuilder.getInstance().build(links, url, 1);
		}
		return links;
	}

	@Override
	protected List<Link> getLinks2Reset() {
		List<Link> links=new ArrayList<Link>();
		Map searchParams=new HashMap();
		Site site=this.getSite();
		if(null!=site){
			searchParams.put("EQ|site.id", site.getId());
			searchParams.put("EQ|type", 1);
		}
		Page<Link> page=this.linkService.getPage(searchParams, 1, 0);
		if(null!=page && CollectionUtils.isNotEmpty(page.getContent())){
			for(Link link :page.getContent()){
				String url=link.getUrl();
				String regexp="^[\\w].+page=[\\d]{1}$";
				boolean isMatch=RegexpUtil.isMatch(url, regexp);
				if(isMatch){
					links.add(link);
				}
			}
		}
		return links;
	}

}

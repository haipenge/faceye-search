package com.faceye.component.spider.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.service.SiteLinkService;
import com.faceye.component.spider.service.domain.DomainLink;
import com.faceye.component.spider.service.domain.LinkBuilder;

@Service("w3SchoolLinkServiceImpl")
public class W3SchoolLinkServiceImpl extends BaseSiteLinkServiceImpl implements SiteLinkService {
	@Value("#{property['domain.w3school']}")
	private String domain = "";
	@Override
	protected String getDomain() {
		return domain;
	}

	@Override
	protected List<DomainLink> getDomainLinks2Save() {
		List<DomainLink> domainLinks=new ArrayList<DomainLink>();
		//html
		LinkBuilder.getInstance().build(domainLinks, "http://www.w3school.com.cn/xhtml/index.asp", 1);
		//xhtml
		LinkBuilder.getInstance().build(domainLinks, "http://www.w3school.com.cn/xhtml/index.asp", 1);
		//html5
		LinkBuilder.getInstance().build(domainLinks, "http://www.w3school.com.cn/html5/index.asp", 1);
		//css
		LinkBuilder.getInstance().build(domainLinks, "http://www.w3school.com.cn/css/index.asp", 1);
		//css3
		LinkBuilder.getInstance().build(domainLinks, "http://www.w3school.com.cn/css3/index.asp", 1);
		//javascript
		LinkBuilder.getInstance().build(domainLinks, "http://www.w3school.com.cn/js/index.asp", 1);
		//html dom
		LinkBuilder.getInstance().build(domainLinks, "http://www.w3school.com.cn/htmldom/index.asp", 1);
		//jQuery
		LinkBuilder.getInstance().build(domainLinks, "http://www.w3school.com.cn/jquery/index.asp", 1);
		//ajax
		LinkBuilder.getInstance().build(domainLinks, "http://www.w3school.com.cn/ajax/index.asp", 1);
		//json
		LinkBuilder.getInstance().build(domainLinks, "http://www.w3school.com.cn/json/index.asp", 1);
		//xml
		LinkBuilder.getInstance().build(domainLinks, "http://www.w3school.com.cn/xml/index.asp", 1);
		//xsl
		LinkBuilder.getInstance().build(domainLinks, "http://www.w3school.com.cn/xsl/xsl_languages.asp", 1);
		//SQL
		LinkBuilder.getInstance().build(domainLinks, "http://www.w3school.com.cn/sql/index.asp", 1);
		//php
		LinkBuilder.getInstance().build(domainLinks, "http://www.w3school.com.cn/php/index.asp", 1);
		//asp
		LinkBuilder.getInstance().build(domainLinks, "http://www.w3school.com.cn/asp/index.asp", 1);
		//asp.net
	    LinkBuilder.getInstance().build(domainLinks, "http://www.w3school.com.cn/aspnet/index.asp", 1);
	    LinkBuilder.getInstance().build(domainLinks, "http://www.w3school.com.cn/aspnet/webpages_intro.asp", 1);
		return domainLinks;
	}
	@Override
	protected List<Link> getLinks2Reset() {
		return null;
	}

}

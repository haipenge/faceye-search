package com.faceye.component.spider.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.service.SiteLinkService;
import com.faceye.component.spider.service.domain.DomainLink;
import com.faceye.component.spider.service.domain.LinkBuilder;

@Service("yiibaiLinkServiceImpl")
public class YiibaiLinkServiceImpl extends BaseSiteLinkServiceImpl implements SiteLinkService {
	@Value("#{property['domain.yiibai.com']}")
    private String domain="";
	@Override
	protected String getDomain() {
		return domain;
	}

	@Override
	protected List<DomainLink> getDomainLinks2Save() {
		List<DomainLink> domainLinks=new ArrayList<DomainLink>();
		//数据库类
		//memcached
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/memcached/", 1);
		//MongoDB
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/mongodb/", 1);
		//postgresql
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/postgresql/", 1);
		//Sqlite
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/sqlite/", 1);
		//DB2
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/db2/", 1);
		//Redis
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/redis/", 1);
		//MySQL
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/mysql/", 1);
	   //python
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/python/", 1);
		//ruby
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/ruby/", 1);
		//swift
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/swift/", 1);
		//svn 
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/html/svn/", 1);
		//git
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/git/", 1);
		//shell
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/shell/", 1);
		//oc
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/objective_c/", 1);
		//php
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/php/", 1);
		//lua
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/lua/", 1);
		//Tcl
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/tcl/", 1);
	   //JAVA
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/java/", 1);
		//json
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/json/", 1);
	    //testng
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/testng/", 1);
		//jmeter
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/jmeter/", 1);
		//struts2
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/struts_2/", 1);
		//maven
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/maven/", 1);
		//javamail
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/javamail_api/", 1);
		//spring
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/spring/", 1);
		//jsp
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/jsp/", 1);
		//ant
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/ant/", 1);
		//ibatis
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/ibatis/", 1);
		//hibernate
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/hibernate/", 1);
		//jdbc
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/jdbc/", 1);
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/jasper_reports/", 1);
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/javaexamples/", 1);
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/mybatis/", 1);
		//lucene
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/lucene/", 1);
		//jfreechat
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/jfreechart/", 1);
		//jpa
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/jpa/", 1);
		//tika
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/tika/", 1);
		//easymock
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/easymock/", 1);
		//jopl
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/jogl/", 1);
		//guava
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/guava/", 1);
		//java8
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/java8/", 1);
		//stream
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/xstream/", 1);
		//java xml
		LinkBuilder.getInstance().build(domainLinks, "http://www.yiibai.com/java_xml/", 1);
		return domainLinks;
	}

	@Override
	
	protected List<Link> getLinks2Reset() {
		return null;
	}

}

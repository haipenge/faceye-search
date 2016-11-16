package com.faceye.component.spider.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
 * CSDN的链接初始化
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年8月14日
 */
@Service("CSDNLinkServiceImpl")
public class CSDNLinkServiceImpl extends BaseSiteLinkServiceImpl implements SiteLinkService {

//	private static String CSDN = "CSND";
	@Value("#{property['domain.csdn.net']}")
	private String domain="";
	private static String CSDN_BLOG_HOME = "http://blog.csdn.net/index.html";
	@Autowired
	private SiteService siteService = null;

	@Autowired
	private LinkService linkService = null;


	

	private List<BlogCategory> buildBlogCategory() {
		List<BlogCategory> items = new ArrayList<BlogCategory>();
		// Mobile
		BlogCategory mobile = new BlogCategory();
		mobile.setCategory("mobile");
		mobile.setMaxPage(15);
		items.add(mobile);
		// Web
		BlogCategory web = new BlogCategory();
		web.setCategory("web");
		web.setMaxPage(7);
		items.add(web);
		// enterprise架构
		BlogCategory enterprise = new BlogCategory();
		enterprise.setCategory("enterprise");
		enterprise.setMaxPage(3);
		items.add(enterprise);
		// code 编程语言
		BlogCategory code = new BlogCategory();
		code.setCategory("code");
		code.setMaxPage(40);
		items.add(code);
		// www.互联网
		BlogCategory www = new BlogCategory();
		www.setCategory("www");
		www.setMaxPage(4);
		items.add(www);
		// database
		BlogCategory database = new BlogCategory();
		database.setCategory("database");
		database.setMaxPage(5);
		items.add(database);
		// system 系统运维
		BlogCategory system = new BlogCategory();
		system.setCategory("system");
		system.setMaxPage(3);
		items.add(system);
		// cloud 云计算
		BlogCategory cloud = new BlogCategory();
		cloud.setCategory("cloud");
		cloud.setMaxPage(3);
		items.add(cloud);
		// software 研发管理
		BlogCategory software = new BlogCategory();
		software.setCategory("software");
		software.setMaxPage(3);
		items.add(software);
		return items;
	}

	class BlogCategory {
		private String category = "";
		private String url = "";
		private Integer maxPage = 0;

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Integer getMaxPage() {
			return maxPage;
		}

		public void setMaxPage(Integer maxPage) {
			this.maxPage = maxPage;
		}

	}

	/**
	 * 判断URL是否要重置
	 * @todo
	 * @param url
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月2日
	 */
	private boolean isReset(String url) {
		boolean res = false;
		if (StringUtils.isNotEmpty(url)) {
			if (StringUtils.endsWith(url, "page=1") || StringUtils.endsWith(url, "page=2") || StringUtils.endsWith(url, "page=3")
					|| StringUtils.endsWith(url, "page=4")|| StringUtils.endsWith(url, "page=5")) {
				res = true;
			}
		}
		return res;
	}

	/**
	 * 初始化网站基础信息
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月14日
	 */


	@Override
	protected String getDomain() {
		return this.domain;
	}

	@Override
	protected List<DomainLink> getDomainLinks2Save() {
		String url = "http://blog.csdn.net/index.html";
		List<DomainLink> links=new ArrayList<DomainLink>(0);
		Site site = this.getSite();
		for (int i = 1; i < 94; i++) {
			String link = url + "?&page=" + i;
			LinkBuilder.getInstance().build(links, link, 1);
		}
		List<BlogCategory> items = this.buildBlogCategory();
		if (CollectionUtils.isNotEmpty(items)) {
			String oUrl = "http://blog.csdn.net/";
			for (BlogCategory category : items) {
				String buildUrl = oUrl + category.getCategory() + "/index.html";
				for (int i = 1; i <= category.getMaxPage(); i++) {
					url = buildUrl + "?&page=" + i;
					LinkBuilder.getInstance().build(links, url, 1);
				}
			}
		}
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
		if (null != page && CollectionUtils.isNotEmpty(page.getContent())) {
			for (Link link : page.getContent()) {
				String url = link.getUrl();
				boolean isReset = this.isReset(url);
				if (isReset) {
					links.add(link);
				}
			}
		}
		return links;
	}
}

package com.faceye.component.spider.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.service.SiteLinkService;
import com.faceye.component.spider.service.domain.DomainLink;
import com.faceye.component.spider.service.domain.LinkBuilder;

@Service
//@Transactional
public class CnBlogsLinkServiceImpl extends BaseSiteLinkServiceImpl implements SiteLinkService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Value("#{property['domain.cnblogs.com']}")
	private String domain="";

	

	/**
	 * 构造首页将要爬取内容
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年12月23日
	 */
	private List<HomeListLink> buildHomeListLink() {
		List<HomeListLink> urls = new ArrayList<HomeListLink>(0);
		// 博客园首页
		HomeListLink home = new HomeListLink("首页", "http://www.cnblogs.com/", 200);
		urls.add(home);
		// 编程语言
		HomeListLink programLanguage = new HomeListLink("编程语言", "http://www.cnblogs.com/cate/2/", 30);
		urls.add(programLanguage);
		// JAVA
		HomeListLink java = new HomeListLink("Java", "http://www.cnblogs.com/cate/java/", 128);
		urls.add(java);
		// PHP
		HomeListLink php = new HomeListLink("PHP", "http://www.cnblogs.com/cate/php/", 60);
		urls.add(php);
		// C++
		HomeListLink cpp = new HomeListLink("CPP", "http://www.cnblogs.com/cate/cpp/", 58);
		urls.add(cpp);
		// python
		HomeListLink python = new HomeListLink("Python", "http://www.cnblogs.com/cate/python/", 30);
		urls.add(python);
		// ruby
		HomeListLink ruby = new HomeListLink("Ruby", "http://www.cnblogs.com/cate/ruby/", 2);
		urls.add(ruby);
		// Web前端
		HomeListLink web = new HomeListLink("Web", "http://www.cnblogs.com/cate/108703/", 32);
		urls.add(web);
		// html/css
		HomeListLink html = new HomeListLink("Html/Css", "http://www.cnblogs.com/cate/web/", 75);
		urls.add(html);
		// javascript
		HomeListLink javascript = new HomeListLink("javascript", "http://www.cnblogs.com/cate/javascript/", 143);
		urls.add(javascript);
		// jQuery
		HomeListLink jQuery = new HomeListLink("jQuery", "http://www.cnblogs.com/cate/jquery/", 60);
		urls.add(jQuery);
		// Html5
		HomeListLink html5 = new HomeListLink("HTML5", "http://www.cnblogs.com/cate/html5/", 26);
		urls.add(html5);
		// 手机开发
		HomeListLink mobile = new HomeListLink("Mobile", "http://www.cnblogs.com/cate/108705/", 24);
		urls.add(mobile);
		// android
		HomeListLink android = new HomeListLink("Android", "http://www.cnblogs.com/cate/android/", 108);
		urls.add(android);
		// ios
		HomeListLink ios = new HomeListLink("IOS", "http://www.cnblogs.com/cate/ios/", 60);
		urls.add(ios);
		// windows phone
		HomeListLink wp = new HomeListLink("Windows Phone", "http://www.cnblogs.com/cate/wp/", 16);
		urls.add(wp);
		// 数据库技术
		HomeListLink database = new HomeListLink("DataBase", "http://www.cnblogs.com/cate/108712/", 10);
		urls.add(database);
		// SqlServer
		HomeListLink sqlServer = new HomeListLink("SqlServer", "http://www.cnblogs.com/cate/sqlserver/", 47);
		urls.add(sqlServer);
		// Oracle
		HomeListLink oracle = new HomeListLink("Oracle", "http://www.cnblogs.com/cate/oracle/", 23);
		urls.add(oracle);
		// MySQL
		HomeListLink mysql = new HomeListLink("MySql", "http://www.cnblogs.com/cate/mysql/", 24);
		urls.add(mysql);
		// nosql
		HomeListLink nosql = new HomeListLink("NoSql", "http://www.cnblogs.com/cate/nosql/", 10);
		urls.add(nosql);
		// OS操作系统
		// linux
		HomeListLink linux = new HomeListLink("Linux", "http://www.cnblogs.com/cate/linux/", 81);
		urls.add(linux);
		// windows
		HomeListLink windows = new HomeListLink("Windows", "http://www.cnblogs.com/cate/win7/", 8);
		urls.add(windows);
		// 精华
		HomeListLink best = new HomeListLink("Best", "http://www.cnblogs.com/pick/", 72);
		urls.add(best);
		return urls;
	}

	/**
	 * 包装cnblogs home列表对像
	 * @author @haipenge 
	 * haipenge@gmail.com
	*  Create Date:2014年12月23日
	 */
	class HomeListLink {
		private String url = "";
		private Integer maxCount = 1;
		private String name = "";

		public HomeListLink(String name, String url, Integer maxCount) {
			this.name = name;
			this.url = url;
			this.maxCount = maxCount;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Integer getMaxCount() {
			return maxCount;
		}

		public void setMaxCount(Integer maxCount) {
			this.maxCount = maxCount;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	
	/**
	 * 是否重置链接
	 * @todo
	 * @param url
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月19日
	 */
	private boolean isReset(String url){
		boolean isReset=false;
		if(url.endsWith("#p2")||url.endsWith("#p3")||url.endsWith("#p4")||url.endsWith("#p5")){
			isReset=Boolean.TRUE;
		}
		if(!isReset){
			if(url.endsWith("/1/")||url.endsWith("/2/")||url.endsWith("/3/")||url.endsWith("/4/")||url.endsWith("/5/")){
				isReset=Boolean.TRUE;
			}
		}
		if(!isReset){
			List<HomeListLink> homeListLinks=this.buildHomeListLink();
			for(HomeListLink homeListLink:homeListLinks){
				if(StringUtils.equals(homeListLink.getUrl(), url)){
					isReset=Boolean.TRUE;
				}
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
		List<HomeListLink> urls = this.buildHomeListLink();
		for (HomeListLink link : urls) {
			String url = link.getUrl();
			//Boolean isExist = this.linkService.isLinkExist(url);
			LinkBuilder.getInstance().build(links, url, 1);
			logger.debug(">>FaceYe --> Now Init Link is :" + url);
			for (int i = 2; i < link.getMaxCount(); i++) {
				String pageUrl = url + "#p" + i;
				LinkBuilder.getInstance().build(links, pageUrl, 1);
			}
			logger.debug(">>FaceYe --> Link MaxCount is+" + link.getMaxCount());
		}
		return links;
	}

	@Override
	protected List<Link> getLinks2Reset() {
		List<Link> links=new ArrayList<Link>(0);
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ|type", new Integer(1));
		searchParams.put("ISTRUE|isCrawled", "1");
		searchParams.put("EQ|site.id", new Long(1));
		Page<Link> page = this.linkService.getPage(searchParams, 1, 0);
		for(Link link:page.getContent()){
			if(this.isReset(link.getUrl())){
				links.add(link);
			}
		}
		page = null;
		searchParams.put("EQ|type", new Integer(3));
		page = this.linkService.getPage(searchParams, 1, 0);
		for(Link link:page.getContent()){
			if(this.isReset(link.getUrl())){
				links.add(link);
			}
		}
		return links;
	}

}
/**@generate-service-source@**/

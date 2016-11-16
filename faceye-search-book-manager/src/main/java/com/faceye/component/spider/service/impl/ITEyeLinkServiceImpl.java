package com.faceye.component.spider.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteLinkService;
import com.faceye.component.spider.service.SiteService;
import com.faceye.feature.service.SequenceService;
/**
 * IT eye链接初始化
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年9月14日
*  博客文章列表页:
*  link_type=1
*  如:http://www.iteye.com/blogs?page=14
*  博客文章明细页
*  link_type=2
*  如：http://qindongliang.iteye.com/blog/2115970
 */
@Service("ITEyeLinkServiceImpl")
public class ITEyeLinkServiceImpl implements SiteLinkService{

	private static String ITEYE="iteye";
	private static String ITEYE_BLOG_HOME="http://www.iteye.com/blogs";
	
	@Autowired
	private SequenceService sequenceService=null;
	@Autowired
	private SiteService siteService=null;
	
	@Autowired
	private LinkService linkService=null;
	@Override
	public void saveInitLinks() {
		this.getSite();
		this.saveITEYEBlogHomeLinkLists();
		this.saveInitBlogCategoryList();
	}
	
	/**
	 * 重置链接
	 */

	@Override
	public void reInitLinks() {
		Site site=this.getSite();
		Map searchParams=new HashMap();
		searchParams.put("EQ|site.id", site.getId());
		searchParams.put("EQ|type", new Integer(1));
		searchParams.put("ISTRUE|isCrawled", new Integer(1));
		Page<Link> page = this.linkService.getPage(searchParams, 1, 100);
		this.saveResetLinks(page);
	}
	
	/**
	 * 初始化种子链接
	 * @todo
	 * @param page
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月14日
	 */
	private void saveResetLinks(Page<Link> page){
		if(null!=page && CollectionUtils.isNotEmpty(page.getContent())){
			for(Link link:page.getContent()){
				link.setIsCrawled(Boolean.FALSE);
				link.setIsCrawlSuccess(Boolean.FALSE);
				this.linkService.save(link);
			}
		}
	}

	
	

	/**
	 * 初始化iteye博客列表页(link_type=1)
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月14日
	 */
	private void saveITEYEBlogHomeLinkLists(){
	    Site site=this.getSite();
	    String linkUrl="http://www.iteye.com/blogs?page=";
	    for(int i=1;i<=14;i++){
	    	String url=linkUrl+i;
	    	boolean isExist=this.linkService.isLinkExist(url);
	    	if(!isExist){
//	    		Link link=new Link();
//	    		link.setCreateDate(new Date());
//	    		link.setIsCrawled(Boolean.FALSE);
//	    		link.setIsCrawlSuccess(Boolean.FALSE);
//	    		link.setLastCrawlDate(null);
//	    		link.setParentId(null);
//	    		link.setSite(site);
//	    		link.setType(new Integer(1));
//	    		link.setUrl(url);
//	    		this.linkService.save(link);
	    		this.linkService.saveLink(url, site.getId(), 1);
	    	}
	    }
	}
	/**
	 * 初始化站点信息
	 */
	@Override
	public Site getSite() {
		Site site=null;
		site=this.siteService.getSiteByName(ITEYE);
		if(null==site){
			this.saveITEYESite();
			site=this.siteService.getSiteByName(ITEYE);
		}
		return site;
	}
	private void saveITEYESite(){
		Site site=null;
		if(null==site){
		  site=new Site();
		  site.setId(this.sequenceService.getNextSequence(Site.class.getName()));
		  site.setName(ITEYE);
		  site.setUrl(ITEYE_BLOG_HOME);
		  this.siteService.save(site);
		}
	}
	
	/**
	 * 初始化博客分类列表
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月15日
	 */
	private void saveInitBlogCategoryList(){
		List<BlogCategory> items=this.buildCategories();
		Site site=this.getSite();
		for(BlogCategory category:items){
		    String linkUrl=category.getUrl();
		    for(int i=1;i<=category.getMaxPage();i++){
		    	String url=linkUrl+"?page="+i;
		    	boolean isExist=this.linkService.isLinkExist(url);
		    	if(!isExist){
//		    		Link link=new Link();
//		    		link.setCreateDate(new Date());
//		    		link.setIsCrawled(Boolean.FALSE);
//		    		link.setIsCrawlSuccess(Boolean.FALSE);
//		    		link.setLastCrawlDate(null);
//		    		link.setParentId(null);
//		    		link.setSite(site);
//		    		link.setType(new Integer(1));
//		    		link.setUrl(url);
//		    		this.linkService.save(link);
		    		this.linkService.saveLink(url, site.getId(), 1);
		    	}
		    }
		}
	}
	
	/**
	 * 构建分类列表
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月15日
	 */
	
	private List<BlogCategory> buildCategories(){
		List<BlogCategory> items=new ArrayList<BlogCategory>();
		//移动mobile
		BlogCategory mobile =new BlogCategory();
		mobile.setUrl("http://www.iteye.com/blogs/category/mobile");
		mobile.setCategory("mobile");
		mobile.setMaxPage(3639);
		items.add(mobile);
		
		//web 前端
		BlogCategory web=new BlogCategory();
		web.setUrl("http://www.iteye.com/blogs/category/web");
		web.setCategory("web");
		web.setMaxPage(6947);
		items.add(web);
		
		//architecture 企业架构
		BlogCategory architecture =new BlogCategory();
		architecture.setUrl("http://www.iteye.com/blogs/category/architecture");
		architecture.setMaxPage(4174);
		architecture.setCategory("architecture");
		items.add(architecture);
		
		//编程语言 language
		BlogCategory language=new BlogCategory();
		language.setUrl("http://www.iteye.com/blogs/category/language");
		language.setCategory("language");
		language.setMaxPage(13690);
		items.add(language);
		//互联网 internet
		
		BlogCategory internet=new BlogCategory();
		internet.setUrl("http://www.iteye.com/blogs/category/internet");
		internet.setCategory("internet");
		internet.setMaxPage(1362);
		items.add(internet);
		
		//开源软件 opensource
		BlogCategory opensource =new BlogCategory();
		opensource.setUrl("http://www.iteye.com/blogs/category/opensource");
		opensource.setMaxPage(1816);
		opensource.setCategory("opensource");
		items.add(opensource);
		
		//操作系统 
		BlogCategory os=new BlogCategory();
		os.setUrl("http://www.iteye.com/blogs/category/os");
		os.setMaxPage(2285);
		os.setCategory("os");
		items.add(os);
		
		//数据库 database
		BlogCategory database=new BlogCategory();
		database.setUrl("http://www.iteye.com/blogs/category/database");
		database.setMaxPage(3619);
		database.setCategory("database");
		items.add(database);
		
		//研发管理 develop
	    BlogCategory develop =new BlogCategory();
	    develop.setUrl("http://www.iteye.com/blogs/category/develop");
	    develop.setCategory("develop");
	    develop.setMaxPage(798);
	    items.add(develop);
	    //行业应用
	    BlogCategory industry =new BlogCategory();
	    industry.setUrl("http://www.iteye.com/blogs/category/industry");
	    industry.setCategory("industry");
	    industry.setMaxPage(929);
	    items.add(industry);
		return items;
	}
	class BlogCategory{
		private String category="";
		private String url="";
		private Integer maxPage=0;
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

}

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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteLinkService;
import com.faceye.component.spider.service.SiteService;

/**
 * 初始化宜搜小说爬取链接
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月22日
 */
@Service
public class EasouBLinkServiceImpl implements SiteLinkService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SiteService siteService = null;
	@Autowired
	private LinkService linkService = null;

	@Override
	public void saveInitLinks() {
		this.saveInitNovelHome();
		this.saveInitBookList();
		// 初始化小说分类页手页
		// this.saveInitBookCategoryHome();
		// http://b.easou.com/c/clist.m?esid=5-XDHG2fXbX&gid=3217447&nid=1003217447&or=0&gst=1&p=1
		// String url = "http://b.easou.com/c/clist.m?esid=5-XDHG2fXbX&gid=3217447&nid=1003217447&or=0&gst=1&p=";
		// this.saveInitBooSecciontList(url, 365);
	}

	/**
	 * 初始化小说列表(type=4)
	 * @todo
	 * @param url
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月23日
	 * 
	 */
	private void saveInitBookList() {
		List<String> urls = new ArrayList<String>(0);
		// 言情
		urls.add("http://b.easou.com/c/s.m?q=yanqing&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.1&p=1");
		urls.add("http://b.easou.com/c/s.m?q=yanqing&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.1&p=2");
		urls.add("http://b.easou.com/c/s.m?q=yanqing&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.1&p=3");
		// 玄幻
		urls.add("http://b.easou.com/c/s.m?q=xuanhuan&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=1");
		urls.add("http://b.easou.com/c/s.m?q=xuanhuan&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=2");
		urls.add("http://b.easou.com/c/s.m?q=xuanhuan&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=3");
		// 都市
		urls.add("http://b.easou.com/c/s.m?q=dushi&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=1");
		urls.add("http://b.easou.com/c/s.m?q=dushi&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=2");
		urls.add("http://b.easou.com/c/s.m?q=dushi&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=3");
		// 武侠
		urls.add("http://b.easou.com/c/s.m?q=wuxia&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=1");
		urls.add("http://b.easou.com/c/s.m?q=wuxia&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=2");
		urls.add("http://b.easou.com/c/s.m?q=wuxia&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=3");
		// 网游
		urls.add("http://b.easou.com/c/s.m?q=wangyou&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=1");
		urls.add("http://b.easou.com/c/s.m?q=wangyou&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=2");
		urls.add("http://b.easou.com/c/s.m?q=wangyou&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=3");
		// 校园
		urls.add("http://b.easou.com/c/s.m?q=xiaoyuan&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=1");
		urls.add("http://b.easou.com/c/s.m?q=xiaoyuan&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=2");
		urls.add("http://b.easou.com/c/s.m?q=xiaoyuan&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=3");
		// 灵异
		urls.add("http://b.easou.com/c/s.m?q=lingyi&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=1");
		urls.add("http://b.easou.com/c/s.m?q=lingyi&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=2");
		urls.add("http://b.easou.com/c/s.m?q=lingyi&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=3");
		// 科幻
		urls.add("http://b.easou.com/c/s.m?q=kehuan&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=1");
		urls.add("http://b.easou.com/c/s.m?q=kehuan&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=2");
		urls.add("http://b.easou.com/c/s.m?q=kehuan&f=3&sty=0&esid=f_oeqtXD4hXbX5-XD2&s=0&attb=0&tpg=500&fr=3.4.4.0&p=3");

		for (String url : urls) {
			this.saveLink(url, 4);
		}
	}

	/**
	 * 初始化小说封面页
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月26日
	 */
	private void saveInitNovelHome() {
		List<String> urls = new ArrayList<String>();
		// 言情
		// 校花的贴身高手
		urls.add("http://b.easou.com/c/novel.m?gid=3217447&nid=8585421&p=&count=6&esid=SdXDHRCnhbm&fr=3.4.2.0");
		// 豪门宝贝老婆
		urls.add("http://b.easou.com/c/novel.m?gid=8581330&nid=12544373&p=&count=6&esid=n_hso8XD4FhbmSdXDC&fr=3.4.2.0");
		// 特种教师
		urls.add("http://b.easou.com/c/novel.m?esid=n_hso8XD4FhbmSdXDC&gid=4693396&nid=13161860&fr=3.2.1.1");
		// 天价小妖妻
		urls.add("http://b.easou.com/c/novel.m?gid=4717458&nid=14267787&p=&count=6&esid=n_hso8XD4FhbmSdXDC&fr=3.4.2.0");
		// 艳满杏花村
		urls.add("http://b.easou.com/c/novel.m?gid=6938300&nid=14014694&p=&count=6&esid=n_hso8XD4FhbmSdXDC&fr=3.4.2.0");
		// 总裁的二手新娘
		urls.add("http://b.easou.com/c/novel.m?gid=2619465&nid=13846183&p=&count=6&esid=n_hso8XD4FhbmSdXDC&fr=3.4.2.0");
		// 玄幻
		// 武极天下
		urls.add("http://b.easou.com/c/novel.m?gid=5655930&nid=9295682&p=&count=6&esid=n_hso8XD4FhbmSdXDC&fr=3.4.2.0");
		// 大主宰
		urls.add("http://b.easou.com/c/novel.m?gid=8783053&nid=9297295&p=&count=6&esid=n_hso8XD4FhbmSdXDC&fr=3.4.2.0");
		// 傲世九重天
		urls.add("http://b.easou.com/c/novel.m?gid=8141499&nid=14375600&p=&count=6&esid=n_hso8XD4FhbmSdXDC&fr=3.4.2.0");
		// 完美世界
		urls.add("http://b.easou.com/c/novel.m?gid=10645516&nid=14331086&p=&count=6&esid=n_hso8XD4FhbmSdXDC&fr=3.4.2.0");
		// 武逆
		urls.add("http://b.easou.com/c/novel.m?gid=4711723&nid=14330533&p=&count=6&esid=n_hso8XD4FhbmSdXDC&fr=3.4.2.0");
		// 莽荒记
		urls.add("http://b.easou.com/c/novel.m?gid=6326585&nid=14058594&p=&count=6&esid=n_hso8XD4FhbmSdXDC&fr=3.4.2.0");
		for (String url : urls) {
			this.saveLink(url, 5);
		}
	}

	/**
	 * 存储链接
	 * @todo
	 * @param url
	 * @param type
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月26日
	 */
	private void saveLink(String url, Integer type) {
		if (StringUtils.isNotEmpty(url)) {
			url = url.replaceAll("&amp;", "&");
		}
		Boolean isExist = this.linkService.isLinkExist(url);
		if (!isExist) {
			Site site = this.siteService.get(2L);
			Link link = new Link();
			link.setCreateDate(new Date());
			link.setIsCrawled(Boolean.FALSE);
			link.setIsCrawlSuccess(Boolean.FALSE);
			link.setType(type);
			link.setUrl(url);
//			link.setSite(site);
			link.setSiteId(site.getId());
			this.linkService.save(link);
		}
	}

	/**
	 * http://b.easou.com/c/cn.m?esid=fcsZm6XD48XbX5-XD2&fr=3.0.3.35
	 * 初始化分类首页  type=0;
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月23日
	 */
	private void saveInitBookCategoryHome() {
		String url = "http://b.easou.com/c/cn.m?esid=fcsZm6XD48XbX5-XD2&fr=3.0.3.35";
		Boolean isExist = this.linkService.isLinkExist(url);
		if (!isExist) {
			Site site = this.siteService.get(2L);
			Link link = new Link();
			link.setCreateDate(new Date());
			link.setIsCrawled(Boolean.FALSE);
			link.setIsCrawlSuccess(Boolean.FALSE);
			link.setType(0);
			link.setUrl(url);
//			link.setSite(site);
			link.setSiteId(site.getId());
			this.linkService.save(link);
		}
	}

	/**
	 * 初始化一本小说的章节列表页  type=1
	 * @todo
	 * @param bookUrl
	 * @param page
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月22日
	 */
	private void saveInitBooSecciontList(String bookUrl, int page) {
		Site site = siteService.get(2L);
		for (int i = 1; i <= page; i++) {
			String _url = bookUrl + i;
			Boolean isExist = this.linkService.isLinkExist(_url);
			if (!isExist) {
//				Link link = new Link();
//				link.setUrl(_url);
//				link.setIsCrawlSuccess(Boolean.FALSE);
//				link.setIsCrawled(Boolean.FALSE);
//				link.setCreateDate(new Date());
////				link.setSite(site);
//				link.setSiteId(site.getId());
//				link.setType(1);
//				this.linkService.save(link);
				this.linkService.saveLink(_url, site.getId(), 1);
				logger.debug(">>FaceYe init easou book url:" + _url);
			}
		}
	}

	@Override
	public void reInitLinks() {
		// 每天重新初始化小说章节列表页的首页
		Boolean isContinue = Boolean.TRUE;
		while (isContinue) {
			logger.debug(">>FaceYe reset e book links.");
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("EQ|type", new Integer(7));
			searchParams.put("ISTRUE|isCrawled", "1");
			searchParams.put("EQ|site.id", new Long(2));
			Page<Link> page = this.linkService.getPage(searchParams, 1, 100);
			if (page != null || CollectionUtils.isNotEmpty(page.getContent())) {
				this.saveResetLink(page);
			} else {
				isContinue = Boolean.FALSE;
				break;
			}

			// page=null;
			// searchParams.put("EQ|type", new Integer(7));
			// page=this.linkService.getPage(searchParams, 1, 0);
			this.saveResetLink(page);
		}

	}

	/**
	 * 保存link状态重置
	 * @todo
	 * @param page
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月22日
	 */
	private void saveResetLink(Page<Link> page) {
		if (page != null && CollectionUtils.isNotEmpty(page.getContent())) {
			for (Link link : page.getContent()) {
				link.setIsCrawled(Boolean.FALSE);
				link.setIsCrawlSuccess(Boolean.FALSE);
				logger.debug(">>FaceYe --> Re Set link:" + link.getUrl());
				this.linkService.save(link);
			}
		}
	}

	@Override
	public Site getSite() {
		return this.siteService.get(2L);
	}

}

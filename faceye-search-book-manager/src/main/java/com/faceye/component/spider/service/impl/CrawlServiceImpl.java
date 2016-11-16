package com.faceye.component.spider.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.ParseService;
import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.job.thread.CrawlThread;
import com.faceye.component.spider.job.thread.ThreadPoolController;
import com.faceye.component.spider.service.CrawlService;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteService;
import com.faceye.feature.service.MultiQueueService;

/**
 * 爬取服务
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月8日
 */
@Service
public class CrawlServiceImpl implements CrawlService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("easouBParseServiceImpl")
	private ParseService easouParseService = null;
	@Autowired
	private LinkService linkSerivce = null;

	@Autowired
	private SiteService siteService = null;
	
	@Autowired
	@Qualifier("linkQueueService")
	private MultiQueueService linkQueueService = null;

	@Override
	public void crawl() {
		List<Site> sites = this.siteService.getAll();
		if (CollectionUtils.isNotEmpty(sites)) {
			for (Site site : sites) {
				String key = "" + site.getId();
				linkQueueService.addQueue(key);
				boolean isAdd = false;
				if (linkQueueService.isEmpty(key) || linkQueueService.getSize(key) < 50) {
					isAdd = true;
				} else {
					logger.debug(">>FaceYe -->" + site.getName() + " 有线程正在爬取，现在不需要新增线程进行爬取.");
				}
				if (isAdd) {
					Map searchParams = new HashMap();
					searchParams.put("EQ|site.id", site.getId());
					List<Link> links = this.linkSerivce.getLinks2Crawl(searchParams);
					if (CollectionUtils.isNotEmpty(links)) {
						linkQueueService.addAll(key, links);
					}
				}
			}
		}
		this.doCrawl();
	}

	@Override
	public void crawlLink(Link link) {
		this.linkQueueService.add(link);
		this.doCrawl();
	}

	/**
	 * 执行爬取
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月25日
	 */
	private void doCrawl() { 
		Set<String> keys = this.linkQueueService.getKeys();
		if (null != keys) {
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String key = it.next();
				Site site=this.siteService.get(Long.parseLong(key));
				Integer threadCount=1;
				if(null!=site && null !=site.getThreadCount()){
					threadCount=site.getThreadCount();
				}
				Runnable runnable = new CrawlThread(key);
				ThreadPoolController.getINSTANCE().execute(key, runnable, threadCount);
			}
		}
	}

	

}

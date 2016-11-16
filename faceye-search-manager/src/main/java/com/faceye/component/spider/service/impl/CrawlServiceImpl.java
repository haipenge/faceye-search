package com.faceye.component.spider.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.job.thread.CrawlThread;
import com.faceye.component.spider.job.thread.ThreadPoolController;
import com.faceye.component.spider.service.CrawlService;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteService;
import com.faceye.feature.service.MultiQueueService;
import com.faceye.feature.service.PropertyService;
import com.faceye.feature.util.Json;
import com.faceye.feature.util.http.Http;
import com.fasterxml.jackson.core.type.TypeReference;

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
	private LinkService linkSerivce = null;
	@Autowired
	private SiteService siteService = null;
	@Autowired
	private PropertyService propertyService = null;
	@Autowired
	@Qualifier("linkQueueService")
	private MultiQueueService linkQueueService = null;
	@Override
	public void crawl() {
		logger.debug(">>FaceYe --> Do crawl job now.");
		this.prepare2Crawl();
		this.doCrawl();
	}

	/**
	 * 准备爬取的URL
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月27日
	 */
	private void prepare2Crawl() {
		String channelType = this.propertyService.get("spider.crawl.channel.type");
		if (StringUtils.equalsIgnoreCase(channelType, "local")) {
			logger.debug(">>FaceYe -->> Prepare local crawl.");
			this.prepare2LocalCrawl();
		} else {
			this.prepare2RemoteCrawl();
		}

	}

	/**
	 * 准备本地爬取URL
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月27日
	 */
	private void prepare2LocalCrawl() {
		Map sitesSearchParams=new HashMap();
		sitesSearchParams.put("boolean|isCrawl",Boolean.TRUE);
		List<Site> sites=this.siteService.getPage(sitesSearchParams, 1, 0).getContent();
//		List<Site> sites = this.siteService.getAll();
		if (CollectionUtils.isNotEmpty(sites)) {
			for (Site site : sites) {
				String key = "" + site.getId();
				linkQueueService.addQueue(key);
				boolean isAdd = false;
				if (linkQueueService.isEmpty(key) || linkQueueService.getSize(key) ==0) {
					isAdd = true;
				} else {
					logger.debug(">>FaceYe -->" + site.getName() + " 有线程正在爬取，现在不需要新增线程进行爬取.");
				}
				if (isAdd) {
					Map searchParams = new HashMap();
					//优先爬取种子资源
					searchParams.put("EQ|type", 0);
					searchParams.put("EQ|siteId", site.getId());
					searchParams.put("EQ|mimeType", 0);
					List<Link> links = this.linkSerivce.getLinks2Crawl(searchParams);
					if (CollectionUtils.isNotEmpty(links)) {
						linkQueueService.addAll(key, links);
					}
					//一般待爬资源
					searchParams=new HashMap();
					searchParams.put("EQ|siteId", site.getId());
					searchParams.put("NE|type", 0);
					if(!site.getIsCrawlImage()){
						searchParams.put("EQ|mimeType", 0);
					}
					//一般资源，爬取一次后不再继续爬取
					searchParams.put("boolean|isCrawled", false);
					List<Link> commonLinks = this.linkSerivce.getLinks2Crawl(searchParams);
					if (CollectionUtils.isNotEmpty(commonLinks)) {
						linkQueueService.addAll(key, commonLinks);
					}
				}
			}
		}
	}

	/**
	 * 准备远程爬取资源
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月27日
	 */
	private void prepare2RemoteCrawl() {
		boolean is2RequestNewUrls = false;
		logger.debug(">>FaceYe -->Start 2 get crawl links from control center.");
		// 重新请求条件，所有链接都爬取完成
		Set<String> keys = this.linkQueueService.getKeys();
		if (CollectionUtils.isEmpty(keys)) {
			is2RequestNewUrls = true;
		} else {
			Iterator<String> it = keys.iterator();
			boolean isAllEmpty=true;
			while (it.hasNext()) {
				String key = it.next();
				if (!this.linkQueueService.isEmpty(key)) {
                       isAllEmpty=false;
                       break;
				}
			}
			if(isAllEmpty){
				is2RequestNewUrls=true;
			}
		}
		logger.debug(">>FaceYe is2RequestNewUrls?"+is2RequestNewUrls);
		if (is2RequestNewUrls) {
			String api = "";
			String host = this.propertyService.get("spider.control.center.host");
			String channel=this.propertyService.get("spider.crawl.channel.name");
			String remoteLinkDistributeApi = this.propertyService.get("spider.control.center.link.distribute.api");
			api = host + remoteLinkDistributeApi;
			Map params=new HashMap();
			params.put("channel", channel);
			String callResult=Http.getInstance().post(api, "UTF-8", params);
//			String callResult = Http.getInstance().get(api, "UTF-8");
			if (StringUtils.isNotEmpty(callResult)) {
				List<Link> links = Json.toObject(callResult, new TypeReference<List<Link>>() {
				});
				if (CollectionUtils.isNotEmpty(links)) {
					logger.debug(">>FaceYe --> get links size from control center is:"+links.size());
					for (Link link : links) {
						this.linkQueueService.add("" + link.getSiteId(), link);
					}
				}else{
					logger.debug(">>FaceYe have not got any links from control center.");
				}
			}
		}
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
		logger.debug(">>FaceYe --> Do crawl now.");
		Set<String> keys = this.linkQueueService.getKeys();
		if (null != keys) {
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String key = it.next();
				logger.debug(">>FaceYe --> Crawl queue set key is:"+key+",it is a site id.");
				Site site = this.siteService.get(Long.parseLong(key));
				logger.debug(">>FaceYe --> crawl site is:"+site.getName());
				Integer threadCount = 1;
				if (null != site && null != site.getThreadCount()) {
					threadCount = site.getThreadCount();
				}
				Runnable runnable = new CrawlThread(key);
				ThreadPoolController.getINSTANCE().execute(key, runnable, threadCount);
			}
		}else{
			logger.debug(">>FaceYe --> Crawl link Queue set is null.");
		}
	}

}

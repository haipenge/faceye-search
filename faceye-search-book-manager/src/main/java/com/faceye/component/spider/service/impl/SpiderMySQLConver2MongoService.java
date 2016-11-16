package com.faceye.component.spider.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.MySQL2MongoService;
import com.faceye.component.spider.entity.CrawlResult;
import com.faceye.component.spider.entity.Link;
import com.faceye.component.spider.entity.Site;
import com.faceye.component.spider.repository.mongo.CrawlResultRepository;
import com.faceye.component.spider.repository.mongo.LinkRepository;
import com.faceye.component.spider.repository.mongo.SiteRepository;
import com.faceye.feature.service.SequenceService;

@Service("spiderMySQLConver2MongoService")
public class SpiderMySQLConver2MongoService implements MySQL2MongoService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	@Qualifier("_linkRepository")
	private com.faceye.component.spider.repository.jpa.LinkRepository jpaLinkRepository = null;
	
	@Autowired
	@Qualifier("_siteRepository")
	private com.faceye.component.spider.repository.jpa.SiteRepository jpaSiteRepository = null;
	
	@Autowired
	@Qualifier("_crawlResultRepository")
	private com.faceye.component.spider.repository.jpa.CrawlResultRepository jpaCrawlResutlRepository = null;
	@Autowired
	private LinkRepository linkRepository = null;
	@Autowired
	private SiteRepository siteRepository = null;
	@Autowired
	private CrawlResultRepository crawlResultRepository = null;

	@Autowired
	private SequenceService sequenceService = null;

	@Override
	public void conver() {
		 this.converSite();
		 this.converLink();
		//this.converCrawlResult();
	}

	/**
	 * 转换site
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年2月9日
	 */
	private void converSite() {

		List<Site> sites = this.jpaSiteRepository.findAll();
		if (CollectionUtils.isNotEmpty(sites)) {
			for (Site site : sites) {
				com.faceye.component.spider.doc.Site newSite = new com.faceye.component.spider.doc.Site();
				newSite.setId(site.getId());
				newSite.setName(site.getName());
				newSite.setUrl(site.getUrl());
				this.siteRepository.save(newSite);
			}
		}
	}

	/**
	 * 转换链接
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年2月9日
	 */
	private void converLink() {
		int page = 1;
		Pageable pageable = new PageRequest(page, 1000);
		Page<Link> links = this.jpaLinkRepository.findAll(pageable);
		while (links != null && CollectionUtils.isNotEmpty(links.getContent())) {
			page += 1;
			for (Link link : links) {
				com.faceye.component.spider.doc.Link newLink = new com.faceye.component.spider.doc.Link();
				newLink.setId(link.getId());
				newLink.setCreateDate(link.getCreateDate());
				newLink.setIsCrawled(link.getIsCrawled());
				newLink.setIsCrawlSuccess(link.getIsCrawlSuccess());
				newLink.setLastCrawlDate(link.getLastCrawlDate());
				newLink.setMimeType(link.getMimeType());
				newLink.setParentId(link.getParentId());
				newLink.setSiteId(link.getSite().getId());
				newLink.setType(link.getType());
				newLink.setUrl(link.getUrl());
				logger.debug(">>FaceYe conver link :" + link.getUrl());
				this.linkRepository.save(newLink);
			}
			pageable = new PageRequest(page, 1000);
			links = this.jpaLinkRepository.findAll(pageable);
			// links = this.linkService.findAll(pageable);
		}
	}

	private void converCrawlResult() {
		int page = 1;
		Pageable pageable = new PageRequest(page, 1000);
		Page<CrawlResult> crawlResults = this.jpaCrawlResutlRepository.findAll(pageable);
		while (crawlResults != null && CollectionUtils.isNotEmpty(crawlResults.getContent())) {
			page += 1;
			for (CrawlResult crawlResult : crawlResults.getContent()) {
				try {
					com.faceye.component.spider.doc.CrawlResult newCrawlResult = new com.faceye.component.spider.doc.CrawlResult();
					newCrawlResult.setId(crawlResult.getId());
					newCrawlResult.setCrawlDate(crawlResult.getCrawlDate());
					newCrawlResult.setIsParse(crawlResult.getIsParse());
					newCrawlResult.setIsParseSuccess(crawlResult.getIsParseSuccess());
					newCrawlResult.setLinkId(crawlResult.getLink().getId());
					newCrawlResult.setName(crawlResult.getName());
					newCrawlResult.setLinkUrl(crawlResult.getLink().getUrl());
					newCrawlResult.setStorePath(crawlResult.getStorePath());
					newCrawlResult.setSiteId(crawlResult.getLink().getSite().getId());
					newCrawlResult.setLinkType(crawlResult.getLink().getType());
					logger.debug(">>FaceYe conver crawlResut,store path is:" + crawlResult.getStorePath());
					this.crawlResultRepository.save(newCrawlResult);
				} catch (Exception e) {
					logger.error(">>FaceYe Throws Exception when conver." + e.toString());
				}
			}
			pageable = new PageRequest(page, 1000);
			crawlResults = this.jpaCrawlResutlRepository.findAll(pageable);
		}
	}

}

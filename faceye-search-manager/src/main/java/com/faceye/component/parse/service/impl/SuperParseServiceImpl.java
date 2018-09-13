package com.faceye.component.parse.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.doc.ParseResult;
import com.faceye.component.parse.service.ParseService;
import com.faceye.component.parse.service.document.Document;
import com.faceye.component.parse.service.factory.Parse;
import com.faceye.component.parse.service.factory.ParseFilter;
import com.faceye.component.parse.service.factory.filter.EmailParseFilter;
import com.faceye.component.parse.service.factory.filter.LeMovieParseFilter;
import com.faceye.component.parse.service.factory.filter.MovieBodyParseFilter;
import com.faceye.component.parse.service.factory.filter.SuperBodyParseFilter;
import com.faceye.component.parse.service.factory.filter.SuperLinkParseFilter;
import com.faceye.component.parse.service.thread.ParseThread;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.MatcherConfig;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.MatcherConfigService;
import com.faceye.feature.service.MultiQueueService;
import com.faceye.feature.service.job.thread.ThreadPoolController;
import com.faceye.feature.util.bean.BeanContextUtil;

/**
 * 超级解析器
 * 
 * @author @haipenge haipenge@gmail.com Create Date:2015年1月20日
 */
@Service
public class SuperParseServiceImpl extends BaseParseServiceImpl implements ParseService {

	@Autowired
	private MatcherConfigService matcherConfigService = null;
	@Autowired
	@Qualifier("parseQueueService")
	private MultiQueueService parseQueueService = null;
	@Autowired
	private Parse parse = null;

	@Override
	protected String getDomain() {
		return null;
	}

	public void saveParseResult() {
		Integer TOTAL_LOOP = 3;
		boolean isContinue = true;
		logger.debug(">>FaceYe --> Start super parse now.TOTAL_LOOP is :" + TOTAL_LOOP);
		while (isContinue) {
			try {
				if (this.parseQueueService.isEmpty()) {
					List<CrawlResult> crawlResults = getCrawlResults();
					if (CollectionUtils.isNotEmpty(crawlResults)) {
						this.parseQueueService.addAll(crawlResults);
					} else {
						isContinue = false;
						break;
					}
					logger.debug(">>Face Y --> Run super parse loop now.loop is:" + TOTAL_LOOP + ",queue size is:" + this.parseQueueService.getSize());
//					for (int i = 0; i < 5; i++) {
						Runnable runnabe = new ParseThread();
						ThreadPoolController.getINSTANCE().execute("Crawl-Result-Parse-Pool", runnabe, 1);
//					}
					TOTAL_LOOP--;
				} else {
					logger.debug(">>FaceYe super parse crawl result size is:" + this.parseQueueService.getSize());
					Thread.sleep(10000L);
				}

			} catch (Exception e) {
				logger.error(">>FaceYe -- Threows Exception :", e);
			}
		}
		// int count = 0;
		// while (count < TOTAL_LOOP.intValue()) {
		// List<CrawlResult> crawlResults = getCrawlResults();
		// if (CollectionUtils.isNotEmpty(crawlResults)) {
		// for (CrawlResult crawlResult : crawlResults) {
		// this.saveParseResult(crawlResult);
		// }
		// count++;
		// } else {
		// logger.debug(">>FaceYe --> crawl result is empty,need to check or wait for next crawl loop finish.");
		// break;
		// }
		// if (count >= TOTAL_LOOP.intValue()) {
		// break;
		// }
		// }
	}

	@Override
	protected List<CrawlResult> getCrawlResults() {
		List<Site> sites = this.getUseSuperParseSites();
		List<CrawlResult> crawlResults = new ArrayList<CrawlResult>();
		for (Site site : sites) {
			if (null != site) {
				Map<String, Object> searchParams = new HashMap<String, Object>();
				// 解析首页列表
				searchParams.put("ISFALSE|isParse", "0");
				searchParams.put("EQ|siteId", site.getId());
				reporter.reporter(searchParams);
				// searchParams.put("EQ|link.type", 1);
				Page<CrawlResult> page = this.crawlResultService.getPage(searchParams, 1, 1000);
				if (CollectionUtils.isNotEmpty(page.getContent())) {
					crawlResults.addAll(page.getContent());
					logger.debug(">>FaceYe -->Crawl result size is:" + page.getContent().size() + ",of site:" + site.getId() + "," + site.getName());
				}

			} else {
				logger.debug(">>FaceYe --> site is null.");
			}
		}
		return crawlResults;
	}

	@Override
	protected boolean parse(CrawlResult crawlResult, String content, Integer type) {
		Long siteId = crawlResult.getSiteId();
		Long linkId = crawlResult.getLinkId();
		boolean res = false;
		Class[] clazzs = new Class[] { SuperLinkParseFilter.class, SuperBodyParseFilter.class };
		Document document = parse.parse(crawlResult, clazzs);
//		logger.debug(">>FaceYe parse trace 1->"+document.getLinkUrl());
		if (document != null) {
			if (CollectionUtils.isNotEmpty(document.getLinks())) {
				res = true;
				this.linkService.saveDomainLinks(document.getLinks(), this.siteService.get(siteId));
			}
//			logger.debug(">>FaceYe parse trace 2->"+document.getLinkUrl());
			// 如果是youku 明细页
			if (document.getLinkType() != null && document.getLinkType().intValue() == 2 && StringUtils.indexOf(document.getLinkUrl(), "youku.com") != -1) {
				ParseFilter filter = (ParseFilter) BeanContextUtil.getBean(MovieBodyParseFilter.class);
				filter.filter(document, crawlResult, content);
			} else if (document.getLinkType() != null && document.getLinkType().intValue() == 2 && StringUtils.indexOf(document.getLinkUrl(), "letv.com") != -1) {
				// 如果是乐视详情页
				ParseFilter filter = (ParseFilter) BeanContextUtil.getBean(LeMovieParseFilter.class);
				filter.filter(document, crawlResult, content);
			} else if (document.getLinkType() != null && document.getLinkType() == 2 && StringUtils.contains(document.getLinkUrl(), "wx.dm15.com")) {
//				ParseFilter filter = (ParseFilter) BeanContextUtil.getBean(CsjParseFilter.class);
//				filter.filter(document, crawlResult, content);
			} else if(document.getLinkType()!=null && document.getLinkType()==2 && StringUtils.contains(document.getLinkUrl(), "kankandou.com")){
//				logger.debug(">>FaceYe parse trace 3->"+document.getLinkUrl());
//				ParseFilter filter=(ParseFilter) BeanContextUtil.getBean(KankandouParseFilter.class);
//				filter.filter(document, crawlResult, content);
			} else {
				if (StringUtils.isNotEmpty(document.getBody()) && StringUtils.isNotEmpty(document.getTitle())) {
					res = true;
					this.saveParseResult(crawlResult, document.getMetaData(), document.getTitle(), document.getBody(), document.getCategoryName(), document.getCategoryAlias());
				}
			}
		}
		// Integer linkType = crawlResult.getLinkType();
		// Page<MatcherConfig> matcherConfigs = this.getMatcherConfigs(siteId, linkType);
		// if (matcherConfigs != null) {
		// if (crawlResult.getLinkType() != null) {
		// // 如果是种子页或是列表页,则进入链接解析流程
		// if (crawlResult.getLinkType().intValue() == 0 || crawlResult.getLinkType().intValue() == 1) {
		// res = this.parseLinks(crawlResult, content, matcherConfigs.getContent());
		// } else {
		// // 如果是详情页或疑似详情页，先解析链接，再解析主体
		// res = this.parseLinks(crawlResult, content, matcherConfigs.getContent());
		// boolean mainContentRes = this.parseMainContent(crawlResult, content, matcherConfigs.getContent());
		// if (mainContentRes) {
		// res = mainContentRes;
		// }
		// }
		// }
		// }
		return res;
	}

	@Override
	protected void wrapParseResult(ParseResult parseResult) {

	}

	/**
	 * 取得页面要进行匹配的正则表达式
	 * 
	 * @todo
	 * @param siteId
	 * @param linkType
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2015年3月4日
	 */
	private Page<MatcherConfig> getMatcherConfigs(Long siteId, Integer linkType) {
		Page<MatcherConfig> configs = null;
		// 种子链接，一般为列表，所以种子链接，使用列表的正则表达式进行解析
		if (linkType == 0) {
			linkType = 1;
		}
		Map searchParams = new HashMap();
		searchParams.put("EQ|siteId", siteId);
		searchParams.put("EQ|type", linkType);
		configs = this.matcherConfigService.getPage(searchParams, 1, 0);
		return configs;
	}

	/**
	 * 取得使用超级解析器的站点
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2015年3月5日
	 */
	private List<Site> getUseSuperParseSites() {
		List<Site> sites = null;
		Map searchParams = new HashMap();
		searchParams.put("ISTRUE|isUseSuperParse", "1");
		sites = this.siteService.getPage(searchParams, 1, 0).getContent();
		return sites;
	}

}

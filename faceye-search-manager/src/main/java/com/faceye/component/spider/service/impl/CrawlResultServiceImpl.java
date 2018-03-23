package com.faceye.component.spider.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.ImageService;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.repository.mongo.CrawlResultRepository;
import com.faceye.component.spider.service.CrawlResultService;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteService;
import com.faceye.component.spider.util.FileUtil;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;

@Service
public class CrawlResultServiceImpl extends BaseMongoServiceImpl<CrawlResult, Long, CrawlResultRepository> implements CrawlResultService {

	@Autowired
	private ImageService imageService = null;

	@Autowired
	private LinkService linkService = null;

	@Autowired
	private SiteService siteService = null;

	@Autowired
	public CrawlResultServiceImpl(CrawlResultRepository dao) {
		super(dao);
	}

	@Override
	public void saveCrawlResult(Link link, byte[] content, String charset) {
		if (content == null) {
			// content 为null,认为爬取失败
			logger.error(">>FaceYe crawl link " + link.getUrl() + ",result is null.");
			link.setIsCrawled(true);
			link.setIsCrawlSuccess(false);
			this.linkService.save(link);
		} else {
			// link.setIsCrawled(true);
			// link.setIsCrawlSuccess(true);
			// this.linkService.save(link);
			if (link.getMimeType() == null || link.getMimeType() == 0) {
				String pageContent = "";
				try {
					pageContent = new String(content, charset);
					this.saveCrawlResult(link, pageContent);
				} catch (UnsupportedEncodingException e) {
					logger.error(">>FaceYe throws Exception: --->" + e.toString());
				}
			} else if (link.getMimeType() != null && link.getMimeType() == 1) {
				// TODO 需要构建图片存储模块
//				FileUtil.getInstance().writeImg(content, writeImgUrl)
				imageService.saveImage(link, content);
			}
		}
	}

	@Override
	public void saveCrawlResult(Link link, String content) {
		String realFile = FileUtil.getInstance().write(content, link.getUrl());
		logger.debug(">>FaceYe crawled:" + link.getUrl() + ",store path:" + realFile + ",content length is:" + content.length());
		CrawlResult crawlResult = new CrawlResult();
		crawlResult.setId(sequenceService.getNextSequence(CrawlResult.class.getName()));
		// crawlResult.setLink(link);
		crawlResult.setSiteId(link.getSiteId());
		crawlResult.setLinkId(link.getId());
		crawlResult.setLinkType(link.getType());
		crawlResult.setLinkUrl(link.getUrl());
		crawlResult.setIsParse(Boolean.FALSE);
		crawlResult.setStorePath(realFile);
		crawlResult.setCrawlDate(new Date());
		crawlResult.setIsWeixin(link.getIsWeixin());
		this.saveAndFlush(crawlResult);
		link.setIsCrawled(true);
		link.setIsCrawlSuccess(true);
		link.setLastCrawlDate(new Date());
		this.linkService.save(link);
		Long siteId = link.getSiteId();
		if (siteId != null) {
			Site site = this.siteService.get(link.getSiteId());
			if (site != null) {
				site.setLastCrawlDate(new Date());
				this.siteService.save(site);
			}
		}
	}

	// @Override
	// public Page<CrawlResult> getPage(Map<String, Object> searchParams, int page, int size)   {
	// if (page != 0) {
	// page = page - 1;
	// }
	// reporter.reporter(searchParams);
	// QCrawlResult qCrawlResult = QCrawlResult.crawlResult;
	// BooleanBuilder builder = new BooleanBuilder();
	// if (StringUtils.isNotEmpty(MapUtils.getString(searchParams, "ISFALSE|isParse"))) {
	// logger.debug(">>FaceYe --> search params key :ISFALSE|isParse,value is:");
	// builder.and(qCrawlResult.isParse.isFalse());
	// } else if (StringUtils.isNotEmpty(MapUtils.getString(searchParams, "ISTRUE|isParse"))) {
	// logger.debug(">>FaceYe --> search params key :ISTRUE|isParse,value is:");
	// builder.and(qCrawlResult.isParse.isTrue());
	// }
	// if (StringUtils.isNotEmpty(MapUtils.getString(searchParams, "EQ|link.site.id"))) {
	// logger.debug(">>FaceYe --> search params key :EQ|link.site.id,value is:"+MapUtils.getString(searchParams, "EQ|link.site.id"));
	// builder.and(qCrawlResult.siteId.eq(MapUtils.getLong(searchParams, "EQ|link.site.id")));
	// }
	// if (StringUtils.isNotEmpty(MapUtils.getString(searchParams, "EQ|link.type"))) {
	// logger.debug(">>FaceYe --> search params key :EQ|link.type,value is:"+MapUtils.getString(searchParams, "EQ|link.type"));
	// builder.and(qCrawlResult.linkType.eq(MapUtils.getInteger(searchParams, "EQ|link.type")));
	// }
	// Predicate predicate=builder.getValue();
	// Sort sort = new Sort(Direction.DESC, "id");
	// Pageable pageable = new PageRequest(page, size,sort);
	// Page<CrawlResult> res = this.dao.findAll(predicate, pageable);
	// logger.debug(">>FaceYe query crawlresut size is:"+res.getContent().size());
	// return res;
	// }

}
/**@generate-service-source@**/

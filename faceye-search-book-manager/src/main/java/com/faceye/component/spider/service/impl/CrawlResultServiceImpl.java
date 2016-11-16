package com.faceye.component.spider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.repository.mongo.CrawlResultRepository;
import com.faceye.component.spider.service.CrawlResultService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;

@Service
public class CrawlResultServiceImpl extends BaseMongoServiceImpl<CrawlResult, Long, CrawlResultRepository> implements CrawlResultService {

	@Autowired
	public CrawlResultServiceImpl(CrawlResultRepository dao) {
		super(dao);
	}

//	@Override
//	public Page<CrawlResult> getPage(Map<String, Object> searchParams, int page, int size) throws ServiceException {
//		if (page != 0) {
//			page = page - 1;
//		}
//		reporter.reporter(searchParams);
//		QCrawlResult qCrawlResult = QCrawlResult.crawlResult;
//		BooleanBuilder builder = new BooleanBuilder();
//		if (StringUtils.isNotEmpty(MapUtils.getString(searchParams, "ISFALSE|isParse"))) {
//			logger.debug(">>FaceYe --> search params key :ISFALSE|isParse,value is:");
//			builder.and(qCrawlResult.isParse.isFalse());
//		} else if (StringUtils.isNotEmpty(MapUtils.getString(searchParams, "ISTRUE|isParse"))) {
//			logger.debug(">>FaceYe --> search params key :ISTRUE|isParse,value is:");
//			builder.and(qCrawlResult.isParse.isTrue());
//		}
//		if (StringUtils.isNotEmpty(MapUtils.getString(searchParams, "EQ|link.site.id"))) {
//			logger.debug(">>FaceYe --> search params key :EQ|link.site.id,value is:"+MapUtils.getString(searchParams, "EQ|link.site.id"));
//			builder.and(qCrawlResult.siteId.eq(MapUtils.getLong(searchParams, "EQ|link.site.id")));
//		}
//		if (StringUtils.isNotEmpty(MapUtils.getString(searchParams, "EQ|link.type"))) {
//			logger.debug(">>FaceYe --> search params key :EQ|link.type,value is:"+MapUtils.getString(searchParams, "EQ|link.type"));
//			builder.and(qCrawlResult.linkType.eq(MapUtils.getInteger(searchParams, "EQ|link.type")));
//		}
//		Predicate predicate=builder.getValue();
//		Sort sort = new Sort(Direction.DESC, "id");
//		Pageable pageable = new PageRequest(page, size,sort);
//		Page<CrawlResult> res = this.dao.findAll(predicate, pageable);
//		logger.debug(">>FaceYe query crawlresut size is:"+res.getContent().size());
//		return res;
//	}

}
/**@generate-service-source@**/

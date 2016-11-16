package com.faceye.component.push.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.faceye.component.push.doc.PushRecord;
import com.faceye.component.push.repository.mongo.PushRecordRepository;
import com.faceye.component.push.service.PushRecordService;
import com.faceye.component.push.service.PushService;
import com.faceye.component.push.service.model.PushObject;
import com.faceye.component.push.service.model.PushObjectBuilder;
import com.faceye.component.search.doc.Article;
import com.faceye.component.search.service.SearchArticleService;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.SiteService;
import com.faceye.feature.service.SequenceService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;

@Service
public class PushRecordServiceImpl extends BaseMongoServiceImpl<PushRecord, Long, PushRecordRepository> implements PushRecordService {

	@Autowired
	private SiteService siteService = null;

	@Autowired
	private SearchArticleService searchArticleService = null;

	@Autowired
	private PushServiceFactory pushServiceFactory = null;
	
	@Autowired
	private SequenceService sequenceService=null;

	@Value("#{property['push.host']}")
	private String pushHost = "";

	@Autowired
	public PushRecordServiceImpl(PushRecordRepository dao) {
		super(dao);
	}

	/**
	 * 推送文章
	 */
	@Override
	public void doPushArticle(Long[] articleIds, Long[] siteIds) {
		for (Long articleId : articleIds) {
			Article article = this.searchArticleService.get(articleId);
			for (Long siteId : siteIds) {
				Site site = this.siteService.get(siteId);
				PushService pushService = this.pushServiceFactory.getPushService(site);
				PushObject pushObject = PushObjectBuilder.builder(article);
				pushService.push(pushObject);
				PushRecord pushRecord = new PushRecord();
				pushRecord.setId(this.sequenceService.getNextSequence(PushRecord.class.getName()));
				pushRecord.setArticleId(article.getId());
				pushRecord.setSiteId(siteId);
				this.dao.save(pushRecord);
			}
		}
	}

	
}
/**@generate-service-source@**/

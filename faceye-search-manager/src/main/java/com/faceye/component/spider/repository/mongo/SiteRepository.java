package com.faceye.component.spider.repository.mongo;

import com.faceye.component.spider.doc.Site;
import com.faceye.feature.repository.mongo.BaseMongoRepository;


public interface SiteRepository extends BaseMongoRepository<Site,Long> {

	public Site getSiteByName(String name);
}

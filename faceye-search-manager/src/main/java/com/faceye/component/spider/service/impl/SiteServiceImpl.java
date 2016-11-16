package com.faceye.component.spider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.repository.mongo.SiteRepository;
import com.faceye.component.spider.service.SiteService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;

/**
 * 站点管理 
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年12月10日
 */
@Service
public class SiteServiceImpl extends BaseMongoServiceImpl<Site, Long, SiteRepository> implements SiteService {

	@Autowired
	public SiteServiceImpl(SiteRepository dao) {
		super(dao);
	}

	@Override
	public Site getSiteByName(String name) {
		return dao.getSiteByName(name);
	}
	
	
}/**@generate-service-source@**/

package com.faceye.component.spider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.spider.doc.MatcherConfig;
import com.faceye.component.spider.repository.mongo.MatcherConfigRepository;
import com.faceye.component.spider.service.MatcherConfigService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;

@Service
public class MatcherConfigServiceImpl extends BaseMongoServiceImpl<MatcherConfig, Long, MatcherConfigRepository> implements MatcherConfigService {

	@Autowired
	public MatcherConfigServiceImpl(MatcherConfigRepository dao) {
		super(dao);
	}
	
	
}/**@generate-service-source@**/

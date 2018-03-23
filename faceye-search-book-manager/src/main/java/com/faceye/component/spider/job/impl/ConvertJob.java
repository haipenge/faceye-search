package com.faceye.component.spider.job.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.MySQL2MongoService;
 

@Service
public class ConvertJob extends BaseJob {

	@Autowired
	@Qualifier("spiderMySQLConver2MongoService")
	private MySQL2MongoService spiderMySQLConver2MongoService=null;
	@Override
	public void run()   {
		logger.debug(">>FaceYe --> start cover crawl Result 2 mongo job");
		this.spiderMySQLConver2MongoService.conver();
		logger.debug(">>FaceYe --> cover crawl Result 2 mongo finished.");
	}

}

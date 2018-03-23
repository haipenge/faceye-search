package com.faceye.component.spider.job.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.index.service.IndexService;
 

/**
 * 创建索引任务
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年9月29日
 */
@Service
public class IndexWriterJob extends BaseJob {
	@Autowired
    private IndexService indexService=null;
	@Override
	public void run()   {
		logger.debug(">>FaceYe --> do index builder job now.");
		indexService.buildIndex();
		logger.debug(">>FaceYe --> Finish index builder job.");
	}

}

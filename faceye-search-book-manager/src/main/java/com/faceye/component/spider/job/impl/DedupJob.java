package com.faceye.component.spider.job.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.ParseResultService;
import com.faceye.component.search.service.SearchArticleService;
 

/**
 * 去重Job
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年9月30日
 */
@Service
public class DedupJob extends BaseJob {
	@Autowired
    private ParseResultService parseResultService=null;
	@Autowired
	private SearchArticleService searchArticleService=null;
	@Override
	public void run()   {
		logger.debug(">>Start Dudup Job now");
//		this.parseResultService.dedup();
		this.searchArticleService.dedup();
		logger.debug(">>Finish dudup job.");
	}

}

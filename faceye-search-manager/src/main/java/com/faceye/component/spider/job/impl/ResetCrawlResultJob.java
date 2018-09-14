package com.faceye.component.spider.job.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.ParseResultService;
 

@Service
public class ResetCrawlResultJob extends BaseJob {

	@Autowired
	private ParseResultService parseResultService = null;

	@Override
//	@Scheduled(cron = "0 05 14 * * ?")
	public void run()   {
		this.parseResultService.resetParseResult();
	}

}

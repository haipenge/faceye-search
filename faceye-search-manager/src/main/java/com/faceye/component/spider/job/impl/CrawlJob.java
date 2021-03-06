package com.faceye.component.spider.job.impl;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.faceye.component.spider.service.CrawlService;

/**
 * 网页爬取JOB
 * 
 * @author @haipenge haipenge@gmail.com Create Date:2014年7月7日
 */
@Service
@DisallowConcurrentExecution
public class CrawlJob extends BaseJob {
	private Boolean isJobRun = Boolean.FALSE;
	@Autowired
	private CrawlService crawlService = null;

	@Override
	@Scheduled(cron = "0 0/2 * * * ?")
	public void run() {
		logger.debug(">>FaceYe --> crawl job,isJobRun:" + isJobRun);
		if (!isJobRun) {
			try {
				isJobRun = Boolean.TRUE;
				crawlService.crawl();
			} catch (Exception e) {
				logger.error(">>E:", e);
			} finally {
				isJobRun = Boolean.FALSE;
			}
		}
	}

}

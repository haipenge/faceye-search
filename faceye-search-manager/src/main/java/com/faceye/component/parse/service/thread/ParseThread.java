package com.faceye.component.parse.service.thread;

import com.faceye.component.parse.service.ParseService;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.feature.service.MultiQueueService;
import com.faceye.feature.service.job.thread.BaseThread;
import com.faceye.feature.util.bean.BeanContextUtil;

/**
 * 数据解析线程
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年3月8日
 */
public class ParseThread extends BaseThread {
	private ParseService parseService = null;
	private MultiQueueService parseQueueService = null;

	public ParseThread() {
		if (parseService == null) {
			parseService = (ParseService) BeanContextUtil.getBean("superParseServiceImpl");
		}
		if (parseQueueService == null) {
			parseQueueService = (MultiQueueService) BeanContextUtil.getBean("parseQueueService");
		}
	}

	@Override
	public void doBusiness() {
		CrawlResult crawlResult = null;
		boolean isContinue = true;
		try {
			while (isContinue) {
				try {
					crawlResult = (CrawlResult) this.parseQueueService.get();
					if (null != crawlResult) {
						this.parseService.saveParseResult(crawlResult);
					} else {
						isContinue = false;
					}
				} catch (Exception e) {
					logger.error(">>FaceYe throws Exception in parse thread,", e);
				}
			}
		} catch (Exception e) {
			logger.error(">>FaceYe throws Excetpio in thread,crawlResult id is:" + crawlResult.getId(), e);
		}
	}

}

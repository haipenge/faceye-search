package com.faceye.component.spider.job.impl;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.spider.service.LinkService;
import com.faceye.feature.service.job.impl.BaseJob;

/**
 * 重置种子链接
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年3月12日
 */
@Service
@DisallowConcurrentExecution
public class ResetSeedLinkJob extends BaseJob {
	@Autowired()
	private LinkService linkService = null;

	@Override
	public void run() {
		this.linkService.resetSeedLinks();
	}

}

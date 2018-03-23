package com.faceye.component.spider.job.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.ParseResultService;
 
/**
 * 文章自动发布任务
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年2月9日
 */
@Service
public class AutoPublishJob extends BaseJob {
	@Autowired
	private ParseResultService parseResultService=null;
	@Override
	public void run()   {
		parseResultService.saveAuthPublish();
	}

}

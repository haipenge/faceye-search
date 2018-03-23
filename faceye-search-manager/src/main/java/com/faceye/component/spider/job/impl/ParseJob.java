package com.faceye.component.spider.job.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.ParseResultService;
import com.faceye.component.parse.service.ParseService;
 

/**
 * 解析JOB
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月22日
 */
@Service
public class ParseJob extends BaseJob {
	@Autowired
	@Qualifier("cnblogsParseServiceImpl")
	private ParseService cnblogsParseService = null;
	
	@Autowired
	@Qualifier("OSChinaParseServiceImpl")
	private ParseService oschinaParseService = null;
	@Autowired
	@Qualifier("CSDNParseServiceImpl")
	private ParseService csdnParseService = null;
	@Autowired
	@Qualifier("ITEyeParseServiceImpl")
	private ParseService iteyeParseService = null;
	@Autowired
	@Qualifier("w3SchoolParseServiceImpl")
	private ParseService w3SchoolParseService = null;
	@Autowired
	private ParseResultService parseResultService = null;
	@Autowired
	@Qualifier("superParseServiceImpl")
	private ParseService superParseService=null;

	@Autowired
	@Qualifier("segmentFaultParseServiceImpl")
	private ParseService segmentFaultParseService = null;

	@Autowired
	@Qualifier("yiibaiParseServiceImpl")
	private ParseService yiibaiParseService = null;

	@Autowired
	@Qualifier("ctoParseService")
	private ParseService ctoParseService = null;

	Boolean isJobRun = Boolean.FALSE;

	@Override
	public void run()   {
		// 解析cnblogs页面
		logger.error(">>>>>Parse Job Start ..is job run :"+isJobRun);
		try {
			if (!isJobRun) {
				isJobRun = Boolean.TRUE;
				//超级解析器
				this.superParseService.saveParseResult();
				logger.debug(">>FaceYe start ParseJob now.");
				this.ctoParseService.saveParseResult();
				//this.cnblogsParseService.saveParseResult();
				// this.easouBParseService.saveParseResult();
				// this.parseResultService.saveRebuildParseResult();
				this.oschinaParseService.saveParseResult();
				// logger.debug(">>FaceYe end ParseJob now.");
				// parse csdn blogs
				//this.csdnParseService.saveParseResult();
				// parse w3school.
				//this.w3SchoolParseService.saveParseResult();
				// parse yiibai.com
				//this.yiibaiParseService.saveParseResult();
				
				//this.segmentFaultParseService.saveParseResult();
				
				// parse iteye blogs.
				// this.iteyeParseService.saveParseResult();
			}
		} catch (Exception e) {
			logger.error(">>FaceYe --:", e);
		} finally {
			isJobRun = Boolean.FALSE;
		}
	}

}

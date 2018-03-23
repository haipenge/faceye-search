package com.faceye.component.spider.job.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.ResetKindleResultService;
 

@Service
public class ResetKindleResultJob extends BaseJob {
	@Autowired
    private ResetKindleResultService resetKindleResultService=null;
	@Override
	public void run()   {
		this.resetKindleResultService.reset();
	}

}

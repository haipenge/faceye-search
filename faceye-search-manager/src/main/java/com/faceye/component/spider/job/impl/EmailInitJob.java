package com.faceye.component.spider.job.impl;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.search.service.EmailService;
import com.faceye.feature.util.ServiceException;
/**
 * 电子邮件初始化job
 * @author @haipenge 
 * @联系:haipenge@gmail.com
 * 创建时间:2015年7月29日
 */
@Service
@DisallowConcurrentExecution
public class EmailInitJob extends BaseJob{
    private boolean isJobRun=false;
    @Autowired
    private EmailService emailService=null;
	@Override
	public void run() throws ServiceException {
		if(!isJobRun){
			this.emailService.readAndImport();
		}
		isJobRun=true;
	}

}

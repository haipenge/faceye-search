package com.faceye.component.search.service;

import com.faceye.component.search.doc.Email;
import com.faceye.feature.service.BaseService;

public interface EmailService extends BaseService<Email,Long>{

	public Email getEmailByAddress(String address);
	
	/**
	 * 读取，导入邮件
	 * @todo
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年7月28日
	 */
	public void readAndImport();
	
	/**
	 * 定时发送邮件任务
	 * @todo
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年7月30日
	 */
	public void send();
	
	public void send(Long id);
	
//	public void send(Email email);
}/**@generate-service-source@**/

package com.faceye.component.push.service;

import com.faceye.component.push.service.model.PushObject;

/**
 * 数据推送服务
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年12月27日
 */
public interface PushService {

	/**
	 * 登陆
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年12月27日
	 */
	public boolean doLogin();
	
	/**
	 * 推送
	 * @todo
	 * @param pushObject
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月22日
	 */
	public void push(PushObject pushObject);
}

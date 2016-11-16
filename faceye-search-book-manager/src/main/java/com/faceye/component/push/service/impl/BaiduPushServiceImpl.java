package com.faceye.component.push.service.impl;

import org.springframework.stereotype.Service;

import com.faceye.component.push.service.PushService;
import com.faceye.component.push.service.model.PushObject;
/**
 * Baidu推送
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年12月27日
 */
@Service("baiduPushService")
public class BaiduPushServiceImpl implements PushService {

	@Override
	public boolean doLogin() {
		return false;
	}

	@Override
	public void push(PushObject pushObject) {
	}

}

package com.faceye.component.weixin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.search.service.SearchArticleService;
import com.faceye.component.weixin.service.WeixinService;

/**
 * 微信服务类
 * 
 * @author haipenge
 *
 */
@Service
public class WeixinServiceImpl implements WeixinService {
	@Autowired
	private SearchArticleService searchArticleService = null;

	public SearchArticleService getSearchArticleService() {
		return searchArticleService;
	}
}

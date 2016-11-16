package com.faceye.component.spider.service;

import com.faceye.component.spider.doc.Site;
import com.faceye.feature.service.BaseService;

public interface SiteService extends BaseService<Site,Long>{

	/**
	 * 根据名字取得网站
	 * @todo
	 * @param name
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年8月13日
	 */
	public Site getSiteByName(String name);
	
}/**@generate-service-source@**/

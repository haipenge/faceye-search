package com.faceye.component.spider.service;

import com.faceye.component.spider.doc.Site;

public interface SiteLinkService {
	  /**
     * 初始化的链接
     * @todo
     * @author:@haipenge
     * haipenge@gmail.com
     * 2014年7月8日
     */
	public void saveInitLinks();
	
	/**
	 * 重新将种子链接置为未爬取状态，保证 爬虫可以重新爬取
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月21日
	 */
	public void reInitLinks();
	
	/**
	 * 取得网站首页信息
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月14日
	 */
	public Site getSite();
}

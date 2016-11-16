package com.faceye.component.push.service;

import com.faceye.component.push.doc.PushRecord;
import com.faceye.feature.service.BaseService;

public interface PushRecordService extends BaseService<PushRecord,Long>{

	/**
	 * 推送文章到指定站点
	 * @todo
	 * @param articleId
	 * @param siteId
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月24日
	 */
	public void doPushArticle(Long[] articleId,Long[] siteIds);
}/**@generate-service-source@**/

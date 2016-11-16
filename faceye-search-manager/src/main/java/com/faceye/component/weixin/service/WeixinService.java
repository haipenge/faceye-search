package com.faceye.component.weixin.service;

import com.faceye.component.search.doc.Article;
import com.faceye.component.weixin.doc.Weixin;
import com.faceye.feature.service.BaseService;

public interface WeixinService extends BaseService<Weixin,Long>{

	/**
	 * 根据文章ID查询
	 * @todo
	 * @param articleId
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月30日
	 */
	public Weixin getWeixinByArticle(Article article);
}/**@generate-service-source@**/

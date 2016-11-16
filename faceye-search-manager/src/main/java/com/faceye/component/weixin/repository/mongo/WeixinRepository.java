package com.faceye.component.weixin.repository.mongo;

import com.faceye.component.search.doc.Article;
import com.faceye.component.weixin.doc.Weixin;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * Weixin 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
public interface WeixinRepository extends BaseMongoRepository<Weixin,Long> {
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
	
}/**@generate-repository-source@**/

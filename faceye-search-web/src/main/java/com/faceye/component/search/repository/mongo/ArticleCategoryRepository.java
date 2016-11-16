package com.faceye.component.search.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * ArticleCategory 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
public interface ArticleCategoryRepository extends BaseMongoRepository<ArticleCategory,Long> {
	
	/**
	 * 根据别名取得文章分类
	 * @todo
	 * @param alias
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年11月8日
	 */
	public ArticleCategory getArticleCategoryByAlias(String alias);
}/**@generate-repository-source@**/

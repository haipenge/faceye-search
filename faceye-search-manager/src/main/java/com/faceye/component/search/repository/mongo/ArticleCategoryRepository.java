package com.faceye.component.search.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * ArticleCategory 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
public interface ArticleCategoryRepository extends BaseMongoRepository<ArticleCategory,Long> {
	
	public ArticleCategory getArticleCategoryByAlias(String alias);
	
	public ArticleCategory getArticleCategoryByName(String name);
	
}/**@generate-repository-source@**/

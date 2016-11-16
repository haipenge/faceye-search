package com.faceye.component.spider.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.faceye.component.spider.doc.MatcherConfig;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * MatcherConfig 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
public interface MatcherConfigRepository extends BaseMongoRepository<MatcherConfig,Long> {
	
	
}/**@generate-repository-source@**/

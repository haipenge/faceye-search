package com.faceye.component.parse.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.faceye.component.parse.doc.FilterWord;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * FilterWord 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
public interface FilterWordRepository extends BaseMongoRepository<FilterWord,Long> {
	
	public FilterWord getFilterWordByWord(String word);
	
}/**@generate-repository-source@**/

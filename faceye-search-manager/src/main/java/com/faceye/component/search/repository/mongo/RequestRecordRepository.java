package com.faceye.component.search.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.faceye.component.search.doc.RequestRecord;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * RequestRecord 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
public interface RequestRecordRepository extends BaseMongoRepository<RequestRecord,Long> {
	
	
}/**@generate-repository-source@**/

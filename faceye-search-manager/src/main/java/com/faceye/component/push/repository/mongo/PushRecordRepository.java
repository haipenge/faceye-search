package com.faceye.component.push.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.faceye.component.push.doc.PushRecord;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * PushRecord 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
public interface PushRecordRepository extends BaseMongoRepository<PushRecord,Long> {
	
	
}/**@generate-repository-source@**/

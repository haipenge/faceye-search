package com.faceye.component.search.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.faceye.component.search.doc.Email;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * Email 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
public interface EmailRepository extends BaseMongoRepository<Email,Long> {
	
	public Email getEmailByAddress(String address);
}/**@generate-repository-source@**/

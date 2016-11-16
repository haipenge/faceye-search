package com.faceye.component.parse.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.faceye.component.parse.doc.Image;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * Image 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
public interface ImageRepository extends BaseMongoRepository<Image,Long> {
	
	
}/**@generate-repository-source@**/

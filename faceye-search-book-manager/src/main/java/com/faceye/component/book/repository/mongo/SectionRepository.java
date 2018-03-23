package com.faceye.component.book.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.faceye.component.book.doc.Section;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * Section 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
@Repository("mSectionRepository")
public interface SectionRepository extends BaseMongoRepository<Section,Long> {
	
	
}/**@generate-repository-source@**/

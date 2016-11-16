package com.faceye.component.search.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.faceye.component.search.doc.Subject;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * Subject 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
public interface SubjectRepository extends BaseMongoRepository<Subject,Long> {
	
	/**
	 * 根据 alias取得Subject
	 * @todo
	 * @param alias
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月1日
	 */
	public Subject getSubjectByAlias(String alias);
	
}/**@generate-repository-source@**/

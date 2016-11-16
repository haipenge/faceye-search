package com.faceye.component.book.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.faceye.component.book.doc.Category;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * Category 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
@Repository("mCategoryRepository")
public interface CategoryRepository extends BaseMongoRepository<Category,Long> {
	
	
}/**@generate-repository-source@**/

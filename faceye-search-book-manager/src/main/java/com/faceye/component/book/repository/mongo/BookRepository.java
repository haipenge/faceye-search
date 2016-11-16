package com.faceye.component.book.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.faceye.component.book.doc.Book;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * Book 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
@Repository("mBookRepository")
public interface BookRepository extends BaseMongoRepository<Book,Long> {
	
	
}/**@generate-repository-source@**/

package com.faceye.component.search.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.faceye.component.search.doc.Movie;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * Movie 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
public interface MovieRepository extends BaseMongoRepository<Movie,Long> {
	
	public Movie getMovieByName(String name);
	
}/**@generate-repository-source@**/

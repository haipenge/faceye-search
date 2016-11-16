package com.faceye.component.index.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.faceye.component.index.doc.AnalyzerWord;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * AnalyzerWord 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
public interface AnalyzerWordRepository extends BaseMongoRepository<AnalyzerWord,Long> {
	
	/**
	 * 根据解析出来的词，查找解析对像
	 * @todo
	 * @param word
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年10月2日
	 */
	public AnalyzerWord getAnalyzerWordByWord(String word);
}/**@generate-repository-source@**/

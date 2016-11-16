package com.faceye.component.parse.repository.mongo;

import java.util.List;

import com.faceye.component.parse.doc.ParseResult;
import com.faceye.feature.repository.mongo.BaseMongoRepository;

public interface ParseResultRepository extends BaseMongoRepository<ParseResult, Long> {

	/**
	 * 根据文章标题取得文章
	 * @todo
	 * @param name
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年12月30日
	 */
	public List<ParseResult> getParseResultsByName(String name);
}

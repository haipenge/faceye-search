package com.faceye.component.search.repository.mongo;

import java.util.List;

import com.faceye.component.search.doc.Article;
import com.faceye.feature.repository.mongo.BaseMongoRepository;

public interface SearchArticleRepository extends BaseMongoRepository<Article, Long> {
 
	/**
	 * 根据 文章 名查询
	 * @todo
	 * @param name
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年10月7日
	 */
	public List<Article> getArticlesByName(String name);
}

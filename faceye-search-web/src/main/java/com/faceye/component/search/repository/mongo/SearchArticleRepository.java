package com.faceye.component.search.repository.mongo;

import java.util.List;

import com.faceye.component.search.doc.Article;
import com.faceye.feature.repository.mongo.BaseMongoRepository;

public interface SearchArticleRepository extends BaseMongoRepository<Article, Long> {
 
	
	/**
	 * 根据标题取得文章
	 * @todo
	 * @param name
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月17日
	 */
	public Article getArticleByName(String name);
	
	public List<Article> getArticlesByName(String name);
}

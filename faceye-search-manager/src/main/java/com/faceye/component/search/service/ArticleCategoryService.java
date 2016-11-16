package com.faceye.component.search.service;

import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.feature.service.BaseService;

public interface ArticleCategoryService extends BaseService<ArticleCategory,Long>{

	public ArticleCategory getArticleCategoryByAlias(String alias);
	
	public ArticleCategory getArticleCategoryByName(String name);
}/**@generate-service-source@**/

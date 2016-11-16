package com.faceye.component.search.service;

import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.feature.service.BaseService;

public interface ArticleCategoryService extends BaseService<ArticleCategory,Long>{

	/**
	 * 根据别名取得文章分类
	 * @todo
	 * @param alias
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年11月8日
	 */
	public ArticleCategory getArticleCategoryByAlias(String alias);
}/**@generate-service-source@**/

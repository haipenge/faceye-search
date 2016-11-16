package com.faceye.component.search.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.component.search.repository.mongo.ArticleCategoryRepository;
import com.faceye.component.search.service.ArticleCategoryService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;

@Service
public class ArticleCategoryServiceImpl extends BaseMongoServiceImpl<ArticleCategory, Long, ArticleCategoryRepository> implements ArticleCategoryService {

	@Autowired
	public ArticleCategoryServiceImpl(ArticleCategoryRepository dao) {
		super(dao);
	}

	@Override
	public ArticleCategory getArticleCategoryByAlias(String alias) {
		return this.dao.getArticleCategoryByAlias(alias);
	}
	
	
}/**@generate-service-source@**/

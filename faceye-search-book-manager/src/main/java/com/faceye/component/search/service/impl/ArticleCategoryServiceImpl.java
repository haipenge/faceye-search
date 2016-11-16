package com.faceye.component.search.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.component.search.repository.mongo.ArticleCategoryRepository;
import com.faceye.component.search.service.ArticleCategoryService;
import com.faceye.feature.service.impl.BaseServiceImpl;
import com.faceye.feature.util.ServiceException;
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

package com.faceye.test.component.search.repository;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.component.search.repository.mongo.ArticleCategoryRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;

public class ArticleCategoryRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	ArticleCategoryRepository articleCategoryRepository =null;
	@Test
	public void testGetAll() throws Exception{
		List<ArticleCategory> categories=this.articleCategoryRepository.findAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(categories));
	}
}

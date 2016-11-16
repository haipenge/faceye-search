package com.faceye.test.component.search.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.component.search.repository.mongo.ArticleCategoryRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * ArticleCategory DAO 测试
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月26日
 */
public class ArticleCategoryRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private ArticleCategoryRepository articleCategoryRepository = null;

	@Before
	public void before() throws Exception {
		this.articleCategoryRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		ArticleCategory entity = new ArticleCategory();
		this.articleCategoryRepository.save(entity);
		Iterable<ArticleCategory> entities = this.articleCategoryRepository.findAll();
		Assert.isTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		ArticleCategory entity = new ArticleCategory();
		this.articleCategoryRepository.save(entity);
        this.articleCategoryRepository.delete(entity.getId());
        Iterable<ArticleCategory> entities = this.articleCategoryRepository.findAll();
		Assert.isTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		ArticleCategory entity = new ArticleCategory();
		this.articleCategoryRepository.save(entity);
		ArticleCategory articleCategory=this.articleCategoryRepository.findOne(entity.getId());
		Assert.isTrue(articleCategory!=null);
	}

	
}

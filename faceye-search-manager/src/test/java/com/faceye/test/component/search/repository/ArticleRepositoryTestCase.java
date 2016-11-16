package com.faceye.test.component.search.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.faceye.component.search.doc.Article;
import com.faceye.component.search.repository.mongo.ArticleRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * Article DAO 测试
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月26日
 */
public class ArticleRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private ArticleRepository articleRepository = null;

	@Before
	public void before() throws Exception {
//		this.articleRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		Article entity = new Article();
		this.articleRepository.save(entity);
		Iterable<Article> entities = this.articleRepository.findAll();
		Assert.isTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		Article entity = new Article();
		this.articleRepository.save(entity);
        this.articleRepository.delete(entity.getId());
        Iterable<Article> entities = this.articleRepository.findAll();
		Assert.isTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		Article entity = new Article();
		this.articleRepository.save(entity);
		Article article=this.articleRepository.findOne(entity.getId());
		Assert.isTrue(article!=null);
	}

	
}

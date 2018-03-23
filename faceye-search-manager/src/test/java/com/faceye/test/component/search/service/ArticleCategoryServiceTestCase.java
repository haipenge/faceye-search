package com.faceye.test.component.search.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.junit.Assert;

import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.component.search.service.ArticleCategoryService;
import com.faceye.test.feature.service.BaseServiceTestCase;


/**
 * ArticleCategory  服务层测试用例
 * 
 * @author @haipenge haipenge@gmail.com Create Date:2014年5月20日
 */
public class ArticleCategoryServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private ArticleCategoryService articleCategoryService = null;
	/**
	 * 初始化
	 * @todo
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年5月20日
	 */
	@Before
	public void set() throws Exception {
		Assert.assertTrue(articleCategoryService != null);
	}

	/**
	 * 清理
	 * @todo
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年5月20日
	 */
	@After
	public void after() throws Exception {
//		this.articleCategoryService.removeAllInBatch();
	}

	/**
	 *  保存测试
	 * @todo
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年5月20日
	 */
	@Test
	public void testSave() throws Exception {
		ArticleCategory entity = new ArticleCategory();
		this.articleCategoryService.save(entity);
		List<ArticleCategory> entites = this.articleCategoryService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testSaveAndFlush() throws Exception {
		ArticleCategory entity = new ArticleCategory();
		this.articleCategoryService.save(entity);
		List<ArticleCategory> entites = this.articleCategoryService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testMultiSave() throws Exception {
		for (int i = 0; i < 5; i++) {
			ArticleCategory entity = new ArticleCategory();
			this.articleCategoryService.save(entity);
		}
		List<ArticleCategory> entities = this.articleCategoryService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testRemoveById() throws Exception {
		ArticleCategory entity = new ArticleCategory();
		this.articleCategoryService.save(entity);
		logger.debug(">>Entity id is:" + entity.getId());
		ArticleCategory e = this.articleCategoryService.get(entity.getId());
		Assert.assertTrue(e != null);
	}

	@Test
	public void testRemoveEntity() throws Exception {
		ArticleCategory entity = new ArticleCategory();
		this.articleCategoryService.save(entity);
		this.articleCategoryService.remove(entity);
		List<ArticleCategory> entities = this.articleCategoryService.getAll();
		Assert.assertTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAllInBatch() throws Exception {
		for (int i = 0; i < 5; i++) {
			ArticleCategory entity = new ArticleCategory();
			this.articleCategoryService.save(entity);
		}
		List<ArticleCategory> entities = this.articleCategoryService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
		this.articleCategoryService.removeAllInBatch();
		entities = this.articleCategoryService.getAll();
		Assert.assertTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			ArticleCategory entity = new ArticleCategory();
			this.articleCategoryService.save(entity);
		}
//		this.articleCategoryService.removeAll();
		List<ArticleCategory> entities = this.articleCategoryService.getAll();
		Assert.assertTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveListInBatch() throws Exception {
		List<ArticleCategory> entities = new ArrayList<ArticleCategory>();
		for (int i = 0; i < 5; i++) {
			ArticleCategory entity = new ArticleCategory();
			
			this.articleCategoryService.save(entity);
			entities.add(entity);
		}
		this.articleCategoryService.removeInBatch(entities);
		entities = this.articleCategoryService.getAll();
		Assert.assertTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testGetAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			ArticleCategory entity = new ArticleCategory();
			this.articleCategoryService.save(entity);
		}
		List<ArticleCategory> entities = this.articleCategoryService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testGetPage() throws Exception {
		for (int i = 0; i < 25; i++) {
			ArticleCategory entity = new ArticleCategory();
			this.articleCategoryService.save(entity);
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Page<ArticleCategory> page = this.articleCategoryService.getPage(searchParams, 1, 5);
		Assert.assertTrue(page != null && page.getSize() == 5);
		searchParams.put("EQ_name", "test-10");
		page = this.articleCategoryService.getPage(searchParams, 1, 5);
		Assert.assertTrue(page != null && page.getTotalElements() == 1);
		searchParams = new HashMap<String, Object>();
		searchParams.put("LIKE_name", "test");
		page = this.articleCategoryService.getPage(searchParams, 1, 5);

		Assert.assertTrue(page != null && page.getTotalElements() == 25 && page.getNumberOfElements() == 5);

	}

	@Test
	public void testGet() throws Exception {
		Long id = null;
		for (int i = 0; i < 25; i++) {
			ArticleCategory entity = new ArticleCategory();
			this.articleCategoryService.save(entity);
			id = entity.getId();
		}
		ArticleCategory e = this.articleCategoryService.get(id);
		Assert.assertTrue(e != null);
	}

	@Test
	public void testGetByIds() throws Exception {
		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < 25; i++) {
			ArticleCategory entity = new ArticleCategory();
			this.articleCategoryService.save(entity);
			if (i < 5) {
				ids.add(entity.getId());
			}
		}
		List<ArticleCategory> entities = this.articleCategoryService.getAll(ids);
		Assert.assertTrue(entities != null && entities.size() == 5);
	}
}

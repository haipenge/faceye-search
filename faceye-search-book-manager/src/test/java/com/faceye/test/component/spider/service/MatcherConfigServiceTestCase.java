package com.faceye.test.component.spider.service;

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

import com.faceye.component.spider.doc.MatcherConfig;
import com.faceye.component.spider.service.MatcherConfigService;
import com.faceye.test.feature.service.BaseServiceTestCase;


/**
 * MatcherConfig  服务层测试用例
 * 
 * @author @haipenge haipenge@gmail.com Create Date:2014年5月20日
 */
public class MatcherConfigServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private MatcherConfigService matcherConfigService = null;
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
		Assert.assertTrue(matcherConfigService != null);
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
		//this.matcherConfigService.removeAllInBatch();
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
		MatcherConfig entity = new MatcherConfig();
		this.matcherConfigService.save(entity);
		List<MatcherConfig> entites = this.matcherConfigService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testSaveAndFlush() throws Exception {
		MatcherConfig entity = new MatcherConfig();
		this.matcherConfigService.save(entity);
		List<MatcherConfig> entites = this.matcherConfigService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testMultiSave() throws Exception {
		for (int i = 0; i < 5; i++) {
			MatcherConfig entity = new MatcherConfig();
			this.matcherConfigService.save(entity);
		}
		List<MatcherConfig> entities = this.matcherConfigService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testRemoveById() throws Exception {
		MatcherConfig entity = new MatcherConfig();
		this.matcherConfigService.save(entity);
		logger.debug(">>Entity id is:" + entity.getId());
		MatcherConfig e = this.matcherConfigService.get(entity.getId());
		Assert.assertTrue(e != null);
	}

	@Test
	public void testRemoveEntity() throws Exception {
		MatcherConfig entity = new MatcherConfig();
		this.matcherConfigService.save(entity);
		this.matcherConfigService.remove(entity);
		List<MatcherConfig> entities = this.matcherConfigService.getAll();
		Assert.assertTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAllInBatch() throws Exception {
		for (int i = 0; i < 5; i++) {
			MatcherConfig entity = new MatcherConfig();
			this.matcherConfigService.save(entity);
		}
		List<MatcherConfig> entities = this.matcherConfigService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
		this.matcherConfigService.removeAllInBatch();
		entities = this.matcherConfigService.getAll();
		Assert.assertTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			MatcherConfig entity = new MatcherConfig();
			this.matcherConfigService.save(entity);
		}
		this.matcherConfigService.removeAll();
		List<MatcherConfig> entities = this.matcherConfigService.getAll();
		Assert.assertTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveListInBatch() throws Exception {
		List<MatcherConfig> entities = new ArrayList<MatcherConfig>();
		for (int i = 0; i < 5; i++) {
			MatcherConfig entity = new MatcherConfig();
			
			this.matcherConfigService.save(entity);
			entities.add(entity);
		}
		this.matcherConfigService.removeInBatch(entities);
		entities = this.matcherConfigService.getAll();
		Assert.assertTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testGetAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			MatcherConfig entity = new MatcherConfig();
			this.matcherConfigService.save(entity);
		}
		List<MatcherConfig> entities = this.matcherConfigService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testGetPage() throws Exception {
		for (int i = 0; i < 25; i++) {
			MatcherConfig entity = new MatcherConfig();
			this.matcherConfigService.save(entity);
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Page<MatcherConfig> page = this.matcherConfigService.getPage(searchParams, 1, 5);
		Assert.assertTrue(page != null && page.getSize() == 5);
		searchParams.put("EQ_name", "test-10");
		page = this.matcherConfigService.getPage(searchParams, 1, 5);
		Assert.assertTrue(page != null && page.getTotalElements() == 1);
		searchParams = new HashMap<String, Object>();
		searchParams.put("LIKE_name", "test");
		page = this.matcherConfigService.getPage(searchParams, 1, 5);

		Assert.assertTrue(page != null && page.getTotalElements() == 25 && page.getNumberOfElements() == 5);

	}

	@Test
	public void testGet() throws Exception {
		Long id = null;
		for (int i = 0; i < 25; i++) {
			MatcherConfig entity = new MatcherConfig();
			this.matcherConfigService.save(entity);
			id = entity.getId();
		}
		MatcherConfig e = this.matcherConfigService.get(id);
		Assert.assertTrue(e != null);
	}

	@Test
	public void testGetByIds() throws Exception {
		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < 25; i++) {
			MatcherConfig entity = new MatcherConfig();
			this.matcherConfigService.save(entity);
			if (i < 5) {
				ids.add(entity.getId());
			}
		}
		List<MatcherConfig> entities = this.matcherConfigService.getAll(ids);
		Assert.assertTrue(entities != null && entities.size() == 5);
	}
}

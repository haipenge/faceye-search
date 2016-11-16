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
import org.springframework.util.Assert;

import com.faceye.component.spider.doc.Site;
import com.faceye.feature.repository.SearchFilter;
import com.faceye.component.spider.service.SiteService;
import com.faceye.test.feature.service.BaseServiceTestCase;


/**
 * Site  服务层测试用例
 * 
 * @author @haipenge haipenge@gmail.com Create Date:2014年5月20日
 */
public class SiteServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private SiteService siteService = null;
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
		Assert.isTrue(siteService != null);
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
//		this.siteService.removeAllInBatch();
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
		Site entity = new Site();
		this.siteService.save(entity);
		List<Site> entites = this.siteService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testSaveAndFlush() throws Exception {
		Site entity = new Site();
		this.siteService.save(entity);
		List<Site> entites = this.siteService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testMultiSave() throws Exception {
		for (int i = 0; i < 5; i++) {
			Site entity = new Site();
			this.siteService.save(entity);
		}
		List<Site> entities = this.siteService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testRemoveById() throws Exception {
		Site entity = new Site();
		this.siteService.save(entity);
		logger.debug(">>Entity id is:" + entity.getId());
		Site e = this.siteService.get(entity.getId());
		Assert.isTrue(e != null);
	}

	@Test
	public void testRemoveEntity() throws Exception {
		Site entity = new Site();
		this.siteService.save(entity);
		this.siteService.remove(entity);
		List<Site> entities = this.siteService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAllInBatch() throws Exception {
		for (int i = 0; i < 5; i++) {
			Site entity = new Site();
			this.siteService.save(entity);
		}
		List<Site> entities = this.siteService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
		this.siteService.removeAllInBatch();
		entities = this.siteService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			Site entity = new Site();
			this.siteService.save(entity);
		}
		this.siteService.removeAll();
		List<Site> entities = this.siteService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveListInBatch() throws Exception {
		List<Site> entities = new ArrayList<Site>();
		for (int i = 0; i < 5; i++) {
			Site entity = new Site();
			
			this.siteService.save(entity);
			entities.add(entity);
		}
		this.siteService.removeInBatch(entities);
		entities = this.siteService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testGetAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			Site entity = new Site();
			this.siteService.save(entity);
		}
		List<Site> entities = this.siteService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testGetPage() throws Exception {
		for (int i = 0; i < 25; i++) {
			Site entity = new Site();
			this.siteService.save(entity);
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Page<Site> page = this.siteService.getPage(searchParams, 1, 5);
		Assert.isTrue(page != null && page.getSize() == 5);
		searchParams.put("EQ|name", "test-10");
		page = this.siteService.getPage(searchParams, 1, 5);
		Assert.isTrue(page != null && page.getTotalElements() == 1);
		searchParams = new HashMap<String, Object>();
		searchParams.put("LIKE|name", "test");
		page = this.siteService.getPage(searchParams, 1, 5);

		Assert.isTrue(page != null && page.getTotalElements() == 25 && page.getNumberOfElements() == 5);

	}

	@Test
	public void testGet() throws Exception {
		Long id = null;
		for (int i = 0; i < 25; i++) {
			Site entity = new Site();
			this.siteService.save(entity);
			id = entity.getId();
		}
		Site e = this.siteService.get(id);
		Assert.isTrue(e != null);
	}

	@Test
	public void testGetByIds() throws Exception {
		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < 25; i++) {
			Site entity = new Site();
			this.siteService.save(entity);
			if (i < 5) {
				ids.add(entity.getId());
			}
		}
		List<Site> entities = this.siteService.getAll(ids);
		Assert.isTrue(entities != null && entities.size() == 5);
	}
}

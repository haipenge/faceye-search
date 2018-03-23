package com.faceye.test.component.book.service;

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

import com.faceye.component.book.doc.Section;
import com.faceye.component.book.service.SectionService;
import com.faceye.test.feature.service.BaseServiceTestCase;


/**
 * Section  服务层测试用例
 * 
 * @author @haipenge haipenge@gmail.com Create Date:2014年5月20日
 */
public class SectionServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private SectionService sectionService = null;
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
		Assert.assertTrue(sectionService != null);
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
//		this.sectionService.removeAllInBatch();
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
		Section entity = new Section();
		this.sectionService.save(entity);
		List<Section> entites = this.sectionService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testSaveAndFlush() throws Exception {
		Section entity = new Section();
		this.sectionService.save(entity);
		List<Section> entites = this.sectionService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testMultiSave() throws Exception {
		for (int i = 0; i < 5; i++) {
			Section entity = new Section();
			this.sectionService.save(entity);
		}
		List<Section> entities = this.sectionService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testRemoveById() throws Exception {
		Section entity = new Section();
		this.sectionService.save(entity);
		logger.debug(">>Entity id is:" + entity.getId());
		Section e = this.sectionService.get(entity.getId());
		Assert.assertTrue(e != null);
	}

	@Test
	public void testRemoveEntity() throws Exception {
		Section entity = new Section();
		this.sectionService.save(entity);
		this.sectionService.remove(entity);
		List<Section> entities = this.sectionService.getAll();
		Assert.assertTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAllInBatch() throws Exception {
		for (int i = 0; i < 5; i++) {
			Section entity = new Section();
			this.sectionService.save(entity);
		}
		List<Section> entities = this.sectionService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
		this.sectionService.removeAllInBatch();
		entities = this.sectionService.getAll();
		Assert.assertTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			Section entity = new Section();
			this.sectionService.save(entity);
		}
		this.sectionService.removeAll();
		List<Section> entities = this.sectionService.getAll();
		Assert.assertTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveListInBatch() throws Exception {
		List<Section> entities = new ArrayList<Section>();
		for (int i = 0; i < 5; i++) {
			Section entity = new Section();
			
			this.sectionService.save(entity);
			entities.add(entity);
		}
		this.sectionService.removeInBatch(entities);
		entities = this.sectionService.getAll();
		Assert.assertTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testGetAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			Section entity = new Section();
			this.sectionService.save(entity);
		}
		List<Section> entities = this.sectionService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testGetPage() throws Exception {
		for (int i = 0; i < 25; i++) {
			Section entity = new Section();
			this.sectionService.save(entity);
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Page<Section> page = this.sectionService.getPage(searchParams, 1, 5);
		Assert.assertTrue(page != null && page.getSize() == 5);
		searchParams.put("EQ_name", "test-10");
		page = this.sectionService.getPage(searchParams, 1, 5);
		Assert.assertTrue(page != null && page.getTotalElements() == 1);
		searchParams = new HashMap<String, Object>();
		searchParams.put("LIKE_name", "test");
		page = this.sectionService.getPage(searchParams, 1, 5);

		Assert.assertTrue(page != null && page.getTotalElements() == 25 && page.getNumberOfElements() == 5);

	}

	@Test
	public void testGet() throws Exception {
		Long id = null;
		for (int i = 0; i < 25; i++) {
			Section entity = new Section();
			this.sectionService.save(entity);
			id = entity.getId();
		}
		Section e = this.sectionService.get(id);
		Assert.assertTrue(e != null);
	}

	@Test
	public void testGetByIds() throws Exception {
		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < 25; i++) {
			Section entity = new Section();
			this.sectionService.save(entity);
			if (i < 5) {
				ids.add(entity.getId());
			}
		}
		List<Section> entities = this.sectionService.getAll(ids);
		Assert.assertTrue(entities != null && entities.size() == 5);
	}
}

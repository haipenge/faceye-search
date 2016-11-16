package com.faceye.test.component.parse.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.faceye.component.parse.service.FilterWordService;
import com.faceye.test.feature.service.BaseServiceTestCase;


/**
 * FilterWord  服务层测试用例
 * 
 * @author @haipenge haipenge@gmail.com Create Date:2014年5月20日
 */
public class FilterWordServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private FilterWordService filterWordService = null;
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
		Assert.isTrue(filterWordService != null);
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
//		this.filterWordService.removeAllInBatch();
	}

	/**
	 *  保存测试
	 * @todo
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年5月20日
	 */
//	@Test
//	public void testSave() throws Exception {
//		FilterWord entity = new FilterWord();
//		this.filterWordService.save(entity);
//		List<FilterWord> entites = this.filterWordService.getAll();
//		Assert.isTrue(CollectionUtils.isNotEmpty(entites));
//	}
//
//	@Test
//	public void testSaveAndFlush() throws Exception {
//		FilterWord entity = new FilterWord();
//		this.filterWordService.save(entity);
//		List<FilterWord> entites = this.filterWordService.getAll();
//		Assert.isTrue(CollectionUtils.isNotEmpty(entites));
//	}
//
//	@Test
//	public void testMultiSave() throws Exception {
//		for (int i = 0; i < 5; i++) {
//			FilterWord entity = new FilterWord();
//			this.filterWordService.save(entity);
//		}
//		List<FilterWord> entities = this.filterWordService.getAll();
//		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
//	}
//
//	@Test
//	public void testRemoveById() throws Exception {
//		FilterWord entity = new FilterWord();
//		this.filterWordService.save(entity);
//		logger.debug(">>Entity id is:" + entity.getId());
//		FilterWord e = this.filterWordService.get(entity.getId());
//		Assert.isTrue(e != null);
//	}
//
//	@Test
//	public void testRemoveEntity() throws Exception {
//		FilterWord entity = new FilterWord();
//		this.filterWordService.save(entity);
//		this.filterWordService.remove(entity);
//		List<FilterWord> entities = this.filterWordService.getAll();
//		Assert.isTrue(CollectionUtils.isEmpty(entities));
//	}
//
//	@Test
//	public void testRemoveAllInBatch() throws Exception {
//		for (int i = 0; i < 5; i++) {
//			FilterWord entity = new FilterWord();
//			this.filterWordService.save(entity);
//		}
//		List<FilterWord> entities = this.filterWordService.getAll();
//		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
//		this.filterWordService.removeAllInBatch();
//		entities = this.filterWordService.getAll();
//		Assert.isTrue(CollectionUtils.isEmpty(entities));
//	}
//
//	@Test
//	public void testRemoveAll() throws Exception {
//		for (int i = 0; i < 5; i++) {
//			FilterWord entity = new FilterWord();
//			this.filterWordService.save(entity);
//		}
//		this.filterWordService.removeAll();
//		List<FilterWord> entities = this.filterWordService.getAll();
//		Assert.isTrue(CollectionUtils.isEmpty(entities));
//	}
//
//	@Test
//	public void testRemoveListInBatch() throws Exception {
//		List<FilterWord> entities = new ArrayList<FilterWord>();
//		for (int i = 0; i < 5; i++) {
//			FilterWord entity = new FilterWord();
//			
//			this.filterWordService.save(entity);
//			entities.add(entity);
//		}
//		this.filterWordService.removeInBatch(entities);
//		entities = this.filterWordService.getAll();
//		Assert.isTrue(CollectionUtils.isEmpty(entities));
//	}
//
//	@Test
//	public void testGetAll() throws Exception {
//		for (int i = 0; i < 5; i++) {
//			FilterWord entity = new FilterWord();
//			this.filterWordService.save(entity);
//		}
//		List<FilterWord> entities = this.filterWordService.getAll();
//		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
//	}
//
//	@Test
//	public void testGetPage() throws Exception {
//		for (int i = 0; i < 25; i++) {
//			FilterWord entity = new FilterWord();
//			this.filterWordService.save(entity);
//		}
//		Map<String, Object> searchParams = new HashMap<String, Object>();
//		Page<FilterWord> page = this.filterWordService.getPage(searchParams, 1, 5);
//		Assert.isTrue(page != null && page.getSize() == 5);
//		searchParams.put("EQ_name", "test-10");
//		page = this.filterWordService.getPage(searchParams, 1, 5);
//		Assert.isTrue(page != null && page.getTotalElements() == 1);
//		searchParams = new HashMap<String, Object>();
//		searchParams.put("LIKE_name", "test");
//		page = this.filterWordService.getPage(searchParams, 1, 5);
//
//		Assert.isTrue(page != null && page.getTotalElements() == 25 && page.getNumberOfElements() == 5);
//
//	}
//
//	@Test
//	public void testGet() throws Exception {
//		Long id = null;
//		for (int i = 0; i < 25; i++) {
//			FilterWord entity = new FilterWord();
//			this.filterWordService.save(entity);
//			id = entity.getId();
//		}
//		FilterWord e = this.filterWordService.get(id);
//		Assert.isTrue(e != null);
//	}

//	@Test
//	public void testGetByIds() throws Exception {
//		List<Long> ids = new ArrayList<Long>();
//		for (int i = 0; i < 25; i++) {
//			FilterWord entity = new FilterWord();
//			this.filterWordService.save(entity);
//			if (i < 5) {
//				ids.add(entity.getId());
//			}
//		}
//		List<FilterWord> entities = this.filterWordService.getAll(ids);
//		Assert.isTrue(entities != null && entities.size() == 5);
//	}
	@Test
	public void testInit() throws Exception{
		this.filterWordService.init();
	}
}

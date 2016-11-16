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
import org.springframework.util.Assert;

import com.faceye.component.search.doc.RequestRecord;
import com.faceye.component.search.service.RequestRecordService;
import com.faceye.test.feature.service.BaseServiceTestCase;


/**
 * RequestRecord  服务层测试用例
 * 
 * @author @haipenge haipenge@gmail.com Create Date:2014年5月20日
 */
public class RequestRecordServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private RequestRecordService requestRecordService = null;
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
		Assert.isTrue(requestRecordService != null);
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
//		this.requestRecordService.removeAllInBatch();
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
		RequestRecord entity = new RequestRecord();
		this.requestRecordService.save(entity);
		List<RequestRecord> entites = this.requestRecordService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testSaveAndFlush() throws Exception {
		RequestRecord entity = new RequestRecord();
		this.requestRecordService.save(entity);
		List<RequestRecord> entites = this.requestRecordService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testMultiSave() throws Exception {
		for (int i = 0; i < 5; i++) {
			RequestRecord entity = new RequestRecord();
			this.requestRecordService.save(entity);
		}
		List<RequestRecord> entities = this.requestRecordService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testRemoveById() throws Exception {
		RequestRecord entity = new RequestRecord();
		this.requestRecordService.save(entity);
		logger.debug(">>Entity id is:" + entity.getId());
		RequestRecord e = this.requestRecordService.get(entity.getId());
		Assert.isTrue(e != null);
	}

	@Test
	public void testRemoveEntity() throws Exception {
		RequestRecord entity = new RequestRecord();
		this.requestRecordService.save(entity);
		this.requestRecordService.remove(entity);
		List<RequestRecord> entities = this.requestRecordService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAllInBatch() throws Exception {
		for (int i = 0; i < 5; i++) {
			RequestRecord entity = new RequestRecord();
			this.requestRecordService.save(entity);
		}
		List<RequestRecord> entities = this.requestRecordService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
		this.requestRecordService.removeAllInBatch();
		entities = this.requestRecordService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			RequestRecord entity = new RequestRecord();
			this.requestRecordService.save(entity);
		}
		this.requestRecordService.removeAll();
		List<RequestRecord> entities = this.requestRecordService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveListInBatch() throws Exception {
		List<RequestRecord> entities = new ArrayList<RequestRecord>();
		for (int i = 0; i < 5; i++) {
			RequestRecord entity = new RequestRecord();
			
			this.requestRecordService.save(entity);
			entities.add(entity);
		}
		this.requestRecordService.removeInBatch(entities);
		entities = this.requestRecordService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testGetAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			RequestRecord entity = new RequestRecord();
			this.requestRecordService.save(entity);
		}
		List<RequestRecord> entities = this.requestRecordService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testGetPage() throws Exception {
		for (int i = 0; i < 25; i++) {
			RequestRecord entity = new RequestRecord();
			this.requestRecordService.save(entity);
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Page<RequestRecord> page = this.requestRecordService.getPage(searchParams, 1, 5);
		Assert.isTrue(page != null && page.getSize() == 5);
		searchParams.put("EQ|name", "test-10");
		page = this.requestRecordService.getPage(searchParams, 1, 5);
		Assert.isTrue(page != null && page.getTotalElements() == 1);
		searchParams = new HashMap<String, Object>();
		searchParams.put("LIKE|name", "test");
		page = this.requestRecordService.getPage(searchParams, 1, 5);

		Assert.isTrue(page != null && page.getTotalElements() == 25 && page.getNumberOfElements() == 5);

	}

	@Test
	public void testGet() throws Exception {
		Long id = null;
		for (int i = 0; i < 25; i++) {
			RequestRecord entity = new RequestRecord();
			this.requestRecordService.save(entity);
			id = entity.getId();
		}
		RequestRecord e = this.requestRecordService.get(id);
		Assert.isTrue(e != null);
	}

	@Test
	public void testGetByIds() throws Exception {
		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < 25; i++) {
			RequestRecord entity = new RequestRecord();
			this.requestRecordService.save(entity);
			if (i < 5) {
				ids.add(entity.getId());
			}
		}
		List<RequestRecord> entities = this.requestRecordService.getAll(ids);
		Assert.isTrue(entities != null && entities.size() == 5);
	}
}

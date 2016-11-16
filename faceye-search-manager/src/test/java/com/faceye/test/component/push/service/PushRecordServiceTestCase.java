package com.faceye.test.component.push.service;

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

import com.faceye.component.push.doc.PushRecord;
import com.faceye.component.push.service.PushRecordService;
import com.faceye.test.feature.service.BaseServiceTestCase;


/**
 * PushRecord  服务层测试用例
 * 
 * @author @haipenge haipenge@gmail.com Create Date:2014年5月20日
 */
public class PushRecordServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private PushRecordService pushRecordService = null;
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
		Assert.isTrue(pushRecordService != null);
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
		this.pushRecordService.removeAllInBatch();
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
		PushRecord entity = new PushRecord();
		this.pushRecordService.save(entity);
		List<PushRecord> entites = this.pushRecordService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testSaveAndFlush() throws Exception {
		PushRecord entity = new PushRecord();
		this.pushRecordService.save(entity);
		List<PushRecord> entites = this.pushRecordService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testMultiSave() throws Exception {
		for (int i = 0; i < 5; i++) {
			PushRecord entity = new PushRecord();
			this.pushRecordService.save(entity);
		}
		List<PushRecord> entities = this.pushRecordService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testRemoveById() throws Exception {
		PushRecord entity = new PushRecord();
		this.pushRecordService.save(entity);
		logger.debug(">>Entity id is:" + entity.getId());
		PushRecord e = this.pushRecordService.get(entity.getId());
		Assert.isTrue(e != null);
	}

	@Test
	public void testRemoveEntity() throws Exception {
		PushRecord entity = new PushRecord();
		this.pushRecordService.save(entity);
		this.pushRecordService.remove(entity);
		List<PushRecord> entities = this.pushRecordService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAllInBatch() throws Exception {
		for (int i = 0; i < 5; i++) {
			PushRecord entity = new PushRecord();
			this.pushRecordService.save(entity);
		}
		List<PushRecord> entities = this.pushRecordService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
		this.pushRecordService.removeAllInBatch();
		entities = this.pushRecordService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			PushRecord entity = new PushRecord();
			this.pushRecordService.save(entity);
		}
		this.pushRecordService.removeAll();
		List<PushRecord> entities = this.pushRecordService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveListInBatch() throws Exception {
		List<PushRecord> entities = new ArrayList<PushRecord>();
		for (int i = 0; i < 5; i++) {
			PushRecord entity = new PushRecord();
			
			this.pushRecordService.save(entity);
			entities.add(entity);
		}
		this.pushRecordService.removeInBatch(entities);
		entities = this.pushRecordService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testGetAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			PushRecord entity = new PushRecord();
			this.pushRecordService.save(entity);
		}
		List<PushRecord> entities = this.pushRecordService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testGetPage() throws Exception {
		for (int i = 0; i < 25; i++) {
			PushRecord entity = new PushRecord();
			this.pushRecordService.save(entity);
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Page<PushRecord> page = this.pushRecordService.getPage(searchParams, 1, 5);
		Assert.isTrue(page != null && page.getSize() == 5);
		searchParams.put("EQ_name", "test-10");
		page = this.pushRecordService.getPage(searchParams, 1, 5);
		Assert.isTrue(page != null && page.getTotalElements() == 1);
		searchParams = new HashMap<String, Object>();
		searchParams.put("LIKE_name", "test");
		page = this.pushRecordService.getPage(searchParams, 1, 5);

		Assert.isTrue(page != null && page.getTotalElements() == 25 && page.getNumberOfElements() == 5);

	}

	@Test
	public void testGet() throws Exception {
		Long id = null;
		for (int i = 0; i < 25; i++) {
			PushRecord entity = new PushRecord();
			this.pushRecordService.save(entity);
			id = entity.getId();
		}
		PushRecord e = this.pushRecordService.get(id);
		Assert.isTrue(e != null);
	}

	@Test
	public void testGetByIds() throws Exception {
		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < 25; i++) {
			PushRecord entity = new PushRecord();
			this.pushRecordService.save(entity);
			if (i < 5) {
				ids.add(entity.getId());
			}
		}
		List<PushRecord> entities = this.pushRecordService.getAll(ids);
		Assert.isTrue(entities != null && entities.size() == 5);
	}
}

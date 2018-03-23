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

import com.faceye.component.search.doc.Email;
import com.faceye.component.search.service.EmailService;
import com.faceye.test.feature.service.BaseServiceTestCase;


/**
 * Email  服务层测试用例
 * 
 * @author @haipenge haipenge@gmail.com Create Date:2014年5月20日
 */
public class EmailServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private EmailService emailService = null;
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
		Assert.assertTrue(emailService != null);
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
		//this.emailService.removeAllInBatch();
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
		Email entity = new Email();
		this.emailService.save(entity);
		List<Email> entites = this.emailService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testSaveAndFlush() throws Exception {
		Email entity = new Email();
		this.emailService.save(entity);
		List<Email> entites = this.emailService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testMultiSave() throws Exception {
		for (int i = 0; i < 5; i++) {
			Email entity = new Email();
			this.emailService.save(entity);
		}
		List<Email> entities = this.emailService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testRemoveById() throws Exception {
		Email entity = new Email();
		this.emailService.save(entity);
		logger.debug(">>Entity id is:" + entity.getId());
		Email e = this.emailService.get(entity.getId());
		Assert.assertTrue(e != null);
	}

	@Test
	public void testRemoveEntity() throws Exception {
		Email entity = new Email();
		this.emailService.save(entity);
		this.emailService.remove(entity);
		List<Email> entities = this.emailService.getAll();
		Assert.assertTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAllInBatch() throws Exception {
		for (int i = 0; i < 5; i++) {
			Email entity = new Email();
			this.emailService.save(entity);
		}
		List<Email> entities = this.emailService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
		this.emailService.removeAllInBatch();
		entities = this.emailService.getAll();
		Assert.assertTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			Email entity = new Email();
			this.emailService.save(entity);
		}
		this.emailService.removeAll();
		List<Email> entities = this.emailService.getAll();
		Assert.assertTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveListInBatch() throws Exception {
		List<Email> entities = new ArrayList<Email>();
		for (int i = 0; i < 5; i++) {
			Email entity = new Email();
			
			this.emailService.save(entity);
			entities.add(entity);
		}
		this.emailService.removeInBatch(entities);
		entities = this.emailService.getAll();
		Assert.assertTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testGetAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			Email entity = new Email();
			this.emailService.save(entity);
		}
		List<Email> entities = this.emailService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testGetPage() throws Exception {
		for (int i = 0; i < 25; i++) {
			Email entity = new Email();
			this.emailService.save(entity);
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Page<Email> page = this.emailService.getPage(searchParams, 1, 5);
		Assert.assertTrue(page != null && page.getSize() == 5);
		searchParams.put("EQ_name", "test-10");
		page = this.emailService.getPage(searchParams, 1, 5);
		Assert.assertTrue(page != null && page.getTotalElements() == 1);
		searchParams = new HashMap<String, Object>();
		searchParams.put("LIKE_name", "test");
		page = this.emailService.getPage(searchParams, 1, 5);

		Assert.assertTrue(page != null && page.getTotalElements() == 25 && page.getNumberOfElements() == 5);

	}

	@Test
	public void testGet() throws Exception {
		Long id = null;
		for (int i = 0; i < 25; i++) {
			Email entity = new Email();
			this.emailService.save(entity);
			id = entity.getId();
		}
		Email e = this.emailService.get(id);
		Assert.assertTrue(e != null);
	}

	@Test
	public void testGetByIds() throws Exception {
		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < 25; i++) {
			Email entity = new Email();
			this.emailService.save(entity);
			if (i < 5) {
				ids.add(entity.getId());
			}
		}
		List<Email> entities = this.emailService.getAll(ids);
		Assert.assertTrue(entities != null && entities.size() == 5);
	}
	@Test
	public void testReadAndImport() throws Exception{
		this.emailService.readAndImport();
	}
	@Test
	public void testSend() throws Exception{
		Long id=1370127L;
		this.emailService.send(id);
	}
}

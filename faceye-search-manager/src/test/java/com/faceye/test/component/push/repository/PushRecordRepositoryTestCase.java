package com.faceye.test.component.push.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;

import com.faceye.component.push.doc.PushRecord;
import com.faceye.component.push.repository.mongo.PushRecordRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * PushRecord DAO 测试
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月26日
 */
public class PushRecordRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private PushRecordRepository pushRecordRepository = null;

	@Before
	public void before() throws Exception {
		this.pushRecordRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		PushRecord entity = new PushRecord();
		this.pushRecordRepository.save(entity);
		Iterable<PushRecord> entities = this.pushRecordRepository.findAll();
		Assert.assertTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		PushRecord entity = new PushRecord();
		this.pushRecordRepository.save(entity);
        this.pushRecordRepository.deleteById(entity.getId());
        Iterable<PushRecord> entities = this.pushRecordRepository.findAll();
		Assert.assertTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		PushRecord entity = new PushRecord();
		this.pushRecordRepository.save(entity);
		PushRecord pushRecord=this.pushRecordRepository.findById(entity.getId()).get();
		Assert.assertTrue(pushRecord!=null);
	}

	
}

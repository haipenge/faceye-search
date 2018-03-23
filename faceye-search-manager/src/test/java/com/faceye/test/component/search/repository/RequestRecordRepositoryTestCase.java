package com.faceye.test.component.search.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;

import com.faceye.component.search.doc.RequestRecord;
import com.faceye.component.search.repository.mongo.RequestRecordRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * RequestRecord DAO 测试
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月26日
 */
public class RequestRecordRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private RequestRecordRepository requestRecordRepository = null;

	@Before
	public void before() throws Exception {
		this.requestRecordRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		RequestRecord entity = new RequestRecord();
		this.requestRecordRepository.save(entity);
		Iterable<RequestRecord> entities = this.requestRecordRepository.findAll();
		Assert.assertTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		RequestRecord entity = new RequestRecord();
		this.requestRecordRepository.save(entity);
        this.requestRecordRepository.deleteById(entity.getId());
        Iterable<RequestRecord> entities = this.requestRecordRepository.findAll();
		Assert.assertTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		RequestRecord entity = new RequestRecord();
		this.requestRecordRepository.save(entity);
		RequestRecord requestRecord=this.requestRecordRepository.findById(entity.getId()).get();
		Assert.assertTrue(requestRecord!=null);
	}

	
}

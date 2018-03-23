package com.faceye.test.component.parse.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;

import com.faceye.component.parse.doc.FilterWord;
import com.faceye.component.parse.repository.mongo.FilterWordRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * FilterWord DAO 测试
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月26日
 */
public class FilterWordRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private FilterWordRepository filterWordRepository = null;

	@Before
	public void before() throws Exception {
//		this.filterWordRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		FilterWord entity = new FilterWord();
		this.filterWordRepository.save(entity);
		Iterable<FilterWord> entities = this.filterWordRepository.findAll();
		Assert.assertTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		FilterWord entity = new FilterWord();
		this.filterWordRepository.save(entity);
        this.filterWordRepository.deleteById(entity.getId());
        Iterable<FilterWord> entities = this.filterWordRepository.findAll();
		Assert.assertTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		FilterWord entity = new FilterWord();
		this.filterWordRepository.save(entity);
		FilterWord filterWord=this.filterWordRepository.findById(entity.getId()).get();
		Assert.assertTrue(filterWord!=null);
	}

	
}

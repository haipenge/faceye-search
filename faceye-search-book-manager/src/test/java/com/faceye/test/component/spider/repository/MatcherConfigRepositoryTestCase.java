package com.faceye.test.component.spider.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;

import com.faceye.component.spider.doc.MatcherConfig;
import com.faceye.component.spider.repository.mongo.MatcherConfigRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * MatcherConfig DAO 测试
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月26日
 */
public class MatcherConfigRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private MatcherConfigRepository matcherConfigRepository = null;

	@Before
	public void before() throws Exception {
		//this.matcherConfigRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		MatcherConfig entity = new MatcherConfig();
		this.matcherConfigRepository.save(entity);
		Iterable<MatcherConfig> entities = this.matcherConfigRepository.findAll();
		Assert.assertTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		MatcherConfig entity = new MatcherConfig();
		this.matcherConfigRepository.save(entity);
        this.matcherConfigRepository.deleteById(entity.getId());
        Iterable<MatcherConfig> entities = this.matcherConfigRepository.findAll();
		Assert.assertTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		MatcherConfig entity = new MatcherConfig();
		this.matcherConfigRepository.save(entity);
		MatcherConfig matcherConfig=this.matcherConfigRepository.findById(entity.getId()).get();
		Assert.assertTrue(matcherConfig!=null);
	}

	
}

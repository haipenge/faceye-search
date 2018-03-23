package com.faceye.test.component.index.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;

import com.faceye.component.index.doc.AnalyzerWord;
import com.faceye.component.index.repository.mongo.AnalyzerWordRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * AnalyzerWord DAO 测试
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月26日
 */
public class AnalyzerWordRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private AnalyzerWordRepository analyzerWordRepository = null;

	@Before
	public void before() throws Exception {
//		this.analyzerWordRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		AnalyzerWord entity = new AnalyzerWord();
		this.analyzerWordRepository.save(entity);
		Iterable<AnalyzerWord> entities = this.analyzerWordRepository.findAll();
		Assert.assertTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		AnalyzerWord entity = new AnalyzerWord();
		this.analyzerWordRepository.save(entity);
        this.analyzerWordRepository.deleteById(entity.getId());
        Iterable<AnalyzerWord> entities = this.analyzerWordRepository.findAll();
		Assert.assertTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		AnalyzerWord entity = new AnalyzerWord();
		this.analyzerWordRepository.save(entity);
		AnalyzerWord analyzerWord=this.analyzerWordRepository.findById(entity.getId()).get();
		Assert.assertTrue(analyzerWord!=null);
	}

	
}

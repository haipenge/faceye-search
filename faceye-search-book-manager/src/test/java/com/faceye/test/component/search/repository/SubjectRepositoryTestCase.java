package com.faceye.test.component.search.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;

import com.faceye.component.search.doc.Subject;
import com.faceye.component.search.repository.mongo.SubjectRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * Subject DAO 测试
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月26日
 */
public class SubjectRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private SubjectRepository subjectRepository = null;

	@Before
	public void before() throws Exception {
//		this.subjectRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		Subject entity = new Subject();
		this.subjectRepository.save(entity);
		Iterable<Subject> entities = this.subjectRepository.findAll();
		Assert.assertTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		Subject entity = new Subject();
		this.subjectRepository.save(entity);
        this.subjectRepository.deleteById(entity.getId());
        Iterable<Subject> entities = this.subjectRepository.findAll();
		Assert.assertTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		Subject entity = new Subject();
		this.subjectRepository.save(entity);
		Subject subject=this.subjectRepository.findById(entity.getId()).get();
		Assert.assertTrue(subject!=null);
	}

	
}

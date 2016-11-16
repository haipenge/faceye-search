package com.faceye.test.component.search.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

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
		Assert.isTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		Subject entity = new Subject();
		this.subjectRepository.save(entity);
        this.subjectRepository.delete(entity.getId());
        Iterable<Subject> entities = this.subjectRepository.findAll();
		Assert.isTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		Subject entity = new Subject();
		this.subjectRepository.save(entity);
		Subject subject=this.subjectRepository.findOne(entity.getId());
		Assert.isTrue(subject!=null);
	}

	
}

package com.faceye.test.component.search.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.faceye.component.search.doc.Email;
import com.faceye.component.search.repository.mongo.EmailRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * Email DAO 测试
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月26日
 */
public class EmailRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private EmailRepository emailRepository = null;

	@Before
	public void before() throws Exception {
		//this.emailRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		Email entity = new Email();
		this.emailRepository.save(entity);
		Iterable<Email> entities = this.emailRepository.findAll();
		Assert.isTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		Email entity = new Email();
		this.emailRepository.save(entity);
        this.emailRepository.delete(entity.getId());
        Iterable<Email> entities = this.emailRepository.findAll();
		Assert.isTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		Email entity = new Email();
		this.emailRepository.save(entity);
		Email email=this.emailRepository.findOne(entity.getId());
		Assert.isTrue(email!=null);
	}

	
}

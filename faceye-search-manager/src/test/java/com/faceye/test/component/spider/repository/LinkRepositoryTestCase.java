package com.faceye.test.component.spider.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.repository.mongo.LinkRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * Link DAO 测试
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月26日
 */
public class LinkRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private LinkRepository linkRepository = null;

	@Before
	public void before() throws Exception {
//		this.linkRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		Link entity = new Link();
		this.linkRepository.save(entity);
		Iterable<Link> entities = this.linkRepository.findAll();
		Assert.isTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		Link entity = new Link();
		this.linkRepository.save(entity);
        this.linkRepository.delete(entity.getId());
        Iterable<Link> entities = this.linkRepository.findAll();
		Assert.isTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		Link entity = new Link();
		this.linkRepository.save(entity);
		Link link=this.linkRepository.findOne(entity.getId());
		Assert.isTrue(link!=null);
	}

	
}

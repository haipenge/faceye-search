package com.faceye.test.component.search.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.faceye.component.search.entity.Section;
import com.faceye.component.search.repository.jpa.SectionRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * Section DAO 测试
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月26日
 */
public class SectionRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private SectionRepository sectionRepository = null;

	@Before
	public void before() throws Exception {
		this.sectionRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		Section entity = new Section();
		this.sectionRepository.save(entity);
		Iterable<Section> entities = this.sectionRepository.findAll();
		Assert.isTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		Section entity = new Section();
		this.sectionRepository.save(entity);
        this.sectionRepository.delete(entity.getId());
        Iterable<Section> entities = this.sectionRepository.findAll();
		Assert.isTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		Section entity = new Section();
		this.sectionRepository.save(entity);
		Section section=this.sectionRepository.findOne(entity.getId());
		Assert.isTrue(section!=null);
	}

	
}

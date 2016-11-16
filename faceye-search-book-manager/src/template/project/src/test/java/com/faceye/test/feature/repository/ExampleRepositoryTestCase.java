package com.faceye.test.feature.repository;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import com.faceye.feature.entity.Example;
import com.faceye.feature.repository.ExampleRepository;

public class ExampleRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private ExampleRepository exampleRepository = null;

	@Before
	public void before() throws Exception {
		this.exampleRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		Example entity = new Example();
		entity.setName("test-entity");
		this.exampleRepository.save(entity);
		Iterable<Example> entities = this.exampleRepository.findAll();
		Assert.isTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		Example entity = new Example();
		entity.setName("test-entity");
		this.exampleRepository.save(entity);
        this.exampleRepository.delete(entity.getId());
        Iterable<Example> entities = this.exampleRepository.findAll();
		Assert.isTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		Example entity = new Example();
		entity.setName("test-entity");
		this.exampleRepository.save(entity);
		Example example=this.exampleRepository.findOne(entity.getId());
		Assert.isTrue(example!=null);
	}

	@Test
	public void testGetPage() throws Exception {
		for (int i = 0; i < 25; i++) {
			Example entity = new Example();
			entity.setName("test-" + i);
			this.exampleRepository.save(entity);
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Page<Example> page = this.exampleRepository.getPage(searchParams, 0, 5);
		Assert.isTrue(page != null && page.getSize() == 5);
		searchParams.put("EQ_name", "test-10");
		page = this.exampleRepository.getPage(searchParams, 0, 5);
		Assert.isTrue(page != null && page.getTotalElements() == 1);
		searchParams = new HashMap<String, Object>();
		searchParams.put("LIKE_name", "test");
		page = this.exampleRepository.getPage(searchParams, 0, 5);

		Assert.isTrue(page != null && page.getTotalElements() == 25 && page.getNumberOfElements() == 5);

	}
	
	
}

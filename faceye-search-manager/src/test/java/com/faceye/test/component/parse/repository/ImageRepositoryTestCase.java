package com.faceye.test.component.parse.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;

import com.faceye.component.parse.doc.Image;
import com.faceye.component.parse.repository.mongo.ImageRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * Image DAO 测试
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月26日
 */
public class ImageRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private ImageRepository imageRepository = null;

	@Before
	public void before() throws Exception {
//		this.imageRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		Image entity = new Image();
		this.imageRepository.save(entity);
		Iterable<Image> entities = this.imageRepository.findAll();
		Assert.assertTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		Image entity = new Image();
		this.imageRepository.save(entity);
        this.imageRepository.deleteById(entity.getId());
        Iterable<Image> entities = this.imageRepository.findAll();
		Assert.assertTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		Image entity = new Image();
		this.imageRepository.save(entity);
		Image image=this.imageRepository.findById(entity.getId()).get();
		Assert.assertTrue(image!=null);
	}

	
}

package com.faceye.test.component.weixin.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.faceye.component.weixin.doc.Weixin;
import com.faceye.component.weixin.repository.mongo.WeixinRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * Weixin DAO 测试
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月26日
 */
public class WeixinRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private WeixinRepository weixinRepository = null;

	@Before
	public void before() throws Exception {
		//this.weixinRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		Weixin entity = new Weixin();
		this.weixinRepository.save(entity);
		Iterable<Weixin> entities = this.weixinRepository.findAll();
		Assert.isTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		Weixin entity = new Weixin();
		this.weixinRepository.save(entity);
        this.weixinRepository.delete(entity.getId());
        Iterable<Weixin> entities = this.weixinRepository.findAll();
		Assert.isTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		Weixin entity = new Weixin();
		this.weixinRepository.save(entity);
		Weixin weixin=this.weixinRepository.findOne(entity.getId());
		Assert.isTrue(weixin!=null);
	}

	
}

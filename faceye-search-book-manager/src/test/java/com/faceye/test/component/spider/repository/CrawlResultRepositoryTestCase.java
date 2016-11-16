package com.faceye.test.component.spider.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.faceye.component.spider.entity.CrawlResult;
import com.faceye.component.spider.repository.jpa.CrawlResultRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * CrawlResult DAO 测试
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月26日
 */
public class CrawlResultRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private CrawlResultRepository crawlResultRepository = null;

	@Before
	public void before() throws Exception {
//		this.crawlResultRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		CrawlResult entity = new CrawlResult();
		this.crawlResultRepository.save(entity);
		Iterable<CrawlResult> entities = this.crawlResultRepository.findAll();
		Assert.isTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		CrawlResult entity = new CrawlResult();
		this.crawlResultRepository.save(entity);
        this.crawlResultRepository.delete(entity.getId());
        Iterable<CrawlResult> entities = this.crawlResultRepository.findAll();
		Assert.isTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		CrawlResult entity = new CrawlResult();
		this.crawlResultRepository.save(entity);
		CrawlResult crawlResult=this.crawlResultRepository.findOne(entity.getId());
		Assert.isTrue(crawlResult!=null);
	}

	
}

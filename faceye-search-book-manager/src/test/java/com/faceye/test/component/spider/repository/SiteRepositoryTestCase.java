package com.faceye.test.component.spider.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.repository.mongo.SiteRepository;
import com.faceye.feature.service.SequenceService;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * Site DAO 测试
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月26日
 */
public class SiteRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private SiteRepository siteRepository = null;
	@Autowired
	private SequenceService sequenceService=null;

	@Before
	public void before() throws Exception {
//		this.siteRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		Site entity = new Site();
		this.siteRepository.save(entity);
		Iterable<Site> entities = this.siteRepository.findAll();
		Assert.isTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		Site entity = new Site();
		this.siteRepository.save(entity);
        this.siteRepository.delete(entity.getId());
        Iterable<Site> entities = this.siteRepository.findAll();
		Assert.isTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		Site entity = new Site();
		this.siteRepository.save(entity);
		Site site=this.siteRepository.findOne(entity.getId());
		Assert.isTrue(site!=null);
	}
	
	@Test
	public void testGetSiteByName() throws Exception{
		String name="http://sohu.com";
		Site site=new Site();
		site.setName("http://sohu.com");
		site.setId(this.sequenceService.getNextSequence(Site.class.getName()));
		this.siteRepository.save(site);
		Site querySite=this.siteRepository.getSiteByName(name);
		Assert.isTrue(null!=querySite);
		
	}

	
}

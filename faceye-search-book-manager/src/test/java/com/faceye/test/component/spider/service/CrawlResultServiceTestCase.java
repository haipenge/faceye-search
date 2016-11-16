package com.faceye.test.component.spider.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;

import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.service.CrawlResultService;
import com.faceye.test.feature.service.BaseServiceTestCase;


/**
 * CrawlResult  服务层测试用例
 * 
 * @author @haipenge haipenge@gmail.com Create Date:2014年5月20日
 */
public class CrawlResultServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private CrawlResultService crawlResultService = null;
	/**
	 * 初始化
	 * @todo
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年5月20日
	 */
	@Before
	public void set() throws Exception {
		Assert.isTrue(crawlResultService != null);
	}

	/**
	 * 清理
	 * @todo
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年5月20日
	 */
	@After
	public void after() throws Exception {
//		this.crawlResultService.removeAllInBatch();
	}

	/**
	 *  保存测试
	 * @todo
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年5月20日
	 */
	@Test
	public void testSave() throws Exception {
		CrawlResult entity = new CrawlResult();
		entity.setLinkUrl("http://sohu.com");
		this.crawlResultService.save(entity);
		List<CrawlResult> entites = this.crawlResultService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testSaveAndFlush() throws Exception {
		CrawlResult entity = new CrawlResult();
		this.crawlResultService.save(entity);
		List<CrawlResult> entites = this.crawlResultService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testMultiSave() throws Exception {
		for (int i = 0; i < 5; i++) {
			CrawlResult entity = new CrawlResult();
			entity.setLinkUrl("http://sohu.com"+i);
			this.crawlResultService.save(entity);
		}
		List<CrawlResult> entities = this.crawlResultService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testRemoveById() throws Exception {
		CrawlResult entity = new CrawlResult();
		this.crawlResultService.save(entity);
		logger.debug(">>Entity id is:" + entity.getId());
		CrawlResult e = this.crawlResultService.get(entity.getId());
		Assert.isTrue(e != null);
	}

	@Test
	public void testRemoveEntity() throws Exception {
		CrawlResult entity = new CrawlResult();
		this.crawlResultService.save(entity);
		this.crawlResultService.remove(entity);
		List<CrawlResult> entities = this.crawlResultService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAllInBatch() throws Exception {
		for (int i = 0; i < 5; i++) {
			CrawlResult entity = new CrawlResult();
			this.crawlResultService.save(entity);
		}
		List<CrawlResult> entities = this.crawlResultService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
		this.crawlResultService.removeAllInBatch();
		entities = this.crawlResultService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			CrawlResult entity = new CrawlResult();
			this.crawlResultService.save(entity);
		}
		this.crawlResultService.removeAll();
		List<CrawlResult> entities = this.crawlResultService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveListInBatch() throws Exception {
		List<CrawlResult> entities = new ArrayList<CrawlResult>();
		for (int i = 0; i < 5; i++) {
			CrawlResult entity = new CrawlResult();
			
			this.crawlResultService.save(entity);
			entities.add(entity);
		}
		this.crawlResultService.removeInBatch(entities);
		entities = this.crawlResultService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testGetAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			CrawlResult entity = new CrawlResult();
			this.crawlResultService.save(entity);
		}
		List<CrawlResult> entities = this.crawlResultService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testGetPage() throws Exception {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ|siteId", "15");
		searchParams.put("ISFALSE|isParse", "0");
		Page<CrawlResult> page = this.crawlResultService.getPage(searchParams, 1, 25);
        Assert.isTrue(page.getContent().size()==25);		
        for(CrawlResult crawlResult:page.getContent()){
        	Assert.isTrue(crawlResult.getSiteId().toString().equals("15") && !crawlResult.getIsParse());
        }
        searchParams = new HashMap<String, Object>();
        searchParams.put("EQ|siteId", 15);
		searchParams.put("ISTRUE|isParse", "0");
		 page = this.crawlResultService.getPage(searchParams, 1, 25);
        Assert.isTrue(page.getContent().size()==25);		
        for(CrawlResult crawlResult:page.getContent()){
        	Assert.isTrue(crawlResult.getSiteId().toString().equals("15") && crawlResult.getIsParse());
        }
        searchParams = new HashMap<String, Object>();
        searchParams.put("EQ|siteId", 1);
        searchParams.put("EQ|linkType", 1);
		searchParams.put("ISFALSE|isParse", "0");
		 page = this.crawlResultService.getPage(searchParams, 1, 5);
        Assert.isTrue(page.getContent().size()==5);		
        for(CrawlResult crawlResult:page.getContent()){
        	Assert.isTrue(crawlResult.getSiteId().toString().equals("1") && !crawlResult.getIsParse());
        }

	}

	@Test
	public void testGet() throws Exception {
		Long id = null;
		for (int i = 0; i < 25; i++) {
			CrawlResult entity = new CrawlResult();
			this.crawlResultService.save(entity);
			id = entity.getId();
		}
		CrawlResult e = this.crawlResultService.get(id);
		Assert.isTrue(e != null);
	}

	@Test
	public void testGetByIds() throws Exception {
		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < 25; i++) {
			CrawlResult entity = new CrawlResult();
			this.crawlResultService.save(entity);
			if (i < 5) {
				ids.add(entity.getId());
			}
		}
		List<CrawlResult> entities = this.crawlResultService.getAll(ids);
		Assert.isTrue(entities != null && entities.size() == 5);
	}
	
	
}

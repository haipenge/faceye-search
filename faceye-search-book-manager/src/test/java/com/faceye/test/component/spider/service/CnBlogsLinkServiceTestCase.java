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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.junit.Assert;

import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteLinkService;
import com.faceye.test.feature.service.BaseServiceTestCase;


/**
 * Link  服务层测试用例
 * 
 * @author @haipenge haipenge@gmail.com Create Date:2014年5月20日
 */
public class CnBlogsLinkServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private LinkService linkService = null;
	
	@Autowired
	@Qualifier("cnBlogsLinkServiceImpl")
	private SiteLinkService siteLinkService=null;
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
		Assert.assertTrue(linkService != null);
	}

//	/**
//	 * 清理
//	 * @todo
//	 * @throws Exception
//	 * @author:@haipenge
//	 * haipenge@gmail.com
//	 * 2014年5月20日
//	 */
//	@After
//	public void after() throws Exception {
////		this.linkService.removeAllInBatch();
//	}
//
//	/**
//	 *  保存测试
//	 * @todo
//	 * @throws Exception
//	 * @author:@haipenge
//	 * haipenge@gmail.com
//	 * 2014年5月20日
//	 */
//	@Test
//	public void testSave() throws Exception {
//		Link entity = new Link();
//		this.linkService.save(entity);
//		List<Link> entites = this.linkService.getAll();
//		Assert.assertTrue(CollectionUtils.isNotEmpty(entites));
//	}
//
//	@Test
//	public void testSaveAndFlush() throws Exception {
//		Link entity = new Link();
//		this.linkService.save(entity);
//		List<Link> entites = this.linkService.getAll();
//		Assert.assertTrue(CollectionUtils.isNotEmpty(entites));
//	}
//
//	@Test
//	public void testMultiSave() throws Exception {
//		for (int i = 0; i < 5; i++) {
//			Link entity = new Link();
//			this.linkService.save(entity);
//		}
//		List<Link> entities = this.linkService.getAll();
//		Assert.assertTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
//	}
//
//	@Test
//	public void testRemoveById() throws Exception {
//		Link entity = new Link();
//		this.linkService.save(entity);
//		logger.debug(">>Entity id is:" + entity.getId());
//		Link e = this.linkService.get(entity.getId());
//		Assert.assertTrue(e != null);
//	}
//
//	@Test
//	public void testRemoveEntity() throws Exception {
//		Link entity = new Link();
//		this.linkService.save(entity);
//		this.linkService.remove(entity);
//		List<Link> entities = this.linkService.getAll();
//		Assert.assertTrue(CollectionUtils.isEmpty(entities));
//	}
//
//	@Test
//	public void testRemoveAllInBatch() throws Exception {
//		for (int i = 0; i < 5; i++) {
//			Link entity = new Link();
//			this.linkService.save(entity);
//		}
//		List<Link> entities = this.linkService.getAll();
//		Assert.assertTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
//		this.linkService.removeAllInBatch();
//		entities = this.linkService.getAll();
//		Assert.assertTrue(CollectionUtils.isEmpty(entities));
//	}
//
//	@Test
//	public void testRemoveAll() throws Exception {
//		for (int i = 0; i < 5; i++) {
//			Link entity = new Link();
//			this.linkService.save(entity);
//		}
//		this.linkService.removeAll();
//		List<Link> entities = this.linkService.getAll();
//		Assert.assertTrue(CollectionUtils.isEmpty(entities));
//	}
//
//	@Test
//	public void testRemoveListInBatch() throws Exception {
//		List<Link> entities = new ArrayList<Link>();
//		for (int i = 0; i < 5; i++) {
//			Link entity = new Link();
//			
//			this.linkService.save(entity);
//			entities.add(entity);
//		}
//		this.linkService.removeInBatch(entities);
//		entities = this.linkService.getAll();
//		Assert.assertTrue(CollectionUtils.isEmpty(entities));
//	}

	@Test
	public void testGetAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			Link entity = new Link();
			this.linkService.save(entity);
		}
		List<Link> entities = this.linkService.getAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testGetPage() throws Exception {
		for (int i = 0; i < 25; i++) {
			Link entity = new Link();
			this.linkService.save(entity);
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Page<Link> page = this.linkService.getPage(searchParams, 1, 5);
		Assert.assertTrue(page != null && page.getSize() == 5);
		searchParams.put("EQ_name", "test-10");
		page = this.linkService.getPage(searchParams, 1, 5);
		Assert.assertTrue(page != null && page.getTotalElements() == 1);
		searchParams = new HashMap<String, Object>();
		searchParams.put("LIKE_name", "test");
		page = this.linkService.getPage(searchParams, 1, 5);

		Assert.assertTrue(page != null && page.getTotalElements() == 25 && page.getNumberOfElements() == 5);

	}

	@Test
	public void testGet() throws Exception {
		Long id = null;
		for (int i = 0; i < 25; i++) {
			Link entity = new Link();
			this.linkService.save(entity);
			id = entity.getId();
		}
		Link e = this.linkService.get(id);
		Assert.assertTrue(e != null);
	}

	@Test
	public void testGetByIds() throws Exception {
		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < 25; i++) {
			Link entity = new Link();
			this.linkService.save(entity);
			if (i < 5) {
				ids.add(entity.getId());
			}
		}
		List<Link> entities = this.linkService.getAll(ids);
		Assert.assertTrue(entities != null && entities.size() == 5);
	}
	/**
	 * 测试初始化cnblogs链接
	 * @todo
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月8日
	 */
	@Test
	public void testSaveCnblosLinks() throws Exception{
		this.siteLinkService.saveInitLinks();
	}
	
	@Test
	public void testReInitCnBlogLInks() throws Exception{
		this.siteLinkService.reInitLinks();
	}
	@Test
	public void testIsLinkExist() throws Exception{
		String url="http://www.baidu.com";
		Boolean isExist=this.linkService.isLinkExist(url);
		Assert.assertTrue(isExist);
		url="http://www.cnblogs.com/Jialiang/p/3771671.html";
		isExist=this.linkService.isLinkExist(url);
		Assert.assertTrue(!isExist);
	}
}

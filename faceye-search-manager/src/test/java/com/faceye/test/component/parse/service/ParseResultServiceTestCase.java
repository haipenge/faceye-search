package com.faceye.test.component.parse.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.faceye.component.parse.doc.FilterWord;
import com.faceye.component.parse.service.ParseResultService;
import com.faceye.component.spider.service.CrawlResultService;
import com.faceye.test.feature.service.BaseServiceTestCase;

/**
 * ParseResult  服务层测试用例
 * 
 * @author @haipenge haipenge@gmail.com Create Date:2014年5月20日
 */
public class ParseResultServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private ParseResultService parseResultService = null;

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
		Assert.isTrue(parseResultService != null);
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
//		this.parseResultService.removeAllInBatch();
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
//		ParseResult entity = new ParseResult();
//		this.parseResultService.save(entity);
//		List<ParseResult> entites = this.parseResultService.getAll();
//		Assert.isTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testSaveAndFlush() throws Exception {
//		ParseResult entity = new ParseResult();
//		this.parseResultService.save(entity);
//		List<ParseResult> entites = this.parseResultService.getAll();
//		Assert.isTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testMultiSave() throws Exception {
//		for (int i = 0; i < 5; i++) {
//			ParseResult entity = new ParseResult();
//			this.parseResultService.save(entity);
//		}
//		List<ParseResult> entities = this.parseResultService.getAll();
//		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testRemoveById() throws Exception {
//		ParseResult entity = new ParseResult();
//		this.parseResultService.save(entity);
//		logger.debug(">>Entity id is:" + entity.getId());
//		ParseResult e = this.parseResultService.get(entity.getId());
//		Assert.isTrue(e != null);
	}

	@Test
	public void testRemoveEntity() throws Exception {
//		ParseResult entity = new ParseResult();
//		this.parseResultService.save(entity);
//		this.parseResultService.remove(entity);
//		List<ParseResult> entities = this.parseResultService.getAll();
//		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAllInBatch() throws Exception {
//		for (int i = 0; i < 5; i++) {
//			ParseResult entity = new ParseResult();
//			this.parseResultService.save(entity);
//		}
//		List<ParseResult> entities = this.parseResultService.getAll();
//		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
//		this.parseResultService.removeAllInBatch();
//		entities = this.parseResultService.getAll();
//		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAll() throws Exception {
//		for (int i = 0; i < 5; i++) {
//			ParseResult entity = new ParseResult();
//			this.parseResultService.save(entity);
//		}
//		this.parseResultService.removeAll();
//		List<ParseResult> entities = this.parseResultService.getAll();
//		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveListInBatch() throws Exception {
//		List<ParseResult> entities = new ArrayList<ParseResult>();
//		for (int i = 0; i < 5; i++) {
//			ParseResult entity = new ParseResult();
//
//			this.parseResultService.save(entity);
//			entities.add(entity);
//		}
//		this.parseResultService.removeInBatch(entities);
//		entities = this.parseResultService.getAll();
//		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testGetAll() throws Exception {
//		for (int i = 0; i < 5; i++) {
//			ParseResult entity = new ParseResult();
//			this.parseResultService.save(entity);
//		}
//		List<ParseResult> entities = this.parseResultService.getAll();
//		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testGetPage() throws Exception {
//		for (int i = 0; i < 25; i++) {
//			ParseResult entity = new ParseResult();
//			this.parseResultService.save(entity);
//		}
//		Map<String, Object> searchParams = new HashMap<String, Object>();
//		Page<ParseResult> page = this.parseResultService.getPage(searchParams, 1, 5);
//		Assert.isTrue(page != null && page.getSize() == 5);
//		searchParams.put("EQ_name", "test-10");
//		page = this.parseResultService.getPage(searchParams, 1, 5);
//		Assert.isTrue(page != null && page.getTotalElements() == 1);
//		searchParams = new HashMap<String, Object>();
//		searchParams.put("LIKE_name", "test");
//		page = this.parseResultService.getPage(searchParams, 1, 5);
//
//		Assert.isTrue(page != null && page.getTotalElements() == 25 && page.getNumberOfElements() == 5);

	}

	@Test
	public void testGet() throws Exception {
//		Long id = null;
//		for (int i = 0; i < 25; i++) {
//			ParseResult entity = new ParseResult();
//			this.parseResultService.save(entity);
//			id = entity.getId();
//		}
//		ParseResult e = this.parseResultService.get(id);
//		Assert.isTrue(e != null);
	}

	@Test
	public void testGetByIds() throws Exception {
//		List<Long> ids = new ArrayList<Long>();
//		for (int i = 0; i < 25; i++) {
//			ParseResult entity = new ParseResult();
//			this.parseResultService.save(entity);
//			if (i < 5) {
//				ids.add(entity.getId());
//			}
//		}
//		List<ParseResult> entities = this.parseResultService.getAll(ids);
//		Assert.isTrue(entities != null && entities.size() == 5);
	}

	@Test
	public void testSaveParseResult() throws IOException {
//		String path = "/work/Work/spider/crawl/20140708-21/com/cnblogs/20140708-213535-39.html";
//		String content = FileUtil.getInstance().read(path);
//		CrawlResult crawlResult = this.crawlResultService.get(1L);
//		ParseResult parseResult = new ParseResult();
//		parseResult.setContent(content);
//		parseResult.setCrawlResult(crawlResult);
//		this.parseResultService.save(parseResult);
//		List<ParseResult> parseResults=this.parseResultService.getAll();
//		Assert.isTrue(CollectionUtils.isNotEmpty(parseResults));
	}
	
	@Test
	public void testSaveParseResult2Mongo() throws Exception{
		this.parseResultService.saveParseResult2Mongo();
	}
	@Test
	public void testSaveReParseResult() throws Exception{
		this.parseResultService.saveRebuildParseResult();
	}
	
	@Test
	public void testGetFilter() throws Exception{
		List<FilterWord> words=this.parseResultService.testFilterWords(985L);
		Assert.isTrue(CollectionUtils.isNotEmpty(words));
	}
}

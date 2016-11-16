package com.faceye.test.component.index.service;

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

import com.faceye.component.index.doc.AnalyzerWord;
import com.faceye.component.index.service.AnalyzerWordService;
import com.faceye.feature.service.SequenceService;
import com.faceye.test.feature.service.BaseServiceTestCase;


/**
 * AnalyzerWord  服务层测试用例
 * 
 * @author @haipenge haipenge@gmail.com Create Date:2014年5月20日
 */
public class AnalyzerWordServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private AnalyzerWordService analyzerWordService = null;
	@Autowired
	private SequenceService sequenceService=null;
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
		Assert.isTrue(analyzerWordService != null);
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
//		this.analyzerWordService.removeAllInBatch();
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
		AnalyzerWord entity = new AnalyzerWord();
		entity.setId(this.sequenceService.getNextSequence(AnalyzerWord.class.getName()));
		entity.setWord("test");
		entity.setWordCount(1);
		this.analyzerWordService.save(entity);
		List<AnalyzerWord> entites = this.analyzerWordService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testSaveAndFlush() throws Exception {
		AnalyzerWord entity = new AnalyzerWord();
		this.analyzerWordService.save(entity);
		List<AnalyzerWord> entites = this.analyzerWordService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entites));
	}

	@Test
	public void testMultiSave() throws Exception {
		for (int i = 0; i < 5; i++) {
			AnalyzerWord entity = new AnalyzerWord();
			this.analyzerWordService.save(entity);
		}
		List<AnalyzerWord> entities = this.analyzerWordService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testRemoveById() throws Exception {
		AnalyzerWord entity = new AnalyzerWord();
		this.analyzerWordService.save(entity);
		logger.debug(">>Entity id is:" + entity.getId());
		AnalyzerWord e = this.analyzerWordService.get(entity.getId());
		Assert.isTrue(e != null);
	}

	@Test
	public void testRemoveEntity() throws Exception {
		AnalyzerWord entity = new AnalyzerWord();
		this.analyzerWordService.save(entity);
		this.analyzerWordService.remove(entity);
		List<AnalyzerWord> entities = this.analyzerWordService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAllInBatch() throws Exception {
		for (int i = 0; i < 5; i++) {
			AnalyzerWord entity = new AnalyzerWord();
			this.analyzerWordService.save(entity);
		}
		List<AnalyzerWord> entities = this.analyzerWordService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
		this.analyzerWordService.removeAllInBatch();
		entities = this.analyzerWordService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			AnalyzerWord entity = new AnalyzerWord();
			this.analyzerWordService.save(entity);
		}
		this.analyzerWordService.removeAll();
		List<AnalyzerWord> entities = this.analyzerWordService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testRemoveListInBatch() throws Exception {
		List<AnalyzerWord> entities = new ArrayList<AnalyzerWord>();
		for (int i = 0; i < 5; i++) {
			AnalyzerWord entity = new AnalyzerWord();
			
			this.analyzerWordService.save(entity);
			entities.add(entity);
		}
		this.analyzerWordService.removeInBatch(entities);
		entities = this.analyzerWordService.getAll();
		Assert.isTrue(CollectionUtils.isEmpty(entities));
	}

	@Test
	public void testGetAll() throws Exception {
		for (int i = 0; i < 5; i++) {
			AnalyzerWord entity = new AnalyzerWord();
			this.analyzerWordService.save(entity);
		}
		List<AnalyzerWord> entities = this.analyzerWordService.getAll();
		Assert.isTrue(CollectionUtils.isNotEmpty(entities) && entities.size() == 5);
	}

	@Test
	public void testGetPage() throws Exception {
		for (int i = 0; i < 25; i++) {
			AnalyzerWord entity = new AnalyzerWord();
			this.analyzerWordService.save(entity);
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Page<AnalyzerWord> page = this.analyzerWordService.getPage(searchParams, 1, 5);
		Assert.isTrue(page != null && page.getSize() == 5);
		searchParams.put("EQ_name", "test-10");
		page = this.analyzerWordService.getPage(searchParams, 1, 5);
		Assert.isTrue(page != null && page.getTotalElements() == 1);
		searchParams = new HashMap<String, Object>();
		searchParams.put("LIKE_name", "test");
		page = this.analyzerWordService.getPage(searchParams, 1, 5);

		Assert.isTrue(page != null && page.getTotalElements() == 25 && page.getNumberOfElements() == 5);

	}

	@Test
	public void testGet() throws Exception {
		Long id = null;
		for (int i = 0; i < 25; i++) {
			AnalyzerWord entity = new AnalyzerWord();
			this.analyzerWordService.save(entity);
			id = entity.getId();
		}
		AnalyzerWord e = this.analyzerWordService.get(id);
		Assert.isTrue(e != null);
	}

	@Test
	public void testGetByIds() throws Exception {
		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < 25; i++) {
			AnalyzerWord entity = new AnalyzerWord();
			this.analyzerWordService.save(entity);
			if (i < 5) {
				ids.add(entity.getId());
			}
		}
		List<AnalyzerWord> entities = this.analyzerWordService.getAll(ids);
		Assert.isTrue(entities != null && entities.size() == 5);
	}
}

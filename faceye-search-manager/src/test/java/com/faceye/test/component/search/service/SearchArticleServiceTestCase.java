package com.faceye.test.component.search.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.faceye.component.search.doc.Article;
import com.faceye.component.search.service.SearchArticleService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class SearchArticleServiceTestCase extends BaseServiceTestCase {
	@Autowired
	SearchArticleService searchArticleService = null;

	@Test
	public void testSave() throws Exception {
		// Article article=new Article();
		// article.setId(1L);
		// article.setName("test-name");
		// this.searchArticleService.save(article);
	}

	@Test
	public void testGet() throws Exception {
		// Long id=1L;
		// Article article=this.searchArticleService.get(id);
		// logger.debug("name is:"+article.getName());
		// Assert.isTrue(article!=null);
	}

	@Test
	public void testRemove() throws Exception {
		// Long id=1L;
		// this.searchArticleService.remove(id);
		// Article article=this.searchArticleService.get(id);
		// Assert.isTrue(null==article);
	}

	@Test
	public void testUpdate() throws Exception {
		// Article article =this.searchArticleService.get(1L);
		// article.setName("update-name-now");
		// this.searchArticleService.save(article);
		// Assert.isTrue(article!=null);
	}

	@Test
	public void testGetPage() throws Exception {
		// for(int i=0;i<20;i++){
		// Article article=new Article();
		// article.setId(new Long(i));
		// article.setAlias("alias-"+i);
		// article.setName("test-name"+i);
		// this.searchArticleService.save(article);
		// }
		// Page<Article> page=this.searchArticleService.getPage(null, 1, 5);
		// Assert.isTrue(page!=null && page.getContent().size()==5);
		Map searchParams = new HashMap();
		searchParams.put("isIndexed", Boolean.FALSE);
		Page<Article> articles = this.searchArticleService.getPage(searchParams, 1, 10);
		Assert.isTrue(articles != null && CollectionUtils.isNotEmpty(articles.getContent()) && articles.getContent().size() == 10);
	}

	@Test
	public void testDedup() throws Exception {
		this.searchArticleService.dedup();
	}

	@Test
	public void testPush2Weixin() throws Exception {
		String ids="64741,64746,64784";
		boolean res=this.searchArticleService.push2Weixin(ids);
		Assert.isTrue(res);
	}

}

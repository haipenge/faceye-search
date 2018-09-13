package com.faceye.test.component.dream.service;

import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.faceye.component.search.doc.Article;
import com.faceye.component.search.service.SearchArticleService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class SearchArticleServiceTestCase extends BaseServiceTestCase {

	@Autowired
	private SearchArticleService searchArticleService=null;
	@Test
	public void testGetArticleByName() throws Exception{
		String name="一步一步开发自己的博客站点（一） 需求分析 ";
		Article article=this.searchArticleService.getArticleByName(name);
		Assert.isTrue(article!=null);
	}
}

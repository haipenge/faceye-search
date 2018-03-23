package com.faceye.test.component.index.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.junit.Assert;

import com.faceye.component.index.service.impl.WordContainer;
import com.faceye.component.parse.doc.ParseResult;
import com.faceye.component.parse.service.ParseResultService;
import com.faceye.component.search.service.SearchService;
import com.faceye.component.search.service.impl.SearchResult;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class SearcherServiceTestCase extends BaseServiceTestCase {
	private Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
  private SearchService searchService=null;
	@Autowired
	private ParseResultService parseResltService=null;
	@Test
	public void testSearch() throws Exception{
		Page<SearchResult> pageResult=searchService.search("回收机制java测试",1, 10);
		for(SearchResult searchResult:pageResult.getContent()){
			logger.debug(">>Name is:"+searchResult.getName());
			logger.debug(">>Content is:\n"+searchResult.getContents().get(0));
			logger.debug("----------------------------------");
		}
		Assert.assertTrue(pageResult!=null && pageResult.getContent().size()>0);
	}
	@Test
	public void testGetAnalyzerResult() throws Exception{
		String str="子系统构建用户验证信息后将授权后的内容返回给客户端";
		String [] res=this.searchService.getAnalyzerResult(str);
		for(String s:res){
			logger.debug("Word is:"+s);
		}
		Assert.assertTrue(res!=null && res.length>0);
	}
	@Test
	public void testGetKeywords() throws Exception{
		ParseResult parseResult=this.parseResltService.get(982L);
		WordContainer container=null;
		String content=parseResult.getContent();
		if(null!=parseResult&&StringUtils.isNotEmpty(content)){
			 container=this.searchService.getKeywords(parseResult.getContent());
			 if(null!=container){
				 container.println();
			 }
		}
		Assert.assertTrue(null!=container &&CollectionUtils.isNotEmpty(container.getWords()));
	}
}

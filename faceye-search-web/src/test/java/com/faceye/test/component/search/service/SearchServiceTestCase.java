package com.faceye.test.component.search.service;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.junit.Assert;

import com.faceye.component.search.service.SearchService;
import com.faceye.component.search.service.impl.SearchResult;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class SearchServiceTestCase extends BaseServiceTestCase {

	@Autowired
	private SearchService searchService = null;

	@Test
	public void testSearch() throws Exception {
		String key = "sPring,Springframework";
        Page<SearchResult> searchResults=this.searchService.search(key, 1, 20);
        this.print(searchResults);
        Assert.assertTrue(CollectionUtils.isNotEmpty(searchResults.getContent()));
	}
	
	private void print(Page<SearchResult> searchResults){
		if(CollectionUtils.isNotEmpty(searchResults.getContent())){
			for(SearchResult searchResult:searchResults.getContent()){
				logger.debug(">>Name:"+searchResult.getName()+",length:"+searchResult.getContents().size());
			}
		}
	}
	
}

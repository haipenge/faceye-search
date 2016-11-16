package com.faceye.test.component.index.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.faceye.component.index.service.IndexService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class IndexServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private IndexService indexService = null;
    @Test
    public void testBuildIndex() throws Exception{
    	this.indexService.buildIndex();
    }
}

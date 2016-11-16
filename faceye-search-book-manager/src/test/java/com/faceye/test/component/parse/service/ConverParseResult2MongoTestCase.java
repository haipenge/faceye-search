package com.faceye.test.component.parse.service;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;

import com.faceye.component.parse.doc.ParseResult;
import com.faceye.component.parse.service.MySQL2MongoService;
import com.faceye.component.parse.service.ParseResultService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class ConverParseResult2MongoTestCase extends BaseServiceTestCase {

	@Autowired
	@Qualifier("parseResultConver2MongoServiceImpl")
	private MySQL2MongoService parseResultMySQL2MongoService=null;
	
	@Autowired
	private ParseResultService parseResultService=null;
	
	@Test
	public void testConver() throws Exception{
		parseResultMySQL2MongoService.conver();
        Page<ParseResult> parseResults=this.parseResultService.getPage(null, 1, 10);
        Assert.isTrue(parseResults!=null &&CollectionUtils.isNotEmpty(parseResults.getContent()));
        
	}
}

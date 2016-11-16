package com.faceye.test.component.parse.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.faceye.component.parse.service.ParseService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class ParseServiceTestCase extends BaseServiceTestCase {

	@Autowired
	@Qualifier("cnblogsParseServiceImpl")
	private ParseService cnblogsParseService = null;

	@Test
	public void testCnblogsParse() throws Exception {
		this.cnblogsParseService.saveParseResult();
	}
}

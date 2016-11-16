package com.faceye.test.component.parse.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.faceye.component.parse.service.ParseService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class OSChinaParseServiceTestCase extends BaseServiceTestCase {

	@Autowired
	@Qualifier("OSChinaParseServiceImpl")
	private ParseService oschinaParseService=null;
	@Test
	public void testSaveParse() throws Exception{
		this.oschinaParseService.saveParseResult();
	}
}

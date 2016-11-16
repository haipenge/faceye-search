package com.faceye.test.component.parse.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.faceye.component.parse.service.ParseService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class ITEyeParseServiceTestCase extends BaseServiceTestCase {

	@Autowired
	@Qualifier("ITEyeParseServiceImpl")
	private ParseService iteyeParseService=null;
	
	@Test
	public void testParse() throws Exception{
		this.iteyeParseService.saveParseResult();
	}
}

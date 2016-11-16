package com.faceye.test.component.parse.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.faceye.component.parse.service.ParseService;
import com.faceye.test.feature.service.BaseServiceTestCase;

/**
 * CSDN解析测试
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年9月14日
 */
public class CSDNParseServiceTestCase extends BaseServiceTestCase {

	@Autowired
	@Qualifier("CSDNParseServiceImpl")
	private ParseService csdnParseService = null;
	
	
	@Test
	public void testParse() throws Exception{
		this.csdnParseService.saveParseResult();
	}
}

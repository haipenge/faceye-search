package com.faceye.test.component.parse.service;

import org.apache.commons.collections.MapUtils;
import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.faceye.component.parse.service.MySQL2MongoService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class BookConver2MongoServiceTestCase extends BaseServiceTestCase {

	@Autowired
	@Qualifier("bookConver2MongoService")
	private MySQL2MongoService bookConver2MongoService=null;
	@Test
	public void testBookConver2Mongo() throws Exception{
		this.bookConver2MongoService.conver();
		Assert.isTrue(1==1);
	}
}

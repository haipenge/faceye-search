package com.faceye.test.component.parse.service;

import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.faceye.component.parse.service.BuildArticleCategoryInitService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class BuildArticleCategoryInitServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private BuildArticleCategoryInitService buildArticleCategoryInitService = null;

	@Test
	public void testBuild() throws Exception {
       this.buildArticleCategoryInitService.build();
       Assert.assertTrue(1==1);
	}
}

package com.faceye.test.component.index;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.faceye.component.index.util.DirectoryUtil;


@RunWith(JUnit4.class)
public class DirectoryUtilTestCase {
    private Logger logger=LoggerFactory.getLogger(getClass());
	@Test
	public void testGetChildrenDirs() throws Exception{
		String [] dirs=DirectoryUtil.getChildrenDirs("/work/Work/spider/index");
		if(dirs!=null){
			for(String dir:dirs){
				logger.debug(">>Dir is:"+dir);
			}
		}
		Assert.isTrue(dirs!=null&&dirs.length>0);
	}
}

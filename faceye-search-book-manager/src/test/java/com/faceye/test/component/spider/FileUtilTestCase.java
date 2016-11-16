package com.faceye.test.component.spider;

import org.junit.Test;

import com.faceye.component.spider.util.FileUtil;
import com.faceye.component.spider.util.PathUtil;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class FileUtilTestCase extends BaseServiceTestCase {
	@Test
	public void testWriteImg() throws Exception {
		String imgPath = "/work/picture/DSC00237.JPG";
		byte[] bytes = FileUtil.getInstance().readImg(imgPath);
		for (int i = 1; i < 5; i++) {
			String writeImgUrl = PathUtil.generateDynamicDirs();
			writeImgUrl+=PathUtil.generateFileName();
			writeImgUrl+=".JPG";
			FileUtil.getInstance().writeImg(bytes, writeImgUrl);
		}
	}
}

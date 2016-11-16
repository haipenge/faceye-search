package com.faceye.component.index.util;

import java.io.File;

/**
 * 目录工具类
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月15日
 */
public class DirectoryUtil {
	public static String[] getChildrenDirs(String parent) {
		String[] children = null;
		File file = new File(parent);
		if (file.isDirectory()) {
			children = file.list();
		}
		return children;
	}
}

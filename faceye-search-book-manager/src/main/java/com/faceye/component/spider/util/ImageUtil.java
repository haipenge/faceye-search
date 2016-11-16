package com.faceye.component.spider.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtil {
    private static Logger logger=LoggerFactory.getLogger(ImageUtil.class);
	/**
	 * 根据图片URL，取得图片文件的后缀名
	 * @todo
	 * @param url
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月5日
	 */
	public static String getImageSuffix(String url){
		String suffix="";
		suffix = StringUtils.substring(url, StringUtils.lastIndexOf(url, "."));
		logger.debug(">>FaceYe -->图片:" + url + ",图片后缀名为:" + suffix);
		if (StringUtils.isNotEmpty(suffix)) {
			if (suffix.toUpperCase().indexOf("PNG") != -1) {
				suffix = "png";
			} else if (suffix.toUpperCase().indexOf("JPG") != -1) {
				suffix = "jpg";
			} else if (suffix.toUpperCase().indexOf("JPEG") != -1) {
				suffix = "jpeg";
			} else if (suffix.toUpperCase().indexOf("GIF") != -1) {
				suffix = "gif";
			} else if (suffix.toUpperCase().indexOf("bmp") != -1) {
				suffix = "bmp";
			}
		}
		return suffix;
	}
}

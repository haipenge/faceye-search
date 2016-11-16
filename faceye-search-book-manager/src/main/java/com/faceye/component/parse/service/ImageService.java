package com.faceye.component.parse.service;

import com.faceye.component.parse.doc.Image;
import com.faceye.component.spider.doc.Link;
import com.faceye.feature.service.BaseService;

public interface ImageService extends BaseService<Image,Long>{

	/**
	 * 存储图片
	 * @todo
	 * @param link
	 * @param content
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月5日
	 */
	public void saveImage(Link link,byte[] content);
}/**@generate-service-source@**/

package com.faceye.component.parse.service;

import java.util.List;

import com.faceye.component.parse.doc.Image;
import com.faceye.component.spider.doc.Link;
import com.faceye.feature.service.BaseService;

public interface ImageService extends BaseService<Image, Long> {

	/**
	 * 存储图片
	 * @todo
	 * @param link
	 * @param content
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月5日
	 */
	public void saveImage(Link link, byte[] content);

	/**
	 * 根据图片访问路径查询
	 * @todo
	 * @param url
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月30日
	 */
	public Image getImageByUrl(String url);

	/**
	 * 取得一篇文章的全部图片
	 * @todo
	 * @param articleId
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月30日
	 */
	public List<Image> getImagesByArticleId(Long articleId);
}
/**@generate-service-source@**/

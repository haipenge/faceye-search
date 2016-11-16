package com.faceye.component.search.service;

import java.util.List;

import com.faceye.component.search.doc.Image;
import com.faceye.feature.service.BaseService;

public interface ImageService extends BaseService<Image, Long> {

	
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

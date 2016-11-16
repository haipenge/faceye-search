package com.faceye.component.search.repository.mongo;

import java.util.List;

import com.faceye.component.search.doc.Image;
import com.faceye.feature.repository.mongo.BaseMongoRepository;

/**
 * Image 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
public interface ImageRepository extends BaseMongoRepository<Image, Long> {

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
/**@generate-repository-source@**/

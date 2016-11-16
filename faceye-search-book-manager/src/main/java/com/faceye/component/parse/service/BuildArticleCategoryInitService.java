package com.faceye.component.parse.service;

import com.faceye.component.search.doc.ArticleCategory;

/**
 * 文章分类初始化
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年11月8日
 */
public interface BuildArticleCategoryInitService {

	/**
	 * 文章分类初始化
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年11月8日
	 */
	public void build();
	
	/**
	 * 构建文章分类
	 * @todo
	 * @param name
	 * @param content
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年12月29日
	 */
	public ArticleCategory buildArticleCategory(String name,String content);
}

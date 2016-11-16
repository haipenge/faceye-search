package com.faceye.component.search.service;

import java.util.List;

import com.faceye.component.search.doc.Article;
import com.faceye.feature.service.BaseService;

/**
 * 文章查询服务类
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年8月9日
 */
public interface SearchArticleService extends BaseService<Article,Long>{
	
	/**
	 * 根据标题取得文章
	 * @todo
	 * @param name
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月17日
	 */
	public Article getArticleByName(String name);
	
	public List<Article> getArticlesByName(String name);
	
}

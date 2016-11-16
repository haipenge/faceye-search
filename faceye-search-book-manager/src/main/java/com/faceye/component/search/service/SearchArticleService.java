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
	 * 合并相同标题的文章
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年10月6日
	 */
	public void dedup();
	
	
	/**
	 * 根据 文章 名查询
	 * @todo
	 * @param name
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年10月7日
	 */
	public List<Article> getArticlesByName(String name);
	
}

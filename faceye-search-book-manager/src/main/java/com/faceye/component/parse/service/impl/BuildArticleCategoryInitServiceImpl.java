package com.faceye.component.parse.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.BuildArticleCategoryInitService;
import com.faceye.component.search.doc.Article;
import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.component.search.service.ArticleCategoryService;
import com.faceye.component.search.service.SearchArticleService;

@Service
public class BuildArticleCategoryInitServiceImpl implements BuildArticleCategoryInitService {
  
	private Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private ArticleCategoryService articleCategoryService = null;
	@Autowired
	private SearchArticleService articleService = null;

	@Override
	public void build() {
		List<ArticleCategory> categories = this.articleCategoryService.getAll();
		int page = 1;
		int size = 1000;
		ArticleCategory other=this.articleCategoryService.getArticleCategoryByAlias("other");
		if (CollectionUtils.isNotEmpty(categories)) {
			Page<Article> articles = this.articleService.getPage(null, page, size);
			while (null != articles && CollectionUtils.isNotEmpty(articles.getContent())) {
				logger.debug(">>FaceYe -->build article,page is:"+page);
				for (Article article : articles) {
					if (null == article.getCategoryId() && StringUtils.isEmpty(article.getCategoryAlias())
							&& StringUtils.isEmpty(article.getCategoryAlias())) {
						String name = article.getName();
						logger.debug(">>FaceYe -->build article :"+name);
						String content = article.getContent();
						ArticleCategory c=null;
						if (StringUtils.isNotEmpty(content) || content.length() > 10 || StringUtils.isNotEmpty(name)) {
							for (ArticleCategory category : categories) {
                                if(name.toLowerCase().indexOf(category.getName().toLowerCase())!=-1){
                                	c=category;
                                }
                                if(null==c){
                                	if(content.toLowerCase().indexOf(category.getName().toLowerCase())!=-1){
                                		c=category;
                                	}
                                }
                                if(null==c){
                                	c=other;
                                }
                                article.setCategoryAlias(c.getAlias());
                                article.setCategoryId(c.getId());
                                article.setCategoryName(c.getName());
                                this.articleService.save(article);
							}
						}
					}
				}
				page=page+1;
				articles=this.articleService.getPage(null, page, size);
			}
		}
	}

	/**
	 * 取得文章的分类信息
	 */
	@Override
	public ArticleCategory buildArticleCategory(String name, String content) {
		ArticleCategory category=null;
		List<ArticleCategory> categories = this.articleCategoryService.getAll();
		ArticleCategory other=this.articleCategoryService.getArticleCategoryByAlias("other");
		if(StringUtils.isNotEmpty(name)&&StringUtils.isNotEmpty(content)){
			for(int i=categories.size()-1;i>0;i--){
				ArticleCategory a=categories.get(i);
				if(name.toLowerCase().indexOf(a.getName().toLowerCase())!=-1){
					category=a;
				}
				if(null==category){
					if(content.toLowerCase().indexOf(a.getName().toLowerCase())!=-1){
						category=a;
					}
				}
				if(category==null){
					String keywords=a.getKeywords();
					if(StringUtils.isNotEmpty(keywords)){
						if(StringUtils.contains(keywords, ",")){
							String[] keyword=keywords.split(",");
							for(String k:keyword){
								if(StringUtils.isNotEmpty(k)){
									if(StringUtils.contains(name.toLowerCase(), k.toLowerCase())|| StringUtils.contains(content, k.toLowerCase())){
										category=a;
									}
								}
							}
						}else{
							if(StringUtils.contains(name.toLowerCase(), keywords.toLowerCase())|| StringUtils.contains(content, keywords.toLowerCase())){
								category=a;
							}
						}
					}
				}
				if(null==category){
					category=other;
				}
			}
		}
		return category;
	}

}

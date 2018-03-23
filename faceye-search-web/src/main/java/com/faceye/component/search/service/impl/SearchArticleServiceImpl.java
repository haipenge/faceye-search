package com.faceye.component.search.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.faceye.component.cache.service.RedisService;
import com.faceye.component.cache.util.CacheUtils;
import com.faceye.component.search.doc.Article;
import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.component.search.doc.QArticle;
import com.faceye.component.search.repository.mongo.SearchArticleRepository;
import com.faceye.component.search.service.ArticleCategoryService;
import com.faceye.component.search.service.SearchArticleService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;
import com.faceye.feature.util.RandUtil;
 
import com.querydsl.core.types.Predicate;

@Service
public class SearchArticleServiceImpl extends BaseMongoServiceImpl<Article, Long, SearchArticleRepository> implements SearchArticleService {

	@Autowired
	private ArticleCategoryService articleCategoryService = null;

	@Autowired
	private RedisService redisService = null;

	@Autowired
	public SearchArticleServiceImpl(SearchArticleRepository dao) {
		super(dao);
	}

	@Override
	public Page<Article> getPage(Map<String, Object> searchParams, int page, int size)   {
		String oldKey = this.buildPageCacheKey(searchParams, page, size);
		try {
			this.redisService.delete(oldKey);
		} catch (Exception e) {
			logger.error(">>FaceYe -- > Clear old list key:" + oldKey, e);
		}
		String key = "LIST_" + oldKey;
		try {
			this.redisService.delete(key);
		} catch (Exception e) {
			logger.error(">>FaceYe -- > Clear old list key:" + oldKey, e);
		}
		key = CacheUtils.getInstance().buildPageCacheKey(searchParams, page, size);
		Page<Article> result = null;
		try {
			if (StringUtils.isNotEmpty(key) && key.length() < 255) {
				result = (Page<Article>) this.redisService.get(key);
			}
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception when get cache of key:" + key);
		}
		if (result == null) {
			if (page != 0) {
				page = page - 1;
			}
			Long categoryId = MapUtils.getLong(searchParams, "categoryId");
			String alias = MapUtils.getString(searchParams, "alias");
			Predicate predicate = null;
			QArticle qArticle = QArticle.article;
			if (categoryId == null) {
				if (StringUtils.isNotEmpty(alias)) {
					ArticleCategory articleCategory = this.articleCategoryService.getArticleCategoryByAlias(alias);
					if (null != articleCategory) {
						categoryId = articleCategory.getId();
					}
				}
			}
			if (predicate == null) {
				if (null != categoryId) {
					predicate = qArticle.categoryId.eq(categoryId);
				}
			} else {
			}
			// Direction direction=true?Direction.ASC:Direction.DESC;
			Sort sort = new Sort(Direction.DESC, "_id");
			// sort.and(new Sort(Direction.DESC, "clickCount"));
			Pageable pageable = new PageRequest(page, size, sort);
			// Pageable pageable = new PageRequest(page, size);
			result = this.dao.findAll(predicate, pageable);
			if (CollectionUtils.isNotEmpty(result.getContent())) {
				if (StringUtils.isNotEmpty(key) && key.length() < 255) {
					int cacheDays = 1;
					int cacheHours = 2;
					if (page > 10) {
						cacheDays = 180;
						cacheHours = RandUtil.getRandInt(24, 240);
					}
					this.redisService.set(key, result, cacheHours, TimeUnit.HOURS);
				}
			}
		}
		return result;
	}

	@Override
	public Article getArticleByName(String name) {
		return this.dao.getArticleByName(name);
	}

	@Override
	public Article get(Long id) {
		String key = "" + id;
		this.redisService.delete(key);
		String newKey = CacheUtils.getInstance().buildKey("DETAIL", id);
		Article article = (Article) this.redisService.get(newKey);
		if (article == null) {
			article = dao.findOne(id);
			// this.redisService.set(key, article);
			this.redisService.set(newKey, article, 365, TimeUnit.DAYS);
		}
		return article;
	}

	/**
	 * 构建列表的缓存key
	 * 
	 * @todo
	 * @param params
	 * @param page
	 * @param size
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2015年4月8日
	 */
	@Deprecated
	private String buildPageCacheKey(Map params, int page, int size) {
		String key = "";
		if (MapUtils.isNotEmpty(params)) {
			Iterator it = params.keySet().iterator();
			List<String> keys = new ArrayList<String>();
			while (it.hasNext()) {
				keys.add(it.next().toString());
			}
			Collections.sort(keys);
			for (String k : keys) {
				key += MapUtils.getString(params, k);
				key += "-";
			}
		}
		key += page;
		key += "-";
		key += size;
		return key;
	}

	@Override
	public List<Article> getArticlesByName(String name) {
		return this.dao.getArticlesByName(name);
	}

}

package com.faceye.component.search.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.faceye.component.search.doc.Article;
import com.faceye.component.search.doc.QArticle;
import com.faceye.component.search.repository.mongo.SearchArticleRepository;
import com.faceye.component.search.service.SearchArticleService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;
import com.faceye.feature.util.ServiceException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@Service
public class SearchArticleServiceImpl extends BaseMongoServiceImpl<Article, Long, SearchArticleRepository> implements SearchArticleService {
	@Autowired
	public SearchArticleServiceImpl(SearchArticleRepository dao) {
		super(dao);
	}

	@Override
	public Page<Article> getPage(Map<String, Object> searchParams, int page, int size) throws ServiceException {
		if (page != 0) {
			page = page - 1;
		}
		Long categoryId = MapUtils.getLong(searchParams, "categoryId");
		Boolean isIndexed = MapUtils.getBoolean(searchParams, "isIndexed");
		String name = MapUtils.getString(searchParams, "name");
		/**
		 * 查询关键字
		 */
		String q=MapUtils.getString(searchParams, "q");
		Predicate predicate = null;
		QArticle qArticle = QArticle.article;
		BooleanBuilder builder = new BooleanBuilder();
		if (categoryId != null) {
			builder.and(qArticle.categoryId.eq(categoryId));
		}
		if (isIndexed != null) {
			if (isIndexed) {
				// predicate = qArticle.isIndexed.isTrue();
				builder.and(qArticle.isIndexed.isTrue());
			} else {
				// predicate = qArticle.isIndexed.isFalse();
				builder.and(qArticle.isIndexed.isFalse());
			}
		}
		if (StringUtils.isNotEmpty(name)) {
			// predicate = qArticle.name.eq(name);
			builder.and(qArticle.name.eq(name));
		}
		if (null != categoryId) {
			builder.and(qArticle.categoryId.eq(categoryId));
		}
		if(StringUtils.isNotEmpty(q)){
			builder.and(qArticle.name.like("%"+q+"%"));
		}
		// Direction direction=true?Direction.ASC:Direction.DESC;
		Sort sort = new Sort(Direction.DESC, "createDate");
		Pageable pageable = new PageRequest(page, size, sort);
		Page<Article> res = this.dao.findAll(builder.getValue(), pageable);
		return res;
	}

	@Override
	public void dedup() {
		boolean isContinue = Boolean.TRUE;
		int page = 1;
		while (isContinue) {
			Page<Article> articles = this.getPage(null, page, 100);
			page++;
			if (null != articles && CollectionUtils.isNotEmpty(articles.getContent())) {
				for (Article article : articles.getContent()) {
					// 如果标题少于5个字符，文章 长度少于60个字符，将文章删除
					if (StringUtils.length(article.getName()) < 5 || StringUtils.length(article.getContent()) < 60) {
						logger.debug(">>FaceYe --> Article title or content too short,to be remove." + article.getName());
						this.remove(article);
						continue;
					}
					Map searchParams = new HashMap();
					searchParams.put("name", article.getName());
					// article.setIsIndexed(false);
					// this.save(article);
					Page<Article> repeates = this.getPage(searchParams, 1, 100);
					if (null != repeates && CollectionUtils.isNotEmpty(repeates.getContent()) && repeates.getContent().size() > 1) {
						logger.debug(">>.................FaceYe article dedup:id is:" + article.getId() + "," + article.getName());
						for (Article a : repeates.getContent()) {
							if (a.getId().intValue() != article.getId().intValue()) {
								logger.debug(">>>>>>>>>>>>>>>>>>>>>FaceYe dedup article del is:" + a.getId() + "," + a.getName());
								this.dao.delete(a);
							}
						}
					}
				}
			} else {
				isContinue = Boolean.FALSE;
			}
		}
	}

	@Override
	public List<Article> getArticlesByName(String name) {
		return this.dao.getArticlesByName(name);
	}

}

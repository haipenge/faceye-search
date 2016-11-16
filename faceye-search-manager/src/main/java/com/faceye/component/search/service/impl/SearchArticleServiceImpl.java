package com.faceye.component.search.service.impl;

import java.util.Date;
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

import com.faceye.component.parse.doc.Image;
import com.faceye.component.parse.service.ImageService;
import com.faceye.component.parse.util.RegexpConstants;
import com.faceye.component.search.doc.Article;
import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.component.search.doc.QArticle;
import com.faceye.component.search.doc.Subject;
import com.faceye.component.search.repository.mongo.SearchArticleRepository;
import com.faceye.component.search.service.ArticleCategoryService;
import com.faceye.component.search.service.SearchArticleService;
import com.faceye.component.search.service.SubjectService;
import com.faceye.component.weixin.doc.Weixin;
import com.faceye.component.weixin.service.WeixinService;
import com.faceye.feature.service.PropertyService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;
import com.faceye.feature.util.ServiceException;
import com.faceye.feature.util.regexp.RegexpUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@Service
public class SearchArticleServiceImpl extends BaseMongoServiceImpl<Article, Long, SearchArticleRepository> implements SearchArticleService {

	@Autowired
	private ArticleCategoryService articleCategoryService = null;
	@Autowired
	private SubjectService subjectService = null;
	@Autowired
	private PropertyService propertyService = null;
	@Autowired
	private ImageService imageService = null;
	@Autowired
	private WeixinService weixinService = null;

	@Autowired
	public SearchArticleServiceImpl(SearchArticleRepository dao) {
		super(dao);
	}

	@Override
	public Page<Article> getPage(Map<String, Object> searchParams, int page, int size) throws ServiceException {
		if (page != 0) {
			page = page - 1;
		}
		logger.debug(">>FaceYe --Start to query search article.");
		Long categoryId = MapUtils.getLong(searchParams, "categoryId");
		Boolean isIndexed = MapUtils.getBoolean(searchParams, "isIndexed");
		String name = MapUtils.getString(searchParams, "name");
		/**
		 * 查询关键字
		 */
		String q = MapUtils.getString(searchParams, "q");
		Predicate predicate = null;
		QArticle qArticle = QArticle.article;
		BooleanBuilder builder = new BooleanBuilder();
		if (categoryId != null) {
			logger.debug(">>FaceYe --> search article by category,id is:"+categoryId);
			builder.and(qArticle.categoryId.eq(categoryId));
		}
		if (isIndexed != null) {
			logger.debug(">>FaceYe -->Search article by isIndexed:"+isIndexed);
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
			logger.debug(">>FaceYe search article by name:name eq "+name);
			builder.and(qArticle.name.eq(name));
		}

		if (StringUtils.isNotEmpty(q)) {
			logger.debug(">>FaceYe search article by q ,q like :"+q);
			builder.and(qArticle.name.like("%" + q + "%"));
		}
		if (null != builder) {
			predicate = builder.getValue();
			if (null != predicate) {
				logger.debug(">>FaceYe query dsl is:" + predicate.toString());
			}
		}
		// Direction direction=true?Direction.ASC:Direction.DESC;
		Sort sort = new Sort(Direction.DESC, "id");
		Pageable pageable = new PageRequest(page, size,sort);
		Page<Article> res = this.dao.findAll(predicate, pageable);
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

	@Override
	public void saveSearchArticle(Article entity, Map params) {
		Long categoryId = MapUtils.getLong(params, "categoryId");
		Long subjectId = MapUtils.getLong(params, "subjectId");
		String isWeixin = MapUtils.getString(params, "isWeixin");
		if (null != categoryId) {
			ArticleCategory category = this.articleCategoryService.get(categoryId);
			if (category != null) {
				entity.setCategoryAlias(category.getAlias());
				entity.setCategoryName(category.getName());
				entity.setCategoryId(category.getId());
			}
		} else {
			ArticleCategory category = this.articleCategoryService.getArticleCategoryByAlias("other");
			if (category != null) {
				entity.setCategoryAlias(category.getAlias());
				entity.setCategoryName(category.getName());
				entity.setCategoryId(category.getId());
			}
		}
		if (null != subjectId) {
			Subject subject = this.subjectService.get(subjectId);
			if (subject != null) {
				entity.setSubjectAlias(subject.getAlias());
				entity.setSubjectName(subject.getName());
				entity.setSubjectId(subject.getId());
			}
		}
		
		this.save(entity);
		// 提取文章内容中的全部图片链接
		try {
			List<Map<String, String>> matches = RegexpUtil.match(entity.getContent(), RegexpConstants.DISTILL_IMG_SRC);
			if (CollectionUtils.isNotEmpty(matches)) {
				for (Map<String, String> map : matches) {
					String url = map.get("1");
					if (StringUtils.isNotEmpty(url)) {
						Image image = this.imageService.getImageByUrl(url);
						if (null == image) {
							String imgServer = this.propertyService.get("image.server");
							String storePath = url.replace(imgServer, "");
							image = new Image();
							image.setUrl(url);
							image.setStorePath(storePath);
							image.setArticleId(entity.getId());
							this.imageService.save(image);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		}
		/**
		 *  是否微信推广文章
		 */
		if (StringUtils.isNotEmpty(isWeixin)) {
			if (StringUtils.equals(isWeixin.toUpperCase(), "ON") || StringUtils.equals(isWeixin.toUpperCase(), "YES")
					|| StringUtils.equals(isWeixin, "1")) {
				entity.setIsWeixin(true);
				Weixin weixin = this.weixinService.getWeixinByArticle(entity);
				if (null == weixin) {
					weixin = new Weixin();
					weixin.setArticle(entity);
					weixin.setName(entity.getName());
					this.weixinService.save(weixin);
				}
			}
		}
	}

	@Override
	public boolean push2Weixin(String ids) {
		boolean res = false;
		if (StringUtils.isNotEmpty(ids)) {
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				if (StringUtils.isNotEmpty(id)) {
					Article article = this.get(Long.parseLong(id));
					if (article != null) {
						if (article.getIsWeixin()) {
							Weixin weixin = this.weixinService.getWeixinByArticle(article);
							if (weixin == null) {
								weixin = new Weixin();
								weixin.setArticle(article);
								weixin.setCreateDate(new Date());
								weixin.setName(article.getName());
								this.weixinService.save(weixin);
							}
						}
					}
				}
				res = true;
			}
		}
		return res;
	}

}

package com.faceye.component.search.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.faceye.component.cache.service.RedisService;
import com.faceye.component.search.doc.Article;
import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.component.search.doc.Movie;
import com.faceye.component.search.service.ArticleCategoryService;
import com.faceye.component.search.service.MovieService;
import com.faceye.component.search.service.SearchArticleService;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.service.SequenceService;
import com.faceye.feature.upload.Upload;
import com.faceye.feature.upload.UploadResult;
import com.faceye.feature.util.AjaxResult;
import com.faceye.feature.util.Json;

/**
 * 推送服务
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年12月10日
 */
@Controller
@RequestMapping("/search/push")
public class PushController extends BaseController<Article, Long, SearchArticleService> {

	@Autowired
	private RedisService redisService = null;
	@Autowired
	private ArticleCategoryService articleCategoryService = null;

	@Autowired
	private SequenceService sequenceService = null;

	@Autowired
	private MovieService movieService = null;

	@Autowired
	public PushController(SearchArticleService service) {
		super(service);
	}

	/**
	 * 接收推送文章并存储
	 * @todo
	 * @param request
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年12月29日
	 */
	@ResponseBody
	@RequestMapping("/receive")
	public String receive(HttpServletRequest request, @RequestParam(value = "key", required = true) String key,
			@RequestParam(value = "name", required = true) String name, @RequestParam(value = "alias", required = false) String alias,
			@RequestParam(value = "keywords", required = false) String keywords,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "categoryId", required = false) Long categoryId,
			@RequestParam(value = "categoryName", required = false) String categoryName,
			@RequestParam(value = "categoryAlias", required = false) String categoryAlias,
			@RequestParam(value = "subjectId", required = false) Long subjectId,
			@RequestParam(value = "subjectName", required = false) String subjectName,
			@RequestParam(value = "subjectAlias", required = false) String subjectAlias,
			@RequestParam(value = "sourceUrl", required = false) String sourceUrl,
			@RequestParam(value = "isWeixin", required = false) Boolean isWeixin) {
		boolean isAllow = this.authorize(key);
		logger.debug(">>FaceYe --> name:" + name + ",is allow:" + isAllow + ",IP is :" + request.getRemoteAddr());
		String res = "";
		if (isAllow) {
			ArticleCategory category = this.articleCategoryService.getArticleCategoryByAlias(categoryAlias);
			if (null == category && StringUtils.isNotEmpty(categoryAlias)) {
				category = new ArticleCategory();
				category.setId(this.sequenceService.getNextSequence(ArticleCategory.class.getName()));
				category.setAlias(categoryAlias);
				category.setName(categoryName);
				this.articleCategoryService.save(category);
			}

			boolean isExist = true;
			try {
				List<Article> articles = this.service.getArticlesByName(name);
				// Article article = this.service.getArticleByName(name);
				if (CollectionUtils.isEmpty(articles)) {
					isExist = false;
				}
			} catch (Exception e) {
				logger.debug(">>FaceYe throws Exception :>", e);
			}
			if (!isExist) {
				Article article = null;
				article = new Article();
				// article.setId(this.sequenceService.getNextSequence("SEQ_SEARCH_ARTICLE"));
				article.setAlias(alias);
				article.setCategoryAlias(categoryAlias);
				article.setCategoryId(category.getId());
				article.setCategoryName(category.getName());
				article.setCategoryAlias(category.getAlias());
				article.setClickCount(0);
				article.setContent(content);
				article.setCreateDate(new Date());
				article.setDescription(description);
				article.setIsIndexed(false);
				article.setKeywords(keywords);
				article.setName(name);
				article.setSubjectAlias(subjectAlias);
				article.setSubjectId(subjectId);
				article.setSubjectName(subjectName);
				article.setSourceUrl(sourceUrl);
				article.setIsWeixin(isWeixin);
				this.service.save(article);
//				if (isWeixin) {
//					Weixin weixin = new Weixin();
//					weixin.setArticle(article);
//					weixin.setName(article.getName());
//					this.weixinService.save(weixin);
//				}
				this.redisService.set("" + article.getId(), article);
				res = "{\"result\":\"success\"}";
			} else {
				res = "{\"result\":\"failure\",\"msg\":\"name is exist now.\"}";
			}

		} else {
			res = "{\"result\":\"failure\",\"msg\":\"not not allowed\"}";
		}
		return res;
	}

	@RequestMapping("/receiveImg")
	public String receiveImg(HttpServletRequest request) {
		Upload upload = new Upload();
		List<UploadResult> results = upload.upoad(request);
		return "";
	}

	/**
	 * 接收电影数据上报
	 * @todo
	 * @param request
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年7月19日
	 */
	@RequestMapping("/movie")
	@ResponseBody
	public String movie(HttpServletRequest request) {
		BufferedReader in = null;
		String inputLine = "";
		StringBuilder receiveData = new StringBuilder();
		try {
			in = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				receiveData.append(inputLine);
			}
			String json = receiveData.toString();
			if (StringUtils.isNotEmpty(json)) {
				Movie movie = Json.toObject(json, Movie.class);
				if (movie != null) {
					movie.setId(null);
					String name = movie.getName();
					if (StringUtils.isNotEmpty(name)) {
						Movie exist = this.movieService.getMovieByName(name);
						if (exist == null) {
							this.movieService.save(movie);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(">>FaceYe -->", e);
		}
		return AjaxResult.getInstance().buildDefaultResult(true);
	}

	/**
	 * 判断推送数据是否被允许
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年12月29日
	 */
	private boolean authorize(String key) {
		boolean isAllow = Boolean.FALSE;
		int dayOfMonth = Calendar.DAY_OF_MONTH;// 1-31
		int monthOfYear = Calendar.MONTH;// 0-11
		int hourOfDay = Calendar.HOUR_OF_DAY;// 0-23
		String sign = "" + ((monthOfYear + hourOfDay) * dayOfMonth);
		if (StringUtils.equals("FACEYE-NET-SONG", key)) {
			isAllow = Boolean.TRUE;
		}
		return isAllow;
	}

}

package com.faceye.component.search.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.faceye.component.search.doc.Article;
import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.component.search.doc.Movie;
import com.faceye.component.search.service.ArticleCategoryService;
import com.faceye.component.search.service.MovieService;
import com.faceye.component.search.service.SearchArticleService;
import com.faceye.component.search.service.SearchService;
import com.faceye.component.search.service.impl.GlobalEntity;
import com.faceye.component.search.service.impl.SearchResult;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.util.RandUtil;
import com.faceye.feature.util.http.HttpUtil;

/**
 * 文章查询的入口类
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年8月10日
 */
@Controller
@RequestMapping("/search")
public class SearchController extends BaseController<Article, Long, SearchArticleService> {
	@Autowired
	private SearchService searchService = null;
	@Autowired
	private ArticleCategoryService articleCategoryService = null;
	@Autowired
    private MovieService movieService=null;
	@Autowired
	public SearchController(SearchArticleService service) {
		super(service);
	}

	/**
	 * 首页
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年5月24日
	 */
	@RequestMapping("/index.html")
	public String home(HttpServletRequest request, Model model) {
		Map searchParams = HttpUtil.getRequestParams(request);
		String alias = MapUtils.getString(searchParams, "alias");
		ArticleCategory category = null;
		if (StringUtils.isNotEmpty(alias)) {
			category = this.articleCategoryService.getArticleCategoryByAlias(alias);
		}
		// 无分类，转向首页
		if (category == null) {
			return this.home(model, request);
		} else {
			// 有分类，转向分类首页
			return this.categoryHome(model, request);
		}
		// return "search.article.home";
	}

	/**
	 * 首页，无分类参数
	 * @todo
	 * @param model
	 * @param requst
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年5月1日
	 */
	private String home(Model model, HttpServletRequest request) {
		Map searchParams = HttpUtil.getRequestParams(request);
		String alias = MapUtils.getString(searchParams, "alias");
		List<ArticleCategory> categories = this.articleCategoryService.getAll();
		Page<Article> page = this.service.getPage(searchParams, this.getPage(searchParams), this.getSize(searchParams));
		model.addAttribute("page", page);
		// if (StringUtils.isEmpty(alias)) {
		searchParams.put("alias", "hadoop");
		Page<Article> hadoop = this.service.getPage(searchParams, 1, 10);
		model.addAttribute("hadoop", hadoop);
		searchParams.put("alias", "spring");
		Page<Article> spring = this.service.getPage(searchParams, 1, 10);
		model.addAttribute("spring", spring);
		searchParams.put("alias", "mongodb");
		Page<Article> mongodb = this.service.getPage(searchParams, 1, 10);
		model.addAttribute("mongodb", mongodb);
		// }
		GlobalEntity global = new GlobalEntity();
		global.setTitle(this.getI18N("global.faceye") + " - " + this.getI18N("global.name"));
		global.setKeywords(this.getI18N("global.keywords"));
		global.setDesc(this.getI18N("global.desc"));
		model.addAttribute("global", global);
		model.addAttribute("categories", categories);
		Page<Movie> movies=this.movieService.getPage(null, 1, 10);
		model.addAttribute("movies", movies);
		org.springframework.web.servlet.view.tiles3.TilesConfigurer s=null;
		return "search.article.home";
	}

	/**
	 * 各分类首页
	 * @todo
	 * @param model
	 * @param request
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年5月1日
	 */
	private String categoryHome(Model model, HttpServletRequest request) {
		String searchKeyWords="";
		Map searchParams = HttpUtil.getRequestParams(request);
		String alias = MapUtils.getString(searchParams, "alias");
		List<ArticleCategory> categories = this.articleCategoryService.getAll();
		Page<Article> page = this.service.getPage(searchParams, this.getPage(searchParams), this.getSize(searchParams));
		model.addAttribute("page", page);
		// if (StringUtils.isEmpty(alias)) {
		searchParams.put("alias", "hadoop");
		Page<Article> hadoop = this.service.getPage(searchParams, 1, 10);
		model.addAttribute("hadoop", hadoop);
		searchParams.put("alias", "spring");
		Page<Article> spring = this.service.getPage(searchParams, 1, 10);
		model.addAttribute("spring", spring);
		searchParams.put("alias", "mongodb");
		Page<Article> mongodb = this.service.getPage(searchParams, 1, 10);
		model.addAttribute("mongodb", mongodb);
		// }
		ArticleCategory category = null;
		if (StringUtils.isNotEmpty(alias)) {
			category = this.articleCategoryService.getArticleCategoryByAlias(alias);
		}
		model.addAttribute("category", category);
		//关键词检索
		searchKeyWords+=category.getName()+","+category.getKeywords();
		Page<SearchResult> searchResults=this.searchService.search(searchKeyWords, 1, 20);
	    model.addAttribute("searchResults", searchResults);
		GlobalEntity global = new GlobalEntity();
		global.setTitle(category.getName() + "-" + category.getAlias() + " - " + this.getI18N("global.name"));
		global.setKeywords(category.getName() + "-" + category.getAlias());
		global.setDesc(category.getName() + "-" + category.getAlias());
		model.addAttribute("global", global);
		model.addAttribute("categories", categories);
		Page<Movie> movies=this.movieService.getPage(null, 1, 10);
		model.addAttribute("movies", movies);
		return "search.category.article.home";
	}

	/**
	 * 取得数据明细
	 * @todo
	 * @param id
	 * @param model
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年5月26日
	 */
	@RequestMapping("/{id}.html")
	public String detail(@PathVariable Long id, Model model) {
		List<ArticleCategory> categories = this.articleCategoryService.getAll();
		model.addAttribute("categories", categories);
		if (id != null) {
			// 文章本身搜索
			Article article = this.service.get(id);
//			article.setClickCount(article.getClickCount() + 1);
//			this.service.save(article);
			if (article != null) {
				GlobalEntity global = new GlobalEntity();
				String keywords = "";
				String description = "";
				if (StringUtils.isNotEmpty(article.getKeywords())) {
					keywords = article.getKeywords();
				}
				if (StringUtils.isNotEmpty(article.getDescription())) {
					description = article.getDescription();
				} else {
					description = article.getName();
				}
				global.setTitle(article.getName() + " - " + this.getI18N("global.name"));
				global.setKeywords(keywords);
				global.setDesc(description);
				model.addAttribute("global", global);
				model.addAttribute("article", article);
			}
			String key=article.getName();
			if(StringUtils.isNotEmpty(article.getCategoryName())&&!StringUtils.equals(article.getCategoryAlias(), "other")){
				key=article.getCategoryName();
				key+=",";
				key+=article.getCategoryAlias();
			}
			// 相似度搜索
			Page<SearchResult> searchResults = this.searchService.search(key, 1, 9);
			model.addAttribute("searchResults", searchResults);
		}
		Map movieSearchParams=new HashMap();
		movieSearchParams.put("lte|onlineDate", new Date());
		Page<Movie> movies=this.movieService.getPage(movieSearchParams, 1, 10);
		model.addAttribute("movies", movies);
		if(movies!=null && CollectionUtils.isNotEmpty(movies.getContent())){
			int rand=RandUtil.getRandInt(0, movies.getContent().size());
			Movie movie=movies.getContent().get(rand);
			model.addAttribute("movie", movie);
		}
		return "search.article.detail";
	}

}

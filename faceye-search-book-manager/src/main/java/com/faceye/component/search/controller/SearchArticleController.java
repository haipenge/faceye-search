package com.faceye.component.search.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faceye.component.search.doc.Article;
import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.component.search.service.ArticleCategoryService;
import com.faceye.component.search.service.SearchArticleService;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.util.AjaxResult;
import com.faceye.feature.util.http.HttpUtil;

/**
 * 已推送文章管理
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年9月29日
 */
@Controller
@RequestMapping("/search/searchArticle")
@Scope("prototype")
public class SearchArticleController extends BaseController<Article, Long, SearchArticleService> {

	@Autowired
	private ArticleCategoryService articleCategoryService=null;
	@Autowired
	public SearchArticleController(SearchArticleService service) {
		super(service);
	}

	/**
	 * 首页
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年5月24日
	 */
	@RequestMapping("/home")
	public String home(HttpServletRequest request, Model model) {
		List<ArticleCategory> categories=this.articleCategoryService.getAll();
		model.addAttribute("categories", categories);
		Map searchParams = HttpUtil.getRequestParams(request);
		Page<Article> page = this.service.getPage(searchParams, getPage(searchParams), getSize(searchParams));
		model.addAttribute("page", page);
		return "search.searchArticle.manager";
	}

	

	/**
	 * 转向编辑或新增页面
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年5月24日
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		if (id != null) {
			Article entity = this.service.get(id);
			model.addAttribute("searchArticle", entity);
		}
		return "search.searchArticle.update";
	}

	/**
	 * 转向新增页面
	 * @todo
	 * @param model
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年5月27日
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) {
		return "search.searchArticle.update";
	}

	/**
	 * 数据保存
	 */
	@RequestMapping("/save")
	public String save(Article entity, RedirectAttributes redirectAttributes) {
		this.service.save(entity);
		return "redirect:/search/searchArticle/home";
	}

	/**
	 * 数据删除
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年5月24日
	 */
	@RequestMapping("/remove/{id}")
	@ResponseBody
	public String remove(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		if (id != null) {
			this.service.remove(id);
		}
		// return "redirect:/search/searchArticle/home";
		return AjaxResult.getInstance().buildDefaultResult(true);
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
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable Long id, Model model) {
		if (id != null) {
			Article entity = this.service.get(id);
			model.addAttribute("searchArticle", entity);
		}
		return "search.searchArticle.detail";
	}

	
}

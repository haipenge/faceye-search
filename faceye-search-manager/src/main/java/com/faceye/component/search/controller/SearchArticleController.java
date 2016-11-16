package com.faceye.component.search.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faceye.component.parse.doc.Image;
import com.faceye.component.parse.service.ImageService;
import com.faceye.component.parse.util.RegexpConstants;
import com.faceye.component.search.doc.Article;
import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.component.search.doc.Subject;
import com.faceye.component.search.service.ArticleCategoryService;
import com.faceye.component.search.service.SearchArticleService;
import com.faceye.component.search.service.SubjectService;
import com.faceye.component.spider.util.FileUtil;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.service.PropertyService;
import com.faceye.feature.util.AjaxResult;
import com.faceye.feature.util.http.HttpUtil;
import com.faceye.feature.util.regexp.RegexpUtil;

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
	private ArticleCategoryService articleCategoryService = null;
	@Autowired
	private SubjectService subjectService = null;

	@Autowired
	private PropertyService propertyService = null;

	@Autowired
	private ImageService imageService = null;

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
		logger.debug(">>FaceYe --> do get category now.");
		List<ArticleCategory> categories = this.articleCategoryService.getAll();
		model.addAttribute("categories", categories);
		Map searchParams = HttpUtil.getRequestParams(request);
		logger.debug(">>FaceYe --> do get search article now.");
		Page<Article> page = this.service.getPage(searchParams, getPage(searchParams), getSize(searchParams));
		model.addAttribute("page", page);
		model.addAttribute("searchParams", searchParams);
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
		this.beforeInput(model);
		if (id != null) {
			Article entity = this.service.get(id);
			model.addAttribute("searchArticle", entity);
			List<Image> images = this.imageService.getImagesByArticleId(entity.getId());
			model.addAttribute("images", images);
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
		this.beforeInput(model);
		return "search.searchArticle.update";
	}

	/**
	 * 数据保存
	 */
	@RequestMapping("/save")
	public String save(Article entity, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		this.service.saveSearchArticle(entity, HttpUtil.getRequestParams(request));
		return "redirect:/search/searchArticle/home";
	}

	/**
	 * 转向录入页面前的操作
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月30日
	 */
	protected void beforeInput(Model model) {
		List<ArticleCategory> categories = this.articleCategoryService.getAll();
		model.addAttribute("categories", categories);
		List<Subject> subjects = this.subjectService.getAll();
		model.addAttribute("subjects", subjects);
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
			List<Image> images = this.imageService.getImagesByArticleId(id);
			if (CollectionUtils.isNotEmpty(images)) {
				for (Image image : images) {
					if (StringUtils.isNotEmpty(image.getStorePath())) {
						FileUtil.getInstance().deleteImage(image.getStorePath());
					}
					this.imageService.remove(image);
				}
			}
			this.service.remove(id);
		}
		// return "redirect:/search/searchArticle/home";
		return AjaxResult.getInstance().buildDefaultResult(true);
	}
	/**
	 * 批量删除
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年4月1日
	 */
	@RequestMapping("/multiRemove")
	@ResponseBody
	public String multiRemove(@RequestParam(required=true)String ids){
	  String [] idArray=ids.split(",");
	  for(String _id:idArray){
		  if(StringUtils.isNotEmpty(_id)){
			  Long id=Long.parseLong(_id);
			  List<Image> images = this.imageService.getImagesByArticleId(id);
				if (CollectionUtils.isNotEmpty(images)) {
					for (Image image : images) {
						if (StringUtils.isNotEmpty(image.getStorePath())) {
							FileUtil.getInstance().deleteImage(image.getStorePath());
						}
						this.imageService.remove(image);
					}
				}
				this.service.remove(id);
		  }
	  }
	  return AjaxResult.getInstance().buildDefaultResult(true);	
	}
	
	/**
	 * 推送到微信
	 * @todo
	 * @param ids
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年6月5日
	 */
	public String multiPush2Weixin(@RequestParam(required=true)String ids){
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
	/**
	 * 批量设置文章分类
	 * @todo
	 * @param ids
	 * @param categoryId
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年7月23日
	 */
	@RequestMapping("/setArticleCategory")
	@ResponseBody
	public String setArticleCategory(@RequestParam(required=true)String ids,@RequestParam(required=true)Long categoryId){
		String[] idArray=StringUtils.split(ids,",");
		if(idArray!=null && idArray.length>0){
			ArticleCategory articleCategory=this.articleCategoryService.get(categoryId);
			for(String id:idArray){
				if(StringUtils.isNotEmpty(id)){
					Article article=this.service.get(Long.parseLong(id));
					if(article!=null && articleCategory!=null){
						article.setCategoryId(articleCategory.getId());
						article.setCategoryAlias(articleCategory.getAlias());
						article.setCategoryName(articleCategory.getName());
						this.service.save(article);
					}
				}
			}
		}
		return AjaxResult.getInstance().buildDefaultResult(true);
	}

}

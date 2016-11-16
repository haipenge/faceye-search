package com.faceye.component.search.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.component.search.service.ArticleCategoryService;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.util.http.HttpUtil;


@Controller
@Scope("prototype")
@RequestMapping("/search/articleCategory")
public class ArticleCategoryController extends BaseController<ArticleCategory, Long, ArticleCategoryService> {
	
	@Autowired
	public ArticleCategoryController(ArticleCategoryService service) {
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
		Map searchParams=HttpUtil.getRequestParams(request);
		Page<ArticleCategory> page = this.service.getPage(searchParams, getPage(searchParams), getSize(searchParams));
		model.addAttribute("page", page);
		return "search.articleCategory.manager";
	}

	/**
	 * 转向编辑或新增页面
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年5月24日
	 */
//	@RequestMapping("/edit/{id}")
//	public String edit(@PathVariable("id") Long id,Model model) {
//		if(id!=null){
//			ArticleCategory entity=this.service.get(id);
//			model.addAttribute("articleCategory", entity);
//		}
//		return "search.articleCategory.update";
//	}
	
	/**
	 * 转向新增页面
	 * @todo
	 * @param model
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年5月27日
	 */
//	@RequestMapping(value="/input")
//	public String input(Model model){
//		return "search.articleCategory.update";
//	}
	

	/**
	 * 数据保存
	 */
//	@RequestMapping("/save")
//	public String save(ArticleCategory entity, RedirectAttributes redirectAttributes) {
//		this.service.save(entity);
//		return "redirect:/search/articleCategory/home";
//	}

	/**
	 * 数据删除
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年5月24日
	 */
//	@RequestMapping("/remove/{id}")
//	public String remove(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
//		if(id!=null){
//			this.service.remove(id);
//		}
//		return "redirect:/search/articleCategory/home";
//	}
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
//	@RequestMapping("/detail/{id}")
//	public String detail(@PathVariable Long id,Model model){
//		if(id!=null){
//			ArticleCategory entity=this.service.get(id);
//			model.addAttribute("articleCategory", entity);
//		}
//		return "search.articleCategory.detail";
//	}

}

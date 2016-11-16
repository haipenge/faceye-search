package com.faceye.component.weixin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faceye.component.search.doc.Article;
import com.faceye.component.search.service.SearchArticleService;
import com.faceye.component.weixin.doc.Weixin;
import com.faceye.component.weixin.service.WeixinService;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.util.http.HttpUtil;

@Controller
@Scope("prototype")
@RequestMapping("/weixin/weixin")
public class WeixinController extends BaseController<Weixin, Long, WeixinService> {

	@Autowired
	private SearchArticleService searchArticleService=null;
	@Autowired
	public WeixinController(WeixinService service) {
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
		Map searchParams = HttpUtil.getRequestParams(request);
		model.addAttribute("searchParams", searchParams);
		Page<Weixin> page = this.service.getPage(searchParams, getPage(searchParams), getSize(searchParams));
		model.addAttribute("page", page);
		return "weixin.weixin.manager";
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
			Weixin entity = this.service.get(id);
			model.addAttribute("weixin", entity);
		}
		return "weixin.weixin.update";
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
		return "weixin.weixin.update";
	}

	/**
	 * 数据保存
	 */
	@RequestMapping("/save")
	public String save(Weixin entity, RedirectAttributes redirectAttributes) {
		this.service.save(entity);
		return "redirect:/weixin/weixin/home";
	}

	/**
	 * 数据删除
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年5月24日
	 */
	@RequestMapping("/remove/{id}")
	public String remove(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		if (id != null) {
			this.service.remove(id);
		}
		return "redirect:/weixin/weixin/home";
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
			Weixin entity = this.service.get(id);
			if (null != entity) {
				Article article=entity.getArticle();
				model.addAttribute("article",article);
				model.addAttribute("weixin", entity);
			}
		}
		return "weixin.weixin.detail";
	}

}

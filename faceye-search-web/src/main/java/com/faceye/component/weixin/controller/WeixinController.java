package com.faceye.component.weixin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.faceye.component.search.doc.Article;
import com.faceye.component.weixin.service.WeixinService;

/**
 * 微信控制器
 * 
 * @author haipenge
 *
 */
@Controller
//微信统一使用本链接
@RequestMapping("/cms/content/weixin")
@Scope("prototype")
public class WeixinController {
	@Autowired
	private WeixinService weixinService=null;
	/**
	 * 微信首页
	 * @return
	 * @Desc:
	 * @Author:haipenge
	 * @Date:2016年6月14日 上午10:19:22
	 */
	public String home() {
       return "weixin.weixin.home";
	}
	/**
	 * 文章明细
	 * @param id
	 * @param model
	 * @return
	 * @Desc:
	 * @Author:haipenge
	 * @Date:2016年6月14日 上午10:21:11
	 */
	@RequestMapping("/{id}.html")
	public String detail(@PathVariable Long id, Model model){
		if(id!=null){
			Article article=this.weixinService.getSearchArticleService().get(id);
			model.addAttribute("article",article);
		}
		return "weixin.weixin.detail";
	}
}

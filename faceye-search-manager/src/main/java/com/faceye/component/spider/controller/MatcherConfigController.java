package com.faceye.component.spider.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faceye.component.spider.doc.MatcherConfig;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.MatcherConfigService;
import com.faceye.component.spider.service.SiteService;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.util.http.HttpUtil;

@Controller
@Scope("prototype")
@RequestMapping("/spider/matcherConfig")
public class MatcherConfigController extends BaseController<MatcherConfig, Long, MatcherConfigService> {

	@Autowired
	private SiteService siteService=null;
	@Autowired
	public MatcherConfigController(MatcherConfigService service) {
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
		
		Page<MatcherConfig> page = this.service.getPage(searchParams, getPage(searchParams), getSize(searchParams));
		model.addAttribute("page", page);
		List<Site> sites=this.siteService.getAll();
        model.addAttribute("sites", sites);
        this.resetSearchParams(searchParams);
        model.addAttribute("searchParams", searchParams);
		return "spider.matcherConfig.manager";
	}

	/**
	 * 转向编辑或新增页面
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年5月24日
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id,Model model) {
		if(id!=null){
			MatcherConfig entity=this.service.get(id);
			List<Site> sites=this.siteService.getAll();
	        model.addAttribute("sites", sites);
			model.addAttribute("matcherConfig", entity);
		}
		return "spider.matcherConfig.update";
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
	@RequestMapping(value="/input")
	public String input(Model model){
        List<Site> sites=this.siteService.getAll();
        model.addAttribute("sites", sites);
		return "spider.matcherConfig.update";
	}
	

	/**
	 * 数据保存
	 */
	@RequestMapping("/save")
	public String save(MatcherConfig entity, RedirectAttributes redirectAttributes) {
		this.service.save(entity);
		return "redirect:/spider/matcherConfig/home";
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
		if(id!=null){
			this.service.remove(id);
		}
		return "redirect:/spider/matcherConfig/home";
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
	public String detail(@PathVariable Long id,Model model){
		if(id!=null){
			MatcherConfig entity=this.service.get(id);
			model.addAttribute("matcherConfig", entity);
		}
		return "spider.matcherConfig.detail";
	}

}

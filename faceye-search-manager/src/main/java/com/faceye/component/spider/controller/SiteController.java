package com.faceye.component.spider.controller;

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

import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.SiteService;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.service.SequenceService;
import com.faceye.feature.util.http.HttpUtil;

@Controller
@Scope("prototype")
@RequestMapping("/spider/site")
public class SiteController extends BaseController<Site, Long, SiteService> {

	@Autowired
	private SequenceService sequenceService=null;
	@Autowired
	public SiteController(SiteService service) {
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
		Page<Site> page = this.service.getPage(searchParams, getPage(searchParams), getSize(searchParams));
		model.addAttribute("page", page);
		return "spider.site.manager";
	}
	
	@RequestMapping("/getSites.json")
	@ResponseBody
	public Page<Site> getSites(){
		Page<Site> sites=this.service.getPage(null, 1,0);
		return sites;
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
			Site entity=this.service.get(id);
			model.addAttribute("site", entity);
		}
		return "spider.site.update";
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
		return "spider.site.update";
	}
	

	/**
	 * 数据保存
	 */
	@RequestMapping("/save")
	public String save(Site entity, RedirectAttributes redirectAttributes) {
//		entity.setId(this.sequenceService.getNextSequence(Site.class.getName()));
		this.service.save(entity);
		return "redirect:/spider/site/home";
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
		return "redirect:/spider/site/home";
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
			Site entity=this.service.get(id);
			model.addAttribute("site", entity);
		}
		return "spider.site.detail";
	}

}

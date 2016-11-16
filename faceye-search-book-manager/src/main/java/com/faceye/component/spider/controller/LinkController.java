package com.faceye.component.spider.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteService;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.service.SequenceService;
import com.faceye.feature.util.AjaxResult;
import com.faceye.feature.util.http.HttpUtil;

@Controller
@Scope("prototype")
@RequestMapping("/spider/link")
public class LinkController extends BaseController<Link, Long, LinkService> {
	@Autowired
	private SequenceService sequenceService = null;

	@Autowired
	private SiteService siteService = null;

	@Autowired
	public LinkController(LinkService service) {
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
		List<Site> sites = this.siteService.getAll();
		model.addAttribute("sites", sites);
		Page<Link> page = this.service.getPage(searchParams, getPage(searchParams), getSize(searchParams));
		model.addAttribute("page", page);
		return "spider.link.manager";
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
			Link entity = this.service.get(id);
			model.addAttribute("link", entity);
		}
		return "spider.link.update";
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
		List<Site> sites = this.siteService.getAll();
		model.addAttribute("sites", sites);
		return "spider.link.update";
	}

	/**
	 * 数据保存
	 */
	@RequestMapping("/save")
	public String save(Link entity, RedirectAttributes redirectAttributes) {
		entity.setId(this.sequenceService.getNextSequence(Link.class.getName()));
		this.service.save(entity);
		return "redirect:/spider/link/home";
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
		return "redirect:/spider/link/home";
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
			Link entity = this.service.get(id);
			model.addAttribute("link", entity);
		}
		return "spider.link.detail";
	}

	@RequestMapping("/reCrawl")
	@ResponseBody
	public String reCrawl(HttpServletRequest request) {
        Map<String,String> params=HttpUtil.getRequestParams(request);
        String ids=MapUtils.getString(params, "ids");
        String type=MapUtils.getString(params, "type");//type=1,重新爬取,type==2，设置为种子链接 
        
        if(StringUtils.isNotEmpty(ids)){
        	String [] idList=ids.split(",");
        	for(String id:idList){
        		Link link=this.service.get(Long.parseLong(id));
        		if(link!=null){
        			link.setIsCrawled(false);
        			link.setIsCrawlSuccess(false);
        			if(StringUtils.isNotEmpty(type)&& StringUtils.equals(type, "2")){
        				link.setType(0);
        			}
        			this.service.save(link);
        		}
        	}
        }
		return AjaxResult.getInstance().buildDefaultResult(true);
	}
	
	
}

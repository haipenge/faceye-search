package com.faceye.component.parse.controller;

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

import com.faceye.component.parse.doc.FilterWord;
import com.faceye.component.parse.service.FilterWordService;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.util.http.HttpUtil;

@Controller
@Scope("prototype")
@RequestMapping("/parse/filterWord")
public class FilterWordController extends BaseController<FilterWord, Long, FilterWordService> {

	@Autowired
	public FilterWordController(FilterWordService service) {
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
		Page<FilterWord> page = this.service.getPage(searchParams, getPage(searchParams), getSize(searchParams));
		model.addAttribute("page", page);
		return "parse.filterWord.manager";
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
			FilterWord entity=this.service.get(id);
			model.addAttribute("filterWord", entity);
		}
		return "parse.filterWord.update";
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
		return "parse.filterWord.update";
	}
	

	/**
	 * 数据保存
	 */
	@RequestMapping("/save")
	public String save(FilterWord entity, RedirectAttributes redirectAttributes) {
		this.service.save(entity);
		return "redirect:/parse/filterWord/home";
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
		return "redirect:/parse/filterWord/home";
	}
	/**
	 * ajax方式的删除
	 * @todo
	 * @param id
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月25日
	 */
	@RequestMapping("/ajaxRemove/{id}")
	@ResponseBody
	public String ajaxRemove(@PathVariable("id") Long id) {
		if(id!=null){
			this.service.remove(id);
		}
		return "{\"result\":true}";
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
			FilterWord entity=this.service.get(id);
			model.addAttribute("filterWord", entity);
		}
		return "parse.filterWord.detail";
	}

}

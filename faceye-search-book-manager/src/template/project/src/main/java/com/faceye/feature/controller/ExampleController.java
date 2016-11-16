package com.faceye.feature.controller;

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

import com.faceye.feature.entity.Example;
import com.faceye.feature.service.ExampleService;
import com.faceye.feature.util.HttpUtil;

@Controller
@Scope("prototype")
@RequestMapping("/feature/example")
public class ExampleController extends BaseController<Example, Long, ExampleService> {

	@Autowired
	public ExampleController(ExampleService service) {
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
		int pageNum=0;
		int size=20;
		Page<Example> page = this.service.getPage(searchParams, pageNum, size);
		model.addAttribute("page", page);
		return "feature.example.manager";
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
			Example entity=this.service.get(id);
			model.addAttribute("example", entity);
		}
		return "feature.example.update";
	}

	/**
	 * 数据保存
	 */
	@RequestMapping("/save")
	public String save(Example entity, RedirectAttributes redirectAttributes) {
		this.service.save(entity);
		return "redirect:/feature/user/home";
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
		return "redirect:/feature/user/home";
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
			Example entity=this.service.get(id);
			model.addAttribute("example", entity);
		}
		return "feature.example.detail";
	}

}

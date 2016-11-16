package com.faceye.component.dream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("dhome")
@RequestMapping("/dream/home")
public class HomeController {

	/**
	 * 首页
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月17日
	 */
	@RequestMapping("/index.html")
	public String home() {
		return "dream.home.home";
	}
}

package com.faceye.component.search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search/browser")
public class BrowserController {

	@RequestMapping("/index.html")
	public String home(){
		return "search.browser.home";
	}
}

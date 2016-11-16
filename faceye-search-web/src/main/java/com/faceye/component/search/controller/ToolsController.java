package com.faceye.component.search.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.faceye.component.search.service.SearchService;
import com.faceye.feature.util.GlobalEntity;

/**
 * 工具页面
 * @author @haipenge 
 * @联系:haipenge@gmail.com
 * 创建时间:2015年6月10日
 */
@Controller
@RequestMapping("/search/tools")
public class ToolsController {
	@Autowired
	private SearchService searchService=null;
	/**
	 * 正则表达式工具首页
	 * @todo
	 * @param model
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年8月16日
	 */
	@RequestMapping("/regexp.html")
	public String regexp(Model model){
		Map typeInfo = this.getType("");
		if (typeInfo != null) {
			GlobalEntity global = new GlobalEntity();
			global.setDesc(typeInfo.get("description").toString());
			global.setKeywords(typeInfo.get("keywords").toString());
			global.setTitle(typeInfo.get("title").toString());
			model.addAttribute("global", global);
		}
		Page searchResults=this.searchService.search("正则表达式", 1, 10);
		model.addAttribute("searchResults",searchResults);
		return "search.tools.regexp";
	}
	/**
	 * 工具类-》正则表达式
	 * @todo
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年6月10日
	 */
	@RequestMapping("/regexp-{type}.html")
	public String regexp(@PathVariable String type, Model model) {
		boolean isTypeExist = ArrayUtils.contains(language, type);
		Map typeInfo = this.getType(type);
		if (typeInfo != null) {
			GlobalEntity global = new GlobalEntity();
			global.setDesc(typeInfo.get("description").toString());
			global.setKeywords(typeInfo.get("keywords").toString());
			global.setTitle(typeInfo.get("title").toString());
			model.addAttribute("global", global);
			Page searchResults=this.searchService.search(global.getKeywords(), 1, 10);
			model.addAttribute("searchResults",searchResults);
		}
		
		return "search.tools.regexp";
	}

	String[] language = new String[] { "javascript", "java", "php", "ruby", "rails", "asp", "asp.net", "android", "ios", "lua" };

	private List<Map> buildTypes() {
		List<Map> types = new ArrayList<Map>();
		for (int i = 0; i < language.length; i++) {
			Map type = this.getType(language[i]);
			types.add(type);
		}
		return types;
	}

	private Map getType(String languageType) {
		if (StringUtils.isEmpty(languageType)) {
			languageType = "";
		}
		Map type = new HashMap();
		type.put("type", languageType);
		type.put("title", languageType + "正则表达式在线测试工具");
		type.put("key", languageType + "正则表达式");
		type.put("keywords", "正则表达式在线测试工具" + languageType + "," + languageType + "正则表达式在线测试工具");
		type.put("description", "正则表达式在线测试工具" + languageType + "," + languageType + "正则表达式在线测试工具" + ",正则表达式,在线检测工具");
		return type;
	}
}

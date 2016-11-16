package com.faceye.component.parse.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.doc.ParseResult;
import com.faceye.component.parse.service.MetaData;
import com.faceye.component.parse.service.ParseService;
import com.faceye.component.parse.util.HtmlUtil;
import com.faceye.component.parse.util.RegexpConstants;
import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.domain.DomainLink;
import com.faceye.component.spider.service.domain.LinkBuilder;

@Service("segmentFaultParseServiceImpl")
public class SegmentFaultParseServiceImpl extends BaseParseServiceImpl implements ParseService {

	@Value("#{property['domain.segmentfault.com']}")
	private String domain = "";

	protected String getDomain() {
		return domain;
	}

	

	/**
	 * 对爬结果进行解析
	 * @todo
	 * @param crawlResult
	 * @param content
	 * @param type
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月4日
	 */
	protected boolean parse(CrawlResult crawlResult, String content, Integer type) {
		boolean res = false;
		res = this.parseLinks(content);
		if (null != type && type.intValue() == 2) {
			String mainContent = this.parseMainContent(content);
			MetaData meta = this.distillMeta(content);
			String title = this.distillTitle(content);
			if (StringUtils.isNotEmpty(mainContent)) {
				res = true;
				this.saveParseResult(crawlResult, meta, title, mainContent,"","");
			}
		}
		return res;
	}

	protected void wrapParseResult(ParseResult parseResult) {

		if (null != parseResult) {
			String name = parseResult.getName();
			name = StringUtils.replace(name, "SegmentFault", "");
			name = StringUtils.replace(name, "-", " ");
			parseResult.setName(StringUtils.trim(name));
			String content = parseResult.getContent();
			content = StringUtils.replace(content, "欢迎来到最专业的开发者社区", "");
			content = StringUtils.replace(content, "终于被你注意到了 ^_^，如果你觉得这个社区还不错，记得要加入我们哦", "");
			content = StringUtils.replace(content, "请先  后评论", "");
			content = StringUtils.replace(content, "SegmentFault原创编译，转载请遵守本站相关声明。", "");
			content = StringUtils.replace(content, "翻译与责任编辑：", "");
			content = StringUtils.replace(content, "沙渺", "");
			content = StringUtils.replace(content, "原文：", "");
			content = StringUtils.replace(content, "原文:", "");
			content = StringUtils.replace(content, "原文", "");
			content = StringUtils.replace(content, "转载自：", "");
			content = StringUtils.replace(content, "转载自", "");
			content = StringUtils.replace(content, "转载", "");
			content = StringUtils.replace(content, "翻译", "");
			content = StringUtils.replace(content, "来源：", "");
			// <li>上一篇：</li>
			content = StringUtils.replace(content, "上一篇：", "");
			// <li class="mt5">下一篇：</li>
			content = HtmlUtil.getInstance().replace(content, "<li\\sclass=\"mt5\">下一篇：<\\/li>");
			content = StringUtils.replace(content, "下一篇：", "");
			// 2014年03月28日
			content = HtmlUtil.getInstance().replace(content, "[\\d]{4}年[\\d]{2}月[\\d]{2}日");
			// <span>回复 ：</span>
			content = StringUtils.replace(content, "<span>回复 ：</span>", "");
			content = HtmlUtil.getInstance().replace(content, "<span\\sclass=\"count\">[\\d]{1}<\\/span>");
			content = StringUtils.replace(content, "&middot;", "");
			// 请先登陆，再评论
			// <li class="widget-comments__form row">
			content = StringUtils.replace(content, "<li\\sclass=\"widget-comments__form\\srow\">[\\W\\w]*?<\\/li>", "");
			content = StringUtils.replace(content, "讨论区", "");
			content = HtmlUtil.getInstance().replace(content, RegexpConstants.REPLACE_EMPTY_SPAN);
			content = HtmlUtil.getInstance().replace(content, RegexpConstants.REPLACE_EMPTY_P);
			content = HtmlUtil.getInstance().replace(content, RegexpConstants.REPLACE_EMPTY_LI);
			content = HtmlUtil.getInstance().replace(content, RegexpConstants.REPLACE_EMPTY_UL);
			content = StringUtils.replace(content, "<p>图[\\d]{1}<\\/p>", "");
			parseResult.setContent(content);

		}
	}

	/**
	 * 解析页面中的二级页面链接
	 * @todo
	 * @param content
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月4日
	 * @throws Exception 
	 */
	private boolean parseLinks(String content) {
		boolean res = false;
		// <h2 class="title"><a href="/blog/raigor/1190000000304867">Mongo Hacker - MongoDB 命令行配色增强工具</a></h2>
		String regexp = "<h2\\sclass=\"title\"><a\\shref=[\"\\']?([^\"\\'>]+)[\"\\']?[^>]*>.+?<[\\s]*?\\/a></h2>";
		/**
		 * <li class="widget-links__item"><a title="使用 psutil 和 MongoDB 做系统监控" href="/blog/yexiaobai/1190000000666342">使用 psutil 和 MongoDB 做系统监控</a>
		                            <small class="text-muted">
		                                                                            4 收藏，
		                                                                        673 浏览
		                            </small>
		                    </li>
		 */
		String regexp2 = RegexpConstants.DISTIL_A_HREF;
		Site site = getSite();
		List<DomainLink> links = new ArrayList<DomainLink>();
		try {
			List<Map<String, String>> matchs = RegexpUtil.match(content, regexp);
			if (CollectionUtils.isNotEmpty(matchs)) {
				res = true;
				for (Map map : matchs) {
					String url = MapUtils.getString(map, "1");
					url = this.getDomain() + url;
					if (StringUtils.isNotEmpty(url) && url.indexOf("#") != -1) {
						continue;
					}
					LinkBuilder.getInstance().build(links, url, 2);
				}
			}
			List<Map<String, String>> allUrls = RegexpUtil.match(content, regexp2);
			if (CollectionUtils.isNotEmpty(allUrls)) {
				res=true;
				for (Map map : allUrls) {
					String url = MapUtils.getString(map, "1");
					if (StringUtils.isNotEmpty(url) && url.indexOf("#") != -1) {
						continue;
					}
					if (StringUtils.startsWith(url, "/blog")) {
						url = this.getDomain() + url;
						LinkBuilder.getInstance().build(links, url, 2);
					}
				}
			}
		} catch (Exception e) {
			logger.error(">>--->" + e.toString());
		} finally {
			this.linkService.saveDomainLinks(links, site);
		}
		return res;
	}

	/**
	 * 提取文字
	 * @todo
	 * @param content
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月4日
	 */
	private String parseMainContent(String content) {
		String mainContent = "";
		// Regexp is :<div\sclass=\"container\smt30\">([\W\w]*?)<\/div><\!\-\-\s\/\.main\s\-\->
		// <div\\sclass=\"container\\smt30\">([\\W\\w]*?)<\\/div><\\!\\-\\-\\s\\/\.main\\s\\-\\->
		String regexp = "<div\\sclass=\"container\\smt30\">([\\W\\w]*?)<\\/div><\\!\\-\\-\\s\\/\\.main\\s\\-\\->";
		List<Map<String, String>> matchs;
		try {
			matchs = RegexpUtil.match(content, regexp);
			if (CollectionUtils.isNotEmpty(matchs)) {
				mainContent = matchs.get(0).values().iterator().next();
			}
		} catch (Exception e) {
			logger.error(">>--->" + e.getMessage());
		}

		return mainContent;
	}

}

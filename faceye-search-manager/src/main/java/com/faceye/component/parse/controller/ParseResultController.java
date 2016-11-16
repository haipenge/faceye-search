package com.faceye.component.parse.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faceye.component.index.service.impl.WordContainer;
import com.faceye.component.parse.doc.FilterWord;
import com.faceye.component.parse.doc.ParseResult;
import com.faceye.component.parse.service.ParseResultService;
import com.faceye.component.parse.service.ParseService;
import com.faceye.component.parse.service.document.Document;
import com.faceye.component.parse.service.factory.Parse;
import com.faceye.component.parse.service.factory.filter.SuperBodyParseFilter;
import com.faceye.component.parse.service.factory.filter.SuperLinkParseFilter;
import com.faceye.component.parse.service.thread.PushThread;
import com.faceye.component.parse.util.LevelUtils;
import com.faceye.component.parse.util.ParseResultConstants;
import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.component.search.doc.Subject;
import com.faceye.component.search.service.ArticleCategoryService;
import com.faceye.component.search.service.SearchService;
import com.faceye.component.search.service.SubjectService;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.job.thread.ThreadPoolController;
import com.faceye.component.spider.service.CrawlResultService;
import com.faceye.component.spider.service.SiteService;
import com.faceye.component.spider.util.FileUtil;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.service.PropertyService;
import com.faceye.feature.util.AjaxResult;
import com.faceye.feature.util.bean.BeanContextUtil;
import com.faceye.feature.util.http.Http;
import com.faceye.feature.util.http.HttpUtil;
import com.faceye.feature.util.regexp.RegexpUtil;

@Controller
@Scope("prototype")
@RequestMapping("/parse/parseResult")
public class ParseResultController extends BaseController<ParseResult, Long, ParseResultService> {

	@Autowired
	private SearchService searchService = null;

	@Autowired
	private ArticleCategoryService articleCategoryService = null;
	@Autowired
	private SubjectService subjectService = null;

	@Autowired
	private SiteService siteService = null;

	@Autowired
	private Parse parse = null;

	@Autowired
	private CrawlResultService crawlResultService = null;
	@Autowired
	@Qualifier("superParseServiceImpl")
	private ParseService parseService=null;

	@Autowired
	public ParseResultController(ParseResultService service) {
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
		try {
//			if (ParseResultConstants.PAGE_COUNT >= 10 && !searchParams.containsKey("page")) {
//				int rand = MathUtil.getRandInt(1, 10);
//				searchParams.put("page", "" + rand);
//			}
			model.addAttribute("searchParams", searchParams);
			// if (StringUtils.equals(MapUtils.getString(searchParams, "isPush2Mongo"), "0")) {
			// searchParams.put("isPush2Mongo", Boolean.FALSE);
			// } else if (StringUtils.equals(MapUtils.getString(searchParams, "isPush2Mongo"), "1")) {
			// searchParams.put("isPush2Mongo", Boolean.TRUE);
			// }
			List<ArticleCategory> articleCategories = this.articleCategoryService.getAll();
			model.addAttribute("articleCategories", articleCategories);
			Page<ParseResult> page = this.service.getPage(searchParams, getPage(searchParams), 50);
			model.addAttribute("page", page);
			List<Site> sites = this.siteService.getAll();
			model.addAttribute("sites", sites);
			// 解析统计数据
			ParseResultConstants.PAGE_COUNT = page.getTotalPages();
			// List stats = this.statsParseResult();
			// model.addAttribute("stats", stats);
			// 解析文章级别数据
			List<Map<String, String>> levels = LevelUtils.getLevels();
			model.addAttribute("levels", levels);
			// 查询未推送到生产环境的数据量
			// searchParams = new HashMap();
			// searchParams.put("ISFALSE|isPush2ProductEnv", false);
			// searchParams.put("ISTRUE|isAllow", true);
			// int unPushedAllowCount = this.service.getCount(searchParams);
			// model.addAttribute("unPushedAllowedCount", unPushedAllowCount);
		} catch (Exception e) {
			logger.error(">>FaceYe Throws Exception:", e);
		}
		return "parse.parseResult.manager";
	}

	private List statsParseResult() {
		List res = new ArrayList();
		List<Site> sites = this.siteService.getAll();
		if (CollectionUtils.isNotEmpty(sites)) {
			for (Site site : sites) {
				Map items = new HashMap();
				Map searchParams = new HashMap();
				searchParams.put("EQ|siteId", site.getId());
				int count = this.service.getCount(searchParams);
				// site.setName(URLUtils.getDomain(site.getUrl()));
				items.put("site", site);
				items.put("count", count);
				res.add(items);
			}
		}
		return res;
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
			ParseResult entity = this.service.get(id);
			this.beforeInput(model);
			String title = entity.getName();
			String content = entity.getContent();
			WordContainer titleAnalyzerContainer = searchService.getKeywords(title);
			WordContainer contentAnalyzerContainer = searchService.getKeywords(content);
			model.addAttribute("titleAnalyzer", titleAnalyzerContainer.getWords());
			model.addAttribute("contentAnalyzer", contentAnalyzerContainer.getWords());
			model.addAttribute("parseResult", entity);
		}
		return "parse.parseResult.update";
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
		this.beforeInput(model);
		return "parse.parseResult.update";
	}

	/**
	 * 
	 * @todo
	 * @param model
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月2日
	 */
	protected void beforeInput(Model model) {
		// 取得文章分类
		List<ArticleCategory> categories = this.articleCategoryService.getAll();
		model.addAttribute("categories", categories);
		List<Subject> subjects = this.subjectService.getAll();
		model.addAttribute("subejcts", subjects);
	}

	/**
	 * 数据保存
	 */
	@RequestMapping("/save")
	public String save(ParseResult entity, RedirectAttributes redirectAttributes) {
		String description = entity.getDescription();
		if (StringUtils.isNotEmpty(description)) {
			if (description.length() > 255) {
				description = description.substring(0, 254);
				entity.setDescription(description);
			}
		}
		this.service.save(entity);
		return "redirect:/parse/parseResult/home";
	}

	/**
	 * 更新文章标题
	 * @todo
	 * @param id
	 * @param name
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月2日
	 */
	@RequestMapping("/updateName")
	@ResponseBody
	public String updateName(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "name", required = true) String name) {
		Long parseResultId = Long.parseLong(id);
		ParseResult parseResult = this.service.get(parseResultId);
		if (null != parseResult && name.length() >= 5) {
			parseResult.setName(name);
			this.service.save(parseResult);
		}
		return AjaxResult.getInstance().buildDefaultResult(true);
	}

	/**
	 * 数据删除
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年5月24日
	 */
	@RequestMapping("/remove/{id}")
	@ResponseBody
	public String remove(@PathVariable("id") Long id) {
		if (id != null) {
			// this.service.remove(id);
			ParseResult parseResult = this.service.get(id);
			if (null != parseResult) {
				parseResult.setIsRemove(true);
				this.service.save(parseResult);
			}
		}
		return "{\"result\":true}";

		// return "redirect:/parse/parseResult/home";
	}

	@RequestMapping("/multiRemove")
	@ResponseBody
	public String remove(@RequestParam(value = "ids", required = true) String ids) {
		if (ids != null) {
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				if (StringUtils.isNotEmpty(id)) {
					// this.service.remove(Long.parseLong(id));
					ParseResult parseResult = this.service.get(Long.parseLong(id));
					if (null != parseResult) {
						parseResult.setIsRemove(true);
						this.service.save(parseResult);
					}
				}
			}
		}
		return "{\"result\":true}";

		// return "redirect:/parse/parseResult/home";
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
			ParseResult entity = this.service.get(id);
			model.addAttribute("parseResult", entity);
			List<FilterWord> filters = null;
			filters = this.service.testFilterWords(id);
			model.addAttribute("filterWords", filters);
			//对标题进行分词
			WordContainer titleWordContainer=this.searchService.getKeywords(entity.getName());
			model.addAttribute("titleWordContainer", titleWordContainer);
			// 对文章内容进行分词
			WordContainer wordContainer=this.searchService.getKeywords(entity.getContent());
			model.addAttribute("wordContainer",wordContainer);
		}
		return "parse.parseResult.detail";
	}

	/**
	 * 将解析结果推送到生产mongo
	 * @todo
	 * @param parseResultId
	 * @param isIgnoreFilterWord
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年12月29日
	 */
	@RequestMapping("/pushParseResult2Mongo")
	@ResponseBody
	public String pushParseResult2Mongo(@RequestParam(required = true) Long parseResultId,
			@RequestParam(required = false, defaultValue = "false") Boolean isIgnoreFilterWord) {
		logger.debug(">>FaceYe --> Do ajax push parse result 2 mongo.");
		Boolean res = this.service.saveParseResult2MongoIgnoreFilterWord(parseResultId, isIgnoreFilterWord);
		return AjaxResult.getInstance().buildDefaultResult(res);
	}

	/***
	 * 批量推送到mongo
	 * @todo
	 * @param parseResultIds
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月20日
	 */
	@RequestMapping("/multiPushParseResult2Mongo")
	@ResponseBody
	public String multiPushParseResult2Mongo(@RequestParam(required = true) String parseResultIds) {
		logger.debug(">>FaceYe --> parse result ids is:" + parseResultIds);
		if (StringUtils.isNotEmpty(parseResultIds)) {
			List<Long> _ids = new ArrayList<Long>(0);
			String[] ids = parseResultIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				Long id = Long.parseLong(ids[i]);
				_ids.add(id);
				// this.service.saveParseResult2Mongo(id);
				// this.service.saveParseResult2MongoIgnoreFilterWord(id, true);
			}
			if (_ids.size() > 0) {
				Runnable runnable = new PushThread(_ids.toArray(new Long[_ids.size()]));
				ThreadPoolController.getINSTANCE().execute("FaceYe--Push-2-Mongo", runnable, 1);
				// try {
				// Thread.sleep(3000L);
				// } catch (InterruptedException e) {
				// logger.error(">>--->"+e.getMessage());
				// }
			}
		}
		return "{\"result\":true}";
	}
	
	
	@RequestMapping("/push2Cms")
	@ResponseBody
	public String push2Cms(@RequestParam(required=true)Long id){
		Map params=new HashMap();
		ParseResult parseResult=this.service.get(id);
		if(parseResult!=null){
			params.put("name", parseResult.getName());
			params.put("keywords", parseResult.getKeywords());
			params.put("description", parseResult.getDescription());
			params.put("content", parseResult.getContent());
			Http.getInstance().post(BeanContextUtil.getInstance().getBean(PropertyService.class).get("cms.push.url"), "UTF-8", params);
		}
		return AjaxResult.getInstance().buildDefaultResult(true);
	}

	/**
	 * 审核通过，允许自动发布
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年2月9日
	 */
	@RequestMapping("/allow")
	@ResponseBody
	public String allowPublish(@RequestParam(required = true) Long parseResultId) {
		if (null != parseResultId) {
			ParseResult parseResult = this.service.get(parseResultId);
			if (null != parseResult) {
				parseResult.setIsAllow(true);
				this.service.save(parseResult);
				return AjaxResult.getInstance().buildDefaultResult(true);
			} else {
				return AjaxResult.getInstance().buildDefaultResult(false);
			}
		} else {
			return AjaxResult.getInstance().buildDefaultResult(false);
		}
	}

	/**
	 * 批量审核
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年2月9日
	 */
	@RequestMapping("/multiAllow")
	@ResponseBody
	public String multiAllow(@RequestParam(required = false) String parseResultIds, @RequestParam(required = false) Long siteId) {
		if (siteId != null) {
			Map searchParams = new HashMap();
			searchParams.put("isPush2ProductEnv", "0");
			searchParams.put("isAllow", "0");
			searchParams.put("level", 1);
			Page<ParseResult> page = this.service.getPage(searchParams, 1, 500);
			if (page != null && CollectionUtils.isNotEmpty(page.getContent())) {
				for (ParseResult parseResult : page.getContent()) {
					parseResult.setIsAllow(Boolean.TRUE);
					this.service.save(parseResult);
				}
			}
		} else {
			if (StringUtils.isNotEmpty(parseResultIds)) {
				String[] ids = parseResultIds.split(",");
				if (ids != null && ids.length > 0) {
					for (String id : ids) {
						if (StringUtils.isNotEmpty(id)) {
							ParseResult parseResult = this.service.get(Long.parseLong(id));
							parseResult.setIsAllow(true);
							this.service.save(parseResult);
						}
					}
				}
			}
		}
		return AjaxResult.getInstance().buildDefaultResult(true);
	}

	/**
	 * 禁词测试，检查一篇文章中有多少禁词
	 * @todo
	 * @param parseResultId
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月25日
	 */
	@RequestMapping("/filterTest")
	@ResponseBody
	public List<FilterWord> filterTest(@RequestParam(required = true) Long parseResultId) {
		List<FilterWord> filters = null;
		filters = this.service.testFilterWords(parseResultId);
		return filters;
	}

	/**
	 * 对页面内容进行测试解析
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月7日
	 */
	@RequestMapping("/testParseCrawlResult/{crawlResultId}")
	public String testParseCrawlResult(@PathVariable Long crawlResultId, Model model) {
		Document document = null;
		CrawlResult crawlResult = this.crawlResultService.get(crawlResultId);
		Class[] clazzs = new Class[] { SuperLinkParseFilter.class, SuperBodyParseFilter.class };
		document = parse.parse(crawlResult, clazzs);
		model.addAttribute("document", document);
		return "parse.parseResult.test";
	}

	/**
	 * 正则表达式测试
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月7日
	 */
	@RequestMapping("/regexpTest")
	@ResponseBody
	public List<Map<String, String>> regexpTest(@RequestParam(required = true) Long crawlResultId,
			@RequestParam(required = true) String regexp) {
		List<Map<String, String>> res = null;
		CrawlResult crawlResult = this.crawlResultService.get(crawlResultId);
		logger.debug(">>FaceYe regexp test:crawlResult id is:" + crawlResultId + ",regexp :" + regexp);
		if (null != crawlResult) {
			String storePath = crawlResult.getStorePath();
			try {
				String content = FileUtil.getInstance().read(storePath);
				res = RegexpUtil.match(content, regexp);
			} catch (IOException e) {
				logger.error(">>FaceYe throws Exception: --->", e);
			} catch (Exception e) {
				logger.error(">>FaceYe throws Exception: --->", e);
			}

		}
		return res;
	}
	/**
	 * 调用 超级解析器
	 * @param request
	 * @return
	 * @Desc:
	 * @Author:haipenge
	 * @Date:2016年8月3日 上午11:54:35
	 */
	@RequestMapping("/callSuperParse")
	@ResponseBody
	public String callSuperParse(HttpServletRequest request){
		Map params=HttpUtil.getRequestParams(request);
		Long id=MapUtils.getLong(params, "id");
		CrawlResult crawlResult=this.crawlResultService.get(id);
		if(crawlResult!=null){
			logger.debug(">>FaceYe have got crawl result,"+crawlResult.getStorePath());
			this.parseService.saveParseResult(crawlResult);
		}else{
			logger.debug(">>FaceYe -- Crawl result is null of id:"+id);
		}
		return AjaxResult.getInstance().buildDefaultResult(true);
	}
}

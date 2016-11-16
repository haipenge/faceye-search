package com.faceye.component.spider.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.MatcherConfig;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.CrawlResultService;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.MatcherConfigService;
import com.faceye.component.spider.service.SiteService;
import com.faceye.component.spider.util.FileUtil;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.service.SequenceService;
import com.faceye.feature.util.AjaxResult;
import com.faceye.feature.util.http.HttpUtil;

@Controller
@Scope("prototype")
@RequestMapping("/spider/crawlResult")
public class CrawlResultController extends BaseController<CrawlResult, Long, CrawlResultService> {
	@Autowired
	private SequenceService sequenceService = null;
	@Autowired
	private SiteService siteService = null;

	@Autowired
	private MatcherConfigService matcherConfigService = null;
	
	@Autowired
	private LinkService linkService=null;

	@Autowired
	public CrawlResultController(CrawlResultService service) {
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
		List<Site> sites = this.siteService.getAll();
		model.addAttribute("sites", sites);
		Map searchParams = HttpUtil.getRequestParams(request);
		Page<CrawlResult> page = this.service.getPage(searchParams, getPage(searchParams), getSize(searchParams));
		model.addAttribute("page", page);
		this.resetSearchParams(searchParams);
		model.addAttribute("searchParams", searchParams);
		return "spider.crawlResult.manager";
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
			CrawlResult entity = this.service.get(id);
			model.addAttribute("crawlResult", entity);
		}
		return "spider.crawlResult.update";
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
		return "spider.crawlResult.update";
	}

	/**
	 * 数据保存
	 */
	@RequestMapping("/save")
	public String save(CrawlResult entity, RedirectAttributes redirectAttributes) {
		entity.setId(this.sequenceService.getNextSequence(CrawlResult.class.getName()));
		this.service.save(entity);
		return "redirect:/spider/crawlResult/home";
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
		return "redirect:/spider/crawlResult/home";
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

			CrawlResult entity = this.service.get(id);
			model.addAttribute("crawlResult", entity);
			if (null != entity) {
				String storePath = entity.getStorePath();
				try {
					String html = FileUtil.getInstance().read(storePath);
					model.addAttribute("html", html);
				} catch (IOException e) {
					logger.error(">>FaceYe throws Exception: --->" + e.toString());
				}
				// 取得Matcher Config
				Map searchParams = new HashMap();
				searchParams.put("EQ|siteId", entity.getSiteId());
				List<MatcherConfig> matcherConfigs = this.matcherConfigService.getPage(searchParams, 1, 0).getContent();
				model.addAttribute("matcherConfigs", matcherConfigs);
			}
		}
		return "spider.crawlResult.detail";
	}

	/**
	 * 设置为重新解析
	 * @todo
	 * @param ids
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月8日
	 */
	@RequestMapping("/reParse")
	@ResponseBody
	public String reParse(@RequestParam(required=false) String ids, @RequestParam(required=false) Long siteId,@RequestParam(required=false) Integer linkType) {
		if (siteId != null) {
          Map searchParams=new HashMap();
          searchParams.put("EQ|siteId", siteId);
          if(linkType!=null){
        	  searchParams.put("EQ|linkType", linkType);
          }
          Page<CrawlResult> crawlResults=this.service.getPage(searchParams, 1, 0);
          if(crawlResults!=null &&CollectionUtils.isNotEmpty(crawlResults.getContent())){
        	  for(CrawlResult crawlResult : crawlResults.getContent()){
        		  crawlResult.setIsParse(false);
        		  crawlResult.setIsParseSuccess(false);
        		  this.service.save(crawlResult);
        	  }
          }
		} else {
			if (StringUtils.isNotEmpty(ids)) {
				String[] idList = ids.split(",");
				for (String id : idList) {
					if (StringUtils.isNotEmpty(id)) {
						CrawlResult crawlResult = this.service.get(Long.parseLong(id));
						crawlResult.setIsParse(false);
						crawlResult.setIsParseSuccess(false);
						this.service.save(crawlResult);
					}
				}
			}
		}
		return AjaxResult.getInstance().buildDefaultResult(true);
	}
   
	/**
	 * 远程爬取数据上报接口
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月27日
	 */
	@RequestMapping("/remoteCrawlResultReport")
	@ResponseBody
	public String remoteCrawlResultReport(@RequestParam(required=true)Long linkId,@RequestParam(required=true)String content){
		Link link=this.linkService.get(linkId);
		this.service.saveCrawlResult(link, content);
		return AjaxResult.getInstance().buildDefaultResult(true);
	}
}

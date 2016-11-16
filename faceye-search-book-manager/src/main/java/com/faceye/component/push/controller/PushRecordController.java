package com.faceye.component.push.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faceye.component.push.doc.PushRecord;
import com.faceye.component.push.service.PushRecordService;
import com.faceye.component.push.service.model.PushObject;
import com.faceye.component.push.service.model.PushObjectBuilder;
import com.faceye.component.search.doc.Article;
import com.faceye.component.search.service.SearchArticleService;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.util.AjaxResult;
import com.faceye.feature.util.MathUtil;
import com.faceye.feature.util.http.HttpUtil;

@Controller
@Scope("prototype")
@RequestMapping("/push/pushRecord")
public class PushRecordController extends BaseController<PushRecord, Long, PushRecordService> {

	@Autowired
	private SearchArticleService searchArticleService = null;

	@Autowired
	public PushRecordController(PushRecordService service) {
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
		Page<PushRecord> page = this.service.getPage(searchParams, getPage(searchParams), getSize(searchParams));
		model.addAttribute("page", page);
		return "push.pushRecord.manager";
	}

	/**
	 * 取得推送的文章
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月30日
	 */
	@RequestMapping("/getPushObjects.json")
	@ResponseBody
	public Page getPushArticles(HttpServletRequest request) {
		Map searchParams = HttpUtil.getRequestParams(request);
		Page<PushObject> res = null;
		Page<Article> page = this.searchArticleService.getPage(searchParams, MathUtil.getRandInt(1, 30), 10);
		if (null != page && CollectionUtils.isNotEmpty(page.getContent())) {
			List<PushObject> pushObejcts = new ArrayList<PushObject>(0);
			for (Article article : page.getContent()) {
				PushObject pushObject = PushObjectBuilder.builder(article);
				pushObejcts.add(pushObject);
			}
			res = new PageImpl<PushObject>(pushObejcts);
		}
		return res;
	}

	/**
	 * 文章详情
	 * @todo
	 * @param id
	 * @param model
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月30日
	 */
	@RequestMapping("/getPushObject.json")
	@ResponseBody
	public PushObject getPushObject(Model model) {
		PushObject pushObject = null;
		Page<Article> page = this.searchArticleService.getPage(null, MathUtil.getRandInt(1, 30), 10);
		if (null != page && CollectionUtils.isNotEmpty(page.getContent())) {
			Article entity = page.getContent().get(MathUtil.getRandInt(0, page.getContent().size() - 1));
			pushObject = PushObjectBuilder.builder(entity);
		}
		return pushObject;
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
			PushRecord entity = this.service.get(id);
			model.addAttribute("pushRecord", entity);
		}
		return "push.pushRecord.update";
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
		return "push.pushRecord.update";
	}

	/**
	 * 数据保存
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(@RequestParam(value = "articleIds", required = true) String articleIds,
			@RequestParam(value = "siteIds", required = true) String siteIds) {
		// this.service.save(entity);
		// return "redirect:/push/pushRecord/home";
		if (StringUtils.isNotEmpty(articleIds) && StringUtils.isNotEmpty(siteIds)) {
			String[] articleIdArray = articleIds.split(",");
			String[] siteIdArray = siteIds.split(",");
			List<Long> articleIdList = new ArrayList<Long>(0);
			for (String id : articleIdArray) {
				if (StringUtils.isNotEmpty(id)) {
					articleIdList.add(NumberUtils.toLong(id));
				}
			}
			List<Long> siteIdList = new ArrayList<Long>(0);
			for (String id : siteIdArray) {
				if (StringUtils.isNotEmpty(id)) {
					siteIdList.add(NumberUtils.toLong(id));
				}
			}
			this.service.doPushArticle(articleIdList.toArray(new Long[articleIdList.size()]),
					siteIdList.toArray(new Long[siteIdList.size()]));
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
	public String remove(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		if (id != null) {
			this.service.remove(id);
		}
		return "redirect:/push/pushRecord/home";
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
			PushRecord entity = this.service.get(id);
			model.addAttribute("pushRecord", entity);
		}
		return "push.pushRecord.detail";
	}

}

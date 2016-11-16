package com.faceye.component.parse.service.impl;

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
import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.search.doc.Subject;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.Site;

@Service("yiibaiParseServiceImpl")
public class YiibaiParseServiceImpl extends BaseParseServiceImpl implements ParseService {
	@Value("#{property['domain.yiibai.com']}")
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
		// if (null != type && type.intValue() == 2) {
		String mainContent = this.parseMainContent(content);
		MetaData meta = this.distillMeta(content);
		String title = this.distillTitle(content);
		if (StringUtils.isNotEmpty(mainContent)) {
			res = true;
			this.saveParseResult(crawlResult, meta, title, mainContent,"","");
		}
		// }
		return res;
	}

	protected void wrapParseResult(ParseResult parseResult) {
		String sourceUrl = parseResult.getSourceUrl();
		if (StringUtils.isNotEmpty(sourceUrl)) {
			// http://www.yiibai.com/tika/tika_extracting_html_document.html
			// http://www.yiibai.com/tika/
			sourceUrl = StringUtils.replace(sourceUrl, this.getDomain(), "");
			String subjectName = StringUtils.substring(sourceUrl, 1, sourceUrl.lastIndexOf("/"));
			Subject subject=this.subjectService.getSubjectByAlias(subjectName);
			if(null==subject){
				subject=new Subject();
				subject.setId(this.sequenceService.getNextSequence(Subject.class.getName()));
				subject.setAlias(subjectName);
				subject.setName(subjectName);
				this.subjectService.save(subject);
			}
			parseResult.setSubjectAlias(subject.getAlias());
			parseResult.setSubjectName(subject.getName());
			parseResult.setSubjectId(subject.getId());
		} else {
			logger.error(">>FaceYe -> Source url is empty of parse result id:" + parseResult.getId());
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
		// <a class="list-group-item" href="/redis/redis_backup.html" title="Redis备份">Redis备份</a>
		String regexp = "<a\\sclass=\"list-group-item\"[^>]*?href=[\"\\']?([^\"\\'>]+)[\"\\']?[^>]*>.+?<[\\s]*?\\/a>";
		try {
			List<Map<String, String>> matchs = RegexpUtil.match(content, regexp);
			if (CollectionUtils.isNotEmpty(matchs)) {
				res = true;
				Site site = getSite();
				for (Map map : matchs) {
					String url = MapUtils.getString(map, "1");
					boolean isExist = this.linkService.isLinkExist(this.getDomain()+url);
					if (!isExist) {
//						Link link = new Link();
//						link.setIsCrawled(false);
//						link.setIsCrawlSuccess(false);
//						link.setSite(site);
//						link.setType(2);
//						link.setUrl(this.getDomain() + url);
//						this.linkService.save(link);
						this.linkService.saveLink(url, site.getId(), 2);
					}
				}
			}

		} catch (Exception e) {
			logger.error(">>--->" + e.toString());
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
		String regexp = "<div\\sclass=\"content-body\">([\\W\\w]*?)<div\\sclass=\"footer\">";
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

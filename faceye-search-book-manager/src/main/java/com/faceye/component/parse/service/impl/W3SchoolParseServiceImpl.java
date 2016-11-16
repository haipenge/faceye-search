package com.faceye.component.parse.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.doc.ParseResult;
import com.faceye.component.parse.service.MetaData;
import com.faceye.component.parse.service.ParseService;
import com.faceye.component.parse.util.RegexpConstants;
import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.search.doc.Subject;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.domain.DomainLink;
import com.faceye.component.spider.service.domain.LinkBuilder;

/**
 * Link Type =1 List
 * Link Type =2 Detail
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年1月1日
 */
@Service("w3SchoolParseServiceImpl")
public class W3SchoolParseServiceImpl extends BaseParseServiceImpl implements ParseService {
	@Value("#{property['domain.w3school']}")
	private String domain = "";

	

	

	/**
	 * 增加parseResult将要存储的内容
	 * @todo
	 * @param parseResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月1日
	 */
	protected void wrapParseResult(ParseResult parseResult) {
		// 设置解析结果的专题属性
		String sourceUrl = parseResult.getSourceUrl();
		if (StringUtils.isNotEmpty(sourceUrl)) {
			// define subject.
			String alias = "";
			String[] array = sourceUrl.split("/");
			if (null != array && array.length > 2) {
				alias = array[1];
			} else {
				logger.debug(">>FaceYe --> Url " + sourceUrl + ",can not be split by '/'");
			}
			if (StringUtils.isNotEmpty(alias)) {
				Subject subject = this.subjectService.getSubjectByAlias(alias.toLowerCase());
				if (null == subject) {
					subject = new Subject();
					subject.setName(alias);
					subject.setAlias(alias.toLowerCase());
					subject.setId(this.sequenceService.getNextSequence(Subject.class.getName()));
					this.subjectService.save(subject);
					parseResult.setSubjectAlias(alias.toLowerCase());
					parseResult.setSubjectId(subject.getId());
					parseResult.setSubjectName(subject.getName());
				}
			}
		}
	}

	/**
	 * 解析网页内容
	 * @todo
	 * @param content
	 * @param type
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月1日
	 */
	@Override
	protected boolean parse(CrawlResult crawlResult, String content, Integer type) {
		boolean res = false;
		List<DomainLink> domainLinks = this.parseNavSecondLinks(content);
		// 解析列表页
		if (null != type && type.intValue() == 1) {
			try {
				if (CollectionUtils.isNotEmpty(domainLinks)) {
					res = true;
					logger.debug(">>FaceYe --> Distil link size is:" + domainLinks.size());
					Site site = this.getSite();
					for (DomainLink domainLink : domainLinks) {
						String url = domainLink.getUrl();
						Integer linkType = domainLink.getType();
						boolean isExist = this.linkService.isLinkExist(url);
						if (!isExist) {
//							Link link = new Link();
//							link.setIsCrawled(false);
//							link.setIsCrawlSuccess(false);
//							link.setType(linkType);
//							link.setUrl( url);
//							link.setSite(site);
//							logger.debug(">>FaceYe --> will save url:" + url);
//							this.linkService.save(link);
							this.linkService.saveLink(url, site.getId(), linkType);
						} else {
							logger.debug(">>FaceYe --> Url exist now:" + url);
						}

					}
				} else {
					logger.debug(">>FaceYe --> have not got any detail links");
				}
			} catch (Exception e) {
				logger.error(">>--->" + e.getMessage());
			}
		}
		if (null != type && type.intValue() == 2) {
			if(res){
				res=false;
			}
			String mainContent = this.parseMainContent(content);
			String title = this.distillTitle(content);
			if (StringUtils.isNotEmpty(mainContent) && StringUtils.isNotEmpty(title)) {
				res=true;
				MetaData meta = this.distillMeta(mainContent);
				this.saveParseResult(crawlResult, meta, title, mainContent);
			} else {
				logger.debug(">>FaceYe --> title or mainContent is empty.");
			}
		}
		return res;
	}

	/**
	 * 提取左边栏URL链接
	 * @todo
	 * @param content
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月1日
	 */
	private List<DomainLink> parseNavSecondLinks(String content) {
		List<DomainLink> domainLinks = new ArrayList<DomainLink>(0);
		String regexp = "<div\\sid=\"navsecond\">([\\W\\w]*?)<\\/div>";
		try {
			List<Map<String, String>> matches = RegexpUtil.match(content, regexp);
			if (CollectionUtils.isNotEmpty(matches)) {
				String matchContent = matches.get(0).values().iterator().next().toString();
				if (StringUtils.isNotEmpty(matchContent)) {
					// <a href="/html/html_media.asp" title="HTML 多媒体">HTML 媒体</a>
					String urlRegexp = "<a[^>]*?href=[\"\\']?[^\"\\'>]+[\"\\']?[^>]*>.+?<[\\s]*?\\/a>";
					// urlRegexp = "<a\\shref=\"(.+?)\"\\stitle=\"[.+?]\">.+?</a>";
					urlRegexp = RegexpConstants.DISTIL_A_HREF;
					List<Map<String, String>> urls = RegexpUtil.match(matchContent, urlRegexp);
					if (CollectionUtils.isNotEmpty(urls)) {
						for (Map<String, String> urlMap : urls) {
							String url = urlMap.get("1").toString();
							url = this.getDomain() + url;
							LinkBuilder.getInstance().build(domainLinks, url, 2);
						}
					}
				}
			}
			logger.debug(">>FaceYe --> got domain link size is :" + domainLinks.size());
		} catch (Exception e) {
			logger.error(">>FaceY -- Throws Exception :" + e);
		}
		return domainLinks;
	}

	/**
	 * 提取网页主体内容
	 * @todo
	 * @param content
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月1日
	 */
	private String parseMainContent(String content) {
		String res = "";
		String regexp = "<div\\sid=\"maincontent\">([\\W\\w]*?)<div\\sid=\"sidebar\">";
		List<Map<String, String>> matches = null;
		try {
			matches = RegexpUtil.match(content, regexp);
		} catch (Exception e) {
			logger.error(">>--->" + e.getMessage());
		}
		if (CollectionUtils.isNotEmpty(matches)) {
			res = matches.get(0).values().iterator().next();
		}
		if (StringUtils.isNotEmpty(res)) {
			res = StringUtils.replace(res, "w3school", "");
			res = StringUtils.replace(res, "W3School", "");
		} else {
			logger.debug(">>FaceYe --> main content is empty.");
		}
		return res;
	}

	protected String getDomain() {
		return domain;
	}

}

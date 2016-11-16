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
import com.faceye.component.parse.util.HtmlUtil;
import com.faceye.component.parse.util.RegexpConstants;
import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.domain.DomainLink;
import com.faceye.component.spider.service.domain.LinkBuilder;

/**
 * CSDN内容解析器
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年9月14日
*  列表页:link_type=1
*  如：http://blog.csdn.net/index.html?&page=91
*  明细页:link_type=2
*  如：http://blog.csdn.net/xukai871105/article/details/39120399
 */
@Service
public class CSDNParseServiceImpl extends BaseParseServiceImpl implements ParseService {
	@Value("#{property['domain.csdn.net']}")
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
		if (null != type && null != crawlResult) {
			// 解析列表页
			if (type.intValue() == 1) {
				res = this.saveParseLinkLists(content);
			} else if (type.intValue() == 2||type.intValue()==3) {
				res = this.saveParseLinkLists(content);
				String mainContent = this.parseMainContent(content);
				MetaData meta = this.distillMeta(content);
				String title = this.distillTitle(content);
				if (StringUtils.isNotEmpty(title)) {
					StringBuffer sb = new StringBuffer();
					String[] tArray = title.split("-");
					if (tArray != null && tArray.length >= 4) {
						for (int i = 0; i < tArray.length; i++) {
							if (i < tArray.length - 3) {
								sb.append(tArray[i]);
							}
						}
						title = sb.toString();
					}
				}
				if (StringUtils.isNotEmpty(mainContent)) {
					res = true;
					this.saveParseResult(crawlResult, meta, title, mainContent,"","");
				}
			}
		} else {
			logger.error(">>FaceYe--> type is null or crawlResult is null.");
		}
		return res;
	}

	/**
	 * 解析CSND博客首页列表页（type=1)，并从列表页提取明细页(link_type=2)进行存储 
	 * @todo
	 * @param crawlResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月14日
	 */
	private boolean saveParseLinkLists(String content) {
		boolean res = false;
		// <a name="39121767" href="http://blog.csdn.net/snowyzhe/article/details/39121767" target="_blank">Reading Notes:《如何阅读一本书》</a>
		String regexp = "<a\\sname=\"[0-9].+?\"\\shref=\"(.+?)\"\\starget=\"_blank\">.+?</a>";
		Site site = this.getSite();
		List<DomainLink> links = new ArrayList<DomainLink>(0);
		if (StringUtils.isNotEmpty(content)) {
			try {
//				List<Map<String, String>> urls = RegexpUtil.match(content, regexp);
//				if (CollectionUtils.isNotEmpty(urls)) {
//					res = true;
//					for (Map<String, String> map : urls) {
//						String url = map.values().iterator().next();
//						if (StringUtils.isNotEmpty(url)) {
//							LinkBuilder.getInstance().build(links, url, 2);
//						}
//					}
//				}
				List<Map<String, String>> allLinks = HtmlUtil.getInstance().getLinks(content);
				if (CollectionUtils.isNotEmpty(allLinks)) {
					for (Map<String, String> link : allLinks) {
						String url = link.get("1");
						if (StringUtils.isNotEmpty(url) && StringUtils.indexOf(url, "#")==-1) {
							Integer type = 2;
							if (url.indexOf("detail") != -1) {
								type = 2;
							} else {
								//可能为个人博客首页,或分类页
								type = 3;
							}
							if (StringUtils.startsWith(url, "http") && StringUtils.indexOf(url, "blog.csdn.net") != -1) {
								LinkBuilder.getInstance().build(links, url, type);
							} else if (!StringUtils.startsWith(url, "http")&&StringUtils.indexOf(url, "csdn.net")==-1) {
								if (url.indexOf("article") != -1) {
									url = "http://blog.csdn.net" + url;
									LinkBuilder.getInstance().build(links, url, type);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error(">>--->" + e.getMessage());
			} finally {
				this.linkService.saveDomainLinks(links, site);
			}
		}

		return res;
	}

	

	/**
	 * 解析网页内容
	 * @todo
	 * @param content
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月19日
	 */
	private String parseMainContent(String content) {
		String mainContent = "";
		if (StringUtils.isNotEmpty(content)) {
			try {

				List<Map<String, String>> bodyMatchs = RegexpUtil.match(content, RegexpConstants.DISTILL_CSDN_BLOG_DETAIL);
				MetaData meta = this.distillMeta(content);
				if (CollectionUtils.isNotEmpty(bodyMatchs)) {
					mainContent = bodyMatchs.get(0).values().iterator().next();
					// 提取的内容div未闭合,增加闭合标签
					mainContent = HtmlUtil.getInstance().replaceHtml(mainContent);
				}
			} catch (Exception e) {
				logger.error(">>--->" + e.getMessage());
			} finally {
			}
		}
		return mainContent;
	}

	@Override
	protected void wrapParseResult(ParseResult parseResult) {
	}

}

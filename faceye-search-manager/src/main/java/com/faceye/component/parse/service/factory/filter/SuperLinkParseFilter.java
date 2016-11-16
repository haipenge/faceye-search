package com.faceye.component.parse.service.factory.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.document.Document;
import com.faceye.component.parse.service.factory.ParseFilter;
import com.faceye.component.parse.util.RegexpConstants;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.MatcherConfig;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.domain.DomainLink;
import com.faceye.component.spider.service.domain.LinkBuilder;
import com.faceye.component.spider.util.URLUtils;
import com.faceye.feature.util.bean.BeanContextUtil;
import com.faceye.feature.util.regexp.RegexpUtil;

/**
 * 超级解析器的链接提取
 * 
 * @author @haipenge haipenge@gmail.com Create Date:2015年3月7日
 */
@Service
public class SuperLinkParseFilter extends BaseParseFilter implements ParseFilter {

	@Override
	synchronized public void filter(Document document, CrawlResult crawlResult, String content) {
		// 如果是明细页，不再提取明细页的链接，2015.06.07 加入本夫则
		// 原因：爬取了过多的历史数据，淹没了最新的列表数据
		if (crawlResult != null && crawlResult.getLinkType() == 2) {
			return;
		}
		Integer linkType=0;
		if(crawlResult.getLinkType()!=null && crawlResult.getLinkType().intValue()==0){
			linkType=1;
		}else{
			linkType=crawlResult.getLinkType();
		}
		List<MatcherConfig> matcherConfigs = this.getMatcherConfigs(crawlResult.getSiteId(), linkType).getContent();
		Long siteId = crawlResult.getSiteId();
		Site site = this.siteService.get(siteId);
		String prefix = "";
		String suffix = "";
		List<DomainLink> links = new ArrayList<DomainLink>();
		List<Map<String, String>> urls = null;
		try {
			urls = RegexpUtil.match(content, RegexpConstants.DISTIL_A_HREF);
			//logger.debug(">>FaceYe --> crawl result :" + crawlResult.getId() + " have " + urls.size() + " urls .match configs size is:" + matcherConfigs.size());
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		}
		if (CollectionUtils.isNotEmpty(urls)) {
			for (Map<String, String> map : urls) {
				String url = map.get("1");
				if (StringUtils.contains(url, "#") || StringUtils.contains(url, "rss")) {
					continue;
				}
				boolean isMatch = false;
				MatcherConfig selectMatcherConfig = null;
				for (MatcherConfig matcherConfig : matcherConfigs) {
					if (matcherConfig.getType().intValue() == 1) {
						String regexp = matcherConfig.getRegexp();
						//logger.debug(">>FaceYe matcher config regexp is:" + matcherConfig.getRegexp() + ",test url is:" + url);
						isMatch = RegexpUtil.isMatch(url, regexp);
						//logger.debug(">>FaceYe --> url:" + url + ",is match with:" + regexp + ",?" + isMatch);
						if (isMatch) {
							prefix = matcherConfig.getPrefix();
							suffix = matcherConfig.getSuffix();
							selectMatcherConfig = matcherConfig;
							if (StringUtils.equals(prefix, ".")) {
								String crawlLinkUrl = crawlResult.getLinkUrl();
								//logger.error(">>>FaceYe crawl-link is :" + crawlLinkUrl);
								if (StringUtils.isNotEmpty(crawlLinkUrl)) {
									if (StringUtils.endsWith(crawlLinkUrl, "/")) {
										prefix = crawlLinkUrl;
									} else {
										prefix = crawlLinkUrl.substring(0, crawlLinkUrl.lastIndexOf("/"));
										if (!StringUtils.endsWith(prefix, "/")) {
											prefix += "/";
										}
									}
								} else {
									logger.debug(">>Link url is empty .");
								}
							} else {
								//logger.debug(">>>NON----------------------------->>prefix is:" + prefix + ",url is:" + url);
							}

							break;
						}
					}
				}
				// 从页面中解析得到的链接，作为详情页存储
				if (isMatch) {
					if (StringUtils.isNotEmpty(prefix)) {
						if (!StringUtils.startsWith(url, "http")) {
							url = prefix + url;
						} else {
							if (!StringUtils.contains(url, URLUtils.getDomain(crawlResult.getLinkUrl()))) {
								url = "";
							}
						}
					} else {
						if (!StringUtils.startsWith(url, "http")) {
							url = "";
						}
					}
					if (StringUtils.isNotEmpty(url)) {
						Integer type = 2;
						if (selectMatcherConfig != null && selectMatcherConfig.getTypeOfLinkAfterParse() != null) {
							type = selectMatcherConfig.getTypeOfLinkAfterParse();
						}
						String parentUrl=crawlResult.getLinkUrl();
						if (StringUtils.contains(parentUrl, "wx.dm15.com")) {
//                              String parentUrl=document.getLinkUrl();
							Link link=BeanContextUtil.getInstance().getBean(LinkService.class).get(crawlResult.getLinkId());
							LinkBuilder.getInstance().build(links, url, type, link.getDistributeChannel());
						} else {
							LinkBuilder.getInstance().build(links, url, type, selectMatcherConfig.getIsWeixin());
						}

					}
				}
			}
		}
		document.setLinks(links);
	}

}

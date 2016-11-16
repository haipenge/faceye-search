package com.faceye.component.parse.service.factory.filter;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.MetaData;
import com.faceye.component.parse.service.document.Document;
import com.faceye.component.parse.service.factory.ParseFilter;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.MatcherConfig;
import com.faceye.component.spider.doc.Site;
import com.faceye.feature.util.regexp.RegexpUtil;
/**
 * 超级解析器内容提取
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年3月7日
 */
@Service
public class SuperBodyParseFilter extends BaseParseFilter implements ParseFilter {

	@Override
	public void filter(Document document, CrawlResult crawlResult, String content) {
		Long siteId = crawlResult.getSiteId();
		List<MatcherConfig> matcherConfigs = this.getMatcherConfigs(crawlResult.getSiteId(), crawlResult.getLinkType()).getContent();
		String matchContent = "";
		String titleRegexp = "";
		if (StringUtils.isNotEmpty(content)) {
			Site site = this.siteService.get(siteId);
			boolean isMatcher = false;
			for (MatcherConfig matcherConfig : matcherConfigs) {
				if (matcherConfig.getType().intValue() == 2) {
					try {
						List<Map<String, String>> matchers = RegexpUtil.match(content, matcherConfig.getRegexp());
						if (CollectionUtils.isNotEmpty(matchers)) {
							matchContent = matchers.get(0).get("1");
							titleRegexp = matcherConfig.getTitleRegexp();
							isMatcher = true;
						}
					} catch (Exception e) {
						logger.error(">>FaceYe throws Exception: --->", e);
					}
					if (isMatcher) {
						break;
					}
				}
			}
			// 提取标题
			MetaData metaData = this.distillMeta(content);
			String title = this.distillTitle(content);
			if (StringUtils.isNotEmpty(titleRegexp)) {
				List<Map<String, String>> titleMatchers;
				try {
					titleMatchers = RegexpUtil.match(title, titleRegexp);
					if (CollectionUtils.isNotEmpty(titleMatchers)) {
						String titleMatcher = titleMatchers.get(0).get("1");
						if (StringUtils.isNotEmpty(titleMatcher)) {
							title = titleMatcher;
						}
					}
				} catch (Exception e) {
					logger.error(">>FaceYe throws Exception: --->", e);
				}
			}
		    document.setMetaData(metaData);
			if (StringUtils.isNotEmpty(matchContent)) {
				document.setBody(matchContent);
			}
			if (StringUtils.isNotEmpty(title)) {
				document.setTitle(title);
			}
		}
	}

}

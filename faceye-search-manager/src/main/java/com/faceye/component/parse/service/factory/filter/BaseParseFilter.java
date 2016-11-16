package com.faceye.component.parse.service.factory.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.faceye.component.parse.service.MetaData;
import com.faceye.component.parse.service.factory.ParseFilter;
import com.faceye.component.parse.util.RegexpConstants;
import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.spider.doc.MatcherConfig;
import com.faceye.component.spider.service.MatcherConfigService;
import com.faceye.component.spider.service.SiteService;

abstract public class BaseParseFilter implements ParseFilter {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected MatcherConfigService matcherConfigService = null;
	@Autowired
	protected SiteService siteService = null;
	public BaseParseFilter(){
		
	}
	
	/**
	 * 取得页面要进行匹配的正则表达式
	 * @todo
	 * @param siteId
	 * @param linkType
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月4日
	 */
	protected Page<MatcherConfig> getMatcherConfigs(Long siteId, Integer linkType) {
		Page<MatcherConfig> configs = null;
		// 种子链接，一般为列表，所以种子链接，使用列表的正则表达式进行解析
		if (linkType == 0) {
			linkType = 1;
		}
		Map searchParams = new HashMap();
		searchParams.put("EQ|siteId", siteId);
		//开启特定类型页面，只使用一种解析器进行解析
		searchParams.put("EQ|type", linkType);
		configs = this.matcherConfigService.getPage(searchParams, 1, 0);
		return configs;
	}
	
	/**
	 * 提取Meta数据
	 * @todo
	 * @param body
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月20日
	 */
	protected MetaData distillMeta(String content) {
		MetaData meta = new MetaData();
		try {
			List<Map<String, String>> metas = RegexpUtil.match(content, RegexpConstants.DISTILL_HTML_META);
			if (CollectionUtils.isNotEmpty(metas)) {
				for (Map map : metas) {
				    String k=map.get("4").toString();
				    k=k.replaceAll("中华美食网", "");
					meta.put(map.get("2").toString(), k);
				}
			}
		} catch (Exception e) {
			logger.error(">>--->" + e.getMessage());
		}
		return meta;
	}

	/**
	 * 提取网页标题
	 * @todo
	 * @param content
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月30日
	 */
	protected String distillTitle(String content) {
		String res = "";
		try {
			List<Map<String, String>> titleMatchs = RegexpUtil.match(content, RegexpConstants.DISTIAL_HTML_TITILE);
			if (CollectionUtils.isNotEmpty(titleMatchs)) {
				res = titleMatchs.get(0).values().iterator().next();
				res = this.filterTitle(res);
			}
		} catch (Exception e) {
			logger.error(">>--->" + e.getMessage());
		}
		logger.debug(">>FaceYe --> title is :" + res);
		return res;
	}

	/**
	 * 对标题进行过滤
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年2月4日
	 */
	protected String filterTitle(String title) {
		if (StringUtils.isNotEmpty(title)) {
			title = title.replace("[转载]", "");
			title = title.replace("[ 转载 ]", "");
			title = title.replace("【转载】", "");
			// title = title.replace("【转载】", "");
			title = title.replace("【 转载 】", "");
			title = title.replace("【 转载 】", "");
			title = title.replace("【转】", "");
			title = title.replace("【 转 】", "");
			title = title.replace("[转]", "");
			title = title.replace("[ 转 ]", "");
			title = title.replace("（转）", "");
			title = title.replace("(转)", "");
			title = title.replace("转：", "");
			title = title.replace("（转）", "");
			title = title.replace("《转》", "");
			title = title.replace("[分享]", "");
			title = title.replace("[ 分享 ]", "");
			title = title.replace("【分享】", "");
			title = title.replace("【 分享 】", "");
			title = title.replace("?", "");
			title= title.replace("【原】", "");
			title = StringUtils.trim(title);
		}
		return title;
	}
}

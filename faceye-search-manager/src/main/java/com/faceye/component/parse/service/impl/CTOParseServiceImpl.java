package com.faceye.component.parse.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.doc.ParseResult;
import com.faceye.component.parse.service.MetaData;
import com.faceye.component.parse.service.ParseService;
import com.faceye.component.parse.util.RegexpConstants;
import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.job.thread.BaseThread;
import com.faceye.component.spider.job.thread.ThreadPoolController;
import com.faceye.component.spider.service.domain.DomainLink;
import com.faceye.component.spider.service.domain.LinkBuilder;
import com.faceye.component.spider.util.URLUtils;
import com.faceye.feature.service.QueueService;

/**
 * 51cto parse service
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年1月27日
 */
@Service("ctoParseService")
public class CTOParseServiceImpl extends BaseParseServiceImpl implements ParseService {
	@Value("#{property['domain.51cto.com']}")
	private String domain = "";

	@Autowired
	@Qualifier("ctoParseQueueService")
	private QueueService<Map> ctoParseQueueService = null;

	@Override
	protected String getDomain() {
		return domain;
	}

	@Override
	protected boolean parse(CrawlResult crawlResult, String content, Integer type) {
		// Map linkParseMap = new HashMap();
		// linkParseMap.put("crawlResult", crawlResult);
		// linkParseMap.put("content", content);
		// linkParseMap.put("type", type);
		// logger.debug(">>FaceYe --> cto parse queue size is :" + ctoParseQueueService.getSize());
		// this.ctoParseQueueService.add(linkParseMap);

		boolean res = this.saveParseLinks(crawlResult, content, type);
		// this.runParseThread();
		// boolean res = false;
		if (type.intValue() == 2) {
			String mainContent = this.parseMainContent(content);
			MetaData meta = this.distillMeta(content);
			// 一次SQL Server 10054 Troubleshooting - Joe TJ - 51CTO技术博客
			String title = this.distillTitle(content);
			if (StringUtils.isNotEmpty(title)) {
				if (StringUtils.indexOf(title, "-") != -1) {
					title = title.substring(0, title.lastIndexOf("-"));
				}
				if (StringUtils.indexOf(title, "-") != -1) {
					title = title.substring(0, title.lastIndexOf("-"));
				}
				title = StringUtils.trim(title);
			}
			if (StringUtils.isNotEmpty(mainContent)) {
				res = true;
				this.saveParseResult(crawlResult, meta, title, mainContent,"","");
			} else {
				res = false;
			}
		}
		return res;
	}

	class CTOParseThread extends BaseThread {
		@Override
		public void doBusiness() {
			while (true) {
				if (!ctoParseQueueService.isEmpty()) {
					Map map = ctoParseQueueService.get();
					CrawlResult crawlResult = (CrawlResult) map.get("crawlResult");
					String content = MapUtils.getString(map, "content");
					Integer type = MapUtils.getInteger(map, "type");
					saveParseLinks(crawlResult, content, type);
				} else {
					try {
						Thread.sleep(15000L);
					} catch (InterruptedException e) {
						logger.error(">>FaceYe throws Exception: --->" + e.toString());
					}
				}
			}
		}
	}

	private boolean isParseThredRun = false;

	private void runParseThread() {
		if (!isParseThredRun) {
			for (int i = 0; i < 5; i++) {
				Runnable runnable = new CTOParseThread();
				new Thread(runnable).start();
				// ThreadPoolController.getINSTANCE().execute(CTOParseThread.class.getName(), runnable);
			}
			isParseThredRun = true;
		}

	}

	/**
	 * 存储链接信息
	 * @todo
	 * @param crawlResult
	 * @param content
	 * @param type
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月29日
	 */
	private boolean saveParseLinks(CrawlResult crawlResult, String content, Integer type) {
		boolean res = false;
		// 匹配原创博客列表页
		List<DomainLink> links = this.parseLinks(crawlResult.getLinkUrl(), content);
		if (CollectionUtils.isNotEmpty(links)) {
			res = true;
			logger.debug(">>FaceYe --> Got Links size is:" + links.size());
			this.linkService.saveDomainLinks(links, this.getSite());
		} else {
			logger.debug(">>FaceYe -- have not got any links from :" + crawlResult.getStorePath());
		}
		return res;
	}

	/**
	 * 匹配原创博客分类页
	 * @todo
	 * @param content
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月27日
	 */
	private List<DomainLink> parseLinks(String crawlUrl, String content) {
		List<DomainLink> links = new ArrayList<DomainLink>();
		// 提取分类链接列表
		// <a href="/original/26">移动开发</a>
		String regexp = "<a\\shref=\"(\\/original\\/[\\d]*?)\">[\\W\\w]*?<\\/a>";
		List<Map<String, String>> matches;
		String urlPrefix = "http://blog.51cto.com";
		// 提取博客文章明细页
		// link_type = 2
		// <a target="_blank" href="http://faiscoo.blog.51cto.com/9822968/1608897" china="标题">三个网站制作的要点</a>
		try {
			// 提取页面中的全部链接
			matches = RegexpUtil.match(content, RegexpConstants.DISTIL_A_HREF);
			if (CollectionUtils.isNotEmpty(matches)) {
				for (Map<String, String> map : matches) {
					String url = MapUtils.getString(map, "1");
					// 发现页面中的博客详情页链接
					// http://schina.blog.51cto.com/9734953/1609064
					// http:\/\/[a-zA-Z0-9-_]*?\.blog\.51cto\.com\/[\d]+\/[\d]+
					if (StringUtils.isNotEmpty(url)) {
						if (StringUtils.contains(url, "51ctoblog")) {
							continue;
						}
						if (StringUtils.contains(url, "#")) {
							continue;
						}
						if (StringUtils.indexOf(url, "http") != -1 && StringUtils.indexOf(url, "51cto") != -1
								&& StringUtils.indexOf(url, "rss.php") != -1) {
							continue;
						}
						String detailUrlRegexp = "http:\\/\\/[a-zA-Z0-9-_]*?\\.blog\\.51cto\\.com\\/[\\d]+\\/[\\d]+";
						boolean isMatch = RegexpUtil.isMatch(url, detailUrlRegexp);
						if (isMatch) {
							// 博客详情页 type =2
							LinkBuilder.getInstance().build(links, url, 2);
						}
						// 分类页链接 type =1 最新博客列表页
						String categoryUrlRegexp = "\\/original\\/[\\d]+";
						isMatch = RegexpUtil.isMatch(url, categoryUrlRegexp);
						if (isMatch) {
							String fullUrl = urlPrefix + url;
							LinkBuilder.getInstance().build(links, fullUrl, 1);
							// for (int i = 1; i <= 30; i++) {
							// String pageUrl = fullUrl + "/" + i;
							// LinkBuilder.getInstance().build(links, pageUrl, 1);
							// }
						}
						// 匹配个人博客首页 type =3
						// http://joetang.blog.51cto.com
						String blogHomeRegexp = "http:\\/\\/[a-zA-Z0-9-_]+\\.blog\\.51cto\\.com\\/?";
						boolean isUrlEndWithCto = false;
						if (StringUtils.endsWith(url, "c") || StringUtils.endsWith(url, "t") || StringUtils.endsWith(url, "o")) {
							isUrlEndWithCto = true;
						}
						if (!isUrlEndWithCto) {
							isMatch = RegexpUtil.isMatch(url, blogHomeRegexp);
							if (isMatch) {
								LinkBuilder.getInstance().build(links, url, 3);
							}
						}
						// 博客列表页 type=4
						// /2296191/p-1
						String blogPageRegexp = "\\/[0-9]+\\/p-[d]+";
						isMatch = RegexpUtil.isMatch(url, blogPageRegexp);
						if (isMatch) {
							if (StringUtils.isNotEmpty(crawlUrl)) {
								String domain = URLUtils.getDomain(crawlUrl);
								String blogPageUrl = "http://" + domain + url;
								LinkBuilder.getInstance().build(links, blogPageUrl, 4);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->" + e.toString());
		}

		return links;
	}

	/**
	 * 解析博客内容主体
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月28日
	 */
	private String parseMainContent(String content) {
		String res = "";
		String regexp = "<!\\-\\-正文\\s?begin\\-\\->([\\W\\w]*?)<!\\-\\-正文\\s?end\\-\\->";
		List<Map<String, String>> matches;
		try {
			matches = RegexpUtil.match(content, regexp);
			if (CollectionUtils.isNotEmpty(matches)) {
				res = MapUtils.getString(matches.get(0), "1");
			}
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->" + e.toString());
		}
		return res;
	}

	@Override
	protected void wrapParseResult(ParseResult parseResult) {
	}

}

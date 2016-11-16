package com.faceye.component.parse.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.faceye.component.parse.doc.ParseResult;
import com.faceye.component.parse.service.BuildArticleCategoryInitService;
import com.faceye.component.parse.service.FilterWordService;
import com.faceye.component.parse.service.ImageService;
import com.faceye.component.parse.service.MetaData;
import com.faceye.component.parse.service.ParseResultService;
import com.faceye.component.parse.util.HtmlUtil;
import com.faceye.component.parse.util.RegexpConstants;
import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.component.search.service.SubjectService;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.CrawlResultService;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteService;
import com.faceye.component.spider.util.FileUtil;
import com.faceye.component.spider.util.ImageUtil;
import com.faceye.component.spider.util.PathUtil;
import com.faceye.component.spider.util.URLUtils;
import com.faceye.feature.service.Reporter;
import com.faceye.feature.service.SequenceService;

/**
 * 基础解析类
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月8日
 */
abstract public class BaseParseServiceImpl {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected CrawlResultService crawlResultService = null;
	@Autowired
	protected ParseResultService parseResultService = null;
	@Autowired
	protected LinkService linkService = null;
	@Autowired
	protected SiteService siteService = null;
	@Autowired
	protected SequenceService sequenceService = null;
	@Autowired
	protected SubjectService subjectService = null;
	@Autowired
	private FilterWordService filterWordService = null;
	@Autowired
	private ImageService imageService = null;
	@Autowired
	protected BuildArticleCategoryInitService buildArticleCategoryInitService = null;

	@Autowired
	protected Reporter reporter = null;

	private Integer TOTAL_LOOP = 5;

	public void saveParseResult() {
		int count = 0;
//		while (count < TOTAL_LOOP.intValue()) {
			List<CrawlResult> crawlResults = getCrawlResults();
			if (CollectionUtils.isNotEmpty(crawlResults)) {
				for (CrawlResult crawlResult : crawlResults) {
					this.saveParseResult(crawlResult);
				}
				count++;
			} else {
				logger.debug(">>FaceYe --> crawl result is empty,need to check or wait for next crawl loop finish.");
//				break;
			}
			if (count >= TOTAL_LOOP.intValue()) {
//				break;
			}
//		}
	}

	protected List<CrawlResult> getCrawlResults() {
		Site site = getSite();
		List<CrawlResult> crawlResults = null;
		if (null != site) {
			Map<String, Object> searchParams = new HashMap<String, Object>();
			// 解析首页列表
			searchParams.put("ISFALSE|isParse", "0");
			searchParams.put("EQ|siteId", site.getId());
			reporter.reporter(searchParams);
			// searchParams.put("EQ|link.type", 1);
			Page<CrawlResult> page = this.crawlResultService.getPage(searchParams, 1, 500);
			crawlResults = CollectionUtils.isNotEmpty(page.getContent()) ? page.getContent() : null;
		} else {
			logger.debug(">>FaceYe --> site is null.");
		}
		return crawlResults;
	}

	protected Site getSite() {
		logger.debug(">>FaceYe--> Get Site in Clss:" + getClass().getName() + ",domain is :" + getDomain());
		Site site = this.siteService.getSiteByName(getDomain());
		if (site == null) {
			logger.debug(">>FaceYe -->Site is empty of domain:" + getDomain());
		}
		return site;
	}

	/**
	 * 取得域名,子类实现
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月19日
	 */
	abstract protected String getDomain();

	/**
	 * 保存解析结果
	 * @todo
	 * @param crawlResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月1日
	 */
	public void saveParseResult(CrawlResult crawlResult) {
		boolean isParseSuccess = false;
		// Link link = crawlResult.getLink();
		Integer type = crawlResult.getLinkType();
		if (null == type) {
			Link link = this.linkService.get(crawlResult.getLinkId());
			type = crawlResult.getLinkType();
			logger.debug(">>FaceYe link is not exist of crawl result:" + crawlResult.getId());
		}
		if (type != null) {
			String storePath = crawlResult.getStorePath();
			String content = "";
			try {
				content = FileUtil.getInstance().read(storePath);
			} catch (IOException e) {
				logger.error(">>--->" + e.getMessage());
				// return;
			}
			if (StringUtils.isNotEmpty(content)) {
				isParseSuccess = parse(crawlResult, content, type);
			}
		} else {
			logger.debug(">>FaceYe have not got link type of crawl result :" + crawlResult.getId());
		}
		crawlResult.setIsParse(true);
		crawlResult.setIsParseSuccess(isParseSuccess);
		this.crawlResultService.save(crawlResult);
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
	abstract protected boolean parse(CrawlResult crawlResult, String content, Integer type);

	/**
	 * 保存解析结果
	 * @todo
	 * @param crawlResult
	 * @param title
	 * @param content
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月1日
	 */
	protected void saveParseResult(CrawlResult crawlResult, MetaData meta, String title, String content) {
		boolean isStore = true;
		title = StringUtils.trim(title);
		
		isStore = this.isStore(title, content);
		if (isStore) {
			// 是否有重名文章
			isStore = this.isNotHaveSaveNameParseResult(title);
		}
		if (isStore) {
			// 提取正文中的图链接
			//content=this.parseImage(crawlResult, content);
			
			if (StringUtils.isNotEmpty(content)) {
				content = HtmlUtil.getInstance().replaceHtml(content);
				content = this.rebuildContent(content);
			}
			ArticleCategory category = this.buildArticleCategoryInitService.buildArticleCategory(title, content);
			boolean isContainsFilterWord = this.filterWordService.isContainsFilterWrod(title + "," + content);
			ParseResult parseResult = new ParseResult();
			parseResult.setId(this.sequenceService.getNextSequence(ParseResult.class.getName()));
			parseResult.setContent(content);
			// parseResult.setCrawlResult(crawlResult);
			parseResult.setCrawlResultId(crawlResult.getId());
			parseResult.setCreateDate(new Date());
			parseResult.setName(title);
			parseResult.setKeywords(meta.get("keywords"));
			parseResult.setDescription(meta.get("description"));
			// 设置分类
			parseResult.setCategoryId(category.getId());
			parseResult.setCategoryAlias(category.getAlias());
			parseResult.setCategoryName(category.getName());
			parseResult.setSourceUrl(crawlResult.getLinkUrl());
			parseResult.setIsContainsFilterWord(isContainsFilterWord);
			//设置站点ID
		    parseResult.setSiteId(crawlResult.getSiteId());
			// 回调方法，对parseResult进行二次包装
			this.wrapParseResult(parseResult);
			// 对parseResult 设置level
			this.setParseResultLevel(parseResult);
			this.parseResultService.save(parseResult);
		}
	}

	/**
	 * 对conent进行预处理
	 * @todo
	 * @param content
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月19日
	 */
	private String rebuildContent(String content) {
		String res = "";
		res = StringUtils.replace(content, "本文来自CSDN博客，转载请标明出处：", "");
		res = StringUtils.replace(res, "本文来自CSDN博客，转载请标明出处", "");
		res = StringUtils.replace(res, "原文链接：", "");
		res = StringUtils.replace(res, "参考网址：", "");
		res = StringUtils.replace(res, "网址：", "");

		res = StringUtils.replace(res, "原文链接:", "");
		res = StringUtils.replace(res, "参考网址:", "");
		res = StringUtils.replace(res, "网址:", "");

		res = StringUtils.replace(res, "原文链接", "");
		res = StringUtils.replace(res, "参考网址", "");
		res = StringUtils.replace(res, "网址", "");

		res = StringUtils.replace(res, "转载出处:", "");
		res = StringUtils.replace(res, "转载出处：", "");
		res = StringUtils.replace(res, "转载出处", "");

		res = StringUtils.replace(res, "原文地址：", "");
		res = StringUtils.replace(res, "原文地址:", "");
		res = StringUtils.replace(res, "原文地址", "");

		// 博客园，转载请注明出处

		res = StringUtils.replace(res, "博客园，转载请注明出处。", "");
		res = StringUtils.replace(res, "博客园，", "");
		res = StringUtils.replace(res, "博客园", "");
		res = StringUtils.replace(res, " 转载请注明出处:", "");
		res = StringUtils.replace(res, "转载请注明出处：", "");
		res = StringUtils.replace(res, " 转载请注明出处", "");

		// 51cto.
		// 本文出自 “” 博客，请务必保留此出处
		res = StringUtils.replace(res, "<p>本文出自 “” 博客，请务必保留此出处</p>", "");
		// 本文出自 “” 博客，请务必保留此出处
		// 本文出自 “” 博客，请务必保留此出处
		res = StringUtils.replace(res, "本文出自 “” 博客，请务必保留此出处", "");
		res = StringUtils.replace(res, "<p>本文出自 “” 博客，谢绝转载！</p>", "");
		res = StringUtils.replace(res, "本文出自 “” 博客，谢绝转载！", "");
		res = StringUtils.replace(res, "<p>本文出自 “” 博客，转载请与作者联系！</p>", "");
		res = StringUtils.replace(res, "本文出自 “” 博客，转载请与作者联系！", "");
		res = StringUtils.replace(res, "装载自：", "");
		res = StringUtils.replace(res, "装载自:", "");
		res = StringUtils.replace(res, "装载自", "");
		res = StringUtils.replace(res, "装载", "");
		res = StringUtils.replace(res, "原文连接：", "");
		res = StringUtils.replace(res, "原文连接", "");
		res = StringUtils.replace(res, "<p></p>", "");
		res = StringUtils.replace(res, "<p><br /></p>", "");

		return res;
	}

	/**
	 * 设置解析结果的级别
	 * 
	 * 文章 级别
	 * 1级：不包含禁词，标题长度大于10个符号，内容长度大于500个字符，归属于具体分类
	 * 2级：不包含禁词，标题长度大于10个字符，内容长度在200-500字符，归属于具体分类
	 * 3级：不包含禁词，标题长度大于10个字符，内容长度大于500个字符，归属于其它分类
	 * 4级：不包含禁词，标题长度小于10个字符，长度不限，有分类或无分类
	 * -------------------------------------------------------------------
	 * 5级:包含禁词,标题长度大于10个符号，内容长度大于500个字符。归属于具体分类
	 * 6级,包含禁词，标题长度大于10字符,内容长度在200-500字符，归属于具体分类
	 * 7级,包含禁词，标题长度大于10个字符，内容长度大于500字符，归属于其它分类
	 * 8级,包含禁词，标题长度小于10个字符，长度不限。有分类或无分类
	 * -------------------------------------------------------------------
	 * 0级,未归类上以8类级别的解析结果
	 * @todo
	 * @param parseResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月28日
	 */
	private void setParseResultLevel(ParseResult parseResult) {
		Integer level = 0;
		int nameLength = StringUtils.length(parseResult.getName());
		int contentLength = StringUtils.length(parseResult.getContent());
		// 包含禁词
		if (parseResult.getIsContainsFilterWord()) {
			if (nameLength > 10) {
				if (contentLength > 500) {
					if (StringUtils.equals(parseResult.getCategoryAlias(), "other")) {
						level = 7;
					} else {
						level = 5;
					}
				} else if (contentLength > 200 && contentLength <= 500) {
					level = 6;
				} else {
					level = 0;
				}
			} else {
				level = 8;
			}

		} else {
			// 不包含禁词
			if (nameLength > 10) {
				if (contentLength > 500) {
					// 归属于其它分类
					if (StringUtils.equals(parseResult.getCategoryAlias(), "other")) {
						level = 3;
					} else {
						level = 1;
					}
				} else if (contentLength > 200 && contentLength <= 500) {
					level = 2;
				} else {
					level = 0;
				}
			} else {
				level = 4;
			}
		}
		parseResult.setLevel(level);
	}

	/**
	 * 回调方法，由子类实现
	 * 增加parseResult将要存储的内容
	 * @todo
	 * @param parseResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月1日
	 */
	abstract protected void wrapParseResult(ParseResult parseResult);

	/**
	 * 是否有重名的文章
	 * @todo
	 * @param title
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月1日
	 */
	protected boolean isNotHaveSaveNameParseResult(String title) {
		List<ParseResult> haveSameNameParseResults = this.parseResultService.getParseResultsByName(title);
		boolean isNotHaveSameNameParseResult = CollectionUtils.isEmpty(haveSameNameParseResults);
		if (isNotHaveSameNameParseResult) {
			logger.debug(">>Article will be save:" + title);
		} else {
			logger.debug(">>FaceYe --> article name:" + title + " have same.can not be save.");
		}
		return isNotHaveSameNameParseResult;
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
					meta.put(map.get("2").toString(), map.get("4").toString());
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

	/**
	 * 检查是否要对爬取结果进行存储
	 * @todo
	 * @param name
	 * @param content
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年12月30日
	 */
	protected boolean isStore(String name, String content) {
		boolean res = false;
		// 文字数量
		String contentWords = HtmlUtil.getInstance().replaceAll(content);
		contentWords = StringUtils.replace(contentWords, " ", "");
		contentWords = StringUtils.trim(contentWords);
		// Rule 1:标题大于5个字符,内容长度超过100个字符
		if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(contentWords) && name.length() > 5 && contentWords.length() > 50) {
			res = true;
		}
		return res;
	}

	/**
	 * 解析页面中的图片
	 * @todo
	 * @param crawlResult
	 * @param content
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月30日
	 */
	protected String parseImage(CrawlResult crawlResult, String content) {
		List<Map<String, String>> matches;
		try {
			String pageUrl = crawlResult.getLinkUrl();
			// 提取页面中的全部img标签
			matches = RegexpUtil.match(content, RegexpConstants.DISTILL_IMG_FULL);
			if (CollectionUtils.isNotEmpty(matches)) {
				for (Map<String, String> map : matches) {
					String img = map.values().iterator().next();
					// 提取img标签中的data-src
					List<Map<String, String>> dataSrcs = RegexpUtil.match(img, RegexpConstants.DISTILL_IMG_DATA_SRC);
					String imgSrc = "";
					if (CollectionUtils.isNotEmpty(dataSrcs)) {
						imgSrc = dataSrcs.get(0).values().iterator().next();
					} else {
						// 如果没有data-src-标签，则提取src标签
						List<Map<String, String>> srcs = RegexpUtil.match(img, RegexpConstants.DISTILL_IMG_SRC);
						imgSrc = srcs.get(0).values().iterator().next();
					}

					// 如果解析到有效img路径
					if (StringUtils.isNotEmpty(imgSrc)) {
						// 处理图片URL
						if (!StringUtils.startsWith(imgSrc, "http")) {
							String domainUrl = URLUtils.getDomainUrl(pageUrl);
							if (StringUtils.startsWith(imgSrc, "/")) {
								imgSrc = domainUrl + imgSrc;
							} else {
								imgSrc = domainUrl + "/" + imgSrc;
							}
						}
						// 取得文件名后缀
						String suffix = ImageUtil.getImageSuffix(imgSrc);
						if (StringUtils.isNotEmpty(imgSrc)&&StringUtils.isNotEmpty(suffix)) {
							logger.debug(">>FaceYe -->获取图片:"+imgSrc);
							Long id = this.sequenceService.getNextSequence(Link.class.getName());
							Link link = new Link();
							link.setId(id);
							link.setCreateDate(new Date());
							link.setIsCrawled(false);
							link.setIsCrawlSuccess(false);
							link.setMimeType(1);
							link.setParentId(crawlResult.getLinkId());
							link.setSiteId(crawlResult.getSiteId());
							// 设置图片的link type =100
							link.setType(100);
							link.setUrl(imgSrc);
							this.linkService.save(link);
							content = content.replaceAll(img, "#" + id + "#");
						}
						// 生成图片的存储路径
//						String imgPath = "";
//						if (StringUtils.isNotEmpty(suffix)) {
//							imgPath = PathUtil.generateDynamicDirs();
//							imgPath += PathUtil.generateFileName();
//							imgPath += ".";
//							imgPath += suffix;
//							logger.debug(">>FaceYe-->图片的存储路径是:" + imgPath);
//						}
					}

				}
			}
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->" + e.toString());
		}
		return content;
	}
}

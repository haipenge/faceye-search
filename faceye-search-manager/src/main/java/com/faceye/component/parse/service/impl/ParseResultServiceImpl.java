package com.faceye.component.parse.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.faceye.component.index.service.impl.WordContainer;
import com.faceye.component.parse.doc.FilterWord;
import com.faceye.component.parse.doc.ParseResult;
import com.faceye.component.parse.doc.QParseResult;
import com.faceye.component.parse.repository.mongo.ParseResultRepository;
import com.faceye.component.parse.service.BuildArticleCategoryInitService;
import com.faceye.component.parse.service.FilterWordService;
import com.faceye.component.parse.service.ParseResultService;
import com.faceye.component.parse.util.HtmlUtil;
import com.faceye.component.parse.util.RegexpConstants;
import com.faceye.component.search.doc.Article;
import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.component.search.service.ArticleCategoryService;
import com.faceye.component.search.service.SearchArticleService;
import com.faceye.component.search.service.SearchService;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.service.CrawlResultService;
import com.faceye.component.spider.util.Http;
import com.faceye.component.spider.util.URLUtils;
//import com.faceye.component.spider.util.Http;
import com.faceye.feature.service.SequenceService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;
import com.google.common.base.Optional;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

/**
 * 解析结果存储对像
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年9月28日
 */
@Service
public class ParseResultServiceImpl extends BaseMongoServiceImpl<ParseResult, Long, ParseResultRepository> implements ParseResultService {

	@Autowired
	private SequenceService sequenceService = null;
	@Autowired
	private SearchArticleService searchArticleService = null;

	@Autowired
	private SearchService searchService = null;

	@Autowired
	private FilterWordService filterWordService = null;

	@Autowired
	private CrawlResultService crawlResultService = null;

	@Autowired
	private BuildArticleCategoryInitService buildArticleCategoryInitService = null;

	@Autowired
	private ArticleCategoryService articleCategoryService = null;
	// 推送地址
	@Value("#{property['push.host']}#{property['push.url']}")
	private String pushUrl = "";

	@Autowired
	public ParseResultServiceImpl(ParseResultRepository dao) {
		super(dao);
	}

	/**
	 * 保存MySQL中的数据,到mongo中
	 */
	@Override
	public void saveParseResult2Mongo() {
		int pageNum = 1;
		Boolean isContinue = Boolean.TRUE;
		int loops = 0;
		while (isContinue) {
			Map searchParams = new HashMap();
			searchParams.put("isPush2Mongo", "0");
			searchParams.put("isContainsFilterWord", Boolean.FALSE);
			Page<ParseResult> page = this.getPage(searchParams, pageNum, 1000);
			if (page != null && CollectionUtils.isNotEmpty(page.getContent())) {
				logger.debug(">>FaceYe --> push 2 mongo now, do loop is:" + (loops++));
				for (ParseResult parseResult : page.getContent()) {
					this.saveParseResult2Mongo(parseResult);
				}
				if (!page.hasNext()) {
					isContinue = Boolean.FALSE;
				} else {
					// pageNum++;
				}
			} else {
				isContinue = Boolean.FALSE;
			}
		}
	}

	public void saveParseResult2Mongo(ParseResult parseResult) {
		this.saveParseResult2Mongo(parseResult, false);
	}

	/**
	 * 推送数据到mongo
	 * @todo
	 * @param parseResult
	 * @param isIgnoreFilterWord
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月26日
	 */
	private boolean saveParseResult2Mongo(ParseResult parseResult, Boolean isIgnoreFilterWord) {
		boolean res = Boolean.FALSE;
		String name = parseResult.getName();
		// 如果文章标题少于5个字符串，不进行存储
		if (StringUtils.length(name) <= 5) {
			logger.debug(">>FaceYe push faile,name length less than 5,name is :"+parseResult.getName());
			return false;
		}
		logger.debug(">>FaceYe -- push parse result now,name is:"+name);
		String content = parseResult.getContent();
		// 替换A标签的正则表达式
		// String aRegexp = "<a[^>]*?href=[\"\\']?[^\"\\'>]+[\"\\']?[^>]*>.+?<[\\s]*?\\/a>";
		// content = HtmlUtil.getInstance().replace(content, aRegexp);
		// // 替换Img标签的正则表达式
		// String imgRegexp = "<img[^>]*?>";
		// content = HtmlUtil.getInstance().replace(content, imgRegexp);
		// // 去除了所有的div
		// String divRegexp = "<div[^>]*?>";
		// content = HtmlUtil.getInstance().replace(content, divRegexp);
		// String endDivRegexp = "<\\/div>";
		// content = HtmlUtil.getInstance().replace(content, endDivRegexp);
		// content = HtmlUtil.getInstance().replace(content, RegexpConstants.REPLACE_ALL_IFRAME);
		content = HtmlUtil.getInstance().replaceHtml(content);
		parseResult.setContent(content);
		// 是否存储
		Boolean isStore = Boolean.FALSE;
		// 如果文章长度不超过100个字符，标题少于5个字符，不进行存储
		if (StringUtils.isEmpty(content) || StringUtils.isEmpty(name) || content.length() < 50 || name.length() < 5) {
			logger.debug(">>FaceYe push fail:content is empty or name is empty or content length <50 or name length <5.");
			isStore = Boolean.FALSE;
			return isStore;
		} else {
			isStore = Boolean.TRUE;
		}
		// 是否包含禁词
		if (!isIgnoreFilterWord && isStore) {
			boolean isContainsFilterWord = this.filterWordService.isContainsFilterWrod(name + "," + content);
			if (isContainsFilterWord) {
				logger.debug(">>FaceYe article:" + parseResult.getName() + " 包含禁词，不可入库。");
				parseResult.setIsContainsFilterWord(Boolean.TRUE);
				// this.dao.save(parseResult);
			}
			// 包含禁词的，不可入库
			if (!isContainsFilterWord && StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(content)) {
				isStore = Boolean.TRUE;
			} else {
				isStore = Boolean.FALSE;
				logger.debug(">>FaceYe --> 包含禁词，不可入库。");
			}
		}
		// 判断有没有同名文章，如果有同名文章，同时body长相近，则不再存储
		logger.debug(">>FaceYe push parse  result to mongo ,isStore :"+isStore);
		if (isStore) {
			List<Article> articles = this.searchArticleService.getArticlesByName(parseResult.getName());
			if (CollectionUtils.isNotEmpty(articles)) {
				int contenLength = StringUtils.length(content);
				for (Article article : articles) {
					if (Math.abs(contenLength - StringUtils.length(article.getContent())) < 50) {
						// 如果文章内容的长度差别在25个字符以内，则不进行存储
						logger.debug(">>FaceYe push 2 mongo:" + parseResult.getId() + ":" + name
								+ ",have same(name) pushed article:两文章内容长度相差不超过50个有效字符." + article.getId());
						isStore = Boolean.FALSE;
						break;
					}
				}
			}
		}
		
		if (isStore) {
			logger.debug(">>FaceYe --> now save parse result:" + name);
			String keywords = parseResult.getKeywords();
			if (StringUtils.isEmpty(keywords)) {
				keywords = this.searchService.getKeywords(parseResult.getName()).toString();
				parseResult.setKeywords(keywords);
			}
			String description = StringUtils.isEmpty(parseResult.getSummary()) ? parseResult.getDescription() : parseResult.getSummary();
			if (StringUtils.isEmpty(description)) {
				String _content = HtmlUtil.getInstance().replaceAll(parseResult.getContent());
				description = parseResult.getName() + ","
						+ (StringUtils.isNotEmpty(_content) && _content.length() > 20 ? _content.substring(0, 20) : _content);
				parseResult.setDescription(description);

			}
			/**
			 * 记录生产环境推送记录
			 */
			boolean isPush2ProductEnv = this.push2PruductEnv(parseResult);
			if (isPush2ProductEnv) {
				res = Boolean.TRUE;
				parseResult.setIsPush2ProductEnv(isPush2ProductEnv);
				// 推送成功，再进行数据存储。
				// 获取文章分类
				ArticleCategory articleCategory = null;
				if (null == parseResult.getCategoryId()) {
					articleCategory = this.buildArticleCategoryInitService.buildArticleCategory(parseResult.getName(),
							parseResult.getContent());
				} else {
					articleCategory = this.articleCategoryService.get(parseResult.getCategoryId());
				}
				if (null == articleCategory) {
					articleCategory = this.articleCategoryService.getArticleCategoryByAlias("other");
				}
				// Long nextSeq = this.sequenceService.getNextSequence(SequenceConstants.SEQ_SEARCH_ARTICLE);
				Article article = new Article();
				// article.setId(nextSeq);
				article.setName(parseResult.getName());
				article.setKeywords(keywords);
				article.setDescription(description);
				article.setContent(content);
				article.setIsIndexed(Boolean.FALSE);
				article.setCategoryId(articleCategory.getId());
				article.setCategoryAlias(articleCategory.getAlias());
				article.setCategoryName(articleCategory.getName());
				article.setSubjectAlias(parseResult.getSubjectAlias());
				article.setSubjectId(parseResult.getSubjectId());
				article.setSubjectName(parseResult.getSubjectName());
				this.searchArticleService.save(article);
				parseResult.setIsPush2Mongo(Boolean.TRUE);
				// this.save(parseResult);
			}
		}
		this.save(parseResult);
		return res;
	}

	/**
	 * 重建parse Result
	 */
	@Override
	public void saveRebuildParseResult() {
		int pageNum = 1;
		Boolean isContinue = Boolean.TRUE;
		while (isContinue) {
			Page<ParseResult> page = this.getPage(null, pageNum, 100);
			if (page != null && CollectionUtils.isNotEmpty(page.getContent())) {
				logger.debug(">>FaceYe --> now do loop is:" + pageNum);
				for (ParseResult parseResult : page.getContent()) {
					this.saveRebuildParseResult(parseResult);
				}
				if (!page.hasNext()) {
					isContinue = Boolean.FALSE;
				} else {
					pageNum++;
				}
				break;
			} else {
				isContinue = Boolean.FALSE;
			}
		}
	}

	/**
	 * 替换页面中的所有 a，img，div
	 * @todo
	 * @param parseResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年8月15日
	 */
	private void saveRebuildParseResult(ParseResult parseResult) {
		String content = parseResult.getContent();
		// 替换A标签的正则表达式
		String aRegexp = "<a[^>]*?href=[\"\\']?[^\"\\'>]+[\"\\']?[^>]*>.+?<[\\s]*?\\/a>";
		content = HtmlUtil.getInstance().replace(content, RegexpConstants.REPLACE_ALL_A_HREF);
		// 替换Img标签的正则表达式
		String imgRegexp = "<img[^>]*?>";
		content = HtmlUtil.getInstance().replace(content, RegexpConstants.REPLACE_ALL_IMG);
		// 去除了所有的div
		String divRegexp = "<div[^>]*?>";
		content = HtmlUtil.getInstance().replace(content, RegexpConstants.REPLACE_ALL_DIV);
		// String endDivRegexp="<\\/div>";=
		// content=HtmlUtil.getInstance().replace(content, endDivRegexp);
		if (StringUtils.isNotEmpty(content)) {
			parseResult.setContent(content);
			if (StringUtils.isEmpty(parseResult.getKeywords())) {
				this.saveAnalyzer(parseResult);
			}
			this.save(parseResult);
		}
	}

	/**
	 * 自动对标题进行分词处理，同时，提取文章接要
	 * @todo
	 * @param parseResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年8月15日
	 */
	private void saveAnalyzer(ParseResult parseResult) {
		if (null != parseResult) {
			String title = parseResult.getName();
			String content = parseResult.getContent();
			String keyword = "";
			String summary = "";
			if (StringUtils.isNotEmpty(title)) {
				WordContainer keywordContainer = this.searchService.getKeywords(title);
				keyword = keywordContainer.toString();
			}
			if (StringUtils.isNotEmpty(content)) {
				content = HtmlUtil.getInstance().replaceAll(content);
				content = content.replaceAll(" ", "");
				if (StringUtils.isNotEmpty(content)) {
					if (content.length() > 255) {
						summary = content.substring(0, 254);
					} else {
						summary = content;
					}
				}
			}
			parseResult.setKeywords(keyword);
			parseResult.setSummary(summary);
		}
	}

	@Override
	public void filter() {
		int pageNum = 1;
		Boolean isContinue = Boolean.TRUE;
		while (isContinue) {
			Page<ParseResult> page = this.getPage(null, pageNum, 100);
			if (page != null && CollectionUtils.isNotEmpty(page.getContent())) {
				logger.debug(">>FaceYe --> now do loop is:" + pageNum);
				for (ParseResult parseResult : page.getContent()) {
					this.saveRebuildParseResult(parseResult);
				}
				if (!page.hasNext()) {
					isContinue = Boolean.FALSE;
				} else {
					pageNum++;
				}
				break;
			} else {
				isContinue = Boolean.FALSE;
			}
		}
	}

	@Override
	public void saveParseResult2Mongo(Long parseResultId) {
		ParseResult parseResult = this.get(parseResultId);
		// 如果已经推送过，刚不需要二次推送
		if (!parseResult.getIsPush2Mongo()) {
			this.saveParseResult2Mongo(parseResult);
		} else {
			logger.debug(">>FaceYe -->> parse result:" + parseResultId + " is pushed 2 mongo.do not need push again.");
		}
	}

	@Override
	public void resetParseResult() {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("ISTRUE|isParse", "1");
		searchParams.put("NOTEQ|link.site.id", "2");
		int page = 1;
		int size = 1000;
		Page<CrawlResult> crawlResults = this.crawlResultService.getPage(searchParams, page, size);
		Boolean isContinue = Boolean.TRUE;
		while (isContinue) {
			logger.debug(">>FaceYe --> now Reset CrawlResult,page is:" + page);
			if (crawlResults != null && CollectionUtils.isNotEmpty(crawlResults.getContent())) {
				for (CrawlResult crawlResult : crawlResults.getContent()) {
					crawlResult.setIsParse(Boolean.FALSE);
					crawlResult.setIsParseSuccess(Boolean.FALSE);
					this.crawlResultService.save(crawlResult);
				}
				crawlResults = this.crawlResultService.getPage(searchParams, page, size);
			} else {
				isContinue = Boolean.FALSE;
			}
		}

	}

	@Override
	public List<FilterWord> testFilterWords(Long parseResultId) {
		ParseResult parseResult = this.get(parseResultId);
		String name = parseResult.getName();
		String content = parseResult.getContent();
		content = HtmlUtil.getInstance().replaceAll(content);
		String filterContent = name + " " + content;
		List<FilterWord> words = this.filterWordService.getFilterWords(filterContent);
		return words;
	}

	@Override
	synchronized public boolean saveParseResult2MongoIgnoreFilterWord(Long parseResultId, Boolean isIgnoreFilterWord) {
		logger.debug(">>FaceYe --> save parse result ");
		ParseResult parseResult = this.get(parseResultId);
		return this.saveParseResult2Mongo(parseResult, isIgnoreFilterWord);
	}

	/**
	 * 将解析结果中的同名文章排除掉
	 */
	@Override
	public void dedup() {
		int pageNum = 1;
		Boolean isContinue = Boolean.TRUE;
		int loops = 0;
		while (isContinue) {
			Map searchParams = new HashMap();
			// searchParams.put("ISFALSE|isPush2Mongo", "0");
			// searchParams.put("ISFALSE|isContainsFilterWord", "0");
			Page<ParseResult> page = this.getPage(searchParams, pageNum, 1000);
			if (page != null && CollectionUtils.isNotEmpty(page.getContent())) {
				logger.debug(">>FaceYe --> dump  now, do loop is:" + (loops++));
				for (ParseResult parseResult : page.getContent()) {
					Map queryByName = new HashMap();
					queryByName.put("name", parseResult.getName());
					// 删除重名的文章
					Page byNameResults = this.getPage(queryByName, 1, 100);
					if (null != byNameResults && byNameResults.getContent().size() > 1) {
						logger.debug(">>FaceYe --> Dedup name:" + parseResult.getName());
						this.dao.delete(parseResult);
					}
					// this.saveParseResult2Mongo(parseResult);
				}
				if (!page.hasNext()) {
					isContinue = Boolean.FALSE;
				} else {
					pageNum++;
				}
			} else {
				isContinue = Boolean.FALSE;
			}
		}
	}

	@Override
	public Page<ParseResult> getPage(Map<String, Object> searchParams, int page, int size)   {
		if (page != 0) {
			page = page - 1;
		}
		QParseResult qParseResult = QParseResult.parseResult;
		BooleanBuilder builder = new BooleanBuilder();
		if (MapUtils.isNotEmpty(searchParams)) {
			if (StringUtils.isNotEmpty(MapUtils.getString(searchParams, "isPush2Mongo"))) {
				if (StringUtils.equals(MapUtils.getString(searchParams, "isPush2Mongo"), "0")) {
					builder.and(qParseResult.isPush2Mongo.isFalse());
				} else {
					builder.and(qParseResult.isPush2Mongo.isTrue());
				}
			}
			if (StringUtils.isNotEmpty(MapUtils.getString(searchParams, "isPush2ProductEnv"))) {
				if (StringUtils.equals(MapUtils.getString(searchParams, "isPush2ProductEnv"), "0")) {
					builder.and(qParseResult.isPush2ProductEnv.isFalse());
				} else {
					builder.and(qParseResult.isPush2ProductEnv.isTrue());
				}
			}

			if (searchParams.containsKey("isContainsFilterWord")) {
				if (MapUtils.getBooleanValue(searchParams, "isContainsFilterWord")) {
					builder.and(qParseResult.isContainsFilterWord.isTrue());
				} else {
					builder.and(qParseResult.isContainsFilterWord.isFalse());
				}
			}
			if (StringUtils.isNotEmpty(MapUtils.getString(searchParams, "name"))) {
				builder.and(qParseResult.name.like("%" + MapUtils.getString(searchParams, "name") + "%"));
			}

			if (StringUtils.isNotEmpty(MapUtils.getString(searchParams, "sourceUrl"))) {
				String sourceUrl = MapUtils.getString(searchParams, "sourceUrl");
				String rootDomain = URLUtils.getDomainKey(sourceUrl);
				builder.and(qParseResult.sourceUrl.like("%" + rootDomain + "%"));
			}
			Long siteId = MapUtils.getLong(searchParams, "siteId");
			if (siteId != null) {
				builder.and(qParseResult.siteId.eq(siteId));
			}
			Long categoryId = MapUtils.getLong(searchParams, "categoryId");
			if (categoryId != null) {
				builder.and(qParseResult.categoryId.eq(categoryId));
			}
			// 根据级别查询
			if (MapUtils.getInteger(searchParams, "level") != null) {
				builder.and(qParseResult.level.eq(MapUtils.getInteger(searchParams, "level")));
			}
			// 是否审核通过
			if (StringUtils.isNotEmpty(MapUtils.getString(searchParams, "isAllow"))) {
				if (StringUtils.equals(MapUtils.getString(searchParams, "isAllow"), "1")) {
					builder.and(qParseResult.isAllow.isTrue());
				} else {
					builder.and(qParseResult.isAllow.isFalse());
				}
			} else {
				// builder.and(qParseResult.isAllow.isFalse());
			}
			if (StringUtils.isNotEmpty(MapUtils.getString(searchParams, "isRemove"))) {
				if (StringUtils.equals(MapUtils.getString(searchParams, "isRemove"), "1")) {
					builder.and(qParseResult.isRemove.isTrue());
				} else {
					builder.and(qParseResult.isRemove.isFalse());
				}
			} else {
				builder.and(qParseResult.isRemove.isFalse());
			}
			if(StringUtils.isNotEmpty(MapUtils.getString(searchParams, "isEmpty|sign"))){
				builder.and(qParseResult.sign.isEmpty());
			}
		}
		Predicate predicate = builder.getValue();
		if (predicate != null) {
			logger.info(">>FaceYe query parseResult params is:" + predicate.toString());
			logger.error(">>FaceYe query parseResult params is:" + predicate.toString());
		}
		Sort sort = new Sort(Direction.DESC, "id");
		Pageable pageable = new PageRequest(page, size, sort);
		Page<ParseResult> res = this.dao.findAll(predicate, pageable);
		if (predicate != null) {
			logger.debug(">>FaceYe query parseResult params is:" + predicate.toString() + ",page is:" + page + ",size is:" + size
					+ ",query result size is:" + res.getContent().size());
		}
		return res;
	}

	/**
	 * 通过WEB接口的形式，推送到生产环境
	 * @todo
	 * @param parseResult
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年12月29日
	 */
	private boolean push2PruductEnv(ParseResult parseResult) {
		boolean res = false;
		if (null != parseResult) {
			String signKey = "FACEYE-NET-SONG";
			Map params = new HashMap();
			// params.put(key, parseResult.get)
			params.put("categoryAlias", parseResult.getCategoryAlias());
			params.put("categoryId", parseResult.getCategoryId());
			params.put("categoryName", parseResult.getCategoryName());
			params.put("content", parseResult.getContent());
			params.put("description", parseResult.getDescription());
			params.put("keywords", parseResult.getKeywords());
			String name = parseResult.getName();
			if (StringUtils.isNotEmpty(parseResult.getSourceUrl())
					&& parseResult.getSourceUrl().toLowerCase().indexOf("segmentfault.com") != -1) {
				name = StringUtils.replace(name, "SegmentFault", "");
				name = StringUtils.replace(name, "-", "");
				name = StringUtils.trim(name);
			}
			params.put("name", name);
			params.put("subjectAlias", parseResult.getSubjectAlias());
			params.put("subjectId", parseResult.getSubjectId());
			params.put("subjectName", parseResult.getSubjectName());
			params.put("summary", parseResult.getSummary());
			params.put("key", signKey);
			params.put("sourceUrl", parseResult.getSourceUrl());
			params.put("isWeixin", parseResult.getIsWeiXin());
			String resJson = Http.getInstance().post(pushUrl, "UTF-8", params);
			logger.debug(">>FaceYe --> Push to line,push url is:" + pushUrl + ",article name is :" + name);
			logger.debug(">>FaceYe--> push return json is:" + resJson);
			if (StringUtils.isNotEmpty(resJson) && resJson.toLowerCase().indexOf("success") != -1) {
				res = true;
			}
		}
		return res;
	}

	private String buildSignkey() {
		int dayOfMonth = Calendar.DAY_OF_MONTH;// 1-31
		int monthOfYear = Calendar.MONTH;// 0-11
		int hourOfDay = Calendar.HOUR_OF_DAY;// 0-23
		String sign = "" + ((monthOfYear + hourOfDay) * dayOfMonth);
		return sign;
	}

	@Override
	public List<ParseResult> getParseResultsByName(String name) {
		return dao.getParseResultsByName(name);
	}

	@Override
	public int getCount(Map searchParams) {
		int count = 0;
		QParseResult qParseResult = QParseResult.parseResult;
		Predicate predicate = qParseResult.id.isNotNull();
		BooleanBuilder builder = new BooleanBuilder();

		// String sourceUrl = MapUtils.getString(searchParams, "sourceUrl");
		Long siteId = MapUtils.getLong(searchParams, "EQ|siteId");
		String startDate = MapUtils.getString(searchParams, "startDate");
		String endDate = MapUtils.getString(searchParams, "endDate");
		// if (StringUtils.isNotEmpty(sourceUrl)) {
		// builder.and(qParseResult.sourceUrl.like("%" + sourceUrl + "%"));
		// }
		if (null != siteId) {
			builder.and(qParseResult.siteId.eq(siteId));
		}
		if (StringUtils.isEmpty(startDate)) {
			startDate = "" + (System.currentTimeMillis() - 24 * 60 * 60 * 1000L);
		}
		if (StringUtils.isEmpty(endDate)) {
			endDate = "" + System.currentTimeMillis();
		}
		builder.and(qParseResult.createDate.after(new Date(Long.parseLong(startDate))));
		builder.and(qParseResult.createDate.before(new Date(Long.parseLong(endDate))));
		long res = this.dao.count(builder.getValue());
		// count=this.dao.count(builder.getValue());
		count = new Long(res).intValue();
		return count;
	}

	@Override
	public void saveAuthPublish() {
		Map searchParams = new HashMap();
		searchParams.put("isAllow", "1");
		searchParams.put("isPush2Mongo", "0");
		searchParams.put("isPush2ProductEnv", "0");
		Page<ParseResult> page = this.getPage(searchParams, 1, 15);
		if (page == null || CollectionUtils.isEmpty(page.getContent())) {
			logger.debug(">>FaceYe --> Auto publish have no parse result 2 publish.");
		} else {
			for (ParseResult parseResult : page.getContent()) {
				logger.debug(">>FaceYe auto publish parse result ,id:" + parseResult.getName() + ",name:" + parseResult.getName());
				boolean res = this.saveParseResult2Mongo(parseResult, true);
				if (!res) {
					parseResult.setIsAllow(false);
					this.save(parseResult);
				}
				logger.debug(">>FaceYe auto publish parse result ,id:" + parseResult.getName() + ",name:" + parseResult.getName()
						+ ",publish result:" + res);
				try {
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					logger.error(">>FaceYe throws Exception: --->" + e.toString());
				}
			}
		}
	}

	@Override
	public ParseResult getParseResultBySign(String sign) {
		return this.dao.getParseResultBySign(sign);
	}
}
/**@generate-service-source@**/

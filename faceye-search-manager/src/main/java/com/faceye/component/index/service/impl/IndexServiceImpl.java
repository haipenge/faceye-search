package com.faceye.component.index.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.faceye.component.index.doc.AnalyzerWord;
import com.faceye.component.index.service.AnalyzerWordService;
import com.faceye.component.index.service.IndexService;
import com.faceye.component.index.service.Word;
import com.faceye.component.index.util.AnalyzerFactory;
import com.faceye.component.parse.util.HtmlUtil;
import com.faceye.component.search.doc.Article;
import com.faceye.component.search.service.SearchArticleService;
import com.faceye.component.search.service.SearchService;
import com.faceye.component.spider.util.PathUtil;
import com.faceye.feature.service.SequenceService;

/**
 * 索引服务类
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月13日
 */
@Service
public class IndexServiceImpl implements IndexService {
	@Value("#{property['spider.root.index.path']}")
	private String INDEX_PATH = "";

	private Analyzer analyzer = null;
	private IndexWriter indexWriter = null;
	private Directory dir = null;

	private Logger logger = LoggerFactory.getLogger(getClass());
	// @Autowired
	// private ParseResultService parseResultService = null;

	@Autowired
	private SearchService searchService = null;

	@Autowired
	private SearchArticleService searchArticleService = null;

	@Autowired
	private AnalyzerWordService analyzerWordService = null;

	@Autowired
	private SequenceService sequenceService = null;

	/**
	 * 创建索引
	 */
	@Override
	public void buildIndex() {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("isIndexed", Boolean.FALSE);
		Boolean isContinue = Boolean.TRUE;
		try {
			this.init();
			while (isContinue) {
				try {
					Page<Article> searchArticles = this.searchArticleService.getPage(searchParams, 1, 500);
					logger.debug(">>FaceYe now build index doc.page size is:" + searchArticles.getContent().size());
					if (null == searchArticles || CollectionUtils.isEmpty(searchArticles.getContent())) {
						isContinue = Boolean.FALSE;
					}
					// Page<ParseResult> parseResults = this.parseResultService.getPage(searchParams, 1, 100);
					List<Document> docs = new ArrayList<Document>(0);
					for (Article article : searchArticles) {
						String name = article.getName();
						String content = HtmlUtil.getInstance().replaceAll(article.getContent());
						Document doc = this.buildDocument(article.getId(), name, content, article.getCreateDate());
						docs.add(doc);
						article.setIsIndexed(Boolean.TRUE);
						this.searchArticleService.save(article);
						// this.saveAnalyzerResult(name+","+content);
						logger.debug(">>FaceYe indexed :" + article.getId() + ":" + article.getName());
						// parseResult.setIsIndex(Boolean.TRUE);
						// this.parseResultService.save(parseResult);
					}
					indexWriter.addDocuments(docs, analyzer);
					indexWriter.commit();
				} catch (IOException e) {
					logger.error(">>--->" + e.getMessage());
				}
				// 每个批次间暂停30s
				Thread.sleep(3 * 1000L);
			}
		} catch (Exception e) {
			logger.error(">>-->", e);
		} finally {
			destroy();
		}
	}

	private Document buildDocument(Long id, String name, String content, Date createDate) {
		Document doc = new Document();
		// name=StringUtils.lowerCase(name);
		// content=StringUtils.lowerCase(content);
		Field fieldID = new StringField("id", id == null ? "" : id.toString(), Field.Store.YES);
		doc.add(fieldID);
		Field fieldName = new TextField("name", name, Field.Store.YES);
		doc.add(fieldName);
		Field fieldContent = new TextField("content", content, Field.Store.YES);
		// 向文档中加入域
		doc.add(fieldContent);
		if (createDate == null) {
			createDate = new Date();
		}
		Field fieldDate = new StringField("createDate", DateUtils.formatDate(createDate, "yyyy-MM-dd HH:mm:ss"), Field.Store.YES);
		doc.add(fieldDate);
		return doc;
	}

	/**
	 * 全局初始化
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月14日
	 */
	private void init() {
		this.getAnalyzer();
		this.getIndexWriter();
		this.getDir();
	}

	/**
	 * 初始化基础工具
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月14日
	 */

	private Analyzer getAnalyzer() {
		if (analyzer == null) {
			analyzer = AnalyzerFactory.getAnalyzer();
		}
		return analyzer;
	}

	private IndexWriter getIndexWriter() {
		if (indexWriter == null) {
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_9, getAnalyzer());
			try {
				indexWriter = new IndexWriter(getDir(), config);
			} catch (IOException e) {
				logger.error(">>--->" + e.getMessage());
			}
		}
		return indexWriter;
	}

	private Directory getDir() {
		if (dir == null) {
			try {
				dir = FSDirectory.open(new File(this.buildIndexPath(INDEX_PATH)));
			} catch (IOException e) {
				logger.error(">>--->" + e.getMessage());
			}
		}
		return dir;
	}

	/**
	 * 创建每日存储索引目录 
	 * @todo
	 * @param parentPath
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月14日
	 */
	private String buildIndexPath(String parentPath) {
		String res = "";
		if (StringUtils.isEmpty(parentPath)) {
			parentPath = this.INDEX_PATH;
		}
		String dailyPath = "";
		try {
			dailyPath = PathUtil.generatePath();
		} catch (Exception e) {
			logger.error(">>--->" + e.getMessage());
		}
		if (StringUtils.isEmpty(dailyPath)) {
			dailyPath = "DEFAULT_DAILY";
		}
		res = parentPath + "/" + dailyPath;
		return res;

	}

	/**
	 * 数据清理
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月14日
	 */
	private void destroy() {
		if (indexWriter != null) {
			try {
				indexWriter.close();
				indexWriter = null;
			} catch (IOException e) {
				logger.error(">>--->" + e.getMessage());
			}
		}
		if (dir != null) {
			try {
				dir.close();
				dir = null;
			} catch (IOException e) {
				logger.error(">>--->" + e.getMessage());
			}

		}
	}

	/**
	 * 对文本进行解析，并将解析结果进行保存
	 * @todo
	 * @param content
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年10月2日
	 */
	private void saveAnalyzerResult(String content) {
		WordContainer wordContainer = this.searchService.getKeywords(content);
		if (null != wordContainer && CollectionUtils.isNotEmpty(wordContainer.getWords())) {
			for (Word word : wordContainer.getWords()) {
				String keyWord = word.getText();
				int count = word.getCount();
				AnalyzerWord analyzerWord = this.analyzerWordService.getAnalyzerWordByWord(keyWord);
				if (null != analyzerWord) {
					analyzerWord.setWordCount(analyzerWord.getWordCount() + count);
				} else {
					analyzerWord = new AnalyzerWord();
					analyzerWord.setId(this.sequenceService.getNextSequence(AnalyzerWord.class.getName()));
					analyzerWord.setWord(keyWord);
					analyzerWord.setWordCount(count);
				}
				this.analyzerWordService.save(analyzerWord);
			}
		}
	}

}

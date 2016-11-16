package com.faceye.component.parse.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.doc.FilterWord;
import com.faceye.component.parse.repository.mongo.FilterWordRepository;
import com.faceye.component.parse.service.FilterWordService;
import com.faceye.component.parse.util.SequenceConstants;
import com.faceye.feature.service.PropertyService;
import com.faceye.feature.service.SequenceService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;

@Service
public class FilterWordServiceImpl extends BaseMongoServiceImpl<FilterWord, Long, FilterWordRepository> implements FilterWordService {

	@Autowired
	private PropertyService propertyService = null;

	@Autowired
	private SequenceService sequenceService = null;

	private List<FilterWord> filterWords = null;

	@Autowired
	public FilterWordServiceImpl(FilterWordRepository dao) {
		super(dao);
	}

	@Override
	public void init() {
		List<String> words = this.read();
		if (CollectionUtils.isNotEmpty(words)) {
			for (String word : words) {
				FilterWord filterWord = this.dao.getFilterWordByWord(word);
				if (filterWord == null) {
					filterWord = new FilterWord();
					Long nextSeq = this.sequenceService.getNextSequence(SequenceConstants.SEQ_PUBLIC);
					filterWord.setCreateDate(new Date());
					filterWord.setId(nextSeq);
					filterWord.setLevel(new Integer(0));
					filterWord.setWord(word);
					this.dao.save(filterWord);
				}
			}
		}
	}

	private List<String> read() {
		List<String> res = new ArrayList<String>();
		String dir = this.propertyService.get("filter.word.dir");
		File file = new File(dir);
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			if (listFiles != null) {
				for (File f : listFiles) {
					String path = dir + "/" + f.getName();
					logger.debug(">>FaceYe --> file is:" + path);
					BufferedReader in;
					try {
						in = new BufferedReader(new FileReader(path));
						String s;
						while ((s = in.readLine()) != null) {
							String word = "";
							if (StringUtils.isNotEmpty(s)) {
								String[] str = null;
								if (s.indexOf("=") != -1) {
									str = s.split("=");
								} else if (s.indexOf("|") != -1) {
									str = s.split("\\|");
								} else {
									word = s;
								}
								if (null != str) {
									word = StringUtils.trim(str[0]);
								}
								logger.debug(">>FaceYe --> s is:" + s);
								if (StringUtils.isNotEmpty(word)) {
									logger.debug(">>FaceYe --> Word is:" + word);
									res.add(word);
								}
							}

						}
						in.close();
					} catch (FileNotFoundException e) {
						logger.error(">>--->" + e.getMessage());
					} catch (IOException e) {
						logger.error(">>--->" + e.getMessage());
					}

				}
			}
		}
		return res;
	}

	@Override
	public boolean isContainsFilterWrod(String content) {
		boolean res = Boolean.FALSE;
		if (CollectionUtils.isEmpty(filterWords)) {
			filterWords = this.getAll();
		}
		if (StringUtils.isNotEmpty(content) && CollectionUtils.isNotEmpty(filterWords)) {
			for (FilterWord filterWord : filterWords) {
				String word = filterWord.getWord();
				word = StringUtils.trim(word);
				if (StringUtils.isNotEmpty(word) && content.indexOf(word) != -1) {
					res = Boolean.TRUE;
					logger.debug(">>FaceYe --> Forbidden Word:" + word + ",id is:" + filterWord.getId());
					break;
				}
			}
		}
		return res;
	}

	@Override
	public List<FilterWord> getFilterWords(String content) {
		if (CollectionUtils.isEmpty(filterWords)) {
			filterWords = this.getAll();
		}
		List<FilterWord> res = new ArrayList<FilterWord>(0);
		if (StringUtils.isNotEmpty(content) && CollectionUtils.isNotEmpty(filterWords)) {
			for (FilterWord filterWord : filterWords) {
				String word = filterWord.getWord();
				word = StringUtils.trim(word);
				if (StringUtils.isNotEmpty(word) && content.indexOf(word) != -1) {
					res.add(filterWord);
					logger.debug(">>FaceYe --> Forbidden Word:" + word + ",id is:" + filterWord.getId());
				}
			}
		}
		return res;
	}

}
/**@generate-service-source@**/

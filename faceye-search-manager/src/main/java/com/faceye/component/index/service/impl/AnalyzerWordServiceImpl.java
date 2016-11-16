package com.faceye.component.index.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.index.doc.AnalyzerWord;
import com.faceye.component.index.repository.mongo.AnalyzerWordRepository;
import com.faceye.component.index.service.AnalyzerWordService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;

@Service
public class AnalyzerWordServiceImpl extends BaseMongoServiceImpl<AnalyzerWord, Long, AnalyzerWordRepository> implements AnalyzerWordService {

	@Autowired
	public AnalyzerWordServiceImpl(AnalyzerWordRepository dao) {
		super(dao);
	}

	@Override
	public AnalyzerWord getAnalyzerWordByWord(String word) {
		return this.dao.getAnalyzerWordByWord(word);
	}
	
	
}/**@generate-service-source@**/

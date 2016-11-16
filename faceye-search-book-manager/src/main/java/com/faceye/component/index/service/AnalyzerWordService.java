package com.faceye.component.index.service;

import com.faceye.component.index.doc.AnalyzerWord;
import com.faceye.feature.service.BaseService;

public interface AnalyzerWordService extends BaseService<AnalyzerWord,Long>{

	/**
	 * 根据解析出来的词，查找解析对像
	 * @todo
	 * @param word
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年10月2日
	 */
	public AnalyzerWord getAnalyzerWordByWord(String word);
}/**@generate-service-source@**/

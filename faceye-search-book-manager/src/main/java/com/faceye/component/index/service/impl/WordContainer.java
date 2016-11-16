package com.faceye.component.index.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.faceye.component.index.service.Word;

/**
 * 分词计算容器
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年8月10日
 */
public class WordContainer {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private List<Word> words = null;

	public WordContainer() {
		if (null == words) {
			words = new ArrayList<Word>();
		}
	}

	/**
	 * 从集合中查词
	 * @todo
	 * @param text
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年8月10日
	 */
	public Word getWord(String text) {
		Word word = null;
		for (Word w : words) {
			if (StringUtils.equals(w.getText(), text)) {
				word = w;
				break;
			}
		}
		return word;
	}

	/**
	 * 增加一个词到容器中
	 * @todo
	 * @param text
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年8月10日
	 */
	public void addWord(String text) {
		Word word = this.getWord(text);
		if (null == word) {
			word = new Word();
			word.setText(text);
			word.setCount(1);
			words.add(word);
		} else {
			word.increcement();
		}
	}

	public List<Word> getWords() {
		sort();
		return words;
	}

	/**
	 * 根据频度对词进行排序
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年8月10日
	 */
	private void sort() {
		WordComparator comparator = new WordComparator();
		Collections.sort(words, comparator);
	}

	public void println() {
		sort();
		for (Word word : words) {
			logger.debug(">>FaceYe --> word:" + word.getText() + "    :    " + word.getCount());
		}
	}

	public String toString() {
		StringBuilder res = new StringBuilder("");
		List<Word> words = this.getWords();
		if (CollectionUtils.isNotEmpty(words)) {
			for (Word word : words) {
				boolean isWord = this.isWord(word.getText());
				if (isWord) {
					res.append(word.getText());
					res.append(" ");
				}
			}
		}
		return res.toString();
	}

	/**
	 * 是否是一个词
	 * @todo
	 * @param word
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年8月14日
	 */
	private boolean isWord(String word) {
		boolean isWord = Boolean.TRUE;
		/**
		 * 去掉单字
		 */
		if (StringUtils.isEmpty(word) || word.length() == 1 || isNumber(word)) {
			isWord = Boolean.FALSE;
		}

		return isWord;
	}

	/**
	 * 是否是数字 
	 * @todo
	 * @param word
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年8月14日
	 */
	private boolean isNumber(String word) {
		boolean isNumber = Boolean.FALSE;
		Pattern pattern = Pattern.compile("[0-9]*");
		isNumber = pattern.matcher(word).matches();
		return isNumber;
	}

}

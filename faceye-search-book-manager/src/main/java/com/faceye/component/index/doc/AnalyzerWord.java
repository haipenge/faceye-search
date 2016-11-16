package com.faceye.component.index.doc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * AnalyzerWord ORM 实体
 * 数据库表:index_analyzerWord
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */
@Document(collection = "index_analyzer_word")
public class AnalyzerWord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8926119711730830203L;
	@Id
	private Long id = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	   * 说明:解析词
	   * 属性名: word
	   * 类型: String
	   * 数据库字段:word
	   * @author haipenge
	   */
	@Indexed
	private String word;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * 说明:词频
	 * 属性名: wordCount
	 * 类型: Integer
	 * 数据库字段:word_count
	 * @author haipenge
	 */
	private Integer wordCount;

	public Integer getWordCount() {
		return wordCount;
	}

	public void setWordCount(Integer wordCount) {
		this.wordCount = wordCount;
	}
}
/**@generate-entity-source@**/

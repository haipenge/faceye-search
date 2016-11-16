package com.faceye.component.search.service.impl;

import java.io.Serializable;

/**
 * 分词后的词对像
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年8月10日
 */
public class Word implements Serializable {
	// 词
	private String text = "";
	// 出现的频度(总次数)
	private Integer count = new Integer(1);

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * 频度计数器自动+1
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年8月10日
	 */
	public void increcement() {
		this.count++;
	}

}

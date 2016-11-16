package com.faceye.component.search.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 检索结果
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月20日
 */
public class SearchResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 103780368535235940L;
	private Long id = null;
	private String name = "";
	private List<String> contents=new ArrayList<String>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getContents() {
		return contents;
	}
	public void setContents(List<String> contents) {
		this.contents = contents;
	}
	
}

package com.faceye.component.parse.service.factory.model.csj;

import java.util.ArrayList;
import java.util.List;

public class Question {

	private Integer nextid = null;
	private String mold = "";
	private String title = "";
	private String leixing = "";
	private List<Answer> datalist = new ArrayList<Answer>();

	public Integer getNextid() {
		return nextid;
	}

	public void setNextid(Integer nextid) {
		this.nextid = nextid;
	}

	public String getMold() {
		return mold;
	}

	public void setMold(String mold) {
		this.mold = mold;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLeixing() {
		return leixing;
	}

	public void setLeixing(String leixing) {
		this.leixing = leixing;
	}

	public List<Answer> getDatalist() {
		return datalist;
	}

	public void setDatalist(List<Answer> datalist) {
		this.datalist = datalist;
	}

}

package com.faceye.component.parse.service.factory.model.csj;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试酱问题json对像解析
 * 
 * @author haipenge Exam:http://wx.dm15.com/toggleQuestion.php?t=1440&type=2
 */
public class Result {
	private Integer status = null;
	private String message = "";
	private List<Question> question = new ArrayList<Question>(0);

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Question> getQuestion() {
		return question;
	}

	public void setQuestion(List<Question> question) {
		this.question = question;
	}

}

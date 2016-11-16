package com.faceye.component.push.service.model;

import java.io.Serializable;

/**
 * 文章推送结果
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年1月24日
 */
public class PushResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1879492662884040948L;
	
	/**
	 * 是否推送成功
	 */
	private Boolean isSuccess=false;
	
	/**
	 * 推送成功后返回URL
	 */
	private String returnUrl="";
	
	/**
	 * 推送返回消息
	 */
	private String msg="";

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	

}

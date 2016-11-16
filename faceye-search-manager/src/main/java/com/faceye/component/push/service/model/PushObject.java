package com.faceye.component.push.service.model;

/**
 * 推送对像
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年1月22日
 */
public class PushObject implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4907657109732455480L;
	private Long id=null;
	//标题
	private String name="";
	//内容
	private String content="";
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}

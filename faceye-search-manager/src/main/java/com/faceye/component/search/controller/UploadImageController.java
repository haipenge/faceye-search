package com.faceye.component.search.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 图片上传，用于文章中插入图片
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年3月28日
 */
@Controller("/search/uploadImage")
@Scope("prototype")
public class UploadImageController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/upload")
	public String upload(){
		return "";
	}
	
}

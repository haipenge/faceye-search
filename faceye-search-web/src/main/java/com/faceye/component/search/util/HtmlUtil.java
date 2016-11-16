package com.faceye.component.search.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Html处理
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月10日
 */
public class HtmlUtil {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static HtmlUtil INSTANCE = null;

	private HtmlUtil() {

	}

	public static HtmlUtil getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new HtmlUtil();
		}
		return INSTANCE;
	}

	/**
	 * 脱掉content中的html标记
	 * @todo
	 * @param content
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月10日
	 */
	public String replaceHtml(String content) {
		String res = "";
		res=this.replace(content, RegexpConstants.HTML_DIV_START);
		res=this.replace(res, RegexpConstants.HTML_DIV_END);
		res=this.replace(res, RegexpConstants.HTML_SCRIPT);
		res=this.replace(res, RegexpConstants.HTML_NOTE);
		res=this.replace(res, RegexpConstants.REPLACE_ALL_A_HREF);
		res=this.replace(res, RegexpConstants.REPLACE_ALL_IMG);
		return res;
	}
	
	
	public String replaceAll(String content){
		content=this.replace(content, RegexpConstants.HTML_ALL);
		return content;
	}
	
	public String replace(String content,String regex){
		String res="";
		if(StringUtils.isNotEmpty(content)&&StringUtils.isNotEmpty(regex)){
			res=content.replaceAll(regex,"");
		}
		return res;
	}

}

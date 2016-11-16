package com.faceye.component.push.service.model;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.faceye.component.search.doc.Article;

/**
 * 推送对像构造器
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年1月22日
 */
public class PushObjectBuilder {

	private static Logger logger = LoggerFactory.getLogger(PushObjectBuilder.class);

	private static String pushHost = "http://faceye.net/";

	public static PushObject builder(String name, String content) {
		PushObject pushObject = null;
		if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(content)) {
			pushObject = new PushObject();
			pushObject.setName(name);
			pushObject.setContent(content);
		}
		return pushObject;
	}

	public static PushObject builder(Article article) {
		PushObject pushObject = null;
        pushObject=wrapPushObject(article);
		return pushObject;
	}

	/**
	 * 将文章包装成推送对像进行推送
	 * @todo
	 * @param article
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月24日
	 */
	private static PushObject wrapPushObject(Article article) {
		PushObject pushObject = PushObjectBuilder.builder(article.getName(), article.getContent());
		pushObject.setId(article.getId());
		pushObject.setContent(wrapContent(article));
		return pushObject;
	}

	private static String wrapContent(Article article) {
		StringBuilder sb = new StringBuilder("");
		String wrapperLink = buildPushLink(article);
		// sb.append(wrapperLink);
		sb.append(article.getContent());
		sb.append(wrapperLink);
		return sb.toString();
	}

	private static String buildPushUrl(Article article) {
		String url = pushHost + "search/" + article.getId() + ".html";
		return url;
	}

	/**
	 * 构造推送链接
	 * @todo
	 * @param article
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月24日
	 */
	private static String buildPushLink(Article article) {
		StringBuilder sb = new StringBuilder();
		String url = buildPushUrl(article);
		sb.append("<p><strong><font color=\"green\">原文链接:</font></strong>");
		sb.append("<a href=\"");
		sb.append(url);
		sb.append("\">");
		sb.append(url);
		sb.append("</a>");
		sb.append("</p>");
		return sb.toString();
	}

}

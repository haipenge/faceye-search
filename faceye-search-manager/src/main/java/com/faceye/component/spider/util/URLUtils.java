package com.faceye.component.spider.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 处理URL的工具类
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年1月19日
 */
public final class URLUtils {

	/**
	 * 从URL中取得域名,如http://xx.yy.zz.com，将取得xx.yy.zz.com,不带www
	 * @todo
	 * @param url
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月19日
	 */
	public static String getDomain(String url) {
		StringBuilder sb = new StringBuilder("");
		if (StringUtils.isNotEmpty(url)) {
			url = url.substring(url.indexOf("//") + 2);
			if (url.indexOf("/") != -1) {
				url = url.substring(0, url.indexOf("/"));
			}
			String[] parts = url.split("\\.");
			if (parts != null && parts.length > 0) {
				for (int i = 0; i < parts.length; i++) {
					if (!StringUtils.equalsIgnoreCase(parts[i], "www")) {
						sb.append(parts[i]);
						sb.append(".");
					}
				}
			}
			if (sb.length() != 0) {
				sb.deleteCharAt(sb.lastIndexOf("."));
			}
		}
		return sb.toString();
	}

	/**
	 * 取得根域名，如http://xx.yy.zz.com，将取得zz.com
	 * @todo
	 * @param url
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月24日
	 */
	public static String getRootDomain(String url) {
		String domain = "";
		String fullDomain = getDomain(url);
		if (StringUtils.isNotEmpty(fullDomain)) {
			String reverse = StringUtils.reverseDelimited(fullDomain, '.');
			String[] reverses = reverse.split("\\.");
			if (reverses != null && reverses.length > 2) {
				for (int i = 0; i < 2; i++) {
					domain += reverses[i];
					if (i == 0) {
						domain += ".";
					}
				}
			} else {
				domain = reverse;
			}

		}
		domain = StringUtils.reverseDelimited(domain, '.');
		return domain;
	}

	/**
	 * 仅仅取得域名本身，如http://xx.yy.zz.com，将取得zz
	 * @todo
	 * @param url
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月24日
	 */
	public static String getDomainKey(String url) {
		String key = "";
		String rootDomain = getRootDomain(url);
		if (StringUtils.isNotEmpty(rootDomain)) {
			if (StringUtils.indexOf(rootDomain, ".") != -1) {
				String[] roots = rootDomain.split("\\.");
				if (roots != null && roots.length == 2) {
					key = roots[0];
				}

			}
		}
		return key;
	}

	/**
	 * 取得域名的链接
	 * 如:http://www.ssx.ss.xss.com/htl/sl.do?i=1
	 * 将返回:http://www.ssx.ss.xss.com
	 * @todo
	 * @param url
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月5日
	 */
	public static String getDomainUrl(String url) {
		String res = "";
		if (StringUtils.isNotEmpty(url)) {
			res += "http://";
			url = url.substring(url.indexOf("//") + 2);
			if (url.indexOf("/") != -1) {
				res += url.substring(0, url.indexOf("/"));
			} else if (url.indexOf("?") != -1) {
				res += url.substring(0, url.indexOf("?"));
			}else{
				res+=url;
			}
		}
		return res;
	}
}

package com.faceye.component.search.util;

public class RegexpConstants {
	// 匹配中文字符的正则表达式：
	public static final String CHINESE_CHARACTERS = "[\u4e00-\u9fa5]";
	// 评注：匹配中文还真是个头疼的事，有了这个表达式就好办了
	// 匹配双字节字符(包括汉字在内)：
	public static final String DOUBLE_BYTE_CHAR = "[^\\x00-\\xff]";
	// 评注：可以用来计算字符串的长度（一个双字节字符长度计2，ASCII字符计1）
	// 匹配空白行的正则表达式：
	public static final String BLANK_LINE = "\\n\\s*\\r";
	// 评注：可以用来删除空白行
	// 匹配HTML标记的正则表达式：
	public static final String HTML = "<(\\S*?)[^>]*>.*?</\1>|<.*? />";
	// 评注：网上流传的版本太糟糕，上面这个也仅仅能匹配部分，对于复杂的嵌套标记依旧无能为力
	// 匹配首尾空白字符的正则表达式：^\s*|\s*$
	// 评注：可以用来删除行首行尾的空白字符(包括空格、制表符、换页符等等)，非常有用的表达式
	// 匹配Email地址的正则表达式：
	public static final String EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	// 评注：表单验证时很实用
	// 匹配网址URL的正则表达式：[a-zA-z]+://[^\s]*
	/**
	 * 提取A标签里面的URL
	 * <a[^>]*?href=[\"\\']?([^\"\\'>]+?)[\"\\']?[^>]*>.+?<[\\s]*?\\/a>
	 */
	public static final String DISTIL_A_HREF = "<a[^>]*?href=[\"\\']?([^\"\\'>]+)[\"\\']?[^>]*>.+?<[\\s]*?\\/a>";
	/**
	 * 提取网页的标题
	 */
	public static final String DISTIAL_HTML_TITILE = "<title[^\\>]*?>\\s*?(.*?)\\s*?<\\/title>";

	/**
	 * JS
	 */
	public static final String HTML_SCRIPT = "<script[\\s].*?>\\$\\{\\}\\s\\S.*?<\\/script>";

	public static final String HTML_DIV_START = "<div[^>]*?>";
	public static final String HTML_DIV_END = "<[\\s]*?\\/div>";
	public static final String HTML_NOTE = "<!--[\\s].*?-->";
	/**
	 * 匹配全部html元素
	 */
	public static final String HTML_ALL = "<[^>].*?>";

	/**
	 *  提取Cnblogs文章明细
	 */
	public static final String DISTIL_CNBLOGS_BODY = "<div id=\"cnblogs_post_body\">(.+?)<div id=\"blog_post_info_block\">";
	/**
	 * 提取Cnblog博客园子首页 文章明细链接
	 * http://home.cnblogs.com/blog/page/2/
	 */
	public static final String DISTIL_CNBLOGS_LIST_LINKS = "<a[\\s]href=\"([^\"\\'>]+)\"[\\s]target=\"_blank\">[^>]*?</a>";

	/**
	 * 替换页面中的所有A标签
	 */
	public static final String REPLACE_ALL_A_HREF = "<a[^>]*?href=[\"\\']?[^\"\\'>]+[\"\\']?[^>]*>.+?<[\\s]*?\\/a>";
	
	/**
	 * 替换Iframe
	 */
	public static final String REPLACE_ALL_IFRAME="<IFRAME[^>]*?><\\/IFRAME>";

	/**
	 * 替换页面中的所有IMG标签
	 */
	public static final String REPLACE_ALL_IMG = "<img[^>]*?>";

	/**
	 * 替换页面中的所有div标签
	 */
	public static final String REPLACE_ALL_DIV = "<[\\s]*?\\/?div[^>]*?>";

	/**
	 * 提取oschina博客列表页所有博客文章明细URL
	 */
	// <a href="http://my.oschina.net/Jacedy/blog/300872" target='_blank' title='Lambda表达式解析'>Lambda表达式解析</a>
	public static final String DISTILL_OSCHINA_BLOGS_LIST_LINKS = "<a[\\s]href=\"([^\"\\'>]+)\"[\\s]target=\\'_blank\\'[\\s]title=\\'[^>]*?\\'>[^>]*?</a>";

	/**
	 * 提取oschina 博客中中分类页
	 */
	// <a href="/blog?type=428602#catalogs" class='tag'>移动开发</a>
	public static final String DISTILL_OSCHINA_BLOG_CATEGORY_LINKS = "<a[\\s]href=\"([^\"\\'>]+)\"[\\s]class=\\'tag\\'>[^>]*?</a>";
	/**
	 * 提取OSchina博客内容详情
	 */
	public static final String DISTILL_OSCHINA_BLOG_DTAIL = "<div class=\\'BlogContent\\'>(.+?)</div>[\\s]*?<div class=\\'BlogShare\\'>";

	/**
	 * 提取csdn博客明细
	 */
	public static final String DISTILL_CSDN_BLOG_DETAIL = "<div id=\"article_content\" class=\"article_content\">(.+?)<div class=\"bdsharebuttonbox\" style=\"float: right;\">";

	/**
	 * 提取iteye博客明细
	 */
	public static final String DISTILL_ITEYE_BLOG_DETAIL = "<div class=\"blog_main\">(.+?)<div id=\"bottoms\" class=\"clearfix\">";

}

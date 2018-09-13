package com.faceye.component.parse.service.factory.filter;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.collections.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.faceye.component.book.entity.Author;
//import com.faceye.component.book.entity.Book;
//import com.faceye.component.book.entity.BookCategory;
//import com.faceye.component.book.entity.BookTag;
//import com.faceye.component.book.entity.DownloadResource;
//import com.faceye.component.book.service.AuthorService;
//import com.faceye.component.book.service.BookCategoryService;
//import com.faceye.component.book.service.BookService;
//import com.faceye.component.book.service.BookTagService;
//import com.faceye.component.book.service.DownloadResourceService;
//import com.faceye.component.parse.service.document.Document;
//import com.faceye.component.parse.util.HtmlUtil;
//import com.faceye.component.parse.util.RegexpUtil;
//import com.faceye.component.spider.doc.CrawlResult;
//import com.faceye.component.spider.service.CrawlResultService;
//import com.faceye.feature.util.PathUtil;
//import com.faceye.feature.util.RandUtil;
//import com.faceye.feature.util.http.Http;
//import com.faceye.feature.util.storage.StorageHandler;

/**
 * 看看豆数据解析器
 * 
 * @author haipenge
 *
 */
//@Service
//public class KankandouParseFilter extends BaseParseFilter {
//	@Autowired
//	private CrawlResultService crawlResultService = null;
//	@Autowired
//	private BookService bookService = null;
//	@Autowired
//	private BookTagService bookTagService = null;
//	@Autowired
//	private AuthorService authorService = null;
//	@Autowired
//	private DownloadResourceService downloadResourceService = null;
//	@Autowired
//	private BookCategoryService bookCategoryService = null;
//
//	@Override
//	public void filter(Document document, CrawlResult crawlResult, String content) {
//		List<BookTag> bookTags = new ArrayList<BookTag>(0);
//		Book book = null;
//		try {
//			logger.debug(">>FaceYe track 1 of url:"+document.getLinkUrl());
//			// 取得分类名
//			String bookCategoryName = this.getBookCategoryName(content);
//			// 取得书名
//			String name = this.getName(document.getTitle());
//			logger.debug(">>FaceYe --> Book name is:"+name);
//			book = this.bookService.getBookByName(name);
//			if (book == null) {
//				logger.debug(">>FaceYe track 2 of url:"+document.getLinkUrl());
//				book = new Book();
//				book.setName(name);
//				BookCategory bookCategory = null;
//				if (StringUtils.isNotEmpty(bookCategoryName)) {
//					bookCategory = this.bookCategoryService.getBookCategoryByName(bookCategoryName);
//					if (bookCategory == null) {
//						bookCategory = new BookCategory();
//						bookCategory.setName(bookCategoryName);
//						this.bookCategoryService.save(bookCategory);
//					}
//					book.setBookCategory(bookCategory);
//				}
//				logger.debug(">>FaceYe track 3 of url:"+document.getLinkUrl());
//				
//				// 取得图片
//				String imgUrl = this.getBookImgUrl(content);
//				byte[] imgContent = Http.getInstance().getContent(imgUrl, "UTF-8", null);
//				String imgPath = "";
//				if (imgContent != null) {
//					imgPath = "/kindle/img" + PathUtil.generateDynamicDirs() + RandUtil.randDateString() + StringUtils.substring(imgUrl, StringUtils.lastIndexOf(imgUrl, "."));
//					StorageHandler.getInstance().write(imgContent, imgPath);
//					book.setImgUrl(imgPath);
//				}
//				logger.debug(">>FaceYe track 4 of url:"+document.getLinkUrl());
//				
//				// 取得作者
//				String author = this.getAuthor(content);
//				// 作者简介
//				String profile = this.getAuthorProfile(content);
//				if (StringUtils.isNotEmpty(author)) {
//					Author oAuthor = this.authorService.getAuthorByName(author);
//					if (oAuthor == null) {
//						oAuthor = new Author();
//						oAuthor.setName(author);
//						oAuthor.setProfile(profile);
//						this.authorService.save(oAuthor);
//					}
//					book.setAuthor(oAuthor);
//				}
//				logger.debug(">>FaceYe track 5 of url:"+document.getLinkUrl());
//				
//				// 取得标签
//				String[] tags = this.getTags(content);
//				if (tags != null && tags.length > 0) {
//					for (String tag : tags) {
//						String tagName = StringUtils.trim(tag);
//						if (StringUtils.isNotEmpty(tagName)) {
//							BookTag bookTag = this.bookTagService.getBookTagByName(tagName);
//							if (bookTag == null) {
//								bookTag = new BookTag();
//								bookTag.setName(tagName);
//								this.bookTagService.save(bookTag);
//							}
//							bookTags.add(bookTag);
//						}
//					}
//					if (CollectionUtils.isNotEmpty(bookTags)) {
//						book.setBookTags(bookTags);
//					}
//				}
//				logger.debug(">>FaceYe track 6 of url:"+document.getLinkUrl());
//				
//
//				// 内容简介
//				String contentValidity = this.getContentValidity(content);
//				book.setContentValidity(contentValidity);
//
//				// 资源简介
//				String contentV = this.getObjectContent(content);
//				if (StringUtils.isEmpty(contentValidity)) {
//					book.setContentValidity(contentV);
//				}
//
//				// 目录
//				String catalog = this.getCatalog(content);
//				book.setCatalog(catalog);
//				// 书摘
//				String digest = this.getDigest(content);
//				book.setDigest(digest);
//				this.bookService.save(book);
//
//				logger.debug(">>FaceYe track 7 of url:"+document.getLinkUrl());
//				
//				// 取得下载文件信息map[name,url]
//				List<Map<String, String>> downloadFiles = this.getDownloadResource(content);
//				// List<DownloadResource> resources=new ArrayList<DownloadResource>(0);
//				if (CollectionUtils.isNotEmpty(downloadFiles)) {
//					for (Map map : downloadFiles) {
//						String format = "";
//						String fileName = MapUtils.getString(map, "name");
//						String size = MapUtils.getString(map, "size");
//						format = StringUtils.substring(fileName, StringUtils.lastIndexOf(fileName, ".") + 1);
//						String downloadUrl = MapUtils.getString(map, "url");
//						// logger.debug(">>FaceYe got kindle file download url :"+downloadUrl+",file name is:"+fileName);
//						String downloadStorePath = "/kindle/download" + PathUtil.generateDynamicDirs() + RandUtil.randDateString() + "." + format;
//						logger.debug(
//								">>FaceYe got kindle file download url :" + downloadUrl + ",file name is:" + fileName + ",size is:" + size + ",storage path:" + downloadStorePath);
//						byte[] fileContent = Http.getInstance().getContent(downloadUrl, "UTF-8", null);
//						if (fileContent != null) {
//							StorageHandler.getInstance().write(fileContent, downloadStorePath);
//							DownloadResource resource = new DownloadResource();
//							resource.setFormat(format);
//							resource.setUrl(downloadStorePath);
//							resource.setBook(book);
//							resource.setSize(size);
//							this.downloadResourceService.save(resource);
//						} else {
//							logger.debug(">>FaceYe file content is null of :" + downloadUrl);
//						}
//						// if(StringUtils.isNotEmpty(downloadUrl)){
//						//
//						// }
//					}
//				}
//				logger.debug(">>FaceYe track 8 of url:"+document.getLinkUrl());
//				
//			}
//			crawlResult.setIsParse(true);
//			crawlResult.setIsParseSuccess(true);
//			this.crawlResultService.save(crawlResult);
//			try {
//				Thread.sleep(2000L);
//			} catch (InterruptedException e) {
//				logger.error(">>FaceYe Throws Exception:", e);
//			}
//		} catch (Exception e) {
//			logger.error(">>FaceYe throws Exception when parse kankandou detail :", e);
//		}
//	}
//
//	private String getName(String content) {
//		String name = "";
////		String regexp = "<h1 class=\"object-title\">([\\S\\s]*?)<button class=\"fav-btn\" id=\"fav\">收藏本书<\\/button><\\/h1>";
////		name = this.get(content, regexp);
//		if(StringUtils.isNotEmpty(content)){
//			String [] tmp=StringUtils.split(content, "-");
//			if(tmp!=null && tmp.length>0){
//				name=tmp[0];
//			}
//		}
//		return name;
//	}
//
//	/**
//	 * 提取图片
//	 * 
//	 * @param content
//	 * @return
//	 * @Desc:
//	 * @Author:haipenge
//	 * @Date:2016年8月2日 上午9:17:41
//	 */
//	private String getBookImgUrl(String content) {
//		String url = "";
//		// <div class="object-img">
//		// <img src="https://img1.doubanio.com/lpic/s27971428.jpg" alt="行在宽处">
//		// </div>
//		// <div class=\"object-img\">[\s]+<img src=\"([\S]+)\"[^>]+>
//		String regexp = "<div class=\"object-img\">[\\s]+<img src=\"([\\S]+)\"[^>]+>";
//		url = this.get(content, regexp);
//		return url;
//	}
//
//	/**
//	 * 取得作者
//	 * 
//	 * @param content
//	 * @return
//	 * @Desc:
//	 * @Author:haipenge
//	 * @Date:2016年8月2日 上午9:52:56
//	 */
//	private String getAuthor(String content) {
//		String regexp = "<li><b>本书作者：<\\/b>[\\s]+<span><a[^>]+>([\\s\\S]*?)<\\/a><\\/span>[\\s]*?<\\/li>";
//		String author = this.get(content, regexp);
//		author=HtmlUtil.getInstance().replaceAll(author);
//		return author;
//	}
//
//	/**
//	 * 取得所属标签
//	 * 
//	 * @param content
//	 * @return
//	 * @Desc:
//	 * @Author:haipenge
//	 * @Date:2016年8月2日 上午9:57:27
//	 */
//	private String[] getTags(String content) {
//		String[] tags = null;
//		List<String> items = new ArrayList<String>(0);
//		String regexp = "<li><b>标签：<\\/b>([\\S\\s]*?)<\\/li>";
//		String spans = this.get(content, regexp);
//		if (StringUtils.isNotEmpty(spans)) {
//			regexp = "<span><a[^>]*?>([\\S\\s]*?)<\\/a><\\/span>";
//			try {
//				List<Map<String, String>> matchers = RegexpUtil.match(spans, regexp);
//				if (CollectionUtils.isNotEmpty(matchers)) {
//					for (Map<String, String> map : matchers) {
//						items.add(StringUtils.trim(map.get("1")));
//					}
//				}
//			} catch (Exception e) {
//				logger.error(">>FaceYe Throws Exception:", e);
//			}
//			if (CollectionUtils.isNotEmpty(items)) {
//				tags = items.toArray(new String[items.size()]);
//			}
//		}
//		return tags;
//	}
//
//	private String get(String content, String regexp) {
//		String res = "";
//		try {
//			List<Map<String, String>> matchers = RegexpUtil.match(content, regexp);
//			if (CollectionUtils.isNotEmpty(matchers)) {
//				res = matchers.get(0).get("1");
//			}
//		} catch (Exception e) {
//			logger.error(">>FaceYe Throws Exception:", e);
//		}
//		return res;
//	}
//
//	/**
//	 * 取得下载链接map[name,url]
//	 * 
//	 * @param content
//	 * @return
//	 * @Desc:
//	 * @Author:haipenge
//	 * @Date:2016年8月2日 上午10:16:10
//	 */
//	private List<Map<String, String>> getDownloadResource(String content) {
//		List<Map<String, String>> result = new ArrayList<Map<String, String>>(0);
//		String regexp = "<ul class=\"files\">([\\S\\s]*?)<\\/ul>";
//		String files = this.get(content, regexp);
//		if (StringUtils.isNotEmpty(files)) {
//			regexp = "<a href=\"([\\S\\s]*?)\" target=\"_blank\">([\\S\\s^>]*?)<span>([\\S\\s]*?)<\\/span>[^<]*?<\\/a>";
//			List<Map<String, String>> matchers;
//			try {
//				matchers = RegexpUtil.match(files, regexp);
//				if (CollectionUtils.isNotEmpty(matchers)) {
//					for (Map<String, String> map : matchers) {
//						Map<String, String> item = new HashMap();
//						String url = MapUtils.getString(map, "1");
//						String name = MapUtils.getString(map, "2");
//						name = StringUtils.trim(name);
//						String size = MapUtils.getString(map, "3");
//						size = StringUtils.replace(size, "(", "");
//						size = StringUtils.replace(size, ")", "");
//						logger.debug(">>FaceYe -> kankandou -> kindle file is:" + url + "," + name + ",size:" + size);
//						item.put("url", url);
//						item.put("name", name);
//						item.put("size", size);
//						result.add(item);
//					}
//				}
//			} catch (Exception e) {
//				logger.error(">>FaceYe Throws Exception:", e);
//			}
//
//		}
//
//		return result;
//	}
//
//	/**
//	 * 取得内容简介
//	 * 
//	 * @param content
//	 * @return
//	 * @Desc:
//	 * @Author:haipenge
//	 * @Date:2016年8月2日 上午10:35:20
//	 */
//	private String getContentValidity(String content) {
//		String res = "";
//		String regexp = "<div id=\"detail-tag-id-3\"[^>]*?>([\\S\\s]*?)<\\/div><\\/div><\\/div>";
//		res = this.get(content, regexp);
//		if (StringUtils.isNotEmpty(res)) {
//			res = HtmlUtil.getInstance().replaceHtml(res);
//			res = StringUtils.replace(res, "内容简介", "");
//		}
//		return res;
//	}
//
//	/**
//	 * 取得作者简介
//	 * 
//	 * @param content
//	 * @return
//	 * @Desc:
//	 * @Author:haipenge
//	 * @Date:2016年8月2日 上午10:39:21
//	 */
//	private String getAuthorProfile(String content) {
//		String res = "";
//		String regexp = "<div id=\"detail-tag-id-4\"[^>]*?>([\\S\\s]*?)<\\/div><\\/div><\\/div>";
//		res = this.get(content, regexp);
//		if (StringUtils.isNotEmpty(res)) {
//			res = HtmlUtil.getInstance().replaceHtml(res);
//			res = StringUtils.replace(res, "作者简介", "");
//		}
//		return res;
//	}
//
//	/**
//	 * 取得目录
//	 * 
//	 * @param content
//	 * @return
//	 * @Desc:
//	 * @Author:haipenge
//	 * @Date:2016年8月2日 上午10:40:15
//	 */
//	private String getCatalog(String content) {
//		String res = "";
//		String regexp = "<div id=\"detail-tag-id-6\"[^>]*?>([\\S\\s]*?)<\\/div><\\/div><\\/div>";
//		res = this.get(content, regexp);
//		if (StringUtils.isNotEmpty(res)) {
//			res = HtmlUtil.getInstance().replaceHtml(res);
//			res = StringUtils.replace(res, "目录", "");
//		}
//		return res;
//	}
//
//	/**
//	 * 取得书摘
//	 * 
//	 * @param content
//	 * @return
//	 * @Desc:
//	 * @Author:haipenge
//	 * @Date:2016年8月2日 上午10:41:14
//	 */
//	private String getDigest(String content) {
//		String res = "";
//		String regexp = "<div id=\"detail-tag-id-7\"[^>]*?>([\\S\\s]*?)<\\/div><\\/div><\\/div>";
//		res = this.get(content, regexp);
//		if (StringUtils.isNotEmpty(res)) {
//			res = HtmlUtil.getInstance().replaceHtml(res);
//			res = StringUtils.replace(res, "精彩书摘", "");
//		}
//		return res;
//	}
//
//	/**
//	 * 取得分类
//	 * 
//	 * @param content
//	 * @return
//	 * @Desc:
//	 * @Author:haipenge
//	 * @Date:2016年8月2日 下午9:16:38
//	 */
//	private String getBookCategoryName(String content) {
//		// <p class="side-o-cate">分类：<a href="http://kankandou.com/book/经管.html">经管</a></p>
//		String regexp = "<p class=\"side-o-cate\">分类：<a href=\"[^>]*?\">([\\S\\s]*?)<\\/a><\\/p>";
//		String res = this.get(content, regexp);
//		return res;
//	}
//
//	/**
//	 * 取得资源简介
//	 * 
//	 * @param content
//	 * @return
//	 * @Desc:
//	 * @Author:haipenge
//	 * @Date:2016年8月3日 上午11:20:18
//	 */
//	private String getObjectContent(String content) {
//		String regexp = "<div class=\"object-content\">([\\S\\s]*?)<\\/div>";
//		String res = this.get(content, regexp);
//		res=HtmlUtil.getInstance().replaceHtml(res);
//		res = StringUtils.replace(res, "编辑推荐", "");
//		return res;
//	}

//}

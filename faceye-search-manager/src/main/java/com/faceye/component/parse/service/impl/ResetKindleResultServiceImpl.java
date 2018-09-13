package com.faceye.component.parse.service.impl;

//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Service;
//
//import com.faceye.component.book.entity.Author;
//import com.faceye.component.book.entity.Book;
//import com.faceye.component.book.service.AuthorService;
//import com.faceye.component.book.service.BookService;
//import com.faceye.component.parse.service.ResetKindleResultService;
//import com.faceye.component.parse.util.HtmlUtil;
//
//@Service
//public class ResetKindleResultServiceImpl implements ResetKindleResultService {
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	@Autowired
//	private BookService bookService = null;
//	@Autowired
//	private AuthorService authorService = null;
//
//	@Override
//	public void reset() {
////		this.resetBook();
//		this.resetAuthor();
//	}
//
//	/**
//	 * 重置图书信息
//	 * 
//	 * @Desc:
//	 * @Author:haipenge
//	 * @Date:2016年8月12日 下午2:35:04
//	 */
//	private void resetBook() {
//		int page = 1;
//		Page<Book> books = this.bookService.getPage(null, page, 100);
//		while (books != null && CollectionUtils.isNotEmpty(books.getContent())) {
//			for (Book book : books.getContent()) {
//				String imgUrl = book.getImgUrl();
//				if(StringUtils.indexOf(imgUrl, "http://image.faceye.net")!=-1){
//					imgUrl=StringUtils.replace(imgUrl, "http://image.faceye.net", "");
//				}
//				if (StringUtils.indexOf(imgUrl, "http:/image.faceye.net") != -1) {
//					imgUrl = StringUtils.replace(imgUrl, "http:/image.faceye.net", "");
//					book.setImgUrl(imgUrl);
//					this.bookService.save(book);
//				}
//				if (StringUtils.indexOf(imgUrl, "//") != -1) {
//					imgUrl = StringUtils.replace(imgUrl, "//", "/");
//					book.setImgUrl(imgUrl);
//					this.bookService.save(book);
//				}
//			}
//			logger.debug(">>FaceYe --> Reset book, page is:" + page);
//			page++;
//			try {
//				Thread.sleep(3000L);
//			} catch (InterruptedException e) {
//				logger.error(">>FaceYe Throws Exception:",e);
//			}
//			books = this.bookService.getPage(null, page, 100);
//		}
//	}
//
//	/**
//	 * 重置作者信息
//	 * 
//	 * @Desc:
//	 * @Author:haipenge
//	 * @Date:2016年8月12日 下午2:39:16
//	 */
//	private void resetAuthor() {
//		int page = 1;
//		Page<Author> authors = this.authorService.getPage(null, page, 100);
//		while (authors != null && CollectionUtils.isNotEmpty(authors.getContent())) {
//			for (Author author : authors.getContent()) {
//				String name = author.getName();
//				if (StringUtils.indexOf(name, "<") != -1) {
//					name = HtmlUtil.getInstance().replaceAll(name);
//					author.setName(name);
//					this.authorService.save(author);
//				}
//			}
//			logger.debug(">>FaceYe --> reset author,page is :" + page);
//			page++;
//			try {
//				Thread.sleep(3000L);
//			} catch (InterruptedException e) {
//				logger.error(">>FaceYe Throws Exception:",e);
//			}
//			authors = this.authorService.getPage(null, page, 100);
//		}
//	}
//
//}

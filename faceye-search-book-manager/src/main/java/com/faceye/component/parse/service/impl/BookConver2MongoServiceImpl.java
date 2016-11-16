package com.faceye.component.parse.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.MySQL2MongoService;
import com.faceye.component.search.entity.Book;
import com.faceye.component.search.entity.Category;
import com.faceye.component.search.entity.Section;
import com.faceye.component.search.service.BookService;
import com.faceye.component.search.service.CategoryService;
import com.faceye.component.search.service.SectionService;
import com.faceye.feature.service.SequenceService;
import com.faceye.feature.util.bean.BeanContextUtil;

/**
 * 将小说从mysql转存到Mongo
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年11月6日
 */
@Service("bookConver2MongoService")
public class BookConver2MongoServiceImpl implements MySQL2MongoService {
    
	private Logger logger=org.slf4j.LoggerFactory.getLogger(getClass());
	@Autowired
	private SequenceService sequenceService=null;
	
	@Override
	public void conver() {
		logger.debug(">>FaceYe start 2 conver novel form mysql 2 mongo");
		this.converCategory();
		this.converBook();
		this.converSection();
	}
	
	/**
	 * 转化分类
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年11月6日
	 */
	private void converCategory(){
		CategoryService categoryService=BeanContextUtil.getInstance().getBean(CategoryService.class);
		List<Category> allCategories=categoryService.getAll();
	    logger.debug(">>FaceYe --> conver category 2 mongo now.");
		com.faceye.component.book.service.CategoryService mCategoryService=BeanContextUtil.getInstance().getBean(com.faceye.component.book.service.CategoryService.class);
		if(CollectionUtils.isNotEmpty(allCategories)){
			for(Category category:allCategories){
				logger.debug(">>FaceYe--> conver "+category.getName()+" 2 mongo now.");
				com.faceye.component.book.doc.Category mCategory=new com.faceye.component.book.doc.Category();
				mCategory.setId(category.getId());
				mCategory.setName(category.getName());
				mCategory.setOrderIndex(category.getOrderIndex());
				mCategoryService.save(mCategory);
			}
		}
		logger.debug(">>FaceYe -->,finish category conver.");
	}
	
	/**
	 * 转化小说
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年11月6日
	 */
	private void converBook(){
		logger.debug(">>FaceYe --> conver book 2 mongo now...");
		BookService bookService=BeanContextUtil.getInstance().getBean(BookService.class);
		List<Book> allBooks=bookService.getAll();
		if(CollectionUtils.isNotEmpty(allBooks)){
			com.faceye.component.book.service.BookService mBookService =BeanContextUtil.getInstance().getBean(com.faceye.component.book.service.BookService.class);
			for(Book book: allBooks){
				logger.debug(">>FaceYe --> conver "+book.getName()+" 2 mongo now.");
				com.faceye.component.book.doc.Book mBook=new com.faceye.component.book.doc.Book();
				mBook.setId(book.getId());
				mBook.setAuthor(book.getAuthor());
				mBook.setName(book.getName());
				mBook.setCategoryId(book.getCategory()==null?null:book.getCategory().getId());
				mBook.setCategoryName(book.getCategory()==null?null:book.getCategory().getName());
				mBook.setContent(book.getContent());
				mBook.setPic(book.getPic());
				mBookService.save(mBook);
			}
		}
		logger.debug(">>FaceYe --> finish conver book 2 mongo.");
	}
	/**
	 * 转化章节
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年11月6日
	 */
	private void converSection(){
		
		logger.debug(">>FaceYe --> start 2 conver section 2 mongo....");
		SectionService sectionService=BeanContextUtil.getInstance().getBean(SectionService.class);
		com.faceye.component.book.service.SectionService mSectionService =BeanContextUtil.getInstance().getBean(com.faceye.component.book.service.SectionService.class);
		int page=1,size=500;
		Page<Section> sections=sectionService.getPage(null, page, size);
		while(sections!=null&& CollectionUtils.isNotEmpty(sections.getContent()) ){
			logger.debug(">>FaceYe --> conver section 2 mongo now.page is:"+page);
			List mSectionList=new ArrayList();
			for(Section section: sections.getContent()){
				com.faceye.component.book.doc.Section mSection =new com.faceye.component.book.doc.Section();
				mSection.setId(section.getId());
				mSection.setBookId(section.getBook()==null?null:section.getBook().getId());
				mSection.setBookName(section.getBook()==null?null:section.getBook().getName());
				mSection.setContent(section.getContent());
				mSection.setCreateDate(section.getCreateDate());
				mSection.setIndexNum(section.getIndexNum());
				mSection.setName(section.getName());
				mSectionList.add(mSection);
			}
			mSectionService.save(mSectionList);
			page=page+1;
			sections=sectionService.getPage(null, page, size);
		}
		logger.debug(">>FaceYe --> finish conver section 2 mongo now.");
		
	}
}

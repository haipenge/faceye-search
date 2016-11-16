package com.faceye.component.book.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.faceye.component.book.doc.Book;
import com.faceye.component.book.repository.mongo.BookRepository;
import com.faceye.component.book.service.BookService;
import com.faceye.feature.service.SequenceService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;

@Service("mBookService")
public class BookServiceImpl extends BaseMongoServiceImpl<Book, Long, BookRepository> implements BookService {
	@Autowired
    private SequenceService sequenceService=null;
	@Autowired
	public BookServiceImpl(BookRepository dao) {
		super(dao);
	}
	
	
}/**@generate-service-source@**/

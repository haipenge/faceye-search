package com.faceye.component.search.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.faceye.component.search.entity.Book;

import org.springframework.transaction.annotation.Transactional;

import com.faceye.component.search.repository.jpa.BookRepository;
import com.faceye.feature.repository.SearchFilter;
import com.faceye.feature.repository.jpa.DynamicSpecifications;
import com.faceye.component.search.service.BookService;
import com.faceye.feature.service.impl.BaseServiceImpl;
 

@Service
@Transactional
public class BookServiceImpl extends BaseServiceImpl<Book, Long, BookRepository> implements BookService {

	@Autowired
	public BookServiceImpl(BookRepository dao) {
		super(dao);
	}

	@Override
	public Book getBookByName(String name) {
		return this.dao.getBookByName(name);
	}
	
	
}/**@generate-service-source@**/

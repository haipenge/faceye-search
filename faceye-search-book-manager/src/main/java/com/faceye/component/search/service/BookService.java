package com.faceye.component.search.service;

import com.faceye.component.search.entity.Book;
import com.faceye.feature.service.BaseService;

public interface BookService extends BaseService<Book,Long>{
	public Book getBookByName(String name);
	
}/**@generate-service-source@**/

package com.faceye.component.book.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.book.doc.Category;
import com.faceye.component.book.repository.mongo.CategoryRepository;
import com.faceye.component.book.service.CategoryService;
import com.faceye.feature.service.SequenceService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;

@Service("mCategoryService")
public class CategoryServiceImpl extends BaseMongoServiceImpl<Category, Long, CategoryRepository> implements CategoryService {
	@Autowired
    private SequenceService sequenceService=null;
	@Autowired
	public CategoryServiceImpl(CategoryRepository dao) {
		super(dao);
	}
	
	
}/**@generate-service-source@**/

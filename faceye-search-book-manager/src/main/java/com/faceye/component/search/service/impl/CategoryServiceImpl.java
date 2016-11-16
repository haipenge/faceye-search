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

import com.faceye.component.search.entity.Category;

import org.springframework.transaction.annotation.Transactional;

import com.faceye.component.search.repository.jpa.CategoryRepository;
import com.faceye.feature.repository.SearchFilter;
import com.faceye.feature.repository.jpa.DynamicSpecifications;
import com.faceye.component.search.service.CategoryService;
import com.faceye.feature.service.impl.BaseServiceImpl;
import com.faceye.feature.util.ServiceException;

@Service
@Transactional
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long, CategoryRepository> implements CategoryService {

	@Autowired
	public CategoryServiceImpl(CategoryRepository dao) {
		super(dao);
	}

	@Override
	public Category getCategoryByName(String name) {
		return dao.getCategoryByName(name);
	}
	
	
}/**@generate-service-source@**/

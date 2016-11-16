package com.faceye.component.search.service;

import com.faceye.component.search.entity.Category;
import com.faceye.feature.service.BaseService;

public interface CategoryService extends BaseService<Category,Long>{

	public Category getCategoryByName(String name);
}/**@generate-service-source@**/

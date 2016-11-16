package com.faceye.feature.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.faceye.feature.entity.Example;
import com.faceye.feature.repository.DynamicSpecifications;
import com.faceye.feature.repository.ExampleRepository;
import com.faceye.feature.repository.SearchFilter;
import com.faceye.feature.service.ExampleService;
import com.faceye.feature.util.ServiceException;

@Service
public class ExampleServiceImpl extends BaseServiceImpl<Example, Long, ExampleRepository> implements ExampleService {

	@Autowired
	public ExampleServiceImpl(ExampleRepository dao) {
		super(dao);
	}
	/**
	 * 分页查询，如果size＝0,则按条件查询全部数据
	 */
	@Override
	public Page<Example> getPage(Map<String, Object> searchParams, int page, int size) throws ServiceException {
		Specification<Example> specification = null;
		Page<Example> result = null;
		Class clazz = Example.class;
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		specification = DynamicSpecifications.bySearchFilter(filters.values(), clazz);
		if (size != 0) {
			Pageable pageable = new PageRequest(page, size);
			result = dao.findAll(specification, pageable);
		} else {
			List<Example> items = dao.findAll(specification);
			result = new PageImpl<Example>(items);
		}
		return result;
	}
}

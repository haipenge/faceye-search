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

import com.faceye.component.search.entity.Section;

import org.springframework.transaction.annotation.Transactional;

import com.faceye.component.search.repository.jpa.SectionRepository;
import com.faceye.feature.repository.SearchFilter;
import com.faceye.feature.repository.jpa.DynamicSpecifications;
import com.faceye.component.search.service.SectionService;
import com.faceye.feature.service.impl.BaseServiceImpl;
import com.faceye.feature.util.ServiceException;

@Service
public class SectionServiceImpl extends BaseServiceImpl<Section, Long, SectionRepository> implements SectionService {

	@Autowired
	public SectionServiceImpl(SectionRepository dao) {
		super(dao);
	}
	
	
}/**@generate-service-source@**/

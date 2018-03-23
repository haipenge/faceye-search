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

import com.faceye.component.search.doc.Subject;
import com.faceye.component.search.repository.mongo.SubjectRepository;
import com.faceye.component.search.service.SubjectService;
import com.faceye.feature.service.impl.BaseServiceImpl;
 
import com.faceye.feature.service.impl.BaseMongoServiceImpl;

@Service
public class SubjectServiceImpl extends BaseMongoServiceImpl<Subject, Long, SubjectRepository> implements SubjectService {

	@Autowired
	public SubjectServiceImpl(SubjectRepository dao) {
		super(dao);
	}

	@Override
	public Subject getSubjectByAlias(String alias) {
		return this.dao.getSubjectByAlias(alias);
	}
	
	
}/**@generate-service-source@**/

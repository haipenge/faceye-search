package com.faceye.component.book.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.faceye.component.book.doc.Section;
import com.faceye.component.book.repository.mongo.SectionRepository;
import com.faceye.component.book.service.SectionService;
import com.faceye.feature.service.SequenceService;
import com.faceye.feature.service.impl.BaseServiceImpl;
 
import com.faceye.feature.service.impl.BaseMongoServiceImpl;

@Service("mSectionService")
public class SectionServiceImpl extends BaseMongoServiceImpl<Section, Long, SectionRepository> implements SectionService {
	@Autowired
    private SequenceService sequenceService=null;
	@Autowired
	public SectionServiceImpl(SectionRepository dao) {
		super(dao);
	}
	
	
}/**@generate-service-source@**/

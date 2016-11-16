package com.faceye.component.search.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.search.doc.RequestRecord;
import com.faceye.component.search.repository.mongo.RequestRecordRepository;
import com.faceye.component.search.service.RequestRecordService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;

@Service
public class RequestRecordServiceImpl extends BaseMongoServiceImpl<RequestRecord, Long, RequestRecordRepository> implements RequestRecordService {

	@Autowired
	public RequestRecordServiceImpl(RequestRecordRepository dao) {
		super(dao);
	}
	
	
}/**@generate-service-source@**/

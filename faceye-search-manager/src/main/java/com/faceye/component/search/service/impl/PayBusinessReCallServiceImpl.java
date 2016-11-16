package com.faceye.component.search.service.impl;

import org.springframework.stereotype.Service;

import com.faceye.component.weixin.service.pay.PayBusinessReCallService;
import com.faceye.component.weixin.service.pay.model.PayProductInfo;

@Service
public class PayBusinessReCallServiceImpl implements PayBusinessReCallService {

	@Override
	public PayProductInfo buildPayProductInfo(String productOrOrderId) {
		return null;
	}

	@Override
	public boolean pay(String productOrOrderId) {
		return false;
	}

}

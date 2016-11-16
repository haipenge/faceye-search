package com.faceye.test.component.security.repository;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.faceye.component.security.repository.AuthRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;

public class AuthRepositoryTestCase  extends BaseRepositoryTestCase{

	@Autowired
	private AuthRepository authRepository=null;
	@Test
	public void testGetUsers() throws Exception{
		List items=this.authRepository.getUsers();
		Assert.isTrue(CollectionUtils.isNotEmpty(items));
	}
}

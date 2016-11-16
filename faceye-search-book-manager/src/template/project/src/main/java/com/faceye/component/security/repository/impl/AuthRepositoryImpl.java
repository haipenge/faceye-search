package com.faceye.component.security.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.faceye.component.security.repository.AuthRepository;

@Repository
public class AuthRepositoryImpl implements AuthRepository {

	@PersistenceContext
	private EntityManager entityManager = null;
   
	public List<?> getUsers() {
		List items = null;
		StringBuilder sql = new StringBuilder("select user.id as userId,role.id as roleId,user.name as username from security_user user,security_role role,security_user_role user_role where 1=1 and user.id=user_role.user_id and role.id =user_role.role_id");
		items = entityManager.createNativeQuery(sql.toString(),"Auth").getResultList();
		return items;
	}
	
	public class Auth{
		private Long userId;
		private Long roleId;
		private String username;
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public Long getRoleId() {
			return roleId;
		}
		public void setRoleId(Long roleId) {
			this.roleId = roleId;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		
	}

}

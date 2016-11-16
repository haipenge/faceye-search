package com.faceye.component.security.repository.impl;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Auth {
	@Id
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

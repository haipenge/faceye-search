package com.faceye.component.push.service.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.faceye.feature.util.MathUtil;

/**
 * 推送用户构造器
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年1月27日
 */
public class UserBuilder {
	private Logger logger = LoggerFactory.getLogger(UserBuilder.class);
	private static Map<String, UserBuilder> CONTAINER = null;
	private List<User> users = null;

	// private static class UserBuilderHolder{
	// private static UserBuilder INSTANCE=new UserBuilder();
	// }
	private UserBuilder() {
		if (CollectionUtils.isEmpty(users)) {
			users = new ArrayList<User>(0);
		}
	}

	public synchronized static UserBuilder getInstance(String key) {
		UserBuilder userBuilder = null;
		if (MapUtils.isEmpty(CONTAINER)) {
			CONTAINER = new HashMap<String, UserBuilder>();
		}
		if (CONTAINER.containsKey(key)) {
			userBuilder = CONTAINER.get(key);
		} else {
			userBuilder = new UserBuilder();
			CONTAINER.put(key, userBuilder);
		}
		return userBuilder;
	}

	/**
	 * 构建用户
	 * @todo
	 * @param username
	 * @param password
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月27日
	 */
	public void builderUser(String username, String password,String id) {
		boolean isExist = this.isUserExist(username);
		if (!isExist) {
			User user = new User();
			user.setPassword(password);
			user.setUsername(username);
			user.setId(id);
			users.add(user);
		}
	}

	/**
	 * 用户是否已存在
	 * @todo
	 * @param username
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月27日
	 */
	private boolean isUserExist(String username) {
		boolean isExist = false;
		if (StringUtils.isNotEmpty(username)) {
			for (User user : users) {
				if (StringUtils.equals(user.getUsername(), username)) {
					isExist = true;
					break;
				}
			}
		}
		return isExist;
	}

	/**
	 * 查找用户
	 * @todo
	 * @param username
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月27日
	 */
	public User getUser(String username) {
		User user = null;
		for (User u : users) {
			if (StringUtils.equals(u.getUsername(), username)) {
				user = u;
				break;
			}
		}
		return user;
	}

	/**
	 * 随机获取用户
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月27日
	 */
	public User getRandUser() {
		User user = null;
		if (CollectionUtils.isNotEmpty(users)) {
			if (users.size() >= 2) {
				int size = this.users.size();
				int rand = MathUtil.getRandInt(0, size);
				if (rand < users.size()) {
					user = users.get(rand);
				} else {
					user = users.get(0);
				}
			} else {
				user = users.get(0);
			}
		}
		if (null != user) {
			logger.debug(">>FaceYe --> user is:" + user.getUsername());
		} else {
			logger.debug(">>FaceYe --> user is null.");
		}
		return user;
	}
}

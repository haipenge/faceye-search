package com.faceye.component.push.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.faceye.component.push.service.PushService;
import com.faceye.component.push.service.model.PushObject;
import com.faceye.component.push.service.model.User;
import com.faceye.component.push.service.model.UserBuilder;
import com.faceye.component.push.util.PushHttp;
import com.faceye.feature.util.SHAUtils;

/**
 * 51CTO推送服务
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年1月29日
 */
@Service("ctoPushService")
public class CTOPushServiceImpl extends BasePushServiceImpl implements PushService {

	private PushHttp pushHttp = PushHttp.getInstance(CTOPushServiceImpl.class.getName());

	@Override
	public boolean doLogin() {
		boolean res = false;
		String loginUrl = "http://home.51cto.com/index.php?s=/Index/doLogin";
		Map params = new HashMap();
		User user = this.getUser();
		params.put("email", user.getUsername());
		params.put("passwd", user.getPassword());
		params.put("reback", "http://blog.51cto.com");
		this.pushHttp.post(loginUrl, "gb2312", params);
		res = this.isLogin();
		return res;
	}

	@Override
	public void push(PushObject pushObject) {
		boolean isLogin = this.isLogin();
		if (!isLogin) {
			isLogin = this.doLogin();
		}

		String url = "http://blog.51cto.com/user_index.php?action=addblog_new";
		// enctype="multipart/form-data"

		if (isLogin) {
			logger.debug(">>FaceYe --> login success.");
			this.pushHttp.get("http://home.51cto.com/index.php?s=/space/9885981", "gb2312");
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				logger.error(">>FaceYe throws Exception: --->" + e.toString());
			}
			this.pushHttp.get("http://home.51cto.com/index.php?s=/space/9885981", "gb2312");

			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				logger.error(">>FaceYe throws Exception: --->" + e.toString());
			}
			this.pushHttp.get("http://blog.51cto.com/addblog.php", "gb2312");
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				logger.error(">>FaceYe throws Exception: --->" + e.toString());
			}
			Map params = new HashMap();
			/**
			 * 是否原创
			 * -1 请选择
			 * 0 原创
			 * 1 转载
			 * 2 翻译
			 */
			params.put("isorg", "1");
			// 标题
			params.put("atc_title", pushObject.getName());
			// 内容
			params.put("atc_content", pushObject.getContent());
			// 上传文件
			// tags 不能空
			params.put("tags", "学习记录");
			/**
			 * 系统分类
			 * 网络技术
			*路由交换
			*安 全
			*系统软件
			*操作系统
			*服务器&存储
			*开发技术
			*移动开发
			*Web开发
			*Java
			*.Net
			*C/C++
			*数据库
			*软件工程&测试
			*IT管理
			*项目管理
			*管理软件
			*IT业界
			*IT职场
			*休 闲
			*情 感
			*数码影音
			 */
			params.put("classname", "开发技术");

			// 版权声明，0，无
			params.put("copy", "0");
			// 是否公开,0公开，1隐藏
			params.put("ishide", "0");
			// 摘要
			params.put("atc_info", "");
			// 是否允许评论，0，允许，1，禁
			params.put("plc", "0");
			// 以下为隐藏属性
			params.put("drf_id", "110639");
			
			params.put("step", "2");
			params.put("job", "add");
			params.put("pusher", "");
			
			params.put("uploadplus", "");
			params.put("cidpre", "");
			params.put("tid", "");
			params.put("dirid", "0");
			
			params.put("Input22", "");
			params.put("Input223", "");
			params.put("Input22", "");
			params.put("Input223", "");
			params.put("Input22", "");
			params.put("Input223", "");
			String pushResult = this.pushHttp.post(url, "gb2312", params);
			logger.debug(">>FaceYe --> push result is :\n" + pushResult);
		}

	}

	private boolean isLogin() {
		boolean res = false;
		String url = "http://blog.51cto.com";
		String pageResult = this.pushHttp.get(url, "gb2312");
		// logger.debug(">>FaceYe --> page result is:" + pageResult);
		if (StringUtils.isNotEmpty(pageResult) && StringUtils.indexOf(pageResult, "登录我的博客") != -1) {
			res = true;
		}

		return res;
	}

	private User getUser() {
		User user = null;
		UserBuilder userBuilder = UserBuilder.getInstance(CTOPushServiceImpl.class.getName());
		user = userBuilder.getRandUser();
		if (null == user) {
			// userBuilder.builderUser("82676683@qq.com", SHAUtils.sha("ecsunchina147"),"2311406");
			userBuilder.builderUser("1491129716@qq.com", SHAUtils.sha("147258"), "");
			user = userBuilder.getRandUser();
		}
		return user;
	}

}

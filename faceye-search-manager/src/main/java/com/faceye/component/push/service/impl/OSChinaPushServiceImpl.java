package com.faceye.component.push.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.push.service.PushService;
import com.faceye.component.push.service.model.PushObject;
import com.faceye.component.push.service.model.User;
import com.faceye.component.push.service.model.UserBuilder;
import com.faceye.component.push.util.PushHttp;
import com.faceye.feature.util.SHAUtils;

@Service("oschinaPushService")
public class OSChinaPushServiceImpl extends BasePushServiceImpl implements PushService {

	private PushHttp pushHttp = PushHttp.getInstance(OSChinaPushServiceImpl.class.getName());
	//1491129716@qq.com
	private String username = "";
	private String password = "ecsunchina147";

	private String loginPostUrl = "https://www.oschina.net/action/user/hash_login";

	@Override
	public boolean doLogin() {
		boolean res = false;
		User user=this.getUser();
		Map<String, String> params = new HashMap<String, String>();
		username=user.getUsername();
		params.put("email", user.getUsername());
		params.put("pwd", user.getPassword());
		params.put("save_login", "1");
		params.put("verifyCode", "");
		String loginResult = pushHttp.post(loginPostUrl, "UTF-8", params);
		logger.debug(">>FaceYe -->Login in Result is \n" + res);
		boolean isLogin = this.isLogin();
		return isLogin;
	}

	/**
	 * 是否已登陆
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月27日
	 */
	private boolean isLogin() {
		boolean isLogin = false;
		String homeUrl = "http://www.oschina.net/";
		String content = pushHttp.get(homeUrl, "utf-8");
		if (StringUtils.isNotEmpty(content) && content.indexOf("haipenge") != -1) {
			isLogin = true;
		}
		return isLogin;
	}

	@Override
	public void push(PushObject pushObject) {
		boolean isLogin=this.isLogin();
		if(!isLogin){
			this.doLogin();
		}
		Map<String, String> params = new HashMap<String, String>();
		Map<String,String>preParams=this.prePushParams();
		if(MapUtils.isNotEmpty(preParams)){
			params.putAll(preParams);
		}
		String submitUrl="http://my.oschina.net/action/blog/save";
		params.put("title", pushObject.getName());
		params.put("content", pushObject.getContent());
		/**
		 * 分类
		 * 3265227 工作日志
		 * 3265228 日常记录
		 * 3265229 转帖
		 */
		params.put("catalog", "3265227");
		/**
		 * 编辑器类型
		 */
		params.put("content_type", "2");
		// 摘要
		params.put("abstracts", "");
		// tags
		params.put("tags", "");
		/**
		 * 博客分类
		 * 0 ＝> 未选择
		 * 428602 => 移动开发
		 * 428612 => 前端开发
		 * 428640 => 服务端开发/管理
		 * 429511 => 游戏开发
		 * 428609 => 编程语言
		 * 428610 => 数据库
		 * 428611 => 企业开发
		 * 428647 => 图像/多媒体
		 * 428613 => 系统运维
		 * 428638 => 软件工程
		 * 428639 => 云计算
		 * 430884 => 开源硬件
		 * 430381 => 其他类型
		 */
		params.put("classification", "430381");
		/**
		 * 文章类型
		 * 1.原创
		 * 4.转贴
		 */
		params.put("type", "1");
		/**
		 * 转帖URL
		 */
		params.put("origin_url","");
		/**
		 * 是否对所有人可见
		 * 0,可见，1，不可见
		 */
		params.put("privacy", "0");
		/**
		 * 是否允许评论
		 * 0,允许
		 * 1,不
		 */
		params.put("deny_comment", "0");
		/**
		 * 是否自动生成目录
		 * 1，生，0，不生
		 */
		params.put("auto_content", "1");
		pushHttp.post(submitUrl, "utf-8", params);
		logger.debug(">>FaceYe -->push result: push article is:"+pushObject.getName()+",username is:"+username+",site is oschina");
	}

	/**
	 * 准备push的参数
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月27日
	 */
	private Map prePushParams() {
		Map params = new HashMap();
		//2311581
		User user =UserBuilder.getInstance(OSChinaPushServiceImpl.class.getName()).getUser(username);
		String url = "http://my.oschina.net/u/"+user.getId()+"/admin/new-blog";
		String content = pushHttp.get(url, "utf-8");
		// <input type='hidden' name='user_code' value='YE54aCKydV7j3pbrUEMAGnV60H0Zs5tt4gZkTmmE'/>
		String regexp = "<input\\stype=\'hidden\'\\sname=\'user_code\'\\svalue=\'([\\W\\w]*?)\'\\/>";
		try {
			List<Map<String, String>> matches = RegexpUtil.match(content, regexp);
			if (CollectionUtils.isNotEmpty(matches)) {
				String value = matches.get(0).values().iterator().next();
				params.put("user_code", value);
			}
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->" + e.toString());
		}
		// <input type='hidden' id='hdn_blog_id' name='draft' value='0'/>
		regexp = "<input\\stype=\'hidden\'\\sid=\'hdn_blog_id\'\\sname=\'draft\'\\svalue=\'([\\W\\w]*?)\'\\/>";
		try {
			List<Map<String, String>> matches = RegexpUtil.match(content, regexp);
			if (CollectionUtils.isNotEmpty(matches)) {
				String value = matches.get(0).values().iterator().next();
				params.put("draft", value);
			}
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->" + e.toString());
		}
		return params;
	}
	
	/**
	 * 随机取得推送用户
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月27日
	 */
	private User getUser(){
		User user=null;
		UserBuilder userBuilder=UserBuilder.getInstance(OSChinaPushServiceImpl.class.getName());
		user =userBuilder.getRandUser();
		if(null==user){
			userBuilder.builderUser("82676683@qq.com", SHAUtils.sha("ecsunchina147"),"2311406");
			userBuilder.builderUser("1491129716@qq.com", SHAUtils.sha("ecsunchina147"),"2311581");
			user=userBuilder.getRandUser();
		}
		return user;
	}
	
	

}

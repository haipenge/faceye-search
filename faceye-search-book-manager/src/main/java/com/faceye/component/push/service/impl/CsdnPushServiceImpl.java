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
import com.faceye.component.push.util.PushHttp;

@Service("csdnPushService")
public class CsdnPushServiceImpl extends BasePushServiceImpl implements PushService {

	private String loginUrl = "https://passport.csdn.net/account/login?from=http://my.csdn.net/my/mycsdn";

	private String postUrl = "https://passport.csdn.net/account/login?from=http://my.csdn.net/my/mycsdn";

	private String homeUrl = "http://my.csdn.net/my/mycsdn";
	
	private PushHttp pushHttp=PushHttp.getInstance(CsdnPushServiceImpl.class.getName());

	@Override
	public boolean doLogin() {
		Map<String,String> dynamicParams=this.getLoginPage();
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "ecsun@sohu.com");
		params.put("password", "ecsunchina");
		params.put("rememberMe", "true");
        if(MapUtils.isNotEmpty(dynamicParams)){
        	params.putAll(dynamicParams);
        }
		//
		//params.put("lt", "LT-34075002-sc1ebTS5QNizySCbfcNMzbFyAYQU96");
		//params.put("execution", "e2s1");
		params.put("_eventId", "submit");
		String loginResultPage = pushHttp.post(postUrl, "utf-8", params);
		logger.debug(">>FaceYe -->Login result page is:" + loginResultPage);
		logger.debug(">>FaceYe -->-------------------------------------------------");
		String afterLoginPage = pushHttp.get(homeUrl, "utf-8");
		logger.debug(">>FaceYe --> after login page is :" + afterLoginPage);
		return StringUtils.isNotEmpty(afterLoginPage) && afterLoginPage.indexOf("发表文章") != -1;
	}
/**
 * 取得登陆页面
 * @todo
 * @return
 * @author:@haipenge
 * haipenge@gmail.com
 * 2015年1月24日
 */
	private Map<String,String> getLoginPage() {
		Map<String, String> res = new HashMap<String, String>();
		String pageContent = pushHttp.get(loginUrl, "utf-8");
		// <input type="hidden" name="lt" value="LT-34191637-SSDND7gRLvLllubaNzf6O7BCCBxZQA" />
		String regexp = "<input\\stype=\"hidden\"\\sname=\"lt\"\\svalue=\"([\\W\\w]*?)\"\\s\\/>";
		try {
			List<Map<String, String>> matchs = RegexpUtil.match(pageContent, regexp);
			if(CollectionUtils.isNotEmpty(matchs)){
				String lt=matchs.get(0).values().iterator().next();
				res.put("lt", lt);
			}
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->" + e.toString());
		}
		
		// <input type="hidden" name="execution" value="e6s1" />
		regexp="<input\\stype=\"hidden\"\\sname=\"execution\"\\svalue=\"([\\W\\w]*?)\"\\s\\/>";
		try {
			List<Map<String, String>> matchs = RegexpUtil.match(pageContent, regexp);
			if(CollectionUtils.isNotEmpty(matchs)){
				String lt=matchs.get(0).values().iterator().next();
				res.put("execution", lt);
			}
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->" + e.toString());
		}
		return res;
	}

	@Override
	public void push(PushObject pushObject) {
		if(null!=pushObject){
			String submitUrl="http://write.blog.csdn.net/postedit";
			Map params=new HashMap();
			params.put("stat", "publish");
//			params.put("isPub", "1");
//			params.put("edit", "1");
			submitUrl+="?edit=1";
			submitUrl+="&isPub=1";
			submitUrl+="&joinblogcontest=undefined";
//			params.put("joinblogcontest", "undefined");
			//生成javascript的随机数
//			params.put("r", ""+Math.random());
			submitUrl+="&r="+Math.random();
			//1-原创,2,转载
			params.put("typ", "2");
			params.put("titl", pushObject.getName());
			params.put("cont",pushObject.getContent());
			params.put("desc", "");
			params.put("tags", "");
			//上传文件名
			params.put("flnm", "");
			/**
			 * 频道分类
			 * 1->移动开发
			 * 14->Web前端
			 * 15->架构设计
			 * 16->编程语言
			 * 17->互联网
			 * 6->数据库
			 * 12->系统运维
			 * 2->云计算
			 * 3->研发管理
			 * 7->综合
			 */
			params.put("chnl", "7");
			/**
			 * 是否允许评论
			 * 1.禁
			 * 2.允许
			 */
			params.put("comm", "2");
			/**
			 * 是否发布到首页，0,1
			 */
			params.put("level", "0");
			params.put("tag2", "");
			//自动保存后的标记
			params.put("artid", "0");
			/**
			 * 验证码
			 */
			params.put("checkcode", "undefined");
			
			params.put("userinfo1", "4");
			String pushResult=this.pushHttp.post(submitUrl, "UTF-8", params);
			logger.debug(">>FaceYe ->:"+pushResult);
		}
	}

}

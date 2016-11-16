package com.faceye.component.push.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.faceye.component.push.service.PushService;
import com.faceye.component.push.service.model.PushObject;
import com.faceye.component.push.util.PushHttp;
/**
 * Cnblogs的推送记录
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年12月27日
 */
@Service("cnblogsPushService")
public class CnblogsPushServiceImpl extends BasePushServiceImpl implements PushService {

	//登陆提交URL
	private String postUrl="http://passport.cnblogs.com/login.aspx?ReturnUrl=http%3a%2f%2fwww.cnblogs.com%2f";
	
	private PushHttp pushHttp=PushHttp.getInstance(CnblogsPushServiceImpl.class.getName());
	@Override
	public boolean doLogin() {
		boolean result=false;
		Map<String,String> params=new HashMap<String,String>();
		params.put("__EVENTTARGET", "");
		params.put("__EVENTARGUMENT", "");
		//??
		params.put("__VIEWSTATE", "/wEPDwUKLTM1MjEzOTU2MGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFC2Noa1JlbWVtYmVy4b/ZXiH+8FthXlmKpjSEgi7XBNU=");
		params.put("__VIEWSTATEGENERATOR", "C2EE9ABB");
		params.put("__EVENTVALIDATION", "/wEdAAUIqCk3Gcmu25zI9fQWqoC7hI6Xi65hwcQ8/QoQCF8JIahXufbhIqPmwKf992GTkd0wq1PKp6+/1yNGng6H71Uxop4oRunf14dz2Zt2+QKDEM3sbzJLySdZoy08+/dzW8VF2on0");
		params.put("tbUserName", "haipenge");
		params.put("tbPassword", "147258");
		params.put("chkRemember", "");
		params.put("btnLogin","登 录");
		String res=pushHttp.post(postUrl, "utf-8", params);
		logger.debug(">>FaceYe login Result is:\n"+res);
		result=this.isLogin();
		return result;
	}
	/***
	 * 检查是否成功登陆
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月22日
	 */
	private boolean isLogin(){
		boolean res=false;
		String url="http://i.cnblogs.com/";
		String page=pushHttp.get(url, "utf-8");
		if (StringUtils.isNotEmpty(page)&&page.indexOf("添加新随笔")!=-1){
			res=true;
		}
		return res;
	}
	
	/**
	 * 推送资源
	 */
	public void push(PushObject pushObject){
		boolean isLogin=this.isLogin();
		if(!isLogin){
			this.doLogin();
		}
		logger.debug(">>FaceYe is cnblogs login success?"+isLogin);
		this.doPush(pushObject);
	}
	/**
	 * 推送资源
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月22日
	 */
	private void doPush(PushObject pushObject){
		String postUrl="http://i.cnblogs.com/EditPosts.aspx?opt=1";
//		String content=this.getContentHeader()+pushObject.getContent()+this.getContentFooter();
		pushObject.setContent(pushObject.getContent());
		Map<String,String> params=new HashMap<String,String>();
		params.put("__VIEWSTATE", __VIEWSTATE);
		params.put("__VIEWSTATEGENERATOR", "FE27D343");
		params.put("Editor$Edit$txbTitle", pushObject.getName());
		params.put("Editor$Edit$EditorBody", pushObject.getContent());
		params.put("Editor$Edit$Advanced$ckbPublished", "on");
		params.put("Editor$Edit$Advanced$chkDisplayHomePage", "on");
		params.put("Editor$Edit$Advanced$chkComments", "on");
		params.put("Editor$Edit$Advanced$chkMainSyndication", "on");
		params.put("Editor$Edit$lkbPost", "发布");
		//为空选项
		params.put("name_site_categroy", "");
		params.put("Editor$Edit$Advanced$chkPinned","");
		params.put("Editor$Edit$Advanced$chkIsOnlyForRegisterUser", "");
		params.put("Editor$Edit$Advanced$txbEntryName", "");
		params.put("Editor$Edit$Advanced$txbExcerpt", "");
		params.put("Editor$Edit$Advanced$txbTag", "");
		params.put("Editor$Edit$Advanced$tbEnryPassword", "");
		String res=this.pushHttp.post(postUrl, "utf-8", params);
		logger.debug(">>FaceYe post 2 cnblogs result is:"+res);
	}
	
	
	/**
	 * 网页内钳标记，未知
	 */
	private String __VIEWSTATE="/wEPDwUKLTg5MDEzODY0MQ8WAh4TVmFsaWRhdGVSZXF1ZXN0TW9kZQIBFgJmD2QWBgIGDxYCHgRUZXh0BUo8bGluayByZWw9InN0eWxlc2hlZXQiIHR5cGU9InRleHQvY3NzIiBocmVmPSIvY3NzL2FkbWluLmNzcz9pZD0yMDE0MDgyMiIvPmQCCA8WAh8BBZ0BPHNjcmlwdCB0eXBlPSJ0ZXh0L2phdmFzY3JpcHQiIHNyYz0iL3NjcmlwdC9qcXVlcnkuY25ibG9ncy50aGlja2JveC5qcyI+PC9zY3JpcHQ+CjxzY3JpcHQgdHlwZT0idGV4dC9qYXZhc2NyaXB0IiBzcmM9Ii9zY3JpcHQvYWRtaW4uanM/aWQ9MjAxNDA4MjIiPjwvc2NyaXB0PmQCCg9kFgICAQ9kFhQCAQ8PFgIeC05hdmlnYXRlVXJsBSBodHRwOi8vd3d3LmNuYmxvZ3MuY29tL2hhaXBlbmdlL2RkAgMPDxYGHgZUYXJnZXRlHwIFIGh0dHA6Ly93d3cuY25ibG9ncy5jb20vaGFpcGVuZ2UvHwEFFOW3peS9nCDnlJ/mtLsg5a625Lq6ZGQCBQ8PFgQfAgUXaHR0cDovL3d3dy5jbmJsb2dzLmNvbS8eCEltYWdlVXJsBS5odHRwOi8vc3RhdGljLmNuYmxvZ3MuY29tL2ltYWdlcy9hZG1pbmxvZ28uZ2lmZGQCDw8WAh4HVmlzaWJsZWdkAiMPZBYCAgEPZBYMAgEPDxYGHglDb2xsYXBzZWRnHgtDb2xsYXBzaWJsZWcfBWhkZAICD2QWBGYPZBYCZg8PFgIfAQUM5re75Yqg6ZqP56yUZGQCAQ9kFhACAQ8PFgQfAmUfBWhkZAIDDw9kFgIeCW9ua2V5ZG93bgUVdGl0bGVfa2V5ZG93bihldmVudCk7ZAIFDxYCHwEFEihUaW55TUNF57yW6L6R5ZmoKWQCCQ9kFgICAQ9kFggCAQ9kFgICAQ9kFgICAQ8QDxYGHg5EYXRhVmFsdWVGaWVsZAUKQ2F0ZWdvcnlJRB4NRGF0YVRleHRGaWVsZAUFVGl0bGUeC18hRGF0YUJvdW5kZ2QQFQAVABQrAwBkZAIDD2QWAgIBD2QWBgIBD2QWAgIBDxYCHghkaXNhYmxlZGRkAgMPFgIfDGRkAgQPFgIfAQU85Y+R5biD6Iez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ZAIPD2QWAmYPFQYENzczNAQ3NzM0AAAENzczNA/lhbbku5bmioDmnK/ljLpkAgkPDxYCHwVoZBYCAgEPZBYCAgMPEGRkFgBkAgsPDxYCHwZoZBYCAgEPZBYOAgcPEA8WAh4HQ2hlY2tlZGhkZGRkAgsPEA8WAh8OaGRkZGQCEA8QDxYCHw5oZGRkZAISDxAPFgIfDmhkZGRkAhQPFgIfBWhkAhYPEGRkFgBkAh8PEA8WAh8OaGRkZGQCDQ8PFgIfAQUG5Y+R5biDZGQCDw8PFgIfAQUM5a2Y5Li66I2J56i/ZGQCFQ9kFgICAQ9kFgICAg8PFgIfAQUSMjAxNS8xLzIyIDEyOjE1OjA2ZGQCBA8WAh8BZWQCBQ8WAh8BBQYxMDg2OTdkAgYPFgIfAQUDODA4ZAIIDxYCHwEFATBkAiUPDxYEHwEFCGhhaXBlbmdlHwIFI2h0dHA6Ly9ob21lLmNuYmxvZ3MuY29tL3UvaGFpcGVuZ2UvZGQCJw8WAh8BBV08YSBocmVmPSJodHRwOi8vc3BhY2UuY25ibG9ncy5jb20vbXNnL3JlY2VudCIgdGFyZ2V0PSJfYmxhbmsiIGlkPSJsbmtfc2l0ZV9tc2ciPuefrea2iOaBrzwvYT5kAikPFgIfAQWdATxhIGhyZWY9Imh0dHA6Ly9wYXNzcG9ydC5jbmJsb2dzLmNvbS9sb2dvdXQuYXNweD9SZXR1cm5VUkw9aHR0cDovL3d3dy5jbmJsb2dzLmNvbS9oYWlwZW5nZS8iIG9uY2xpY2s9InJldHVybiBjb25maXJtKCfnoa7orqTopoHpgIDlh7rnmbvlvZXlkJc/JykiPuazqOmUgDwvYT5kAisPFgIfAQUEMjAxNWQCLQ8PFgQfAgUXaHR0cDovL3d3dy5jbmJsb2dzLmNvbS8fAQUJ5Y2a5a6i5ZutZGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgkFNEVkaXRvciRFZGl0JEFQT3B0aW9ucyRBZHZhbmNlZHBhbmVsMSRja2xDYXRlZ29yaWVzJDAFMEVkaXRvciRFZGl0JEFQT3B0aW9ucyRBUFNpdGVIb21lJGNiSG9tZUNhbmRpZGF0ZQU2RWRpdG9yJEVkaXQkQVBPcHRpb25zJEFQU2l0ZUhvbWUkY2JJc1B1Ymxpc2hUb1NpdGVIb21lBSFFZGl0b3IkRWRpdCRBZHZhbmNlZCRja2JQdWJsaXNoZWQFJ0VkaXRvciRFZGl0JEFkdmFuY2VkJGNoa0Rpc3BsYXlIb21lUGFnZQUgRWRpdG9yJEVkaXQkQWR2YW5jZWQkY2hrQ29tbWVudHMFJ0VkaXRvciRFZGl0JEFkdmFuY2VkJGNoa01haW5TeW5kaWNhdGlvbgUeRWRpdG9yJEVkaXQkQWR2YW5jZWQkY2hrUGlubmVkBS1FZGl0b3IkRWRpdCRBZHZhbmNlZCRjaGtJc09ubHlGb3JSZWdpc3RlclVzZXJquG4OgqAgn5d1nAe1ylSLS2O3EA==";

//	/**
//	 * header
//	 */
//	private String getContentHeader(){
//		StringBuilder sb=new StringBuilder("");
//		sb.append("<a href=\"http://faceye.net\">");
//		sb.append("TEST");
//		sb.append("</a>");
//		return sb.toString();
//	}
//	/**
//	 * 构建footer
//	 * @todo
//	 * @return
//	 * @author:@haipenge
//	 * haipenge@gmail.com
//	 * 2015年1月22日
//	 */
//	private String getContentFooter(){
//		StringBuilder sb=new StringBuilder("");
//		sb.append("<a href=\"http://faceye.net\">");
//		sb.append("TEST");
//		sb.append("</a>");
//		return sb.toString();
//	}
}

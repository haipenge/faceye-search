package com.faceye.component.push.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.faceye.component.push.service.PushService;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.util.URLUtils;
import com.faceye.feature.service.Reporter;
/**
 * 向其它站点推送的Bean工厂管理
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年1月27日
 */
@Service
public class PushServiceFactory implements ApplicationContextAware {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	Reporter reporter = null;
	@Autowired
	private static ApplicationContext applicationContext = null;

	public PushService getPushService(Site site) {

		PushService pushService = null;
		if (null != site) {
			Map<String, PushService> beans = this.getBeans();
			if (MapUtils.isNotEmpty(beans)) {
				String url=site.getUrl();
				Iterator it = beans.keySet().iterator();
				String domainKey=URLUtils.getDomainKey(url);
				while(it.hasNext()){
					String keyOfBean=it.next().toString();
					if(StringUtils.contains(keyOfBean.toLowerCase(), domainKey.toLowerCase())){
						pushService=beans.get(keyOfBean);
						break;
					}
				}
			}
		}
		return pushService;
	}
	

	private Map<String, PushService> getBeans() {
		List<PushService> services = null;
		Map<String, PushService> beans = applicationContext.getBeansOfType(PushService.class);
		return beans;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}

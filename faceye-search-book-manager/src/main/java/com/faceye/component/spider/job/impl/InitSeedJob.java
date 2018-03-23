package com.faceye.component.spider.job.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.faceye.component.spider.service.SiteLinkService;
 

/**
 * 种子资源初始化job
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月22日
 */
@Service
public class InitSeedJob extends BaseJob {

	@Autowired
	@Qualifier("cnBlogsLinkServiceImpl")
	private SiteLinkService cnBlogsLinkService = null;
	@Autowired
	@Qualifier("easouBLinkServiceImpl")
	private SiteLinkService easouBLinkService = null;
	@Autowired
	@Qualifier("OSChinaLinkServiceImpl")
	private SiteLinkService oschinaLinkService = null;
	@Autowired
	@Qualifier("CSDNLinkServiceImpl")
	private SiteLinkService csdnLinkService = null;

	@Autowired
	@Qualifier("ITEyeLinkServiceImpl")
	private SiteLinkService iteyeLinkService=null;
	
	@Autowired
	@Qualifier("w3SchoolLinkServiceImpl")
	private SiteLinkService w3SchoolLinkService=null;
	@Autowired
	@Qualifier("yiibaiLinkServiceImpl")
	private SiteLinkService yiibaiLinkService=null;
	
	@Autowired
	@Qualifier("segmentFaultLinkServiceImpl")
	private SiteLinkService segmentFaultLinkService=null;
	
	@Autowired
	@Qualifier("ctoLinkService")
	private SiteLinkService ctoLinkService=null;
	@Override
	public void run()   {
		this.segmentFaultLinkService.saveInitLinks();
		this.segmentFaultLinkService.reInitLinks();
		// 初始化cnblogs链接资源
		cnBlogsLinkService.saveInitLinks();
		// 重置cnblogs首页，博客园子首页，博客园子精华列表页
		cnBlogsLinkService.reInitLinks();
		// this.easouBLinkService.reInitLinks();
		// 初始化OSChina链接
		this.oschinaLinkService.saveInitLinks();
		this.oschinaLinkService.reInitLinks();
		// 初始化CSDN链接信息
		this.csdnLinkService.saveInitLinks();
		this.csdnLinkService.reInitLinks();
		
		//初始化w3school链接
		//this.w3SchoolLinkService.saveInitLinks();
		//this.w3SchoolLinkService.reInitLinks();
		
		//this.yiibaiLinkService.saveInitLinks();
		
		this.segmentFaultLinkService.saveInitLinks();
		this.segmentFaultLinkService.reInitLinks();
		
		//51CTO
		this.ctoLinkService.saveInitLinks();
		this.ctoLinkService.reInitLinks();
		//初始化iteye 当前无法爬取，不再初始化
//		this.iteyeLinkService.saveInitLinks();
//		this.iteyeLinkService.reInitLinks();
	}

}

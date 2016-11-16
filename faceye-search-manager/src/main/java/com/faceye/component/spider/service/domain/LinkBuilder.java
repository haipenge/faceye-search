package com.faceye.component.spider.service.domain;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 构建Link集合
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年1月1日
 */
public class LinkBuilder {
	private LinkBuilder() {

	}

	/**
	 * Holder
	 * @author @haipenge 
	 * haipenge@gmail.com
	*  Create Date:2015年1月1日
	 */
	private static class LinkBuilderHolder {
		private static LinkBuilder INSTANCE = new LinkBuilder();
	}

	public static LinkBuilder getInstance() {
		return LinkBuilderHolder.INSTANCE;
	}
	/**
	 * 构建待爬取的URL集合
	 * @todo
	 * @param links
	 * @param urls
	 * @param type
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月4日
	 */
	public void build(List<DomainLink> links,List<String> urls,Integer type){
		if(CollectionUtils.isNotEmpty(urls)){
			for(String url:urls){
				this.build(links, url, type);
			}
		}
	}
	/**
	 * 构建DomainLink集合
	 * @todo
	 * @param links
	 * @param url
	 * @param type
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月1日
	 */
	public void build(List<DomainLink> links,String url,Integer type){
		boolean isExist=this.isExist(links, url, type);
		if(!isExist){
			DomainLink link=new DomainLink();
			link.setType(type);
			link.setUrl(url);
			links.add(link);
		}
	}
	
	public void build(List<DomainLink> links,String url,Integer type,String categoryKey){
		boolean isExist=this.isExist(links, url, type);
		if(!isExist){
			DomainLink link=new DomainLink();
			link.setType(type);
			link.setUrl(url);
			link.setCategoryKey(categoryKey);
			links.add(link);
			
		}
	}
	public void build(List<DomainLink> links,String url,Integer type,Boolean isWeixin){
		boolean isExist=this.isExist(links, url, type);
		if(!isExist){
			DomainLink link=new DomainLink();
			link.setType(type);
			link.setUrl(url);
			link.setIsWeixin(isWeixin);
			links.add(link);
		}
	}
	
	
	/**
	 * 检查是否有重复URL
	 * @todo
	 * @param links
	 * @param url
	 * @param type
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月1日
	 */
	private boolean isExist(List<DomainLink> links,String url,Integer type){
		boolean res=Boolean.FALSE;
		if(CollectionUtils.isNotEmpty(links)){
			for(DomainLink link:links){
				//&&link.getType().intValue()==type.intValue()
				if(StringUtils.equals(link.getUrl(),url)){
					res=Boolean.TRUE;
					break;
				}
			}
		}
		return res;
	}
	
	
}

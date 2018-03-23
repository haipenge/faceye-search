package com.faceye.test.component.spider.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.service.LinkService;
import com.faceye.feature.service.PropertyService;
import com.faceye.feature.util.Json;
import com.faceye.feature.util.http.Http;
import com.faceye.test.feature.service.BaseServiceTestCase;
import com.fasterxml.jackson.core.type.TypeReference;

public class LinkServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private LinkService linkService = null;
	@Autowired
	private PropertyService propertyService = null;

	@Test
	public void testIsLinkExist() throws Exception {
		Link link = new Link();

		String url = "http://home.cnblogs.com/blog/page/1/";
		link.setUrl(url);
		link.setCreateDate(new Date());
		link.setIsCrawled(false);
		link.setLastCrawlDate(null);
		link.setMimeType(1);
		link.setSiteId(1L);
		link.setType(1);
		this.linkService.save(link);
		boolean isExist = this.linkService.isLinkExist(url);
		Assert.assertTrue(isExist);
		url = "http://home.cnblogs.com/blog/page/1/ssf";
		isExist = this.linkService.isLinkExist(url);
		Assert.assertTrue(!isExist);
	}

	/**
	 * 测试json互转
	 * @todo
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月27日
	 */
	@Test
	public void test2Json() throws Exception {
		List<Link> links = this.linkService.getPage(null, 1, 10).getContent();
		logger.debug(">>Link size is:" + links.size());
		String json = Json.toJson(links);
		logger.debug("JSON is:" + json);
		List<Link> json2Object = Json.toObject(json, new TypeReference<List<Link>>() {
		});
		Assert.assertTrue(CollectionUtils.isNotEmpty(json2Object));
		Assert.assertTrue(json2Object.get(0) instanceof Link);
	}

	@Test
	public void testGetDistributeLinks() throws Exception {
		Map params = new HashMap();
		params.put("channel", "dev");
		String api = "";
		String host = this.propertyService.get("spider.control.center.host");
		// String channel=this.propertyService.get("spider.crawl.channel.name");
		String remoteLinkDistributeApi = this.propertyService.get("spider.control.center.link.distribute.api");
		api = host + remoteLinkDistributeApi;
		String callResult = Http.getInstance().post(api, "UTF-8", params);
		// String callResult = Http.getInstance().get(api, "UTF-8");
		Assert.assertTrue(StringUtils.isNotEmpty(callResult));
		if (StringUtils.isNotEmpty(callResult)) {
			List<Link> links = Json.toObject(callResult, new TypeReference<List<Link>>() {
			});
			Assert.assertTrue(CollectionUtils.isNotEmpty(links) && links.get(0) instanceof Link);
		}

	}

}

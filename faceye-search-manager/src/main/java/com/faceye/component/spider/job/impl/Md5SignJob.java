package com.faceye.component.spider.job.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.doc.ParseResult;
import com.faceye.component.parse.service.ParseResultService;
import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.service.LinkService;
import com.faceye.feature.util.MD5Utils;
import com.faceye.feature.util.ServiceException;

/**
 * 对Link[url]和parse result [name]进行签名，减少存储空间，提高排重速度
 * 
 * @author haipenge
 *
 */
@Service
public class Md5SignJob extends BaseJob {

	@Autowired
	private LinkService linkService = null;
	@Autowired
	private ParseResultService parseResultService = null;

	@Override
	public void run() throws ServiceException {
//		this.linkSign();
		this.parseResultSign();
	}

	private void linkSign() {
		int page = 1;
		Map params=new HashMap();
		params.put("isEmpty|sign", "");
		Page<Link> links = this.linkService.getPage(params, page, 500);
		while (links != null && CollectionUtils.isNotEmpty(links.getContent())) {
			try {
				for (Link link : links.getContent()) {
					if (StringUtils.isEmpty(link.getSign())) {
						link.setSign(MD5Utils.md5(link.getUrl()));
					}
				}
				this.linkService.save(links.getContent());
				page++;
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					logger.error(">>FaceYe Throws Exception:", e);
				}
				links = this.linkService.getPage(null, page, 500);
			} catch (Exception e) {
				logger.error(">>FaceYe throws Exception->:", e);
			}
		}
	}

	private void parseResultSign() {
		int page = 1;
		Map params=new HashMap();
		params.put("isEmpty|sign", "");
		Page<ParseResult> parseResults = this.parseResultService.getPage(params, page, 500);
		while (parseResults != null && CollectionUtils.isNotEmpty(parseResults.getContent())) {
			try {
				for (ParseResult parseResult : parseResults.getContent()) {
					if (StringUtils.isEmpty(parseResult.getSign())) {
						parseResult.setSign(MD5Utils.md5(parseResult.getName()));
					}
				}
				this.parseResultService.save(parseResults.getContent());
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					logger.error(">>FaceYe Throws Exception:", e);
				}
				logger.debug(">>FaceYe --> Finish parse result sign name loop:"+page);
				page++;
				parseResults = this.parseResultService.getPage(null, page, 500);
			} catch (Exception e) {
				logger.error(">>FaceYe --> Throws Exception,", e);
			}
		}

	}

}

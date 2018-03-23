package com.faceye.component.parse.service.factory;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.document.Document;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.util.FileUtil;
import com.faceye.feature.util.bean.BeanContextUtil;

@Service
public class ParseImpl implements Parse {
	private Logger logger = LoggerFactory.getLogger(ParseImpl.class);

	@Override
	public Document parse(CrawlResult crawlResult, Class[] filters) {
		Document document = new Document();
		if (filters != null) {
			for (Class clazz : filters) {
				try {
					
					document.setCrawlResult(crawlResult);
					String storePath = crawlResult.getStorePath();
					String content = "";
					content = FileUtil.getInstance().read(storePath);
					ParseFilter filter = (ParseFilter)BeanContextUtil.getBean(clazz);
					filter.filter(document, crawlResult, content);
					document.setLinkType(crawlResult.getLinkType());
					document.setLinkUrl(crawlResult.getLinkUrl());
				} catch (IOException e) {
					logger.error(">>FaceYe throws Exception: --->", e);
				}
			}
		}
		return document;
	}

}

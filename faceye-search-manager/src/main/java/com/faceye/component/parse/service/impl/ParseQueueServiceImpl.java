package com.faceye.component.parse.service.impl;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.stereotype.Service;

import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.Link;
import com.faceye.feature.service.impl.MultiQueueServiceImpl;

@Service("parseQueueService")
public class ParseQueueServiceImpl extends MultiQueueServiceImpl<CrawlResult> {
	private Queue queue = null;

	@Override
	public Queue<CrawlResult> getQueue() {
		if (queue == null) {
			if (queue == null) {
				queue = new ConcurrentLinkedQueue<Link>();
			}
		}
		return queue;
	}

}

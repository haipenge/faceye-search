package com.faceye.component.spider.service.impl;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.faceye.component.spider.entity.Link;
import com.faceye.feature.service.MultiQueueService;
import com.faceye.feature.service.impl.MultiQueueServiceImpl;
import com.faceye.feature.service.impl.QueueServiceImpl;

/**
 * 待爬取的链接队列
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月8日
 */
@Service("linkQueueService")
public class LinkQueueServiceImpl extends MultiQueueServiceImpl<Link>  {
	private Map<String, Queue<Link>> queues = null;
	private Queue<Link> queue = null;

	@Override
	public Queue<Link> getQueue() {
		if (queue == null) {
			queue = new ConcurrentLinkedQueue<Link>();
		}
		if (null == queues) {
			queues = new ConcurrentHashMap<String, Queue<Link>>();
		}
		return queue;
	}



}

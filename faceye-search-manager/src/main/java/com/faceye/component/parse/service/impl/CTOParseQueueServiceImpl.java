package com.faceye.component.parse.service.impl;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.stereotype.Service;

import com.faceye.feature.service.QueueService;
import com.faceye.feature.service.impl.QueueServiceImpl;

@Service("ctoParseQueueService")
public class CTOParseQueueServiceImpl extends QueueServiceImpl<Map> implements QueueService<Map> {
    
	private Queue<Map> queue = null;
	@Override
	public Queue<Map> getQueue() {
		if (queue == null) {
			queue = new ConcurrentLinkedQueue<Map>();
		}
		return queue;
	}
}

package com.faceye.component.spider.job;


/**
 * JOB接口，内部定时任务，都将实现本接口
 * @author apple
 *
 */
public interface IJob {

	/**
	 * JOB 任务
	 */
	public void doJob();
}

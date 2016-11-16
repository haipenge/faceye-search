package com.faceye.component.search.service;

import com.faceye.component.search.doc.Movie;
import com.faceye.feature.service.BaseService;

public interface MovieService extends BaseService<Movie,Long>{

	public Movie getMovieByName(String name);
	
	public Movie getMovieByNameAndFrom(String name,String from);
	
	/**
	 * 推送电影到生产环境
	 * @todo
	 * @param id
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年7月19日
	 */
	public void push2ProductEnv(Long id);
	public void doPush();
	
}/**@generate-service-source@**/

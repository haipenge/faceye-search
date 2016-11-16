package com.faceye.component.parse.service;

import java.util.List;
import java.util.Map;

import com.faceye.component.parse.doc.FilterWord;
import com.faceye.component.parse.doc.ParseResult;
import com.faceye.feature.service.BaseService;

public interface ParseResultService extends BaseService<ParseResult, Long> {

	/**
	 * 将解析结果转存到mongo
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年8月10日
	 */
	public void saveParseResult2Mongo();
	
	
	/**
	 * 将解析结果推送到mogo
	 * @todo
	 * @param parseResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月20日
	 */
	public void saveParseResult2Mongo(ParseResult parseResult);
	
	/**
	 * 推送解析结果到mongo
	 * @todo
	 * @param parseResultId
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月20日
	 */
	public void saveParseResult2Mongo(Long parseResultId);
	
	/**
	 * 推送资料到Mongo,是否忽略禁词
	 * @todo
	 * @param parseResultId
	 * @param isIgnoreFilterWord
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月26日
	 */
	public boolean saveParseResult2MongoIgnoreFilterWord(Long parseResultId,Boolean isIgnoreFilterWord);

	/**
	 * 对解析后的数据进行后处理,去掉未处理的<a>,<img>标签等
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年8月10日
	 */
	public void saveRebuildParseResult();

	/**
	 * 敏感词过滤
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年8月15日
	 */
	public void filter();
	
	/**
	 * 重置解析结果,让解析任务重新解析
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月23日
	 */
	public void resetParseResult();
	
	/**
	 * 检查文章中有多少禁词
	 * @todo
	 * @param parseResultId
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月25日
	 */
	public List<FilterWord> testFilterWords(Long parseResultId);
	
	/**
	 * 去重
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月30日
	 */
    public void dedup();
    

	/**
	 * 根据 文章 标题取得文章 
	 * @todo
	 * @param name
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年12月30日
	 */
	public List<ParseResult> getParseResultsByName(String name);
	
	/**
	 * 数据统计查询
	 * @todo
	 * @param searchParams
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月19日
	 */
	public int getCount(Map searchParams);
	
	/**
	 * 自动发布，用于文章定时发布
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年2月9日
	 */
	public void saveAuthPublish();
	
}
/**@generate-service-source@**/

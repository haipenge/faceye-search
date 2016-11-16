package com.faceye.component.spider.repository.jpa;

import org.springframework.stereotype.Repository;

import com.faceye.component.spider.entity.CrawlResult;
import com.faceye.feature.repository.jpa.BaseRepository;
/**
 * CrawlResult 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
@Repository(value="_crawlResultRepository")
public interface CrawlResultRepository extends BaseRepository<CrawlResult,Long> {
	
	
}/**@generate-repository-source@**/

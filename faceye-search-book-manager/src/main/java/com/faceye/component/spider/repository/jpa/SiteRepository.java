package com.faceye.component.spider.repository.jpa;

import org.springframework.stereotype.Repository;

import com.faceye.component.spider.entity.Site;
import com.faceye.feature.repository.jpa.BaseRepository;
/**
 * Site 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
@Repository(value="_siteRepository")
public interface SiteRepository extends BaseRepository<Site,Long> {
	
	public Site getSiteByName(String name);
}/**@generate-repository-source@**/

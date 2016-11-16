package com.faceye.component.spider.repository.jpa;

import org.springframework.stereotype.Repository;

import com.faceye.component.spider.entity.Link;
import com.faceye.feature.repository.jpa.BaseRepository;
/**
 * Link 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
@Repository(value="_linkRepository")
public interface LinkRepository extends BaseRepository<Link,Long> {
	
}/**@generate-repository-source@**/

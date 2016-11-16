package com.faceye.component.search.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.faceye.component.search.entity.Section;
import com.faceye.feature.repository.jpa.BaseRepository;
/**
 * Section 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
public interface SectionRepository extends BaseRepository<Section,Long> {
	
	
}/**@generate-repository-source@**/

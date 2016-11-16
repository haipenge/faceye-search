package com.faceye.component.search.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.faceye.component.search.entity.Category;
import com.faceye.feature.repository.jpa.BaseRepository;
/**
 * Category 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
public interface CategoryRepository extends BaseRepository<Category,Long> {
	public Category getCategoryByName(String name);
	
}/**@generate-repository-source@**/

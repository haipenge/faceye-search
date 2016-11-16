package com.faceye.feature.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.faceye.feature.entity.Example;
/**
 * Example 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
public interface ExampleRepository extends BaseRepository<Example, Long> {
 
}

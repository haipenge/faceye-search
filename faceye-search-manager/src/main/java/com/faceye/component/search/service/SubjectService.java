package com.faceye.component.search.service;

import com.faceye.component.search.doc.Subject;
import com.faceye.feature.service.BaseService;

public interface SubjectService extends BaseService<Subject,Long>{
    /**
     * 根据别名取得分类
     * @todo
     * @param alias
     * @return
     * @author:@haipenge
     * haipenge@gmail.com
     * 2015年1月1日
     */
	public Subject getSubjectByAlias(String alias);
	
	
	
}/**@generate-service-source@**/

package com.faceye.component.spider.repository.mongo;

import com.faceye.component.spider.doc.Link;
import com.faceye.feature.repository.mongo.BaseMongoRepository;


public interface LinkRepository extends BaseMongoRepository<Link,Long> {

	
	public Link getLinkBySign(String sign);
}

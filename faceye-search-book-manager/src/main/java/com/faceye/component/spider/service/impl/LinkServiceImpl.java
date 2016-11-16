package com.faceye.component.spider.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.QLink;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.repository.mongo.LinkRepository;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteService;
import com.faceye.component.spider.service.domain.DomainLink;
import com.faceye.feature.service.SequenceService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;
import com.faceye.feature.util.ServiceException;
import com.querydsl.core.BooleanBuilder;

/**
 * 链接资源管理 
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年12月10日
 */
@Service
public class LinkServiceImpl extends BaseMongoServiceImpl<Link, Long, LinkRepository> implements LinkService {

	@Autowired
	private SiteService siteService = null;

	@Autowired
	private SequenceService sequenceService = null;

	@Autowired
	public LinkServiceImpl(LinkRepository dao) {
		super(dao);
	}

	@Override
	synchronized public Boolean isLinkExist(String url) {
		Boolean isExist = Boolean.FALSE;
		if (StringUtils.isNotEmpty(url)) {
			try {
				QLink qLink = QLink.link;
				BooleanBuilder builder = new BooleanBuilder();
				builder.and(qLink.url.eq(url));
				Pageable pageable = new PageRequest(0, 5);
				Page<Link> page = this.dao.findAll(builder.getValue(), pageable);
				if (page != null && CollectionUtils.isNotEmpty(page.getContent())) {
					// logger.debug(">>FaceYe -->链接已存在:"+url);
					isExist = Boolean.TRUE;
				} else {
					logger.debug(">>FaceYe --> Url " + url + " 将会被作为新链接保存。");
				}
			} catch (Exception e) {
				logger.debug(">>FaceYe throws Exception:" + e.toString());
				isExist = Boolean.TRUE;
			}
		}
		return isExist;
	}

	@Override
	public void saveDomainLinks(List<DomainLink> links, Site site) {
		if (CollectionUtils.isNotEmpty(links)) {
			List<Link> willSavedLinks = new ArrayList<Link>();
			for (DomainLink domainLink : links) {
				String url = domainLink.getUrl();
				Integer type = domainLink.getType();
				boolean isExist = this.isLinkExist(url);
				if (!isExist) {
					Link link = new Link();
					Long id = this.sequenceService.getNextSequence(Link.class.getName());
					logger.debug(">>FaceYe save url:" + url + ",id is:" + id);
					link.setId(id);
					link.setIsCrawled(false);
					link.setIsCrawlSuccess(false);
					// link.setSite(site);
					link.setSiteId(site.getId());
					link.setType(type);
					link.setUrl(url);
					// this.save(link);
					willSavedLinks.add(link);
				}
			}
			if (CollectionUtils.isNotEmpty(willSavedLinks)) {
				this.save(willSavedLinks);
			}
		}
	}

	@Override
	public List<Link> getLinks2Crawl(Map searchParams) {
		if (null == searchParams) {
			searchParams = new HashMap();
		}
		List<Link> links = null;
		// List<Site> sites = this.siteService.getAll();
		// Site site = null;
		// if (CollectionUtils.isNotEmpty(sites)) {
		// site = sites.get(MathUtil.getRandInt(0, sites.size()));
		// }
		// if(null!=site){
		// searchParams.put("EQ|site.id", site.getId());
		// }
		searchParams.put("ISFALSE|isCrawled", "0");
		//
		Page<Link> page = getPage(searchParams, 1, 500);
		if (null != page) {
			links = page.getContent();
		}
		return links;
	}

	@Override
	public void saveLink(String url, Long siteId, Integer type) {
		this.saveLink(url, siteId, type, 1);
	}

	@Override
	public void saveLink(String url, Long siteId, Integer type, Integer mimeType) {
		Link link = new Link();
		Long id = this.sequenceService.getNextSequence(Link.class.getName());
		link.setId(id);
		logger.debug(">>FaceYe Do save link id is:" + id + ",url is:" + url);
		link.setUrl(url);
		link.setSiteId(siteId);
		link.setCreateDate(new Date());
		link.setIsCrawled(false);
		link.setIsCrawlSuccess(false);
		link.setType(type);
		link.setMimeType(mimeType);
		this.dao.save(link);
	}

	@Override
	public Page<Link> getPage(Map<String, Object> searchParams, int page, int size) throws ServiceException {
		if (page != 0) {
			page = page - 1;
		}
		// searchParams.put("EQ|site.id", site.getId());
		// searchParams.put("EQ|type", new Integer(1));
		// searchParams.put("ISTRUE|isCrawled", new Integer(1));
		BooleanBuilder builder = new BooleanBuilder();
		QLink qLink = QLink.link;
		if (MapUtils.getLong(searchParams, "EQ|site.id") != null) {
			builder.and(qLink.siteId.eq(MapUtils.getLong(searchParams, "EQ|site.id")));
		}
		if (MapUtils.getInteger(searchParams, "EQ|type") != null) {
			builder.and(qLink.type.eq(MapUtils.getInteger(searchParams, "EQ|type")));
		}
		if (MapUtils.getInteger(searchParams, "ISTRUE|isCrawled") != null) {
			builder.and(qLink.isCrawled.isTrue());
		}
		if (MapUtils.getInteger(searchParams, "ISFALSE|isCrawled") != null) {
			builder.and(qLink.isCrawled.isFalse());
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(searchParams, "EQ|url"))) {
			builder.and(qLink.url.like("%" + MapUtils.getString(searchParams, "EQ|url") + "%"));
		}
		Page<Link> res = null;

		Pageable pageable = null;
		// 保证新采集到的链接最先爬取
		Sort sort = new Sort(Direction.DESC, "id");
		if (size == 0) {
			// size = 1000;
			// pageable = new PageRequest(page);
			// List<Link> items = (List<Link>) this.dao.findAll(builder.getValue());
			Iterable<Link> links = this.dao.findAll(builder.getValue());
			List<Link> items = (List) links;
			// Iterator<Link> it=links.iterator();
			// while(it.hasNext()){
			// items.add(it.next());
			// }
			res = new PageImpl(items);
		} else {
			pageable = new PageRequest(page, size, sort);
			res = this.dao.findAll(builder.getValue(), pageable);
		}

		return res;
	}

	@Override
	public void resetSeedLinks() {
		Map searchParams = new HashMap();
		searchParams.put("EQ|type", 0);
		Page<Link> links = this.getPage(searchParams, 1, 0);
		if (links != null && CollectionUtils.isNotEmpty(links.getContent())) {
			for (Link link : links.getContent()) {
				link.setIsCrawled(false);
				link.setIsCrawlSuccess(false);
				this.dao.save(link);
			}
		}
	}

}
/**@generate-service-source@**/

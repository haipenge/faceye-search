package com.faceye.component.spider.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.document.Document;
import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.QLink;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.repository.mongo.LinkRepository;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteService;
import com.faceye.component.spider.service.domain.DomainLink;
import com.faceye.feature.repository.mongo.DynamicSpecifications;
import com.faceye.feature.service.SequenceService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;
import com.faceye.feature.util.MD5Utils;
import com.faceye.feature.util.ServiceException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

/**
 * 链接资源管理
 * 
 * @author @haipenge haipenge@gmail.com Create Date:2014年12月10日
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
		Boolean isExist = Boolean.TRUE;
		if (StringUtils.isNotEmpty(url)) {
			try {
				String sign = MD5Utils.md5(url);
				Link link = this.getLinkBySign(sign);
				if (link == null) {
					isExist = false;
				}
				// QLink qLink = QLink.link;
				// BooleanBuilder builder = new BooleanBuilder();
				// builder.and(qLink.url.eq(url));
				// Pageable pageable = new PageRequest(0, 5);
				// Page<Link> page = this.dao.findAll(builder.getValue(), pageable);
				// if (CollectionUtils.isEmpty(page.getContent())) {
				// isExist = false;
				// }
			} catch (Exception e) {
				logger.debug(">>FaceYe throws Exception:" + e.toString());
				isExist = Boolean.TRUE;
			}
		}
		return isExist;
	}

	@Override
	synchronized public void saveDomainLinks(List<DomainLink> links, Site site) {
		if (CollectionUtils.isNotEmpty(links)) {
			List<Link> willSavedLinks = new ArrayList<Link>();
			for (DomainLink domainLink : links) {
				String url = domainLink.getUrl();
				boolean isUrlRight2Save = this.isUrlRight2Save(url);
				if (isUrlRight2Save) {
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
						if (StringUtils.contains(url, "wx.dm15.com")) {
							if (StringUtils.isNotEmpty(domainLink.getCategoryKey())) {
								link.setDistributeChannel(domainLink.getCategoryKey());
							}
						}
						link.setIsWeixin(domainLink.getIsWeixin());
						this.save(link);
						// willSavedLinks.add(link);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(willSavedLinks)) {
				// this.save(willSavedLinks);
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
		searchParams.put("ISFALSE|isDistributed", false);

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
		link.setIsDistributed(false);
		link.setType(type);
		link.setMimeType(mimeType);
		this.dao.save(link);
	}

	public Page<Link> getPage_(Map<String, Object> searchParams, int page, int size) throws ServiceException {
		if (page != 0) {
			page = page - 1;
		}

		// BooleanBuilder builder = new BooleanBuilder();
		// QLink qLink = QLink.link;
		// if (MapUtils.getLong(searchParams, "EQ|site.id") != null) {
		// builder.and(qLink.siteId.eq(MapUtils.getLong(searchParams, "EQ|site.id")));
		// }
		// if (MapUtils.getInteger(searchParams, "EQ|type") != null) {
		// builder.and(qLink.type.eq(MapUtils.getInteger(searchParams, "EQ|type")));
		// }
		// if (MapUtils.getInteger(searchParams, "NE|type") != null) {
		// builder.and(qLink.type.ne(MapUtils.getInteger(searchParams, "NE|type")));
		// }
		// if (MapUtils.getInteger(searchParams, "ISTRUE|isCrawled") != null) {
		// builder.and(qLink.isCrawled.isTrue());
		// }
		// if (MapUtils.getInteger(searchParams, "ISFALSE|isCrawled") != null) {
		// builder.and(qLink.isCrawled.isFalse());
		// }
		// if (StringUtils.isNotEmpty(MapUtils.getString(searchParams, "EQ|url"))) {
		// builder.and(qLink.url.like("%" + MapUtils.getString(searchParams, "EQ|url") + "%"));
		// }
		//

		// SimpleEntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
		// EntityPath<T> entityPath = resolver.createPath(entityClass);
		// PathBuilder<T> builder = new PathBuilder<T>(entityPath.getType(), entityPath.getMetadata());
		// Path path = entityPath.getRoot();
		// List<Predicate> predicates=DynamicSpecifications.buildPredicates(searchParams, entityClass);
		// Predicate predicate=DynamicSpecifications.builder(predicates);
		// NumberPath numberPath = new NumberPath(Number.class, path, "age");
		// predicates.add(numberPath.eq(15));
		Predicate predicate = DynamicSpecifications.builder(searchParams, entityClass);
		if (predicate != null) {
			logger.debug(">>FaceYe -->Query predicate is:" + predicate.toString());
		}
		Page<Link> res = null;
		Pageable pageable = null;
		// 保证新采集到的链接最先爬取
		Sort sort = new Sort(Direction.DESC, "id");
		if (size == 0) {
			// size = 1000;
			// pageable = new PageRequest(page);
			// List<Link> items = (List<Link>) this.dao.findAll(builder.getValue());
			Iterable<Link> links = this.dao.findAll(predicate);
			List<Link> items = (List) links;
			// Iterator<Link> it=links.iterator();
			// while(it.hasNext()){
			// items.add(it.next());
			// }
			res = new PageImpl(items);
		} else {
			pageable = new PageRequest(page, size, sort);
			res = this.dao.findAll(predicate, pageable);
		}

		return res;
	}

	@Override
	public void resetSeedLinks() {
		Map searchParams = new HashMap();
		searchParams.put("EQ|type", 0);
		int page = 1;
		Page<Link> links = this.getPage(searchParams, page, 100);
		while (links != null && CollectionUtils.isNotEmpty(links.getContent())) {
			for (Link link : links.getContent()) {
				link.setIsCrawled(false);
				link.setIsCrawlSuccess(false);
				this.dao.save(link);
			}
			page++;
			links = this.getPage(searchParams, page, 100);
		}
	}

	/**
	 * 向各个爬取节点分发爬取链接 加锁，防止链接重发
	 */
	public synchronized List getDistributeLinks(String channel) {
		Map searchParams = new HashMap();
		searchParams.put("ISFALSE|isDistributed", false);
		searchParams.put("ISFALSE|isCrawled", false);
		searchParams.put("EQ|mimeType", 0);
		Page<Link> links = this.getPage(searchParams, 1, 100);
		if (CollectionUtils.isNotEmpty(links.getContent())) {
			for (Link link : links) {
				link.setIsDistributed(true);
				link.setDistributeDate(new Date());
				link.setDistributeChannel(channel);
				this.dao.save(link);
			}
		}
		return links.getContent();
	}

	/**
	 * URL是否复合存储规则
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge 联系:haipenge@gmail.com 创建时间:2015年6月5日
	 */
	private boolean isUrlRight2Save(String url) {
		boolean res = false;
		// a.jsp
		if (StringUtils.isNotEmpty(url)) {
			if (StringUtils.startsWith(url.toLowerCase(), "http")) {
				res = true;
			}
			// http://share.baidu.com/s?type=text&searchPic=0&sign=on&to=renren&url=http://36kr.com/p/533586.html&title=NBA请了一家科技公司来运营它的旗舰店
			if (StringUtils.lastIndexOf(url.toLowerCase(), "http") != 0) {
				res = false;
			}
		}
		return res;
	}

	@Override
	public void saveLink(String url, Long siteId, Integer type, String categoryType) {
		Link link = new Link();
		Long id = this.sequenceService.getNextSequence(Link.class.getName());
		link.setId(id);
		logger.debug(">>FaceYe Do save link id is:" + id + ",url is:" + url);
		link.setUrl(url);
		link.setSiteId(siteId);
		link.setCreateDate(new Date());
		link.setIsCrawled(false);
		link.setIsCrawlSuccess(false);
		link.setIsDistributed(false);
		link.setType(type);
		link.setMimeType(0);// 网页
		// URL的文章分类，暂时使用
		link.setDistributeChannel(categoryType);
		this.dao.save(link);
	}

	@Override
	public Link getLinkBySign(String sign) {
		return this.dao.getLinkBySign(sign);
	}

}
/** @generate-service-source@ **/

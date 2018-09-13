package com.faceye.component.weixin.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.doc.Image;
import com.faceye.component.parse.service.ImageService;
import com.faceye.component.search.doc.Article;
import com.faceye.component.weixin.doc.Weixin;
import com.faceye.component.weixin.entity.Account;
import com.faceye.component.weixin.entity.ResponseContent;
import com.faceye.component.weixin.entity.ResponseContentItem;
import com.faceye.component.weixin.repository.mongo.WeixinRepository;
import com.faceye.component.weixin.service.AccountService;
import com.faceye.component.weixin.service.ResponseContentItemService;
import com.faceye.component.weixin.service.ResponseContentService;
import com.faceye.component.weixin.service.WeixinService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;
import com.faceye.feature.util.JspUtil;

@Service
public class WeixinServiceImpl extends BaseMongoServiceImpl<Weixin, Long, WeixinRepository> implements WeixinService {
	// 微信的appid
	@Value("#{property['weixin.app.id']}")
	private String appId = "";
	@Value("#{property['faceye.host']}")
	private String host = "";
	@Autowired
	private AccountService accountService = null;
	@Autowired
	private ResponseContentService responseContentService = null;
	@Autowired
	private ResponseContentItemService responseContentItemService = null;
	@Autowired
    private ImageService imageService=null;
	@Autowired
	public WeixinServiceImpl(WeixinRepository dao) {
		super(dao);
	}



	@Override
	public Weixin save(Weixin weixin) {
		super.save(weixin);
		Account account = this.accountService.getAccountByAppId(appId);
		if (account != null) {
			ResponseContent responseContent = this.responseContentService.getDefaultResponseContent(account);
			if (responseContent != null) {
				ResponseContentItem responseContentItem = new ResponseContentItem();
				responseContentItem.setName(weixin.getArticle().getName());
				List<Image> images=this.imageService.getImagesByArticleId(weixin.getArticle().getId());
				if(CollectionUtils.isNotEmpty(images)){
					responseContentItem.setPicUrl(images.get(0).getUrl());
				}else{
					logger.debug(">>FaceYe --> article images is empty.");
				}
				responseContentItem.setRemark(JspUtil.getSummary(weixin.getArticle().getContent(), 40));
				responseContentItem.setResponseContent(responseContent);
				responseContentItem.setUrl(host + "/weixin/weixin/detail/" + weixin.getId() + ".html");
				this.responseContentItemService.save(responseContentItem);
			}
		}
		return weixin;
	}



	@Override
	public Weixin getWeixinByArticle(Article article) {
		return this.dao.getWeixinByArticle(article);
	}

}
/**@generate-service-source@**/

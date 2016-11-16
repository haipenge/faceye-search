package com.faceye.component.spider.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.service.SiteLinkService;
import com.faceye.component.spider.service.domain.DomainLink;
import com.faceye.component.spider.service.domain.LinkBuilder;

/**
 * 测试酱link初始化
 * 
 * @author haipenge
 * 
 */
@Service("csjLinkService")
public class CsjLinkServiceImpl extends BaseSiteLinkServiceImpl implements SiteLinkService {

	@Override
	protected String getDomain() {
		return "http://wx.dm15.com";
	}

	@Override
	protected List<DomainLink> getDomainLinks2Save() {
		
		//1:爱情,5:财富,2:性格,6:智商,3:趣味,10:健康,7:社交,8:星座,4:职业
		List<DomainLink> links = new ArrayList<DomainLink>(0);
		// http://wx.dm15.com/page/1_2.html
		LinkBuilder.getInstance().build(links, "http://wx.dm15.com/love/", 1,"1");
		// 372
		for (int i = 2; i <= 372 / 16; i++) {
			LinkBuilder.getInstance().build(links, "http://wx.dm15.com/page/1_" + i + ".html", 1,"1");
		}
		// 财富
		LinkBuilder.getInstance().build(links, "http://wx.dm15.com/wealth/", 1,"5");
		for (int i = 2; i <= 94 / 16; i++) {
			LinkBuilder.getInstance().build(links, "http://wx.dm15.com/page/5_" + i + ".html", 1,"5");
		}
		// 性格
		LinkBuilder.getInstance().build(links, "http://wx.dm15.com/nature/", 1,"2");
		for (int i = 2; i <= 370 / 16; i++) {
			LinkBuilder.getInstance().build(links, "http://wx.dm15.com/page/2_" + i + ".html", 1,"2");
		}
		// 智商
		LinkBuilder.getInstance().build(links, "http://wx.dm15.com/IQ/", 1,"6");
		// 27
		LinkBuilder.getInstance().build(links, "http://wx.dm15.com/page/6_" + 2 + ".html", 1,"6");
//趣味265 2
		LinkBuilder.getInstance().build(links, "http://wx.dm15.com/interest/", 1,"3");
		for (int i = 2; i <= 265 / 16; i++) {
			LinkBuilder.getInstance().build(links, "http://wx.dm15.com/page/3_" + i + ".html", 1,"3");
		}
		//健康66 10
		LinkBuilder.getInstance().build(links, "http://wx.dm15.com/health/", 1,"10");
		for (int i = 2; i <= 66 / 16; i++) {
			LinkBuilder.getInstance().build(links, "http://wx.dm15.com/page/10_" + i + ".html", 1,"10");
		}
		//社交 177 7
		LinkBuilder.getInstance().build(links, "http://wx.dm15.com/social/", 1,"7");
		for (int i = 2; i <= 177 / 16; i++) {
			LinkBuilder.getInstance().build(links, "http://wx.dm15.com/page/7_" + i + ".html", 1,"7");
		}
		//38,8
		LinkBuilder.getInstance().build(links, "http://wx.dm15.com/constellatory/", 1,"8");
		for (int i = 2; i <= 38 / 16; i++) {
			LinkBuilder.getInstance().build(links, "http://wx.dm15.com/page/8_" + i + ".html", 1,"8");
		}
		//职业,83,4
		LinkBuilder.getInstance().build(links, "http://wx.dm15.com/career/", 1,"4");
		for (int i = 2; i <= 83 / 16; i++) {
			LinkBuilder.getInstance().build(links, "http://wx.dm15.com/page/4_" + i + ".html", 1,"4");
		}
		// 可能不同的解析
//		LinkBuilder.getInstance().build(links, "http://wx.dm15.com/localism/", 1);
//		LinkBuilder.getInstance().build(links, "http://wx.dm15.com/happy/ListInfo.php?classid=46", 1);
		return links;
	}

	@Override
	protected List<Link> getLinks2Reset() {
		return null;
	}

}

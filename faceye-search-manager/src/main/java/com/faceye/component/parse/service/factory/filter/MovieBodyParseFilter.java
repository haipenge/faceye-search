package com.faceye.component.parse.service.factory.filter;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.MetaData;
import com.faceye.component.parse.service.document.Document;
import com.faceye.component.parse.util.HtmlUtil;
import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.search.doc.Movie;
import com.faceye.component.search.service.MovieService;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.service.CrawlResultService;
import com.faceye.component.spider.service.LinkService;
import com.faceye.feature.util.DateUtil;

/**
 * 电影明细解析(youku)
 * @author @haipenge 
 * @联系:haipenge@gmail.com
 * 创建时间:2015年7月18日
 */
@Service
public class MovieBodyParseFilter extends BaseParseFilter {
	@Autowired
	private MovieService movieService = null;
	@Autowired
	private CrawlResultService crawlResultService = null;
	@Autowired
	private LinkService linkService=null;

	@Override
	public void filter(Document document, CrawlResult crawlResult, String content) {
		// 提取标题
		MetaData metaData = this.distillMeta(content);
		String title = this.distillTitle(content);
		// 《边境巡逻》正片—美国—电影—优酷网，视频高清在线观看—又名：《The Shepherd》《忠于职守:边境巡逻》
		// 提取真实影片名称
		String titleRegexp = "(《[\\s\\S]+》)[\\S\\s]+";
		String realTitle = "";
		try {
			realTitle = (String) RegexpUtil.match(title, titleRegexp).get(0).get("1");
			realTitle = StringUtils.trim(realTitle);
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		}
		logger.debug(">>FaceYe --> realTitle :" + realTitle);
		// 提取电影名称
		// <span class="name">边境巡逻</span>
		String nameRegexp = "<span class=\"name\">([\\s\\S].*?)<\\/span>";
		String name = "";
		try {
			name = RegexpUtil.match(content, nameRegexp).get(0).get("1").toString();
			name = HtmlUtil.getInstance().replaceAll(name);
			name = StringUtils.trim(name);
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		}
		logger.debug(">>FaceYe --> name is:" + name);
		// 别名
		String alias = "";
		String aliasRegexp = "<li class=\"row1 alias\">([\\S\\s].*?)<li class=\"row2\">";
		try {
			alias = com.faceye.feature.util.regexp.RegexpUtil.match(content, aliasRegexp).get(0).get("1").toString();
			if (StringUtils.isNotEmpty(alias)) {
				alias = HtmlUtil.getInstance().replaceAll(alias);
			}
			alias = StringUtils.trim(alias);
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		}
		logger.debug(">>FaceYe --> alias :" + alias);
		// 上映时间
		// <span class="pub"><label>上映:</label>2008-03-04</span>
		String onlineDate = "";
		String onlineDateRegexp = "<span class=\"pub\"><label>上映:</label>([\\S\\s].*?)<\\/span>";
		try {
			onlineDate = RegexpUtil.match(content, onlineDateRegexp).get(0).get("1").toString();
			onlineDate = StringUtils.trim(onlineDate);
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		}
		logger.debug(">>FaceYe onlineDate:" + onlineDate);
		// 地区
		// <span class="area">
		// <label>地区:</label>
		// <a charset="420-2-1" href="http://www.youku.com/v_olist/c_96_a_美国.html" target="_blank">美国</a></span>
		String area = "";
		String areaRegexp = "<span class=\"area\">([\\S\\s].*?)<\\/span>";
		try {
			area = RegexpUtil.match(content, areaRegexp).get(0).get("1").toString();
			if (StringUtils.isNotEmpty(area)) {
				area = HtmlUtil.getInstance().replaceAll(area);
				area = StringUtils.replace(area, "地区:", "");
				area = StringUtils.trim(area);
			}
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		}
		logger.debug(">>FaceYe area:" + area);
		// 类型
		// <span class="type">
		// <label>类型:</label>
		// <a target="_blank" charset="420-2-8" href="http://www.youku.com/v_olist/c_96_g_动作.html">动作</a> / <a target="_blank" charset="420-2-8"
		// href="http://www.youku.com/v_olist/c_96_g_惊悚.html">惊悚</a> / <a target="_blank" charset="420-2-8"
		// href="http://www.youku.com/v_olist/c_96_g_犯罪.html">犯罪</a> </span>
		String categoryName = "";
		String categoryNameRegexp = "<span class=\"type\">([\\S\\s].*?)<\\/span>";
		try {
			categoryName = RegexpUtil.match(content, categoryNameRegexp).get(1).get("1");
			categoryName = HtmlUtil.getInstance().replaceAll(categoryName);
			categoryName = StringUtils.replace(categoryName, "类型:", "");
			categoryName = StringUtils.trim(categoryName);
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		}
		logger.debug(">>FaceYe category name :" + categoryName);
		// 导演
		// <span class="director">
		// <label>导演:</label>
		// <a href="http://www.youku.com/star_page/uid_UNDEzOTI=.html" charset="420-2-7" target="_blank">艾萨克·佛罗伦汀</a> </span>
		String director = "";
		String directorRegexp = "<span class=\"director\">([\\S\\s].*?)<\\/span>";
		try {
			director = RegexpUtil.match(content, directorRegexp).get(0).get("1").toString();
			director = HtmlUtil.getInstance().replaceAll(director);
			director = StringUtils.replace(director, "导演:", "");
			director = StringUtils.trim(director);
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		}
		logger.debug(">>FaceYe -> director:" + director);
		// 主演
		// <span class="actor">
		// <label>主演:</label>
		// <a href="http://www.youku.com/star_page/uid_UNDI3ODg=.html" charset="420-2-10" target="_blank">尚格·云顿</a> / <a
		// href="http://www.youku.com/star_page/uid_UMTM5NjQ4.html" charset="420-2-10" target="_blank">斯科特·阿金斯</a> </span>
		String actor = "";
		String actorRegexp = "<span class=\"actor\">([\\S\\s].*?)<\\/span>";
		try {
			actor = RegexpUtil.match(content, actorRegexp).get(0).get("1").toString();
			actor = HtmlUtil.getInstance().replaceAll(actor);
			actor = StringUtils.replace(actor, "主演:", "");
			actor = StringUtils.trim(actor);
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		}
		logger.debug(">>FaceYe --> actor:" + actor);
		// 片长
		// <span class="duration">
		// <label>时长:</label>93分钟 </span>
		String totalMinutes = "";
		String totalMinutesRegexp = "<span class=\"duration\">([\\S\\s].*?)<\\/span>";
		try {
			totalMinutes = RegexpUtil.match(content, totalMinutesRegexp).get(0).get("1").toString();
			totalMinutes = HtmlUtil.getInstance().replaceAll(totalMinutes);
			totalMinutes = StringUtils.replace(totalMinutes, "时长:", "");
			totalMinutes = StringUtils.trim(totalMinutes);
		} catch (Exception e1) {
			logger.error(">>FaceYe throws Exception: --->", e1);
		}

		// 影片简介
		String remark = "";
		String remarkRegexp = "<span class=\"long\" style=\"display:none;\">([\\S\\s].*?)<\\/span>";
		try {
			remark = RegexpUtil.match(content, remarkRegexp).get(0).get("1").toString();
			remark = StringUtils.trim(remark);
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		}
		if (StringUtils.isEmpty(remark)) {
			remarkRegexp = "<div class=\"detail\" id=\"Detail\">([\\s\\S].*?)<\\/div>";
			try {
				remark = RegexpUtil.match(content, remarkRegexp).get(0).get("1").toString();
				remark = HtmlUtil.getInstance().replaceAll(remark);
			} catch (Exception e) {
				logger.error(">>FaceYe throws Exception: --->", e);
			}
		}
		remark = StringUtils.trim(remark);
		logger.debug(">>FaceYe --> remark :" + remark);
		// 提取电影图片
		//<li class="thumb"><img src='http://r4.ykimg.com/050E00005594DBF767BC3C5E1B0EE168' alt='擦枪走火'></li>
//		String imgRegexp="<li class=\"thumb\"><img src=\'([^\'].*?)[^>].*?></li>";
//		try {
//			String imgUrl=RegexpUtil.match(content, imgRegexp).get(0).get("1").toString();
//			if(StringUtils.isNotEmpty(imgUrl)){
//				Link link =new Link();
//				link.setCreateDate(new Date());
//				link.setIsCrawled(false);
//				link.setUrl(imgUrl);
//				link.setMimeType(1);
//				link.setSiteId(document.getCrawlResult().getSiteId());
//				link.setType(2);
//				this.linkService.save(link);
//			}
//		} catch (Exception e) {
//			logger.error(">>FaceYe throws Exception: --->",e);
//		}
		
		
		if (StringUtils.isNotEmpty(name)) {
			Movie movie = this.movieService.getMovieByNameAndFrom(name, "YOUKU");
			if (movie == null) {
				movie = new Movie();
				movie.setName(name);
				movie.setArea(area);
				movie.setCategoryName(categoryName);
				movie.setDirector(director);
				movie.setActor(actor);
				movie.setFrom("YOUKU");
				if (StringUtils.isNotEmpty(onlineDate)) {
					movie.setOnlineDate(DateUtil.getDateFromString(onlineDate, "yyyy-MM-dd"));
				}
				movie.setRemark(remark);
				movie.setTotalMinutes(totalMinutes);
				this.movieService.save(movie);
			}
			crawlResult.setIsParseSuccess(true);
		}

		crawlResult.setIsParse(true);
		this.crawlResultService.save(crawlResult);
	}

}

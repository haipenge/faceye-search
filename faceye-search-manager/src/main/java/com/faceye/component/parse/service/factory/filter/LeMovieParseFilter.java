package com.faceye.component.parse.service.factory.filter;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.document.Document;
import com.faceye.component.parse.util.HtmlUtil;
import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.search.doc.Movie;
import com.faceye.component.search.service.MovieService;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.service.CrawlResultService;
import com.faceye.feature.util.DateUtil;

/**
 * 乐视电影解析器
 * 
 * @author apple
 *
 */
@Service
public class LeMovieParseFilter extends BaseParseFilter {

	private String domain = "letv.com";
	@Autowired
	private MovieService movieService = null;
	@Autowired
	private CrawlResultService crawlResultService = null;

	/**
	 * 乐视电影详情解析器
	 */
	@Override
	public void filter(Document document, CrawlResult crawlResult, String content) {
		if (StringUtils.isNotEmpty(content)) {
			String title = this.distillTitle(content);
			// <title>消失的凶手_消失的子弹2电影_在线观看_演员表_下载-乐视网</title>
			if (StringUtils.isNotEmpty(title)) {
				title = StringUtils.replace(title, "电影_在线观看_演员表_下载-乐视网", "");
			}
			// <p class="p2">导演：

			String directorRegexp = "<p class=\"p2\">导演：([\\s\\S].*?)<\\/p>";
			String director = this.match(content, directorRegexp);
			// 演员
			// <p class="p3">主演：
			String actorsRegexp = "<p class=\"p3\">主演：([\\s\\S].*?)<\\/p>";
			String actors = this.match(content, actorsRegexp);
			// 时长，<span class="s3">时长：
			String timeLengthRegexp = "<span class=\"s3\">时长：([\\s\\S].*?)<\\/span>";
			String timeLength = this.match(content, timeLengthRegexp);
			// 地区：<span class="s4">地区：
			String areaRegexp = "<span class=\"s4\">地区：([\\s\\S].*?)<\\/span>";
			String area = this.match(content, areaRegexp);
			// 类型：<span class="s5">类型：
			String typeRegexp = "<span class=\"s5\">类型：([\\s\\S].*?)<\\/span>";
			String type = this.match(content, typeRegexp);
			// 剧情介绍:<p class="p6">
			String descRegexp = "<p class=\"p6\">([\\s\\S].*?)<\\/p>";
			String desc = this.match(content, descRegexp);
			desc = StringUtils.trim(desc);
			// 年代：<span class="s5">年代：
			String onlineDateRegexp = "<span class=\"s5\">年代：([\\s\\S].*?)<\\/span>";
			String onlineDate = this.match(content, onlineDateRegexp);
			if (StringUtils.isNotEmpty(desc)) {
				desc = this.convert(desc);
			}
			if (StringUtils.isNotEmpty(title)) {
				Movie movie = this.movieService.getMovieByNameAndFrom(title, "LETV");
				if (movie == null) {
					movie = new Movie();
					movie.setName(title);
					movie.setArea(area);
					movie.setCategoryName(type);
					movie.setDirector(director);
					movie.setActor(actors);
					movie.setFrom("LETV");
					if (StringUtils.isNotEmpty(onlineDate)) {
						if (StringUtils.length(onlineDate) == 4) {
							movie.setOnlineDate(DateUtil.getDateFromString(onlineDate+"-7-15", "yyyy-MM-dd"));
						} else {
							movie.setOnlineDate(DateUtil.getDateFromString(onlineDate, "yyyy-MM-dd"));
						}
					}
					movie.setRemark(desc);
					movie.setTotalMinutes(timeLength);
					this.movieService.save(movie);
				}
				crawlResult.setIsParseSuccess(true);
			}

			crawlResult.setIsParse(true);
			this.crawlResultService.save(crawlResult);

		}
	}

	private String match(String content, String regexp) {
		String res = "";
		try {
			List<Map<String, String>> actorsMatchs = RegexpUtil.match(content, regexp);
			if (CollectionUtils.isNotEmpty(actorsMatchs)) {
				res = actorsMatchs.get(0).get("1");
				res = HtmlUtil.getInstance().replaceHtml(res);
			}
		} catch (Exception e) {
			logger.error(">>FaceYe Throws Exception:", e);
		}
		return res;
	}

	/**
	 * unicode ->汉字
	 * 
	 * @param utfString
	 * @return
	 * @Desc:
	 * @Author:apple
	 * @Date:2015年12月19日 下午5:44:57
	 */
	public String convert(String utfString) {
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;

		while ((i = utfString.indexOf("\\u", pos)) != -1) {
			sb.append(utfString.substring(pos, i));
			if (i + 5 < utfString.length()) {
				pos = i + 6;
				sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
			}
		}

		return sb.toString();
	}

}

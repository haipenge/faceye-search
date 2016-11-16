package com.faceye.component.search.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faceye.component.search.doc.Movie;
import com.faceye.component.search.service.MovieService;
import com.faceye.component.search.service.impl.GlobalEntity;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.util.http.HttpUtil;

@Controller
@Scope("prototype")
@RequestMapping("/search/movie")
public class MovieController extends BaseController<Movie, Long, MovieService> {

	@Autowired
	public MovieController(MovieService service) {
		super(service);
	}

	/**
	 * 首页
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年5月24日
	 */
	@RequestMapping("/home")
	public String home(HttpServletRequest request, Model model) {
		Map searchParams = HttpUtil.getRequestParams(request);
		model.addAttribute("searchParams", searchParams);
		Page<Movie> page = this.service.getPage(searchParams, getPage(searchParams), getSize(searchParams));
		model.addAttribute("page", page);
		return "search.movie.home";
	}

	/**
	 * 转向编辑或新增页面
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年5月24日
	 */
//	@RequestMapping("/edit/{id}")
//	public String edit(@PathVariable("id") Long id, Model model) {
//		if (id != null) {
//			Movie entity = this.service.get(id);
//			model.addAttribute("movie", entity);
//		}
//		return "search.movie.update";
//	}

	/**
	 * 转向新增页面
	 * @todo
	 * @param model
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年5月27日
	 */
//	@RequestMapping(value = "/input")
//	public String input(Model model) {
//		return "search.movie.update";
//	}

//	/**
//	 * 数据保存
//	 */
//	@RequestMapping("/save")
//	public String save(Movie entity, RedirectAttributes redirectAttributes) {
//		this.service.save(entity);
//		return "redirect:/search/movie/home";
//	}

//	/**
//	 * 数据删除
//	 * 
//	 * @todo
//	 * @return
//	 * @author:@haipenge haipenge@gmail.com 2014年5月24日
//	 */
//	@RequestMapping("/remove/{id}")
//	public String remove(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
//		if (id != null) {
//			this.service.remove(id);
//		}
//		return "redirect:/search/movie/home";
//	}

	/**
	 * 取得数据明细
	 * @todo
	 * @param id
	 * @param model
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年5月26日
	 */
	@RequestMapping("/detail/{id}.html")
	public String detail(@PathVariable Long id, Model model) {
		if (id != null) {
			Movie movie = this.service.get(id);
			if (null != movie) {
				GlobalEntity globalEntity = new GlobalEntity();
				model.addAttribute("movie", movie);
				//set title and meta
				String title = movie.getName();
				title += "_" + "在线观看_完整版";
				title += "_FaceYe影院";
				globalEntity.setTitle(title);
				String description = movie.getName();
				description += "," + movie.getName() + "高清在线观看";
				description += "," + movie.getName() + "完整版下载";
				globalEntity.setDesc(description);
				String keywords = movie.getName();
				keywords += "," + movie.getName() + "高清在线观看";
				keywords += "," + movie.getName()+"完整版";
				keywords += ",下载,高速下载,迅雷下载,种子";
                globalEntity.setKeywords(keywords);
                model.addAttribute("global", globalEntity);
			}
		}
		Page<Movie> page = this.service.getPage(null, 1, 10);
		model.addAttribute("movies", page);
		return "search.movie.detail";
	}
	/**
	 * 在线观看
	 * @todo
	 * @param id
	 * @param model
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年7月19日
	 */
	@RequestMapping("/view/{id}.html")
	public String view(@PathVariable Long id,Model model){
		if (id != null) {
			Movie movie = this.service.get(id);
			if (null != movie) {
				GlobalEntity globalEntity = new GlobalEntity();
				model.addAttribute("movie", movie);
				//set title and meta
				String title = movie.getName();
				title += "_" + "高清在线观看";
				title += "_FaceYe影院";
				globalEntity.setTitle(title);
				String description = movie.getName();
				description += "," + movie.getName() + "高清在线观看";
				//description += "," + movie.getName() + "完整版下载";
				globalEntity.setDesc(description);
				String keywords = movie.getName();
				keywords += "," + movie.getName() + "高清在线观看";
				keywords += "," + movie.getName()+"完整版";
				keywords += ",下载,高速下载,迅雷下载,种子";
                globalEntity.setKeywords(keywords);
                model.addAttribute("global", globalEntity);
			}
		}
		Page<Movie> page = this.service.getPage(null, 1, 10);
		model.addAttribute("movies", page);
		return "search.movie.view";
	}
	/**
	 * 下载
	 * @todo
	 * @param id
	 * @param model
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年7月19日
	 */
	@RequestMapping("/download/{id}.html")
	public String download(@PathVariable Long id,Model model){
		if (id != null) {
			Movie movie = this.service.get(id);
			if (null != movie) {
				GlobalEntity globalEntity = new GlobalEntity();
				model.addAttribute("movie", movie);
				//set title and meta
				String title = movie.getName();
				title += "_" + "高清下载";
				title += "_FaceYe影院";
				globalEntity.setTitle(title);
				String description = movie.getName();
				description += "," + movie.getName() + "迅雷下载";
				description += "," + movie.getName() + "完整版下载";
				globalEntity.setDesc(description);
				String keywords = movie.getName();
				keywords += "," + movie.getName() + "迅雷下载";
				keywords += "," + movie.getName()+"完整版,高清下载";
				keywords += ",下载,高速下载,迅雷下载,种子";
                globalEntity.setKeywords(keywords);
                model.addAttribute("global", globalEntity);
			}
		}
		Page<Movie> page = this.service.getPage(null, 1, 10);
		model.addAttribute("movies", page);
		return "search.movie.download";
	}

}

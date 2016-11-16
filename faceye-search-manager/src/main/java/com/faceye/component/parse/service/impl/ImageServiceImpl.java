package com.faceye.component.parse.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.doc.Image;
import com.faceye.component.parse.repository.mongo.ImageRepository;
import com.faceye.component.parse.service.ImageService;
import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.util.FileUtil;
import com.faceye.component.spider.util.ImageUtil;
import com.faceye.component.spider.util.PathUtil;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;

@Service
public class ImageServiceImpl extends BaseMongoServiceImpl<Image, Long, ImageRepository> implements ImageService {

	@Autowired
	public ImageServiceImpl(ImageRepository dao) {
		super(dao);
	}
	@Override
	public void saveImage(Link link, byte[] content) {
		String suffix=ImageUtil.getImageSuffix(link.getUrl());
		String storeImgPath=this.buildImgStorePath(suffix);
		FileUtil.getInstance().writeImg(content, storeImgPath);
		Image image=new Image();
		image.setLinkId(link.getId());
		image.setSourceUrl(link.getUrl());
		image.setStorePath(storeImgPath);
		this.save(image);
		logger.debug(">>FaceYe -->爬取图片："+link.getUrl()+",存储到:"+storeImgPath);
	}

	/**
	 * 构建图片存储路径
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月5日
	 */
	private String buildImgStorePath(String suffix) {
		String res = "";
		if (StringUtils.isNotEmpty(suffix)) {
			res = PathUtil.generateDynamicDirs();
			res += PathUtil.generateFileName();
			res += ".";
			res += suffix;
			logger.debug(">>FaceYe-->图片的存储路径是:" + res);
		}
		return res;
	}

	@Override
	public Image getImageByUrl(String url) {
		return this.dao.getImageByUrl(url);
	}

	@Override
	public List<Image> getImagesByArticleId(Long articleId) {
		return this.dao.getImagesByArticleId(articleId);
	}

}
/**@generate-service-source@**/

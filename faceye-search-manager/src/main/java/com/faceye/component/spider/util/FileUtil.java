package com.faceye.component.spider.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.faceye.feature.service.PropertyService;
import com.faceye.feature.util.bean.BeanContextUtil;

/**
 * 文件操作
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月23日
 */
public class FileUtil {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static FileUtil INSTANCE = null;

	private FileUtil() {

	}

	synchronized public static FileUtil getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new FileUtil();
		}
		return INSTANCE;
	}

	synchronized public String write(String content, String url) {
		String realFile = "";
		try {
			String path = PathUtil.generatePath(url);
			String filename = PathUtil.generateFileName();
			realFile = write(content, path, filename);
		} catch (Exception e) {
			logger.error(">>--->" + e.getMessage());
		}
		return realFile;
	}

	/**
	 * 写文件
	 * @todo
	 * @param content
	 * @param charset
	 * @param path
	 * @param filename
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月7日
	 */
	synchronized public String write(String content, String path, String filename) {
		FileWriter writer = null;
		BufferedWriter bufferWriter = null;
		String realFile = "";
		if (StringUtils.isNotEmpty(path) && StringUtils.isNotEmpty(filename)) {
			realFile = path + filename + ".html";
			String fullPath = getCrawlStorePath() + path;
			File pathFile = new File(fullPath);
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			String fullFileNameWithPath = fullPath + filename + ".html";
			try {
				File file = new File(fullFileNameWithPath);
				if (file.exists()) {
					file.createNewFile();
				}
				writer = new FileWriter(file);
				bufferWriter = new BufferedWriter(writer);
				bufferWriter.write(content);
				bufferWriter.flush();
			} catch (FileNotFoundException e) {
				logger.error(">>--->" + e.getMessage());
			} catch (IOException e) {
				logger.error(">>--->" + e.getMessage());
			} finally {
				if (bufferWriter != null) {
					try {
						bufferWriter.close();
					} catch (IOException e) {
						logger.error(">>--->" + e.getMessage());
					}
				}
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						logger.error(">>--->" + e.getMessage());
					}
				}
			}
		}
		return realFile;
	}

	public void write(String content, String path, String filename, Boolean isAppend) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(path + filename, isAppend)));
			out.println(content);
		} catch (IOException e) {
			logger.error(">>--->" + e.getMessage());
		} finally {
			out.close();
		}
	}

	/**
	 * 写入图片流
	 * @todo
	 * @param bytes
	 * @param writeImgUrl:文件写入路径
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月20日
	 */
	public boolean writeImg(byte[] bytes, String writeImgUrl) {
		String imgPath = this.getImgStorePath() + writeImgUrl;
		boolean res = false;
		if (null != bytes && bytes.length > 0) {
			BufferedOutputStream out = null;
			try {
				String dir = imgPath.substring(0, imgPath.lastIndexOf("/"));
				File file = new File(dir);
				if (!file.exists()) {
					file.mkdirs();
				}
				File imgFile = new File(imgPath);
				if (!imgFile.exists()) {
					imgFile.createNewFile();
				}
				out = new BufferedOutputStream(new FileOutputStream(imgFile));
				out.write(bytes);
				out.flush();
				res = true;
			} catch (Exception e) {
				res = false;
				logger.error(">>FaceYe throws Exception when wirte img,path is:" + imgPath + ",exception:" + e.toString());
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						logger.error(">>FaceYe throws Exception: --->" + e.toString());
					}
				}
			}
		}
		return res;
	}

	/**
	 * 读文件
	 * @todo
	 * @param imgUrl
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月20日
	 */
	public byte[] readImg(String imgUrl) {
		byte[] bytes = null;
		File file = new File(imgUrl);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			// int byteRead=0;
			// while((byteRead=fis.read(bytes))!=-1){
			// fis.read(bytes, 0, byteRead);
			// }
			// byte[] buf = new byte[(int) file.length()];
			bytes = new byte[(int) file.length()];
			fis.read(bytes);
		} catch (FileNotFoundException e) {
			logger.error(">>FaceYe throws Exception: --->" + e.toString());
		} catch (IOException e) {
			logger.error(">>FaceYe throws Exception: --->" + e.toString());
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error(">>FaceYe throws Exception: --->" + e.toString());
				}
			}
		}

		return bytes;
	}

	/**
	 * 读取文件
	 * @todo
	 * @param path
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月8日
	 * @throws IOException 
	 */
	synchronized public String read(String path) throws IOException {
		String res = "";
		StringBuffer buffer = new StringBuffer();
		String realPath = this.getCrawlStorePath() + path;

		BufferedReader in = new BufferedReader(new FileReader(realPath));
		String s;
		while ((s = in.readLine()) != null) {
			buffer.append(s);
			buffer.append("\n");
		}
		in.close();
		buffer.append("\n");
		res = buffer.toString();
		// logger.debug(">>FaceYe read file is:"+realPath);
		return res;
	}

	/**
	 * 读取待爬取的种子URL文件
	 * @todo
	 * @param seedName
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月4日
	 */
	synchronized public List<String> readSeed(String seedName) {
		List<String> urls = new ArrayList<String>();
		String path = this.getSeedsPath() + "/" + seedName + ".properties";
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			String s;
			while ((s = in.readLine()) != null) {
				if (StringUtils.isNotEmpty(s) && !StringUtils.startsWith(s, "#")) {
					urls.add(s);
				}
			}
			in.close();
		} catch (Exception e) {
			logger.error(">>FaceYe --> throws Exception when read seed." + e.toString());
		}
		return urls;
	}

	/**
	 * 删除已上传的图片
	 * @todo
	 * @param path
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年4月1日
	 */
	synchronized public boolean deleteImage(String path) {
		boolean res = false;
		String root = this.getImgStorePath();
		String fullPath = root + path;
		File file = new File(fullPath);
		if (file.exists()&&file.isFile()) {
			res=file.delete();
		}
		return res;
	}

	/**
	 * 爬取网页存储路径
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月20日
	 */
	private String getCrawlStorePath() {
		PropertyService propertyService = BeanContextUtil.getBean(PropertyService.class);
		return propertyService.get("spider.root.crawl.path");
	}

	/**
	 * 爬取图片存储路径
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月20日
	 */
	private String getImgStorePath() {
		String url = "";
		url = BeanContextUtil.getBean(PropertyService.class).get("spider.root.img.path");
		return url;
	}

	/**
	 * 种子资源存储路径
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月20日
	 */
	private String getSeedsPath() {
		PropertyService propertyService = BeanContextUtil.getBean(PropertyService.class);
		return propertyService.get("spider.root.seed.path");
	}
}

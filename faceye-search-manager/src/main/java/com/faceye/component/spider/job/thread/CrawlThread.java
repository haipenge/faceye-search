package com.faceye.component.spider.job.thread;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.faceye.component.parse.service.ImageService;
import com.faceye.component.parse.util.HtmlUtil;
import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.service.CrawlResultService;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.util.Constants;
import com.faceye.component.spider.util.DeflateUtils;
import com.faceye.component.spider.util.GZIPUtils;
import com.faceye.component.spider.util.Http;
import com.faceye.feature.service.MultiQueueService;
import com.faceye.feature.service.PropertyService;
import com.faceye.feature.service.SequenceService;
import com.faceye.feature.util.bean.BeanContextUtil;

/**
 * 网页爬取线程
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月6日
 */
public class CrawlThread extends BaseThread {
	private CloseableHttpClient httpClient;
	private Map<String, String> headers = null;
	private static int BUFFER_SIZE = 8 * 1024;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String charset = DEFAULT_CHARSET;
	private final static String DEFAULT_CHARSET = "UTF-8";
	private LinkService linkService = null;
	private SequenceService sequenceService = null;
	private CrawlResultService crawlResultService = null;
	private ImageService imageService = null;
	private PropertyService propertyService = null;
	/**
	 * 线程默认休眠时间
	 */
	private final static Integer DEFAULT_THREAD_SLEEP_SECONDS = 5;

	private static Integer THREAD_SLEEP_SECONDS = null;

	private String key = "";

	// public CrawlThread(CloseableHttpClient httpClient) {
	// PropertyService propertyService = BeanContextUtil.getBean(PropertyService.class);
	// if (null != propertyService) {
	// String configSeconds = propertyService.get("x.spider.crawl.thread.sleep.seconds");
	// THREAD_SLEEP_SECONDS = StringUtils.isEmpty(configSeconds) ? DEFAULT_THREAD_SLEEP_SECONDS : Integer.parseInt(configSeconds);
	// } else {
	// THREAD_SLEEP_SECONDS = DEFAULT_THREAD_SLEEP_SECONDS;
	// }
	// this.httpClient = httpClient;
	// }

	public CrawlThread(String key) {
		propertyService = BeanContextUtil.getBean(PropertyService.class);
		if (null != propertyService) {
			String configSeconds = propertyService.get("spider.crawl.thread.sleep.seconds");
			THREAD_SLEEP_SECONDS = StringUtils.isEmpty(configSeconds) ? DEFAULT_THREAD_SLEEP_SECONDS : Integer.parseInt(configSeconds);
			linkService = BeanContextUtil.getBean(LinkService.class);
			sequenceService = BeanContextUtil.getBean(SequenceService.class);
			crawlResultService = BeanContextUtil.getBean("crawlResultServiceImpl");
			imageService = BeanContextUtil.getBean(ImageService.class);
		} else {
			THREAD_SLEEP_SECONDS = DEFAULT_THREAD_SLEEP_SECONDS;
		}
		CloseableHttpClient httpClient = null;
		httpClient = Http.getInstance().getClient();
		this.httpClient = httpClient;
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	int count = 0;

	public void doBusiness() {
		try {
			MultiQueueService<Link> linkQueueService = BeanContextUtil.getBean("linkQueueService");
			Boolean isContinue = Boolean.TRUE;
			int crawlPages = 0;
			while (isContinue) {
				Link link = linkQueueService.get(this.getKey());
				if (link == null) {
					isContinue = Boolean.FALSE;
					logger.debug(">>FaceYe --> Have now Links now of key:" + key);
					break;
				} else {
					count++;
					doCrawl(link);
					crawlPages++;
//					Thread.sleep(3000 * count);
					Thread.sleep(THREAD_SLEEP_SECONDS*1000L);
					if (count > 3) {
						count = 0;
					}
					if (crawlPages % 5 == 0) {
						logger.debug(">>FaceYe -->Thread--crawl pages is:" + crawlPages + ",less pages :" + linkQueueService.getSize(key)
								+ ",current loop complete rate: " + (100 * crawlPages / (crawlPages + linkQueueService.getSize(key))) + "%");
					}
				}
			}
		} catch (Exception e) {
			logger.error(">>FaceYe :", e);
		} finally {
			logger.debug(">>FaceYe:Crawl Thread final.");
		}
	}

	private void doCrawl(Link link) {
		CloseableHttpResponse response = null;
		InputStream in = null;
		byte[] content = null;
		HttpGet httpget = null;
		try {
			String url = link.getUrl();
			if (StringUtils.startsWith(url, "http")) {
				httpget = initHttpGet(link.getUrl());
				HttpClientContext context = HttpClientContext.create();
				headers = new HashMap<String, String>();
				response = httpClient.execute(httpget, context);
				HttpEntity entity = response.getEntity();
				Header[] heads = response.getAllHeaders();
				// 检测网页编码
				Charset _charset = ContentType.get(entity).getCharset();
				String readCharset = "";
				if (_charset != null) {
					readCharset = StringUtils.trim(_charset.displayName());
				}
				if (StringUtils.isEmpty(readCharset)) {
					readCharset = HtmlUtil.getInstance().getCharset(heads);
				}
				in = entity.getContent();
				initHeaders(heads);
				long contentLength = Long.MAX_VALUE;
				if (entity.getContentLength() != -1) {
					contentLength = entity.getContentLength();
				}
				byte[] buffer = new byte[BUFFER_SIZE];
				int bufferFilled = 0;
				int totalRead = 0;
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				while ((bufferFilled = in.read(buffer, 0, buffer.length)) != -1 && totalRead + bufferFilled <= contentLength) {
					totalRead += bufferFilled;
					out.write(buffer, 0, bufferFilled);
				}
				content = out.toByteArray();
				// 如果此是还没有检测到网页编码，则使用正则表达式检测网页编码
				if (StringUtils.isEmpty(readCharset)) {
					readCharset = this.getCharset(content, charset);
				}
				if (StringUtils.isNotEmpty(readCharset) && !StringUtils.equalsIgnoreCase(readCharset, charset)) {
					charset = readCharset;
				}

				// response.setContent(content);
				if (content != null) {
					String contentEncoding = MapUtils.getString(headers, "Content-Encoding");
					if (StringUtils.isNotEmpty(contentEncoding)) {
						if (StringUtils.equals("gzip", contentEncoding.toLowerCase())
								|| StringUtils.equals("x-gzip", contentEncoding.toLowerCase())) {
							content = GZIPUtils.unzipBestEffort(content);
						} else if (StringUtils.equals("deflate", contentEncoding.toLowerCase())) {
							content = DeflateUtils.deflate(content);
						}
					}
				}
			}
		} catch (ClientProtocolException ex) {
			logger.error(">>FaceYe error,url is :" + link.getUrl());
		} catch (IOException ex) {
			logger.error(">>FaceYe error,url is :" + link.getUrl());
		} catch (HttpException e) {
			logger.error(">>Get Url Content Throws Exception :" + e.toString() + ",url is:" + link.getUrl());
		} catch (Exception e) {
			logger.error(">>Get Url Content Throws Exception :" + e.toString() + ",url is:" + link.getUrl());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error(">>--->" + e.getMessage());
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error(">>--->" + e.getMessage());
				}
			}
			if (content != null) {
				this.afterCrawl(link, content, charset);
			} else {
				logger.error(">>FaceYe crawl empty of url:" + link.getUrl());
			}
		}
	}

	/**
	 * 爬取结束，保存爬取结果
	 * @todo
	 * @param link
	 * @param storePath
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月8日
	 */
	private void afterCrawl(Link link, byte[] content, String charset) {
		if (StringUtils.isEmpty(charset)) {
			charset = "UTF-8";
		}
		String channelType = this.getCrawlChannelType();
		if (StringUtils.equalsIgnoreCase(channelType, "local")) {
			this.crawlResultService.saveCrawlResult(link, content, charset);
		} else {
			Map params = new HashMap();
			String pageContent;
			try {
				pageContent = new String(content, charset);
				params.put("linkId", link.getId());
				params.put("content", pageContent);
				String postResult = Http.getInstance().post(this.getRemoteReportApi(), charset, params);
				logger.debug(">>FaceYe post crawl result of link:" + link.getUrl() + ",is:" + postResult);
			} catch (UnsupportedEncodingException e) {
				logger.error(">>FaceYe throws Exception: --->", e);
			}
		}
	}

	/**
	 *
	 */
	private String getCrawlChannelType() {
		String channelType = "";
		// spider.crawl.channel.type,local,remote
		channelType = propertyService.get("spider.crawl.channel.type");
		return channelType;
	}

	/**
	 * 取得远程上报API
	 */
	private String getRemoteReportApi() {
		String url = "";
		String reportHost = this.propertyService.get("spider.control.center.host");
		String reportApi = this.propertyService.get("spider.control.center.crawl.result.report.api");
		url = reportHost + reportApi;
		return url;
	}

	protected String getCharset(byte[] content, String defaultCharset) {
		String charset = "";
		String regEx = "(?=<meta).*?(?<=charset=[\\'|\\\"]?)([[a-z]|[A-Z]|[0-9]|-]*)";
		Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(new String(content)); // 默认编码转成字符串，因为我们的匹配中无中文，所以串中可能的乱码对我们没有影响
		boolean result = m.find();
		if (m.groupCount() == 1) {
			charset = m.group(1);
		} else {
			charset = defaultCharset;
		}
		return charset;
	}

	private HttpGet initHttpGet(String url) {
		HttpGet httpGet = new HttpGet(url);
		this.initRequestConfig(httpGet);
		httpGet.setHeader("User-Agent", Constants.USER_AGENT);
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		// 用逗号分隔显示可以同时接受多种编码
		httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpGet.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		// httpGet.setHeader("referer","");
		return httpGet;
	}

	private void initRequestConfig(HttpGet httpget) {
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectionRequestTimeout(30000)
				.setConnectTimeout(30000).build();
		httpget.setConfig(requestConfig);
	}

	private void initHeaders(Header[] heads) throws Exception {
		if (MapUtils.isNotEmpty(headers)) {
			headers.clear();
		}
		if (heads != null && heads.length > 0) {
			for (int i = 0; i < heads.length; i++) {
				Header header = heads[i];
				String name = header.getName();
				String value = header.getValue();
				headers.put(name, value);
			}
		}
	}
}

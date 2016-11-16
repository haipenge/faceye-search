package com.faceye.component.push.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.faceye.component.spider.util.Constants;
import com.faceye.component.spider.util.DeflateUtils;
import com.faceye.component.spider.util.GZIPUtils;

public class PushHttp {
	private Logger logger = LoggerFactory.getLogger(getClass());
	public static CloseableHttpClient httpClient = null;
	// SSL http client
	public static CloseableHttpClient sslHttpClient = null;
	private static int BUFFER_SIZE = 8 * 1024;
	private Map<String, String> headers = new HashMap<String, String>(0);
	private String useragent = "";
	private PoolingHttpClientConnectionManager cm = null;

	private static Map<String, PushHttp> INSTANCE_CONTAINER = null;

	private PushHttp() {
		this.initConnectionManager();

		// this.createDefaultHttpClient();

		this.createSSLHttpClient();
	}

	/**
	 * 初始化connection manager
	 */
	private void initConnectionManager() {
		if (null == cm) {
			cm = new PoolingHttpClientConnectionManager();
			// 将最大连接数增加到200
			cm.setMaxTotal(200);
			// 将每个路由基础的连接增加到20
			cm.setDefaultMaxPerRoute(20);
			// 将目标主机的最大连接数增加到50
			// HttpHost localhost = new HttpHost("www.baidu.com", 80);
			// cm.setMaxPerRoute(new HttpRoute(localhost), 50);
		}
	}

	public synchronized static PushHttp getInstance(String key) {
		if (MapUtils.isEmpty(INSTANCE_CONTAINER)) {
			INSTANCE_CONTAINER = new HashMap<String, PushHttp>();
		}
		PushHttp pushHttp = null;
		if (INSTANCE_CONTAINER.containsKey(key)) {
			pushHttp = INSTANCE_CONTAINER.get(key);
		} else {
			pushHttp = new PushHttp();
			INSTANCE_CONTAINER.put(key, pushHttp);
		}
		return pushHttp;
	}

	public synchronized CloseableHttpClient getClient() {
		return httpClient;
	}

	/**
	 * 创建默认的httpClient
	 */
	private void createDefaultHttpClient() {
		if (null == httpClient) {
			httpClient = HttpClients.custom().setConnectionManager(cm).build();
			// httpClient.getParams().set
			// httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			// httpClient.getParams().setParameter("http.protocol.single-cookie-header", true);
		}
	}

	/**
	 * 创建SSL Client
	 */
	private void createSSLHttpClient() {
		SSLContext sslContext = null;
		try {
			sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			logger.error(">>FaceYe throws Exception: --->" + e.toString());
		}
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
		// return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		httpClient = HttpClients.custom().setConnectionManager(cm).setSSLSocketFactory(sslsf).build();
	}

	public synchronized CloseableHttpClient getSSLClient() {
		return httpClient;
	}

	/**
	 * 根据URL取得HttpClient
	 */
	public synchronized CloseableHttpClient getClient(String url) {
		CloseableHttpClient client = null;
		if (StringUtils.isNotEmpty(url)) {
			url = url.toLowerCase();
			if (url.startsWith("https")) {
				client = this.getSSLClient();
			} else {
				client = this.getClient();
			}
		}
		return client;
	}

	public String get(String url, String charset) {
		String res = "";
		HttpGet httpget = null;
		CloseableHttpResponse response = null;
		InputStream in = null;
		byte[] content = null;
		CloseableHttpClient httpClient = this.getClient(url);
		try {
			httpget = this.initHttpGet(url);
			HttpContext context = HttpClientContext.create();
			response = httpClient.execute(httpget, context);
			HttpEntity entity = response.getEntity();
			if (StringUtils.isEmpty(charset)) {
				charset = ContentType.getOrDefault(entity).getCharset().displayName();
			}
			in = entity.getContent();
			Header[] heads = response.getAllHeaders();
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
		} catch (ClientProtocolException ex) {
			logger.error(">>FaceYe error,url is :" + httpget.getURI().toASCIIString());
		} catch (IOException ex) {
			logger.error(">>FaceYe error,url is :" + httpget.getURI().toASCIIString());
		} catch (HttpException e) {
			logger.error(">>Get Url Content Throws Exception :" + e.toString() + ",url is:" + httpget.getURI().toString());
		} catch (Exception e) {
			logger.error(">>Get Url Content Throws Exception :" + e.toString() + ",url is:" + httpget.getURI().toString());
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
			// this.destroy();
			if (null != content) {
				try {
					res = new String(content, charset);
				} catch (UnsupportedEncodingException e) {
					logger.error(">>--->" + e.toString());
				}
			}
		}
		return res;
	}

	private void initRequestConfig(HttpGet httpget) {
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectionRequestTimeout(30000)
				.setConnectTimeout(30000).build();
		httpget.setConfig(requestConfig);
	}

	private HttpGet initHttpGet(String url) {
		HttpGet httpGet = new HttpGet(url);
		this.initRequestConfig(httpGet);
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.setHeader("User-Agent", Constants.USER_AGENT);
		// 用逗号分隔显示可以同时接受多种编码
		httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpGet.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		return httpGet;
	}

	/**
	 * 初始化post请求
	 * @todo
	 * @param url
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年12月29日
	 */
	private HttpPost initHttpPost(String url) {
		HttpPost post = new HttpPost(url);

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectionRequestTimeout(30000)
				.setConnectTimeout(30000).build();
		post.setConfig(requestConfig);
		// 用逗号分隔显示可以同时接受多种编码
		post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.setHeader("User-Agent", Constants.USER_AGENT);
		post.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		post.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		post.setHeader("Keep-Alive", "300");
		// CSDN需加下面的头信息，才可以正常发布文章
		// post.setHeader("X-Requested-With","XMLHttpRequest");
		// post.setHeader("Referer","http://write.blog.csdn.net/postedit?ref=toolbar");
		return post;
	}

	/**
	 * 关闭使用后的链接
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月6日
	 */
	public void destroy() {
		if (cm != null) {
			cm.close();
		}
		// cm = null;
		// httpClient = null;
		// INSTANCE = null;
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

	/**
	 * POST提交
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年12月29日
	 */
	public String post(String url, String charset, Map<String, String> params) {
		String res = "";
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		InputStream in = null;
		byte[] content = null;
		CloseableHttpClient httpClient = this.getClient(url);
		try {
			httpPost = this.initHttpPost(url);
			List<NameValuePair> nvps = map2NameValuePair(params);
			if (StringUtils.indexOf(url, "51cto") != -1 && StringUtils.indexOf(url, "addblog_new") != -1) {
				MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, "---------------------------",
						Charset.forName("gb2312"));
				
				MultipartEntityBuilder builder=MultipartEntityBuilder.create();
				Iterator it = params.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next().toString();
					String value = MapUtils.getString(params, key);
					FormBodyPart part=new FormBodyPart(key, new StringBody(value,Charset.forName("gb2312")));
					builder.addTextBody(key, value);
//					mpEntity.addPart(key, new StringBody(value, Charset.forName("gb2312")));
				}
				HttpEntity entity=builder.build();
				httpPost.setHeader("Cookie", "Hm_lvt_110fc9b2e1cae4d110b7959ee4f27e3b=1422512817; Hm_lpvt_110fc9b2e1cae4d110b7959ee4f27e3b=1422512817; PHPSESSID=e5e082eefb208856922c6ab92255f005; lastvisit=0%091422529576%09%2Fjs%2Fblog_top_list.php%3F; CNZZDATA4274540=cnzz_eid%3D1865576461-1422511257-http%253A%252F%252Fwww.51cto.com%252F%26ntime%3D1422511257; www51cto=66B674A8F179AC55A64AC126DA69FA13axbP; _ga=GA1.2.1422215618.1422512930; pub_cookietime=2592000; _ourplusFirstTime=115-1-29-14-30-12; _ourplusReturnTime=115-1-29-19-4-16; _ourplusReturnCount=38; bdshare_firstime=1422513049835; Hm_lvt_2283d46608159c3b39fc9f1178809c21=1422513053,1422514658; Hm_lpvt_2283d46608159c3b39fc9f1178809c21=1422529580; pub_sauth1=UFAAVVRXDlUCAjsIWwFTDQwCPVVSAAcEU1ABUA0; pub_sauth2=a7939333f448c949b4690ca5ddb8a2f6; refreshlimit=1422529638%09%2Fuser_index.php%3Faction%3Daddblog_new; blog_top=yes; _gat=1");
				httpPost.setHeader("Referer","http://blog.51cto.com/user_index.php?action=addblog_new");
				httpPost.setHeader("X-Requested-With","XMLHttpRequest");
				httpPost.setEntity(entity);
//				httpPost.addHeader("Content-Type","multipart/form-data;");
			} else {
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
			}
			HttpContext context = HttpClientContext.create();
			response = httpClient.execute(httpPost, context);

			HttpEntity entity = response.getEntity();
			if (StringUtils.isEmpty(charset)) {
				charset = ContentType.getOrDefault(entity).getCharset().displayName();
			}
			in = entity.getContent();
			Header[] heads = response.getAllHeaders();
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
		} catch (ClientProtocolException ex) {
			logger.error(">>FaceYe error,url is :" + httpPost.getURI().toASCIIString());
		} catch (IOException ex) {
			logger.error(">>FaceYe error,url is :" + httpPost.getURI().toASCIIString());
		} catch (HttpException e) {
			logger.error(">>Get Url Content Throws Exception :" + e.toString() + ",url is:" + httpPost.getURI().toString());
		} catch (Exception e) {
			logger.error(">>Get Url Content Throws Exception :" + e.toString() + ",url is:" + httpPost.getURI().toString());
		} finally {
			if (null != content) {
				try {
					res = new String(content, charset);
				} catch (UnsupportedEncodingException e) {
					logger.error(">>FaceYe throws Exception: --->" + e.toString());
				}
			}
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
			if (httpPost != null) {
				httpPost.abort();
			}
			// this.destroy();
		}
		return res;
	}

	/**
	 * 组装参数
	 * 
	 * @param params
	 * @return
	 */
	private List<NameValuePair> map2NameValuePair(Map<String, String> params) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (MapUtils.isNotEmpty(params)) {
			Iterator<String> it = params.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = MapUtils.getString(params, key);
				// if (null!=value &&StringUtils.isNotEmpty(value)&&StringUtils.isNotBlank(value)) {
				if (null != value) {
					NameValuePair nvp = new BasicNameValuePair(key, value);
					logger.debug(">>HttpCall params:->" + key + "    =   " + value);
					nvps.add(nvp);
				}
			}
		}
		return nvps;
	}
}

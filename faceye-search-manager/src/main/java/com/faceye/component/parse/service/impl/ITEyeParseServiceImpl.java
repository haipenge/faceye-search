package com.faceye.component.parse.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.doc.ParseResult;
import com.faceye.component.parse.service.MetaData;
import com.faceye.component.parse.service.ParseResultService;
import com.faceye.component.parse.service.ParseService;
import com.faceye.component.parse.util.HtmlUtil;
import com.faceye.component.parse.util.RegexpConstants;
import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.CrawlResultService;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteLinkService;
import com.faceye.component.spider.util.FileUtil;
@Service
public class ITEyeParseServiceImpl extends BaseParseServiceImpl implements ParseService {
    
	@Autowired
	@Qualifier("ITEyeLinkServiceImpl")
	private SiteLinkService iteyeLinkService=null;
	@Autowired
	private LinkService linkService=null;
	@Autowired
	private CrawlResultService crawlResultService=null;
	@Autowired
	private ParseResultService parseResultService=null;
	public void saveAndParse(CrawlResult crawlResult, Integer type) {
		if(null!=type&& null!=crawlResult){
			//解析列表页
			if(type.intValue()==1){
				this.saveParseLinkLists(crawlResult);
			}else if(type.intValue()==2){
				this.saveParseBlogDetails(crawlResult);
			}
		}else{
			logger.error(">>FaceYe--> type is null or crawlResult is null.");
		}
	}

	@Override
	public void saveParseResult() {
		boolean isContinue=Boolean.TRUE;
		Site site=this.iteyeLinkService.getSite();
		while(isContinue){
			int loopCount=0;
			Map searchParams=new HashMap();
			searchParams.put("ISFALSE|isParse", "0");
			searchParams.put("EQ|link.type", new Integer(1));
			searchParams.put("EQ|link.site.id", site.getId());
			//解析列表页 type=1
			Page<CrawlResult> crawlResults=this.crawlResultService.getPage(searchParams,1, 1000);
			if(crawlResults==null || CollectionUtils.isEmpty(crawlResults.getContent())){
				loopCount++;
			}else{
				this.saveParseResult(crawlResults, new Integer(1));
			}
			//解析明细页,type=2
			crawlResults=null;
			searchParams.put("EQ|link.type", new Integer(2));
			crawlResults=this.crawlResultService.getPage(searchParams,1, 1000);
			if(crawlResults==null || CollectionUtils.isEmpty(crawlResults.getContent())){
				loopCount++;
			}else{
				this.saveParseResult(crawlResults, new Integer(2));
			}
			if(loopCount==2){
				isContinue=Boolean.FALSE;
			}
		}
	}

	
	/**
	 * 解析爬取结果
	 * @todo
	 * @param crawlResults
	 * @param type
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月14日
	 */
	private void saveParseResult(Page<CrawlResult> crawlResults,Integer type){
		if(crawlResults!=null&&CollectionUtils.isNotEmpty(crawlResults.getContent())){
			for(CrawlResult crawlResult:crawlResults.getContent()){
				this.saveAndParse(crawlResult, type);
			}
		}
	}
	
	
	

	/**
	 * 解析iteye博客首页列表页（type=1)，并从列表页提取明细页(link_type=2)进行存储 
	 * @todo
	 * @param crawlResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月14日
	 */
    private void saveParseLinkLists(CrawlResult crawlResult){
    	String path = crawlResult.getStorePath();
		String content="";
		try {
			content = FileUtil.getInstance().read(path);
		} catch (IOException e1) {
			logger.error(">>--->"+e1.getMessage());
		}
		//<a href='http://springmvc-springdata.iteye.com/blog/2116200' title='自己在项目中写的简单的仿freemarker模板引擎工具' target='_blank'>自己在项目中写的简单的仿freemarker模板引擎工具</a>
		String regexp = "<a href=\'([^']+?)\' title=\'[^>]+?\' target=\'_blank\'>[^<]+?<\\/a>";
		if (StringUtils.isNotEmpty(content)) {
			try {
				List<Map<String, String>> urls = RegexpUtil.match(content, regexp);
				if (CollectionUtils.isNotEmpty(urls)) {
					Site site=this.iteyeLinkService.getSite();
					logger.debug(">>FaceYe: parse iteye blogs home best page,store path:" + path + ",got url size :" + urls.size());
					for (Map<String, String> map : urls) {
						String url = map.values().iterator().next();
						if (StringUtils.isNotEmpty(url)) {
							Boolean isExist = this.linkService.isLinkExist(url);
							if (!isExist) {
//								Link link = new Link();
//								link.setCreateDate(new Date());
//								link.setIsCrawled(Boolean.FALSE);
//								link.setIsCrawlSuccess(Boolean.FALSE);
//								link.setUrl(url);
//								link.setType(2);
//								link.setSite(site);
//								this.linkService.save(link);
								this.linkService.saveLink(url, site.getId(), 2);
							}
						}
					}
				} else {
					logger.error(">>FaceYe:parse csdn blogs home best page,got empty detail url.store path is :" + path);
				}
			} catch (Exception e) {
				logger.error(">>--->" + e.getMessage());
			}finally{
				crawlResult.setIsParse(Boolean.TRUE);
				this.crawlResultService.save(crawlResult);
			}
		}
    }
    /**
     * 解析博客明细
     * @todo
     * @param crawlResult
     * @author:@haipenge
     * haipenge@gmail.com
     * 2014年9月14日
     */
    private void saveParseBlogDetails(CrawlResult crawlResult){
    	Boolean isParseSuccess = Boolean.FALSE;
		String path = crawlResult.getStorePath();
		String content="";
		try {
			content = FileUtil.getInstance().read(path);
		} catch (IOException e1) {
			logger.error(">>--->"+e1.getMessage());
		}
		if (StringUtils.isNotEmpty(content)) {
			try {
				List<Map<String, String>> bodyMatchs = RegexpUtil.match(content, RegexpConstants.DISTILL_ITEYE_BLOG_DETAIL);
				List<Map<String, String>> titleMatchs = RegexpUtil.match(content, RegexpConstants.DISTIAL_HTML_TITILE);
				MetaData meta=this.distillMeta(content);
				if (CollectionUtils.isNotEmpty(bodyMatchs)) {
					String distillBody = bodyMatchs.get(0).values().iterator().next();
					//提取的内容div未闭合,增加闭合标签
					distillBody="<div>"+distillBody;
				    //去掉内容中所有的a标签
					distillBody = HtmlUtil.getInstance().replace(distillBody, RegexpConstants.REPLACE_ALL_A_HREF);
					// 替换Img标签的正则表达式
					distillBody = HtmlUtil.getInstance().replace(distillBody, RegexpConstants.REPLACE_ALL_IMG);
					//替换iframe标签
					distillBody=HtmlUtil.getInstance().replace(distillBody, RegexpConstants.REPLACE_ALL_IFRAME);
					
					String title = titleMatchs.get(0).values().iterator().next();
					if (StringUtils.isNotEmpty(title)) {
						StringBuilder sb = new StringBuilder();
						String[] tArray = title.split("-");
						if (tArray != null && tArray.length >= 3) {
							for (int i = 0; i < tArray.length; i++) {
								if (i < tArray.length - 2) {
									sb.append(tArray[i]);
								}
							}
							title = sb.toString();
						}
					}
					
					if (StringUtils.isNotEmpty(distillBody)) {
						isParseSuccess = Boolean.TRUE;
						ParseResult parseResult = new ParseResult();
						parseResult.setId(this.sequenceService.getNextSequence(ParseResult.class.getName()));
						parseResult.setContent(distillBody);
//						parseResult.setCrawlResult(crawlResult);
						parseResult.setCrawlResultId(crawlResult.getId());
						parseResult.setCreateDate(new Date());
						parseResult.setName(title);
						parseResult.setKeywords(meta.get("keywords"));
						parseResult.setDescription(meta.get("description"));
						this.parseResultService.save(parseResult);
						crawlResult.setIsParseSuccess(Boolean.TRUE);
					} else {
						logger.debug(">>FaceYe:empty body of path:" + path);
						crawlResult.setIsParseSuccess(Boolean.FALSE);
					}
				}
			} catch (Exception e) {
				logger.error(">>--->" + e.getMessage());
				crawlResult.setIsParseSuccess(Boolean.FALSE);
			} finally {
				logger.debug(">>FaceYe parse crawlResult:" + isParseSuccess + ",path is:" + path);
				crawlResult.setIsParse(Boolean.TRUE);
				this.crawlResultService.save(crawlResult);
			}
		} else {
			logger.error(">>FaceYe--> content is empty of path:" + path);
		}
    }

	

	@Override
	protected String getDomain() {
		return null;
	}

	@Override
	protected boolean parse(CrawlResult crawlResult, String content, Integer type) {
		return false;
	}

	@Override
	protected void wrapParseResult(ParseResult parseResult) {
	}
	
	
}

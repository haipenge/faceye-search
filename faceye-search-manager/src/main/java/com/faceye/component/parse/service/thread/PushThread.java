package com.faceye.component.parse.service.thread;

import com.faceye.component.parse.service.ParseResultService;
import com.faceye.component.spider.job.thread.BaseThread;
import com.faceye.feature.util.bean.BeanContextUtil;

public class PushThread extends BaseThread {
    private Long [] parseResultIds=null;
   
	public PushThread(Long [] parseResultIds){
		this.setParseResultIds(parseResultIds);
	}
	
	
	@Override
	public void doBusiness() {
		ParseResultService parseResultService=BeanContextUtil.getInstance().getBean(ParseResultService.class);
		if(null!=parseResultService){
			logger.debug(">>Now push thread start.");
			if(null!=parseResultIds && parseResultIds.length>0){
				for(Long parseResultId:parseResultIds){
					parseResultService.saveParseResult2MongoIgnoreFilterWord(parseResultId, true);
					try {
						logger.debug(">>FaceYe-->刚刚推送完文章，休息5秒继续推送");
						Thread.sleep(5000L);
					} catch (InterruptedException e) {
						logger.error(">>FaceYe throws Exception: --->"+e.toString());
					}
				}
			}
		}else{
			logger.debug(">>FaceYe --> parseResultService is empty,please check spring bean factory.");
		}
	}


	public Long[] getParseResultIds() {
		return parseResultIds;
	}


	public void setParseResultIds(Long[] parseResultIds) {
		this.parseResultIds = parseResultIds;
	}
	
	
	

}

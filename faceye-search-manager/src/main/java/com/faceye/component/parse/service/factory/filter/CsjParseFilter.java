package com.faceye.component.parse.service.factory.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.document.Document;
import com.faceye.component.parse.service.factory.ParseFilter;
import com.faceye.component.parse.service.factory.model.csj.Answer;
import com.faceye.component.parse.service.factory.model.csj.Question;
import com.faceye.component.parse.service.factory.model.csj.Result;
import com.faceye.component.parse.service.factory.model.csj.ResultStat;
import com.faceye.component.parse.util.HtmlUtil;
import com.faceye.component.questionnaire.entity.AnswerStat;
import com.faceye.component.questionnaire.entity.Questionnaire;
import com.faceye.component.questionnaire.entity.QuestionnaireType;
import com.faceye.component.questionnaire.service.AnswerService;
import com.faceye.component.questionnaire.service.AnswerStatService;
import com.faceye.component.questionnaire.service.QuestionService;
import com.faceye.component.questionnaire.service.QuestionnaireService;
import com.faceye.component.questionnaire.service.QuestionnaireTypeService;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.service.CrawlResultService;
import com.faceye.component.spider.service.LinkService;
import com.faceye.feature.entity.UploadFile;
import com.faceye.feature.service.UploadFileService;
import com.faceye.feature.util.Json;
import com.faceye.feature.util.PathUtil;
import com.faceye.feature.util.bean.BeanContextUtil;
import com.faceye.feature.util.http.Http;
import com.faceye.feature.util.regexp.RegexpUtil;
import com.faceye.feature.util.storage.StorageHandler;

/**
 * 测试酱解析器
 * 
 * @author haipenge
 *
 */
@Service
public class CsjParseFilter extends BaseParseFilter implements ParseFilter {

	private String imgDir = "cjs";

	@Override
	synchronized public void filter(Document document, CrawlResult crawlResult, String content) {
		// 如果是明细页
		if (crawlResult.getLinkType() == 2) {
			Link link = BeanContextUtil.getInstance().getBean(LinkService.class).get(crawlResult.getLinkId());
			if (link != null) {
				String categoryKey = link.getDistributeChannel();
				// <div class="biaoti">测你的智商有多高？</div>
				String biaotiRegex = "<div class=\"biaoti\">([\\S\\s]*?)</div>";
				String biaoti = "";
				try {
					List<Map<String, String>> biaotiMatchers = RegexpUtil.match(content, biaotiRegex);
					if (CollectionUtils.isNotEmpty(biaotiMatchers)) {
						biaoti = biaotiMatchers.get(0).get("1");
					}
				} catch (Exception e) {
					logger.error(">>FaceYe Throws Exception:", e);
				}
				// 匹配问题描述
				String desc = "";
				String contentRegex = "<label for=\"\" class=\"sr-only\">前言<\\/label>([\\S\\s]*?)<div class=\"buttons\">";
				List<Map<String, String>> descMatchers;
				try {
					descMatchers = RegexpUtil.match(content, contentRegex);
					if (CollectionUtils.isNotEmpty(descMatchers)) {
						desc = descMatchers.get(0).get("1");
						desc = HtmlUtil.getInstance().replaceAll(desc);
						desc = StringUtils.replaceChars(desc, "前言", "");
					}
				} catch (Exception e) {
					logger.error(">>FaceYe Throws Exception:", e);
				}
				String imgUrl = "";
				// <div id="panel1" class="panel-body"><img src="http://cdn.2177.cn/IQ/420a1820ba35a5a1145d1ad4fb032177.jpg" style="margin-top: 10px;width:100%">
				String imgRegexp = "<div id=\"panel1\" class=\"panel-body\"><img src=\"([\\s\\S]*?)\" style=\"margin-top: 10px;width:100%\"\\/>";
				try {
					List<Map<String, String>> imgMatchers = RegexpUtil.match(content, imgRegexp);
					if (CollectionUtils.isNotEmpty(imgMatchers)) {
						imgUrl = imgMatchers.get(0).get("1");
					}
				} catch (Exception e) {
					logger.error(">>FaceYe Throws Exception:", e);
				}
				if (StringUtils.isEmpty(imgUrl)) {
					imgRegexp = "<div id=\"panel1\" class=\"panel-body\"><img src=\"([\\s\\S]*?)\" style=\"margin-top: 10px;width:100%\">";
					try {
						List<Map<String, String>> imgMatchers = RegexpUtil.match(content, imgRegexp);
						if (CollectionUtils.isNotEmpty(imgMatchers)) {
							imgUrl = imgMatchers.get(0).get("1");
						}
					} catch (Exception e) {
						logger.error(">>FaceYe Throws Exception:", e);
					}
				}

				logger.debug(">>FaceYe csj parse result:" + biaoti + ",[]," + desc + ",[]" + imgUrl);
				// 获取文章参数:
				// data:{t: "1512", type:"3"},
				String paramsRegexp = "t: \"([\\d]+)\", type:\"([0-9a-zA-Z]*?)\"";
				String t = "";
				String type = "";
				try {
					List<Map<String, String>> paramsMatchers = RegexpUtil.match(content, paramsRegexp);
					if (CollectionUtils.isNotEmpty(paramsMatchers)) {
						t = paramsMatchers.get(0).get("1");
						type = paramsMatchers.get(0).get("2");
					}
				} catch (Exception e) {
					logger.error(">>FaceYe Throws Exception:", e);
				}

				logger.debug(">>FaceYe --> get params of csj is:t:" + t + ",type:" + type);

				String toggleQuestionUrl = "http://wx.dm15.com/toggleQuestion.php?t=" + t + "&type=" + type;
				logger.debug(">>FaceYe --> Toggle question url is:" + toggleQuestionUrl);
				String questionContent = "";
				if (StringUtils.isNotEmpty(t) && StringUtils.isNotEmpty(type)) {
					questionContent = Http.getInstance().get(toggleQuestionUrl, "UTF-8");
					logger.debug(">>FaceYe question content is:" + questionContent);
				} else {
					logger.debug(">>FaceYe --> have not got params t and type of url:" + document.getLinkUrl());
				}
				QuestionnaireType questionnaireType = null;
				if (StringUtils.isNotEmpty(categoryKey)) {
					questionnaireType = BeanContextUtil.getInstance().getBean(QuestionnaireTypeService.class).getQuestionnaireTypeByType(categoryKey);
					if (questionnaireType == null) {
						questionnaireType = new QuestionnaireType();
						questionnaireType.setType(categoryKey);
						BeanContextUtil.getInstance().getBean(QuestionnaireTypeService.class).save(questionnaireType);
					}
				}
				Questionnaire questionnaire = null;
				questionnaire = new Questionnaire();
				questionnaire.setName(biaoti);
				questionnaire.setRemark(desc);
				questionnaire.setResultTip("");
				questionnaire.setUploadFiles(null);
				if (questionnaireType != null) {
					questionnaire.setQuestionnaireType(questionnaireType);
				}
				BeanContextUtil.getInstance().getBean(QuestionnaireService.class).save(questionnaire);
				if (StringUtils.isNotEmpty(questionContent)) {
					logger.debug(">>FaceYe Question is:" + questionContent);
					Result result = Json.toObject(questionContent, Result.class);
					if (result != null) {
						this.saveQuestion(questionnaire, result, type);
					}
				}
				// 下载并存储图片
				if (StringUtils.isNotEmpty(imgUrl)) {
					byte[] imgBytes = Http.getInstance().getContent(imgUrl, "UTF-8", null);
					if (imgBytes != null && imgBytes.length > 0) {
						UploadFile uploadFile = new UploadFile();
						String filename = PathUtil.generateFileName();
						String storePath = "/" + imgDir + PathUtil.generateDynamicDirs() + filename + ".jpg";
						StorageHandler.getInstance().write(imgBytes, storePath);
						// uploadFile.setKey(Questionnaire.class.getName());
						uploadFile.setStorePath(storePath);
						uploadFile.setTargetEntityClassName(Questionnaire.class.getName());
						uploadFile.setTargetEntityId(questionnaire.getId());
						BeanContextUtil.getInstance().getBean(UploadFileService.class).save(uploadFile);
						questionnaire.getUploadFiles().add(uploadFile);
						BeanContextUtil.getInstance().getBean(QuestionnaireService.class).save(questionnaire);
						// uploadFile.setType(type);
					}
				}
				// 取得答案分析
				// http://wx.dm15.com/toggleResult.php?t=1440&g=2
				String statResultUrl = "http://wx.dm15.com/toggleResult.php?t=" + t;
				List<AnswerStat> answerStats = new ArrayList<AnswerStat>(0);
				for (int i = 1; i <= 10; i++) {
					String statRes = Http.getInstance().get(statResultUrl + "&g=" + i, "UTF-8");
					ResultStat resultStat = Json.toObject(statRes, ResultStat.class);
					if (resultStat != null && StringUtils.isNotEmpty(resultStat.getTitle())) {
						AnswerStat answerStat = new AnswerStat();
						answerStat.setTitle(resultStat.getTitle());
						answerStat.setStitle(resultStat.getStitle());
						answerStat.setRemark(resultStat.getDesc());
						answerStat.setQuestionnaire(questionnaire);
						answerStats.add(answerStat);
					} else {
						break;
					}
				}
				if (CollectionUtils.isNotEmpty(answerStats)) {
					BeanContextUtil.getInstance().getBean(AnswerStatService.class).save(answerStats);
				}
			}
			crawlResult.setIsParse(true);
			BeanContextUtil.getInstance().getBean(CrawlResultService.class).save(crawlResult);
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				logger.error(">>FaceYe Throws Exception:", e);
			}
		}
	}

	/**
	 * 保存解析结果（问题）
	 * 
	 * @param result
	 * @param type
	 * @Desc:
	 * @Author:haipenge
	 * @Date:2016年7月15日 下午9:57:47
	 */
	private void saveQuestion(Questionnaire questionnaire, Result result, String type) {
		if (result.getQuestion() != null) {
			for (Question question : result.getQuestion()) {
				com.faceye.component.questionnaire.entity.Question q = new com.faceye.component.questionnaire.entity.Question();
				q.setName(question.getTitle());
				q.setNum(question.getNextid() + 1);
				q.setQuestionnaire(questionnaire);
				BeanContextUtil.getInstance().getBean(QuestionService.class).save(q);
				int index = 1;
				List<com.faceye.component.questionnaire.entity.Answer> answers = new ArrayList<com.faceye.component.questionnaire.entity.Answer>();
				for (Answer answer : question.getDatalist()) {
					com.faceye.component.questionnaire.entity.Answer a = new com.faceye.component.questionnaire.entity.Answer();
					a.setName(answer.getTitle());
					a.setMark("" + index);
					index++;
					a.setQuestion(q);
					answers.add(a);
				}
				if (CollectionUtils.isNotEmpty(answers)) {
					BeanContextUtil.getInstance().getBean(AnswerService.class).save(answers);
				}
			}
		} else {
			logger.debug(">>FaceYe question is null.");
		}
	}

}

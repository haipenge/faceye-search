package com.faceye.component.search.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.faceye.component.mail.service.MailService;
import com.faceye.component.mail.service.wrap.Mail;
import com.faceye.component.parse.util.RegexpConstants;
import com.faceye.component.search.doc.Article;
import com.faceye.component.search.doc.Email;
import com.faceye.component.search.repository.mongo.EmailRepository;
import com.faceye.component.search.service.EmailService;
import com.faceye.component.search.service.SearchArticleService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;
 
import com.faceye.feature.util.regexp.RegexpUtil;

@Service
public class EmailServiceImpl extends BaseMongoServiceImpl<Email, Long, EmailRepository> implements EmailService {
	@Value("#{property['mail.csdn']}")
	private String csdn = "";
	@Value("#{property['mail.xiaomi']}")
	private String xiaomi = "";
//	@Autowired
//	private MailService mailService = null;
	@Autowired
	private SearchArticleService searchArticleService = null;

	@Autowired
	public EmailServiceImpl(EmailRepository dao) {
		super(dao);
	}

	@Override
	public Email getEmailByAddress(String address) {
		return dao.getEmailByAddress(address);
	}

	@Override
	public void readAndImport() {
		this.readAndImportCsdn();
		this.readAndImportXiaomin();
	}

	private void readAndImportCsdn() {
		String url = "/Users/apple/Downloads/www.csdn.net.sql";
		// String [] lines=FileUtil.readText(url);
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(csdn));
		} catch (FileNotFoundException e1) {
			logger.error(">>FaceYe throws Exception: --->", e1);
		}
		String line;
		List<Email> emails = new ArrayList<Email>(0);
		try {
			while ((line = in.readLine()) != null) {
				try {
					String address = RegexpUtil.match(line, RegexpConstants.E_MAIL).get(0).get("1");
					if (StringUtils.isNotEmpty(address)) {
						address = StringUtils.lowerCase(address);
						address = StringUtils.replacePattern(address, "___csdn_[0-9]+", "");
						Email email = this.getEmailByAddress(address);
						// Email email = null;
						if (email == null) {
							email = new Email();
							email.setFrom(1);
							email.setAddress(address);
							emails.add(email);
						}
					}
				} catch (Exception e) {
					logger.error(">>FaceYe throws Exception: --->", e);
				}
				if (emails.size() > 500) {
					this.save(emails);
					emails = new ArrayList<Email>(0);
				}
			}
		} catch (ServiceException | IOException e1) {
			logger.error(">>FaceYe throws Exception: --->", e1);
		}
		this.save(emails);
		try {
			in.close();
		} catch (IOException e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		}
	}

	private void readAndImportXiaomin() {
		// String [] lines=FileUtil.readText(url);
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(xiaomi));
		} catch (FileNotFoundException e1) {
			logger.error(">>FaceYe throws Exception: --->", e1);
		}
		String line;
		List<Email> emails = new ArrayList<Email>(0);
		try {
			while ((line = in.readLine()) != null) {
				try {
					String address = RegexpUtil.match(line, RegexpConstants.E_MAIL).get(0).get("1");
					if (StringUtils.isNotEmpty(address)) {
						address = StringUtils.lowerCase(address);
						address = StringUtils.replacePattern(address, "___csdn_[0-9]+", "");
						Email email = this.getEmailByAddress(address);
						// Email email = null;
						if (email == null) {
							email = new Email();
							email.setFrom(2);
							email.setAddress(address);
							emails.add(email);
						}
					}
				} catch (Exception e) {
					logger.error(">>FaceYe throws Exception: --->", e);
				}
				if (emails.size() > 500) {
					this.save(emails);
					emails = new ArrayList<Email>(0);
				}
			}
		} catch (ServiceException | IOException e1) {
			logger.error(">>FaceYe throws Exception: --->", e1);
		}
		this.save(emails);
		try {
			in.close();
		} catch (IOException e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		}
	}

	/**
	 * 批量发送邮件
	 */
	@Override
	public void send() {
		List<Email> emails = this.getEmails(1, 1);
		if (CollectionUtils.isNotEmpty(emails)) {
			for (Email email : emails) {
//				this.send(email);
				try {
					Thread.sleep(2 * 1000L);
				} catch (InterruptedException e) {
					logger.error(">>FaceYe throws Exception: --->", e);
				}
			}
		}
	}

	/**
	 * 电子邮件
	 * @todo
	 * @param start
	 * @param size
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年7月31日
	 */
	private List<Email> getEmails(Integer start, Integer size) {
		List<Email> emails = new ArrayList<Email>();
		Map searchParams = new HashMap();
		searchParams.put("EQ|address", "82676683@qq.com");
		emails = this.getPage(searchParams, 1, 1).getContent();
		return emails;
	}

	@Override
	public void send(Long id) {
		Email email = this.get(id);
		this.send(email);
	}

//	@Override
//	public void send(Email email) {
//		List<Article> articles = this.searchArticleService.getPage(null, 1, 20).getContent();
//		Map body = new HashMap();
//		body.put("articles", articles);
//		Mail mail = new Mail();
//		mail.setSubject("This is a article test template.");
//		mail.setTemplate("faceye.net.mail.sender.template");
//		mail.setBody(body);
//		mail.setReceiver(email.getAddress());
//		this.mailService.sendMail(mail);
//	}
}
/**@generate-service-source@**/

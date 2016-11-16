package com.faceye.component.parse.service.factory.filter;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.service.document.Document;
import com.faceye.component.parse.service.factory.ParseFilter;
import com.faceye.component.parse.util.RegexpConstants;
import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.search.doc.Email;
import com.faceye.component.search.service.EmailService;
import com.faceye.component.spider.doc.CrawlResult;

@Service
public class EmailParseFilter extends BaseParseFilter implements ParseFilter {
	@Autowired
	private EmailService emailService = null;

	@Override
	public void filter(Document document, CrawlResult crawlResult, String content) {
		try {
			List<Map<String, String>> emails = RegexpUtil.match(content, RegexpConstants.E_MAIL);
			if (CollectionUtils.isNotEmpty(emails)) {
				for (Map<String, String> match : emails) {
					String address = match.get("1");
					address=StringUtils.trim(address);
					Email email = this.emailService.getEmailByAddress(address);
					if (null == email) {
						email = new Email();
						email.setAddress(address);
						this.emailService.save(email);
					}
				}
			}
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		}

	}

}

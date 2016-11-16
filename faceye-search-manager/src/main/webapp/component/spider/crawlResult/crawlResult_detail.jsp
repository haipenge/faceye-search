<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/spider/crawlResult/crawlResult.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/spider/crawlResult/crawlResult.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/lib/zeroclipboard/ZeroClipboard.min.js"/>"></script>
<div class="page-head">
	<h2>
		<fmt:message key="spider.crawlResult.detail"></fmt:message>
	</h2>
</div>

<div class="cl-mcont">
	<div class="block-flat">
		<div class="content">
			<input type="hidden" name="crawlResultId" value="${crawlResult.id}">
			<textarea rows="4" cols="6" class="form-control" name="regexp"></textarea>
		</div>
		<div class="content">
			<c:forEach items="${matcherConfigs}" var="matcherConfig">
				<p>${matcherConfig.name}:<span class="regexp"><c:out value="${matcherConfig.regexp}" escapeXml="true" /></span>
				&nbsp;&nbsp;
				</p>
			</c:forEach>
		</div>
		<div class="content">
		  <button type="button" class="btn btn-info pull-right regexp-test"><fmt:message key="spider.crawlResult.parse.test"/></button>
		</div>
		
		<div class="content" id="msg"></div>
			<div class="content">
				<p><fmt:message key="spider.crawlResult.isParse"/>: <f:boolean value="${crawlResult.isParse}"/></p>
				<p><fmt:message key="spider.crawlResult.parse.result"/>:<f:boolean value="${crawlResult.isParseSuccess}"/></p>
				<p><fmt:message key="spider.crawlResult.path"/>:${crawlResult.storePath}</p>
				<p>
					<a href="${crawlResult.linkUrl}" target="_blank">${crawlResult.linkUrl}</a>
				</p>
			</div>
		<div class="content" id="copy-msg" style="display:none;"></div>
		<div class="content">
		<button id="copy" data-clipboard-target="code" type="button" class="btn btn-success-outline btn-sm" style="margin-left: 15px;">
						<fmt:message key="global.copy" />
					</button>
		</div>
			<div class="content">
				<pre id="code" style="width:1200px;word-break:break-all;word-break:keep-all;word-wrap:break-word;">
			     <c:out value="${html}" escapeXml="true" />
			    </pre>
			</div>
			<tr>
	<td><fmt:message key="spider.crawlResult.isWeixin"></fmt:message></td>
	<td>${crawlResult.isWeixin}</td>
</tr>
<!--@generate-entity-jsp-property-detail@-->

	</div>
</div>
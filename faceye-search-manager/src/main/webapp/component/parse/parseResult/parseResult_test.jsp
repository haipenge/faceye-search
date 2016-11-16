<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/parse/parseResult/parseResult.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/parse/parseResult/parseResult.js"/>"></script>
<div class="page-head">
	<h2>
		<fmt:message key="parse.parseResult.manager"></fmt:message>
		<a class="btn btn-primary" href="<c:url value="/parse/parseResult/input"/>"> <fmt:message
				key="parse.parseResult.add"></fmt:message>
		</a>
	</h2>
</div>
<div class="cl-mcont">
	<div class="block-flat" style="padding-top: 0px;">
		<div class="content">
			<h3>ID:${document.crawlResult.id},Link Type:${document.crawlResult.linkType}</h3>
			<p>
				Link Url is:<a href="${document.crawlResult.linkUrl }">${document.crawlResult.linkUrl }</a>
				&nbsp;&nbsp;&nbsp;&nbsp;|<a class="btn btn-success"
					href="<c:url value="/spider/crawlResult/detail/${document.crawlResult.id}"/>">To Parse Test</a>
			</p>
			<hr>
			<div class="content">
				<c:forEach items="${document.links}" var="link">
					<p>${link.url}</p>
				</c:forEach>
			</div>
		</div>
		<hr>
		<div class="content">
			<h3>${document.title}</h3>
			<pre style="width:900px;word-break:break-all;word-break:keep-all;word-wrap:break-word;">
		  <c:out value="${document.body}" escapeXml="true" />
		 </pre>
		</div>
	</div>
</div>
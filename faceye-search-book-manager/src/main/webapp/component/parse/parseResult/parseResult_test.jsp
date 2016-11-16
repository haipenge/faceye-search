<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/parse/parseResult/parseResult.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/parse/parseResult/parseResult.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="parse.parseResult.manager"></fmt:message>
			<a class="btn btn-success btn-sm pull-right" href="<c:url value="/parse/parseResult/input"/>"> <fmt:message
					key="parse.parseResult.add"></fmt:message>
			</a>
		</div>
	</div>
	<div class="panel-body">
		<h3>ID:${document.crawlResult.id},Link Type:${document.crawlResult.linkType}</h3>
		<p>Link Url is:<a href="${document.crawlResult.linkUrl }">${document.crawlResult.linkUrl }</a> &nbsp;&nbsp;&nbsp;&nbsp;|<a class="btn btn-success" href="<c:url value="/spider/crawlResult/detail/${document.crawlResult.id }"/>">To Parse Test</a></p>
		<hr>
		<div class="content">
			<c:forEach items="${document.links}" var="link">
				<p>${link.url}</p>
			</c:forEach>
		</div>
		<hr>
		<h3>${document.title}</h3>
		<div class="content">
			<pre>
		 
		  <c:out value="${document.body}" escapeXml="true" />
		 </pre>
		</div>
	</div>
</div>
<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/search/searchArticle/article.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/searchArticle/article.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="search.searchArticle.detail"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
		<p>${searchArticle.name } &nbsp;&nbsp;|&nbsp;&nbsp;<fmt:formatDate value="${searchArticle.createDate}" type="date" pattern="yyyy-MM-dd"/></p>
		<div class="content">${searchArticle.content}</div>
	</div>
</div>
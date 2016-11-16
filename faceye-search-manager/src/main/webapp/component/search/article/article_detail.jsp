<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/component/search/article/article.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/article/article.js"/>"></script>
<div class="page-header">
	<h3>${article.name }<small>${article.category} -- ${article.createDate}</small></h3>
</div>
<div class="content">${article.content}</div>
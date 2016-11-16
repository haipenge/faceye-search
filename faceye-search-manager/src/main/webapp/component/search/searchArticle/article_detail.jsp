<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/search/searchArticle/searchArticle.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/searchArticle/searchArticle.js"/>"></script>

<div class="block-flat">
	<div class="header">
	    <a href="<c:url value="/search/searchArticle/home"/>">Articles</a>
		<h3>${searchArticle.name }<small>${searchArticle.categoryName}&nbsp;&nbsp;<fmt:formatDate
					value="${searchArticle.createDate}" type="date" pattern="yyyy-MM-dd" />
					&nbsp;&nbsp;
					<a href="<c:url value="/search/searchArticle/edit/${searchArticle.id}"/>">Edit</a>
					<a href="<c:url value="/search/searchArticle/remove/${searchArticle.id}"/>">Remove</a>
			</small>
		</h3>
	</div>
	<div class="content">${searchArticle.content}</div>
</div>
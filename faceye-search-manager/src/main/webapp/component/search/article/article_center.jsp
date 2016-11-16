<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/component/search/article/article.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/article/article.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="search.article.manager"></fmt:message>
			<a class="btn btn-success btn-sm pull-right" href="<c:url value="/search/article/input"/>"> <fmt:message
					key="search.article.add"></fmt:message>
			</a>
		</div>
	</div>
	<div class="panel-body">
		<div class="content">
			<form action="<c:url value="/search/article/home"/>" method="post" role="form" class="form-horizontal"></form>
		</div>
		<div classs="table-responsive">
			<table class="table table-bordered table-hover">
				<tr>
					<td><fmt:message key='search.article.name'></fmt:message></td>
					<td><fmt:message key='search.article.content'></fmt:message></td>
					<td><fmt:message key='search.article.createDate'></fmt:message></td>
					<td><fmt:message key='search.article.category'></fmt:message></td>
					<!--@generate-entity-jsp-property-desc@-->
					<td><fmt:message key="global.edit"></fmt:message></td>
					<td><fmt:message key="global.remove"></fmt:message></td>
				</tr>
				<c:forEach items="${page.content}" var="article">
					<tr>
						<td>${article.name}</td>
						<td>${article.content}</td>
						<td>${article.createDate}</td>
						<td>${article.category}</td>
						<!--@generate-entity-jsp-property-value@-->
						<td><a href="<c:url value="/search/article/edit/${article.id}"/>"> <fmt:message key="global.edit"></fmt:message>
						</a></td>
						<td><a href="<c:url value="/search/article/remove/${article.id}"/>"> <fmt:message key="global.remove"></fmt:message>
						</a></td>
					<tr>
				</c:forEach>
			</table>
		</div>
		<f:page page="${page}" url="/search/article/home" params="<%=request.getParameterMap()%>" />
	</div>
</div>
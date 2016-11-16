<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/search/articleCategory/articleCategory.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/articleCategory/articleCategory.js"/>"></script>


<div class="page-head">
	<h2>
		<fmt:message key="search.articleCategory.manager"></fmt:message>
		<a class="btn btn-primary" href="<c:url value="/search/articleCategory/input"/>"> <fmt:message
				key="search.articleCategory.add"></fmt:message>
		</a>
	</h2>
	<div class="content">
		<form action="<c:url value="/search/articleCategory/home"/>" method="post" role="form" class="form-horizontal">
			<fieldset>
				<div class="form-group">
					<div class="col-md-1">
						<input type="text" name="LIKE|name" placeholder="<fmt:message key="search.articleCategory.name"></fmt:message>"
							class="form-control input-sm">
					</div>
					<!--@generate-entity-jsp-query-detail@-->
					<div class="col-md-1">
						<button type="submit" class="btn btn-sm btn-default">Search</button>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>
<div class="cl-mcont">
	<div class="block-flat">
		<div class="content">
			<div classs="table-responsive">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th><fmt:message key='search.articleCategory.name'></fmt:message></th>
							<th><fmt:message key='search.articleCategory.orderIndex'></fmt:message></th>
							<th><fmt:message key='search.articleCategory.alias'></fmt:message></th>
							<th><fmt:message key='search.articleCategory.keywords'></fmt:message></th>
							<!--@generate-entity-jsp-property-desc@-->
							<th><fmt:message key="global.edit"></fmt:message></th>
							<th><fmt:message key="global.remove"></fmt:message></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.content}" var="articleCategory">
							<tr>
								<td>${articleCategory.name}</td>
								<td>${articleCategory.orderIndex}</td>
								<td>${articleCategory.alias}</td>
								<td>${articleCategory.keywords}</td>
								<!--@generate-entity-jsp-property-value@-->
								<td><a href="<c:url value="/search/articleCategory/edit/${articleCategory.id}"/>"> <fmt:message
											key="global.edit"></fmt:message>
								</a></td>
								<td><a href="<c:url value="/search/articleCategory/remove/${articleCategory.id}"/>"> <fmt:message
											key="global.remove"></fmt:message>
								</a></td>
							<tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<f:page page="${page}" url="/search/articleCategory/home" params="<%=request.getParameterMap()%>" />
		</div>
	</div>
</div>

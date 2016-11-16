<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/weixin/weixin/weixin.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/weixin/weixin/weixin.js"/>"></script>
<div class="page-head">
	<h2>
		<fmt:message key="weixin.weixin.manager"></fmt:message>
		<a class="btn btn-primary" href="<c:url value="/weixin/weixin/input"/>"> <fmt:message key="weixin.weixin.add"></fmt:message>
		</a>
	</h2>
</div>
<div class="cl-mcont">
	<!-- 
	<div class="header">
		<h2>
			<fmt:message key="security.role.manager"></fmt:message>
		</h2>
		<a class="btn btn-default" href="<c:url value="/security/role/input"/>"> <fmt:message key="security.role.add"></fmt:message>
		</a>
	</div>
	 -->
	<div class="block-flat">
		<div class="content">
			<form action="<c:url value="/weixin/weixin/home"/>" method="post" role="form" class="form-horizontal">
				<fieldset>
					<div class="form-group">

						<div class="col-md-1">
							<input type="text" name="EQ|name" value="${searchParams.name}"
								placeholder="<fmt:message key="weixin.weixin.name"></fmt:message>" class="form-control input-sm">
						</div>

						<div class="col-md-1">
							<input type="text" name="EQ|article.$id" value="${searchParams.articleId}"
								placeholder="<fmt:message key="weixin.weixin.articleId"></fmt:message>" class="form-control input-sm">
						</div>
						<!--@generate-entity-jsp-query-detail@-->
						<div class="col-md-1">
							<button type="submit" class="btn btn-sm btn-default">
								<fmt:message key="global.search"></fmt:message>
							</button>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="content">
			<div classs="table-responsive">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th><fmt:message key='weixin.weixin.name'></fmt:message></th>
							<th><fmt:message key='weixin.weixin.articleId'></fmt:message></th>
							<th>Previe</th>
							<!--@generate-entity-jsp-property-desc@-->
							<th><fmt:message key="global.edit"></fmt:message></th>
							<th><fmt:message key="global.remove"></fmt:message></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.content}" var="weixin">
							<tr>
								<td>${weixin.name}</td>
								<td>${weixin.article.id}</td>
								<td><a href="<c:url value="/weixin/weixin/detail/${weixin.id}"/>">Preview</a></td>
								<!--@generate-entity-jsp-property-value@-->
								<td><a href="<c:url value="/weixin/weixin/edit/${weixin.id}"/>"> <fmt:message key="global.edit"></fmt:message>
								</a></td>
								<td><a href="<c:url value="/weixin/weixin/remove/${weixin.id}"/>"> <fmt:message key="global.remove"></fmt:message>
								</a></td>
							<tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<f:page page="${page}" url="/weixin/weixin/home" params="<%=request.getParameterMap()%>" />
		</div>
	</div>
</div>

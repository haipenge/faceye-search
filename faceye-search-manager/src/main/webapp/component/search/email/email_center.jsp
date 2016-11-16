<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/search/email/email.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/email/email.js"/>"></script>
<div class="page-head">
	<h2>
		<fmt:message key="search.email.manager"></fmt:message>
		<a class="btn btn-primary" href="<c:url value="/search/email/input"/>"> <fmt:message key="search.email.add"></fmt:message>
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
			<form action="<c:url value="/search/email/home"/>" method="post" role="form" class="form-horizontal">
				<fieldset>
					<div class="form-group">

						<div class="col-md-2">
							<input type="text" name="like|address" value="${searchParams.address}"
								placeholder="<fmt:message key="search.email.address"></fmt:message>" class="form-control input-sm">
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
		<div id="msg"></div>
		<div class="content">
			<button class="btn btn-primary" id="send-mails">Send Mails</button>
		</div>
		<div class="content">
			<div classs="table-responsive">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th><input type="checkbox" name="check-all"></th>
							<th><fmt:message key='search.email.address'></fmt:message></th>
							<!--@generate-entity-jsp-property-desc@-->
							<th><fmt:message key="global.edit"></fmt:message></th>
							<th><fmt:message key="global.remove"></fmt:message></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.content}" var="email">
							<tr>
								<td><input type="checkbox" name="check-single" value="${email.id}"></td>
								<td>${email.address}</td>
								<!--@generate-entity-jsp-property-value@-->
								<td><a href="<c:url value="/search/email/edit/${email.id}"/>"> <fmt:message key="global.edit"></fmt:message>
								</a></td>
								<td><a href="<c:url value="/search/email/remove/${email.id}"/>"> <fmt:message key="global.remove"></fmt:message>
								</a></td>
							<tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<f:page page="${page}" url="/search/email/home" params="<%=request.getParameterMap()%>" />
		</div>
	</div>
</div>

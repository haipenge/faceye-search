<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/component/spider/site/site.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/spider/site/site.js"/>"></script>



<div class="page-head">
	<h2>
		<fmt:message key="spider.site.manager"></fmt:message>
		<a class="btn btn-primary" href="<c:url value="/spider/site/input"/>"> <fmt:message key="spider.site.add"></fmt:message>
		</a>
	</h2>
</div>
<div class="cl-mcont">
	<div class="block-flat">
		<div class="content">
			<div classs="table-responsive">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th><fmt:message key='spider.site.name'></fmt:message></th>
							<td>Matchers</td>
							<th><fmt:message key='spider.site.isUseSuperParse'></fmt:message></th>
							<th><fmt:message key='spider.site.threadCount'></fmt:message></th>
							<!--@generate-entity-jsp-property-desc@-->
							<th><fmt:message key="global.edit"></fmt:message></th>
							<th><fmt:message key="global.remove"></fmt:message></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.content}" var="site">
							<tr>
								<td>${site.name}</td>
								<td><a href="<c:url value="/spider/matcherConfig/home?EQ|siteId=${site.id}"/>">Matchers</a></td>
								<td>${site.isUseSuperParse}</td>
								<td>${site.threadCount}</td>
								<!--@generate-entity-jsp-property-value@-->
								<td><a href="<c:url value="/spider/site/edit/${site.id}"/>"> <fmt:message key="global.edit"></fmt:message>
								</a></td>
								<td><a href="<c:url value="/spider/site/remove/${site.id}"/>"> <fmt:message key="global.remove"></fmt:message>
								</a></td>
							<tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<f:page page="${page}" url="/spider/site/home" params="<%=request.getParameterMap()%>" />
		</div>
	</div>
</div>

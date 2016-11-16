<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/index/analyzerWord/analyzerWord.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/index/analyzerWord/analyzerWord.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="index.analyzerWord.manager"></fmt:message>
			<a class="btn btn-success btn-sm pull-right" href="<c:url value="/index/analyzerWord/input"/>"> <fmt:message
					key="index.analyzerWord.add"></fmt:message>
			</a>
		</div>
	</div>
	<div class="panel-body">
		<div class="content">
			<form action="<c:url value="/index/analyzerWord/home"/>" method="post" role="form" class="form-horizontal"></form>
		</div>
		<div classs="table-responsive">
			<table class="table table-bordered table-hover">
				<tr>
					<td><fmt:message key='index.analyzerWord.word'></fmt:message></td>
					<td><fmt:message key='index.analyzerWord.wordCount'></fmt:message></td>
					<!--@generate-entity-jsp-property-desc@-->
					<td><fmt:message key="global.edit"></fmt:message></td>
					<td><fmt:message key="global.remove"></fmt:message></td>
				</tr>
				<c:forEach items="${page.content}" var="analyzerWord">
					<tr>
						<td>${analyzerWord.word}</td>
						<td>${analyzerWord.wordCount}</td>
						<!--@generate-entity-jsp-property-value@-->
						<td><a href="<c:url value="/index/analyzerWord/edit/${analyzerWord.id}"/>"> <fmt:message
									key="global.edit"></fmt:message>
						</a></td>
						<td><a href="<c:url value="/index/analyzerWord/remove/${analyzerWord.id}"/>"> <fmt:message
									key="global.remove"></fmt:message>
						</a></td>
					<tr>
				</c:forEach>
			</table>
		</div>
		<f:page page="${page}" url="/index/analyzerWord/home" params="<%=request.getParameterMap()%>" />
	</div>
</div>
<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/js/component/parse/filterWord/filterWord.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/parse/filterWord/filterWord.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="parse.filterWord.manager"></fmt:message>
			<a class="btn btn-success btn-sm pull-right" href="<c:url value="/parse/filterWord/input"/>">
			  <fmt:message key="parse.filterWord.add"></fmt:message>
			</a>
		</div>
	</div>
	<div class="panel-body">
	    <div class="content">
	      	<form action="<c:url value="/parse/filterWord/home"/>" method="post" role="form" class="form-horizontal">
	      	   
	      	</form>
	    </div>
		<div classs="table-responsive">
			<table class="table table-bordered table-hover">
				<tr>
					<td><fmt:message key='parse.filterWord.word'></fmt:message></td>   
 <!--@generate-entity-jsp-property-desc@-->
					<td><fmt:message key="global.edit"></fmt:message></td>
					<td><fmt:message key="global.remove"></fmt:message></td>
				</tr>
				<c:forEach items="${page.content}" var="filterWord">
					<tr>
						<td>${filterWord.word}</td>   
 <!--@generate-entity-jsp-property-value@-->
						<td>
						   <a href="<c:url value="/parse/filterWord/edit/${filterWord.id}"/>">
						     <fmt:message key="global.edit"></fmt:message>
						   </a>
						 </td>
						<td>
						   <a href="<c:url value="/parse/filterWord/remove/${filterWord.id}"/>">
						       <fmt:message key="global.remove"></fmt:message>
						   </a>
						</td>
					<tr>
				</c:forEach>
			</table>
		</div>
		<f:page page="${page}" url="/parse/filterWord/home" params="<%=request.getParameterMap()%>" />
	</div>
</div>
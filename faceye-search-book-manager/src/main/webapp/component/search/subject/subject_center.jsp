<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/component/search/subject/subject.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/subject/subject.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="search.subject.manager"></fmt:message>
			<a class="btn btn-success btn-sm pull-right" href="<c:url value="/search/subject/input"/>">
			  <fmt:message key="search.subject.add"></fmt:message>
			</a>
		</div>
	</div>
	<div class="panel-body">
	    <div class="content">
	      	<form action="<c:url value="/search/subject/home"/>" method="post" role="form" class="form-horizontal">
	      	   
	      	</form>
	    </div>
		<div classs="table-responsive">
			<table class="table table-bordered table-hover">
				<tr>
					<td><fmt:message key='search.subject.name'></fmt:message></td>   
 <td><fmt:message key='search.subject.alias'></fmt:message></td>   
 <!--@generate-entity-jsp-property-desc@-->
					<td><fmt:message key="global.edit"></fmt:message></td>
					<td><fmt:message key="global.remove"></fmt:message></td>
				</tr>
				<c:forEach items="${page.content}" var="subject">
					<tr>
						<td>${subject.name}</td>   
 <td>${subject.alias}</td>   
 <!--@generate-entity-jsp-property-value@-->
						<td>
						   <a href="<c:url value="/search/subject/edit/${subject.id}"/>">
						     <fmt:message key="global.edit"></fmt:message>
						   </a>
						 </td>
						<td>
						   <a href="<c:url value="/search/subject/remove/${subject.id}"/>">
						       <fmt:message key="global.remove"></fmt:message>
						   </a>
						</td>
					<tr>
				</c:forEach>
			</table>
		</div>
		<f:page page="${page}" url="/search/subject/home" params="<%=request.getParameterMap()%>" />
	</div>
</div>
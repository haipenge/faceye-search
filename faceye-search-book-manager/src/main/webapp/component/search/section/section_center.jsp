<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/js/component/search/section/section.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/section/section.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="search.section.manager"></fmt:message>
			<a class="btn btn-success btn-sm pull-right" href="<c:url value="/search/section/input"/>">
			  <fmt:message key="search.section.add"></fmt:message>
			</a>
		</div>
	</div>
	<div class="panel-body">
	    <div class="content">
	      	<form action="<c:url value="/search/section/home"/>" method="post" role="form" class="form-horizontal">
	      	   
	      	</form>
	    </div>
		<div classs="table-responsive">
			<table class="table table-bordered table-hover">
				<tr>
					<td><fmt:message key='search.section.name'></fmt:message></td>   
 <td><fmt:message key='search.section.indexNum'></fmt:message></td>   
 <td><fmt:message key='search.section.createDate'></fmt:message></td>   
 <!--@generate-entity-jsp-property-desc@-->
					<td><fmt:message key="global.edit"></fmt:message></td>
					<td><fmt:message key="global.remove"></fmt:message></td>
				</tr>
				<c:forEach items="${page.content}" var="section">
					<tr>
						<td>${section.name}</td>   
 <td>${section.indexNum}</td>   
 <td>${section.createDate}</td>   
 <!--@generate-entity-jsp-property-value@-->
						<td>
						   <a href="<c:url value="/search/section/edit/${section.id}"/>">
						     <fmt:message key="global.edit"></fmt:message>
						   </a>
						 </td>
						<td>
						   <a href="<c:url value="/search/section/remove/${section.id}"/>">
						       <fmt:message key="global.remove"></fmt:message>
						   </a>
						</td>
					<tr>
				</c:forEach>
			</table>
		</div>
		<f:page page="${page}" url="/search/section/home" params="<%=request.getParameterMap()%>" />
	</div>
</div>
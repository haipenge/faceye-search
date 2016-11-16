<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/js/component/search/category/category.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/category/category.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="search.category.manager"></fmt:message>
			<a class="btn btn-success btn-sm pull-right" href="<c:url value="/search/category/input"/>">
			  <fmt:message key="search.category.add"></fmt:message>
			</a>
		</div>
	</div>
	<div class="panel-body">
	    <div class="content">
	      	<form action="<c:url value="/search/category/home"/>" method="post" role="form" class="form-horizontal">
	      	   
	      	</form>
	    </div>
		<div classs="table-responsive">
			<table class="table table-bordered table-hover">
				<tr>
					<td><fmt:message key='search.category.name'></fmt:message></td>   
 <td><fmt:message key='search.category.orderIndex'></fmt:message></td>   
 <!--@generate-entity-jsp-property-desc@-->
					<td><fmt:message key="global.edit"></fmt:message></td>
					<td><fmt:message key="global.remove"></fmt:message></td>
				</tr>
				<c:forEach items="${page.content}" var="category">
					<tr>
						<td>${category.name}</td>   
 <td>${category.orderIndex}</td>   
 <!--@generate-entity-jsp-property-value@-->
						<td>
						   <a href="<c:url value="/search/category/edit/${category.id}"/>">
						     <fmt:message key="global.edit"></fmt:message>
						   </a>
						 </td>
						<td>
						   <a href="<c:url value="/search/category/remove/${category.id}"/>">
						       <fmt:message key="global.remove"></fmt:message>
						   </a>
						</td>
					<tr>
				</c:forEach>
			</table>
		</div>
		<f:page page="${page}" url="/search/category/home" params="<%=request.getParameterMap()%>" />
	</div>
</div>
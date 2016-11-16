<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/component/book/book/book.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/book/book/book.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="book.book.manager"></fmt:message>
			<a class="btn btn-success btn-sm pull-right" href="<c:url value="/book/book/input"/>">
			  <fmt:message key="book.book.add"></fmt:message>
			</a>
		</div>
	</div>
	<div class="panel-body">
	    <div class="content">
	      	<form action="<c:url value="/book/book/home"/>" method="post" role="form" class="form-horizontal">
	      	   
	      	</form>
	    </div>
		<div classs="table-responsive">
			<table class="table table-bordered table-hover">
				<tr>
					<td><fmt:message key='book.book.name'></fmt:message></td>   
 <td><fmt:message key='book.book.author'></fmt:message></td>   
 <td><fmt:message key='book.book.pic'></fmt:message></td>   
 <td><fmt:message key='book.book.categoryId'></fmt:message></td>   
 <td><fmt:message key='book.book.categoryName'></fmt:message></td>   
 <td><fmt:message key='book.book.content'></fmt:message></td>   
 <!--@generate-entity-jsp-property-desc@-->
					<td><fmt:message key="global.edit"></fmt:message></td>
					<td><fmt:message key="global.remove"></fmt:message></td>
				</tr>
				<c:forEach items="${page.content}" var="book">
					<tr>
						<td>${book.name}</td>   
 <td>${book.author}</td>   
 <td>${book.pic}</td>   
 <td>${book.categoryId}</td>   
 <td>${book.categoryName}</td>   
 <td>${book.content}</td>   
 <!--@generate-entity-jsp-property-value@-->
						<td>
						   <a href="<c:url value="/book/book/edit/${book.id}"/>">
						     <fmt:message key="global.edit"></fmt:message>
						   </a>
						 </td>
						<td>
						   <a href="<c:url value="/book/book/remove/${book.id}"/>">
						       <fmt:message key="global.remove"></fmt:message>
						   </a>
						</td>
					<tr>
				</c:forEach>
			</table>
		</div>
		<f:page page="${page}" url="/book/book/home" params="<%=request.getParameterMap()%>" />
	</div>
</div>
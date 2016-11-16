<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/component/book/section/section.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/book/section/section.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="book.section.manager"></fmt:message>
			<a class="btn btn-success btn-sm pull-right" href="<c:url value="/book/section/input"/>">
			  <fmt:message key="book.section.add"></fmt:message>
			</a>
		</div>
	</div>
	<div class="panel-body">
	    <div class="content">
	      	<form action="<c:url value="/book/section/home"/>" method="post" role="form" class="form-horizontal">
	      	   
	      	</form>
	    </div>
		<div classs="table-responsive">
			<table class="table table-bordered table-hover">
				<tr>
					<td><fmt:message key='book.section.name'></fmt:message></td>   
 <td><fmt:message key='book.section.createDate'></fmt:message></td>   
 <td><fmt:message key='book.section.indexNum'></fmt:message></td>   
 <td><fmt:message key='book.section.content'></fmt:message></td>   
 <td><fmt:message key='book.section.bookId'></fmt:message></td>   
 <td><fmt:message key='book.section.bookName'></fmt:message></td>   
 <!--@generate-entity-jsp-property-desc@-->
					<td><fmt:message key="global.edit"></fmt:message></td>
					<td><fmt:message key="global.remove"></fmt:message></td>
				</tr>
				<c:forEach items="${page.content}" var="section">
					<tr>
						<td>${section.name}</td>   
 <td>${section.createDate}</td>   
 <td>${section.indexNum}</td>   
 <td>${section.content}</td>   
 <td>${section.bookId}</td>   
 <td>${section.bookName}</td>   
 <!--@generate-entity-jsp-property-value@-->
						<td>
						   <a href="<c:url value="/book/section/edit/${section.id}"/>">
						     <fmt:message key="global.edit"></fmt:message>
						   </a>
						 </td>
						<td>
						   <a href="<c:url value="/book/section/remove/${section.id}"/>">
						       <fmt:message key="global.remove"></fmt:message>
						   </a>
						</td>
					<tr>
				</c:forEach>
			</table>
		</div>
		<f:page page="${page}" url="/book/section/home" params="<%=request.getParameterMap()%>" />
	</div>
</div>
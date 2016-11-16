<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/component/push/pushRecord/pushRecord.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/push/pushRecord/pushRecord.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="push.pushRecord.manager"></fmt:message>
			<a class="btn btn-success btn-sm pull-right" href="<c:url value="/push/pushRecord/input"/>">
			  <fmt:message key="push.pushRecord.add"></fmt:message>
			</a>
		</div>
	</div>
	<div class="panel-body">
	    <div class="content">
	      	<form action="<c:url value="/push/pushRecord/home"/>" method="post" role="form" class="form-horizontal">
	      	   
	      	</form>
	    </div>
		<div classs="table-responsive">
			<table class="table table-bordered table-hover">
				<tr>
					<td><fmt:message key='push.pushRecord.articleId'></fmt:message></td>   
 <!--@generate-entity-jsp-property-desc@-->
					<td><fmt:message key="global.edit"></fmt:message></td>
					<td><fmt:message key="global.remove"></fmt:message></td>
				</tr>
				<c:forEach items="${page.content}" var="pushRecord">
					<tr>
						<td>${pushRecord.articleId}</td>   
 <!--@generate-entity-jsp-property-value@-->
						<td>
						   <a href="<c:url value="/push/pushRecord/edit/${pushRecord.id}"/>">
						     <fmt:message key="global.edit"></fmt:message>
						   </a>
						 </td>
						<td>
						   <a href="<c:url value="/push/pushRecord/remove/${pushRecord.id}"/>">
						       <fmt:message key="global.remove"></fmt:message>
						   </a>
						</td>
					<tr>
				</c:forEach>
			</table>
		</div>
		<f:page page="${page}" url="/push/pushRecord/home" params="<%=request.getParameterMap()%>" />
	</div>
</div>
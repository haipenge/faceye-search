<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/component/parse/image/image.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/parse/image/image.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="parse.image.manager"></fmt:message>
			<a class="btn btn-success btn-sm pull-right" href="<c:url value="/parse/image/input"/>">
			  <fmt:message key="parse.image.add"></fmt:message>
			</a>
		</div>
	</div>
	<div class="panel-body">
	    <div class="content">
	      	<form action="<c:url value="/parse/image/home"/>" method="post" role="form" class="form-horizontal">
	      	   
	      	</form>
	    </div>
		<div classs="table-responsive">
			<table class="table table-bordered table-hover">
				<tr>
					<td><fmt:message key='parse.image.storePath'></fmt:message></td>   
 <td><fmt:message key='parse.image.sourceUrl'></fmt:message></td>   
 <td><fmt:message key='parse.image.parseResultId'></fmt:message></td>   
 <td><fmt:message key='parse.image.linkId'></fmt:message></td>   
 <td><fmt:message key='parse.image.crawlResultId'></fmt:message></td>   
 <!--@generate-entity-jsp-property-desc@-->
					<td><fmt:message key="global.edit"></fmt:message></td>
					<td><fmt:message key="global.remove"></fmt:message></td>
				</tr>
				<c:forEach items="${page.content}" var="image">
					<tr>
						<td>${image.storePath}</td>   
 <td>${image.sourceUrl}</td>   
 <td>${image.parseResultId}</td>   
 <td>${image.linkId}</td>   
 <td>${image.crawlResultId}</td>   
 <!--@generate-entity-jsp-property-value@-->
						<td>
						   <a href="<c:url value="/parse/image/edit/${image.id}"/>">
						     <fmt:message key="global.edit"></fmt:message>
						   </a>
						 </td>
						<td>
						   <a href="<c:url value="/parse/image/remove/${image.id}"/>">
						       <fmt:message key="global.remove"></fmt:message>
						   </a>
						</td>
					<tr>
				</c:forEach>
			</table>
		</div>
		<f:page page="${page}" url="/parse/image/home" params="<%=request.getParameterMap()%>" />
	</div>
</div>
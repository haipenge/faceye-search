<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/weixin/weixin/weixin.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/weixin/weixin/weixin.js"/>"></script>
<div class="page-head">
	<h2>
		Weixin Articles
	</h2>
</div>

<div class="container">
	<div class="content">
		<div classs="table-responsive">
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th><fmt:message key='weixin.weixin.name'></fmt:message></th>
						<!--@generate-entity-jsp-property-desc@-->
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.content}" var="weixin">
						<tr>
							<td>${weixin.name}</td>
							<!--@generate-entity-jsp-property-value@-->
							<td><a href="<c:url value="/weixin/weixin/detail/${weixin.id}.html"/>"> View </a></td>
							</a>
							</td>
						<tr>
						<tr>
						  <td colspan="2"><input type="text" class="form-control" value="http://www.faceye.net/weixin/weixin/detail/${weixin.id}.html"></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<f:page page="${page}" url="/weixin/weixin/home" params="<%=request.getParameterMap()%>" />
	</div>
</div>
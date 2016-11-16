<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/spider/matcherConfig/matcherConfig.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/spider/matcherConfig/matcherConfig.js"/>"></script>


<div class="page-head">
	<h2>
		<fmt:message key="spider.matcherConfig.manager"></fmt:message>
		<a class="btn btn-primary" href="<c:url value="/spider/matcherConfig/input"/>"> <fmt:message key="spider.matcherConfig.add"></fmt:message>
		</a>
	</h2>
</div>
<div class="cl-mcont">
	<div class="block-flat">
		<div class="content">
			<form action="<c:url value="/spider/matcherConfig/home"/>" method="post" role="form" class="form-horizontal">
				<fieldset>
					<div class="form-group">
						<div class="col-md-2">
							<input type="text" name="LIKE|name" placeholder="<fmt:message key="spider.matcherConfig.name"></fmt:message>" class="form-control input-sm">
						</div>
						<div class="col-md-2">
							<select name="EQ|siteId" class="form-control input-sm">
								<option value=""><fmt:message key="spider.site.select" /></option>
								<c:forEach items="${sites}" var="site">
									<option value="${site.id}">${site.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-2">
							<select name="EQ|type" class="form-control">
								<option value=""><fmt:message key="spider.link.type" /></option>
								<option value="0" <c:if test="${searchParams.type eq 1 }">selected</c:if>><fmt:message key="spider.link.type.0" /></option>
								<option value="1" <c:if test="${searchParams.type eq 1 }">selected</c:if>><fmt:message key="spider.link.type.1" /></option>
								<option value="2" <c:if test="${searchParams.type eq 2 }">selected</c:if>><fmt:message key="spider.link.type.2" /></option>
							</select>
						</div>
						<div class="col-md-2">
							<input type="text" name="LIKE|categoryName" value="${searchParams.categoryName}" placeholder="<fmt:message key="spider.matcherConfig.categoryName"></fmt:message>"
								class="form-control input-sm">
						</div>
						<!--@generate-entity-jsp-query-detail@-->
						<div class="col-md-2">
							<button type="submit" class="btn btn-sm btn-default"><fmt:message key="global.search"/></button>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="content">
			<div classs="table-responsive" style="font-size: 11px;">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th><fmt:message key='spider.matcherConfig.name'></fmt:message></th>
							<th><fmt:message key='spider.matcherConfig.siteId'></fmt:message></th>
							<th><fmt:message key='spider.matcherConfig.type'></fmt:message></th>
							<th><fmt:message key='spider.matcherConfig.prefix'></fmt:message></th>
							<th><fmt:message key='spider.matcherConfig.suffix'></fmt:message></th>
							<th><fmt:message key='spider.matcherConfig.typeOfLinkAfterParse'></fmt:message></th>
							<th><fmt:message key='spider.matcherConfig.categoryName'></fmt:message></th>
							<th><fmt:message key='spider.matcherConfig.categoryAlias'></fmt:message></th>
							<!--@generate-entity-jsp-property-desc@-->
							<th><fmt:message key="global.edit"></fmt:message></th>
							<th><fmt:message key="global.remove"></fmt:message></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.content}" var="matcherConfig">
							<tr>
								<td>${matcherConfig.name}</td>
								<td>${matcherConfig.siteId}</td>
								<td>${matcherConfig.type}</td>
								<td>${matcherConfig.prefix}</td>
								<td>${matcherConfig.suffix}</td>
								<td><c:choose>
								  <c:when test="${matcherConfig.typeOfLinkAfterParse eq 0}">
								    <fmt:message key="spider.link.type.0"/>
								  </c:when>
								  <c:when test="${matcherConfig.typeOfLinkAfterParse eq 1}">
								    <fmt:message key="spider.link.type.1"/>
								  </c:when>
								  <c:when test="${matcherConfig.typeOfLinkAfterParse eq 2}">
								    <fmt:message key="spider.link.type.2"/>
								  </c:when>
								  <c:otherwise>
								    Unknown
								  </c:otherwise>
								</c:choose></td>
								<td>${matcherConfig.categoryName}</td>
								<td>${matcherConfig.categoryAlias}</td>
								<!--@generate-entity-jsp-property-value@-->
								<td><a href="<c:url value="/spider/matcherConfig/edit/${matcherConfig.id}"/>"> <fmt:message key="global.edit"></fmt:message>
								</a></td>
								<td><a href="<c:url value="/spider/matcherConfig/remove/${matcherConfig.id}"/>"> <fmt:message key="global.remove"></fmt:message>
								</a></td>
							<tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<f:page page="${page}" url="/spider/matcherConfig/home" params="<%=request.getParameterMap()%>" />
		</div>
	</div>
</div>


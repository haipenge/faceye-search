<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/spider/matcherConfig/matcherConfig.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/spider/matcherConfig/matcherConfig.js"/>"></script>


<div class="page-head">
	<h2>
		<fmt:message key="spider.matcherConfig.manager"></fmt:message>
		<a class="btn btn-primary" href="<c:url value="/spider/matcherConfig/input"/>"> <fmt:message
				key="spider.matcherConfig.add"></fmt:message>
		</a>
	</h2>
</div>
<div class="cl-mcont">
	<div class="block-flat">
		<div class="content">
			<form action="<c:url value="/spider/matcherConfig/home"/>" method="post" role="form" class="form-horizontal">
				<fieldset>
					<div class="form-group">
						<div class="col-md-1">
							<input type="text" name="EQ|name" placeholder="<fmt:message key="spider.matcherConfig.name"></fmt:message>"
								class="form-control input-sm">
						</div>
						<div class="col-md-1">
							<input type="text" name="EQ|regexp" placeholder="<fmt:message key="spider.matcherConfig.regexp"></fmt:message>"
								class="form-control input-sm">
						</div>
						<div class="col-md-1">
							<select name="EQ|siteId" class="form-control input-sm">
								<option>Select site</option>
								<c:forEach items="${sites}" var="site">
									<option value="${site.id}">${site.name}</option>
								</c:forEach>
							</select>

						</div>
						<div class="col-md-1">
							<input type="text" name="EQ|type" placeholder="<fmt:message key="spider.matcherConfig.type"></fmt:message>"
								class="form-control input-sm">
						</div>
						<div class="col-md-1">
							<input type="text" name="EQ|prefix" placeholder="<fmt:message key="spider.matcherConfig.prefix"></fmt:message>"
								class="form-control input-sm">
						</div>
						<div class="col-md-1">
							<input type="text" name="EQ|suffix" placeholder="<fmt:message key="spider.matcherConfig.suffix"></fmt:message>"
								class="form-control input-sm">
						</div>
						<div class="col-md-1">
							<input type="text" name="EQ|titleRegexp"
								placeholder="<fmt:message key="spider.matcherConfig.titleRegexp"></fmt:message>" class="form-control input-sm">
						</div>
						<div class="col-md-1">
							<input type="text" name="EQ|typeOfLinkAfterParse"
								placeholder="<fmt:message key="spider.matcherConfig.typeOfLinkAfterParse"></fmt:message>"
								class="form-control input-sm">
						</div>
						<!--@generate-entity-jsp-query-detail@-->
						<div class="col-md-1">
							<button type="submit" class="btn btn-sm btn-default">Search</button>
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
							<th><fmt:message key='spider.matcherConfig.regexp'></fmt:message></th>
							<th><fmt:message key='spider.matcherConfig.siteId'></fmt:message></th>
							<th><fmt:message key='spider.matcherConfig.type'></fmt:message></th>
							<th><fmt:message key='spider.matcherConfig.prefix'></fmt:message></th>
							<th><fmt:message key='spider.matcherConfig.suffix'></fmt:message></th>
							<th><fmt:message key='spider.matcherConfig.titleRegexp'></fmt:message></th>
							<th><fmt:message key='spider.matcherConfig.typeOfLinkAfterParse'></fmt:message></th>
							<!--@generate-entity-jsp-property-desc@-->
							<th><fmt:message key="global.edit"></fmt:message></th>
							<th><fmt:message key="global.remove"></fmt:message></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.content}" var="matcherConfig">
							<tr>
								<td>${matcherConfig.name}</td>
								<td>${matcherConfig.regexp}</td>
								<td>${matcherConfig.siteId}</td>
								<td>${matcherConfig.type}</td>
								<td>${matcherConfig.prefix}</td>
								<td>${matcherConfig.suffix}</td>
								<td>${matcherConfig.titleRegexp}</td>
								<td>${matcherConfig.typeOfLinkAfterParse}</td>
								<!--@generate-entity-jsp-property-value@-->
								<td><a href="<c:url value="/spider/matcherConfig/edit/${matcherConfig.id}"/>"> <fmt:message
											key="global.edit"></fmt:message>
								</a></td>
								<td><a href="<c:url value="/spider/matcherConfig/remove/${matcherConfig.id}"/>"> <fmt:message
											key="global.remove"></fmt:message>
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


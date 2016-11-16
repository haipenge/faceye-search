<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/spider/crawlResult/crawlResult.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/spider/crawlResult/crawlResult.js"/>"></script>

<div class="page-head">
	<h2>
		<fmt:message key="spider.crawlResult.manager"></fmt:message>
		<a class="btn btn-primary" href="<c:url value="/spider/crawlResult/input"/>"> <fmt:message
				key="spider.crawlResult.add"></fmt:message>
		</a>
	</h2>
</div>
<div class="cl-mcont">
	<div class="block-flat">
		<div class="content">
			<form action="<c:url value="/spider/crawlResult/home"/>" method="post" role="form" class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-md-1">Site:</label>
					<div class="col-md-2">
						<select name="EQ|siteId" class="form-control">
							<option value="">Select site</option>
							<c:forEach items="${sites}" var="site">
								<option value="${site.id}">${site.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-4">
						<label class="col-md-4 control-label">Is Parse:</label> <label class="radio-inline"> <input type="radio"
							name="ISTRUE|isParse" value="1">Yes
						</label> <label class="radio-inline"> <input type="radio" name="ISFALSE|isParse" value="0">No
						</label>
					</div>
					<div class="col-md-2">
						<button type="submit" class="btn btn-success">Search</button>
					</div>
				</div>

			</form>
		</div>

		<div class="content">
			<button type="button" class="btn btm-sm btn-default re-parse">Re Parse</button>
		</div>
		<div id="msg"></div>
		<div class="content">
			<div classs="table-responsive">
				<table class="table table-bordered table-hover" style="font-size: 11px;">
					<thead>
						<tr>
							<th><input type="checkbox" name="checkAll"></th>
							<th>Path</th>
							<th>Type</th>
							<th>Is parse</th>
							<th>Is Parse Success</th>
							<th><fmt:message key='spider.crawlResult.crawlDate'></fmt:message></th>
							<th><fmt:message key='spider.crawlResult.link'></fmt:message></th>
							<th>Show</th>
							<th>Parse</th>
							<!--@generate-entity-jsp-property-desc@-->
							<th><fmt:message key="global.remove"></fmt:message></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.content}" var="crawlResult" varStatus="status">
							<tr>
								<td><input type="checkbox" name="checkSingle" value="${crawlResult.id}"></td>
								<td>${crawlResult.storePath }</td>
								<td>${crawlResult.linkType }</td>
								<td>${crawlResult.isParse }</td>
								<td>${crawlResult.isParseSuccess}</td>
								<td><fmt:formatDate value="${crawlResult.crawlDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
								<td><a href="${crawlResult.linkUrl}" title="${crawlResult.linkUrl}" target="_blabk">Source Open</a></td>
								<td><a href="<c:url value="/spider/crawlResult/detail/${crawlResult.id}"/>" target="blank">Show</a></td>
								<td><a href="<c:url value="/parse/parseResult/testParseCrawlResult/${crawlResult.id}"/>" target="blank">Parse
										Test</a></td>
								<!--@generate-entity-jsp-property-value@-->
								<td><a href="<c:url value="/spider/crawlResult/remove/${crawlResult.id}"/>"> <fmt:message
											key="global.remove"></fmt:message>
								</a></td>
							<tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<f:page page="${page}" url="/spider/crawlResult/home" params="<%=request.getParameterMap()%>" />
	</div>
</div>
<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/spider/link/link.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/spider/link/link.js"/>"></script>


<div class="page-head">
	<h2>
		<fmt:message key="spider.link.manager"></fmt:message>
		<a class="btn btn-primary" href="<c:url value="/spider/link/input"/>"> <fmt:message key="spider.link.add"></fmt:message>
		</a>
	</h2>
</div>
<div class="cl-mcont">
	<div class="block-flat">
		<div class="content">
			<form action="<c:url value="/spider/link/home"/>" method="post" role="form" class="form-horizontal">
				<div class="form-group">
					<div class="col-md-1">
						<select name="EQ|site.id" class="form-control">
							<option value="">Select site</option>
							<c:forEach items="${sites}" var="site">
								<option value="${site.id}">${site.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-1">
						<select name="EQ|type" class="form-control">
							<option value="">Select Type</option>
							<option value="0">Seed</option>
							<option value="1">List</option>
							<option value="2">Detail</option>
						</select>
					</div>
					<div class="col-md-4">
						<label>IsCrawl</label> <label class="radio-inline"> <input type="radio" name="ISTRUE|isCrawled" value="1">Yes
						</label> <label class="radio-inline"> <input type="radio" name="ISFALSE|isCrawled" value="0">No
						</label>
					</div>
					<div class="col-md-2">
						<input type="text" name="EQ|url" class="form-control" placeholder="link url...">
					</div>
					<div class="col-md-1">
						<button type="submit" class="btn btn-sm btn-default">Search</button>
					</div>
				</div>

			</form>
		</div>
		<div class="content">
			<button class="btn btn-sm btn-default re-crawl">Re Crawl</button>
			<button class="btn btn-sm btn-default as-seed">As Seed</button>
		</div>
		<div id="msg"></div>
		<div class="content">
			<div classs="table-responsive">
				<table class="table table-bordered table-hover" style="font-size: 11px;">
					<thead>
						<tr>
							<th><input type="checkbox" name="checkAllLink"></th>
							<th><fmt:message key='spider.link.url'></fmt:message></th>
							<th><fmt:message key='spider.link.isCrawled'></fmt:message></th>
							<th><fmt:message key='spider.link.createDate'></fmt:message></th>
							<th><fmt:message key='spider.link.isCrawlSuccess'></fmt:message></th>
							<th><fmt:message key='spider.link.lastCrawlDate'></fmt:message></th>
							<td>Site</td>
							<!--@generate-entity-jsp-property-desc@-->
							<th><fmt:message key="global.edit"></fmt:message></th>
							<th><fmt:message key="global.remove"></fmt:message></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.content}" var="link">
							<tr>
								<td><input type="checkbox" name="checkSingleLink" value="${link.id}"></td>
								<td><a href="${link.url}" target="blank">${link.url}</a></td>
								<td>${link.isCrawled}</td>
								<td><fmt:formatDate value="${link.createDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
								<td>${link.isCrawlSuccess}</td>
								<td><fmt:formatDate value="${link.lastCrawlDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
								<td></td>
								<!--@generate-entity-jsp-property-value@-->
								<td><a href="<c:url value="/spider/link/edit/${link.id}"/>"> <fmt:message key="global.edit"></fmt:message>
								</a></td>
								<td><a href="<c:url value="/spider/link/remove/${link.id}"/>"> <fmt:message key="global.remove"></fmt:message>
								</a></td>
							<tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<f:page page="${page}" url="/spider/link/home" params="<%=request.getParameterMap()%>" />
		</div>
	</div>
</div>

<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/spider/crawlResult/crawlResult.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/spider/crawlResult/crawlResult.js"/>"></script>

<div class="page-head">
	<h2>
		<fmt:message key="spider.crawlResult.manager"></fmt:message>
	</h2>
</div>
<div class="cl-mcont">
	<div class="block-flat">
		<div class="content">
			<form action="<c:url value="/spider/crawlResult/home"/>" method="post" role="form" class="form-horizontal">
				<div class="form-group">
					<div class="col-md-2">
						<select name="EQ|siteId" class="form-control">
							<option value=""><fmt:message key="spider.site.select" /></option>
							<c:forEach items="${sites}" var="site">
								<option value="${site.id}" <c:if test="${searchParams.siteId eq site.id}">selected</c:if>>${site.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-3">

						<select name="EQ|linkType" class="form-control">
							<option value=""><fmt:message key="spider.link.type" /></option>
							<option value="0"><fmt:message key="spider.link.type.0" /></option>
							<option value="1"><fmt:message key="spider.link.type.1" /></option>
							<option value="2"><fmt:message key="spider.link.type.2" /></option>
						</select>
					</div>
					<div class="col-md-4">
						<label class="col-md-4 control-label"><fmt:message key="spider.crawlResult.isParse" />:</label> <label class="radio-inline"> <input type="radio" name="ISTRUE|isParse"
							value="1" <c:if test="${searchParams.isParse eq '1'}">checked</c:if>>Yes
						</label> <label class="radio-inline"> <input type="radio" name="ISFALSE|isParse" value="0" <c:if test="${searchParams.isParse eq '0'}">checked</c:if>>No
						</label>
					</div>

					<div class="col-md-2">
						<button type="submit" class="btn btn-success">
							<fmt:message key="global.search" />
						</button>
					</div>
				</div>
			</form>
		</div>

		<div class="content">
			<button type="button" class="btn btm-sm btn-default re-parse">
				<fmt:message key="spider.crawlResult.reparse" />
			</button>
			<input type="checkbox" name="isWholeSite"><label><fmt:message key="spider.crawlResult.is.whole.site" /></label>
		</div>
		<div id="msg"></div>
		<div class="content">
			<div classs="table-responsive">
				<table class="table table-bordered table-hover" style="font-size: 11px;">
					<thead>
						<tr>
							<th><input type="checkbox" name="checkAll"></th>
							<th><fmt:message key="spider.crawlResult.path" /></th>
							<th><fmt:message key="spider.link.type" /></th>
							<th><fmt:message key="spider.crawlResult.isParse" /></th>
							<th><fmt:message key="spider.crawlResult.parse.result" /></th>
							<th><fmt:message key='spider.crawlResult.crawlDate'></fmt:message></th>
							<th><fmt:message key='spider.crawlResult.link'></fmt:message></th>
							<th><fmt:message key="global.view" /></th>
							<th><fmt:message key="spider.crawlResult.parse.test" /></th>
							<th><fmt:message key='spider.crawlResult.isWeixin'></fmt:message></th>
							<!--@generate-entity-jsp-property-desc@-->
							<th>Call Super</th>
							<th><fmt:message key="global.remove"></fmt:message></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.content}" var="crawlResult" varStatus="status">
							<tr>
								<td><input type="checkbox" name="checkSingle" value="${crawlResult.id}"></td>
								<td>${crawlResult.storePath }</td>
								<td><c:choose>
										<c:when test="${crawlResult.linkType eq 0}">
											<fmt:message key="spider.link.type.0" />
										</c:when>
										<c:when test="${crawlResult.linkType eq 1}">
											<fmt:message key="spider.link.type.1" />
										</c:when>
										<c:when test="${crawlResult.linkType eq 2}">
											<fmt:message key="spider.link.type.2" />
										</c:when>
										<c:otherwise>
								 Unknown
								 </c:otherwise>
									</c:choose></td>
								<td><f:boolean value="${crawlResult.isParse }" /></td>
								<td><f:boolean value="${crawlResult.isParseSuccess}" /></td>
								<td><fmt:formatDate value="${crawlResult.crawlDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
								<td><a href="${crawlResult.linkUrl}" title="${crawlResult.linkUrl}" target="_blabk"><fmt:message key="spider.crawlResult.parse.open"/></a></td>
								<td><a href="<c:url value="/spider/crawlResult/detail/${crawlResult.id}"/>" target="blank"><fmt:message key="global.view" /></a></td>
								<td><a href="<c:url value="/parse/parseResult/testParseCrawlResult/${crawlResult.id}"/>" target="blank"><fmt:message key="spider.crawlResult.parse.test" /></a></td>
								<td><f:boolean value="${crawlResult.isWeixin}" /></td>
								<!--@generate-entity-jsp-property-value@-->
								<td><a href="#" class="call-super-parse">Call Super</a></td>
								<td><a href="<c:url value="/spider/crawlResult/remove/${crawlResult.id}"/>"> <fmt:message key="global.remove"></fmt:message>
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
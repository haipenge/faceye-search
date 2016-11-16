<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/parse/parseResult/parseResult.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/parse/parseResult/parseResult.js"/>"></script>
<div class="page-head">
	<h2>
		<fmt:message key="parse.parseResult.manager"></fmt:message>
		<c:forEach items="${levels}" var="level">
			<a class="btn btn-default" data-toggle="tooltip" data-placement="top" title="${level.msg}" href="/parse/parseResult/home?level=${level.level}">${level.name}</a>
		</c:forEach>
	</h2>
</div>
<div class="cl-mcont" style="padding:0px;">
	<div class="block-flat" style="padding: 0px;margin:0px;">
		<div class="content" style="margin:0px;padding:0px;">
			<form action="<c:url value="/parse/parseResult/home"/>" method="post" role="form" class="form-horizontal" id="search-form" style="margin-bottom:0px;padding-bottom:2px;">
				<fieldset style="padding:0px;">
					<div class="form-group">
						<div class="col-md-2">
							<select name="siteId" class="form-control">
								<option>Select Site</option>
								<c:forEach items="${sites}" var="site">
									<option value="${site.id}" <c:if test="${searchParams.siteId eq site.id }">selected</c:if>>${site.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-2">
							<select name="isPush2Mongo" class="form-control">
								<option value="">Is Pushed</option>
								<option value="1" <c:if test="${searchParams.isPush2Mongo eq '1' }"> selected</c:if>>YES</option>
								<option value="0" <c:if test="${searchParams.isPush2Mongo eq '0' }"> selected</c:if>>NO</option>
							</select>
						</div>
						<div class="col-md-2">
							<select name="isAllow" class="form-control">
								<option value="">Is Allow</option>
								<option value="1" <c:if test="${searchParams.isAllow eq '1' }"> selected</c:if>>YES</option>
								<option value="0" <c:if test="${searchParams.isAllow eq '0' }"> selected</c:if>>NO</option>
							</select>
						</div>
						<div class="col-md-1">
							<input type="input" name="name" class="form-control" value="${searchParams.name}" placeholder="Name...">
						</div>
						<div class="col-md-2">
							<input type="input" name="sourceUrl" class="form-control" placeholder="Domain">
						</div>
						<div class="col-md-2">
							<select name="categoryId" class="form-control">
								<option value="">Select Category</option>
								<c:forEach items="${articleCategories}" var="category">
									<option value="${category.id}" <c:if test="${searchParams.categoryId eq category.id}">selected</c:if>>${category.name }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-1">
							<button type="submit" class="btn btn-primary"><fmt:message key="global.search"/></button>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="content" style="padding-top: 0px;">
			<c:forEach items="${stats}" var="s">
				<c:set var="domain" value="${s.site.name}" />
				<a href="<c:url value="/parse/parseResult/home?siteId=${s.site.id}"/>" class="btn btn-sm btn-success">${s.site.name }&nbsp;&nbsp;<span class="badge">${s.count}</span></a>
			</c:forEach>
		</div>
		<div class="content" style="padding-top: 0px;">
			<button class="btn-sm btn-success multi-push-btn">Multi Push</button>
			<button class="btn-sm btn-success multi-allow">Multi Allow</button>
			<input type="checkbox" name="isWholeSite"><label class="control-label">Is WholeSite</label>
			<button class="btn-sm btn-danger" id="multi-remove"><fmt:message key="global.remove"/></button>
			<span class="label label-default">UnPushedAllowCount:${unPushedAllowedCount}</span>
		</div>
		<div id="msg"></div>
		<div class="content" style="padding-top: 0px;margin-top:2px;">
			<f:page page="${page}" url="/parse/parseResult/home" params="<%=request.getParameterMap()%>" />
			<div classs="table-responsive">
				<table class="table table-bordered table-hover">
					<tr>
					<!-- 
						<td></td>
						 -->
						<td><input type="checkbox" name="checkAllParseResultIds"></td>
						<td><fmt:message key='parse.parseResult.name'></fmt:message></td>
					</tr>
					<c:forEach items="${page.content}" var="parseResult" varStatus="status">
						<tr id="${parseResult.id}">
						<!-- 
							<td>${status.count }</td>
							 -->
							<td><c:if test="${!parseResult.isPush2Mongo }">
									<input type="checkbox" name="parseResultId" value="${parseResult.id}" checked>
								</c:if></td>
							<td>
								<p>
									<span id="name-${parseResult.id}" class="name-eidt">${parseResult.name}</span><a href="<c:url value="/parse/parseResult/detail/${parseResult.id}"/>" target="_blank">...</a>
									<span class="label label-primary">L:${parseResult.level}</span> <span class="label label-warning">F:${parseResult.isContainsFilterWord}</span> <span
										class="label label-success">${parseResult.categoryName}</span>
									<sapn class="pull-right"> <fmt:formatDate value="${parseResult.createDate }" type="date" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></sapn>
								</p> <c:set var="content" value="${parseResult.content}"></c:set> <c:set var="sourceUrl" value="${parseResult.sourceUrl}"></c:set>
								<p style="color: gray; font-size: 11px; padding: 5px 5px 5px 10px; word-break: break-all; word-wrap: break-word;">
									<%
										String content = pageContext.getAttribute("content").toString();
											String sourceUrl = pageContext.getAttribute("sourceUrl").toString();
											out.print(JspUtil.getSummary(content, 200));
									%>
								</p>
								<p style="font-size: 11px; padding: 0px 5px 0px 5px;">
									<span class="label label-success">Length:<%=content.length()%></span> <span class="label label-success">Source:<a href="${parseResult.sourceUrl}" target="_blank"><%=JspUtil.getDomain(sourceUrl)%></a></span>
									|
									<c:choose>
										<c:when test="${parseResult.isPush2Mongo eq true}">
											<span class="label label-warning">Pushed</span>
										</c:when>
										<c:otherwise>
											<a href="#" class="btn-sm btn-success" onclick="ParseResult.push2Mongo(${parseResult.id},'true');return false;" id="btn-${parseResult.id}">Push2Net</a>
										</c:otherwise>
									</c:choose>
									<a href="#" class="btn btn-sm btn-success" onclick="ParseResult.push2Cms(${parseResult.id});" id="btn-cms-push-${parseResult.id}">Push2Cms</a>
									<a href="#" class="btn-sm btn-info" onclick="ParseResult.testFilter(${parseResult.id});return false;">Filter</a>

									<!--@generate-entity-jsp-property-value@-->
									<c:if test="${parseResult.isPush2Mongo eq false }">
										<a class="btn-sm btn-default" href="<c:url value="/parse/parseResult/edit/${parseResult.id}"/>" target="_blank"> <fmt:message key="global.edit"></fmt:message>
										</a>
									</c:if>
									<c:if test="${!parseResult.isPush2Mongo || empty parseResult.isPush2Mongo}">
										<a class="btn-sm btn-danger" href="#" onclick="ParseResult.remove(${parseResult.id});return false;"> <fmt:message key="global.remove"></fmt:message>
										</a>
									</c:if>
									<c:if test="${!parseResult.isAllow || empty parseResult.isAllow }">
										<a href="#" class="btn-sm btn-danger" onclick="ParseResult.allow(${parseResult.id});return false;">To Allow</a>
									</c:if>
									<c:if test="${parseResult.isAllow}">
										<span class="label ${parseResult.isAllow?'label-success':'label-info'}"> Allow?:${parseResult.isAllow?'Yes':'No'}</span>
									</c:if>
									&nbsp;&nbsp; <span class="label ${parseResult.isPush2ProductEnv?'label-success':'label-info'}">Is Push2ProductEnv:${parseResult.isPush2ProductEnv?'Yes':'No'}</span>
								</p>
							</td>
						<tr>
					</c:forEach>
				</table>
			</div>
			<f:page page="${page}" url="/parse/parseResult/home" params="<%=request.getParameterMap()%>" />
		</div>
		<div class="content">
			<button class="btn btn-success multi-push-btn">Multi Push</button>
			<button class="btn-sm btn-success multi-allow">Multi Allow</button>
		</div>
	</div>
</div>

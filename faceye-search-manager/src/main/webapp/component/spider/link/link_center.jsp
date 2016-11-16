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
						<select name="EQ|siteId" class="form-control">
							<option value=""><fmt:message key="spider.site"/></option>
							<c:forEach items="${sites}" var="site">
								<option value="${site.id}" <c:if test="${searchParams.siteId eq site.id}">selected</c:if>>${site.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-2">
						<select name="EQ|type" class="form-control">
							<option value=""><fmt:message key="spider.link.type"/></option>
							<option value="0" <c:if test="${searchParams.type eq '0' }">selected</c:if>><fmt:message key="spider.link.type.0"/></option>
							<option value="1" <c:if test="${searchParams.type eq '1' }">selected</c:if>><fmt:message key="spider.link.type.1"/></option>
							<option value="2" <c:if test="${searchParams.type eq '2' }">selected</c:if>><fmt:message key="spider.link.type.2"/></option>
						</select>
					</div>
					<div class="col-md-3">
						<label><fmt:message key="spider.link.isCrawled"/></label> <label class="radio-inline"> <input type="radio" name="boolean|isCrawled" value="true"  <c:if test="${searchParams.isCrawled}">checked</c:if>><f:boolean value="true"/>
						</label> <label class="radio-inline"> <input type="radio" name="boolean|isCrawled" value="false"  <c:if test="${!searchParams.isCrawled }">checked</c:if>><f:boolean value="false"/>
						</label>
					</div>
					<div class="col-md-3">
						<label><fmt:message key="spider.link.distribute"/></label> <label class="radio-inline"> <input type="radio" name="ISTRUE|isDistributed"
							value="1"  <c:if test="${searchParams.isDistributed eq '1' }">checked</c:if>><f:boolean value="true"/>
						</label> <label class="radio-inline"> <input type="radio" name="ISFALSE|isDistributed" value="0"  <c:if test="${searchParams.isDistributed eq '0' }">checked</c:if>><f:boolean value="false"/>
						</label>
					</div>
					<div class="col-md-2">
						<input type="text" name="LIKE|url" value="${searchParams.url }" class="form-control" placeholder="link url...">
					</div>
					<div class="col-md-1">
						<button type="submit" class="btn btn-sm btn-default"><fmt:message key="global.search"/></button>
					</div>
				</div>
			</form>
		</div>
		<div class="content">
		    <button class="btn btn-sm btn-danger multi-remove"><fmt:message key="global.remove"/></button>
			<button class="btn btn-sm btn-default re-crawl"><fmt:message key="spider.link.recrawl"/></button>
			<button class="btn btn-sm btn-default as-seed"><fmt:message key="spider.link.as.seed"/></button>
		</div>
		<div id="msg"></div>
		<div class="content">
			<div classs="table-responsive">
				<table class="table table-bordered table-hover" style="font-size: 11px;">
					<thead>
						<tr>
							<th><input type="checkbox" name="checkAllLink"></th>
							<th><fmt:message key='spider.link.url'></fmt:message></th>
							<th><fmt:message key="spider.link.type"/></th>
							<th><fmt:message key="spider.link.isDistributed" /></th>
							<!-- 
							<th><fmt:message key="spider.link.distribute.channel" /></th>
                            -->
							<th><fmt:message key='spider.link.createDate'></fmt:message></th>
							<th><fmt:message key='spider.link.isCrawled'></fmt:message></th>
							<th><fmt:message key='spider.link.isCrawlSuccess'></fmt:message></th>
							<th><fmt:message key='spider.link.lastCrawlDate'></fmt:message></th>
							<!-- 
							<th>Site</th>
							 -->
							<th><fmt:message key="global.view"/></th>
							<!--@generate-entity-jsp-property-desc@-->
							<th><fmt:message key="global.edit"></fmt:message></th>
							<th><fmt:message key="global.remove"></fmt:message></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.content}" var="link">
							<tr id = "${link.id}">
								<td><input type="checkbox" name="checkSingleLink" value="${link.id}"></td>
								<c:set var="linkUrl" value="${link.url}"></c:set>
								<td><a href="${link.url}" title="${link.url}" target="blank">${link.url}</td>
								<td>
								  <c:choose>
								    <c:when test="${link.type eq 0 }">
								    <fmt:message key="spider.link.type.0"/>
								    </c:when>
								    <c:when test="${link.type eq 1 }">
								    <fmt:message key="spider.link.type.1"/>
								    </c:when>
								    <c:when test="${link.type eq 2 }">
								    <fmt:message key="spider.link.type.2"/>
								    </c:when>
								    <c:otherwise>
								      UnKnown
								    </c:otherwise>
								  </c:choose>
								</td>
								<td><f:boolean value="${link.isDistributed}" /></td>
								<!-- 
								<td>${link.distributeChannel}<fmt:formatDate value="${link.distributeDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
								 -->
								<td><fmt:formatDate value="${link.createDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
								<td><f:boolean value="${link.isCrawled}" /></td>
								<td><f:boolean value="${link.isCrawlSuccess}" /></td>
								<td><fmt:formatDate value="${link.lastCrawlDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
								<!-- 
								<td>${link.siteId}</td>
								 -->
								<td><a href="<c:url value="/spider/crawlResult/home?EQ|linkId=${link.id}"/>"><fmt:message key="global.view"/></a></td>
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

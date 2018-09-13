<%@ include file="/component/core/taglib/taglib.jsp"%>
<div class="col-md-7 col-md-offset-1 bg-white">
	<div class="content">
		<ul class="list-unstyled">
			<c:forEach items="${page.content}" var="parseResult">
				<li>
					<h4>
						<a href="<c:url value="${host}/search/${parseResult.id}.html"/>" title="${parseResult.name}">${parseResult.name}</a>
					</h4> <c:set var="content" value="${parseResult.content}"></c:set>
					<p><%=JspUtil.getSummary(pageContext
						.getAttribute("content").toString(), 150)%></p>
					<p>
						<c:if test="${ not empty parseResult.categoryAlias && not empty parseResult.categoryName}">
							<span class="label label-success"><a
								href="<c:url value="${host}/search.html?alias=${parseResult.categoryAlias}"/>">${parseResult.categoryName}</a></span>
						</c:if>
						<span class="label label-info pull-right"><fmt:formatDate value="${parseResult.createDate}"
								pattern="yyyy-MM-dd"></fmt:formatDate></span>
					</p>
					<hr>
				</li>
			</c:forEach>
		</ul>
		<f:page page="${page}" url="${host}/search/index.html" params="<%=request.getParameterMap()%>" />
	</div>
	<h3><fmt:message key="search.movie.hot"/></h3>
	<div class="content">
		<ul class="list-unstyled">
			<c:forEach var="movie" items="${movies.content}">
				<li>
					<h4>
						<a href="<c:url value="/search/movie/detail/${movie.id}.html"/>">${movie.name}</a>
					</h4>
					<p>
						<span><fmt:message key="search.movie.director" />:${movie.director }</span> <span><fmt:message
								key="search.movie.actor" />:${movie.actor }</span> <span><fmt:message key="search.movie.onlineDate" />:<fmt:formatDate
								value="${movie.onlineDate}" type="date" pattern="yyyy-MM-dd"></fmt:formatDate></span>
					</p>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
<div class="col-md-3 bg-white" style="margin-left: 5px;">
	<div class="content">
		<h4>
			Hadoop <small><a href="<c:url value="${host}/search/index.html?alias=hadoop"/>" class="pull-right">More...</a></small>
		</h4>
		<ul class="list-unstyled">
			<c:forEach items="${hadoop.content}" var="parseResult">
				<li><a href="<c:url value="${host}/search/${parseResult.id}.html"/>"><c:if
							test="${fn:length(parseResult.name) > 26}">
							<c:out value="${fn:substring(parseResult.name, 0, 26)}..." />
						</c:if> <c:if test="${fn:length(parseResult.name) <= 26}">${parseResult.name}</c:if></a></li>
			</c:forEach>
		</ul>
	</div>
	<div class="content">
		<script type="text/javascript">
			BAIDU_CLB_fillSlot("1058992");
		</script>
	</div>
	<div class="content">
		<h4>
			Spring<small><a href="<c:url value="${host}/search/index.html?alias=spring"/>" class="pull-right">More...</a></small>
		</h4>
		<ul class="list-unstyled">
			<c:forEach items="${spring.content}" var="parseResult">
				<li><a href="<c:url value="${host}/search/${parseResult.id}.html"/>"><c:if
							test="${fn:length(parseResult.name) > 26}">
							<c:out value="${fn:substring(parseResult.name, 0, 26)}..." />
						</c:if> <c:if test="${fn:length(parseResult.name) <= 26}">${parseResult.name}</c:if></a></li>
			</c:forEach>
		</ul>
	</div>
	<div class="content">
		<h4>
			MongoDB <small><a href="<c:url value="${host}/search/index.html?alias=mongodb"/>" class="pull-right">More...</a></small>
		</h4>
		<ul class="list-unstyled">
			<c:forEach items="${mongodb.content}" var="parseResult">
				<li><a href="<c:url value="${host}/search/${parseResult.id}.html"/>"><c:if
							test="${fn:length(parseResult.name) > 26}">
							<c:out value="${fn:substring(parseResult.name, 0, 26)}..." />
						</c:if> <c:if test="${fn:length(parseResult.name) <= 26}">${parseResult.name}</c:if></a></li>
			</c:forEach>
		</ul>
	</div>
</div>
<div class="col-md-1"></div>

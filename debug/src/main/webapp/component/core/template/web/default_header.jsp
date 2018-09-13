<%@ include file="/component/core/taglib/taglib.jsp"%>
<div class="navbar-header">
	<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
		<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span>
		<span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
	</button>
	<a class="navbar-brand" href="<c:url value="/"/>"><fmt:message key="global.home" /></a>
</div>
<div class="navbar-collapse collapse">
	<ul class="nav navbar-nav">
	<!-- 
		<li class="active"><a href="<c:url value="/"/>"><fmt:message key="global.home" /></a></li>
		 -->
		<c:forEach items="${categories}" var="category" varStatus="status">
			<c:if test="${status.index le 4 }">
				<li <c:if test="${category.alias eq param.alias}"> class="active"</c:if>><a href="${host}/search/index.html?alias=${category.alias}">${category.name }</a></li>
			</c:if>
		</c:forEach>
		<li><a href="<c:url value="/search/movie/home"/>"><fmt:message key="search.movie.movie"/></a></li>
		<li><a href="<c:url value="/code/example/api"/>">Code</a></li>
		<c:if test="${fn:length(categories) gt 4 }">
			<li class="dropdown">
			 <a href="#" class="dropdown-toggle" data-toggle="dropdown">More <span class="caret"></span></a>
			  <ul class="dropdown-menu" role="menu">
			   <c:forEach items="${categories}" var="category" varStatus="status">
					<c:if test="${status.index gt 4 }">
						<li><a href="${host}/search/index.html?alias=${category.alias}">${category.name }</a></li>
					</c:if>
				</c:forEach>
				</ul>
			</li>
		</c:if>
	</ul>
</div>
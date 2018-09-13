<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/search/movie/movie.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/movie/movie.js"/>"></script>
<div class="row">
	<div class="col-md-10 col-md-offset-1 bg-white">
		<div class="page-header">
			<div class="row">
				<div class="col-lg-4">
					<fmt:message key="search.movie"></fmt:message>
				</div>
				<div class="col-lg-8">
					<form action="<c:url value="/search/movie/home"/>" class="form-horizontal">
						<div class="input-group">
							<input type="text" class="form-control" namle="LIKE|name" placeholder="Search for..."> <span class="input-group-btn">
								<button class="btn btn-default" type="submit">
									<fmt:message key="faceye.search" />
								</button>
							</span>
						</div>
					</form>
				</div>
			</div>

		</div>
		<div class="content">
			<ul class="list-unstyled">
				<c:forEach var="movie" items="${page.content}">
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
			<f:page page="${page}" url="/search/movie/home" params="<%=request.getParameterMap()%>" />
		</div>
	</div>
	<div class="col-md-1"></div>
</div>



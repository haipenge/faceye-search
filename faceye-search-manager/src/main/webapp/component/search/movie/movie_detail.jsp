<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/search/movie/movie.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/movie/movie.js"/>"></script>
<div class="block-flat">
	<div class="header">
		<h3>
			<fmt:message key="search.movie.detail"></fmt:message>
		</h3>
	</div>
	<div class="content">
		<div classs="table-responsive">
			<table class="table table-bordered table-hover">
				<tr>
					<td><fmt:message key="search.movie.name"></fmt:message></td>
					<td>${movie.name}</td>
				</tr>
				<tr>
					<td><fmt:message key="search.movie.director"></fmt:message></td>
					<td>${movie.director}</td>
				</tr>
				<tr>
					<td><fmt:message key="search.movie.actor"></fmt:message></td>
					<td>${movie.actor}</td>
				</tr>
				<tr>
					<td><fmt:message key="search.movie.categoryName"></fmt:message></td>
					<td>${movie.categoryName}</td>
				</tr>
				<tr>
					<td><fmt:message key="search.movie.language"></fmt:message></td>
					<td>${movie.language}</td>
				</tr>
				<tr>
					<td><fmt:message key="search.movie.onlineDate"></fmt:message></td>
					<td><fmt:formatDate value="${movie.onlineDate}" type="date" pattern="yyyy-MM-dd"></fmt:formatDate></td>
				</tr>

				<tr>
					<td><fmt:message key="search.movie.area"></fmt:message></td>
					<td>${movie.area}</td>
				</tr>
				<tr>
					<td><fmt:message key="search.movie.totalMinutes"></fmt:message></td>
					<td>${movie.totalMinutes}</td>
				</tr>
				<tr>
					<td><fmt:message key="search.movie.remark"></fmt:message></td>
					<td>${movie.remark}</td>
				</tr>
				<tr>
				  <td><fmt:message key="search.movie.description"/></td>
				  <td>${movie.description }
				</tr>
				<!--@generate-entity-jsp-property-detail@-->





			</table>
		</div>
	</div>
</div>
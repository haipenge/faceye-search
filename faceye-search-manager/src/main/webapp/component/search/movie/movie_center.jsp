<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/search/movie/movie.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/movie/movie.js"/>"></script>
<div class="page-head">
	<h2>
		<fmt:message key="search.movie.manager"></fmt:message>
		<a class="btn btn-primary" href="<c:url value="/search/movie/input"/>"> <fmt:message key="search.movie.add"></fmt:message>
		</a>
	</h2>
</div>
<div class="cl-mcont">
	<!-- 
	<div class="header">
		<h2>
			<fmt:message key="security.role.manager"></fmt:message>
		</h2>
		<a class="btn btn-default" href="<c:url value="/security/role/input"/>"> <fmt:message key="security.role.add"></fmt:message>
		</a>
	</div>
	 -->
	<div class="block-flat">
		<div class="content">
			<form action="<c:url value="/search/movie/home"/>" method="post" role="form" class="form-horizontal">
				<fieldset>
					<div class="form-group">
						<div class="col-md-1">
							<input type="text" name="like|name" value="${searchParams.name}"
								placeholder="<fmt:message key="search.movie.name"></fmt:message>" class="form-control input-sm">
						</div>
						<div class="col-md-1">
							<input type="text" name="EQ|categoryName" value="${searchParams.categoryName}"
								placeholder="<fmt:message key="search.movie.categoryName"></fmt:message>" class="form-control input-sm">
						</div>
						<div class="col-md-2">
							<label class="radio-inline"><input type="radio" name="boolean|isPush" id="inlineRadio1"
								value="true" <c:if test="${searchParams.isPush eq 'true'}">checked</c:if>> <fmt:message key="search.movie.is.push.yes"/>
							</label>
							<label class="radio-inline"> <input type="radio" name="boolean|isPush" id="inlineRadio2"
								value="false" <c:if test="${searchParams.isPush eq 'false' || empty searchParams.isPush}">checked</c:if>> <fmt:message key="search.movie.is.push.no"/>
							</label>
						</div>
					   <div class="col-md-2">
					      <select name="EQ|from" class="form-control">
					          <option value="">From</option>
					          <option value="YOUKU">Youku.com</option>
					          <option value="LETV">Letv.com</option>
					      </select>
					   </div>
						<!--@generate-entity-jsp-query-detail@-->
						<div class="col-md-1">
							<button type="submit" class="btn btn-sm btn-default">
								<fmt:message key="global.search"></fmt:message>
							</button>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div id="msg"></div>
		<div class="content">
		  <button class="btn btn-danger multi-remove"><fmt:message key="global.remove"/></button>
		  <button class="btn btn-primary push2ProductEnv">Push 2 Product</button>
		</div>
		<div class="content">
			<div classs="table-responsive">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
						  <th><input type="checkbox" name="check-all"></th>
							<th><fmt:message key='search.movie.name'></fmt:message></th>
							<th><fmt:message key="search.movie.is.push"/></th>
							<th><fmt:message key='search.movie.director'></fmt:message></th>
							<th><fmt:message key='search.movie.actor'></fmt:message></th>
							<th><fmt:message key='search.movie.categoryName'></fmt:message></th>
							<th><fmt:message key='search.movie.onlineDate'></fmt:message></th>
							<th><fmt:message key="search.movie.area"/></th>
							<th><fmt:message key="search.movie.totalMinutes"/></th>
							<th><fmt:message key="search.movie.from"></fmt:message></th>
							<!--@generate-entity-jsp-property-desc@-->
							<th><fmt:message key="global.edit"></fmt:message></th>
							<th><fmt:message key="global.remove"></fmt:message></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.content}" var="movie">
							<tr>
							    <td><input type="checkbox" name="check-single" value="${movie.id}"></td>
								<td><a href="<c:url value="/search/movie/detail/${movie.id}.html"/>">${movie.name}</a></td>
								<td><f:boolean value="${movie.isPush }"/></td>
								<td>${movie.director}</td>
								<td>${movie.actor}</td>
								<td>${movie.categoryName}</td>
								<td><fmt:formatDate value="${movie.onlineDate}" type="date"
										pattern="yyyy-MM-dd"></fmt:formatDate></td>
								<td>${movie.area }</td>
								<td>${movie.totalMinutes }</td>
								<td>${movie.from}</td>
								<!--@generate-entity-jsp-property-value@-->
								<td><a href="<c:url value="/search/movie/edit/${movie.id}"/>"> <fmt:message key="global.edit"></fmt:message>
								</a></td>
								<td><a href="<c:url value="/search/movie/remove/${movie.id}"/>"> <fmt:message key="global.remove"></fmt:message>
								</a></td>
							<tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<f:page page="${page}" url="/search/movie/home" params="<%=request.getParameterMap()%>" />
		</div>
	</div>
</div>

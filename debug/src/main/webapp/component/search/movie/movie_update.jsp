<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/search/movie/movie.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/movie/movie.js"/>"></script>
<div class="block-flat">
	<div class="header">
		<h3>
			<c:choose>
				<c:when test="${empty movie.id}">
					<fmt:message key="search.movie.add"></fmt:message>
				</c:when>
				<c:otherwise>
					<fmt:message key="search.movie.edit"></fmt:message>
				</c:otherwise>
			</c:choose>
		</h3>
	</div>
	<div class="content">
		<form action="<c:url value="/search/movie/save"/>" method="post" role="form" class="form-horizontal">
			<input type="hidden" name="id" value="${movie.id}" />
			<fieldset>
				<div class="form-group">
					<label class="col-md-2 control-label" for="name"> <fmt:message key="search.movie.name"></fmt:message>
					</label>
					<div class="col-md-6">
						<input type="text" name="name" value="${movie.name}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="director"> <fmt:message key="search.movie.director"></fmt:message>
					</label>
					<div class="col-md-6">
						<input type="text" name="director" value="${movie.director}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="actor"> <fmt:message key="search.movie.actor"></fmt:message>
					</label>
					<div class="col-md-6">
						<input type="text" name="actor" value="${movie.actor}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="categoryName"> <fmt:message key="search.movie.categoryName"></fmt:message>
					</label>
					<div class="col-md-6">
						<input type="text" name="categoryName" value="${movie.categoryName}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="language"> <fmt:message key="search.movie.language"></fmt:message>
					</label>
					<div class="col-md-6">
						<input type="text" name="language" value="${movie.language}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="onlineDate"> <fmt:message key="search.movie.onlineDate"></fmt:message>
					</label>
					<div class="col-md-6">
						<input type="text" name="onlineDate" value="${movie.onlineDate}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="area"> <fmt:message key="search.movie.area"></fmt:message>
					</label>
					<div class="col-md-6">
						<input type="text" name="area" value="${movie.area}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="totalMinutes"> <fmt:message key="search.movie.totalMinutes"></fmt:message>
					</label>
					<div class="col-md-6">
						<input type="text" name="totalMinutes" value="${movie.totalMinutes}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="remark"> <fmt:message key="search.movie.remark"></fmt:message>
					</label>
					<div class="col-md-6">
						<textarea rows="" cols="" name="remark" value="${movie.remark}" class="form-control">${movie.remark}</textarea>
					</div>
				</div>
				<!--@generate-entity-jsp-property-update@-->





				<div class="form-group">
					<div class="col-md-offset-2 col-md-10">
						<button type="submit" class="btn btn-primary">
							<fmt:message key="global.submit.save"></fmt:message>
						</button>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>
<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/search/articleCategory/articleCategory.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/articleCategory/articleCategory.js"/>"></script>



<div class="block-flat">
	<div class="header">
		<h3>
			<c:choose>
				<c:when test="${empty articleCategory.id}">
					<fmt:message key="search.articleCategory.add"></fmt:message>
				</c:when>
				<c:otherwise>
					<fmt:message key="search.articleCategory.edit"></fmt:message>
				</c:otherwise>
			</c:choose>
		</h3>
	</div>
	<div class="content">
		<form action="<c:url value="/search/articleCategory/save"/>" method="post" role="form" class="form-horizontal">
			<input type="hidden" name="id" value="${articleCategory.id}" />
			<fieldset>
				<div class="form-group">
					<label class="col-md-2 control-label" for="name"> <fmt:message key="search.articleCategory.name"></fmt:message>
					</label>
					<div class="col-md-6">
						<input type="text" name="name" value="${articleCategory.name}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="orderIndex"> <fmt:message
							key="search.articleCategory.orderIndex"></fmt:message>
					</label>
					<div class="col-md-6">
						<input type="text" name="orderIndex" value="${articleCategory.orderIndex}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="alias"> <fmt:message key="search.articleCategory.alias"></fmt:message>
					</label>
					<div class="col-md-6">
						<input type="text" name="alias" value="${articleCategory.alias}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="keywords"> <fmt:message key="search.articleCategory.keywords"></fmt:message>
					</label>
					<div class="col-md-6">
						<input type="text" name="keywords" value="${articleCategory.keywords}" class="form-control">
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

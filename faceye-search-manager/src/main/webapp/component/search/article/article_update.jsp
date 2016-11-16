<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/component/search/article/article.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/article/article.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="search.article.edit"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
		<form action="<c:url value="/search/article/save"/>" method="post" role="form" class="form-horizontal">
			<input type="hidden" name="id" value="${article.id}" />
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="name">
					  <fmt:message key="search.article.name"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="name" value="${article.name}" class="form-control">
					</div>
				</div>
                				<div class="form-group">
					<label class="col-md-4 control-label" for="content">
					  <fmt:message key="search.article.content"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="content" value="${article.content}" class="form-control">
					</div>
				</div>
                				<div class="form-group">
					<label class="col-md-4 control-label" for="createDate">
					  <fmt:message key="search.article.createDate"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="createDate" value="${article.createDate}" class="form-control">
					</div>
				</div>
                				<div class="form-group">
					<label class="col-md-4 control-label" for="category">
					  <fmt:message key="search.article.category"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="category" value="${article.category}" class="form-control">
					</div>
				</div>
                <!--@generate-entity-jsp-property-update@-->
				<div class="form-group">
					<div class="col-md-8 pull-right">
						<button type="submit" class="btn btn-success">
							<fmt:message key="global.submit.save"></fmt:message>
						</button>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>
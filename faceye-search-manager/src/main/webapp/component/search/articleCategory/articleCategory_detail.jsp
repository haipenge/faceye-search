<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/search/articleCategory/articleCategory.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/articleCategory/articleCategory.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="search.articleCategory.detail"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="articleCategory">
					  <fmt:message key="search.articleCategory.name"></fmt:message>
					 </label>
					<div class="col-md-4">
						${articleCategory.name}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="articleCategory">
					  <fmt:message key="search.articleCategory.orderIndex"></fmt:message>
					 </label>
					<div class="col-md-4">
						${articleCategory.orderIndex}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="articleCategory">
					  <fmt:message key="search.articleCategory.alias"></fmt:message>
					 </label>
					<div class="col-md-4">
						${articleCategory.alias}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="articleCategory">
					  <fmt:message key="search.articleCategory.keywords"></fmt:message>
					 </label>
					<div class="col-md-4">
						${articleCategory.keywords}
					</div>
				</div>
				<!--@generate-entity-jsp-property-detail@-->
				
				
				
				
			</fieldset>
	</div>
</div>
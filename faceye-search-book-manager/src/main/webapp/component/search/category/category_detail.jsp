<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/component/search/category/category.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/category/category.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="search.category.detail"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="category">
					  <fmt:message key="search.category.name"></fmt:message>
					 </label>
					<div class="col-md-4">
						${category.name}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="category">
					  <fmt:message key="search.category.orderIndex"></fmt:message>
					 </label>
					<div class="col-md-4">
						${category.orderIndex}
					</div>
				</div>
				<!--@generate-entity-jsp-property-detail@-->
				
				
			</fieldset>
	</div>
</div>
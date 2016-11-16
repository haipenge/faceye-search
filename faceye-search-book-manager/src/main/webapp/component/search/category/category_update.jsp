<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/component/search/category/category.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/category/category.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="search.category.edit"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
		<form action="<c:url value="/search/category/save"/>" method="post" role="form" class="form-horizontal">
			<input type="hidden" name="id" value="${category.id}" />
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="name">
					  <fmt:message key="search.category.name"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="name" value="${category.name}" class="form-control">
					</div>
				</div>
                				<div class="form-group">
					<label class="col-md-4 control-label" for="orderIndex">
					  <fmt:message key="search.category.orderIndex"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="orderIndex" value="${category.orderIndex}" class="form-control">
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
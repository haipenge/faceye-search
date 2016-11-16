<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/component/search/book/book.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/book/book.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="search.book.detail"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="book">
					  <fmt:message key="search.book.name"></fmt:message>
					 </label>
					<div class="col-md-4">
						${book.name}
					</div>
				</div>
				<!--@generate-entity-jsp-property-detail@-->
				
			</fieldset>
	</div>
</div>
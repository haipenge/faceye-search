<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/component/parse/filterWord/filterWord.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/parse/filterWord/filterWord.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="parse.filterWord.detail"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="filterWord">
					  <fmt:message key="parse.filterWord.word"></fmt:message>
					 </label>
					<div class="col-md-4">
						${filterWord.word}
					</div>
				</div>
				<!--@generate-entity-jsp-property-detail@-->
				
			</fieldset>
	</div>
</div>
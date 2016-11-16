<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/component/search/section/section.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/section/section.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="search.section.detail"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="section">
					  <fmt:message key="search.section.name"></fmt:message>
					 </label>
					<div class="col-md-4">
						${section.name}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="section">
					  <fmt:message key="search.section.indexNum"></fmt:message>
					 </label>
					<div class="col-md-4">
						${section.indexNum}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="section">
					  <fmt:message key="search.section.createDate"></fmt:message>
					 </label>
					<div class="col-md-4">
						${section.createDate}
					</div>
				</div>
				<!--@generate-entity-jsp-property-detail@-->
				
				
				
			</fieldset>
	</div>
</div>
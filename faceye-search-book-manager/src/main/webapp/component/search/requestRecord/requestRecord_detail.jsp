<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/search/requestRecord/requestRecord.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/requestRecord/requestRecord.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="search.requestRecord.detail"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="requestRecord">
					  <fmt:message key="search.requestRecord.url"></fmt:message>
					 </label>
					<div class="col-md-4">
						${requestRecord.url}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="requestRecord">
					  <fmt:message key="search.requestRecord.referer"></fmt:message>
					 </label>
					<div class="col-md-4">
						${requestRecord.referer}
					</div>
				</div>
				<!--@generate-entity-jsp-property-detail@-->
				
				
			</fieldset>
	</div>
</div>
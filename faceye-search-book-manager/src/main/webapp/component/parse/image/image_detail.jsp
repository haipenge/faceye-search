<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/parse/image/image.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/parse/image/image.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="parse.image.detail"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="image">
					  <fmt:message key="parse.image.storePath"></fmt:message>
					 </label>
					<div class="col-md-4">
						${image.storePath}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="image">
					  <fmt:message key="parse.image.sourceUrl"></fmt:message>
					 </label>
					<div class="col-md-4">
						${image.sourceUrl}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="image">
					  <fmt:message key="parse.image.parseResultId"></fmt:message>
					 </label>
					<div class="col-md-4">
						${image.parseResultId}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="image">
					  <fmt:message key="parse.image.linkId"></fmt:message>
					 </label>
					<div class="col-md-4">
						${image.linkId}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="image">
					  <fmt:message key="parse.image.crawlResultId"></fmt:message>
					 </label>
					<div class="col-md-4">
						${image.crawlResultId}
					</div>
				</div>
				<!--@generate-entity-jsp-property-detail@-->
				
				
				
				
				
			</fieldset>
	</div>
</div>
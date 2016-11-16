<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/parse/image/image.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/parse/image/image.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="parse.image.edit"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
		<form action="<c:url value="/parse/image/save"/>" method="post" role="form" class="form-horizontal">
			<input type="hidden" name="id" value="${image.id}" />
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="storePath">
					  <fmt:message key="parse.image.storePath"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="storePath" value="${image.storePath}" class="form-control">
					</div>
				</div>
                				<div class="form-group">
					<label class="col-md-4 control-label" for="sourceUrl">
					  <fmt:message key="parse.image.sourceUrl"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="sourceUrl" value="${image.sourceUrl}" class="form-control">
					</div>
				</div>
                				<div class="form-group">
					<label class="col-md-4 control-label" for="parseResultId">
					  <fmt:message key="parse.image.parseResultId"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="parseResultId" value="${image.parseResultId}" class="form-control">
					</div>
				</div>
                				<div class="form-group">
					<label class="col-md-4 control-label" for="linkId">
					  <fmt:message key="parse.image.linkId"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="linkId" value="${image.linkId}" class="form-control">
					</div>
				</div>
                				<div class="form-group">
					<label class="col-md-4 control-label" for="crawlResultId">
					  <fmt:message key="parse.image.crawlResultId"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="crawlResultId" value="${image.crawlResultId}" class="form-control">
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
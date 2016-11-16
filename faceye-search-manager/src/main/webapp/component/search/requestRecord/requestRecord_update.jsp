<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/search/requestRecord/requestRecord.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/requestRecord/requestRecord.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="search.requestRecord.edit"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
		<form action="<c:url value="/search/requestRecord/save"/>" method="post" role="form" class="form-horizontal">
			<input type="hidden" name="id" value="${requestRecord.id}" />
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="url">
					  <fmt:message key="search.requestRecord.url"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="url" value="${requestRecord.url}" class="form-control">
					</div>
				</div>
                				<div class="form-group">
					<label class="col-md-4 control-label" for="referer">
					  <fmt:message key="search.requestRecord.referer"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="referer" value="${requestRecord.referer}" class="form-control">
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
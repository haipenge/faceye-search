<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/push/pushRecord/pushRecord.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/push/pushRecord/pushRecord.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="push.pushRecord.detail"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="pushRecord">
					  <fmt:message key="push.pushRecord.articleId"></fmt:message>
					 </label>
					<div class="col-md-4">
						${pushRecord.articleId}
					</div>
				</div>
				<!--@generate-entity-jsp-property-detail@-->
				
			</fieldset>
	</div>
</div>
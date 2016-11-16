<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/search/subject/subject.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/subject/subject.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="search.subject.detail"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="subject">
					  <fmt:message key="search.subject.name"></fmt:message>
					 </label>
					<div class="col-md-4">
						${subject.name}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="subject">
					  <fmt:message key="search.subject.alias"></fmt:message>
					 </label>
					<div class="col-md-4">
						${subject.alias}
					</div>
				</div>
				<!--@generate-entity-jsp-property-detail@-->
				
				
			</fieldset>
	</div>
</div>
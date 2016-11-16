<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/spider/matcherConfig/matcherConfig.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/spider/matcherConfig/matcherConfig.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="spider.matcherConfig.detail"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="matcherConfig">
					  <fmt:message key="spider.matcherConfig.name"></fmt:message>
					 </label>
					<div class="col-md-4">
						${matcherConfig.name}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="matcherConfig">
					  <fmt:message key="spider.matcherConfig.regexp"></fmt:message>
					 </label>
					<div class="col-md-4">
						${matcherConfig.regexp}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="matcherConfig">
					  <fmt:message key="spider.matcherConfig.siteId"></fmt:message>
					 </label>
					<div class="col-md-4">
						${matcherConfig.siteId}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="matcherConfig">
					  <fmt:message key="spider.matcherConfig.type"></fmt:message>
					 </label>
					<div class="col-md-4">
						${matcherConfig.type}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="matcherConfig">
					  <fmt:message key="spider.matcherConfig.prefix"></fmt:message>
					 </label>
					<div class="col-md-4">
						${matcherConfig.prefix}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="matcherConfig">
					  <fmt:message key="spider.matcherConfig.suffix"></fmt:message>
					 </label>
					<div class="col-md-4">
						${matcherConfig.suffix}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="matcherConfig">
					  <fmt:message key="spider.matcherConfig.titleRegexp"></fmt:message>
					 </label>
					<div class="col-md-4">
						${matcherConfig.titleRegexp}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="matcherConfig">
					  <fmt:message key="spider.matcherConfig.typeOfLinkAfterParse"></fmt:message>
					 </label>
					<div class="col-md-4">
						${matcherConfig.typeOfLinkAfterParse}
					</div>
				</div>
				<!--@generate-entity-jsp-property-detail@-->
				
				
				
				
				
				
				
				
			</fieldset>
	</div>
</div>
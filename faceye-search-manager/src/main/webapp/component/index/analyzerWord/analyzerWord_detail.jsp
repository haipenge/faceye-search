<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/index/analyzerWord/analyzerWord.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/index/analyzerWord/analyzerWord.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="index.analyzerWord.detail"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="analyzerWord">
					  <fmt:message key="index.analyzerWord.word"></fmt:message>
					 </label>
					<div class="col-md-4">
						${analyzerWord.word}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="analyzerWord">
					  <fmt:message key="index.analyzerWord.wordCount"></fmt:message>
					 </label>
					<div class="col-md-4">
						${analyzerWord.wordCount}
					</div>
				</div>
				<!--@generate-entity-jsp-property-detail@-->
				
				
			</fieldset>
	</div>
</div>
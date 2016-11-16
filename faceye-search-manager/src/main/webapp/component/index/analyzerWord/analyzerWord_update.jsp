<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/index/analyzerWord/analyzerWord.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/index/analyzerWord/analyzerWord.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="index.analyzerWord.edit"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
		<form action="<c:url value="/index/analyzerWord/save"/>" method="post" role="form" class="form-horizontal">
			<input type="hidden" name="id" value="${analyzerWord.id}" />
			<fieldset>
				<div class="form-group">
					<label class="col-md-4 control-label" for="word"> <fmt:message key="index.analyzerWord.word"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="word" value="${analyzerWord.word}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="wordCount"> <fmt:message key="index.analyzerWord.wordCount"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="wordCount" value="${analyzerWord.wordCount}" class="form-control">
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
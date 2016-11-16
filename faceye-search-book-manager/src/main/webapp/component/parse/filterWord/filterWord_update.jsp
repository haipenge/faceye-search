<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/component/parse/filterWord/filterWord.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/parse/filterWord/filterWord.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="parse.filterWord.edit"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
		<form action="<c:url value="/parse/filterWord/save"/>" method="post" role="form" class="form-horizontal">
			<input type="hidden" name="id" value="${filterWord.id}" />
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="word">
					  <fmt:message key="parse.filterWord.word"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="word" value="${filterWord.word}" class="form-control">
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
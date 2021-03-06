<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/component/search/section/section.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/section/section.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="search.section.edit"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
		<form action="<c:url value="/search/section/save"/>" method="post" role="form" class="form-horizontal">
			<input type="hidden" name="id" value="${section.id}" />
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="name">
					  <fmt:message key="search.section.name"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="name" value="${section.name}" class="form-control">
					</div>
				</div>
                				<div class="form-group">
					<label class="col-md-4 control-label" for="indexNum">
					  <fmt:message key="search.section.indexNum"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="indexNum" value="${section.indexNum}" class="form-control">
					</div>
				</div>
                				<div class="form-group">
					<label class="col-md-4 control-label" for="createDate">
					  <fmt:message key="search.section.createDate"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="createDate" value="${section.createDate}" class="form-control">
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
<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/component/spider/crawlResult/crawlResult.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/spider/crawlResult/crawlResult.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="spider.crawlResult.edit"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
		<form action="<c:url value="/spider/crawlResult/save"/>" method="post" role="form" class="form-horizontal">
			<input type="hidden" name="id" value="${crawlResult.id}" />
			<fieldset>
				<div class="form-group">
					<label class="col-md-4 control-label" for="name"> <fmt:message key="spider.crawlResult.name"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="name" value="${crawlResult.name}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="content"> <fmt:message key="spider.crawlResult.content"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="content" value="${crawlResult.content}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="crawlDate"> <fmt:message key="spider.crawlResult.crawlDate"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="crawlDate" value="${crawlResult.crawlDate}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="link"> <fmt:message key="spider.crawlResult.link"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="linkUrl" value="${crawlResult.linkUrl}" class="form-control">
					</div>
				</div>
				<div class="form-group">
	<label class="col-md-2 control-label" for="isWeixin"> <fmt:message
			key="spider.crawlResult.isWeixin"></fmt:message>
	</label>
	<div class="col-md-6">
		<input type="text" name="isWeixin" value="${crawlResult.isWeixin}" class="form-control">
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
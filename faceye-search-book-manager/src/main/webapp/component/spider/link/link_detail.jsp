<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/component/spider/link/link.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/spider/link/link.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="spider.link.detail"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="link">
					  <fmt:message key="spider.link.url"></fmt:message>
					 </label>
					<div class="col-md-4">
						${link.url}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="link">
					  <fmt:message key="spider.link.isCrawled"></fmt:message>
					 </label>
					<div class="col-md-4">
						${link.isCrawled}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="link">
					  <fmt:message key="spider.link.createDate"></fmt:message>
					 </label>
					<div class="col-md-4">
						${link.createDate}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="link">
					  <fmt:message key="spider.link.isCrawlSuccess"></fmt:message>
					 </label>
					<div class="col-md-4">
						${link.isCrawlSuccess}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="link">
					  <fmt:message key="spider.link.lastCrawlDate"></fmt:message>
					 </label>
					<div class="col-md-4">
						${link.lastCrawlDate}
					</div>
				</div>
				<!--@generate-entity-jsp-property-detail@-->
				
				
				
				
				
			</fieldset>
	</div>
</div>
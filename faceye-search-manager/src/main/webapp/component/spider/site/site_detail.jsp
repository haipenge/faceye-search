<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/component/spider/site/site.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/spider/site/site.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="spider.site.detail"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="site">
					  <fmt:message key="spider.site.name"></fmt:message>
					 </label>
					<div class="col-md-4">
						${site.name}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="site">
					  <fmt:message key="spider.site.isUseSuperParse"></fmt:message>
					 </label>
					<div class="col-md-4">
						${site.isUseSuperParse}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="site">
					  <fmt:message key="spider.site.threadCount"></fmt:message>
					 </label>
					<div class="col-md-4">
						${site.threadCount}
					</div>
				</div>
				<tr>
	<td><fmt:message key="spider.site.lastCrawlDate"></fmt:message></td>
	<td>${site.lastCrawlDate}</td>
</tr>
<tr>
	<td><fmt:message key="spider.site.isCrawlImage"></fmt:message></td>
	<td>${site.isCrawlImage}</td>
</tr>
<!--@generate-entity-jsp-property-detail@-->


				
				
				
			</fieldset>
	</div>
</div>
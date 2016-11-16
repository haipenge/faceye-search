<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/spider/crawlResult/crawlResult.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/spider/crawlResult/crawlResult.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="spider.crawlResult.detail"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
		<div class="content">
			<div class="row">
				<div class="col-md-6">
					<input type="hidden" name="crawlResultId" value="${crawlResult.id}">
					<textarea rows="4" cols="6" class="form-control" name="regexp"></textarea>
				</div>
				<div class="col-md-6">
					<c:forEach items="${matcherConfigs}" var="matcherConfig">
						<p>${matcherConfig.name}:<c:out value="${matcherConfig.regexp}" escapeXml="true" />
						</p>
					</c:forEach>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<button type="button" class="btn btn-info pull-right regexp-test">Regexp Test</button>
				</div>
			</div>
			<div id="msg"></div>
		</div>
		<fieldset>
			<div class="form-group">
				<label class="col-md-1 control-label" for="crawlResult">Is parse: </label>
				<div class="col-md-1">${crawlResult.isParse}</div>
				<label class="col-md-1 control-label" for="crawlResult">Is parse success: </label>
				<div class="col-md-2">${crawlResult.isParseSuccess}</div>
			</div>
			<div class="for-group">
				<label class="col-md-1">Store Path:</label>
				<div class="col-md-10">${crawlResult.storePath}</div>
			</div>
			<div class="form-group">
				<label class="col-md-1 control-label" for="crawlResult">Link:</label>
				<div class="col-md-10">
					<a href="${crawlResult.linkUrl}" target="_blank">${crawlResult.linkUrl}</a>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-12">
					<pre style="font-size: 11px;">
			     <c:out value="${html}" escapeXml="true" />
			    </pre>
				</div>
			</div>
			<!--@generate-entity-jsp-property-detail@-->



		</fieldset>
	</div>
</div>
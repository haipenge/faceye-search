<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/component/spider/site/site.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/spider/site/site.js"/>"></script>


<div class="block-flat">
	<div class="header">
		<h3>
			<c:choose>
				<c:when test="${empty site.id}">
					<fmt:message key="spider.site.add"></fmt:message>
				</c:when>
				<c:otherwise>
					<fmt:message key="spider.site.edit"></fmt:message>
				</c:otherwise>
			</c:choose>
		</h3>
	</div>
	<div class="content">
		<form action="<c:url value="/spider/site/save"/>" method="post" role="form" class="form-horizontal">
			<input type="hidden" name="id" value="${site.id}" />
			<fieldset>
				<div class="form-group">
					<label class="col-md-2 control-label" for="name"> <fmt:message key="spider.site.name"></fmt:message>
					</label>
					<div class="col-md-8">
						<input type="text" name="name" value="${site.name}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="isUseSuperParse"> <fmt:message key="spider.site.isUseSuperParse"></fmt:message>
					</label>
					<div class="col-md-8">
						<input type="text" name="isUseSuperParse" value="${site.isUseSuperParse}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="threadCount"> <fmt:message key="spider.site.threadCount"></fmt:message>
					</label>
					<div class="col-md-8">
						<input type="text" name="threadCount" value="${site.threadCount}" class="form-control">
					</div>
				</div>
				<!--@generate-entity-jsp-property-update@-->
				<div class="form-group">
					<div class="col-md-offset-2 col-md-10">
						<button type="submit" class="btn btn-primary">
							<fmt:message key="global.submit.save"></fmt:message>
						</button>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>

<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/weixin/weixin/weixin.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/weixin/weixin/weixin.js"/>"></script>
<div class="block-flat">
	<div class="header">
		<h3>
			<c:choose>
				<c:when test="${empty weixin.id}">
					<fmt:message key="weixin.weixin.add"></fmt:message>
				</c:when>
				<c:otherwise>
					<fmt:message key="weixin.weixin.edit"></fmt:message>
				</c:otherwise>
			</c:choose>
		</h3>
	</div>
	<div class="content">
		<form action="<c:url value="/weixin/weixin/save"/>" method="post" role="form" class="form-horizontal">
			<input type="hidden" name="id" value="${weixin.id}" />
			<fieldset>
				<div class="form-group">
					<label class="col-md-2 control-label" for="name"> <fmt:message key="weixin.weixin.name"></fmt:message>
					</label>
					<div class="col-md-6">
						<input type="text" name="name" value="${weixin.name}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="articleId"> <fmt:message key="weixin.weixin.articleId"></fmt:message>
					</label>
					<div class="col-md-6">
						<input type="text" name="articleId" value="${weixin.articleId}" class="form-control">
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
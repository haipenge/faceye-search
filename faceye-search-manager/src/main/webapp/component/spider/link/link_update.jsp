<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/component/spider/link/link.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/spider/link/link.js"/>"></script>
<div class="block-flat">
	<div class="header">
		<h3>
			<c:choose>
				<c:when test="${empty link.id}">
					<fmt:message key="spider.link.add"></fmt:message>
				</c:when>
				<c:otherwise>
					<fmt:message key="spider.link.edit"></fmt:message>
				</c:otherwise>
			</c:choose>
		</h3>
	</div>
	<div class="content">
		<form action="<c:url value="/spider/link/save"/>" method="post" role="form" class="form-horizontal">
			<input type="hidden" name="id" value="${link.id}" />
			<fieldset>
				<div class="form-group">
					<label class="col-md-2 control-label" for="url"> <fmt:message key="spider.link.url"></fmt:message>
					</label>
					<div class="col-md-10">
						<input type="text" name="url" value="${link.url}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="isCrawled"> <fmt:message key="spider.site"></fmt:message>
					</label>
					<div class="col-md-10">
						<select name="siteId" class="form-control">
							<option><fmt:message key="spider.site"></fmt:message></option>
							<c:forEach items="${sites}" var="site">
								<option value="${site.id}" <c:if test="${site.id eq link.siteId }">selected</c:if>>${site.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-md-2 control-label" for="lastCrawlDate"> <fmt:message key="spider.link.type"></fmt:message>
					</label>
					<div class="col-md-10">
						<select name="type" class="form-control">
							<option><fmt:message key="spider.link.type"></fmt:message></option>
							<option value="0" <c:if test="${link.type eq 0 }">selected</c:if>><fmt:message key="spider.link.type.0"></fmt:message></option>
							<option value="1" <c:if test="${link.type eq 1 }">selected</c:if>><fmt:message key="spider.link.type.1"></fmt:message></option>
							<option value="2" <c:if test="${link.type eq 2 }">selected</c:if>><fmt:message key="spider.link.type.2"></fmt:message></option>
						</select>
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
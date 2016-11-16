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
					<div class="col-md-6">
						<input type="text" name="name" value="${site.name}" class="form-control">
					</div>
				</div>

				<div class="form-group">
					<label class="col-md-2 control-label" for="threadCount"> <fmt:message key="spider.site.threadCount"></fmt:message>
					</label>
					<div class="col-md-6">
						<select name="threadCount" class="form-control">
							<c:forEach begin="1" end="5" step="1" var="thread">
								<option value="${thread}" <c:if test="${thread eq site.threadCount }">selected</c:if>>${thread}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="isUseSuperParse"> <fmt:message key="spider.site.isUseSuperParse"></fmt:message>
					</label>
					<div class="col-md-6">
						<label class="radio-inline"> <input type="radio" name="isUseSuperParse" value="true" <c:if test="${site.isUseSuperParse}">checked</c:if>> <f:boolean
								value="true" />
						</label> <label class="radio-inline"> <input type="radio" name="isUseSuperParse" value="false" <c:if test="${!site.isUseSuperParse}">checked</c:if>> <f:boolean
								value="false" />
						</label>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="isCrawlImage"> <fmt:message key="spider.site.isCrawlImage"></fmt:message>
					</label>
					<div class="col-md-6">
						<label class="radio-inline"> <input type="radio" name="isCrawlImage" value="true" <c:if test="${site.isCrawlImage}">checked</c:if>> <f:boolean value="true" />
						</label> <label class="radio-inline"> <input type="radio" name="isCrawlImage" value="false" <c:if test="${!site.isCrawlImage}">checked</c:if>> <f:boolean
								value="false" />
						</label>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="isCrawl"> <fmt:message key="spider.site.isCrawl"></fmt:message>
					</label>
					<div class="col-md-6">
						<label class="radio-inline"> <input type="radio" name="isCrawl" value="true" <c:if test="${site.isCrawl}">checked</c:if>> <f:boolean value="true" />
						</label> <label class="radio-inline"> <input type="radio" name="isCrawl" value="false" <c:if test="${!site.isCrawl}">checked</c:if>> <f:boolean value="false" />
						</label>
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

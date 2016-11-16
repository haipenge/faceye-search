<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/spider/matcherConfig/matcherConfig.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/spider/matcherConfig/matcherConfig.js"/>"></script>



<div class="block-flat">
	<div class="header">
		<h3>
			<c:choose>
				<c:when test="${empty matcherConfig.id}">
					<fmt:message key="spider.matcherConfig.add"></fmt:message>
				</c:when>
				<c:otherwise>
					<fmt:message key="spider.matcherConfig.edit"></fmt:message>
				</c:otherwise>
			</c:choose>
		</h3>
	</div>
	<div class="content">
		<form action="<c:url value="/spider/matcherConfig/save"/>" method="post" role="form" class="form-horizontal">
			<input type="hidden" name="id" value="${matcherConfig.id}" />
			<fieldset>
				<div class="form-group">
					<label class="col-md-2 control-label" for="name"> <fmt:message key="spider.matcherConfig.name"></fmt:message>
					</label>
					<div class="col-md-8">
						<input type="text" name="name" value="${matcherConfig.name}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="regexp"> <fmt:message key="spider.matcherConfig.regexp"></fmt:message>
					</label>
					<div class="col-md-8">
						<textarea rows="5" cols="4" name="regexp" class="form-control">${matcherConfig.regexp}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="siteId"> <fmt:message key="spider.matcherConfig.siteId"></fmt:message>
					</label>
					<div class="col-md-8">
						<select name="siteId" class="form-control">
							<option>Select Site</option>
							<c:forEach items="${sites}" var="site">
								<option value="${site.id}" <c:if test="${site.id eq matcherConfig.siteId}">selected</c:if>>${site.name}</option>
							</c:forEach>
						</select>

					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="type"> <fmt:message key="spider.matcherConfig.type"></fmt:message>
					</label>
					<div class="col-md-8">
						<select name="type" class="form-control">
							<option>Select Link Type</option>
							<option value="1" <c:if test="${matcherConfig.type eq 1 }">selected</c:if>>List Page</option>
							<option value="2" <c:if test="${matcherConfig.type eq 2 }">selected</c:if>>Detail Page</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="prefix"> <fmt:message key="spider.matcherConfig.prefix"></fmt:message>
					</label>
					<div class="col-md-8">
						<input type="text" name="prefix" value="${matcherConfig.prefix}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="suffix"> <fmt:message key="spider.matcherConfig.suffix"></fmt:message>
					</label>
					<div class="col-md-8">
						<input type="text" name="suffix" value="${matcherConfig.suffix}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="titleRegexp"> <fmt:message
							key="spider.matcherConfig.titleRegexp"></fmt:message>
					</label>
					<div class="col-md-8">
						<input type="text" name="titleRegexp" value="${matcherConfig.titleRegexp}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="typeOfLinkAfterParse"> <fmt:message
							key="spider.matcherConfig.typeOfLinkAfterParse"></fmt:message>
					</label>
					<div class="col-md-8">
						<select name="typeOfLinkAfterParse" class="form-control">
							<option>Select Link Type</option>
							<option value="1" <c:if test="${matcherConfig.typeOfLinkAfterParse eq 1 }">selected</c:if>>List Page</option>
							<option value="2" <c:if test="${matcherConfig.typeOfLinkAfterParse eq 2 }">selected</c:if>>Detail Page</option>
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

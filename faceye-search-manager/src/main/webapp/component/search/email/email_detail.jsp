<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/component/search/email/email.css"/>" />
<script type="text/javascript"
	src="<c:url value="/js/component/search/email/email.js"/>"></script>
<div class="block-flat">
	<div class="header">
		<h3>
			<fmt:message key="search.email.detail"></fmt:message>
		</h3>
	</div>
	<div class="content">
		<div classs="table-responsive">
			<table class="table table-bordered table-hover">
			 <tr>
	<td><fmt:message key="search.email.address"></fmt:message></td>
	<td>${email.address}</td>
</tr>
<!--@generate-entity-jsp-property-detail@-->

			</table>
		</div>
	</div>
</div>
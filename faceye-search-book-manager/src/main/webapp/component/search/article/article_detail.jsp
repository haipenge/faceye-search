<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/component/search/article/article.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/article/article.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="search.article.detail"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="article">
					  <fmt:message key="search.article.name"></fmt:message>
					 </label>
					<div class="col-md-4">
						${article.name}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="article">
					  <fmt:message key="search.article.content"></fmt:message>
					 </label>
					<div class="col-md-4">
						${article.content}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="article">
					  <fmt:message key="search.article.createDate"></fmt:message>
					 </label>
					<div class="col-md-4">
						${article.createDate}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="article">
					  <fmt:message key="search.article.category"></fmt:message>
					 </label>
					<div class="col-md-4">
						${article.category}
					</div>
				</div>
				<!--@generate-entity-jsp-property-detail@-->
				
				
				
				
			</fieldset>
	</div>
</div>
<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/book/book/book.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/book/book/book.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="book.book.detail"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="book">
					  <fmt:message key="book.book.name"></fmt:message>
					 </label>
					<div class="col-md-4">
						${book.name}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="book">
					  <fmt:message key="book.book.author"></fmt:message>
					 </label>
					<div class="col-md-4">
						${book.author}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="book">
					  <fmt:message key="book.book.pic"></fmt:message>
					 </label>
					<div class="col-md-4">
						${book.pic}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="book">
					  <fmt:message key="book.book.categoryId"></fmt:message>
					 </label>
					<div class="col-md-4">
						${book.categoryId}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="book">
					  <fmt:message key="book.book.categoryName"></fmt:message>
					 </label>
					<div class="col-md-4">
						${book.categoryName}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="book">
					  <fmt:message key="book.book.content"></fmt:message>
					 </label>
					<div class="col-md-4">
						${book.content}
					</div>
				</div>
				<!--@generate-entity-jsp-property-detail@-->
				
				
				
				
				
				
			</fieldset>
	</div>
</div>
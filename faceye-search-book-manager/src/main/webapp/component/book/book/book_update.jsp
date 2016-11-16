<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/book/book/book.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/book/book/book.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="book.book.edit"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
		<form action="<c:url value="/book/book/save"/>" method="post" role="form" class="form-horizontal">
			<input type="hidden" name="id" value="${book.id}" />
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="name">
					  <fmt:message key="book.book.name"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="name" value="${book.name}" class="form-control">
					</div>
				</div>
                				<div class="form-group">
					<label class="col-md-4 control-label" for="author">
					  <fmt:message key="book.book.author"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="author" value="${book.author}" class="form-control">
					</div>
				</div>
                				<div class="form-group">
					<label class="col-md-4 control-label" for="pic">
					  <fmt:message key="book.book.pic"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="pic" value="${book.pic}" class="form-control">
					</div>
				</div>
                				<div class="form-group">
					<label class="col-md-4 control-label" for="categoryId">
					  <fmt:message key="book.book.categoryId"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="categoryId" value="${book.categoryId}" class="form-control">
					</div>
				</div>
                				<div class="form-group">
					<label class="col-md-4 control-label" for="categoryName">
					  <fmt:message key="book.book.categoryName"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="categoryName" value="${book.categoryName}" class="form-control">
					</div>
				</div>
                				<div class="form-group">
					<label class="col-md-4 control-label" for="content">
					  <fmt:message key="book.book.content"></fmt:message>
					</label>
					<div class="col-md-4">
						<input type="text" name="content" value="${book.content}" class="form-control">
					</div>
				</div>
                <!--@generate-entity-jsp-property-update@-->
                
                
                
                
                
                
				<div class="form-group">
					<div class="col-md-8 pull-right">
						<button type="submit" class="btn btn-success">
							<fmt:message key="global.submit.save"></fmt:message>
						</button>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>
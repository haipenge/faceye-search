<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/js/lib/bootstrap3-wysihtml5-bower-master/dist/bootstrap3-wysihtml5.min.css"/>"></link>
<script type="text/javascript"
	src="<c:url value="/js/lib/bootstrap3-wysihtml5-bower-master/dist/bootstrap3-wysihtml5.all.min.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/component/parse/parseResult/parseResult.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/parse/parseResult/parseResult.js"/>"></script>

<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="parse.parseResult.edit"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
		<form action="<c:url value="/parse/parseResult/save"/>" method="post" role="form" class="form-horizontal">
			<input type="hidden" name="id" value="${parseResult.id}" />
			<fieldset>
				<div class="form-group">
					<label class="col-md-1 control-label" for="name"> <fmt:message key="parse.parseResult.name"></fmt:message>
					</label>
					<div class="col-md-11">
						<input type="text" name="name" value="${parseResult.name}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-1 control-label" for="keywords">Key:</label>
					<div class="col-md-11">
						<input type="text" class="form-control" name="keywords"
							value="<c:forEach var="key" items="${titleAnalyzer}">${key.text},</c:forEach>">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-1 control-label" for="categoryId">Category:</label>
					<div class="col-md-4">
						<select name="categoryId" class="form-control">
							<option>Select Category</option>
							<c:forEach var="category" items="${categories}">
								<option value="${category.id}"
									<c:if test="${not empty parseResult.categoryId && category.id eq parseResult.categoryId }">selected</c:if>>${category.name }</option>
							</c:forEach>
						</select>
					</div>
					<label class="col-md-1 control-label" for="subjectId">Subject:</label>
					<div class="col-md-4">
						<select name="subjectId" class="form-control">
							<option>Select Subject</option>
							<c:forEach var="subject" items="${subjects}">
								<option value="${subject.id}"
									<c:if test="${not empty parseResult.subjectId && parseResult.subjectId eq subject.id}"></c:if>>${subject.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-1 control-label" for="content"> <fmt:message key="parse.parseResult.content"></fmt:message>
					</label>
					<div class="col-md-11">
						<textarea class="textarea form-control" name="content" placeholder="Enter text ..."
							style="width: 100%; height: 200px; font-size: 14px; line-height: 18px;">${parseResult.content}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-1 control-label" for="keywords">Key:</label>
					<div class="col-md-11">
						<c:forEach var="key" items="${contentAnalyzer}">${key.text},</c:forEach>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-1 control-label" for="description">Description </label>
					<div class="col-md-11">
						<input type="text" name="description" value="${parseResult.description}" class="form-control">
					</div>
				</div>
				<div class="form-group">
	<label class="col-md-2 control-label" for="isWeixin"> <fmt:message
			key="parse.parseResult.isWeixin"></fmt:message>
	</label>
	<div class="col-md-6">
		<input type="text" name="isWeixin" value="${parseResult.isWeixin}" class="form-control">
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
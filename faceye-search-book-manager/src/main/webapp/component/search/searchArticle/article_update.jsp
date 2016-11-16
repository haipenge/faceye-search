<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/js/lib/jQuery-File-Upload/css/jquery.fileupload.css"/>">
<link rel="stylesheet" href="<c:url value="/js/lib/jQuery-File-Upload/css/jquery.fileupload-ui.css"/>">
<script type="text/javascript" src="<c:url value="/js/lib/tinymce/tinymce.min.js"/>"></script>
<!-- 
<link rel="stylesheet" type="text/css"
	href="<c:url value="/js/lib/bootstrap3-wysihtml5-bower-master/dist/bootstrap3-wysihtml5.min.css"/>"></link>
<script type="text/javascript"
	src="<c:url value="/js/lib/bootstrap3-wysihtml5-bower-master/dist/bootstrap3-wysihtml5.all.min.js"/>"></script>
	 -->
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/search/searchArticle/searchArticle.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/searchArticle/searchArticle.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="search.searchArticle.edit"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
		<form action="<c:url value="/search/searchArticle/save"/>" method="post" role="form" class="form-horizontal">
			<input type="hidden" name="id" value="${searchArticle.id}" /> <input type="hidden" name="categoryId"
				value="${searchArticle.categoryId }" /> <input type="hidden" name="subjectId" value="${searchArticle.subjectId }" />
			<input type="hidden" name="clickCount" value="${searchArticle.clickCount}" />
			<fieldset>
				<div class="form-group">
					<label class="col-md-2 control-label" for="name"> <fmt:message key="search.searchArticle.name"></fmt:message>
					</label>
					<div class="col-md-10">
						<input type="text" name="name" value="${searchArticle.name}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="alias">Alias </label>
					<div class="col-md-10">
						<input type="text" name="alias" value="${searchArticle.alias}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="keywords"> keywords </label>
					<div class="col-md-10">
						<input type="text" name="keywords" value="${searchArticle.keywords}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="description">Description </label>
					<div class="col-md-10">
						<textarea rows="" cols="" name="description" class="form-control">${searchArticle.description}</textarea>
					</div>
				</div>

				<div class="form-group">
					<label class="col-md-2 control-label" for="categoryName"> Category Name </label>
					<div class="col-md-10">
						<input type="text" name="categoryName" value="${searchArticle.categoryName}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="categoryAlias"> CategoryAlias </label>
					<div class="col-md-10">
						<input type="text" name="categoryAlias" value="${searchArticle.categoryAlias}" class="form-control">
					</div>
				</div>

				<div class="form-group">
					<label class="col-md-2 control-label" for="subjectName"> Subject Name </label>
					<div class="col-md-10">
						<input type="text" name="subjectName" value="${searchArticle.subjectName}" class="form-control">
					</div>
				</div>

				<div class="form-group">
					<label class="col-md-2 control-label" for="subjectAlias">Subject Alias </label>
					<div class="col-md-10">
						<input type="text" name="subjectAlias" value="${searchArticle.subjectAlias}" class="form-control">
					</div>
				</div>

				<div class="form-group">
					<label class="col-md-2 control-label" for="content"> <fmt:message key="search.searchArticle.content"></fmt:message>
					</label>
					<div class="col-md-10">
						<textarea class="textarea form-control" name="content" placeholder="Enter text ..."
							style="width: 100%; height: 200px; font-size: 14px; line-height: 18px;">${searchArticle.content}</textarea>
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

<!-- Start File Upload -->
<div class="content">
	<label class="col-md-2 control-label">Upload:</label>
	<div id="fileupload" class="col-md-6">
		<!-- 
			<form id="fileupload" action="<c:url value="/UploadServlet"/>" method="POST" enctype="multipart/form-data" role="form" class="form-horizontal" style="border:1px solid blue;">
				 -->
		<!-- Redirect browsers with JavaScript disabled to the origin page -->
		<noscript></noscript>
		<!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
		<div class="row fileupload-buttonbar">
			<div class="col-md-10">
				<!-- The fileinput-button span is used to style the file input field as button -->
				<span class="btn btn-success fileinput-button"> <i class="glyphicon glyphicon-plus"></i> <span><s:text
							name="faceye.mall.goodsImage.add" /></span> <input type="file" name="files[]" multiple>
				</span>
				<button type="submit" class="btn btn-primary start">
					<i class="glyphicon glyphicon-upload"></i> <span><fmt:message key="global.file.upload" /></span>
				</button>
				<button type="reset" class="btn btn-warning cancel">
					<i class="glyphicon glyphicon-ban-circle"></i> <span><fmt:message key="global.cancel" /></span>
				</button>
				<button type="button" class="btn btn-danger delete">
					<i class="glyphicon glyphicon-trash"></i> <span><fmt:message key="global.remove" /></span>
				</button>
				<input type="checkbox" class="toggle">
				<!-- The global file processing state -->
				<span class="fileupload-process"></span>
			</div>
			<!-- The global progress state -->
			<div class="col-md-2 fileupload-progress fade">
				<!-- The global progress bar -->
				<div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
					<div class="progress-bar progress-bar-success" style="width: 0%;"></div>
				</div>
				<!-- The extended global progress state -->
				<div class="progress-extended">&nbsp;</div>
			</div>
		</div>
		<!-- The table listing the files available for upload/download -->
		<div class="row">
			<div class="table-responsive">
				<table role="presentation" class="table table-striped table-bordered table-hover">
					<tbody class="files"></tbody>
				</table>
			</div>

		</div>
		<div class="form-group">
			<div class="col-md-8 pull-right">
				<button type="button" id="doc-save-btn" class="btn btn-success">
					<fmt:message key="global.submit.save"></fmt:message>
				</button>
			</div>
		</div>
		<!-- 
			</form> -->
	</div>
</div>

<!-- End File Upload -->


<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview">
                  
            </span>
        </td>
<!--
        <td>
            <p class="name">{%=file.name%}</p>
            <strong class="error text-danger"></strong>
        </td>
        <td>
            <p class="size"><fmt:message key="global.file.upload"/></p>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
-->
        <td>
            {% if (!i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start" disabled>
                    <i class="glyphicon glyphicon-upload"></i>
                    <span><fmt:message key="global.file.upload"/></span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span><fmt:message key="global.cancel"/></span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade" id="{%=file.key%}">
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) {  %}
                    <a href="{%=file.thumbnailUrl%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}"></a>
     <!--
                    <input type="hidden" name="filename" value="{%=file.name%}">
                    <input type="hidden" name="file_name[]" valu="{%=file.name%}">
-->
                {% } %}
            </span>

        </td>

        <td>
            <p class="name">
                {% if (file.url) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                {% } else { %}
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div><span class="label label-danger">Error</span> {%=file.error%}</div>
            {% } %}
        </td>
<!--
        <td>
            <span class="size">{%=o.formatFileSize(file.size)%}</span>
        </td>
-->

        <td>
            {% if (file.deleteUrl) { %}
                <button class="btn btn-danger delete" data-key="{%=file.key%}" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    <i class="glyphicon glyphicon-trash"></i>
                    <span><fmt:message key="global.remove"/></span>
                </button>
                <input type="checkbox" name="delete" value="1" class="toggle">
            {% } else { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span><fmt:message key="global.cancel"/></span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>

<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
<script src="<c:url value="/js/lib/jQuery-File-Upload/js/vendor/jquery.ui.widget.js"/>"></script>
<!-- The Templates plugin is included to render the upload/download listings -->
<script src="<c:url value="/js/lib/jQuery-File-Upload/js/tmpl.min.js"/>"></script>
<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
<script src="<c:url value="/js/lib/jQuery-File-Upload/js/load-image.min.js"/>"></script>
<!-- The Canvas to Blob plugin is included for image resizing functionality -->
<script src="<c:url value="/js/lib/jQuery-File-Upload/js/canvas-to-blob.min.js"/>"></script>
<!-- blueimp Gallery script -->
<script src="<c:url value="/js/lib/jQuery-File-Upload/js/jquery.blueimp-gallery.min.js"/>"></script>
<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
<script src="<c:url value="/js/lib/jQuery-File-Upload/js/jquery.iframe-transport.js"/>"></script>
<!-- The basic File Upload plugin -->
<script src="<c:url value="/js/lib/jQuery-File-Upload/js/jquery.fileupload.js"/>"></script>
<!-- The File Upload processing plugin -->
<script src="<c:url value="/js/lib/jQuery-File-Upload/js/jquery.fileupload-process.js"/>"></script>
<!-- The File Upload image preview & resize plugin -->
<script src="<c:url value="/js/lib/jQuery-File-Upload/js/jquery.fileupload-image.js"/>"></script>
<!-- The File Upload audio preview plugin -->
<script src="<c:url value="/js/lib/jQuery-File-Upload/js/jquery.fileupload-audio.js"/>"></script>
<!-- The File Upload video preview plugin -->
<script src="<c:url value="/js/lib/jQuery-File-Upload/js/jquery.fileupload-video.js"/>"></script>
<!-- The File Upload validation plugin -->
<script src="<c:url value="/js/lib/jQuery-File-Upload/js/jquery.fileupload-validate.js"/>"></script>
<!-- The File Upload user interface plugin -->
<script src="<c:url value="/js/lib/jQuery-File-Upload/js/jquery.fileupload-ui.js"/>"></script>
<!-- The main application script -->
<script src="<c:url value="/js/lib/jQuery-File-Upload/js/upload.js"/>"></script>
<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
<!--[if (gte IE 8)&(lt IE 10)]>
<script src="js/cors/jquery.xdr-transport.js"></script>
<![endif]-->
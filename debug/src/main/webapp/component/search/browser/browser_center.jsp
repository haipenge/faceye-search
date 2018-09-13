<%@ include file="/component/core/taglib/taglib.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript" src="<c:url value="/js/lib/tinymce/tinymce.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/component/search/browser/Browser.js"/>"></script>
<!-- 
<link rel="stylesheet" type="text/css"
	href="<c:url value="/js/lib/bootstrap3-wysihtml5-bower-master/dist/bootstrap3-wysihtml5.min.css"/>"></link>
<script type="text/javascript"
	src="<c:url value="/js/lib/bootstrap3-wysihtml5-bower-master/dist/bootstrap3-wysihtml5.all.min.js"/>"></script>
	-->
<div class="row">
	<div class="col-lg-6">
		<!-- show pages -->
		<div class="container-fluid" id="browser-view">
			<iframe id="browser-view-iframe" style="width: 100%; height: 100%; border: none;"></iframe>
		</div>
	</div>
	<div class="col-lg-6">
		<div class="container-fluid" id="browser">
			<div class="row">
				<form role="form" class="form-horizontal" onSubmit="return false;">
					<div class="form-group">
						<div class="col-lg-12">
							<input type="text" class="form-control" placeholder="http://www.faceye.net">
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-12">
							<textarea name="content" placeholder="Enter text ..." style="width: 100%; height: 200px;"></textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-12">
							<button class="btn-lg btn-success pull-left" type="button">Go</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
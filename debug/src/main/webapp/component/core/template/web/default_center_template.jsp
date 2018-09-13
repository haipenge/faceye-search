<%@ include file="/component/core/taglib/taglib.jsp"%>
<div class="row">
	<div class="col-md-7 col-sm-offset-1 bg-white">
		<tiles:insertAttribute name="default-center-left"></tiles:insertAttribute>
	</div>
	<div class="col-md-3 bg-white" id="right-navbar" style="margin-left:5px;">
		<tiles:insertAttribute name="default-center-right"></tiles:insertAttribute>
	</div>
	<div class="col-md-1"></div>
</div>
<%@ include file="/component/core/taglib/taglib.jsp"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<html>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="">
<meta http-equiv="description" content="">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="<c:url value="/js/lib/bootstrap/css/bootstrap.min.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/js/lib/bootstrap/css/bootstrap-theme.min.css"/>" />
<script type="text/javascript" src="<c:url value="/js/lib/jquery/jquery-min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/lib/bootstrap/js/bootstrap.min.js"/>"></script>
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 20px;
}
</style>
</head>
<body>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="panel-title">Default Error!</div>
		</div>
		<div class="panel-body">
			Hi baby~.The page Can not be found,you can follow me:<br> <a href="<c:url value="${host}/default/about.html"/>">About Me</a>
		</div>
		<%
			Exception ex = (Exception) request.getAttribute("exception");
		%>
		<H2>Exception:</H2>
		<%
			if (exception != null) {
				
				exception.printStackTrace(new PrintWriter(out));
			}
			if (ex != null) {
				ex.printStackTrace(new PrintWriter(out));
			}
		%>
	</div>
</body>
</html>

<%@ include file="/component/core/taglib/taglib.jsp"%>
<div class="content">
	<ol class="breadcrumb">
		<li><a href="<c:url value="${host}/"/>"><fmt:message key="global.home"></fmt:message></a></li>
		<li><a href="<c:url value="${host}/search/index.html?alias=${article.categoryAlias}"/>">${article.categoryName }</a></li>
		<li class="active">${article.name}</li>
	</ol>
</div>
<div class="page-header">
	<h3>${article.name}</h3>
</div>
<div class="content">
	<script type="text/javascript">
		var cpro_id = "u2129117";
		(window["cproStyleApi"] = window["cproStyleApi"] || {})[cpro_id] = {
			at : "3",
			rsi0 : "750",
			rsi1 : "90",
			pat : "6",
			tn : "baiduCustNativeAD",
			rss1 : "#FFFFFF",
			conBW : "1",
			adp : "1",
			ptt : "0",
			titFF : "%E5%BE%AE%E8%BD%AF%E9%9B%85%E9%BB%91",
			titFS : "14",
			rss2 : "#000000",
			titSU : "0",
			ptbg : "90",
			piw : "0",
			pih : "0",
			ptp : "0"
		}
	</script>
	<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>

	<!-- 
	<script type="text/javascript">
		BAIDU_CLB_fillSlot("1058997");
	<script>
-->
	<script type="text/javascript">
var cpro_id="u2135589";
(window["cproStyleApi"] = window["cproStyleApi"] || {})[cpro_id]={at:"3",rsi0:"750",rsi1:"250",pat:"1",tn:"baiduCustNativeAD",rss1:"#FFFFFF",conBW:"0",adp:"1",ptt:"0",titFF:"%E5%BE%AE%E8%BD%AF%E9%9B%85%E9%BB%91",titFS:"14",rss2:"#006699",titSU:"0",tft:"0",tlt:"1",ptbg:"90",piw:"0",pih:"0",ptp:"0"}
</script>
<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
	${article.content}
	<p>
		<a href="<c:url value="${host}/search/${article.id}.html"/>">${article.name}</a>
	</p>
</div>
<div class="content">
	<script type="text/javascript">
		var cpro_id = "u2135663";
	</script>
	<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
</div>
<div class="content">
	<h4>
		<fmt:message key="search.article.likes" />
	</h4>
	<c:forEach items="${searchResults.content}" var="searchResult" varStatus="status">
		<p>
			&nbsp;&nbsp;${status.count}.&nbsp;&nbsp;<a href="<c:url value="${host}/search/${searchResult.id}.html"/>">${searchResult.name}</a>
		</p>
	</c:forEach>
</div>
<div class="content">
	<h4>
		<fmt:message key="search.article.push.category"></fmt:message>
	</h4>
	<p>
		<c:forEach items="${categories}" var="category" varStatus="status">
			<c:set var="lab-class" value="label-danger"></c:set>
			<c:if test="${status.count mod 2 == 0 }">
				<c:set var="lab-class" value="label-warning"></c:set>
			</c:if>
			<span class="label ${lab-class }"><a
				href="<c:url value="${host}/search/index.html?alias=${category.alias}" />">${category.name}</a></span>
		</c:forEach>
	</p>
</div>
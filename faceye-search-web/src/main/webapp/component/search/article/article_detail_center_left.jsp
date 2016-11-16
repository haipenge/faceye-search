<%@ include file="/component/core/taglib/taglib.jsp"%>
<script type="text/javascript" src="<c:url value="/js/component/search/article/article.js"/>"></script>
<ol class="breadcrumb">
	<li><a href="<c:url value="${host}/"/>"><fmt:message key="global.home"></fmt:message></a></li>
	<li><a href="<c:url value="${host}/search/index.html?alias=${article.categoryAlias}"/>">${article.categoryName }</a></li>
	<li class="active">${article.name}</li>
</ol>
<div class="page-header" id="bottom-ad" style="margin-top: 0px;">
	<h1>${article.name}&nbsp;&nbsp;<small><span class="label label-success"><fmt:message key="search.movie.to.view" /></span>&nbsp;->&nbsp;<a
			href="<c:url value="/search/movie/detail/${movie.id}.html"/>">${movie.name }</a></small>
	</h1>
	
	<div class="content">
		<script type="text/javascript">
			var cpro_id = "u2683010";
		</script>
		<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
	</div>
</div>
<c:if test="${not empty article.description }">
	<blockquote>
		<p>${article.description}</p>
	</blockquote>
</c:if>
<p>
	<fmt:message key="search.article.what" />
	<a href="#article-content" title="${article.name}">${article.name}</a>
</p>
<div class="content">
	<script type="text/javascript">
		var cpro_id = "u2682946";
	</script>
	<script type="text/javascript" src="http://cpro.baidustatic.com/cpro/ui/c.js"></script>
</div>
<!-- 
<div class="row" style="margin-top: 15px;">
	<div class="content text-center">
		<script type="text/javascript">
			var cpro_id = "u2670132";
		</script>
		<script type="text/javascript" src="http://cpro.baidustatic.com/cpro/ui/c.js"></script>
	</div>
</div>
 -->
<div class="content">
	<script type="text/javascript">
		var cpro_id = "u2682957";
	</script>
	<script type="text/javascript" src="http://cpro.baidustatic.com/cpro/ui/c.js"></script>

</div>
<div class="content" id="article-content" style="margin-top: 5px;">
	${article.content}
	<p>
		<a href="<c:url value="${host}/search/${article.id}.html"/>" title="${article.name }">${article.name}</a>
	</p>
	<h4>${article.name}
		&nbsp;&nbsp;
		<fmt:message key="search.article.discuss" />
	</h4>
</div>
<div class="row">
	<div class="content">
		<script type="text/javascript">
			var cpro_id = "u2682986";
		</script>
		<script type="text/javascript" src="http://cpro.baidustatic.com/cpro/ui/c.js"></script>
	</div>
	<div class="content text-center">
		<script type="text/javascript">
			var cpro_id = "u2670152";
		</script>
		<script type="text/javascript" src="http://cpro.baidustatic.com/cpro/ui/c.js"></script>
	</div>
	<div class="content text-center" style="margin-top: 5px;">
		<script type="text/javascript">
			var cpro_id = "u2670156";
		</script>
		<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
	</div>
	<!-- 
	<div class="content" style="margin-top: 5px;">
		<script type="text/javascript">
			var cpro_id = "u2213691";
		</script>
		<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
	</div>
	 -->
</div>
<div class="content">
	<h5>
		<fmt:message key="search.article.likes" />
	</h5>
	<c:forEach items="${searchResults.content}" var="searchResult" varStatus="status">
		<p>
			&nbsp;&nbsp;${status.count}.&nbsp;&nbsp;<a href="<c:url value="${host}/search/${searchResult.id}.html"/>" title="${searchResult.name }">${searchResult.name}</a>
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
			<span class="label ${lab-class }"><a href="<c:url value="${host}/search/index.html?alias=${category.alias}" />">${category.name}</a></span>
		</c:forEach>
	</p>
</div>
<script type="text/javascript">
$(document).ready(function(){
	Tools.copy($('#copy'));
});
</script>
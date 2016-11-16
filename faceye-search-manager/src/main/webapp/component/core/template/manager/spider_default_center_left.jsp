<%@ include file="/component/core/taglib/taglib.jsp"%>
<li><a href="#"><i class="fa fa-smile-o"></i><span><fmt:message key="spider.spider"/></span></a>
	<ul class="sub-menu">
		<li class="<%=JspUtil.isActive(request, "/spider/link")%>"><a href="/spider/link/home"><fmt:message key="spider.link.manager"></fmt:message></a></li>
		<li class="<%=JspUtil.isActive(request,"/index/analyzerWord") %>"><a href="/index/analyzerWord/home"><fmt:message key="index.analyzerWord.manager"/></a></li>
		<li class="<%=JspUtil.isActive(request, "/spider/crawlResult")%>"><a href="/spider/crawlResult/home"><fmt:message key="spider.crawlResult.manager"></fmt:message></a></li>
		<li class="<%=JspUtil.isActive(request, "/parse/parseResult")%>"><a href="/parse/parseResult/home"><fmt:message key="parse.parseResult.manager"></fmt:message></a></li>
		<li class="<%=JspUtil.isActive(request, "/spider/site")%>"><a href="/spider/site/home"><fmt:message key="spider.site.manager"></fmt:message></a></li>
		<li class="<%=JspUtil.isActive(request, "/spider/matcherConfig")%>"><a href="/spider/matcherConfig/home"><fmt:message key="spider.matcherConfig.manager"></fmt:message></a></li>
		<li class="divider"></li>
		<li class="<%=JspUtil.isActive(request, "/search/articleCategory")%>"><a href="/search/articleCategory/home"><fmt:message key="search.articleCategory.manager"></fmt:message></a></li>
		<li class="<%=JspUtil.isActive(request, "/parse/filterWord")%>"><a href="/parse/filterWord/home"><fmt:message key="parse.filterWord.manager"></fmt:message></a></li>
		<li class="<%=JspUtil.isActive(request, "/parse/image")%>"><a href="/parse/image/home"><fmt:message key="parse.image.manager"></fmt:message></a></li>
		<li class="<%=JspUtil.isActive(request, "/search/movie")%>"><a href="/search/movie/home"><fmt:message key="search.movie.manager"></fmt:message></a></li>
	</ul></li>

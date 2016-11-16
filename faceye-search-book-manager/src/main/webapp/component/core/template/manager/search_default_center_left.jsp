<%@ include file="/component/core/taglib/taglib.jsp"%>
<li class="<%=JspUtil.isActive(request, "link")%>"><a  href="/spider/link/home"><fmt:message key="spider.link.manager"></fmt:message></a></li>
<li class="<%=JspUtil.isActive(request, "crawlResult")%>"><a  href="/spider/crawlResult/home"><fmt:message key="spider.crawlResult.manager"></fmt:message></a></li>
<li class="<%=JspUtil.isActive(request, "parseResult")%>"><a  href="/parse/parseResult/home"><fmt:message key="parse.parseResult.manager"></fmt:message></a></li>
<li class="<%=JspUtil.isActive(request, "site")%>"><a  href="/spider/site/home"><fmt:message key="spider.site.manager"></fmt:message></a></li>
<li class="<%=JspUtil.isActive(request, "matcherConfig")%>"><a  href="/spider/matcherConfig/home"><fmt:message key="spider.matcherConfig.manager"></fmt:message></a></li>
<li class="divider"></li>
<li class="<%=JspUtil.isActive(request, "searchArticle")%>"><a  href="/search/searchArticle/home"><fmt:message key="search.searchArticle.manager" /></a></li>
<li class="<%=JspUtil.isActive(request, "articleCategory")%>"><a  href="/search/articleCategory/home"><fmt:message key="search.articleCategory.manager"></fmt:message></a></li>
<!-- 
<li><a  href="/search/book/home"><fmt:message key="search.book.manager"></fmt:message></a></li>
<li><a  href="/search/section/home"><fmt:message key="search.section.manager"></fmt:message></a></li>
 -->
<li class="<%=JspUtil.isActive(request, "filterWord")%>"><a  href="/parse/filterWord/home"><fmt:message key="parse.filterWord.manager"></fmt:message></a></li>
<!-- 
<li><a  href="/resource/doc/home"><fmt:message key="resource.doc.manager"></fmt:message></a></li>
<li><a  href="/resource/doc/home"><fmt:message key="resource.doc.manager"></fmt:message></a></li>
 -->
<li class="<%=JspUtil.isActive(request, "analyzerWord")%>"><a  href="/index/analyzerWord/home"><fmt:message key="index.analyzerWord.manager"></fmt:message></a></li>
<li class="<%=JspUtil.isActive(request, "requestRecord")%>"><a  href="/search/requestRecord/home"><fmt:message key="search.requestRecord.manager"></fmt:message></a></li>
<li class="divider"></li>
<li class="<%=JspUtil.isActive(request, "category")%>"><a  href="/book/category/home"><fmt:message key="book.category.manager"></fmt:message></a></li>
<li class="<%=JspUtil.isActive(request, "book")%>"><a  href="/book/book/home"><fmt:message key="book.book.manager"></fmt:message></a></li>
<li class="<%=JspUtil.isActive(request, "section")%>"><a  href="/book/section/home"><fmt:message key="book.section.manager"></fmt:message></a></li>

<li class="<%=JspUtil.isActive(request, "pushRecord")%>"><a  href="/push/pushRecord/home"><fmt:message key="push.pushRecord.manager"></fmt:message></a></li>
<li class="<%=JspUtil.isActive(request, "subject")%>"><a  href="/search/subject/home"><fmt:message key="search.subject.manager"></fmt:message></a></li>
<li class="<%=JspUtil.isActive(request, "image")%>"><a  href="/parse/image/home"><fmt:message key="parse.image.manager"></fmt:message></a></li>



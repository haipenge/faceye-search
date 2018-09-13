<%@ include file="/component/core/taglib/taglib.jsp"%>
<h3><fmt:message key="search.tools.regexp.tools"/></h3>
<div class="content">
  <ul class="list-unstyled">
    <li><a href="<c:url value="${host}/search/tools/regexp.html"/>" title="<fmt:message key="search.tools.regexp.tools"/>"><fmt:message key="search.tools.regexp.tools"/></a></li>
    <li><a href="<c:url value="${host}/search/tools/regexp-javascript.html"/>" title="javascript<fmt:message key="search.tools.regexp.tools"/>">javascript<fmt:message key="search.tools.regexp.tools"/></a></li>
    <li><a href="<c:url value="${host}/search/tools/regexp-php.html"/>" title="PHP<fmt:message key="search.tools.regexp.tools"/>">PHP<fmt:message key="search.tools.regexp.tools"/></a></li>
    <li><a href="<c:url value="${host}/search/tools/regexp-java.html"/>" title="Java<fmt:message key="search.tools.regexp.tools"/>">Java<fmt:message key="search.tools.regexp.tools"/></a></li>
    <li><a href="<c:url value="${host}/search/tools/regexp-ruby.html"/>" title="Ruby<fmt:message key="search.tools.regexp.tools"/>">Ruby<fmt:message key="search.tools.regexp.tools"/></a></li>
    <li><a href="<c:url value="${host}/search/tools/regexp-rails.html"/>" title="Rails<fmt:message key="search.tools.regexp.tools"/>">Rails<fmt:message key="search.tools.regexp.tools"/></a></li>
    <li><a href="<c:url value="${host}/search/tools/regexp-asp.html"/>" title="asp<fmt:message key="search.tools.regexp.tools"/>">asp<fmt:message key="search.tools.regexp.tools"/></a></li>
    <li><a href="<c:url value="${host}/search/tools/regexp-android.html"/>" title="android<fmt:message key="search.tools.regexp.tools"/>">android<fmt:message key="search.tools.regexp.tools"/></a></li>
    <li><a href="<c:url value="${host}/search/tools/regexp-ios.html"/>" title="ios<fmt:message key="search.tools.regexp.tools"/>">ios<fmt:message key="search.tools.regexp.tools"/></a></li>
  </ul>
</div>
<c:set var="count" value="0" />
<c:forEach items="${searchResults.content}" var="searchResult" varStatus="status">
	<div class="content">
		<c:if test="${article.id ne searchResult.id}">
			<p>
				<a href="<c:url value="${host}/search/${searchResult.id}.html"/>">${searchResult.name}</a>
			</p>
			<p>${searchResult.contents[0] }</p>
			<hr />
		</c:if>
	</div>
	<c:if test="${status.count eq 2 }">
		<div class="content">
		<script type="text/javascript">
			var cpro_id = "u2129072";
			(window["cproStyleApi"] = window["cproStyleApi"] || {})[cpro_id] = {
				at : "3",
				rsi0 : "300",
				rsi1 : "300",
				pat : "1",
				tn : "baiduCustNativeAD",
				rss1 : "#FFFFFF",
				conBW : "0",
				adp : "1",
				ptt : "0",
				titFF : "%E5%BE%AE%E8%BD%AF%E9%9B%85%E9%BB%91",
				titFS : "14",
				rss2 : "#336699",
				titSU : "0",
				tft : "0",
				tlt : "1",
				ptbg : "90",
				piw : "140",
				pih : "90",
				ptp : "0"
			}
		</script>
		<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
	</div>
	</c:if>
</c:forEach>
<c:if test="${empty searchResults.content}">
	<div class="content">
		<script type="text/javascript">
			var cpro_id = "u2129072";
			(window["cproStyleApi"] = window["cproStyleApi"] || {})[cpro_id] = {
				at : "3",
				rsi0 : "300",
				rsi1 : "300",
				pat : "1",
				tn : "baiduCustNativeAD",
				rss1 : "#FFFFFF",
				conBW : "0",
				adp : "1",
				ptt : "0",
				titFF : "%E5%BE%AE%E8%BD%AF%E9%9B%85%E9%BB%91",
				titFS : "14",
				rss2 : "#336699",
				titSU : "0",
				tft : "0",
				tlt : "1",
				ptbg : "90",
				piw : "140",
				pih : "90",
				ptp : "0"
			}
		</script>
		<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
	</div>
</c:if>
<script type="text/javascript">
var cpro_id = "u2246869";
</script>
<script src="http://cpro.baidustatic.com/cpro/ui/f.js" type="text/javascript"></script>


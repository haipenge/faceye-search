<%@ include file="/component/core/taglib/taglib.jsp"%>
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
			<!-- 
			<script type="text/javascript">
				BAIDU_CLB_fillSlot("1058992");
			</script>
			-->
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
<div class="content">
	<script type="text/javascript">
		BAIDU_CLB_fillSlot("1058992");
	</script>
</div>


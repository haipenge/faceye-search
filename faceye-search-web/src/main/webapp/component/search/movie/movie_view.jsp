<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/search/movie/movie.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/movie/movie.js"/>"></script>
<div class="row">
	<div class="col-md-10 col-md-offset-1 bg-white">
		<div class="page-header">
			<h3>
				<fmt:message key="search.movie.movie" />
				: ${movie.name}
			</h3>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="content">
					<script type="text/javascript">
						var cpro_id = "u888888";
						(window["cproStyleApi"] = window["cproStyleApi"] || {})[cpro_id] = {
							at : "3",
							rsi0 : "300",
							rsi1 : "250",
							pat : "17",
							tn : "baiduCustNativeAD",
							rss1 : "#FFFFFF",
							conBW : "1",
							adp : "1",
							ptt : "0",
							titFF : "%E5%BE%AE%E8%BD%AF%E9%9B%85%E9%BB%91",
							titFS : "",
							rss2 : "#000000",
							titSU : "0"
						}
					</script>
					<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
				</div>
				<div class="content">
					<h3>
						<fmt:message key="search.movie.hot" />
					</h3>
					<ul class="list-unstyled">
						<c:forEach var="movie" items="${movies.content}">
							<li><a href="<c:url value="/search/movie/detail/${movie.id}.html"/>" title="${movie.name}">${movie.name}</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="col-md-6">
				<div class="row">
					<div class="col-md-6">
						<div class="content">
							<p>
								<fmt:message key="search.movie.director" />
								:${movie.director}
							</p>
							<p>
								<fmt:message key="search.movie.onlineDate" />
								:
								<fmt:formatDate value="${movie.onlineDate}" type="date" pattern="yyyy-MM-dd"></fmt:formatDate>
							</p>
							<p>
								<fmt:message key="search.movie.actor" />
								:${movie.actor}
							</p>
							<p>
								<fmt:message key="search.movie.totalMinutes" />
								:${movie.totalMinutes}
							</p>
							<p>
								<fmt:message key="search.movie.area" />
								:${movie.area}
							</p>
						</div>
					</div>
					<div class="col-md-6">
						<script type="text/javascript">
							var cpro_id = "u2217217";
						</script>
						<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>

					</div>
				</div>

				<div class="content">
					<p>
						<fmt:message key="search.movie.remark" />
						:${movie.remark}
					</p>
				</div>
				<div class="row">
					<div class="content">
						<a href="<c:url value="/search/movie/detail/${movie.id}.html"/>"
							title="${movie.name}<fmt:message key="search.movie.start.play"/>" class="btn btn-lg btn-success"><fmt:message
								key="search.movie.start.play" /></a>
						<p>
							<input type="text" name="number1" value="5">&nbsp;&nbsp;+<input type="text" name="number2" value="8">&nbsp;&nbsp;=&nbsp;&nbsp;<input
								type="text" name="result">
						</p>
					</div>
					<div class="content">
						<script type="text/javascript">
							var cpro_id = "u2217906";
						</script>
						<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
					</div>
				</div>
				<div class="content">

					<a href="<c:url value="/search/movie/detail/${movie.id}.html"/>"
						title="${movie.name}<fmt:message key="search.movie.online.show"/>"><fmt:message key="search.movie.online.show" /></a>
					<a href="<c:url value="/search/movie/download/${movie.id}.html"/>"
						title="${movie.name}<fmt:message key="search.movie.xunlei.download"/>"><fmt:message
							key="search.movie.xunlei.download" /></a>
				</div>
				<div class="content">
					<a href="#" title="${movie.name}<fmt:message key="search.movie.mobile"/>"><fmt:message
							key="search.movie.mobile" /></a>| <a href="#" title="${movie.name}<fmt:message key="search.movie.pad"/>"><fmt:message
							key="search.movie.pad" /></a>
				</div>
				<!-- 
				<div class="content">
					<script type="text/javascript">
						var cpro_id = "u2217221";
						(window["cproStyleApi"] = window["cproStyleApi"] || {})[cpro_id] = {
							at : "3",
							rsi0 : "500",
							rsi1 : "250",
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
				</div>
				 -->
				<div class="content">
					<script type="text/javascript">
						var cpro_id = "u2217225";
						(window["cproStyleApi"] = window["cproStyleApi"] || {})[cpro_id] = {
							at : "3",
							rsi0 : "500",
							rsi1 : "250",
							pat : "1",
							tn : "baiduCustNativeAD",
							rss1 : "#FFFFFF",
							conBW : "1",
							adp : "1",
							ptt : "0",
							titFF : "%E5%BE%AE%E8%BD%AF%E9%9B%85%E9%BB%91",
							titFS : "14",
							rss2 : "#000000",
							titSU : "0",
							tft : "0",
							tlt : "1",
							ptbg : "90",
							piw : "0",
							pih : "0",
							ptp : "0"
						}
					</script>
					<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
				</div>
			</div>
			<div class="col-md-2">
				<div class="content">
					<script type="text/javascript">
						var cpro_id = "u2217228";
					</script>
					<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>

				</div>
			</div>
		</div>
		<div class="row">

			<div class="content col-md-offset-4">
				<h3>${movie.name}&nbsp;&nbsp;<fmt:message key="search.movie.discuss" />
				</h3>
			</div>
			<div class="content col-md-offset-4">
			${movie.description }
			</div>
		</div>
	</div>
	<div class="col-md-1"></div>
</div>

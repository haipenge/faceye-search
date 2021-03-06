<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/parse/parseResult/parseResult.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/parse/parseResult/parseResult.js"/>"></script>
<div class="page-head">
	<h2>
		<fmt:message key="parse.parseResult.detail"></fmt:message>
	</h2>
</div>
<div class="cl-mcont">
	<div class="block-flat" style="padding-top: 0px;">
		<p>
			<c:choose>
				<c:when test="${parseResult.isPush2Mongo eq true}">Pushed</c:when>
				<c:otherwise>
					<button class="btn btn-success" onclick="ParseResult.push2Mongo(${parseResult.id},'true');return false;" id="btn-${parseResult.id}">Push</button>
				</c:otherwise>
			</c:choose>
			<button class="btn btn-success" onclick="ParseResult.testFilter(${parseResult.id});return false;">Filter</button>
			<c:if test="${parseResult.isPush2Mongo eq false}">
				<a class="btn btn-success" href="<c:url value="/parse/parseResult/edit/${parseResult.id}"/>"><fmt:message key="global.edit" /></a>
			</c:if>
			<c:if test="${!parseResult.isPush2Mongo || empty parseResult.isPush2Mongo}">
				<button class="btn btn-warning" onclick="ParseResult.remove(${parseResult.id});return false;">
					<fmt:message key="global.remove"></fmt:message>
				</button>
			</c:if>
		</p>
		<c:if test="${not empty filterWords}">
			<div class="alert alert-warning" role="alert">
				<c:forEach items="${filterWords}" var="word">
					<span class="label label-danger" id="${word.id}-word" onclick="ParseResult.removeFilterWord(${word.id});return false;">${word.word} &nbsp;&nbsp;
						<button type="btn btn-default">DEL</button>
					</span> &nbsp;&nbsp;|&nbsp;&nbsp;
		  </c:forEach>
			</div>
		</c:if>
		<p id="${parseResult.id}">
			<b>${parseResult.name}</b>
		</p>
		<p>
			Source:<a href="${parseResult.sourceUrl}" target="_blank">${parseResult.sourceUrl }</a>
		</p>
		<div class="content">${parseResult.content}</div>
		<div class="content">
			<p>
				<c:choose>
					<c:when test="${parseResult.isPush2Mongo eq true}">Pushed</c:when>
					<c:otherwise>
						<button class="btn btn-success" onclick="ParseResult.push2Mongo(${parseResult.id},'true');return false;" id="btn-${parseResult.id}">Push</button>
					</c:otherwise>
				</c:choose>
				<button class="btn btn-success" onclick="ParseResult.testFilter(${parseResult.id});return false;">Filter</button>
			</p>
		</div>
	</div>
	<div class="content">
		<h4>Analyzer Title result:</h4>
		<c:forEach var="word" items="${titleWordContainer.words}">
			<span class="label label-success">${word.text }(${word.count})</span>
		</c:forEach>
	</div>
	<div class="content">
			<h4>Analyzer content result:</h4>
			<c:forEach var="word" items="${wordContainer.words}">
				<span class="label label-info">${word.text }(${word.count})</span>
			</c:forEach>
		</div>
</div>

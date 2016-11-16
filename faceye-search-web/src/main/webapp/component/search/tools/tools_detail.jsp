<%@ include file="/component/core/taglib/taglib.jsp"%>

<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
		<a href="<c:url value="${host}/"/>"><fmt:message key="global.home"></fmt:message></a>&nbsp;&gt;&nbsp;${article.name}
		 </div>
	</div>
	<div class="panel-body">
	<div class="row">
	  <div class="col-md-8">${article.content}</div>
	   <div class="col-md-4">
	     <c:forEach items="${searchResults.content}" var="searchResult">
	       <div class="well">
	         <c:if test="${article.id ne searchResult.id }">
	          <p>${searchResult.name}</p>
	          <p>${searchResult.contents[0] }</p>
	          </c:if>
	       </div>
	     </c:forEach>
	   </div>
	</div>
	</div>
</div>
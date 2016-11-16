<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/search/searchArticle/searchArticle.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/searchArticle/searchArticle.js"/>"></script>
<div class="page-head">
	<h2>
		<fmt:message key="search.searchArticle.manager"></fmt:message>
		<a class="btn btn-success" href="<c:url value="/search/searchArticle/input"/>"> <fmt:message
				key="search.searchArticle.add"></fmt:message>
		</a>
	</h2>
	<div class="content">
		<form action="<c:url value="/search/searchArticle/home"/>" method="post" role="form" class="form-horizontal">
			<fieldset>
				<div class="form-group">
					<label class="col-md-1 control-label" for="categoryId">Category:</label>
					<div class="col-md-2">
						<select name="categoryId" class="form-control">
							<option>Select Caetgory</option>
							<c:forEach items="${categories}" var="category">
								<option value="${category.id}" <c:if test="${searchParams.categoryId eq category.id }">selected</c:if>>${category.name }</option>
							</c:forEach>
						</select>
					</div>
					<label class="col-md-1 control-label">Indexed:</label>
					<div class="col-md-2">
						<select name="isIndexed" class="form-control">
							<option value="true" <c:if test="${searchParams.isIndexed eq 'true' }"> selected</c:if>>YES</option>
							<option value="false" <c:if test="${searchParams.isIndexed eq 'false' }"> selected</c:if>>NO</option>
						</select>
					</div>
					<label class="col-md-1 control-label">Query:</label>
					<div class="col-md-2">
						<input type="text" name="q" value="${searchParams.q}" class="form-control">
					</div>
					<div class="col-md-2">
						<button type="submit" class="btn btn-success">Search</button>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>

<div class="cl-mcont">
	<div class="block-flat">
		<div class="content">
			<button type="button" id="push" class="btn btn-default">Push</button>
			<button type ="button" id ="delete" class="btn btn-default">Delete</button>
			<button type="button" id="to-weixin" class="btn btn-primary">To Weixin</button>
			<button type="button" id="set-article-category" class="btn btn-info">Set Articel Category</button>
		</div>
		<div id="msg"></div>
		<div classs="table-responsive">
			<table class="table table-hover table-striped table-bordered" style="font-size: 11px;">
				<tr>
					<td><input type="checkbox" name="checkAllSearchArticleIds"></td>
					<td><fmt:message key='search.searchArticle.name'></fmt:message></td>
					<td>category</td>
					<td>Indexed?</td>
					<td>Is Weixin</td>
					<!-- 
					<td>Keyword</td>
					<td>Description</td>
					-->
					<td><fmt:message key='search.searchArticle.createDate'></fmt:message></td>
					<!--@generate-entity-jsp-property-desc@-->

					<td><fmt:message key="global.remove"></fmt:message></td>
				</tr>
				<c:forEach items="${page.content}" var="article">
					<tr id="${article.id}">
						<td><input type="checkbox" name="searchArticleId" value="${article.id }"></td>
						<td><a href="<c:url value="/search/searchArticle/detail/${article.id}"/>" target="_blank">${article.name}</a></td>
						<!-- 
						<td>${article.keywords }</td>
						<td>${article.description}</td>
						-->
						<td>${article.categoryName}</td>
						<td><f:boolean value="${article.isIndexed }"/></td>
						<td><f:boolean value="${article.isWeixin}"/></td>
						<td><fmt:formatDate value="${article.createDate}" type="date" pattern="yyyy-MM-dd HH:mm" /></td>
						<!--@generate-entity-jsp-property-value@-->
						<td><a href="<c:url value="/search/searchArticle/edit/${article.id}"/>" target="_blank"> <fmt:message
									key="global.edit"></fmt:message>
						</a>|<a href="#" onclick="SearchArticle.remove(${article.id});return false;"> <fmt:message key="global.remove"></fmt:message>
						</a></td>
					<tr>
				</c:forEach>
			</table>
		</div>
		<f:page page="${page}" url="/search/searchArticle/home" params="<%=request.getParameterMap()%>" />
	</div>
</div>

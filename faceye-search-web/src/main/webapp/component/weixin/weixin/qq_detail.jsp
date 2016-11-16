<%@ include file="/component/core/taglib/taglib.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/weixin/weixin/weixin.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/weixin/weixin/weixin.js"/>"></script>
<div class="container">
	<div class="header">
		<h3>${article.name}</h3>
	</div>
	<div class="content">${article.content}<br>
		<script type="text/javascript">
			var cpro_id = "u2139551";
			(window["cproStyleApi"] = window["cproStyleApi"] || {})[cpro_id] = {
				at : "3",
				pat : "9",
				cpro_h : "65",
				tn : "template_inlay_all_mobile_lu_native_ad_image_txt_1",
				rss1 : "#FFFFFF",
				titFF : "%E5%BE%AE%E8%BD%AF%E9%9B%85%E9%BB%91",
				titFS : "16",
				rss2 : "#000000",
				conpl : "0",
				conpr : "20",
				desfs : "12",
				rss3 : "#FFFFFF",
				desbc : "#DCDCDC",
				pimc : "40",
				imgRatio : "1",
				itepl : "10",
				titSU : "0",
				itecbc : "#DCDCDC",
				itecpl : "0",
				itecpt : "0"
			}
		</script>
		<script src="http://cpro.baidustatic.com/cpro/ui/cm.js" type="text/javascript"></script>
	</div>
</div>
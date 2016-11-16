<%@ include file="/component/core/taglib/taglib.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/weixin/weixin/weixin.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/weixin/weixin/weixin.js"/>"></script>
<script type="text/javascript">
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
window.shareData = {
    "timeLineLink": "http://www.faceye.net/weixin/weinx/detail/${article.id}.html",   
    "sendFriendLink": "http://www.faceye.net/weixin/weinx/detail/${article.id}.html",
    "weiboLink": "http://www.faceye.net/weixin/weinx/detail/${article.id}.html",
    "tTitle": "${article.name}",
    "tContent": "${article.description}",
    "fTitle": "${article.name}",
    "fContent": "${article.description}",
    "wContent": "${article.description}"
    };
    // 发送给好友
    WeixinJSBridge.on('menu:share:appmessage', function (argv) {
        WeixinJSBridge.invoke('sendAppMessage', {
            "img_url": "img/logo_white.png",
            "img_width": "401",
            "img_height": "275",
            "link": window.shareData.sendFriendLink,
            "desc": window.shareData.fContent,
            "title": window.shareData.fTitle
        }, function (res) {
            _report('send_msg', res.err_msg);
        })
    });
    // 分享到朋友圈
    WeixinJSBridge.on('menu:share:timeline', function (argv) {
        WeixinJSBridge.invoke('shareTimeline', {
            "img_url": "img/logo_white.png",
            "img_width": "401",
            "img_height": "275",
            "link": window.shareData.timeLineLink,
            "desc": window.shareData.tContent,
            "title": window.shareData.tTitle
        }, function (res) {
            _report('timeline', res.err_msg);
        });
    });

}, false);
</script>
<div class="container">
<div class="header">
	<h3>${article.name}</h3>
</div>
<div class="content">${article.content}</div>
</div>
<%@ include file="/component/core/taglib/taglib.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript" src="/js/component/search/tools/tools.js"></script>
<div class="content">
	<ol class="breadcrumb">
		<li><a href="<c:url value="${host}/"/>"><fmt:message key="global.home"></fmt:message></a></li>
		<li><fmt:message key="search.tools.regexp.title" />
	</ol>
</div>
<div class="page-header">
	<h3>${global.title }</h3>
</div>
<div class="content">
	<form action="#" method="post" role="form" class="form-horizontal">
		<fieldset>
			<div class="form-group">
				<label class="col-md-2 control-label" for="regexp"><fmt:message key="search.tools.regexp.name" /> </label>
				<div class="col-md-10">
					<textarea rows="" cols="" name="regexp" class="form-control" placeholder="[a-zA-Z0-9].+"></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label" for="content"><fmt:message key="search.tools.regexp.match.content" /></label>
				<div class="col-md-10">
					<textarea rows="10" cols="" name="content" class="form-control"></textarea>
				</div>
			</div>
			<div class="form-group">
			   <div class="col-md-2">
			   </div>
				<div class="col-md-6">
					<div class="content text-right">
						<script type="text/javascript">
							var cpro_id = "u2213667";
						</script>
						<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>

					</div>
				</div>
				<div class="col-md-4">
					<button type="button" class="btn btn-primary btn-block" id="regexp-match">
						<fmt:message key="search.tools.regexp.match" />
					</button>
				</div>
			</div>
		</fieldset>
	</form>
</div>

<div class="form-group" id="match-result-container">
	<h4>
		<fmt:message key="search.tools.regexp.match.result.title" />
	</h4>
	<label class="col-md-2 control-label" for="content"><fmt:message key="search.tools.regexp.match.result.title" /></label>
	<div class="col-md-10">
		<textarea rows="10" cols="" name="match-results" class="form-control" id="match-results"></textarea>
	</div>
</div>

<h4>常用正则表达式:</h4>
<div class="content">
	<h5>正则表达式之邮箱验证:</h5>
	<pre>
$email = "test@";
if (preg_match('/^[^0-9][a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[@][a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,4}$/',$email)) {
echo "Your email is ok.";
} else {
echo "电子邮件经正则表达式验证失败，当前邮件不是一个正确的电子邮件。";
}
</pre>
</div>
<div class="content">
	<h5>正则表达式之匹配IP:</h5>
	<pre>
 ((25[0-5]|2[0-4]\d|1\d\d|[1-9]\d|\d)\.){3}(25[0-5]|2[0-4]\d|1\d\d|[1-9]\d|[1-9])
 </pre>
</div>
<div class="content">
	<h5>正则表达式之常用:</h5>
	<p>通常，在我们日常的开发工作中，会使用到一些常用的正则表达式，比如电话，邮件，手机，汉字，数字等一系列的正则表达式，下面这些常用的正常正则表达式涵盖了日常使用正则表达式80%以上。可以较好的满足日常开发需要。当需要使用正则表达式的时候，只需要打开本页面即可。你可以将本页放入收藏夹。以供备用。</p>
	<pre>
 验证数字正则表达式：^[0-9]*$ 
验证n位的数字正则表达式：^\d{n}$ 
验证至少n位数字正则表达式：^\d{n,}$ 
验证m-n位的数字正则表达式：^\d{m,n}$ 
验证零和非零开头的数字正则表达式：^(0|[1-9][0-9]*)$ 
验证有两位小数的正实数正则表达式：^[0-9]+(.[0-9]{2})?$ 
验证有1-3位小数的正实数正则表达式：^[0-9]+(.[0-9]{1,3})?$ 
验证非零的正整数正则表达式：^\+?[1-9][0-9]*$ 
验证非零的负整数正则表达式：^\-[1-9][0-9]*$ 
验证非负整数（正整数 + 0）正则表达式: ^\d+$ 
验证非正整数（负整数 + 0）正则表达式： ^((-\d+)|(0+))$ 
验证长度为3的字符正则表达式：^.{3}$ 
验证由26个英文字母组成的字符串正则表达式：^[A-Za-z]+$ 
验证由26个大写英文字母组成的字符串正则表达式：^[A-Z]+$ 
验证由26个小写英文字母组成的字符串正则表达式：^[a-z]+$ 
验证由数字和26个英文字母组成的字符串正则表达式：^[A-Za-z0-9]+$ 
验证由数字、26个英文字母或者下划线组成的字符串正则表达式：^\w+$ 
验证用户密码正则表达式:^[a-zA-Z]\w{5,17}$ 正确格式为：以字母开头，长度在6-18之间，只能包含字符、数字和下划线。 
验证是否含有 ^%&',;=?正则表达式：$\" 等字符：[^%&',;=?$\x22]+ 
验证汉字正则表达式：^[\u4e00-\u9fa5],{0,}$ 
验证Email地址正则表达式：^\w+[-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$ 
验证InternetURL正则表达式：^http://([\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$ ；^[a-zA-z]+://(w+(-w+)*)(.(w+(-w+)*))*(?S*)?$ 
验证电话号码正则表达式：^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}$：--正确格式为：XXXX-XXXXXXX，XXXX-XXXXXXXX，XXX-XXXXXXX，XXX-XXXXXXXX，XXXXXXX，XXXXXXXX。 
验证身份证号（15位或18位数字）正则表达式：^\d{15}|\d{}18$ 
验证一年的12个月正则表达式：^(0?[1-9]|1[0-2])$ 正确格式为：“01”-“09”和“1”“12” 
验证一个月的31天正则表达式：^((0?[1-9])|((1|2)[0-9])|30|31)$ 正确格式为：01、09和1、31。 
验证整数正则表达式：^-?\d+$ 
非负浮点数（正浮点数 + 0）正则表达式：^\d+(\.\d+)?$ 
正浮点数正则表达式： ^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$ 
非正浮点数（负浮点数 + 0）正则表达式： ^((-\d+(\.\d+)?)|(0+(\.0+)?))$ 
负浮点数正则表达式： ^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$ 
浮点数正则表达式： ^(-?\d+)(\.\d+)?
 </pre>
</div>
<h4>正则表达式相关文章:</h4>
<div class="content">
	<ul class="list-unstyled">
		<li><a href="http://www.faceye.net/search/214131.html" title="javascript正则表达式实例与干货分享">javascript正则表达式实例与干货分享</a></li>
		<li><a href="http://www.faceye.net/search/212970.html" title="验证邮箱正则表达式，包含二级域名邮箱，手机号正则表达式">验证邮箱正则表达式，包含二级域名邮箱，手机号正则表达式</a></li>
		<li><a href="http://www.faceye.net/search/212835.html" title="iOS开发_常用的正则表达式">iOS开发_常用的正则表达式</a></li>
	</ul>
</div>
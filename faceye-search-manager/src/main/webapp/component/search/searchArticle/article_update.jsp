<%@ include file="/component/core/taglib/taglib.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<link rel="stylesheet" href="<c:url value="/js/lib/jQuery-File-Upload/css/jquery.fileupload.css"/>">
<link rel="stylesheet" href="<c:url value="/js/lib/jQuery-File-Upload/css/jquery.fileupload-ui.css"/>">
<script type="text/javascript" charset="utf-8" src="<c:url value="/js/lib/ueditor/ueditor.config.js"/>"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/js/lib/ueditor/ueditor.all.min.js"/>"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/js/lib/ueditor/lang/zh-cn/zh-cn.js"/>"></script>
<!-- 
<link rel="stylesheet" type="text/css"
	href="<c:url value="/js/lib/bootstrap3-wysihtml5-bower-master/dist/bootstrap3-wysihtml5.min.css"/>"></link>
<script type="text/javascript"
	src="<c:url value="/js/lib/bootstrap3-wysihtml5-bower-master/dist/bootstrap3-wysihtml5.all.min.js"/>"></script>
	 -->
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/search/searchArticle/searchArticle.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/search/searchArticle/searchArticle.js"/>"></script>
<div class="block-flat">
	<div class="header">
		<h3>
			<c:choose>
				<c:when test="${empty searchArticle.id}">
					<fmt:message key="search.searchArticle.add"></fmt:message>
				</c:when>
				<c:otherwise>
					<fmt:message key="search.searchArticle.edit"></fmt:message>
				</c:otherwise>
			</c:choose>
		</h3>
	</div>
	<div class="content">
		<!-- 
		<div id="btns">
			<div>
				<button onclick="getAllHtml()">获得整个html的内容</button>
				<button onclick="getContent()">获得内容</button>
				<button onclick="setContent()">写入内容</button>
				<button onclick="setContent(true)">追加内容</button>
				<button onclick="getContentTxt()">获得纯文本</button>
				<button onclick="getPlainTxt()">获得带格式的纯文本</button>
				<button onclick="hasContent()">判断是否有内容</button>
				<button onclick="setFocus()">使编辑器获得焦点</button>
				<button onmousedown="isFocus(event)">编辑器是否获得焦点</button>
				<button onmousedown="setblur(event)">编辑器失去焦点</button>

			</div>
			<div>
				<button onclick="getText()">获得当前选中的文本</button>
				<button onclick="insertHtml()">插入给定的内容</button>
				<button id="enable" onclick="setEnabled()">可以编辑</button>
				<button onclick="setDisabled()">不可编辑</button>
				<button onclick=" UE.getEditor('editor').setHide()">隐藏编辑器</button>
				<button onclick=" UE.getEditor('editor').setShow()">显示编辑器</button>
				<button onclick=" UE.getEditor('editor').setHeight(300)">设置高度为300默认关闭了自动长高</button>
			</div>

			<div>
				<button onclick="getLocalData()">获取草稿箱内容</button>
				<button onclick="clearLocalData()">清空草稿箱</button>
			</div>

		</div>
		<div>
			<button onclick="createEditor()">创建编辑器</button>
			<button onclick="deleteEditor()">删除编辑器</button>
		</div>
		 -->
		<form action="<c:url value="/search/searchArticle/save"/>" method="post" role="form" class="form-horizontal">
			<input type="hidden" name="id" value="${searchArticle.id}" /> 
			<input type="hidden" name="clickCount" value="${searchArticle.clickCount}" />
			<input type="hidden" name="isIndexed" value="${searchArticle.isIndexed }"/>
			<input type="hidden" name="sourceUrl" value="${searchArticle.sourceUrl }"/>
			<fieldset>
				<div class="form-group">
					<label class="col-md-1 control-label" for="name"> <fmt:message key="search.searchArticle.name"></fmt:message>
					</label>
					<div class="col-md-11">
						<input type="text" name="name" value="${searchArticle.name}" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-1 control-label" for="categoryName"> Category </label>
					<div class="col-md-3">
						<select name="categoryId" class="form-control">
							<option value="">Select Category</option>
							<c:forEach items="${categories}" var="category">
								<option value="${category.id}" <c:if test="${category.id eq searchArticle.categoryId }"> selected</c:if>>${category.name }</option>
							</c:forEach>
						</select>
					</div>
					<label class="col-md-1 control-label" for="subjectName"> Subject </label>
					<div class="col-md-3">
						<select name="subjectId" class="form-control">
							<option value="">Select Subject</option>
							<c:forEach items="${subjects}" var="subject">
								<option value="${subject.id}" <c:if test="${subject.id eq searchArticle.subjectId}"> selected</c:if>>${subject.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-4">
						<div class="checkbox">
							<label><input type="checkbox" name="isWeixin" <c:if test="${searchArticle.isWeixin}"> checked</c:if>>Is
								Weixin? </label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-1 control-label" for="keywords"> keywords </label>
					<div class="col-md-5">
						<input type="text" name="keywords" value="${searchArticle.keywords}" class="form-control">
					</div>
					<label class="col-md-1 control-label" for="description">Description </label>
					<div class="col-md-5">
						<input type="text" name="description" class="form-control" value="${searchArticle.description}">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-1 control-label" for="content"> <fmt:message key="search.searchArticle.content"></fmt:message>
					</label>
					<div class="col-md-11">
						<!-- 
						<script id="editor" type="text/plain" style="height:500px;"></script>
						-->
						<textarea name="content" id="editor" style="height: 300px;" placeholder="Enter text ...">${searchArticle.content}</textarea>
					</div>
				</div>
				<div class="form-group">
					<dl class="dl-horizontal">
						<c:forEach items="${images}" var="image">
							<dt id="${image.id}-dt">
								<img src="${imageServer}${image.storePath}" alt="..." class="img-thumbnail">
							</dt>
							<dd id="${image.id}-dd">
								<a href="#" onclick="SearchArticle.removeImage(${image.id});return false;">Remove</a>
							</dd>
						</c:forEach>
					</dl>
				</div>
				<!--@generate-entity-jsp-property-update@-->
				<div class="form-group">
					<div class="col-md-offset-1">
						<button type="submit" class="btn btn-success">
							<fmt:message key="global.submit.save"></fmt:message>
						</button>
						<button onclick="getContent()">获得内容</button>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>


<script type="text/javascript">
	window.UEDITOR_HOME_URL = "/"
	var ue = UE.getEditor('editor');
	/**
	 *afterExecCommand
	 *insertimage
	 *afterinsertimage
	 */
	ue.addListener('afterinsertimage', function(type, opt) {
		//get upload img url:opt[0]._src
		var images = [];
		/* if (opt) {
			for (var i = 0; i < opt.length; i++) {
				var img = opt[i];
				images.push(img._src);
			}
		} */
		//alert('fire after insert image.'+opt[0]._src);
	});
	//simpleupload
	ue.addListener('afterExecCommand', function(type, opt, arg1) {
		//alert(type+','+opt+',');
		/* var args = '';
		for (var i = 0; i < arg1.length; i++) {
			args += arg1[i];
		} */
		//alert(args);
	});

	ue.addListener('afterinserthtml', function(type, opt) {
		//alert('fire after insert html .......' + opt);
		
	});

	ue.addListener('contentChange', function() {
		var content = ue.getContent();
		//var regex="<img[^>]*?data\\-src=[\"\\']?([^\"\\'>]+)[\"\\']?[^>]*\\/>";
		//var regex=/<img[^>]*?src=[\"\']?([^\"\'>]+)[\"\']?[^>]*\/>/gim;
		/* var regexp = new RegExp("<img[^>]*?src=[\"\\']?([^\"\\'>]+)[\"\\']?[^>]*\\/>", 'g');
		var images = [];
		alert("content is:" + content);
		if (content && content.length > 0) {
			var record = '';
			while ((record = regexp.exec(content)) != null) {
				images.push(RegExp.$1);
			}
		}
		if (images && images.length > 0) {
			var str = '';
			for (var i = 0; i<images.length>0; i++) {
				str += '' + i + images[i];
			}
			alert("distil img result is:" + str);
		} */
		//
		var root =ue.body;
		var imageNodes=UE.dom.domUtils.getElementsByTagName(root,'img');
		if(imageNodes && imageNodes.length>0){
			for(var i=0;i<imageNodes.length;i++){
				var node=imageNodes[i];
				UE.dom.domUtils.addClass(node,'img-responsive');
			}
		}
		var content=ue.getContent();
	});

	function isFocus(e) {
		alert(UE.getEditor('editor').isFocus());
		UE.dom.domUtils.preventDefault(e)
	}
	function setblur(e) {
		UE.getEditor('editor').blur();
		UE.dom.domUtils.preventDefault(e);
	}
	function insertHtml() {
		var value = prompt('插入html代码', '');
		UE.getEditor('editor').execCommand('insertHtml', value);
	}
	function createEditor() {
		enableBtn();
		UE.getEditor('editor');
	}
	function getAllHtml() {
		alert(UE.getEditor('editor').getAllHtml());
	}
	function getContent() {
		var arr = [];
		arr.push("使用editor.getContent()方法可以获得编辑器的内容");
		arr.push("内容为：");
		arr.push(UE.getEditor('editor').getContent());
		alert(arr.join("\n"));
	}
	function getPlainTxt() {
		var arr = [];
		arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
		arr.push("内容为：");
		arr.push(UE.getEditor('editor').getPlainTxt());
		alert(arr.join('\n'));
	}
	function setContent(isAppendTo) {
		var arr = [];
		arr.push("使用editor.setContent('欢迎使用ueditor')方法可以设置编辑器的内容");
		UE.getEditor('editor').setContent('欢迎使用ueditor', isAppendTo);
		alert(arr.join("\n"));
	}
	function setDisabled() {
		UE.getEditor('editor').setDisabled('fullscreen');
		disableBtn("enable");
	}

	function setEnabled() {
		UE.getEditor('editor').setEnabled();
		enableBtn();
	}

	function getText() {
		//当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
		var range = UE.getEditor('editor').selection.getRange();
		range.select();
		var txt = UE.getEditor('editor').selection.getText();
		alert(txt);
	}

	function getContentTxt() {
		var arr = [];
		arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
		arr.push("编辑器的纯文本内容为：");
		arr.push(UE.getEditor('editor').getContentTxt());
		alert(arr.join("\n"));
	}
	function hasContent() {
		var arr = [];
		arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
		arr.push("判断结果为：");
		arr.push(UE.getEditor('editor').hasContents());
		alert(arr.join("\n"));
	}
	function setFocus() {
		UE.getEditor('editor').focus();
	}
	function deleteEditor() {
		disableBtn();
		UE.getEditor('editor').destroy();
	}
	function disableBtn(str) {
		var div = document.getElementById('btns');
		var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
		for (var i = 0, btn; btn = btns[i++];) {
			if (btn.id == str) {
				UE.dom.domUtils.removeAttributes(btn, [ "disabled" ]);
			} else {
				btn.setAttribute("disabled", "true");
			}
		}
	}
	function enableBtn() {
		var div = document.getElementById('btns');
		var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
		for (var i = 0, btn; btn = btns[i++];) {
			UE.dom.domUtils.removeAttributes(btn, [ "disabled" ]);
		}
	}

	function getLocalData() {
		alert(UE.getEditor('editor').execCommand("getlocaldata"));
	}

	function clearLocalData() {
		UE.getEditor('editor').execCommand("clearlocaldata");
		alert("已清空草稿箱");
	}
</script>
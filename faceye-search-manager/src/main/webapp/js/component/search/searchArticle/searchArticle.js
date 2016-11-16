/**
 * 说明:Article js 脚本 作者:@haipenge
 */
var SearchArticle = {
	init : function() {
		if ($('.textarea').length > 0) {
			// $('.textarea').wysihtml5();
		}
		// SearchArticle.loadRichTextEditor();
		/**
		 * 全选，或是全不选
		 */
		$('input[name="checkAllSearchArticleIds"]').click(function() {
			SearchArticle.checkAllOrNothing(this, $('input[name="searchArticleId"]'));
		});
		$('#delete').click(function() {
			SearchArticle.multiRemove();
		});

		/**
		 * 设置分类
		 */
		$('#set-article-category').click(function() {
			SearchArticle.setArticleCategory();
		});
		SearchArticle.push();
	},
	/**
	 * 推送文章
	 */
	push : function() {
		$('#push')
				.click(
						function() {
							ids = SearchArticle.getCheckedIds($('input[name="searchArticleId"]'));
							$
									.ajax({
										url : '/spider/site/getSites.json',
										type : 'get',
										dataType : 'json',
										success : function(data, textStatus, xhr) {
											if (data.content) {
												var html = '<div class="container">';
												html += '<form class="form-horizontal" role="form">';
												html += '<div class="form-group">';
												for (var i = 0; i < data.content.length; i++) {
													var site = data.content[i];
													html += '<div class="check-box">';
													html += '<label>';
													html += '<input type="checkbox" name="siteId" value="' + site.id
															+ '">&nbsp;&nbsp;' + site.name;
													html += '</label>';
													html += '</div>';
												}
												html += '</div>';
												html += '<button type="button" class="btn btn-success" onclick="SearchArticle.doPush();return false;">发布</button>';
												html += '</form>';
											} else {
												html += '没有可选推送站点.';
											}
											html += '</div>';
											var modal = new Modal({
												id : 'push-sites-modal',
												title : '推送',
												body : html
											});
											modal.show();
										}
									});

						});
	},
	/**
	 * 推送文章
	 */
	doPush : function() {
		var articleIds = SearchArticle.getCheckedIds('input[name="searchArticleId"]');
		var siteIds = SearchArticle.getCheckedIds('input[name="siteId"]');
		if (articleIds != '' && siteIds != '') {
			$.ajax({
				url : '/push/pushRecord/save',
				type : 'post',
				dataType : 'json',
				data : {
					siteIds : siteIds,
					articleIds : articleIds
				},
				success : function(data, textStatus, xhr) {
					alert(data.result);
					if (data.result) {
						$('#push-sites-modal').remove();
						var html = '<div class="container">';
						html += '<p>推送成功.</p>';
						html += '</div>';
						var modal = new Modal({
							title : '推送',
							body : html,
							footer : true
						});
						modal.show();
					}
				}
			});
		}
	},
	/**
	 * 设置文章 分类
	 */
	setArticleCategory : function() {
		var articleIds = SearchArticle.getCheckedIds('input[name="searchArticleId"]');
		var categoryId = $('select[name="categoryId"]').val();
		if (articleIds != '' && categoryId != '') {
			$.ajax({
				url : '/search/searchArticle/setArticleCategory',
				type : 'post',
				dataType : 'json',
				data : {
					ids : articleIds,
					categoryId : categoryId
				},
				success : function(data, textStatus, xhr) {
					var msg = new Msg({
						msg : '分类设置成功.'
					});
					msg.show();
				}
			});
		} else {
			var Msg = new Msg({
				msg : '请选择分类.'
			});
			msg.show();
		}
	},
	/**
	 * 全选与反选
	 */
	checkAllOrNothing : function(checkAll, checkSingle) {
		var isChecked = $(checkAll).is(':checked');
		if (isChecked == true) {
			$(checkSingle).each(function() {
				$(this).prop('checked', true);
			});
		} else {
			$(checkSingle).each(function() {
				$(this).prop('checked', false);
			});
		}
	},
	getCheckedIds : function(el) {
		var checked = $(el);
		var ids = "";
		$(checked).each(function() {
			if ($(this).is(':checked')) {
				var id = $(this).val();
				if (id !== '') {
					ids += id;
					ids += ',';
				}
			}
		});
		return ids;
	},

	/**
	 * 删除
	 */
	remove : function(id) {
		$.ajax({
			url : '/search/searchArticle/remove/' + id,
			type : 'post',
			dataType : 'json',
			success : function(data, textStatus) {
				var content = '删除成功.';
				if (data.result) {
					// $('#'+id).remove();
					$('#' + id).popover({
						title : '删除.',
						content : content,
						placement : 'top',
						container : 'body',
						trigger : 'hover manual',
						delay : {
							"show" : 500,
							"hide" : 100
						}
					});
					$('#' + id).popover('show');
					$('#' + id).on('hide.bs.popover', function() {
						$('#' + id).remove();
					});
				}
			}
		});
	},
	/**
	 * 批量删除
	 */
	multiRemove : function() {
		var articleIds = SearchArticle.getCheckedIds('input[name="searchArticleId"]');
		if (articleIds) {
			$.ajax({
				url : '/search/searchArticle/multiRemove',
				type : 'post',
				dataType : 'json',
				data : {
					ids : articleIds
				},
				success : function(data, textStatus, xhr) {
					var idArray = articleIds.split(',');
					for (var i = 0; i < idArray.length; i++) {
						if (idArray[i]) {
							$('#' + idArray[i]).remove();
						}
					}

				}

			});
		} else {
			Msg
			msg = new Msg({
				msg : '请先选择文章ID'
			});
			msg.show();
		}
	},
	/**
	 * 加载富文本编辑器
	 */
	loadRichTextEditor : function() {
		// tinymce.init({
		// mode :"textareas",
		// plugins:['image'],
		// theme :"modern",
		// language:'zh_CN',
		// //TinyMCE 会将所有的 font 元素转换成 span 元素
		// convert_fonts_to_spans: true,
		// // 换行符会被转换成 br 元素
		// convert_newlines_to_brs: false,
		// // 在换行处 TinyMCE 会用 BR 元素而不是插入段落
		// force_br_newlines: false,
		// // 当返回或进入 Mozilla/Firefox 时，这个选项可以打开/关闭段落的建立
		// force_p_newlines: false,
		// // 这个选项控制是否将换行符从输出的 HTML 中去除。选项默认打开，因为许多服务端系统将换行转换成 <br
		// // />，因为文本是在无格式的 textarea 中输入的。使用这个选项可以让所有内容在同一行。
		// remove_linebreaks: false,
		// // 不能把这个设置去掉，不然图片路径会出错
		// relative_urls: false,
		// // 不允许拖动大小
		// resize: false,
		// font_formats:
		// "宋体=宋体;黑体=黑体;仿宋=仿宋;楷体=楷体;隶书=隶书;幼圆=幼圆;Arial=arial,helvetica,sans-serif;Comic
		// Sans MS=comic sans ms,sans-serif;Courier New=courier
		// new,courier;Tahoma=tahoma,arial,helvetica,sans-serif;Times New
		// Roman=times new
		// roman,times;Verdana=verdana,geneva;Webdings=webdings;Wingdings=wingdings,zapf
		// dingbats",
		// fontsize_formats: "8pt 10pt 12pt 14pt 18pt 24pt 36pt"
		// });
	},
	/**
	 * 删除文章中的图片
	 */
	removeImage : function(id) {
		$.ajax({
			url : '/parse/image/remove',
			type : 'post',
			dataType : 'json',
			data : {
				id : id
			},
			success : function(data, textStatus, xhr) {
				$('#' + id + '-dd').remove();
				$('#' + id + '-dt').remove();
			}
		});
	}
};

$(document).ready(function() {
	SearchArticle.init();
});
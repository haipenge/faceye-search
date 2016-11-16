/**
 * 说明:ParseResult js 脚本 作者:@haipenge
 */
var ParseResult = {
	init : function() {
		// $('.push').click(function() {
		// var id = $(this).attr('id');
		// ParseResult.push2Mongo(id);
		// });

		/**
		 * 全选，或是全不选
		 */
		$('input[name="checkAllParseResultIds"]').click(function() {
			ParseResult.checkAllOrNothing(this, $('input[name="parseResultId"]'));
		});
		// 批量推送
		$('.multi-push-btn').click(function() {
			ParseResult.multiPushParseResult2Mongo();
		});
		// 批量删除
		$('#multi-remove').click(function() {
			ParseResult.multiRemove();
		});
		if ($('.textarea').length > 0) {
			$('.textarea').wysihtml5();
		}
		$('.name-eidt').click(function() {
			ParseResult.editParseResultName(this);
		});
		$('.multi-allow').click(function() {
			ParseResult.multiAllow();
		});
	},
	/**
	 * 推送到Mongo
	 */
	push2Mongo : function(id, isIgnoreFilterWord) {
		$.ajax({
			url : '/parse/parseResult/pushParseResult2Mongo',
			type : 'post',
			dataType : 'json',
			data : {
				parseResultId : id,
				isIgnoreFilterWord : isIgnoreFilterWord
			},
			success : function(data, textStatus) {
				var content = '';
				if (data.result) {
					content = '已成功推送到Mongo.';
				} else {
					content = '推送失败,可能包含有禁词.';
				}
				$('#' + id).popover({
					title : '推送',
					content : content,
					delay : {
						show : 500,
						hide : 100
					},
					placement : 'top',
					trigger : 'hover manual'
				});
				$('#' + id).popover('show');
				$('#' + id).on('hide.bs.popover', function() {
					$('#' + id).remove();
					$('#btn-' + id).parent().empty().append("Pushed");
				});
				return false;
			}
		});
		return false;
	},
	/**
	 * 推送到CMS
	 */
	push2Cms:function(id){
		$.ajax({
			url:'/parse/parseResult/push2Cms',
			type:'post',
			dataType:'json',
			data:{
				id:id
			},
			success:function(data,textStatus,xhr){
				$('#' + id).popover({
					title : '推送',
					content : content,
					delay : {
						show : 500,
						hide : 100
					},
					placement : 'top',
					trigger : 'hover manual'
				});
				$('#' + id).popover('show');
				$('#' + id).on('hide.bs.popover', function() {
					$('#' + id).remove();
					$('#btn-cms-push' + id).parent().empty().append("Pushed");
				});
			}
		});
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
	/**
	 * 批量推送到mongo
	 */
	multiPushParseResult2Mongo : function() {
		var modal = new Modal({
			title : '正在推送'
		});
		var html = '正在推送到Mongo,请稍候';
		modal.setBody(html);
		modal.show();
		var checked = $('input[name="parseResultId"]');
		var ids = ParseResult.getCheckedIds();
		$.ajax({
			url : '/parse/parseResult/multiPushParseResult2Mongo',
			type : 'post',
			dataType : 'json',
			data : {
				parseResultIds : ids
			},
			success : function(data, textStatus) {
				var id = ids.split(',');
				for (i = 0; i < id.length; i++) {
					if (id[i] != '') {
						$('#' + id[i]).parent().empty().append('Pushed');
					}
				}
				modal.hide();
				$('#search-form').submit();
			}
		});
	},
	/**
	 * 测试文章中有多少禁词
	 */
	testFilter : function(id) {
		$.ajax({
			url : '/parse/parseResult/filterTest',
			type : 'post',
			dataType : 'json',
			data : {
				parseResultId : id
			},
			success : function(data, textStatus) {
				var html = new StringBuffer();
				var modal = new Modal({
					title : '禁词列表'
				});
				for (var i = 0; i < data.length; i++) {
					html.append('<p id="' + data[i].id + '-word">');
					html.append('<b>');
					html.append(data[i].id);
					html.append('</b>');
					html.append('&nbsp;');
					html.append('&nbsp;');
					html.append('&nbsp;');
					html.append('&nbsp;');
					html.append(data[i].word);
					html.append('&nbsp;');
					html.append('&nbsp;');
					html.append('<button class="btn btn-success pull-right" onclick="ParseResult.removeFilterWord('
							+ (data[i].id) + ');return false;" name="DEL">DEL</button>');
					html.append('</p>');
				}
				if (data.length === 0) {
					html.append('无禁词');
				}
				modal.setBody(html.toString());
				modal.show();
			}
		});
	},
	/**
	 * 清除禁词
	 */
	removeFilterWord : function(id) {
		$.ajax({
			url : '/parse/filterWord/ajaxRemove/' + id,
			type : 'post',
			dataType : 'json',
			success : function(data, textStatus) {
				if (data.result) {
					$('#' + id + "-word").remove();
				}
			}
		});
	},
	/**
	 * 删除ParseResult
	 */
	remove : function(id) {
		$.ajax({
			url : '/parse/parseResult/remove/' + id,
			type : 'post',
			dataType : 'json',
			success : function(data, textStatus) {
				$('#' + id).remove();
			}
		});
	},
	getCheckedIds : function() {
		var checked = $('input[name="parseResultId"]');
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
	 * 批量删除ParseResult
	 */
	multiRemove : function() {
		var ids = ParseResult.getCheckedIds();
		if (ids !== '') {
			$.ajax({
				url : '/parse/parseResult/multiRemove',
				type : 'post',
				dataType : 'json',
				data : {
					ids : ids
				},
				success : function(data, textStatus) {
					var idArray = ids.split(",");
					if (idArray && idArray.length > 0) {
						for (var i = 0; i < idArray.length; i++) {
							var id = idArray[i];
							if (id && id != '') {
								$('#' + id).remove();
							}
						}
					}

				}
			});
		}

	},
	/**
	 * 显示编辑标题文本框
	 */
	editParseResultName : function(el) {
		if ($(el).find('input').length === 0) {
			var id = $(el).attr('id').split('-')[1];
			var name = $(el).html();
			var html = '<input name="name" value="' + name
					+ '" class="form-control" onblur="ParseResult.doNameUpdateOfParseResult(this);return false;">';
			$(el).empty().append(html);
		}
	},
	/**
	 * 编辑标题，并保存
	 */
	doNameUpdateOfParseResult : function(el) {
		var id = $(el).parent().attr('id').split('-')[1];
		var name = $(el).val();
		if ('' !== name && undefined !== name && name.length >= 5) {
			$.ajax({
				url : '/parse/parseResult/updateName',
				type : 'post',
				dataType : 'json',
				data : {
					id : id,
					name : name
				},
				success : function(data, textStatus) {
					if (data.result) {
						ParseResult.pop(el, '标题修改成功.');
						$(el).parent().empty().append(name);
					} else {
						ParseResult.pop(el, '标题修改失败.');
					}
				},
				failure : function() {
					ParseResult.pop(el, '标题修改失败.');
				}
			});
		} else {
			ParseResult.pop(el, '标题长度最少需要5个有效字符.');
			// var modal =new Modal({title:'提示',body:'标题长度最少需要5个有效字符.'});
			// modal.show();
		}
		return;
	},
	pop : function(el, msg) {
		$(el).popover('destroy');
		$(el).popover({
			// title:'提示',
			content : msg,
			html : 'true',
			container : 'body',
			delay : {
				"show" : 500,
				"hide" : 100
			},
			placement : 'top'
		});
		$(el).popover('show');
	},
	/**
	 * 显示解析结果分类选择窗口
	 */
	showArticleCategoriesSelectWindow : function() {
		$.ajax({
			url : '/search/articleCategory/categories.json'
		});
	},
	/**
	 * 审核
	 */
	allow : function(parseResultId) {
		$.ajax({
			url : '/parse/parseResult/allow',
			type : 'post',
			dataType : 'json',
			data : {
				parseResultId : parseResultId
			},
			success : function(data, textStatus, xhr) {
				if (data.result) {
					ParseResult.pop($('#' + parseResultId), '审核通过.');
					$('#' + parseResultId).remove();
				}
			}
		});
	},
	/**
	 * 批量审核
	 */
	multiAllow : function() {
		var ids = ParseResult.getCheckedIds();
		var isWholeSite = $('input[name="isWholeSite"]').is(':checked');
		if (isWholeSite) {
			var siteId = $('select[name="EQ|siteId"]').val();
			if (siteId && siteId!='0') {
				$.ajax({
					url : '/parse/parseResult/multiAllow',
					type : 'post',
					dataType : 'json',
					data : {
						siteId : siteId
					},
					success : function(data, textStatus, xhr) {
						if (data.result) {
							var msg = new Msg({
								msg : '已全站推送'
							});
							msg.show();
						}
					}
				});
			} else {
				var msg = new Msg({
					msg : '请先选择站点',
					type : 'warning'
				});
				msg.show();
			}
		} else {
			if (ids) {
				$.ajax({
					url : '/parse/parseResult/multiAllow',
					type : 'post',
					dataType : 'json',
					data : {
						parseResultIds : ids
					},
					success : function(data, textStatus, xhr) {
						if (data.result) {
							var id = ids.split(',');
							for (i = 0; i < id.length; i++) {
								if (id[i] != '') {
									$('#' + id[i]).parent().empty().append('Pushed');
								}
							}
						}
					}
				});
			} else {
				var msg = new Msg({
					msg : '请先选择文章',
					type : 'warning'
				});
				msg.show();
			}
		}
	}
};
$(document).ready(function() {
	ParseResult.init();
});
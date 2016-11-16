/**
*说明:AnalyzerWord js 脚本
*作者:@haipenge
*/
var AnalyzerWord={
  init:function(){
  }
};
/**
*说明:FilterWord js 脚本
*作者:@haipenge
*/
var FilterWord={
  init:function(){
  }
};
/**
*说明:Image js 脚本
*作者:@haipenge
*/
var Image={
  init:function(){
  }
};
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
/**
*说明:PushRecord js 脚本
*作者:@haipenge
*/
var PushRecord={
  init:function(){
  }
};
/**
*说明:Article js 脚本
*作者:@haipenge
*/
var Article={
  init:function(){
  }
};
/**
*说明:ArticleCategory js 脚本
*作者:@haipenge
*/
var ArticleCategory={
  init:function(){
  }
};
/**
*说明:Email js 脚本
*作者:@haipenge
*/
var Email={
  init:function(){
	 $('#send-mails').click(function(){
		 Email.send();
	 });
  },
  /**
   * 发送邮件
   */
  send:function(){
	  var checkedIds=Check.getCheckedIds($('input[name="check-single"]'));
	  alert(checkedIds);
	  $.ajax({
		  url:'/search/email/send',
		  type:'post',
		  dataType:'json',
		  data:{
			  ids:checkedIds
		  },
		  success:function(data,textStatus,xhr){
			  var m=new Msg({msg:'发送成功'});
			  m.show();
		  }
	  });
  },
};

$(document).ready(function(){
	Email.init();
});
/**
*说明:Movie js 脚本
*作者:@haipenge
*/
var Movie={
  init:function(){
	  /**
       *全选、全不选
       */
       $('input[name="check-all"]').click(function(){
	     Check.onCheck($('input[name="check-all"]'),$('input[name="check-single"]'));
	    });
       $('.push2ProductEnv').click(function(){Movie.push2ProductEnv();});
       $('.multi-remove').click(function(){Movie.multiRemove()});
  },
  /**
   * 推送到生产环境
   */
  push2ProductEnv:function(){
	  var checkedIds=Check.getCheckedIds($('input[name="check-single"]'));
	  $.ajax({
		  url:'/search/movie/push2ProductEnv',
		  type:'post',
		  dataType:'json',
		  data:{
			  ids:checkedIds
		  },
		  success:function(data,textStatus,xhr){
			  var m=new Msg({msg:'推送成功'});
			  m.show();
		  }
	  });
  },
  multiRemove:function(){
	  var checkedIds=Check.getCheckedIds($('input[name="check-single"]'));
	  $.ajax({
		  url:'/search/movie/multiRemove',
		  type:'post',
		  dataType:'json',
		  data:{
			  ids:checkedIds
		  },
		  success:function(data,textStatus,xhr){
			  var idArray=checkedIds.split(',');
			  if(idArray){
				  for(var i=0;i<idArray.length;i++){
					  $('#'+idArray[i]).remove();
				  }
			  }
			  var m=new Msg({msg:'删除成功'});
			  m.show();
		  }
	  });
  }
};

$(document).ready(function(){
	Movie.init();
});
/**
*说明:RequestRecord js 脚本
*作者:@haipenge
*/
var RequestRecord={
  init:function(){
  }
};
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
/**
*说明:Subject js 脚本
*作者:@haipenge
*/
var Subject={
  init:function(){
  }
};
/**
 * 说明:CrawlResult js 脚本 作者:@haipenge
 */
var CrawlResult = {
	init : function() {
		$('input[name="checkAll"]').click(function() {
			Check.onCheck($('input[name="checkAll"]'), $('input[name="checkSingle"]'));
		});
		$('.re-parse').click(function() {
			CrawlResult.reParse();
		});
		$('.regexp-test').click(function() {
			CrawlResult.regexpTest();
		});
	},
	/**
	 * 重新解析
	 */
	reParse : function() {
		var ids = Check.getCheckedIds($('input[name="checkSingle"]'));
		var isWholeSite = $('input[name="isWholeSite"]').is(':checked');
		if (isWholeSite) {
			var siteId = $('select[name="EQ|siteId"]').val();
			var linkType=$('select[name="EQ|linkType]').val();
			if (siteId) {
				$.ajax({
					url : '/spider/crawlResult/reParse',
					type : 'post',
					dataType : 'json',
					data : {
						siteId : siteId,
						linkType:linkType
					},
					success : function(data, textStatus, xhr) {
						var msg = new Msg({
							msg : '已设置重新解析.'
						});
						msg.show();
					}
				});
			} else {
				var msg = new Msg({
					msg : '请选择站点',
					type : 'warning'
				});
				msg.show();
			}
		} else {
			if (ids) {
				$.ajax({
					url : '/spider/crawlResult/reParse',
					type : 'post',
					dataType : 'json',
					data : {
						ids : ids
					},
					success : function(data, textStatus, xhr) {
						var msg = new Msg({
							msg : '已设置重新解析.'
						});
						msg.show();
					}
				});
			} else {
				var msg = new Msg({
					msg : '请先选择重新解析的记录.'
				});
				msg.show();
			}
		}
	},
	/**
	 * 正则表达式测试
	 */
	regexpTest : function() {
		var crawlResultId = $('input[name="crawlResultId"]').val();
		var regexp = $('textarea[name="regexp"]').val();
		var msg = '';
		msg += '<p>';
		msg += '正则表达式是:';
		msg += regexp;
		msg += '</p>';
		if (!regexp || regexp == '' || regexp == undefined) {
			msg += '<p>正则表达式不能为空</p>';
			var m = new Msg({
				msg : msg
			});
			m.show();
		} else {
			$.ajax({
				url : '/parse/parseResult/regexpTest',
				type : 'post',
				dataType : 'json',
				data : {
					crawlResultId : crawlResultId,
					regexp : regexp
				},
				success : function(data, textStatus, xhr) {
					if (data.result) {
						msg += '<p>';
						msg += '没有匹配的合适的结果';
						msg += '</p>';
					} else {
						msg += '<h3>匹配结果:</h3>';
						for (var i = 0; i < data.length; i++) {
							var record = data[i];
							msg += '<p>';
							msg += record['1'];
							msg += '</p>';
						}
					}
					var m = new Msg({
						msg : msg
					});
					m.show();
				}
			});
		}
	}
};
$(document).ready(function() {
	CrawlResult.init();
});
/**
 * 说明:Link js 脚本 作者:@haipenge
 */
var Link = {
	init : function() {
		$('input[name="checkAllLink"]').click(function() {
			Check.onCheck($('input[name="checkAllLink"]'), $('input[name="checkSingleLink"]'));
		});
		$('.re-crawl').click(function(){
			Link.reCrawl("1");
		});
		$('.as-seed').click(function(){
			Link.reCrawl("2");
		});
		$('.multi-remove').click(function(){
			Link.multiRemove();
		});
	},
	/**
	 * 批量删除
	 */
	multiRemove:function(){
		var ids = Check.getCheckedIds($('input[name="checkSingleLink"]'));
	    if(ids && ids !=''){
	    	$.ajax({
	    		url:'/spider/link/multiRemove',
	    		data:'post',
	    		dataType:'json',
	    		data:{
	    			ids:ids
	    		},
	    		success:function(data,textStats){
	    			var msg=new Msg({msg:'删除成功'});
	    			msg.show();
	    			var id = ids.split(',');
					for (i = 0; i < id.length; i++) {
						if (id[i] != '') {
							$('#' + id[i]).remove();
						}
					}
	    		}
	    	});
	    }
	},
	/**
	 * 设置重新爬取
	 */
	reCrawl : function(type) {
		var ids = Check.getCheckedIds($('input[name="checkSingleLink"]'));
		if (ids && ids != '') {
			$.ajax({
				url : '/spider/link/reCrawl',
				type : 'post',
				dataType : 'json',
				data : {
					ids : ids,
					type:type
				},
				success : function(data, textStaus, xhr) {
					var info='';
					if(type==1){
						info='已设置重新爬取';
					}else{
						info='已设置为种子链接';
					}
					var msg = new Msg({
						msg : info
					});
					msg.show($('#msg'));
				}
			});
		} else {
			var msg = new Msg({
				msg : '请先选择链接.'
			});
			msg.show($('#msg'));
		}
	}
};
$(document).ready(function() {
	Link.init();
});
/**
*说明:MatcherConfig js 脚本
*作者:@haipenge
*/
var MatcherConfig={
  init:function(){
  }
};
/**
*说明:Site js 脚本
*作者:@haipenge
*/
var Site={
  init:function(){
  }
};
/**
*说明:Weixin js 脚本
*作者:@haipenge
*/
var Weixin={
  init:function(){
  }
};

$(document).ready(function(){
	Weixin.init();
});

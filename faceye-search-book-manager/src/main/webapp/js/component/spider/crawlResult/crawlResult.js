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
		if (!regexp || regexp == '' || regexp==undefined) {
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
						msg+='<h3>匹配结果:</h3>';
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
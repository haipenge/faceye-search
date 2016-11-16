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
		$('.call-super-parse').click(function(){
			CrawlResult.callSuperParse(this);
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
	},
	/**
	*调用 超级解析器
	*/
	callSuperParse:function(el){
		var id=$(el).parent().parent().find('input[name="checkSingle"]').val();
		$.ajax({
			url:'/parse/parseResult/callSuperParse',
			type:'post',
			dataType:'json',
			data:{
				id:id
			},
			success:function(data,textStatux){
				var msg=new Msg({msg:'调用成功'});
				msg.show();
			}
		});
	}
};
$(document).ready(function() {
	CrawlResult.init();
	Copy.copy('#copy');
});
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
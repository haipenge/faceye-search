/**
 * 工具类
 */
var SearchTools = {
	init : function() {
		Regexp.init();
	},

};
/**
 * 正则表达式检查工具
 */
var Regexp = {
	init : function() {
		$('#regexp-match').click(function() {
			Regexp.match();
		});
		$('#match-result-container').hide();
	},
	match : function() {
		var regexp = $('textarea[name="regexp"]').val();
		var content = $('textarea[name="content"]').val();
		var regex = new RegExp(regexp, "gim");
		var results = content.match(regex);
		var sb = '';
		var index=1;
		if (results) {
			//sb+='共匹配到&nbsp;&nbsp;<b>'+results.length+'</b>&nbsp;&nbsp;条数据<br><br>';
			for (var i = 0; i < results.length; i++) {
				var result = results[i];
				var gt = /</gim;
				var lt = />/gim;
				result=result.replace(/</gim, '&lt;');
				result=result.replace(/>/gim, '&gt;');
				//sb+='<font color="blue">';
				//sb+=index;
				//sb+='</font>';
				//sb+='.&nbsp;&nbsp;'
				sb += result;
				sb += '\r\n';
				 sb+='<br>';
				 index++;
			}
		} else {
			sb += '没有匹配到任何数据.请检查正则表达式和匹配内容.';
		}
		$('#match-results').empty().append(sb);
		$('#match-result-container').show();
	}
};

String.prototype.multiReplace = function(hash) {
	var str = this, key;
	for (key in hash) {
		if (Object.prototype.hasOwnProperty.call(hash, key)) {
			str = str.replace(new RegExp(key, 'g'), hash[key]);
		}
	}
	return str;
};

$(document).ready(function() {
	SearchTools.init();
});
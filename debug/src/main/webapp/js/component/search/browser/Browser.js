/**
*定义浏览器插件
*author:@haipenge
*/
$(document).ready(function(){
	Browser.init();
});
/**
 * 定义浏览器插件
 */
var Browser={
		init:function(){
			$('#browser button[type="button"]').click(function(){
				Browser.load();
			});
			$('#browser input[type="text"]').bind('keypress',function(event){
				if(event.keyCode ==13){
					Browser.load();
				}
			});
			Browser.loadRichTextEditor();
		},
		/**
		 * 加载页面
		 */
		load:function(){
			var url=$('#browser input[type="text"]').val();
			
			$('#browser-view-iframe').load(function(){
				/**
				 * 取得body中的内容
				 */
				var body=$(this).contents().find("body").html();
				/**
				 * 取得body的高度
				 */
				var height=$(this).contents().find("body").height();
			});
			$('#browser-view-iframe').attr('src',url);
		},
		/**
		 * 加载富文本编辑器
		 */
		loadRichTextEditor:function(){
			tinymce.init({
				mode :"textareas",
				theme :"modern",
				language:'zh_CN'
			});
		}
};

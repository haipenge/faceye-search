/**
*说明:Article js 脚本
*作者:@haipenge
*/
var Article={
  clickCount:0,
  init:function(){
	  //Article.onContentClick();
  },
  onContentClick:function(){
	  //文章内容区ID
	  //conte id:article-content
	  //底部广告位ID
	  //bottom ad id:bottom-ad
	  $('#article-content').mousedown(function(){
		  Article.clickCount+=1;
		  //&& Article.clickCount % 2 ==0 
		  //if(Article.clickCount>=2 ){
		    //location.href='#bottom-ad';
		  //}
//		  $('#bottom-ad').attr('id','');
		  Article.init();
	  });
  }
};
$(document).ready(function(){
	Article.init();
});
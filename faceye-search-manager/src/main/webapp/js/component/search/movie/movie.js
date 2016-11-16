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
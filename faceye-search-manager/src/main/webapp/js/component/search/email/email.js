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
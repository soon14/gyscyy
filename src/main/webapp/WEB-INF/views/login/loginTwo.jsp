<!-- 透明登录框首页 -->
<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<script type="text/javascript">
			Aptech = A = {};
			Aptech.CONTEXT_ROOT_PATH = "${ctx}";
			
		</script>	
		<title>中国电建贵阳院投资运营生产管理系统</title>

		<meta name="description" content="User login page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="${ctx}/static/css/themes/${theme}/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx}/static/fonts/awesome/css/font-awesome.min.css" />

		<!-- text fonts -->
		<link rel="stylesheet" href="${ctx}/static/css/themes/${theme}/fonts.googleapis.com.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="${ctx}/static/css/themes/${theme}/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
		<![endif]-->
		<link rel="stylesheet" href="${ctx}/static/css/themes/${theme}/ace-rtl.min.css" />

		<!-- <link rel="stylesheet" href="${ctx}/static/css/core.css" /> 
		<link href="${ctx}/favicon.ico" rel="shortcut icon">-->
		
		
		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="${ctx}/static/css/ace-ie.min.css" />
		<![endif]-->
		<!-- inline styles related to this page -->
		
		<link rel="stylesheet" href="${ctx}/static/css/themes/${theme}/loginTransparent.css" />
		
		<!--[if !IE]>-->
		<script src="${ctx}/static/js/core/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->
		
		<script src="${ctx}/static/js/core/bootstrap.min.js"></script>
		
		<!-- jquery validate -->
		<script src="${ctx}/static/js/core/jquery.validate.min.js"></script>
		<script src="${ctx}/static/js/core/jquery-additional-methods.min.js"></script>
		<script src="${ctx}/static/js/core/jquery.validate.messages_zh.min.js"></script>	
		
		<!-- 自定义工具 -->
		<script src="${ctx}/static/js/utils.js"></script>
	</head>
	
	<body class="login-layout">
		<div class="headDiv">
			<div class="login-logo"></div>
			<div class="login-text">中国电建贵阳院投资运营生产管理系统</div>
		</div>
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div  id='loginBox' style="width: 100%">
						<form id="loginForm" role="form" style="margin-top: 10%">
							<fieldset>
							<div id="u5" class="ax_default _图片">
							<img id="u5_img" class="img " src="../static/images/backImage2/u5.png" style="float: left;"/>
             					<img id="u6_img" class="img " src="../static/images/backImage2/u6.png" style="float: right;"/>
            				</div>
              					
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class='footerDiv'></div>
		<script type="text/javascript">
			jQuery(function($) {
				 $(document).on('click', '.toolbar a[data-target]', function(e) {
					e.preventDefault();
					var target = $(this).data('target');
					$('.widget-box.visible').removeClass('visible');//hide others
					$(target).addClass('visible');//show target
				 });
				 
				 $(document).keyup(function (evnet) {
					 if (evnet.keyCode == '13') {
						 $("#btnLogin").trigger("click");
					 }
				 });
				 
				 $('#loginForm').validate({
					errorElement: 'div',
					errorClass: 'help-block',
					focusInvalid: false,
					ignore: "",
					 rules: {
						username: {
							required: true,
						},
						password: {
							required: true,
						},
					 },
					highlight: function (e) {
						if($(".alert-info")){
							$(".alert-info").remove();
						}
						$(e).closest('.form-group').removeClass('has-info').addClass('has-error');
					},
			
					success: function (e) {
						$(e).closest('.form-group').removeClass('has-error');//.addClass('has-info');
						$(e).remove();
					},
			
					errorPlacement: function (error, element) {
						if(element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
							var controls = element.closest('div[class*="col-"]');
							if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
							else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
						}
						else if(element.is('.select2')) {
							error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
						}
						else if(element.is('.chosen-select')) {
							error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
						}
						else error.insertAfter(element);
					},
					submitHandler: function(form){
						$.ajax({
							url : format_url("/login/auth"),
							dataType: "JSON",
							contentType : "application/json",
							data: $("#loginForm").serializeObject(),
							success: function(result){
								if(result.result == "success"){
									var uid = result.data;
									window.location.href ="${ctx}/login/main?uid=" + uid;
								}else{
									$("#msgDiv").empty();
									$("#msgDiv").append("<div class='alert alert-info' style='padding:0px; margin-bottom:0px; height:20px;'><button class='close' type='button' data-dismiss='alert'><i class='ace-icon fa fa-times'></i></button>" + result.data +"</div>");
								}
							}
						});
					}
				 });
				 
				 $("#btnLogin").on("click", function(){
						$("#loginForm").submit();
				 })
				 var day = "${day}";
				 if(day=="一月初一" || day=="一月初二" || day=="一月初三"||day=="一月初四"||day=="一月初五"){
					 $(".login-layout").css({'background':'#1D2024 url(../static/images/backImage2/backImage1.jpg) no-repeat', 'background-size':'100%'});
					 $("#btnLogin").removeClass("btn-primary").addClass("btn-danger");
				 }else if(day=="一月十五" ||day=="一月十六" ||day=="一月十七"){
					 $(".login-layout").css({'background':'#1D2024 url(../static/images/backImage2/backImage2.jpg) no-repeat', 'background-size':'100%'});
					 $("#btnLogin").removeClass("btn-primary").addClass("btn-danger");
				 }else if(day=="八月十五" ||day=="八月十六" ||day=="八月十六"){
					 $(".login-layout").css({'background':'#1D2024 url(../static/images/backImage2/backImage3.jpg) no-repeat', 'background-size':'100%'});
					 $("#btnLogin").removeClass("btn-primary").addClass("btn-danger");
				 }else{
					 var index = Math.floor(Math.random()*11+1);
					 $(".login-layout").css({'background':'#1D2024 url(../static/images/backImage/'+index+'.jpg) no-repeat', 'background-size':'100%'});
					 $("#btnLogin").removeClass("btn-danger").addClass("btn-primary");
				 }
				 
				$('#u5_img').click(function(){
					var openWin = window.open();
					openWin.location = "http://172.19.220.5:8080/ynjk/home/dataView";
				});
				$('#u6_img').click(function(){
					window.location.href ="${ctx}/login/main";
				});
				 $('#forBoxOneLabel').click(function(){
					var getPosition = $(this).css('background-position');
					if(getPosition=='1px 50%'){
						$(this).css('background-position','-30px 50%');
					}else{
						$(this).css('background-position','1px 50%');
					}
				  });
			});
		</script>
		 <style type="text/css">
            .u5 img{position:absolute;bottom:10px;left:50%;margin-left:-60px;border:0;}
        </style>
	</body>
</html>
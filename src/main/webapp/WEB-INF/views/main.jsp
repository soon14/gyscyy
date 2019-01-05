<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh"> 
    <head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>	
		
		<script type="text/javascript">
			Aptech = A = {};
			Aptech.CONTEXT_ROOT_PATH = "${ctx}";
			Aptech.UID = "${uid}";
		</script>	
		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="${ctx}/static/css/themes/${theme}/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx}/static/fonts/awesome/css/font-awesome.min.css" />

		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="${ctx}/static/css/themes/${theme}/select2.min.css" />
		
		<!-- text fonts -->
		<link rel="stylesheet" href="${ctx}/static/css/themes/${theme}/fonts.googleapis.com.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="${ctx}/static/css/themes/${theme}/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
		<![endif]-->
		<link rel="stylesheet" href="${ctx}/static/css/themes/${theme}/ace-skins.min.css" />
		<link rel="stylesheet" href="${ctx}/static/css/themes/${theme}/ace-rtl.min.css" />

		<!-- gritter -->
		<link rel="stylesheet" href="${ctx}/static/css/themes/${theme}/jquery.gritter.min.css" />
		
		<link rel="stylesheet" href="${ctx}/static/css/themes/${theme}/style.css" />
		<!--nav-skin-->
		<link rel="stylesheet" href="${ctx}/static/css/themes/${theme}/nav-skin.css" />
		
		<link rel="stylesheet" href="${ctx}/static/css/themes/${theme}/iconfont.css" />
		<!-- <link rel="stylesheet" href="${ctx}/static/css/core.css" /> 
		<link href="${ctx}/favicon.ico" rel="shortcut icon">-->
		
		
		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="${ctx}/static/css/ace-ie.min.css" />
		<![endif]-->
		<!-- inline styles related to this page -->
		
		<!-- ace settings handler -->
		<script src="${ctx}/static/js/ace/ace-extra.min.js"></script>

		<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

		<!--[if lte IE 8]>
		<script src="assets/js/html5shiv.min.js"></script>
		<script src="assets/js/respond.min.js"></script>
		<![endif]-->

		<title>主页面</title>
    </head>  
	<body class="no-skin">
		<%@ include file="/WEB-INF/views/common/header.jsp" %>	
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
				<%@ include file="/WEB-INF/views/common/sidebar.jsp" %>
				<div class="main-content">
					<div class="main-content-inner" id="page-container">
						<!-- 页面渲染 -->
					</div>
					<%@ include file="/WEB-INF/views/common/footer.jsp" %>	
				</div>		
				<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
					<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
				</a>			
			</div>
			<%@ include file="/WEB-INF/views/common/script.jsp" %>
			
			<script type="text/javascript">
				template.openTag = '<!--[';
				template.closeTag = ']-->';
				$('#sidebar').niceScroll({
					horizrailenabled:false,
				    cursorcolor: "#013d5a",//#CC0071 光标颜色
				    cursoropacitymax: 1, //改变不透明度非常光标处于活动状态（scrollabar“可见”状态），范围从1到0
				    touchbehavior: false, //使光标拖动滚动像在台式电脑触摸设备
				    cursorwidth: "5px", //像素光标的宽度
				    cursorborder: "0", // 游标边框css定义
				    cursorborderradius: "5px",//以像素为光标边界半径
				    autohidemode: true  //是否隐藏滚动条
				});
				$('#page-container').niceScroll({
					horizrailenabled:false,
				    cursorcolor: "#ccc",//#CC0071 光标颜色
				    cursoropacitymax: 1, //改变不透明度非常光标处于活动状态（scrollabar“可见”状态），范围从1到0
				    touchbehavior: false, //使光标拖动滚动像在台式电脑触摸设备
				    cursorwidth: "5px", //像素光标的宽度
				    cursorborder: "0", // 游标边框css定义
				    cursorborderradius: "5px",//以像素为光标边界半径
				    autohidemode: 'scroll' //是否隐藏滚动条
				});
				$("#btnHome").on("click", function(){
					window.location.href=format_url("/centralized/home");
				});
				$("#btnLight").on("click", function(){
					window.location.href=format_url("/centralized/light?sid=3");
				});
				$("#btnWind").on("click", function(){
					window.location.href=format_url("/realTimeDisplay/windfield/index/damaoqi");
				});
				$("#test").on("click", function(){
					window.location.href=format_url("/sysUser/test");
				});
				$("#btnScreen").on('click', function(){
					window.location.href=format_url("/centralized/home");
				});
				$("#logo-title").on('click', function(){
					A.loadPage({
						render : '#page-container',
						url : format_url("/demo/index")
					});
				});
				seajs.use([ 'dialog'], function(A) {
					$("#info").on("click", function() {
						var id = <%=sysUserEntity.getId()%>;
						profileDialog = new A.dialog({
							title:"个人信息维护",
							height:470,
							width:650,
							url:format_url("/sysUser/profileView/"+ id),
						}).render();
					});
					$("#password").on("click", function() {
						var id = <%=sysUserEntity.getId()%>;
						userDialog = new A.dialog({
							title:"修改密码",
							height:350,
							width:550,
							url:format_url("/sysUser/reset/"+ id),
						}).render();
					});
					$("#changeUnit").on("click", function() {
						var id = <%=sysUserEntity.getId()%>;
						profileDialog = new A.dialog({
							title:"单位变更",
							height:250,
							width:650,
							url:format_url("/sysUser/getChangeUnit/"+ id),
						}).render();
					});
					$("#getPic").on("click",function(){
					var getPic = $(this);
					var title = getPic.attr("title");
					if(title =='截图'){
						getPic.attr('href','#');
						getPic.removeAttr("download");
	   					 html2canvas(document.getElementById("main-container"), { 
	   					 	height: $("#main-container").outerHeight() + 20, 
	   					 	width: $("#main-container").outerWidth() + 20 , 
	   					 	onrendered: function(canvas) { //将canvas画布放大若干倍，然后盛放在较小的容器内，就显得不模糊了 
		   							 var timestamp = Date.parse(new Date()); //把截取到的图片替换到a标签的路径下载 
		   							 getPic.attr("title","下载");
		   							 getPic.attr('href',canvas.toDataURL()); //下载下来的图片名字 
		   							 getPic.attr('download',timestamp + '.png') ; //document.body.appendChild(canvas);
	   						 	} 
	   						 //可以带上宽高截取你所需要的部分内容
							}); 
	   					alert("截图成功，请再次点击下载截图");
					}else{
							getPic.attr("title","截图");
					}
   				});
				
				});
		 		var int = self.setInterval('changeHeaderMessage()',300000);
		 		changeHeaderMessage();
				function changeHeaderMessage(){
					var getMessageNumber = $('#waittingMessage span').text();//获取消息数量与后面请求后的消息数量做对比
					$.ajax({
						url :format_url("/messageCenter/searchMessageCount"),
						contentType : 'application/json',
						dataType : 'JSON',
						cache: false,
						type : 'POST',
						success: function(result){
							var messageNum = result.data;
							if(messageNum!=getMessageNumber){
								setTimeout(function(){
									$('#messageShowPage').css('display','block');
									$('#messageShowPage').animate({bottom:'0px'},1000);
								},500)
								setTimeout(function(){
									$('#messageShowPage').animate({bottom:'-120px'},1000);
								},6000)
							}
							messageNum>99?messageNum='99+':messageNum=messageNum;
							messageNum==0?messageNum='0':messageNum=messageNum;
							$('#waittingMessage span').text(messageNum);
						}
					});
					$.ajax({
						url :format_url("/messageCenter/searchTaskCount"),
						contentType : 'application/json',
						dataType : 'JSON',
						cache: false,
						type : 'POST',
						success: function(result){
							var waittingNum = result.data;
							waittingNum>99?waittingNum='99+':waittingNum=waittingNum;
							waittingNum==0?waittingNum='0':waittingNum=waittingNum;
							$('#waittingMission span').text(waittingNum);
						}
					});
				}
				$('#messageShowPage a').on('click',function(){
					A.loadPage({
						render : '#page-container',
						url : format_url('/messageCenter/index')
					});	
				})
				$('#waittingMessage').on('click',function(){
					A.loadPage({
						render : '#page-container',
						url : format_url('/messageCenter/index')
					});	
				})
				$('#waittingMission').on('click',function(){
					A.loadPage({
						render : '#page-container',
						url : format_url('/todoTask/list/1/10')
					});	
				})
				function getHeadImg(){
					var url = "<%=request.getContextPath()%>/<%=sysUserEntity.getHeadUrl()%>"
					var xmlHttp ;
			        if (window.ActiveXObject)
			         {
			          xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			         }
			         else if (window.XMLHttpRequest)
			         {
			          xmlHttp = new XMLHttpRequest();
			         }
			        xmlHttp.open("Get",url,false);
			        xmlHttp.send();
			        if(xmlHttp.status==404){
			        	$('#headImg').attr('src','${ctx}//static/images/avatars/user.png');
			        }
			        else{
			        	$('#headImg').attr('src',url);
			        }
				}
				getHeadImg();
				function chargeKeepChecked(id){
					var getText = jQuery.trim($(id)[0].innerText);
					var chargeTrue = false;
					if(getText=='保存'){
						$(id)[0].innerText = '提交中...';
						$(id).addClass('disabled');
					}else{
						chargeTrue = true;
					}
					return chargeTrue;
				}
				function firstPage(){
					A.loadPage({
						render : '#page-container',
						url : format_url('/login/firstPage')
					});
				}
				//初始化加载首页公告栏
				firstPage();
				//首页跳转
				$('#indexImgA').on('click',function(){
					firstPage();
				})
// 				if(chargeKeepChecked("#editInstockBtn")){
// 					return;
// 				}
			</script>
    </body>  
</html>  
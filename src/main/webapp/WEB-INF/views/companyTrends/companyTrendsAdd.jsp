<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="breadcrumbs ace-save-state" id="breadcrumbs" style="margin-bottom:100px;">
    		<ul class="breadcrumb">
    			<li>
    				<i class="ace-icon fa fa-home home-icon"></i>
    				<a href="javascript:void(0);" onclick="firstPage()">首页</a>
    			</li>
				<li class="active">公司动态</li>
    			<li class="active">添加</li>
    		</ul><!-- /.breadcrumb -->
    		<div style="margin-right:180px;margin-top:10px;">
	   			<button id="backBtnAddTrainPlan" class="btn btn-xs btn-primary pull-right">
	   				<i class="glyphicon glyphicon-share-alt"></i>
	   				返回
	   			</button>
   			</div>
    		<h5 class="table-title header smaller blue" style="margin-left:30px;margin-right:20px">基础信息</h5>
    	</div>
		<div class="col-md-12">
			<form class="form-horizontal" role="form"  style="margin-right:180px;" id="addForm">
			<input id="id" class="col-md-12" name="id"   readonly  type="hidden"/>
			<input id="uploadPersonid" class="col-md-12" name="uploadPerson"   readonly  type="hidden" value="${sysUserEntity.name }"/>
			<input id="uploadTimeid" class="col-md-12" name="uploadTime"   readonly  type="hidden"/>
			<input class="col-md-12" id="" name="createPeopleId" type="hidden" value="${sysUserEntity.id }">
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>标题：
					</label>
					<div class="col-md-10">
	                   <input class="col-md-12" id="titleId" name="title" type="text" placeholder="" maxlength="256" value="">
                	</div>
               	</div>
               	<div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						副标题：
				    </label>
				    <div class="col-md-10">
						<input class="col-md-12" id="subTitleId" name="subTitle" type="text" placeholder="" maxlength="256" value="">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>发布时间：</label>
                 		<div class="col-md-4">
                  		<div id="publishTimeDiv"></div>
                  		</div>
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>结束时间：</label>
                 		<div class="col-md-4">
                  		<div id="endTimeDiv"></div>
                  		</div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">是否上推荐位</label>
					<div class="col-md-10">
					<input name="ifCommend" type="checkbox" value="1" />
					</div>
               </div>
		       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;" id="commendPicturefileNeed">*</span>推荐位图片
						</label>
							<div class="col-md-10" id="commendPicturefile"></div>
               </div>
<!-- 		       <div class="form-group"> -->
<!-- 						<label class="col-md-2 control-label no-padding-right">首页图片</label> -->
<!-- 							<div class="col-md-10" id="homePagePicturefile"></div> -->
<!--                </div> -->
               <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>内容
					</label>
					<div class="col-md-10">
	                   <textarea class="col-md-12" id="contentId" name="content"style="height:80px; resize:none;"  placeholder="" maxlength="128" >${entity.content }</textarea>
                	</div>
               </div>
				<label class="col-md-2 control-label no-padding-right" style="font-size:5px;">
				<span style="color:red;">*</span>预览内容
				</label>
               <div class="form-group" style="margin-left:265px;">
					
					<div class="col-md-10" id="previewDiv" name = "previewName" style="width: 100%;"></div>
               </div>
		       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">附件</label>
							<div class="col-md-10" id="fileAddressfile"></div>
               </div>
            </form>
    		<div style="margin-right:180px;">
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right" >
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','uploaddropzone','my97datepicker','wysiwyg'], function(A){
					//推荐位图片
					var commendPictureZone =new A.uploaddropzone({
						render : "#commendPicturefile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
						acceptedFiles:".png,.jpg",//指定上传文件类型
						options:{
							maxFiles:1,
						}
					}).render('1');
					//首页图片
// 					var homePagePictureZone =new A.uploaddropzone({
// 						render : "#homePagePicturefile",
// 						fileId:[],
// 						autoProcessQueue:true,//是否自动上传
// 						addRemoveLinks : true,//显示删除按钮
// 						acceptedFiles:".png,.jpg",//指定上传文件类型
// 						options:{
// 							maxFiles:1,
// 						}
// 					}).render('2');
					//附件
					var fileAddressZone =new A.uploaddropzone({
						render : "#fileAddressfile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render('3');
					
					//发布时间
					var publishTimeDatePicker = new A.my97datepicker({
						id: "publishTimeId",
						name:"publishTime",
						render:"#publishTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'endTimeId\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					//结束时间
					var endTimeDatePicker = new A.my97datepicker({
						id: "endTimeId",
						name:"endTime",
						render:"#endTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'publishTimeId\\')}",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					
					//预览内容填写(富文本编辑器)
					var contentWysiwyg = new A.wysiwyg({
						render : "#previewDiv",
						options:{
							height:"400px",
							toolbar:[
										'font',
										null,
										'fontSize',
										null,
										{name:'bold', className:'btn-info'},
										{name:'italic', className:'btn-info'},
										{name:'strikethrough', className:'btn-info'},
										{name:'underline', className:'btn-info'},
										null,
										{name:'insertunorderedlist', className:'btn-success'},
										{name:'insertorderedlist', className:'btn-success'},
										{name:'outdent', className:'btn-purple'},
										{name:'indent', className:'btn-purple'},
										null,
										{name:'justifyleft', className:'btn-primary'},
										{name:'justifycenter', className:'btn-primary'},
										{name:'justifyright', className:'btn-primary'},
										{name:'justifyfull', className:'btn-inverse'},
										null,
// 										{name:'createLink', className:'btn-pink'},
// 										{name:'unlink', className:'btn-pink'},
// 										null,
										//{name:'insertImage', className:'btn-success'},
										null,
										'foreColor',
										null,
										{name:'undo', className:'btn-grey'},
										{name:'redo', className:'btn-grey'}
								    ],
						}
					}).render();
					
					//推荐位图片必填标识
					$('#commendPicturefileNeed').hide();
					$('input[name="ifCommend"]').change(function() {
						debugger;
						var yesCheck = $("input[type='checkbox']").is(':checked');
						if(yesCheck){
							$('#commendPicturefileNeed').show();
						}else{
							$('#commendPicturefileNeed').hide();
						}
					});
					
					//信息提交
					$('#addForm').validate({
						rules:  {
								title:{maxlength:256,required:true},
								publishTime:{required:true,maxlength:256},
								endTime:{required:true,maxlength:64},
								content:{required:true,maxlength:2000},
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/companyTrends/addEntity");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							//发布时间
							obj.publishTimeStr = $('#publishTimeId').val();
							//结束时间
							obj.endTimeStr = $('#endTimeId').val();
							//推荐位图片
							obj.commendPicture=JSON.stringify(commendPictureZone.getValue());
							//首页图片
// 							obj.homePagePicture=JSON.stringify(homePagePictureZone.getValue());
							//附件
							obj.fileAddress=JSON.stringify(fileAddressZone.getValue());
							//获取是否上传推荐位
							obj.commendStatus = $("input[type='checkbox']").is(':checked');
							//赋值状态为待提交
							obj.status = 1;
							//预览内容
							obj.preview = contentWysiwyg.getValue();
							if(obj.preview==null||obj.preview==""){
								alert("请填写预览内容！");
								return;
							}
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									A.loadPage({
										render : '#page-container',
										url : format_url('/companyTrends/index/')
									});
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					
					$("#saveBtn").on("click", function(){
						//获取checkbox值
						if($("input[type='checkbox']").is(':checked') && commendPictureZone.getValue().length==0){
							alert('请上传推荐页图片');
							return;
						}
						//上传时间
						//获取当月 yyyy-MM-dd
						var date = new Date();
						var seperator1 = "-";
						var month = date.getMonth() + 1;
						var day = date.getDate();
						var strDate = date.getDate();
						if (month >= 1 && month <= 9) {
							month = "0" + month;
						}
						if (day >= 0 && day <= 9) {
							day = "0" + day;
						}
						var currentdate = date.getFullYear() + seperator1 + month + seperator1 + day
						//创建时间赋值
						$("#uploadTimeid").val(currentdate);
						$("#addForm").submit();
    				});
					
					//由添加迁移页返回到列表页
    				$("#backBtnAddTrainPlan").on("click",function(){
    					A.loadPage({
    						render : '#page-container',
    						url : format_url("/companyTrends/index/")
    					});
    				});
    				
				});
			});
			
        </script>
    </body>
</html>
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
    			<li class="active">预览</li>
    		</ul><!-- /.breadcrumb -->
    		<div style="margin-right:180px;margin-top:10px;">
	   			<button id="backBtnAddTrainPlan" class="btn btn-xs btn-primary pull-right">
	   				<i class="glyphicon glyphicon-share-alt"></i>
	   				返回
	   			</button>
   			</div>
    		<h5 class="table-title header smaller blue" style="margin-left:30px;margin-right:20px">基础信息</h5>
    	</div>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:180px;" id="addForm">
			<input id="id" class="col-md-12" name="id"   readonly  type="hidden" value="${entity.id }"/>
			<input id="uploadPersonid" class="col-md-12" name="uploadPerson"   readonly  type="hidden" value="${sysUserEntity.name }"/>
			<input id="uploadTimeid" class="col-md-12" name="uploadTime"   readonly  type="hidden"/>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>标题：
					</label>
					<div class="col-md-10">
	                   <input class="col-md-12" id="titleId" name="title" type="text" placeholder="" maxlength="20" readonly value="${entity.title }">
                	</div>
               	</div>
               	<div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						副标题：
				    </label>
				    <div class="col-md-10">
						<input class="col-md-12" id="subTitleId" name="subTitle" type="text" placeholder="" maxlength="256" readonly value="${entity.subTitle }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>发布时间：</label>
                 		<div class="col-md-4">
                  		<input id="publishTimeDiv" class="col-md-12" readonly type="text" value="${entity.publishTimeShow}">
                  		</div>
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>结束时间：</label>
                 		<div class="col-md-4">
                  		<input id="endTimeDiv" class="col-md-12" readonly type="text" value="${entity.endTimeShow}">
                  		</div>
               </div>
               <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>内容
					</label>
					<div class="col-md-10">
	                   <textarea class="col-md-12" id="contentId" name="content"style="height:80px; resize:none;" readonly placeholder="" maxlength="128" >${entity.content }</textarea>
                	</div>
               </div>
               <label class="col-md-2 control-label no-padding-right" style="font-size:5px;">
				<span style="color:red;">*</span>预览内容
				</label>
               <div class="form-group" style="margin-left:265px;">
					
					<div class="col-md-10" id="previewDiv" name = "previewName" style="width: 1255px;">
<!-- 	                   <textarea class="col-md-12" id="contentId" name="content"style="height:400px; resize:none;"  placeholder="" maxlength="4000"></textarea> -->
                	</div>
               </div>
		       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">附件</label>
							<div class="col-md-10" id="fileAddressfile"></div>
               </div>
            </form>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','uploaddropzone','my97datepicker','wysiwyg'], function(A){
					var appData = ${entityJson};
					var id = ${id};
					
					//附件
					var fileAddressZone =new A.uploaddropzone({
						render : "#fileAddressfile",
						fileId:${entity.fileAddress},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render('3');
					
					//预览内容填写
					var contentWysiwyg = new A.wysiwyg({
						render : "#previewDiv",
						options:{
							height:"500px",
							editable:false,
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
									//	{name:'createLink', className:'btn-pink'},
									//	{name:'unlink', className:'btn-pink'},
									//	null,
									//{name:'insertImage', className:'btn-success'},
									null,
									'foreColor',
									null,
									{name:'undo', className:'btn-grey'},
									{name:'redo', className:'btn-grey'}
								    ],
						}
					}).render();
					contentWysiwyg.setValue(appData.preview);
					
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
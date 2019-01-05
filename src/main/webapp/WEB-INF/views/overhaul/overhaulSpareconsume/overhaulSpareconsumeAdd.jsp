<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li>
					检修管理
				</li>
				<li class="active">检修日志</li>
				<li class="active">工作任务填报</li>
			</ul>
		</div>
		
		<div style="float:right;margin-right:50px;">
			<button id="overhaulWorkTaskAddBtnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
			 <input id="overhaulArrangeId" name="overhaulArrangeId" type="hidden" value="${overhaulArrangeId}"/>
		       <div class="form-group">
					
				    <label class="col-md-2 control-label no-padding-right">
						物资名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="">
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						物资编码
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="code" name="code" type="text" placeholder="" maxlength="64" value="">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						规格型号
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="model" name="model" type="text" placeholder="" maxlength="64" value="">
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						生产厂家
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="manufacturer" name="manufacturer" type="text" placeholder="" maxlength="64" value="">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						单位
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="unit" name="unit" type="text" placeholder="" maxlength="64" value="">
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						数量
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="number" name="number" type="text" placeholder="" maxlength="64" value="">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						设备
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="equipName" name="equipName" type="text" placeholder="" maxlength="64" value="">
                    </div>
               </div>
            </form>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					$('#addForm').validate({
						debug:true,
						rules:  {
							id:{      maxlength:20},
							name:{      maxlength:64},
							code:{      maxlength:64},
							model:{      maxlength:64},
							manufacturer:{      maxlength:64},
							unit:{      maxlength:64},
							number:{      maxlength:64},
							equipName:{      maxlength:64}
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/overhaulSpareconsume");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									listFormDialog.close();
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						$("#addForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>
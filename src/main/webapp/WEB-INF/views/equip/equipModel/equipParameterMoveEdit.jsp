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
    
    			<li>
    				设备管理
    			</li>
    			<li>设备参数配置</li>
    			<li class="active">修改</li>
    		</ul><!-- /.breadcrumb -->
    		<h5 class="table-title header smaller blue" style="margin-left:30px">修改</h5>
    	</div>
    	<input type="hidden" id="id" name="id" value="${id}" />
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    主键
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="id" name="id" type="text" placeholder="${formField.toolTip}" maxlength="20" value="dataMap[id]">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    参数
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="parameter" name="parameter" type="text" placeholder="${formField.toolTip}" maxlength="64" value="dataMap[parameter]">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    默认值
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="default_value" name="default_value" type="text" placeholder="${formField.toolTip}" maxlength="64" value="dataMap[default_value]">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    参数类型
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="parameter_type" name="parameter_type" type="text" placeholder="${formField.toolTip}" maxlength="1" value="dataMap[parameter_type]">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    模版
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="model_id" name="model_id" type="text" placeholder="${formField.toolTip}" maxlength="20" value="dataMap[model_id]">
				    </div>	
               </div>
			</form>
    		<div style="margin-right:100px;">
        		<button id="backBtnEdit" class="btn btn-xs btn-primary pull-right">
    				<i class="glyphicon glyphicon-share-alt"></i>
    				返回
    			</button>
        		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			保存
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
        			var appData = ${dataMapJson};
					var id = $('#id').val();
					var currentPage = '${currentPage}';
					var pageSize = '${pageSize}';
					$('#editForm').validate({
						debug:true,
						rules: {},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/equipparameter/"+id);
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('操作成功');
									A.loadPage({
										render : '#page-container',
										url : format_url('/equipparameter/index/' + currentPage + '/' + pageSize)
									});
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					$("#editBtn").on("click", function(){
    					$("#editForm").submit();
    				});
					
					//由迁移修改页面返回列表页面
    				$("#backBtnEdit").on("click",function(){
    					A.loadPage({
    						render : '#page-container',
    						url : format_url('/equipparameter/index/' + currentPage + '/' + pageSize)
    					});
    				});
					
				});
			});
        </script>
    </body>
</html>
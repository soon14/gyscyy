<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
			<input id="status" name="status" type="hidden" value="N">
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>货位编码
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="code" name="code" type="text" placeholder="" maxlength="64" value="">
                	</div>
                	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>货位名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="">
                    </div>
               </div>
           
	           <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
						<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128"></textarea>
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
					var wareAreaId=${wareAreaId};
					//combobx组件
					var goodAreaIdCombobox = new A.combobox({
						render: "#goodAreaId",
						//返回数据待后台返回TODO
						datasource:'${'goodAreaIdCombobox'}',
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					$('#addForm').validate({
						debug:true,
						rules:  {
							code:{required:true, maxlength:64},
							name:{required:true,maxlength:64},
							remark:{maxlength:128},
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/wareHouseAreaPosition/saveAddPage/"+wareAreaId);
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
									if(result.result=="exception"){
										alert('添加失败！同一货区下货位编码不可重复！');
										return;
									}else{
										alert('添加成功');
										listFormDialog.close();
									}
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
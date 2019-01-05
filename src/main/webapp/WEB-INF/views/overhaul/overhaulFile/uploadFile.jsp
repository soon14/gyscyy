<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="col-md-12">
		<div class="page-content">
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"   id="addForm">
		       <div class="form-group form-horizontal">
					<label class="col-md-2 control-label no-padding-center">
						<span style="color:red;">*</span>上传文件
					</label>
                	<div class="col-xs-10" id="divfile" style="min-height:100px;">
					</div>
               </div>
            </form>
    		<div style="margin-top:20px;">
    			<button id="cancelBtn" class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','uploaddropzone','my97datepicker'], function(A){
					var fileSize = ${fileSize};
					var fileType = "${fileType}";
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
						maxFilesize:100,
						maxFiles:10,
						thumbnailHeight:60,
						thumbnailWidth:60,
						url:format_url("/overhaulFile/upload")
					}).render();
					$('#dropzone').css("min-height","120px");
					
					
					
					$('#addForm').validate({
						debug:true,
						rules:  {attchmentId:{required:true},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/overhaulFile/saveFile");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.attchmentId = JSON.stringify(uploaddropzone.getValue());
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
									 $("#page-container").load(format_url('/overhaulFile/index'));
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						if(uploaddropzone.getValue()==null || uploaddropzone.getValue()=="" || uploaddropzone.getValue()=="[]"){
							alert("请选择要上传的文件");
							return;
						}
						$("#addForm").submit();
    				});
    				
					$("#cancelBtn").on("click", function(){
						 $("#page-container").load(format_url('/overhaulFile/index'));
    				});
    				
				});
			});
        </script>
    </body>
</html>
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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
			<input class="col-md-12" id="id" name="id"  value="${standardEntity.id}" type="hidden">
		       <div class="form-group">
		       <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>文件名称
				    </label>
				    <div class="col-md-4"> 
				    <input class="col-md-12" id="name" name="name" type="text" readonly="readonly" placeholder="" maxlength="64" value="${standardEntity.name }">
<!-- 						<textarea class="col-md-12" id="name" name="name" style="height:80px; resize:none;"  placeholder="" maxlength="64" ></textarea> -->
					</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>版本
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="edition" name="edition" readonly="readonly" type="text" placeholder="" maxlength="64"value="${standardEntity.edition}">
                    </div>
               </div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>归口单位</label>
					<div class="col-md-4">
						<input class="col-md-12" id="unitName" name="unitName" readonly="readonly" type="text"  maxlength="64"value="${standardEntity.unitName}">
					</div>
				</div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
	                   <textarea class="col-md-12" id="remark" name="remark" readonly="readonly" style="height:80px; resize:none;"  placeholder="" maxlength="128">${standardEntity.remark}</textarea>
                	</div>
               </div>
               <div class="form-group form-horizontal">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>附件</label>
								<div class="col-xs-10" id="divfile">
								</div>
			   </div>
            </form>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${standardEntity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
					$('#editForm').validate({
						debug:true,
						rules:  {
							name:{ required:true,     maxlength:64},
							edition:{required:true,      maxlength:64},
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/standard/"+${id});
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							if(eval(uploaddropzone.getValue()).length==1){
								alert("请添加一个附件");
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
									if(result.result=="success"){
										alert('修改成功');	
									listFormDialog.close();
									}else{
										alert(result.errorMsg);
									}
								},
								error:function(v,n){
									alert('修改失败');
								}
							});
						}
					});
					$("#qx").on("click", function(){
// 						if(confirm("是否保存?")){
// 							$("#editForm").submit();
// 						}else{
							listFormDialog.close();	
// 						}
    				});
				});
			});
        </script>
    </body>
</html>
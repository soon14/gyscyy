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
			<input class="col-md-12" id="type" name="type"  value="${standardEntity.type}" type="hidden">
			<input class="col-md-12" id="unitValue" value="${standardEntity.unitId}" type="hidden">
		       <div class="form-group">
		       <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>文件名称
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="${standardEntity.name }">
<!-- 						<textarea class="col-md-12" id="name" name="name" style="height:80px; resize:none;"  placeholder="" maxlength="64" ></textarea> -->
					</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>版本
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="edition" name="edition" type="text" placeholder="" maxlength="64"value="${standardEntity.edition}">
                    </div>
               </div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>归口单位</label>
					<div class="col-md-4">
						<select id="editUnit"  name="unitId"></select>
					</div>
				</div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
	                   <textarea class="col-md-12" id="remark" name="remark"style="height:80px; resize:none;"  placeholder="" maxlength="128">${standardEntity.remark}</textarea>
                	</div>
               </div>
               <div class="form-group form-horizontal">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>附件</label>
								<div class="col-xs-10" id="divfile">
								</div>
			   </div>
            </form>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right"id="qx">
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
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${standardEntity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					
					//归口单位
					var unitCombobox = new A.combobox({
						render : "#editUnit",
						name : "unitId",
						datasource : ${unitList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					unitCombobox.setValue($("#unitValue").val());
					
					$('#editForm').validate({
						debug:true,
						rules:  {
							name:{ required:true,     maxlength:64},
							edition:{required:true,      maxlength:64},
							fileId:{required:true,      maxlength:4000},
							unitId:{required:true},
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/standard/"+${id});
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							if(eval(uploaddropzone.getValue()).length==0){
								alert("请添加一个附件");
								return;
							}
							var unitName = $("#editUnit ").find("option:selected").text();
							obj.unitName = unitName;
							debugger;
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
					$("#saveBtn").on("click", function(){
						$("#editForm").submit();
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
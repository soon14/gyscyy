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
			 <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>典型票名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="name" readonly="readonly"
						name="name" type="text" placeholder="" maxlength="64" value="${t.name}">
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>典型票类型
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="typeName" readonly="readonly"
	                    name="typeName" type="text" placeholder="" maxlength="64" value="${t.typeName}">
                	</div>
               </div>
			</form>
    		<div style="margin-right:100px;">
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="glyphicon glyphicon-remove"></i>
        			关闭
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					//票类型
					var typeId = new A.combobox({
						render : "#type",
						datasource : ${typicalTicketType},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					typeId.setValue('${t.type}');
					$('#editForm').validate({
						debug:true,
						rules: {
							name : {required : true,maxlength : 64},
							type : {required : true,maxlength : 64},
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/typicalTicket/"+${t.id});
							var obj = $("#editForm").serializeObject();
							obj.id=${t.id};
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										alert('操作成功');
										listFormDialog.close();
									}else{
										alert(result.errorMsg);
									}
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
				});
			});
        </script>
    </body>
</html>
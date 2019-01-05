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
			   <input class="col-md-12" id="flag" name="flag" type="hidden" value="1">
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>典型票名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="">
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>典型票类型
					</label>
					<div class="col-md-4">
					 <select id="type" class="col-md-12" name="type"></select>
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
					//票类型
					var typeId = new A.combobox({
						render : "#type",
						datasource : ${typicalTicketType},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					$('#addForm').validate({
						debug:true,
						rules:  {
							name : {required : true,maxlength : 64},
							type : {required : true,maxlength : 64},
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/typicalTicket");
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
									listFormDialog.close();
									var url="";
									//每一个票的添加页面
									if(result.data.type==1){
										url=format_url('/workTicket/getAdd',{"typicalTicketId":result.data.id});
									}
									if(result.data.type==2){
										url=format_url('/workTicketTwo/getAdd',{"typicalTicketId":result.data.id});
									}
									if(result.data.type==3){
// 										url=format_url('/operationTicket/getAdd',{"typicalTicketId":result.data.id});
										url=format_url('/operationTicket/getAdd?typicalTicketId='+result.data.id);
									}
									if(result.data.type==4){
										url=format_url('/interventionTicket/getAdd',{"typicalTicketId":result.data.id});
									}
									A.loadPage({
										render : '#page-container',
										url :url,
										callback:function(response,status,xhr){
											
										}
									});
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
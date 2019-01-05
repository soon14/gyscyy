<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addFormInvalid">
			<input id="invalidId" name="id" type="hidden" value="${invalidId}"/>
		       <div class="form-group">
					<label class="col-md-4 control-label no-padding-right">
						鉴定时间
					</label>
					<div class="col-md-8">
					   <input class="col-md-12" id="invalidDate" name="invalidDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${dateInvalid}" type="date"/>'>
                	</div>
               </div>
               <div class="form-group">
				    <label class="col-md-4 control-label no-padding-right">
						鉴定人
				    </label>
				    <div class="col-md-8">
				    	<input class="col-md-12" id="invalidId" name="invalidId" type="hidden"  readonly="readonly" maxlength="64" value="${userEntity.id}">
						<input class="col-md-12" id="invalidName"  type="text"  readonly="readonly" maxlength="64" value="${userEntity.name}">
                    </div>
               </div>
               <div class="form-group">
				    <label class="col-md-4 control-label no-padding-right">
						鉴定类型
				    </label>
				    <div class="col-md-8">
				    	 <select id="identify" name="identify" class="form-control chosen-select" ></select>
                    </div>
               </div>
               <div class="form-group">
						<label class="col-md-4 control-label no-padding-right"><span style="color:red;">*</span>备注</label>
						<div class="col-md-8">
							<textarea id="invalidContent" name="invalidContent" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea>
						</div>
				</div>
		       
            </form>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveInvalid" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree'], function(A){
					
					//下拉框
					var identifyCombobox = new A.combobox({
						render: '#identify',
						datasource:${identifyType},
						allowBlank:true,
						options:{
							"disable_search_threshold":10,
							"placeholder_text_single" : "请选择"
						}
					}).render();
					
					
					$('#addFormInvalid').validate({
						debug:true,
						rules:  {
							invalidContent:{required:true,maxlength:64},identify:{required:true,maxlength:20}
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/operationTicket/saveInvalid");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addFormInvalid").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('鉴定成功');
									listFormDialog.close();
									operationTicketDatatables.draw();
								},
								error:function(v,n){
									alert('鉴定失败');
								}
							});
						}
					});
					$("#saveInvalid").on("click", function(){
						$("#addFormInvalid").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>
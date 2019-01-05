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
				<form class="form-horizontal" role="form" style="margin-right:100px;" id="workSafeAddForm">
						<input id="workId" name="workticketId" type="hidden" value="${workId}"/>
						<input id="uuid" name="uuidCode" type="hidden" value="${uuid}"/>
							<div class="form-group" style="display: none;">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>序号</label>
									<div class="col-md-4">
											<input class="col-md-12" id="orderSeq" name="orderSeq" type="text" placeholder="" maxlength="20" value="${total}">
								</div>
								<label class="col-md-2 control-label no-padding-right">执行情况</label>
								<div class="col-md-4">
								
								 <select id="executeSituation" name="executeSituation"    class="form-control chosen-select" style="width:200px;"></select>
								 
								</div>
							</div>
							
						<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>安全措施</label>
								<div class="col-md-10">
									<textarea id="signerContent" name="signerContent"  style="height:80px; resize:none;" class="col-md-12" maxlength="300"></textarea>
								</div>
						</div>
								
			</form>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveBtnSafe" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					
					//状态下拉框
					var zxqkCombobox = new A.combobox({
						render: '#executeSituation',
						datasource:${executeSituation},
						allowBlank:true,
						options:{
							"disable_search_threshold":10,
							"placeholder_text_single" : "请选择"
						}
					}).render();
					
					var flag=${flag};
					if(flag==4||flag==5){
						$('#workSafeAddForm').validate({
							debug:true,
							rules:  {
								signerContent:{required:true,maxlength:512}
								},
							submitHandler: function (form) {
								//添加按钮
								var url = format_url("/workSafe");
								//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
								var obj = $("#workSafeAddForm").serializeObject();
								obj.safeType=${flag};
								$("#saveBtnSafe").attr({"disabled":"disabled"});
								$.ajax({
									url : url,
									contentType : 'application/json',
									dataType : 'JSON',
									data : JSON.stringify(obj),
									cache: false,
									type : 'POST',
									success: function(result){
										alert('添加成功');
										workSafeOneDialog.close();
									},
									error:function(v,n){
										alert('添加失败');
									}
								});
							}
						});
					}else{
						$('#workSafeAddForm').validate({
							debug:true,
							rules:  {
								orderSeq:{required:true,maxlength:20},
								signerContent:{required:true,maxlength:512}
								},
							submitHandler: function (form) {
								//添加按钮
								var url = format_url("/workSafe");
								//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
								var obj = $("#workSafeAddForm").serializeObject();
								obj.safeType=${flag};
								$("#saveBtnSafe").attr({"disabled":"disabled"});
								$.ajax({
									url : url,
									contentType : 'application/json',
									dataType : 'JSON',
									data : JSON.stringify(obj),
									cache: false,
									type : 'POST',
									success: function(result){
										alert('添加成功');
										workSafeOneDialog.close();
									},
									error:function(v,n){
										alert('添加失败');
									}
								});
							}
						});
					}
					
					$("#saveBtnSafe").on("click", function(){
						$("#workSafeAddForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>
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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addsmForm">
							<input class="col-md-12" id="rlId" name="rlId" type="hidden" value="${rlId}">
							<input class="col-md-12" id="flag" name="meetingFlag" type="hidden" value="${flag}">							
		        <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>内容
					</label>
					<div class="col-md-10">
                    <textarea placeholder="请输入内容" name="meetingContent"  class="col-md-12" style="height:150px; resize:none;"></textarea>                   				    					
                	</div>							   
               </div>
		       <div class="form-group">									
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>措施检查
				    </label>
				    <div class="col-md-1">
                	<input name="checkState" type="checkbox" id="checkStateid"/>			                  					                   
                    </div>
               </div>
               
            </form>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="savesmBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					var checkStateCombobox = new A.combobox({
						render: "#checkStateDiv",
						datasource:${checkStateCombobox},
						options:{
							"disable_search_threshold":10
						}
					}).render();
					$('#addsmForm').validate({
						debug:true,
						rules:  {meetingContent:{required:true,   maxlength:64},checkState:{required:true},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/safeMeeting");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addsmForm").serializeObject();
						//	var checkStatetag=document.getElementsByName('checkState');
							if($('#checkStateid').is(":checked"))	
							{
								obj.checkState='checked';
							}else{
								obj.checkState='uncheck';								
							}
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
					$("#savesmBtn").on("click", function(){
						$("#addsmForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>
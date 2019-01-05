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
						    	<input type="hidden" id="iid" name="iid" value="${id}" />
			
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    测试时间
				    </label>
				    <div class="col-md-4">
					    <div id="testDateDiv">
				    </div>					    
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    氧气测定值
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="testValue" name="testValue" type="text" placeholder="" maxlength="64" value="${ entity.testValue }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    有毒有害气体测定值
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="testToxicGas" name="testToxicGas" type="text" placeholder="" maxlength="64" value="${ entity.testToxicGas }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    测试位置
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="testAddress" name="testAddress" type="text" placeholder="" maxlength="64" value="${ entity.testAddress }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    测量人
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="testPerson" name="testPerson" type="text" placeholder="" maxlength="64" value="${ entity.testPerson }">
				    </div>	
               </div>
			</form>
    		<div style="margin-right:100px;">
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="ace-icon fa fa-times"></i>
        			取消
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
        			var appData = ${entityJson};
					var id = $('#iid').val();
					alert(id)
					//日期组件
					var testDatePicker = new A.my97datepicker({
						id: "testDateId",
						name: "testDate",
						render: "#testDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					testDatePicker.setValue(appData.testDate);
					$('#editForm').validate({
						debug:true,
						rules: {
							testValue:{      maxlength:64},
							testToxicGas:{      maxlength:64},
							testAddress:{      maxlength:64},
							testPerson:{      maxlength:64},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/workTicketTest/"+id);
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							obj.id=$('#iid').val();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('操作成功');
									workTestDialog.close();
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
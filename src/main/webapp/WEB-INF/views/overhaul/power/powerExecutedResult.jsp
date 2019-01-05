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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
			<input id="id" value="${entity.id }" type="hidden" name="id"/>
				<div class="form-group"  style="margin-top:40px;">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>执行结束时间
				    </label>
				    <div class="col-md-4">
                    	<div id="endDateDiv"></div>
                    </div>	
    		     </div>
		       <div class="form-group"  style="margin-top:30px;">
					<label class="col-md-2 control-label no-padding-right">
						   <span style="color:red;">*</span>完成情况
					</label>
					<div class="col-md-10">
					<textarea id="measur" name="measur" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="64"></textarea>
                	</div>
               </div>
		       <div class="form-group"  style="margin-top:30px;">
					<label class="col-md-2 control-label no-padding-right">
						 结果附件
					</label>
					<div class="col-xs-10" id="divfile1">
					</div>
               </div>
			    </form>
    		<div style="margin-right:100px;" id="saveBtn">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="function" class="btn btn-xs btn-success pull-right" style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				提交
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['my97datepicker','combobox','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzone=new A.uploaddropzone({
						render : "#divfile1",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render('Other2');
					
					
					//日期组件
					var endDatePicker = new A.my97datepicker({
						id: "endDate",
						name:"endDate",
						render:"#endDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "<fmt:formatDate  pattern='yyyy-MM-dd HH:mm' value='${entity.requestDate}' type='date'/>",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					
					$('#addForm').validate({
						debug:true,
						rules: {
							endDate:{required:true,maxlength:30},
							measur:{required:true,maxlength:1024}
							    
							 },
						submitHandler: function (form) {
							 
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.taskId=$("#taskId").val();
							obj.procInstId=$("#procInstId").val();
							obj.endDate = $('#endDate').val()+":00";
							obj.attchmentResultIds=JSON.stringify(uploaddropzone.getValue());

							obj.id= $('#id').val();
							var url="power/executed/";
							$.ajax({
								url :  format_url("/"+url+"/"+obj.taskId),
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										alert('提交成功');	
										window.scrollTo(0,0);
										listFormDialog.close();
										$("#page-container").load(format_url('/todoTask/list/1/10'));
									}else{
										alert(result.result);
									}
								},
								error:function(v,n){
									alert('提交失败');
								}
							});
						}
					});
					
					
					
					$("#function").on("click", function(e){
						$("#addForm").submit();
    				});
				});
			});
        </script>
    </body>
</html>
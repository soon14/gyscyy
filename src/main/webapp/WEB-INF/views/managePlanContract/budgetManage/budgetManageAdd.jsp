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
			    <input class="col-md-12" id="year" name="year" type="hidden"  value="${giveDate }">
			    <input class="col-md-12" id="unitId" name="unitId" type="hidden"  value="${unitId }">

		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>项目
				    </label>
				    <div class="col-md-10">
						<input class="col-md-12" id="projectName" name="projectName" type="text" placeholder="" maxlength="64" value="" style="height: 50px;">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>本年发生数
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="occurNum" name="occurNum" type="text" placeholder="" maxlength="64" value="">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>本年预算数
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="budgetNum" name="budgetNum" type="text" placeholder="" maxlength="64" value="">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						执行预算差额
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="differentNum" name="differentNum" type="text" placeholder="" maxlength="64" value="">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						执行率
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="implementRate" name="implementRate" type="text" placeholder="" maxlength="64" value="">
                    </div>
               </div>
<!-- 		       <div class="form-group"> -->
<!-- 					<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						单位 -->
<!-- 					</label> -->
<!-- 					<div class="col-md-4"> -->
<!-- 	                   <input class="col-md-12" id="unitId" name="unitId" type="text" placeholder="" maxlength="64" value=""> -->
<!--                 	</div> -->
<!--                </div> -->
            </form>
    		<div style="margin-right:100px; margin-top: 30px;">
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
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
					//日期组件
					var yearDatePicker = new A.my97datepicker({
						id: "yearId",
						name:"year",
						render:"#yearDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM"
						}
					}).render();
					$('#addForm').validate({
						debug:true,
						rules:  {
							id:{maxlength:20},
							year:{maxlength:64},
							projectName:{maxlength:64,required:true},
							occurNum:{maxlength:64,required:true},
							budgetNum:{maxlength:64,required:true},
							differentNum:{maxlength:64},
							implementRate:{maxlength:64},
							unitId:{maxlength:64},
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/budgetManage");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.year = $("#year").val()+"-01-01 00:00:00";
							
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
					$("#saveBtn").on("click", function(){
						$("#addForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>
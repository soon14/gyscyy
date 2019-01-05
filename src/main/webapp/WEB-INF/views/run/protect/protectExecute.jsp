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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addPEForm">
		        <div class="form-group">
		        <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>执行结束时间
					</label>
					<div class="col-md-4">
					   <div id="executeEndtimeDiv"></div>
                	</div>					   				   			  
				</div>		    
               <div class="form-group">
  				<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>完成情况
				    </label>	              
                <div class="col-md-10">
                    <textarea placeholder="请输入完成情况" name="finishSituation"  class="col-md-12" style="height:90px; resize:none;"></textarea>                   				    
                    </div>
               </div>
               <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
                	   <textarea placeholder="请输入保护变动内容" name="executeRemarks"  class="col-md-12" style="height:90px; resize:none;"></textarea>
                	</div>
               </div>
               <div class="form-group form-horizontal">
								<label class="col-md-2 control-label no-padding-right">附件</label>
								<div class="col-xs-10" id="divfileExecute">
								</div>
				</div>
            </form>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="savePEBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzoneExecute =new A.uploaddropzone({
						render : "#divfileExecute",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render('Other');
					//日期组件
					var recordTimeDatePicker = new A.my97datepicker({
						id: "executeEndtime",
						name:"executeEndtime",
						render:"#executeEndtimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					$('#addPEForm').validate({
						debug:true,
						rules:  {
							executeEndtime:{required:true,date:true},
							finishSituation:{required:true,maxlength:128},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/protect/zxrExecute");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addPEForm").serializeObject();
							obj.executeFileId=JSON.stringify(uploaddropzoneExecute.getValue());
							obj.taskId=$("#taskId").val();
							obj.procInstId=$("#procInstId").val();
							obj.id=$("#protectId").val();
							obj.executeEndtime=$("#executeEndtime").val();
						//	$("#savePEBtn").attr({"disabled":"disabled"});

							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									window.scrollTo(0,0);
									listFormDialog.close();
									$("#page-container").load(format_url('/todoTask/list/1/10'));
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#savePEBtn").on("click", function(){
						$("#addPEForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>
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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addTargetForm">
			   <input id="id" class="col-md-12" name="id"   readonly  type="hidden"/>
			   
	           <div class="form-group">
 					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>年号
				    </label>
				    <div class="col-md-4">
						<div id="targetDateDiv"></div>
                    </div>  
                       <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>时间
					</label>
					<div class="col-md-4">
					   <div id="signDateDiv"></div>
                	</div>
               </div>
		       <div class="form-group">
				 
                    <label class="col-md-2 control-label no-padding-right">
					<span style="color:red;">*</span> 填写人
				    </label>
				    <div class="col-md-4">
				         <input  class="col-md-12" id="createUserId" name="createUserId" placeholder=""  type="text" maxlength="64" readonly="readonly" value="${userName }">
                    </div> 
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>名称
					</label>
					<div class="col-md-4">
								<input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="">
								</div>
               </div>
<!--               <div class="form-group"> -->
<!-- 					<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						备注 -->
<!-- 					</label> -->
<!-- 					<div class="col-md-10"> -->
<!-- 	                   <textarea class="col-md-12" id="remark" name="remark"style="height:80px; resize:none;"  placeholder="" maxlength="128"></textarea> -->
<!--                 	</div> -->
<!--                </div> -->
               <div class="form-group">
				<label class="col-md-2 control-label no-padding-right">
				<span style="color:red;">*</span>附件
                   </label>
				   <div class="col-md-10" id="divfile">
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
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
						options : {
							maxFiles:1,
						}
					}).render();
					
					//日期组件 年号
					var targetDateDatePicker = new A.my97datepicker({
						id: "targetDateId",
						name:"targetDate",
						render:"#targetDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					//日期组件 签订时间
					var signDateDatePicker = new A.my97datepicker({
						id: "signDateId",
						name:"signDate",
						render:"#signDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					//单位
// 					 searchunitid = new A.combobox({
// 						render : "#signUnitId",
// 						datasource : ${unitList},
// 						allowBlank: true,
// 						options : {
// 							"disable_search_threshold" : 10
// 						}
// 					}).render();
					
					$('#addTargetForm').validate({
						debug:true,
						rules:  {
							id:{      maxlength:20},
							name:{maxlength:64},
							targetDate:{  required:true ,     maxlength:64},
							signDate:{ required:true, maxlength:64},
							name:{ required:true , maxlength:64}
						
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/targetManage/add");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addTargetForm").serializeObject();
							obj.targetDate = $('#targetDateId').val()+"-01-01 00:00:00";
							obj.signDate = $('#signDateId').val()+" 00:00:00";
							//附件上传
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							obj.type=$("#type").val()
							
							if(eval(uploaddropzone.getValue()).length==0){
								alert("请上传附件");
								return;
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
					$("#saveBtn").on("click", function(){
						
						var yearNumStr = targetDateDatePicker.getValue();
						var checkDateStr = signDateDatePicker.getValue().split("-")[0];
						if(yearNumStr!=checkDateStr){
							alert("年号和时间不符");
							return;
						}
						$("#addTargetForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>